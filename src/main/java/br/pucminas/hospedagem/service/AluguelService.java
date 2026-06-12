package br.pucminas.hospedagem.service;

import br.pucminas.hospedagem.exception.QuartoIndisponivelException;
import br.pucminas.hospedagem.model.Aluguel;
import br.pucminas.hospedagem.repository.AluguelRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AluguelService {

    private final AluguelRepository repository;

    public AluguelService(AluguelRepository repository) {
        this.repository = repository;
    }

    public List<Aluguel> listarTodos() {
        return repository.findAll();
    }

    public Optional<Aluguel> buscarPorId(Long id) {
        return repository.findById(id);
    }

    //Salva um novo aluguel após validações

    public Aluguel salvar(Aluguel aluguel) {
        // Validações
        if (aluguel.getQuarto() == null) {
            throw new IllegalArgumentException("Quarto deve ser informado.");
        }
        if (aluguel.getCliente() == null) {
            throw new IllegalArgumentException("Cliente deve ser informado.");
        }

        // Validar datas
        aluguel.validarDatas();

        // Validar capacidade
        aluguel.getQuarto().validarCapacidade(aluguel.getQtdHospedes());

        // Validar disponibilidade
        if (!aluguel.getQuarto().estaDisponivel(aluguel.getDataHoraEntrada(), aluguel.getDataHoraSaida())) {
            throw new QuartoIndisponivelException(
                    "Quarto não está disponível para as datas solicitadas: " +
                    aluguel.getDataHoraEntrada() + " até " + aluguel.getDataHoraSaida()
            );
        }

        return repository.save(aluguel);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}

