package com.xomute

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import ktx.graphics.color
import ktx.graphics.use
import kotlin.math.*

class Raycaster(val count: Int) {

    val lineW = WIDTH / count.toFloat()
    val smoothing = 4 // bigger the number -> smoother the walls

    fun draw2D(renderer: ShapeRenderer, px: Float, py: Float, pa: Float) {
        var xo = 0f
        var yo = 0f
        var rx = 0f
        var ry = 0f
        val pl = Vector2(px, py)

        val maxDof = 20

        var ra = pa - DR * count / 2
        if (ra < 0) ra += 2 * PI.toFloat()
        if (ra > 2 * PI) ra -= 2 * PI.toFloat()

        for (r in 0 until count * smoothing) {
            val vRay = Vector2(1000f, 1000f)
            val hRay = Vector2(1000f, 1000f)
            var intersectedTileH = 0
            var intersectedTileV = 0
            // check horizontal
            var dof = 0
            val aTan = -1 / tan(ra)
            if (ra > PI) { // looking down
                ry = ((py.toInt() shr 6) shl 6) - 0.0001f// todo: remove every hardcoded number
                rx = (py - ry) * aTan + px
                yo = -GameMap.size
                xo = -yo * aTan
            }

            if (ra < PI) { // looking up
                ry = ((py.toInt() shr 6) shl 6) + GameMap.size// todo: remove every hardcoded number
                rx = (py - ry) * aTan + px
                yo = GameMap.size
                xo = -yo * aTan
            }

            if (ra == 0f || ra == PI.toFloat()) { // looking straight left or right
                rx = px
                ry = py
                dof = maxDof
            }


            while (dof < maxDof) {
                val mapX = rx.toInt() shr 6 // todo: remove every hardcoded number
                val mapY = ry.toInt() shr 6  // todo: remove every hardcoded number
                val index = mapX + mapY * GameMap.x
                if (index >= 0 && index < GameMap.x * GameMap.y && GameMap[index] > 0) { // hit obstacle
                    hRay.x = rx
                    hRay.y = ry
                    intersectedTileH = GameMap[index]
                    dof = maxDof
                } else {
                    rx += xo
                    ry += yo
                    dof++
                }
            }

            // check vertical

            dof = 0
            val nTan = -tan(ra)
            if (ra > PI2 && ra < PI32) { // looking left
                // the math behind this is simple: we want to round our y to closes 64 divisible value
                rx = ((px.toInt() shr 6) shl 6).toFloat() - 0.0001f
                ry = (px - rx) * nTan + py
                xo = -GameMap.size
                yo = -xo * nTan
            }

            if (ra < PI2 || ra > PI32) { // looking right
                // the math behind this is simple: we want to round our y to closes 64 divisible value
                rx = ((px.toInt() shr 6) shl 6) + GameMap.size // todo: remove every hardcoded number
                ry = (px - rx) * nTan + py
                xo = GameMap.size
                yo = -xo * nTan
            }

            if (ra == PI2.toFloat() || ra == PI32.toFloat()) { // looking straight up or down
                rx = px
                ry = py
                dof = maxDof
            }

            while (dof < maxDof) {
                val mapX = rx.toInt() shr 6 // todo: remove every hardcoded number
                val mapY = ry.toInt() shr 6  // todo: remove every hardcoded number
                val index = mapX + mapY * GameMap.x
                if (index >= 0 && index < GameMap.x * GameMap.y && GameMap[index] != 0) { // hit obstacle
                    vRay.x = rx
                    vRay.y = ry
                    intersectedTileV = GameMap[index]
                    dof = maxDof
                } else {
                    rx += xo
                    ry += yo
                    dof++
                }
            }

            var clr: Color
            val distH = hRay.dst(pl)
            val distV = vRay.dst(pl)
            var minDist: Float
            val minRay = if (distV < distH) {
                minDist = distV
                clr = when (intersectedTileV) {
                    GameMap.WALL -> color(0.9f, 0.9f, 0.9f)
                    GameMap.WALL2 -> color(0.9f, 0f, 0f)
                    else -> Color.BLACK
                }
                vRay
            } else {
                minDist = distH
                clr = when (intersectedTileH) {
                    GameMap.WALL -> color(0.7f, 0.7f, 0.7f)
                    GameMap.WALL2 -> color(0.7f, 0f, 0f)
                    else -> Color.BLACK
                }
                hRay
            }

            // todo: remove in future releases
//            renderer.use(ShapeRenderer.ShapeType.Line) {
//                Gdx.gl.glLineWidth(1f)
//                it.color = Color.GREEN
//                renderer.line(pl, minRay)
//            }

            var ca = pa - ra
            if (ca < 0) ca += 2 * PI.toFloat()
            if (ca > 2 * PI) ca -= 2 * PI.toFloat()
            minDist *= cos(ca)
            var lineH = GameMap.size * UI_HEIGHT / minDist
            if (lineH > UI_HEIGHT) lineH = UI_HEIGHT
            val lineO = UI_HEIGHT / 2 - lineH / 2
            renderer.use(ShapeRenderer.ShapeType.Filled) {
//                Gdx.gl.glLineWidth(lineW)
                it.color = clr
                it.rect(r * lineW / smoothing, lineO, lineW / smoothing, lineH)
            }
            ra += DR / smoothing
            if (ra < 0) ra += 2 * PI.toFloat()
            if (ra > 2 * PI.toFloat()) ra -= 2 * PI.toFloat()
        }
    }
}
