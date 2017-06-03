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
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.cityshare.app.model.Cnh;
import com.cityshare.app.model.HttpRequest;
import com.cityshare.app.model.Login;
import com.cityshare.app.model.Server;
import com.cityshare.app.model.Usuario;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static com.cityshare.app.model.HttpRequest.post;

public class ConfirmarInfoReserva extends AppCompatActivity {

    Context context;
    TextView telefone, celular, email, data_retirada, data_entrega, txt_diarias, txt_valor_total;
    Spinner sp_cnh;

    Usuario usuario;
    int id_anuncio;
    long time_data_retirada;
    long time_data_entrega;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_info_reserva);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;
        telefone = (TextView) findViewById(R.id.txt_telefone);
        celular = (TextView) findViewById(R.id.txt_celular);
        email = (TextView) findViewById(R.id.txt_email);
        data_retirada = (TextView) findViewById(R.id.txt_data_retirada);
        data_entrega = (TextView) findViewById(R.id.txt_data_entrega);
        txt_diarias = (TextView) findViewById(R.id.txt_diarias);
        txt_valor_total = (TextView) findViewById(R.id.txt_valor_total);
        sp_cnh = (Spinner) findViewById(R.id.sp_cnh);

        id_anuncio = getIntent().getIntExtra("idAnuncio", -1);
        if( id_anuncio == -1 ) {
            startActivity( new Intent(context, MainActivity.class));
            return;
        }

        time_data_retirada = getIntent().getLongExtra("dataRetirada", -1);
        time_data_entrega = getIntent().getLongExtra("dataEntrega", -1);

        data_retirada.setText( new SimpleDateFormat().format( new Date(time_data_retirada) ) );
        data_entrega.setText( new SimpleDateFormat().format( new Date(time_data_entrega) ) );

        long diarias = TimeUnit.MILLISECONDS.toDays( time_data_entrega - time_data_retirada );
        txt_diarias.setText( String.format( Locale.getDefault(), "%d", diarias ) );

        double valor_diaria = getIntent().getDoubleExtra("valorDiaria", -1);
        double total = valor_diaria * diarias;

        txt_valor_total.setText( String.format(Locale.getDefault(), "R$%.2f", total) );

        new GetInfoUsuario().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_confirmar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch( item.getItemId() ) {
            case R.id.action_confirmar:
                new GerarPedido().execute();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private class GetInfoUsuario extends AsyncTask<Void, Void, Void> {
        ProgressDialog progress;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = ProgressDialog.show(context, "Carregando", "Aguarde");
        }

        @Override
        protected Void doInBackground(Void... params) {
            String url = Server.servidor + "apis/android/get_info_usuario.php";

            HashMap<String, String> parametros = new HashMap<>();

            int id_usuario = Login.getId_usuario(context);
            parametros.put("idUsuario", String.valueOf(id_usuario));

            String json = post(url, parametros);
            Log.d("JSON", json);

            usuario = new Gson().fromJson(json, Usuario.class);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progress.dismiss();

            telefone.setText( usuario.getTelefone() );
            celular.setText( usuario.getCelular() );
            email.setText( usuario.getEmail() );

            ArrayAdapter spinner_adapter = new ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, usuario.getListaCnh());
            sp_cnh.setAdapter( spinner_adapter );
        }
    }

    private class GerarPedido extends AsyncTask<Void, Void, Void> {
        ProgressDialog progress;
        int id_cnh_selecionado;
        int id_usuario;
        Boolean resultado;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = ProgressDialog.show(context, "Gerando Pedido...", "Aguarde");
            id_cnh_selecionado = ( (Cnh) sp_cnh.getSelectedItem()).getId();
            id_usuario = Login.getId_usuario(context);
        }

        @Override
        protected Void doInBackground(Void... params) {

            String url = Server.servidor + "apis/android/gerar_pedido.php";
            HashMap<String, String> parametros = new HashMap<>();
            parametros.put("idPublicacao", String.valueOf(id_anuncio));

            parametros.put("dataRetirada", String.valueOf(time_data_retirada/1000));
            parametros.put("dataDevolucao", String.valueOf(time_data_entrega/1000));
            parametros.put("idCnh", String.valueOf(id_cnh_selecionado));
            parametros.put("idUsuarioLocatario", String.valueOf(id_usuario));

            String json = HttpRequest.post(url, parametros);
            Log.d("JSONAGENDAMENTO", json);

            resultado = new Gson().fromJson(json, Boolean.class);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progress.dismiss();

            if( resultado ) {
                Intent winPedidoEnviado = new Intent(context, PedidoEnviado.class);
                winPedidoEnviado.putExtra("idAnuncio", id_anuncio);
                winPedidoEnviado.putExtra("dataRetirada", time_data_retirada);
                winPedidoEnviado.putExtra("dataEntrega", time_data_entrega);

                startActivity( winPedidoEnviado );
            } else {
                AlertDialog dialog = new AlertDialog.Builder(context).setTitle("Erro").setMessage("Não foi possível gerar o pedido").create();
                dialog.show();
            }
        }
    }
}
