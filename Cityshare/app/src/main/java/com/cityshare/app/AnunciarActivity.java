package com.cityshare.app;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.HashMap;

public class AnunciarActivity extends AppCompatActivity {

    Context context;
    EditText txtTitulo;
    EditText txtDescricao;
    Spinner spnTipoVeiculo;
    Spinner spnFabricante;
    Spinner spnTipoCombustivel;
    Spinner spnTransmissao;
    Spinner spnQtdPortas;
    Spinner spnModeloVeiculo;
    EditText txtQuilometragem;
    EditText txtValorDiaria;
    EditText txtValorCombustivel;
    EditText txtLimiteQuilometragem;
    EditText txtValorQuilometragemExcedida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anunciar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;
        txtTitulo = (EditText) findViewById(R.id.titulo_anuncio);
        txtDescricao = (EditText) findViewById(R.id.descricao_anuncio);
        txtQuilometragem = (EditText) findViewById(R.id.quilometragem);
        txtValorDiaria = (EditText) findViewById(R.id.valor_diaria);
        txtValorCombustivel = (EditText) findViewById(R.id.valor_combustivel);
        txtLimiteQuilometragem = (EditText) findViewById(R.id.limite_quilometragem);
        txtValorQuilometragemExcedida = (EditText) findViewById(R.id.valor_quilometragem_excedida);
        spnTipoVeiculo = (Spinner) findViewById(R.id.spn_tipo_veiculo);
        spnFabricante = (Spinner) findViewById(R.id.spn_fabricante);
        spnTipoCombustivel = (Spinner) findViewById(R.id.spn_tipo_combustivel);
        spnTransmissao = (Spinner) findViewById(R.id.spn_transmissao);
        spnQtdPortas = (Spinner) findViewById(R.id.spn_qtd_portas);
        spnModeloVeiculo = (Spinner) findViewById(R.id.spn_modelo_veiculo);
    }

    private class BuscarTiposVeiculo extends AsyncTask<Void, Void, Void> {
        ProgressDialog progress;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = ProgressDialog.show(context, "Carregando", "Aguarde...");
        }

        @Override
        protected Void doInBackground(Void... params) {
            String url = getString( R.string.serverAddr ) + "apis/android/listar_tipos_veiculo.php";
            HashMap<String, String> parametros = new HashMap<>();

            
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progress.dismiss();
        }
    }
    private class BuscarFabricantes extends AsyncTask<Void, Void, Void> {
        ProgressDialog progress;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = ProgressDialog.show(context, "Carregando", "Aguarde...");
        }

        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progress.dismiss();
        }
    }
    private class BuscarTransmissoes extends AsyncTask<Void, Void, Void> {
        ProgressDialog progress;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = ProgressDialog.show(context, "Carregando", "Aguarde...");
        }

        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progress.dismiss();
        }
    }
    private class BuscarTiposCombustivel extends AsyncTask<Void, Void, Void> {
        ProgressDialog progress;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = ProgressDialog.show(context, "Carregando", "Aguarde...");
        }

        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progress.dismiss();
        }
    }
    private class BuscarModelosVeiculo extends AsyncTask<Void, Void, Void> {
        ProgressDialog progress;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = ProgressDialog.show(context, "Carregando", "Aguarde...");
        }

        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progress.dismiss();
        }
    }
    private class AcaoAnunciar extends AsyncTask<Void, Void, Void> {
        ProgressDialog progress;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = ProgressDialog.show(context, "Carregando", "Aguarde...");
        }

        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
