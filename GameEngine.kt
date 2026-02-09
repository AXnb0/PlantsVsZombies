package com.game.pvz

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.view.MotionEvent
import com.game.pvz.managers.*
import com.game.pvz.models.*
import com.game.pvz.plants.*
import com.game.pvz.ui.PlantCard
import java.util.concurrent.CopyOnWriteArrayList

class GameEngine(private val context: Context) {
    
    // 屏幕尺寸
    private var screenWidth = 0
    private var screenHeight = 0
    
    // 网格配置
    private val rows = 5
    private val cols = 9
    private var cellWidth = 0f
    private var cellHeight = 0f
    private var gridOffsetX = 0f
    private var gridOffsetY = 0f
    
    // 游戏对象列表
    private val plants = CopyOnWriteArrayList<Plant>()
    private val zombies = CopyOnWriteArrayList<Zombie>()
    private val projectiles = CopyOnWriteArrayList<Projectile>()
    private val suns = CopyOnWriteArrayList<Sun>()
    
    // 管理器
    private val resourceManager = ResourceManager()
    private val collisionManager = CollisionManager()
    private val waveManager = WaveManager()
    
    // UI元素
    private val plantCards = mutableListOf<PlantCard>()
    private var selectedPlantType: PlantType? = null
    
    // 游戏状态
    private var isPaused = false
    private var gameOver = false
    private var victory = false
    
    // 时间计数器
    private var sunSpawnTimer = 0
    private val sunSpawnInterval = 300 // 5秒生成一个阳光
    
    init {
        initializePlantCards()
    }
    
    fun setScreenSize(width: Int, height: Int) {
        screenWidth = width
        screenHeight = height
        
        // 计算网格尺寸
        val gridWidth = screenWidth * 0.75f
        val gridHeight = screenHeight * 0.6f
        
        cellWidth = gridWidth / cols
        cellHeight = gridHeight / rows
        
        gridOffsetX = screenWidth * 0.15f
        gridOffsetY = screenHeight * 0.25f
        
        // 初始化植物卡片位置
        initializePlantCardPositions()
    }
    
    private fun initializePlantCards() {
        plantCards.add(PlantCard(PlantType.SUNFLOWER, 50, 750))
        plantCards.add(PlantCard(PlantType.PEASHOOTER, 100, 750))
        plantCards.add(PlantCard(PlantType.WALLNUT, 50, 3000))
        plantCards.add(PlantCard(PlantType.SNOW_PEA, 175, 750))
        plantCards.add(PlantCard(PlantType.CHERRY_BOMB, 150, 5000))
    }
    
    private fun initializePlantCardPositions() {
        val cardWidth = screenWidth / 6f
        val cardHeight = screenHeight / 10f
        val startY = 20f
        
        plantCards.forEachIndexed { index, card ->
            card.setBounds(
                20f + index * (cardWidth + 10f),
                startY,
                cardWidth,
                cardHeight
            )
        }
    }
    
    fun update() {
        if (isPaused || gameOver) return
        
        // 更新植物
        plants.forEach { it.update() }
        
        // 更新僵尸
        zombies.forEach { it.update() }
        
        // 更新子弹
        projectiles.forEach { it.update() }
        
        // 更新阳光
        suns.forEach { it.update() }
        
        // 植物生成子弹
        plants.forEach { plant ->
            if (plant.canShoot()) {
                val row = ((plant.y - gridOffsetY) / cellHeight).toInt()
                if (hasZombieInRow(row)) {
                    plant.shoot()?.let { projectile ->
                        projectiles.add(projectile)
                    }
                }
            }
        }
        
        // 向日葵生成阳光
        plants.forEach { plant ->
            if (plant is Sunflower && plant.canProduceSun()) {
                plant.produceSun()?.let { sun ->
                    suns.add(sun)
                }
            }
        }
        
        // 碰撞检测
        collisionManager.checkCollisions(zombies, plants, projectiles)
        
        // 移除死亡对象
        plants.removeAll { it.isDead() }
        zombies.removeAll { it.isDead() }
        projectiles.removeAll { it.shouldRemove() }
        suns.removeAll { it.shouldRemove() }
        
        // 生成天降阳光
        sunSpawnTimer++
        if (sunSpawnTimer >= sunSpawnInterval) {
            sunSpawnTimer = 0
            spawnSunFromSky()
        }
        
        // 波次管理
        waveManager.update(this)
        
        // 更新植物卡片冷却
        plantCards.forEach { it.update() }
        
        // 检查游戏结束
        checkGameOver()
    }
    
