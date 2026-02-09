package com.game.pvz.models

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

abstract class Zombie(
    x: Float,
    y: Float,
    width: Float = 50f,
    height: Float = 70f
) : GameObject(x, y, width, height) {
    
    protected var speed = 0.5f
    protected var damage = 10
    protected var attackCooldown = 0
    protected var attackInterval = 60 // 每秒攻击一次
    protected var isAttacking = false
    protected var targetPlant: Plant? = null
    protected var color = Color.rgb(100, 150, 100)
    protected var slowEffect = 0 // 减速效果持续时间
    
    override fun update() {
        if (attackCooldown > 0) {
            attackCooldown--
        }
        
        if (slowEffect > 0) {
            slowEffect--
        }
        
        if (!isAttacking) {
            // 移动
            val currentSpeed = if (slowEffect > 0) speed * 0.5f else speed
            x -= currentSpeed
        } else {
            // 攻击目标植物
            if (attackCooldown == 0) {
                targetPlant?.takeDamage(damage)
                attackCooldown = attackInterval
            }
        }
    }
    
    override fun draw(canvas: Canvas, paint: Paint) {
        // 绘制僵尸身体
        paint.style = Paint.Style.FILL
        paint.color = color
        canvas.drawRect(
            x - width / 2,
            y - height / 2,
            x + width / 2,
            y + height / 2,
            paint
        )
        
        // 绘制头部
        paint.color = Color.rgb(120, 170, 120)
        canvas.drawCircle(x, y - height / 3, width / 3, paint)
        
        // 如果被减速，绘制蓝色效果
        if (slowEffect > 0) {
            paint.color = Color.argb(100, 0, 0, 255)
            canvas.drawRect(
                x - width / 2,
                y - height / 2,
                x + width / 2,
                y + height / 2,
                paint
            )
        }
        
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
        paint.color = Color.rgb(0, 200, 0)
        val currentWidth = barWidth * (health.toFloat() / maxHealth)
        canvas.drawRect(barX, barY, barX + currentWidth, barY + barHeight, paint)
    }
    
    fun startAttacking(plant: Plant) {
        isAttacking = true
        targetPlant = plant
    }
    
    fun stopAttacking() {
        isAttacking = false
        targetPlant = null
    }
    
    fun applySlow(duration: Int) {
        slowEffect = duration
    }
    
    fun isSlowed(): Boolean {
        return slowEffect > 0
    }
}
