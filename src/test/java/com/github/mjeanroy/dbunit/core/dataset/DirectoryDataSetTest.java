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

package com.github.mjeanroy.dbunit.core.dataset;

import com.github.mjeanroy.dbunit.tests.utils.FileComparator;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ITableIterator;
import org.dbunit.dataset.ITableMetaData;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.github.mjeanroy.dbunit.tests.utils.TestUtils.getTestResource;
import static org.assertj.core.api.Assertions.assertThat;

public class DirectoryDataSetTest {

	@Test
	public void it_should_create_directory_dataset() throws Exception {
		File directory = getTestResource("/dataset/xml");

		DirectoryDataSet dataSet = new DirectoryDataSet(directory, false, new FileComparator());

		assertThat(dataSet.getPath())
			.isNotNull()
			.isEqualTo(directory);
	}

	@Test
	public void it_should_return_table_names() throws Exception {
		File directory = getTestResource("/dataset/xml");
		DirectoryDataSet dataSet = new DirectoryDataSet(directory, false, new FileComparator());

		String[] tableNames = dataSet.getTableNames();
		assertThat(tableNames)
			.isNotNull()
			.hasSize(2)
			.containsOnly("foo", "bar");
	}

	@Test
	public void it_should_get_table() throws Exception {
		File directory = getTestResource("/dataset/xml");
		DirectoryDataSet dataSet = new DirectoryDataSet(directory, false, new FileComparator());

		ITable t1 = dataSet.getTable("foo");
		ITable t2 = dataSet.getTable("bar");
		assertThat(t1).isNotNull();
		assertThat(t1.getRowCount()).isEqualTo(2);

		assertThat(t2).isNotNull();
		assertThat(t2.getRowCount()).isEqualTo(3);
	}

	@Test
	public void it_should_get_table_metadata() throws Exception {
		File directory = getTestResource("/dataset/xml");
		DirectoryDataSet dataSet = new DirectoryDataSet(directory, false, new FileComparator());

		ITableMetaData meta1 = dataSet.getTableMetaData("foo");

		assertThat(meta1).isNotNull();
		assertThat(meta1.getColumns())
			.isNotNull()
			.isNotEmpty()
			.hasSize(2)
			.extracting("columnName")
			.containsOnly("id", "name");

		ITableMetaData meta2 = dataSet.getTableMetaData("bar");

		assertThat(meta2).isNotNull();
		assertThat(meta2.getColumns())
			.isNotNull()
			.isNotEmpty()
			.hasSize(2)
			.extracting("columnName")
			.containsOnly("id", "title");
	}

	@Test
	public void it_should_iterate_over_tables() throws Exception {
		File directory = getTestResource("/dataset/xml");
		DirectoryDataSet dataSet = new DirectoryDataSet(directory, false, new FileComparator());

		ITableIterator it = dataSet.iterator();

		List<ITable> tables = new ArrayList<ITable>();
		while (it.next()) {
			tables.add(it.getTable());
		}

		assertThat(tables)
			.isNotNull()
			.hasSize(2)
			.extracting("tableMetaData.tableName")
			.containsExactly("bar", "foo");
	}

	@Test
	public void it_should_iterate_over_tables_in_reverse_order() throws Exception {
		File directory = getTestResource("/dataset/xml");
		DirectoryDataSet dataSet = new DirectoryDataSet(directory, false, new FileComparator());

		ITableIterator it = dataSet.reverseIterator();

		List<ITable> tables = new ArrayList<ITable>();
		while (it.next()) {
			tables.add(it.getTable());
		}

		assertThat(tables)
			.isNotNull()
			.hasSize(2)
			.extracting("tableMetaData.tableName")
			.containsExactly("foo", "bar");
	}

	@Test
	public void it_should_check_for_case_insensitive_names() throws Exception {
		File directory = getTestResource("/dataset/xml");
		DirectoryDataSet d1 = new DirectoryDataSet(directory, false, new FileComparator());
		DirectoryDataSet d2 = new DirectoryDataSet(directory, true, new FileComparator());

		assertThat(d1.isCaseSensitiveTableNames()).isFalse();
		assertThat(d2.isCaseSensitiveTableNames()).isTrue();
	}
}
