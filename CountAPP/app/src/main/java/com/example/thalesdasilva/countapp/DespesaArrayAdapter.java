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

import com.example.thalesdasilva.countapp.entidades.Despesa;

/**
 * Created by ThalesdaSilva on 07/08/2017.
 */

public class DespesaArrayAdapter extends ArrayAdapter<Despesa> {

    //Constantes Públicas
    public static final String GAMES = "Games";
    public static final String TELEFONE = "Telefone";
    public static final String VIAGEM = "Viagem";
    public static final String GASTOS_PESSOAIS = "Gastos pessoais";
    public static final String VEICULO = "Veículo";
    public static final String COMBUSTIVEL = "Combustível";
    public static final String ALIMENTACAO = "Alimentação";
    public static final String CONSERTOS = "Consertos";
    public static final String ELETRONICOS = "Eletrônicos";
    public static final String COMPRAS = "Compras";
    public static final String LIVROS = "Livros/ Revistas/ Jornais";
    public static final String CIGARROS = "Cigarros";
    public static final String RELOGIO = "Relógio";
    public static final String PENSAO = "Pensão";
    public static final String PERDA_APOSTA = "Perdas de aposta";
    public static final String ALUGUEL = "Aluguel";
    public static final String ROUPAS = "Roupas";
    public static final String CRIANCAS = "Crianças";
    public static final String ENTRETENIMENTO = "Entretenimento";
    public static final String FAMILIA = "Família";
    public static final String PRESENTE = "Presente";
    public static final String SAUDE = "Saúde";
    public static final String CASA = "Casa";
    public static final String SEGURO = "Seguro";
    public static final String ANIMAL = "Animais de estimação";
    public static final String CALCADOS = "Calçados";
    public static final String IMPOSTOS = "Impostos";
    public static final String TRANSPORTE = "Transporte";
    public static final String AGUA = "Água";
    public static final String LUZ = "Luz";
    public static final String GAS = "Gás";
    public static final String OUTROS = "Outros";

    //Atributos
    private int resource = 0;
    private LayoutInflater inflater;
    private Context context;

