package com.opsc7312.flappybirdice1

import android.os.Bundle
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity

class StartGame : AppCompatActivity() {
    private lateinit var gameview: GameView

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gameview = GameView(this)
        setContentView(gameview)
    }
}


