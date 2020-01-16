package com.lugo.manueln.app.adaptadores;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lugo.manueln.app.NotaFragment;
import com.lugo.manueln.app.R;
import com.lugo.manueln.app.nota;

import java.util.List;

public class adapterNotasFavoritos extends RecyclerView.Adapter<adapterNotasFavoritos.MyViewHolder> {

    List<nota> notaList;
    Context context;
    FragmentActivity fragmentActivity;
    public adapterNotasFavoritos(Context context, List<nota> miLista, FragmentActivity activity){

        this.context=context;
        notaList=miLista;
        fragmentActivity=activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.vista_nota,parent,false);

        ViewGroup.LayoutParams params=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        view.setLayoutParams(params);

        return new MyViewHolder(view);
    }


    public void onBindViewHolder(@NonNull adapterNotasFavoritos.MyViewHolder holder, int position) {

        holder.txt_titulo.setText(notaList.get(position).getTitulo());
        holder.txt_texto.setText(notaList.get(position).getTexto());

        final int idSelec=position;

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NotaFragment editFragmentNota=new NotaFragment();
                Bundle miBundle=new Bundle();



                miBundle.putInt("id",notaList.get(idSelec).getId());
                miBundle.putBoolean("edit",true);

                editFragmentNota.setArguments(miBundle);

                Fragment fragmentOcultar=fragmentActivity.getSupportFragmentManager().findFragmentById(R.id.f_nota);
                fragmentActivity.getSupportFragmentManager().beginTransaction().hide(fragmentOcultar).add(R.id.f_nota,editFragmentNota).addToBackStack(null).commit();

                //En Construccion.....

            }
        });
    }

    @Override
    public int getItemCount() {
        return notaList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_titulo,txt_texto;
        CardView cardView;
        public MyViewHolder(View itemView) {
            super(itemView);
            txt_titulo=itemView.findViewById(R.id.textTitulo);
            txt_texto=itemView.findViewById(R.id.textText);
            cardView=itemView.findViewById(R.id.vistaCard);
        }
    }
}
