package br.pucminas.hospedagem.service;

import br.pucminas.hospedagem.dto.AluguelRequest;
import br.pucminas.hospedagem.exception.QuartoIndisponivelException;
import br.pucminas.hospedagem.model.*;
import br.pucminas.hospedagem.repository.AluguelRepository;
import br.pucminas.hospedagem.repository.ClienteRepository;
import br.pucminas.hospedagem.repository.QuartoRepository;
import br.pucminas.hospedagem.repository.ResidenciaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AluguelService {

    private final AluguelRepository repository;
    private final QuartoRepository quartoRepository;
    private final ClienteRepository clienteRepository;
    private final ResidenciaRepository residenciaRepository;

    public AluguelService(AluguelRepository repository,
                          QuartoRepository quartoRepository,
                          ClienteRepository clienteRepository,
                          ResidenciaRepository residenciaRepository) {
        this.repository = repository;
        this.quartoRepository = quartoRepository;
        this.clienteRepository = clienteRepository;
        this.residenciaRepository = residenciaRepository;
    }

    public List<Aluguel> listarTodos() {
        return repository.findAll();
    }

    public List<Aluguel> filtrarPorTipoQuarto(String tipoQuarto) {
        return repository.findByQuartoTipoQuarto(tipoQuarto);
    }

    public List<Aluguel> listarPorCliente(Long clienteId) {
        return repository.findByClienteId(clienteId);
    }

    public Optional<Aluguel> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Aluguel criarAluguel(AluguelRequest request) {
        Quarto quarto = quartoRepository.findById(request.getQuartoId())
                .orElseThrow(() -> new IllegalArgumentException("Quarto não encontrado: " + request.getQuartoId()));

        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado: " + request.getClienteId()));

        Aluguel aluguel = new Aluguel();
        aluguel.setQuarto(quarto);
        aluguel.setCliente(cliente);
        aluguel.setDataHoraEntrada(request.getDataHoraEntrada());
        aluguel.setDataHoraSaida(request.getDataHoraSaida());
        aluguel.setQtdHospedes(request.getQtdHospedes());
        aluguel.setStatus(request.getStatus() != null ? request.getStatus() : StatusAluguel.ATIVO);

        if (request.getResidenciaId() != null) {
            residenciaRepository.findById(request.getResidenciaId())
                    .ifPresent(aluguel::setResidencia);
        }

        return salvar(aluguel);
    }

    public Aluguel salvar(Aluguel aluguel) {
        if (aluguel.getQuarto() == null) {
            throw new IllegalArgumentException("Quarto deve ser informado.");
        }
        if (aluguel.getCliente() == null) {
            throw new IllegalArgumentException("Cliente deve ser informado.");
        }

        aluguel.validarDatas();
        aluguel.getQuarto().validarCapacidade(aluguel.getQtdHospedes());

        // Verificação real de disponibilidade no banco (sobreposição de períodos)
        Long quartoId = aluguel.getQuarto().getId();
        if (quartoId != null) {
            List<Aluguel> conflitos = repository.findAlugueisConflitantes(
                    quartoId,
                    aluguel.getDataHoraEntrada(),
                    aluguel.getDataHoraSaida());
            if (!conflitos.isEmpty()) {
                throw new QuartoIndisponivelException(
                        "Quarto #" + quartoId + " já possui reserva ativa para o período: " +
                        aluguel.getDataHoraEntrada() + " até " + aluguel.getDataHoraSaida());
            }
        }

        // Fallback para sobrescrita via modelo (permite testes com mocks)
        if (!aluguel.getQuarto().estaDisponivel(aluguel.getDataHoraEntrada(), aluguel.getDataHoraSaida())) {
            throw new QuartoIndisponivelException(
                    "Quarto não está disponível para as datas solicitadas: " +
                    aluguel.getDataHoraEntrada() + " até " + aluguel.getDataHoraSaida());
        }

        aluguel.setQtdDiarias(aluguel.calcularDiarias());
        aluguel.setValorTotal(aluguel.calcularValorFinal());

        // Gera pagamento associado ao aluguel
        Pagamento pagamento = new Pagamento(aluguel.getValorTotal(), LocalDateTime.now(), false);
        aluguel.setPagamento(pagamento);

        return repository.save(aluguel);
    }

    public Optional<Aluguel> cancelarAluguel(Long id) {
        return repository.findById(id).map(aluguel -> {
            aluguel.setStatus(StatusAluguel.CANCELADO);
            return repository.save(aluguel);
        });
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
