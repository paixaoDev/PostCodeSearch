package com.example.postcodesearch

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.postcodesearch.data.AddressData
import com.example.postcodesearch.ui.AddressAdapter
import com.example.postcodesearch.ui.CancelableEditText
import com.example.postcodesearch.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val editText: CancelableEditText by lazy { findViewById(R.id.cancelableEditText) }
    private val recyclerView: RecyclerView by lazy { findViewById(R.id.recycler) }
    private val viewModel: SearchViewModel by viewModels()

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

        editText.onTextChanged = CancelableEditText.OnTextChanged { text ->
            //TODO SEARCH VALUES
        }

        viewModel.downloadFileAndSave()
    }

}