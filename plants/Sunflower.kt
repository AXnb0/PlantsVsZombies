package com.game.pvz.plants

import com.game.pvz.models.Plant
import com.game.pvz.models.Sun
import android.graphics.Color

class Sunflower(x: Float, y: Float) : Plant(x, y) {
    
    private var sunProductionTimer = 0
    private val sunProductionInterval = 1500 // 25秒生产一次阳光
    
    init {
        health = 100
        maxHealth = 100
        color = Color.YELLOW
    }
    
    override fun update() {
        super.update()
        sunProductionTimer++
    }
    
    fun canProduceSun(): Boolean {
        return sunProductionTimer >= sunProductionInterval
    }
    
    fun produceSun(): Sun? {
        return if (canProduceSun()) {
            sunProductionTimer = 0
            Sun(x, y, 25, false)
        } else {
            null
        }
    }
}
