package br.com.how.gerenciamentodecolecoes.colecao;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class EditarFragment extends Fragment {

    EditText etNome;
    EditText etDescricao;
    EditText etDataInicio;
    CheckBox cbCompleta;
    Spinner spCategoria;
    ArrayList<Integer> listCategoriaId;
    ArrayList<String> listCategoriaName;
    DatabaseHelper databaseHelper;
    Colecao c;

    public EditarFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.colecao_fragment_editar, container, false);

        //Pega o id dos parâmetros
        Bundle bundle = getArguments();
        int id_colecao = bundle.getInt("id");
        databaseHelper = new DatabaseHelper(getActivity());
        //Busca a coleção no banco de dados pelo id
        c = databaseHelper.getByIdColecao(id_colecao);

        //Busca os campos do formulário
        spCategoria = v.findViewById(R.id.spinnerCategoriaColecao);
        etNome = v.findViewById(R.id.editTextNomeColecao);
        etDescricao = v.findViewById(R.id.editTextDescricaoColecao);
        etDataInicio = v.findViewById(R.id.editTextDataInicioColecao);
        cbCompleta = v.findViewById(R.id.checkBoxCompletaColecao);

        //Busca os nomes de todas as categorias
        listCategoriaId = new ArrayList<>();
        listCategoriaName = new ArrayList<>();
        databaseHelper.getAllNameCategoria(listCategoriaId, listCategoriaName);

        //Preenche o spinner de categorias com os valores encontrados do banco de dados
        ArrayAdapter<String> spCategoriaArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, listCategoriaName);
        spCategoria.setAdapter(spCategoriaArrayAdapter);

        //Preenche os campos do formulário
        etNome.setText(c.getNome());
        etDescricao.setText(c.getDescricao());
        etDataInicio.setText(c.getData_inicio());
        cbCompleta.setChecked(c.getCompleta());
        spCategoria.setSelection(listCategoriaId.indexOf(c.getId_categoria()));

        //Busca o botão editar
        Button btnEditar = v.findViewById(R.id.buttonEditarColecao);

        //Sobscreve o listner de selecionar o botão para chamar o método editar da própria classe
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editar(id_colecao);
            }
        });

        //Busca o botão excluir
        Button btnExcluir = v.findViewById(R.id.buttonExcluirColecao);

        //Sobscreve o listner de selecionar o botão para mostrar um alert dialog
        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(R.string.dialog_excluir_colecao);
                //Caso o botão "sim" for selecionar ele chama o método excluir da própria classe
                builder.setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        excluir(id_colecao);
                    }
                });
                //Caso o botão "não" for selecionado ele não faz nada
                builder.setNegativeButton(R.string.nao, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        return v;
    }

    private void editar (int id) {
        //Valida se os campos do fomulário não foram enviados vázios quando selecionado o botão editar
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
            c.setId(id);
            String nomeCategoria = spCategoria.getSelectedItem().toString();
            c.setId_categoria(listCategoriaId.get(listCategoriaName.indexOf(nomeCategoria)));
            c.setNome(etNome.getText().toString());
            c.setDescricao(etDescricao.getText().toString());
            c.setData_inicio(etDataInicio.getText().toString());
            c.setCompleta(cbCompleta.isChecked());
            //Atualiza a coleção no banco de dados
            databaseHelper.updateColecao(c);
            Toast.makeText(getActivity(), "Coleção editado!", Toast.LENGTH_LONG).show();
            //Muda a tela para a listagem de coleção
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameColecao, new ListarFragment()).commit();
        }
    }

    private void excluir (int id) {
        //Instância uma nova coleção preenche com o id
        c = new Colecao();
        c.setId(id);
        //Remove a coleção no banco de dados
        databaseHelper.deleteColecao(c);
        Toast.makeText(getActivity(), "Coleção excluída!", Toast.LENGTH_LONG).show();
        //Muda a tela para a listagem de coleção
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameColecao, new ListarFragment()).commit();
    }
}