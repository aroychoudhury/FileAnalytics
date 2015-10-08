/* Copyright 2015 Roychoudhury, Abhishek */

package org.abhishek.fileanalytics.orchestrate.impl;

import org.abhishek.fileanalytics.lifecycle.Persistable;
import org.abhishek.fileanalytics.orchestrate.Orchestrater;
import org.abhishek.fileanalytics.persist.Persister;
import org.abhishek.fileanalytics.preprocess.AbstractPreProcessor;
import org.abhishek.fileanalytics.process.AbstractProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <tt>BasicLogFileOrchestrator</tt> provides a generic way of invoking the
 * preprocess and process operations on a Log File.
 * 
 * @author abhishek
 * @since 1.0
 */
public class BasicLogFileOrchestrator implements Orchestrater {
    private static final Logger logger = LoggerFactory.getLogger(BasicLogFileOrchestrator.class);

    /**
     * @author abhishek
     * @since 1.0
     * @see org.abhishek.fileanalytics.orchestrate.Orchestrater#preProcessFile(PersistedObj,
     *      MetaKey, org.abhishek.fileanalytics.persist.Persister,
     *      org.abhishek.fileanalytics.preprocess.AbstractPreProcessor)
     */
    @Override
    public <MetaKey, PersistedObj extends Persistable> void preProcessFile(
        PersistedObj metadata,
        MetaKey serializedMetaName,
        Persister<MetaKey, PersistedObj> persister,
        AbstractPreProcessor<PersistedObj> preProcessor) {
        logger.debug("metadata : {}", metadata);

        if (null == metadata) {
            new IllegalStateException("Files cannot be processed without pre-processing");
        }

        if (preProcessor.validate()) {
            preProcessor.initialize();
            preProcessor.preprocess();
            metadata = preProcessor.getContent();
            preProcessor.destroy();
        }

        persister.persist(serializedMetaName, metadata);
    }

    /**
     * @author abhishek
     * @since 1.0
     * @see org.abhishek.fileanalytics.orchestrate.Orchestrater#processFile(MetaKey,
     *      org.abhishek.fileanalytics.persist.Persister,
     *      org.abhishek.fileanalytics.process.AbstractProcessor)
     */
    @Override
    public <MetaKey, PersistedObj extends Persistable, Response> Response processFile(
        MetaKey serializedMetaName,
        Persister<MetaKey, PersistedObj> persister,
        AbstractProcessor<Response, PersistedObj> processor) {
        logger.debug("serializedMetaFileName : {}", serializedMetaName);

        PersistedObj metadata = (PersistedObj) persister.retrieve(serializedMetaName);
        logger.debug("metadata : {}", metadata);

        if (null == metadata) {
            new IllegalStateException("Files cannot be processed without pre-processing");
        }

        Response response = null;
        processor.setMetadata(metadata);
        if (processor.validate()) {
            processor.initialize();
            processor.process();
            response = processor.getContent();
            processor.destroy();
        }

        return response;
    }
}
