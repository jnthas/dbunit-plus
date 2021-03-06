/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2015-2016 Mickael Jeanroy
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.mjeanroy.dbunit.integration.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;

import java.util.LinkedList;
import java.util.List;

/**
 * Allow execution of several {@link TestExecutionListener} in the right order.
 */
public class CompositeTestExecutionListener implements TestExecutionListener {

	/**
	 * Class Logger.
	 */
	private static final Logger log = LoggerFactory.getLogger(CompositeTestExecutionListener.class);

	/**
	 * Set of listener to execute before test execution.
	 */
	private final LinkedList<TestExecutionListener> listeners;

	/**
	 * Set of listeners to execute after test execution: basically only the original listeners in
	 * reverse order.
	 */
	private final LinkedList<TestExecutionListener> reverseListeners;

	public CompositeTestExecutionListener(List<TestExecutionListener> listeners) {
		this.listeners = new LinkedList<TestExecutionListener>();
		this.reverseListeners = new LinkedList<TestExecutionListener>();

		// Create both lists in one pass.
		for (TestExecutionListener listener : listeners) {
			this.listeners.addLast(listener);
			this.reverseListeners.addFirst(listener);
		}
	}

	@Override
	public void beforeTestClass(final TestContext testContext) throws Exception {
		execute(listeners, new ListenerFunction() {
			@Override
			public void apply(TestExecutionListener input) throws Exception {
				input.beforeTestClass(testContext);
			}
		});
	}

	@Override
	public void prepareTestInstance(final TestContext testContext) throws Exception {
		execute(listeners, new ListenerFunction() {
			@Override
			public void apply(TestExecutionListener listener) throws Exception {
				listener.prepareTestInstance(testContext);
			}
		});
	}

	@Override
	public void beforeTestMethod(final TestContext testContext) throws Exception {
		execute(listeners, new ListenerFunction() {
			@Override
			public void apply(TestExecutionListener listener) throws Exception {
				listener.beforeTestMethod(testContext);
			}
		});
	}

	@Override
	public void afterTestMethod(final TestContext testContext) throws Exception {
		execute(reverseListeners, new ListenerFunction() {
			@Override
			public void apply(TestExecutionListener listener) throws Exception {
				listener.afterTestMethod(testContext);
			}
		});
	}

	@Override
	public void afterTestClass(final TestContext testContext) throws Exception {
		execute(reverseListeners, new ListenerFunction() {
			@Override
			public void apply(TestExecutionListener listener) throws Exception {
				listener.afterTestClass(testContext);
			}
		});
	}

	private void execute(List<TestExecutionListener> listeners, ListenerFunction func) throws Exception {
		Exception ex = null;

		for (TestExecutionListener listener : listeners) {
			try {
				func.apply(listener);
			}
			catch (Exception e) {
				log.error(e.getMessage(), e);
				ex = e;
			}
		}

		// Throw last exception.
		if (ex != null) {
			throw ex;
		}
	}

	private static interface ListenerFunction {

		void apply(TestExecutionListener listener) throws Exception;
	}
}
