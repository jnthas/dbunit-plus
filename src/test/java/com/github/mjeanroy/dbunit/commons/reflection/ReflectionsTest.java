/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 - 2016 Mickael Jeanroy
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

package com.github.mjeanroy.dbunit.commons.reflection;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ReflectionsTest {

	@Test
	public void it_should_get_static_fields() {
		List<Field> fields = Reflections.findStaticFields(TestClass.class);
		assertThat(fields)
			.isNotNull()
			.isNotEmpty()
			.hasSize(2)
			.extracting("name")
			.contains("F1", "F2");
	}

	@Test
	public void it_should_get_static_methods() {
		List<Method> fields = Reflections.findStaticMethods(TestClass.class);
		assertThat(fields)
			.isNotNull()
			.isNotEmpty()
			.hasSize(1)
			.extracting("name")
			.contains("m1");
	}

	private static class TestClass {
		public static final int F1 = 0;
		public static final int F2 = 1;

		public static int m1() {
			return F1 + F2;
		}

		public int f1;
		public int f2;

		public int compute() {
			return f1 + f2;
		}
	}
}
