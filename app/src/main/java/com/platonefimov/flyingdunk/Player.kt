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
    var width = 216f
        private set
    var height = 216f
        private set

    private var screenY: Int = 1080

    val bitmap: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.ball)

    private val horizontalSpeed = 1f
    var goingUp = false
    private val verticalAcceleration = 0.2f
    private val maxVerticalSpeed = 5f
    private var verticalSpeed = 0f

    fun scale(screenY: Int) {
        this.screenY = screenY
        width = screenY * 0.20f
        height = width
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

        if (y > screenY - height) {
            y = screenY - height
            verticalSpeed = 0f
        }
        if (y < 0) {
            y = 0f
            verticalSpeed = 0f
        }
    }
}
