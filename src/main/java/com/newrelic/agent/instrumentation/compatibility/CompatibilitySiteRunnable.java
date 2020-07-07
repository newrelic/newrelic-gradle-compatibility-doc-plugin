/*
 * Copyright 2020 New Relic Corporation. All rights reserved.
 * SPDX-License-Identifier: Apache-2.0
 */

package com.newrelic.agent.instrumentation.compatibility;

import org.gradle.api.Project;

import javax.inject.Inject;
import java.io.File;

public class CompatibilitySiteRunnable implements Runnable {

    private final File json;
    private final File htmlDir;

    private Project project;
    private String taskName;
    private String title;
    private String documentation;
    private String type;
    private String url;
    private String range;

    @Inject
    public CompatibilitySiteRunnable(String taskName, String title, String documentation, String type, String url, String range, File json, File htmlDir) {
        this.taskName = taskName;
        this.title = title;
        this.documentation = documentation;
        this.type = type;
        this.url = url;
        this.range = range;
        this.json = json;
        this.htmlDir = htmlDir;
    }

    public void run() {
        new FreeMarkerSiteGenerator(json, htmlDir).generate(taskName, title, documentation, type, url, range);
    }

}
