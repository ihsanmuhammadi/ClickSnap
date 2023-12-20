/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DB.KoneksiDB;
import Model.Customer;
import Model.Product;
import Model.Transactions;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import martmain.Purchase;
import martmain.TransaksiAdmin;
import martmain.TransaksiUser;
import martmain.UserPanel;

/**
 *
 * @author achma
 */
public class TransactionController {
    private Purchase prc;
    private Transactions trxm;
    private Connection connection;
    private UserPanel up;
    private TransaksiAdmin trxAdm;
    private TransaksiUser trxUsr;
    private Customer currentUser;
//    public Customer currentUser;
    
    public TransactionController(Purchase prc, TransaksiAdmin trxAdm, TransaksiUser trxUsr) {
        this.prc = prc;
        this.trxAdm = trxAdm;
        this.trxUsr = trxUsr;
        KoneksiDB koneksiDB = new KoneksiDB();
        koneksiDB.bukaKoneksi();
        this.connection = koneksiDB.getConn();
//        setCurrentUser(currentUser);
    }
    
    public void showTxAdm() {
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
                
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

                while (res.next()) {
                    model.addRow(new Object[]{
                        no++,                     
                        res.getString("namauser"),
//                        name,
//                        rwyt.currentUser.getName(),
                        res.getString("productnama"),
                        dateFormat.format(res.getTimestamp("date")),
                        res.getInt("jumlahBeli"),
                        res.getInt("totalHarga"),
                    });
                }
                trxAdm.getTable().setModel(model);
            } catch (SQLException e) {
                System.out.println("Error : " + e.getMessage());
            }
}

    
 public void shwoTxUsr() {
    DefaultTableModel model = new DefaultTableModel();
    model.addColumn("No.");
    model.addColumn("Nama Produk");
    model.addColumn("Tanggal");
    model.addColumn("Jumlah");
    model.addColumn("Total Harga");

    try {
        int no = 1;
        String sql = "SELECT transactions.*, users.name AS namauser, product.nama AS productnama\n" +
            "FROM transactions\n" +
            "INNER JOIN users ON transactions.userId = users.id\n" +
            "INNER JOIN product ON transactions.productId = product.id\n" +
            "WHERE transactions.userId = " + trxUsr.currentUser.getId();

        java.sql.Statement stm = connection.createStatement();
        java.sql.ResultSet res = stm.executeQuery(sql);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        while (res.next()) {
            model.addRow(new Object[]{
                no++,
                res.getString("productnama"),
                dateFormat.format(res.getTimestamp("date")),
                res.getInt("jumlahBeli"),
                res.getInt("totalHarga"),
            });
        }
        trxUsr.getTable().setModel(model);
    } catch (SQLException e) {
        System.out.println("Error : " + e.getMessage());
    }
}
    
    public void saveTransaction() {
    try {
        int userId = 0;
        int productId = Integer.parseInt(prc.getId().getText());
        int jumlahBeli = Integer.parseInt(prc.getJml().getText());
        int totalHarga = Integer.parseInt(prc.getTotal().getText());
        
        // Gantilah nilai parameter pada konstruktor sesuai dengan urutan yang benar
        trxm = new Transactions(userId, productId, jumlahBeli, totalHarga);

        String sql = "INSERT INTO transactions (userId, productId, jumlahBeli, totalHarga) VALUES (?, ?, ?, ?)";
//        String sql = "INSERT INTO transak (user_id, product_id, jumlah_beli, total_harga) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            // Sesuaikan dengan tipe data yang sesuai
            statement.setInt(1, prc.currentUser.getId());
            statement.setInt(2, trxm.getProductId());
            statement.setInt(3, trxm.getJumlahBeli());
            statement.setInt(4, trxm.getTotalHarga());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(null, "Transaksi berhasil disimpan.", "Info", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Gagal menyimpan transaksi.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (NumberFormatException ex) {
        // Tangani kesalahan konversi tipe data di sini jika diperlukan
        ex.printStackTrace();
    }
}
    
//    public void saveTransaction() {
//    try {
//        int userId = 0;
//        int productId = Integer.parseInt(prc.getId().getText());
//        int jumlahBeli = Integer.parseInt(prc.getJml().getText());
//        int totalHarga = Integer.parseInt(prc.getTotal().getText());
//        
//        // Gantilah nilai parameter pada konstruktor sesuai dengan urutan yang benar
//        trxm = new Transactions(userId, productId, jumlahBeli, totalHarga);
//
//        String sql = "INSERT INTO transactions (userId, productId, jumlahBeli, totalHarga) VALUES (?, ?, ?, ?)";
////        String sql = "INSERT INTO transak (user_id, product_id, jumlah_beli, total_harga) VALUES (?, ?, ?, ?)";
//        
//        try (PreparedStatement statement = connection.prepareStatement(sql)) {
//            // Sesuaikan dengan tipe data yang sesuai
////            statement.setInt(1, trxm.getId());
////            statement.setInt(2, trxm.getProductId());
//            statement.setLong(1, trxm.getId());
//            statement.setLong(2, trxm.getProductId());
//            statement.setInt(3, trxm.getJumlahBeli());
//            statement.setInt(4, trxm.getTotalHarga());
//
//            int rowsInserted = statement.executeUpdate();
//            if (rowsInserted > 0) {
//                JOptionPane.showMessageDialog(null, "Transaksi berhasil disimpan.", "Info", JOptionPane.INFORMATION_MESSAGE);
//            } else {
//                JOptionPane.showMessageDialog(null, "Gagal menyimpan transaksi.", "Error", JOptionPane.ERROR_MESSAGE);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(null, "Terjadi kesalahan: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//        }
//    } catch (NumberFormatException ex) {
//        // Tangani kesalahan konversi tipe data di sini jika diperlukan
//        ex.printStackTrace();
//    }
//}

    
//    public void showTxPanel() {
//        Transaksi tx = new Transaksi();
//        tx.setVisible(true);
//        tx.pack();
//        tx.setLocationRelativeTo(null);
//    }
    
    
    public void showAvailPrdk() {
        DefaultTableModel model_prdk = new DefaultTableModel();
        model_prdk.getDataVector().removeAllElements();
        model_prdk.fireTableDataChanged();

        try {
            String sql = "SELECT * FROM product WHERE stok = 1";
            java.sql.Statement stm = connection.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);

            while (res.next()) {
                Object[] obj = new Object[4];
                obj[0] = res.getString("nama");
                obj[1] = res.getInt("stok");
                obj[2] = res.getInt("harga");
                obj[3] = res.getString("detail");
                model_prdk.addRow(obj);

                prc.getCb().addItem((String) obj[0]);
        }
    } catch (SQLException e) {
        System.out.println("Error : " + e.getMessage());
    }
}
    
    
    public void isiHargaDANid(){
        String sql = "SELECT * FROM product WHERE nama = '"+prc.getCb().getSelectedItem()+"'";
//        "select * from product where nama='"+cbProduk.getSelectedItem()+"'"
        
        try {
         java.sql.Statement stm = connection.createStatement();
         java.sql.ResultSet rs = stm.executeQuery(sql);

        if(rs.next()) {
            int productId = rs.getInt("id");
            // Menggunakan labelId (sesuaikan dengan komponen yang sesuai)
            prc.getId().setText(String.valueOf(productId));
            
            String hrg = rs.getString("harga");
            prc.getHarga().setText(hrg);
        }
        } catch (Exception e) {
        }
    }
    
    
    public void setTotal() {
    try {
        Double harga_prdk = Double.parseDouble(prc.getHarga().getText());
        Double jml_beli = Double.parseDouble(prc.getJml().getText());
        Double total = harga_prdk * jml_beli;

        // Mengonversi nilai total menjadi int
        int totalInt = total.intValue();

        prc.getTotal().setText(String.valueOf(totalInt));
    } catch (NumberFormatException e) {
        // Handle exception, misalnya dengan menampilkan pesan kesalahan kepada pengguna.
        e.printStackTrace(); // Hanya untuk tujuan demonstrasi, sebaiknya di-handle secara lebih baik.
    }
}
}



//    private void setCurrentUser(Customer currentUser) {
//        this.currentUser = prc.getcu;}
