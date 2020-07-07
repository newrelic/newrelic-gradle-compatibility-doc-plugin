/*
 * Copyright 2020 New Relic Corporation. All rights reserved.
 * SPDX-License-Identifier: Apache-2.0
 */

package com.newrelic.agent.instrumentation.compatibility

import org.apache.maven.artifact.versioning.DefaultArtifactVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.plugins.JavaBasePlugin
import org.gradle.api.tasks.OutputDirectory

class CompatibilitySitePlugin implements Plugin<Project> {

    public static final String GENERATE_SITE_TASK_NAME = "generateCompatibilitySite"
    public static final String EXTENSION_NAME = "site"
    public static String DEFAULT_OUTPUT_DIR = "/build/docs/site"
    public static String DEFAULT_JSON_OUTPUT = "/build/docs/site/compatibility.json"

    @OutputDirectory
    File jsonOutput

    @OutputDirectory
    File htmlOutput

    @Override
    void apply(Project project) {
        CompatibilitySitePluginExtension compatibilityPlugin = project.extensions.create(EXTENSION_NAME, CompatibilitySitePluginExtension)

        List<Task> tasks = new ArrayList<>()

        Task siteTask = project.task(GENERATE_SITE_TASK_NAME)
        siteTask.group = JavaBasePlugin.DOCUMENTATION_GROUP
        siteTask.description = "Generates a web page containing information about the project."

        // Only process instrumentation projects
        if (project.getParent().getName() != "instrumentation") {
            return
        }

        project.afterEvaluate {
            // site and title are required to generate the html
            if (!project.getExtensions()["site"]["title"].isEmpty() && !project.getExtensions()["site"]["type"].isEmpty()) {
                addSiteTask(project, compatibilityPlugin, tasks)
            }
            for (Task task : tasks) {
                siteTask.finalizedBy(task)
            }
        }
    }

    void addSiteTask(Project project, CompatibilitySitePluginExtension compatibilityPluginExtension, List tasks) {
        String passesOnly = project.extensions.getByName("verifyInstrumentation").passesOnly().keySet().take(1)[0]
        String passes = project.extensions.getByName("verifyInstrumentation").passes().keySet().take(1)[0]

        String title = compatibilityPluginExtension.getTitle()
        String type = compatibilityPluginExtension.getType()
        String versionOverride = compatibilityPluginExtension.getVersionOverride()
        // documentation and url are current unused
        String documentation = compatibilityPluginExtension.getDocumentation()
        String url = compatibilityPluginExtension.getUrl()

        List<String> versions
        // Store the ranges in a set sorted by Maven's version
        SortedSet<DefaultArtifactVersion> range = new TreeSet<>()
        if (!versionOverride.isEmpty()) {
            versions = getRangeList(versionOverride)
            for (String version : versions) {
                range.add(new DefaultArtifactVersion(version))
            }
        } else if (passesOnly != null) {
            versions = getRangeList(passesOnly)
            for (String version : versions) {
                range.add(new DefaultArtifactVersion(version))
            }
        } else if (passes != null) {
            versions = getRangeList(passes)
            for (String version : versions) {
                range.add(new DefaultArtifactVersion(version))
            }

        } else {
            throw new RuntimeException("There must be a valid range")
        }

        def task = project.task(compatibilityPluginExtension, type: CompatibilitySiteTask) {
            setTitle(title)
            setDocumentation(documentation)
            setUrl(url)
            setType(type)
            setRange(range)

            jsonOutput = project.file("${project.rootDir}" + DEFAULT_JSON_OUTPUT)
            htmlOutput = project.file("${project.rootDir}" + DEFAULT_OUTPUT_DIR)

            setJsonFile(jsonOutput)
            setHtmlFile(htmlOutput)
        }
        tasks.add(task)
    }

    private List<String> getRangeList(String range) {
        List<String> versions = new ArrayList<>()
        int index = range.indexOf("[")
            if (index == -1) {
                List<String> tmp = range.split(":")
                index = tmp.size()
                versions.add(tmp.get(index - 1))
            } else {
                versions = range.substring(index + 1, range.length() - 1).split(',')
            }
        return versions
    }
}
