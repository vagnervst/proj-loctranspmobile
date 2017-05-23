package com.cityshare.app;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.ViewFlipper;

import com.cityshare.app.model.Cidade;
import com.cityshare.app.model.Estado;
import com.cityshare.app.model.HttpRequest;
import com.cityshare.app.model.Login;
import com.cityshare.app.model.TipoCartaoCredito;
import com.cityshare.app.model.Usuario;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class ConfiguracoesContaActivity extends AppCompatActivity {

    ViewFlipper flipper;
    ImageView iv_foto_perfil;
    EditText edt_nome, edt_sobrenome, edt_data_nascimento, edt_rg, edt_cpf, edt_telefone, edt_email, edt_celular;
    RadioButton rdo_feminino, rdo_masculino;
    Spinner sp_estado, sp_cidade, sp_banco, sp_tipo_cartao;

    Context context;
    Usuario usuario;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_pessoais:
                    flipper.setDisplayedChild(0);
                    return true;
                case R.id.navigation_contato:
                    flipper.setDisplayedChild(1);
                    return true;
                case R.id.navigation_financeiro:
                    flipper.setDisplayedChild(2);
                    return true;
                case R.id.navigation_conducao:
                    flipper.setDisplayedChild(3);
                    return true;
                case R.id.navigation_autenticacao:
                    flipper.setDisplayedChild(4);
                    return true;
            }

            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes_conta);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        flipper = (ViewFlipper) findViewById(R.id.vf_flipper);
        iv_foto_perfil = (ImageView) findViewById(R.id.iv_foto_perfil);
        edt_nome = (EditText) findViewById(R.id.edt_nome);
        edt_sobrenome = (EditText) findViewById(R.id.edt_sobrenome);
        edt_data_nascimento = (EditText) findViewById(R.id.edt_data_nascimento);
        rdo_feminino = (RadioButton) findViewById(R.id.rdo_feminino);
        rdo_masculino = (RadioButton) findViewById(R.id.rdo_masculino);
        edt_rg = (EditText) findViewById(R.id.edt_rg);
        edt_cpf = (EditText) findViewById(R.id.edt_cpf);
        sp_estado = (Spinner) findViewById(R.id.sp_estado);
        sp_cidade = (Spinner) findViewById(R.id.sp_cidade);
        edt_telefone = (EditText) findViewById(R.id.edt_telefone);
        edt_celular = (EditText) findViewById(R.id.edt_celular);
        edt_email = (EditText) findViewById(R.id.edt_email);
        sp_tipo_cartao = (Spinner) findViewById(R.id.sp_tipo_cartao);
        sp_banco = (Spinner) findViewById(R.id.sp_banco);

        context = this;

        new GetInfoUsuario().execute();
    }

    private class GetCartaoCredito extends AsyncTask<Void, Void, Void> {
        ProgressDialog progress;
        TipoCartaoCredito[] listaTipoCartao;
        Integer idTipoCartao;

        private GetCartaoCredito(int idTipoCartao) {
            this.idTipoCartao = idTipoCartao;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = ProgressDialog.show(context, "Carregando", "Aguarde");
        }

        @Override
        protected Void doInBackground(Void... params) {
            String url = getString(R.string.serverAddr) + "apis/android/get_tipos_cartao.php";

            String json = HttpRequest.get(url);
            Log.d("CARTAO", json);

            listaTipoCartao = new Gson().fromJson(json, TipoCartaoCredito[].class);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progress.dismiss();

            if( listaTipoCartao != null ) {
                sp_tipo_cartao.setAdapter( new ArrayAdapter<>(context, R.layout.support_simple_spinner_dropdown_item, listaTipoCartao) );

                if( this.idTipoCartao != null ) {
                    for( int i = 0; i < sp_tipo_cartao.getAdapter().getCount(); ++i ) {
                        TipoCartaoCredito cartao = (TipoCartaoCredito) sp_tipo_cartao.getItemAtPosition(i);

                        if( cartao.getId() == this.idTipoCartao ) {
                            sp_tipo_cartao.setSelection(i);
                        }
                    }
                }
            }
        }
    }

    private class GetCidades extends AsyncTask<Void, Void, Void> {
        ProgressDialog progress;
        Cidade[] listaCidades;
        Integer idEstado;

        private GetCidades(int idEstado) {
            this.idEstado = idEstado;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = ProgressDialog.show(context, "Carregando", "Aguarde");
        }

        @Override
        protected Void doInBackground(Void... params) {
            String url = getString(R.string.serverAddr) + "apis/android/get_cidades.php";

            HashMap<String, String> parametros = new HashMap<>();
            parametros.put("idEstado", String.valueOf( this.idEstado ));

            String json = HttpRequest.post(url, parametros);
            listaCidades = new Gson().fromJson(json, Cidade[].class);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progress.dismiss();

            sp_cidade.setAdapter( new ArrayAdapter<>(context, R.layout.support_simple_spinner_dropdown_item, listaCidades) );

            if( this.idEstado != null ) {
                for (int i = 0; i < sp_cidade.getAdapter().getCount(); ++i) {
                    Cidade cidade = (Cidade) sp_cidade.getItemAtPosition(i);

                    if (cidade.getIdEstado() == this.idEstado) {
                        sp_cidade.setSelection(i);
                    }
                }
            }
        }
    }

    private class GetEstados extends AsyncTask<Void, Void, Void> {
        ProgressDialog progress;
        Estado[] listaEstados;
        Integer idEstado;

        private GetEstados(int idEstado) {
            this.idEstado = idEstado;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = ProgressDialog.show(context, "Carregando", "Aguarde");
        }

        @Override
        protected Void doInBackground(Void... params) {
            String url = getString(R.string.serverAddr) + "apis/android/get_estados.php";

            String json = HttpRequest.get(url);

            listaEstados = new Gson().fromJson(json, Estado[].class);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progress.dismiss();

            sp_estado.setAdapter( new ArrayAdapter<>(context, R.layout.support_simple_spinner_dropdown_item, listaEstados) );

            if( this.idEstado != null ) {
                for (int i = 0; i < sp_estado.getAdapter().getCount(); ++i) {
                    Estado estado = (Estado) sp_estado.getItemAtPosition(i);

                    if (estado.getId() == this.idEstado) {
                        sp_estado.setSelection(i);
                    }
                }
            }
        }
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
            String url = getString(R.string.serverAddr) + "apis/android/get_info_usuario.php";

            HashMap<String, String> parametros = new HashMap<>();
            parametros.put("idUsuario", String.valueOf(Login.getId_usuario(context)));

            String json = HttpRequest.post(url, parametros);

            usuario = new Gson().fromJson(json, Usuario.class);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progress.dismiss();

            if( usuario != null ) {
                Picasso.with(context).load( getString(R.string.serverAddr) + "img/uploads/usuarios/" + usuario.getFotoPerfil() ).into(iv_foto_perfil);
                edt_nome.setText( usuario.getNome() );
                edt_sobrenome.setText( usuario.getSobrenome() );

                try {
                    Date data_nascimento = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse( usuario.getDataNascimento() );

                    edt_data_nascimento.setText( SimpleDateFormat.getDateInstance().format(data_nascimento) );
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if( usuario.getSexo() == 'f' ) {
                    rdo_feminino.setChecked(true);
                } else if( usuario.getSexo() == 'm' ) {
                    rdo_masculino.setChecked(true);
                }

                edt_rg.setText( usuario.getRg() );
                edt_cpf.setText( usuario.getCpf() );

                new GetEstados( usuario.getIdEstado() ).execute();
                new GetCidades( usuario.getIdEstado() ).execute();

                edt_telefone.setText( usuario.getTelefone() );
                edt_celular.setText( usuario.getCelular() );
                edt_email.setText( usuario.getEmailContato() );

                new GetCartaoCredito(usuario.getCartaoCredito().getIdTipo()).execute();
            }

        }
    }
}
