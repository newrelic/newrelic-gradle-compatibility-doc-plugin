/*
 * Copyright 2020 New Relic Corporation. All rights reserved.
 * SPDX-License-Identifier: Apache-2.0
 */

package com.newrelic.agent.instrumentation.compatibility;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static com.newrelic.agent.instrumentation.compatibility.Constants.*;

public class FreeMarkerSiteGenerator implements CompatibilitySiteGenerator {

    private final File jsonFile;
    private final File htmlDir;
    private final Category category = new Category(new HashMap<String, TreeMap<String,ArtifactInfo>>());


    public FreeMarkerSiteGenerator(File jsonFile, File htmlFile) {
        this.jsonFile = jsonFile;
        this.htmlDir = htmlFile;
    }

    @Override
    public void generate(String taskName, String title, String documentation, String type, String url, String range) {
        generate(taskName, title, documentation, type, url, range, null);
    }

    public void generate(String taskName, String title, String documentation, String type, String url, String range, String details) {
        try {
            htmlDir.mkdirs();
            // Because gradle can't keep state between projects, we have to append the current projects data to a file,
            // on disk and read it back on each subsequent project evaluation
            writeJson(title, type, range, details);
            processJson();

            // Generate MDX file with compatibility documentation
            processCompatibilityDocTemplate();

            // Generate a simple MD file for internal reference in the agent repo
            processSimpleCompatibilityDoc();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void processJson() {
        JSONObject obj;
        String line = null;
        try {
            FileReader fileReader = new FileReader(jsonFile);
            if (!Files.exists(jsonFile.toPath())) {
                return;
            }

            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine()) != null) {
                obj = (JSONObject) new JSONParser().parse(line);
                category.addCategory(String.valueOf(obj.get("type")), (JSONObject) obj.get("artifact"));
            }

            bufferedReader.close();
        } catch (IOException | ParseException ex) {
            ex.printStackTrace();
        }
    }

    private void writeJson(String title, String type, String range, String details) {
        JSONObject project = new JSONObject();
        JSONObject projectDetails = new JSONObject();

        StringBuilder newRange = new StringBuilder();

        if (range.endsWith(RANGE_SEPARATOR)) {
            newRange.append(range);
            newRange.append(CURRENT_VERSION);
        } else {
            newRange.append(range);
        }
        projectDetails.put("range", newRange.toString());
        projectDetails.put("title", title);
        projectDetails.put("details", details != null && !details.isEmpty() ? details : "");
        project.put("type", type);
        project.put("artifact", projectDetails);

        // We must append, so the next project(s) has all the previous projects info
        try (FileWriter writer = new FileWriter(jsonFile, true)) {
            writer.write(project.toJSONString() + "\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void processCompatibilityDocTemplate() {
        File mdxFile;
        FileWriter writer;
        try {
            mdxFile = new File(this.htmlDir.getPath() + "/compatibility-requirements-java-agent.mdx");
            // we write this file multiple times, so we must remove it each time
            if (htmlDir.exists()) {
                Files.deleteIfExists(mdxFile.toPath());
            }
            writer = new FileWriter(mdxFile);

            Configuration configuration = new Configuration(Configuration.VERSION_2_3_28);
            configuration.setClassLoaderForTemplateLoading(this.getClass().getClassLoader(), "template");
            configuration.setDefaultEncoding("UTF-8");
            configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            configuration.setLogTemplateExceptions(false);

            Template template = configuration.getTemplate(PUBLIC_DOC_FTL_TEMPLATE);

            template.process(category, writer);

        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    private void processSimpleCompatibilityDoc() {
        File simpleMdFile;
        FileWriter writer;
        try {
            simpleMdFile = new File(this.htmlDir.getPath() + "/compatibility-requirements-java-agent-internal.md");
            // we write this file multiple times, so we must remove it each time
            if (htmlDir.exists()) {
                Files.deleteIfExists(simpleMdFile.toPath());
            }
            writer = new FileWriter(simpleMdFile);

            Configuration configuration = new Configuration(Configuration.VERSION_2_3_28);
            configuration.setClassLoaderForTemplateLoading(this.getClass().getClassLoader(), "template");
            configuration.setDefaultEncoding("UTF-8");
            configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            configuration.setLogTemplateExceptions(false);

            Template template = configuration.getTemplate(INTERNAL_DOC_FTL_TEMPLATE);

            template.process(category, writer);

        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }
}
