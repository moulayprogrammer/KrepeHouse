package com.moulay.krepehouse.Models;

import javafx.scene.image.Image;

import java.time.LocalDateTime;
import java.util.Objects;

public class FoodBillTable {

    private int uniqueId;
    private String nameAr;
    private String nameFr;
    private float Price;
    private float PriceTotal;
    private int qte;

    public FoodBillTable() {
    }

    public FoodBillTable(Food food) {
        this.uniqueId = food.getUniqueId();
        this.nameAr = food.getNameAr();
        this.nameFr = food.getNameFr();
        this.Price = food.getPrice();
    }

    public int getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(int uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getNameAr() {
        return nameAr;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }

    public String getNameFr() {
        return nameFr;
    }

    public void setNameFr(String nameFr) {
        this.nameFr = nameFr;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(float price) {
        Price = price;
    }

    public float getPriceTotal() {
        return PriceTotal;
    }

    public void setPriceTotal(float priceTotal) {
        PriceTotal = priceTotal;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FoodBillTable food = (FoodBillTable) o;
        return Objects.equals(uniqueId, food.getUniqueId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(uniqueId);
    }

}
