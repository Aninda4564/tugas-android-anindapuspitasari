package com.latihan.project_tugas_android_akhir.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.latihan.project_tugas_android_akhir.models.Mahasiswa

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    
    companion object {
        private const val DATABASE_NAME = "ninda_app.db"
        private const val DATABASE_VERSION = 1
        
        // Table name
        private const val TABLE_MAHASISWA = "mahasiswa"
        
        // Column names
        private const val COLUMN_ID = "id"
        private const val COLUMN_NIM = "nim"
        private const val COLUMN_NAMA = "nama"
        private const val COLUMN_JURUSAN = "jurusan"
    }
    
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = """
            CREATE TABLE $TABLE_MAHASISWA (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NIM TEXT NOT NULL UNIQUE,
                $COLUMN_NAMA TEXT NOT NULL,
                $COLUMN_JURUSAN TEXT NOT NULL
            )
        """.trimIndent()
        db?.execSQL(createTable)
    }
    
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_MAHASISWA")
        onCreate(db)
    }
    
    // CREATE - Tambah Data
    fun tambahData(mahasiswa: Mahasiswa): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NIM, mahasiswa.nim)
            put(COLUMN_NAMA, mahasiswa.nama)
            put(COLUMN_JURUSAN, mahasiswa.jurusan)
        }
        val result = db.insert(TABLE_MAHASISWA, null, values)
        db.close()
        return result
    }
    
    // READ - Ambil Semua Data
    fun ambilSemuaData(): List<Mahasiswa> {
        val mahasiswaList = mutableListOf<Mahasiswa>()
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_MAHASISWA ORDER BY $COLUMN_ID DESC", null)
        
        if (cursor.moveToFirst()) {
            do {
                val mahasiswa = Mahasiswa(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    nim = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NIM)),
                    nama = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAMA)),
                    jurusan = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_JURUSAN))
                )
                mahasiswaList.add(mahasiswa)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return mahasiswaList
    }
    
    // UPDATE - Ubah Data
    fun ubahData(mahasiswa: Mahasiswa): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAMA, mahasiswa.nama)
            put(COLUMN_JURUSAN, mahasiswa.jurusan)
        }
        val result = db.update(
            TABLE_MAHASISWA, 
            values, 
            "$COLUMN_NIM = ?", 
            arrayOf(mahasiswa.nim)
        )
        db.close()
        return result
    }
    
    // DELETE - Hapus Data
    fun hapusData(nim: String): Int {
        val db = this.writableDatabase
        val result = db.delete(TABLE_MAHASISWA, "$COLUMN_NIM = ?", arrayOf(nim))
        db.close()
        return result
    }
    
    // Cek apakah NIM sudah ada
    fun isNimExists(nim: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_MAHASISWA WHERE $COLUMN_NIM = ?", 
            arrayOf(nim)
        )
        val exists = cursor.count > 0
        cursor.close()
        db.close()
        return exists
    }
}
