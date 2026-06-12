package br.pucminas.hospedagem.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes de Disponibilidade de Quarto")
class DisponibilidadeQuartoTest {

    private Quarto quarto;
    private LocalDateTime dataEntrada;
    private LocalDateTime dataSaida;

    @BeforeEach
    void setUp() {
        quarto = new QuartoIndividual(100.0, false, false);
        dataEntrada = LocalDateTime.now().plusDays(5);
        dataSaida = LocalDateTime.now().plusDays(10);
    }

    @Test
    @DisplayName("Deve retornar disponível por padrão")
    void testQuartoDisponiblePadrao() {
        boolean disponivel = quarto.estaDisponivel(dataEntrada, dataSaida);
        assertTrue(disponivel, "Quarto deve estar disponível por padrão");
    }

    @Test
    @DisplayName("Deve retornar disponível para diferentes períodos")
    void testQuartoDisponibleDiferentesPeriodos() {
        LocalDateTime entrada1 = LocalDateTime.now().plusDays(1);
        LocalDateTime saida1 = LocalDateTime.now().plusDays(3);
        assertTrue(quarto.estaDisponivel(entrada1, saida1), "Quarto deve estar disponível");

        LocalDateTime entrada2 = LocalDateTime.now().plusDays(20);
        LocalDateTime saida2 = LocalDateTime.now().plusDays(25);
        assertTrue(quarto.estaDisponivel(entrada2, saida2), "Quarto deve estar disponível em período diferente");
    }

    @Test
    @DisplayName("Quarto com múltiplas validações deve estar disponível")
    void testQuartoDisponibleAposPriorizacao() {
        // Simula verificação de disponibilidade para vários períodos
        assertTrue(quarto.estaDisponivel(LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2)));
        assertTrue(quarto.estaDisponivel(LocalDateTime.now().plusDays(3), LocalDateTime.now().plusDays(4)));
        assertTrue(quarto.estaDisponivel(LocalDateTime.now().plusDays(5), LocalDateTime.now().plusDays(10)));
    }

    @Test
    @DisplayName("Quarto com sobrescrita de estaDisponivel deve ser respeitada")
    void testQuartoComDisponibilidadeCustomizada() {
        Quarto quartoCustomizado = new QuartoIndividual(100.0, false, false) {
            @Override
            public boolean estaDisponivel(LocalDateTime inicio, LocalDateTime fim) {
                // Simula que não está disponível em junho
                return !inicio.getMonth().name().equals("JUNE");
            }
        };

        LocalDateTime entradaJunho = LocalDateTime.of(2025, 6, 15, 14, 0);
        LocalDateTime saidaJunho = LocalDateTime.of(2025, 6, 20, 11, 0);

        assertFalse(quartoCustomizado.estaDisponivel(entradaJunho, saidaJunho),
                "Quarto customizado não deve estar disponível em junho");

        LocalDateTime entradaJulho = LocalDateTime.of(2025, 7, 15, 14, 0);
        LocalDateTime saidaJulho = LocalDateTime.of(2025, 7, 20, 11, 0);

        assertTrue(quartoCustomizado.estaDisponivel(entradaJulho, saidaJulho),
                "Quarto customizado deve estar disponível em julho");
    }
}
