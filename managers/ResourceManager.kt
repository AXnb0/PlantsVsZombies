package com.game.pvz.managers

class ResourceManager {
    
    private var sunCount = 150 // 初始阳光
    
    fun getSunCount(): Int = sunCount
    
    fun addSun(amount: Int) {
        sunCount += amount
    }
    
    fun spendSun(amount: Int): Boolean {
        return if (sunCount >= amount) {
            sunCount -= amount
            true
        } else {
            false
        }
    }
    
    fun canAfford(cost: Int): Boolean {
        return sunCount >= cost
    }
    
    fun reset() {
        sunCount = 150
    }
}
