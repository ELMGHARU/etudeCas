// VoitureController.java
package com.example.servicevoiture.controller;

import com.example.servicevoiture.client.ClientService;
import com.example.servicevoiture.model.Voiture;
import com.example.servicevoiture.repository.VoitureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/voitures")
public class VoitureController {

    @Autowired
    private VoitureRepository voitureRepository;

    @Autowired
    private ClientService clientService;

    @GetMapping
    public List<Voiture> findAll() {
        List<Voiture> voitures = voitureRepository.findAll();
        voitures.forEach(v -> {
            try {
                v.setClient(clientService.findById(v.getClientId()));
            } catch (Exception e) {
                // Handle case where client is not found
                v.setClient(null);
            }
        });
        return voitures;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Voiture> findById(@PathVariable Long id) {
        Voiture voiture = voitureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Voiture not found with id: " + id));
        try {
            voiture.setClient(clientService.findById(voiture.getClientId()));
        } catch (Exception e) {
            // Handle case where client is not found
            voiture.setClient(null);
        }
        return ResponseEntity.ok(voiture);
    }

    @GetMapping("/client/{clientId}")
    public List<Voiture> findByClientId(@PathVariable Long clientId) {
        List<Voiture> voitures = voitureRepository.findByClientId(clientId);
        voitures.forEach(v -> {
            try {
                v.setClient(clientService.findById(clientId));
            } catch (Exception e) {
                // Handle case where client is not found
                v.setClient(null);
            }
        });
        return voitures;
    }

    @PostMapping
    public ResponseEntity<Voiture> save(@RequestBody Voiture voiture) {
        // Verify if client exists
        try {
            clientService.findById(voiture.getClientId());
        } catch (Exception e) {
            throw new RuntimeException("Client not found with id: " + voiture.getClientId());
        }
        return ResponseEntity.ok(voitureRepository.save(voiture));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Voiture> update(@PathVariable Long id, @RequestBody Voiture voiture) {
        if (!voitureRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        // Verify if client exists
        try {
            clientService.findById(voiture.getClientId());
        } catch (Exception e) {
            throw new RuntimeException("Client not found with id: " + voiture.getClientId());
        }
        voiture.setId(id);
        return ResponseEntity.ok(voitureRepository.save(voiture));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        if (!voitureRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        voitureRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}