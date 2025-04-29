package com.moulay.krepehouse.Models;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

public class SimpleFood implements Serializable {

    private static final long serialVersionUID = 1L;

    private  int uniqueId;
    private  String nameAr;
    private  String nameFr;
    private  float Price;
    private  String description;
    private  byte[]  picture;
    private  int archive;
    private transient LocalDateTime createAt;
    private transient LocalDateTime updateAt;

    public SimpleFood() {
    }

    public SimpleFood(int uniqueId, String nameAr, String nameFr, float price, String description, byte[] picture, int archive, LocalDateTime createAt, LocalDateTime updateAt) {
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

    public SimpleFood(String nameAr, String nameFr, float price, String description, byte[] picture) {
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

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleFood food = (SimpleFood) o;
        return Objects.equals(uniqueId, food.getUniqueId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(uniqueId);
    }

    @Override
    public String toString() {
        return "SimpleFood{" +
                "uniqueId=" + uniqueId +
                ", nameAr='" + nameAr + '\'' +
                ", nameFr='" + nameFr + '\'' +
                ", Price=" + Price +
                ", description='" + description + '\'' +
                ", picture=" + Arrays.toString(picture) +
                ", archive=" + archive +
                ", createAt=" + createAt +
                ", updateAt=" + updateAt +
                '}';
    }

}
