package com.lugo.manueln.app.utilidades;

import com.lugo.manueln.app.ListaNotasCategorias;
import com.lugo.manueln.app.ListaNotasDestacadas;
import com.lugo.manueln.app.ListaNotasFragment;
import com.lugo.manueln.app.NotaFragment;
import com.lugo.manueln.app.addCategoriaFragment;

public interface interFragment extends addCategoriaFragment.OnFragmentInteractionListener,ListaNotasFragment.OnFragmentInteractionListener,ListaNotasDestacadas.OnFragmentInteractionListener,ListaNotasCategorias.OnFragmentInteractionListener,NotaFragment.OnFragmentInteractionListener {
}
