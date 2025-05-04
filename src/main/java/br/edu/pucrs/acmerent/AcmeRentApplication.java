package br.edu.pucrs.acmerent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import br.edu.pucrs.acmerent.controller.AcmeRentController;
import br.edu.pucrs.acmerent.entity.Automobile;
import br.edu.pucrs.acmerent.entity.Client;
import br.edu.pucrs.acmerent.entity.Rental;
import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootApplication
public class AcmeRentApplication {
    public static void main(String[] args) {
        SpringApplication.run(AcmeRentApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(AcmeRentController controller) {
        return args -> {
            Client c1 = new Client("11111111111", "João Silva", "51999999999");
            Client c2 = new Client("22222222222", "Maria Souza", "51888888888");
            Client c3 = new Client("33333333333", "Carlos Lima", "51777777777");
            c1.setId(1L);
            c2.setId(2L);
            c3.setId(3L);
            controller.addClient(1L, c1);
            controller.addClient(2L, c2);
            controller.addClient(3L, c3);

            for (long i = 1; i <= 10; i++) {
                Automobile a = new Automobile("ABC-000" + i, "Modelo" + i, 2020 + (int)(i % 5));
                a.setId(i);
                controller.addAutomobile(i, a);
            }

            Rental r1 = new Rental(1L, LocalDate.now(), 5, new BigDecimal("100.00"), c1, controller.getAutomobile(1L));
            Rental r2 = new Rental(2L, LocalDate.now().minusDays(10), 10, new BigDecimal("80.00"), c2, controller.getAutomobile(2L));
            controller.addRental(1L, r1);
            controller.addRental(2L, r2);
        };
    }
} 