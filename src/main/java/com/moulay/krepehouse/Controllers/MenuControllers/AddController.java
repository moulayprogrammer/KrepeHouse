package com.moulay.krepehouse.Controllers.MenuControllers;

import com.moulay.krepehouse.BddPackage.FoodOperation;
import com.moulay.krepehouse.BddPackage.MenuOperation;
import com.moulay.krepehouse.Models.Food;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class AddController implements Initializable {


    @FXML
    TableView<Food> tableFood,tableMenu;
    @FXML
    TableColumn<Food,Integer> clIdFood;
    @FXML
    TableColumn<Food,String> clNameArFood,clNameFrFood,clNameArMenu;
    @FXML
    TableColumn<Food, Image> clPictureFood,clPictureMenu;
    @FXML
    Button btnInsert;

    private final MenuOperation operation = new MenuOperation();
    private final FoodOperation foodOperation = new FoodOperation();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private void refreshTableFood(){

    }

    @FXML
    private void OnSaveStay(){
       /* String nameAr = tfNameAr.getText().trim();
        String nameFr = tfNameFr.getText().trim();
        String price = tfPrice.getText().trim();
        String desc = taDesc.getText().trim();
        Image image = ivPicture.getImage();


        if ( !nameAr.isEmpty() && !nameFr.isEmpty() && !price.isEmpty()){

            Food food = new Food(nameAr,nameFr,Float.parseFloat(price),desc,image);

            boolean ins = insert(food);
            if (ins){
                tfNameAr.clear();
                tfNameFr.clear();
                tfPrice.clear();
                taDesc.clear();
                OnDeletePicture();
            }else labelAlert("حدث خطـــأ");

        }else {
            labelAlert("من فضلك املأ كل الحقول الأساسية");
        }*/
    }

    @FXML
    private void OnSave(){

       /* String nameAr = tfNameAr.getText().trim();
        String nameFr = tfNameFr.getText().trim();
        String price = tfPrice.getText().trim();
        String desc = taDesc.getText().trim();
        Image image = ivPicture.getImage();


        if ( !nameAr.isEmpty() && !nameFr.isEmpty() && !price.isEmpty() ){


            Food food = new Food(nameAr,nameFr,Float.parseFloat(price),desc,image);


            boolean ins = insert(food);
            if (ins){
                closeDialog(btnInsert);
            }else {
                labelAlert("حدث خطـــأ");
            }

        }else {
            labelAlert("من فضلك املأ كل الحقول الأساسية");
        }
*/
    }

    private void labelAlert(String st){

       /* try {

            lbAlert.setText(st);
            FadeTransition ft = new FadeTransition(Duration.millis(2000), lbAlert);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.setCycleCount(2);
            ft.setAutoReverse(true);
            ft.play();

        }catch (Exception e){
            e.printStackTrace();
        }*/
    }

    private boolean insert(Food food) {
        boolean insert = false;
        /*try {
            insert = operation.insert(food);
            return insert;
        }catch (Exception e){
            e.printStackTrace();
            return insert;
        }*/
        return insert;
    }

    @FXML
    private void OnClose(){
        closeDialog(btnInsert);
    }

    private void closeDialog(Button btn) {
        ((Stage)btn.getScene().getWindow()).close();
    }
}
