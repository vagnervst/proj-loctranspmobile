package com.cityshare.app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.cityshare.app.model.HttpRequest;
import com.cityshare.app.model.Login;
import com.cityshare.app.model.Pedido;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Locale;

public class DetalhesPedidoActivity extends AppCompatActivity {

    Context context;
    ViewFlipper flipper;
    TextView nome;
    TextView statusPedido;
    TextView valorTotal;
    TextView valorCombustivel;
    TextView valorQuilometragem;
    TextView limiteQuilometragem;
    TextView cnhSelecionada;
    Button solicitarRetirada;
    Button solicitarDevolucao;
    Button confirmarLocal;
    Button cancelarLocacao;

    int idPedido;

    Pedido pedido;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_detalhes:
                    flipper.setDisplayedChild(0);
                    return true;
                case R.id.navigation_acoes:
                    flipper.setDisplayedChild(1);
                    return true;
                case R.id.navigation_historico:
                    flipper.setDisplayedChild(2);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_pedido);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        context = this;
        flipper = (ViewFlipper) findViewById(R.id.flipper_detalhes_pedido);
        nome = (TextView) findViewById(R.id.txt_nome);
        statusPedido = (TextView) findViewById(R.id.txt_status_pedido);
        valorTotal = (TextView) findViewById(R.id.txt_valor_total);
        valorCombustivel = (TextView) findViewById(R.id.txt_valor_combustivel);
        valorQuilometragem = (TextView) findViewById(R.id.txt_valor_quilometragem);
        cnhSelecionada = (TextView) findViewById(R.id.txt_cnh_selecionada);
        limiteQuilometragem = (TextView) findViewById(R.id.txt_limite_quilometragem);

        confirmarLocal = (Button) findViewById(R.id.btn_confirmar_local);
        confirmarLocal.setOnClickListener( new AcaoConfirmarLocal() );

        solicitarRetirada = (Button) findViewById(R.id.btn_solicitar_retirada);
        solicitarRetirada.setOnClickListener(new AcaoSolicitarRetirada());

        solicitarDevolucao = (Button) findViewById(R.id.btn_solicitar_devolucao);
        solicitarDevolucao.setOnClickListener( new AcaoSolicitarDevolucao() );

        cancelarLocacao = (Button) findViewById(R.id.btn_cancelar_locacao);


        idPedido = getIntent().getIntExtra("idPedido", -1);

        if( idPedido == -1 ) {
            Intent mainWin = new Intent(context, MainActivity.class);
            startActivity(mainWin);
            return;
        }

        new BuscarInfoPedido().execute();
    }

    private void relacionar_componentes_status_pedido() {
        final int id_status_aguardando_definicao_local_retirada = 2, id_status_aguardando_confirmacao_retirada = 3, id_status_aguardando_definicao_local_entrega = 4, id_status_aguardando_confirmacao_entrega = 5;
        final int NAO_DEFINIDO = 0, DEFINIDO = 1;

        int id_usuario = Login.getId_usuario(context);
        if( pedido.getIdStatusPedido() == id_status_aguardando_definicao_local_retirada && pedido.getIdUsuarioLocatario() == id_usuario && pedido.getLocalRetiradaLocatario() == NAO_DEFINIDO ||
                pedido.getIdStatusPedido() == id_status_aguardando_definicao_local_entrega && pedido.getIdUsuarioLocatario() == id_usuario && pedido.getLocalDevolucaoLocatario() == NAO_DEFINIDO )
        {
            if( pedido.getIdStatusPedido() == id_status_aguardando_definicao_local_entrega ) {
                confirmarLocal.setText("Confirmar local de entrega");
            }

            confirmarLocal.setVisibility( View.VISIBLE );
        }

        if( pedido.getIdStatusPedido() == id_status_aguardando_definicao_local_retirada && pedido.getIdUsuarioLocador() == id_usuario && pedido.getLocalRetiradaLocador() == NAO_DEFINIDO ||
                pedido.getIdStatusPedido() == id_status_aguardando_definicao_local_entrega && pedido.getIdUsuarioLocador() == id_usuario && pedido.getLocalDevolucaoLocador() == NAO_DEFINIDO )
        {
            if( pedido.getIdStatusPedido() == id_status_aguardando_definicao_local_entrega ) {
                confirmarLocal.setText("Confirmar local de entrega");
            }

            confirmarLocal.setVisibility( View.VISIBLE );
        }

        if( pedido.getIdStatusPedido() == id_status_aguardando_confirmacao_retirada && pedido.getIdUsuarioLocatario() == id_usuario && pedido.getSolicitacaoRetiradaLocatario() == NAO_DEFINIDO ) {
            solicitarRetirada.setVisibility( View.VISIBLE );
        }

        if( pedido.getIdStatusPedido() == id_status_aguardando_confirmacao_retirada && pedido.getIdUsuarioLocador() == id_usuario && pedido.getSolicitacaoRetiradaLocador() == NAO_DEFINIDO ) {
            solicitarRetirada.setVisibility( View.VISIBLE );
        }

        if( pedido.getIdStatusPedido() == id_status_aguardando_confirmacao_entrega && pedido.getIdUsuarioLocatario() == id_usuario && pedido.getSolicitacaoDevolucaoLocatario() == NAO_DEFINIDO ) {
            solicitarDevolucao.setVisibility( View.VISIBLE );
        }

        if( pedido.getIdStatusPedido() == id_status_aguardando_confirmacao_entrega && pedido.getIdUsuarioLocador() == id_usuario && pedido.getSolicitacaoDevolucaoLocador() == NAO_DEFINIDO ) {
            solicitarDevolucao.setVisibility( View.VISIBLE );
        }

        if( pedido.getIdStatusPedido() <= id_status_aguardando_confirmacao_retirada ) {
            cancelarLocacao.setVisibility( View.VISIBLE );
        }
    }

    private class AcaoConfirmarLocal implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent confirmarLocalWin = new Intent(context, ConfirmarLocalActivity.class);
            confirmarLocalWin.putExtra("idPedido", idPedido);

            startActivity( confirmarLocalWin);
        }
    }

    private class AcaoSolicitarRetirada implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent solicitarRetiradaWin = new Intent(context, SolicitarRetiradaActivity.class);
            solicitarRetiradaWin.putExtra("idPedido", idPedido);

            startActivity( solicitarRetiradaWin );
        }
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
            String json = HttpRequest.post(url, parametros);

            pedido = new Gson().fromJson(json, Pedido.class);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progress.dismiss();

            nome.setText( pedido.getNomeLocador() );
            statusPedido.setText( pedido.getStatusPedido() );

            valorTotal.setText( String.format( Locale.getDefault(), "R$%.2f", pedido.getValorTotal() ) );
            valorCombustivel.setText( String.format( Locale.getDefault(), "R$%.2f", pedido.getValorCombustivel() ) );
            valorQuilometragem.setText( String.format( Locale.getDefault(), "R$%.2f", pedido.getValorQuilometragem() ) );
            limiteQuilometragem.setText( String.valueOf( pedido.getLimiteQuilometragem() ) );
            cnhSelecionada.setText( String.valueOf( pedido.getNumeroCnh() ) );

            relacionar_componentes_status_pedido();
        }
    }

    private class AcaoSolicitarDevolucao implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            LayoutInflater inflater = getLayoutInflater();

            builder.setView( inflater.inflate(R.layout.dialog_solicitar_entrega, null) ).setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            })
            .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    private class AcaoVisualizarPendencias implements View.OnClickListener {

        @Override
        public void onClick(View v) {

        }
    }

}
