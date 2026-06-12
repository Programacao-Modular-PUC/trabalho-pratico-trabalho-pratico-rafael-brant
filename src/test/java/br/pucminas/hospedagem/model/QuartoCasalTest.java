package br.pucminas.hospedagem.model;

import br.pucminas.hospedagem.exception.CapacidadeExcedidaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes do Quarto de Casal")
class QuartoCasalTest {

    private QuartoCasal quarto;

    @BeforeEach
    void setUp() {
        quarto = new QuartoCasal(200.0, true, true, false);
    }

    @Test
    @DisplayName("Deve calcular diária de casal corretamente")
    void testCalcularValorDiariaQuartoCasal() {
        double valor = quarto.calcularValorDiaria(2);
        // Valor base (200) + ar (50) + hidro (80) = 330
        assertEquals(330.0, valor, "Valor deve ser 330 para 2 hóspedes");
    }

    @Test
    @DisplayName("Deve ter capacidade máxima de 2 sem berço")
    void testCapacidadeMaximaSemBerco() {
        QuartoCasal quartoSemBerco = new QuartoCasal(200.0, false, false);
        assertEquals(2, quartoSemBerco.getCapacidadeMaxima(), "Capacidade sem berço deve ser 2");
    }

    @Test
    @DisplayName("Deve ter capacidade máxima de 3 com berço")
    void testCapacidadeMaximaComBerco() {
        QuartoCasal quartoComBerco = new QuartoCasal(200.0, false, false, true);
        assertEquals(3, quartoComBerco.getCapacidadeMaxima(), "Capacidade com berço deve ser 3");
    }

    @Test
    @DisplayName("Deve validar 2 hóspedes sem erro")
    void testValidarCapacidadeDoisHospedes() {
        assertDoesNotThrow(() -> quarto.validarCapacidade(2), "Deve aceitar 2 hóspedes sem berço");
    }

    @Test
    @DisplayName("Deve lançar exceção ao exceder capacidade com 3 hóspedes sem berço")
    void testValidarCapacidadeExcedidaSemBerco() {
        QuartoCasal quartoSemBerco = new QuartoCasal(200.0, false, false);
        assertThrows(CapacidadeExcedidaException.class, () -> quartoSemBerco.validarCapacidade(3),
                "Deve lançar exceção para 3 hóspedes sem berço");
    }

    @Test
    @DisplayName("Deve validar 3 hóspedes com berço sem erro")
    void testValidarCapacidadeTresHospedesComBerco() {
        QuartoCasal quartoComBerco = new QuartoCasal(200.0, false, false, true);
        assertDoesNotThrow(() -> quartoComBerco.validarCapacidade(3), "Deve aceitar 3 hóspedes com berço");
    }

    @Test
    @DisplayName("Deve lançar exceção ao exceder capacidade com 4 hóspedes com berço")
    void testValidarCapacidadeExcedidaComBerco() {
        QuartoCasal quartoComBerco = new QuartoCasal(200.0, false, false, true);
        assertThrows(CapacidadeExcedidaException.class, () -> quartoComBerco.validarCapacidade(4),
                "Deve lançar exceção para 4 hóspedes mesmo com berço");
    }

    @Test
    @DisplayName("Deve adicionar taxa extra de berço para 3 hóspedes")
    void testCalcularValorDiariaComTaxaBerco() {
        QuartoCasal quartoComBerco = new QuartoCasal(200.0, true, true, true);
        double valor = quartoComBerco.calcularValorDiaria(3);
        // Valor base (200) + ar (50) + hidro (80) + taxa berço (30) = 360
        assertEquals(360.0, valor, "Valor com berço deve incluir taxa de 30");
    }

    @Test
    @DisplayName("Não deve adicionar taxa de berço para 2 hóspedes")
    void testCalcularValorDiariaSemTaxaBerco() {
        QuartoCasal quartoComBerco = new QuartoCasal(200.0, true, true, true);
        double valor = quartoComBerco.calcularValorDiaria(2);
        // Valor base (200) + ar (50) + hidro (80) = 330 (sem taxa de berço)
        assertEquals(330.0, valor, "Valor para 2 hóspedes não deve incluir taxa de berço");
    }

    @Test
    @DisplayName("Deve permitir definir berço dinamicamente")
    void testDefinirBercoDinamicamente() {
        assertFalse(quarto.isTemBerco(), "Inicialmente sem berço");
        quarto.setTemBerco(true);
        assertTrue(quarto.isTemBerco(), "Deve ter berço após setTemBerco(true)");
        assertEquals(3, quarto.getCapacidadeMaxima(), "Capacidade deve ser 3 com berço");
    }
}
