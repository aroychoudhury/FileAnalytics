package org.abhishek.fileanalytics.parse;

import org.abhishek.fileanalytics.lifecycle.AbstractValidater;
import org.abhishek.fileanalytics.lifecycle.Destroyable;
import org.abhishek.fileanalytics.lifecycle.Initializable;
import org.abhishek.fileanalytics.lifecycle.Validatable;

/**
 * <tt>AbstractParseHelper</tt> provides the implementation for teh defaults
 * methods.
 * 
 * @author abhishek
 * @since 1.0
 * @param <E>
 *            Placeholder to return any data after the parsing.
 */
public abstract class AbstractParseHelper<E> 
                extends AbstractValidater 
                implements ParseHelper<E>, Initializable, Destroyable, Validatable {
}
