package com.android.internal.test.api

import com.android.internal.test.model.*
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface SimpleApi {

    //requête envoyée sous format key-value
    @FormUrlEncoded
    @POST("loginservice")
    suspend fun login(
            //@Field pr configurer les noms telque sur postman
            @Field("username") username: String?,
            @Field("password") password: String?,
            @Field("redirect") redirect: Boolean
            ):Response<ResponseBody>


    @GET("portal/custom-page/API/bpm/category")
    //@Header au niveau de postman contient jsession et token
    //@Query dans queryparams il y a p=0
    suspend fun getCategory(@Header("Cookie") key: String, @Query("p") p: Int ): Response<List<Category>>


    @GET("API/bpm/process?p=0&c=100&o=version%20desc&f=activationState=ENABLED")
    suspend fun getProcess(@Header("Cookie") key: String, @Query("f") f: String): Response<List<Process>>

    @GET("API/system/session/unusedId")
    suspend fun getUser(@Header("Cookie") key: String): Response<UserInfo>

    @GET("API/bpm/process/{processId}/contract")
    suspend fun getContract(@Header("Cookie") key: String,@Path("processId") processId: String): Response<Contract>



    @POST("API/bpm/process/{processId}/instantiation")
    suspend fun postSubmit(@Header("Cookie") key: String, @Header("X-Bonita-API-Token") token: String, @Path("processId") processId: String): Response<String>


    //@POST("API/bpm/process/{processId}/instantiation")
    //suspend fun postSubmit(@Header("Cookie") key: String, @Header("X-Bonita-API-Token") token: String, @Path("processId") processId: String, @Body body:String): Response<String>


    //@GET("API/system/session/unusedId")
    //suspend fun getUser(@Header("JSESSIONID") jsessionid: String,
      //                  @Header("X-Bonita-Token") token: String): Response<UserInfo>


    //@Headers("setCookies")
    //@GET("API/system/session/unusedId")
    //suspend fun getUser( jsessionid: String,
                            // token: String): Response<UserInfo>

    
}

