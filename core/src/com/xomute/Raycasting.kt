package com.xomute

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import ktx.app.KtxApplicationAdapter
import ktx.app.clearScreen

val WIDTH = 1024
val HEIGHT = 512

class Raycasting : KtxApplicationAdapter {

    private lateinit var renderer: ShapeRenderer
    private lateinit var camera: OrthographicCamera
    private lateinit var map: GameMap
    private lateinit var player: Player

    override fun create() {
        renderer = ShapeRenderer()
        camera = OrthographicCamera(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat()).also {
            it.setToOrtho(true, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
        }
        map = GameMap()
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
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.move(Direction.LEFT, map)
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.move(Direction.RIGHT, map)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            player.move(Direction.UP, map)
        } else
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            player.move(Direction.DOWN, map)
        }

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            dispose()
            super.dispose()
        }
    }

    private fun draw() {
        clearScreen(0f, 0f, 0f)
        renderer.projectionMatrix = camera.combined
        renderer.color = Color.WHITE
        map.draw(renderer)
        player.draw(renderer)
    }

    override fun dispose() {
        renderer.dispose()
    }
}
