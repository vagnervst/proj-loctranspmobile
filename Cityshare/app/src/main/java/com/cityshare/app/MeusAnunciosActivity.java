package com.cityshare.app;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;

import com.cityshare.app.model.Anuncio;
import com.cityshare.app.model.HttpRequest;
import com.cityshare.app.model.Login;
import com.cityshare.app.model.Server;
import com.google.gson.Gson;

import java.util.HashMap;

public class MeusAnunciosActivity extends AppCompatActivity {

    ListView meus_anuncios;

    Context context;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_disponiveis:
                    new CarregarAnuncios( Anuncio.DISPONIVEL ).execute();
                    return true;
                case R.id.navigation_em_uso:
                    new CarregarAnuncios( Anuncio.INDISPONIVEL ).execute();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_anuncios);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        context = this;
        meus_anuncios = (ListView) findViewById( R.id.lv_meus_anuncios );
    }


    private class CarregarAnuncios extends AsyncTask<Void, Void, Anuncio[]> {
        private ProgressDialog progresso;
        private int statusAnuncio;

        private CarregarAnuncios(int status) {
            this.statusAnuncio = status;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progresso = ProgressDialog.show(context, "Carregando Anúncios", "Aguarde");
        }

        @Override
        protected Anuncio[] doInBackground(Void... params) {
            String url = Server.servidor + "apis/android/lista_anuncios.php";

            HashMap<String, String> parametros = new HashMap<>();
            parametros.put("params", "p.idStatusPublicacao = " + this.statusAnuncio + " AND p.idUsuario = " + Login.getId_usuario(context));

            String json = HttpRequest.post(url, parametros);

            return new Gson().fromJson(json, Anuncio[].class);
        }

        @Override
        protected void onPostExecute(Anuncio[] anuncios) {
            super.onPostExecute(anuncios);
            progresso.dismiss();

            MeuAnuncioAdapter adapter = new MeuAnuncioAdapter(context, R.layout.list_view_item_meu_anuncio, anuncios);
            meus_anuncios.setAdapter( adapter );
        }
    }
}
