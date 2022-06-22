package com.example.myapplication

import android.app.ProgressDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.firebase.database.*
import com.google.firebase.storage.StorageReference

class SignUp : AppCompatActivity() {

    private var mDatabase: DatabaseReference? = null
    private var mFirebaseInstance: FirebaseDatabase? = null
    lateinit var name: EditText
    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var confirm_password: EditText
    lateinit var phone: EditText
    lateinit var progressDialog:ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

            supportActionBar?.hide()
            val toolbar = findViewById<Toolbar>(R.id.toolbar)
            toolbar.title = "Sign up"

            toolbar.navigationIcon = resources.getDrawable(R.drawable.back_arrow)

            toolbar.setNavigationOnClickListener { onBackPressed() }

        mFirebaseInstance = FirebaseDatabase.getInstance()
        mDatabase = mFirebaseInstance!!.getReference("test")

        name = findViewById(R.id.first_name)
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        phone = findViewById(R.id.phone_number)
        confirm_password = findViewById(R.id.confirm_password)

        findViewById<TextView>(R.id.login_text).setOnClickListener{
            onBackPressed()
        }

        findViewById<Button>(R.id.sign_up).setOnClickListener {
            if (name.text.toString().isEmpty()) {
                Toast.makeText(this@SignUp, "Please Enter First Name", Toast.LENGTH_SHORT).show()
            } else if (phone.text.toString().isEmpty()) {
                Toast.makeText(this@SignUp, "Please Enter Phone Number", Toast.LENGTH_SHORT).show()
            } else if (phone.text.toString().length < 10) {
                Toast.makeText(this@SignUp, "Please Enter valid Phone Number", Toast.LENGTH_SHORT)
                    .show()
            } else if (password.text.toString().isEmpty()) {
                Toast.makeText(this@SignUp, "Please Enter Password", Toast.LENGTH_SHORT).show()
            } else if (confirm_password.text.toString().isEmpty()) {
                Toast.makeText(this@SignUp, "Please Enter ConfirmPassword", Toast.LENGTH_SHORT)
                    .show()
            } else if (!password.text.toString().equals(confirm_password.text.toString())) {
                Toast.makeText(
                    this@SignUp,
                    "Password and Confirm Password did not Match",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Register()
            }
        }
    }

    fun Register() {
        progressDialog = ProgressDialog(this@SignUp)
        progressDialog.setMessage("Loading")
        progressDialog.show()

        mDatabase?.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.hasChild(phone.text.toString())){
                    Toast.makeText(this@SignUp, "User already Exist", Toast.LENGTH_SHORT).show()
                    progressDialog.hide()
                }
                else {
                    val user: user = user(
                        name.text.toString(),
                        phone.text.toString(),
                        email.text.toString(),
                        password.text.toString(),
                    )
                    Toast.makeText(this@SignUp, "User Created", Toast.LENGTH_SHORT).show()
                    mDatabase!!.child(phone.text.toString()).setValue(user)
                    progressDialog.hide()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                progressDialog.hide()
            }
        })
    }
}