package com.gdx.client.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import me.game.sanxiao.SanXiao
import test.kotlin.TestKotlin

/**
 * Created by wangbo on 16/7/15.
 */
class DesktopLauncher {
    companion object {
        @JvmStatic fun main(args: Array<String>) {
            var config: LwjglApplicationConfiguration = LwjglApplicationConfiguration()
//            config.width = 480
//            config.height = 320
//            LwjglApplication(TestKotlin(), config)

            config.width = 320
            config.height = 480
            LwjglApplication(SanXiao(), config)
        }
    }
}