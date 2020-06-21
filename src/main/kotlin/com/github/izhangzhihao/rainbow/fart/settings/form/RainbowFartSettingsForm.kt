package com.github.izhangzhihao.rainbow.fart.settings.form

import com.github.izhangzhihao.rainbow.fart.settings.RainbowFartSettings
import javax.swing.JCheckBox
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JTextField

class RainbowFartSettingsForm {
    private var panel: JPanel? = null
    private var appearancePanel: JPanel? = null
    private var enableRainbowFart: JCheckBox? = null
    private var customVoicePackage: JTextField? = null

    private val settings: RainbowFartSettings = RainbowFartSettings.instance

    fun component(): JComponent? = panel

    fun isRainbowEnabled() = enableRainbowFart?.isSelected

    fun customVoicePackage() = customVoicePackage?.text

    val isModified: Boolean
        get() = (isRainbowEnabled() != settings.isRainbowFartEnabled
                || customVoicePackage() != settings.customVoicePackage)

    init {
        loadSettings()
    }

    fun loadSettings() {
        enableRainbowFart?.isSelected = settings.isRainbowFartEnabled
        customVoicePackage?.text = settings.customVoicePackage
    }
}