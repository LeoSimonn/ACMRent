package br.edu.pucrs.acmerent.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Rental {

    @Id
    private Long rentalNumber;
    private LocalDate startDate;
    private int numberOfDays;
    private BigDecimal totalValue;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "automobile_id")
    private Automobile automobile;

    public Rental() {}

    public Rental(Long rentalNumber, LocalDate startDate, int numberOfDays, BigDecimal dailyRate, Client client, Automobile automobile) {
        this.rentalNumber = rentalNumber;
        this.startDate = startDate;
        this.numberOfDays = numberOfDays;
        this.totalValue = calculateTotalValue(dailyRate);
        this.client = client;
        this.automobile = automobile;
        this.automobile.rent();
    }

    private BigDecimal calculateTotalValue(BigDecimal dailyRate) {
        BigDecimal days = BigDecimal.valueOf(this.numberOfDays);
        BigDecimal value = dailyRate.multiply(days);
        if (this.numberOfDays > 7) {
            BigDecimal discount = value.multiply(BigDecimal.valueOf(0.05));
            value = value.subtract(discount);
        }
        return value;
    }

    public Long getRentalNumber() {
        return rentalNumber;
    }

    public void setRentalNumber(Long rentalNumber) {
        this.rentalNumber = rentalNumber;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public int getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(int numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Automobile getAutomobile() {
        return automobile;
    }

    public void setAutomobile(Automobile automobile) {
        this.automobile = automobile;
    }
}
