package com.lugo.manueln.app;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Switch;
import android.widget.Toast;

import com.lugo.manueln.app.utilidades.ConexionBBDDHelper;
import com.lugo.manueln.app.utilidades.utilidades;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NotaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NotaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotaFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "id";
    private static final String ARG_PARAM2 = "edit";
    private static final String ARG_PARAM3 = "f_proveniente";

    // TODO: Rename and change types of parameters
    private int fragmentProveniente;
    private int idParametro;
    private boolean editParametro;

    private final int FRAGMENT_NOTA=1;
    private final int FRAGMENT_NOTA_DESTACADA=2;
    private ArrayList<String> catSpinner;
    private ArrayList<String> listCategoria;

    public boolean isR_guardado() {
        return r_guardado;
    }




    private ConexionBBDDHelper miConex;
    private boolean r_guardado;

    private int idCreada;

    private OnFragmentInteractionListener mListener;

    private FloatingActionButton buttonGuardar;


    private Switch sDestacado;

    private Spinner spiCategoria;

    private EditText editTitulo,editTexto;

    public NotaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotaFragment newInstance(String param1, String param2) {
        NotaFragment fragment = new NotaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idParametro = getArguments().getInt(ARG_PARAM1);
            editParametro = getArguments().getBoolean(ARG_PARAM2);
            fragmentProveniente=getArguments().getInt(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista= inflater.inflate(R.layout.fragment_nota, container, false);

         miConex = new ConexionBBDDHelper(getContext(), "bd_notas", null, 1);

        r_guardado=false;

        spiCategoria=vista.findViewById(R.id.spiCategoria);

        buttonGuardar=vista.findViewById(R.id.floatButtonSave);

        sDestacado=vista.findViewById(R.id.sw_destacado);
        editTitulo=vista.findViewById(R.id.titulo);
        editTexto=vista.findViewById(R.id.texto);

        
        cargarSpinner();
        ArrayAdapter<String> miAdapterSpi=new ArrayAdapter(getContext(),R.layout.support_simple_spinner_dropdown_item,listCategoria);

        spiCategoria.setAdapter(miAdapterSpi);







        if(editParametro){
            consultarDatos(idParametro);
        }else {
            idCreada=consultaID();
        }


        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarNota();
            }
        });

        return vista;
    }

    private void cargarSpinner() {

        catSpinner=new ArrayList<String>();

        SQLiteDatabase BBDD=miConex.getReadableDatabase();

        Cursor cursor=BBDD.rawQuery("SELECT * FROM " + utilidades.TABLA_CATEGORIAS,null);

        while (cursor.moveToNext()){

            catSpinner.add(cursor.getString(0));

        }
        BBDD.close();

        listCategoria=new ArrayList<String>();

        listCategoria.add("Todos");

        for(int i=0;i<catSpinner.size();i++){

            listCategoria.add(catSpinner.get(i));


        }

        Log.i("hola","TERMINAMOS");
    }

    private void consultarDatos(int id) {


        SQLiteDatabase miBBDD=miConex.getReadableDatabase();

        Cursor miCursor=miBBDD.rawQuery("SELECT * FROM "+ utilidades.TABLA_NOTAS +" WHERE " + utilidades.CAMPO_ID + "= ?",new String[]{""+id});

        if(miCursor.moveToNext()){
            editTitulo.setText(miCursor.getString(1));
            editTexto.setText(miCursor.getString(2));
            if(miCursor.getInt(3)==1){
                sDestacado.setChecked(true);
            }else {
                sDestacado.setChecked(false);
            }

            int positionSpiner=0;

            switch (miCursor.getString(4)){

                case "Todas":
                    spiCategoria.setSelection(0);
                    break;

                case "Compras":
                    spiCategoria.setSelection(1);
                    break;

                case  "Mercado":
                    spiCategoria.setSelection(2);
                    break;

                case "Tarea":
                    spiCategoria.setSelection(3);
                    break;

                case "Claves":
                    spiCategoria.setSelection(4);
                    break;
            }
            }

            miBBDD.close();
            miConex.close();

    }

    private void guardarNota() {
        ConexionBBDDHelper miConex = new ConexionBBDDHelper(getContext(), "bd_notas", null, 1);

        if(editParametro){

            SQLiteDatabase bbdd=miConex.getWritableDatabase();

            ContentValues valores=new ContentValues();

            valores.put(utilidades.CAMPO_TITULO, editTitulo.getText().toString());
            valores.put(utilidades.CAMPO_TEXTO, editTexto.getText().toString());

            String prueba=spiCategoria.getSelectedItem().toString();
            valores.put(utilidades.CAMPO_CATEGORIA,prueba);

            int Favorito=esDestacado();
            valores.put(utilidades.CAMPO_DESTACADO,Favorito);

            String id=idParametro+"";

            int result=bbdd.update(utilidades.TABLA_NOTAS,valores,"id = ?" ,new String[]{id});


            Toast.makeText(getContext(), "Se Produjo la edicion en  " + result +  " filas", Toast.LENGTH_SHORT).show();

            bbdd.close();
            miConex.close();
            r_guardado=true;
        }
        else if(editTitulo.getText().equals("")==false && editTexto.equals("")==false) {




            SQLiteDatabase bbdd = miConex.getWritableDatabase();

            ContentValues values = new ContentValues();


            values.put(utilidades.CAMPO_ID,idCreada);
            values.put(utilidades.CAMPO_TITULO, editTitulo.getText().toString());
            values.put(utilidades.CAMPO_TEXTO, editTexto.getText().toString());

            String prueba=spiCategoria.getSelectedItem().toString();
            values.put(utilidades.CAMPO_CATEGORIA,prueba);

            int Favorito=esDestacado();
            values.put(utilidades.CAMPO_DESTACADO,Favorito);

            Long result = bbdd.insert(utilidades.TABLA_NOTAS, null, values);

            Toast.makeText(getContext(), "Se Produjo el Guardado con id: " + result, Toast.LENGTH_SHORT).show();
            bbdd.close();
            miConex.close();
            r_guardado=true;
        }else {
            Toast.makeText(getContext(),"Por favor Rellene Los Campos",Toast.LENGTH_SHORT).show();
        }

        Fragment miF=null;
        if (fragmentProveniente==FRAGMENT_NOTA){
            miF=new ListaNotasFragment();
        }else if(fragmentProveniente==FRAGMENT_NOTA_DESTACADA){
            miF=new ListaNotasDestacadas();
        }
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.f_nota,miF).commit();


    }

    private int esDestacado() {

       if(sDestacado.isChecked()){
           return 1;
       }else {
           return 0;
       }
    }

    private int consultaID() {
        ConexionBBDDHelper miConex = new ConexionBBDDHelper(getContext(), "bd_notas", null, 1);
        SQLiteDatabase BD=miConex.getReadableDatabase();


           Cursor miCursor=BD.rawQuery("SELECT * FROM " + utilidades.TABLA_NOTAS ,null);
           ArrayList<Integer> miListaId=new ArrayList<>();
           while (miCursor.moveToNext()) {
               miListaId.add(miCursor.getInt(0));
           }

           BD.close();
           miConex.close();
           return  miListaId.size() + 1;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
