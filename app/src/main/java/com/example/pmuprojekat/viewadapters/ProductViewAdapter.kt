package com.example.pmuprojekat.viewadapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.apitest.viewmodels.CategoryViewModel
import com.example.pmuprojekat.MainActivity
import com.example.pmuprojekat.R
import com.example.pmuprojekat.aktivnosti.DetaljiKategorijeActivity
import com.example.pmuprojekat.aktivnosti.KategorijeActivity
import com.example.pmuprojekat.data.Category
import com.example.pmuprojekat.data.Product
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.newCoroutineContext
import kotlin.coroutines.coroutineContext

class ProductViewAdapter(val ctx: Context, val data: LiveData<List<Product>>): RecyclerView.Adapter<ProductViewAdapter.ProductViewHodler>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHodler {
        val inflater:LayoutInflater= LayoutInflater.from(ctx)
        val view=inflater.inflate(R.layout.product_row,parent,false)
        return ProductViewHodler(view)
    }

    override fun onBindViewHolder(holder: ProductViewHodler, position: Int) {
        holder.bindItems(data.value!!.get(position))
    }

    override fun getItemCount(): Int {
        if(data.value!=null){
            return data.value!!.size
        }
        return 0;
    }
    inner class ProductViewHodler(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(model:Product){
            val proId=model.productId
            val nam=itemView.findViewById<TextView>(R.id.productName)
            nam.text=model.productName
            val detaljnije=itemView.findViewById<Button>(R.id.detaljnijeProizvod)
            detaljnije.setOnClickListener {
                val intent=Intent(ctx,DetaljiKategorijeActivity::class.java)
                intent.putExtra("cId",proId)
                ctx.startActivity(intent)
            }
        }
    }

}



