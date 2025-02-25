package org.example;


import org.example.repository.MinefieldDb;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ScoreManagement {
    private static final ArrayList<String> skorlar = new ArrayList<>();
    private static MinefieldDb minefieldDb=new MinefieldDb();

    public static void gosterSkorTablosu() {
        List<String> skorlar = minefieldDb.listScores();
        if (skorlar.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Henüz skor eklenmemiş!", "Scoreboard", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, String.join("\n", skorlar), "Scoreboard", JOptionPane.PLAIN_MESSAGE);
        }
    }
}

