package com.moulay.krepehouse.Models;

import java.time.LocalDateTime;

public class FoodMenu {

    private int uniqueId;
    private int uniqueIdMenu;
    private int uniqueIdFood;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    public FoodMenu() {
    }

    public FoodMenu(int uniqueId, int uniqueIdMenu, int uniqueIdFood, LocalDateTime createAt, LocalDateTime updateAt) {
        this.uniqueId = uniqueId;
        this.uniqueIdMenu = uniqueIdMenu;
        this.uniqueIdFood = uniqueIdFood;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public int getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(int uniqueId) {
        this.uniqueId = uniqueId;
    }

    public int getUniqueIdMenu() {
        return uniqueIdMenu;
    }

    public void setUniqueIdMenu(int uniqueIdMenu) {
        this.uniqueIdMenu = uniqueIdMenu;
    }

    public int getUniqueIdFood() {
        return uniqueIdFood;
    }

    public void setUniqueIdFood(int uniqueIdFood) {
        this.uniqueIdFood = uniqueIdFood;
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
