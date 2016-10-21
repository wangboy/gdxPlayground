package  game.kt

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector3
import game.kt.MyGame
import java.util.*

/**
 * Created by wangbo on 2016/10/20.
 */

class GameScreen(var game: MyGame) : ScreenAdapter(), InputProcessor {

    var camera: OrthographicCamera = OrthographicCamera(WORLD_WIDTH.toFloat(), WORLD_HEIGHT)
    var world: World = World(game)
    var worldRenderer: WorldRenderer = WorldRenderer(game, world)
    var touchSrc: Vector3 = Vector3()
    var touchDes: Vector3 = Vector3()

    fun update(delta: Float) {
        world.update(delta)
    }

    fun draw() {
        Gdx.gl20.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT)

        camera.update()
        with(game.batch) {
            projectionMatrix = camera.combined
            begin()
            draw(Assets.bg, 0f, 0f, WORLD_WIDTH.toFloat(), WORLD_HEIGHT)
            end()
        }
        worldRenderer.render()
    }

    override fun render(delta: Float) {
        update(delta)
        draw()
    }

    override fun dispose() = super.dispose()

    /* ============================================= */
    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        if (world.isTouchable()) {
            camera.unproject(touchDes.set(screenX.toFloat(), screenY.toFloat(), 0f))

            if (touchDes.cpy().sub(touchSrc).len() < CUBE_WIDTH / 2) {
                world.click(touchSrc)
            }
        }
        return false
    }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean = false

    override fun keyTyped(character: Char): Boolean {
        Gdx.app.log(this.toString(), " typed char = $character")
        return false
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        if (world.isTouchable()) {
            this.camera.unproject(touchSrc.set(screenX.toFloat(), screenY.toFloat(), 0f))
        }

        Gdx.app.log(this.toString(), " touchDown at ${touchSrc.x}_${touchSrc.y}, pointer = $pointer, button = $button")
        return false
    }

    override fun scrolled(amount: Int): Boolean = false

    override fun keyUp(keycode: Int): Boolean = false

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        if (world.isTouchable()) {
            camera.unproject(touchDes.set(screenX.toFloat(), screenY.toFloat(), 0f))
            if (touchDes.cpy().sub(touchSrc).len() > CUBE_WIDTH / 2) {
                world.touchDrag(touchSrc, touchDes)
            }
        }
        return false
    }

    override fun keyDown(keycode: Int): Boolean = false

}