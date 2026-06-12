package br.pucminas.hospedagem.model;

import br.pucminas.hospedagem.exception.CapacidadeExcedidaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes do Quarto Familia")
class QuartoFamiliaTest {

    // 1 cama solteiro + 1 cama casal + 1 queen/king = 1 + 2 + 2 = 5 hospedes max
    private QuartoFamilia quarto;

    @BeforeEach
    void setUp() {
        quarto = new QuartoFamilia(300.0, true, true, 1, 1, 1, 2);
    }

    @Test
    @DisplayName("Capacidade maxima deve considerar todas as camas")
    void testCapacidadeMaxima() {
        // 1 solteiro + 1 casal (2) + 1 queen/king (2) = 5
        assertEquals(5, quarto.getCapacidadeMaxima());
    }

    @Test
    @DisplayName("Deve calcular diaria para 1 hospede sem desconto")
    void testCalcularDiariaUmHospede() {
        // base = 300 + 50 (ar) + 80 (hidro) = 430
        // com hospedes = 430 * (1 + 0.05 * 1) = 430 * 1.05 = 451.5
        // desconto 0% (1 hospede)
        double valor = quarto.calcularValorDiaria(1);
        assertEquals(451.5, valor, 0.01);
    }

    @Test
    @DisplayName("Deve aplicar 5% de desconto para 3 hospedes")
    void testCalcularDiariaTresHospedesComDesconto() {
        // base = 430; com hospedes = 430 * (1 + 0.05 * 3) = 430 * 1.15 = 494.5
        // desconto 5% -> 494.5 * 0.95 = 469.775
        double valor = quarto.calcularValorDiaria(3);
        assertEquals(469.775, valor, 0.01);
    }

    @Test
    @DisplayName("Deve aplicar 10% de desconto para 5 hospedes")
    void testCalcularDiariaCincoHospedesComDesconto() {
        // base = 430; com hospedes = 430 * (1 + 0.05 * 5) = 430 * 1.25 = 537.5
        // desconto 10% -> 537.5 * 0.90 = 483.75
        double valor = quarto.calcularValorDiaria(5);
        assertEquals(483.75, valor, 0.01);
    }

    @Test
    @DisplayName("Deve aplicar 15% de desconto para 7 ou mais hospedes")
    void testCalcularDiariaSeteMaisHospedesComDesconto() {
        QuartoFamilia quartoGrande = new QuartoFamilia(300.0, true, true, 3, 2, 1, 3);
        // capacidade = 3 + 4 + 2 = 9
        // base = 430; com hospedes = 430 * (1 + 0.05 * 7) = 430 * 1.35 = 580.5
        // desconto 15% -> 580.5 * 0.85 = 493.425
        double valor = quartoGrande.calcularValorDiaria(7);
        assertEquals(493.425, valor, 0.01);
    }

    @Test
    @DisplayName("Desconto progressivo torna familia mais vantajoso que quartos separados")
    void testDescontoGrupoMaisVantajosoQueQuartosSeparados() {
        // 5 hospedes no quarto familia
        double valorFamilia = quarto.calcularValorDiaria(5);

        // 5 quartos individuais equivalentes (base 300, sem ar/hidro)
        QuartoIndividual individual = new QuartoIndividual(300.0, true, true);
        double valorCincoIndividuais = individual.calcularValorDiaria(1) * 5;

        assertTrue(valorFamilia < valorCincoIndividuais,
                "Quarto familia deve ser mais barato que 5 quartos individuais para grupo de 5");
    }

    @Test
    @DisplayName("Deve validar capacidade corretamente")
    void testValidarCapacidade() {
        assertDoesNotThrow(() -> quarto.validarCapacidade(5));
        assertThrows(CapacidadeExcedidaException.class, () -> quarto.validarCapacidade(6));
    }

    @Test
    @DisplayName("Deve registrar quantidade de ambientes")
    void testQtdAmbientes() {
        assertEquals(2, quarto.getQtdAmbientes());
        quarto.setQtdAmbientes(3);
        assertEquals(3, quarto.getQtdAmbientes());
    }

    @Test
    @DisplayName("Deve calcular diaria sem ar e sem hidro corretamente")
    void testCalcularDiariaSemComodidades() {
        QuartoFamilia quartoSimples = new QuartoFamilia(300.0, false, false, 2, 1, 0, 1);
        // base = 300; com hospedes = 300 * (1 + 0.05 * 2) = 300 * 1.10 = 330
        // desconto 0% (2 hospedes)
        double valor = quartoSimples.calcularValorDiaria(2);
        assertEquals(330.0, valor, 0.01);
    }
}
