import org.apache.tools.ant.filters.ReplaceTokens

plugins {
    id("com.gradle.build-scan") version "2.3"
    id("org.gradle.guides.getting-started") version "0.15.5"
    id("org.gradle.guides.test-jvm-code") version "0.15.5"
    id("org.gradle.guides.ci.travis") version "0.15.5"
}

repositories {
    maven {
        url = uri("https://repo.gradle.org/gradle/libs")
    }
}

dependencies {
    testImplementation("org.gradle:sample-check:0.7.0")
    testImplementation(gradleTestKit())
}

guide {
    repoPath = "gradle-guides/creating-build-scans"
}

buildScan {
    termsOfServiceUrl = "https://gradle.com/terms-of-service"
    termsOfServiceAgree = "yes"
}

tasks {
    val preProcessSamples by registering(Copy::class) {
        into("$buildDir/samples")
        from("samples")
        val tokens = mapOf("scanPluginVersion" to resolveLatestBuildScanPluginVersion())
        filter<ReplaceTokens>("tokens" to tokens)
    }

    asciidoctor {
        dependsOn(preProcessSamples)
        attributes.putAll(mapOf(
            "samplescodedir" to project.file("build/samples/code").absolutePath
        ))
    }

    test {
        dependsOn(preProcessSamples)
        systemProperty("samplesDir", "$buildDir/samples")
    }
}

configure<org.gradle.guides.ci.TravisExtension> {
    encryptedVariables.add("l2OsL534+NgIRu9OpK3ToF3xgRvC0qcyB7ry0JFShVxx2SK0s2gz//zleKciW8Ln1BeTXSKV3m2tSGRuwgb6Mnh33987BHxI6+gmOwOnSEwmgis4MKiXBXHkiLtZzIysIP/QReAVIQ/jYgfBwhQ9cWN2DEeAUdT30CjW4Bt+jo7cthVt3cuD24U+L8ESSRIQnS9feWQxi4olfCdoa7LxYEY1NnJZTY6ub8nJag/soWMCi4+MuH3e25W+g2xe967Cxpm05n7kXU0+4jUwgnP0erDPpbP0USVXWc1lfaYuq8JgTYmLcHnahIPEHWXPHCQj0JUfkhokDsLWZLe1hSDZfRG3NcjKY7L1+UawcnYOZkrR1qBdCMuN2wiFsV1C+pKba4IOKG/SGI+wFB5JZs7PHcBCSm1GgyLkQqQCC0E93TUZabqjeNWckKaS5SSNN8sjybSAnHw17OYfwivKiEFkJAVXT0wwGFk1zRxknm4eNTSJ8z3pwbrvus0WNqVPNyGoRwG47Nrs2vVcCQBLhIML4pOD9aqJNWS7shRIMLlWPR8L9SnuAjBm79QKhAoN3PePT9kdOWDjh4dXOgGHnovsignkyWtr8c+/eWp7Herv5XDYahRWtH+lRw97V7UnDyAOl2r3Tdv1ln56YpLvcb89yzKg5Fhei+3U4Ig2OBiwtfk=")
}