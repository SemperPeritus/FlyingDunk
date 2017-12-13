package com.platonefimov.flyingdunk

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.HandlerThread
import android.view.SurfaceView

@SuppressLint("ViewConstructor")
class GameView(context: Context, private val screenX: Int, private val screenY: Int) :
        SurfaceView(context), Runnable {

    private var playing = true
    private var gameThread: Thread? = null
    private val fps = 60f

    private var canvas = Canvas()
    private val paint = Paint()

    fun pause() {
        playing = false
        try {
            gameThread?.join()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    fun resume() {
        playing = true
        gameThread = Thread(this)
        gameThread?.start()
    }

    override fun run() {
        while (playing) {
            val beginTime = System.currentTimeMillis()
            update()
            draw()
            val delta = System.currentTimeMillis() - beginTime
            if (delta < 1000f / fps)
                HandlerThread.sleep((1000f / fps).toLong() - delta)
        }
    }

    private fun update() {
    }

    private fun draw() {
        if (holder.surface.isValid) {
            canvas = holder.lockCanvas()

            canvas.drawColor(Color.argb(255, 0, 0, 0))

            paint.color = Color.argb(255, 255, 0, 0)

            canvas.drawCircle(200f, 500f, 100f, paint)

            holder.unlockCanvasAndPost(canvas)
        }
    }
}
