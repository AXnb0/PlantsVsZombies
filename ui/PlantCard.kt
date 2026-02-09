package com.game.pvz.ui

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import com.game.pvz.PlantType

class PlantCard(
    val plantType: PlantType,
    val cost: Int,
    private val cooldownTime: Int
) {
    private var bounds = RectF()
    private var cooldownTimer = 0
    
    fun setBounds(x: Float, y: Float, width: Float, height: Float) {
        bounds.set(x, y, x + width, y + height)
    }
    
    fun update() {
        if (cooldownTimer > 0) {
            cooldownTimer--
        }
    }
    
    fun draw(canvas: Canvas, paint: Paint, isSelected: Boolean) {
        // 绘制卡片背景
        paint.style = Paint.Style.FILL
        paint.color = when {
            isSelected -> Color.YELLOW
            cooldownTimer > 0 -> Color.GRAY
            else -> Color.WHITE
        }
        canvas.drawRect(bounds, paint)
        
        // 绘制边框
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 3f
        paint.color = if (isSelected) Color.RED else Color.BLACK
        canvas.drawRect(bounds, paint)
        
        // 绘制植物图标（简化为颜色块）
        paint.style = Paint.Style.FILL
        paint.color = getPlantColor()
        val iconSize = bounds.height() * 0.5f
        val iconX = bounds.centerX() - iconSize / 2
        val iconY = bounds.top + 10f
        canvas.drawCircle(iconX + iconSize / 2, iconY + iconSize / 2, iconSize / 2, paint)
        
        // 绘制阳光消耗
        paint.color = Color.BLACK
        paint.textSize = 20f
        paint.textAlign = Paint.Align.CENTER
        canvas.drawText(cost.toString(), bounds.centerX(), bounds.bottom - 10f, paint)
        
        // 绘制冷却遮罩
        if (cooldownTimer > 0) {
            paint.color = Color.argb(150, 0, 0, 0)
            val cooldownHeight = bounds.height() * (cooldownTimer.toFloat() / cooldownTime)
            canvas.drawRect(
                bounds.left,
                bounds.top,
                bounds.right,
                bounds.top + cooldownHeight,
                paint
            )
        }
    }
    
    private fun getPlantColor(): Int {
        return when (plantType) {
            PlantType.SUNFLOWER -> Color.YELLOW
            PlantType.PEASHOOTER -> Color.GREEN
            PlantType.WALLNUT -> Color.rgb(139, 69, 19)
            PlantType.SNOW_PEA -> Color.CYAN
            PlantType.CHERRY_BOMB -> Color.RED
        }
    }
    
    fun contains(x: Float, y: Float): Boolean {
        return bounds.contains(x, y)
    }
    
    fun isAvailable(): Boolean {
        return cooldownTimer == 0
    }
    
    fun startCooldown() {
        cooldownTimer = cooldownTime
    }
    
    fun reset() {
        cooldownTimer = 0
    }
}
