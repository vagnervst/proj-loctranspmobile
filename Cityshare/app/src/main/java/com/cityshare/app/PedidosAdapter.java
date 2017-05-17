package com.cityshare.app;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cityshare.app.model.Pedido;

import java.util.List;

/**
 * Created by vagne_000 on 09/05/2017.
 */

public class PedidosAdapter extends ArrayAdapter<Pedido> {

    int resource;
    public PedidosAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Pedido> objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if( convertView == null ) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(resource, null);
        }

        Pedido pedido = getItem(position);

        TextView veiculo = (TextView) v.findViewById(R.id.txt_modelo_veiculo);
        TextView data_retirada = (TextView) v.findViewById(R.id.txt_data_retirada);
        TextView data_entrega = (TextView) v.findViewById(R.id.txt_data_entrega);
        TextView status = (TextView) v.findViewById(R.id.txt_status_pedido);

        veiculo.setText( pedido.getVeiculo() );
        data_retirada.setText( pedido.getDataRetirada() );
        data_entrega.setText( pedido.getDataEntrega() );
        status.setText( pedido.getStatusPedido() );

        return v;
    }
}
