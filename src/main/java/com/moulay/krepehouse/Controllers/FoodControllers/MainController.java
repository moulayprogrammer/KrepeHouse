package com.moulay.krepehouse.Controllers.FoodControllers;

import com.moulay.krepehouse.BddPackage.FoodOperation;
import com.moulay.krepehouse.Models.Food;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
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
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private Label lbNumber;
    @FXML
    private TextField tfRecherche;

    @FXML
    TableView<Food> table;
    @FXML
    TableColumn<Food,Integer> clId;
    @FXML
    TableColumn<Food,String> clNameAr,clNameFr;
    @FXML
    TableColumn<Food,Double> clPrice;
    @FXML
    TableColumn<Food, Image> clPicture;


    private final FoodOperation operation = new FoodOperation();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

//        vboxOption.setVisible(false);

        clId.setCellValueFactory(new PropertyValueFactory<>("uniqueId"));
        clNameAr.setCellValueFactory(new PropertyValueFactory<>("nameAr"));
        clNameFr.setCellValueFactory(new PropertyValueFactory<>("nameFr"));
        clPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
        clPicture.setCellValueFactory(new PropertyValueFactory<>("picture"));

        // Customize image cell
        clPicture.setCellFactory(col -> new javafx.scene.control.TableCell<>() {
            private final ImageView imageView = new ImageView();

            {
                imageView.setFitWidth(100);
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

        tfRecherche.textProperty().addListener((observableValue, s, t1) -> {
            if (!t1.isEmpty()) ActionSearch();
            else refresh();
        });

        refresh();
    }

    @FXML
    private void OnAdd(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/moulay/krepehouse/FoodView/addView.fxml"));
            DialogPane temp = loader.load();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(temp);
            dialog.resizableProperty().setValue(false);
            dialog.initOwner(this.tfRecherche.getScene().getWindow());
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
            Node closeButton = dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
            closeButton.setVisible(false);
            dialog.showAndWait();

            refresh();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void OnUpdate() {
        try {

            Food selectedFood = table.getSelectionModel().getSelectedItem();

            if (selectedFood != null) {
                try {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/moulay/krepehouse/FoodView/updateView.fxml"));
                    DialogPane temp = loader.load();
                    UpdateController controller = loader.getController();
                    controller.Init(selectedFood);
                    Dialog<ButtonType> dialog = new Dialog<>();
                    dialog.setDialogPane(temp);
                    dialog.resizableProperty().setValue(false);
                    dialog.initOwner(this.tfRecherche.getScene().getWindow());
                    dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
                    Node closeButton = dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
                    closeButton.setVisible(false);
                    dialog.showAndWait();

                    refresh();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Alert alertWarning = new Alert(Alert.AlertType.WARNING);
                alertWarning.setHeaderText("تحذير");
                alertWarning.setContentText("من فضلك قم بتحديد ما تريد تعديله");
                alertWarning.initOwner(this.tfRecherche.getScene().getWindow());
                Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
                okButton.setText("موافق");
                alertWarning.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void OnDelete(ActionEvent actionEvent) {

        try {

            Food selectedFood = table.getSelectionModel().getSelectedItem();

            if (selectedFood != null) {
                try {

                    if (!operation.isExistInBills(selectedFood) && !operation.isExistInMenu(selectedFood)){
                        operation.delete(selectedFood);
                        refresh();
                    }else {
                        Alert alertWarning = new Alert(Alert.AlertType.WARNING);
                        alertWarning.setHeaderText("تحذير");
                        alertWarning.setContentText("الوجبة تم استعمالها في طلب او قائمة طعام");
                        alertWarning.initOwner(this.tfRecherche.getScene().getWindow());
                        Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
                        okButton.setText("موافق");
                        alertWarning.showAndWait();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Alert alertWarning = new Alert(Alert.AlertType.WARNING);
                alertWarning.setHeaderText("تحذير");
                alertWarning.setContentText("من فضلك قم بتحديد ما تريد حذفه");
                alertWarning.initOwner(this.tfRecherche.getScene().getWindow());
                Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
                okButton.setText("موافق");
                alertWarning.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void ActionSearch() {
        try {
            // filtrer les données
            ObservableList<Food> dataFacture = table.getItems();
            FilteredList<Food> filteredData = new FilteredList<>(dataFacture, e -> true);
            String txtRecherche = tfRecherche.getText().trim();

            filteredData.setPredicate(food -> {
                if (txtRecherche.isEmpty()) {
                    refresh();
                    return true;
                } else if (food.getNameAr().contains(txtRecherche)) {

                    return true;
                } else if (food.getNameFr().contains(txtRecherche)) {
                    return true;
                } else if (food.getDescription().contains(txtRecherche)) {
                    return true;
                } else return String.valueOf(food.getPrice()).contains(txtRecherche);
            });

            SortedList<Food> sortedList = new SortedList<>(filteredData);
            sortedList.comparatorProperty().bind(table.comparatorProperty());
            table.setItems(sortedList);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void tableClick(MouseEvent mouseEvent) {
        if ( mouseEvent.getClickCount() == 2 && mouseEvent.getButton().equals(MouseButton.PRIMARY) ) OnUpdate();
    }

    @FXML
    private void ActionRefresh(){
        tfRecherche.clear();
        refresh();
    }

    private void refresh(){
        ArrayList<Food> foods = operation.getAll();
        ObservableList<Food> foodObservableList = FXCollections.observableArrayList();
        foodObservableList.addAll(foods);
        table.setItems(foodObservableList );

        lbNumber.setText(String.valueOf(foods.size()));
    }


}
