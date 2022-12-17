package com.mamunsproject.grocery_roomdb

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.grocery_add_dialouge.*

class MainActivity : AppCompatActivity(), GroceryAdapter.GroceryItemClickInterface {

    lateinit var itemRV: RecyclerView
    lateinit var addFAB: FloatingActionButton
    lateinit var list: List<GroceryItem>
    lateinit var groceryAdapter: GroceryAdapter
    lateinit var groceryViewModel: GroceryViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        init()

        itemRV = findViewById(R.id.idRvItems)
        addFAB = findViewById(R.id.idFabAdd)
        list = ArrayList<GroceryItem>()
        groceryAdapter = GroceryAdapter(list, this)

        itemRV.layoutManager = LinearLayoutManager(this)
        itemRV.adapter = groceryAdapter

        val groceryRepository = GroceryRepository(GroceryDatabase(this))
        val factory = GroceryViewModelFactory(groceryRepository)

        groceryViewModel = ViewModelProvider(this, factory).get(GroceryViewModel::class.java)

        groceryRepository.getAllItems().observe(this, Observer {
            groceryAdapter.list = it
            groceryAdapter.notifyDataSetChanged()
        })




        addFAB.setOnClickListener {
            openDialouge()

        }
    }


    fun init() {

    }

    private fun openDialouge() {
        Toast.makeText(applicationContext, "In the ", Toast.LENGTH_SHORT).show()

        val dialog = Dialog(this)
        dialog.setContentView(R.layout.grocery_add_dialouge)

        val cancelBtn = dialog.findViewById<Button>(R.id.idCancelBTN)
        val addBtn = dialog.findViewById<Button>(R.id.idAddBTN)
        val itemCountBtn = dialog.findViewById<TextView>(R.id.idEditItemQuantity)
        val itemEditName = dialog.findViewById<TextView>(R.id.idEditItemName)
        val itemPriceEdit = dialog.findViewById<TextView>(R.id.idEditItemPrice)


        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }

        addBtn.setOnClickListener {
            val itemName: String = itemEditName.text.toString()
            val itemPrice: String = itemPriceEdit.text.toString()
            val itemQuantity: String = itemCountBtn.text.toString()


            val qty: Int = itemQuantity.toInt()
            val pr: Int = itemPrice.toInt()

            if (itemName.isNotEmpty() && itemPrice.isNotEmpty() && itemQuantity.isNotEmpty()) {
                val items = GroceryItem(itemName, qty, pr)
                groceryViewModel.insert(items)
                groceryAdapter.notifyDataSetChanged()
                dialog.dismiss()
            } else {
                Toast.makeText(
                    applicationContext,
                    "Please Enter all the data...",
                    Toast.LENGTH_SHORT
                ).show()

            }


        }

        dialog.show()
    }

    override fun onItemClick(groceryItem: GroceryItem) {
        groceryViewModel.delete(groceryItem)
        groceryAdapter.notifyDataSetChanged()
        Toast.makeText(applicationContext, "Item Deleted...", Toast.LENGTH_SHORT).show()
    }
}