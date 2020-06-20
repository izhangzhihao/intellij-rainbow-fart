package com.github.izhangzhihao.rainbow.fart.settings.form

import com.github.izhangzhihao.rainbow.fart.settings.RainbowFartSettings
import javax.swing.JCheckBox
import javax.swing.JComponent
import javax.swing.JPanel

class RainbowFartSettingsForm {
    private var panel: JPanel? = null
    private var appearancePanel: JPanel? = null
    private var enableRainbowFart: JCheckBox? = null


    private val settingsRainbow: RainbowFartSettings = RainbowFartSettings.instance

    fun component(): JComponent? = panel

    fun isRainbowEnabled() = enableRainbowFart?.isSelected

    val isModified: Boolean
        get() = (isRainbowEnabled() != settingsRainbow.isRainbowFartEnabled)

    init {
        loadSettings()
    }

    fun loadSettings() {
        enableRainbowFart?.isSelected = settingsRainbow.isRainbowFartEnabled
    }
}