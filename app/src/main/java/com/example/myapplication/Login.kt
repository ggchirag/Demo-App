package com.example.myapplication

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.firebase.database.*

class Login : AppCompatActivity() {

    private var mDatabase: DatabaseReference? = null
    private var mFirebaseInstance: FirebaseDatabase? = null
    lateinit var phone_number: EditText
    lateinit var password:EditText
    lateinit var progressDialog:ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.title = "Login"

        toolbar.navigationIcon = resources.getDrawable(R.drawable.back_arrow)

        toolbar.setNavigationOnClickListener { onBackPressed() }

        mFirebaseInstance = FirebaseDatabase.getInstance()
        mDatabase = mFirebaseInstance!!.getReference("test")

        phone_number = findViewById(R.id.phone_number)
        password = findViewById(R.id.password)

        findViewById<TextView>(R.id.sign_up_text).setOnClickListener {
            val intent = Intent(this@Login, SignUp::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.login).setOnClickListener {

            if (phone_number.text.toString().isEmpty() || password.text.toString().isEmpty()){
                Toast.makeText(this@Login, "Please Enter all Fields", Toast.LENGTH_SHORT).show()
            }
            else if(phone_number.text.toString().length<10){
                Toast.makeText(this@Login, "Please Enter correct phone Number", Toast.LENGTH_SHORT).show()
            }
            else{
                login()
            }
        }

    }

    private fun login() {
        progressDialog = ProgressDialog(this@Login)
        progressDialog.setMessage("Loading")
        progressDialog.show()

        mDatabase?.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.hasChild(phone_number.text.toString())){
                    if (snapshot.child(phone_number.text.toString()).child("password").value?.equals(password.text.toString())!!){
                        progressDialog.hide()
                        publicClass.Phone = phone_number.text.toString()
                        var intent:Intent = Intent(this@Login,Home::class.java)
                        startActivity(intent)
                    }
                    else{
                        progressDialog.hide()
                    }
                }
                else {
                    Toast.makeText(this@Login, "Please Enter valid Details", Toast.LENGTH_SHORT).show()
                    progressDialog.hide()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                progressDialog.hide()
            }
        })

    }
}