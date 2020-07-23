package com.github.izhangzhihao.rainbow.fart

import java.io.File

fun resolvePath(path: String): File {
    return if (path.startsWith("~")) {
        val home = System.getProperty("user.home")
        File(home + path.removePrefix("~"))
    } else {
        File(path)
    }
}