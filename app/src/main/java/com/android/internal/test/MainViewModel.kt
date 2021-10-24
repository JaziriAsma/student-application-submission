package com.android.internal.test

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.internal.test.model.*
import com.android.internal.test.repository.Repository
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response

//Faire le lien entre repository et l'activity
class MainViewModel(private val repository: Repository) : ViewModel() {

    val myResponse1: MutableLiveData<Response<ResponseBody>> = MutableLiveData()
    val myCategory: MutableLiveData<Response<List<Category>>> = MutableLiveData()
    val myProcess: MutableLiveData<Response<List<Process>>> = MutableLiveData()
    val myContract: MutableLiveData<Response<Contract>> = MutableLiveData()
    val mySubmit: MutableLiveData<Response<String>> = MutableLiveData()
    val myUserId: MutableLiveData<Response<UserInfo>> = MutableLiveData()



    fun login(username: String, password: String, redirect: Boolean) {
        viewModelScope.launch {
            val response = repository.login(username, password, redirect)
            myResponse1.value = response
        }
    }

    fun getCategory(key: String, p: Int) {
        viewModelScope.launch {
            val response = repository.getCategory(key, p)
            myCategory.value = response
        }
    }

    fun getProcess(key: String, f: String) {
        viewModelScope.launch {
            val response = repository.getProcess(key, f)
            myProcess.value = response
        }
    }


    fun getUser(key: String) {
        viewModelScope.launch {
            val response = repository.getUser(key)
            myUserId.value = response
        }
    }

    fun getContract(key: String, processId: String) {
        viewModelScope.launch {
            val response = repository.getContract(key, processId)
            myContract.value = response
        }
    }

    fun postSubmit(key: String, token: String, processId: String) {
        viewModelScope.launch {
            val response = repository.postSubmit(key, token, processId)
            mySubmit.value = response
        }

    }
}