package org.abhishek.fileanalytics.process.impl;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.abhishek.fileanalytics.dto.config.Fragment;
import org.abhishek.fileanalytics.dto.config.Template;
import org.abhishek.fileanalytics.dto.persist.FileMetadata;
import org.abhishek.fileanalytics.factory.PreProcesserFactory;
import org.abhishek.fileanalytics.parse.AbstractParser;
import org.abhishek.fileanalytics.parse.Parser;
import org.abhishek.fileanalytics.process.AbstractProcessor;
import org.abhishek.fileanalytics.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessLogFileContentOld extends AbstractProcessor {
	private static final Logger logger = LoggerFactory.getLogger(ProcessLogFileContentOld.class);

	/** Class variables */
	private int startLine = 0;
	private int endLine = 0;
	private FileMetadata metadata = null;
	private Template template = null;

	public ProcessLogFileContentOld(FileMetadata metadata, Template template, int startLine, int endLine) {
		super();
		this.metadata = metadata;
		this.startLine = startLine;
		this.endLine = endLine;
		this.template = template;
	}

	public void process() {
		logger.debug("metadata : {}", this.metadata);
		logger.debug("template : {}", this.template);
		logger.debug("line numbers : {} to {}", this.startLine, this.endLine);

		RandomAccessFile randomAccessFile = null;
		FileChannel channel = null;

		try {
			int bufferSize = this.metadata.getBufferSize();
			long startPosition = this.metadata.getLineStartPosition(this.startLine);
			char endOfLine = this.metadata.getEndOfLine();

			String file = this.metadata.getFilePath() + File.separator + this.metadata.getFileName() + "." + this.metadata.getFileExtn();

			logger.info("Setup Information : {} | {} | {} | {}",
				bufferSize, file, endOfLine, startPosition);

			randomAccessFile = new RandomAccessFile(file, "r");
			channel = randomAccessFile.getChannel();
			ByteBuffer buffer = ByteBuffer.allocate(bufferSize);

			long actualPosition = 0L;
			long iterations = 0L;

			int lineCharCnt = 0;
			int lineCnt = this.startLine;
			char[] lineChars = new char[this.metadata.getLineLength(this.startLine)];
			boolean excOccurred = false;

			channel.position(startPosition);
			while (-1 != (channel.read(buffer))) {
				buffer.flip();

				while (buffer.hasRemaining()) {
					byte read = buffer.get();

					char readChar = (char) read;
					lineChars[lineCharCnt++] = readChar;
					if (readChar == endOfLine) {
						actualPosition = buffer.position() + (bufferSize * iterations);

						//int prevParseLen = Parser.DEFAULT;
						int prevEndPosn = Parser.DEFAULT;
						for (Fragment fragment : template.getFragments()) {
							@SuppressWarnings("rawtypes")
                            AbstractParser parser = PreProcesserFactory.getParser(fragment.getParserClassName());
							logger.debug("Parser class : {}", parser.toString());

							try {
								parser.initialize();
								if (parser.validate()) {
									parser.parseFragment(lineChars, prevEndPosn);
									//prevParseLen = parser.parseLength();
									//prevEndPosn = parser.parseEndPosition();
								}
								parser.destroy();
								parser = null; // allow to collected for GC
							} catch(Exception e) {
								logger.error("Error in parser execution : " + parser.toString(), e);
								if (!parser.IsContinueOnExc()) {
									excOccurred = true;
								}
							}

							if (excOccurred) {
								break;
							}
						}

						lineCharCnt = 0;
						lineCnt++;
						logger.debug("Parsing information : {}. {} [{}]", lineCnt, lineCharCnt, actualPosition);
					}
					if (lineCnt == this.endLine) {
						break;
					}
				}

				buffer.clear();
				iterations++;
				if (lineCnt == this.endLine) {
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

	@Override
	public void destroy() {
		this.metadata = null;
		this.template = null;
	}
}
