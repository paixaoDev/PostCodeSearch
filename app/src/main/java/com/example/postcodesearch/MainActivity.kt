package com.example.postcodesearch

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.postcodesearch.data.AddressData
import com.example.postcodesearch.ui.AddressAdapter

class MainActivity : AppCompatActivity() {

    private val editText: AppCompatEditText by lazy { findViewById(R.id.appCompatEditText) }
    private val cancelButton: Button by lazy { findViewById(R.id.button) }
    private val recyclerView: RecyclerView by lazy { findViewById(R.id.recycler) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val addressList = listOf(
            AddressData("", "1"),
            AddressData("", "2"),
            AddressData("", "3"),
            AddressData("", "4"),
            AddressData("", "5"),
            AddressData("", "6"),
            AddressData("", "7"),
            AddressData("", "8"),
            AddressData("", "9"),
            AddressData("", "10")
        )
        recyclerView.adapter = AddressAdapter(addressList)

        editTextSetup()
        cancelButtonSetup()
    }

    private fun editTextSetup() {
        editText.doOnTextChanged { text, start, before, count ->
            cancelButton.visibility = when (text.isNullOrBlank()) {
                true -> View.GONE
                false -> View.VISIBLE
            }
        }
    }

    private fun cancelButtonSetup() {
        cancelButton.setOnClickListener { editText.setText("") }
    }

}