package com.game.pvz.zombies

import com.game.pvz.models.Zombie
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class BucketZombie(x: Float, y: Float) : Zombie(x, y) {
    
    init {
        health = 650
        maxHealth = 650
        speed = 0.5f
        damage = 10
        color = Color.rgb(90, 130, 90)
    }
    
    override fun draw(canvas: Canvas, paint: Paint) {
        super.draw(canvas, paint)
        
        // 绘制铁桶
        paint.color = Color.GRAY
        paint.style = Paint.Style.FILL
        canvas.drawRect(
            x - 18f,
            y - height / 3 - 25f,
            x + 18f,
            y - height / 3 - 5f,
            paint
        )
        
        // 绘制铁桶边缘
        paint.color = Color.DKGRAY
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 2f
        canvas.drawRect(
            x - 18f,
            y - height / 3 - 25f,
            x + 18f,
            y - height / 3 - 5f,
            paint
        )
    }
}
