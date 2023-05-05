package com.example.deliveryapplication

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.deliveryapplication.model.AppDatabase
import com.example.deliveryapplication.model.Card
import com.example.deliveryapplication.model.MenuCard
import com.example.deliveryapplication.model.MenuItem
import java.time.LocalDate
import java.util.Date

class menuFragment : Fragment() {

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var selectionActionButton: Button
    private lateinit var recyclerViewAdapter: RecyclerViewAdapterMenu

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        val rootView = inflater.inflate(R.layout.fragment_menu, container, false)

        val recyclerView = rootView.findViewById(R.id.menuList) as RecyclerView
        recyclerView.setHasFixedSize(true)

        val mlayoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = mlayoutManager

        recyclerViewAdapter = RecyclerViewAdapterMenu(viewModel.menuItems)
        recyclerView.adapter = recyclerViewAdapter

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }

        selectionActionButton = view.findViewById(R.id.button)
        selectionActionButton.setOnClickListener {
            val pref = context?.getSharedPreferences("Connect", Context.MODE_PRIVATE)
            val connected = pref?.getBoolean("connected", false)

            if (connected == true) {
                val selectedItems = recyclerViewAdapter.getSelectedItems()
                // Do something with the selected items
            }
        }

        recyclerViewAdapter.setSelectionActionButton(selectionActionButton)
        recyclerViewAdapter.setOnItemSelectedListener(object : RecyclerViewAdapterMenu.OnItemSelectedListener {
            override fun onItemSelected(selectedItems: List<MenuItem>) {
                val currentDate = LocalDate.now().toString()
                val appDatabase = AppDatabase.buildDatabase(requireActivity())
                appDatabase?.cardDao()?.addCards(Card(1,currentDate))
                var listToBeInsterted = mutableListOf<MenuCard>()
                for (item in selectedItems){
                    listToBeInsterted.add(MenuCard(item.id.toLong(),1))
                }
                appDatabase?.menuCardDao()?.addMenuDataCardItems()

            }
        })
    }
}
