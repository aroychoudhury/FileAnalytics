package org.abhishek.fileanalytics.dto.config;

import org.abhishek.fileanalytics.constants.ConfigTypes;
import org.abhishek.fileanalytics.exception.ValidationFailureException;
import org.abhishek.fileanalytics.lifecycle.Configurable;
import org.abhishek.fileanalytics.lifecycle.Validatable;

public class Template implements Validatable, Configurable {
    /**
     * Used to match the file and template to be used.
     */
    private String     templateFileName = null;

    /**
     * FileWrapper text template. This will have data mapped to the defined
     * fragments.
     */
    private String     template         = null;

    /**
     * Automatically parse the 'template' and configure fragments as required.
     * 
     * If Fragment information is defined, this is changed to false.
     */
    private boolean    autoParse        = true;

    /**
     * Fragment information which defines how to process each fragment as read
     * from the file. ArrayList is not used to optimize performance.
     */
    private Fragment[] fragments        = null;

    public Template(String templateFileName, String template) {
        super();
        this.templateFileName = templateFileName;
        this.template = template;
        this.autoParse = true;
    }

    public Template(String fileNameStarting, Fragment[] fragments) {
        super();
        this.templateFileName = fileNameStarting;
        this.fragments = fragments;
        this.autoParse = false;
    }

    public String getTemplateFileName() {
        return templateFileName;
    }

    public String getTemplate() {
        return template;
    }

    public boolean isAutoParse() {
        return autoParse;
    }

    public Fragment[] getFragments() {
        return fragments;
    }

    public boolean validate() {
        if (null == this.templateFileName || "".equals(this.templateFileName.trim())) {
            throw new ValidationFailureException("Template File Name cannot be Null/Empty");
        }
        if ((null == this.template || "".equals(this.template.trim())) && (null == this.fragments || 0 == this.fragments.length)) {
            throw new ValidationFailureException("Either Template or Fragments needs to be configured");
        }
        return true;
    }

    /**
     * @author abhishek
     * @since 1.0
     * @see org.abhishek.fileanalytics.lifecycle.Configurable#configurationType()
     */
    @Override
    public ConfigTypes configurationType() {
        return ConfigTypes.FILE;
    }
}
