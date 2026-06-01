package br.pucminas.hospedagem.controller;

import br.pucminas.hospedagem.model.Residencia;
import br.pucminas.hospedagem.service.ResidenciaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/residencias")
public class ResidenciaController {

    private final ResidenciaService service;

    public ResidenciaController(ResidenciaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Residencia> listarTodas() {
        return service.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Residencia> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Residencia criar(@RequestBody Residencia residencia) {
        return service.salvar(residencia);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Residencia> atualizar(@PathVariable Long id, @RequestBody Residencia residencia) {
        return service.buscarPorId(id).map(existente -> {
            residencia.setId(id);
            return ResponseEntity.ok(service.salvar(residencia));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
