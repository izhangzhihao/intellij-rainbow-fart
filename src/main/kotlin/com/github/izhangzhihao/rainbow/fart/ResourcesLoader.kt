package com.github.izhangzhihao.rainbow.fart

import com.github.izhangzhihao.rainbow.fart.BuildInContributes.buildInContributes
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.StartupActivity
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay
import java.time.Duration
import java.time.LocalDateTime


class ResourcesLoader : StartupActivity {

    override fun runActivity(project: Project) {
        val buildInJson = ResourcesLoader::class.java.getResource("/build-in-voice-chinese/contributes.json").readText()

        val moshi = Moshi.Builder().build()

        val jsonAdapter: JsonAdapter<Contributes> = moshi.adapter(Contributes::class.java)

        val contributes: Contributes? = jsonAdapter.fromJson(buildInJson)

        contributes?.contributes?.forEach {
            it.keywords.forEach { keyword ->
                when (keyword) {
                    "\$time_each_hour" -> GlobalScope.launch {
                        while (true) {
                            if (LocalDateTime.now().hour in 10..17 && LocalDateTime.now().hour !in 11..13) {
                                RainbowFartTypedHandler.FartTypedHandler.releaseFart(it.voices)
                                delay(Duration.ofHours(1))
                            }
                        }
                    }

                    "\$time_midnight" -> GlobalScope.launch {
                        while (true) {
                            if (LocalDateTime.now().hour > 20) {
                                RainbowFartTypedHandler.FartTypedHandler.releaseFart(it.voices)
                            }
                            delay(Duration.ofHours(1))
                        }
                    }

                    "\$time_evening" -> GlobalScope.launch {
                        while (true) {
                            if (LocalDateTime.now().hour in 18..20) {
                                RainbowFartTypedHandler.FartTypedHandler.releaseFart(it.voices)
                            }
                            delay(Duration.ofHours(1))
                        }
                    }

                    "\$time_noon" -> GlobalScope.launch {
                        while (true) {
                            if (LocalDateTime.now().hour == 12 && LocalDateTime.now().minute in 30..55) {
                                RainbowFartTypedHandler.FartTypedHandler.releaseFart(it.voices)
                            }
                            delay(Duration.ofMinutes(30))
                        }
                    }

                    "\$time_before_noon" -> GlobalScope.launch {
                        while (true) {
                            if (LocalDateTime.now().hour == 11 && LocalDateTime.now().minute in 30..55) {
                                RainbowFartTypedHandler.FartTypedHandler.releaseFart(it.voices)
                            }
                            delay(Duration.ofMinutes(30))
                        }
                    }

                    "\$time_morning" -> GlobalScope.launch {
                        while (true) {
                            if (LocalDateTime.now().hour in 8..9) {
                                RainbowFartTypedHandler.FartTypedHandler.releaseFart(it.voices)
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