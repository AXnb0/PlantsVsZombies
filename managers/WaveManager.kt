package com.game.pvz.managers

import com.game.pvz.GameEngine
import com.game.pvz.ZombieType

class WaveManager {
    
    private var currentWave = 0
    private val totalWaves = 10
    private var waveTimer = 0
    private val waveInterval = 1800 // 30秒一波
    private var zombiesSpawnedInWave = 0
    private var zombiesToSpawnInWave = 0
    private var spawnTimer = 0
    private val spawnInterval = 180 // 3秒生成一个僵尸
    
    fun update(gameEngine: GameEngine) {
        if (currentWave >= totalWaves) return
        
        waveTimer++
        
        // 开始新的一波
        if (waveTimer >= waveInterval && zombiesSpawnedInWave >= zombiesToSpawnInWave) {
            startNextWave()
            waveTimer = 0
        }
        
        // 在当前波次中生成僵尸
        if (zombiesSpawnedInWave < zombiesToSpawnInWave) {
            spawnTimer++
            
            if (spawnTimer >= spawnInterval) {
                spawnZombie(gameEngine)
                spawnTimer = 0
            }
        }
    }
    
    private fun startNextWave() {
        currentWave++
        zombiesSpawnedInWave = 0
        
        // 根据波次计算要生成的僵尸数量
        zombiesToSpawnInWave = when (currentWave) {
            1 -> 3
            2 -> 4
            3 -> 5
            4 -> 6
            5 -> 7
            6 -> 8
            7 -> 9
            8 -> 10
            9 -> 12
            10 -> 20 // 最后一波大波僵尸
            else -> 5
        }
    }
    
    private fun spawnZombie(gameEngine: GameEngine) {
        val row = (Math.random() * 5).toInt()
        
        // 根据波次决定僵尸类型
        val zombieType = when {
            currentWave <= 2 -> ZombieType.NORMAL
            currentWave <= 5 -> {
                if (Math.random() < 0.7) ZombieType.NORMAL else ZombieType.CONE
            }
            currentWave <= 8 -> {
                when {
                    Math.random() < 0.5 -> ZombieType.NORMAL
                    Math.random() < 0.8 -> ZombieType.CONE
                    else -> ZombieType.BUCKET
                }
            }
            else -> {
                when {
                    Math.random() < 0.3 -> ZombieType.NORMAL
                    Math.random() < 0.6 -> ZombieType.CONE
                    else -> ZombieType.BUCKET
                }
            }
        }
        
        gameEngine.spawnZombie(zombieType, row)
        zombiesSpawnedInWave++
    }
    
    fun getCurrentWave(): Int = currentWave
    
    fun isComplete(): Boolean = currentWave >= totalWaves
    
    fun reset() {
        currentWave = 0
        waveTimer = 0
        zombiesSpawnedInWave = 0
        zombiesToSpawnInWave = 0
        spawnTimer = 0
    }
}
