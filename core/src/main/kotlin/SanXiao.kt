package game.kt

import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import me.game.sanxiao.Assests

/**
 * Created by wangbo on 2016/10/20.
 */

abstract class MyGame() : Game() {
    lateinit var batch: SpriteBatch
}

class SanXiao : MyGame() {

    override fun create() {
        batch = SpriteBatch()
        Assests.init()
        setScreen(MainMenuScreen(this))
    }

    override fun render() = super.render()
}