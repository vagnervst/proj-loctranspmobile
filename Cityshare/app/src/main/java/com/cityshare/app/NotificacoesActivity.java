package com.cityshare.app;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;

import com.cityshare.app.model.HttpRequest;
import com.cityshare.app.model.Login;
import com.cityshare.app.model.Notificacao;
import com.cityshare.app.model.Server;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.HashMap;

public class NotificacoesActivity extends AppCompatActivity {

    ListView lv_notificacoes;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacoes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;
        lv_notificacoes = (ListView) findViewById(R.id.lv_notificacoes);

        new GetNotificacoes().execute();
    }

    private class GetNotificacoes extends AsyncTask<Void, Void, Void> {
        ProgressDialog progress;
        Notificacao[] listaNotificacoes;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = ProgressDialog.show(context, "Carregando", "Aguarde");
        }

        @Override
        protected Void doInBackground(Void... params) {
            int id_usuario = Login.getId_usuario(context);

            String url = Server.servidor + "apis/get_notificacoes_usuario.php";

            HashMap<String, String> parametros = new HashMap<>();
            parametros.put("where", "n.idUsuarioDestinatario = " + String.valueOf(id_usuario));

            String json = HttpRequest.post(url, parametros);
            Log.d("JSON", json);

            listaNotificacoes = new Gson().fromJson(json, Notificacao[].class);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progress.dismiss();

            NotificacoesAdapter adapter = new NotificacoesAdapter( context, R.layout.list_view_item_notificacao, Arrays.asList( listaNotificacoes ) );
            lv_notificacoes.setAdapter( adapter );
        }
    }

}
