package me.game.tetris

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.utils.Array

/**
 * Created by wangbo on 2016/10/25.
 */

const val BASE_WIDTH: Float = 1080f / 4
const val BASE_HEIGHT: Float = 1920f / 2

const val WORLD_WIDTH: Float = 100f
const val WORLD_HEIGHT: Float = WORLD_WIDTH * (BASE_HEIGHT / BASE_WIDTH)

class Assets {
    companion object {
        val atlas = TextureAtlas(Gdx.files.internal("./tetris/tetris.atlas"))
        val red: TextureAtlas.AtlasRegion = atlas.findRegion("01Red")

        val bg: Texture = Texture(Gdx.files.internal("./tetris/BG.jpg"))

        val scores: Array<TextureAtlas.AtlasRegion> = atlas.findRegions("Score")
        val sound: Sound = Gdx.audio.newSound(Gdx.files.internal("./tetris/click.wav"))


        fun init() {


        }
    }
}

fun playSound(sound: Sound) {
    sound.play(1f)
}

fun glClear() {
    Gdx.gl20.glClearColor(0f, 0f, 0f, 1f)
    Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT)
}

fun log(caller: Any, info: String) {
    Gdx.app.log(caller.toString(), info)
}