package com.moulay.krepehouse.Controllers.BillControllers;

import com.moulay.krepehouse.BddPackage.BillOperation;
import com.moulay.krepehouse.BddPackage.MenuOperation;
import com.moulay.krepehouse.Models.Bill;
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
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.awt.print.PrinterJob;
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

        tfRecherche.textProperty().addListener((observableValue, s, t1) -> {
            if (!t1.isEmpty()) ActionSearch();
            else refresh();
        });

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
        /*try {

            Bill selectedMenu = table.getSelectionModel().getSelectedItem();

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
        }*/
    }

    @FXML
    private void OnDelete(ActionEvent actionEvent) {

        /*try {

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
        }*/
    }

    @FXML
    void ActionSearch() {
        /*try {
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
        }*/
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
        ArrayList<Bill> bills = operation.getAll();
        ObservableList<Bill> billObservableList = FXCollections.observableArrayList();
        billObservableList.addAll(bills);
        table.setItems(billObservableList );

        lbNumber.setText(String.valueOf(bills.size()));
    }

    @FXML
    private void OnPrint(){

        try {

            Bill selectedBill = table.getSelectionModel().getSelectedItem();

            if (selectedBill != null) {
                try {

                    String path = System.getProperty("user.dir")+"/src/main/resources/com/moulay/krepehouse/Jasper/Invoice1.jrxml";
                    JasperDesign design = JRXmlLoader.load(path);

                    if (operation.getConn().isClosed()) operation.connectDatabase();

                    String sql = "SELECT\n" +
                            "    b.NUMBER AS Number,\n" +
                            "    b.DATE AS Date,\n" +
                            "    f.name_ar AS NameAr,\n" +
                            "    fb.QTE AS Qte,\n" +
                            "    f.PRICE AS Price,\n" +
                            "    fb.TOTAL_PRICE AS FoodTotal,\n" +
                            "    b.TOTAL_PRICE AS BillTotal\n" +
                            "FROM bill AS b\n" +
                            "INNER JOIN food_bill AS fb \n" +
                            "    ON b.UniqueID = fb.UniqueID_BILL\n" +
                            "INNER JOIN food AS f \n" +
                            "    ON fb.UniqueID_FOOD = f.UniqueID\n" +
                            "WHERE b.UniqueID = "+selectedBill.getUniqueId() ;

                    JRDesignQuery designQuery = new JRDesignQuery();
                    designQuery.setText(sql);
                    design.setQuery(designQuery);

                    JasperReport jasperReport = JasperCompileManager.compileReport(design);
                    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,null, operation.getConn());

                    JasperViewer.viewReport(jasperPrint,false);

                    if (!operation.getConn().isClosed()) operation.closeDatabase();

                    // 5. Direct Print
//                    PrinterJob printerJob = PrinterJob.getPrinterJob();
//                    JasperPrintManager.printReport(jasperPrint, false);
                    /*if (printerJob.printDialog()) {

                    }*/

                } catch (Exception e) {
                    e.printStackTrace();
                }
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
