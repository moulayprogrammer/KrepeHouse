package com.moulay.krepehouse.Controllers.BillControllers;

import com.moulay.krepehouse.BddPackage.BillOperation;
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
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddController implements Initializable {

    @FXML
    ScrollPane scroll;
    @FXML
    GridPane gridFood;
    @FXML
    TableView<Food> tableMenu;
    @FXML
    TableColumn<Food,String> clNameArMenu;
    @FXML
    TableColumn<Food, Image> clPictureMenu;
    @FXML
    TextField tfRechercheFood;
    @FXML
    DatePicker dpDate;
    @FXML
    Button btnInsert;

    private final BillOperation operation = new BillOperation();
    private final FoodMenuOperation foodMenuOperation = new FoodMenuOperation();
    private final FoodOperation foodOperation = new FoodOperation();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

//        dpDate.setValue(LocalDate.now());

//        tableFood.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableMenu.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

       /* clIdFood.setCellValueFactory(new PropertyValueFactory<>("uniqueId"));
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
        });*/

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
            dialog.initOwner(this.tfRechercheFood.getScene().getWindow());
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
        /*try {

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
        }*/
    }

    @FXML
    private void ActionDeleteFromMenu(){
        /*try {

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
        }*/
    }

    @FXML
    private void ActionRefreshFood(){
        tfRechercheFood.clear();
        refreshTableFood();
    }

    private void refreshTableFood(){

        try {

            System.out.println("refresh food");
            ArrayList<Food> foods = foodOperation.getAll();

            int col = 0;
            int row = 0;

            for (int i = 0; i < foods.size(); i++) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/moulay/krepehouse/BillView/itemlCard.fxml"));
                    AnchorPane temp = loader.load();
                    ItemController controller = loader.getController();
                    controller.Init(foods.get(i));

                    if (col == 3 ) {
                        col = 0;
                        row++;
                    }

                    gridFood.add(temp,col++,row);

                }catch (Exception e) {
                    e.printStackTrace();
                }
            }


            foods.forEach(food -> {


            });



        }catch (Exception e) {
            e.printStackTrace();
        }





        /*ObservableList<Food> foodObservableList = FXCollections.observableArrayList();
        foodObservableList.setAll(foods);

        foodObservableList.removeAll(tableMenu.getItems());

        tableFood.setItems(foodObservableList );*/
    }

    @FXML
    private void OnSave(){

       /* String name = tfName.getText().trim();
        LocalDate date = dpDate.getValue();

        if ( !name.isEmpty() && date != null && !tableMenu.getItems().isEmpty()){

            Menu menu = new Menu(name,date,true);

            int insert = operation.insertId(menu);
            tableMenu.getItems().forEach(food -> {
                FoodMenu foodMenu = new FoodMenu(insert,food.getUniqueId());
                foodMenuOperation.insert(foodMenu);
            });

            closeDialog(btnInsert);
        }else {
            labelAlert("من فضلك املأ كل الحقول الأساسية");
        }*/
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
