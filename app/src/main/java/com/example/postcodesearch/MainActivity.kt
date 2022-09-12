package com.example.postcodesearch

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.postcodesearch.data.AddressData
import com.example.postcodesearch.data.CallStateResult
import com.example.postcodesearch.ui.AddressAdapter
import com.example.postcodesearch.ui.CancelableEditText
import com.example.postcodesearch.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val editText: CancelableEditText by lazy { findViewById(R.id.cancelableEditText) }
    private val loading: View by lazy { findViewById(R.id.loading) }
    private val recyclerView: RecyclerView by lazy { findViewById(R.id.recycler) }
    private val viewModel: SearchViewModel by viewModels()

    private val localAddressAdapter by lazy { AddressAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText.onTextChanged = CancelableEditText.OnTextChanged { text ->
            viewModel.searchAddress(text)
        }

        viewModel.addressesState().observe(this){ state ->
            when(state){
                is CallStateResult.Loading -> showLoading()
                is CallStateResult.OnRemoteAddressFileReceived -> {
                    AddressData.saveFile(this, state.data)
                    val addressList = AddressData.mapFileDataIntoObjectList(this)
                    viewModel.saveData(addressList)
                }
                is CallStateResult.OnDataSaved -> {
                    viewModel.getDataFromLocal()
                }
                is CallStateResult.OnAddressesFetchedFromLocal -> {
                    hideLoading()
                    createAdapter(state.list)
                }
                is CallStateResult.OnQueryFinished -> {
                    hideLoading()
                    localAddressAdapter.address = state.querryList
                }
                is CallStateResult.Error -> {
                    hideLoading()
                    Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.downloadFileAndSave()
    }

    private fun createAdapter (addressList: List<AddressData>){
        recyclerView.adapter = localAddressAdapter
        localAddressAdapter.address = addressList
    }

    private fun showLoading () {
        loading.visibility = View.VISIBLE
    }

    private fun hideLoading (){
        loading.visibility = View.GONE
    }
}