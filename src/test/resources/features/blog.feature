Feature: Blog Sayfası Test Senaryoları

  Scenario: Sayfa başlığını doğrula
    Given Blog sayfasına git
    Then Sayfa başlığı "Hakan Tetik - SDET & Test Automation Engineer" olmalı

  Scenario: Ana menü linklerini kontrol et
    Given Blog sayfasına git
    Then Menüde "Hakkımda" linki olmalı
    And Menüde "Projeler" linki olmalı
    And Menüde "Yetenekler" linki olmalı

  Scenario: Hakkımda bölümünü kontrol et
    Given Blog sayfasına git
    When "Hakkımda" bölümüne git
    Then "Hakkımda" başlığı görünür olmalı

  Scenario: Yetenekler bölümünü kontrol et
    Given Blog sayfasına git
    When "Yetenekler" bölümüne git
    Then "Yetenekler" başlığı görünür olmalı

  Scenario: Projeler bölümünü kontrol et
    Given Blog sayfasına git
    When "Projeler" bölümüne git
    Then "Projeler" başlığı görünür olmalı