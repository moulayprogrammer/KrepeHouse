package com.moulay.krepehouse.Controllers.FoodControllers;

import com.moulay.krepehouse.BddPackage.FoodOperation;
import com.moulay.krepehouse.Models.Food;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
    private TextField tfNameAr,tfNameFr,tfPrice;
    @FXML
    TextArea taDesc;
    @FXML
    ImageView ivPicture;
    @FXML
    Label lbAlert;
    @FXML
    Button btnInsert;

    private final FoodOperation operation = new FoodOperation();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        // Listener for validating float input
        tfPrice.textProperty().addListener((obs, oldText, newText) -> {
            if (!newText.matches("-?\\d*(\\.\\d*)?")) {
                tfPrice.setText(oldText); // Revert to previous valid input
            }
        });
    }

    @FXML
    private void OnSaveStay(){
        String nameAr = tfNameAr.getText().trim();
        String nameFr = tfNameFr.getText().trim();
        String price = tfPrice.getText().trim();
        String desc = taDesc.getText().trim();
        Image image = ivPicture.getImage();


        if ( !nameAr.isEmpty() && !nameFr.isEmpty() && !price.isEmpty()){

            Food food = new Food(nameAr,nameFr,Float.parseFloat(price),desc,image);

            boolean ins = insert(food);
            if (!ins){
                labelAlert("حدث خطـــأ");
            }

        }else {
            labelAlert("من فضلك املأ كل الحقول الأساسية");
        }
    }

    @FXML
    private void OnSave(){

        String nameAr = tfNameAr.getText().trim();
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

    }

    private void labelAlert(String st){

        try {

            lbAlert.setText(st);
            FadeTransition ft = new FadeTransition(Duration.millis(2000), lbAlert);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.setCycleCount(2);
            ft.setAutoReverse(true);
            ft.play();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private boolean insert(Food food) {
        boolean insert = false;
        try {
            insert = operation.insert(food);
            return insert;
        }catch (Exception e){
            e.printStackTrace();
            return insert;
        }
    }

    @FXML
    private void OnClose(){
        closeDialog(btnInsert);
    }

    private void closeDialog(Button btn) {
        ((Stage)btn.getScene().getWindow()).close();
    }

    @FXML
    private void OnAddPicture(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        File imageFile = fileChooser.showOpenDialog( btnInsert.getScene().getWindow());

        if (imageFile != null) {
            Image image = new Image(imageFile.toURI().toString());
            ivPicture.setImage(image);
        }
    }

    @FXML
    public void OnDeletePicture(ActionEvent actionEvent) {
        ivPicture.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/moulay/krepehouse/Images/crepe.jpg"))));
    }
}
