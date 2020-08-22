package com.xomute

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import ktx.graphics.use

enum class Direction {
    LEFT, RIGHT, UP, DOWN
}

class Player {
    private var x: Int = WIDTH / 4
    private var y: Int = HEIGHT / 4
    private val speed = 5
    val width = 10
    val height = 10

    fun draw(renderer: ShapeRenderer) {
        renderer.use(ShapeRenderer.ShapeType.Filled) {
            renderer.color = Color.YELLOW
            renderer.rect(x.toFloat(), y.toFloat(), width.toFloat(), height.toFloat())
        }
    }

    fun move(dir: Direction, map: GameMap, speed: Int = this.speed) {
        if (speed == 0) return

        // todo: that's fucking a lot of checks - try to reduce
        when (dir) {
            Direction.LEFT -> if (map.check(x - speed, y) && map.check(x - speed, y + height)) x -= speed else move(dir, map, speed - 1)
            Direction.RIGHT -> if (map.check(x + speed + width, y) && map.check(x + speed, y + height)) x += speed else move(dir, map, speed - 1)
            Direction.UP -> if (map.check(x, y - speed) && map.check(x + width, y - speed)) y -= speed else move(dir, map, speed - 1)
            Direction.DOWN -> if (map.check(x, y + speed + height) && map.check(x + width, y + speed)) y += speed else move(dir, map, speed - 1)
        }

    }
}
