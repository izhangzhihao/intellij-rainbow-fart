package com.github.izhangzhihao.rainbow.fart

import com.intellij.codeInsight.template.impl.editorActions.TypedActionHandlerBase
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.actionSystem.TypedActionHandler
import javazoom.jl.player.Player
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FartTypedHandler(originalHandler: TypedActionHandler) : TypedActionHandlerBase(originalHandler) {

    private var candidate: MutableList<Char> = mutableListOf()

    override fun execute(editor: Editor, charTyped: Char, dataContext: DataContext) {
        candidate.add(charTyped)

        val str = candidate.joinToString("")

        //TODO: refactoring
        BuildInContributes.buildInContributes
                .forEach { (keyword, voices) ->
                    if (str.contains(keyword, true)) {
                        GlobalScope.launch(Dispatchers.Default) {
                            val mp3Stream = FartTypedHandler::class.java.getResourceAsStream("/build-in-voice-chinese/" + voices.random())
                            val player = Player(mp3Stream)
                            player.play()
                            player.close()
                        }
                        candidate.clear()
                    }
                }

        if (candidate.size > 100) {
            candidate = candidate.subList(90, candidate.size - 1)
        }
        this.myOriginalHandler?.execute(editor, charTyped, dataContext)
    }
}