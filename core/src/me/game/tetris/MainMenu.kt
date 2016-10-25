package me.game.tetris

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.Align
import kotlin.test.todo

/**
 * Created by wangbo on 2016/10/25.
 */

class MainMenu : ScreenAdapter() {

    val cam = OrthographicCamera(BASE_WIDTH, BASE_HEIGHT)
    val touchPoint: Vector3 = Vector3()
    val playerBounds: Rectangle = Rectangle(0f, BASE_HEIGHT / 3, BASE_WIDTH, BASE_HEIGHT / 3)
    val shapeRenderer: ShapeRenderer = ShapeRenderer()
    val font: BitmapFont = BitmapFont()

    init {
        cam.position.set(BASE_WIDTH / 2, BASE_HEIGHT / 2, 0f)
        Gdx.input.inputProcessor = null
    }

    fun update() {
        if (Gdx.input.justTouched()) {
            cam.unproject(touchPoint.set(Gdx.input.x.toFloat(), Gdx.input.y.toFloat(), 0f))
            Gdx.app.log(this.toString(), " touch ${touchPoint.x}_${touchPoint.y}")

            if (playerBounds.contains(touchPoint.x, touchPoint.y)) {
                playSound(Assets.sound)
                Tetris.game.screen = GameScreen()
            }
        }
    }

    fun draw() {
        glClear()

        cam.update()

        with(batch) {
            projectionMatrix = cam.combined
            disableBlending()
            begin()
            font.draw(this, " Click to Start!! ", 0f, BASE_HEIGHT / 2, BASE_WIDTH, Align.center, true)
            end()
        }

        with(shapeRenderer) {
            projectionMatrix = batch.projectionMatrix
            begin(ShapeRenderer.ShapeType.Line)
            color = Color.RED
            rect(playerBounds.x, playerBounds.y, playerBounds.width, playerBounds.height)
            end()
        }


    }


    override fun render(delta: Float) {
        todo { println(" =============== ") }
        this.update()
        this.draw()
    }

}