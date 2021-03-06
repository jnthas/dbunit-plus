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

import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ITableIterator;
import org.dbunit.dataset.ITableMetaData;

import java.io.File;
import java.util.Comparator;
import java.util.List;

import static com.github.mjeanroy.dbunit.commons.io.Files.listFiles;
import static com.github.mjeanroy.dbunit.commons.lang.PreConditions.isDirectory;
import static com.github.mjeanroy.dbunit.commons.lang.PreConditions.isReadable;
import static com.github.mjeanroy.dbunit.commons.lang.PreConditions.notNull;
import static com.github.mjeanroy.dbunit.core.dataset.DataSetFactory.createDataSet;
import static java.util.Collections.sort;

/**
 * Directory dataSet.
 * This dataSet implementation will scan directory, extract all files
 * and create appropriate dataSet implementation for each files.
 */
public class DirectoryDataSet implements IDataSet {

	/**
	 * Directory path.
	 */
	private final File path;

	/**
	 * Internal data set.
	 */
	private final CompositeDataSet dataSet;

	/**
	 * Create dataSet.
	 *
	 * @param path Directory path.
	 * @param caseSensitiveTableNames Case sensitivity flag.
	 * @param comparator File comparator, used to sort files in given order.
	 * @throws DataSetException
	 */
	DirectoryDataSet(File path, boolean caseSensitiveTableNames, Comparator<File> comparator) throws DataSetException {
		isDirectory(path, "Path should be a valid directory");
		isReadable(path, "Path should be readable");
		notNull(comparator, "Comparator should not be null");

		// List all files and create composite data set.
		List<File> files = listFiles(path);
		sort(files, comparator);

		IDataSet[] dataSets = new IDataSet[files.size()];
		int i = 0;
		for (File file : files) {
			dataSets[i++] = createDataSet(file);
		}

		this.path = path;
		this.dataSet = new CompositeDataSet(dataSets, true, caseSensitiveTableNames);
	}

	@Override
	public String[] getTableNames() throws DataSetException {
		return dataSet.getTableNames();
	}

	@Override
	public ITableMetaData getTableMetaData(String tableName) throws DataSetException {
		return dataSet.getTableMetaData(tableName);
	}

	@Override
	public ITable getTable(String tableName) throws DataSetException {
		return dataSet.getTable(tableName);
	}

	@Override
	public ITable[] getTables() throws DataSetException {
		return dataSet.getTables();
	}

	@Override
	public ITableIterator iterator() throws DataSetException {
		return dataSet.iterator();
	}

	@Override
	public ITableIterator reverseIterator() throws DataSetException {
		return dataSet.reverseIterator();
	}

	@Override
	public boolean isCaseSensitiveTableNames() {
		return dataSet.isCaseSensitiveTableNames();
	}

	/**
	 * Get {@link #path}.
	 *
	 * @return {@link #path}
	 */
	public File getPath() {
		return path;
	}

	@Override
	public String toString() {
		return String.format("%s{path=%s}", getClass().getSimpleName(), path);
	}
}
