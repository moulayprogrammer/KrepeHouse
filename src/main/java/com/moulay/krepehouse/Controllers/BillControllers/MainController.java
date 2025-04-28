package com.moulay.krepehouse.Controllers.BillControllers;

import com.moulay.krepehouse.BddPackage.MenuOperation;
import com.moulay.krepehouse.Models.Menu;
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
import javafx.scene.layout.VBox;

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
    TableView<Menu> table;
    @FXML
    TableColumn<Menu,Integer> clId;
    @FXML
    TableColumn<Menu,String> clName;
    @FXML
    TableColumn<Menu, LocalDate> clDate;


    private final MenuOperation operation = new MenuOperation();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        clId.setCellValueFactory(new PropertyValueFactory<>("uniqueId"));
        clName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        tfRecherche.textProperty().addListener((observableValue, s, t1) -> {
            if (!t1.isEmpty()) ActionSearch();
            else refresh();
        });

        refresh();
    }

    @FXML
    private void OnAdd(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/moulay/krepehouse/MenuView/addView.fxml"));
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

            Menu selectedMenu = table.getSelectionModel().getSelectedItem();

            if (selectedMenu != null) {
                try {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/moulay/krepehouse/MenuView/updateView.fxml"));
                    DialogPane temp = loader.load();
                    UpdateController controller = loader.getController();
                    controller.Init(selectedMenu);
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

             Menu selectedMenu = table.getSelectionModel().getSelectedItem();

            if (selectedMenu != null) {
                try {
                    operation.delete(selectedMenu);
                    refresh();

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
            ObservableList<Menu> dataFacture = table.getItems();
            FilteredList<Menu> filteredData = new FilteredList<>(dataFacture, e -> true);
            String txtRecherche = tfRecherche.getText().trim();

            filteredData.setPredicate(food -> {
                if (txtRecherche.isEmpty()) {
                    refresh();
                    return true;
                } else if (food.getName().contains(txtRecherche)) {

                    return true;
                } else return  (food.getDate().format( DateTimeFormatter.ofPattern("dd-MM-yyyy")).contains(txtRecherche));
            });

            SortedList<Menu> sortedList = new SortedList<>(filteredData);
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
        ArrayList<Menu> menus = operation.getAll();
        ObservableList<Menu> menuObservableList = FXCollections.observableArrayList();
        menuObservableList.addAll(menus);
        table.setItems(menuObservableList );

//        lbNumber.setText(String.valueOf(menus.size()));
    }


}
