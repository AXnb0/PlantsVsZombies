package com.game.pvz

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView

class GameView(context: Context) : SurfaceView(context), SurfaceHolder.Callback {
    
    private val gameEngine: GameEngine
    private var gameThread: GameThread? = null
    private val paint = Paint()
    
    init {
        holder.addCallback(this)
        gameEngine = GameEngine(context)
        isFocusable = true
    }
    
    override fun surfaceCreated(holder: SurfaceHolder) {
        gameThread = GameThread(holder)
        gameThread?.isRunning = true
        gameThread?.start()
    }
    
    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        gameEngine.setScreenSize(width, height)
    }
    
    override fun surfaceDestroyed(holder: SurfaceHolder) {
        var retry = true
        gameThread?.isRunning = false
        while (retry) {
            try {
                gameThread?.join()
                retry = false
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }
    
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                gameEngine.handleTouch(event.x, event.y, event.action)
            }
        }
        return true
    }
    
    fun pause() {
        gameEngine.pause()
    }
    
    fun resume() {
        gameEngine.resume()
    }
    
    private fun update() {
        gameEngine.update()
    }
    
    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        
        // 清空画布
        canvas.drawColor(Color.rgb(144, 238, 144))
        
        // 绘制游戏内容
        gameEngine.draw(canvas, paint)
    }
    
    inner class GameThread(private val surfaceHolder: SurfaceHolder) : Thread() {
        var isRunning = false
        private val targetFPS = 60
        private val targetTime = (1000 / targetFPS).toLong()
        
        override fun run() {
            var startTime: Long
            var timeMillis: Long
            var waitTime: Long
            
            while (isRunning) {
                startTime = System.currentTimeMillis()
                var canvas: Canvas? = null
                
                try {
                    canvas = surfaceHolder.lockCanvas()
                    synchronized(surfaceHolder) {
                        update()
                        canvas?.let { draw(it) }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    canvas?.let {
                        try {
                            surfaceHolder.unlockCanvasAndPost(it)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
                
                timeMillis = System.currentTimeMillis() - startTime
                waitTime = targetTime - timeMillis
                
                try {
                    if (waitTime > 0) {
                        sleep(waitTime)
                    }
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
    }
}
