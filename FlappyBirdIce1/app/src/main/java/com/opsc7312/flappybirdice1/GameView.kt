package com.opsc7312.flappybirdice1

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Point
import android.graphics.Rect
import android.view.Display
import android.view.MotionEvent
import android.view.View
import android.os.Handler
import kotlin.random.Random

class GameView(context: Context) : View(context) {
    private val handler = Handler()
    private val runnable: Runnable
    private val UPDATE_MILLIS = 30
    private val background: Bitmap
    private val toptube: Bitmap
    private val bottomtube: Bitmap
    private val display: Display
    private val point: Point
    private val dWidth: Int
    private val dHeight: Int
    private val rect: Rect
    private val birds: Array<Bitmap>
    private var birdFrame = 0
    private var velocity = 0
    private val gravity = 3
    private val birdx: Int
    private var birdy: Int
    private var gameState = false
    private val gap = 400
    private val minTubeOffSet: Int
    private val maxTubeOffSet: Int
    private val numberOfTubes = 4
    private val distanceBetweenTubes: Int
    private val tubex = IntArray(numberOfTubes)
    private val toptubey = IntArray(numberOfTubes)
    private val random = Random
    private val tubevelocity = 8

    init {
        runnable = Runnable { invalidate() }

        background = BitmapFactory.decodeResource(resources, R.drawable.background)
        display = (context as Activity).windowManager.defaultDisplay
        point = Point()
        display.getSize(point)
        dWidth = point.x
        dHeight = point.y
        rect = Rect(0, 0, dWidth, dHeight)
        birds = arrayOf(
            BitmapFactory.decodeResource(resources, R.drawable.bird1),
            BitmapFactory.decodeResource(resources, R.drawable.bird1)
        )
        toptube = BitmapFactory.decodeResource(resources, R.drawable.toptube)
        bottomtube = BitmapFactory.decodeResource(resources, R.drawable.toptube)
        birdx = dWidth / 2 - birds[0].width / 2
        birdy = dHeight / 2 - birds[0].height / 2
        distanceBetweenTubes = dWidth * 3 / 4
        minTubeOffSet = gap / 2
        maxTubeOffSet = dHeight - minTubeOffSet - gap

        for (i in 0 until numberOfTubes) {
            tubex[i] = dWidth + i * distanceBetweenTubes
            toptubey[i] = minTubeOffSet + random.nextInt(maxTubeOffSet - minTubeOffSet + 1)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(background, null, rect, null)
        birdFrame = if (birdFrame == 0) 1 else 0

        if (gameState) {
            if (birdy < dHeight - birds[0].height || velocity < 0) {
                velocity += gravity
                birdy += velocity
            }
            for (i in 0 until numberOfTubes) {
                tubex[i] -= tubevelocity
                if (tubex[i] < -toptube.width) {
                    tubex[i] += numberOfTubes * distanceBetweenTubes
                    toptubey[i] = minTubeOffSet + random.nextInt(maxTubeOffSet - minTubeOffSet + 1)
                }
                canvas.drawBitmap(toptube, tubex[i].toFloat(), (toptubey[i] - toptube.height).toFloat(), null)
                canvas.drawBitmap(bottomtube, tubex[i].toFloat(), (toptubey[i] + gap).toFloat(), null)
            }
        }
        canvas.drawBitmap(birds[birdFrame], birdx.toFloat(), birdy.toFloat(), null)
        handler.postDelayed(runnable, UPDATE_MILLIS.toLong())
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            velocity = -30
            gameState = true
        }
        return true
    }
}


