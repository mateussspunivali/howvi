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
public class ItemColecaoTest {
    @Rule
    public ActivityScenarioRule<MainActivity> mActivityRule = new ActivityScenarioRule(MainActivity.class);

    @Test
    public void A_validateAddNewItemColecao() {
        //Início da criação de uma categoria para os teste de item de coleção
        openActionBarOverflowOrOptionsMenu((getInstrumentation().getTargetContext()));

        onView(withText("Coleção"))
                .perform(click());

        onView(withId(R.id.buttonAdicionar))
                .perform(click());

        onData(anything()).inAdapterView(withId(R.id.spinnerCategoriaColecao))
                .atPosition(0)
                .perform(click());

        onData(allOf(is(instanceOf(String.class)), is("Carros"))).perform(click());

        onView(withId(R.id.editTextNomeColecao))
                .perform(replaceText("Teste para itens coleção"));

        onView(withId(R.id.editTextDescricaoColecao))
                .perform(replaceText("Descrição teste"));

        onView(withId(R.id.editTextDataInicioColecao))
                .perform(replaceText("01/09/2010"));

        onView(withId(R.id.checkBoxCompletaColecao))
                .perform(click());

        onView(withId(R.id.buttonAdicionarColecao))
                .perform(click());
        //Fim da criação de uma categoria para os teste de item de coleção

        //Abre o menu
        openActionBarOverflowOrOptionsMenu((getInstrumentation().getTargetContext()));

        //Seleciona a opção Itens da Coleção
        onView(withText("Itens da Coleção"))
                .perform(click());

        //Seleciona o botão adicionar
        onView(withId(R.id.buttonAdicionar))
                .perform(click());

        //Selecione o spinner de coleção
        onData(anything()).inAdapterView(withId(R.id.spinnerColecaoItensColecao))
                .atPosition(0)
                .perform(click());

        //Selecione a coleção Teste para itens coleção
        onData(allOf(is(instanceOf(String.class)), is("Teste para itens coleção"))).perform(click());

        //Preenche o campo do nome de item de coleção
        onView(withId(R.id.editTextNomeItensColecao))
                .perform(replaceText("Teste de item de coleção"));

        //Preenche o campo do descrição de item de coleção
        onView(withId(R.id.editTextDescricaoItensColecao))
                .perform(replaceText("Descrição teste"));

        //Seleciona o botão de adicionar item de coleção
        onView(withId(R.id.buttonAdicionarItensColecao))
                .perform(click());

        //Valida se o item de coleção foi adicionado
        onView(withText("Teste de item de coleção"))
                .check(matches(isDisplayed()));

    }

    @Test
    public void B_validateEditItemColecao() {
        //Abre o menu
        openActionBarOverflowOrOptionsMenu((getInstrumentation().getTargetContext()));

        //Seleciona a opção Itens da Coleção
        onView(withText("Itens da Coleção"))
                .perform(click());

        //Seleciona o item coleção
        onView(withText("Teste de item de coleção"))
                .perform(click());

        //Preenche o campo do nome de item de coleção
        onView(withId(R.id.editTextNomeItensColecao))
                .perform(replaceText("Item de Coleção Teste"));

        //Preenche o campo do descrição de item de coleção
        onView(withId(R.id.editTextDescricaoItensColecao))
                .perform(replaceText("Teste de Descrição"));

        //Seleciona o botão de editar item de coleção
        onView(withId(R.id.buttonEditarItensColecao))
                .perform(click());

        //Valida se o item de coleção foi alterado
        onView(withText("Item de Coleção Teste"))
                .check(matches(isDisplayed()));
    }

    @Test
    public void C_validateDeleteItemColecao() {
        //Abre o menu
        openActionBarOverflowOrOptionsMenu((getInstrumentation().getTargetContext()));

        //Seleciona a opção Itens da Coleção
        onView(withText("Itens da Coleção"))
                .perform(click());

        //Seleciona o item coleção
        onView(withText("Item de Coleção Teste"))
                .perform(click());

        //Seleciona o botão de excluir item de coleção
        onView(withId(R.id.buttonExcluirItensColecao))
                .perform(click());

        //Seleciona a opção sim
        onView(withText("Sim"))
                .perform(click());

        //Valida se voltou para a tela de listagem de item de coleção
        onView(withId(R.id.textViewListarItensColecao))
                .check(matches(isDisplayed()));

        //Início da remoção de uma categoria para os teste de item de coleção
        openActionBarOverflowOrOptionsMenu((getInstrumentation().getTargetContext()));

        onView(withText("Coleção"))
                .perform(click());

        onView(withText("Teste para itens coleção"))
                .perform(click());

        onView(withId(R.id.buttonExcluirColecao))
                .perform(click());

        onView(withText("Sim"))
                .perform(click());
        //Fim da remoção de uma categoria para os teste de item de coleção
    }

    @Test
    public void D_validateListItensColecaoButton() {
        //Abre o menu
        openActionBarOverflowOrOptionsMenu((getInstrumentation().getTargetContext()));

        //Seleciona a opção Itens da Coleção
        onView(withText("Itens da Coleção"))
                .perform(click());

        //Seleciona o botão adicionar
        onView(withId(R.id.buttonAdicionar))
                .perform(click());

        //Seleciona o botão listar
        onView(withId(R.id.buttonListar))
                .perform(click());

        //Valida se voltou para a tela de listagem de item de coleção
        onView(withId(R.id.textViewListarItensColecao))
                .check(matches(isDisplayed()));
    }
}
