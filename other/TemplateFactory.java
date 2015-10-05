package org.abhishek.fileanalytics.factory;

import java.util.HashMap;
import java.util.Map;

import org.abhishek.fileanalytics.constants.LoggerReservedChars;
import org.abhishek.fileanalytics.dto.config.Template;
import org.abhishek.fileanalytics.exception.ConfigurationFailureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TemplateFactory {
    private static final Logger                        logger       = LoggerFactory.getLogger(TemplateFactory.class);

    /* Variables start here */
    private static final TemplateFactory               INSTANCE     = new TemplateFactory();
    private Map<String, Template>                      templates    = null;
    private static String                              template     = null;

    private static Map<Character, LoggerReservedChars> characterMap = null;

    static {
        INSTANCE.templates = new HashMap<String, Template>();

        characterMap = new HashMap<Character, LoggerReservedChars>();
        characterMap.put(LoggerReservedChars.c.valueOf(), LoggerReservedChars.c);
        characterMap.put(LoggerReservedChars.d.valueOf(), LoggerReservedChars.d);
        characterMap.put(LoggerReservedChars.m.valueOf(), LoggerReservedChars.m);
        characterMap.put(LoggerReservedChars.n.valueOf(), LoggerReservedChars.n);
        characterMap.put(LoggerReservedChars.p.valueOf(), LoggerReservedChars.p);
        characterMap.put(LoggerReservedChars.t.valueOf(), LoggerReservedChars.t);
        characterMap.put(LoggerReservedChars.x.valueOf(), LoggerReservedChars.x);
        characterMap.put(LoggerReservedChars.percent.valueOf(), LoggerReservedChars.percent);

        /*
         * addTemplate(new Template("scn_aaa", new Fragment[] { new
         * Fragment(DefaultParser.class) }));
         */
    }

    private TemplateFactory() {
        super();
    }

    public static boolean addTemplate(Template template) {
        String fileName = template.getTemplateFileName();
        if (contains(fileName)) {
            return false;
        }

        INSTANCE.add(template, template.getTemplateFileName());
        logger.info("Added template : {}", fileName);

        return true;
    }

    public void add(Template template, String fileName) {
        synchronized (templates) {
            templates.put(fileName, template);
        }
    }

    public static Template getTemplate(String fileName) {
        if (null == fileName || "".equals(fileName.trim())) {
            throw new IllegalArgumentException("File Name cannot be Null/Empty");
        }
        return get(fileName.trim());
    }

    private static boolean contains(String fileName) {
        if (null == fileName || "".equals(fileName.trim())) {
            return false;
        }
        return INSTANCE.templates.containsKey(fileName);
    }

    private static Template get(String fileName) {
        return INSTANCE.templates.get(fileName);
    }

    public static void processTemplate() {
        if (null == template || "".equals(template.trim())) {
            throw new ConfigurationFailureException("Template cannot be Null/Empty : " + template);
        }
        //char[] templateArr = template.trim().toCharArray();
    }
}
