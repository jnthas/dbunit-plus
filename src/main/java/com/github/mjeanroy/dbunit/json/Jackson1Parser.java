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
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.github.mjeanroy.dbunit.commons.lang.PreConditions.notNull;

public class Jackson1Parser implements JsonParser {

	/**
	 * Class Logger.
	 */
	private static final Logger log = LoggerFactory.getLogger(Jackson2Parser.class);

	/**
	 * Internal Jackson2 Mapper.
	 */
	private final ObjectMapper mapper;

	/**
	 * Create parser with default object mapper.
	 */
	Jackson1Parser() {
		this(new ObjectMapper());
	}

	/**
	 * Create parser with Jackson2 mapper.
	 *
	 * @param mapper Mapper.
	 * @throws NullPointerException If {@code mapper} is {@code null}.
	 */
	public Jackson1Parser(ObjectMapper mapper) {
		this.mapper = notNull(mapper, "Jackson1 Object Mapper should not be null");
	}

	@Override
	@SuppressWarnings("unchecked")
	public Map<String, List<Map<String, Object>>> parse(File input) throws JsonException {
		try {
			return (Map<String, List<Map<String, Object>>>) mapper.readValue(input, Map.class);
		}
		catch (JsonParseException ex) {
			log.error(ex.getMessage(), ex);
			throw new JsonException(ex);
		}
		catch (JsonMappingException ex) {
			log.error(ex.getMessage(), ex);
			throw new JsonException(ex);
		}
		catch (IOException ex) {
			log.error(ex.getMessage(), ex);
			throw new JsonException(ex);
		}
	}
}
