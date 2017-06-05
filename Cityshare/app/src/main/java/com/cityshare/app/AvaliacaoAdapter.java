package com.cityshare.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cityshare.app.model.Avaliacao;
import com.cityshare.app.model.HttpRequest;
import com.cityshare.app.model.Server;
import com.cityshare.app.model.Usuario;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

/**
 * Created by vagne_000 on 03/06/2017.
 */

public class AvaliacaoAdapter extends ArrayAdapter<Avaliacao> {

    int resource;
    public AvaliacaoAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Avaliacao> objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if( v == null ) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(this.resource, null);
        }

        Avaliacao avaliacao = getItem(position);

        ImageView iv_foto_avaliador = (ImageView) v.findViewById(R.id.iv_foto_avaliador);
        TextView txt_nome_avaliador = (TextView) v.findViewById(R.id.txt_nome_avaliador);
        TextView txt_mensagem_avaliacao = (TextView) v.findViewById(R.id.txt_mensagem_avaliacao);

        try {
            txt_nome_avaliador.setText(String.format(Locale.getDefault(), "%s %s", avaliacao.getNomeAvaliador(), avaliacao.getSobrenomeAvaliador()));
            txt_mensagem_avaliacao.setText(avaliacao.getMensagem());

            new GetFotoAvaliador(avaliacao.getIdUsuarioAvaliador(), iv_foto_avaliador).execute();
        } catch( NullPointerException e) {
            e.printStackTrace();
        }

        return v;
    }

    private class GetFotoAvaliador extends AsyncTask<Void, Void, Usuario> {
        private int idUsuario;
        private ImageView imageView;

        private GetFotoAvaliador(int idUsuario, ImageView imageView) {
            this.idUsuario = idUsuario;
            this.imageView = imageView;
        }

        @Override
        protected Usuario doInBackground(Void... params) {
            String url = Server.servidor + "apis/android/get_info_usuario.php";

            HashMap<String, String> parametros = new HashMap<>();
            parametros.put("idUsuario", String.valueOf(this.idUsuario));

            String json = HttpRequest.post(url, parametros);
            Log.d("JSONAVALIADOR", json);

            return new Gson().fromJson(json, Usuario.class);
        }

        @Override
        protected void onPostExecute(Usuario usuario) {
            String url_foto = Server.servidor + "img/uploads/usuarios/" + usuario.getFotoPerfil();
            new CarregarFoto(url_foto, this.imageView).execute();
        }
    }

    private class CarregarFoto extends AsyncTask<Void, Void, Bitmap> {
        private String urlFoto;
        private ImageView imageView;

        private CarregarFoto(String url, ImageView image_view_alvo) {
            this.urlFoto = url;
            this.imageView = image_view_alvo;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            Bitmap foto = null;
            try {
                foto = Glide.with(getContext()).load(this.urlFoto).asBitmap().into(100, 100).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            return foto;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            RoundedBitmapDrawable roundBitmap = RoundedBitmapDrawableFactory.create(null, bitmap);
            roundBitmap.setCircular(true);

            this.imageView.setImageDrawable( roundBitmap );
        }
    }
}
