/* Copyright 2015 Roychoudhury, Abhishek */

package org.abhishek.fileanalytics.orchestrate;

import org.abhishek.fileanalytics.lifecycle.Persistable;
import org.abhishek.fileanalytics.persist.Persister;
import org.abhishek.fileanalytics.preprocess.AbstractPreProcessor;
import org.abhishek.fileanalytics.process.AbstractProcessor;

/**
 * Provides a way to use the 
 * 
 * @author abhishek
 * @since 1.0
 */
public interface Orchestrater {

    <MetaKey, PersistedObj extends Persistable> void preProcessFile(
        PersistedObj metadata,
        MetaKey serializedMetaName,
        Persister<MetaKey, PersistedObj> persister,
        AbstractPreProcessor<PersistedObj> preProcessor);

    <MetaKey, PersistedObj extends Persistable, Response> Response processFile(
        MetaKey serializedMetaName,
        Persister<MetaKey, PersistedObj> persister,
        AbstractProcessor<Response, PersistedObj> processor);

}
