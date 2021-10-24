package com.android.internal.test

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.internal.test.repository.Repository
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun login(view: View) {
        var textView: TextView = findViewById(R.id.textView)
        var button: Button = findViewById(R.id.button)
        var username: EditText = findViewById(R.id.username)
        var password: EditText = findViewById(R.id.password)
        val text1: TextView = findViewById(R.id.text1)
        val text2: TextView = findViewById(R.id.text2)
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)



        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.login(username.text.toString(), password.text.toString(), false)
        viewModel.myResponse1.observe(this, Observer { response ->
                if (response.isSuccessful) {
                    //Utilisateur connecté

                    Log.d("LoginSucc", response.headers().values("Set-Cookie").toString())

                    //Valeur qui contient les Headers renvoyées lors d'une connexion établie
                    val setCookies: List<String> = response.headers().values("Set-Cookie")

                    //Récuperer le jsession d'indice 1 et le token d'indice 2 du headers Set-Cookie
                    val jsession = setCookies[1].split(";")[0]
                    val token = setCookies[2].split(";")[0]


                    //Création d'un sharedPreferences qui permet de stocker le token et jsession
                    val sharedP = getSharedPreferences("userDetails", Context.MODE_PRIVATE)
                    val editor = sharedP.edit()
                    editor.apply {
                        putString("JSESSIONID", jsession)
                        putString("X-Bonita-API-Token", token)
                    }.apply()

                    Log.d("LoginSucc", response.headers().toString())
                    Log.d("LoginSucc", "Connecté")

                    val snackbar: Snackbar = Snackbar.make(view, "Vous êtes connecté!",
                            Snackbar.LENGTH_LONG)
                    val snackbarView: View = snackbar.getView()
                    snackbarView.setBackgroundColor(resources.getColor(R.color.yellow))
                    snackbar.show()


                    //Requête de récupération du userId
                    viewModel.getUser(jsession+";"+token)
                    viewModel.myUserId.observe(this, Observer { response ->
                        if (response.isSuccessful) {
                            //Succès de la requête UserId

                            Log.d("UserSucc",response.body()!!.user_id)

                            //Ajouter la valeur de user_id au SharedPreferences créé
                            val editor = sharedP.edit()
                            editor.apply {
                                putString("user_id",response.body()!!.user_id)
                            }.apply()

                           Log.d("UserSucc", response.code().toString())
                           Log.d("UserSucc", response.body().toString())

                            //Passage à CategoryActivity
                            val intent = Intent(this@MainActivity, CategoryActivity::class.java)
                            startActivity(intent)



                        } else {
                            //Echec de la requête UserId
                            Log.d("UserFail", "échec")
                            Log.d("UserFail", response.code().toString())
                            textView.setText("Pas de userId retourné")
                        }
                    })


                } else {
                    //Utilisateur non connecté

                    Log.d("LoginFail", "non Connecté")
                    val snackbar: Snackbar = Snackbar.make(view, "Veillez vérifier vos coordonnées entrées.",
                            Snackbar.LENGTH_LONG)
                    val snackbarView: View = snackbar.getView()
                    snackbarView.setBackgroundColor(resources.getColor(R.color.red))
                    snackbar.show()

                }


            })
        }


}