package  game.kt

/**
 * Created by wangbo on 2016/10/20.
 */

enum class CubeState {
    STATIC,
    FALL,
    UP,
    RIGHT,
    LEFT
}
//
//const val STATIC = 0
//const val FALL = 1
//const val UP = 2
//const val RIGHT = 3
//const val LEFT = 4

data class Cube(var i: Int = 0, var j: Int = 0,
                var x: Float = 0f, var y: Float = 0f,
                var color: Color, var state: CubeState = CubeState.STATIC)

fun Cube.update(delta: Float): CubeState {
    var pos: FloatArray = World.getPosition(i, j)
    when (state) {
        CubeState.STATIC -> {
            x = pos[0]
            y = pos[1]
        }
        CubeState.FALL -> {
            y -= delta * CUBE_FALL_SPEED
            if (this.y < pos[1]) {
                this.y = pos[1]
                state = CubeState.STATIC
            }
        }
        CubeState.UP -> {
            this.y += delta * CUBE_FALL_SPEED
            if (this.y > pos[1]) {
                this.y = pos[1]
                state = CubeState.STATIC
            }
        }
        CubeState.RIGHT -> {
            this.x += delta * CUBE_FALL_SPEED
            if (this.x > pos[0]) {
                this.x = pos[0]
                state = CubeState.STATIC
            }
        }
        CubeState.LEFT -> {
            this.x -= delta * CUBE_FALL_SPEED
            if (this.x < pos[0]) {
                this.x = pos[0]
                state = CubeState.STATIC
            }
        }
    }
    return state
}
