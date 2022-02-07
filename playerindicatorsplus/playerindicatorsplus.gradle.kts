version = "1.0.0"

project.extra["PluginName"] = "Player Indicators Plus"
project.extra["PluginDescription"] = "Additional options for player indications."
project.extra["ProjectSupportUrl"] = "https://github.com/DragonTTK/ttk-plugins/"

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
