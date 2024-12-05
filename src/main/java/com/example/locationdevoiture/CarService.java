package com.example.locationdevoiture;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mongodb.client.model.Filters.eq;

public class CarService {
    private MongoCollection<Document> carCollection;
    private Map<String, String> rentalRequests = new HashMap<>();

    public CarService() {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase database = mongoClient.getDatabase("car_rental");
        carCollection = database.getCollection("cars");
    }

    public void addCar(Car car) {
        Document doc = new Document("id", car.getId())
                .append("make", car.getMake())
                .append("model", car.getModel())
                .append("year", car.getYear())
                .append("rentalPrice", car.getRentalPrice());
        carCollection.insertOne(doc);
    }

    public Car getCarById(String id) {
        Document doc = carCollection.find(eq("id", id)).first();
        if (doc != null) {
            return new Car(
                    doc.getString("id"),
                    doc.getString("make"),
                    doc.getString("model"),
                    doc.getInteger("year"),
                    doc.getDouble("rentalPrice")
            );
        }
        return null;
    }

    public List<Car> getAllCars() {
        List<Car> cars = new ArrayList<>();
        for (Document doc : carCollection.find()) {
            cars.add(new Car(
                    doc.getString("id"),
                    doc.getString("make"),
                    doc.getString("model"),
                    doc.getInteger("year"),
                    doc.getDouble("rentalPrice")
            ));
        }
        return cars;
    }

    public void updateCar(Car car) {
        carCollection.updateOne(eq("id", car.getId()),
                new Document("$set", new Document("make", car.getMake())
                        .append("model", car.getModel())
                        .append("year", car.getYear())
                        .append("rentalPrice", car.getRentalPrice())));
    }

    public void deleteCar(String id) {
        carCollection.deleteOne(eq("id", id));
    }

    public void requestRental(String carId, User user) {
        if (user.getRole() == User.Role.CLIENT) {
            rentalRequests.put(carId, user.getUsername());
            System.out.println("Rental request submitted by " + user.getUsername());
        } else {
            System.out.println("Only clients can request rentals.");
        }
    }

    public void approveRental(String carId, User user) {
        if (user.getRole() == User.Role.ADMIN) {
            if (rentalRequests.containsKey(carId)) {
                String clientUsername = rentalRequests.get(carId);
                rentalRequests.remove(carId);
                System.out.println("Rental request for car " + carId + " approved for client " + clientUsername);
            } else {
                System.out.println("No rental request found for car " + carId);
            }
        } else {
            System.out.println("Only admins can approve rentals.");
        }
    }
}

class User {
    public enum Role {
        CLIENT, ADMIN
    }

    private String username;
    private Role role;

    public User(String username, Role role) {
        this.username = username;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public Role getRole() {
        return role;
    }
}
