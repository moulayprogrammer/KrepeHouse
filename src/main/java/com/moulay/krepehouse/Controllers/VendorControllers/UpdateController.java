package com.moulay.krepehouse.Controllers.VendorControllers;

import com.moulay.krepehouse.BddPackage.VendorOperation;
import com.moulay.krepehouse.Models.Vendor;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.StringConverter;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;

public class UpdateController implements Initializable {

    @FXML
    private TextField tfName,tfPhone,tfUser,tfPass,tfPassConfirm;
    @FXML
    private DatePicker dpDate;
    @FXML
    Label lbAlert;
    @FXML
    Button btnUpdate;

    private final VendorOperation operation = new VendorOperation();

    private Vendor selectedVendor;


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
    }

    public void Init(Vendor selectedVendor){
        this.selectedVendor = selectedVendor;

        tfName.setText(selectedVendor.getName());
        tfPhone.setText(selectedVendor.getPhone());
        tfUser.setText(this.selectedVendor.getUsername());
        tfPass.setText(selectedVendor.getPassword());
        tfPassConfirm.setText(selectedVendor.getPassword());
        if (selectedVendor.getDateJoined() != null) dpDate.setValue(selectedVendor.getDateJoined());

    }

    @FXML
    private void OnSave(){

        String name = tfName.getText().trim();
        String phone = tfPhone.getText().trim();
        String user = tfUser.getText().trim();
        String pass = tfPass.getText().trim();
        String passConfirm = tfPassConfirm.getText().trim();
        LocalDate date = dpDate.getValue();

        if ( !name.isEmpty() && !user.isEmpty() && !pass.isEmpty() && !passConfirm.isEmpty()){

            if (passConfirm.equals(pass)) {

                Vendor vendor = new Vendor(name, phone, date, user, pass);

                boolean update = update(vendor);

                if (update) {
                    closeDialog(btnUpdate);
                } else {
                    labelAlert("حدث خطـــأ");
                }
            }else labelAlert("كلمة المرور غير مطابقة للتأكيد الرجاء التحقق" );

        }else {
            labelAlert("من فضلك املأ كل الحقول الأساسية");
        }
    }

    private void labelAlert(String st){

        try {

           /* lbAlert.setText(st);
            FadeTransition ft = new FadeTransition(Duration.millis(2000), lbAlert);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.setCycleCount(2);
            ft.setAutoReverse(true);
            ft.play();*/

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private boolean update(Vendor vendor) {
        boolean update = false;
        try {
            update = operation.update(vendor, selectedVendor);
            return update;
        }catch (Exception e){
            e.printStackTrace();
            return update;
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
