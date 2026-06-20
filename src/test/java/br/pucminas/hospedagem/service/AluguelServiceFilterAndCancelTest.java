package br.pucminas.hospedagem.service;

import br.pucminas.hospedagem.exception.DataInvalidaException;
import br.pucminas.hospedagem.exception.QuartoIndisponivelException;
import br.pucminas.hospedagem.model.Aluguel;
import br.pucminas.hospedagem.model.Cliente;
import br.pucminas.hospedagem.model.Quarto;
import br.pucminas.hospedagem.model.QuartoCasal;
import br.pucminas.hospedagem.model.QuartoIndividual;
import br.pucminas.hospedagem.model.StatusAluguel;
import br.pucminas.hospedagem.repository.AluguelRepository;
import br.pucminas.hospedagem.repository.ClienteRepository;
import br.pucminas.hospedagem.repository.QuartoRepository;
import br.pucminas.hospedagem.repository.ResidenciaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes de filtro e cancelamento de aluguel")
class AluguelServiceFilterAndCancelTest {

    @Mock
    private AluguelRepository repository;

    @Mock
    private QuartoRepository quartoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ResidenciaRepository residenciaRepository;

    @InjectMocks
    private AluguelService service;

    private QuartoCasal quartoCasal;
    private Cliente cliente;
    private Aluguel aluguel;

    @BeforeEach
    void setUp() {
        quartoCasal = new QuartoCasal(200.0, true, true, false);
        cliente = new Cliente("Ana", "111.222.333-44", "Rua X", "31999999999", "ana@email.com");
        aluguel = new Aluguel(LocalDateTime.now().plusDays(2), LocalDateTime.now().plusDays(5), 2,
                StatusAluguel.ATIVO, quartoCasal, cliente);
    }

    @Test
    @DisplayName("Deve filtrar alugueis por tipo de quarto")
    void testFiltrarPorTipoQuarto() {
        when(repository.findByQuartoTipoQuarto("CASAL")).thenReturn(List.of(aluguel));

        List<Aluguel> resultado = service.filtrarPorTipoQuarto("CASAL");

        assertEquals(1, resultado.size());
        assertEquals(aluguel, resultado.get(0));
    }

    @Test
    @DisplayName("Deve cancelar aluguel existente")
    void testCancelarAluguel() {
        when(repository.findById(1L)).thenReturn(Optional.of(aluguel));
        when(repository.save(aluguel)).thenReturn(aluguel);

        Optional<Aluguel> resultado = service.cancelarAluguel(1L);

        assertTrue(resultado.isPresent());
        assertEquals(StatusAluguel.CANCELADO, resultado.get().getStatus());
    }

    @Test
    @DisplayName("Deve listar histórico de aluguéis por cliente")
    void testListarPorCliente() {
        when(repository.findByClienteId(1L)).thenReturn(List.of(aluguel));

        List<Aluguel> historico = service.listarPorCliente(1L);

        assertEquals(1, historico.size());
        assertEquals(aluguel, historico.get(0));
    }

    @Test
    @DisplayName("Deve lançar DataInvalidaException quando a data de entrada for após a data de saída")
    void testSalvarAluguelDataInvalida() {
        Aluguel aluguelDataInvalida = new Aluguel(
                LocalDateTime.now().plusDays(5),
                LocalDateTime.now().plusDays(2),
                2,
                StatusAluguel.ATIVO,
                quartoCasal,
                cliente
        );

        assertThrows(DataInvalidaException.class, () -> service.salvar(aluguelDataInvalida),
                "Deve lançar DataInvalidaException quando data de entrada for após data de saída");
    }

    @Test
    @DisplayName("Deve lançar QuartoIndisponivelException quando o quarto não estiver disponível")
    void testSalvarAluguelQuartoIndisponivel() {
        Quarto quartoIndisponivel = new QuartoIndividual(100.0, true, true) {
            @Override
            public boolean estaDisponivel(LocalDateTime inicio, LocalDateTime fim) {
                return false;
            }

            @Override
            public double calcularValorDiaria(int qtdHospedes) {
                return 0;
            }

            @Override
            public int getCapacidadeMaxima() {
                return 1;
            }
        };

        Aluguel aluguelIndisponivel = new Aluguel(
                LocalDateTime.now().plusDays(2),
                LocalDateTime.now().plusDays(5),
                1,
                StatusAluguel.ATIVO,
                quartoIndisponivel,
                cliente
        );

        assertThrows(QuartoIndisponivelException.class, () -> service.salvar(aluguelIndisponivel),
                "Deve lançar QuartoIndisponivelException quando o quarto não estiver disponível");
    }
}
