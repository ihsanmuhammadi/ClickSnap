/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DB.KoneksiDB;
import Model.Product;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import martmain.Riwayat;

/**
 *
 * @author achma
 */
public class AdminController {
    private Connection connection;
    private Riwayat rwyt;
    private Product prdkm;
    
    public AdminController(Riwayat rwyt) {
        this.rwyt = rwyt;
        KoneksiDB koneksiDB = new KoneksiDB();
        koneksiDB.bukaKoneksi();
        this.connection = koneksiDB.getConn();
    }

    
    public void tampilkan_data() {
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("No.");
            model.addColumn("Nama user");
            model.addColumn("Produk ID");
            model.addColumn("Tanggal");
            model.addColumn("Jumlah");
            model.addColumn("Total Harga");

            try {
                int no = 1;
                String name = "SELECT * FROM users";
//                executeQuery();
//                String sql = "SELECT * FROM transactions";
//                String sql = "SELECT * FROM transactions";
                String sql = "SELECT transactions.*, users.name AS namauser, product.nama as productnama\n" +
                            "FROM transactions\n" +
                            "INNER JOIN users ON transactions.userId = users.id\n" +
                            "INNER JOIN product ON transactions.productId = product.id";
//                  String sql = "SELECT *\n" +

//                    "FROM transactions\n" +
//                    "WHERE userId = " + rwyt.currentUser.getId();
                java.sql.Statement stm = connection.createStatement();
                java.sql.ResultSet res = stm.executeQuery(sql);

                while (res.next()) {
                    model.addRow(new Object[]{
                        no++,                     
                        res.getString("namauser"),
//                        name,
//                        rwyt.currentUser.getName(),
                        res.getString("productnama"),
                        res.getTimestamp("date"),
                        res.getInt("jumlahBeli"),
                        res.getInt("totalHarga"),
                    });
                }
                rwyt.getTable().setModel(model);
            } catch (SQLException e) {
                System.out.println("Error : " + e.getMessage());
            }
}
}