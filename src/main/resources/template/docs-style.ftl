<p><a href="/docs/agents/java-agent/getting-started/introduction-new-relic-java">New Relic's Java agent</a> includes built-in instrumentation of the most
    popular parts of the Java ecosystem, including app servers, frameworks, databases, and message queuing systems. For frameworks and libraries that are not
    instrumented out of the box, you can extend the agent with <a href="/docs/agents/java-agent/custom-instrumentation/java-custom-instrumentation">Java custom
        instrumentation</a>.</p>

<h2 id="java-requirements">Requirements to install the agent</h2>

<p>Before you install the Java agent, ensure your system meets these requirements:</p>

<dl class="clamshell-list">
    <dt id="jvm">JVM</dt>
    <dd>
        <p>Fully supported:</p>

        <ul>
            <li>IBM JVM versions 7 and 8 for Linux</li>
            <li>OpenJDK JVM versions 7 to 12 for Linux, Windows, and OS X</li>
            <li>Oracle Hotspot JVM versions 7 to 12 for Linux, Solaris, Windows, and OS X</li>
            <li>Amazon Coretto JVM versions 8 for Linux, Windows, and OS X</li>
            <li>Azul Zulu JVM versions 8 to 12 for Linux, Windows, and OS X</li>
        </ul>

        <p>Supported only with <a href="">Java agent 4.3.x</a> <span class="fileinfo">[ZIP | 2.8 MB]</span> legacy agent:</p>

        <ul>
            <li>Apple Hotspot JVM version 6 for OS X</li>
            <li>IBM JVM version 6</li>
            <li>Oracle Hotspot JVM version 6.0 for Linux, Solaris, Windows, OS X</li>
        </ul>

        <p>Supported only with <a href="https://download.newrelic.com/newrelic/java-agent/newrelic-agent/2.21.7/newrelic-java-2.21.7.zip">Java agent 2.21.x</a>
            <span class="fileinfo">[ZIP | 2.8 MB]</span> legacy agent:</p>

        <ul>
            <li>Oracle Hotspot JVM version 5.0 for Linux, Solaris, Windows, OS X (<a href="/docs/java/java-se-5">Java SE 5.0</a>)</li>
            <li>Oracle JRockit up to and including 1.6.0_50</li>
        </ul>
    </dd>
    <dt id="security-requirements">Security requirements</dt>
    <dd>
        <p>As a standard <a href="/docs/accounts-partnerships/accounts/security/data-security">security measure for data collection</a>, your app server must
            support SHA-2 (256-bit). SHA-1 is not supported.</p>
    </dd>
</dl>

<h2 id="auto-instrumented">Automatically instrumented frameworks and libraries</h2>

<p>After you <a href="/docs/agents/java-agent/installation/install-java-agent">install the Java agent</a>, it automatically instruments many popular frameworks
    and libraries. With automatic instrumentation, the agent collects rich data out of the box, and data will show up in your New Relic dashboards within
    minutes of installation. Even if your library is not automatically instrumented, you can still collect data with <a
            href="/docs/agents/java-agent/custom-instrumentation/java-custom-instrumentation">custom instrumentation</a> and the <a
            href="/docs/agents/java-agent/api-guides/guide-using-java-agent-api">Java agent API</a>.</p>

<p>The agent automatically instruments these frameworks and libraries:</p>

