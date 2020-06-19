package com.github.izhangzhihao.rainbow.fart

import com.github.izhangzhihao.rainbow.fart.BuildInContributes.buildInContributes
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.StartupActivity
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay
import java.time.Duration
import java.time.LocalDateTime


class ResourcesLoader : StartupActivity {

    private val coroutineScope = CoroutineScope(Dispatchers.Unconfined)


    override fun runActivity(project: Project) {
        val buildInJson = ResourcesLoader::class.java.getResource("/build-in-voice-chinese/contributes.json").readText()

        val moshi = Moshi.Builder().build()

        val jsonAdapter: JsonAdapter<Contributes> = moshi.adapter(Contributes::class.java)

        val contributes: Contributes? = jsonAdapter.fromJson(buildInJson)

        contributes?.contributes?.forEach {
            it.keywords.forEach { keyword ->
                when (keyword) {
                    "\$time_each_hour" -> coroutineScope.launch {
                        while (true) {
                            if (LocalDateTime.now().hour in 9..18 && LocalDateTime.now().hour !in 11..13) {
                                FartTypedHandler.FartTypedHandler.releaseFart(it.voices)
                                delay(Duration.ofHours(1))

                            }
                        }
                    }

                    "\$time_midnight" -> coroutineScope.launch {
                        while (true) {
                            if (LocalDateTime.now().hour > 20) {
                                FartTypedHandler.FartTypedHandler.releaseFart(it.voices)
                            }
                            delay(Duration.ofHours(1))
                        }
                    }

                    "\$time_evening" -> coroutineScope.launch {
                        while (true) {
                            if (LocalDateTime.now().hour in 18..20) {
                                FartTypedHandler.FartTypedHandler.releaseFart(it.voices)
                            }
                            delay(Duration.ofHours(1))
                        }
                    }

                    "\$time_noon" -> coroutineScope.launch {
                        while (true) {
                            if (LocalDateTime.now().hour in 12..13) {
                                FartTypedHandler.FartTypedHandler.releaseFart(it.voices)
                            }
                            delay(Duration.ofHours(1))
                        }
                    }

                    "\$time_before_noon" -> coroutineScope.launch {
                        while (true) {
                            if (LocalDateTime.now().hour in 11..12) {
                                FartTypedHandler.FartTypedHandler.releaseFart(it.voices)
                            }
                            delay(Duration.ofHours(1))
                        }
                    }

                    "\$time_morning" -> coroutineScope.launch {
                        while (true) {
                            if (LocalDateTime.now().hour in 8..9) {
                                FartTypedHandler.FartTypedHandler.releaseFart(it.voices)
                            }
                            delay(Duration.ofHours(1))
                        }
                    }

                    else -> {
                        buildInContributes[keyword] = it.voices
                    }
                }
            }
        }
    }
}


@JsonClass(generateAdapter = true)
data class Contribute(val keywords: List<String>, val voices: List<String>)

@JsonClass(generateAdapter = true)
data class Contributes(val contributes: List<Contribute>)

object BuildInContributes {
    val buildInContributes = mutableMapOf<String, List<String>>()

    val buildInContributesSeq: Sequence<Map.Entry<String, List<String>>> by lazy { buildInContributes.asSequence() }
}