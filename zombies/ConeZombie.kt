package com.game.pvz.zombies

import com.game.pvz.models.Zombie
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class ConeZombie(x: Float, y: Float) : Zombie(x, y) {
    
    init {
        health = 370
        maxHealth = 370
        speed = 0.5f
        damage = 10
        color = Color.rgb(100, 140, 100)
    }
    
    override fun draw(canvas: Canvas, paint: Paint) {
        super.draw(canvas, paint)
        
        // 绘制路障头盔
        paint.color = Color.rgb(200, 100, 50)
        paint.style = Paint.Style.FILL
        
        val conePoints = floatArrayOf(
            x - 15f, y - height / 3 - 5f,
            x + 15f, y - height / 3 - 5f,
            x, y - height / 3 - 25f
        )
        
        val path = android.graphics.Path()
        path.moveTo(conePoints[0], conePoints[1])
        path.lineTo(conePoints[2], conePoints[3])
        path.lineTo(conePoints[4], conePoints[5])
        path.close()
        
        canvas.drawPath(path, paint)
    }
}
