package com.example.pmuprojekat.aktivnosti

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.beust.klaxon.Klaxon
import com.example.pmuprojekat.R
import com.example.pmuprojekat.apihandlers.NorthwindApiHandler
import com.example.pmuprojekat.data.ApiRoutes
import com.example.pmuprojekat.data.Category
import com.example.pmuprojekat.data.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetaljiProizvodiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product_item)
        val cId = intent.extras!!["cId"]
        dohvatiProizvode(ApiRoutes.products + "/${cId}")
    }

    fun obrisiProizvod(cId: Int) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val apiHandler = NorthwindApiHandler()
                val result = apiHandler.deleteRequest(ApiRoutes.products + "/${cId}")
                if (result != null) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            getApplicationContext(),
                            "Usepsno obrisan proizvod",
                            Toast.LENGTH_LONG
                        )
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

    fun snimanjeIzmena(cId: Int, cName: String, cIdCat: Int, cDisc: Boolean) {
        val izmenjenProizvod = Product(
            cId,
            cName,
            cIdCat,
            cDisc
        )
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val apiHandler = NorthwindApiHandler()
                val result = apiHandler.putRequest(
                    ApiRoutes.products + "/${cId}",
                    Klaxon().toJsonString(izmenjenProizvod)
                )
                if (result != null) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            getApplicationContext(),
                            "Usepsno izmenjeni podaci",
                            Toast.LENGTH_LONG
                        )
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

    fun dohvatiProizvode(sUrl: String): Product? {
        var product: Product? = null
//        lifecycleScope.launch(Dispatchers.IO) {
//            val apiHandler = NorthwindApiHandler()
//            val result = apiHandler.getRequest(sUrl)
//            if (result != null) {
//                try {
//                    // Parse result string JSON to data class
//                    product = Klaxon().parse(result)
//
//                    withContext(Dispatchers.Main) {
//                        val nazivProizvoda = findViewById<EditText>(R.id.idNazivProizvoda)
//                        nazivProizvoda.setText(product!!.productName)
//                        val izborKategorije = findViewById<Spinner>(R.id.idIzaberiKategoriju)
//                        //izborKategorije.setI(product!!.categoryId)
//                        val brisanjeProizvoda = findViewById<Button>(R.id.idObrsiProizvod)
//                        brisanjeProizvoda.text = "Obrisi"
//                        brisanjeProizvoda.isVisible = true
//                        brisanjeProizvoda.setOnClickListener { obrisiProizvod(product!!.productId) }
//                        val snimiIzmene = findViewById<Button>(R.id.idDodajProizvod)
//                        snimiIzmene.text = "Snimi"
//                        snimiIzmene.setOnClickListener {
//                            snimanjeIzmena(
//                                product!!.productId,
//                                nazivProizvoda.text.toString(),
//                                //opisKategorije.text.toString()
//                            )
//                        }
//
//                    }
//                } catch (err: Error) {
//                    print("Error when parsing JSON: " + err.localizedMessage)
//                }
//            } else {
//                print("Error: Get request returned no response")
//            }
//
//        }
        return product
    }
}