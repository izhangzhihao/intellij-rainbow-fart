package com.github.izhangzhihao.rainbow.fart.settings

import com.github.izhangzhihao.rainbow.fart.settings.form.RainbowFartSettingsForm
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.options.ConfigurationException
import org.jetbrains.annotations.Nls
import javax.swing.JComponent

class RainbowFartConfigurable : Configurable {
    var settingsFormRainbow: RainbowFartSettingsForm? = null

    override fun createComponent(): JComponent? {
        settingsFormRainbow = settingsFormRainbow ?: RainbowFartSettingsForm()
        return settingsFormRainbow?.component()
    }

    override fun isModified(): Boolean {
        return settingsFormRainbow?.isModified ?: return false
    }

    @Throws(ConfigurationException::class)
    override fun apply() {
        val settings = RainbowFartSettings.instance
        settings.isRainbowFartEnabled = settingsFormRainbow?.isRainbowEnabled() ?: true
        settings.customVoicePackage = settingsFormRainbow?.customVoicePackage() ?: ""
    }

    override fun reset() {
        settingsFormRainbow?.loadSettings()
    }

    override fun disposeUIResources() {
        settingsFormRainbow = null
    }

    @Nls
    override fun getDisplayName() = "Rainbow Fart"
}