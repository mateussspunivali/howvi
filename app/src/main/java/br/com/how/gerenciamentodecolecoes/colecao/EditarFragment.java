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
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.colecao_fragment_editar, container, false);
        Bundle bundle = getArguments();
        int id_colecao = bundle.getInt("id");
        databaseHelper = new DatabaseHelper(getActivity());
        c = databaseHelper.getByIdColecao(id_colecao);

        spCategoria = v.findViewById(R.id.spinnerCategoriaColecao);
        etNome = v.findViewById(R.id.editTextNomeColecao);
        etDescricao = v.findViewById(R.id.editTextDescricaoColecao);
        etDataInicio = v.findViewById(R.id.editTextDataInicioColecao);
        cbCompleta = v.findViewById(R.id.checkBoxCompletaColecao);

        listCategoriaId = new ArrayList<>();
        listCategoriaName = new ArrayList<>();
        databaseHelper.getAllNameCategoria(listCategoriaId, listCategoriaName);

        ArrayAdapter<String> spCategoriaArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, listCategoriaName);
        spCategoria.setAdapter(spCategoriaArrayAdapter);

        etNome.setText(c.getNome());
        etDescricao.setText(c.getDescricao());
        etDataInicio.setText(c.getData_inicio());
        cbCompleta.setChecked(c.getCompleta());
        spCategoria.setSelection(listCategoriaId.indexOf(c.getId_categoria()));

        Button btnEditar = v.findViewById(R.id.buttonEditarColecao);

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editar(id_colecao);
            }
        });

        Button btnExcluir = v.findViewById(R.id.buttonExcluirColecao);

        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(R.string.dialog_excluir_colecao);
                builder.setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        excluir(id_colecao);
                    }
                });
                builder.setNegativeButton(R.string.nao, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Não faz nada
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        return v;
    }

    private void editar (int id) {
        if (spCategoria.getSelectedItem() == null) {
            Toast.makeText(getActivity(), "Por favor, selecione a categoria!", Toast.LENGTH_LONG).show();
        } else if (etNome.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o nome!", Toast.LENGTH_LONG).show();
        } else if (etDescricao.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe a descrição!", Toast.LENGTH_LONG).show();
        } else if (etDataInicio.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe a data de início!", Toast.LENGTH_LONG).show();
        } else {
            Colecao c = new Colecao();
            c.setId(id);
            String nomeCategoria = spCategoria.getSelectedItem().toString();
            c.setId_categoria(listCategoriaId.get(listCategoriaName.indexOf(nomeCategoria)));
            c.setNome(etNome.getText().toString());
            c.setDescricao(etDescricao.getText().toString());
            c.setData_inicio(etDataInicio.getText().toString());
            c.setCompleta(cbCompleta.isChecked());
            databaseHelper.updateColecao(c);
            Toast.makeText(getActivity(), "Coleção editado!", Toast.LENGTH_LONG).show();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameColecao, new ListarFragment()).commit();
        }
    }

    private void excluir (int id) {
        c = new Colecao();
        c.setId(id);
        databaseHelper.deleteColecao(c);
        Toast.makeText(getActivity(), "Coleção excluída!", Toast.LENGTH_LONG).show();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameColecao, new ListarFragment()).commit();
    }
}