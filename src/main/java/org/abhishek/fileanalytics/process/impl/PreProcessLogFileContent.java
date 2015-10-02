package org.abhishek.fileanalytics.process.impl;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.abhishek.fileanalytics.dto.persist.FileMetadata;
import org.abhishek.fileanalytics.process.AbstractProcessor;
import org.abhishek.fileanalytics.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PreProcessLogFileContent extends AbstractProcessor {
	private static final Logger logger = LoggerFactory.getLogger(PreProcessLogFileContent.class);

	/** Class variables */
	protected FileMetadata metadata = null;

	public PreProcessLogFileContent(FileMetadata metadata) {
		super();
		this.metadata = metadata;
	}

	@Override
	public void process() {
		logger.debug("metadata : {}", this.metadata);

		RandomAccessFile randomAccessFile = null;
		FileChannel channel = null;

		try {
			int bufferSize = this.metadata.getBufferSize();
			char endOfLine = this.metadata.getEndOfLine();

			String file = this.metadata.getFilePath() + File.separator + this.metadata.getFileName() + "." + this.metadata.getFileExtn();

			logger.info("Setup Information : [{}, {}] | {}", bufferSize, file, endOfLine);

			randomAccessFile = new RandomAccessFile(file, "r");
			channel = randomAccessFile.getChannel();
			ByteBuffer buffer = ByteBuffer.allocate(bufferSize);

			long actualPosition = 0L;
			long iterations = 0L;
			int charCounter = 0;

			while (-1 != (channel.read(buffer))) {
				buffer.flip();

				while (buffer.hasRemaining()) {
					byte read = buffer.get();

					char readChar = (char) read;
					charCounter++;
					if (readChar == endOfLine) {
						actualPosition = buffer.position() + (bufferSize * iterations);

						metadata.addLineStartPosition(actualPosition);
						metadata.addLineLength(charCounter);

						logger.debug("Parsing information : {} [ {} ] - {} {}", 
						    charCounter, actualPosition, metadata.positionsSize(), metadata.lengthsSize());
						charCounter = 0;
					}
				}

				buffer.clear();
				iterations++;
			}
		} catch (IOException io) {
			logger.error("Exception on processing content.", io);
		} finally {
			CommonUtils.closeQuietly(channel);
			CommonUtils.closeQuietly(randomAccessFile);
			logger.info("Method Execution complete");
		}
	}

	@Override
	public void destroy() {
		this.metadata = null;
	}
}
