package game.kt

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.Align
import game.kt.MyGame

/**
 * Created by wangbo on 2016/10/20.
 */

class MainMenuScreen(var game: MyGame) : ScreenAdapter() {
    var guiCam: OrthographicCamera
    var touchPoint: Vector3
    var playerBounds: Rectangle
    var shapeRenderer: ShapeRenderer
    var font: BitmapFont

    init {
        guiCam = OrthographicCamera(BASE_WIDTH, BASE_HEIGHT)
        guiCam.position.set(BASE_WIDTH / 2, BASE_HEIGHT / 2, 0f)

        playerBounds = Rectangle(0f, BASE_HEIGHT / 3, BASE_WIDTH, BASE_HEIGHT / 3)

        font = BitmapFont()

        shapeRenderer = ShapeRenderer()
        touchPoint = Vector3()

        Gdx.input.inputProcessor = null
    }


    fun update() {
        if (Gdx.input.justTouched()) {
            Gdx.app.log("main", "touched ${Gdx.input.x}_${Gdx.input.y}")
            guiCam.unproject(touchPoint.set(Gdx.input.x.toFloat(), Gdx.input.y.toFloat(), 0f))
            Gdx.app.log("main", "touched ${touchPoint.x}_${touchPoint.y}")

            if (playerBounds.contains(touchPoint.x.toFloat(), touchPoint.y.toFloat())) {
                Assets.playSound(Assets.clickSound)
                Gdx.app.log("main", "change to game")
//                game.scree TODO
            }
        }
    }

    fun draw() {
        Gdx.gl20.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT)

        guiCam.update()
        game.batch.projectionMatrix = guiCam.combined

        game.batch.disableBlending()

        game.batch.begin()

        font.draw(game.batch, "Click here to start!! ", 0f, BASE_HEIGHT / 2, BASE_WIDTH, Align.center, true)

        game.batch.end()

        with(shapeRenderer) {
            projectionMatrix = game.batch.projectionMatrix
            begin(ShapeRenderer.ShapeType.Line)
            color = Color.RED
            rect(playerBounds.x.toFloat(), playerBounds.y.toFloat(), playerBounds.width, playerBounds.height)
            end()
        }

    }

    override fun render(delta: Float) {
        update()
        draw()
    }

}