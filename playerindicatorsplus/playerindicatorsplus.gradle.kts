import ProjectVersions.openosrsVersion

version = "1.0.6"

project.extra["PluginName"] = "Player Indicators Plus"
project.extra["PluginDescription"] = "Additional features for PI"
project.extra["ProjectSupportUrl"] = "https://github.com/DragonTTK/ttk-plugins/"

dependencies {
    annotationProcessor(Libraries.lombok)
    annotationProcessor(Libraries.pf4j)

    compileOnly("com.openosrs:runelite-api:$openosrsVersion+")
    compileOnly("com.openosrs:runelite-client:$openosrsVersion+")
    //compileOnly(group = "com.openosrs.externals", name = "iutils", version = "4.7.7+");

    compileOnly(Libraries.guice)
    compileOnly(Libraries.lombok)
    compileOnly(Libraries.pf4j)
}

tasks {
    jar {
        manifest {
            attributes(mapOf(
                    "Plugin-Version" to project.version,
                    "Plugin-Id" to nameToId(project.extra["PluginName"] as String),
                    "Plugin-Provider" to project.extra["PluginProvider"],
                    "Plugin-Description" to project.extra["PluginDescription"],
                    "Plugin-License" to project.extra["PluginLicense"]
            ))
        }
    }
}
