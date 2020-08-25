package com.xomute

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import ktx.graphics.use

class UI {

    fun draw(renderer: ShapeRenderer) {
       renderer.use(ShapeRenderer.ShapeType.Filled) {
           renderer.color = Color.DARK_GRAY
           renderer.rect(0f, UI_HEIGHT, WIDTH.toFloat(), UI_HEIGHT)
           renderer.rect(0f, 0f, 10f, HEIGHT.toFloat())
           renderer.rect(WIDTH - 10f, 0f, 10f, HEIGHT.toFloat())
           renderer.rect(0f, 0f, WIDTH.toFloat(), 10f)
       }
   }
}
