package org.abhishek.fileanalytics.dto.config;

import org.abhishek.fileanalytics.constants.ConfigTypes;
import org.abhishek.fileanalytics.dto.Configurable;
import org.abhishek.fileanalytics.dto.Validatable;
import org.abhishek.fileanalytics.exception.ValidationFailureException;
import org.abhishek.fileanalytics.parse.Parser;

/**
 * @author abhishek
 */
public class Fragment implements Validatable, Configurable {
    /**
     * Parser class treats this fragment as a date, string, name etc., based on
     * parsing logic defined. Can be null.
     */
    private String parserClassName = null;

    @SuppressWarnings("rawtypes")
    public Fragment(Class<? extends Parser> parserClazz) {
        super();
        this.parserClassName = parserClazz.getName();
    }

    public String getParserClassName() {
        return parserClassName;
    }

    @Override
    public boolean validate() {
        if (null == this.parserClassName || "".equals(parserClassName.trim())) {
            throw new ValidationFailureException("End Separator is required for growing fragment");
        } else {
            this.parserClassName = this.parserClassName.trim();
        }
        return true;
    }

    /**
     * @author abhishek
     * @since 1.0
     * @see org.abhishek.fileanalytics.dto.Configurable#configurationType()
     */
    @Override
    public ConfigTypes configurationType() {
        return ConfigTypes.FILE;
    }
}
