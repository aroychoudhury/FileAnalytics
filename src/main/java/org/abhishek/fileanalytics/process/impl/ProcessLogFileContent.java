/* Copyright 2015 Roychoudhury, Abhishek */

package org.abhishek.fileanalytics.process.impl;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.abhishek.fileanalytics.dto.persist.FileMetadata;
import org.abhishek.fileanalytics.exception.ValidationFailureException;
import org.abhishek.fileanalytics.process.AbstractProcessor;
import org.abhishek.fileanalytics.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base class for providing the all Log File Content parsing.
 * 
 * @author abhishek
 * @since 1.0
 */
public class ProcessLogFileContent extends AbstractProcessor<String[], FileMetadata> {
    private static final Logger logger     = LoggerFactory.getLogger(ProcessLogFileContent.class);

    /** Class variables */
    private int                 startLine  = 0;
    private int                 endLine    = 0;
    private int                 insertPosn = 0;
    private String[]            builder    = null;
    protected FileMetadata      metadata   = null;

    public ProcessLogFileContent(int startLine,
        int endLine) {
        super();
        this.startLine = startLine;
        this.endLine = endLine;
        this.builder = new String[this.endLine - this.startLine];
    }

    /**
     * @author abhishek
     * @since 1.0
     * @see org.abhishek.fileanalytics.process.Processor#process()
     */
    @Override
    public void process() {
        logger.debug("metadata : {}", this.metadata);
        logger.debug("line numbers : {} to {}", this.startLine, this.endLine);

        RandomAccessFile randomAccessFile = null;
        FileChannel channel = null;

        try {
            int bufferSize = this.metadata.getBufferSize();
            long startPosition = this.metadata.getLineStartPosition(this.startLine);
            char endOfLine = this.metadata.getEndOfLine();

            String file = this.metadata.getFilePath() + File.separator + this.metadata.getFileName() + "." + this.metadata.getFileExtn();

            randomAccessFile = new RandomAccessFile(file, "r");
            channel = randomAccessFile.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(bufferSize);

            int lineCharCnt = 0;

            // Actual positions
            int lineCnt = this.startLine;
            // int actualEnd = this.endLine - this.startLine;

            logger.info(
                "Setup Information : {} | {} | {} | {}",
                bufferSize,
                file,
                lineCnt,
                startPosition);

            char[] lineChars = new char[this.metadata.getLineLength(lineCnt)];

            // Setting default to false, results in this entering if condition
            // at line number 89.
            boolean isExecutionSuccess = true;

            channel.position(startPosition);
            while (-1 != (channel.read(buffer))) {
                buffer.flip();

                while (buffer.hasRemaining()) {
                    byte read = buffer.get();

                    char readChar = (char) read;
                    // lineCharCnt++;
                    lineChars[lineCharCnt++] = readChar;
                    if (readChar == endOfLine) {
                        logger.debug(
                            "Parsing information : {} [ {} ] - {} {} - {} {}",
                            metadata.getLineLength(lineCnt),
                            metadata.getLineStartPosition(lineCnt),
                            metadata.positionsSize(),
                            metadata.lengthsSize(),
                            lineCharCnt,
                            lineCnt);
                        isExecutionSuccess = this.execute(lineChars);
                        lineCharCnt = 0;
                        lineCnt++;
                        lineChars = new char[this.metadata.getLineLength(lineCnt)];
                    }
                    if (lineCnt == this.endLine || !isExecutionSuccess) {
                        break;
                    }
                }

                buffer.clear();
                if (lineCnt == this.endLine || !isExecutionSuccess) {
                    break;
                }
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
     * @see org.abhishek.fileanalytics.process.AbstractProcessor#destroy()
     */
    @Override
    public void destroy() {
        super.destroy();
        this.metadata = null;
        this.builder = null;
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
        if (0 > this.startLine) {
            throw new ValidationFailureException("Starting Line cannot be negative.");
        }
        if (0 > this.endLine) {
            throw new ValidationFailureException("Ending Line cannot be negative.");
        }
        if (this.startLine >= this.endLine) {
            throw new ValidationFailureException("Ending Line must be greater than Starting Line.");
        }
        return super.validate();
    }

    /**
     * @return
     * @author abhishek
     * @since 1.0
     */
    public String[] getContent() {
        return this.builder;
    }

    /**
     * @author abhishek
     * @since 1.0
     */
    protected void resetContent() {
        this.builder = new String[this.endLine - this.startLine];
        this.insertPosn = 0;
    }

    /**
     * @param lineChars
     * @author abhishek
     * @since 1.0
     */
    protected void appendContent(char[] lineChars) {
        this.builder[this.insertPosn] = String.valueOf(lineChars);
        this.insertPosn++;
    }

    /**
     * @param lineChars
     * @author abhishek
     * @since 1.0
     */
    protected void appendContent(String lineChars) {
        this.builder[this.insertPosn] = lineChars;
        this.insertPosn++;
    }

    /**
     * @author abhishek
     * @since 1.0
     * @see org.abhishek.fileanalytics.process.AbstractProcessor#setMetadata(java.lang.Object)
     */
    @Override
    public void setMetadata(FileMetadata metadata) {
        this.metadata = metadata;
    }
}
