package com.platonefimov.flyingdunk

import android.annotation.SuppressLint
import android.content.Context
import android.os.HandlerThread
import android.view.SurfaceView

@SuppressLint("ViewConstructor")
class GameView(context: Context, private val screenX: Int, private val screenY: Int) :
        SurfaceView(context), Runnable {

    private var playing = true
    private var gameThread: Thread? = null

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
            update()
            draw()
            limitFps()
        }
    }

    private fun limitFps() {
        HandlerThread.sleep(17)
    }

    private fun update() {
    }

    private fun draw() {
    }
}
