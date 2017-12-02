package com.platonefimov.flyingdunk

import android.annotation.SuppressLint
import android.content.Context
import android.view.SurfaceView

@SuppressLint("ViewConstructor")
class GameView(context: Context, private val screenX: Int, private val screenY: Int) :
        SurfaceView(context), Runnable {

    override fun run() {
        //
    }
}
