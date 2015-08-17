/**
 * 
 */
package org.abhishek.fileanalytics.persist.impl.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.abhishek.fileanalytics.dto.persist.FileMetadata;
import org.abhishek.fileanalytics.persist.impl.FileMetaSerializingPersister;
import org.junit.Before;
import org.junit.Test;

/**
 *
 */
public class TestFileMetaSerializingPersistor {
	private FileMetaSerializingPersister serializingPersistor = null;
	private FileMetadata metadata = null;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.serializingPersistor = new FileMetaSerializingPersister();

		this.metadata = new FileMetadata.Builder(new Date(), "app", "log", "./src/").build();
		this.metadata.addLineLength(190);
		this.metadata.addLineLength(290);
	}

	/**
	 * Test method for
	 * {@link org.abhishek.fileanalytics.persist.impl.FileMetaSerializingPersister#persist(java.lang.String, org.abhishek.fileanalytics.dto.persist.FileMetadata)}
	 * .
	 */
	@Test
	public void testPersist() {
		this.serializingPersistor.persist("app.ser", this.metadata);
	}

	/**
	 * Test method for
	 * {@link org.abhishek.fileanalytics.persist.impl.FileMetaSerializingPersister#retrieve(java.lang.String)}
	 * .
	 */
	@Test
	public void testRetrieve() {
		FileMetadata fileMetadata = this.serializingPersistor.retrieve("app.ser");
		assertNotNull(fileMetadata);
		assertNotNull(fileMetadata.getLineLength(1));
		System.out.println("fileMetadata.getLineLength(0) -> " + fileMetadata.getLineLength(1));
	}

	/**
	 * Test method for
	 * {@link org.abhishek.fileanalytics.persist.AbstractPersister#isPersistable()}
	 * .
	 */
	@Test
	public void testIsPersistable() {
		assertTrue(this.serializingPersistor.isPersistable());
	}

	/**
	 * Test method for
	 * {@link org.abhishek.fileprocessor.persist.FileMetaSerializingPersister#exists(java.lang.String)}
	 * .
	 */
	@Test
	public void testExists() {
		assertTrue(this.serializingPersistor.exists("app.ser"));
	}

	/**
	 * Test method for
	 * {@link org.abhishek.fileprocessor.persist.FileMetaSerializingPersister#delete(java.lang.String)}
	 * .
	 */
	@Test
	public void testDelete() {
		assertTrue(this.serializingPersistor.delete("app.ser"));
	}
}
