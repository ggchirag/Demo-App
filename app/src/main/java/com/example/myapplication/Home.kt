package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        supportActionBar?.hide()
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.title = "Home"

        toolbar.navigationIcon = resources.getDrawable(R.drawable.back_arrow)

        toolbar.setNavigationOnClickListener { onBackPressed() }

        val usersFragment=UsersFragment()
        val myAccount=MyAccountFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentArea,usersFragment)
            commit()
        }

        findViewById<BottomNavigationView>(R.id.bottomNavigationView).setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.Users->{supportFragmentManager.beginTransaction().apply {
                    replace(R.id.fragmentArea,usersFragment)
                    commit()
                }}
                R.id.MyAccount->{supportFragmentManager.beginTransaction().apply {
                    replace(R.id.fragmentArea,myAccount)
                    commit()
                }}
            }
            true
        }
    }
}