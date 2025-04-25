package com.moulay.krepehouse.BddPackage;


import com.moulay.krepehouse.Models.Menu;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class MenuOperation extends BDD<Menu>{


    @Override
    public boolean insert(Menu o) {
        connectDatabase();
        boolean ins = false;
        String query = "INSERT INTO `menu` (`Name`,`DATE`) VALUES (?,?) ;";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, o.getName());
            preparedStmt.setDate(2, Date.valueOf(o.getDate()));

            int insert = preparedStmt.executeUpdate();
            if(insert != -1) ins = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDatabase();
        return ins;
    }

    public int insertId(Menu o) {
        connectDatabase();
        int ins = 0;
        String query = "INSERT INTO `menu` (`Name`,`DATE`) VALUES (?,?) ;";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStmt.setString(1, o.getName());
            preparedStmt.setDate(2, Date.valueOf(o.getDate()));

            int insert = preparedStmt.executeUpdate();

            if (insert > 0) {
                try (ResultSet rs = preparedStmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        ins = rs.getInt(1);  // Get the auto-generated ID
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDatabase();
        return ins;
    }

    @Override
    public boolean update(Menu o1, Menu o2) {
        connectDatabase();
        boolean upd = false;
        String query = "UPDATE `menu` SET `Name`= ?, `DATE`= ?, `UPDATE_AT`= ? WHERE `UniqueID`= ?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, o1.getName());
            preparedStmt.setDate(2, Date.valueOf(o1.getDate()));
            preparedStmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            preparedStmt.setInt(4,o2.getUniqueId());

            int update = preparedStmt.executeUpdate();
            if(update != -1) upd = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDatabase();
        return upd;
    }

    @Override
    public boolean delete(Menu o) {
        connectDatabase();
        boolean del = false;
        String query = "DELETE FROM `menu` WHERE `UniqueID`= ?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,o.getUniqueId());

            int delete = preparedStmt.executeUpdate();
            if(delete != -1) del = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDatabase();
        return del;
    }

    @Override
    public boolean isExist(Menu o) {
        return false;
    }

    @Override
    public ArrayList<Menu> getAll() {
        connectDatabase();
        ArrayList<Menu> list = new ArrayList<>();
        String query = "SELECT * FROM `menu` ;";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()){

                Menu menu = new Menu();
                menu.setUniqueId(resultSet.getInt("UniqueID"));
                menu.setName(resultSet.getString("Name"));
                menu.setDate(resultSet.getDate("DATE").toLocalDate());
                menu.setCreateAt(resultSet.getTimestamp("CREATE_AT").toLocalDateTime());
                menu.setUpdateAt(resultSet.getTimestamp("UPDATE_AT").toLocalDateTime());

                list.add(menu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDatabase();
        return list;
    }

    public Menu get(int id) {
        connectDatabase();
        Menu menu = new Menu();
        String query = " SELECT * FROM `menu` WHERE `UniqueID`= ?;";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,id);
            ResultSet resultSet = preparedStmt.executeQuery();
            if (resultSet.next()){

                menu.setUniqueId(resultSet.getInt("UniqueID"));
                menu.setName(resultSet.getString("Name"));
                menu.setDate(resultSet.getDate("DATE").toLocalDate());
                menu.setCreateAt(resultSet.getTimestamp("CREATE_AT").toLocalDateTime());
                menu.setUpdateAt(resultSet.getTimestamp("UPDATE_AT").toLocalDateTime());

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDatabase();
        return  menu;
    }

}
