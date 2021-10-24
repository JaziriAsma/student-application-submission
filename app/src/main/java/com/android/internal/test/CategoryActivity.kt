package com.android.internal.test

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.internal.test.adapter.MyAdapter
import com.android.internal.test.repository.Repository
import kotlinx.android.synthetic.main.activity_category.*

class CategoryActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    //Initialisation du recyclerview
    private val myAdapter by lazy { MyAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        //Récupération du jsession et token
        val sharedPreferences = getSharedPreferences("userDetails", Context.MODE_PRIVATE)
        val j = sharedPreferences.getString("JSESSIONID", "")
        val t = sharedPreferences.getString("X-Bonita-API-Token", "")


        setupRecyclerview()

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.getCategory(j+";"+t, 0)
        viewModel.myCategory.observe(this, Observer { response->
            if(response.isSuccessful){
                //Afficher les données dans le recyclerview
                response.body()?.let { myAdapter.setData(it) }
            }
            else{
                Log.d("Response", response.code().toString())

            }

        })
    }

    //Méthode qui définit notre recyclerview
    private fun setupRecyclerview(){
        recyclerView.adapter=myAdapter
        recyclerView.layoutManager=LinearLayoutManager(this)
    }

    }

