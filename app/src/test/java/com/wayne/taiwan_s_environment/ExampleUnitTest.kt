package com.wayne.taiwan_s_environment

import com.wayne.taiwan_s_environment.model.db.vo.Home
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val list = arrayListOf<Home>()
        list.add(Home(siteName = "2", county = "1", time = 20000))
        list.add(Home(siteName = "2", county = "1", time = 1000))
        list.add(Home(siteName = "12", county = "1", time = 30000))
        list.add(Home(siteName = "1", county = "2", time = 20000))
        list.add(Home(siteName = "1", county = "1", time = 1000))
        list.add(Home(siteName = "3", county = "1", time = 30000))

        for (i in list) {
            println("$i")
        }

        println("======")
        list.sort()

        for (i in list) {
            println("$i")
        }

        assertEquals(4, 2 + 2)
    }
}