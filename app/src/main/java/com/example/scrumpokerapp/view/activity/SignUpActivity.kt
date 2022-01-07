package com.example.scrumpokerapp.view.activity

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.scrumpokerapp.R
import com.example.scrumpokerapp.firebase.FirebaseKeysValues
import com.example.scrumpokerapp.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SignUpActivity: AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etUserName: EditText
    private lateinit var btnSignUp: Button
    private lateinit var spRole: Spinner
    private lateinit var roleSelected: String

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        mAuth = FirebaseAuth.getInstance()

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        etUserName = findViewById(R.id.etUserName)
        btnSignUp = findViewById(R.id.btnSignUp)
        spRole = findViewById(R.id.spRole)
        roleSelected = ""

        ArrayAdapter.createFromResource(
            this,
            R.array.role_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spRole.adapter = adapter
        }



        btnSignUp.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            val userName = etUserName.text.toString()
            val roleSelected = spRole.selectedItemPosition

            signUp(email, password, userName, roleSelected)
        }
    }

    private fun signUp(email: String, password: String, userName: String, roleSelected: Int) {
        //Endpoint auth
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    addUserToDataBase(email, password, mAuth.currentUser?.uid!!, userName, roleSelected)

                    val intent = Intent(this@SignUpActivity, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@SignUpActivity,"Algo salio mal", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun addUserToDataBase(
        email: String,
        password: String,
        uid: String?,
        userName: String,
        roleSelected: Int
    ) {

        //Endpoint post/user
        val db = Firebase.firestore

        db.collection(FirebaseKeysValues().USER_COLLECTION_KEY)
            .add(User(email,password,uid,roleSelected,userName))
            .addOnSuccessListener { documentReference ->
                Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error adding document", e)
            }
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        roleSelected = parent.getItemAtPosition(pos).toString()
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
    }
}