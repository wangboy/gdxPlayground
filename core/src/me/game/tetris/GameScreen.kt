package me.game.tetris

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.OrthographicCamera

/**
 * Created by wangbo on 2016/10/25.
 */

class GameScreen : ScreenAdapter(), InputProcessor {


    val cam: OrthographicCamera = OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT)

    init {
        cam.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0f)

        Gdx.input.inputProcessor = this
    }

    override fun render(delta: Float) {
        glClear()

        cam.update()

        with(batch) {
            projectionMatrix = cam.combined
            begin()
            draw(Assets.bg, 0f, 0f, WORLD_WIDTH, WORLD_HEIGHT)
            end()
        }
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
//        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
        log(this, " touchUp $screenX _ $screenY")
        return false
    }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
//        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
        log(this, " mouseMoved $screenX _ $screenY")
        return false
    }

    override fun keyTyped(character: Char): Boolean {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun scrolled(amount: Int): Boolean {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun keyUp(keycode: Int): Boolean {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
//        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
        log(this, " touchDragged $screenX _ $screenY")
        return false
    }

    override fun keyDown(keycode: Int): Boolean {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}