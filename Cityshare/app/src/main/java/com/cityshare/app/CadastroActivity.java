package com.cityshare.app;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cityshare.app.model.Banco;
import com.cityshare.app.model.Cidade;
import com.cityshare.app.model.Estado;
import com.cityshare.app.model.HttpRequest;
import com.cityshare.app.model.Server;
import com.cityshare.app.model.TipoCartaoCredito;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class CadastroActivity extends AppCompatActivity {

    ViewFlipper viewFlipper;
    ImageButton ib_foto;
    EditText txt_nome, txt_sobrenome, txt_data_nascimento, txt_rg, txt_cpf, txt_telefone, txt_celular, txt_email, txt_numero_cartao, txt_validade_cartao,
            txt_numero_agencia, txt_numero_conta, txt_digito_verificador, txt_numero_cnh, txt_email_autenticacao, txt_senha, txt_confirmar_senha;
    RadioGroup rg_sexo;
    Spinner sp_estado, sp_cidade, sp_tipo_cartao, sp_banco;
    Button btn_vou_alugar, btn_vou_emprestar;
    Menu menu_cadastro;

    private static final int GET_IMAGE = 1;
    private static final int INFO_PESSOAIS = 1, INFO_CONTATO = 2, INFO_CARTAO_CREDITO = 3, INFO_CONTA_BANCARIA = 4, INFO_CNH = 5, INFO_AUTENTICACAO = 6;
    Context context;

    int modoAtual = INFO_PESSOAIS;
    Bitmap nova_foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;

        viewFlipper = (ViewFlipper) findViewById(R.id.vf_flipper);
        ib_foto = (ImageButton) findViewById(R.id.ib_foto);
        txt_nome = (EditText) findViewById(R.id.txt_nome);
        txt_sobrenome = (EditText) findViewById(R.id.txt_sobrenome);
        txt_data_nascimento = (EditText) findViewById(R.id.txt_data_nascimento);
        txt_rg = (EditText) findViewById(R.id.txt_rg);
        txt_cpf = (EditText) findViewById(R.id.txt_cpf);
        rg_sexo = (RadioGroup) findViewById(R.id.rg_sexo);
        sp_estado = (Spinner) findViewById(R.id.sp_estado);
        sp_cidade = (Spinner) findViewById(R.id.sp_cidade);
        txt_telefone = (EditText) findViewById(R.id.txt_telefone);
        txt_celular = (EditText) findViewById(R.id.txt_celular);
        txt_email = (EditText) findViewById(R.id.txt_email);
        btn_vou_alugar = (Button) findViewById(R.id.btn_vou_alugar);
        btn_vou_emprestar = (Button) findViewById(R.id.btn_vou_emprestar);
        sp_tipo_cartao = (Spinner) findViewById(R.id.sp_tipo_cartao);
        txt_numero_cartao = (EditText) findViewById(R.id.txt_numero_cartao);
        txt_validade_cartao = (EditText) findViewById(R.id.txt_validade_cartao);
        sp_banco = (Spinner) findViewById(R.id.sp_banco);
        txt_numero_agencia = (EditText) findViewById(R.id.txt_numero_agencia);
        txt_numero_conta = (EditText) findViewById(R.id.txt_numero_conta);
        txt_digito_verificador = (EditText) findViewById(R.id.txt_digito_verificador);
        txt_numero_cnh = (EditText) findViewById(R.id.txt_numero_cnh);
        txt_email_autenticacao = (EditText) findViewById(R.id.txt_email_autenticacao);
        txt_senha = (EditText) findViewById(R.id.txt_senha);
        txt_confirmar_senha = (EditText) findViewById(R.id.txt_confirmar_senha);

        txt_nome.addTextChangedListener( new KeyListener() );
        txt_sobrenome.addTextChangedListener( new KeyListener() );
        txt_data_nascimento.addTextChangedListener( new KeyListener() );
        txt_rg.addTextChangedListener( new KeyListener() );
        txt_cpf.addTextChangedListener( new KeyListener() );
        txt_telefone.addTextChangedListener( new KeyListener() );
        txt_celular.addTextChangedListener( new KeyListener() );
        txt_email.addTextChangedListener( new KeyListener() );
        txt_numero_cartao.addTextChangedListener( new KeyListener() );
        txt_validade_cartao.addTextChangedListener( new KeyListener() );
        txt_numero_agencia.addTextChangedListener( new KeyListener() );
        txt_numero_conta.addTextChangedListener( new KeyListener() );
        txt_digito_verificador.addTextChangedListener( new KeyListener() );
        txt_numero_cnh.addTextChangedListener( new KeyListener() );
        txt_email_autenticacao.addTextChangedListener( new KeyListener() );
        txt_senha.addTextChangedListener( new KeyListener() );
        txt_confirmar_senha.addTextChangedListener( new KeyListener() );

        txt_data_nascimento.setOnClickListener( new ActionData(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

                Date data_selecionada = null;
                try {
                    data_selecionada = formatter.parse( String.format(Locale.getDefault(), "%d-%d-%d", year, month+1, dayOfMonth) );

                    txt_data_nascimento.setText( SimpleDateFormat.getDateInstance().format(data_selecionada) );

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }));

        txt_validade_cartao.setOnClickListener( new ActionData(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

                Date data_selecionada = null;
                try {
                    data_selecionada = formatter.parse( String.format(Locale.getDefault(), "%d-%d-%d", year, month+1, dayOfMonth) );

                    txt_validade_cartao.setText( SimpleDateFormat.getDateInstance().format(data_selecionada) );

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }));

        ib_foto.setOnClickListener( new ActionFotoPerfil() );

        sp_estado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Estado estado_selecionado = (Estado) sp_estado.getSelectedItem();

                if( estado_selecionado != null ) {
                    new GetCidades(estado_selecionado.getId()).execute();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        new GetEstados().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate( R.menu.menu_confirmar, menu );

        menu_cadastro = menu;
        menu_cadastro.getItem(0).setEnabled(false);

        return super.onCreateOptionsMenu(menu);
    }

    private void toggleBotaoContinuar() {
        menu_cadastro.getItem(0).setEnabled(false);
        menu_cadastro.getItem(0).setVisible(false);
        btn_vou_alugar.setEnabled(false);
        btn_vou_emprestar.setEnabled(false);

        if( modoAtual == INFO_PESSOAIS ) {

            if( nova_foto != null &&
                    !txt_nome.getText().toString().trim().isEmpty() &&
                    !txt_sobrenome.getText().toString().trim().isEmpty() &&
                    !txt_data_nascimento.getText().toString().trim().isEmpty() &&
                    !txt_rg.getText().toString().trim().isEmpty() &&
                    !txt_cpf.getText().toString().trim().isEmpty() )
            {
                menu_cadastro.getItem(0).setVisible(true);
                menu_cadastro.getItem(0).setEnabled(true);

                menu_cadastro.getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        viewFlipper.setDisplayedChild(1);
                        modoAtual = INFO_CONTATO;
                        menu_cadastro.getItem(0).setVisible(false);
                        toggleBotaoContinuar();
                        return true;
                    }
                });
            }
        } else if( modoAtual == INFO_CONTATO ) {

            if( !txt_telefone.getText().toString().trim().isEmpty() &&
                    !txt_celular.getText().toString().trim().isEmpty() &&
                    !txt_email.getText().toString().trim().isEmpty() )
            {
                btn_vou_alugar.setEnabled(true);
                btn_vou_emprestar.setEnabled(true);

                btn_vou_alugar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new GetTiposCartaoCredito().execute();
                        viewFlipper.setDisplayedChild(2);
                        modoAtual = INFO_CARTAO_CREDITO;
                        toggleBotaoContinuar();
                    }
                });

                btn_vou_emprestar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new GetBancos().execute();
                        viewFlipper.setDisplayedChild(3);
                        modoAtual = INFO_CONTA_BANCARIA;
                        toggleBotaoContinuar();
                    }
                });
            }
        } else if( modoAtual == INFO_CARTAO_CREDITO ) {

            if( !txt_numero_cartao.getText().toString().trim().isEmpty() &&
                    !txt_validade_cartao.getText().toString().trim().isEmpty() )
            {
                menu_cadastro.getItem(0).setVisible(true);
                menu_cadastro.getItem(0).setEnabled(true);

                menu_cadastro.getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        viewFlipper.setDisplayedChild(4);
                        modoAtual = INFO_CNH;
                        toggleBotaoContinuar();
                        return true;
                    }
                });
            }

        } else if( modoAtual == INFO_CONTA_BANCARIA ) {

            if( !txt_numero_conta.getText().toString().trim().isEmpty() &&
                    !txt_numero_agencia.getText().toString().trim().isEmpty() &&
                    !txt_digito_verificador.getText().toString().trim().isEmpty() )
            {
                menu_cadastro.getItem(0).setVisible(true);
                menu_cadastro.getItem(0).setEnabled(true);

                menu_cadastro.getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        viewFlipper.setDisplayedChild(5);
                        modoAtual = INFO_AUTENTICACAO;
                        toggleBotaoContinuar();
                        return true;
                    }
                });
            }

        } else if( modoAtual == INFO_CNH ) {

            if( !txt_numero_cnh.getText().toString().trim().isEmpty() ) {
                menu_cadastro.getItem(0).setVisible(true);
                menu_cadastro.getItem(0).setEnabled(true);

                menu_cadastro.getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        viewFlipper.setDisplayedChild(5);
                        modoAtual = INFO_AUTENTICACAO;
                        toggleBotaoContinuar();
                        return true;
                    }
                });
            }

        } else if( modoAtual == INFO_AUTENTICACAO ) {

            if( !txt_email_autenticacao.getText().toString().trim().isEmpty() &&
                    !txt_senha.getText().toString().trim().isEmpty() &&
                    !txt_confirmar_senha.getText().toString().trim().isEmpty() )
            {
                menu_cadastro.getItem(0).setVisible(true);
                menu_cadastro.getItem(0).setEnabled(true);

                menu_cadastro.getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        return true;
                    }
                });
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if( requestCode == GET_IMAGE && resultCode == RESULT_OK ) {
            Uri foto_selecionada = data.getData();
            new CarregarFoto(foto_selecionada).execute();
        }

    }

    private class ActionFotoPerfil implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent selecionarFoto = new Intent(Intent.ACTION_GET_CONTENT);
            selecionarFoto.setType("image/*");

            if( selecionarFoto.resolveActivity(getPackageManager()) != null ) {
                startActivityForResult(selecionarFoto, GET_IMAGE);
            }
        }
    }

    private class KeyListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            toggleBotaoContinuar();
        }
    }

    private class ActionData implements View.OnClickListener {
        DatePickerDialog.OnDateSetListener listener;

        private ActionData(DatePickerDialog.OnDateSetListener when_chosen) {
            this.listener = when_chosen;
        }

        @Override
        public void onClick(View v) {
            Calendar calendar = Calendar.getInstance();

            DatePickerDialog dpDialog = new DatePickerDialog(context, this.listener, calendar.get( Calendar.YEAR ), calendar.get( Calendar.MONTH ), calendar.get( Calendar.DAY_OF_MONTH ));
            dpDialog.show();
        }
    }

    private class GetEstados extends AsyncTask<Void, Void, Void> {
        Estado[] listaEstados;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String url = Server.servidor + "apis/android/get_estados.php";

            String json = HttpRequest.get(url);

            listaEstados = new Gson().fromJson(json, Estado[].class);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            sp_estado.setAdapter( new ArrayAdapter<>(context, R.layout.support_simple_spinner_dropdown_item, listaEstados) );
        }
    }

    private class GetCidades extends AsyncTask<Void, Void, Void> {
        Cidade[] listaCidades;
        Integer idEstado;

        private GetCidades(Integer idEstado) {
            this.idEstado = idEstado;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String url = Server.servidor + "apis/android/get_cidades.php";

            HashMap<String, String> parametros = new HashMap<>();
            parametros.put("idEstado", String.valueOf( this.idEstado ));

            String json = HttpRequest.post(url, parametros);
            listaCidades = new Gson().fromJson(json, Cidade[].class);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

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

    private class GetTiposCartaoCredito extends AsyncTask<Void, Void, Void> {
        TipoCartaoCredito[] listaTipoCartao;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String url = Server.servidor + "apis/android/get_tipos_cartao.php";

            String json = HttpRequest.get(url);

            listaTipoCartao = new Gson().fromJson(json, TipoCartaoCredito[].class);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if( listaTipoCartao != null ) {
                sp_tipo_cartao.setAdapter( new ArrayAdapter<>(context, R.layout.support_simple_spinner_dropdown_item, listaTipoCartao) );
            }
        }
    }

    private class GetBancos extends AsyncTask<Void, Void, Void> {
        Banco[] listaBancos;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String url = Server.servidor + "apis/android/get_bancos.php";

            String json = HttpRequest.get(url);
            listaBancos = new Gson().fromJson(json, Banco[].class);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            if( listaBancos != null ) {
                sp_banco.setAdapter( new ArrayAdapter<>(context, R.layout.support_simple_spinner_dropdown_item, listaBancos) );
            }

        }
    }

    private class CarregarFoto extends AsyncTask<Void, Void, Void> {
        private Uri uri;

        private CarregarFoto(Uri uri) {
            this.uri = uri;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                byte[] bytes = Glide.with(context).load(this.uri).asBitmap().toBytes(Bitmap.CompressFormat.JPEG, 100).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(200, 200).get();

                nova_foto = BitmapFactory.decodeByteArray( bytes, 0, bytes.length );
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ib_foto.setImageBitmap( nova_foto );
            toggleBotaoContinuar();
        }
    }
}