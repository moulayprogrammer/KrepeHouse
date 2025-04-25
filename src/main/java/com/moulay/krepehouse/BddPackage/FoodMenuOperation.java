package com.moulay.krepehouse.BddPackage;


import com.moulay.krepehouse.Models.FoodMenu;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FoodMenuOperation extends BDD<FoodMenu>{

    @Override
    public boolean insert(FoodMenu o) {
        connectDatabase();
        boolean ins = false;
        String query = "INSERT INTO `food_menu` (`UniqueID_MENU`, `UniqueID_FOOD`) VALUES (?,?) ;";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,o.getUniqueIdMenu());
            preparedStmt.setInt(2,o.getUniqueIdFood());

            int insert = preparedStmt.executeUpdate();
            if(insert != -1) ins = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDatabase();
        return ins;
    }

    public int insertId(FoodMenu o) {
        connectDatabase();
        int ins = 0;
        String query = "INSERT INTO `food_menu` (`UniqueID_MENU`, `UniqueID_FOOD`) VALUES (?,?) ;";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,o.getUniqueIdMenu());
            preparedStmt.setInt(2,o.getUniqueIdFood());

            int insert = preparedStmt.executeUpdate();
            if(insert != -1) ins = preparedStmt.getGeneratedKeys().getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDatabase();
        return ins;
    }

    @Override
    public boolean update(FoodMenu o1, FoodMenu o2) {
        /*connectDatabase();
        boolean upd = false;
        String query = "UPDATE `food_bill` SET `UniqueID_BILL`= ?, `UniqueID_FOOD`= ?, `QTE`= ?, `TOTAL_PRICE`= ?," +
                " `UPDATE_AT`= ? WHERE `UniqueID`= ?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,o1.getUniqueIdVendor());
            preparedStmt.setInt(2,o1.getNumber());
            preparedStmt.setDate(3,Date.valueOf(o1.getDate()));
            preparedStmt.setTime(4,Time.valueOf(o1.getTime()));
            preparedStmt.setFloat(5,o1.getTotalPrice());
            preparedStmt.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            preparedStmt.setInt(7,o2.getUniqueId());

            int update = preparedStmt.executeUpdate();
            if(update != -1) upd = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDatabase();*/
        return false;
    }

    @Override
    public boolean delete(FoodMenu o) {
        connectDatabase();
        boolean del = false;
        String query = "DELETE FROM `food_menu` WHERE `UniqueID`= ?";
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
    public boolean isExist(FoodMenu o) {
        return false;
    }

    @Override
    public ArrayList<FoodMenu> getAll() {
        connectDatabase();
        ArrayList<FoodMenu> list = new ArrayList<>();
        String query = "SELECT * FROM `food_menu` ";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()){

                FoodMenu foodMenu = new FoodMenu();
                foodMenu.setUniqueId(resultSet.getInt("UniqueID"));
                foodMenu.setUniqueIdMenu(resultSet.getInt("UniqueID_MENU"));
                foodMenu.setUniqueIdFood(resultSet.getInt("UniqueID_FOOD"));
                foodMenu.setCreateAt(resultSet.getTimestamp("CREATE_AT").toLocalDateTime());
                foodMenu.setUpdateAt(resultSet.getTimestamp("UPDATE_AT").toLocalDateTime());

                list.add(foodMenu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDatabase();
        return list;
    }

    public boolean deleteAllByMenu(int idMenu) {
        connectDatabase();
        boolean del = false;
        String query = "DELETE FROM `food_menu` WHERE `UniqueID_MENU` = ?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,idMenu);

            int delete = preparedStmt.executeUpdate();
            if(delete != -1) del = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDatabase();
        return del;
    }
}
