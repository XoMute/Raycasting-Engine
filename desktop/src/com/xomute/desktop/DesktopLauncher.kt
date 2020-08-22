package com.xomute.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.xomute.HEIGHT
import com.xomute.Raycasting
import com.xomute.WIDTH

object DesktopLauncher {
    @JvmStatic
    fun main(arg: Array<String>) {
        val config = LwjglApplicationConfiguration().apply { this.width = WIDTH; this.height = HEIGHT }
        LwjglApplication(Raycasting(), config)
    }
}
