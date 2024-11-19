package com.gusleite.gskotlin.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.gusleite.gskotlin.R
import com.gusleite.gskotlin.entity.Tip


class TipsAdapter(
    private val context: Context,
    private val tips: List<Tip>
) : RecyclerView.Adapter<TipsAdapter.DicaViewHolder>() {

    class DicaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitulo: TextView = itemView.findViewById(R.id.tvTitulo)
        val tvDescricao: TextView = itemView.findViewById(R.id.tvDescricao)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DicaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_dica, parent, false)
        return DicaViewHolder(view)
    }

    override fun onBindViewHolder(holder: DicaViewHolder, position: Int) {
        val dica = tips[position]
        holder.tvTitulo.text = dica.title
        holder.tvDescricao.text = dica.description

        holder.itemView.setOnClickListener {
            Toast.makeText(context, dica.randomFact, Toast.LENGTH_SHORT).show()
        }

        holder.itemView.setOnLongClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(dica.link))
            context.startActivity(intent)
            true
        }
    }

    override fun getItemCount(): Int = tips.size
}