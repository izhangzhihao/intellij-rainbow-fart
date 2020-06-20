package com.github.izhangzhihao.rainbow.fart.settings

import com.github.izhangzhihao.rainbow.fart.settings.form.FartSettingsForm
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.options.ConfigurationException
import org.jetbrains.annotations.Nls
import javax.swing.JComponent

class FartConfigurable : Configurable {
    var settingsForm: FartSettingsForm? = null

    override fun createComponent(): JComponent? {
        settingsForm = settingsForm ?: FartSettingsForm()
        return settingsForm?.component()
    }

    override fun isModified(): Boolean {
        return settingsForm?.isModified ?: return false
    }

    @Throws(ConfigurationException::class)
    override fun apply() {
        val settings = FartSettings.instance
        settings.isRainbowFartEnabled = settingsForm?.isRainbowEnabled() ?: true
    }

    override fun reset() {
        settingsForm?.loadSettings()
    }

    override fun disposeUIResources() {
        settingsForm = null
    }

    @Nls
    override fun getDisplayName() = "Rainbow Fart"
}