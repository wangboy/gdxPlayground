package game.kt

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion
import com.badlogic.gdx.utils.Array

/**
 * Created by wangbo on 2016/10/20.
 */

const val BASE_WIDTH: Float = (1080 / 4).toFloat()
const val BASE_HEIGHT: Float = (1920 / 4).toFloat()

const val WORLD_WIDTH: Int = 73
const val WORLD_HEIGHT = WORLD_WIDTH * (BASE_HEIGHT / BASE_WIDTH)

const val CUBE_WIDTH = 10

const val MATRIX_OFFSET_X: Float = 1f

const val MATRIX_OFFSET_Y: Float = 28f

const val CUBE_FALL_SPEED = 40f


enum class DragDirection {
    DRAD_LEFT,
    DRAG_RIGHT,
    DRAG_UP,
    DRAG_DOWN,
}

class Assets {
    companion object {
        lateinit var red: AtlasRegion
        lateinit var yellow: AtlasRegion
        lateinit var green: AtlasRegion
        lateinit var purple: AtlasRegion
        lateinit var blue: AtlasRegion

        lateinit var cubes: Array<AtlasRegion>
        lateinit var moneys: Array<AtlasRegion>
        lateinit var times: Array<AtlasRegion>
        lateinit var scores: Array<AtlasRegion>

        lateinit var clickSound: Sound
        lateinit var gameMusic: Music
        lateinit var bg: Texture

        fun init() {
            var atlas = TextureAtlas(Gdx.files.internal("./sanxiao/sanxiao.atlas"))
            with(atlas) {
                red = findRegion("01Red")
                yellow = findRegion("02Yellow")
                green = findRegion("03Green")
                blue = findRegion("04Blue")
                purple = findRegion("05Purple")

                moneys = findRegions("Money")
                times = findRegions("Time")
                scores = findRegions("Score")
            }

            cubes = Array()
            cubes.addAll(red, yellow, green, blue, purple)

            bg = Texture(Gdx.files.internal("./sanxiao/BG.jpg"))

            clickSound = Gdx.audio.newSound(Gdx.files.internal("./sanxiao/clicl.wav"))
            gameMusic = Gdx.audio.newMusic(Gdx.files.internal("./sanxiao/music.mp3"))
        }

        fun playSound(sound: Sound) = sound.play(1f)
    }
}

enum class Color(val code: Int) {
    RED(0),

    YELLOW(1),

    GREEN(2),

    BLUE(3),

    PURPLE(4)

}

fun getColor(code: Int): Color = Color.values().first { it.code === code }


