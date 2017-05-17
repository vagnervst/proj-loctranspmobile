package com.cityshare.app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.cityshare.app.model.HttpRequest;
import com.cityshare.app.model.Login;
import com.cityshare.app.model.Pedido;
import com.google.gson.Gson;

import java.util.HashMap;

import static com.cityshare.app.model.HttpRequest.post;

public class ConfirmarLocalActivity extends AppCompatActivity {

    Context context;

    final int RETIRADA = 1, DEVOLUCAO = 2;
    int idPedido;

    Pedido pedido;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_local);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;

        idPedido = getIntent().getIntExtra("idPedido", -1);

        if( idPedido == -1 ) {
            startActivity( new Intent(context, MainActivity.class) );
            return;
        }

        new BuscarInfoPedido().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_confirmar_local, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch( item.getItemId() ) {
            case R.id.action_confirmar_local:
                new DefinirLocal().execute();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private class BuscarInfoPedido extends AsyncTask<Void, Void, Void> {
        ProgressDialog progress;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = ProgressDialog.show(context, "Carregando", "Aguarde...");
        }

        @Override
        protected Void doInBackground(Void... params) {
            String url = getString( R.string.serverAddr ) + "apis/android/get_info_pedido.php";
            HashMap<String, String> parametros = new HashMap<>();

            parametros.put("idPedido", String.valueOf(idPedido));
            String json = post(url, parametros);
            Log.d("DEBUG", json);

            pedido = new Gson().fromJson(json, Pedido.class);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progress.dismiss();
        }
    }

    private class DefinirLocal extends AsyncTask<Void, Void, Void> {
        ProgressDialog progress;

        Boolean resultado = false;
        int modo = -1;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = ProgressDialog.show(context, "Carregando", "Aguarde");
        }

        @Override
        protected Void doInBackground(Void... params) {
            String url = getString(R.string.serverAddr) + "apis/pedido_definir_local.php";
            HashMap<String, String> parametros = new HashMap<>();

            parametros.put("idPedido", String.valueOf(idPedido));
            parametros.put("idUsuario", String.valueOf(Login.getId_usuario(context)));

            int id_status_aguardando_confirmacao_local_retirada = 2;
            int id_status_aguardando_confirmacao_local_entrega = 4;

            if( pedido.getIdStatusPedido() == id_status_aguardando_confirmacao_local_retirada ) {
                modo = RETIRADA;
            } else if( pedido.getIdStatusPedido() == id_status_aguardando_confirmacao_local_entrega ) {
                modo = DEVOLUCAO;
            }

            if( modo != -1 ) {
                parametros.put("modo", String.valueOf(modo));
                String json = HttpRequest.post( url, parametros );
                resultado = new Gson().fromJson( json, Boolean.class );
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progress.dismiss();

            if( resultado ) {

                Intent localConfirmadoWin = new Intent(context, LocalConfirmadoActivity.class);
                localConfirmadoWin.putExtra("idPedido", idPedido);
                startActivity( localConfirmadoWin );

            } else {
                String erro = ( modo == RETIRADA )? getString(R.string.text_def_local_retirada_erro) : getString(R.string.text_def_local_entrega_erro);

                AlertDialog dialog = new AlertDialog.Builder(context).setTitle("Erro").setMessage(erro).create();
                dialog.show();
            }
        }
    }
}
