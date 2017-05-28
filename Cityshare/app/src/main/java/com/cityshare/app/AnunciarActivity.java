package com.cityshare.app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.cityshare.app.model.Combustivel;
import com.cityshare.app.model.Fabricante;
import com.cityshare.app.model.HttpRequest;
import com.cityshare.app.model.Login;
import com.cityshare.app.model.TipoVeiculo;
import com.cityshare.app.model.Transmissao;
import com.cityshare.app.model.Utils;
import com.cityshare.app.model.Veiculo;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.cityshare.app.model.HttpRequest.post;

public class AnunciarActivity extends AppCompatActivity {

    private ViewFlipper flipper;
    Context context;
    Button btnAnunciar;

    ImageButton ib_imagem_principal;
    ImageButton ib_imagem_a;
    ImageButton ib_imagem_b;
    ImageButton ib_imagem_c;
    ImageButton ib_imagem_d;
    EditText txtTitulo;
    EditText txtDescricao;
    Spinner spnTipoVeiculo;
    Spinner spnFabricante;
    Spinner spnTipoCombustivel;
    Spinner spnTransmissao;
    SeekBar skQtdPortas;
    TextView label_qtd_portas;
    Spinner spnModeloVeiculo;
    EditText txtValorVeiculo;
    EditText txtQuilometragem;
    EditText txtValorDiaria;
    EditText txtValorCombustivel;
    EditText txtLimiteQuilometragem;
    EditText txtValorQuilometragemExcedida;

