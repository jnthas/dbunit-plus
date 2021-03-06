/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Mickael Jeanroy
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

package com.github.mjeanroy.dbunit.commons.lang;

/**
 * Static Objects Utilities.
 */
public final class Objects {

	// Ensure non instantiation.
	private Objects() {
	}

	/**
	 * Get first non {@code null} value:
	 * <ol>
	 *   <li>If {@code o1} is not {@code null}, it is returned.</li>
	 *   <li>Otherwise, return {@code o2}.</li>
	 * </ol>
	 *
	 * @param o1 First value.
	 * @param o2 Second value.
	 * @param <T> Type of values.
	 * @return First non {@code null} value.
	 */
	public static <T> T firstNonNull(T o1, T o2) {
		return o1 == null ? o2 : o1;
	}

	/**
	 * Check that two values are equals:
	 * <ul>
	 *   <li>If both are the same instances (or {@code null}, return {@code true}.</li>
	 *   <li>If one is {@code null}, return {@code false}.</li>
	 *   <li>Finally, if both are non {@code null}, return the result of {code o1.equals(o2)}.</li>
	 * </ul>
	 *
	 * @param v1 First parameter to check.
	 * @param v2 Second parameter to check.
	 * @return Result of equality.
	 */
	public static boolean equals(Object v1, Object v2) {
		return v1 == v2 || (v1 != null && v2 != null && v1.equals(v2));
	}

	/**
	 * Compute {@code hashCode} value from all parameters.
	 *
	 * @param values Parameters.
	 * @return Hash Code value.
	 */
	public static int hashCode(Object... values) {
		if (values == null) {
			return 0;
		}

		int result = 1;
		for (Object element : values) {
			result = 31 * result + (element == null ? 0 : element.hashCode());
		}

		return result;
	}
}
