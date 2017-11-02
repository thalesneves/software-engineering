package com.example.thalesdasilva.countapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.thalesdasilva.countapp.ActCadastro;
import com.example.thalesdasilva.countapp.ActDespesa;
import com.example.thalesdasilva.countapp.ActLogin;
import com.example.thalesdasilva.countapp.ActReceita;
import com.example.thalesdasilva.countapp.ActVisaoGeralDespesa;
import com.example.thalesdasilva.countapp.ActVisaoGeralReceita;
import com.example.thalesdasilva.countapp.R;
import com.example.thalesdasilva.countapp.app.MessageBox;
import com.example.thalesdasilva.countapp.database.DataBase;
import com.example.thalesdasilva.countapp.dominio.RepositorioDespesa;
import com.example.thalesdasilva.countapp.dominio.RepositorioReceita;
import com.example.thalesdasilva.countapp.entidades.Despesa;
import com.example.thalesdasilva.countapp.entidades.Receita;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link VisaoGeral.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link VisaoGeral#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VisaoGeral extends Fragment implements AdapterView.OnItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //Atributos
    private Button btnReceitas;
    private Button btnDespesas;

    private ListView lstReceitas;
    private ArrayAdapter<Receita> adpReceitas;

    private ListView lstDespesas;
    private ArrayAdapter<Despesa> adpDespesas;

    private DataBase database;
    private SQLiteDatabase conn;

    public RepositorioReceita repositorioReceita;
    public RepositorioDespesa repositorioDespesa;

    public static final String Par_Receita = "receita";
    public static final String Par_Despesa = "despesa";

    public Long IDPerfil = null;

    private OnFragmentInteractionListener mListener;

    public VisaoGeral() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VisaoGeral.
     */
    // TODO: Rename and change types and number of parameters
    public static VisaoGeral newInstance(String param1, String param2) {
        VisaoGeral fragment = new VisaoGeral();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().setTitle("Visão Geral");

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.frag_visao_geral, container, false);

        btnReceitas = (Button) view.findViewById(R.id.btnReceitas);
        btnDespesas = (Button) view.findViewById(R.id.btnDespesa);
        lstReceitas = (ListView) view.findViewById(R.id.lstReceitas);
        lstDespesas = (ListView) view.findViewById(R.id.lstDespesas);

        lstReceitas.setOnItemClickListener(VisaoGeral.this);
        lstDespesas.setOnItemClickListener(VisaoGeral.this);

        btnReceitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getContext(), ActVisaoGeralReceita.class);
                startActivity(it);
            }
        });

        btnDespesas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getContext(), ActVisaoGeralDespesa.class);
                startActivity(it);
            }
        });

        try {

            database = new DataBase(getContext());
            conn = database.getWritableDatabase();

            repositorioReceita = new RepositorioReceita(conn);
            repositorioDespesa = new RepositorioDespesa(conn);

//            adpReceitas = repositorioReceita.buscaReceitas(view.getContext());
//            adpDespesas = repositorioDespesa.buscaDespesas(view.getContext());

//            Bundle bundle = getActivity().getIntent().getExtras();
//            final String nome = bundle.getString("edtNome");

            ArrayList<String> dados = database.buscarPerfil(ActLogin.nome);

            IDPerfil = Long.parseLong(dados.get(0));

//            MessageBox.show(getContext(), "Alerta", "ID: " + IDPerfil);

            adpReceitas = database.buscaReceitasPorIDUsuario(view.getContext(), IDPerfil);

            adpDespesas = database.buscaDespesasPorIDUsuario(view.getContext(), IDPerfil);

            lstReceitas.setAdapter(adpReceitas);
            lstDespesas.setAdapter(adpDespesas);

            //MessageBox.show(view.getContext(), "Mensagem", "Conexão criada com sucesso!");

        } catch (SQLException e) {
            MessageBox.show(getContext(), "Erro", "Erro ao criar o Banco de Dados: " + e.getMessage());
        }

        return view;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (conn != null) {
            conn.close();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        atualizaLstReceitas();
        atualizaLstDespesas();

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fabPlus);
        fab.show();
    }

    public void atualizaLstReceitas() {
//        adpReceitas = repositorioReceita.buscaReceitas(getContext());
        adpReceitas = database.buscaReceitasPorIDUsuario(getContext(), IDPerfil);
        lstReceitas.setAdapter(adpReceitas);
    }

    public void atualizaLstDespesas() {
//        adpDespesas = repositorioDespesa.buscaDespesas(getContext());
        adpDespesas = database.buscaDespesasPorIDUsuario(getContext(), IDPerfil);
        lstDespesas.setAdapter(adpDespesas);
    }

    //AdapterView<?> parent = ele irá retornar uma referência para o objeto Adapter, através do método getAdapter.
    //View v = irá retornar a referência do componente que disparou a ação "ListView"
    //int position = irá retornar a posição do item selecionado
    //long id = irá retornar o id do linha do ListView
    @Override
    public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {

        try {

            lstReceitas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Receita receita = adpReceitas.getItem(position);
                    Intent itReceita = new Intent(view.getContext(), ActReceita.class);
                    itReceita.putExtra(Par_Receita, receita);
                    getActivity().startActivityFromFragment(VisaoGeral.this, itReceita, 0, null);
                }
            });

            lstDespesas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Despesa despesa = adpDespesas.getItem(position);
                    Intent itDespesa = new Intent(view.getContext(), ActDespesa.class);
                    itDespesa.putExtra(Par_Despesa, despesa);
                    getActivity().startActivityFromFragment(VisaoGeral.this, itDespesa, 0, null);
                }
            });

        } catch (Exception e) {
            MessageBox.show(getContext(), "Mensagem", "Erro: " + e.getMessage());
        }

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        } else {
            Toast.makeText(getContext(), "Visão Geral.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}//fim da classe
