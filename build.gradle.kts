repositories {
    mavenCentral()
}

plugins {
    id("groovy")
    id("java-gradle-plugin")
    id("maven-publish")
    id("signing")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
    withSourcesJar()
    withJavadocJar()
}

dependencies {
    implementation("org.codehaus.groovy:groovy:2.5.14")
    implementation("org.freemarker:freemarker:2.3.30")
    implementation("com.googlecode.json-simple:json-simple:1.1.1")
    implementation("org.apache.maven:maven-resolver-provider:3.6.1")
}

group = "com.newrelic.agent.java"

// -Prelease=true will render a non-snapshot version
// All other values (including unset) will render a snapshot version.
val release: String? by project
version = "1.1" + if("true" == release) "" else "-SNAPSHOT"

tasks.jar {
    from ("LICENSE")
    manifest {
        attributes(mapOf(
                "Implementation-Vendor" to "New Relic, Inc",
                "Implementation-Version" to project.version
        ))
    }
}

gradlePlugin {
    plugins {
        create("gradle-compatibility-doc-plugin") {
            id = "com.newrelic.gradle-compatibility-doc-plugin"
            implementationClass = "com.newrelic.agent.instrumentation.compatibility.CompatibilitySitePlugin"
        }
    }
}

publishing {
    repositories {
        maven {
            val releasesRepoUrl = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
            val snapshotsRepoUrl = uri("https://oss.sonatype.org/content/repositories/snapshots/")
            url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
            credentials {
                username = System.getenv("SONATYPE_USERNAME")
                password = System.getenv("SONATYPE_PASSWORD")
            }
        }
    }
    // afterEvaluate is necessary because java-gradle-plugin
    // creates its publications in an afterEvaluate callback
    afterEvaluate {
        publications {
            register("mavenJava", MavenPublication::class) {
                from(components["java"])
            }
            // customize all publications here
            withType(MavenPublication::class) {
                pom {
                    name.set(project.name)
                    description.set("Gradle plugin for generating compatibility documents from instrumentation modules.")
                    url.set("https://github.com/newrelic/newrelic-gradle-compatibility-doc-plugin")
                    licenses {
                        license {
                            name.set("The Apache License, Version 2.0")
                            url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                            distribution.set("repo")
                        }
                    }
                    developers {
                        developer {
                            id.set("newrelic")
                            name.set("New Relic")
                            email.set("opensource@newrelic.com")
                        }
                    }
                    scm {
                        url.set("git@github.com:newrelic/newrelic-gradle-compatibility-doc-plugin.git")
                        connection.set("scm:git:git@github.com:newrelic/newrelic-gradle-compatibility-doc-plugin.git")
                    }
                }
            }
        }
    }
}

afterEvaluate {
    signing {
        val signingKeyId: String? by project
        val signingKey: String? by project
        val signingPassword: String? by project
        useInMemoryPgpKeys(signingKeyId, signingKey, signingPassword)
        sign(publishing.publications["gradle-compatibility-doc-pluginPluginMarkerMaven"])
        sign(publishing.publications["mavenJava"])
    }
}
