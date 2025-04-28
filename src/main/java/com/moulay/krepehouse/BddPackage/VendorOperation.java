package com.moulay.krepehouse.BddPackage;

import com.moulay.krepehouse.Models.Food;
import com.moulay.krepehouse.Models.Vendor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class VendorOperation extends BDD<Vendor>{

    @Override
    public boolean insert(Vendor o) {
        connectDatabase();
        boolean ins = false;
        String query = "INSERT INTO `vendor`( `NAME`, `PHONE`, `USERNAME`, `PASSWORD` ) VALUES (?,?,?,?) ;";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1,o.getName());
            preparedStmt.setString(2,o.getPhone());
            preparedStmt.setString(3,o.getUsername());
            preparedStmt.setString(4,o.getPassword());

            int insert = preparedStmt.executeUpdate();
            if(insert != -1) ins = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDatabase();
        return ins;
    }

    public int insertId(Vendor o) {
        connectDatabase();
        int ins = 0;
        String query = "INSERT INTO `vendor`( `NAME`, `PHONE`, `USERNAME`, `PASSWORD` ) VALUES (?,?,?,?) ;";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1,o.getName());
            preparedStmt.setString(2,o.getPhone());
            preparedStmt.setString(3,o.getUsername());
            preparedStmt.setString(4,o.getPassword());

            int insert = preparedStmt.executeUpdate();
            if(insert != -1) ins = preparedStmt.getGeneratedKeys().getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDatabase();
        return ins;
    }

    @Override
    public boolean update(Vendor o1, Vendor o2) {
        connectDatabase();
        boolean upd = false;
        String query = "UPDATE `vendor` SET `NAME`= ?, `PHONE`= ?, `USERNAME`= ?, `PASSWORD`= ?, `UPDATE_AT`= ? WHERE `UniqueID`= ?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1,o1.getName());
            preparedStmt.setString(2,o1.getPhone());
            preparedStmt.setString(3,o1.getUsername());
            preparedStmt.setString(4,o1.getPassword());
            preparedStmt.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            preparedStmt.setInt(6,o2.getUniqueId());

            int update = preparedStmt.executeUpdate();
            if(update != -1) upd = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDatabase();
        return upd;
    }

    @Override
    public boolean delete(Vendor o) {
        connectDatabase();
        boolean del = false;
        String query = "DELETE FROM `vendor` WHERE `UniqueID`= ?";
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
    public boolean isExist(Vendor o) {
        return false;
    }

    @Override
    public ArrayList<Vendor> getAll() {
        connectDatabase();
        ArrayList<Vendor> list = new ArrayList<>();
        String query = "SELECT * FROM `vendor` WHERE `ARCHIVE`= 0;";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()){

                Vendor vendor = new Vendor();
                vendor.setUniqueId(resultSet.getInt("UniqueID"));
                vendor.setName(resultSet.getString("NAME"));
                vendor.setPhone(resultSet.getString("PHONE"));
                vendor.setUsername(resultSet.getString("USERNAME"));
                vendor.setPassword(resultSet.getString("PASSWORD"));
                vendor.setCreateAt(resultSet.getTimestamp("CREATE_AT").toLocalDateTime());
                vendor.setUpdateAt(resultSet.getTimestamp("UPDATE_AT").toLocalDateTime());

                list.add(vendor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDatabase();
        return list;
    }

    public Vendor isExistLogin(Vendor o) {
        connectDatabase();
        Vendor vendor = new Vendor();
        String query = " SELECT * FROM `vendor` WHERE  `USERNAME`= ? AND `PASSWORD`= ?;";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1,o.getUsername());
            preparedStmt.setString(2,o.getPassword());
            ResultSet resultSet = preparedStmt.executeQuery();
            if (resultSet.next()){

                vendor.setUniqueId(resultSet.getInt("UniqueID"));
                vendor.setName(resultSet.getString("NAME"));
                vendor.setPhone(resultSet.getString("PHONE"));
                vendor.setUsername(resultSet.getString("USERNAME"));
                vendor.setPassword(resultSet.getString("PASSWORD"));
                vendor.setCreateAt(resultSet.getTimestamp("CREATE_AT").toLocalDateTime());
                vendor.setUpdateAt(resultSet.getTimestamp("UPDATE_AT").toLocalDateTime());

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDatabase();
        return  vendor;
    }

    public Vendor get(int id) {
        connectDatabase();
        Vendor vendor = new Vendor();
        String query = " SELECT * FROM `food` WHERE `UniqueID`= ?;";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,id);
            ResultSet resultSet = preparedStmt.executeQuery();
            if (resultSet.next()){

                vendor.setUniqueId(resultSet.getInt("UniqueID"));
                vendor.setName(resultSet.getString("NAME"));
                vendor.setPhone(resultSet.getString("PHONE"));
                vendor.setUsername(resultSet.getString("USERNAME"));
                vendor.setPassword(resultSet.getString("PASSWORD"));
                vendor.setCreateAt(resultSet.getTimestamp("CREATE_AT").toLocalDateTime());
                vendor.setUpdateAt(resultSet.getTimestamp("UPDATE_AT").toLocalDateTime());

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDatabase();
        return  vendor;
    }

    public boolean AddToArchive(Vendor o) {
        connectDatabase();
        boolean upd = false;
        String query = "UPDATE `vendor` SET `ARCHIVE`= 1, `UPDATE_AT`= ? WHERE `UniqueID`= ?";

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

    public boolean DeleteFromArchive(Vendor o) {
        connectDatabase();
        boolean upd = false;
        String query = "UPDATE `vendor` SET `ARCHIVE`= 0, `UPDATE_AT`= ? WHERE `UniqueID`= ?";

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

    public ArrayList<Vendor> getAllArchive() {

        connectDatabase();
        ArrayList<Vendor> list = new ArrayList<>();
        String query = "SELECT * FROM `food` WHERE `ARCHIVE`= 1;";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()){

                Vendor vendor = new Vendor();
                vendor.setUniqueId(resultSet.getInt("UniqueID"));
                vendor.setName(resultSet.getString("NAME"));
                vendor.setPhone(resultSet.getString("PHONE"));
                vendor.setUsername(resultSet.getString("USERNAME"));
                vendor.setPassword(resultSet.getString("PASSWORD"));
                vendor.setCreateAt(resultSet.getTimestamp("CREATE_AT").toLocalDateTime());
                vendor.setUpdateAt(resultSet.getTimestamp("UPDATE_AT").toLocalDateTime());

                list.add(vendor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDatabase();
        return list;
    }

    public boolean isExistInBills(Vendor o) {
        connectDatabase();
        boolean ex = false;
        String query = "SELECT * FROM `bill` WHERE `UniqueID_VENDOR` = ?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,o.getUniqueId());
            ResultSet resultSet = preparedStmt.executeQuery();

            if (resultSet.next()){
                ex = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDatabase();
        return ex;
    }

}
