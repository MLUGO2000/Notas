package com.lugo.manueln.app;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.lugo.manueln.app.utilidades.interFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements interFragment {

    private TextView mTextMessage;
    private final int FRAGMENT_NOTA=1;
    Fragment fragmentPrincipal;
    FloatingActionButton buttonFloat;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    fragmentPrincipal =new ListaNotasFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.f_nota,fragmentPrincipal).addToBackStack(null).commit();

                    return true;

                case R.id.navigation_dashboard:

                    fragmentPrincipal =new ListaNotasDestacadas();
                    getSupportFragmentManager().beginTransaction().replace(R.id.f_nota,fragmentPrincipal).addToBackStack(null).commit();

                    return true;

                case R.id.navigation_notifications:

                    fragmentPrincipal=new ListaNotasCategorias();
                    getSupportFragmentManager().beginTransaction().replace(R.id.f_nota,fragmentPrincipal).addToBackStack(null).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        buttonFloat=findViewById(R.id.floatButton);

        buttonFloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int conteo=getSupportFragmentManager().getBackStackEntryCount();


                Fragment fragmentOcultar=getSupportFragmentManager().findFragmentById(R.id.f_nota);
                NotaFragment fragment = new NotaFragment();
                Bundle args=new Bundle();
                args.putBoolean("edit",false);
                args.putInt("f_proveniente",FRAGMENT_NOTA);

                fragment.setArguments(args);
                getSupportFragmentManager().beginTransaction().hide(fragmentOcultar).add(R.id.f_nota, fragment,"fragment_nota").addToBackStack(null).commit();
            }
        });

        fragmentPrincipal =new ListaNotasFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.f_nota,fragmentPrincipal).addToBackStack(null).commit();


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);



    }

    @Override
    public void onBackPressed() {

        int count=getSupportFragmentManager().getBackStackEntryCount();

        if(count>1){
            getSupportFragmentManager().popBackStack();
        }else{
            super.onBackPressed();
        }
    }

    public void onFragmentInteraction(Uri uri) {

    }
}
