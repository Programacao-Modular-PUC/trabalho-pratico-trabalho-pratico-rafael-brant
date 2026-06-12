package br.pucminas.hospedagem.model;

import br.pucminas.hospedagem.exception.CapacidadeExcedidaException;
import br.pucminas.hospedagem.exception.RecursoNaoPermitidoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes do Quarto Individual")
class QuartoIndividualTest {

    private QuartoIndividual quarto;

    @BeforeEach
    void setUp() {
        quarto = new QuartoIndividual(100.0, true, false);
    }

    @Test
    @DisplayName("Deve calcular diária corretamente com ar")
    void testCalcularValorDiariaComAr() {
        double valor = quarto.calcularValorDiaria(1);
        // Valor base (100) + ar (50) + sem hidro (0) = 150
        assertEquals(150.0, valor, "Valor deve ser 150 (100 + 50 de ar)");
    }

    @Test
    @DisplayName("Deve calcular diária corretamente com ar e hidro")
    void testCalcularValorDiariaComArEHidro() {
        QuartoIndividual quartoComHidro = new QuartoIndividual(100.0, true, true);
        double valor = quartoComHidro.calcularValorDiaria(1);
        // Valor base (100) + ar (50) + hidro (80) = 230
        assertEquals(230.0, valor, "Valor deve ser 230 (100 + 50 + 80)");
    }

    @Test
    @DisplayName("Deve calcular diária sem ar nem hidro")
    void testCalcularValorDiariaSemArEHidro() {
        QuartoIndividual quartoSimples = new QuartoIndividual(100.0, false, false);
        double valor = quartoSimples.calcularValorDiaria(1);
        // Valor base (100) apenas
        assertEquals(100.0, valor, "Valor deve ser 100");
    }

    @Test
    @DisplayName("Deve ter capacidade máxima de 1 hóspede")
    void testCapacidadeMaxima() {
        assertEquals(1, quarto.getCapacidadeMaxima(), "Quarto individual deve ter capacidade máxima de 1");
    }

    @Test
    @DisplayName("Deve validar 1 hóspede sem erro")
    void testValidarCapacidadeUmHospede() {
        assertDoesNotThrow(() -> quarto.validarCapacidade(1), "Deve aceitar 1 hóspede");
    }

    @Test
    @DisplayName("Deve lançar exceção ao exceder capacidade com 2 hóspedes")
    void testValidarCapacidadeExcedidaComDoisHospedes() {
        assertThrows(CapacidadeExcedidaException.class, () -> quarto.validarCapacidade(2),
                "Deve lançar CapacidadeExcedidaException para 2 hóspedes");
    }

    @Test
    @DisplayName("Deve lançar exceção com quantidade zero de hóspedes")
    void testValidarCapacidadeZeroHospedes() {
        assertThrows(CapacidadeExcedidaException.class, () -> quarto.validarCapacidade(0),
                "Deve lançar CapacidadeExcedidaException para 0 hóspedes");
    }

    @Test
    @DisplayName("Deve lançar exceção com quantidade negativa de hóspedes")
    void testValidarCapacidadeNegativa() {
        assertThrows(CapacidadeExcedidaException.class, () -> quarto.validarCapacidade(-1),
                "Deve lançar CapacidadeExcedidaException para quantidade negativa");
    }

    @Test
    @DisplayName("Deve lançar exceção ao solicitar berço em quarto individual")
    void testValidarBercoQuartoIndividual() {
        assertThrows(RecursoNaoPermitidoException.class, () -> quarto.validarBerco(),
                "Quarto individual não deve permitir berço");
    }
}
