package com.cityshare.app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cityshare.app.model.HttpRequest;
import com.cityshare.app.model.Login;
import com.cityshare.app.model.Pedido;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class PedidosActivity extends AppCompatActivity {

    ListView lv_pedidos;
    Context context;

    List<Pedido> pedidos;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_pendentes:
                    new CarregarPedidos(CarregarPedidos.PENDENTES).execute();
                    return true;
                case R.id.navigation_concluidos:
                    new CarregarPedidos(CarregarPedidos.CONCLUIDOS).execute();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        context = this;
        lv_pedidos = (ListView) findViewById( R.id.lv_pedidos );

        lv_pedidos.setOnItemClickListener( new AcaoItemPedido() );

        pedidos = new ArrayList<>();

        new CarregarPedidos(CarregarPedidos.PENDENTES).execute();

        int idNotificacao = getIntent().getIntExtra("idNotificacao", -1);

        if( idNotificacao != -1 ) {
            Log.d("IDNOTIFICACAO", String.valueOf(idNotificacao));
        }
    }

    private class CarregarPedidos extends AsyncTask<Void, Void, Void> {
        private static final int PENDENTES = 1, CONCLUIDOS = 2;
        int statusAlvo;
        ProgressDialog progress;

        private CarregarPedidos(int status) {
            this.statusAlvo = status;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = ProgressDialog.show(context, "Carregando", "Aguarde...");
        }

        @Override
        protected Void doInBackground(Void... params) {
            String url = getString( R.string.serverAddr ) + "apis/android/listar_pedidos.php";
            int id_usuario = Login.getId_usuario( context );

            HashMap<String, String> parametros = new HashMap<>();
            parametros.put("idUsuario", String.valueOf( id_usuario ));
            parametros.put("filtragemPedidos", String.valueOf( this.statusAlvo ));

            String json = HttpRequest.post(url, parametros);
            Log.d("JSON", json);

            try {
                pedidos = Arrays.asList(new Gson().fromJson(json, Pedido[].class));
            } catch( Exception e ) {
                Log.d("ERROR", e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progress.dismiss();
            lv_pedidos.setAdapter( new PedidosAdapter(context, R.layout.list_view_item_pedido, pedidos ) );

            super.onPostExecute(aVoid);
        }
    }

    private class AcaoItemPedido implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            int id_pedido = ( pedidos.get(position) ).getId();
            Log.d("idPedido", String.valueOf(id_pedido));

            Intent detalhesPedido = new Intent(context, DetalhesPedidoActivity.class);
            detalhesPedido.putExtra("idPedido", id_pedido);

            startActivity( detalhesPedido );
        }

    }
}
