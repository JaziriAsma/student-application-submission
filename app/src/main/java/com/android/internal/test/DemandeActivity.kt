package com.android.internal.test

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.internal.test.model.InputX
import com.android.internal.test.repository.Repository
import kotlinx.android.synthetic.main.activity_demande.*

class DemandeActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demande)

        val editLinearLayout = findViewById<LinearLayout>(R.id.editTextLinearLayout)
        val button = findViewById<Button>(R.id.button)

        //Récupération de l'idProcess
        val id: String = intent.getStringExtra("idProc")


        //Récupération du jsession et token
        val sharedPreferences = getSharedPreferences("userDetails", Context.MODE_PRIVATE)
        val j = sharedPreferences.getString("JSESSIONID", "")
        val t = sharedPreferences.getString("X-Bonita-API-Token", "")




        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.getContract(j+";"+t,intent.getStringExtra("idProc")+"")
        viewModel.myContract.observe(this, Observer { response->
            if(response.isSuccessful){
                Log.d("DemandeResponse", response.code().toString())
                Log.d("Demande", response.body()!!.inputs.toString())

                //Toast.makeText(this,"hello", Toast.LENGTH_SHORT).show()
                val res= response.body()!!.inputs[0].inputs
                res.forEach { element: InputX ->
                    Log.d("Name",element.name)
                    if (element.type=="TEXT" || element.type=="OFFSETDATETIME" || element.type=="BOOLEAN" || element.type=="LOCALDATE" ){

                        //Pour chacun de ces types, créer un TextView et un EditView

                        // Création de TextView dynamique
                        val textView = TextView(this)
                        textView.layoutParams= RelativeLayout.LayoutParams(
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT)
                        textView.setText(element.name)
                        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
                        textView.setTextColor(Color.parseColor("#3891D8"))
                        editLinearLayout ?.addView(textView)

                        // Création de EditView dynamique
                        val editText = EditText(this)
                        editText.setHint("")
                        editText.layoutParams = LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT)
                        editText.setTextColor(Color.parseColor("#12578E"))
                        editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18F)
                        editLinearLayout?.addView(editText)
                    }

                }


            }else
            {
                Log.d("ResponseSubmit", response.code().toString())
                Toast.makeText(this,"Erreur", Toast.LENGTH_SHORT).show()


            }
        })



        button?.setOnClickListener {
            //api de submit
            viewModel.postSubmit(j+";"+t,t+"",intent.getStringExtra("idProc")+"")
            viewModel.myResponse1.observe(this, Observer { response ->
                Log.d("Submit", response.code().toString())

                if (response.isSuccessful) {
                    Log.d("SubmitSucc", response.code().toString())

                } else {
                    Log.d("SubmitFail", response.code().toString())

                }
            })


            }



    }
}

