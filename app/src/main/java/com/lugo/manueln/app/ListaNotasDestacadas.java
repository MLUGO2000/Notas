package com.lugo.manueln.app;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lugo.manueln.app.adaptadores.adapterNotas;
import com.lugo.manueln.app.utilidades.ConexionBBDDHelper;
import com.lugo.manueln.app.utilidades.utilidades;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListaNotasDestacadas.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListaNotasDestacadas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListaNotasDestacadas extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private final int FRAGMENT_NOTA_DESTACADA=2;

    private OnFragmentInteractionListener mListener;

    private List<nota> lista;



    public ListaNotasDestacadas() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListaNotasDestacadas.
     */
    // TODO: Rename and change types and number of parameters
    public static ListaNotasDestacadas newInstance(String param1, String param2) {
        ListaNotasDestacadas fragment = new ListaNotasDestacadas();
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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista= inflater.inflate(R.layout.fragment_lista_notas_destacadas, container, false);

        lista=new ArrayList<nota>();

        cargarListaDestacado();

        RecyclerView myRecyclerDestacado=vista.findViewById(R.id.recyclerDestacado);

        LinearLayoutManager manager=new LinearLayoutManager(getContext());

        myRecyclerDestacado.setLayoutManager(manager);

        adapterNotas adapterNotas = new adapterNotas(getContext(),lista,getActivity(),FRAGMENT_NOTA_DESTACADA);
        myRecyclerDestacado.setAdapter(adapterNotas);

        return vista;
    }

    private void cargarListaDestacado() {
        ConexionBBDDHelper miConex=new ConexionBBDDHelper(getContext(),"bd_notas",null,1);

        SQLiteDatabase db=miConex.getReadableDatabase();

        Cursor miCursor=db.rawQuery("SELECT * FROM " + utilidades.TABLA_NOTAS + " WHERE " +utilidades.CAMPO_DESTACADO +  "= ?",new String[]{""+1});

        nota miNota=null;
        while (miCursor.moveToNext()){

            miNota=new nota();

            miNota.setId(miCursor.getInt(0));
            miNota.setTitulo(miCursor.getString(1));
            miNota.setTexto(miCursor.getString(2));
            miNota.setDestacado(miCursor.getInt(3));

            lista.add(miNota);

        }

        db.close();
        miConex.close();

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
        mListener = null;
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
