package com.example.fooddonation;

public class User {
    String FoodType,Description,Quantity,Location;
    String did;
    public User(){}

    public User(String foodType, String description, String quantity, String location) {
        FoodType = foodType;
        Description = description;
        Quantity = quantity;
        Location = location;
    }

    public String getFoodType() {
        return FoodType;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public void setFoodType(String foodType) {
        FoodType = foodType;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }
}
