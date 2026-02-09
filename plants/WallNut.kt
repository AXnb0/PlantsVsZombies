package com.game.pvz.plants

import com.game.pvz.models.Plant
import android.graphics.Color

class WallNut(x: Float, y: Float) : Plant(x, y, 60f, 60f) {
    
    init {
        health = 400
        maxHealth = 400
        color = Color.rgb(139, 69, 19)
    }
    
    override fun update() {
        super.update()
        // 坚果墙不射击，只防御
    }
}
