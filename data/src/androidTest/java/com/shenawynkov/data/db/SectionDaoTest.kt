package com.shenawynkov.data.db

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.runner.RunWith
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After

import org.junit.Before
import org.junit.Test
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Rule


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class SectionDaoTest {


    private lateinit var database: SectionDatabase
    private lateinit var doa: SectionDao

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            SectionDatabase::class.java
        ).allowMainThreadQueries()
            .build()

        doa = database.sectionDoa()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun insertAndReadSection() = runBlockingTest {
        val section = SectionEntity("1", "a", "ss", "DF")
        doa.saveSection(section)
        val result=doa.getSection("1")
        assertThat(result).isEqualTo(section)

    }
    @Test
    fun readAllSection() = runBlockingTest {
        val section = SectionEntity("1", "a", "ss", "DF")
        val section2 = SectionEntity("2", "2", "dwd", "asd")

        doa.saveSection(section)
        doa.saveSection(section2)
        val result=doa.getAllSections()
        assertThat(result).contains(section)
        assertThat(result).contains(section2)


    }

}