package com.moulay.krepehouse.BddPackage;

import com.moulay.krepehouse.Models.Food;
import com.moulay.krepehouse.Models.FoodBillTable;
import com.moulay.krepehouse.Models.SimpleFood;
import javafx.scene.image.Image;
import java.awt.image.BufferedImage;
import javafx.embed.swing.SwingFXUtils;
import net.coobird.thumbnailator.Thumbnails;


import javax.imageio.ImageIO;
import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class FoodOperation extends BDD<Food>{

    @Override
    public boolean insert(Food o) {
        connectDatabase();
        boolean ins = false;
        String query = "INSERT INTO `food`( `NAME_AR`, `NAME_FR`, `PRICE`, `DESCRIPTION`, `PICTURE`) VALUES (?,?,?,?,?) ;";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1,o.getNameAr());
            preparedStmt.setString(2,o.getNameFr());
            preparedStmt.setFloat(3,o.getPrice());
            preparedStmt.setString(4,o.getDescription());

                // Convert JavaFX Image to BufferedImage
            Image fxImage = o.getPicture();
            BufferedImage bImage = SwingFXUtils.fromFXImage(fxImage, null);

            // Convert BufferedImage to byte array
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bImage, "png", baos); // use "jpg" if your image is jpg
            byte[] imageBytes = baos.toByteArray();

            preparedStmt.setBytes(5,compressJavaFXImage(o.getPicture()));

            int insert = preparedStmt.executeUpdate();
            if(insert != -1) ins = true;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        closeDatabase();
        return ins;
    }

    @Override
    public boolean update(Food o1, Food o2) {
        return false;
    }

    public int insertId(Food o) {
        connectDatabase();
        int ins = 0;
        String query = "INSERT INTO `food`( `NAME_AR`, `NAME_FR`, `PRICE`, `DESCRIPTION`, `PICTURE`) VALUES (?,?,?,?,?) ;";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1,o.getNameAr());
            preparedStmt.setString(2,o.getNameFr());
            preparedStmt.setFloat(3,o.getPrice());
            preparedStmt.setString(4,o.getDescription());

            // Convert JavaFX Image to BufferedImage
            Image fxImage = o.getPicture();
            BufferedImage bImage = SwingFXUtils.fromFXImage(fxImage, null);

            // Convert BufferedImage to byte array
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bImage, "png", baos); // use "jpg" if your image is jpg
            byte[] imageBytes = baos.toByteArray();

            preparedStmt.setBytes(5,imageBytes);

            int insert = preparedStmt.executeUpdate();
            if(insert != -1) ins = preparedStmt.getGeneratedKeys().getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        closeDatabase();
        return ins;
    }

    public boolean update(Food o1, Food o2, boolean changeImage) {
        connectDatabase();
        boolean upd = false;
        String query = "UPDATE `food` SET `NAME_AR`= ?, `NAME_FR`= ?, `PRICE`= ?, `DESCRIPTION`= ?, `PICTURE`= ?, " +
                "`UPDATE_AT`= ? WHERE `UniqueID`= ?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1,o1.getNameAr());
            preparedStmt.setString(2,o1.getNameFr());
            preparedStmt.setFloat(3,o1.getPrice());
            preparedStmt.setString(4,o1.getDescription());

            byte[] imageBytes;

            if (changeImage){
                imageBytes = compressJavaFXImage(o1.getPicture());
            }else {

                // Convert JavaFX Image to BufferedImage
                Image fxImage = o1.getPicture();
                BufferedImage bImage = SwingFXUtils.fromFXImage(fxImage, null);

                // Convert BufferedImage to byte array
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(bImage, "png", baos); // use "jpg" if your image is jpg
                imageBytes = baos.toByteArray();
            }

            preparedStmt.setBytes(5,imageBytes);

            preparedStmt.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            preparedStmt.setInt(7,o2.getUniqueId());

            int update = preparedStmt.executeUpdate();
            if(update != -1) upd = true;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        closeDatabase();
        return upd;
    }

    @Override
    public boolean delete(Food o) {
        connectDatabase();
        boolean del = false;
        String query = "DELETE FROM `food` WHERE `UniqueID`= ?";
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
    public boolean isExist(Food o) {
        return false;
    }

    @Override
    public ArrayList<Food> getAll() {
        connectDatabase();
        ArrayList<Food> list = new ArrayList<>();
        String query = "SELECT * FROM `food` WHERE `ARCHIVE`= 0;";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()){

                Food food = new Food();
                food.setUniqueId(resultSet.getInt("UniqueID"));
                food.setNameAr(resultSet.getString("NAME_AR"));
                food.setNameFr(resultSet.getString("NAME_FR"));
                food.setPrice(resultSet.getFloat("PRICE"));
                food.setDescription(resultSet.getString("DESCRIPTION"));

                byte[] bytes = resultSet.getBytes("PICTURE");
                InputStream is = new ByteArrayInputStream(bytes);
                food.setPicture(new Image(is));

                food.setCreateAt(resultSet.getTimestamp("CREATE_AT").toLocalDateTime());
                food.setUpdateAt(resultSet.getTimestamp("UPDATE_AT").toLocalDateTime());

                list.add(food);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDatabase();
        return list;
    }

    public Food get(int id) {
        connectDatabase();
        Food food = new Food();
        String query = " SELECT * FROM `food` WHERE `UniqueID`= ?;";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,id);
            ResultSet resultSet = preparedStmt.executeQuery();
            if (resultSet.next()){

                food.setUniqueId(resultSet.getInt("UniqueID"));
                food.setNameAr(resultSet.getString("NAME_AR"));
                food.setNameFr(resultSet.getString("NAME_FR"));
                food.setPrice(resultSet.getFloat("PRICE"));
                food.setDescription(resultSet.getString("DESCRIPTION"));

                byte[] bytes = resultSet.getBytes("PICTURE");
                InputStream is = new ByteArrayInputStream(bytes);
                food.setPicture(new Image(is));

                food.setCreateAt(resultSet.getTimestamp("CREATE_AT").toLocalDateTime());
                food.setUpdateAt(resultSet.getTimestamp("UPDATE_AT").toLocalDateTime());

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDatabase();
        return  food;
    }

    public boolean AddToArchive(Food o) {
        connectDatabase();
        boolean upd = false;
        String query = "UPDATE `food` SET `ARCHIVE`= 1, `UPDATE_AT`= ? WHERE `UniqueID`= ?";

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

    public boolean DeleteFromArchive(Food o) {
        connectDatabase();
        boolean upd = false;
        String query = "UPDATE `food` SET `ARCHIVE`= 0, `UPDATE_AT`= ? WHERE `UniqueID`= ?";

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

    public ArrayList<Food> getAllArchive() {

        connectDatabase();
        ArrayList<Food> list = new ArrayList<>();
        String query = "SELECT * FROM `food` WHERE `ARCHIVE`= 1;";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()){

                Food food = new Food();
                food.setUniqueId(resultSet.getInt("UniqueID"));
                food.setNameAr(resultSet.getString("NAME_AR"));
                food.setNameFr(resultSet.getString("NAME_FR"));
                food.setPrice(resultSet.getFloat("PRICE"));
                food.setDescription(resultSet.getString("DESCRIPTION"));

                byte[] bytes = resultSet.getBytes("PICTURE");
                InputStream is = new ByteArrayInputStream(bytes);
                food.setPicture(new Image(is));

                food.setCreateAt(resultSet.getTimestamp("CREATE_AT").toLocalDateTime());
                food.setUpdateAt(resultSet.getTimestamp("UPDATE_AT").toLocalDateTime());

                list.add(food);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDatabase();
        return list;
    }

    public boolean isExistInBills(Food o) {
        connectDatabase();
        boolean ex = false;
        String query = "SELECT * FROM `food_bill` WHERE `UniqueID_FOOD` = ?;";
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

    public boolean isExistInMenu(Food o) {
        connectDatabase();
        boolean ex = false;
        String query = "SELECT * FROM `food_menu` WHERE `UniqueID_FOOD` = ?;";
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

    public ArrayList<Food> getAllFoodByMenu(int idMenu) {

        connectDatabase();
        ArrayList<Food> list = new ArrayList<>();
        String query = "SELECT `food`.`UniqueID` , `NAME_AR`, `NAME_FR`, `PRICE`, `DESCRIPTION`, `PICTURE`,\n" +
                "`ARCHIVE`,`food`.`CREATE_AT`,`food`.`UPDATE_AT`\n" +
                "FROM `food` \n" +
                "JOIN `food_menu` ON `food`.`UniqueID` = `food_menu`.`UniqueID_FOOD`\n" +
                "JOIN `menu` ON `menu`.`UniqueID` = `food_menu`.`UniqueID_MENU`\n" +
                "WHERE `menu`.`UniqueID` = ?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,idMenu);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()){

                Food food = new Food();
                food.setUniqueId(resultSet.getInt("UniqueID"));
                food.setNameAr(resultSet.getString("NAME_AR"));
                food.setNameFr(resultSet.getString("NAME_FR"));
                food.setPrice(resultSet.getFloat("PRICE"));
                food.setDescription(resultSet.getString("DESCRIPTION"));

                byte[] bytes = resultSet.getBytes("PICTURE");
                InputStream is = new ByteArrayInputStream(bytes);
                food.setPicture(new Image(is));

                food.setCreateAt(resultSet.getTimestamp("CREATE_AT").toLocalDateTime());
                food.setUpdateAt(resultSet.getTimestamp("UPDATE_AT").toLocalDateTime());

                list.add(food);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDatabase();
        return list;
    }

    public ArrayList<SimpleFood> getAllFoodByMenuSelected() {

        connectDatabase();
        ArrayList<SimpleFood> list = new ArrayList<>();
        String query = "SELECT `food`.`UniqueID` , `NAME_AR`, `NAME_FR`, `PRICE`, `DESCRIPTION`, `PICTURE`,\n" +
                "`ARCHIVE`,`food`.`CREATE_AT`,`food`.`UPDATE_AT`\n" +
                "FROM `food` \n" +
                "JOIN `food_menu` ON `food`.`UniqueID` = `food_menu`.`UniqueID_FOOD`\n" +
                "JOIN `menu` ON `menu`.`UniqueID` = `food_menu`.`UniqueID_MENU`\n" +
                "WHERE `menu`.`SELECTED` = TRUE";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()){

                SimpleFood food = new SimpleFood();
                food.setUniqueId(resultSet.getInt("UniqueID"));
                food.setNameAr(resultSet.getString("NAME_AR"));
                food.setNameFr(resultSet.getString("NAME_FR"));
                food.setPrice(resultSet.getFloat("PRICE"));
                food.setDescription(resultSet.getString("DESCRIPTION"));

                food.setPicture(resultSet.getBytes("PICTURE"));

                food.setCreateAt(resultSet.getTimestamp("CREATE_AT").toLocalDateTime());
                food.setUpdateAt(resultSet.getTimestamp("UPDATE_AT").toLocalDateTime());

                list.add(food);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDatabase();
        return list;
    }

    public ArrayList<FoodBillTable> getAllFoodByBill(int idBill) {

        connectDatabase();
        ArrayList<FoodBillTable> list = new ArrayList<>();
        String query = "SELECT `food`.`UniqueID` , `food`.`NAME_AR`, `food`.`NAME_FR`, `food`.`PRICE`, `food_bill`.`TOTAL_PRICE`," +
                " `food_bill`.`QTE` FROM `bill`" +
                "INNER JOIN food_bill ON bill.UniqueID = food_bill.UniqueID_BILL\n" +
                "INNER JOIN food ON food_bill.UniqueID_FOOD = food.UniqueID\n" +
                "WHERE `bill`.`UniqueID` = ?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,idBill);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()){

                FoodBillTable food = new FoodBillTable();
                food.setUniqueId(resultSet.getInt("UniqueID"));
                food.setNameAr(resultSet.getString("NAME_AR"));
                food.setNameFr(resultSet.getString("NAME_FR"));
                food.setPrice(resultSet.getFloat("PRICE"));
                food.setPriceTotal(resultSet.getFloat("TOTAL_PRICE"));
                food.setQte(resultSet.getInt("QTE"));

                list.add(food);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDatabase();
        return list;
    }

    public byte[] compressJavaFXImage(Image javafxImage)
            throws IOException {

        // Convert JavaFX Image to BufferedImage
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(javafxImage, null);

        // Compress using Thumbnailator and write to byte array
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Thumbnails.of(bufferedImage)
                    .size(300, 150)  // Target dimensions
                    .outputFormat("JPEG")              // Output format (JPEG for lossy compression)
                    .outputQuality(0.5)            // Quality (0.0-1.0)
                    .toOutputStream(outputStream);

            return outputStream.toByteArray();
        }
    }
}
