package br.com.how.gerenciamentodecolecoes.categoria;

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

public class AdicionarFragment extends Fragment {

    EditText etNome;

    public AdicionarFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.categoria_fragment_adicionar, container, false);

        etNome = v.findViewById(R.id.editTextNomeCategoria);

        Button btnAdicionar = v.findViewById(R.id.buttonAdicionarCategoria);

        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionar();
            }
        });

        return v;
    }

    private void adicionar () {
        if (etNome.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o nome!", Toast.LENGTH_LONG).show();
        } else {
            DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
            Categoria c = new Categoria();
            c.setNome(etNome.getText().toString());
            databaseHelper.createCategoria(c);
            Toast.makeText(getActivity(), "Categoria salva!", Toast.LENGTH_LONG).show();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameCategoria, new ListarFragment()).commit();
        }
    }
}