package com.android.internal.test.repository

import com.android.internal.test.api.RetrofitInstance
import com.android.internal.test.model.*
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.Query

class Repository {


    suspend fun login(username: String, password: String,redirect: Boolean ):Response<ResponseBody> {
        return RetrofitInstance.api.login(username, password,redirect)
    }

    suspend fun getCategory(key: String, p:Int): Response<List<Category>> {
        return RetrofitInstance.api.getCategory(key,p)
    }
    suspend fun getProcess(key: String,  f: String): Response<List<Process>> {
        return RetrofitInstance.api.getProcess(key,f)
    }

    suspend fun getUser(key: String): Response<UserInfo> {
        return RetrofitInstance.api.getUser(key)
    }

    suspend fun getContract(key: String, processId: String): Response<Contract> {
        return RetrofitInstance.api.getContract(key, processId)
    }

    suspend fun postSubmit(key: String, token: String, processId: String): Response<String> {
        return RetrofitInstance.api.postSubmit(key,token,processId)
    }



}