package org.example;

import org.example.repository.IMinefieldDb;

import javax.swing.*;

public class GameBeforeUsername extends JFrame {
    private JTextField usernameField;
    private JButton startButton;
    private final IMinefieldDb minefieldDb;
    private Player player;

    public GameBeforeUsername(IMinefieldDb minefieldDb) {
        this.minefieldDb = minefieldDb;
        setTitle("Kullanıcı Adı Girişi");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel label = new JLabel("Kullanıcı Adınızı Girin:");
        label.setBounds(30, 20, 200, 25);
        add(label);

        usernameField = new JTextField();
        usernameField.setBounds(30, 50, 200, 25);
        add(usernameField);

        startButton = new JButton("Başla");
        startButton.setBounds(90, 80, 100, 25);
        add(startButton);

        // Butona tıklanınca GameScreen'e geçiş yap
        startButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            if (!username.isEmpty()) {
                player = new Player();
                player.setName(username);
                minefieldDb.playerAdd(player);
                new GameScreen(player,minefieldDb);

                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Lütfen bir kullanıcı adı girin!", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });

        setVisible(true);
    }
}
