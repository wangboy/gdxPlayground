package  game.kt

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector3
import java.util.*


/**
 * Created by wangbo on 2016/10/20.
 */

interface WorldListener {
    fun onMatch(matches: Set<Cube>)
}

enum class WorldState {
    STATIC,
    MOVING,
    MATCHING
}

class World(var game: MyGame) {

    lateinit var matrix: Array<Array<Cube>> //TODO
    lateinit var cpMatrix: Array<Array<Cube>> //TODO

    var dragCubes: Array<Cube?> = kotlin.arrayOfNulls<Cube>(2)
    var dragDirection: DragDirection = DragDirection.DRAG_UP

    var isMoving: Boolean = false
    var needMatching: Boolean = false

    var state: WorldState = WorldState.STATIC

    var match: MutableSet<Cube> = mutableSetOf()
    var newCubes: MutableSet<Cube> = mutableSetOf()

    lateinit var listner: WorldListener

    init {
        matrix = Array(7, {
            var i = it
            Array(7, {
                var cube: Cube = Cube(i = i, j = it, color = getColor(MathUtils.random(Assets.cubes.size - 1)))
                cube.update(0f)
                cube.y += MATRIX_OFFSET_Y
                cube.state = CubeState.FALL
                cube
            })
        })

    }


    companion object {
        fun getPosition(i: Int, j: Int): FloatArray = floatArrayOf(MATRIX_OFFSET_X + i * CUBE_WIDTH, MATRIX_OFFSET_Y + j * CUBE_WIDTH)
        fun getMatrixPosition(x: Float, y: Float): IntArray = intArrayOf(((x - MATRIX_OFFSET_X) / CUBE_WIDTH).toInt(), ((y - MATRIX_OFFSET_Y) / CUBE_WIDTH).toInt())
    }

    fun printMatrix(info: String) {
        println("============ $info =====================")

        var blank: String = "          "
        for (j in (0..6).reversed()) {
            for (i in (0..6)) {
                val cube = matrix[i][j]
                var out: StringBuilder = StringBuilder("[${cube.color}]")
                if (match.contains(cube)) {
                    out.append("--")
                }
                out.append(blank)
                out.delete(10, out.length)
                print(out)
            }
            println()
        }
//        matrix.forEach {
//            it.toList().reversed().forEach {
//                var out: StringBuilder = StringBuilder("[${it.color}]")
//                if (this@World.match.contains(it)) {
//                    out.append("--")
//                }
//                out.append(blank)
//                out.delete(10, out.length)
//                print(out)
//            }
//            println()
//        }
    }

    fun genCube(): Cube = Cube(color = getColor(MathUtils.random(Assets.cubes.size - 1)))

    fun update(delta: Float) {
        if (needMatching) {
            needMatching = false
            var result = checkMatch()
            if (result) {
                clearDrag()
            } else {
                dragCubes[0]?.apply { cancelDrag() }
            }
        }

        if (this.state != WorldState.MATCHING) {
            var moving = false
            matrix.forEach {
                it.forEach {
                    var state = it.update(delta)
                    if (state != CubeState.STATIC) moving = true
                }
            }

            if (isMoving && !moving) {
                needMatching = true
            }
            if (moving) {
                isMoving = moving
                state = WorldState.MOVING
            } else {
                state = WorldState.STATIC
            }
        }
    }

    fun debug(src: Cube, des: Cube) = Gdx.app.log(this.toString(), " switch \nsrc = $src \ndes = $des")

    private fun clearDrag() = Arrays.setAll(dragCubes) { null }

    private fun cancelDrag() {
        var src = dragCubes[0]
        var des = dragCubes[1]

        if (src == null || des == null) return

        when (dragDirection) {
            DragDirection.DRAG_DOWN -> {
                src.state = CubeState.UP
                des.state = CubeState.FALL
                src.j++
                des.j--
            }
            DragDirection.DRAG_UP -> {
                src.state = CubeState.FALL
                des.state = CubeState.UP
                src.j--
                des.j++
            }
            DragDirection.DRAD_LEFT -> {
                src.state = CubeState.RIGHT
                des.state = CubeState.LEFT
                src.i++
                des.i--
            }
            DragDirection.DRAG_RIGHT -> {
                src.state = CubeState.LEFT
                des.state = CubeState.RIGHT
                src.i--
                des.i++
            }
        }

        matrix[src.i][src.j] = src
        matrix[des.i][des.j] = des

        clearDrag()
    }

    fun click(position: Vector3) {
        var clkIndex = World.getMatrixPosition(position.x, position.y)
        if (clkIndex[0] < 0
                || clkIndex[0] > 6
                || clkIndex[1] > 6
                || clkIndex[1] < 0) {
            isMoving = false
            return
        }

        var clickCube = matrix[clkIndex[0]][clkIndex[1]]
        if (dragCubes[0] != null) {
            //TODO
        } else {
            //TODO
        }
    }

    private fun checkMatch(): Boolean {
        return false
    }

    fun isTouchable(): Boolean {
        return false
    }

    fun touchDrag(touchSrc: Vector3, touchDes: Vector3) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.

    }

}