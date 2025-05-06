package br.edu.pucrs.acmerent.controller;

import br.edu.pucrs.acmerent.entity.Automobile;
import br.edu.pucrs.acmerent.entity.Client;
import br.edu.pucrs.acmerent.entity.Rental;
import br.edu.pucrs.acmerent.entity.CarStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/acmerent")
public class AcmeRentController {
    // Simulação de "banco de dados" em memória
    private final Map<Long, Automobile> automobiles = new HashMap<>();
    private final Map<Long, Client> clients = new HashMap<>();
    private final Map<Long, Rental> rentals = new HashMap<>();

    // GET /acmerent/listaautomoveis
    @GetMapping("/listaautomoveis")
    public List<Map<String, Object>> listaAutomoveis() {
        return automobiles.values().stream().map(auto -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", auto.getId());
            map.put("placa", auto.getLicensePlate());
            map.put("modelo", auto.getModel());
            map.put("anoFabricacao", auto.getYearOfManufacture());
            map.put("status", auto.getStatus());
            return map;
        }).collect(Collectors.toList());
    }

    // GET /acmerent/listaclientes
    @GetMapping("/listaclientes")
    public List<Map<String, Object>> listaClientes() {
        return clients.values().stream().map(cli -> {
            Map<String, Object> map = new HashMap<>();
            map.put("codigo", cli.getId());
            map.put("cpf", cli.getCpf());
            map.put("nome", cli.getName());
            map.put("telefone", cli.getPhone());
            return map;
        }).collect(Collectors.toList());
    }

    // GET /acmerent/listalocacoes
    @GetMapping("/listalocacoes")
    public List<Map<String, Object>> listaLocacoes() {
        return rentals.values().stream().map(rental -> {
            Map<String, Object> map = new HashMap<>();
            map.put("numero", rental.getRentalNumber());
            map.put("datainicial", rental.getStartDate());
            map.put("dias", rental.getNumberOfDays());
            map.put("valorlocacao", rental.getTotalValue());
            map.put("cliente", rental.getClient() != null ? rental.getClient().getId() : null);
            map.put("automovel", rental.getAutomobile() != null ? rental.getAutomobile().getId() : null);
            return map;
        }).collect(Collectors.toList());
    }

    // GET /acmerent/consultacliente?codigo={codigo}
    @GetMapping("/consultacliente")
    public ResponseEntity<?> consultaCliente(@RequestParam Long codigo) {
        Client cli = clients.get(codigo);
        if (cli == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado");
        Map<String, Object> map = new HashMap<>();
        map.put("codigo", cli.getId());
        map.put("cpf", cli.getCpf());
        map.put("nome", cli.getName());
        map.put("telefone", cli.getPhone());
        return ResponseEntity.ok(map);
    }

    // POST /acmerent/validaautomovel
    @PostMapping("/validaautomovel")
    public ResponseEntity<Boolean> validaAutomovel(@RequestBody Map<String, Object> body) {
        Long id = ((Number) body.get("id")).longValue();
        Automobile auto = automobiles.get(id);
        if (auto == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        return ResponseEntity.ok(auto.isAvailable());
    }

    // POST /acmerent/atendimento/cadlocacao
    @PostMapping("/atendimento/cadlocacao")
    public ResponseEntity<Boolean> cadastraLocacao(@RequestBody Map<String, Object> body) {
        try {
            Long numero = ((Number) body.get("numero")).longValue();
            Long clientId = ((Number) body.get("clienteId")).longValue();
            Long autoId = ((Number) body.get("automovelId")).longValue();
            int dias = (int) body.get("dias");
            String dataInicial = (String) body.get("datainicial");
            java.time.LocalDate data = java.time.LocalDate.parse(dataInicial);
            java.math.BigDecimal valorDiaria = new java.math.BigDecimal(body.get("valordiaria").toString());
            Client cli = clients.get(clientId);
            Automobile auto = automobiles.get(autoId);
            if (cli == null || auto == null) return ResponseEntity.ok(false);
            Rental rental = new Rental(numero, data, dias, valorDiaria, cli, auto);
            rentals.put(numero, rental);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.ok(false);
        }
    }

    // POST /acmerent/atendimento/atualizaautomovel/{id}/estado/{status}
    @PostMapping("/atendimento/atualizaautomovel/{id}/estado/{status}")
    public ResponseEntity<?> atualizaAutomovel(@PathVariable Long id, @PathVariable String status) {
        Automobile auto = automobiles.get(id);
        if (auto == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Automóvel não encontrado");
        try {
            CarStatus newStatus = CarStatus.valueOf(status.toUpperCase());
            auto.setStatus(newStatus);
            Map<String, Object> map = new HashMap<>();
            map.put("id", auto.getId());
            map.put("placa", auto.getLicensePlate());
            map.put("modelo", auto.getModel());
            map.put("anoFabricacao", auto.getYearOfManufacture());
            map.put("status", auto.getStatus());
            return ResponseEntity.ok(map);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Status inválido");
        }
    }

    // POST /acmerent/atendimento/finalizalocacao
    @PostMapping("/atendimento/finalizalocacao")
    public ResponseEntity<Boolean> finalizaLocacao(@RequestBody Map<String, Object> body) {
        try {
            Long numero = ((Number) body.get("numero")).longValue();
            Rental rental = rentals.get(numero);
            
            if (rental == null) {
                return ResponseEntity.ok(false);
            }

            Automobile auto = rental.getAutomobile();
            if (auto != null) {
                auto.returnCar();
            }
            
            rentals.remove(numero);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.ok(false);
        }
    }

    // Métodos utilitários para inicialização de dados
    public void addClient(Long id, Client c) {
        clients.put(id, c);
    }
    public void addAutomobile(Long id, Automobile a) {
        automobiles.put(id, a);
    }
    public void addRental(Long id, Rental r) {
        rentals.put(id, r);
    }
    public Automobile getAutomobile(Long id) {
        return automobiles.get(id);
    }
} 