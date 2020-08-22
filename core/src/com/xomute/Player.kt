package com.xomute

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.xomute.rays.Rays
import ktx.graphics.use
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

enum class Direction {
    LEFT, RIGHT, FORWARD, BACKWARD
}

class Player {

    private val rays = Rays(60)

    private val speed = 5
    private val width = 10
    private val height = 10

    private var angle: Double = 0.0

    private var x: Int = WIDTH / 4
    private var y: Int = HEIGHT / 4
    private var dx: Float = cos(angle).toFloat() * speed
    private var dy: Float = sin(angle).toFloat() * speed

    private val centerX: Float
        get() = x.toFloat() + width / 2

    private val centerY: Float
        get() = y.toFloat() + height / 2

    fun draw(renderer: ShapeRenderer) {
        renderer.use(ShapeRenderer.ShapeType.Line) {
            rays.draw2D(renderer, centerX, centerY, angle.toFloat())
            renderer.color = Color.YELLOW
            renderer.rect(x.toFloat(), y.toFloat(), width.toFloat(), height.toFloat())
            renderer.line(centerX, centerY, centerX + dx * speed, centerY + dy * speed)
        }
    }

    fun move(dir: Direction, map: GameMap, speed: Int = this.speed) {
        if (speed == 0) return

        // todo: that's fucking a lot of checks - try to reduce
        when (dir) {
//            Direction.LEFT -> if (map.check(x - speed, y) && map.check(x - speed, y + height)) x -= speed else move(dir, map, speed - 1)
//            Direction.RIGHT -> if (map.check(x + speed + width, y) && map.check(x + speed + width, y + height)) x += speed else move(dir, map, speed - 1)
            Direction.FORWARD -> /*if (map.check(x + dx.toInt()*//* + width*//*, y + dx.toInt()*//* + height*//*) *//*&& map.check(x + width + dx.toInt(), y + speed + height)*//*)*/ {
                x += dx.toInt()
                y += dy.toInt()
            }
            Direction.BACKWARD -> /*if (map.check(x - dx.toInt(), y - dy.toInt())*//* && map.check(x + width - dx.toInt(), y + height - dy.toInt())*//*)*/ {
                x -= dx.toInt()
                y -= dy.toInt()
            }
        }
    }

    fun rotate(dir: Direction) {
        when (dir) {
            Direction.LEFT -> {
                angle -= 0.05f; if (angle < 0) angle += PI.toFloat() * 2
            }
            Direction.RIGHT -> {
                angle += 0.05f; if (angle > PI * 2) angle -= PI.toFloat() * 2
            }
            else -> Unit
        }
        dx = cos(angle).toFloat() * speed; dy = sin(angle).toFloat() * speed
    }
}
