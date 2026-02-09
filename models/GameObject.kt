package com.game.pvz.models

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF

abstract class GameObject(
    var x: Float,
    var y: Float,
    var width: Float = 60f,
    var height: Float = 60f
) {
    protected var health: Int = 100
    protected var maxHealth: Int = 100
    
    abstract fun update()
    abstract fun draw(canvas: Canvas, paint: Paint)
    
    fun getBounds(): RectF {
        return RectF(
            x - width / 2,
            y - height / 2,
            x + width / 2,
            y + height / 2
        )
    }
    
    fun intersects(other: GameObject): Boolean {
        return RectF.intersects(getBounds(), other.getBounds())
    }
    
    fun takeDamage(damage: Int) {
        health -= damage
        if (health < 0) health = 0
    }
    
    fun isDead(): Boolean {
        return health <= 0
    }
    
    fun getHealth(): Int = health
    fun getMaxHealth(): Int = maxHealth
}
