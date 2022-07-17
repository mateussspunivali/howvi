package br.com.how.gerenciamentodecolecoes;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;


@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ColecaoTest {
    @Rule
    public ActivityScenarioRule<MainActivity> mActivityRule = new ActivityScenarioRule(MainActivity.class);

    @Test
    public void A_validateAddNewColecao() {
        //Abre o menu
        openActionBarOverflowOrOptionsMenu((getInstrumentation().getTargetContext()));

        //Seleciona a opção Coleção
        onView(withText("Coleção"))
                .perform(click());

        //Seleciona o botão adicionar
        onView(withId(R.id.buttonAdicionar))
                .perform(click());

        //Selecione o spinner de categoria
        onData(anything()).inAdapterView(withId(R.id.spinnerCategoriaColecao))
                .atPosition(0)
                .perform(click());

        //Selecione a categoria Carros
        onData(allOf(is(instanceOf(String.class)), is("Carros"))).perform(click());

        //Preenche o campo do nome de coleção
        onView(withId(R.id.editTextNomeColecao))
                .perform(replaceText("Coleção de teste"));

        //Preenche o campo do descrição de coleção
        onView(withId(R.id.editTextDescricaoColecao))
                .perform(replaceText("Descrição teste"));

        //Preenche o campo do data de inicio de coleção
        onView(withId(R.id.editTextDataInicioColecao))
                .perform(replaceText("01/09/2010"));

        //Seleciona o checkbox de coleção completa
        onView(withId(R.id.checkBoxCompletaColecao))
                .perform(click());

        //Seleciona o botão de adicionar coleção
        onView(withId(R.id.buttonAdicionarColecao))
                .perform(click());

        //Valida se a coleção foi adicionada
        onView(withText("Coleção de teste"))
                .check(matches(isDisplayed()));

    }

    @Test
    public void B_validateEditColecao() {
        //Abre o menu
        openActionBarOverflowOrOptionsMenu((getInstrumentation().getTargetContext()));

        //Seleciona a opção Coleção
        onView(withText("Coleção"))
                .perform(click());

        //Seleciona a coleção
        onView(withText("Coleção de teste"))
                .perform(click());

        //Preenche o campo do nome de coleção
        onView(withId(R.id.editTextNomeColecao))
                .perform(replaceText("Teste de Coleção"));

        //Preenche o campo do descrição de coleção
        onView(withId(R.id.editTextDescricaoColecao))
                .perform(replaceText("Teste de Descrição"));

        //Preenche o campo do data de inicio de coleção
        onView(withId(R.id.editTextDataInicioColecao))
                .perform(replaceText("09/01/2010"));

        //Seleciona o checkbox de coleção completa
        onView(withId(R.id.checkBoxCompletaColecao))
                .perform(click());

        //Seleciona o botão de edição coleção
        onView(withId(R.id.buttonEditarColecao))
                .perform(click());

        //Valida se a coleção foi alterada
        onView(withText("Teste de Coleção"))
                .check(matches(isDisplayed()));
    }

    @Test
    public void C_validateDeleteColecao() {
        //Abre o menu
        openActionBarOverflowOrOptionsMenu((getInstrumentation().getTargetContext()));

        //Seleciona a opção Coleção
        onView(withText("Coleção"))
                .perform(click());

        //Seleciona a coleção
        onView(withText("Teste de Coleção"))
                .perform(click());

        //Seleciona o botão de excluir coleção
        onView(withId(R.id.buttonExcluirColecao))
                .perform(click());

        //Seleciona a opção sim
        onView(withText("Sim"))
                .perform(click());

        //Valida se voltou para a tela de listagem de coleção
        onView(withId(R.id.textViewListarColecao))
                .check(matches(isDisplayed()));
    }

    @Test
    public void D_validateListColecaoButton() {
        //Abre o menu
        openActionBarOverflowOrOptionsMenu((getInstrumentation().getTargetContext()));

        //Seleciona a opção Coleção
        onView(withText("Coleção"))
                .perform(click());

        //Seleciona o botão adicionar
        onView(withId(R.id.buttonAdicionar))
                .perform(click());

        //Seleciona o botão listar
        onView(withId(R.id.buttonListar))
                .perform(click());

        //Valida se voltou para a tela de listagem de coleção
        onView(withId(R.id.textViewListarColecao))
                .check(matches(isDisplayed()));
    }
}
