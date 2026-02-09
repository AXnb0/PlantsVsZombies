package com.game.pvz.zombies

import com.game.pvz.models.Zombie
import android.graphics.Color

class NormalZombie(x: Float, y: Float) : Zombie(x, y) {
    
    init {
        health = 200
        maxHealth = 200
        speed = 0.5f
        damage = 10
        color = Color.rgb(100, 150, 100)
    }
}
