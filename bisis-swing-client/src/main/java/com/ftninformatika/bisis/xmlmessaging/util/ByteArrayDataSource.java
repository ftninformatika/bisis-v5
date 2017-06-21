/*
 *  Created on 2004.11.16
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.ftninformatika.bisis.xmlmessaging.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.activation.DataSource;

/**
 * @author mikiz
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ByteArrayDataSource implements DataSource {
	private byte[] buffer;
	private String mimeType;
	
	
	
	/**
	 * @param data
	 * @param mimeType
	 */
	public ByteArrayDataSource(byte[] data, String mimeType) {
		super();
		this.buffer = data;
		this.mimeType = mimeType;
	}
	/* (non-Javadoc)
	 * @see javax.activation.DataSource#getContentType()
	 */
	public String getContentType() {
		return mimeType;
	}

	/* (non-Javadoc)
	 * @see javax.activation.DataSource#getInputStream()
	 */
	public InputStream getInputStream() throws IOException {
		if(buffer==null){
			throw new IOException("No byte array");
		}
		return new ByteArrayInputStream(buffer);
	}

	/* (non-Javadoc)
	 * @see javax.activation.DataSource#getName()
	 */
	public String getName() {
		return "ByteArrayDataSource";
	}

	/* (non-Javadoc)
	 * @see javax.activation.DataSource#getOutputStream()
	 */
	public OutputStream getOutputStream() throws IOException {
		throw new IOException("OutputStream creation is forbidden");
	}
}
