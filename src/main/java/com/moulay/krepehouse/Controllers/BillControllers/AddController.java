package com.moulay.krepehouse.Controllers.BillControllers;

import com.moulay.krepehouse.BddPackage.*;
import com.moulay.krepehouse.Models.Food;
import com.moulay.krepehouse.Models.FoodBillTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class AddController implements Initializable {


    @FXML
    TableView<Food> tableFood;
    @FXML
    TableView<FoodBillTable> tableBill;
    @FXML
    TableColumn<Food,Integer> clIdFood;
    @FXML
    TableColumn<Food,String> clNameArFood,clNameFrFood;
    @FXML
    TableColumn<FoodBillTable,String> clNameArBill;
    @FXML
    TableColumn<Food, Image> clPictureFood;
    @FXML
    TableColumn<Food, Float> clPriceFood;
    @FXML
    TableColumn<FoodBillTable,Integer> clQteBill;
    @FXML
    TableColumn<FoodBillTable, Float> clTotalBill;
    @FXML
    TextField tfRechercheFood,tfNumber;
    @FXML
    DatePicker dpDate;
    @FXML
    Label lbTotalBill;
    @FXML
    Button btnInsert;
    float totalBill;

    private final BillOperation operation = new BillOperation();
    private final FoodBillOperation foodMenuOperation = new FoodBillOperation();
    private final FoodOperation foodOperation = new FoodOperation();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Set the date format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        dpDate.setConverter(new StringConverter<>() {
            @Override
            public String toString(LocalDate date) {
                return date != null ? formatter.format(date) : "";
            }
            @Override
            public LocalDate fromString(String string) {
                return string != null && !string.isEmpty()
                        ? LocalDate.parse(string, formatter)
                        : null;
            }
        });
        dpDate.setValue(LocalDate.now());

        tableFood.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableBill.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        clIdFood.setCellValueFactory(new PropertyValueFactory<>("uniqueId"));
        clNameArFood.setCellValueFactory(new PropertyValueFactory<>("nameAr"));
        clNameFrFood.setCellValueFactory(new PropertyValueFactory<>("nameFr"));
        clPriceFood.setCellValueFactory(new PropertyValueFactory<>("Price"));
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

        // Set a cell factory with number formatting
        clPriceFood.setCellFactory(tc -> new TableCell<>() {
            @Override
            protected void updateItem(Float item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("%,.2f دج", item));
                }
            }
        });

        clNameArBill.setCellValueFactory(new PropertyValueFactory<>("nameAr"));
        clQteBill.setCellValueFactory(new PropertyValueFactory<>("qte"));
        clTotalBill.setCellValueFactory(new PropertyValueFactory<>("PriceTotal"));
        clTotalBill.setCellFactory(tc -> new TableCell<>() {
            @Override
            protected void updateItem(Float item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("%,.2f دج", item));
                }
            }
        });

        refreshTableFood();
        setBillNumber();
        refreshTotal();

        tfRechercheFood.textProperty().addListener((observableValue, s, t1) -> {
            if (!t1.isEmpty()) ActionSearch();
            else refreshTableFood();
        });
    }

    private void setBillNumber(){
        int number = operation.getLastNumber();
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
        if ( mouseEvent.getClickCount() == 2 && mouseEvent.getButton().equals(MouseButton.PRIMARY) ) ActionAddToBill();
    }

    @FXML
    private void tableMenuClick(MouseEvent mouseEvent) {
        if ( mouseEvent.getClickCount() == 2 && mouseEvent.getButton().equals(MouseButton.PRIMARY) ) ActionDeleteFromBill();
    }
    @FXML
    private void tableFoodKeyPressed(KeyEvent keyEvent){
        if (keyEvent.getCode() == KeyCode.ENTER || keyEvent.getCode() == KeyCode.ADD)  ActionAddToBill();
    }
    @FXML
    private void ActionAddToBill(){
        try {

            ObservableList<Food> selectedItems = tableFood.getSelectionModel().getSelectedItems();

            if (!selectedItems.isEmpty()) {
                try {

                    Map<Integer, FoodBillTable> billTableHashMap = new HashMap<>();

                    // Populate HashMap for O(1) lookups
                    for (FoodBillTable obj : tableBill.getItems()) {
                        billTableHashMap.put(obj.getUniqueId(), obj);
                    }

                    for (Food food : selectedItems) {
                        if (billTableHashMap.containsKey(food.getUniqueId())) {
                            // Increment existing item's qte
                            int newQte = billTableHashMap.get(food.getUniqueId()).getQte() + 1;
                            float newTotal = newQte * food.getPrice();
                            billTableHashMap.get(food.getUniqueId()).setQte(newQte);
                            billTableHashMap.get(food.getUniqueId()).setPriceTotal(newTotal);
                        } else {
                            // Add new item with qte=1
                            FoodBillTable billTable = new FoodBillTable(food);
                            billTable.setQte(1);
                            billTable.setPriceTotal(food.getPrice());
                            tableBill.getItems().add(billTable);
                            billTableHashMap.put(billTable.getUniqueId(), billTable);  // Update HashMap
                        }
                    }
                    tableFood.getSelectionModel().clearSelection();
                    tableBill.refresh();
                    refreshTotal();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Alert alertWarning = new Alert(Alert.AlertType.WARNING);
                alertWarning.setHeaderText("تحذير");
                alertWarning.setContentText("من فضلك قم بتحديد ما تريد اضافته");
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
    private void OnKeyTyped(KeyEvent keyEvent) {

        if (keyEvent.getCode() == KeyCode.ADD){
            ObservableList<FoodBillTable> selectedItems = tableBill.getSelectionModel().getSelectedItems();
            if (!selectedItems.isEmpty()){
                if (!(selectedItems.size() > 1)){
                    int newQte = selectedItems.get(0).getQte() + 1;
                    float newTotal = selectedItems.get(0).getPrice() * newQte;

                    selectedItems.get(0).setQte(newQte);
                    selectedItems.get(0).setPriceTotal(newTotal);

                    tableBill.refresh();
                    refreshTotal();
                }
            }
        }

        if (keyEvent.getCode() == KeyCode.SUBTRACT || keyEvent.getCode() == KeyCode.MINUS){
            ObservableList<FoodBillTable> selectedItems = tableBill.getSelectionModel().getSelectedItems();
            if (!selectedItems.isEmpty()){
                if (!(selectedItems.size() > 1)){
                    int newQte = selectedItems.get(0).getQte() - 1;
                    if (newQte > 0) {
                        float newTotal = selectedItems.get(0).getPrice() * newQte;

                        selectedItems.get(0).setQte(newQte);
                        selectedItems.get(0).setPriceTotal(newTotal);
                        tableBill.refresh();
                        refreshTotal();
                    }else ActionDeleteFromBill();
                }
            }
        }

        if (keyEvent.getCode() == KeyCode.BACK_SPACE || keyEvent.getCode() == KeyCode.DELETE){
            ActionDeleteFromBill();
        }
    }

    @FXML
    private void ActionDeleteFromBill(){
        try {

            ObservableList<FoodBillTable> selectedItems = tableBill.getSelectionModel().getSelectedItems();

            if (!selectedItems.isEmpty()) {
                try {

                    tableBill.getItems().removeAll(selectedItems);
                    refreshTotal();

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

    private void refreshTotal(){
        ObservableList<FoodBillTable> selectedItems = tableBill.getItems();
        totalBill = 0;
        if (!selectedItems.isEmpty()){
            for (int i = 0; i < selectedItems.size(); i++) {
                totalBill += selectedItems.get(i).getPriceTotal();
            }
        }
        lbTotalBill.setText(String.format("%,.2f دج", totalBill));
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

//        foodObservableList.removeAll(tableBill.getItems());

        tableFood.setItems(foodObservableList );
    }

    @FXML
    void ActionSearch() {
        try {
            // filtrer les données
            ObservableList<Food> dataFacture = tableFood.getItems();
            FilteredList<Food> filteredData = new FilteredList<>(dataFacture, e -> true);
            String txtRecherche = tfRechercheFood.getText().trim();

            filteredData.setPredicate(food -> {
                if (txtRecherche.isEmpty()) {
                    refreshTableFood();
                    return true;
                } else if (food.getNameAr().contains(txtRecherche)) {

                    return true;
                } else return  (food.getNameFr().contains(txtRecherche));
            });

            SortedList<Food> sortedList = new SortedList<>(filteredData);
            sortedList.comparatorProperty().bind(tableFood.comparatorProperty());
            tableFood.setItems(sortedList);

        }catch (Exception e){
            e.printStackTrace();
        }
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