    private final int GET_IMAGEM_PRINCIPAL = 1, GET_IMAGEM_A = 2, GET_IMAGEM_B = 3, GET_IMAGEM_C = 4, GET_IMAGEM_D = 5;
    private Bitmap imagem_principal = null, imagem_a = null, imagem_b = null, imagem_c = null, imagem_d = null;
    private HashMap<String, String> parametros_pesquisa;
    private HashMap<String, String> parametros_anuncio;
    private String modo = "insert";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_info_veiculo:
                    flipper.setDisplayedChild(0);
                    return true;
                case R.id.navigation_info_valores:
                    flipper.setDisplayedChild(1);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anunciar);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        context = this;
        flipper = (ViewFlipper) findViewById(R.id.vf_flipper);

        ib_imagem_principal = (ImageButton) findViewById(R.id.ib_imagem_principal);
        ib_imagem_a = (ImageButton) findViewById(R.id.ib_imagem_a);
        ib_imagem_b = (ImageButton) findViewById(R.id.ib_imagem_b);
        ib_imagem_c = (ImageButton) findViewById(R.id.ib_imagem_c);
        ib_imagem_d = (ImageButton) findViewById(R.id.ib_imagem_d);

        ib_imagem_principal.setOnClickListener( new AcaoSelecionarFoto(GET_IMAGEM_PRINCIPAL) );
        ib_imagem_a.setOnClickListener( new AcaoSelecionarFoto(GET_IMAGEM_A) );
        ib_imagem_b.setOnClickListener( new AcaoSelecionarFoto(GET_IMAGEM_B) );
        ib_imagem_c.setOnClickListener( new AcaoSelecionarFoto(GET_IMAGEM_C) );
        ib_imagem_d.setOnClickListener( new AcaoSelecionarFoto(GET_IMAGEM_D) );

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
        skQtdPortas = (SeekBar) findViewById(R.id.sk_qtd_portas);
        label_qtd_portas = (TextView) findViewById(R.id.txt_qtd_portas);
        spnModeloVeiculo = (Spinner) findViewById(R.id.spn_modelo_veiculo);

        skQtdPortas.setProgress(0);
        skQtdPortas.setMax(2);
        label_qtd_portas.setText( String.format(Locale.getDefault(), "%d", skQtdPortas.getProgress()) );

        skQtdPortas.setOnSeekBarChangeListener( new AcaoSelecionarQuantidadePortas() );

        parametros_pesquisa = new HashMap<>();
        parametros_anuncio = new HashMap<>();

        txtTitulo.setText("titulo");
        txtDescricao.setText("descricao");
        txtQuilometragem.setText("100");
        txtValorDiaria.setText("5.30");
        txtValorCombustivel.setText("2.20");
        txtLimiteQuilometragem.setText("100");
        txtValorQuilometragemExcedida.setText("5");
        txtValorVeiculo.setText("400");

        new BuscarTiposVeiculo().execute();
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
                preparar_entradas_para_envio();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if( resultCode == RESULT_OK ) {
            try {
                switch( requestCode ) {
                    case GET_IMAGEM_PRINCIPAL:
                        imagem_principal = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                        imagem_principal = Utils.resize(imagem_principal, ib_imagem_principal.getWidth(), ib_imagem_principal.getHeight());
                        ib_imagem_principal.setImageBitmap(imagem_principal);
                        break;
                    case GET_IMAGEM_A:
                        imagem_a = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                        imagem_a = Utils.resize(imagem_a, ib_imagem_a.getWidth(), ib_imagem_a.getHeight());
                        ib_imagem_a.setImageBitmap(imagem_a);
                        break;
                    case GET_IMAGEM_B:
                        imagem_b = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                        imagem_b = Utils.resize(imagem_b, ib_imagem_b.getWidth(), ib_imagem_b.getHeight());
                        ib_imagem_b.setImageBitmap(imagem_b);
                        break;
                    case GET_IMAGEM_C:
                        imagem_c = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                        imagem_c = Utils.resize(imagem_c, ib_imagem_c.getWidth(), ib_imagem_c.getHeight());
                        ib_imagem_c.setImageBitmap(imagem_c);
                        break;
                    case GET_IMAGEM_D:
                        imagem_d = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                        imagem_d = Utils.resize(imagem_d, ib_imagem_d.getWidth(), ib_imagem_d.getHeight());
                        ib_imagem_d.setImageBitmap(imagem_d);
                        break;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void preparar_entradas_para_envio() {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        if( imagem_principal != null ) {
            imagem_principal.compress(Bitmap.CompressFormat.JPEG, 100, stream );
            String bytes_imagem = Base64.encodeToString( stream.toByteArray(), Base64.DEFAULT );

            parametros_anuncio.put("imagemPrincipal", bytes_imagem);
            stream.reset();
        }

        if( imagem_a != null ) {
            imagem_a.compress(Bitmap.CompressFormat.JPEG, 100, stream );
            String bytes_imagem = Base64.encodeToString( stream.toByteArray(), Base64.DEFAULT );

            parametros_anuncio.put("imagemA", bytes_imagem);
            stream.reset();
        }

        if( imagem_b != null ) {
            imagem_b.compress(Bitmap.CompressFormat.JPEG, 100, stream );
            String bytes_imagem = Base64.encodeToString( stream.toByteArray(), Base64.DEFAULT );

            parametros_anuncio.put("imagemB", bytes_imagem);
            stream.reset();
        }

        if( imagem_c != null ) {
            imagem_c.compress(Bitmap.CompressFormat.JPEG, 100, stream );
            String bytes_imagem = Base64.encodeToString( stream.toByteArray(), Base64.DEFAULT );

            parametros_anuncio.put("imagemC", bytes_imagem);
            stream.reset();
        }

        if( imagem_d != null ) {
            imagem_d.compress(Bitmap.CompressFormat.JPEG, 100, stream );
            String bytes_imagem = Base64.encodeToString( stream.toByteArray(), Base64.DEFAULT );

            parametros_anuncio.put("imagemD", bytes_imagem);
            stream.reset();
        }

        if( txtTitulo.getText() != null && !txtTitulo.getText().toString().trim().isEmpty() ) {
            parametros_anuncio.put("titulo", txtTitulo.getText().toString().trim());
        }

        if( txtDescricao.getText() != null && !txtDescricao.getText().toString().trim().isEmpty() ) {
            parametros_anuncio.put("descricao", txtDescricao.getText().toString().trim());
        }

        if( txtValorDiaria.getText() != null && !txtValorDiaria.getText().toString().trim().isEmpty() ) {
            parametros_anuncio.put("valorDiaria", txtValorDiaria.getText().toString().trim());
        }

        if( txtValorCombustivel.getText() != null && !txtValorCombustivel.getText().toString().trim().isEmpty() ) {
            parametros_anuncio.put("valorCombustivel", txtValorCombustivel.getText().toString().trim());
        }

        if( txtValorQuilometragemExcedida.getText() != null && !txtValorQuilometragemExcedida.getText().toString().trim().isEmpty() ) {
            parametros_anuncio.put("valorQuilometragem", txtValorQuilometragemExcedida.getText().toString().trim());
        }

        if( txtValorVeiculo.getText() != null && !txtValorVeiculo.getText().toString().trim().isEmpty() ) {
            parametros_anuncio.put("valorVeiculo", txtValorVeiculo.getText().toString().trim());
        }

        if( txtQuilometragem.getText() != null && !txtQuilometragem.getText().toString().trim().isEmpty() ) {
            parametros_anuncio.put("quilometragemAtual", txtQuilometragem.getText().toString().trim());
        }

        if( txtLimiteQuilometragem.getText() != null && !txtLimiteQuilometragem.getText().toString().trim().isEmpty() ) {
            parametros_anuncio.put("limiteQuilometragem", txtLimiteQuilometragem.getText().toString().trim());
        }

        if( spnModeloVeiculo.getSelectedItem() != null ) {
            Veiculo veiculo_selecionado = (Veiculo) spnModeloVeiculo.getSelectedItem();

            parametros_anuncio.put("idVeiculo", String.valueOf(veiculo_selecionado.getId()));
        }

        if( !txtTitulo.getText().toString().trim().isEmpty() &&
                !txtDescricao.getText().toString().trim().isEmpty() &&
                !txtValorDiaria.getText().toString().trim().isEmpty() &&
                !txtValorCombustivel.getText().toString().trim().isEmpty() &&
                !txtValorQuilometragemExcedida.getText().toString().trim().isEmpty() &&
                !txtValorVeiculo.getText().toString().trim().isEmpty() &&
                !txtQuilometragem.getText().toString().trim().isEmpty() &&
                !txtLimiteQuilometragem.getText().toString().trim().isEmpty() )
        {
            parametros_anuncio.put("idUsuario", String.valueOf(Login.getId_usuario(context)));
            parametros_anuncio.put("modo", modo);
            new SalvarAnuncio().execute();
        }
    }

    private class AcaoSelecionarFoto implements View.OnClickListener {
        private int request_code;

        private AcaoSelecionarFoto(int rq) {
            this.request_code = rq;
        }

        @Override
        public void onClick(View v) {
            Intent get_imagem = new Intent(Intent.ACTION_GET_CONTENT);
            get_imagem.setType("image/*");

            if( get_imagem.resolveActivity(getPackageManager()) != null ) {
                startActivityForResult( get_imagem, request_code );
            }
        }
    }

    private class AcaoSelecionarQuantidadePortas implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            progress = progress * 2;

            if( progress > 4 ) {
                progress = 4;
            }

            label_qtd_portas.setText( String.format(Locale.getDefault(), "%d", progress) );

            parametros_pesquisa.put("qtdPortas", String.valueOf(progress));
            new BuscarVeiculo().execute();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }

    private class AcaoSelecionarTipoVeiculo implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            TipoVeiculo tipo_selecionado = (TipoVeiculo) spnTipoVeiculo.getSelectedItem();

            if( tipo_selecionado != null ) {
                parametros_pesquisa.put("idTipo", String.valueOf(tipo_selecionado.getId()));

                new BuscarVeiculo().execute();
                new BuscarFabricantes(tipo_selecionado.getId()).execute();
                new BuscarTipoCombustivel( tipo_selecionado.getId() ).execute();
                new BuscarTransmissao( tipo_selecionado.getId() ).execute();
                skQtdPortas.setProgress( skQtdPortas.getProgress() );
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private class BuscarTiposVeiculo extends AsyncTask<Void, Void, Void> {
        ProgressDialog progress;
        TipoVeiculo[] tipos;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = ProgressDialog.show(context, "Carregando", "Aguarde...");
        }

        @Override
        protected Void doInBackground(Void... params) {
            String url = getString(R.string.serverAddr) + "apis/android/listar_tipo_veiculo.php";
            HashMap<String, String> parametros = new HashMap<>();

            String json = post(url, parametros);

            tipos = new Gson().fromJson(json, TipoVeiculo[].class);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progress.dismiss();

            ArrayAdapter<TipoVeiculo> adapterTipoVeiculo = new ArrayAdapter<>(AnunciarActivity.this, android.R.layout.simple_spinner_item, tipos);
            adapterTipoVeiculo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spnTipoVeiculo.setAdapter(adapterTipoVeiculo);
            spnTipoVeiculo.setOnItemSelectedListener(new AcaoSelecionarTipoVeiculo());
        }
    }

    private class BuscarFabricantes extends AsyncTask<Void, Void, Void> {
        int idTipoVeiculo;
        Fabricante[] fabricantes;

        private BuscarFabricantes(int idTipo) {
            this.idTipoVeiculo = idTipo;
        }

        @Override
        protected Void doInBackground(Void... params) {
            String url = getString(R.string.serverAddr) + "apis/android/get_fabricantes.php";

            HashMap<String, String> parametros = new HashMap<>();
            parametros.put("idTipoVeiculo", String.valueOf(idTipoVeiculo));

            String json = HttpRequest.post(url, parametros);
            fabricantes = new Gson().fromJson(json, Fabricante[].class);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (fabricantes != null && fabricantes.length > 0) {
                ArrayAdapter<Fabricante> adapter = new ArrayAdapter<Fabricante>(context, R.layout.support_simple_spinner_dropdown_item, fabricantes);

                spnFabricante.setAdapter(adapter);

                spnFabricante.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Fabricante fabricanteSelecionado = (Fabricante) spnFabricante.getSelectedItem();
                        parametros_pesquisa.put("idFabricante", String.valueOf(fabricanteSelecionado.getId()));
                        new BuscarVeiculo().execute();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                spnFabricante.setEnabled(true);
                spnFabricante.setSelection(0);
            } else {
                spnFabricante.setAdapter( new ArrayAdapter<String>(context, R.layout.support_simple_spinner_dropdown_item, Arrays.asList("Não há fabricantes para este tipo")) );
                spnFabricante.setEnabled(false);
            }
        }
    }

    private class BuscarTipoCombustivel extends AsyncTask<Void, Void, Void> {
        int idTipoVeiculo;
        Combustivel[] listaCombustivel;

        private BuscarTipoCombustivel(int idTipoVeiculo) {
            this.idTipoVeiculo = idTipoVeiculo;
        }

        @Override
        protected Void doInBackground(Void... params) {
            String url = getString(R.string.serverAddr) + "apis/android/get_combustiveis.php";

            HashMap<String, String> parametros = new HashMap<>();
            parametros.put("idTipoVeiculo", String.valueOf(this.idTipoVeiculo));

            String json = HttpRequest.post(url, parametros);

            listaCombustivel = new Gson().fromJson(json, Combustivel[].class);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if( listaCombustivel != null && listaCombustivel.length > 0 ) {
                ArrayAdapter<Combustivel> adapter = new ArrayAdapter<>(context,
                        R.layout.support_simple_spinner_dropdown_item,
                        listaCombustivel
                );

                spnTipoCombustivel.setAdapter( adapter );
                spnTipoCombustivel.setEnabled( true );

                spnTipoCombustivel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Combustivel combustivelSelecionado = (Combustivel) spnTipoCombustivel.getSelectedItem();

                        parametros_pesquisa.put("idCombustivel", String.valueOf(combustivelSelecionado.getId()));
                        new BuscarVeiculo().execute();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                spnTipoCombustivel.setSelection(0);

            } else {
                spnTipoCombustivel.setOnItemSelectedListener( null );

                spnTipoCombustivel.setAdapter( null );
                spnTipoCombustivel.setAdapter( new ArrayAdapter<>(context,
                        R.layout.support_simple_spinner_dropdown_item,
                        Arrays.asList( "Não há combustíveis para este tipo" ))
                );

                spnTipoCombustivel.setEnabled( false );
                parametros_pesquisa.remove("idCombustivel");
            }
        }
    }

    private class BuscarTransmissao extends AsyncTask<Void, Void, Void> {
        private int idTipoVeiculo;
        private Transmissao[] transmissoes;

        private BuscarTransmissao( int idTipoVeiculo ) {
            this.idTipoVeiculo = idTipoVeiculo;
        }

        @Override
        protected Void doInBackground(Void... params) {
            String url = getString(R.string.serverAddr) + "apis/android/listar_transmissao.php";

            HashMap<String, String> parametros = new HashMap<>();
            parametros.put("idTipoVeiculo", String.valueOf(this.idTipoVeiculo));

            String json = HttpRequest.post(url, parametros);
            Log.d("JSONTRANSMISSOES", json);

            transmissoes = new Gson().fromJson(json, Transmissao[].class);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if( transmissoes != null && transmissoes.length > 0 ) {
                ArrayAdapter<Transmissao> adapter = new ArrayAdapter<>(context,
                        R.layout.support_simple_spinner_dropdown_item,
                        transmissoes
                );

                spnTransmissao.setAdapter( adapter );

                spnTransmissao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Transmissao transmissaoSelecionada = (Transmissao) spnTransmissao.getSelectedItem();

                        parametros_pesquisa.put("idTransmissao", String.valueOf(transmissaoSelecionada.getId()));
                        new BuscarVeiculo().execute();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                spnTransmissao.setEnabled(true);
                spnTransmissao.setSelection(0);
            } else {
                spnTransmissao.setOnItemSelectedListener(null);

                spnTransmissao.setAdapter( new ArrayAdapter<>(context,
                        R.layout.support_simple_spinner_dropdown_item,
                        Arrays.asList( "Não há transmissões para este tipo" ))
                );

                spnTransmissao.setEnabled(false);
                parametros_pesquisa.remove("idTransmissao");
            }
        }
    }

    private class BuscarVeiculo extends AsyncTask<Void, Void, Void> {
        private Veiculo[] veiculos;

        @Override
        protected Void doInBackground(Void... params) {
            String url = getString(R.string.serverAddr) + "apis/android/get_veiculos.php";

            String json = HttpRequest.post(url, parametros_pesquisa);

            veiculos = new Gson().fromJson(json, Veiculo[].class);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            for( Map.Entry<String, String> entry : parametros_pesquisa.entrySet() ) {
                Log.d("PESQUISA", entry.getKey() + " = " + entry.getValue());
            }

            if( veiculos != null && veiculos.length > 0 ) {
                ArrayAdapter<Veiculo> adapter = new ArrayAdapter<>(context, R.layout.support_simple_spinner_dropdown_item, this.veiculos);

                spnModeloVeiculo.setAdapter( adapter );
                spnModeloVeiculo.setEnabled( true );
            } else {
                spnModeloVeiculo.setAdapter( new ArrayAdapter<String>(context, R.layout.support_simple_spinner_dropdown_item, Arrays.asList("Nenhum veículo encontrado com essas características")) );
                spnModeloVeiculo.setEnabled(false);
            }
        }
    }

    private class SalvarAnuncio extends AsyncTask<Void, Void, Void> {
        ProgressDialog progress;
        Boolean resultado;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = ProgressDialog.show(context, "Salvando", "Aguarde");
        }

        @Override
        protected Void doInBackground(Void... params) {
            String url = getString(R.string.serverAddr) + "apis/android/anunciar.php";

            String json = HttpRequest.post(url, parametros_anuncio);
            Log.d("JSONANUNCIO", json);

            //resultado = new Gson().fromJson(json, Boolean.class);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progress.dismiss();

            parametros_anuncio = new HashMap<>();
        }
    }

}
