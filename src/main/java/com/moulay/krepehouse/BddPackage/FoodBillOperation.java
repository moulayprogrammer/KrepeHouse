package com.moulay.krepehouse.BddPackage;

import com.moulay.krepehouse.Models.Bill;
import com.moulay.krepehouse.Models.FoodBill;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class FoodBillOperation extends BDD<FoodBill>{

    @Override
    public boolean insert(FoodBill o) {
        connectDatabase();
        boolean ins = false;
        String query = "INSERT INTO `food_bill` (`UniqueID_BILL`, `UniqueID_FOOD`, `QTE`, `TOTAL_PRICE`) VALUES (?,?,?,?) ;";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,o.getUniqueIdBill());
            preparedStmt.setInt(2,o.getUniqueIdFood());
            preparedStmt.setInt(3, o.getQte());
            preparedStmt.setFloat(4,o.getTotalPrice());

            int insert = preparedStmt.executeUpdate();
            if(insert != -1) ins = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDatabase();
        return ins;
    }

    public int insertId(FoodBill o) {
        connectDatabase();
        int ins = 0;
        String query = "INSERT INTO `food_bill` (`UniqueID_BILL`, `UniqueID_FOOD`, `QTE`, `TOTAL_PRICE`) VALUES (?,?,?,?) ;";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,o.getUniqueIdBill());
            preparedStmt.setInt(2,o.getUniqueIdFood());
            preparedStmt.setInt(3, o.getQte());
            preparedStmt.setFloat(4,o.getTotalPrice());

            int insert = preparedStmt.executeUpdate();
            if(insert != -1) ins = preparedStmt.getGeneratedKeys().getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDatabase();
        return ins;
    }

    @Override
    public boolean update(FoodBill o1, FoodBill o2) {
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

    public boolean updateQte(int id, int qte) {
        connectDatabase();
        boolean upd = false;
        String query = "UPDATE `food_bill` SET `QTE`= ?, `UPDATE_AT`= ? WHERE `UniqueID`= ?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,qte);
            preparedStmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            preparedStmt.setInt(3,id);

            int update = preparedStmt.executeUpdate();
            if(update != -1) upd = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDatabase();
        return false;
    }

    @Override
    public boolean delete(FoodBill o) {
        connectDatabase();
        boolean del = false;
        String query = "DELETE FROM `food_bill` WHERE `UniqueID`= ?";
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

    public boolean deleteAllByBill(Bill o) {
        connectDatabase();
        boolean del = false;
        String query = "DELETE FROM `food_bill` WHERE `UniqueID_BILL`= ?";
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
    public boolean isExist(FoodBill o) {
        return false;
    }

    @Override
    public ArrayList<FoodBill> getAll() {
        connectDatabase();
        ArrayList<FoodBill> list = new ArrayList<>();
        String query = "SELECT * FROM `food_bill` ";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()){

                FoodBill foodBill = new FoodBill();
                foodBill.setUniqueId(resultSet.getInt("UniqueID"));
                foodBill.setUniqueIdBill(resultSet.getInt("UniqueID_BILL"));
                foodBill.setUniqueIdFood(resultSet.getInt("UniqueID_FOOD"));
                foodBill.setQte(resultSet.getInt("QTE"));
                foodBill.setTotalPrice(resultSet.getFloat("TOTAL_PRICE"));
                foodBill.setCreateAt(resultSet.getTimestamp("CREATE_AT").toLocalDateTime());
                foodBill.setUpdateAt(resultSet.getTimestamp("UPDATE_AT").toLocalDateTime());

                list.add(foodBill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDatabase();
        return list;
    }

    public ArrayList<FoodBill> getAllByBill(int idBill) {
        connectDatabase();
        ArrayList<FoodBill> list = new ArrayList<>();
        String query = "SELECT * FROM `food_bill` WHERE `UniqueID_BILL`= ?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,idBill);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()){

                FoodBill foodBill = new FoodBill();
                foodBill.setUniqueId(resultSet.getInt("UniqueID"));
                foodBill.setUniqueIdBill(resultSet.getInt("UniqueID_BILL"));
                foodBill.setUniqueIdFood(resultSet.getInt("UniqueID_FOOD"));
                foodBill.setQte(resultSet.getInt("QTE"));
                foodBill.setTotalPrice(resultSet.getFloat("TOTAL_PRICE"));
                foodBill.setCreateAt(resultSet.getTimestamp("CREATE_AT").toLocalDateTime());
                foodBill.setUpdateAt(resultSet.getTimestamp("UPDATE_AT").toLocalDateTime());

                list.add(foodBill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDatabase();
        return list;
    }
}
