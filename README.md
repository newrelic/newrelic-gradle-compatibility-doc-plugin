<a href="https://opensource.newrelic.com/oss-category/#community-project"><picture><source media="(prefers-color-scheme: dark)" srcset="https://github.com/newrelic/opensource-website/raw/main/src/images/categories/dark/Community_Project.png"><source media="(prefers-color-scheme: light)" srcset="https://github.com/newrelic/opensource-website/raw/main/src/images/categories/Community_Project.png"><img alt="New Relic Open Source community project banner." src="https://github.com/newrelic/opensource-website/raw/main/src/images/categories/Community_Project.png"></picture></a>

gradle-compatibility-doc-plugin
====================================
This plugin provides support for `generateCompatibilitySite` DSL in your gradle build files. Using maven dependency strings, you can quickly define what ranges of target libraries are supported by the agent's builtin instrumentation.

:warning: This plugin has a very niche use case for the New Relic Java agent. 
It is not intended to be used or modified for any other environment.

Open source license
====================================

This project is distributed under the Apache 2 license.

What do you need to make this work?
====================================

Required: 
* Gradle, minimum 7.5.1

Start using the plugin
====================================

To use the plugin, update your buildscript dependencies in settings.gradle:

```gradle
pluginManagement {
    repositories {
      mavenCentral()
      gradlePluginPortal()
    }
  }
```

And reference it in `build.gradle`:

```gradle
buildscript {
    dependencies {
        classpath "com.newrelic.agent.java:gradle-compatibility-doc-plugin:1.1"
    }
}

apply plugin "com.newrelic.gradle-compatibility-doc-plugin"
```

Or simply:

```gradle
 plugins {
   id("com.newrelic.gradle-compatibility-doc-plugin") version "1.1"
 }
```

**Note** For instrumentation bundled with the New Relic Java agent, this is already configured and these steps are not required.

Configuring the plugin
====================================

```gradle
site {
    title 'Anorm'
    type 'Datastore'
    versionOverride '[1.0,)'
}
```

Note: `versionOverride` is optional, otherwise the versions are picked up from the `verifyInstrumentation` block in the instrumentation gradle file.

### Type options:
* 'Datastore'
* 'Framework'
* 'Appserver'
* 'Messaging'
* 'Other'

Running the plugin
====================================

Run the plugin with the generateCompatibilitySite task.

To generate an html page including all instrumentation:

```bash
.../java_agent/$ ./gradlew generateCompatibilitySite
```

Development
====================================

To test the plugin locally, first publish it to your local repository of Maven.
  
After cloning, run the following command in the root of this project:
 
 ```bash
./gradlew publishToMavenLocal
 ```
  

Then update your buildscript dependencies in settings.gradle:

```gradle
pluginManagement {
    repositories {
      mavenLocal()
      mavenCentral()
      gradlePluginPortal()
    }
  }
```

This should pick up your local version of the plugin.

Support
====================================

New Relic has open-sourced this project. This project is provided AS-IS WITHOUT WARRANTY OR DEDICATED SUPPORT. Report issues and contributions to the project here on GitHub.

We encourage you to bring your experiences and questions to the [Explorers Hub](https://forum.newrelic.com/s/) where our community members collaborate on solutions and new ideas.

Community
====================================
New Relic hosts and moderates an online forum where customers can interact with New Relic employees as well as other customers to get help and share best practices. Like all official New Relic open source projects, there's a related Community topic in the New Relic Explorers Hub. You can find this project's topic/threads here:

[Java Agent Forum Topics](https://forum.newrelic.com/s/?c__categories=%5B%7B%22id%22%3A%22a6c8W000000EesiQAC%22%2C%22isCustomImage%22%3Afalse%2C%22sObjectType%22%3A%22Category__c%22%2C%22subtitle%22%3A%22%22%2C%22title%22%3A%22Java%20Agent%22%2C%22titleFormatted%22%3A%22%3Cstrong%3EJa%3C%2Fstrong%3Eva%20Agent%22%2C%22subtitleFormatted%22%3A%22%22%2C%22icon%22%3A%22standard%3Adefault%22%7D%5D)

Issues / enhancement requests
====================================
Issues and enhancement requests can be submitted in 
the [issues tab of this repository.](https://github.com/newrelic/newrelic-gradle-verify-instrumentation/issues) 
Please search for and review the existing open issues before submitting a new issue.

Contributing
====================================
We encourage your contributions to improve this project! Keep in mind when you submit your pull request, you'll need to sign the CLA via the click-through using CLA-Assistant. You only have to sign the CLA one time per project. If you have any questions, or to execute our corporate CLA, required if your contribution is on behalf of a company, please drop us an email at opensource@newrelic.com.

**A note about vulnerabilities**

As noted in our [security policy](../../security/policy), New Relic is committed to the privacy and security of our customers and their data. We believe that providing coordinated disclosure by security researchers and engaging with the security community are important means to achieve our security goals.

If you believe you have found a security vulnerability in this project or any of New Relic's products or websites, we welcome and greatly appreciate you reporting it to New Relic through [HackerOne](https://hackerone.com/newrelic).
