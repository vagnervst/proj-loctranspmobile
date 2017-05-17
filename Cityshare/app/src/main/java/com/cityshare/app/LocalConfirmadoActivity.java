package com.cityshare.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class LocalConfirmadoActivity extends AppCompatActivity {

    Button btn_voltar_pedido;

    Context context;

    int idPedido;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_confirmado);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_voltar_pedido = (Button) findViewById(R.id.btn_voltar_pedido);
        btn_voltar_pedido.setOnClickListener( new AcaoVoltarPedido() );

        idPedido = getIntent().getIntExtra("idPedido", -1);

        context = this;

        if( idPedido == -1 ) {
            startActivity( new Intent(context, PedidosActivity.class) );
            return;
        }

    }

    private void voltar_para_pedido() {
        Intent detalhesPedidoWin = new Intent( context, DetalhesPedidoActivity.class );
        detalhesPedidoWin.putExtra( "idPedido", idPedido );
        startActivity( detalhesPedidoWin );
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        voltar_para_pedido();
    }

    private class AcaoVoltarPedido implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            voltar_para_pedido();
        }
    }

}
