package com.github.izhangzhihao.rainbow.fart

import com.github.izhangzhihao.rainbow.fart.settings.RainbowFartSettings
import com.intellij.ide.plugins.IdeaPluginDescriptor
import com.intellij.ide.plugins.PluginManager
import com.intellij.notification.NotificationListener
import com.intellij.notification.NotificationType
import com.intellij.openapi.extensions.PluginId
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.StartupActivity
import com.intellij.ide.startup.StartupActionScriptManager

class RainbowFartUpdateNotifyActivity : StartupActivity {

    override fun runActivity(project: Project) {
        removeIfInstalled()
        val settings = RainbowFartSettings.instance
        if (getPlugin()?.version != settings.version) {
            settings.version = getPlugin()!!.version
            showUpdate(project)
        }
    }

    private fun removeIfInstalled() {
        val pluginId = PluginId.getId("com.github.jadepeng.rainbowfart")
        val isInstalled = PluginManager.isPluginInstalled(pluginId)
        if (isInstalled) {
            val pluginDescriptor = PluginManager.getPlugin(pluginId)
            if (pluginDescriptor != null) {
                StartupActionScriptManager.addActionCommand(StartupActionScriptManager.DeleteCommand(pluginDescriptor.path))
            }
        }
    }

    companion object {
        private const val pluginId = "izhangzhihao.rainbow.fart"


        private val updateContent: String by lazy {
            //language=HTML
            """
    <br/>
    🌈Thank you for downloading <b><a href="https://github.com/izhangzhihao/intellij-rainbow-fart">Rainbow Fart</a></b>!<br>
    👍If you find this plugin helpful, <b><a href="https://github.com/izhangzhihao/intellij-rainbow-fart#%E6%94%AF%E6%8C%81%E6%88%91">please support us!</a>.</b><br>
    🐛If you run into any problem, <b><a href="https://github.com/izhangzhihao/intellij-rainbow-fart/issues">feel free to raise a issue</a>.</b><br>
    🆕See <b><a href="https://github.com/izhangzhihao/intellij-rainbow-fart/releases">changelog</a></b> for more details about this update.<br>
    👉Other additional features under
    <b>Settings > Other Settings > Rainbow Fart</b><br/>
    """
        }

        fun getPlugin(): IdeaPluginDescriptor? = PluginManager.getPlugin(
                PluginId.getId("izhangzhihao.rainbow.brackets"))

        private fun updateMsg(): String {
            val plugin = getPlugin()
            return if (plugin == null) {
                "Rainbow Fart installed."
            } else {
                "Rainbow Fart updated to ${plugin.version}"
            }
        }

        private fun showUpdate(project: Project) {
            val notification = createNotification(
                    updateMsg(),
                    updateContent,
                    pluginId,
                    NotificationType.INFORMATION,
                    NotificationListener.UrlOpeningListener(false)
            )
            showFullNotification(project, notification)
        }
    }
}