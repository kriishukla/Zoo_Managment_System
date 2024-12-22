 package org.example;

import java.util.*;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReferenceArray;

import java.util.*;
// import Zoo.Visitor;

interface Zood {
    void addAttraction(String name, String description, int price, int count);

    void viewAttractions();

    List<Attraction> getAttractions();

    void addAnimal(String name, String type);

    void viewAnimals();

    List<Animal> getAnimals();

    void addDiscount(String category, double percent);

    Map<String, Double> getDiscounts();

    void registerVisitor(String name, int age, String phoneNumber, double balance, String email, String password);

    void addFeedback(String feedback);

    void getFeedback();
}

class Zoo implements Zood {

    public Zoo() {
        attractions = new ArrayList<>();
        animals = new ArrayList<>();
        animals.add(new Animal("Crocodile", "Amphibian"));
        animals.add(new Animal("frog", "Amphibian"));
        animals.add(new Animal("Monkey", "Mammal"));
        animals.add(new Animal("human", "Mammal"));
        animals.add(new Animal("Snake", "Reptile"));
        animals.add(new Animal("Reptile", "Reptile"));
        discounts = new HashMap<>();
        feedbacks = new ArrayList<>();
        visitors = new ArrayList<>();
    }

    public static class Visitor {
        private String name;
        private int age;
        private String phoneNumber;
        private double balance;
        private String email;
        private String password;
        private Double spend = 0.0;
        public String prime;
        public Visitor(String name, int age, String phoneNumber, double balance, String email, String password) {
            this.name = name;
            this.age = age;
            this.phoneNumber = phoneNumber;
            this.balance = balance;
            this.email = email;
            this.password = password;
        }

        public void setPrime(String p){prime = p;}
        public String getPrime(){return prime;}

        public Double getSpend() {
            return spend;
        }

        public void setSpend(Double cash) {
            spend += cash;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public double getBalance() {
            return balance;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }

        public void setBalance(double d) {
            balance = d;
        }
    }

    private List<Attraction> attractions;
    private List<Animal> animals;
    private Map<String, Double> discounts;
    private List<String> feedbacks;
    private Map<Integer, Double> specialdeal = new HashMap<>();
    Scanner scanner = new Scanner(System.in);

    public void addAttraction(String name, String description, int price, int count) {
        Attraction newAttraction = new Attraction(name, description, price, count);
        attractions.add(newAttraction);
    }

    public Double revenue() {
        Double r = 0.0;
        for (Visitor visitor : visitors) {
            r += visitor.getSpend();
        }
        return r;
    }

    public List<Attraction> geAttractions() {
        return attractions;
    }

    public void setSpecialDeal() {
        scanner.nextLine();
        System.out.print("\nEnter the deal: ");
        int no_attr = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Discount: ");
        Double disc = scanner.nextDouble();
        specialdeal.put(no_attr, disc);
    }

    public Map<Integer, Double> getSpecial() {
        return specialdeal;
    }

    public void viewAttractions() {
        int id = 0;
        for (Attraction attraction : attractions) {
            id++;
            System.out.println(id);
            System.out.println(attraction.getName());
            System.out.println(attraction.getDescription());
            System.out.println(attraction.getPrice());
        }
    }

    public List<Attraction> getAttractions() {
        return attractions;
    }

    public void viewSpecialDeal() {
        for (Map.Entry<Integer, Double> entry : specialdeal.entrySet()) {
            System.out.println("Buy " + entry.getKey() + " tickets and get " + entry.getValue() + " off");
        }
    }

