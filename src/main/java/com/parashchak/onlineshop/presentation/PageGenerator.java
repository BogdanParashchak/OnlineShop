package com.parashchak.onlineshop.presentation;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;


public class PageGenerator {
    private static final String HTML_DIR = "src/main/resources";

    private static PageGenerator pageGenerator;
    private final Configuration configuration;

    public static PageGenerator instance() {
        if (pageGenerator == null)
            pageGenerator = new PageGenerator();
        return pageGenerator;
    }

    public String getPage(String filename, Map<String, Object> pageData) {
        Writer writer = new StringWriter();

        try {
            configuration.setDirectoryForTemplateLoading(new File(HTML_DIR));
            Template template = configuration.getTemplate(filename);
            template.process(pageData, writer);
        } catch (Exception e) {
            throw new RuntimeException("Unable generate page", e);
        }
        return writer.toString();
    }

    private PageGenerator() {
        configuration = new Configuration(Configuration.VERSION_2_3_31);
    }
}