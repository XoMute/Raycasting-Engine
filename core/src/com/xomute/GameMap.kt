package com.xomute

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import ktx.graphics.color
import ktx.graphics.use

class GameMap {

    val x = 8
    val y = 8
    val size = 64f

    val WALL = 1

    val map = intArrayOf(
            1, 1, 1, 1, 1, 1, 1, 1,
            1, 0, 1, 0, 0, 0, 0, 1,
            1, 0, 0, 0, 0, 0, 0, 1,
            1, 0, 0, 0, 1, 0, 0, 1,
            1, 0, 0, 0, 0, 0, 0, 1,
            1, 0, 0, 1, 0, 0, 0, 1,
            1, 0, 0, 1, 0, 0, 0, 1,
            1, 1, 1, 1, 1, 1, 1, 1
    )

    operator fun get(i: Int): Int = map[i]

    operator fun get(x: Int, y: Int): Int = map[x + y * this.y]

    fun draw(renderer: ShapeRenderer) {
        renderer.use(ShapeRenderer.ShapeType.Filled) {
            for (x in 0 until x) {
                for (y in 0 until y) {
                    val tile = this[x, y]
                    renderer.color = if (tile == WALL) Color.WHITE else color(.1f, .1f, .1f)
                    renderer.rect(x * size, y * size, size - 1, size - 1)
                }
            }
        }
    }

    fun check(x: Int, y: Int): Boolean = this[x / size.toInt(), y / size.toInt()] == 0
}
