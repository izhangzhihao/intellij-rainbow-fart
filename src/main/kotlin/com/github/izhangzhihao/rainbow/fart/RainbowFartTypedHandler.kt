package com.github.izhangzhihao.rainbow.fart

import com.github.izhangzhihao.rainbow.fart.RainbowFartTypedHandler.FartTypedHandler.releaseFart
import com.github.izhangzhihao.rainbow.fart.settings.RainbowFartSettings
import com.intellij.codeInsight.template.impl.editorActions.TypedActionHandlerBase
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.actionSystem.TypedActionHandler
import javazoom.jl.player.Player
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File

class RainbowFartTypedHandler(originalHandler: TypedActionHandler) : TypedActionHandlerBase(originalHandler) {

    private var candidates: MutableList<Char> = mutableListOf()

    override fun execute(editor: Editor, charTyped: Char, dataContext: DataContext) {
        if (!RainbowFartSettings.instance.isRainbowFartEnabled) {
            this.myOriginalHandler?.execute(editor, charTyped, dataContext)
            return
        }
        candidates.add(charTyped)

        val str = candidates.joinToString("")

        BuildInContributes.buildInContributesSeq
                .firstOrNull { (keyword, _) ->
                    str.contains(keyword, true)
                }?.let { (_, voices) ->
                    GlobalScope.launch {
                        releaseFart(voices)
                    }
                    candidates.clear()
                }
        if (candidates.size > 20) {
            candidates = candidates.subList(10, candidates.size - 1)
        }
        this.myOriginalHandler?.execute(editor, charTyped, dataContext)
    }

    object FartTypedHandler {

        private var playing = false

        fun releaseFart(voices: List<String>) {
            if (RainbowFartSettings.instance.isRainbowFartEnabled && !playing) {
                playing = true
                playVoice(voices)
                playing = false
            }
        }

        private fun playVoice(voices: List<String>) {
            val mp3Stream =
                    if (RainbowFartSettings.instance.customVoicePackage != "") {
                        File(RainbowFartSettings.instance.customVoicePackage + File.separator + voices.random()).inputStream()
                    } else {
                        FartTypedHandler::class.java.getResourceAsStream("/build-in-voice-chinese/" + voices.random())
                    }
            val player = Player(mp3Stream)
            player.play()
            player.close()
        }
    }
}