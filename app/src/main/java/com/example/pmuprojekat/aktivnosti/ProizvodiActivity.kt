package com.example.pmuprojekat.aktivnosti

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.beust.klaxon.Klaxon
import com.example.apitest.viewmodels.CategoryViewModel
import com.example.apitest.viewmodels.ProductViewModel
import com.example.pmuprojekat.R
import com.example.pmuprojekat.apihandlers.NorthwindApiHandler
import com.example.pmuprojekat.data.ApiRoutes
import com.example.pmuprojekat.data.Category
import com.example.pmuprojekat.data.Product
import com.example.pmuprojekat.viewadapters.CategoryViewAdapter
import com.example.pmuprojekat.viewadapters.ProductViewAdapter
import com.google.android.material.internal.ContextUtils.getActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ProizvodiActivity : AppCompatActivity() {
    val viewModel: ProductViewModel by viewModels()
    var productViewAdapter: ProductViewAdapter? = null

    val viewModelKat: CategoryViewModel by viewModels()
    var categoryViewAdapter: CategoryViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_proizvodi)
        dohvatiProizvode(this, ApiRoutes.products)
        dohvatiKategorijeIzbor(this, ApiRoutes.categories)
    }

    private fun dohvatiProizvode(ctx: Context, sUrl: String): List<Product>? {
        var products: List<Product>? = null
        lifecycleScope.launch(Dispatchers.IO) {
            val apiHandler = NorthwindApiHandler()
            val result = apiHandler.getRequest(sUrl)
            if (result != null) {
                try {
                    // Parse result string JSON to data class
                    products = Klaxon().parseArray(result)

                    withContext(Dispatchers.Main) {
                        viewModel.lstProizvoda.value = products
                        val lstProizovdaView = findViewById<RecyclerView>(R.id.lstProizvodiView)
                        lstProizovdaView.layoutManager = LinearLayoutManager(ctx)
                        productViewAdapter = ProductViewAdapter(ctx, viewModel.lstProizvoda)
                        lstProizovdaView.adapter = productViewAdapter
                    }
                } catch (err: Error) {
                    print("Error when parsing JSON: " + err.localizedMessage)
                }
            } else {
                print("Error: Get request returned no response")
            }

        }
        return products
    }

    private fun dohvatiKategorijeIzbor(ctx: Context, sUrl: String): List<Category>? {
        var categories: List<Category>? = null
        lifecycleScope.launch(Dispatchers.IO) {
            val apiHandler = NorthwindApiHandler()
            val result = apiHandler.getRequest(sUrl)
            if (result != null) {
                try {
                    // Parse result string JSON to data class
                    categories = Klaxon().parseArray(result)

                    withContext(Dispatchers.Main) {
                        viewModelKat.lstKategorije.value = categories
                        val lstKategorijaView = findViewById<Spinner>(R.id.idIzaberiKategoriju)
                        //lstKategorijaView.layoutManager = LinearLayoutManager(ctx)
                        categoryViewAdapter = CategoryViewAdapter(ctx, viewModelKat.lstKategorije)
                        //lstKategorijaView.adapter = categoryViewAdapter
                    }
                } catch (err: Error) {
                    print("Error when parsing JSON: " + err.localizedMessage)
                }
            } else {
                print("Error: Get request returned no response")
            }

        }
        return categories
    }

    fun prikaziUnosProizvoda(view: View) {
        setContentView(R.layout.product_item)
        val productButton = findViewById<Button>(R.id.idDodajProizvod)
        productButton.text = "Dodaj"

        val nazivProizvoda = findViewById<EditText>(R.id.idNazivProizvoda)
        val izborKategorije = findViewById<Spinner>(R.id.idIzaberiKategoriju)
        val popust = findViewById<CheckBox>(R.id.idPopust)

        productButton.setOnClickListener {
            val noviProizvod = Product(
                0,
                nazivProizvoda.text.toString(),
                izborKategorije.getSelectedItem().toString().toInt(),
                popust.isChecked()
            )
            lifecycleScope.launch(Dispatchers.IO) {
                try {
                    val apiHandler = NorthwindApiHandler()
                    val result = apiHandler.postRequest(
                        ApiRoutes.products,
                        Klaxon().toJsonString(noviProizvod)
                    )
                    if (result != null) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(getApplicationContext(), "Usepsno dodavanje proizvoda", Toast.LENGTH_LONG)
                                .show()
                        }

                    } else {
                        print("Error: Get request returned no response")
                    }
                } catch (err: Error) {
                    print("Error when parsing JSON: " + err.localizedMessage)
                }
            }
        }
    }


}