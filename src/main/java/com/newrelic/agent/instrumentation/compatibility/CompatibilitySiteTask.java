/*
 * Copyright 2020 New Relic Corporation. All rights reserved.
 * SPDX-License-Identifier: Apache-2.0
 */

package com.newrelic.agent.instrumentation.compatibility;

import org.gradle.api.Action;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;
import org.gradle.workers.IsolationMode;
import org.gradle.workers.WorkerConfiguration;
import org.gradle.workers.WorkerExecutor;

import javax.inject.Inject;
import java.io.File;
import java.util.SortedSet;

import static com.newrelic.agent.instrumentation.compatibility.Constants.RANGE_SEPARATOR;

public class CompatibilitySiteTask extends DefaultTask {

    private final WorkerExecutor workerExecutor;

    private String title;
    private String documentation;
    private String type;
    private String url;
    private String range;
    private File json;
    private File htmlDir;

    @Input
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Input
    public String getDocumentation() {
        return this.documentation;
    }

    public void setDocumentation(String documentation) {
        this.documentation = documentation;
    }

    @Input
    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Input
    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setRange(SortedSet<DefaultTask> range) throws Exception {
        if (range.size() > 2) throw new RuntimeException("More than one version is not allowed");
        StringBuilder sb = new StringBuilder();
        sb.append(String.valueOf(range.first()));
        sb.append(RANGE_SEPARATOR);
        if (range.size() == 2) sb.append(String.valueOf(range.last()));
        this.range = sb.toString();
    }

    public void setJsonFile(File json) {
        this.json = json;
    }

    public void setHtmlFile(File htmlDir) {
        this.htmlDir = htmlDir;
    }

    @Inject
    public CompatibilitySiteTask(WorkerExecutor workerExecutor) {
        super();
        this.workerExecutor = workerExecutor;
    }

    @TaskAction
    public void generate() {
        workerExecutor.submit(CompatibilitySiteRunnable.class, new Action<WorkerConfiguration>() {
            @Override
            public void execute(WorkerConfiguration config) {
                config.setIsolationMode(IsolationMode.NONE);
                config.params(getName(), title, documentation, type, url, range, json, htmlDir);
            }
        });
    }
}
