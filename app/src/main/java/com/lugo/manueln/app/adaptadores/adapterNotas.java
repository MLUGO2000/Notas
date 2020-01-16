package com.lugo.manueln.app.adaptadores;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.Toast;

import com.lugo.manueln.app.MainActivity;
import com.lugo.manueln.app.NotaFragment;
import com.lugo.manueln.app.R;
import com.lugo.manueln.app.nota;
import com.lugo.manueln.app.utilidades.ConexionBBDDHelper;
import com.lugo.manueln.app.utilidades.utilidades;

import java.util.List;

public class adapterNotas extends RecyclerView.Adapter<adapterNotas.MyViewHolder> {

    List<nota> notaList;
    Context context;
    FragmentActivity fragmentActivity;
    int f_Seleccionado;
    public adapterNotas(Context context, List<nota> miLista, FragmentActivity activity,int fSelec){

        this.context=context;
        notaList=miLista;
        fragmentActivity=activity;
        f_Seleccionado=fSelec;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.vista_nota,parent,false);

        ViewGroup.LayoutParams params=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        view.setLayoutParams(params);

        return new MyViewHolder(view);
    }


    public void onBindViewHolder(@NonNull adapterNotas.MyViewHolder holder, final int position) {

        holder.txt_titulo.setText(notaList.get(position).getTitulo());
        holder.txt_texto.setText(notaList.get(position).getTexto() );

        final int idSelec=position;

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NotaFragment editFragmentNota=new NotaFragment();
                Bundle miBundle=new Bundle();



                miBundle.putInt("id",notaList.get(idSelec).getId());
                miBundle.putBoolean("edit",true);
                miBundle.putInt("f_proveniente",f_Seleccionado);

                editFragmentNota.setArguments(miBundle);

                Fragment fragmentOcultar=fragmentActivity.getSupportFragmentManager().findFragmentById(R.id.f_nota);
                fragmentActivity.getSupportFragmentManager().beginTransaction().hide(fragmentOcultar).add(R.id.f_nota,editFragmentNota).addToBackStack(null).commit();

                //En Construccion.....

            }
        });

        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
               // Toast.makeText(context, "Seleccionando Nota Con nombre" + notaList.get(position).getTitulo(), Toast.LENGTH_SHORT).show();


                AlertDialog.Builder miAlert=new AlertDialog.Builder(context);

                miAlert.setTitle("¿Eliminar Registro?");

                miAlert.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int posicion=0;
                        if(notaList.size()==1){
                            posicion=0;
                        }else {
                            posicion=position;
                        }
                        removerNota(notaList.get(posicion).getId(),posicion);

                    }
                });

                miAlert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });


                miAlert.show();


                return true;
            }
        });
    }

    private void removerNota(int id,int p_posicion) {

        ConexionBBDDHelper miConex=new ConexionBBDDHelper(context,"bd_notas",null,1);

        SQLiteDatabase miBD=miConex.getWritableDatabase();

        miBD.delete(utilidades.TABLA_NOTAS,utilidades.CAMPO_ID+ "=" +id,null);

        notaList.remove(p_posicion);
        notifyItemRemoved(p_posicion);

        miBD.close();
        miConex.close();

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
