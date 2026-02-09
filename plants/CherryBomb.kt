package com.game.pvz.plants

import com.game.pvz.models.Plant
import com.game.pvz.GameEngine
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class CherryBomb(x: Float, y: Float, private val gameEngine: GameEngine) : Plant(x, y) {
    
    private var explodeTimer = 90 // 1.5秒后爆炸
    private var exploded = false
    private val explosionRadius = 150f
    private val explosionDamage = 1800
    
    init {
        health = 100
        maxHealth = 100
        color = Color.RED
    }
    
    override fun update() {
        super.update()
        
        if (!exploded) {
            explodeTimer--
            
            if (explodeTimer <= 0) {
                explode()
            }
        }
    }
    
    override fun draw(canvas: Canvas, paint: Paint) {
        if (exploded) {
            // 绘制爆炸效果
            paint.style = Paint.Style.FILL
            paint.color = Color.argb(150, 255, 100, 0)
            canvas.drawCircle(x, y, explosionRadius, paint)
            
            paint.color = Color.argb(200, 255, 200, 0)
            canvas.drawCircle(x, y, explosionRadius * 0.7f, paint)
            
            paint.color = Color.RED
            canvas.drawCircle(x, y, explosionRadius * 0.4f, paint)
        } else {
            // 绘制樱桃炸弹
            paint.style = Paint.Style.FILL
            paint.color = Color.RED
            canvas.drawCircle(x - 15f, y, width / 2, paint)
            canvas.drawCircle(x + 15f, y, width / 2, paint)
            
            // 绘制倒计时
            paint.color = Color.WHITE
            paint.textSize = 30f
            paint.textAlign = Paint.Align.CENTER
            canvas.drawText((explodeTimer / 60 + 1).toString(), x, y + 10f, paint)
        }
    }
    
    private fun explode() {
        exploded = true
        gameEngine.explodeArea(x, y, explosionRadius, explosionDamage)
        
        // 设置一个短暂延迟后移除
        health = 0
    }
}
