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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.cityshare.app.model.DoubleTypeAdapter;
import com.cityshare.app.model.HttpRequest;
import com.cityshare.app.model.Login;
import com.cityshare.app.model.Pedido;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DetalhesPedidoActivity extends AppCompatActivity {

    Context context;
    ViewFlipper flipper;
    TextView modelo_veiculo;
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
    Button visualizarPendencias;
    ListView lv_historico_alteracao;

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

        modelo_veiculo = (TextView) findViewById(R.id.txt_modelo_veiculo);
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

        visualizarPendencias = (Button) findViewById(R.id.btn_visualizar_pendencias);

        cancelarLocacao = (Button) findViewById(R.id.btn_cancelar_locacao);

        lv_historico_alteracao = (ListView) findViewById(R.id.lv_historico_alteracoes);

        idPedido = getIntent().getIntExtra("idPedido", -1);

        if( idPedido == -1 ) {
            Intent mainWin = new Intent(context, MainActivity.class);
            startActivity(mainWin);
            return;
        }

        new BuscarInfoPedido().execute();
    }

    private void relacionar_componentes_status_pedido() {
        final int cod_status_agendado = 1,
                cod_status_aguardando_definicao_local_retirada = 2,
                cod_status_aguardando_confirmacao_retirada = 3,
                cod_status_aguardando_definicao_local_entrega = 4,
                cod_status_aguardando_confirmacao_entrega = 5,
                cod_status_agurdando_definicao_pendencias = 6,
                cod_status_aguardando_confirmacao_pendencias = 7;

        final int NAO_DEFINIDO = 0, DEFINIDO = 1;

        int id_usuario = Login.getId_usuario(context);
        if( pedido.getIdStatusPedido() == cod_status_aguardando_definicao_local_retirada && pedido.getIdUsuarioLocatario() == id_usuario && pedido.getLocalRetiradaLocatario() == NAO_DEFINIDO ||
                pedido.getIdStatusPedido() == cod_status_aguardando_definicao_local_entrega && pedido.getIdUsuarioLocatario() == id_usuario && pedido.getLocalDevolucaoLocatario() == NAO_DEFINIDO )
        {
            if( pedido.getIdStatusPedido() == cod_status_aguardando_definicao_local_entrega ) {
                confirmarLocal.setText("Confirmar local de entrega");
            }

            confirmarLocal.setVisibility( View.VISIBLE );
        }

        if( pedido.getIdStatusPedido() == cod_status_aguardando_definicao_local_retirada && pedido.getIdUsuarioLocador() == id_usuario && pedido.getLocalRetiradaLocador() == NAO_DEFINIDO ||
                pedido.getIdStatusPedido() == cod_status_aguardando_definicao_local_entrega && pedido.getIdUsuarioLocador() == id_usuario && pedido.getLocalDevolucaoLocador() == NAO_DEFINIDO )
        {
            if( pedido.getIdStatusPedido() == cod_status_aguardando_definicao_local_entrega ) {
                confirmarLocal.setText("Confirmar local de entrega");
            }

            confirmarLocal.setVisibility( View.VISIBLE );
        }

        if( pedido.getIdStatusPedido() == cod_status_aguardando_confirmacao_retirada && pedido.getIdUsuarioLocatario() == id_usuario && pedido.getSolicitacaoRetiradaLocatario() == NAO_DEFINIDO ) {
            solicitarRetirada.setVisibility( View.VISIBLE );
        }

        if( pedido.getIdStatusPedido() == cod_status_aguardando_confirmacao_retirada && pedido.getIdUsuarioLocador() == id_usuario && pedido.getSolicitacaoRetiradaLocador() == NAO_DEFINIDO ) {
            solicitarRetirada.setVisibility( View.VISIBLE );
        }

        if( pedido.getIdStatusPedido() == cod_status_aguardando_confirmacao_entrega && pedido.getIdUsuarioLocatario() == id_usuario && pedido.getSolicitacaoDevolucaoLocatario() == NAO_DEFINIDO ) {
            solicitarDevolucao.setVisibility( View.VISIBLE );
        }

        if( pedido.getIdStatusPedido() == cod_status_aguardando_confirmacao_entrega && pedido.getIdUsuarioLocador() == id_usuario && pedido.getSolicitacaoDevolucaoLocador() == NAO_DEFINIDO ) {
            solicitarDevolucao.setVisibility( View.VISIBLE );
        }

        if( pedido.getIdStatusPedido() >= cod_status_aguardando_confirmacao_pendencias ) {
            visualizarPendencias.setVisibility( View.VISIBLE );
        }

        if( pedido.getIdStatusPedido() <= cod_status_aguardando_confirmacao_retirada ) {
            cancelarLocacao.setVisibility( View.VISIBLE );
        }
    }

    private void abrirModalFormaPagamentoPendencias() {

        AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle("Forma de Pagamento de Pendências").setMessage("Escolha a forma de pagamento das pendências");
        final View dialog_pagamento_pendencias = getLayoutInflater().inflate(R.layout.dialog_pagamento_pendencias, null);

        TextView txt_dias_atraso = (TextView) dialog_pagamento_pendencias.findViewById(R.id.txt_dias_atraso);
        TextView txt_litros_combustivel = (TextView) dialog_pagamento_pendencias.findViewById(R.id.txt_litros_combustivel);
        TextView txt_quilometragem_excedida = (TextView) dialog_pagamento_pendencias.findViewById(R.id.txt_quilometragem_excedida);
        Button btn_dinheiro = (Button) dialog_pagamento_pendencias.findViewById(R.id.btn_dinheiro);
        Button btn_cartao_credito = (Button) dialog_pagamento_pendencias.findViewById(R.id.btn_cartao_credito);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:dd", Locale.getDefault());

        try {
            Date data_entrega = formatter.parse( pedido.getDataEntrega() );
            Date data_entrega_efetuada = formatter.parse( pedido.getDataEntregaEfetuada() );

            long diff = data_entrega_efetuada.getTime() - data_entrega.getTime();
            int dias_atraso = (int) TimeUnit.MILLISECONDS.toDays( diff );

            txt_dias_atraso.setText( String.format(Locale.getDefault(), "%d = %.2f", dias_atraso, dias_atraso * pedido.getValorDiaria()) );

            double litros_restantes = pedido.getTanqueVeiculo() / pedido.getCombustivelRestante();
            double preco_combustivel = litros_restantes * pedido.getValorCombustivel();

            txt_litros_combustivel.setText( String.format(Locale.getDefault(), "%.2fL = R$%.2f", litros_restantes, preco_combustivel) );

            double valor_quilometragem_excedida = pedido.getQuilometragemExcedida() * pedido.getValorQuilometragem();

            txt_quilometragem_excedida.setText(
                    String.format(Locale.getDefault(), "%dKm = %.2f", pedido.getQuilometragemExcedida(), valor_quilometragem_excedida)
            );

            builder.setView( dialog_pagamento_pendencias );

            final AlertDialog dialog = builder.create();

            btn_dinheiro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    abrirModalPagamentoDinheiro();
                    dialog.dismiss();
                }
            });

            btn_cartao_credito.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    abrirModalPagamentoCartaoCredito();
                    dialog.dismiss();
                }
            });

            dialog.show();

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void abrirModalPagamentoDinheiro() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle("Pagamento em Dinheiro").setMessage("R$XX,XX devem ser pagos à ~nome do locador~").setPositiveButton("Confirmar Pagamento", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new PagarPendencias( PagarPendencias.DINHEIRO, null ).execute();
            }
        }).setNegativeButton("Voltar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                abrirModalFormaPagamentoPendencias();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void abrirModalPagamentoCartaoCredito() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle("Pagamento em Cartão de Crédito").setMessage("R$XX,XX serão descontados do cartão de crédito cadastrado em sua conta");
        View view_pagamento_cartao = getLayoutInflater().inflate(R.layout.dialog_pagamento_cartao_credito, null);
        final EditText txt_codigo_seguranca = (EditText) view_pagamento_cartao.findViewById(R.id.txt_codigo_seguranca_cartao);

        builder.setPositiveButton("Realizar Pagamento", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if( !txt_codigo_seguranca.getText().toString().isEmpty() ) {
                    String codigo_seguranca = txt_codigo_seguranca.getText().toString().trim();
                    new PagarPendencias( PagarPendencias.CARTAO_CREDITO, codigo_seguranca ).execute();
                }
            }
        });

        builder.setNegativeButton("Voltar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                abrirModalFormaPagamentoPendencias();
            }
        });

        builder.setView(view_pagamento_cartao);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void abrirModalAvaliacao() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle("Avaliar Usuário").setMessage("Curtiu a negociação? Deixe uma avaliação para ~nome do usuário~ :-)");
        View view_dialog_avaliacao = getLayoutInflater().inflate(R.layout.dialog_avaliar_usuario, null);
        builder.setView(view_dialog_avaliacao);

        final EditText comentario_avaliacao = (EditText) view_dialog_avaliacao.findViewById(R.id.edt_comentario_avaliacao);
        final SeekBar nota_avaliacao = (SeekBar) view_dialog_avaliacao.findViewById(R.id.sb_nota_avaliacao);
        final TextView label_nota_avaliacao = (TextView) view_dialog_avaliacao.findViewById(R.id.txt_label_nota);

        final List<ImageView> estrelas_avaliacao = new ArrayList<>();
        estrelas_avaliacao.add( (ImageView) view_dialog_avaliacao.findViewById(R.id.iv_estrela1) );
        estrelas_avaliacao.add( (ImageView) view_dialog_avaliacao.findViewById(R.id.iv_estrela2) );
        estrelas_avaliacao.add( (ImageView) view_dialog_avaliacao.findViewById(R.id.iv_estrela3) );
        estrelas_avaliacao.add( (ImageView) view_dialog_avaliacao.findViewById(R.id.iv_estrela4) );
        estrelas_avaliacao.add( (ImageView) view_dialog_avaliacao.findViewById(R.id.iv_estrela5) );

        nota_avaliacao.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if( progress < 0 ) {
                    progress = 0;
                } else if( progress > 5 ) {
                    progress = 5;
                }

                for( int i = 0; i < 5; ++i ) {

                    if( i < progress ) {
                        estrelas_avaliacao.get(i).setImageResource(R.mipmap.ic_blue_star);
                    } else {
                        estrelas_avaliacao.get(i).setImageResource(R.mipmap.ic_blue_star_outline);
                    }
                }

                label_nota_avaliacao.setText( String.format(Locale.getDefault(), "%d/5", progress) );
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        builder.setPositiveButton("Avaliar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String comentario = "";

                if( comentario_avaliacao.getText() != null ) {
                    comentario = comentario_avaliacao.getText().toString().trim();
                }

                new AvaliarUsuario(nota_avaliacao.getProgress(), comentario).execute();
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
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

    private class AcaoSolicitarDevolucao implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            LayoutInflater inflater = getLayoutInflater();

            View dialog_devolucao = inflater.inflate( R.layout.dialog_solicitar_entrega, null );
            TextView txt_data_entrega = (TextView) dialog_devolucao.findViewById( R.id.txt_data_entrega );
            TextView txt_data_entrega_efetuada = (TextView) dialog_devolucao.findViewById( R.id.txt_data_entrega_efetuada );
            TextView txt_dias_atraso = (TextView) dialog_devolucao.findViewById( R.id.txt_atraso );

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());

            Date data_entrega = null;
            try {
                data_entrega = formatter.parse( pedido.getDataEntrega() );

                txt_data_entrega.setText( formatter.format( data_entrega ) );

                Date data_atual = Calendar.getInstance().getTime();
                txt_data_entrega_efetuada.setText( formatter.format( data_atual ) );

                long diff = data_atual.getTime() - data_entrega.getTime();
                int dias_atraso = (int) TimeUnit.MILLISECONDS.toDays( diff );
                if( dias_atraso < 0 ) dias_atraso = 0;

                txt_dias_atraso.setText( String.format(Locale.getDefault(), "%d", dias_atraso ) );

            } catch (ParseException e) {
                e.printStackTrace();
            }

            builder.setView( dialog_devolucao ).setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    new SolicitarDevolucao().execute();
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
            AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle("Pendências Definidas").setMessage("Estas são as pendências definidas pelo locador");
            LayoutInflater inflater = getLayoutInflater();

            View dialog_confirmar_pendencias = inflater.inflate(R.layout.dialog_confirmar_pendencias, null);
            TextView txt_dias_atraso = (TextView) dialog_confirmar_pendencias.findViewById(R.id.txt_dias_atraso);
            TextView txt_combustivel_restante = (TextView) dialog_confirmar_pendencias.findViewById(R.id.txt_litros_combustivel);
            TextView txt_quilometragem_excedida = (TextView) dialog_confirmar_pendencias.findViewById(R.id.txt_quilometragem_excedida);

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());

            try {
                Date data_entrega = formatter.parse( pedido.getDataEntrega() );
                Date data_entrega_efetuada = formatter.parse( pedido.getDataEntregaEfetuada() );

                long diff = data_entrega_efetuada.getTime() - data_entrega.getTime();
                int dias_atrasados = (int) TimeUnit.MILLISECONDS.toDays( diff );
                if( dias_atrasados < 0 ) dias_atrasados = 0;

                double valor_atraso = dias_atrasados * pedido.getValorDiaria();

                txt_dias_atraso.setText( String.format(Locale.getDefault(), "%d dias = R$%.2f", dias_atrasados, valor_atraso) );

                double combustivelRestante = pedido.getTanqueVeiculo()/pedido.getCombustivelRestante();
                double valorCombustivel = pedido.getValorCombustivel();

                double valorTotalCombustivel = combustivelRestante * valorCombustivel;
                txt_combustivel_restante.setText( String.format(Locale.getDefault(), "%.2fL = R$%.2f", combustivelRestante, valorTotalCombustivel) );

                double valorQuilometragem = pedido.getValorQuilometragem();
                int quilometragemExcedida = pedido.getQuilometragemExcedida();

                double valorTotalQuilometragem = quilometragemExcedida * valorQuilometragem;
                txt_quilometragem_excedida.setText( String.format(Locale.getDefault(), "%dKm = R$%.2f", quilometragemExcedida, valorTotalQuilometragem) );

                builder.setView( dialog_confirmar_pendencias ).setPositiveButton("Concordar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new DefinirStatusPendencias( 1 ).execute();
                    }
                }).setNegativeButton("Discordar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new DefinirStatusPendencias( 0 ).execute();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
    }

    private class AcaoFormaPagamentoPendencias implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            abrirModalFormaPagamentoPendencias();
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

            try {
                Gson gson = new GsonBuilder().registerTypeAdapter(Double.class, new DoubleTypeAdapter()).create();
                pedido = gson.fromJson(json, Pedido.class);
            }catch( Exception e ) {
                Log.d("DEBUG", e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progress.dismiss();

            if( pedido == null ) {
                startActivity( new Intent(context, PedidosActivity.class) );
                return;
            }

            modelo_veiculo.setText( pedido.getVeiculo() );
            nome.setText( pedido.getNomeLocador() );
            statusPedido.setText( pedido.getStatusPedido() );

            valorTotal.setText( String.format( Locale.getDefault(), "R$%.2f", pedido.getValorTotal() ) );
            valorCombustivel.setText( String.format( Locale.getDefault(), "R$%.2f", pedido.getValorCombustivel() ) );
            valorQuilometragem.setText( String.format( Locale.getDefault(), "R$%.2f", pedido.getValorQuilometragem() ) );
            limiteQuilometragem.setText( String.valueOf( pedido.getLimiteQuilometragem() ) );
            cnhSelecionada.setText( String.valueOf( pedido.getNumeroCnh() ) );

            lv_historico_alteracao.setAdapter( new HistoricoPedidoAdapter(context, R.layout.list_view_item_historico, pedido.getHistoricoAlteracao()) );

            if( pedido.getIdStatusPedido() == 7 ) {
                visualizarPendencias.setOnClickListener(new AcaoVisualizarPendencias());
            } else if( pedido.getIdStatusPedido() == 8 ) {
                visualizarPendencias.setOnClickListener( new AcaoFormaPagamentoPendencias()  );
            }

            relacionar_componentes_status_pedido();
        }
    }

    private class SolicitarDevolucao extends AsyncTask<Void, Void, Void> {
        ProgressDialog progress;
        Boolean resultado;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = ProgressDialog.show(context, "Carregando", "Aguarde");
        }

        @Override
        protected Void doInBackground(Void... params) {
            String url = getString(R.string.serverAddr) + "apis/pedido_solicitacao.php";
            HashMap<String, String> parametros = new HashMap<>();
            parametros.put("idPedido", String.valueOf(idPedido));
            parametros.put("idUsuario", String.valueOf(Login.getId_usuario(context)));

            String json = HttpRequest.post(url, parametros);
            resultado = new Gson().fromJson(json, Boolean.class);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progress.dismiss();
        }
    }

    private class DefinirStatusPendencias extends AsyncTask<Void, Void, Void> {
        ProgressDialog progress;
        int is_pendencias_aceitas;
        Boolean resultado;

        public DefinirStatusPendencias(int aceite) {
            this.is_pendencias_aceitas = aceite;
        }

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(context, "Carregando", "Aguarde");
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String url = getString(R.string.serverAddr) + "apis/pedido_definir_pendencias.php";

            HashMap<String, String> parametros = new HashMap<>();
            parametros.put("idUsuario", String.valueOf(Login.getId_usuario(context)));
            parametros.put("idPedido", String.valueOf(pedido.getId()));
            parametros.put("statusPendencia", String.valueOf(is_pendencias_aceitas));

            String json = HttpRequest.post(url, parametros);
            Log.d("JSONPENDENCIAS", json);
            resultado = new Gson().fromJson( json, Boolean.class );

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progress.dismiss();

            if( resultado ) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle("Forma de Pagamento").setMessage("Escolha a forma de pagamento das pendências");
                View v = getLayoutInflater().inflate(R.layout.dialog_pagamento_pendencias, null);

                builder.setView( v );

                AlertDialog dialog = builder.create();
                dialog.show();

            } else {
                AlertDialog dialog = new AlertDialog.Builder(context).setTitle("Erro").setMessage("Houve uma falha ao executar a operação").create();
                dialog.show();
            }
        }
    }

    private class PagarPendencias extends AsyncTask<Void, Void, Void> {
        ProgressDialog progress;
        private static final int CARTAO_CREDITO = 1, DINHEIRO = 2;
        private int formaPagamento;
        private String codigoSeguranca;
        Boolean resultado;

        private PagarPendencias(int formaPagamento, String codigoSeguranca) {
            this.formaPagamento = formaPagamento;
            this.codigoSeguranca = codigoSeguranca;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = ProgressDialog.show(context, "Carregando", "Aguarde");
        }

        @Override
        protected Void doInBackground(Void... params) {
            String url = getString(R.string.serverAddr) + "apis/pedido_realizar_pagamento.php";

            HashMap<String, String> parametros = new HashMap<>();
            parametros.put("idPedido", String.valueOf(idPedido));
            parametros.put("formaPagamento", String.valueOf(formaPagamento));

            if( codigoSeguranca != null ) {
                parametros.put("codigoSegurancaCartao", codigoSeguranca);
            }

            String json  = HttpRequest.post(url, parametros);
            resultado = new Gson().fromJson(json, Boolean.class);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progress.dismiss();

            if( resultado ) {

                if( formaPagamento == DINHEIRO ) {
                    AlertDialog dialog = new AlertDialog.Builder(context).setTitle("Pagamento realizado").setMessage("Aguarde ~nome do locador~ confirmar o pagamento").create();
                    dialog.show();
                } else if( formaPagamento == CARTAO_CREDITO ) {
                    abrirModalAvaliacao();
                }

            } else {
                AlertDialog dialog = new AlertDialog.Builder(context).setTitle("Houve um erro").setMessage("Houve um erro ao tentar realizar o pagamento").create();
                dialog.show();
            }
        }
    }

    private class AvaliarUsuario extends AsyncTask<Void, Void, Void> {
        ProgressDialog progress;
        private String comentarioAvaliacao;
        private int notaAvaliacao;
        Boolean resultado;

        private AvaliarUsuario(int nota, String comentario) {
            this.notaAvaliacao = nota;
            this.comentarioAvaliacao = comentario;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = ProgressDialog.show(context, "Carregando", "Aguarde");
        }

        @Override
        protected Void doInBackground(Void... params) {
            String url = getString(R.string.serverAddr) + "apis/pedido_avaliacao.php";

            HashMap<String, String> parametros = new HashMap<>();
            parametros.put("notaAvaliacao", String.valueOf(notaAvaliacao));
            parametros.put("idPedido", String.valueOf(idPedido));
            parametros.put("mensagemAvaliacao", comentarioAvaliacao);
            parametros.put("idUsuario", String.valueOf(Login.getId_usuario(context)));

            String json = HttpRequest.post(url, parametros);
            resultado = new Gson().fromJson(json, Boolean.class);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progress.dismiss();

            if( resultado ) {
                AlertDialog dialog = new AlertDialog.Builder(context).setTitle("Avaliação enviada").setMessage("~nome do usuario~ agradece a sua avaliação!").create();
                dialog.show();
            } else {
                AlertDialog dialog = new AlertDialog.Builder(context).setTitle("Houve um erro").setMessage("Houve um erro ao tentar enviar a avaliação").create();
                dialog.show();
            }
        }
    }
}
