package com.moulay.krepehouse.Controllers.BillControllers;

import com.moulay.krepehouse.BddPackage.ConnectBD;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Connection;

public class PrintController {

    private final ConnectBD connectBD = new ConnectBD();
    private JasperPrint jasperPrint;


    public void printPrepare(int idBill){

        try {

            String path = System.getProperty("user.dir")+"/src/main/resources/com/moulay/krepehouse/Jasper/Invoice.jrxml";
            JasperDesign design = JRXmlLoader.load(path);

            Connection connection = connectBD.connect();

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
                    "WHERE b.UniqueID = "+ idBill ;

            JRDesignQuery designQuery = new JRDesignQuery();
            designQuery.setText(sql);
            design.setQuery(designQuery);

            JasperReport jasperReport = JasperCompileManager.compileReport(design);
            jasperPrint = JasperFillManager.fillReport(jasperReport,null, connection);

            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewPrint(){
        try {
            JasperViewer.viewReport(jasperPrint,false);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void directPrintWithDialog(){
        try {
            JasperPrintManager.printReport(jasperPrint, true);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void directPrint(){
        try {
            JasperPrintManager.printReport(jasperPrint, false);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
