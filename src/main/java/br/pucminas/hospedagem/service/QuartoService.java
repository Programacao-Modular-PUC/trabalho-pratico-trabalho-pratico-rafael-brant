package br.pucminas.hospedagem.service;

import br.pucminas.hospedagem.model.Quarto;
import br.pucminas.hospedagem.repository.QuartoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuartoService {

    private final QuartoRepository repository;

    public QuartoService(QuartoRepository repository) {
        this.repository = repository;
    }

    public List<Quarto> listarTodos() {
        return repository.findAll();
    }

    public Optional<Quarto> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Quarto salvar(Quarto quarto) {
        return repository.save(quarto);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