    fun draw(canvas: Canvas, paint: Paint) {
        // 绘制网格
        drawGrid(canvas, paint)
        
        // 绘制阳光
        suns.forEach { it.draw(canvas, paint) }
        
        // 绘制植物
        plants.forEach { it.draw(canvas, paint) }
        
        // 绘制僵尸
        zombies.forEach { it.draw(canvas, paint) }
        
        // 绘制子弹
        projectiles.forEach { it.draw(canvas, paint) }
        
        // 绘制UI
        drawUI(canvas, paint)
        
        // 绘制游戏结束界面
        if (gameOver) {
            drawGameOver(canvas, paint)
        }
    }
    
    private fun drawGrid(canvas: Canvas, paint: Paint) {
        paint.color = Color.argb(50, 0, 0, 0)
        paint.strokeWidth = 2f
        paint.style = Paint.Style.STROKE
        
        for (row in 0..rows) {
            val y = gridOffsetY + row * cellHeight
            canvas.drawLine(gridOffsetX, y, gridOffsetX + cols * cellWidth, y, paint)
        }
        
        for (col in 0..cols) {
            val x = gridOffsetX + col * cellWidth
            canvas.drawLine(x, gridOffsetY, x, gridOffsetY + rows * cellHeight, paint)
        }
    }
    
    private fun drawUI(canvas: Canvas, paint: Paint) {
        // 绘制植物卡片
        plantCards.forEach { card ->
            card.draw(canvas, paint, selectedPlantType == card.plantType)
        }
        
        // 绘制阳光计数
        paint.color = Color.BLACK
        paint.textSize = 50f
        paint.style = Paint.Style.FILL
        canvas.drawText("Sun: ${resourceManager.getSunCount()}", screenWidth * 0.75f, 60f, paint)
        
        // 绘制波次信息
        canvas.drawText("Wave: ${waveManager.getCurrentWave()}/10", screenWidth * 0.75f, 120f, paint)
    }
    
    private fun drawGameOver(canvas: Canvas, paint: Paint) {
        paint.color = Color.argb(180, 0, 0, 0)
        canvas.drawRect(0f, 0f, screenWidth.toFloat(), screenHeight.toFloat(), paint)
        
        paint.color = if (victory) Color.GREEN else Color.RED
        paint.textSize = 80f
        paint.textAlign = Paint.Align.CENTER
        
        val message = if (victory) "Victory!" else "Game Over"
        canvas.drawText(message, screenWidth / 2f, screenHeight / 2f, paint)
        
        paint.textSize = 40f
        canvas.drawText("Tap to restart", screenWidth / 2f, screenHeight / 2f + 100f, paint)
    }
    
