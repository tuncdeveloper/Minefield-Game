package org.example;

import org.example.repository.IMinefieldDb;

import javax.swing.*;
import java.awt.*;

public class Login extends JFrame {
    private JPanel menu;
    private JButton oyunOynaButton, cikisButton, skorTablosuButton;
    private final IMinefieldDb minefieldDb;

    public Login(IMinefieldDb minefieldDb) {
        this.minefieldDb = minefieldDb;

        // Pencere başlığını ve ikonunu ayarla
        setTitle("Login");
        ImageIcon frameIcon = new ImageIcon(getClass().getResource("/login.PNG"));
        Image image = frameIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);  // Resmi küçült
        setIconImage(image);

        // Nimbus Look and Feel (varsa) ile arayüzü modernleştir
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ex) {
            System.out.println("Nimbus Look and Feel ayarlanamadı: " + ex);
        }

        // Pencere boyutunu büyüt
        setSize(600, 400);  // Yeni boyut
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Pencereyi ekranın ortasında aç
        setLocationRelativeTo(null);

        // Ana paneli oluştur ve düzenle
        menu = new JPanel();
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setBackground(new Color(19, 15, 15, 55)); // Hafif gri arka plan
        menu.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Başlık metnini ekle
        JLabel welcomeLabel = new JLabel("MINEFIELD!");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeLabel.setForeground(new Color(0, 0, 0));  // Steel Blue renginde metin

        // Başlık resmini küçült
        ImageIcon resizedIcon = new ImageIcon(frameIcon.getImage().getScaledInstance(500, 200, Image.SCALE_SMOOTH)); // Küçültülmüş başlık resmi
        JLabel titleLabel = new JLabel(resizedIcon);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Butonları oluştur
        oyunOynaButton = new JButton("Play Game");
        cikisButton = new JButton("Exit");
        skorTablosuButton = new JButton("Scoreboard");

        // Buton yazı fontunu ayarla
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 14);
        oyunOynaButton.setFont(buttonFont);
        cikisButton.setFont(buttonFont);
        skorTablosuButton.setFont(buttonFont);

        // Buton renklerini ayarla
        oyunOynaButton.setBackground(new Color(70, 130, 180));      // Steel Blue
        skorTablosuButton.setBackground(new Color(34, 139, 34));      // Forest Green
        cikisButton.setBackground(new Color(220, 20, 60));            // Crimson

        oyunOynaButton.setForeground(Color.WHITE);
        skorTablosuButton.setForeground(Color.WHITE);
        cikisButton.setForeground(Color.WHITE);

        // Butonlar için hover efekti ve kenarlık gibi ek görsel özellikler eklemek isterseniz,
        // Border ve Rollover özelliklerini de ayarlayabilirsiniz.
        oyunOynaButton.setFocusPainted(false);
        skorTablosuButton.setFocusPainted(false);
        cikisButton.setFocusPainted(false);

        // Butonları bir araya getirecek alt panel (FlowLayout kullanarak aralarına boşluk bırakıyoruz)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        buttonPanel.setOpaque(false);
        buttonPanel.add(oyunOynaButton);
        buttonPanel.add(skorTablosuButton);
        buttonPanel.add(cikisButton);

        // Ana paneli düzenle: Önce başlık metni, başlık resmi, sonra buton paneli
        menu.add(welcomeLabel);  // Hoş geldiniz mesajı
        menu.add(Box.createRigidArea(new Dimension(0, 20)));  // Boşluk bırak
        menu.add(titleLabel);    // Başlık resmi
        menu.add(Box.createRigidArea(new Dimension(0, 20)));  // Başlık ile butonlar arasında boşluk
        menu.add(buttonPanel);

        setContentPane(menu);

        // ActionListener eklemeleri
        oyunOynaButton.addActionListener(e -> {
            new GameBeforeUsername(minefieldDb);
            dispose();
        });

        cikisButton.addActionListener(e -> System.exit(0));

        skorTablosuButton.addActionListener(e -> ScoreManagement.gosterSkorTablosu());
    }
}
