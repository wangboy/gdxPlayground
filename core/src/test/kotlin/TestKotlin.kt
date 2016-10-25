package test.kotlin

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.ui.Image

/**
 * Created by wangbo on 16/7/15.
 */


class TestKotlin : ApplicationAdapter() {
    lateinit var batch: SpriteBatch
    lateinit var img: Image

    override fun create() {
        batch = SpriteBatch()
        img = Image(Texture("badlogic.jpg"))
        img.setSize(480.toFloat(), 320.toFloat())
    }

    override fun render() {
        Gdx.gl.glClearColor(1f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        batch.begin()
        img.draw(batch, 1f)
        batch.end()
    }

}