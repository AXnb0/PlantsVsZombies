package com.game.pvz.plants

import com.game.pvz.models.Plant
import com.game.pvz.models.Projectile
import android.graphics.Color

class Peashooter(x: Float, y: Float) : Plant(x, y) {
    
    init {
        health = 100
        maxHealth = 100
        shootInterval = 90 // 1.5秒射击一次
        color = Color.rgb(50, 200, 50)
    }
    
    override fun canShoot(): Boolean {
        return shootCooldown == 0
    }
    
    override fun shoot(): Projectile? {
        return if (canShoot()) {
            shootCooldown = shootInterval
            Projectile(x + width / 2, y, 20, false)
        } else {
            null
        }
    }
}
