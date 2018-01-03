package com.platonefimov.flyingdunk

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.os.HandlerThread
import android.view.MotionEvent
import android.view.SurfaceView

@SuppressLint("ViewConstructor")
class GameView(context: Context) : SurfaceView(context), Runnable {

    private var screen = Rect()

    private var playing = true
    private var gameThread: Thread? = null
    private val fps = 60f

    private var canvas = Canvas()
    private val paint = Paint()

    private val background: Bitmap = BitmapFactory.decodeResource(context.resources,
            R.drawable.background)

    private var player = Player(context)

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        screen = Rect(0, 0, width, height)
        player.scale(height)

        return super.onCreateDrawableState(extraSpace)
    }

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

            // Sleep a little if rendered too fast
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

            // Paint preferences
            paint.isFilterBitmap = true

            // Clear canvas
            canvas.drawBitmap(background, null, screen, paint)

            // Drawing player
            canvas.drawBitmap(player.bitmap, null,
                    RectF(player.x, player.y,
                            player.x + player.width, player.y + player.height),
                    paint)

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
