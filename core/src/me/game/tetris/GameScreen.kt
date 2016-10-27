package me.game.tetris

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.glutils.ShaderProgram

/**
 * Created by wangbo on 2016/10/25.
 */

class GameScreen : ScreenAdapter(), InputProcessor {

    val cam: OrthographicCamera = OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT)
//    var vertexShader: String
//    var fragmentShader: String
//    var shader: ShaderProgram

    init {
        cam.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0f)

        Gdx.input.inputProcessor = this

//        vertexShader = """
//        attribute vec4 a_position;
//        attribute vec4 a_color;
//        attribute vec2 a_texCoord0;
//        uniform mat4 u_projTrans;
//        varying vec4 v_color;
//        varying vec2 v_texCoords;
//
//        void main()
//        {
//           v_color = a_color;
//           v_texCoords = a_texCoord0;
//           gl_Position =  u_projTrans * a_position;
//        }
//        """
//
//        fragmentShader = """
//        #ifdef GL_ES
//        #define LOWP lowp
//        precision mediump float;
//        else
//        #define LOWP
//        #endif
//        varying LOWP vec4 v_color;
//        varying vec2 v_texCoords;
//        uniform sampler2D u_texture;
//        void main()
//        {
//          gl_FragColor = v_color * texture2D(u_texture, v_texCoords).a;
//        }
//        """

//        shader = ShaderProgram(vertexShader, fragmentShader)
    }

    override fun render(delta: Float) {
        glClear()

        cam.update()

        with(batch) {
            projectionMatrix = cam.combined
            begin()
            draw(Assets.bg, 0f, 0f, WORLD_WIDTH, WORLD_HEIGHT)
            draw(Assets.cube, WORLD_WIDTH / 2, WORLD_HEIGHT / 2, Assets.cube.regionWidth.toFloat(), Assets.cube.regionWidth.toFloat())

            this.shader = Shaders.shader
            var sprite: Sprite = Sprite(Assets.cube)
            sprite.color = Color.BLUE
            draw(sprite, WORLD_WIDTH / 3, WORLD_HEIGHT / 3, Assets.cube.regionWidth.toFloat(), Assets.cube.regionWidth.toFloat())
            sprite.color = Color.GOLD
            draw(sprite, WORLD_WIDTH / 4, WORLD_HEIGHT / 4, Assets.cube.regionWidth.toFloat(), Assets.cube.regionWidth.toFloat())
            this.shader = null
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