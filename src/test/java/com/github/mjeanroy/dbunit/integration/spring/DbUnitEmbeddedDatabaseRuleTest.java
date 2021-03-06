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

import com.github.mjeanroy.dbunit.tests.fixtures.TestClassWithDataSet;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.mockito.InOrder;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import static com.github.mjeanroy.dbunit.tests.db.JdbcQueries.countFrom;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DbUnitEmbeddedDatabaseRuleTest {

	@Test
	public void it_should_create_rule_with_database() {
		EmbeddedDatabase db = mock(EmbeddedDatabase.class);
		DbUnitEmbeddedDatabaseRule rule = new DbUnitEmbeddedDatabaseRule(db);
		assertThat(rule.getDb()).isSameAs(db);
	}

	@Test
	public void it_should_create_rule_with_default_database() {
		DbUnitEmbeddedDatabaseRule rule = new DbUnitEmbeddedDatabaseRule();
		assertThat(rule.getDb()).isNotNull();
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	@Test
	public void it_should_start_database_and_load_data_set() throws Throwable {
		final Statement statement = mock(Statement.class);
		final Class testClass = TestClassWithDataSet.class;
		final Description description = mock(Description.class);
		when(description.getTestClass()).thenReturn(testClass);
		when(description.getMethodName()).thenReturn("method1");

		final EmbeddedDatabase db = spy(new EmbeddedDatabaseBuilder()
			.setType(EmbeddedDatabaseType.HSQL)
			.addScript("classpath:/sql/init.sql")
			.build());

		final DbUnitEmbeddedDatabaseRule rule = new DbUnitEmbeddedDatabaseRule(db);

		Statement result = rule.apply(statement, description);

		assertThat(result).isNotNull();
		verify(statement, never()).evaluate();
		verify(db, never()).shutdown();

		doAnswer(new Answer() {
			@Override
			public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
				assertThat(countFrom(db.getConnection(), "foo")).isEqualTo(2);
				assertThat(countFrom(db.getConnection(), "bar")).isEqualTo(3);
				return null;
			}
		}).when(statement).evaluate();

		result.evaluate();

		InOrder inOrder = inOrder(db, statement);
		inOrder.verify(statement).evaluate();
		inOrder.verify(db).shutdown();
	}
}
