/*
 * Copyright 2020 New Relic Corporation. All rights reserved.
 * SPDX-License-Identifier: Apache-2.0
 */

package com.newrelic.agent.instrumentation.compatibility;

import org.apache.maven.artifact.versioning.DefaultArtifactVersion;
import org.json.simple.JSONObject;

import java.util.Map;
import java.util.TreeMap;

import static com.newrelic.agent.instrumentation.compatibility.Constants.*;

public class Category {

    private Map<String, TreeMap<String, ArtifactInfo>> category;

    public Category(Map<String, TreeMap<String, ArtifactInfo>> category) {
        this.category = category;
    }

    public void addCategory(String category, JSONObject artifact) {
        String title = String.valueOf(artifact.get("title"));
        String range = String.valueOf(artifact.get("range"));
        String details = String.valueOf(artifact.get("details"));

        if (this.category.containsKey(category)) {
            if (this.category.get(category).containsKey(title)) {
                ArtifactInfo existingInfo = this.category.get(category).get(title);
                ArtifactInfo newInfo = new ArtifactInfo(range, details);
                existingInfo.merge(newInfo);
            } else {
                this.category.get(category).put(title, new ArtifactInfo(range, details));
            }
        } else {
            this.category.put(category, addArtifact(artifact));
        }
    }

    private static TreeMap<String, ArtifactInfo> addArtifact(JSONObject object) {
        TreeMap<String, ArtifactInfo> artifact = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

        String title = String.valueOf(object.get("title"));
        String range = String.valueOf(object.get("range"));
        String details = String.valueOf(object.get("details"));

        artifact.put(title, new ArtifactInfo(range, details));
        return artifact;
    }

    // This is used by FTL
    public Object get(String key) {
        return category.get(key);
    }
}
