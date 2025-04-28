package com.moulay.krepehouse.Controllers.MenuControllers;

import com.moulay.krepehouse.BddPackage.FoodMenuOperation;
import com.moulay.krepehouse.BddPackage.FoodOperation;
import com.moulay.krepehouse.BddPackage.MenuOperation;
import com.moulay.krepehouse.Models.Food;
import com.moulay.krepehouse.Models.FoodMenu;
import com.moulay.krepehouse.Models.Menu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UpdateController implements Initializable {


    @FXML
    TableView<Food> tableFood,tableMenu;
    @FXML
    TableColumn<Food,Integer> clIdFood;
    @FXML
    TableColumn<Food,String> clNameArFood,clNameFrFood,clNameArMenu;
    @FXML
    TableColumn<Food, Image> clPictureFood,clPictureMenu;
    @FXML
    TextField tfRechercheFood,tfName;
    @FXML
    DatePicker dpDate;
    @FXML
    Button btnUpdate;

    private final MenuOperation operation = new MenuOperation();
    private final FoodMenuOperation foodMenuOperation = new FoodMenuOperation();
    private final FoodOperation foodOperation = new FoodOperation();

    private Menu selectedMenu;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        tableFood.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableMenu.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        clIdFood.setCellValueFactory(new PropertyValueFactory<>("uniqueId"));
        clNameArFood.setCellValueFactory(new PropertyValueFactory<>("nameAr"));
        clNameFrFood.setCellValueFactory(new PropertyValueFactory<>("nameFr"));
        clPictureFood.setCellValueFactory(new PropertyValueFactory<>("picture"));

        // Customize image cell
        clPictureFood.setCellFactory(col -> new TableCell<>() {
            private final ImageView imageView = new ImageView();

            {
                imageView.setFitWidth(80);
                imageView.setFitHeight(50);
                imageView.setPreserveRatio(true);
            }

            @Override
            protected void updateItem(Image image, boolean empty) {
                super.updateItem(image, empty);
                if (empty || image == null) {
                    setGraphic(null);
                } else {
                    imageView.setImage(image);
                    setGraphic(imageView);
                }
            }
        });

        clNameArMenu.setCellValueFactory(new PropertyValueFactory<>("nameAr"));
        clPictureMenu.setCellValueFactory(new PropertyValueFactory<>("picture"));

        // Customize image cell
        clPictureMenu.setCellFactory(col -> new TableCell<>() {
            private final ImageView imageView = new ImageView();

            {
                imageView.setFitWidth(80);
                imageView.setFitHeight(50);
                imageView.setPreserveRatio(true);
            }

            @Override
            protected void updateItem(Image image, boolean empty) {
                super.updateItem(image, empty);
                if (empty || image == null) {
                    setGraphic(null);
                } else {
                    imageView.setImage(image);
                    setGraphic(imageView);
                }
            }
        });

//        refreshTableFood();

    }

    public void Init(Menu menu){
        this.selectedMenu = menu;

        tfName.setText(menu.getName());
        dpDate.setValue(menu.getDate());

        ArrayList<Food> foods = foodOperation.getAllFoodByMenu(menu.getUniqueId());
        ObservableList<Food> foodObservableList = FXCollections.observableArrayList();
        foodObservableList.setAll(foods);

        foodObservableList.removeAll(tableMenu.getItems());

        tableMenu.setItems(foodObservableList );

        refreshTableFood();

    }

    @FXML
    private void ActionAddFood(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/moulay/krepehouse/FoodView/addView.fxml"));
            DialogPane temp = loader.load();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(temp);
            dialog.resizableProperty().setValue(false);
            dialog.initOwner(this.tableFood.getScene().getWindow());
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
            Node closeButton = dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
            closeButton.setVisible(false);
            dialog.showAndWait();

            refreshTableFood();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void tableFoodClick(MouseEvent mouseEvent) {
        if ( mouseEvent.getClickCount() == 2 && mouseEvent.getButton().equals(MouseButton.PRIMARY) ) ActionAddToMenu();
    }

    @FXML
    private void tableMenuClick(MouseEvent mouseEvent) {
        if ( mouseEvent.getClickCount() == 2 && mouseEvent.getButton().equals(MouseButton.PRIMARY) ) ActionDeleteFromMenu();
    }
    @FXML
    private void ActionAddToMenu(){
        try {

            ObservableList<Food> selectedItems = tableFood.getSelectionModel().getSelectedItems();

            if (!selectedItems.isEmpty()) {
                try {
                    tableMenu.getItems().addAll(selectedItems);
                    tableFood.getItems().removeAll(selectedItems);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Alert alertWarning = new Alert(Alert.AlertType.WARNING);
                alertWarning.setHeaderText("تحذير");
                alertWarning.setContentText("من فضلك قم بتحديد ما تريد تعديله");
                alertWarning.initOwner(this.tableFood.getScene().getWindow());
                Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
                okButton.setText("موافق");
                alertWarning.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void ActionDeleteFromMenu(){
        try {

            ObservableList<Food> selectedItems = tableMenu.getSelectionModel().getSelectedItems();

            if (!selectedItems.isEmpty()) {
                try {

                    tableFood.getItems().addAll(selectedItems);
                    tableMenu.getItems().removeAll(selectedItems);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Alert alertWarning = new Alert(Alert.AlertType.WARNING);
                alertWarning.setHeaderText("تحذير");
                alertWarning.setContentText("من فضلك قم بتحديد ما تريد تعديله");
                alertWarning.initOwner(this.tableFood.getScene().getWindow());
                Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
                okButton.setText("موافق");
                alertWarning.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void ActionRefreshFood(){
        tfRechercheFood.clear();
        refreshTableFood();
    }



    private void refreshTableFood(){
        ArrayList<Food> foods = foodOperation.getAll();
        ObservableList<Food> foodObservableList = FXCollections.observableArrayList();
        foodObservableList.setAll(foods);

        foodObservableList.removeAll(tableMenu.getItems());

        tableFood.setItems(foodObservableList );
    }


    @FXML
    private void OnSave(){

        String name = tfName.getText().trim();
        LocalDate date = dpDate.getValue();

        if ( !name.isEmpty() && date != null && !tableMenu.getItems().isEmpty()){

            Menu menu = new Menu(name,date, selectedMenu.isSelect());

            operation.update(menu,selectedMenu);
            foodMenuOperation.deleteAllByMenu(selectedMenu.getUniqueId());
            tableMenu.getItems().forEach(food -> {
                FoodMenu foodMenu = new FoodMenu(selectedMenu.getUniqueId(),food.getUniqueId());
                foodMenuOperation.insert(foodMenu);
            });

            closeDialog(btnUpdate);

        }else {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("تحذير");
            alertWarning.setContentText("قم بادخال كل الخانات");
            alertWarning.initOwner(this.tableFood.getScene().getWindow());
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("موافق");
            alertWarning.showAndWait();
        }
    }

    @FXML
    private void OnClose(){
        closeDialog(btnUpdate);
    }

    private void closeDialog(Button btn) {
        ((Stage)btn.getScene().getWindow()).close();
    }
}
