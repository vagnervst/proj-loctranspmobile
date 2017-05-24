package com.cityshare.app;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.cityshare.app.R;
import com.cityshare.app.model.Fabricante;
import com.cityshare.app.model.HttpRequest;
import com.cityshare.app.model.Login;
import com.cityshare.app.model.Pedido;
import com.cityshare.app.model.TipoCombustivel;
import com.cityshare.app.model.TipoVeiculo;
import com.cityshare.app.model.Transmissao;
import com.cityshare.app.model.Veiculo;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Struct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.cityshare.app.model.HttpRequest.post;

public class AnunciarActivity extends AppCompatActivity {

    Context context;
    Button btnAnunciar;
    EditText txtTitulo;
    EditText txtDescricao;
    Spinner spnTipoVeiculo;
    Spinner spnFabricante;
    Spinner spnTipoCombustivel;
    Spinner spnTransmissao;
    Spinner spnQtdPortas;
    Spinner spnModeloVeiculo;
    EditText txtValorVeiculo;
    EditText txtQuilometragem;
    EditText txtValorDiaria;
    EditText txtValorCombustivel;
    EditText txtLimiteQuilometragem;
    EditText txtValorQuilometragemExcedida;
    List<TipoVeiculo> listaTipoVeiculo;
    List<Fabricante> listaFabricantes;
    List<Transmissao> listaTransmissoes;
    List<TipoCombustivel> listaTipoCombustivel;
    List<Veiculo> listaModelosVeiculo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anunciar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;
        btnAnunciar = (Button) findViewById(R.id.btn_anunciar);
        txtTitulo = (EditText) findViewById(R.id.titulo_anuncio);
        txtDescricao = (EditText) findViewById(R.id.descricao_anuncio);
        txtQuilometragem = (EditText) findViewById(R.id.quilometragem);
        txtValorDiaria = (EditText) findViewById(R.id.valor_diaria);
        txtValorCombustivel = (EditText) findViewById(R.id.valor_combustivel);
        txtLimiteQuilometragem = (EditText) findViewById(R.id.limite_quilometragem);
        txtValorQuilometragemExcedida = (EditText) findViewById(R.id.valor_quilometragem_excedida);
        txtValorVeiculo = (EditText) findViewById(R.id.valor_veiculo);
        spnTipoVeiculo = (Spinner) findViewById(R.id.spn_tipo_veiculo);
        spnFabricante = (Spinner) findViewById(R.id.spn_fabricante);
        spnTipoCombustivel = (Spinner) findViewById(R.id.spn_tipo_combustivel);
        spnTransmissao = (Spinner) findViewById(R.id.spn_transmissao);
        spnQtdPortas = (Spinner) findViewById(R.id.spn_qtd_portas);
        spnModeloVeiculo = (Spinner) findViewById(R.id.spn_modelo_veiculo);

        new BuscarTiposVeiculo().execute();
        new BuscarFabricantes().execute();
        new BuscarTransmissoes().execute();
        new BuscarTiposCombustivel().execute();
        new BuscarModelosVeiculo().execute();

        btnAnunciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AcaoAnunciar().execute();
            }
        });
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
            String url = getString( R.string.serverAddr ) + "apis/android/listar_tipo_veiculo.php";
            HashMap<String, String> parametros = new HashMap<>();

            String json = post(url, parametros);
            Log.d("DEBUG", json);

            listaTipoVeiculo = new ArrayList<>();
            try {
                JSONArray arrayTipoVeiculo = new JSONArray(json.toString());
                JSONObject tipoVeiculo;

                for( int i = 0; i < arrayTipoVeiculo.length(); i++ ) {
                    tipoVeiculo = new JSONObject(arrayTipoVeiculo.getString(i));

                    TipoVeiculo objTipoVeiculo = new TipoVeiculo();
                    objTipoVeiculo.setId(tipoVeiculo.getInt("id"));
                    objTipoVeiculo.setTitulo(tipoVeiculo.getString("titulo"));
                    listaTipoVeiculo.add(objTipoVeiculo);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progress.dismiss();

            ArrayAdapter<TipoVeiculo> adapterTipoVeiculo = new ArrayAdapter<>(AnunciarActivity.this, android.R.layout.simple_spinner_item, listaTipoVeiculo);
            adapterTipoVeiculo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spnTipoVeiculo.setAdapter(adapterTipoVeiculo);
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
            String url = getString( R.string.serverAddr ) + "apis/android/listar_fabricantes.php";
            HashMap<String, String> parametros = new HashMap<>();

            String json = post(url, parametros);
            Log.d("DEBUG", json);

            listaFabricantes = new ArrayList<>();
            try {
                JSONArray arrayFabricantes = new JSONArray(json.toString());
                JSONObject fabricantes;

                for( int i = 0; i < arrayFabricantes.length(); i++ ) {
                    fabricantes = new JSONObject(arrayFabricantes.getString(i));

                    Fabricante objFabricantes = new Fabricante();
                    objFabricantes.setId(fabricantes.getInt("id"));
                    objFabricantes.setNome(fabricantes.getString("nome"));
                    listaFabricantes.add(objFabricantes);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progress.dismiss();

            ArrayAdapter<Fabricante> adapterFabricante = new ArrayAdapter<>(AnunciarActivity.this, android.R.layout.simple_spinner_item, listaFabricantes);
            adapterFabricante.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spnFabricante.setAdapter(adapterFabricante);
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
            String url = getString( R.string.serverAddr ) + "apis/android/listar_transmissao.php";
            HashMap<String, String> parametros = new HashMap<>();

            String json = post(url, parametros);
            Log.d("DEBUG", json);

            listaTransmissoes = new ArrayList<>();
            try {
                JSONArray arrayTransmissoes = new JSONArray(json.toString());
                JSONObject trasmissoes;

                for( int i = 0; i < arrayTransmissoes.length(); i++ ) {
                    trasmissoes = new JSONObject(arrayTransmissoes.getString(i));

                    Transmissao objTransmissoes = new Transmissao();
                    objTransmissoes.setId(trasmissoes.getInt("id"));
                    objTransmissoes.setTitulo(trasmissoes.getString("titulo"));
                    listaTransmissoes.add(objTransmissoes);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progress.dismiss();

            ArrayAdapter<Transmissao> adapterTransmissao = new ArrayAdapter<>(AnunciarActivity.this, android.R.layout.simple_spinner_item, listaTransmissoes);
            adapterTransmissao.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spnTransmissao.setAdapter(adapterTransmissao);
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
            String url = getString( R.string.serverAddr ) + "apis/android/listar_tipo_combustivel.php";
            HashMap<String, String> parametros = new HashMap<>();

            String json = post(url, parametros);
            Log.d("DEBUG", json);

            listaTipoCombustivel = new ArrayList<>();
            try {
                JSONArray arrayTipoCombustivel = new JSONArray(json.toString());
                JSONObject tiposCombustivel;

                for( int i = 0; i < arrayTipoCombustivel.length(); i++ ) {
                    tiposCombustivel = new JSONObject(arrayTipoCombustivel.getString(i));

                    TipoCombustivel objTiposCombustivel= new TipoCombustivel();
                    objTiposCombustivel.setId(tiposCombustivel.getInt("id"));
                    objTiposCombustivel.setNome(tiposCombustivel.getString("nome"));
                    listaTipoCombustivel.add(objTiposCombustivel);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progress.dismiss();

            ArrayAdapter<TipoCombustivel> adapterTipoCombustivel = new ArrayAdapter<>(AnunciarActivity.this, android.R.layout.simple_spinner_item, listaTipoCombustivel);
            adapterTipoCombustivel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spnTipoCombustivel.setAdapter(adapterTipoCombustivel);
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
            String url = getString( R.string.serverAddr ) + "apis/android/listar_veiculos.php";
            HashMap<String, String> parametros = new HashMap<>();

            String json = post(url, parametros);
            Log.d("DEBUG", json);

            listaModelosVeiculo = new ArrayList<>();
            try {
                JSONArray arrayModelosVeiculo = new JSONArray(json.toString());
                JSONObject modelosVeiculo;

                for( int i = 0; i < arrayModelosVeiculo.length(); i++ ) {
                    modelosVeiculo = new JSONObject(arrayModelosVeiculo.getString(i));

                    Veiculo objModelosVeiculo = new Veiculo();
                    objModelosVeiculo.setId(modelosVeiculo.getInt("id"));
                    objModelosVeiculo.setNome(modelosVeiculo.getString("nome"));
                    listaModelosVeiculo.add(objModelosVeiculo);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progress.dismiss();

            ArrayAdapter<Veiculo> adapterModelosVeiculo = new ArrayAdapter<>(AnunciarActivity.this, android.R.layout.simple_spinner_item, listaModelosVeiculo);
            adapterModelosVeiculo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spnModeloVeiculo.setAdapter(adapterModelosVeiculo);
        }
    }
    private class AcaoAnunciar extends AsyncTask<Void, Void, Void> {
        ProgressDialog progress;
        Boolean resultado = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = ProgressDialog.show(context, "Carregando", "Aguarde...");
        }

        @Override
        protected Void doInBackground(Void... params) {
            String url = getString(R.string.serverAddr) + "apis/anuncio_publicar.php";
            HashMap<String, String> parametros = new HashMap<>();

            parametros.put("idUsuario", String.valueOf(Login.getId_usuario(context)));
            parametros.put("txtTitulo", String.valueOf(txtTitulo));
            parametros.put("txtDescricao", String.valueOf(txtDescricao));
            parametros.put("slTipo", String.valueOf(spnTipoVeiculo));
            parametros.put("slFabricante", String.valueOf(spnFabricante));
            parametros.put("slCombustivel", String.valueOf(spnTipoCombustivel));
            parametros.put("slTransmissao", String.valueOf(spnTransmissao));
            parametros.put("slModelo", String.valueOf(spnModeloVeiculo));
            parametros.put("txtQuilometragem", String.valueOf(txtQuilometragem));
            parametros.put("txtLimiteQuilometragem", String.valueOf(txtLimiteQuilometragem));
            parametros.put("txtValorQuilometragem", String.valueOf(txtValorQuilometragemExcedida));
            parametros.put("txtValorVeiculo", String.valueOf(txtValorVeiculo));
            parametros.put("txtValorDiaria", String.valueOf(txtValorDiaria));
            parametros.put("txtValorCombustivel", String.valueOf(txtValorCombustivel));


            String json = HttpRequest.post( url, parametros );
            Log.d("JSON", json);

            //resultado = new Gson().fromJson( json, Boolean.class );

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progress.dismiss();

            if( resultado ) {
                Toast toast = Toast.makeText(context, "Inserido com Sucesso!", Toast.LENGTH_LONG);
                toast.show();
            } else {
                Toast toast = Toast.makeText(context, "Deu ruim", Toast.LENGTH_LONG);
                toast.show();
            }

        }
    }
}
