# Java Agent Compatibility

## JVMs

This version of the Java Agent supports Java versions 8 - 25.

<#if Appserver??>
  ## App/Web severs
  The agent automatically instruments the following app/web servers.

  <#list Appserver as key, value>
  * ${key} ${value.range}
  <#if value.hasDetails()>
  <#list value.details as detail>
    * ${detail}
  </#list>
  </#if>
  </#list>
</#if>

<#if Framework??>
  ## Frameworks
  The agent automatically instruments the following frameworks.

  <#list Framework as key, value>
  * ${key} ${value.range}
  <#if value.hasDetails()>
  <#list value.details as detail>
    * ${detail}
  </#list>
  </#if>
  </#list>
  * JSF (Java Server Faces)
</#if>

<#if Http??>
  ## HTTP libraries
  The agent automatically instruments the following HTTP libraries.

  <#list Http as key, value>
  * ${key} ${value.range}
  <#if value.hasDetails()>
  <#list value.details as detail>
    * ${detail}
  </#list>
  </#if>
  </#list>
</#if>

<#if Logging??>
  ## Logging libraries
  The agent automatically instruments the following logging libraries. For more information about the Java agent's logging solutions, including
  log forwarding and logs in context, see our [logging-specific documentation](https://docs.newrelic.com/docs/logs/logs-context/java-configure-logs-context-all/).

  <#list Logging as key, value>
  * ${key} ${value.range}
  <#if value.hasDetails()>
  <#list value.details as detail>
    * ${detail}
  </#list>
  </#if>
  </#list>
</#if>

<#if Messaging??>
  ## Messaging
  The agent automatically instruments the following messaging services.

  <#list Messaging as key, value>
  * ${key} ${value.range}
  <#if value.hasDetails()>
  <#list value.details as detail>
    * ${detail}
  </#list>
  </#if>
  </#list>

  <#if Kafka??>
  The agent instruments the following Kafka libraries. Not all Kafka instrumentation is enabled by default. See our [Kafka documentation](https://docs.newrelic.com/docs/apm/agents/java-agent/instrumentation/java-agent-instrument-kafka-message-queues/) for more information.

  <#list Kafka as key, value>
  * ${key} ${value.range}
  <#if value.hasDetails()>
  <#list value.details as detail>
    * ${detail}
  </#list>
  </#if>
  </#list>
  </#if>

</#if>

<#if Datastore??>
  ## Datastores
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
</#if>

<#if InstanceLevelDB??>
  ## Instance-level database information
  New Relic collects instance details for a variety of databases and database drivers.

  * Any [compatible JDBC driver](#JDBC)
  <#list InstanceLevelDB as key, value>
  * ${key} ${value.range}
  <#if value.hasDetails()>
  <#list value.details as detail>
    * ${detail}
  </#list>
  </#if>
  </#list>
</#if>

<#if AI??>
  ## AI Monitoring
  If you have version 8.12.0 or higher of Java agent, you can collect AI data from certain AI libraries and frameworks.
  <#list AI as key, value>
  * ${key} ${value.range}
  <#if value.hasDetails()>
  <#list value.details as detail>
    * ${detail}
  </#list>
  </#if>
  </#list>
</#if>

<#if Other??>
  ## Other instrumented features
  <#list Other as key, value>
  * ${key} ${value.range}
  <#if value.hasDetails()>
  <#list value.details as detail>
    * ${detail}
  </#list>
  </#if>
  </#list>
</#if>