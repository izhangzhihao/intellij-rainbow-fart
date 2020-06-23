package com.github.izhangzhihao.rainbow.fart.settings

import com.intellij.openapi.components.ServiceManager.getService
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil.copyBean
import org.jetbrains.annotations.Nullable

@State(name = "FartSettings", storages = [(Storage("rainbow_fart.xml"))])
class RainbowFartSettings : PersistentStateComponent<RainbowFartSettings> {

    var isRainbowFartEnabled = true
    var version = "Unknown"
    var customVoicePackage: String = ""

    @Nullable
    override fun getState() = this

    override fun loadState(state: RainbowFartSettings) {
        copyBean(state, this)
    }

    companion object {
        val instance: RainbowFartSettings
            get() = getService(RainbowFartSettings::class.java)

        var isAppliedApplicationLevel = false
    }
}