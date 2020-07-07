
<!DOCTYPE htmlFile>
<htmlFile lang="en">
  <head>
    <meta charset="utf-8">
    <title>Compatibility and requirements for the Java agent</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="css/bootstrap.css" rel="stylesheet">
    <style type="text/css">
      body {
        color: #02303A;
        padding-top: 20px;
      padding-bottom: 40px;
      font-size: 16px;
      }

      .container-narrow {
        margin: 0 auto;
        max-width: 800px;
      }
      .container-narrow > hr {
        margin: 30px 0;
      }

      .jumbotron {
        margin: 20px 0;
        text-align: center;
      }

      h2, h3 {
        font-size: 24px;
        color: #1DA2BD;
      }
    </style>
    <link href="css/bootstrap-responsive.css" rel="stylesheet">

  </head>

  <body>

    <div class="container-narrow">

      <div class="masthead">
        <ul class="nav nav-pills pull-right">
          <li><a href="https://docs.newrelic.com/docs/agents/java-agent/getting-started/introduction-new-relic-java" id="website-link"><img src="img/newrelic-logo.png" height="185" width="185"></a></li>
        </ul>
        <h1>New Relic Java Agent Supportability</h1>
      </div>

      <hr>

      <div class="jumbotron">
        <p class="lead">
        Automatically instrumented frameworks and libraries
        </p>
      </div>

      <hr>

      <div>
        <h2>App/Web servers</h2>
	<p>The agent automatically instruments the following app/web servers. To install the Java agent on supported app/web servers, see Install the Java agent.</p>
	<br>
          <table class="table table-striped">
              <tr>
                  <th>Application Servers</th>
                  <th>Minimum Version</th>
		  <th>Maximum Version</th>
	      </tr>
              <#if Appserver??>
              <#list Appserver as key, value>
	      <tr>
                  <td>${key}</td>
                  <td>${value?keep_before("to")}</td>
		  <td>${value?keep_after("to")}</td>
	      </tr>
          </#list>
          </#if>
	  </table>
       </div>

      <hr>

      <div>
        <h2>HTTP and messaging</h2>
	<p>The agent automatically instruments the following HTTP clients and messaging services. For instructions, see Install the Java agent.</p>
	<br>
          <table class="table table-striped">
              <tr>
                  <th>HTTP and Messaging Clients</th>
                  <th>Minimum Version</th>
		  <th>Maximum Version</th>
	      </tr>
              <#if Messaging??>
              <#list Messaging as key, value>
	      <tr>
                  <td>${key}</td>
                  <td>${value?keep_before("to")}</td>
		  <td>${value?keep_after("to")}</td>
	      </tr>
          </#list>
          </#if>
	  </table>
      </div>

      <hr>

      <div>
        <h2>Datastores</h2>
	<p>New Relic currently supports MySQL and PostgreSQL to capture explain plans for slow database queries.</p>
	<br>
          <table class="table table-striped">
              <tr>
                  <th>Datastore Clients</th>
                  <th>Minimum Version</th>
		  <th>Maximum Version</th>
	      </tr>
              <#if Datastore??>
              <#list Datastore as key, value>
	      <tr>
                  <td>${key}</td>
                  <td>${value?keep_before("to")}</td>
		  <td>${value?keep_after("to")}</td>
	      </tr>
          </#list>
          </#if>
	  </table>
      </div>

      <hr>
      <div>
        <h2>Other instrumented features</h2>
	<br>
          <table class="table table-striped">
              <tr>
                  <th>Other</th>
                  <th>Minimum Version</th>
		  <th>Maximum Version</th>
	      </tr>
              <#if Other??>
              <#list Other as key, value>
	      <tr>
                  <td>${key}</td>
                  <td>${value?keep_before("to")}</td>
		  <td>${value?keep_after("to")}</td>
	      </tr>
          </#list>
          </#if>
	  </table>
      </div>

      <hr>

      <div class="footer">
        <#assign dateTime = .now>
        <div class="pull-right">Updated: ${dateTime?string("yyyy-MM-dd HH:mm:ss")}</div>
      </div>
    </div>

  </body>
</htmlFile>