<dl class="clamshell-list">
    <dt id="app-web-servers">App/Web servers</dt>
    <dd>
        <p>The agent automatically instruments the following app/web servers. To install the Java agent on supported app/web servers, see <a
                    href="/docs/agents/java-agent/installation/java-agent-manual-installation">Install the Java agent</a>.</p>
        <!-- Keep this list in order of relevance-->

        <ul>
            <li> ColdFusion 10</li>
            <li> JBoss EAP 6.0 to latest</li>
            <li> TomEE 1.5 to latest</li>
            <#if Appserver??>
            <#list Appserver as key, value>
            <li>${key} from ${value} </li>
                </#list>
                </#if>
        </ul>
    </dd>
    <dt id="frameworks">Frameworks and libraries</dt>
    <dd>
        <p>The agent automatically instruments the following frameworks. To install the Java agent on supported frameworks, see <a
                    href="/docs/agents/java-agent/installation/java-agent-manual-installation">Install the Java agent</a>.</p>
        <!-- Keep this list in order of relevance-->

        <ul>
            <li> JAX-RS 1.0 to latest </li>
            <li> JSF (Java Server Faces) </li>
            <li> Quartz Job Scheduler 1.8.3 to 2.2.x </li>
            <li> Spring Boot 1.4 to latest </li>
            <li> Struts 2.0.5 to latest </li>
            <#if Framework??>
            <#list Framework as key, value>
            <li> ${key} from ${value} </li>
                </#list>
                </#if>
        </ul>
    </dd>
    <dt id="http-messaging">HTTP and messaging</dt>
    <dd>
        <p>The agent automatically instruments the following HTTP clients and messaging services. For instructions, see <a
                    href="/docs/agents/java-agent/installation/java-agent-manual-installation">Install the Java agent</a>.</p>

        <ul>
            <li> HttpURLConnection (java.net) </li>
            <#if Messaging??>
            <#list Messaging as key, value>
            <li> ${key} from ${value} </li>
                </#list>
                </#if>
        </ul>
    </dd>
    <dt id="JDBC">Datastores</dt>
    <dd>
        <p>New Relic currently supports MySQL and PostgreSQL to capture explain plans for slow database queries.</p>

        <ul>
        <li> Generic JDBC (any JDBC compliant driver) </li>
            <#if Datastore??>
            <#list Datastore as key, value>
            <li>${key} from ${value} </li>
                </#list>
                </#if>
        </ul>
    </dd>
    <dt id="instance-level-db">Instance-level database information</dt>
    <dd>
        <p>New Relic collects <a href="/docs/apm/applications-menu/features/analyze-database-instance-level-performance-issues">instance details for a variety
                of databases and database drivers</a>. The ability to view specific instances and the types of database information in New Relic APM depends on
            your New Relic agent version.</p>

        <p>New Relic's Java agent <a href="/docs/release-notes/agent-release-notes/java-release-notes/java-agent-3330">versions 3.33.0 or higher</a> support the
            following:</p>

        <ul>
            <li>Any <a href="#JDBC">compatible JDBC driver</a></li>
            <li>Amazon DynamoDB</li>
            <li>DataStax Cassandra driver</li>
            <li>Jedis Redis driver</li>
            <li>Mongo</li>
            <li>Spymemcached</li>
        </ul>

        <p><b>Exception:</b> Instance-level information is not reported for calls to the <code>getBulk()</code> API method.</p>

        <p>The Java agent reports the database name and database server/identifier attributes on slow query traces and transaction traces for these database
            drivers. To request instance-level information from additional datastores, get support at <a href="https://support.newrelic.com">support.newrelic.com</a>.
        </p>
    </dd>
    <dt id="hosting-services">Hosting services</dt>
    <dd><span style="font-size: 12pt;">You can install the Java agent on a variety of hosting services, including ones not listed below. Here are detailed installation guides for particular hosting services:</span>
        <ul>
            <li><a href="/docs/agents/java-agent/additional-installation/google-app-engine-flexible-installation-java#tomcat-example">Google App Engine (GAE)
                    flexible environment</a></li>
            <li><a href="/docs/agents/java-agent/heroku/java-agent-heroku">Heroku</a></li>
        </ul>
    </dd>
    <dt id="async">Asynchronous instrumentation</dt>
    <dd>
        <p>For supported frameworks, the Java agent <a href="/docs/agents/java-agent/async-instrumentation/asynchronous-applications-monitoring-considerations">usually
                instruments async work automatically</a>. However, you can use the Java agent API to <a
                    href="/docs/agents/java-agent/async-instrumentation/java-agent-api-asynchronous-applications">extend this instrumentation</a>.</p>
    </dd>
    <dt id="other">Other instrumented features</dt>
    <dd>
        <ul>
            <#if Other??>
            <#list Other as key, value>
            <li>${key} from ${value}
                </#list>
                </#if>
        </ul>
    </dd>
