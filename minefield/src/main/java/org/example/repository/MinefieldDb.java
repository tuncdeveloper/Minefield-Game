package org.example.repository;

import org.example.Player;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MinefieldDb implements IMinefieldDb {

    private static final String URL = "jdbc:postgresql://localhost:5432/minefield_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "test";

    // Oyuncu ekleme metodu
    public void playerAdd(Player player) {
        String query = "INSERT INTO players (name) VALUES (?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, player.getName());
            pstmt.executeUpdate();

            // ID'yi güncellemek için
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    player.setPlayerId(rs.getInt(1));  // ID'yi player objesine set et
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Skor ekleme metodu
    public void scoreAdd(Integer playerIdFk, int score) {
        System.out.println("Player ID: " + playerIdFk);  // PlayerIdFk'nı kontrol et

        String query = "INSERT INTO scores (player_id_fk, score) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            if (playerIdFk == null) {
                pstmt.setNull(1, java.sql.Types.INTEGER);
            } else {
                pstmt.setInt(1, playerIdFk); // playerIdFk null değilse setInt kullanıyoruz
            }

            pstmt.setInt(2, score);
            pstmt.executeUpdate();
            System.out.println("Skor eklendi: " + score);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    // Skorları listeleme metodu
    public List<String> listScores() {
        List<String> scores = new ArrayList<>();
        // 'ORDER BY' ifadesi doğru yazılmıştır, ; kaldırıldı
        String query = "SELECT p.name, s.score " +
                "FROM scores s " +
                "JOIN players p ON s.player_id_fk = p.player_id " +
                "ORDER BY s.score DESC";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String result = rs.getString("name") + " - " + rs.getInt("score");
                scores.add(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return scores;
    }

}
