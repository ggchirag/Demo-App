package com.example.myapplication

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView


class userAdapter(userList: ArrayList<user>, context: Context) :
    RecyclerView.Adapter<userAdapter.ItemViewHolder>() {
    private val userArrayList: ArrayList<user> = userList
    private val mContext: Context? = context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): userAdapter.ItemViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: userAdapter.ItemViewHolder, position: Int) {
        holder.name.text = "Name : "+userArrayList.get(position).name
        holder.number.text = "Phone : "+userArrayList.get(position).mobile
        holder.email.text = "Email : "+userArrayList.get(position).email

        holder.user.setOnClickListener{
            if (publicClass.Phone.equals(userArrayList.get(position).mobile)){
                val myAccount=MyAccountFragment()

                (mContext as AppCompatActivity).supportFragmentManager.beginTransaction().apply {
                    replace(R.id.fragmentArea,myAccount)
                    addToBackStack("temp")
                    commit()
                }
//                (mContext as Activity).findViewById<View>(R.id.fragmentArea)
            }
        }
    }

    override fun getItemCount(): Int {
        return userArrayList.size
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var name: TextView
        lateinit var number: TextView
        lateinit var email: TextView
        lateinit var user: LinearLayout

        init {
            name = itemView.findViewById(R.id.Name)
            number = itemView.findViewById(R.id.Phone)
            email = itemView.findViewById(R.id.Email)
            user = itemView.findViewById(R.id.user)
        }
    }
}
