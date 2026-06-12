package br.pucminas.hospedagem.controller;

import br.pucminas.hospedagem.model.Quarto;
import br.pucminas.hospedagem.model.QuartoCasal;
import br.pucminas.hospedagem.model.QuartoFamilia;
import br.pucminas.hospedagem.model.QuartoIndividual;
import br.pucminas.hospedagem.service.QuartoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quartos")
public class QuartoController {

    private final QuartoService service;

    public QuartoController(QuartoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Quarto> listarTodos() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Quarto> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/individual")
    public QuartoIndividual criarIndividual(@RequestBody QuartoIndividual quarto) {
        return (QuartoIndividual) service.salvar(quarto);
    }

    @PostMapping("/casal")
    public QuartoCasal criarCasal(@RequestBody QuartoCasal quarto) {
        return (QuartoCasal) service.salvar(quarto);
    }

    @PostMapping("/familia")
    public QuartoFamilia criarFamilia(@RequestBody QuartoFamilia quarto) {
        return (QuartoFamilia) service.salvar(quarto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