    fun handleTouch(x: Float, y: Float, action: Int) {
        if (gameOver && action == MotionEvent.ACTION_DOWN) {
            resetGame()
            return
        }
        
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                // 检查是否点击了阳光
                suns.forEach { sun ->
                    if (sun.contains(x, y)) {
                        resourceManager.addSun(sun.value)
                        sun.collect()
                        return
                    }
                }
                
                // 检查是否点击了植物卡片
                plantCards.forEach { card ->
                    if (card.contains(x, y) && card.isAvailable() && 
                        resourceManager.canAfford(card.cost)) {
                        selectedPlantType = if (selectedPlantType == card.plantType) {
                            null
                        } else {
                            card.plantType
                        }
                        return
                    }
                }
                
                // 检查是否在网格内放置植物
                selectedPlantType?.let { type ->
                    val card = plantCards.find { it.plantType == type }
                    card?.let {
                        if (isInGrid(x, y) && resourceManager.canAfford(card.cost)) {
                            val col = ((x - gridOffsetX) / cellWidth).toInt()
                            val row = ((y - gridOffsetY) / cellHeight).toInt()
                            
                            if (canPlaceAt(row, col)) {
                                placePlant(type, row, col)
                                resourceManager.spendSun(card.cost)
                                card.startCooldown()
                                selectedPlantType = null
                            }
                        }
                    }
                }
            }
        }
    }
    
    private fun isInGrid(x: Float, y: Float): Boolean {
        return x >= gridOffsetX && x <= gridOffsetX + cols * cellWidth &&
               y >= gridOffsetY && y <= gridOffsetY + rows * cellHeight
    }
    
    private fun canPlaceAt(row: Int, col: Int): Boolean {
        return plants.none { 
            val plantRow = ((it.y - gridOffsetY) / cellHeight).toInt()
            val plantCol = ((it.x - gridOffsetX) / cellWidth).toInt()
            plantRow == row && plantCol == col
        }
    }
    
    private fun placePlant(type: PlantType, row: Int, col: Int) {
        val x = gridOffsetX + col * cellWidth + cellWidth / 2
        val y = gridOffsetY + row * cellHeight + cellHeight / 2
        
        val plant = when (type) {
            PlantType.SUNFLOWER -> Sunflower(x, y)
            PlantType.PEASHOOTER -> Peashooter(x, y)
            PlantType.WALLNUT -> WallNut(x, y)
            PlantType.SNOW_PEA -> SnowPea(x, y)
            PlantType.CHERRY_BOMB -> CherryBomb(x, y, this)
        }
        
        plants.add(plant)
    }
    
    private fun hasZombieInRow(row: Int): Boolean {
        return zombies.any {
            val zombieRow = ((it.y - gridOffsetY) / cellHeight).toInt()
            zombieRow == row
        }
    }
    
    fun spawnZombie(type: ZombieType, row: Int) {
        val x = gridOffsetX + cols * cellWidth + 50f
        val y = gridOffsetY + row * cellHeight + cellHeight / 2
        
        val zombie = when (type) {
            ZombieType.NORMAL -> com.game.pvz.zombies.NormalZombie(x, y)
            ZombieType.CONE -> com.game.pvz.zombies.ConeZombie(x, y)
            ZombieType.BUCKET -> com.game.pvz.zombies.BucketZombie(x, y)
        }
        
        zombies.add(zombie)
    }
    
    private fun spawnSunFromSky() {
        val x = gridOffsetX + Math.random().toFloat() * cols * cellWidth
        val sun = Sun(x, 0f, 25, true)
        suns.add(sun)
    }
    
    fun explodeArea(centerX: Float, centerY: Float, radius: Float, damage: Int) {
        zombies.forEach { zombie ->
            val dx = zombie.x - centerX
            val dy = zombie.y - centerY
            val distance = Math.sqrt((dx * dx + dy * dy).toDouble()).toFloat()
            
            if (distance <= radius) {
                zombie.takeDamage(damage)
            }
        }
    }
    
    private fun checkGameOver() {
        // 检查是否有僵尸到达左侧
        zombies.forEach { zombie ->
            if (zombie.x < gridOffsetX - 100f) {
                gameOver = true
                victory = false
                return
            }
        }
        
        // 检查是否完成所有波次
        if (waveManager.isComplete() && zombies.isEmpty()) {
            gameOver = true
            victory = true
        }
    }
    
    private fun resetGame() {
        plants.clear()
        zombies.clear()
        projectiles.clear()
        suns.clear()
        
        resourceManager.reset()
        waveManager.reset()
        plantCards.forEach { it.reset() }
        
        selectedPlantType = null
        gameOver = false
        victory = false
        sunSpawnTimer = 0
    }
    
    fun pause() {
        isPaused = true
    }
    
    fun resume() {
        isPaused = false
    }
}

enum class PlantType {
    SUNFLOWER, PEASHOOTER, WALLNUT, SNOW_PEA, CHERRY_BOMB
}

enum class ZombieType {
    NORMAL, CONE, BUCKET
}
