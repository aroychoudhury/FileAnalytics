package org.abhishek.fileanalytics.persist.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.abhishek.fileanalytics.dto.persist.FileMetadata;
import org.abhishek.fileanalytics.exception.PersistFailureException;
import org.abhishek.fileanalytics.persist.AbstractPersister;
import org.abhishek.fileanalytics.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileMetaSerializingPersister extends AbstractPersister<String, FileMetadata> {
	private static final Logger logger = LoggerFactory.getLogger(FileMetaSerializingPersister.class);

	public static String TMP_DIRECTORY = System.getProperty("java.io.tmpdir") + File.separator + "fileprocessor";

	static {
		File directory = new File(TMP_DIRECTORY);
		directory.mkdir();
		logger.info("Directory created at : {}", TMP_DIRECTORY);
	}

	public synchronized boolean exists(String fileName) {
		return (new File(TMP_DIRECTORY + File.separator + fileName)).exists();
	}

	public synchronized void persist(String fileName, FileMetadata metadata) throws PersistFailureException {
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try {
			// save the object to file
			fos = new FileOutputStream(TMP_DIRECTORY + File.separator + fileName);
			out = new ObjectOutputStream(fos);
			out.writeObject(metadata);
		} catch (Exception ex) {
			throw new PersistFailureException("Exception in serializing FileWrapper : " + fileName, ex);
		} finally {
			CommonUtils.closeQuietly(out);
			CommonUtils.closeQuietly(fos);
		}
	}

	public synchronized FileMetadata retrieve(String fileName) throws PersistFailureException {
		FileMetadata metadata = null;
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream(TMP_DIRECTORY + File.separator + fileName + ".ser");
			ois = new ObjectInputStream(fis);
			metadata = (FileMetadata) ois.readObject();
		} catch (Exception ex) {
			throw new PersistFailureException("Exception in reading serialized FileWrapper : " + fileName, ex);
		} finally {
			CommonUtils.closeQuietly(ois);
			CommonUtils.closeQuietly(fis);
		}
		return metadata;
	}

	public synchronized boolean delete(String fileName) throws PersistFailureException {
		return (new File(TMP_DIRECTORY + File.separator + fileName)).delete();
	}
}
