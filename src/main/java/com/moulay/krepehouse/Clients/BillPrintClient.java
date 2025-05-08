package com.moulay.krepehouse.Clients;

import com.moulay.krepehouse.BddPackage.BillOperation;
import com.moulay.krepehouse.BddPackage.VendorOperation;
import com.moulay.krepehouse.Models.Vendor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import java.awt.print.PrinterJob;
import java.io.*;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;

public class BillPrintClient implements Runnable{

    private final Socket billPrintSocket;
    private final BillOperation operation = new BillOperation();

    BufferedReader in;


    public BillPrintClient(Socket socket) throws IOException {
        this.billPrintSocket = socket;
    }

    @Override
    public void run() {
        try {
            // Initialize streams
            in = new BufferedReader(
                    new InputStreamReader(billPrintSocket.getInputStream())
            );

            String msg = in.readLine();
            if (!msg.isEmpty()){
                System.out.println("Received from client: " + msg);

                String path = System.getProperty("user.dir")+"/src/main/resources/com/moulay/krepehouse/Jasper/Invoice1.jrxml";
                JasperDesign design = JRXmlLoader.load(path);

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
                        "WHERE b.UniqueID = "+ msg ;

                JRDesignQuery designQuery = new JRDesignQuery();
                designQuery.setText(sql);
                design.setQuery(designQuery);

                JasperReport jasperReport = JasperCompileManager.compileReport(design);
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,null, operation.getConn());

                // 5. Direct Print
                JasperPrintManager.printReport(jasperPrint, false);
            }

            closeResources();

        } catch (IOException | JRException e) {
            log("Error with client connection: " + e.getMessage());
            e.printStackTrace();
        } finally {
//            closeResources();
        }
    }

    private String processInput(String input) {
        // Add your business logic here
        // This is a simple echo server with added processing
        return "Server response: " + input.toUpperCase();
    }

    private void closeResources() {
        try {
            if (in != null) in.close();
            if (billPrintSocket != null) billPrintSocket.close();
            if (!operation.getConn().isClosed()) operation.closeDatabase();
        } catch (IOException | SQLException e) {
           log("Error closing client resources: " + e.getMessage());
        }
    }

    // Make this package-private if ClientHandler is in the same package
    public void log(String message) {
        System.out.println("log server : " + message);
    }
}
