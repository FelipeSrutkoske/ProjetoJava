/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class main {

    public static void main(String[] args) {
        // Configurações do Banco de Dados
        String url = "jdbc:mysql://localhost:3306/pessoa";
        String user = "roxxot";
        String password = "xxx";
        String query = "SELECT id_cena, descricao, cena_titulo, cena_objetivo FROM cena";

        // Configurar a janela (JFrame)
        JFrame frame = new JFrame("Cenas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);

        // Criar uma tabela para exibir os dados
        DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Descrição", "Título", "Objetivo"}, 0);
        JTable table = new JTable(model);

        // Conectar ao banco de dados e preencher a tabela
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int idCena = rs.getInt("id_cena");
                String descricao = rs.getString("descricao");
                String titulo = rs.getString("cena_titulo");
                String objetivo = rs.getString("cena_objetivo");

                // Adiciona os dados na tabela
                model.addRow(new Object[]{idCena, descricao, titulo, objetivo});
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Adicionar a tabela a um painel rolável e exibi-la
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane);

        // Tornar a janela visível
        frame.setVisible(true);
    }
}
