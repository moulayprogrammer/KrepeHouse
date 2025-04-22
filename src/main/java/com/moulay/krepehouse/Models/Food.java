package com.moulay.krepehouse.Models;

import javafx.scene.image.Image;

import java.io.File;
import java.time.LocalDateTime;

public class Food {

    private int uniqueId;
    private String nameAr;
    private String nameFr;
    private float Price;
    private String description;
    private Image  picture;
    private int archive;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    public Food() {
    }

    public Food(int uniqueId, String nameAr, String nameFr, float price, String description, Image picture, int archive, LocalDateTime createAt, LocalDateTime updateAt) {
        this.uniqueId = uniqueId;
        this.nameAr = nameAr;
        this.nameFr = nameFr;
        this.Price = price;
        this.description = description;
        this.picture = picture;
        this.archive = archive;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public Food(String nameAr, String nameFr, float price, String description, Image picture) {
        this.nameAr = nameAr;
        this.nameFr = nameFr;
        this.Price = price;
        this.description = description;
        this.picture = picture;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Image getPicture() {
        return picture;
    }

    public void setPicture(Image picture) {
        this.picture = picture;
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
