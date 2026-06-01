package br.pucminas.hospedagem.service;

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

    public Aluguel salvar(Aluguel aluguel) {
        return repository.save(aluguel);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
