package com.example.apitest.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pmuprojekat.data.Product

class ProductViewModel: ViewModel() {
    val lstProizvoda= MutableLiveData<List<Product>>()
    val proizvod=MutableLiveData<Product>()
}