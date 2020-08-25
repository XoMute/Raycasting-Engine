package com.xomute

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import ktx.graphics.use
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

enum class Direction {
    LEFT, RIGHT, FORWARD, BACKWARD
}

class Player {

    val rays = Raycaster(60)

    private val speed = 2f
//    private val width = 10
//    private val height = 10

    private var angle: Double = 0.0
    private val diagonalLimit = 0.7f

    private var x: Float = 1 * GameMap.size + GameMap.size / 2
    private var y: Float = 1 * GameMap.size + GameMap.size / 2
    private var dx: Float = cos(angle).toFloat() * speed
    private var dy: Float = sin(angle).toFloat() * speed

    fun draw(renderer: ShapeRenderer) {
        rays.draw2D(renderer, x, y, angle.toFloat())
    }

    fun move(dir: Direction, map: GameMap, diagonal: Boolean = false) {
        // todo: implement proper collision detection (don't stop if walking into the
        when (dir) {
            Direction.FORWARD -> {
                calculate(diagonal = diagonal)
            }
            Direction.BACKWARD -> {
                calculate(angle - PI, diagonal)
            }
            Direction.LEFT -> {
                calculate(angle - PI2, diagonal)
            }
            Direction.RIGHT -> {
                calculate(angle + PI2, diagonal)
            }
        }
        when {
            map.check(x + dx, y + dy) -> {
                x += dx
                y += dy
            }
            map.check(x + dx, y) -> {
                x += dx
            }
            map.check(x, y + dy) -> {
                y += dy
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
        calculate()
    }

    private fun calculate(angle: Double = this.angle, diagonal: Boolean = false) {
        val speed: Float = if (diagonal) diagonalLimit * this.speed else this.speed
        dx = cos(angle).toFloat() * speed
        dy = sin(angle).toFloat() * speed
    }

    fun drawOnMap(renderer: ShapeRenderer) {
        renderer.use(ShapeRenderer.ShapeType.Line) {
            renderer.color = Color.YELLOW
            renderer.rect(x, y, 5f, 5f)
            renderer.line(x, y, x + dx * speed, y + dy * speed)
        }
    }
}
