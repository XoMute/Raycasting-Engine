package com.xomute.rays

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.xomute.*
import ktx.graphics.use
import kotlin.math.*

class Rays(val count: Int) {

    fun draw2D(renderer: ShapeRenderer, px: Float, py: Float, pa: Float) {
        var xo = 0f
        var yo = 0f
        var rx = 0f
        var ry = 0f
        val pl = Vector2(px, py)

        val maxDof = 20

        var ra = pa - DR * (count / 2)
        if (ra < 0) ra += 2 * PI.toFloat()
        if (ra > 2 * PI) ra -= 2 * PI.toFloat()
        renderer.color = Color.GREEN

        for (r in 0 until count) {
            val vRay = Vector2(1000f, 1000f)
            val hRay = Vector2(1000f, 1000f)
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
                dof = maxDof // todo: remove every hardcoded number
            }


            while (dof < maxDof) { // todo: remove every hardcoded number
                val mapX = rx.toInt() shr 6 // todo: remove every hardcoded number
                val mapY = ry.toInt() shr 6  // todo: remove every hardcoded number
                val index = mapX + mapY * GameMap.x
                if (index >= 0 && index < GameMap.x * GameMap.y && GameMap[index] > 0) { // hit obstacle
                    hRay.x = rx
                    hRay.y = ry
                    dof = maxDof
                } else { // todo: remove every hardcoded number
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
                xo = -64f  // todo: remove every hardcoded number
                yo = -xo * nTan
            }

            if (ra < PI2 || ra > PI32) { // looking right
                // the math behind this is simple: we want to round our y to closes 64 divisible value
                rx = ((px.toInt() shr 6) shl 6) + 64f  // todo: remove every hardcoded number
                ry = (px - rx) * nTan + py
                xo = 64f  // todo: remove every hardcoded number
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
                    dof = maxDof
                } else {
                    rx += xo
                    ry += yo
                    dof++
                }
            }

            val minRay = if (vRay.dst(pl) < hRay.dst(pl)) vRay else hRay
            renderer.line(pl, minRay)
            ra += DR
            if (ra < 0) ra += 2 * PI.toFloat()
            if (ra > 2 * PI.toFloat()) ra -= 2 * PI.toFloat()
        }
    }
}
