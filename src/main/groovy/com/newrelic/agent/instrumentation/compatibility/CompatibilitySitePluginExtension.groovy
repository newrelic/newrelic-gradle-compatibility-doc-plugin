/*
 * Copyright 2020 New Relic Corporation. All rights reserved.
 * SPDX-License-Identifier: Apache-2.0
 */

package com.newrelic.agent.instrumentation.compatibility

class CompatibilitySitePluginExtension {
    enum validTypes {
        Framework, Datastore, Messaging, Other, Appserver, AI, InstanceLevelDB, Logging
    }
    def title = ''
    def documentation = ''
    def outputDir = ''
    def url = ''
    def type = ''
    def versionOverride = ''
    def types = []

    void title(info) {
        this.title = info
    }

    void documentation(info) {
        documentation = info
    }

    void url(info) {
        url = info
    }

    void type(info) {
        def possibleTypes = info.split(", ")
        for (String t : possibleTypes) {
            if (! (t in (validTypes.values().collect {it.name() }))) {
                print(String.format("Invalid type %s specified for site task. Must be one of: \n", t))
                for (validType in validTypes.values()) {
                    println(validType.name())
                }
                throw new RuntimeException()
            }
        }
        types = possibleTypes

    }

    void versionOverride(info) {
        versionOverride = info
    }

}
