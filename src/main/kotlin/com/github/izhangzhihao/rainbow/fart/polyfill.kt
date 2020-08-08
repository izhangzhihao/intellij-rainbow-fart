package com.github.izhangzhihao.util

import com.intellij.psi.PsiElement
import com.intellij.psi.tree.IElementType
import com.intellij.psi.util.PsiUtilCore

val PsiElement?.elementType: IElementType?
  get() = PsiUtilCore.getElementType(this)