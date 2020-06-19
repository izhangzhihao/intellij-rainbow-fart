package com.github.izhangzhihao.rainbow.fart

import com.intellij.codeInsight.template.impl.editorActions.TypedActionHandlerBase
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.actionSystem.TypedActionHandler


class FartTypedHandler(originalHandler: TypedActionHandler) : TypedActionHandlerBase(originalHandler) {
    override fun execute(editor: Editor, charTyped: Char, dataContext: DataContext) {
        println(charTyped)
        this.myOriginalHandler?.execute(editor, charTyped, dataContext)
    }
}