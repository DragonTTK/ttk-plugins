version = "1.0.2"

project.extra["PluginName"] = "myplugin123"
project.extra["PluginDescription"] = "Disable animation of django claws"
project.extra["ProjectSupportUrl"] = "https://discord.com/invite/6j5gbekPjA"

tasks {
    jar {
        manifest {
            attributes(
                mapOf(
                    "Plugin-Version" to project.version,
                    "Plugin-Id" to nameToId(project.extra["PluginName"] as String),
                    "Plugin-Provider" to project.extra["PluginProvider"],
                    "Plugin-Description" to project.extra["PluginDescription"],
                    "Plugin-License" to project.extra["PluginLicense"]
                )
            )
        }
    }
}
