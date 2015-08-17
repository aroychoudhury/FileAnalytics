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

public class ProcessLogFileContent extends AbstractProcessor {
    private static final Logger logger    = LoggerFactory.getLogger(ProcessLogFileContent.class);

    /** Class variables */
    private int                 startLine       = 0;
    private int                 endLine         = 0;
    private FileMetadata        metadata        = null;
    private String[]            builder         = null;
    private int                 insertPosn = 0;

    public ProcessLogFileContent(FileMetadata metadata, int startLine, int endLine) {
        super();
        this.metadata = metadata;
        this.startLine = startLine;
        this.endLine = endLine;
        this.builder = new String[this.endLine - this.startLine];
    }

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
            //int actualEnd = this.endLine - this.startLine;

            logger.info("Setup Information : {} | {} | {} | {}",
                bufferSize,
                file,
                lineCnt,
                startPosition);

            char[] lineChars = new char[this.metadata.getLineLength(lineCnt)];
            boolean exOccurred = false;

            channel.position(startPosition);
            while (-1 != (channel.read(buffer))) {
                buffer.flip();

                while (buffer.hasRemaining()) {
                    byte read = buffer.get();

                    char readChar = (char) read;
                    //lineCharCnt++;
                    lineChars[lineCharCnt++] = readChar;
                    if (readChar == endOfLine) {
                        logger.debug("Parsing information : {} [ {} ] - {} {} - {} {}", 
                            metadata.getLineLength(lineCnt), metadata.getLineStartPosition(lineCnt), metadata.positionsSize(), metadata.lengthsSize(),
                            lineCharCnt, lineCnt);
                        exOccurred = this.execute(lineChars);
                        lineCharCnt = 0;
                        lineCnt++;
                        lineChars = new char[this.metadata.getLineLength(lineCnt)];
                    }
                    if (lineCnt == this.endLine || exOccurred) {
                        break;
                    }
                }

                buffer.clear();
                if (lineCnt == this.endLine || exOccurred) {
                    break;
                }
            }
        } catch (IOException io) {
            logger.error("Exception on processing content.", io);
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
}
