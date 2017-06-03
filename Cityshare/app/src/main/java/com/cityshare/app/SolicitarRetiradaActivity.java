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
import android.widget.EditText;

import com.cityshare.app.model.HttpRequest;
import com.cityshare.app.model.Login;
import com.cityshare.app.model.Pedido;
import com.cityshare.app.model.Server;
import com.google.gson.Gson;

import java.util.HashMap;

import static com.cityshare.app.model.HttpRequest.post;

public class SolicitarRetiradaActivity extends AppCompatActivity {

    Context context;
    EditText txtCodigoSeguranca;

    final int RETIRADA = 1, DEVOLUCAO = 2;

    int idPedido;
    Pedido pedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitar_retirada);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;
        txtCodigoSeguranca = (EditText) findViewById(R.id.txt_codigo_seguranca);
        idPedido = getIntent().getIntExtra("idPedido", -1);

        if( idPedido == -1 ) {
            startActivity( new Intent(context, PedidosActivity.class) );
            return;
        }

        new BuscarInfoPedido().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_confirmar, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch( item.getItemId() ) {
            case R.id.action_confirmar:
                new AcaoSolicitarRetirada().execute();
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
            String url = Server.servidor + "apis/android/get_info_pedido.php";
            HashMap<String, String> parametros = new HashMap<>();

            parametros.put("idPedido", String.valueOf(idPedido));
            String json = post(url, parametros);

            pedido = new Gson().fromJson(json, Pedido.class);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progress.dismiss();
        }
    }

    private class AcaoSolicitarRetirada extends AsyncTask<Void, Void, Void> {
        ProgressDialog progress;
        Boolean resultado = false;
        String codigo_seguranca = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = ProgressDialog.show(context, "Carregando", "Aguarde...");

            codigo_seguranca = txtCodigoSeguranca.getText().toString().trim();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String url = Server.servidor + "apis/pedido_solicitacao.php";
            HashMap<String, String> parametros = new HashMap<>();
            parametros.put("idPedido", String.valueOf(idPedido));
            parametros.put("codigoSeguranca", codigo_seguranca);
            parametros.put("idUsuario", String.valueOf(Login.getId_usuario(context)));

            final int id_aguardando_confirmacao_retirada = 3;

            int modo = -1;
            if( pedido.getIdStatusPedido() == id_aguardando_confirmacao_retirada ) {
                modo = RETIRADA;
            } else {
                modo = DEVOLUCAO;
            }

            if( modo != -1 ) {
                parametros.put("modo", String.valueOf(modo));
                String json = HttpRequest.post(url, parametros);
                Log.d("JSONRETIRADA", json);

                resultado = new Gson().fromJson(json, Boolean.class);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progress.dismiss();

            if( resultado ) {

                Intent solicitacaoRetiradaEnviadawin = new Intent(context, SolicitacaoRetiradaEnviadaActivity.class);
                solicitacaoRetiradaEnviadawin.putExtra("idPedido", idPedido);

                startActivity( solicitacaoRetiradaEnviadawin );
            } else {
                AlertDialog dialog = new AlertDialog.Builder(context).setTitle("Erro").setMessage("Houve um erro ao tentar executar a solicitação de retirada").create();
                dialog.show();
            }
        }
    }
}
