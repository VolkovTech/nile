import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	kotlin("jvm") version "1.4.21" apply false
	kotlin("plugin.spring") version "1.4.21" apply false
}

allprojects {
	group = "tech.volkov"
	version = "0.0.1-SNAPSHOT"

	tasks.withType<JavaCompile> {
		sourceCompatibility = "1.8"
		targetCompatibility = "1.8"
	}

	tasks.withType<KotlinCompile> {
		kotlinOptions {
			freeCompilerArgs = listOf("-Xjsr305=strict")
			jvmTarget = "1.8"
		}
	}
}

subprojects {
	repositories {
		mavenCentral()
	}

	tasks.withType<Test> {
		useJUnitPlatform()
	}
}
