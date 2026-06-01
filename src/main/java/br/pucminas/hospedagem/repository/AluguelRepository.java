package br.pucminas.hospedagem.repository;

import br.pucminas.hospedagem.model.Aluguel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AluguelRepository extends JpaRepository<Aluguel, Long> {}
