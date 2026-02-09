package com.game.pvz.models

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class Projectile(
    x: Float,
    y: Float,
    private val damage: Int = 20,
    private val isFrozen: Boolean = false
) : GameObject(x, y, 15f, 15f) {
    
    private val speed = 5f
    private var shouldRemoveFlag = false
    
    override fun update() {
        x += speed
    }
    
    override fun draw(canvas: Canvas, paint: Paint) {
        paint.style = Paint.Style.FILL
        paint.color = if (isFrozen) Color.CYAN else Color.rgb(0, 200, 0)
        canvas.drawCircle(x, y, width / 2, paint)
        
        // 如果是冰冻豌豆，添加特效
        if (isFrozen) {
            paint.color = Color.argb(100, 200, 200, 255)
            canvas.drawCircle(x, y, width / 2 + 5f, paint)
        }
    }
    
    fun shouldRemove(): Boolean {
        return shouldRemoveFlag || x > 2000 // 超出屏幕范围
    }
    
    fun markForRemoval() {
        shouldRemoveFlag = true
    }
    
    fun getDamage(): Int = damage
    
    fun isFrozenProjectile(): Boolean = isFrozen
}
