package com.android.internal.test

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.internal.test.adapter.ProcessAdapter
import com.android.internal.test.model.Process
import com.android.internal.test.repository.Repository
import kotlinx.android.synthetic.main.activity_process.*


class ProcessActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    //Initialisation du recyclerview au démarrage
    private val processAdapter by lazy { ProcessAdapter(this) }

    var process= ArrayList<Process>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_process)

        //Récupération du jsession, token et user_id
        val sharedPreferences = getSharedPreferences("userDetails", Context.MODE_PRIVATE)
        val j = sharedPreferences.getString("JSESSIONID", "")
        val t = sharedPreferences.getString("X-Bonita-API-Token", "")
        val u = sharedPreferences.getString("user_id", "")


        setupRecyclerview()

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.getProcess(j+";"+t, "user_id="+u)
        viewModel.myProcess.observe(this, Observer { response->
            if(response.isSuccessful){
                //Afficher les données dans le recyclerview
                response.body()?.let { processAdapter.setData(it) }

                Log.d("ProcessSucc", response.body().toString())
                Log.d("ProcessSucc", "C'est bon")

            }
            else{
                Log.d("ProcessFail", response.code().toString())
                Log.d("ProcessFail", "C'est pas bon")

            }

        })
    }



    //Méthode qui définit notre recyclerview
    private fun setupRecyclerview(){
        recyclerView.adapter=processAdapter
        recyclerView.layoutManager= LinearLayoutManager(this)
    }








}

