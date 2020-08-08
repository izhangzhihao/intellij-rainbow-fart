package com.github.izhangzhihao.rainbow.fart

import com.github.izhangzhihao.rainbow.fart.RainbowFartTypedHandler.FartTypedHandler.releaseFart
import com.intellij.codeInsight.lookup.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.beans.PropertyChangeListener

class RainbowFartLookupHandler : LookupListener {
    override fun itemSelected(event: LookupEvent) {
        try {
            val currentItem: LookupElement = event.lookup.currentItem ?: return
            val lookupString: String = currentItem.lookupString
            if (BuildInContributes.buildInContributes.containsKey(lookupString)) {
                GlobalScope.launch((Dispatchers.Default)) {
                    releaseFart(BuildInContributes.buildInContributes.getOrDefault(lookupString, emptyList()))
                }
            }
        } finally {
            super.itemSelected(event)
        }
    }
}

class RainbowFartLookupComponent(lookupManager: LookupManager) {

    private val lookupListener = PropertyChangeListener { evt ->
        if (evt.newValue is Lookup) {
            val lookup = evt.newValue as Lookup
            lookup.addLookupListener(RainbowFartLookupHandler())
        }
    }

    init {
        //https://sourcegraph.com/github.com/JetBrains/intellij-community/-/commit/6429469c1bb576f4af5d9bd6473e0e3a71b7c7d5?visible=3
        lookupManager.addPropertyChangeListener(lookupListener)
    }
}