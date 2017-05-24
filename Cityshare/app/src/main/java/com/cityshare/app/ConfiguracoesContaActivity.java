package com.cityshare.app;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.ViewFlipper;

import com.cityshare.app.model.Banco;
import com.cityshare.app.model.Cidade;
import com.cityshare.app.model.Cnh;
import com.cityshare.app.model.Estado;
import com.cityshare.app.model.HttpRequest;
import com.cityshare.app.model.Login;
import com.cityshare.app.model.TipoCartaoCredito;
import com.cityshare.app.model.Usuario;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class ConfiguracoesContaActivity extends AppCompatActivity {

    ViewFlipper flipper;
    ImageButton iv_foto_perfil, ib_adicionar_cnh;
    EditText edt_nome, edt_sobrenome, edt_data_nascimento, edt_rg, edt_cpf, edt_telefone, edt_email, edt_celular, edt_numero_cartao, edt_validade_cartao,
            edt_numero_agencia, edt_numero_conta_bancaria, edt_digito_verificador_conta_bancaria, edt_numero_nova_cnh, edt_validade_nova_cnh, edt_email_autenticacao, edt_senha, edt_confirmar_senha, edt_nova_senha;
    RadioButton rdo_feminino, rdo_masculino;
    Spinner sp_estado, sp_cidade, sp_banco, sp_tipo_cartao;
    ListView lv_cnh;
    MenuItem botao_edicao;

    Context context;
    Usuario usuario;
    CnhAdapter adapter_cnh;
    boolean modo_edicao = false;
    Bitmap nova_foto = null;

    static final int GET_IMAGE = 1;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            modo_edicao = false;
            toggle_modo_edicao(modo_edicao, false);

            if( botao_edicao != null ) {
                botao_edicao.setIcon(R.mipmap.ic_edit_white);
            }
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
        iv_foto_perfil = (ImageButton) findViewById(R.id.iv_foto_perfil);
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
        edt_numero_cartao = (EditText) findViewById(R.id.edt_numero_cartao);
        edt_validade_cartao = (EditText) findViewById(R.id.edt_validade_cartao);
        sp_banco = (Spinner) findViewById(R.id.sp_banco);
        edt_numero_agencia = (EditText) findViewById(R.id.edt_numero_agencia);
        edt_numero_conta_bancaria = (EditText) findViewById(R.id.edt_numero_conta_bancaria);
        edt_digito_verificador_conta_bancaria = (EditText) findViewById(R.id.edt_digito_verificador_conta_bancaria);
        lv_cnh = (ListView) findViewById(R.id.lv_cnh);
        ib_adicionar_cnh = (ImageButton) findViewById(R.id.ib_adicionar_cnh);
        edt_numero_nova_cnh = (EditText) findViewById(R.id.edt_numero_nova_cnh);
        edt_validade_nova_cnh = (EditText) findViewById(R.id.edt_validade_nova_cnh);
        edt_email_autenticacao = (EditText) findViewById(R.id.edt_email_autenticacao);
        edt_senha = (EditText) findViewById(R.id.edt_senha);
        edt_confirmar_senha = (EditText) findViewById(R.id.edt_confirmar_senha);
        edt_nova_senha = (EditText) findViewById(R.id.edt_nova_senha);

        edt_data_nascimento.setOnClickListener( new ActionData(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

                Date data_selecionada = null;
                try {
                    data_selecionada = formatter.parse( String.format(Locale.getDefault(), "%d-%d-%d", year, month+1, dayOfMonth) );

                    edt_data_nascimento.setText( SimpleDateFormat.getDateInstance().format(data_selecionada) );

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }) );

        edt_validade_nova_cnh.setOnClickListener( new ActionData(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

                Date data_selecionada = null;
                try {
                    data_selecionada = formatter.parse( String.format(Locale.getDefault(), "%d-%d-%d", year, month+1, dayOfMonth) );

                    edt_validade_nova_cnh.setText( SimpleDateFormat.getDateInstance().format(data_selecionada) );

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }) );

        context = this;

        iv_foto_perfil.setOnClickListener( new ActionFotoPerfil() );

        ib_adicionar_cnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if( ( edt_numero_nova_cnh.getText() != null && !edt_numero_nova_cnh.getText().toString().trim().isEmpty() ) &&
                        edt_validade_nova_cnh.getText() != null && !edt_validade_nova_cnh.getText().toString().trim().isEmpty() ) {

                    try {
                        String numero_cnh = edt_numero_nova_cnh.getText().toString().trim();
                        Date data_validade = SimpleDateFormat.getDateInstance().parse( edt_validade_nova_cnh.getText().toString().trim() );

                        Cnh objCnh = new Cnh();
                        objCnh.setNumeroRegistro(Integer.parseInt(numero_cnh));
                        objCnh.setValidade(String.valueOf(data_validade.getTime()));

                        new CrudCnh(CrudCnh.INSERT, objCnh).execute();

                        edt_numero_nova_cnh.setText("");
                        edt_validade_nova_cnh.setText("");

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        toggle_modo_edicao(false, false);
        new GetInfoUsuario().execute();
    }

    public void desativar_entradas() {
        iv_foto_perfil.setEnabled( false );
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);
        iv_foto_perfil.setColorFilter(new ColorMatrixColorFilter(matrix));

        edt_nome.setEnabled( false );
        edt_sobrenome.setEnabled( false );
        edt_data_nascimento.setEnabled( false );
        rdo_feminino.setEnabled( false );
        rdo_masculino.setEnabled( false );
        edt_rg.setEnabled( false );
        edt_cpf.setEnabled( false );
        sp_estado.setEnabled( false );
        sp_cidade.setEnabled( false );
        edt_telefone.setEnabled( false );
        edt_celular.setEnabled( false );
        edt_email.setEnabled( false );
        sp_tipo_cartao.setEnabled( false );
        edt_numero_cartao.setEnabled( false );
        edt_validade_cartao.setEnabled( false );
        sp_banco.setEnabled( false );
        edt_numero_agencia.setEnabled( false );
        edt_numero_conta_bancaria.setEnabled( false );
        edt_digito_verificador_conta_bancaria.setEnabled( false );
        edt_numero_nova_cnh.setEnabled( false );
        edt_validade_nova_cnh.setEnabled( false );
        edt_email_autenticacao.setEnabled( false );
        edt_senha.setEnabled( false );
        edt_confirmar_senha.setEnabled( false );
        edt_nova_senha.setEnabled( false );
    }

    private class EnviarNovosDadosParaApi extends AsyncTask<Void, Void, Void> {
        ProgressDialog progress;
        HashMap<String, String> parametros;
        Boolean resultado;

        private EnviarNovosDadosParaApi(HashMap<String, String> parametros) {
            this.parametros = parametros;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = ProgressDialog.show(context, "Carregando", "Aguarde");
        }

        @Override
        protected Void doInBackground(Void... params) {
            String url = getString(R.string.serverAddr) + "apis/android/set_info_usuario.php";

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

    private void modificar_informacoes() {

        int indice_view_ativa = flipper.getDisplayedChild();

        HashMap<String, String> parametros = new HashMap<>();

        if( indice_view_ativa == 0 ) {
            if( nova_foto != null ) {
                //new EnviarNovaImagemParaApi().execute();

                ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                nova_foto.compress(Bitmap.CompressFormat.JPEG, 100, byteArray);
                byte[] bytes_imagem = byteArray.toByteArray();

                parametros.put( "fotoPerfil", Base64.encodeToString(bytes_imagem, Base64.DEFAULT) );
            }

            if( edt_nome.getText() != null && !edt_nome.getText().toString().trim().isEmpty() ) {
                String nome = edt_nome.getText().toString().trim();
                parametros.put("nome", nome);
            }

            if( edt_sobrenome.getText() != null && !edt_sobrenome.getText().toString().trim().isEmpty() ) {
                String sobrenome = edt_sobrenome.getText().toString().trim();
                parametros.put("sobrenome", sobrenome);
            }

            if( edt_data_nascimento.getText() != null && !edt_data_nascimento.getText().toString().trim().isEmpty() ) {
                String str_data_nascimento = edt_data_nascimento.getText().toString().trim();

                Date data_nascimento = null;
                try {
                    data_nascimento = SimpleDateFormat.getDateInstance().parse( str_data_nascimento );

                    long unix_timestamp = data_nascimento.getTime()/1000;
                    parametros.put("dataNascimento", String.valueOf(unix_timestamp));

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            char sexo = ( rdo_feminino.isChecked() )? 'f' : 'm';
            parametros.put("sexo", String.valueOf(sexo));

            if( edt_rg.getText() != null && !edt_rg.getText().toString().trim().isEmpty() ) {
                String rg = edt_rg.getText().toString().trim();
                parametros.put("rg", rg);
            }

            if( edt_cpf.getText() != null && !edt_cpf.getText().toString().trim().isEmpty() ) {
                String cpf = edt_cpf.getText().toString().trim();
                parametros.put("cpf", cpf);
            }

            Estado estado_selecionado = (Estado) sp_estado.getSelectedItem();
            parametros.put("idEstado", String.valueOf(estado_selecionado.getId()));

            Cidade cidade_selecionada = (Cidade) sp_cidade.getSelectedItem();
            parametros.put("idCidade", String.valueOf(cidade_selecionada.getId()));

        } else if( indice_view_ativa == 1 ) {
            if( edt_telefone.getText() != null && !edt_telefone.getText().toString().trim().isEmpty() ) {
                String telefone = edt_telefone.getText().toString().trim();
                parametros.put("telefone", telefone);
            }

            if( edt_celular.getText() != null && !edt_celular.getText().toString().trim().isEmpty() ) {
                String celular = edt_celular.getText().toString().trim();
                parametros.put("celular", celular);
            }

            if( edt_email.getText() != null && !edt_email.getText().toString().trim().isEmpty() ) {
                String email = edt_email.getText().toString().trim();
                parametros.put("emailContato", email);
            }

        } else if( indice_view_ativa == 2 ) {

            if( sp_tipo_cartao.getSelectedItem() != null ) {
                TipoCartaoCredito tipoCartao = (TipoCartaoCredito) sp_tipo_cartao.getSelectedItem();

                parametros.put("idTipoCartao", String.valueOf(tipoCartao.getId()));

                if (edt_numero_cartao.getText() != null && !edt_numero_cartao.getText().toString().trim().isEmpty()) {
                    String numero_cartao = edt_numero_cartao.getText().toString().trim();
                    parametros.put("numeroCartao", numero_cartao);
                }

                if (edt_validade_cartao.getText() != null && !edt_validade_cartao.getText().toString().trim().isEmpty()) {
                    String validade_cartao = edt_validade_cartao.getText().toString().trim();
                    parametros.put("validadeCartao", validade_cartao);
                }
            }

            if( sp_banco.getSelectedItem() != null ) {
                Banco banco_selecionado = (Banco) sp_banco.getSelectedItem();
                parametros.put("idBanco", String.valueOf(banco_selecionado.getId()));

                if (edt_numero_agencia.getText() != null && !edt_numero_agencia.getText().toString().trim().isEmpty()) {
                    String numero_agencia = edt_numero_agencia.getText().toString().trim();
                    parametros.put("numeroAgencia", numero_agencia);
                }

                if (edt_numero_conta_bancaria.getText() != null && !edt_numero_conta_bancaria.getText().toString().trim().isEmpty()) {
                    String numero_conta_bancaria = edt_numero_conta_bancaria.getText().toString().trim();
                    parametros.put("numeroContaBancaria", numero_conta_bancaria);
                }

                if (edt_digito_verificador_conta_bancaria.getText() != null && !edt_digito_verificador_conta_bancaria.getText().toString().trim().isEmpty()) {
                    String digito_verificador_conta_bancaria = edt_digito_verificador_conta_bancaria.getText().toString().trim();
                    parametros.put("digitoContaBancaria", digito_verificador_conta_bancaria);
                }
            }

        } else if( indice_view_ativa == 3 ) {
            //edt_numero_nova_cnh.setEnabled( modo );
            //edt_validade_nova_cnh.setEnabled( modo );
        } else if( indice_view_ativa == 4 ) {
            if( edt_email_autenticacao.getText() != null && !edt_email_autenticacao.getText().toString().trim().isEmpty() ) {
                String email_autenticacao = edt_email_autenticacao.getText().toString().trim();
                parametros.put("emailAutenticacao", email_autenticacao);
            }

            if( edt_senha.getText() != null && !edt_senha.getText().toString().trim().isEmpty() ) {
                String senha = edt_senha.getText().toString().trim();
                parametros.put("senha", senha);
            }

            if( edt_confirmar_senha.getText() != null && !edt_confirmar_senha.getText().toString().trim().isEmpty() ) {
                String confirmar_senha = edt_confirmar_senha.getText().toString().trim();
                parametros.put("confirmarSenha", confirmar_senha);
            }

            if( edt_nova_senha.getText() != null && !edt_nova_senha.getText().toString().trim().isEmpty() ) {
                String nova_senha = edt_nova_senha.getText().toString().trim();
                parametros.put("novaSenha", nova_senha);
            }
        }

        if( parametros.size() > 0 ) {
            new EnviarNovosDadosParaApi(parametros).execute();
        }
    }

    private void toggle_botao_edicao(boolean modo) {
        if( botao_edicao == null ) return;

        if( !modo ) {
            botao_edicao.setIcon( R.mipmap.ic_edit_white );
            modificar_informacoes();
        } else {
            botao_edicao.setIcon( R.mipmap.ic_done_white );
        }
    }

    private void toggle_modo_edicao(boolean modo, boolean executar_modificacao) {
        if( executar_modificacao ) {
            toggle_botao_edicao(modo);
        }

        int indice_view_ativa = flipper.getDisplayedChild();

        desativar_entradas();

        if( indice_view_ativa == 0 ) {
            iv_foto_perfil.setEnabled( true );
            ColorMatrix matrix = new ColorMatrix();

            if( modo ) {
                matrix.setSaturation(1);
            } else {
                matrix.setSaturation(0);
            }

            iv_foto_perfil.setColorFilter( new ColorMatrixColorFilter(matrix) );

            edt_nome.setEnabled( modo );
            edt_sobrenome.setEnabled( modo );
            edt_data_nascimento.setEnabled( modo );
            rdo_feminino.setEnabled( modo );
            rdo_masculino.setEnabled( modo );
            edt_rg.setEnabled( modo );
            edt_cpf.setEnabled( modo );
            sp_estado.setEnabled( modo );
            sp_cidade.setEnabled( modo );
        } else if( indice_view_ativa == 1 ) {
            edt_telefone.setEnabled( modo );
            edt_celular.setEnabled( modo );
            edt_email.setEnabled( modo );
        } else if( indice_view_ativa == 2 ) {
            sp_tipo_cartao.setEnabled( modo );
            edt_numero_cartao.setEnabled( modo );
            edt_validade_cartao.setEnabled( modo );
            sp_banco.setEnabled( modo );
            edt_numero_agencia.setEnabled( modo );
            edt_numero_conta_bancaria.setEnabled( modo );
            edt_digito_verificador_conta_bancaria.setEnabled( modo );
        } else if( indice_view_ativa == 3 ) {
            edt_numero_nova_cnh.setEnabled( modo );
            edt_validade_nova_cnh.setEnabled( modo );
        } else if( indice_view_ativa == 4 ) {
            edt_email_autenticacao.setEnabled( modo );
            edt_senha.setEnabled( modo );
            edt_confirmar_senha.setEnabled( modo );
            edt_nova_senha.setEnabled( modo );
        }
    }

    private Bitmap resize(Bitmap image, int maxWidth, int maxHeight) {
        if (maxHeight > 0 && maxWidth > 0) {
            int width = image.getWidth();
            int height = image.getHeight();
            float ratioBitmap = (float) width / (float) height;
            float ratioMax = (float) maxWidth / (float) maxHeight;

            int finalWidth = maxWidth;
            int finalHeight = maxHeight;
            if (ratioMax > 1) {
                finalWidth = (int) ((float)maxHeight * ratioBitmap);
            } else {
                finalHeight = (int) ((float)maxWidth / ratioBitmap);
            }
            image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true);
            return image;
        } else {
            return image;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_dados_conta, menu);

        botao_edicao = menu.getItem(0);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch( item.getItemId() ) {
            case R.id.action_editar:
                modo_edicao = !modo_edicao;
                toggle_modo_edicao(modo_edicao, true);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if( requestCode == GET_IMAGE && resultCode == RESULT_OK ) {
            Uri foto_selecionada = data.getData();

            try {
                Bitmap mp = MediaStore.Images.Media.getBitmap(getContentResolver(), foto_selecionada);
                mp = resize(mp, iv_foto_perfil.getWidth(), iv_foto_perfil.getHeight());
                nova_foto = mp;

                iv_foto_perfil.setImageBitmap( mp );

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private class CrudCnh extends AsyncTask<Void, Void, Void> {
        ProgressDialog progress;
        private static final String INSERT = "insert", UPDATE = "update", DELETE = "delete";
        private Cnh cnh_alvo;
        private String modo;
        private Boolean boolean_resultado;
        private Integer integer_resultado;

        private CrudCnh(String modo, Cnh cnh) {
            this.modo = modo;
            this.cnh_alvo = cnh;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = ProgressDialog.show(context, "Carregando", "Aguarde");
        }

        @Override
        protected Void doInBackground(Void... params) {
            String url = getString(R.string.serverAddr) + "apis/android/crud_cnh.php";

            HashMap<String, String> parametros = new HashMap<>();
            parametros.put("idCnh", String.valueOf(cnh_alvo.getId()));

            if( this.modo.equals(UPDATE) || this.modo.equals(INSERT) ) {
                parametros.put("txtNumeroRegistro", String.valueOf(cnh_alvo.getNumeroRegistro()));

                long unix_timestamp = Long.parseLong(cnh_alvo.getValidade()) / 1000;
                parametros.put("dataValidade", String.valueOf(unix_timestamp));
            }

            parametros.put("modo", this.modo);
            parametros.put("idUsuario", String.valueOf(Login.getId_usuario(context)));

            String json = HttpRequest.post(url, parametros);
            Log.d("JSONCNH", json);

            if( this.modo.equals(DELETE) || this.modo.equals(UPDATE) ) {
                boolean_resultado = new Gson().fromJson(json, Boolean.class);
            } else {
                integer_resultado = new Gson().fromJson(json, Integer.class);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progress.dismiss();

            if( ( boolean_resultado != null && !boolean_resultado ) || ( integer_resultado != null && integer_resultado <= 0 ) ) {
                AlertDialog dialog = new AlertDialog.Builder(context).setTitle("Erro").setMessage("Houve uma falha ao tentar modificar a CNH").create();
                dialog.show();
            } else {
                new GetInfoUsuario().execute();
            }
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

    private class GetBancos extends AsyncTask<Void, Void, Void> {
        Banco[] listaBancos;
        Integer idBanco;

        private GetBancos(Integer idBanco) {
            this.idBanco = idBanco;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String url = getString(R.string.serverAddr) + "apis/android/get_bancos.php";

            String json = HttpRequest.get(url);
            listaBancos = new Gson().fromJson(json, Banco[].class);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            if( listaBancos != null ) {
                sp_banco.setAdapter( new ArrayAdapter<>(context, R.layout.support_simple_spinner_dropdown_item, listaBancos) );

                if( idBanco != null ) {

                    for( int i = 0; i < sp_banco.getAdapter().getCount(); ++i ) {
                        Banco banco = (Banco) sp_banco.getItemAtPosition(i);

                        if( banco.getId() == idBanco ) {
                            sp_banco.setSelection(i);
                        }
                    }

                }
            }

        }
    }

    private class GetTiposCartaoCredito extends AsyncTask<Void, Void, Void> {
        TipoCartaoCredito[] listaTipoCartao;
        Integer idTipoCartao;

        private GetTiposCartaoCredito(Integer idTipoCartao) {
            this.idTipoCartao = idTipoCartao;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String url = getString(R.string.serverAddr) + "apis/android/get_tipos_cartao.php";

            String json = HttpRequest.get(url);

            listaTipoCartao = new Gson().fromJson(json, TipoCartaoCredito[].class);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

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
        Estado[] listaEstados;
        Integer idEstado;

        private GetEstados(Integer idEstado) {
            this.idEstado = idEstado;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
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

                if( usuario.getCartaoCredito() != null ) {
                    new GetTiposCartaoCredito( usuario.getCartaoCredito().getIdTipo() ).execute();
                    edt_numero_cartao.setText( usuario.getCartaoCredito().getNumero() );

                    try {

                        edt_validade_cartao.setText( SimpleDateFormat.getInstance().format(
                                new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(usuario.getCartaoCredito().getVencimento())
                        ) );

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                } else {
                    new GetTiposCartaoCredito( null ).execute();
                }

                if( usuario.getContaBancaria() != null ) {
                    new GetBancos( usuario.getContaBancaria().getIdTipoConta() ).execute();
                    edt_numero_agencia.setText( String.format(Locale.getDefault(), "%d", usuario.getContaBancaria().getNumeroAgencia()) );
                    edt_numero_conta_bancaria.setText( String.format(Locale.getDefault(), "%d", usuario.getContaBancaria().getConta()) );
                    edt_digito_verificador_conta_bancaria.setText( String.format(Locale.getDefault(), "%d", usuario.getContaBancaria().getDigito()) );
                } else {
                    new GetBancos( null ).execute();
                }

                adapter_cnh = new CnhAdapter(context, R.layout.list_view_item_cnh, Arrays.asList( usuario.getListaCnh() ));
                lv_cnh.setAdapter( adapter_cnh );

                edt_email_autenticacao.setText( usuario.getEmail() );
            }

        }
    }

    private class CnhAdapter extends ArrayAdapter<Cnh> {

        int resource;
        private CnhAdapter(@NonNull Context context, @LayoutRes int resource, List<Cnh> content) {
            super(context, resource, content);
            this.resource = resource;
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View v = convertView;
            if( v == null ) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                v = inflater.inflate(this.resource, null);
            }

            final Cnh cnh = getItem(position);

            final EditText edt_numero_cnh = (EditText) v.findViewById(R.id.edt_numero_cnh);
            final ImageButton ib_editar = (ImageButton) v.findViewById(R.id.ib_editar_cnh);

            ib_editar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if( cnh == null ) return;

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    View dialog_info_cnh = View.inflate(getContext(), R.layout.dialog_editar_cnh, null);

                    final EditText edt_numero_cnh_dialog = (EditText) dialog_info_cnh.findViewById(R.id.edt_numero_cnh_dialog);
                    final EditText edt_validade_cnh_dialog = (EditText) dialog_info_cnh.findViewById(R.id.edt_validade_cnh);
                    ImageButton btn_remover = (ImageButton) dialog_info_cnh.findViewById(R.id.ib_remover);
                    ImageButton btn_salvar = (ImageButton) dialog_info_cnh.findViewById(R.id.ib_salvar);

                    edt_numero_cnh_dialog.setText( String.valueOf( cnh.getNumeroRegistro() ) );

                    edt_validade_cnh_dialog.setOnClickListener( new ActionData(new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            try {
                                Date data_validade = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse( String.format(Locale.getDefault(), "%d-%d-%d", year, month+1, dayOfMonth) );

                                edt_validade_cnh_dialog.setText( SimpleDateFormat.getDateInstance().format( data_validade ) );

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }) );

                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

                    try {
                        Date data_validade = formatter.parse( cnh.getValidade() );

                        edt_validade_cnh_dialog.setText( SimpleDateFormat.getDateInstance().format(data_validade) );

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    builder.setView(dialog_info_cnh);
                    final AlertDialog dialog = builder.create();

                    btn_remover.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            new CrudCnh( CrudCnh.DELETE, cnh ).execute();
                            dialog.dismiss();
                        }
                    });

                    btn_salvar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            try {
                                String numero_cnh = edt_numero_cnh_dialog.getText().toString().trim();
                                Date data_validade = SimpleDateFormat.getDateInstance().parse( edt_validade_cnh_dialog.getText().toString().trim() );

                                cnh.setNumeroRegistro( Integer.parseInt(numero_cnh) );
                                cnh.setValidade( String.valueOf(data_validade.getTime()) );

                                new CrudCnh( CrudCnh.UPDATE, cnh ).execute();

                                dialog.dismiss();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                        }
                    });

                    dialog.show();
                }
            });

            edt_numero_cnh.setText(String.format(Locale.getDefault(), "%d", cnh.getNumeroRegistro()));

            return v;
        }
    }
}