    public DespesaArrayAdapter(Context context, int resource) {
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

        Despesa despesa = getItem(position);

        if (despesa.getNome().equals(this.GAMES)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                viewHolder.txtIMG.setBackground(getContext().getResources().getDrawable(R.drawable.ic_gamer_controller));
            }
        } else if (despesa.getNome().equals(this.TELEFONE)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                viewHolder.txtIMG.setBackground(getContext().getResources().getDrawable(R.drawable.ic_call));
            }
        } else if (despesa.getNome().equals(this.VIAGEM)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                viewHolder.txtIMG.setBackground(getContext().getResources().getDrawable(R.drawable.ic_travel_1));
            }
        } else if (despesa.getNome().equals(this.GASTOS_PESSOAIS)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                viewHolder.txtIMG.setBackground(getContext().getResources().getDrawable(R.drawable.ic_human));
            }
        } else if (despesa.getNome().equals(this.VEICULO)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                viewHolder.txtIMG.setBackground(getContext().getResources().getDrawable(R.drawable.ic_car));
            }
        } else if (despesa.getNome().equals(this.COMBUSTIVEL)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                viewHolder.txtIMG.setBackground(getContext().getResources().getDrawable(R.drawable.ic_fuel));
            }
        } else if (despesa.getNome().equals(this.ALIMENTACAO)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                viewHolder.txtIMG.setBackground(getContext().getResources().getDrawable(R.drawable.ic_java));
            }
        } else if (despesa.getNome().equals(this.CONSERTOS)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                viewHolder.txtIMG.setBackground(getContext().getResources().getDrawable(R.drawable.ic_build));
            }
        } else if (despesa.getNome().equals(this.ELETRONICOS)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                viewHolder.txtIMG.setBackground(getContext().getResources().getDrawable(R.drawable.ic_laptop));
            }
        } else if (despesa.getNome().equals(this.COMPRAS)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                viewHolder.txtIMG.setBackground(getContext().getResources().getDrawable(R.drawable.ic_shopping));
            }
        } else if (despesa.getNome().equals(this.LIVROS)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                viewHolder.txtIMG.setBackground(getContext().getResources().getDrawable(R.drawable.ic_book_2));
            }
        } else if (despesa.getNome().equals(this.CIGARROS)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                viewHolder.txtIMG.setBackground(getContext().getResources().getDrawable(R.drawable.ic_cigarette));
            }
        } else if (despesa.getNome().equals(this.RELOGIO)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                viewHolder.txtIMG.setBackground(getContext().getResources().getDrawable(R.drawable.ic_clock));
            }
        } else if (despesa.getNome().equals(this.PENSAO)){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                viewHolder.txtIMG.setBackground(getContext().getResources().getDrawable(R.drawable.ic_child_2));
            }
        } else if (despesa.getNome().equals(this.PERDA_APOSTA)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                viewHolder.txtIMG.setBackground(getContext().getResources().getDrawable(R.drawable.ic_cards_128));
            }
        } else if (despesa.getNome().equals(this.ALUGUEL)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                viewHolder.txtIMG.setBackground(getContext().getResources().getDrawable(R.drawable.ic_home));
            }
        } else if (despesa.getNome().equals(this.ROUPAS)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                viewHolder.txtIMG.setBackground(getContext().getResources().getDrawable(R.drawable.ic_t_shirt));
            }
        } else if (despesa.getNome().equals(this.CRIANCAS)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                viewHolder.txtIMG.setBackground(getContext().getResources().getDrawable(R.drawable.ic_children));
            }
        } else if (despesa.getNome().equals(this.ENTRETENIMENTO)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                viewHolder.txtIMG.setBackground(getContext().getResources().getDrawable(R.drawable.ic_party));
            }
        } else if (despesa.getNome().equals(this.FAMILIA)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                viewHolder.txtIMG.setBackground(getContext().getResources().getDrawable(R.drawable.ic_family));
            }
        } else if (despesa.getNome().equals(this.PRESENTE)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                viewHolder.txtIMG.setBackground(getContext().getResources().getDrawable(R.drawable.ic_gift));
            }
        } else if (despesa.getNome().equals(this.SAUDE)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                viewHolder.txtIMG.setBackground(getContext().getResources().getDrawable(R.drawable.ic_health));
            }
        } else if (despesa.getNome().equals(this.CASA)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                viewHolder.txtIMG.setBackground(getContext().getResources().getDrawable(R.drawable.ic_home));
            }
        } else if (despesa.getNome().equals(this.SEGURO)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                viewHolder.txtIMG.setBackground(getContext().getResources().getDrawable(R.drawable.ic_safe));
            }
        } else if (despesa.getNome().equals(this.ANIMAL)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                viewHolder.txtIMG.setBackground(getContext().getResources().getDrawable(R.drawable.ic_animals));
            }
        } else if (despesa.getNome().equals(this.CALCADOS)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                viewHolder.txtIMG.setBackground(getContext().getResources().getDrawable(R.drawable.ic_shoes));
            }
        } else if (despesa.getNome().equals(this.IMPOSTOS)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                viewHolder.txtIMG.setBackground(getContext().getResources().getDrawable(R.drawable.ic_building));
            }
        } else if (despesa.getNome().equals(this.TRANSPORTE)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                viewHolder.txtIMG.setBackground(getContext().getResources().getDrawable(R.drawable.ic_bus));
            }
        } else if (despesa.getNome().equals(this.AGUA)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                viewHolder.txtIMG.setBackground(getContext().getResources().getDrawable(R.drawable.ic_water));
            }
        } else if (despesa.getNome().equals(this.LUZ)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                viewHolder.txtIMG.setBackground(getContext().getResources().getDrawable(R.drawable.ic_lamp));
            }
        } else if (despesa.getNome().equals(this.GAS)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                viewHolder.txtIMG.setBackground(getContext().getResources().getDrawable(R.drawable.ic_gas));
            }
        } else if (despesa.getNome().equals(this.OUTROS)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                viewHolder.txtIMG.setBackground(getContext().getResources().getDrawable(R.drawable.ic_outros));
            }
        }

        viewHolder.txtNome.setText(despesa.getNome());
        viewHolder.txtValorTransacao.setText(String.valueOf(despesa.getValorTransacao()));

        return view;

    }

    static class ViewHolder {
        TextView txtIMG;
        TextView txtNome;
        TextView txtValorTransacao;
    }

}//fim da classe
