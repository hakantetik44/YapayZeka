Feature: Blog Sayfası Test Senaryoları

  Scenario: Sayfa başlığını doğrula
    Given Blog sayfasına git
    Then Sayfa başlığı "Hakan Tetik - SDET & Test Automation Engineer" olmalı

  Scenario: Ana menü linklerini kontrol et
    Given Blog sayfasına git
    Then Menüde "Ana Sayfa" linki olmalı
    And Menüde "Hakkımda" linki olmalı
    And Menüde "Projeler" linki olmalı
    And Menüde "Kurslar" linki olmalı
    And Menüde "İletişim" linki olmalı

  Scenario: Sosyal medya ikonlarını kontrol et
    Given Blog sayfasına git
    Then LinkedIn ikonu görünür olmalı
    And GitHub ikonu görünür olmalı
    And Twitter ikonu görünür olmalı

  Scenario: Hakkımda bölümünü kontrol et
    Given Blog sayfasına git
    When "Hakkımda" bölümüne git
    Then "I'm Here for You" başlığı görünür olmalı
    And "Latest Courses" kartı görünür olmalı
    And "Tech Articles" kartı görünür olmalı
    And "Open Source" kartı görünür olmalı