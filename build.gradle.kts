//this works

import ProjectVersions.openosrsVersion

buildscript {
    repositories {
        gradlePluginPortal()
    }
}

plugins {
    java //this enables annotationProcessor and implementation in dependencies
    checkstyle
}

project.extra["GithubUrl"] = "https://github.com/DragonTTK/ttk-plugins"

apply<BootstrapPlugin>()

allprojects {
    group = "com.openosrs.externals"
    apply<MavenPublishPlugin>()
}

allprojects {
    apply<MavenPublishPlugin>()

    repositories {
        mavenLocal()
        mavenCentral()
        jcenter()
    }
}

subprojects {
    group = "com.openosrs.externals"

    project.extra["PluginProvider"] = "MythicalZxc"
    project.extra["ProjectSupportUrl"] = "https://discord.com/invite/6j5gbekPjA"
    project.extra["PluginLicense"] = "3-Clause BSD License"

    repositories {
        jcenter {
            content {
                excludeGroupByRegex("com\\.openosrs.*")
            }
        }

        exclusiveContent {
            forRepository {
                mavenLocal()
            }
            filter {
                includeGroupByRegex("com\\.openosrs.*")
                includeGroupByRegex("com\\.owain.*")
            }
        }
    }

    apply<JavaPlugin>()

    dependencies {
        annotationProcessor(Libraries.lombok)
        annotationProcessor(Libraries.pf4j)
        annotationProcessor(Libraries.javax)

        compileOnly("com.openosrs:http-api:$openosrsVersion+")
        compileOnly("com.openosrs:runelite-api:$openosrsVersion+")
        compileOnly("com.openosrs:runelite-client:$openosrsVersion+")
        compileOnly("com.openosrs.rs:runescape-api:$openosrsVersion+")

        //compileOnly(group = "com.owain.externals", name = "chinbreakhandler", version = "0.0.2+")

        compileOnly(Libraries.findbugs)
        compileOnly(Libraries.apacheCommonsText)
        compileOnly(Libraries.gson)
        compileOnly(Libraries.guice)
        compileOnly(Libraries.lombok)
        compileOnly(Libraries.okhttp3)
        compileOnly(Libraries.pf4j)
        compileOnly(Libraries.rxjava)
        compileOnly(Libraries.javax)
    }

    configure<JavaPluginConvention> {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    configure<PublishingExtension> {
        repositories {
            maven {
                url = uri("$buildDir/repo")
            }
        }
        publications {
            register("mavenJava", MavenPublication::class) {
                from(components["java"])
            }
        }
    }

    tasks {
        withType<JavaCompile> {
            options.encoding = "UTF-8"
        }

        register<Copy>("copyDeps") {
            into("./build/deps/")
            from(configurations["runtimeClasspath"])
        }

        withType<Jar> {
            doLast {
                copy {
                    from("./build/libs/")
                    into(System.getProperty("user.home") + "/.openosrs/plugins")
                }
            }
        }

        withType<Jar> {
            doLast {
                copy {
                    from("./build/libs/")
                    into("../release/")
                }
            }
        }


        withType<AbstractArchiveTask> {
            isPreserveFileTimestamps = false
            isReproducibleFileOrder = true
            dirMode = 493
            fileMode = 420
        }
    }

}