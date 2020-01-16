package com.lugo.manueln.app.utilidades;

public class utilidades {

   public static final String TABLA_NOTAS="notas";
   public static final String CAMPO_ID="id";
   public static final String CAMPO_TITULO="titulo";
   public static final String CAMPO_TEXTO="texto";
   public static final String CAMPO_DESTACADO="destacado";
   public static final String CAMPO_CATEGORIA="categoria";

   public static final String CREAR_TABLA="CREATE TABLE " + TABLA_NOTAS + "(" + CAMPO_ID + " INTEGER PRIMARY KEY NOT NULL," + CAMPO_TITULO + " TEXT," + CAMPO_TEXTO + " TEXT," + CAMPO_DESTACADO + " INTEGER," +  CAMPO_CATEGORIA + " TEXT)";


   public static final String TABLA_CATEGORIAS="categorias";
   public static final String CAMPO_CATEGORIAS_CREADAS="tipo";

   public static final String CREAR_TABLA_CATEGORIAS="CREATE TABLE " + TABLA_CATEGORIAS + "(" + CAMPO_CATEGORIAS_CREADAS + " TEXT)";


}
