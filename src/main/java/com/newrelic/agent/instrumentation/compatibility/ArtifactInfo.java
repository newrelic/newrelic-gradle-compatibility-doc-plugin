/*
 * Copyright 2020 New Relic Corporation. All rights reserved.
 * SPDX-License-Identifier: Apache-2.0
 */

package com.newrelic.agent.instrumentation.compatibility;

import org.apache.maven.artifact.versioning.DefaultArtifactVersion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.newrelic.agent.instrumentation.compatibility.Constants.*;

public class ArtifactInfo {
    private String range;
    private List<String> details;

    public ArtifactInfo(String range) {
        this(range, "");
    }

    public ArtifactInfo(String range, String detailsString) {
        this.range = range;
        this.details = detailsString == null || detailsString.trim().isEmpty() ? new ArrayList<>() : Arrays.asList(detailsString.split(", "));
    }

    public String getRange() {
        return range;
    }

    public List<String> getDetails() {
        return details;
    }

    public boolean hasDetails() {
        return !details.isEmpty();
    }

    public void merge(ArtifactInfo artifactInfo) {
        details = mergeDetails(artifactInfo);
        range = mergeRange(artifactInfo);
    }

    private List<String> mergeDetails(ArtifactInfo artifactInfo) {
        List<String> mDetails = new ArrayList<>(details);
        mDetails.addAll(artifactInfo.getDetails());
        return mDetails;
    }

    private String mergeRange(ArtifactInfo artifactInfo) {
        String oldRange = this.range;
        String newRange = artifactInfo.getRange();

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

    private int compareVersion(String oldVersion, String newVersion) {
        DefaultArtifactVersion ov = new DefaultArtifactVersion(oldVersion);
        DefaultArtifactVersion nv = new DefaultArtifactVersion(newVersion);
        return ov.compareTo(nv);
    }
}
