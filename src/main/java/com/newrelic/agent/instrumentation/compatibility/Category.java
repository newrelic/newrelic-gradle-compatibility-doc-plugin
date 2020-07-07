/*
 * Copyright 2020 New Relic Corporation. All rights reserved.
 * SPDX-License-Identifier: Apache-2.0
 */

package com.newrelic.agent.instrumentation.compatibility;

import org.apache.maven.artifact.versioning.DefaultArtifactVersion;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.newrelic.agent.instrumentation.compatibility.Constants.CURRENT_VERSION;
import static com.newrelic.agent.instrumentation.compatibility.Constants.RANGE_SEPARATOR;

public class Category {

    private Map<String, Map<String, String>> category;

    public Category(Map<String, Map<String, String>> category) {
        this.category = category;
    }

    public void addCategory(String category, JSONObject artifact) {
        String title = String.valueOf(artifact.get("title"));
        String range = String.valueOf(artifact.get("range"));

        if (this.category.containsKey(category)) {
            if (this.category.get(category).containsKey(title)) {
                String newRange = mergeRange(range, this.category.get(category).get(title));
                artifact.replace("range", range, newRange);
                this.category.get(category).put(title, newRange);
            } else {
                this.category.get(category).put(title, range);
            }
        } else {
            this.category.put(category, addArtifact(artifact));
        }
    }

    private int compareVersion(String oldVersion, String newVersion) {
        DefaultArtifactVersion ov = new DefaultArtifactVersion(oldVersion);
        DefaultArtifactVersion nv = new DefaultArtifactVersion(newVersion);
        return ov.compareTo(nv);
    }

    private String mergeRange(String oldRange, String newRange) {
        StringBuilder mRange = new StringBuilder();

        String oldFrom = oldRange.split(RANGE_SEPARATOR)[0];
        String newFrom = newRange.split(RANGE_SEPARATOR)[0];

        if (compareVersion(oldFrom, newFrom) < 1) {
            mRange.append(oldFrom + RANGE_SEPARATOR);
        } else {
            mRange.append(newFrom + RANGE_SEPARATOR);
        }

        String oldTo = oldRange.split(RANGE_SEPARATOR)[1];
        String newTo = newRange.split(RANGE_SEPARATOR)[1];

        if (oldTo.contains(CURRENT_VERSION) || newTo.contains(CURRENT_VERSION)) {
            mRange.append(CURRENT_VERSION);
        } else {
            if (compareVersion(oldTo, newTo) > 0) {
                mRange.append(oldTo);
            } else {
                mRange.append(newTo);
            }
        }
        return mRange.toString();
    }

    private static Map<String, String> addArtifact(JSONObject object) {
        Map<String, String> artifact = new HashMap<>();

        String title = String.valueOf(object.get("title"));
        String range = String.valueOf(object.get("range"));

        artifact.put(title, range);
        return artifact;
    }

    // This is used by FTL
    public Object get(String key) {
        return category.get(key);
    }
}
