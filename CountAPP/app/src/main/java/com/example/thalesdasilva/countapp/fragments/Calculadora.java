package com.example.thalesdasilva.countapp.fragments;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.thalesdasilva.countapp.R;
import com.example.thalesdasilva.countapp.app.MessageBox;

import org.javia.arity.Symbols;
import org.javia.arity.SyntaxException;

import java.text.DecimalFormat;
import java.util.Locale;

public class Calculadora extends Fragment {

    //Atributos
    private EditText edtTela1;
    private EditText edtTela2;

    private Button btn0;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;
    private Button btn7;
    private Button btn8;
    private Button btn9;

    private Button btnC;
    private Button btnDiv;
    private Button btnX;
    private Button btnSub;
    private Button btnSum;
    private Button btnP;
    private Button btnI;

    private Boolean flag = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Calculadora");
    }

    //Método que limpa a tela
    private void limparTelaClickable() {
        String edt = String.valueOf(edtTela1.getText());
        if (edt.length() > 0) {
            String edt1 = edt.substring(0, edt.length() - 1);
            edtTela1.setText(edt1);
        }
    }

    //Método que limpa a tela(Long Pressed)
    private void limparTelaPressed() {
        edtTela1.setText(null);
        edtTela2.setText(null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for getActivity() fragment
        final View view = inflater.inflate(R.layout.frag_calculadora, container, false);

        edtTela1 = (EditText) view.findViewById(R.id.edtTela1);
        edtTela2 = (EditText) view.findViewById(R.id.edtTela2);

        edtTela1.setKeyListener(null);
        edtTela2.setKeyListener(null);

        btn0 = (Button) view.findViewById(R.id.btn0);
        btn1 = (Button) view.findViewById(R.id.btn1);
        btn2 = (Button) view.findViewById(R.id.btn2);
        btn3 = (Button) view.findViewById(R.id.btn3);
        btn4 = (Button) view.findViewById(R.id.btn4);
        btn5 = (Button) view.findViewById(R.id.btn5);
        btn6 = (Button) view.findViewById(R.id.btn6);
        btn7 = (Button) view.findViewById(R.id.btn7);
        btn8 = (Button) view.findViewById(R.id.btn8);
        btn9 = (Button) view.findViewById(R.id.btn9);

        btnC = (Button) view.findViewById(R.id.btnC);
        btnDiv = (Button) view.findViewById(R.id.btnDiv);
        btnX = (Button) view.findViewById(R.id.btnX);
        btnSub = (Button) view.findViewById(R.id.btnSub);
        btnSum = (Button) view.findViewById(R.id.btnSum);
        btnP = (Button) view.findViewById(R.id.btnP);
        btnI = (Button) view.findViewById(R.id.btnI);

        btnP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String text = edtTela1.getText().toString();
                //edtTela.setText(edtTela.getText() + getString(R.string.P));

                int qtdSeparador = 0;
                loopFor:
                for (int i = text.length() - 1; i >= 0; i--) {
                    // System.out.println(texto.charAt(i));
                    if (text.charAt(i) == getString(R.string.P).charAt(0)) {
                        // System.out.println("separador");
                        qtdSeparador++;
                    } else if (text.charAt(i) == '+'
                            || text.charAt(i) == '-'
                            || text.charAt(i) == '×'
                            || text.charAt(i) == '÷') {
                        break loopFor;
                    }
                }
                // System.out.println(qtdSeparador);
                if (qtdSeparador > 0) {
                    return;
                } else {
                    edtTela1.setText(text.substring(0, text.length())
                            + getString(R.string.P));
                }

            }
        });

        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtTela1.setText(edtTela1.getText() + "0");
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtTela1.setText(edtTela1.getText() + "1");
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtTela1.setText(edtTela1.getText() + "2");
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtTela1.setText(edtTela1.getText() + "3");
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtTela1.setText(edtTela1.getText() + "4");
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtTela1.setText(edtTela1.getText() + "5");
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtTela1.setText(edtTela1.getText() + "6");
            }
        });

        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtTela1.setText(edtTela1.getText() + "7");
            }
        });

        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtTela1.setText(edtTela1.getText() + "8");
            }
        });

        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtTela1.setText(edtTela1.getText() + "9");
            }
        });

        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnC.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        limparTelaClickable();
                        if (edtTela2.getText().equals(getResources().getString(R.string.error))) {
                            edtTela2.setText(null);
                        }
                        flag = false;
                    }
                });

                btnC.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        limparTelaPressed();
                        flag = false;
                        return false;
                    }

                });
            }
        });

        btnDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag == true) {
                    edtTela1.setText(edtTela2.getText() + getString(R.string.btnDiv));
                    if (edtTela2 != null) {
                        edtTela2.setText(null);
                    }
                    flag = false;
                } else {
                    edtTela1.setText(edtTela1.getText() + getString(R.string.btnDiv));
                }
            }
        });

        btnX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag == true) {
                    edtTela1.setText(edtTela2.getText() + getString(R.string.btnX));
                    if (edtTela2 != null) {
                        edtTela2.setText(null);
                    }
                    flag = false;
                } else {
                    edtTela1.setText(edtTela1.getText() + getString(R.string.btnX));
                }
            }
        });

        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag == true) {
                    edtTela1.setText(edtTela2.getText() + "-");
                    if (edtTela2 != null) {
                        edtTela2.setText(null);
                    }
                    flag = false;
                } else {
                    edtTela1.setText(edtTela1.getText() + "-");
                }
            }
        });

        btnSum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag == true) {
                    edtTela1.setText(edtTela2.getText() + "+");
                    if (edtTela2 != null) {
                        edtTela2.setText(null);
                    }
                    flag = false;
                } else {
                    edtTela1.setText(edtTela1.getText() + "+");
                }
            }
        });

        btnI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                flag = true;

                String edt = String.valueOf(edtTela1.getText()).replace(getString(R.string.P), ".").replace(getString(R.string.btnDiv), "/")
                        .replace(getString(R.string.btnX), "*");

                Symbols symbols = new Symbols();

                try {
                    edtTela2.setText(null);
                    Double result = symbols.eval(String.valueOf(edt));
                    DecimalFormat df = new DecimalFormat("##.########");
                    String resultF = String.format(String.valueOf(df.format(result))).replace(".", getString(R.string.P)).
                            replace("/", getString(R.string.btnDiv).replace("*", getString(R.string.btnX)));
                    edtTela2.setText(String.valueOf(resultF));
                } catch (SyntaxException e) {
                    edtTela2.setText(getString(R.string.error));
                    edtTela1.setText(null);
                }

            }

        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fabPlus);
        fab.hide();
    }

}//fim da classe
