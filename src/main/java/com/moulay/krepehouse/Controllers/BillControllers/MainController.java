package com.moulay.krepehouse.Controllers.BillControllers;

import com.moulay.krepehouse.BddPackage.BillOperation;
import com.moulay.krepehouse.Models.Bill;
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
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private Label lbNumber,lbTotal;
    @FXML
    private TextField tfRecherche;
    @FXML
    private DatePicker dpFrom,dpTo;

    @FXML
    TableView<Bill> table;
    @FXML
    TableColumn<Bill,Integer> clNumber;
    @FXML
    TableColumn<Bill,Double> clTotal;
    @FXML
    TableColumn<Bill, LocalDate> clDate;


    private final BillOperation operation = new BillOperation();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        clNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
        clTotal.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        clDate.setCellValueFactory(new PropertyValueFactory<>("date"));
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

        // Set the date format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        dpFrom.setConverter(new StringConverter<>() {
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
        dpFrom.setValue(LocalDate.now());
        dpTo.setConverter(new StringConverter<>() {
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
        dpTo.setValue(LocalDate.now());

        refresh();
    }

    @FXML
    private void OnAdd(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/moulay/krepehouse/BillView/addView.fxml"));
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

            Bill selectedBill = table.getSelectionModel().getSelectedItem();

            if (selectedBill != null) {
                try {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/moulay/krepehouse/BillView/updateView.fxml"));
                    DialogPane temp = loader.load();
                    UpdateController controller = loader.getController();
                    controller.Init(selectedBill);
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

             Bill selectedItem = table.getSelectionModel().getSelectedItem();

            if (selectedItem != null) {
                try {

                    Alert alertWarning = new Alert(Alert.AlertType.CONFIRMATION);
                    alertWarning.setHeaderText("الحذف");
                    alertWarning.setContentText("هل انت متاكد من الالغاء النهائي");
                    alertWarning.initOwner(this.tfRecherche.getScene().getWindow());
                    Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
                    okButton.setText("موافق");
                    okButton.setOnAction(actionEvent1 -> {
                        operation.delete(selectedItem);
                        refresh();
                    });
                    Button Button = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.CANCEL);
                    Button.setText("إلغاء");
                    Button.setOnAction(actionEvent1 -> alertWarning.close());
                    alertWarning.showAndWait();

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
            ObservableList<Bill> dataFacture = table.getItems();
            FilteredList<Bill> filteredData = new FilteredList<>(dataFacture, e -> true);
            String txtRecherche = tfRecherche.getText().trim();

            filteredData.setPredicate(bill -> {
                if (txtRecherche.isEmpty()) {
                    refresh();
                    return true;
                } else if (String.valueOf(bill.getNumber()).contains(txtRecherche)) {

                    return true;
                } else return  (bill.getDate().format( DateTimeFormatter.ofPattern("yyyy/MM/dd")).contains(txtRecherche));
            });

            SortedList<Bill> sortedList = new SortedList<>(filteredData);
            sortedList.comparatorProperty().bind(table.comparatorProperty());
            table.setItems(sortedList);
            refreshTotal();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void tableClick(MouseEvent mouseEvent) {
        if ( mouseEvent.getClickCount() == 2 && mouseEvent.getButton().equals(MouseButton.PRIMARY) ) OnUpdate();
    }

    @FXML
    private void ActionSearchDate(){
        try {
            LocalDate dateFrom = dpFrom.getValue();
            LocalDate dateTo = dpTo.getValue();

            if (dateFrom != null && dateTo != null && (dateFrom.isBefore(dateTo) || dateFrom.isEqual(dateFrom))){

                ArrayList<Bill> bills;
                if (dateFrom.isEqual(dateTo)){
                    bills = operation.getAllByDate(dateFrom);
                }else {
                    bills = operation.getAllBetweenDate(dateFrom,dateTo);
                }
                ObservableList<Bill> billObservableList = FXCollections.observableArrayList();
                billObservableList.addAll(bills);
                table.setItems(billObservableList );
                refreshTotal();
            }else {
                Alert alertWarning = new Alert(Alert.AlertType.WARNING);
                alertWarning.setHeaderText("تحذير");
                alertWarning.setContentText("من فضلك قم بتحديد التواريخ");
                alertWarning.initOwner(this.tfRecherche.getScene().getWindow());
                Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
                okButton.setText("موافق");
                alertWarning.showAndWait();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void refresh(){
        ArrayList<Bill> bills = operation.getAll();
        ObservableList<Bill> billObservableList = FXCollections.observableArrayList();
        billObservableList.addAll(bills);
        table.setItems(billObservableList );

        lbNumber.setText(String.valueOf(bills.size()));
        refreshTotal();
    }

    private void refreshTotal(){
        try {
            if (table.getItems().size() != 0) {
                float total = 0;
                for (int i = 0; i < table.getItems().size(); i++) {
                    total += table.getItems().get(i).getTotalPrice();
                }
                lbTotal.setText(String.format("%,.2f دج", total));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void OnPrint(){

        try {

            Bill selectedBill = table.getSelectionModel().getSelectedItem();

            if (selectedBill != null) {
                PrintController printController = new PrintController();
                printController.printPrepare(selectedBill.getUniqueId());
                printController.viewPrint();
            } else {
                Alert alertWarning = new Alert(Alert.AlertType.WARNING);
                alertWarning.setHeaderText("تحذير");
                alertWarning.setContentText("من فضلك قم بتحديد ما تريد طباعته");
                alertWarning.initOwner(this.tfRecherche.getScene().getWindow());
                Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
                okButton.setText("موافق");
                alertWarning.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
