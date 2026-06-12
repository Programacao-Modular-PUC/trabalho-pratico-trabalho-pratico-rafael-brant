package br.pucminas.hospedagem.controller;

import br.pucminas.hospedagem.model.Aluguel;
import br.pucminas.hospedagem.service.AluguelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alugueis")
public class AluguelController {

    private final AluguelService service;

    public AluguelController(AluguelService service) {
        this.service = service;
    }

    @GetMapping
    public List<Aluguel> listarTodos() {
        return service.listarTodos();
    }

    @GetMapping("/tipo")
    public List<Aluguel> filtrarPorTipoQuarto(@RequestParam String tipoQuarto) {
        return service.filtrarPorTipoQuarto(tipoQuarto);
    }

    @GetMapping("/cliente/{clienteId}")
    public List<Aluguel> listarPorCliente(@PathVariable Long clienteId) {
        return service.listarPorCliente(clienteId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aluguel> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Aluguel criar(@RequestBody Aluguel aluguel) {
        return service.salvar(aluguel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Aluguel> atualizar(@PathVariable Long id, @RequestBody Aluguel aluguel) {
        return service.buscarPorId(id).map(existente -> {
            aluguel.setId(id);
            return ResponseEntity.ok(service.salvar(aluguel));
        }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<Aluguel> cancelar(@PathVariable Long id) {
        return service.cancelarAluguel(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
