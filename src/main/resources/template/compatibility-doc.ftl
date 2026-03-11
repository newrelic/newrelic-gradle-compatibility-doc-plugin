---
title: Compatibility and requirements for the Java agent
tags:
  - Agents
  - Java agent
  - Getting started
translate:
  - jp
metaDescription: 'The New Relic APM Java agent: compatibility and requirements.'
redirects:
  - /docs/agents/java-agent/getting-started/compatibility-requirements-java-agent
  - /docs/compatibility-requirements-java-agent
  - /docs/agents/java-agent/frameworks/java-instance-level-database-information
  - /docs/agents/java-agent/installation/java-se-50-support
  - /docs/java/java-se-5
freshnessValidatedDate: never
---

[New Relic's Java agent](/docs/agents/java-agent/getting-started/introduction-new-relic-java) includes built-in instrumentation of the most popular parts of the Java ecosystem, including app servers, frameworks, databases, and message queuing systems. For frameworks and libraries that are not instrumented out of the box, you can extend the agent with [Java custom instrumentation](/docs/agents/java-agent/custom-instrumentation/java-custom-instrumentation).

Want to try out New Relic's Java agent? [Create a New Relic account](https://newrelic.com/signup) for free! No credit card required.

## Requirements to install the agent [#java-requirements]

Before you install the Java agent, ensure your system meets these requirements:

<CollapserGroup>
  <Collapser
    id="jvm"
    title="JVM"
  >
    The Java agent is compatible with any JVM-based language, including: Java, Scala, Kotlin, and Clojure. For instrumentation support for language-specific features, see the [Automatically instrumented frameworks and libraries](#auto-instrumented) section below.

    <table>
      <thead>
        <tr>
          <th style={{ width: "200px" }}>
            Java version
          </th>

          <th style={{ width: "300px" }}>
            Compatible Java agent versions
          </th>
        </tr>
      </thead>

      <tbody>
        <tr>
          <td>
            Java 26
          </td>

          <td>
            v9.2.0 to current
          </td>
        </tr>
        <tr>
          <td>
            Java 25
          </td>

          <td>
            v8.25.0 to current
          </td>
        </tr>
        <tr>
          <td>
            Java 24
          </td>

          <td>
            v8.20.0 to current
          </td>
        </tr>
        <tr>
          <td>
            Java 21
          </td>

          <td>
            v8.7.0 to current
          </td>
        </tr>
        <tr>
          <td>
            Java 17
          </td>

          <td>
            v7.4.0 to current
          </td>
        </tr>
        <tr>
          <td>
            Java 11
          </td>

          <td>
            v4.7.0 to current
          </td>
        </tr>
        <tr>
          <td>
            Java 8
          </td>

          <td>
            v3.10.0 to current
          </td>
        </tr>
        <tr>
          <td>
            Java 7
          </td>

          <td>
            v3.0.0 to v6.5.0, v6.5.2, v6.5.3, and v6.5.4
          </td>
        </tr>
      </tbody>
    </table>

    Some Java agent versions in this table are no longer supported, but are still listed for reference. The list of supported Java agent versions is in [Java agent EOL policy](/docs/apm/agents/java-agent/getting-started/java-agent-eol-policy/).
  </Collapser>

  <Collapser
    id="security-requirements"
    title="Security requirements"
  >
    As a standard [security measure for data collection](/docs/accounts-partnerships/accounts/security/data-security), your app server must support SHA-2 (256-bit). SHA-1 is not supported.
  </Collapser>

  <Collapser
    id="other-apm"
    title="Use of other monitoring software"
  >
    If your application uses other application monitoring software besides our agent, we cannot guarantee that our agent will work correctly and we cannot offer technical support. For more information, see [Errors while using other monitoring software](/docs/apm/new-relic-apm/troubleshooting/errors-while-using-new-relic-apm-alongside-other-apm-software).
  </Collapser>
</CollapserGroup>

## Built-in instrumentation [#auto-instrumented]

After you [install the Java agent](/docs/agents/java-agent/installation/install-java-agent), it automatically instruments many popular frameworks and libraries. With automatic instrumentation, the agent collects rich data out of the box, and data will show up in your New Relic dashboards within minutes of installation. Even if your library is not automatically instrumented, you can still collect data with [custom instrumentation](/docs/agents/java-agent/custom-instrumentation/java-custom-instrumentation) and the [Java agent API](/docs/agents/java-agent/api-guides/guide-using-java-agent-api).

The agent automatically instruments these frameworks and libraries:

<CollapserGroup>
<#if Appserver??>
  <Collapser
    id="app-web-servers"
    title="App/Web servers"
  >
    The agent automatically instruments the following app/web servers. To install the Java agent on supported app/web servers, see [Install the Java agent](/docs/agents/java-agent/installation/java-agent-manual-installation).

    <#list Appserver as key, value>
    * ${key} ${value.range}
    <#if value.hasDetails()>
    <#list value.details as detail>
      * ${detail}
    </#list>
    </#if>
    </#list>
  </Collapser>
</#if>

<#if Framework??>
  <Collapser
    id="frameworks"
    title="Frameworks"
  >
    The agent automatically instruments the following frameworks. To install the Java agent on supported frameworks, see [Install the Java agent](/docs/agents/java-agent/installation/java-agent-manual-installation).

    <#list Framework as key, value>
    * ${key} ${value.range}
    <#if value.hasDetails()>
    <#list value.details as detail>
      * ${detail}
    </#list>
    </#if>
    </#list>
    * JSF (Java Server Faces)
  </Collapser>
</#if>

<#if Http??>
  <Collapser
          id="http"
          title="HTTP libraries"
  >
    The agent automatically instruments the following HTTP libraries.

    <#list Http as key, value>
    * ${key} ${value.range}
    <#if value.hasDetails()>
    <#list value.details as detail>
      * ${detail}
    </#list>
    </#if>
    </#list>
  </Collapser>
</#if>

<#if Logging??>
  <Collapser
          id="logging"
          title="Logging libraries"
  >
    The agent automatically instruments the following logging libraries. For more information about the Java agent's logging solutions, including
    log forwarding and logs in context, see our [logging-specific documentation](/docs/logs/logs-context/java-configure-logs-context-all/).

    <#list Logging as key, value>
    * ${key} ${value.range}
    <#if value.hasDetails()>
    <#list value.details as detail>
      * ${detail}
    </#list>
    </#if>
    </#list>
  </Collapser>
</#if>

<#if Messaging??>
  <Collapser
    id="messaging"
    title="Messaging"
  >
    The agent automatically instruments the following messaging services. For instructions, see [Install the Java agent](/docs/agents/java-agent/installation/java-agent-manual-installation).

    <#list Messaging as key, value>
    * ${key} ${value.range}
    <#if value.hasDetails()>
    <#list value.details as detail>
      * ${detail}
    </#list>
    </#if>
    </#list>

    <#if Kafka??>
    The Java agent instruments the following Kafka libraries. Not all Kafka instrumentation is enabled by default. See our [Kafka documentation](docs/apm/agents/java-agent/instrumentation/java-agent-instrument-kafka-message-queues/) for more information.

    <#list Kafka as key, value>
    * ${key} ${value.range}
    <#if value.hasDetails()>
    <#list value.details as detail>
      * ${detail}
    </#list>
    </#if>
    </#list>
    </#if>

  </Collapser>
</#if>

<#if Datastore??>
  <Collapser
    id="JDBC"
    title="Datastores"
  >
    New Relic currently supports MySQL and PostgreSQL to capture explain plans for slow database queries.

    * Generic JDBC (any JDBC compliant driver)
    <#list Datastore as key, value>
    * ${key} ${value.range}
    <#if value.hasDetails()>
    <#list value.details as detail>
      * ${detail}
    </#list>
    </#if>
    </#list>
  </Collapser>
</#if>

<#if InstanceLevelDB??>
  <Collapser
    id="instance-level-db"
    title="Instance-level database information"
  >
    New Relic collects [instance details for a variety of databases and database drivers](/docs/apm/applications-menu/features/analyze-database-instance-level-performance-issues). The ability to view specific instances and the types of database information in APM depends on your New Relic agent version.

    New Relic's Java agent [versions 3.33.0 or higher](/docs/release-notes/agent-release-notes/java-release-notes/java-agent-3330) support the following:

    * Any [compatible JDBC driver](#JDBC)
    <#list InstanceLevelDB as key, value>
    * ${key} ${value.range}
    <#if value.hasDetails()>
    <#list value.details as detail>
      * ${detail}
    </#list>
    </#if>
    </#list>
  </Collapser>
</#if>

<#if AI??>
  <Collapser
          id="ai-monitoring"
          title="AI Monitoring"
  >
    If you have version 8.12.0 or higher of Java agent, you can collect AI data from certain AI libraries and frameworks. See our [AI Monitoring documentation](/docs/ai-monitoring/intro-to-ai-monitoring) for more information.
    <#list AI as key, value>
    * ${key} ${value.range}
    <#if value.hasDetails()>
    <#list value.details as detail>
      * ${detail}
    </#list>
    </#if>
    </#list>
  </Collapser>
</#if>

  <Collapser
    id="hosting-services"
    title="Hosting services"
  >
    You can install the Java agent on a variety of hosting services, including ones not listed below. Here are detailed installation guides for particular hosting services:

    * [Google App Engine (GAE) flexible environment](/docs/agents/java-agent/additional-installation/google-app-engine-flexible-installation-java#tomcat-example)
    * [Heroku](/docs/agents/java-agent/heroku/java-agent-heroku)
  </Collapser>

<#if Other??>
  <Collapser
    id="other"
    title="Other instrumented features"
  >
    <#list Other as key, value>
    * ${key} ${value.range}
    <#if value.hasDetails()>
    <#list value.details as detail>
      * ${detail}
    </#list>
    </#if>
    </#list>
  </Collapser>
</#if>
</CollapserGroup>

## Connect the agent to other New Relic products [#digital-intelligence-platform]

The Java agent integrates with other New Relic products to give you end-to-end visibility:

<table>
  <thead>
    <tr>
      <th style={{ width: "200px" }}>
        Product
      </th>

      <th>
        Capability
      </th>
    </tr>
  </thead>

  <tbody>
    <tr>
      <td>
        [Browser monitoring](/docs/browser/new-relic-browser/getting-started/introduction-new-relic-browser)
      </td>

      <td>
        The Java agent automatically injects the browser JavaScript agent when you [enable auto-instrumentation](/docs/browser/new-relic-browser/installation/install-new-relic-browser-agent#select-apm-app). After enabling browser injection, you can view browser data in the [APM Summary page](/docs/apm/applications-menu/monitoring/apm-overview-page) and quickly switch between the APM and browser data for a particular app. For configuration options and manual instrumentation, see [<InlinePopover type="browser"/> and the Java agent](/docs/agents/java-agent/instrumentation/page-load-timing-java).
      </td>
    </tr>

    <tr>
      <td>
        [Infrastructure monitoring](/docs/infrastructure/new-relic-infrastructure/getting-started/introduction-new-relic-infrastructure)
      </td>

      <td>
        When you install the infrastructure and APM agents on the same host, they automatically detect one another. You can then view a list of hosts in the APM UI, and filter your hosts by APM app in our infrastructure UI. For more information, see [APM data in the infrastructure UI](/docs/infrastructure/new-relic-infrastructure/data-instrumentation/new-relic-apm-data-infrastructure).
      </td>
    </tr>

    <tr>
      <td>
        [New Relic dashboards](/docs/query-your-data/explore-query-data/dashboards/introduction-new-relic-one-dashboards)
      </td>

      <td>
        The Java agent sends default events and attributes to dashboards, or you can [run NRQL queries in the query builder](/docs/query-your-data/explore-query-data/query-builder/use-advanced-nrql-mode-specify-data). You can also [record custom events](/docs/insights/insights-data-sources/custom-data/insert-custom-events-new-relic-apm-agents) for advanced analysis.
      </td>
    </tr>

    <tr>
      <td>
        [Synthetic monitoring](/docs/synthetics/new-relic-synthetics/getting-started/introduction-new-relic-synthetics)
      </td>

      <td>
        [Synthetic transaction traces](/docs/synthetics/new-relic-synthetics/using-monitors/collect-synthetic-transaction-traces) connect requests from synthetic monitors to the underlying APM transaction.
      </td>
    </tr>
  </tbody>
</table>