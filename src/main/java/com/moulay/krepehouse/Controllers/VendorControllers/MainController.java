package com.moulay.krepehouse.Controllers.VendorControllers;


import com.moulay.krepehouse.BddPackage.VendorOperation;
import com.moulay.krepehouse.Models.Food;
import com.moulay.krepehouse.Models.Vendor;
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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private Label lbNumber;
    @FXML
    private TextField tfRecherche;

    @FXML
    TableView<Vendor> table;
    @FXML
    TableColumn<Vendor,Integer> clId;
    @FXML
    TableColumn<Vendor,String> clName,clPhone;
    @FXML
    TableColumn<Vendor,LocalDate> clDate;


    private final VendorOperation operation = new VendorOperation();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        table.setEditable(true);
        clId.setCellValueFactory(new PropertyValueFactory<>("uniqueId"));
        clName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        clDate.setCellValueFactory(new PropertyValueFactory<>("dateJoined"));
        // Set a cell factory with number formatting
        clDate.setCellFactory(tc -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/moulay/krepehouse/VendorView/addView.fxml"));
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

            Vendor selectedVendor = table.getSelectionModel().getSelectedItem();

            if (selectedVendor != null) {
                try {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/moulay/krepehouse/VendorView/updateView.fxml"));
                    DialogPane temp = loader.load();
                    UpdateController controller = loader.getController();
                    controller.Init(selectedVendor);
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

            Vendor selectedVendor = table.getSelectionModel().getSelectedItem();

            if (selectedVendor != null) {
                try {

                    if (!operation.isExistInBills(selectedVendor)){
                        Alert alertWarning = new Alert(Alert.AlertType.CONFIRMATION);
                        alertWarning.setHeaderText("الحذف");
                        alertWarning.setContentText("هل انت متاكد من الحذف النهائي");
                        alertWarning.initOwner(this.tfRecherche.getScene().getWindow());
                        Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
                        okButton.setText("موافق");
                        okButton.setOnAction(actionEvent1 -> {
                            operation.delete(selectedVendor);
                            refresh();
                        });
                        Button Button = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.CANCEL);
                        Button.setText("إلغاء");
                        Button.setOnAction(actionEvent1 -> alertWarning.close());
                        alertWarning.showAndWait();

                    }else {
                        Alert alertWarning = new Alert(Alert.AlertType.WARNING);
                        alertWarning.setHeaderText("تحذير");
                        alertWarning.setContentText("العامل قام بعدة طلبيات لا يمكن حذفه");
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
            ObservableList<Vendor> tableItems = table.getItems();
            FilteredList<Vendor> filteredData = new FilteredList<>(tableItems, e -> true);
            String txtRecherche = tfRecherche.getText().trim();

            filteredData.setPredicate(vendor -> {
                if (txtRecherche.isEmpty()) {
                    refresh();
                    return true;
                } else if (vendor.getName().contains(txtRecherche)) {

                    return true;
                } else return vendor.getPhone().contains(txtRecherche);
            });

            SortedList<Vendor> sortedList = new SortedList<>(filteredData);
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
        ArrayList<Vendor> vendors = operation.getAll();
        ObservableList<Vendor> vendorObservableList = FXCollections.observableArrayList();
        vendorObservableList.addAll(vendors);
        table.setItems(vendorObservableList );

        lbNumber.setText(String.valueOf(vendors.size()));
    }


}
