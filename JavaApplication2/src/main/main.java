/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

package main;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class main {

    public static void main(String[] args) {
        // Define o Look and Feel para Windows
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Configurações do Banco de Dados
        String url = "jdbc:mysql://localhost:3306/pessoa";
        String user = "root";
        String password = "bdados749!";

        // Respostas corretas para cada cena
        String[] respostasCorretas = {
            "use CHAVE",
            "use TOALHA",
            "use CHAVE",
            "use CHAVE DE FENDA",
            "use FACA DE COZINHA",
            "use FACA DE COZINHA e CHAVE"
        };

        // Mensagens de sucesso para cada cena
        String[] mensagensSucesso = {
            "Parabéns, você usou a CHAVE corretamente e chegou ao BANHEIRO.",
            "Parabéns, você usou a TOALHA corretamente e chegou ao BANHEIRO.",
            "Parabéns, você usou a CHAVE corretamente e chegou à SALA DE ESTAR.",
            "Parabéns, você usou a CHAVE DE FENDA corretamente e chegou à COZINHA.",
            "Parabéns, você usou a FACA DE COZINHA corretamente e chegou à PORTA DA SAÍDA.",
            "Parabéns, você conseguiu escapar da casa."
        };

        // Objetos errados para cada cena
        String[][] objetosErrados = {
            {"CHAVE", "ESPELHO", "CADEIRA"}, // Cena 1
            {"TOALHA", "TAMPA"},              // Cena 2
            {"LIVRO"},                        // Cena 3
            {"FACA DE COZINHA"},             // Cena 4
            {"LANTERNA"},                     // Cena 5
            {"GANCHO"}                       // Cena 6
        };

        // Mensagens de erro para cada objeto errado
        String[] mensagensErro = {
            "Tentar usar a CHAVE nesse momento da fuga não dará certo, tente outro.",
            "Tentar usar o ESPELHO nesse momento da fuga não dará certo, tente outro.",
            "Tentar usar a CADEIRA nesse momento da fuga não dará certo, tente outro.",
            "Tentar usar a TOALHA nesse momento da fuga não dará certo, tente outro.",
            "Tentar usar a TAMPA nesse momento da fuga não dará certo, tente outro.",
            "Tentar usar o LIVRO nesse momento da fuga não dará certo, tente outro.",
            "Tentar usar a FACA DE COZINHA nesse momento da fuga não dará certo, tente outro.",
            "Tentar usar a LANTERNA nesse momento da fuga não dará certo, tente outro.",
            "Tentar usar o GANCHO nesse momento da fuga não dará certo, tente outro."
        };

        // Criar JTextArea e JScrollPane para exibir as cenas
        JTextArea textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new java.awt.Dimension(600, 300));

        // Conectar ao banco de dados
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement()) {

            // Loop para percorrer as 6 cenas
            for (int i = 1; i <= 6; i++) {
                // Consulta SQL para buscar a descrição da cena atual
                String query = "SELECT descricao FROM cena WHERE id_cena = " + i;
                ResultSet rs = stmt.executeQuery(query);

                String descricaoCena = "";
                if (rs.next()) {
                    descricaoCena = rs.getString("descricao");
                }

                // Exibir a descrição da cena no JTextArea
                textArea.setText(descricaoCena);
                JOptionPane.showMessageDialog(null, scrollPane, "Cena " + i, JOptionPane.PLAIN_MESSAGE);

                // Solicitar entrada do usuário após cada cena
                String resposta = "";

                // Loop para garantir que o usuário digite a resposta correta, número ou "sair"
                while (true) {
                    resposta = JOptionPane.showInputDialog(null, "Digite a ação correta para avançar para próxima cena:", "Qual objeto irá escolher?", JOptionPane.PLAIN_MESSAGE);

                    if ("sair".equalsIgnoreCase(resposta)) {
                        JOptionPane.showMessageDialog(null, "Saindo do programa...", "Saída", JOptionPane.PLAIN_MESSAGE);
                        System.exit(0);  // Encerra o programa
                    } else if (resposta.equalsIgnoreCase(respostasCorretas[i - 1]) || resposta.equals(String.valueOf(i))) {
                        // Mensagem de sucesso
                        JOptionPane.showMessageDialog(null, mensagensSucesso[i - 1], "Sucesso", JOptionPane.PLAIN_MESSAGE);
                        break;  // Sai do loop se a entrada estiver correta
                    } else if (resposta.toLowerCase().startsWith("use ")) {
                        boolean objetoErrado = false;
                        for (int j = 0; j < objetosErrados[i - 1].length; j++) {
                            if (resposta.equalsIgnoreCase("use " + objetosErrados[i - 1][j])) {
                                JOptionPane.showMessageDialog(null, mensagensErro[j], "Erro", JOptionPane.PLAIN_MESSAGE);
                                objetoErrado = true;
                                break;
                            }
                        }
                        if (!objetoErrado) {
                            JOptionPane.showMessageDialog(null, "Hmmm.... Aparentemente este objeto não estava presente na cena.", "Erro", JOptionPane.PLAIN_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Entrada incorreta! Tente novamente.", "Erro", JOptionPane.PLAIN_MESSAGE);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}




















