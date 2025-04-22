package com.moulay.krepehouse.Models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Menu {

    private int uniqueId;
    private LocalDate date;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    public Menu() {
    }

    public Menu(int uniqueId, LocalDate date, LocalDateTime createAt, LocalDateTime updateAt) {
        this.uniqueId = uniqueId;
        this.date = date;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public int getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(int uniqueId) {
        this.uniqueId = uniqueId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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
