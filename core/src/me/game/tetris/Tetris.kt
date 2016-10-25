package me.game.tetris

import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.g2d.SpriteBatch

/**
 * Created by wangbo on 2016/10/25.
 */
val batch: SpriteBatch = SpriteBatch()

class Tetris : Game() {

    companion object {
        lateinit var game: Tetris
    }

    init {
        game = this
    }

    override fun create() {
        setScreen(MainMenu())
    }

    override fun render() {
        super.render()
    }

}

