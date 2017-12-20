package com.platonefimov.flyingdunk

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlin.math.max
import kotlin.math.min

class Player(context: Context, screenY: Int) {

    var x = 50f
        private set
    var y = 50f
        private set

    val bitmap: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.ball)

    private val horizontalSpeed = 1f
    var goingUp = false
    private val verticalAcceleration = 0.2f
    private val maxVerticalSpeed = 5f
    private var verticalSpeed = 0f

    private val maxY = screenY - bitmap.height
    private val minX = 0

    fun update() {
        if (goingUp)
            verticalSpeed += verticalAcceleration
        else
            verticalSpeed -= verticalAcceleration

        if (verticalSpeed > 0) {
            if (verticalSpeed > maxVerticalSpeed)
                verticalSpeed = maxVerticalSpeed
        } else
            verticalSpeed = max(verticalSpeed, -maxVerticalSpeed)

        x += horizontalSpeed
        y -= verticalSpeed
    }
}
