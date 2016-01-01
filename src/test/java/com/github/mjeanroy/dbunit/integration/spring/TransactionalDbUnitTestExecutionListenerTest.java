/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 - 206 Mickael Jeanroy
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

import org.junit.Test;
import org.springframework.test.context.TestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.util.List;

import static com.github.mjeanroy.dbunit.tests.utils.TestUtils.readPrivate;
import static org.assertj.core.api.Assertions.assertThat;

public class TransactionalDbUnitTestExecutionListenerTest {

	@SuppressWarnings("unchecked")
	@Test
	public void it_should_create_listener() throws Exception {
		TransactionalDbUnitTestExecutionListener listener = new TransactionalDbUnitTestExecutionListener();

		List<TestExecutionListener> listeners = (List<TestExecutionListener>) readPrivate(listener, "listeners", List.class);
		assertThat(listeners)
			.isNotNull()
			.isNotEmpty()
			.hasSize(2);

		assertThat(listeners.get(0))
			.isNotNull()
			.isExactlyInstanceOf(TransactionalTestExecutionListener.class);

		assertThat(listeners.get(1))
			.isNotNull()
			.isExactlyInstanceOf(DbUnitTestExecutionListener.class);

		List<TestExecutionListener> reverseListeners = (List<TestExecutionListener>) readPrivate(listener, "reverseListeners", List.class);
		assertThat(reverseListeners)
			.isNotNull()
			.isNotEmpty()
			.hasSize(2);

		assertThat(reverseListeners.get(0))
			.isNotNull()
			.isExactlyInstanceOf(DbUnitTestExecutionListener.class);

		assertThat(reverseListeners.get(1))
			.isNotNull()
			.isExactlyInstanceOf(TransactionalTestExecutionListener.class);
	}
}