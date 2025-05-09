package com.moulay.krepehouse.Controllers.BillControllers;

import com.moulay.krepehouse.Models.Food;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class ItemController implements Initializable {

    @FXML
    private Button btnAdd;

    @FXML
    private ImageView ivPicture;

    @FXML
    private Label lbNameAr;

    @FXML
    private Label lbNameFr;

    @FXML
    private Label lbPrice;

    @FXML
    private Spinner<Integer> spinner;


    private Food food;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

    public void Init(Food food){

        this.food = food;

        lbNameAr.setText(food.getNameAr());
        lbNameFr.setText(food.getNameFr());
        lbPrice.setText(String.format("%,.2f دج", food.getPrice()));
        ivPicture.setImage(food.getPicture());
    }
}
