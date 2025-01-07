# language: tr
@blog
Feature: Blog Sayfası Test Senaryoları

  Background:
    Given Blog sayfasına git

  @performance
  Scenario: Sayfa yüklenme performansını kontrol et
    Then Sayfa 3 saniye içinde yüklenmeli

  @responsive
  Scenario Outline: Farklı ekran boyutlarında responsive tasarımı kontrol et
    When Ekran boyutu "<boyut>" olarak ayarlanır
    Then Sayfa responsive olarak görüntülenmeli
    And Menü öğeleri doğru şekilde görüntülenmeli

    Examples:
      | boyut        |
      | 1920x1080   |
      | 1366x768    |
      | 1024x768    |
      | 375x812     |

  @navigation
  Scenario: Sosyal medya bağlantılarını kontrol et
    When "İletişim" bölümüne git
    Then Form alanları görünür olmalı
      | alan      |
      | Ad Soyad  |
      | Email     |
      | Konu      |
      | Mesaj     |

  @contact
  Scenario: İletişim formunu kontrol et
    When "İletişim" bölümüne git
    Then Form validasyonları çalışmalı
    And Form başarıyla gönderilebilmeli
