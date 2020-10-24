package com.example.lab6

import android.os.AsyncTask
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var personDao: PersonDao
    private lateinit var adapter: MyAdapter
    private lateinit var allPersons: MutableList<ListItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.my_toolbar))

        personDao = AppDatabase.getInstance(applicationContext).getPersonDao()
        allPersons = GetAllPerson().execute().get()

        adapter = MyAdapter(allPersons)

        rcView.adapter = adapter
        rcView.hasFixedSize()
        rcView.layoutManager = LinearLayoutManager(this)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_buttons, menu)
        return true
    }

    inner class GetAllPerson: AsyncTask<Unit, Unit, MutableList<ListItem>>() { // async заменен на воркер
        override fun doInBackground(vararg params: Unit?): MutableList<ListItem> {
            return personDao.getAllPersons()
        }
    }

    inner class InsertNewPerson: AsyncTask<ListItem, Unit, Unit>() {
        override fun doInBackground(vararg params: ListItem?) {
            personDao.insertIntoPerson(params[0]!!)
        }

    }

    inner class DeletePerson: AsyncTask<ArrayList<Int>, Unit, Unit>() {
        override fun doInBackground(vararg params: ArrayList<Int>?) {
            personDao.deleteByIdList(params[0]!!)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        return if (id == R.id.action_add) { // if Ill press add button
            InsertNewPerson().execute(ListItem(image_id = R.drawable.girl, titleText = "Student ${Random.nextInt(10, 100)}", contentText = "Akino Girl"))
            allPersons.clear()
            allPersons.addAll(GetAllPerson().execute().get())
            adapter.notifyDataSetChanged()
            true
        } else if (id == R.id.action_delete) { // if ill press delete button
            if (adapter.checkedItems.size > 0) {
                DeletePerson().execute(adapter.checkedItems)
                allPersons.clear()
                allPersons.addAll(GetAllPerson().execute().get())
                adapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this@MainActivity, "U didn't check an item", Toast.LENGTH_SHORT).show()
            }
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }
}