package com.example.scrumpokerapp.view.activity

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.scrumpokerapp.R
import com.example.scrumpokerapp.firebase.FirebaseKeysValues
import com.example.scrumpokerapp.persistance.UserProfile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogIn: Button
    private lateinit var tvSignUp: TextView

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnLogIn = findViewById(R.id.btnLogIn)
        tvSignUp = findViewById(R.id.tvSignUp)

        tvSignUp.setOnClickListener{
            val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(intent)
        }

        btnLogIn.setOnClickListener{
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            logIn(email, password)
        }
    }

    private fun logIn(email: String, password: String) {
        //Endpoint auth
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    setUserProfile()

                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@LoginActivity,"Error de usuario y contraseña", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun setUserProfile() {
        val db = Firebase.firestore

        //Endpoint get/{Id}
        db.collection(FirebaseKeysValues().USER_COLLECTION_KEY)
            .whereEqualTo("uid", FirebaseAuth.getInstance().currentUser?.uid.toString())
            .limit(1)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    UserProfile.setUserProfile(
                        document.documents[0].get(FirebaseKeysValues().EMAIL_KEY).toString(),
                        document.documents[0].get(FirebaseKeysValues().PASSWORD_KEY).toString(),
                        document.documents[0].get(FirebaseKeysValues().UID_KEY).toString(),
                        document.documents[0].get(FirebaseKeysValues().ROLE_KEY).toString().toInt(),
                        document.documents[0].get(FirebaseKeysValues().USER_NAME_KEY).toString()
                    )
                    Log.d(ContentValues.TAG, "DocumentSnapshot data: ${document.documents}")
                } else {
                    Log.d(ContentValues.TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "get failed with ", exception)
            }
    }


}