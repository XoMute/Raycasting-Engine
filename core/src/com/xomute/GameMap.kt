package com.xomute

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import ktx.graphics.color
import ktx.graphics.use

object GameMap {

    const val x = 8
    const val y = 8
    const val size = 64f

    const val WALL = 1
    const val WALL2 = 2

    private val map = intArrayOf(
            1, 1, 1, 1, 1, 1, 1, 1,
            1, 0, 0, 1, 0, 0, 0, 1,
            1, 0, 1, 1, 0, 0, 0, 1,
            1, 0, 1, 0, 0, 2, 0, 1,
            1, 0, 1, 0, 2, 2, 2, 1,
            1, 0, 0, 0, 0, 0, 2, 1,
            1, 0, 0, 0, 0, 2, 2, 1,
            1, 1, 1, 1, 1, 1, 1, 1
    )

    operator fun get(i: Int): Int = map[i]

    operator fun get(x: Int, y: Int): Int = map[x + y * this.x]

    fun draw(renderer: ShapeRenderer) {
        renderer.use(ShapeRenderer.ShapeType.Filled) {
            for (x in 0 until x) {
                for (y in 0 until y) {
                    val tile = this[x, y]
                    renderer.color = if (tile == WALL) Color.WHITE else if (tile == WALL2) Color.RED else color(.1f, .1f, .1f)
                    renderer.rect(x * size, y * size, size - 1, size - 1)
                }
            }
        }
    }

    fun check(x: Int, y: Int): Boolean = this[x / size.toInt(), y / size.toInt()] >= 0
}
