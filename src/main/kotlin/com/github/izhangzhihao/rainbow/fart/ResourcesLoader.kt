package com.github.izhangzhihao.rainbow.fart

import com.github.izhangzhihao.rainbow.fart.BuildInContributes.buildInContributes
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.StartupActivity
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi


class ResourcesLoader : StartupActivity {
    override fun runActivity(project: Project) {
        val buildInJson = ResourcesLoader::class.java.getResource("/build-in-voice-chinese/contributes.json").readText()

        val moshi = Moshi.Builder().build()

        val jsonAdapter: JsonAdapter<Contributes> = moshi.adapter(Contributes::class.java)

        val contributes: Contributes? = jsonAdapter.fromJson(buildInJson)

        contributes?.contributes?.forEach {
            it.keywords.forEach { keyword ->
                buildInContributes[keyword] = it.voices
            }
        }

        println(buildInContributes)
    }
}


@JsonClass(generateAdapter = true)
data class Contribute(val keywords: List<String>, val voices: List<String>)

@JsonClass(generateAdapter = true)
data class Contributes(val contributes: List<Contribute>)

object BuildInContributes {
    val buildInContributes = mutableMapOf<String, List<String>>()
}