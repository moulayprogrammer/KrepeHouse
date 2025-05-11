module com.moulay.krepehouse {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires org.mariadb.jdbc;
    requires waffle.jna;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.desktop;
    requires javafx.swing;
    requires jasperreports;
    requires org.slf4j;
    requires net.coobird.thumbnailator;


    opens com.moulay.krepehouse to javafx.fxml;
    exports com.moulay.krepehouse;

    exports com.moulay.krepehouse.Controllers;
    opens com.moulay.krepehouse.Controllers to javafx.fxml;

    exports com.moulay.krepehouse.Models;
    opens com.moulay.krepehouse.Models to javafx.fxml;

    exports com.moulay.krepehouse.Controllers.FoodControllers;
    opens com.moulay.krepehouse.Controllers.FoodControllers to javafx.fxml;

    exports com.moulay.krepehouse.Controllers.VendorControllers;
    opens com.moulay.krepehouse.Controllers.VendorControllers to javafx.fxml;

    exports com.moulay.krepehouse.Controllers.BillControllers;
    opens com.moulay.krepehouse.Controllers.BillControllers to javafx.fxml;

    exports com.moulay.krepehouse.Controllers.MenuControllers;
    opens com.moulay.krepehouse.Controllers.MenuControllers to javafx.fxml;


}