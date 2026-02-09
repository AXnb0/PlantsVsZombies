package com.game.pvz.plants

import com.game.pvz.models.Plant
import com.game.pvz.models.Projectile
import android.graphics.Color

class SnowPea(x: Float, y: Float) : Plant(x, y) {
    
    init {
        health = 100
        maxHealth = 100
        shootInterval = 90 // 1.5秒射击一次
        color = Color.CYAN
    }
    
    override fun canShoot(): Boolean {
        return shootCooldown == 0
    }
    
    override fun shoot(): Projectile? {
        return if (canShoot()) {
            shootCooldown = shootInterval
            Projectile(x + width / 2, y, 20, true) // 冰冻子弹
        } else {
            null
        }
    }
}
