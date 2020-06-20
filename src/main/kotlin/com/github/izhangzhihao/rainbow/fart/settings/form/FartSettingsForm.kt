package com.github.izhangzhihao.rainbow.fart.settings.form

import com.github.izhangzhihao.rainbow.fart.settings.FartSettings
import javax.swing.JCheckBox
import javax.swing.JComponent
import javax.swing.JPanel

class FartSettingsForm {
    private var panel: JPanel? = null
    private var appearancePanel: JPanel? = null
    private var enableRainbowFart: JCheckBox? = null


    private val settings: FartSettings = FartSettings.instance

    fun component(): JComponent? = panel

    fun isRainbowEnabled() = enableRainbowFart?.isSelected

    val isModified: Boolean
        get() = (isRainbowEnabled() != settings.isRainbowFartEnabled)

    init {
        loadSettings()
    }

    fun loadSettings() {
        enableRainbowFart?.isSelected = settings.isRainbowFartEnabled
    }
}