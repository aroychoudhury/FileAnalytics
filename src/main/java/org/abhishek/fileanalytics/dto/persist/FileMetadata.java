/* Copyright 2015 Roychoudhury, Abhishek */

package org.abhishek.fileanalytics.dto.persist;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Date;

import org.abhishek.fileanalytics.constants.PersistTypes;
import org.abhishek.fileanalytics.exception.ValidationFailureException;
import org.abhishek.fileanalytics.lifecycle.Persistable;
import org.abhishek.fileanalytics.lifecycle.Validatable;

/**
 * Encpsulates the pre-processed file state.
 * 
 * Implements the {@link org.abhishek.fileanalytics.lifecycle.Persistable} interface
 * indicating how the state needs to be persisted.
 * 
 * Implements the {@link org.abhishek.fileanalytics.lifecycle.Validatable} interface
 * indicating
 * 
 * @author abhishek
 * @since 1.0
 */
public class FileMetadata implements Serializable, Validatable, Persistable {
    private static final long serialVersionUID   = 1031384718289501780L;

    // variable to identify if the object has been deserialized
    // Deserialization process = 0
    // Programmatic creation = 1
    private transient int     initType           = 0;

    private Date              lastModified       = null;

    private String            fileName           = null;
    private String            fileExtn           = null;
    private String            filePath           = null;
    private String            charset            = null;

    private int               bufferSize         = 0;
    private char              endOfLine;

    private int               positionsCnt       = 0;
    private long[]            lineStartPositions = null;

    private int               maxLineLength      = 0;
    private int               lengthsCnt         = 0;
    private int[]             lineLengths        = null;

    /**
     * @param lastModified
     *            Last file modification date.
     * @param fileName
     *            Name of the file.
     * @param fileExtn
     *            Extension of the file.
     * @param filePath
     *            Path location of the file.
     * @param charset
     *            Character set encoding of the file.
     * @param bufferSize
     *            Size of the buffer for file read operations.
     * @param endOfLine
     *            End of Line character for the file in question.
     * @author abhishek
     * @since 1.0
     */
    private FileMetadata(Date lastModified,
        String fileName,
        String fileExtn,
        String filePath,
        String charset,
        int bufferSize,
        char endOfLine) {
        super();

        this.lastModified = lastModified;
        this.fileName = fileName;
        this.fileExtn = fileExtn;
        this.filePath = filePath;
        this.charset = charset;
        this.bufferSize = bufferSize;
        this.endOfLine = endOfLine;

        // will be set when object is created programmatically
        this.initType = 1;

        this.lineStartPositions = new long[1000];
        this.lineLengths = new int[1000];
    }

    /**
     * @return true if the file is deserialized else return false.
     * @author abhishek
     * @since 1.0
     */
    public boolean isDeserialized() {
        return (0 == this.initType);
    }

    /**
     * @return The last modified timestamp.
     * @author abhishek
     * @since 1.0
     */
    public Date getLastModified() {
        return lastModified;
    }

    /**
     * @return The file name.
     * @author abhishek
     * @since 1.0
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @return The file extension.
     * @author abhishek
     * @since 1.0
     */
    public String getFileExtn() {
        return fileExtn;
    }

    /**
     * @return The file location.
     * @author abhishek
     * @since 1.0
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * @return The character set for the file in question.
     * @author abhishek
     * @since 1.0
     */
    public String getCharset() {
        return charset;
    }

    /**
     * @return The buffer size for the file processing.
     * @author abhishek
     * @since 1.0
     */
    public int getBufferSize() {
        return bufferSize;
    }

    /**
     * @return The end of line character.
     * @author abhishek
     * @since 1.0
     */
    public char getEndOfLine() {
        return endOfLine;
    }

    /**
     * @return The maximum number of characters in a line.
     * @author abhishek
     * @since 1.0
     */
    public int getMaxLineLength() {
        return maxLineLength;
    }

    /**
     * @param index
     *            The line number for which the information is required.
     * @return The bytes information for the required line number.
     * @author abhishek
     * @since 1.0
     */
    public long getLineStartPosition(int index) {
        if (0 > index) {
            throw new IllegalArgumentException("Index cannot be Negative : " + index);
        }
        if (1 >= index) {
            return 0L;
        }
        return this.lineStartPositions[index - 2];
    }

    /**
     * @param index
     *            The line number for which the information is required.
     * @return The length of character array information for the required line
     *         number.
     * @author abhishek
     * @since 1.0
     */
    public int getLineLength(int index) {
        if (0 > index) {
            throw new IllegalArgumentException("Index cannot be Negative : " + index);
        }
        if (0 == index) {
            return 0;
        }
        return this.lineLengths[index - 1];
    }

