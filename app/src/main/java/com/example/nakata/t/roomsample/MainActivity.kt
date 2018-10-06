package com.example.nakata.t.roomsample

import android.arch.persistence.room.Room
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = Room.databaseBuilder(applicationContext,
                AppDatabase::class.java, "test-database")
                .allowMainThreadQueries()
                .build()

        test_button_1.setOnClickListener {
            val user = User().apply {
                name = "test1"
                age = 20
            }

            db.userDao.insert(user)

            refreshUserList()
        }

        test_button_2.setOnClickListener {
            val user = db.userDao.findFirest()
            user?.let {
                it.age = it.age + 1
                db.userDao.update(it.id, it.name ?: "No Name", it.age)
            }
            refreshUserList()
        }

        test_button_3.setOnClickListener {
            val user = db.userDao.findFirest()
            user?.let {
                db.userDao.delete(it)
            }
            refreshUserList()
        }

        refreshUserList()

    }


    private fun refreshUserList() {
        val users = db.userDao.findAll()

        for (user in users) {
            Log.e("test", "id: ${user.id}, name: ${user.name}, age: ${user.age}")
        }

        recycler_user_list.adapter = UserAdapter(applicationContext, users)
    }

    inner class UserAdapter(
            private val context: Context,
            private val userList: List<User>
    ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
            return ViewHolder(LayoutInflater
                    .from(context)
                    .inflate(R.layout.item_user, p0, false))
        }

        override fun getItemCount(): Int {
            return userList.size
        }

        override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
            val user = userList[p1]
            if (p0 is ViewHolder) {
                p0.setUser(user)
            }
        }

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            fun setUser(user: User) {
                itemView.findViewById<TextView>(R.id.text_id).text = "${user.id}"
                itemView.findViewById<TextView>(R.id.text_name).text = "${user.name}"
                itemView.findViewById<TextView>(R.id.text_age).text = "${user.age}"
            }

        }
    }
}
