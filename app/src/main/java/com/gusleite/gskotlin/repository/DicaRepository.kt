package com.gusleite.gskotlin.repository

import TipsbaseHelper
import android.content.ContentValues
import android.content.Context
import com.gusleite.gskotlin.entity.Tip

class DicaRepository(context: Context) {
    private val dbHelper = TipsbaseHelper(context)

    fun inserirDica(tip: Tip): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(TipsbaseHelper.COLUMN_TILE, tip.title)
            put(TipsbaseHelper.COLUMN_DESCRIPTION, tip.description)
            put(TipsbaseHelper.COLUMN_LINK, tip.link)
            put(TipsbaseHelper.COLUMN_RANDOM_FACT, tip.randomFact)
        }
        return db.insert(TipsbaseHelper.TABLE_NAME, null, values)
    }

    fun listarDicas(): List<Tip> {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            TipsbaseHelper.TABLE_NAME,
            null, null, null, null, null, null
        )
        val tips = mutableListOf<Tip>()
        with(cursor) {
            while (moveToNext()) {
                val title = getString(getColumnIndexOrThrow(TipsbaseHelper.COLUMN_TILE))
                val description = getString(getColumnIndexOrThrow(TipsbaseHelper.COLUMN_DESCRIPTION))
                val link = getString(getColumnIndexOrThrow(TipsbaseHelper.COLUMN_LINK))
                val randomFact = getString(getColumnIndexOrThrow(TipsbaseHelper.COLUMN_RANDOM_FACT))
                tips.add(Tip(title, description, link, randomFact))
            }
        }
        cursor.close()
        return tips
    }
}