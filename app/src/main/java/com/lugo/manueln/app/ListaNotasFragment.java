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
import android.widget.ImageButton;
import android.widget.Toast;

import com.lugo.manueln.app.adaptadores.adapterNotas;
import com.lugo.manueln.app.utilidades.ConexionBBDDHelper;
import com.lugo.manueln.app.utilidades.utilidades;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListaNotasFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListaNotasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListaNotasFragment extends Fragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private final int FRAGMENT_NOTA=1;

    private OnFragmentInteractionListener mListener;

    private List<nota> miLista;

    public ListaNotasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListaNotasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListaNotasFragment newInstance(String param1, String param2) {
        ListaNotasFragment fragment = new ListaNotasFragment();
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
        View vista =inflater.inflate(R.layout.fragment_lista_notas, container, false);


        //ImageButton b_agregar=vista.findViewById(R.id.agregarButton);

      //  b_agregar.setOnClickListener(this);

        miLista=new ArrayList<nota>();

        cargarLista();

       /* for(int i=0;i<20;i++){
            nota miNota=new nota();

            miNota.setTitulo("Titulo numero " + i);
            miNota.setTexto("Texto " + i + " Texto " + i +" Texto " + i +" Texto " + i +" Texto " + i +" Texto " + i );

            miLista.add(miNota);

        }*/

        RecyclerView myRecycler=vista.findViewById(R.id.r_notas);

        LinearLayoutManager manager=new LinearLayoutManager(getContext());

        myRecycler.setLayoutManager(manager);

        adapterNotas adapterNotas = new adapterNotas(getContext(),miLista,getActivity(),FRAGMENT_NOTA);
        myRecycler.setAdapter(adapterNotas);

        return vista;
    }

    private void cargarLista() {

        ConexionBBDDHelper miConex=new ConexionBBDDHelper(getContext(),"bd_notas",null,1);

        SQLiteDatabase db=miConex.getReadableDatabase();

        Cursor miCursor=db.rawQuery("SELECT * FROM " + utilidades.TABLA_NOTAS,null);

        nota miNota=null;
        while (miCursor.moveToNext()){

            miNota=new nota();

            miNota.setId(miCursor.getInt(0));
            miNota.setTitulo(miCursor.getString(1));
            miNota.setTexto(miCursor.getString(2));
            miNota.setDestacado(miCursor.getInt(3));

            miLista.add(miNota);

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

   /* public void onClick(View view) {
        switch (view.getId()){
            case R.id.agregarButton:

                int conteo=getActivity().getSupportFragmentManager().getBackStackEntryCount();


                    Fragment fragmentOcultar=getActivity().getSupportFragmentManager().findFragmentById(R.id.f_nota);
                    NotaFragment fragment = new NotaFragment();
                    Bundle args=new Bundle();
                    args.putBoolean("edit",false);
                    args.putInt("f_proveniente",FRAGMENT_NOTA);

                    fragment.setArguments(args);
                    getActivity().getSupportFragmentManager().beginTransaction().hide(fragmentOcultar).add(R.id.f_nota, fragment,"fragment_nota").addToBackStack(null).commit();


                    //Toast.makeText(getContext(), "No se puede agregar mas fragments", Toast.LENGTH_SHORT).show();

        }
    }*/


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
