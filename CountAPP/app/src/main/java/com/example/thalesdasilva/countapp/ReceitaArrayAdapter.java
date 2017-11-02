package com.example.thalesdasilva.countapp;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.thalesdasilva.countapp.entidades.Receita;

/**
 * Created by ThalesdaSilva on 07/08/2017.
 */

public class ReceitaArrayAdapter extends ArrayAdapter<Receita> {

    //Constantes Públicas
    public static final String SALARIO = "Salário";
    public static final String ALUGUEL = "Aluguel";
    public static final String TRABALHO = "Trabalho extra";
    public static final String EMPRESA = "Empresa";
    public static final String RENDIMENTO_FINANCEIRO = "Rendimentos financeiros";
    public static final String JOGOS_DE_APOSTA = "Ganhos de aposta";
    public static final String PENSAO = "Pensão";
    public static final String ECONOMIAS_PESSOAIS= "Economias pessoais";
    public static final String OUTROS = "Outros";

    //Atributos
    private int resource = 0;
    private LayoutInflater inflater;
    private Context context;

    public ReceitaArrayAdapter(Context context, int resource) {
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
        ViewHolder viewHolder = null;

        if (convertView == null) {

            viewHolder = new ViewHolder();

            view = inflater.inflate(resource, parent, false);

            viewHolder.txtIMG = (TextView) view.findViewById(R.id.txtIMG);
            viewHolder.txtNome = (TextView) view.findViewById(R.id.txtCategoria);
            viewHolder.txtValorTransacao = (TextView) view.findViewById(R.id.txtValorTransacao);

            view.setTag(viewHolder);

            convertView = view;

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            view = convertView;
        }

        Receita receita = getItem(position);

        if (receita.getNome().equals(this.SALARIO)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                viewHolder.txtIMG.setBackground(getContext().getResources().getDrawable(R.drawable.ic_money_2));
            }
        } else if (receita.getNome().equals(this.ALUGUEL)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                viewHolder.txtIMG.setBackground(getContext().getResources().getDrawable(R.drawable.ic_home));
            }
        } else if (receita.getNome().equals(this.TRABALHO)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                viewHolder.txtIMG.setBackground(getContext().getResources().getDrawable(R.drawable.ic_work));
            }
        } else if (receita.getNome().equals(this.EMPRESA)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                viewHolder.txtIMG.setBackground(getContext().getResources().getDrawable(R.drawable.ic_company_1));
            }
        } else if (receita.getNome().equals(this.RENDIMENTO_FINANCEIRO)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                viewHolder.txtIMG.setBackground(getContext().getResources().getDrawable(R.drawable.ic_bank));
            }
        } else if (receita.getNome().equals(this.JOGOS_DE_APOSTA)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                viewHolder.txtIMG.setBackground(getContext().getResources().getDrawable(R.drawable.ic_cards_128));
            }
        } else if (receita.getNome().equals(this.PENSAO)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                viewHolder.txtIMG.setBackground(getContext().getResources().getDrawable(R.drawable.ic_child_2));
            }
        } else if (receita.getNome().equals(this.ECONOMIAS_PESSOAIS)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                viewHolder.txtIMG.setBackground(getContext().getResources().getDrawable(R.drawable.ic_coin2));
            }
        } else if (receita.getNome().equals(this.OUTROS)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                viewHolder.txtIMG.setBackground(getContext().getResources().getDrawable(R.drawable.ic_outros));
            }
        }

        viewHolder.txtNome.setText(receita.getNome());
        viewHolder.txtValorTransacao.setText(String.valueOf(receita.getValorTransacao()));

        return view;

    }

    static class ViewHolder {
        TextView txtIMG;
        TextView txtNome;
        TextView txtValorTransacao;
    }

}//fim da classe
