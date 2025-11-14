/*
 * Copyright 2020 New Relic Corporation. All rights reserved.
 * SPDX-License-Identifier: Apache-2.0
 */

package com.newrelic.agent.instrumentation.compatibility;

import org.apache.maven.artifact.versioning.DefaultArtifactVersion;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static com.newrelic.agent.instrumentation.compatibility.Constants.*;

public class Category {

    private Map<String, TreeMap<String, String>> category;

    public Category(Map<String, TreeMap<String, String>> category) {
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

        // Check if either bound is exclusive
        boolean oldExclusive = oldTo.endsWith(EXCLUSIVE_SUFFIX);
        boolean newExclusive = newTo.startsWith(EXCLUSIVE_SUFFIX);

        // Remove the exclusive suffix for version comparison
        String oldToVersion = oldExclusive ? oldTo.substring(0, oldTo.length() - EXCLUSIVE_SUFFIX.length()) : oldTo;
        String newToVersion = newExclusive ? newTo.substring(0, newTo.length() - EXCLUSIVE_SUFFIX.length()) : newTo;

        if (oldToVersion.contains(CURRENT_VERSION) || newToVersion.contains(CURRENT_VERSION)) {
            mRange.append(CURRENT_VERSION);
        } else {
            int comparison = compareVersion(oldToVersion, newToVersion);
            if (comparison > 0) {
                // oldTo is greater - use it with its exclusivity marker
                mRange.append(oldTo);
            } else if (comparison < 0) {
                // newTo is greater - use it with its exclusivity marker
                mRange.append(newTo);
            } else {
                // Versions are equal - if either is inclusive, use inclusive
                if (!oldExclusive){
                    mRange.append(oldTo);
                } else {
                    mRange.append(newTo);
                }
            }
        }
        return mRange.toString();
    }

    private static TreeMap<String, String> addArtifact(JSONObject object) {
        TreeMap<String, String> artifact = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

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
