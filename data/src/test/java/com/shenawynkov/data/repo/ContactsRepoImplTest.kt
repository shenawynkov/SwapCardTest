package com.shenawynkov.data.repo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.shenawynkov.data.apiService.ApiService
import com.shenawynkov.data.sharedPref.PrefManger
import com.shenawynkov.domain.model.contact.*
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class ContactsRepoImplTest{
    private val apiService: ApiService = mockk()
    private val sharedPref: PrefManger = mockk()
    lateinit var SUT: ContactsRepoImpl


    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    @Before
    @Throws(Exception::class)
    fun setUp() {

        SUT = ContactsRepoImpl(apiService, sharedPref)
    }



    @Test
    fun getContacts_success_SingleObservableWitheDataReturned() {
        //Arrange
        val contact = ContactX(
            ArrayList(),
            "s",
            "dd",
            "wds",
            1,
            "dsadd",
            true,
            true,
            ArrayList(),
            true,
            "SS",
            "s",
            "ss",
            "sss",
            "sss",
            "sss",
            "sss",
            "ssssss",
            true,
            ArrayList(),
            "ssssss",
            true,
            "ss",
            "ss",
            ArrayList(),
            1,
            1,
            ArrayList()
        )
        val contacts = arrayListOf(contact)
        val expectedResult=Single.just(contacts as List<ContactX>)



        val contactResponse=ContactsResponse(arrayListOf(Contact(contact)),1,1,1,1)
        val baseResponse=BaseResponse<ContactsResponse>(1,"ss",2,contactResponse)

        val response = Single.just(baseResponse)

        //Act


        every {
            sharedPref.userId
        } returns "1"
        every {
            sharedPref.apiKey
        } returns "1"

        every {
            apiService.getContacts(any())
        } returns response

        val result=SUT.getContacts()
        //Assert
        Truth.assertThat(result.blockingGet()).isEqualTo(expectedResult.blockingGet())
    }





}