    /**
     * @param lineStartPosition
     *            Byte position to be added to the content bytes array.
     * @author abhishek
     * @since 1.0
     */
    public void addLineStartPosition(Long lineStartPosition) {
        if (0L >= lineStartPosition) {
            throw new IllegalArgumentException("Line Position does not match the criteria : " + lineStartPosition);
        }
        this.lineStartPositions[this.positionsCnt] = lineStartPosition;
        this.positionsCnt++;
    }

    /**
     * @return The length of the content bytes array.
     * @author abhishek
     * @since 1.0
     */
    public int positionsSize() {
        return this.positionsCnt;
    }

    /**
     * @param lineLength
     *            Length to be added to the character lengths array.
     * @author abhishek
     * @since 1.0
     */
    public void addLineLength(int lineLength) {
        if (0L >= lineLength) {
            throw new IllegalArgumentException("Line Length does not match the criteria : " + lineLength);
        }
        this.lineLengths[this.lengthsCnt] = lineLength;
        this.lengthsCnt++;

        if (this.maxLineLength < lineLength) {
            this.maxLineLength = lineLength;
        }
    }

    /**
     * @return The length of the file content bytes array.
     * @author abhishek
     * @since 1.0
     */
    public int lengthsSize() {
        return this.lengthsCnt;
    }

    /**
     * @author abhishek
     * @since 1.0
     * @see org.abhishek.fileanalytics.lifecycle.Validatable#validate()
     */
    public boolean validate() {
        // lastModified
        if (null == this.lastModified) {
            throw new ValidationFailureException("Last Modified cannot be Null");
        }

        // fileName
        if (null == this.fileName || "".equals(this.fileName.trim())) {
            throw new ValidationFailureException("File Name cannot be Null/Empty");
        } else {
            this.fileName = this.fileName.trim();
        }

        // fileExtn
        if (null == this.fileExtn) {
            throw new ValidationFailureException("File Extn cannot be Null");
        } else {
            this.fileExtn = this.fileExtn.trim();
        }

        // filePath
        if (null == this.filePath || "".equals(this.filePath.trim())) {
            throw new ValidationFailureException("File Path cannot be Null/Empty");
        } else {
            this.filePath = this.filePath.trim();
        }

        // charset
        if (null == this.charset || "".equals(this.charset.trim())) {
            throw new ValidationFailureException("Charset cannot be Null/Empty");
        } else {
            this.charset = this.charset.trim();
        }

        // bufferSize
        if (0 >= this.bufferSize) {
            throw new ValidationFailureException("Buffer Size cannot be Zero or Negative : " + this.bufferSize);
        }
        return true;
    }

    /**
     * Inner class to for the Builder pattern implementation.
     * 
     * @author abhishek
     * @since 1.0
     */
    public static class Builder {
        /** Assign defaults */
        private int    bufferSize = 1024 * 4;
        private String charset    = Charset.defaultCharset().displayName();
        private char   endOfLine  = '\n';
        /** Assign defaults */

        private Date   modified   = null;

        private String fileName   = null;
        private String fileExtn   = null;
        private String filePath   = null;

        public Builder(Date modified,
            String fileName,
            String fileExtn,
            String filePath) {
            super();
            this.fileName = fileName;
            this.fileExtn = fileExtn;
            this.filePath = filePath;
            this.modified = modified;
        }

        public Builder bufferSize(int bufferSize) {
            this.bufferSize = bufferSize;
            return this;
        }

        public Builder bufferSizeInKb(int bufferSizeInKb) {
            if (0 >= bufferSizeInKb) {
                throw new IllegalArgumentException("Buffer Size multiplier cannot be Zero or Negative : " + bufferSizeInKb);
            }
            this.bufferSize = 1024 * bufferSizeInKb;
            return this;
        }

        public Builder charset(String charset) {
            if (!Charset.availableCharsets().containsKey(
                charset)) {
                throw new IllegalArgumentException("Charset must be set via Charset class");
            }
            this.charset = charset;
            return this;
        }

        public Builder endOfLine(char endOfLine) {
            this.endOfLine = endOfLine;
            return this;
        }

        public FileMetadata build() {
            return new FileMetadata(this.modified,
                this.fileName,
                this.fileExtn,
                this.filePath,
                this.charset,
                this.bufferSize,
                this.endOfLine);
        }
    }

    /**
     * @author abhishek
     * @since 1.0
     * @see org.abhishek.fileanalytics.lifecycle.Persistable#persistType()
     */
    @Override
    public PersistTypes persistType() {
        return PersistTypes.FILE;
    }

    /**
     * @author abhishek
     * @since 1.0
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((fileName == null) ? 0 : fileName.hashCode());
        return result;
    }

    /**
     * @author abhishek
     * @since 1.0
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof FileMetadata)) {
            return false;
        }
        FileMetadata other = (FileMetadata) obj;
        if (fileName == null) {
            if (other.fileName != null) {
                return false;
            }
        } else if (!fileName.equals(other.fileName)) {
            return false;
        }
        return true;
    }
}
