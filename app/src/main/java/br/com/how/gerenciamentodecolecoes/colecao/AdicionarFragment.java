package br.com.how.gerenciamentodecolecoes.colecao;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.how.gerenciamentodecolecoes.R;
import br.com.how.gerenciamentodecolecoes.database.DatabaseHelper;

public class AdicionarFragment extends Fragment {

    EditText etNome;
    EditText etDescricao;
    EditText etDataInicio;
    CheckBox cbCompleta;
    Spinner spCategoria;
    ArrayList<Integer> listCategoriaId;
    ArrayList<String> listCategoriaName;
    DatabaseHelper databaseHelper;

    public AdicionarFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.colecao_fragment_adicionar, container, false);

        //Busca os campos do formulário
        spCategoria = v.findViewById(R.id.spinnerCategoriaColecao);
        etNome = v.findViewById(R.id.editTextNomeColecao);
        etDescricao = v.findViewById(R.id.editTextDescricaoColecao);
        etDataInicio = v.findViewById(R.id.editTextDataInicioColecao);
        cbCompleta = v.findViewById(R.id.checkBoxCompletaColecao);

        databaseHelper = new DatabaseHelper(getActivity());

        //Busca os nomes de todas as categorias
        listCategoriaId = new ArrayList<>();
        listCategoriaName = new ArrayList<>();
        databaseHelper.getAllNameCategoria(listCategoriaId, listCategoriaName);

        //Preenche o spinner de categorias com os valores encontrados do banco de dados
        ArrayAdapter<String> spCategoriaArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, listCategoriaName);
        spCategoria.setAdapter(spCategoriaArrayAdapter);

        //Busca o button adicionar
        Button btnAdicionar = v.findViewById(R.id.buttonAdicionarColecao);

        //Sobscreve o listner de selecionar o botão para chamar o método adicionar da própria classe
        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionar();
            }
        });

        return v;
    }

    private void adicionar () {
        //Valida se os campos do fomulário não foram enviados vázios quando selecionado o botão adicionar
        if (spCategoria.getSelectedItem() == null) {
            Toast.makeText(getActivity(), "Por favor, selecione a categoria!", Toast.LENGTH_LONG).show();
        } else if (etNome.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o nome!", Toast.LENGTH_LONG).show();
        } else if (etDescricao.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe a descrição!", Toast.LENGTH_LONG).show();
        } else if (etDataInicio.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe a data de início!", Toast.LENGTH_LONG).show();
        } else {
            //Instância uma nova coleção preenche com os campos do formulário
            Colecao c = new Colecao();
            String nomeCategoria = spCategoria.getSelectedItem().toString();
            c.setId_categoria(listCategoriaId.get(listCategoriaName.indexOf(nomeCategoria)));
            c.setNome(etNome.getText().toString());
            c.setDescricao(etDescricao.getText().toString());
            c.setData_inicio(etDataInicio.getText().toString());
            c.setCompleta(cbCompleta.isChecked());
            //Salva a coleção no banco de dados
            databaseHelper.createColecao(c);
            Toast.makeText(getActivity(), "Coleção salva!", Toast.LENGTH_LONG).show();
            //Muda a tela para a listagem de coleção
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameColecao, new ListarFragment()).commit();
        }
    }
}