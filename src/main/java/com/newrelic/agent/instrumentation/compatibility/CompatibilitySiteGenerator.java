/*
 * Copyright 2020 New Relic Corporation. All rights reserved.
 * SPDX-License-Identifier: Apache-2.0
 */

package com.newrelic.agent.instrumentation.compatibility;

interface CompatibilitySiteGenerator {

    void generate(String taskName, String title, String documentation, String type, String url, String range);
}
