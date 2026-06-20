package br.pucminas.hospedagem.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.pucminas.hospedagem.dto.AluguelRequest;
import br.pucminas.hospedagem.model.Aluguel;
import br.pucminas.hospedagem.service.AluguelService;

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
    public Aluguel criar(@RequestBody AluguelRequest request) {
        return service.criarAluguel(request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Aluguel> atualizar(@PathVariable Long id, @RequestBody Aluguel aluguel) {
        return service.buscarPorId(id).map(existente -> {
            aluguel.setId(id);
            return ResponseEntity.ok(service.salvar(aluguel));
        }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/recibo")
    public ResponseEntity<String> imprimirRecibo(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(a -> ResponseEntity.ok(a.imprimirRecibo()))
                .orElse(ResponseEntity.notFound().build());
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