</dl>

<h2 id="digital-intelligence-platform">Connect the agent to other New Relic products</h2>

<p>The Java agent integrates with other New Relic products to give you end-to-end visibility:</p>

<table class="alternate">
    <thead>
    <tr>
        <th style="width:200px">Product</th>
        <th>Integration</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td><a href="/docs/browser/new-relic-browser/getting-started/introduction-new-relic-browser">New Relic Browser</a></td>
        <td>The Java agent automatically injects the Browser JavaScript agent when you <a
                    href="/docs/browser/new-relic-browser/installation/install-new-relic-browser-agent#select-apm-app">enable auto-instrumentation</a>. After
            enabling Browser injection, you can view Browser data in the <a href="/docs/apm/applications-menu/monitoring/apm-overview-page">APM Overview
                page</a> and quickly switch between the APM and Browser data for a particular app. For configuration options and manual instrumentation, see <a
                    href="/docs/agents/java-agent/instrumentation/page-load-timing-java">New Relic Browser and the Java agent</a>.
        </td>
    </tr>
    <tr>
        <td><a href="/docs/infrastructure/new-relic-infrastructure/getting-started/introduction-new-relic-infrastructure">New Relic Infrastructure</a></td>
        <td>When you install the Infrastructure and APM agents on the same host, they automatically detect one another. You can then view a list of hosts in the
            APM UI, and filter your Infrastructure hosts by APM app in the Infrastructure UI. For more information, see <a
                    href="/docs/infrastructure/new-relic-infrastructure/data-instrumentation/new-relic-apm-data-infrastructure">New Relic APM data in
                Infrastructure</a>.
        </td>
    </tr>
    <tr>
        <td><a href="/docs/insights/use-insights-ui/getting-started/introduction-new-relic-insights">New Relic Insights</a></td>
        <td>The Java agent sends <a href="/docs/insights/insights-data-sources/default-events-attributes/apm-default-event-attributes">default events and
                attributes to Insights</a> for NRQL queries. You can also <a
                    href="/docs/insights/insights-data-sources/custom-data/insert-custom-events-new-relic-apm-agents">record custom events</a> for advanced
            analysis.
        </td>
    </tr>
    <tr>
        <td><a href="/docs/synthetics/new-relic-synthetics/getting-started/introduction-new-relic-synthetics">New Relic Synthetics</a></td>
        <td><a href="/docs/synthetics/new-relic-synthetics/using-monitors/collect-synthetic-transaction-traces">Synthetic transaction traces</a> connect
            requests from Synthetics monitors to the underlying APM transaction.
        </td>
    </tr>
    </tbody>
</table>

<h2 id="more_help">For more help</h2>

<p>Additional documentation resources include:</p>

<ul>
    <li><a href="/docs/agents/java-agent/installation/java-agent-self-installer">Java agent self-installer</a> (how to install the New Relic Java agent)</li>
    <li><a href="/docs/agents/java-agent/installation/upgrading-java-agent">Upgrading the Java agent</a> (how to upgrade New Relic for Java)</li>
    <li><a href="/docs/agents/java-agent/troubleshooting/no-data-appears-java">Troubleshooting: No data appears</a> (what to do if your Java application isn't
        reporting data)
    </li>
    <li><a href="/docs/agents/java-agent/instrumentation/naming-web-transactions">Web transaction naming</a> (how transactions are named)</li>
    <li><a href="/docs/agents/java-agent/custom-instrumentation/java-custom-instrumentation">Custom instrumentation for Java</a> (implement custom
        instrumentation for unsupported frameworks)
    </li>
</ul>
