package br.edu.pucrs.acmerent.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Automobile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String licensePlate;
    private String model;
    private int yearOfManufacture;

    @Enumerated(EnumType.STRING)
    private CarStatus status;

    @OneToMany(mappedBy = "automobile")
    private List<Rental> rentals;

    public Automobile() {}

    public Automobile(String licensePlate, String model, int yearOfManufacture) {
        this.licensePlate = licensePlate;
        this.model = model;
        this.yearOfManufacture = yearOfManufacture;
        this.status = CarStatus.AVAILABLE;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getLicensePlate() { return licensePlate; }
    public void setLicensePlate(String licensePlate) { this.licensePlate = licensePlate; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public int getYearOfManufacture() { return yearOfManufacture; }
    public void setYearOfManufacture(int yearOfManufacture) { this.yearOfManufacture = yearOfManufacture; }
    public CarStatus getStatus() { return status; }
    public void setStatus(CarStatus status) { this.status = status; }
    public List<Rental> getRentals() { return rentals; }
    public void setRentals(List<Rental> rentals) { this.rentals = rentals; }

    public boolean isAvailable() {
        return this.status == CarStatus.AVAILABLE;
    }

    public void rent() {
        if (!isAvailable()) {
            throw new IllegalStateException("Automobile is not available for rent.");
        }
        this.status = CarStatus.RENTED;
    }

    public void returnCar() {
        if (this.status != CarStatus.RENTED) {
            throw new IllegalStateException("Automobile is not rented.");
        }
        this.status = CarStatus.AVAILABLE;
    }

    public void remove() {
        if (this.status == CarStatus.REMOVED) {
            throw new IllegalStateException("Automobile is already removed.");
        }
        this.status = CarStatus.REMOVED;
    }
}
