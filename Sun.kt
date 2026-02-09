package com.game.pvz.models

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class Sun(
    x: Float,
    y: Float,
    val value: Int = 25,
    private val fromSky: Boolean = false
) : GameObject(x, y, 40f, 40f) {
    
    private var targetY = y
    private var fallSpeed = 2f
    private var lifeTime = 480 // 8秒后消失
    private var collected = false
    
    init {
        if (fromSky) {
            targetY = y + 200f + Math.random().toFloat() * 100f
        }
    }
    
    override fun update() {
        if (fromSky && y < targetY) {
            y += fallSpeed
            if (y >= targetY) {
                y = targetY
            }
        }
        
        if (!collected) {
            lifeTime--
        }
    }
    
    override fun draw(canvas: Canvas, paint: Paint) {
        if (collected) return
        
        // 绘制发光效果
        paint.style = Paint.Style.FILL
        paint.color = Color.argb(50, 255, 255, 0)
        canvas.drawCircle(x, y, width / 2 + 10f, paint)
        
        // 绘制阳光主体
        paint.color = Color.YELLOW
        canvas.drawCircle(x, y, width / 2, paint)
        
        // 绘制阳光中心
        paint.color = Color.rgb(255, 200, 0)
        canvas.drawCircle(x, y, width / 3, paint)
        
        // 绘制数值
        paint.color = Color.BLACK
        paint.textSize = 20f
        paint.textAlign = Paint.Align.CENTER
        canvas.drawText(value.toString(), x, y + 7f, paint)
    }
    
    fun contains(touchX: Float, touchY: Float): Boolean {
        if (collected) return false
        
        val dx = touchX - x
        val dy = touchY - y
        val distance = Math.sqrt((dx * dx + dy * dy).toDouble()).toFloat()
        return distance <= width / 2
    }
    
    fun collect() {
        collected = true
    }
    
    fun shouldRemove(): Boolean {
        return collected || lifeTime <= 0
    }
}
