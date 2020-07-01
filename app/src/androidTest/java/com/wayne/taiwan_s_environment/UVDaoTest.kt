package com.wayne.taiwan_s_environment

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.wayne.taiwan_s_environment.model.db.AppDatabase
import com.wayne.taiwan_s_environment.model.db.dao.UVDao
import com.wayne.taiwan_s_environment.model.db.vo.UV
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.io.IOException

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class UVDaoTest {
    private lateinit var db: AppDatabase
    private lateinit var uvDao: UVDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        uvDao = db.uvDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertUVAllTest() {
        val list = arrayListOf<UV>()
        list.add(UV("site1", "","","","","","",1000))
        list.add(UV("site2", "","","","","","",2000))
        uvDao.insertUVAll(list)
        assertEquals(uvDao.getAll().size, 2)
    }

    @Test
    fun deleteByTimeTest() {
        val list = arrayListOf<UV>()
        list.add(UV("site1", "","","","","","",1000))
        list.add(UV("site1", "","","","","","",2000))
        uvDao.insertUVAll(list)
        uvDao.deleteByTime(1000)
        assertEquals(uvDao.getAll().size, 1)
    }

    @Test
    fun getAllNewestByCountyTest() {
        val county = "county"
        val list = arrayListOf<UV>()
        list.add(UV("site1", county,"","","0","","",1000))
        list.add(UV("site1", county,"","","0","","",2000))
        list.add(UV("site1", county,"","","1","","",3000))
        list.add(UV("site2", county,"","","1","","",2000))
        list.add(UV("site3", "${county}2","","","1","","",2000))
        uvDao.insertUVAll(list)
        var assert = ""
        for (uv in uvDao.getAllNewestByCounty(county)) {
            assert += uv.uvi
        }
        assertEquals(assert, "11")
    }

    @Test
    fun getMaxTimeTest() {
        val list = arrayListOf<UV>()
        list.add(UV("site1", "","","","0","","",1000))
        list.add(UV("site1", "","","","0","","",2000))
        list.add(UV("site2", "","","","1","","",3000))
        list.add(UV("site3", "","","","1","","",2000))
        uvDao.insertUVAll(list)

        assertEquals(uvDao.getMaxTime(), 3000)
    }
}