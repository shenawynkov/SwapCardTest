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
class SectionDetailDaoTest {


    private lateinit var database: SectionDatabase
    private lateinit var doa: SectionDetailDao

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            SectionDatabase::class.java
        ).allowMainThreadQueries()
            .build()

        doa = database.sectionDetailDoa()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun insertAndReadSection() = runBlockingTest {
        val section = SectionDetailEntity("1", "a", "ss")
        doa.saveSection(section)
        val result=doa.getSection("1")
        assertThat(result).isEqualTo(section)

    }


}