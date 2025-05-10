package com.moulay.krepehouse.Models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Bill {

    private int uniqueId;
    private int uniqueIdVendor;
    private int number;
    private LocalDate date;
    private LocalTime time;
    private float totalPrice;
    private int archive;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    public Bill() {
    }

    public Bill(int uniqueIdVendor, int number, LocalDate date, LocalTime time, float totalPrice) {
        this.uniqueIdVendor = uniqueIdVendor;
        this.number = number;
        this.date = date;
        this.time = time;
        this.totalPrice = totalPrice;
    }

    public Bill(int uniqueId, int uniqueIdVendor, int number, LocalDate date, LocalTime time, float totalPrice, int archive, LocalDateTime createAt, LocalDateTime updateAt) {
        this.uniqueId = uniqueId;
        this.uniqueIdVendor = uniqueIdVendor;
        this.number = number;
        this.date = date;
        this.time = time;
        this.totalPrice = totalPrice;
        this.archive = archive;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public int getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(int uniqueId) {
        this.uniqueId = uniqueId;
    }

    public int getUniqueIdVendor() {
        return uniqueIdVendor;
    }

    public void setUniqueIdVendor(int uniqueIdVendor) {
        this.uniqueIdVendor = uniqueIdVendor;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getArchive() {
        return archive;
    }

    public void setArchive(int archive) {
        this.archive = archive;
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