    public void buyMembership(int membershipChoice, String username , String password) {
        double membershipPrice;
        String membershipType;
        if (membershipChoice == 1) {
            membershipPrice = 20.0;
            membershipType = "Basic";
        } else {
            membershipPrice = 50.0;
            membershipType = "Premium";
        }

        System.out.print("Enter Discount Code : ");
        String discountCode = scanner.nextLine();

        double discount = 0.0;
        if (discountCode.equals("hello")) {
            discount = 10.0;
        } else if (discountCode.equals("world")) {
            discount = 20.0;

        }
        for (Visitor visitor : visitors) {
            if (Objects.equals(visitor.getName(), username) && Objects.equals(visitor.getPassword(), password)) {
                if (Objects.equals(visitor.getPrime(), "Premium")) {
                    break;
                } else {// if visitor has basic

                    if (visitor.getAge() < 18) {
                        discount = discounts.get("minor");
                    } else if (visitor.getAge() > 65) {
                        discount = discounts.get("senior");
                    }
                    if (discounts.get(discountCode) != null) {
                        discount += discounts.get(discountCode);
                    }

                }
                if ((visitor.getBalance() < 20 && membershipChoice == 1)
                        || (visitor.getBalance() < 50 && membershipChoice == 2)) {
                    System.out.println("\n>>Not Enough Balance");
                    break;
                } else {
                    Double left = visitor.getBalance() - (membershipPrice - (membershipPrice * (discount / 100)));
                    visitor.setBalance(left);
                    visitor.setSpend(left);
                    if (membershipChoice == 1) {
                        visitor.setPrime("basic");
                    } else if (membershipChoice == 2) {
                        visitor.setPrime("premium");
                    }
                    System.out.println("\n>>Bought Successfully ");
                }
            }
            break;
        }
    }


    public void addAnimal(String name, String type) {
        Animal newAnimal = new Animal(name, type);
        animals.add(newAnimal);
    }

    public void viewAnimals() {
        int ide = 0;
        for (Animal animal : animals) {
            ide++;
            System.out.println(ide);
            System.out.println(animal.getName());
            System.out.println(animal.getType());
        }
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public void addDiscount(String category, double percent) {

        discounts.put(category, percent);
    }

    public Map<String, Double> getDiscounts() {
        return discounts;
    }

    private List<Visitor> visitors = new ArrayList<Visitor>();

    public Visitor getVisitor(String username, String password) {
        for (Visitor visitor : visitors) {
            if (username.equals(visitor.getName()) && password.equals(visitor.getPassword())) {
                return visitor;
            }
        }
        return null;
    }

    public void addFeedback(String feedback) {
        feedbacks.add(feedback);
    }

    public void getFeedback() {
        for (String feedbacks : feedbacks) {
            System.out.println(feedbacks);

        }
    }

    public List<Visitor> getvisitorList() {
        return visitors;
    }

    public void registerVisitor(String name, int age, String phoneNumber, double balance, String email,
                                String password) {

        Visitor newVisitor = new Visitor(name, age, phoneNumber, balance, email, password);

        visitors.add(newVisitor);
        System.out.println("Visitor " + name + " has been registered.");
    }
}

abstract class Attractions {
    private String name;
    private String description;
    private int price;
    private int count;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String newAttractionName) {
        this.name = newAttractionName;
    }

    public void setDescription(String newAttractionDescription) {
        this.description = newAttractionDescription;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int newPrice) {
        this.price = newPrice;
    }

    public abstract void setCount();

    public int getVisitorCountAttraction() {
        return count;
    }
}

class Attraction extends Attractions {
    private String name;
    private String description;
    private int price;
    private int count;
    private String schedule;
    private int ticket = 0;

    public void setTicket() {
        ticket++;
    }

    public int getTicket() {
        return this.ticket;
    }

    public Attraction(String name, String description, int price, int count) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.count = count;
    }

    public void setSchedule(String s) {
        schedule = s;
    }

    public String getSchedule() {
        return schedule;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String newAttractionName) {
        this.name = newAttractionName;
    }

    public void setDescription(String newAttractionDescription) {
        this.description = newAttractionDescription;
    }

    public int getPrice() {
        return price;

    }

    public void setPrice(int newPrice) {
        this.price = newPrice;

    }

    public void setCount() {
        count = count + 1;

    }

    public int getVisitorCountAttraction() {
        return count;
    }
}

abstract class Entity {
    protected String name;

    public Entity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        this.name = newName;
    }
}

class Admin extends Entity {
    private String password;

    public Admin(String username, String password) {
        super(username);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }
}

class Animal extends Entity {
    private String type;

    public Animal(String name, String type) {
        super(name);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String newType) {
        this.type = newType;
    }
}

