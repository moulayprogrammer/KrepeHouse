package com.moulay.krepehouse.Models;

import java.time.LocalDateTime;

public class FoodBill {

    private int uniqueId;
    private int uniqueIdBill;
    private int uniqueIdFood;
    private int qte;
    private float totalPrice;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    public FoodBill() {
    }

    public FoodBill(int uniqueIdBill, FoodBillTable foodBillTable) {
        this.uniqueIdBill = uniqueIdBill;
        this.uniqueIdFood = foodBillTable.getUniqueId();
        this.qte = foodBillTable.getQte();
        this.totalPrice = foodBillTable.getPriceTotal();
    }

    public FoodBill(int uniqueId, int uniqueIdBill, int uniqueIdFood, int qte, float totalPrice, LocalDateTime createAt, LocalDateTime updateAt) {
        this.uniqueId = uniqueId;
        this.uniqueIdBill = uniqueIdBill;
        this.uniqueIdFood = uniqueIdFood;
        this.qte = qte;
        this.totalPrice = totalPrice;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public int getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(int uniqueId) {
        this.uniqueId = uniqueId;
    }

    public int getUniqueIdBill() {
        return uniqueIdBill;
    }

    public void setUniqueIdBill(int uniqueIdBill) {
        this.uniqueIdBill = uniqueIdBill;
    }

    public int getUniqueIdFood() {
        return uniqueIdFood;
    }

    public void setUniqueIdFood(int uniqueIdFood) {
        this.uniqueIdFood = uniqueIdFood;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }
}
