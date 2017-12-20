package com.platonefimov.flyingdunk

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.os.HandlerThread
import android.view.MotionEvent
import android.view.SurfaceView

@SuppressLint("ViewConstructor")
class GameView(context: Context, private val screenX: Int, private val screenY: Int) :
        SurfaceView(context), Runnable {

    private var playing = true
    private var gameThread: Thread? = null
    private val fps = 60f

    private var canvas = Canvas()
    private val paint = Paint()

    private val background: Bitmap = BitmapFactory.decodeResource(context.resources,
            R.drawable.background)

    private val player = Player(context, screenY)

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
        player.update()
    }

    private fun draw() {
        if (holder.surface.isValid) {
            // Lock a canvas
            canvas = holder.lockCanvas()

            // Clear canvas
            canvas.drawBitmap(background, 0f, 0f, paint)

            canvas.drawBitmap(player.bitmap, player.x, player.y, paint)

            // Unlock the canvas
            holder.unlockCanvasAndPost(canvas)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        performClick()

        when (event.action) {
            MotionEvent.ACTION_UP -> player.goingUp = false
            MotionEvent.ACTION_DOWN -> player.goingUp = true
        }

        return true
    }

    override fun performClick(): Boolean {
        super.performClick()
        return true
    }
}
