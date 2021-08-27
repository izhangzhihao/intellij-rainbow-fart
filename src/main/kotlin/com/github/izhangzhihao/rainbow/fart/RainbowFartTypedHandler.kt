package com.github.izhangzhihao.rainbow.fart

import com.github.izhangzhihao.rainbow.fart.RainbowFartTypedHandler.FartTypedHandler.isTypingInsideComment
import com.github.izhangzhihao.rainbow.fart.RainbowFartTypedHandler.FartTypedHandler.releaseFart
import com.github.izhangzhihao.rainbow.fart.settings.RainbowFartSettings
import com.intellij.codeInsight.template.impl.editorActions.TypedActionHandlerBase
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.actionSystem.TypedActionHandler
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager
import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.util.elementType
import javazoom.jl.player.Player
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File


class RainbowFartTypedHandler(originalHandler: TypedActionHandler) : TypedActionHandlerBase(originalHandler) {

    private var candidates: MutableList<Char> = mutableListOf()

    override fun execute(editor: Editor, charTyped: Char, dataContext: DataContext) {
        try {
            if (!RainbowFartSettings.instance.isRainbowFartEnabled) {
                return
            }
            val project = editor.project

            if (project != null) {
                //val language = PsiUtilBase.getLanguageInEditor(editor, project)
                val virtualFile = FileDocumentManager.getInstance().getFile(editor.document)
                if (virtualFile != null) {
                    val file = PsiManager.getInstance(project).findFile(virtualFile)
                    if (file != null && isTypingInsideComment(editor, file)) {
                        return
                    }
                }
            }

            candidates.add(charTyped)
            val str = candidates.joinToString("")
            BuildInContributes.buildInContributesSeq
                .firstOrNull { (keyword, _) ->
                    str.contains(keyword, true)
                }?.let { (_, voices) ->
                    GlobalScope.launch(Dispatchers.Default) {
                        releaseFart(voices)
                    }
                    candidates.clear()
                }
            if (candidates.size > 20) {
                candidates = candidates.subList(10, candidates.size - 1)
            }
        } finally {
            // Ensure original handler is called no matter what errors are thrown, to prevent typing from being lost.
            try {
                this.myOriginalHandler?.execute(editor, charTyped, dataContext)
            } catch (e: Throwable) {
            }
        }
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
                    resolvePath(RainbowFartSettings.instance.customVoicePackage + File.separator + voices.random()).inputStream()
                } else {
                    FartTypedHandler::class.java.getResourceAsStream("/build-in-voice-chinese/" + voices.random())
                }
            val player = Player(mp3Stream)
            player.play()
            player.close()
        }

        fun isTypingInsideComment(editor: Editor, file: PsiFile): Boolean {
            val provider = file.viewProvider
            val offset = editor.caretModel.offset
            val elementAtCaret: PsiElement?
            elementAtCaret = if (offset < editor.document.textLength) {
                provider.findElementAt(offset)
            } else {
                provider.findElementAt(editor.document.textLength - 1)
            }
            var element = elementAtCaret
            while (element is PsiWhiteSpace) {
                element = element.getPrevSibling()
            }

            return element.elementType.toString().contains("comment", true)
//            if (element == null) {
//                return false
//            }
//            val node: ASTNode? = element.node
//            return node != null //&& JavaDocTokenType.ALL_JAVADOC_TOKENS.contains(node.getElementType())
        }
    }
}