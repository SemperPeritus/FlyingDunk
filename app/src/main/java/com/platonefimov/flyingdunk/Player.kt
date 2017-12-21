package com.platonefimov.flyingdunk

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlin.math.max

class Player(context: Context) {

    var x = 50f
        private set
    var y = 50f
        private set

    private var screenY: Int = 720

    val bitmap: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.ball)

    private val horizontalSpeed = 1f
    var goingUp = false
    private val verticalAcceleration = 0.2f
    private val maxVerticalSpeed = 5f
    private var verticalSpeed = 0f

    fun scale(screenY: Int) {
        this.screenY = screenY
    }

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

        if (y > screenY - bitmap.height) {
            y = (screenY - bitmap.height).toFloat()
            verticalSpeed = 0f
        }
        if (y < 0) {
            y = 0f
            verticalSpeed = 0f
        }
    }
}
