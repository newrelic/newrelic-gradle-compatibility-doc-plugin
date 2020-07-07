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
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import static com.newrelic.agent.instrumentation.compatibility.Constants.CURRENT_VERSION;
import static com.newrelic.agent.instrumentation.compatibility.Constants.FTL_TEMPLATE;
import static com.newrelic.agent.instrumentation.compatibility.Constants.RANGE_SEPARATOR;

public class FreeMarkerSiteGenerator implements CompatibilitySiteGenerator {

    private final File jsonFile;
    private final File htmlDir;
    private final Category category = new Category(new HashMap<String, Map<String,String>>());


    public FreeMarkerSiteGenerator(File jsonFile, File htmlFile) {
        this.jsonFile = jsonFile;
        this.htmlDir = htmlFile;
    }

    @Override
    public void generate(String taskName, String title, String documentation, String type, String url, String range) {
        try {
            htmlDir.mkdirs();
            // Because gradle can't keep state between projects, we have to append the current projects data to a file,
            // on disk and read it back on each subsequent project evaluation
            writeJson(title, type, range);
            processJson();

            copyCssResources();
            copyImgResources();
            // This will remove the existing html, and build a new with the data in the aforementioned json file
            processIndexPageTemplate();
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

    private void writeJson(String title, String type,String range) {
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

    private void copyCssResources() throws IOException {
        Path path = Files.createDirectories(Paths.get(htmlDir.getAbsolutePath() + "/css/"));
        copyResources(path, "css/", "bootstrap.css");
        copyResources(path, "css/", "bootstrap-responsive.css");
    }

    private void copyImgResources() throws IOException {
        Path path = Files.createDirectories(Paths.get(htmlDir.getAbsolutePath() + "/img/"));
        copyResources(path, "img/", "newrelic-logo.png");
    }

    private void copyResources(Path path, String subdir, String resource) throws IOException {
        if (Files.exists(Paths.get(path.toString() + "/" + resource))) {
            return;
        }
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(subdir + resource);
        try {
            Files.copy(in, Paths.get(path.toString() + "/" + resource), REPLACE_EXISTING);
        } catch (IOException e) {
            //this causes an error the first time you run it, but it doesn't seem to break anything, so ignoring for now
        }
    }

    private void processIndexPageTemplate() {
        File htmlFile;
        FileWriter writer;
        try {
            htmlFile = new File(this.htmlDir.getPath() + "/index.html");
            // we write this file multiple times, so we must remove it each time
            if (htmlDir.exists()) {
                Files.deleteIfExists(htmlFile.toPath());
            }
            writer = new FileWriter(htmlFile);

            Configuration configuration = new Configuration(Configuration.VERSION_2_3_28);
            configuration.setClassLoaderForTemplateLoading(this.getClass().getClassLoader(), "template");
            configuration.setDefaultEncoding("UTF-8");
            configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            configuration.setLogTemplateExceptions(false);

            Template template = configuration.getTemplate(FTL_TEMPLATE);

            template.process(category, writer);

        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }
}
