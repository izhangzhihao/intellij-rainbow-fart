package com.github.izhangzhihao.rainbow.fart

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.izhangzhihao.rainbow.fart.BuildInContributes.buildInContributes
import com.github.izhangzhihao.rainbow.fart.settings.RainbowFartSettings
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.StartupActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay
import java.io.File
import java.time.Duration
import java.time.LocalDateTime


class ResourcesLoader : StartupActivity {

    override fun runActivity(project: Project) {
        val current =
                if (RainbowFartSettings.instance.customVoicePackage != "") {
                    File(RainbowFartSettings.instance.customVoicePackage + File.separator + "manifest.json").readText()
                } else {
                    ResourcesLoader::class.java.getResource("/build-in-voice-chinese/manifest.json").readText()
                }

        val mapper = jacksonObjectMapper()

        val manifest: Manifest = mapper.readValue(current)

        val contributes: List<Contribute> =
                if (manifest.contributes != null) {
                    manifest.contributes
                } else if (RainbowFartSettings.instance.customVoicePackage != "") {
                    val contText = File(RainbowFartSettings.instance.customVoicePackage + File.separator + "contributes.json").readText()
                    mapper.readValue<Contributes>(contText).contributes
                } else {
                    val contText = ResourcesLoader::class.java.getResource("/build-in-voice-chinese/contributes.json").readText()
                    mapper.readValue<Contributes>(contText).contributes
                }

        contributes.forEach {
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

data class Manifest(val name: String, @JsonProperty("display-name") val displayName: String,
                    val avatar: String, @JsonProperty("avatar-dark") val avatarDark: String,
                    val version: String, val description: String, val languages: List<String>,
                    val author: String, val gender: String, val locale: String, val contributes: List<Contribute>?)

data class Contribute(val keywords: List<String>, val voices: List<String>)

data class Contributes(val contributes: List<Contribute>)

object BuildInContributes {
    val buildInContributes = mutableMapOf<String, List<String>>()

    val buildInContributesSeq: Sequence<Map.Entry<String, List<String>>> by lazy { buildInContributes.asSequence() }
}