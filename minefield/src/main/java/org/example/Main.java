package org.example;

import org.example.repository.IMinefieldDb;
import org.example.repository.MinefieldDb;

public class Main {
    public static void main(String[] args) {
        IMinefieldDb minefieldDb = new MinefieldDb();  // Gerçek bir implementasyon kullanılmalı
        Player player = new Player();  // player nesnesi oluşturuluyor
        new Login(minefieldDb).setVisible(true);  // Login sınıfını başlatıyoruz
    }
}