public class Main {
public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int x = 1;
        Map<Integer, Double> specialdeal;
        boolean adminLoggedIn = false;
        boolean visitorLoggedIn = false;

        Zoo zoo = new Zoo();

        String deal = null;
        while (x == 1) {
        System.out.println("Welcome to ZOOtopia!\n" +
        "1. Enter as Admin\n" +
        "2. Enter as a Visitor\n" +
        "3. View Special Deals\n");

        int choice;
        while (true) {
        System.out.print("Enter your choice: ");
        if (scanner.hasNextInt()) {
        choice = scanner.nextInt();
        scanner.nextLine();
        break;
        } else {
        System.out.println("Invalid input. Please enter a valid integer for your choice.");
        scanner.nextLine();
        }
        }

        if (choice == 1) {
        if (!adminLoggedIn) {

        System.out.print("Enter Admin Username: ");
        String adminUsername = scanner.nextLine();
        System.out.print("Enter Admin Password: ");
        String adminPassword = scanner.nextLine();

        if (adminUsername.equals("admin") && adminPassword.equals("admin123")) {
        adminLoggedIn = true;
        } else {
        System.out.println("Invalid admin credentials. Please try again.");
        continue;
        }
        }

        int z = 1;
        while (z == 1) {
        System.out.println("Logged in as Admin.\n" +
        "Admin Menu:\n" +
        "1. Manage Attractions\n" +
        "2. Manage Animals\n" +
        "3. Schedule Events\n" +
        "4. Set Discounts\n" +
        "5. Set Special Deal\n" +
        "6. View Visitor Stats\n" +
        "7. View Feedback\n" +
        "8. Exit\n");

        int adminChoice;
        while (true) {
        System.out.print("Enter your choice: ");
        if (scanner.hasNextInt()) {
        adminChoice = scanner.nextInt();
        scanner.nextLine();
        break;
        } else {
        System.out.println("Invalid input");
        scanner.nextLine();
        }
        }

        if (adminChoice == 1) {
        System.out.println("Manage Attractions:\n" +
        "1. Add Attraction\n" +
        "2. View Attractions\n" +
        "3. Modify Attraction\n" +
        "4. Remove Attraction\n" +
        "5. Exit\n");

        int attractionChoice;
        while (true) {
        System.out.print("Enter your choice: ");
        if (scanner.hasNextInt()) {
        attractionChoice = scanner.nextInt();
        scanner.nextLine();
        break;
        } else {
        System.out.println("Invalid input. Please enter a valid integer for your choice.");
        scanner.nextLine();
        }
        }

        if (attractionChoice == 1) {
        // Adding a new attraction
        System.out.print("Enter Attraction Name: ");
        String attractionName = scanner.nextLine();
        System.out.print("Enter Attraction Description: ");
        String attractionDescription = scanner.nextLine();

        zoo.addAttraction(attractionName, attractionDescription, 0, 0);

        System.out.println("Attraction added successfully.");
        }

        else if (attractionChoice == 2) {
        zoo.viewAttractions();
        }

        else if (attractionChoice == 3) {

        System.out.print("Enter Attraction ID to modify: ");
        int attractionID = scanner.nextInt();
        scanner.nextLine();

        if (attractionID > 0 && attractionID <= zoo.getAttractions().size()) {

        Attraction attraction = zoo.getAttractions().get(attractionID - 1);
        System.out.print("Enter the new Attraction Name: ");
        String newAttractionName = scanner.nextLine();
        System.out.print("Enter the new Attraction Description: ");
        String newAttractionDescription = scanner.nextLine();
        attraction.setName(newAttractionName);
        attraction.setDescription(newAttractionDescription);

        System.out.println("Attraction modified successfully.");
        } else {
        System.out.println("Invalid Attraction ID. Please enter a valid ID.");
        }

        } else if (attractionChoice == 4) {

        System.out.print("Enter Attraction ID to remove: ");
        int attractionID = scanner.nextInt();
        scanner.nextLine();

        if (attractionID > 0 && attractionID <= zoo.getAttractions().size()) {
        zoo.getAttractions().remove(attractionID - 1);
        System.out.println("Attraction removed successfully.");
        } else {
        System.out.println("Invalid Attraction ID. Please enter a valid ID.");
        }
        } else if (attractionChoice == 5) {
        z = 0;
        } else {
        System.out.println("Invalid Admin Choice");
        }
        }

        else if (adminChoice == 2) {
        System.out.println("Manage Animals:\n" +
        "1. Add Animal\n" +
        "2. View Animals\n" +
        "3. Modify Animal\n" +
        "4. Remove Animal\n" +
        "5. Exit\n");

        int animalChoice;
        while (true) {
        System.out.print("Enter your choice: ");
        if (scanner.hasNextInt()) {
        animalChoice = scanner.nextInt();
        scanner.nextLine();
        break;
        } else {
        System.out.println("Invalid input. Please enter a valid integer for your choice.");
        scanner.nextLine();
        }
        }

        if (animalChoice == 1) {
        System.out.print("Enter Animal Name: ");
        String animalName = scanner.nextLine();
        System.out.print("Enter Animal Type ");
        String animalType = scanner.nextLine();

        if ("mammal".equalsIgnoreCase(animalType) || "amphibian".equalsIgnoreCase(animalType)
        || "reptile".equalsIgnoreCase(animalType)) {
        zoo.addAnimal(animalName, animalType);
        System.out.println("Animal added successfully.");
        } else {
        System.out.println(
        "Invalid animal type. Please enter a valid type (mammal, amphibian, reptile).");
        }
        } else if (animalChoice == 2) {

        zoo.viewAnimals();
        } else if (animalChoice == 3) {

        System.out.print("Enter Animal ID to modify: ");
        int animalID = scanner.nextInt();
        scanner.nextLine();
        if (animalID > 0 && animalID <= zoo.getAnimals().size()) {

        Animal animal = zoo.getAnimals().get(animalID - 1);

        System.out.print("Enter the new Animal Name: ");
        String newAnimalName = scanner.nextLine();
        System.out.print("Enter the new Animal Type: ");
        String newAnimalType = scanner.nextLine();
        animal.setName(newAnimalName);
        animal.setType(newAnimalType);

        System.out.println("Animal modified successfully.");
        } else {
        System.out.println("Invalid Animal ID. Please enter a valid ID.");
        }
        } else if (animalChoice == 4) {

        System.out.print("Enter Animal ID to remove: ");
        int animalID = scanner.nextInt();
        scanner.nextLine();

        if (animalID > 0 && animalID <= zoo.getAnimals().size()) {
        zoo.getAnimals().remove(animalID - 1);
        System.out.println("Animal removed successfully.");
        } else {
        System.out.println("Invalid Animal ID. Please enter a valid ID.");
        }
        } else if (animalChoice == 5) {
        z = 0;
        } else {
        System.out.println("Invalid Admin Choice");
        }
        } else if (adminChoice == 3) {
        System.out.print("Enter the name of the attraction: ");
        String attractionName = scanner.nextLine();

        Attraction foundAttraction = null;

        for (Attraction attraction : zoo.getAttractions()) {
        if (attraction.getName().equalsIgnoreCase(attractionName)) {
        foundAttraction = attraction;
        System.out.print("Enter Schedule: ");
        String schedule = scanner.nextLine();
        System.out.print("Enter Price for Attraction: ");
        int price = scanner.nextInt();
        scanner.nextLine();
        attraction.setSchedule(schedule);
        attraction.setPrice(price);
        System.out.println("Count of Visitors: " + attraction.getVisitorCountAttraction());
        break;
        }
        }
        } else if (adminChoice == 4) {
        System.out.println("Set Discounts:\n" +
        "1. Add Discount\n" +
        "2. Modify Discount\n" +
        "3. Remove Discount\n" +
        "4. Exit\n");

        int discountChoice;
        while (true) {
        System.out.print("Enter your choice: ");
        if (scanner.hasNextInt()) {
        discountChoice = scanner.nextInt();
        scanner.nextLine();
        break;
        } else {
        System.out.println("Invalid input. Please enter a valid integer for your choice.");
        scanner.nextLine();
        }
        }

        if (discountChoice == 1) {
        System.out.print("Discount Category: ");
        String discountCategory = scanner.nextLine();
        System.out.print("Enter Discount Percent: ");
        double discountPercent = scanner.nextDouble();
        scanner.nextLine();

        zoo.addDiscount(discountCategory, discountPercent);

        System.out.println("Discount added successfully.");
        } else if (discountChoice == 2) {
        System.out.print("Enter Discount Category to modify: ");
        String discountCategory = scanner.nextLine();

        if (zoo.getDiscounts().containsKey(discountCategory)) {
        System.out.println("Enter the new Discount Percent: ");
        double newDiscountPercent = scanner.nextDouble();
        scanner.nextLine();
        zoo.getDiscounts().remove(discountCategory);
        zoo.addDiscount(discountCategory, newDiscountPercent);
        System.out.println("Discount modified successfully.");
        } else {
        System.out.println("Invalid Discount Category. Please enter a valid category.");
        }
        } else if (discountChoice == 5) {
        for (Map.Entry<String, Double> entry : zoo.getDiscounts().entrySet())
        System.out.println("Key = " + entry.getKey() +
        ", Value = " + entry.getValue());
        } else if (discountChoice == 3) {
        System.out.print("Enter Discount Category to remove: ");
        String discountCategory = scanner.nextLine();

        if (zoo.getDiscounts().containsKey(discountCategory)) {
        zoo.getDiscounts().remove(discountCategory);
        System.out.println("Discount removed successfully.");
        } else {
        System.out.println("Invalid Discount Category. Please enter a valid category.");
        }
        } else if (discountChoice == 4) {
        z = 0;
        } else {
        System.out.println("Invalid Admin Choice");
        }
        } else if (adminChoice == 5) {
        zoo.setSpecialDeal();
        } else if (adminChoice == 6) {
        System.out.println("Visitor Statistics:\n");
        System.out.println("-Total Visitors: " + zoo.getvisitorList().size());
        Double revenue = zoo.revenue();
        System.out.println("Total Revenue: " + revenue);
        int m = -1;
        String attr = "None";
        for (Attraction attraction : zoo.getAttractions()) {
        if (attraction.getTicket() > m) {
        m = attraction.getTicket();
        attr = attraction.getName();
        }
        }
        System.out.println("-Most Popular Attraction is :" + attr);
        }

        else if (adminChoice == 7) {
        zoo.getFeedback();
        } else if (adminChoice == 8) {
        z = 0;
        adminLoggedIn = false;
        System.out.println("Logged out.");
        } else {
        System.out.println("Invalid Admin Choice");
        }
        }
        }

        else if (choice == 2) {
        int vc;
        System.out.println("1. Register\n" +
        "2. Login\n" +
        "");
        while (true) {
        System.out.print("Enter your choice: ");
        if (scanner.hasNextInt()) {
        vc = scanner.nextInt();
        scanner.nextLine();
        break;
        } else {
        System.out.println("Invalid input. Please enter a valid integer for your choice.");
        scanner.nextLine();
        }
        }

        Zoo.Visitor registeredVisitor = null;

        if (vc == 1) {
        Set<String> uniquePhoneNumbers = new HashSet<>();
        Set<String> uniqueEmails = new HashSet<>();
        Set<String> uniqueUsernames = new HashSet<>();
        String visitorUsername;
        while (true) {
        System.out.println("Enter Visitor Username: ");
        visitorUsername = scanner.nextLine();

        // Check if the username is unique
        if (uniqueUsernames.contains(visitorUsername)) {
        System.out.println("Username already in use. Please enter a unique username.");
        } else {
        uniqueUsernames.add(visitorUsername);
        break;
        }
        }

        int visitorAge = 0;
        while (true) {
        System.out.println("Enter Visitor Age: ");
        if (scanner.hasNextInt()) {
        visitorAge = scanner.nextInt();
        scanner.nextLine();
        break;
        } else {
        System.out.println("Invalid input. Please enter a valid integer for visitor age.");
        scanner.nextLine(); // Consume the invalid input
        }
        }

        String visitorPhoneNumber;
        while (true) {
        System.out.println("Enter Visitor Phone Number: ");
        visitorPhoneNumber = scanner.nextLine();

        // Check if the phone number is unique
        if (uniquePhoneNumbers.contains(visitorPhoneNumber)) {
        System.out.println("Phone number already in use. Please enter a unique phone number.");
        } else {
        uniquePhoneNumbers.add(visitorPhoneNumber);
        break;
        }
        }

        double visitorBalance = 0.0;
        while (true) {
        System.out.println("Enter Visitor Balance: ");
        if (scanner.hasNextDouble()) {
        visitorBalance = scanner.nextDouble();
        scanner.nextLine();
        break;
        } else {
        System.out.println("Invalid input. Please enter a valid number for visitor balance.");
        scanner.nextLine(); // Consume the invalid input
        }
        }

        String visitorEmail;
        while (true) {
        System.out.println("Enter Visitor Email: ");
        visitorEmail = scanner.nextLine();

        // Check if the email is unique
        if (uniqueEmails.contains(visitorEmail)) {
        System.out.println("Email address already in use. Please enter a unique email address.");
        } else {
        uniqueEmails.add(visitorEmail);
        break;
        }
        }

        System.out.println("Enter Visitor Password: ");
        String visitorPassword = scanner.nextLine();

        zoo.registerVisitor(visitorUsername, visitorAge, visitorPhoneNumber, visitorBalance, visitorEmail,
        visitorPassword);
        }

        else if (vc == 2) {
        String username = "none";
        String password = "none";
        if (!visitorLoggedIn) {

        System.out.print("Enter Visitor Username: ");
        String visitorUsername = scanner.nextLine();
        System.out.print("Enter Visitor Password: ");
        String visitorPassword = scanner.nextLine();

        registeredVisitor = zoo.getVisitor(visitorUsername, visitorPassword);
        if (registeredVisitor != null) {
        username = visitorUsername;
        password = visitorPassword;
        visitorLoggedIn = true;
        } else {
        System.out.println("Invalid visitor credentials. Please try again.");
        continue;
        }
        }
        int z = 1;
        while (z == 1) {
        System.out.println("Logged in as Visitor.\n" +
        "Visitor Menu:\n" +
        "1. Explore the Zoo\n" +
        "2. Buy Membership\n" +
        "3. Buy Tickets\n" +
        "4. View Discounts\n" +
        "5. View Special Deals\n" +
        "6. Visit Animals\n" +
        "7. Visit Attractions\n" +
        "8. Leave Feedback\n" +
        "9. Log Out\n");
        int visitorChoice;

        while (true) {
        System.out.print("Enter your choice: ");
        if (scanner.hasNextInt()) {
        visitorChoice = scanner.nextInt();
        scanner.nextLine();
        break;
        } else {
        System.out.println("Invalid input. Please enter a valid integer for your choice.");
        scanner.nextLine();
        }
        }

        // Zoo.Visitor registeredVisitor = null;
        if (visitorChoice == 1) {
        System.out.println("Explore the Zoo:\n" +
        "1. View Attractions\n" +
        "2. View Animals\n" +
        "3. Exit\n");
        int exploreChoice;
        while (true) {
        System.out.print("Enter your choice: ");
        if (scanner.hasNextInt()) {
        exploreChoice = scanner.nextInt();
        scanner.nextLine();
        break;
        } else {
        System.out.println("Invalid input. Please enter a valid integer for your choice.");
        scanner.nextLine();
        }
        }
        if (exploreChoice == 1) {

        zoo.viewAttractions();

        } else if (exploreChoice == 2) {

        zoo.viewAnimals();
        } else if (exploreChoice == 3) {
        z = 0;
        } else {
        System.out.println("Invalid Explore Choice");
        }
        }

        else if (visitorChoice == 2) {
        System.out.println("Buy Membership:\n" +
        "1. Basic Membership (₹20)\n" +
        "2. Premium Membership (₹50)\n" +
        "Enter your choice: ");

        int membershipChoice;
        while (true) {
        System.out.print("Enter your choice: ");
        if (scanner.hasNextInt()) {
        membershipChoice = scanner.nextInt();
        if (membershipChoice == 1 || membershipChoice == 2) {
        scanner.nextLine();
        break;
        } else {
        System.out.println("Invalid choice");
        }
        } else {
        System.out.println("Invalid input");
        scanner.nextLine();
        }
        }

        zoo.buyMembership(membershipChoice, username, password);
        }

        else if (visitorChoice == 3) {
        for (Attraction attractions_ds : zoo.getAttractions()) {
        System.out.println(attractions_ds.getName() + "("
        + attractions_ds.getTicket() + ")");
        }
        String ticketChoice = scanner.nextLine();

        double ticketPrice = 0.0;

        for (Attraction attraction : zoo.getAttractions()) {
        if (attraction.getName().equalsIgnoreCase(ticketChoice)) {
        ticketPrice = attraction.getPrice();
        attraction.setCount();
        break;
        }
        }

        for (Animal animal : zoo.getAnimals()) {
        if (animal.getName().equalsIgnoreCase(ticketChoice)) {
        ticketPrice = 15;
        break;
        }
        }

        if (ticketPrice > 0) {
        System.out.println("The ticket for " + ticketChoice + " costs ₹" + ticketPrice);
        System.out.print("Enter Discount Code (if any): ");
        String discountCode = scanner.nextLine();
        double discount = 0.0;

        if (registeredVisitor.getAge() < 18) {
        discount = 10;
        } else if (registeredVisitor.getAge() > 65) {
        discount = 20;
        }

        if (zoo.getDiscounts().containsKey(discountCode)) {
        discount += zoo.getDiscounts().get(discountCode);
        }

        if (discount > 0) {
        ticketPrice = ticketPrice - (ticketPrice * (discount / 100));
        }

        if (registeredVisitor.getBalance() >= ticketPrice) {
        registeredVisitor.setBalance(registeredVisitor.getBalance() - ticketPrice);
        System.out.println("Ticket purchased successfully for ₹" + ticketPrice);
        } else {
        System.out.println("Insufficient balance. Please recharge your balance.");
        }

        System.out.println("Your current balance: ₹" + registeredVisitor.getBalance());
        } else {
        System.out.println("Invalid choice. Please select a valid attraction or animal.");
        }

        }

        else if (visitorChoice == 4) {
        System.out.println("Available Discounts:");
        zoo.addDiscount("minor", 10);
        zoo.addDiscount("senior", 20);
        for (Map.Entry<String, Double> entry : zoo.getDiscounts().entrySet()) {
        System.out.println(entry.getKey() + ": " + entry.getValue() + "%");
        }
        } else if (visitorChoice == 5) {
        zoo.viewSpecialDeal();
        } else if (visitorChoice == 6) {
        System.out.print("Which animal would you like to visit: ");
        String animalChoice = scanner.nextLine();

        for (Animal animal : zoo.getAnimals()) {
        if (animal.getName().equalsIgnoreCase(animalChoice)) {
        System.out.println("Animal Name: " + animal.getName());
        System.out.println("Type: " + animal.getType());
        return;
        }
        }
        System.out.println("Animal not found. Please enter a valid choice.");
        } else if (visitorChoice == 7) {
        System.out.print("Which attraction would you like to visit: ");
        String attractionChoice = scanner.nextLine();

        for (Attraction attraction : zoo.getAttractions()) {
        if (attraction.getName().equalsIgnoreCase(attractionChoice)) {
        System.out.println("Attraction Name: " + attraction.getName());
        System.out.println("Description: " + attraction.getDescription());
        return;
        }
        }
        System.out.println("Attraction not found. Please enter a valid choice.");
        } else if (visitorChoice == 8) {
        System.out.print("Please leave your feedback: ");
        String feedback = scanner.nextLine();
        zoo.addFeedback(feedback);
        System.out.println("Feedback submitted successfully!");
        } else if (visitorChoice == 9) {
        z = 0;
        visitorLoggedIn = false;
        System.out.println("Logged out.");
        } else {
        System.out.println("Invalid Visitor Choice");
        }
        }
        } else if (choice == 3) {
        zoo.viewSpecialDeal();
        } else {
        System.out.println("Invalid Choice");
        }
        }
        }
        }
        }