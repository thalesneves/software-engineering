package com.example.thalesdasilva.countapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.thalesdasilva.countapp.entidades.Usuario;
import com.example.thalesdasilva.countapp.fragments.Perfil;

/**
 * Created by ThalesdaSilva on 14/10/2017.
 */

public class UsuarioArrayAdapter extends ArrayAdapter<Usuario> {

    //Atributos
    private int resource = 0;
    private LayoutInflater inflater;
    private Context context;

    public UsuarioArrayAdapter(Context context, int resource) {
        super(context, resource);
        //Código responsável por gerar a interface.
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.resource = resource;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = null;
        UsuarioArrayAdapter.ViewHolder viewHolder = null;

        if (convertView == null) {

            viewHolder = new UsuarioArrayAdapter.ViewHolder();

            view = inflater.inflate(resource, parent, false);

            viewHolder.edtEmail = (EditText) view.findViewById(R.id.edtEmail);
            viewHolder.edtNome = (EditText) view.findViewById(R.id.edtNome);
            viewHolder.edtSenha = (EditText) view.findViewById(R.id.edtSenha);

            view.setTag(viewHolder);

            convertView = view;

        } else {
            viewHolder = (UsuarioArrayAdapter.ViewHolder) convertView.getTag();
            view = convertView;
        }

        Usuario usuario = getItem(position);

        viewHolder.edtEmail.setText(usuario.getEmail());
        viewHolder.edtNome.setText(usuario.getNomeUsuario());
        viewHolder.edtSenha.setText(usuario.getSenha());

        return view;

    }

    static class ViewHolder {
        EditText edtEmail;
        EditText edtNome;
        EditText edtSenha;
    }

}//fim da classe
