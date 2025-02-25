package org.example;


import org.example.repository.IMinefieldDb;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class GameScreen extends JFrame {
    private final JButton[][] butonlar = new JButton[10][10];
    private final boolean[][] mayinlar = new boolean[10][10];
    private final boolean[][] tiklanan = new boolean[10][10];
    private int tiklamaSayisi = 0;
    private static final int MAYIN_SAYISI = 15;
    private final Player player;
    private final IMinefieldDb minefieldDb;


    public GameScreen(Player player, IMinefieldDb minefieldDb) {
        this.player = player;
        this.minefieldDb = minefieldDb;
        setTitle("MineField - "+player.getName());
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Ekranın ortasında aç

        JPanel panel = new JPanel(new GridLayout(10, 10));
        ekranaMayinlariYerlestir();
        butonlariOlustur(panel);

        add(panel);
        setVisible(true);
    }

    private void ekranaMayinlariYerlestir() {
        Random rand = new Random();
        int sayac = 0;
        while (sayac < MAYIN_SAYISI) {
            int x = rand.nextInt(10);
            int y = rand.nextInt(10);
            if (!mayinlar[x][y]) {
                mayinlar[x][y] = true;
                sayac++;
            }
        }
    }

    private void butonlariOlustur(JPanel panel) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                butonlar[i][j] = new JButton();
                panel.add(butonlar[i][j]);
                int satir = i, sutun = j;

                butonlar[i][j].addActionListener(e -> tikla(satir, sutun));
            }
        }
    }

    private void tikla(int satir, int sutun) {
        if (mayinlar[satir][sutun]) {
            mayinaBasti(satir, sutun);
        } else {
            bosAlanaTiklandi(satir, sutun);
        }
    }

    private void mayinaBasti(int satir, int sutun) {
        // Mayın ikonları
        ImageIcon basilanMayinIkonu = resizeIcon("/mayın2.png", 60, 60);
        ImageIcon normalMayinIkonu = resizeIcon("/mayın.png", 60, 60);

        // Basılan mayın için farklı ikon
        butonlar[satir][sutun].setIcon(basilanMayinIkonu);
        butonlar[satir][sutun].setBackground(null); // Arkaplan değişmesin
// Basılan mayın için farklı ikon
        butonlar[satir][sutun].setIcon(basilanMayinIkonu);
        butonlar[satir][sutun].setDisabledIcon(basilanMayinIkonu); // Buton devre dışı olsa bile ikon belirgin olsun
        butonlar[satir][sutun].setOpaque(true); // Arkaplan tam görünür olsun
        butonlar[satir][sutun].setBackground(Color.RED); // Mayına basılan yeri kırmızı yap
        butonlar[satir][sutun].setEnabled(false); // Ama rengi soluk olmasın

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (mayinlar[i][j]) {
                    if (!(i == satir && j == sutun)) { // Basılan hariç diğerleri normal ikon
                        butonlar[i][j].setIcon(normalMayinIkonu);
                        butonlar[i][j].setDisabledIcon(normalMayinIkonu); // Devre dışı olsa bile canlı ikon göster
                    }
                    butonlar[i][j].setEnabled(false); // Butonu devre dışı bırak ama ikon soluk olmasın
                    butonlar[i][j].setOpaque(true); // Arkaplanın tam görünmesini sağla
                    butonlar[i][j].setBackground(Color.LIGHT_GRAY); // Arka planı belirgin yap

                }
            }
        }

        JOptionPane.showMessageDialog(this, "Game Over!\nYour Score: " + tiklamaSayisi);
        minefieldDb.scoreAdd(player.getPlayerId(),tiklamaSayisi);
        new Login(minefieldDb).setVisible(true);  // Yeni bir Login nesnesi oluştur ve görünür yap
        dispose();

    }


    private ImageIcon resizeIcon(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(getClass().getResource(path));
        Image img = icon.getImage();

        // Şeffaflık destekleyen BufferedImage
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = bufferedImage.createGraphics();

        // Renk kalitesini artıran ayarlar
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);

        g2d.drawImage(img, 0, 0, width, height, null);
        g2d.dispose();

        return new ImageIcon(bufferedImage);
    }





    private void bosAlanaTiklandi(int satir, int sutun) {
        tiklanan[satir][sutun] = true;
        tiklamaSayisi++;

        int cevreMayinSayisi = cevredekiMayinSayisiniBul(satir, sutun);
        butonlar[satir][sutun].setText(cevreMayinSayisi == 0 ? "" : String.valueOf(cevreMayinSayisi));
        butonlar[satir][sutun].setFont(new Font("Arial", Font.BOLD, 24));
        butonlar[satir][sutun].setForeground(Color.BLACK); // Yazı rengini siyah yap
        butonlar[satir][sutun].setOpaque(false);
        butonlar[satir][sutun].setContentAreaFilled(false);
        butonlar[satir][sutun].setBorderPainted(false);


        if (cevreMayinSayisi == 0) otomatikTiklama(satir, sutun);
        if (tiklamaSayisi == 85) kazandiniz();
    }

    private int cevredekiMayinSayisiniBul(int satir, int sutun) {
        int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};

        int sayac = 0;
        for (int i = 0; i < 8; i++) {
            int yeniSatir = satir + dx[i], yeniSutun = sutun + dy[i];
            if (yeniSatir >= 0 && yeniSatir < 10 && yeniSutun >= 0 && yeniSutun < 10 && mayinlar[yeniSatir][yeniSutun]) {
                sayac++;
            }
        }
        return sayac;
    }

    private void otomatikTiklama(int satir, int sutun) {
        int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int i = 0; i < 8; i++) {
            int yeniSatir = satir + dx[i], yeniSutun = sutun + dy[i];
            if (yeniSatir >= 0 && yeniSatir < 10 && yeniSutun >= 0 && yeniSutun < 10 && !mayinlar[yeniSatir][yeniSutun] && !tiklanan[yeniSatir][yeniSutun]) {
                butonlar[yeniSatir][yeniSutun].doClick();
            }
        }
    }

    private void kazandiniz() {
        JOptionPane.showMessageDialog(this, "Winner!\nYour Score: " + tiklamaSayisi);
        minefieldDb.scoreAdd(player.getPlayerId(),tiklamaSayisi);

        dispose();
        new Login(minefieldDb).setVisible(true);  // Yeni bir Login nesnesi oluştur ve görünür yap

    }
}
