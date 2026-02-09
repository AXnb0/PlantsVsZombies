package com.game.pvz.managers

import com.game.pvz.models.Plant
import com.game.pvz.models.Projectile
import com.game.pvz.models.Zombie
import java.util.concurrent.CopyOnWriteArrayList

class CollisionManager {
    
    fun checkCollisions(
        zombies: CopyOnWriteArrayList<Zombie>,
        plants: CopyOnWriteArrayList<Plant>,
        projectiles: CopyOnWriteArrayList<Projectile>
    ) {
        // 检查子弹与僵尸的碰撞
        projectiles.forEach { projectile ->
            zombies.forEach { zombie ->
                if (!projectile.shouldRemove() && projectile.intersects(zombie)) {
                    zombie.takeDamage(projectile.getDamage())
                    
                    // 如果是冰冻子弹，应用减速效果
                    if (projectile.isFrozenProjectile()) {
                        zombie.applySlow(180) // 3秒减速效果
                    }
                    
                    projectile.markForRemoval()
                }
            }
        }
        
        // 检查僵尸与植物的碰撞
        zombies.forEach { zombie ->
            var attacking = false
            
            plants.forEach { plant ->
                if (zombie.intersects(plant)) {
                    if (!zombie.isAttacking) {
                        zombie.startAttacking(plant)
                    }
                    attacking = true
                    return@forEach
                }
            }
            
            if (!attacking && zombie.isAttacking) {
                zombie.stopAttacking()
            }
        }
    }
}
