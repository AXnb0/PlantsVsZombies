package com.game.pvz.models

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

abstract class Plant(
    x: Float,
    y: Float,
    width: Float = 50f,
    height: Float = 50f
) : GameObject(x, y, width, height) {
    
    protected var shootCooldown = 0
    protected var shootInterval = 100 // 帧数
    protected var color = Color.GREEN
    
    override fun update() {
        if (shootCooldown > 0) {
            shootCooldown--
        }
    }
    
    override fun draw(canvas: Canvas, paint: Paint) {
        // 绘制植物主体
        paint.style = Paint.Style.FILL
        paint.color = color
        canvas.drawCircle(x, y, width / 2, paint)
        
        // 绘制健康条
        drawHealthBar(canvas, paint)
    }
    
    protected fun drawHealthBar(canvas: Canvas, paint: Paint) {
        val barWidth = width
        val barHeight = 5f
        val barX = x - barWidth / 2
        val barY = y - height / 2 - 10f
        
        // 背景
        paint.color = Color.RED
        canvas.drawRect(barX, barY, barX + barWidth, barY + barHeight, paint)
        
        // 当前血量
        paint.color = Color.GREEN
        val currentWidth = barWidth * (health.toFloat() / maxHealth)
        canvas.drawRect(barX, barY, barX + currentWidth, barY + barHeight, paint)
    }
    
    open fun canShoot(): Boolean {
        return false
    }
    
    open fun shoot(): Projectile? {
        return null
    }
}
