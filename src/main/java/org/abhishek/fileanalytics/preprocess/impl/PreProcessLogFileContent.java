/* Copyright 2015 Roychoudhury, Abhishek */

package org.abhishek.fileanalytics.preprocess.impl;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.abhishek.fileanalytics.dto.persist.FileMetadata;
import org.abhishek.fileanalytics.exception.ValidationFailureException;
import org.abhishek.fileanalytics.preprocess.AbstractPreProcessor;
import org.abhishek.fileanalytics.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default implementation of the PreProcess module.
 * 
 * Processes the file information from the input
 * {@link org.abhishek.fileanalytics.dto.persist.FileMetadata} object and sets
 * the parsed information back into the same object instance.
 * 
 * A validation has hence been added to check whether the <tt>FileMetadata</tt>
 * object is NULL.
 * 
 * @author abhishek
 * @since 1.0
 */
public final class PreProcessLogFileContent extends AbstractPreProcessor<FileMetadata> {
    private static final Logger logger   = LoggerFactory.getLogger(PreProcessLogFileContent.class);

    /** Class variables */
    protected FileMetadata      metadata = null;

    public PreProcessLogFileContent(FileMetadata metadata) {
        super();
        this.metadata = metadata;
    }

    /**
     * @author abhishek
     * @since 1.0
     * @see org.abhishek.fileanalytics.preprocess.PreProcessor#preprocess()
     */
    @Override
    public void preprocess() {
        logger.debug("metadata : {}", this.metadata);

        RandomAccessFile randomAccessFile = null;
        FileChannel channel = null;

        try {
            int bufferSize = this.metadata.getBufferSize();
            char endOfLine = this.metadata.getEndOfLine();

            String file = this.metadata.getFilePath() + File.separator + this.metadata.getFileName() + "." + this.metadata.getFileExtn();

            logger.info(
                "Setup Information : [{}, {}] | {}",
                bufferSize,
                file,
                endOfLine);

            randomAccessFile = new RandomAccessFile(file,
                "r");
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

                        logger.debug(
                            "Parsing information : {} [ {} ] - {} {}",
                            charCounter,
                            actualPosition,
                            metadata.positionsSize(),
                            metadata.lengthsSize());
                        charCounter = 0;
                    }
                }

                buffer.clear();
                iterations++;
            }
        } catch (IOException io) {
            logger.error(
                "Exception on processing content.",
                io);
        } finally {
            CommonUtils.closeQuietly(channel);
            CommonUtils.closeQuietly(randomAccessFile);
            logger.info("Method Execution complete");
        }
    }

    /**
     * @author abhishek
     * @since 1.0
     * @see org.abhishek.fileanalytics.preprocess.AbstractPreProcessor#getContent()
     */
    @Override
    public FileMetadata getContent() {
        return this.metadata;
    }

    /**
     * @author abhishek
     * @since 1.0
     * @see org.abhishek.fileanalytics.lifecycle.AbstractDestroyer#destroy()
     */
    @Override
    public void destroy() {
        this.metadata = null;
        super.destroy();
    }

    /**
     * @author abhishek
     * @since 1.0
     * @see org.abhishek.fileanalytics.lifecycle.Validatable#validate()
     */
    @Override
    public boolean validate() {
        if (null == metadata || !metadata.validate()) {
            throw new ValidationFailureException("Metadata information cannot be NULL.");
        }
        return super.validate();
    }
}
