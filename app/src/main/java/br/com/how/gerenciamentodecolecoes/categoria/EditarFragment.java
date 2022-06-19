package br.com.how.gerenciamentodecolecoes.categoria;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.how.gerenciamentodecolecoes.R;
import br.com.how.gerenciamentodecolecoes.database.DatabaseHelper;

public class EditarFragment extends Fragment {

    EditText etNome;
    DatabaseHelper databaseHelper;
    Categoria c;

    public EditarFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.categoria_fragment_editar, container, false);

        Bundle b = getArguments();
        int id_categoria = b.getInt("id");
        databaseHelper = new DatabaseHelper(getActivity());
        c = databaseHelper.getByIdCategoria(id_categoria);

        etNome = v.findViewById(R.id.editTextNomeCategoria);

        etNome.setText(c.getNome());

        Button btnEditar = v.findViewById(R.id.buttonEditarCategoria);

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editar(id_categoria);
            }
        });

        Button btnExcluir = v.findViewById(R.id.buttonExcluirCategoria);

        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(R.string.dialog_excluir_categoria);
                builder.setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        excluir(id_categoria);
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
        if (etNome.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o nome!", Toast.LENGTH_LONG).show();
        } else {
            c = new Categoria();
            c.setId(id);
            c.setNome(etNome.getText().toString());
            databaseHelper.updateCategoria(c);
            Toast.makeText(getActivity(), "Categoria editada!", Toast.LENGTH_LONG).show();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameCategoria, new ListarFragment()).commit();
        }
    }

    private void excluir (int id) {
        c = new Categoria();
        c.setId(id);
        databaseHelper.deleteCategoria(c);
        Toast.makeText(getActivity(), "Categoria excluída!", Toast.LENGTH_LONG).show();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameCategoria, new ListarFragment()).commit();
    }
}