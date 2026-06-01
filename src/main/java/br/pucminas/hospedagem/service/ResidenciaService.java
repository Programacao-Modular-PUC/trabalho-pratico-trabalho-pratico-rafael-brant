package br.pucminas.hospedagem.service;

import br.pucminas.hospedagem.model.Residencia;
import br.pucminas.hospedagem.repository.ResidenciaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResidenciaService {

    private final ResidenciaRepository repository;

    public ResidenciaService(ResidenciaRepository repository) {
        this.repository = repository;
    }

    public List<Residencia> listarTodas() {
        return repository.findAll();
    }

    public Optional<Residencia> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Residencia salvar(Residencia residencia) {
        return repository.save(residencia);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
