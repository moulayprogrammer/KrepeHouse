package com.moulay.krepehouse.Models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Vendor {

    private int uniqueId;
    private String name;
    private String phone;
    private LocalDate dateJoined;
    private String username;
    private String password;
    private int archive;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    public Vendor() {
    }

    public Vendor(int uniqueId, String name, String phone, String username, String password, int archive, LocalDateTime createAt, LocalDateTime updateAt) {
        this.uniqueId = uniqueId;
        this.name = name;
        this.phone = phone;
        this.username = username;
        this.password = password;
        this.archive = archive;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public Vendor(String name, String phone, String username, String password) {
        this.name = name;
        this.phone = phone;
        this.username = username;
        this.password = password;
    }

    public Vendor(String name, String phone, LocalDate dateJoined, String username, String password) {
        this.name = name;
        this.phone = phone;
        this.dateJoined = dateJoined;
        this.username = username;
        this.password = password;
    }

    public int getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(int uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(LocalDate dateJoined) {
        this.dateJoined = dateJoined;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
    public String toString() {
        return "Vendor{" +
                "uniqueId=" + uniqueId +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", archive=" + archive +
                ", createAt=" + createAt +
                ", updateAt=" + updateAt +
                '}';
    }
}
