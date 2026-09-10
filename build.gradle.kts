plugins {
    id("com.android.application") version "9.0.0" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.2.10" apply false
    id("org.owasp.dependencycheck") version "12.2.2" apply false // 13.0.0 rejects keyless NVD updates (dependency-check#8715)
}

// Build output under root/builds/ with a subdirectory per module (e.g. builds/app/)
layout.buildDirectory.set(layout.projectDirectory.dir("builds"))
subprojects {
    layout.buildDirectory.set(rootProject.layout.buildDirectory.dir(project.name))
}
