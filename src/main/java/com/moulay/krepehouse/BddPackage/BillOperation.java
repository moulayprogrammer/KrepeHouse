package com.moulay.krepehouse.BddPackage;

import com.moulay.krepehouse.Models.Bill;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class BillOperation extends BDD<Bill>{

    @Override
    public boolean insert(Bill o) {
        connectDatabase();
        boolean ins = false;
        String query = "INSERT INTO `bill`(`UniqueID_VENDOR`, `NUMBER`, `DATE`, `TIME`, `TOTAL_PRICE`) VALUES (?,?,?,?,?) ;";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,o.getUniqueIdVendor());
            preparedStmt.setInt(2,o.getNumber());
            preparedStmt.setDate(3, Date.valueOf(o.getDate()));
            preparedStmt.setTime(4,Time.valueOf(o.getTime()));
            preparedStmt.setFloat(5,o.getTotalPrice());

            int insert = preparedStmt.executeUpdate();
            if(insert != -1) ins = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDatabase();
        return ins;
    }

    public int insertId(Bill o) {
        connectDatabase();
        int ins = 0;
        String query = "INSERT INTO `bill`(`UniqueID_VENDOR`, `NUMBER`, `DATE`, `TIME`, `TOTAL_PRICE`) VALUES (?,?,?,?,?) ;";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStmt.setInt(1,o.getUniqueIdVendor());
            preparedStmt.setInt(2,o.getNumber());
            preparedStmt.setDate(3, Date.valueOf(o.getDate()));
            preparedStmt.setTime(4,Time.valueOf(o.getTime()));
            preparedStmt.setFloat(5,o.getTotalPrice());

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
    public boolean update(Bill o1, Bill o2) {
        connectDatabase();
        boolean upd = false;
        String query = "UPDATE `bill` SET `UniqueID_VENDOR`= ?, `NUMBER`= ?, `DATE`= ?, `TIME`= ?, `TOTAL_PRICE`= ?," +
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
        closeDatabase();
        return upd;
    }

    @Override
    public boolean delete(Bill o) {
        connectDatabase();
        boolean del = false;
        String query = "DELETE FROM `bill` WHERE `UniqueID`= ?";
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
    public boolean isExist(Bill o) {
        return false;
    }

    @Override
    public ArrayList<Bill> getAll() {
        connectDatabase();
        ArrayList<Bill> list = new ArrayList<>();
        String query = "SELECT * FROM `bill` WHERE `ARCHIVE`= 0;";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()){

                Bill bill = new Bill();
                bill.setUniqueId(resultSet.getInt("UniqueID"));
                bill.setUniqueIdVendor(resultSet.getInt("UniqueID_VENDOR"));
                bill.setNumber(resultSet.getInt("NUMBER"));
                bill.setDate(resultSet.getDate("DATE").toLocalDate());
                bill.setTime(resultSet.getTime("TIME").toLocalTime());
                bill.setTotalPrice(resultSet.getFloat("TOTAL_PRICE"));
                bill.setCreateAt(resultSet.getTimestamp("CREATE_AT").toLocalDateTime());
                bill.setUpdateAt(resultSet.getTimestamp("UPDATE_AT").toLocalDateTime());

                list.add(bill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDatabase();
        return list;
    }

    public Bill get(int id) {
        connectDatabase();
        Bill bill = new Bill();
        String query = " SELECT * FROM `bill` WHERE `UniqueID`= ?;";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,id);
            ResultSet resultSet = preparedStmt.executeQuery();
            if (resultSet.next()){

                bill.setUniqueId(resultSet.getInt("UniqueID"));
                bill.setUniqueIdVendor(resultSet.getInt("UniqueID_VENDOR"));
                bill.setNumber(resultSet.getInt("NUMBER"));
                bill.setDate(resultSet.getDate("DATE").toLocalDate());
                bill.setTime(resultSet.getTime("TIME").toLocalTime());
                bill.setTotalPrice(resultSet.getFloat("TOTAL_PRICE"));
                bill.setCreateAt(resultSet.getTimestamp("CREATE_AT").toLocalDateTime());
                bill.setUpdateAt(resultSet.getTimestamp("UPDATE_AT").toLocalDateTime());

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDatabase();
        return  bill;
    }

    public boolean AddToArchive(Bill o) {
        connectDatabase();
        boolean upd = false;
        String query = "UPDATE `bill` SET `ARCHIVE`= 1, `UPDATE_AT`= ? WHERE `UniqueID`= ?";

        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            preparedStmt.setInt(2,o.getUniqueId());

            int update = preparedStmt.executeUpdate();
            if(update != -1) upd = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDatabase();
        return upd;
    }

    public boolean DeleteFromArchive(Bill o) {
        connectDatabase();
        boolean upd = false;
        String query = "UPDATE `bill` SET `ARCHIVE`= 0, `UPDATE_AT`= ? WHERE `UniqueID`= ?";

        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            preparedStmt.setInt(2,o.getUniqueId());

            int update = preparedStmt.executeUpdate();
            if(update != -1) upd = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDatabase();
        return upd;
    }

    public ArrayList<Bill> getAllArchive() {

        connectDatabase();
        ArrayList<Bill> list = new ArrayList<>();
        String query = "SELECT * FROM `bill` WHERE `ARCHIVE`= 1;";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()){

                Bill bill = new Bill();
                bill.setUniqueId(resultSet.getInt("UniqueID"));
                bill.setUniqueIdVendor(resultSet.getInt("UniqueID_VENDOR"));
                bill.setNumber(resultSet.getInt("NUMBER"));
                bill.setDate(resultSet.getDate("DATE").toLocalDate());
                bill.setTime(resultSet.getTime("TIME").toLocalTime());
                bill.setTotalPrice(resultSet.getFloat("TOTAL_PRICE"));
                bill.setCreateAt(resultSet.getTimestamp("CREATE_AT").toLocalDateTime());
                bill.setUpdateAt(resultSet.getTimestamp("UPDATE_AT").toLocalDateTime());

                list.add(bill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDatabase();
        return list;
    }

    public int getLastNumber() {

        connectDatabase();
        int n = 0;
        String query = "SELECT `NUMBER` FROM `bill` ORDER BY `CREATE_AT` DESC LIMIT 1;";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            if (resultSet.next()){

                n = resultSet.getInt("NUMBER");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDatabase();
        return n;
    }

    public ArrayList<Bill> getAllByDate(LocalDate date) {
        connectDatabase();
        ArrayList<Bill> list = new ArrayList<>();
        String query = "SELECT * FROM `bill` WHERE `ARCHIVE`= 0 AND `DATE` = ?;";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setDate(1,Date.valueOf(date));
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()){

                Bill bill = new Bill();
                bill.setUniqueId(resultSet.getInt("UniqueID"));
                bill.setUniqueIdVendor(resultSet.getInt("UniqueID_VENDOR"));
                bill.setNumber(resultSet.getInt("NUMBER"));
                bill.setDate(resultSet.getDate("DATE").toLocalDate());
                bill.setTime(resultSet.getTime("TIME").toLocalTime());
                bill.setTotalPrice(resultSet.getFloat("TOTAL_PRICE"));
                bill.setCreateAt(resultSet.getTimestamp("CREATE_AT").toLocalDateTime());
                bill.setUpdateAt(resultSet.getTimestamp("UPDATE_AT").toLocalDateTime());

                list.add(bill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDatabase();
        return list;
    }

    public ArrayList<Bill> getAllBetweenDate(LocalDate dateFrom, LocalDate dateTo) {
        connectDatabase();
        ArrayList<Bill> list = new ArrayList<>();
        String query = "SELECT * FROM `bill` WHERE `ARCHIVE`= 0 AND `DATE` BETWEEN ? AND ?;";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setDate(1,Date.valueOf(dateFrom));
            preparedStmt.setDate(2,Date.valueOf(dateTo));
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()){

                Bill bill = new Bill();
                bill.setUniqueId(resultSet.getInt("UniqueID"));
                bill.setUniqueIdVendor(resultSet.getInt("UniqueID_VENDOR"));
                bill.setNumber(resultSet.getInt("NUMBER"));
                bill.setDate(resultSet.getDate("DATE").toLocalDate());
                bill.setTime(resultSet.getTime("TIME").toLocalTime());
                bill.setTotalPrice(resultSet.getFloat("TOTAL_PRICE"));
                bill.setCreateAt(resultSet.getTimestamp("CREATE_AT").toLocalDateTime());
                bill.setUpdateAt(resultSet.getTimestamp("UPDATE_AT").toLocalDateTime());

                list.add(bill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDatabase();
        return list;
    }
}