package com.xomute

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import ktx.app.KtxApplicationAdapter
import ktx.app.clearScreen
import kotlin.math.PI

val WIDTH = 800
val HEIGHT = 600
val UI_HEIGHT = HEIGHT - HEIGHT / 6f

val PI2 = PI / 2
val PI32 = 3 * PI / 2
val DR = 0.0174533f // 1 degree in radians

class Game : KtxApplicationAdapter {

    private lateinit var renderer: ShapeRenderer
    private lateinit var camera: OrthographicCamera
    private lateinit var map: GameMap
    private lateinit var ui: UI
    private lateinit var player: Player

    private var toggled = false

    override fun create() {
        renderer = ShapeRenderer()
        camera = OrthographicCamera(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat()).also {
            it.setToOrtho(true, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
        }
        map = GameMap
        ui = UI()
        player = Player()
    }

    override fun render() {
        handleInput()
        logic()
        draw()
    }

    private fun logic() {

    }

    private fun handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            player.move(Direction.FORWARD, map, strafeKeyPressed())
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            player.move(Direction.BACKWARD, map, strafeKeyPressed())
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.move(Direction.LEFT, map, moveKeyPressed())
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.move(Direction.RIGHT, map, moveKeyPressed())
        }

        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            player.rotate(Direction.LEFT)
        } else if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            player.rotate(Direction.RIGHT)
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.TAB)) {
            toggle()
        }

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            dispose()
            super.dispose()
        }
    }

    private fun moveKeyPressed(): Boolean {
        return Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.S)
    }

    private fun strafeKeyPressed(): Boolean {
        return Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.D)
    }

    private fun draw() {
        clearScreen(0f, 0f, 0f)
        renderer.projectionMatrix = camera.combined
        player.draw(renderer)
        if (toggled) {
            map.draw(renderer)
            player.drawOnMap(renderer)
        }
        ui.draw(renderer)
    }

    // toggle map and player render
    private fun toggle() {
        toggled = !toggled
    }

    override fun dispose() {
        renderer.dispose()
    }
}
