/*
 * Copyright 2020 New Relic Corporation. All rights reserved.
 * SPDX-License-Identifier: Apache-2.0
 */

package com.newrelic.agent.instrumentation.compatibility

class CompatibilitySitePluginExtension {
    enum validTypes {
        Framework, Datastore, Messaging, Other, Appserver

    }
    def title = ''
    def documentation = ''
    def outputDir = ''
    def url = ''
    def type = ''
    def versionOverride = ''

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
        if (info == validTypes.Appserver.toString()) {
            type = info
        } else if (info == validTypes.Framework.toString()) {
            type = info
        } else if (info == validTypes.Datastore.toString()) {
            type = info
        } else if (info == validTypes.Messaging.toString()) {
            type = info
        } else if (info == validTypes.Other.toString()) {
            type = info
        } else {
            print(String.format("Invalid type %s specified for site task. Must be one of: \n", info))
            for (t in validTypes.values()) {
                println(t.toString())
            }
            throw new RuntimeException()
        }
    }

    void versionOverride(info) {
        versionOverride = info
    }

}
