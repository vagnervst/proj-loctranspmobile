package com.cityshare.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class SolicitacaoRetiradaEnviadaActivity extends AppCompatActivity {

    Button btnRetornar;

    Context context;

    int idPedido;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitacao_retirada_enviada);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;

        btnRetornar = (Button) findViewById(R.id.btn_retornar);
        btnRetornar.setOnClickListener( new AcaoRetornar() );

        idPedido = getIntent().getIntExtra("idPedido", -1);
        if( idPedido == -1 ) {
            startActivity( new Intent(context, PedidosActivity.class) );
            return;
        }
    }

    private class AcaoRetornar implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent detalhesPedidoWin = new Intent(context, DetalhesPedidoActivity.class);
            detalhesPedidoWin.putExtra("idPedido", idPedido);

            startActivity(detalhesPedidoWin);
        }
    }
}
