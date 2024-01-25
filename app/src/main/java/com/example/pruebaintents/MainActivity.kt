package com.example.pruebaintents

import Articulo
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.pruebaintents.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val dbHelper = DatabaseHelper(this)



        binding.btnAA.setOnClickListener {
            val tipoArticulo= binding.tipoArticulo.text.toString()
            val nombre = binding.nombre.text.toString()
            val peso = binding.peso.text.toString().toInt()
            val precio = binding.precio.text.toString().toInt()
            val articulo = Articulo(getType(tipoArticulo),getName(nombre),peso,precio)
            dbHelper.insertarArticulo(articulo)
            Toast.makeText(this, "Articulo guardado en la base de datos", Toast.LENGTH_SHORT).show()
        }




    }
        fun getName(nombre: String): Articulo.Nombre {
            return when (nombre){
                "BASTON"-> Articulo.Nombre.BASTON
                 "ESPADA" -> Articulo.Nombre.ESPADA
                "DAGA" -> Articulo.Nombre.DAGA
                "MARTILLO" -> Articulo.Nombre.MARTILLO
                "GARRAS" -> Articulo.Nombre.GARRAS
                "POCION" -> Articulo.Nombre.POCION
                "IRA" -> Articulo.Nombre.IRA
                "ESCUDO" -> Articulo.Nombre.ESCUDO
                "ARMADURA" -> Articulo.Nombre.ARMADURA
                else -> Articulo.Nombre.ARMADURA
            }

        }

    fun getType(tipoArticulo: String): Articulo.TipoArticulo{
        return when (tipoArticulo){
            "ARMA" -> Articulo.TipoArticulo.ARMA
                "OBJETO" -> Articulo.TipoArticulo.OBJETO
                "PROTECCION" -> Articulo.TipoArticulo.PROTECCION
            else ->  Articulo.TipoArticulo.OBJETO
        }

    }

    class DatabaseHelper(context: Context): SQLiteOpenHelper(context,DATABASE, null,DATABASE_VERSION){
        companion object{
            private const val DATABASE = "Mochila.db"
            private const val DATABASE_VERSION= 1
            private const val TABLA_MOCHILA = "Mochila"
            private const val KEY_ID = "_id"
            private const val COLUMN_TIPOARTICULO = "TipoArticulo"
            private const val COLUMN_NOMBRE = "Nombre"
            private const val COLUMN_PESO = ""
            private const val COLUMN_PRECIO = ""


        }

        override fun onCreate(db: SQLiteDatabase?) {
            val createTable = "CREATE TABLE $TABLA_MOCHILA " +
                    "($KEY_ID INTEGER PRIMARY KEY," +
                    "$COLUMN_TIPOARTICULO TEXT," +
                    "$COLUMN_NOMBRE TEXT, " +
                    "$COLUMN_PESO INTEGER, "+
                    "$COLUMN_PRECIO INTEGER)"
            db?.execSQL(createTable)
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db?.execSQL("DROP TABLE IF EXISTS $TABLA_MOCHILA")
            onCreate(db)
        }

        fun insertarArticulo(articulo: Articulo){
            val db = this.writableDatabase
            val values=ContentValues().apply {
                put(COLUMN_TIPOARTICULO, articulo.getTipoArticulo().toString())
                put(COLUMN_NOMBRE, articulo.getNombre().toString())
                put(COLUMN_PESO, articulo.getPeso())
                put(COLUMN_PRECIO, articulo.getPrecio())
            }
        }

        @SuppressLint("Range")
        fun getArticulosMochila(): ArrayList<Articulo>{
            val articulos = ArrayList<Articulo>()
            val selectQuery = "SELECT * FROM $TABLA_MOCHILA"
            val db = this.readableDatabase
            val cursor = db.rawQuery(selectQuery, null)
            if (cursor.moveToFirst()){
                do {
                    val id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                    val tipoArticulo = cursor.getString(cursor.getColumnIndex(COLUMN_TIPOARTICULO))
                    val nombre = cursor.getString(cursor.getColumnIndex(COLUMN_NOMBRE))
                    val peso = cursor.getString(cursor.getColumnIndex(COLUMN_PESO))
                    val precio = cursor.getString(cursor.getColumnIndex(COLUMN_PRECIO))

                } while (cursor.moveToNext())
            }
            cursor.close()
            db.close()
            return articulos
        }
    }


}