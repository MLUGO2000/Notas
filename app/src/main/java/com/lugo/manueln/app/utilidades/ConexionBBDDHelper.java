package com.lugo.manueln.app.utilidades;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class ConexionBBDDHelper extends SQLiteOpenHelper {
    public ConexionBBDDHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(utilidades.CREAR_TABLA);
        sqLiteDatabase.execSQL(utilidades.CREAR_TABLA_CATEGORIAS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + utilidades.TABLA_NOTAS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + utilidades.TABLA_CATEGORIAS);
        onCreate(sqLiteDatabase);
    }
}
