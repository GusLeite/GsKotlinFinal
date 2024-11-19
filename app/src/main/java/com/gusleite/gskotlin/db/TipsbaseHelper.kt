
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.gusleite.gskotlin.entity.Tip

class TipsbaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "tips.db"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "tips"
        const val COLUMN_ID = "id"
        const val COLUMN_TILE = "title"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_LINK = "link"
        const val COLUMN_RANDOM_FACT = "random"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_TILE TEXT,
                $COLUMN_DESCRIPTION TEXT,
                $COLUMN_LINK TEXT,
                $COLUMN_RANDOM_FACT TEXT
            )
        """
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertTips(tip: Tip): Long {
        val db = writableDatabase
        val values = ContentValues()
        values.put(COLUMN_TILE, tip.title)
        values.put(COLUMN_DESCRIPTION, tip.description)
        values.put(COLUMN_LINK, tip.link)
        values.put(COLUMN_RANDOM_FACT, tip.randomFact)

        return db.insert(TABLE_NAME, null, values)
    }

    fun listsTips(): List<Tip> {
        val tips = mutableListOf<Tip>()
        val db = readableDatabase
        val cursor: Cursor = db.query(TABLE_NAME, null, null, null, null, null, null)

        if (cursor.moveToFirst()) {
            do {
                val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TILE))
                val description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION))
                val link = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LINK))
                val randomFact = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RANDOM_FACT))

                tips.add(Tip(title, description, link, randomFact))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return tips
    }
}