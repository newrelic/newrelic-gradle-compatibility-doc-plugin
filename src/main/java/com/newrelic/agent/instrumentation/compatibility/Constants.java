/*
 * Copyright 2020 New Relic Corporation. All rights reserved.
 * SPDX-License-Identifier: Apache-2.0
 */

package com.newrelic.agent.instrumentation.compatibility;

public final class Constants {
    static final String RANGE_SEPARATOR = " to ";
    static final String CURRENT_VERSION = "latest";
    static final String PUBLIC_DOC_FTL_TEMPLATE = "compatibility-doc.ftl";
    static final String INTERNAL_DOC_FTL_TEMPLATE = "compatibility-doc-simple.ftl";
    static final String EXCLUSIVE_SUFFIX = " (exclusive)";
    private Constants(){

    throw new AssertionError();
  }
}
