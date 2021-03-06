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

package com.github.mjeanroy.dbunit.integration.spring;

import org.junit.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class EmbeddedDatabaseRuleTest {

	@Test
	public void it_should_create_rule() {
		EmbeddedDatabase db = mock(EmbeddedDatabase.class);
		EmbeddedDatabaseRule rule = new EmbeddedDatabaseRule(db);
		assertThat(rule.getDb())
			.isNotNull()
			.isSameAs(db);
	}

	@Test
	public void it_should_create_default_rule() {
		EmbeddedDatabaseRule rule = new EmbeddedDatabaseRule();
		assertThat(rule.getDb()).isNotNull();
	}

	@Test
	public void it_should_shutdown_db_after_test() {
		EmbeddedDatabase db = mock(EmbeddedDatabase.class);
		EmbeddedDatabaseRule rule = new EmbeddedDatabaseRule(db);
		rule.after();
		verify(db).shutdown();
	}
}
