package com.platonefimov.flyingdunk

import android.annotation.SuppressLint
import android.content.Context
import android.os.HandlerThread
import android.util.Log
import android.view.SurfaceView

@SuppressLint("ViewConstructor")
class GameView(context: Context, private val screenX: Int, private val screenY: Int) :
        SurfaceView(context), Runnable {

    private var playing = true
    private var gameThread: Thread? = null
    private val fps = 60f

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
            Log.v(javaClass.name, beginTime.toString())
        }
    }

    private fun update() {
    }

    private fun draw() {
    }
}
