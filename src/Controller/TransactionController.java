/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DB.KoneksiDB;
import Model.Transactions;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import martmain.Transaksi;
import martmain.UserPanel;

/**
 *
 * @author achma
 */
public class TransactionController {
    private Transaksi trxf;
    private Transactions trxm;
    private Connection connection;
    private UserPanel up;
    
    public TransactionController(Transaksi trxf, UserPanel up) {
        this.trxf = trxf;
        KoneksiDB koneksiDB = new KoneksiDB();
        koneksiDB.bukaKoneksi();
        this.connection = koneksiDB.getConn();
    }

    public void saveTransaction(Transactions trxm) {
        String sql = "INSERT INTO transactions (userId, productId, jumlahBeli, totalHarga) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, trxm.getUserId());
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
    }
    
    public void showTxPanel() {
        Transaksi tx = new Transaksi();
        tx.setVisible(true);
        tx.pack();
        tx.setLocationRelativeTo(null);
    }
    
}


//    public void saveTransaction() {
//        String sql = "INSERT INTO transactions (userId, productId, jumlahBeli, totalHarga) VALUES (?, ?, ?, ?)";
//
//        try (PreparedStatement statement = connection.prepareStatement(sql)) {
//            statement.setInt(1, trxm.getUserId());
//            statement.setInt(2, trxm.getProductId());
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
//    }
