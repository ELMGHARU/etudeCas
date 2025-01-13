// ClientController.java
package com.example.serviceclient.controller;

import com.example.serviceclient.model.Client;
import com.example.serviceclient.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> findById(@PathVariable Long id) {
        return clientRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new RuntimeException("Client not found with id: " + id));
    }

    @PostMapping
    public ResponseEntity<Client> save(@RequestBody Client client) {
        return ResponseEntity.ok(clientRepository.save(client));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> update(@PathVariable Long id, @RequestBody Client client) {
        if (!clientRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        client.setId(id);
        return ResponseEntity.ok(clientRepository.save(client));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        if (!clientRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        clientRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}