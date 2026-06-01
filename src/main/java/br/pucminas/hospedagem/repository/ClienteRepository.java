package br.pucminas.hospedagem.repository;

import br.pucminas.hospedagem.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {}
