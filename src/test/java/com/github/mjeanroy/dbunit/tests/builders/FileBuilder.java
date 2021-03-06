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

package com.github.mjeanroy.dbunit.tests.builders;

import java.io.File;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Builder to create mock {@link File} instances.
 */
public class FileBuilder {

	private String path;

	private boolean directory;

	private boolean file;

	private boolean canRead;

	public FileBuilder(String path) {
		this.path = path;
		this.canRead = true;
	}

	public FileBuilder isDirectory(boolean directory) {
		this.directory = directory;
		return this;
	}

	public FileBuilder isFile(boolean file) {
		this.file = file;
		return this;
	}

	public FileBuilder canRead(boolean canRead) {
		this.canRead = canRead;
		return this;
	}

	public File build() {
		File f = mock(File.class);
		when(f.isDirectory()).thenReturn(directory);
		when(f.isFile()).thenReturn(file);
		when(f.canRead()).thenReturn(canRead);
		when(f.getPath()).thenReturn(path);
		when(f.getName()).thenReturn(path);
		when(f.getAbsolutePath()).thenReturn(path);
		when(f.toString()).thenCallRealMethod();
		return f;
	}
}
