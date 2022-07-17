package br.com.how.gerenciamentodecolecoes;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CategoriaTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityRule = new ActivityScenarioRule(MainActivity.class);

    @Test
    public void A_validateAddNewCategoria() {
        //Seleciona o botão adicionar
        onView(withId(R.id.buttonAdicionar))
                .perform(click());

        //Preenche o campo do nome de categoria
        onView(withId(R.id.editTextNomeCategoria))
                .perform(replaceText("Categoria de teste"));

        //Seleciona o botão de adicionar categoria
        onView(withId(R.id.buttonAdicionarCategoria))
                .perform(click());

        //Valida se a categoria foi adicionada
        onView(withText("Categoria de teste"))
                .check(matches(isDisplayed()));
    }

    @Test
    public void B_validateEditCategoria() {
        //Seleciona a categoria
        onView(withText("Categoria de teste"))
                .perform(click());

        //Preenche o campo do nome de categoria
        onView(withId(R.id.editTextNomeCategoria))
                .perform(replaceText("Teste de categoria"));

        //Seleciona o botão de editar categoria
        onView(withId(R.id.buttonEditarCategoria))
                .perform(click());

        //Valida se a categoria foi alterada
        onView(withText("Teste de categoria"))
                .check(matches(isDisplayed()));
    }

    @Test
    public void C_validateDeleteCategoria() {
        //Seleciona a categoria
        onView(withText("Teste de categoria"))
                .perform(click());

        //Seleciona o botão de excluir categoria
        onView(withId(R.id.buttonExcluirCategoria))
                .perform(click());

        //Seleciona a opção sim
        onView(withText("Sim"))
                .perform(click());

        //Valida se voltou para a tela de listagem de categoria
        onView(withId(R.id.textViewListarCategoria))
                .check(matches(isDisplayed()));
    }

    @Test
    public void D_validateListCategoriaButton() {
        //Seleciona o botão adicionar
        onView(withId(R.id.buttonAdicionar))
                .perform(click());

        //Seleciona o botão listar
        onView(withId(R.id.buttonListar))
                .perform(click());

        //Valida se voltou para a tela de listagem de categoria
        onView(withId(R.id.textViewListarCategoria))
                .check(matches(isDisplayed()));
    }

    @Test
    public void E_validateCategoriaMenuItem() {
        //Abre o menu
        openActionBarOverflowOrOptionsMenu((getInstrumentation().getTargetContext()));

        //Seleciona a opção Coleção
        onView(withText("Coleção"))
                .perform(click());

        //Abre o menu
        openActionBarOverflowOrOptionsMenu((getInstrumentation().getTargetContext()));

        //Seleciona a opção Categoria
        onView(withText("Categoria"))
                .perform(click());

        //Valida se voltou para a tela de listagem de categoria
        onView(withId(R.id.textViewGerenciarCategorias))
                .check(matches(isDisplayed()));
    }
}
