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

package com.github.mjeanroy.dbunit.json;

import com.github.mjeanroy.dbunit.exception.JsonException;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Parse JSON file and return DBUnit dataSet as {@link Map}.
 * Each implementation should wrap specific exception to an internal {@link JsonException} (library
 * will catch instance of this exception and re-throw appropriate exception).
 */
public interface JsonParser {

	/**
	 * Read JSON File and return representation.
	 *
	 * @param input Input file.
	 * @return DataSet representation.
	 * @throws JsonException If parse/read operation fail (invalid schema, unreadable file).
	 */
	Map<String, List<Map<String, Object>>> parse(File input) throws JsonException;
}
