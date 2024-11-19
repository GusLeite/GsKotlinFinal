package com.gusleite.gskotlin

import TipsbaseHelper
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gusleite.gskotlin.adapter.TipsAdapter
import com.gusleite.gskotlin.entity.Tip
import android.widget.Button

//Gustavo Leite da Silva 94343
class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TipsAdapter
    private lateinit var searchView: SearchView
    private lateinit var databaseHelper: TipsbaseHelper

    private var tips = mutableListOf<Tip>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        databaseHelper = TipsbaseHelper(this)

        recyclerView = findViewById(R.id.recyclerView)
        searchView = findViewById(R.id.searchView)
        val btnTeam: Button = findViewById(R.id.btnTeam)

        tips = databaseHelper.listsTips().toMutableList()

        if (tips.isEmpty()) {
            initializeTips()
            tips = databaseHelper.listsTips().toMutableList()
        }

        adapter = TipsAdapter(this, tips)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val filteredTips = tips.filter { it.title.contains(newText ?: "", ignoreCase = true) }
                recyclerView.adapter = TipsAdapter(this@MainActivity, filteredTips)
                return true
            }
        })

        btnTeam.setOnClickListener {
            val intent = Intent(this, TeamActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initializeTips() {
        val initialTips = listOf(
            Tip(
                "Faça exercícios regularmente",
                "Manter uma rotina de exercícios melhora a saúde física e mental.",
                "https://tips.com/exercicios",
                "Exercícios regulares podem reduzir o risco de doenças crônicas."
            ),
            Tip(
                "Durma bem",
                "Ter uma boa noite de sono é essencial para a saúde.",
                "https://tips.com/sono",
                "Dormir bem ajuda a melhorar a memória e a concentração."
            ),
            Tip(
                "Beba bastante água",
                "Manter-se hidratado é crucial para o funcionamento do corpo.",
                "https://tips.com/agua",
                "Beber água suficiente pode melhorar a pele e a digestão."
            ),
            Tip(
                "Coma frutas e vegetais",
                "Uma dieta rica em frutas e vegetais fornece nutrientes essenciais.",
                "https://tips.com/dieta",
                "Frutas e vegetais são ricos em vitaminas e minerais."
            ),
            Tip(
                "Evite o estresse",
                "Pratique técnicas de relaxamento para reduzir o estresse.",
                "https://tips.com/estresse",
                "Reduzir o estresse pode melhorar a saúde mental e física."
            ),
            Tip(
                "Mantenha-se ativo mentalmente",
                "Desafie seu cérebro com atividades intelectuais.",
                "https://tips.com/atividades-mentais",
                "Atividades mentais podem ajudar a prevenir o declínio cognitivo."
            )
        )

        for (tip in initialTips) {
            databaseHelper.insertTips(tip)
        }
    }
}