Feature: Gelişmiş Blog Sayfası Test Senaryoları

  Background: 
    Given Blog sayfasına git

  @smoke @ui
  Scenario: Sayfa yüklenme performansını kontrol et
    Then Sayfa 3 saniye içinde yüklenmeli
    And Console'da hata mesajı olmamalı

  @regression @ui
  Scenario Outline: Farklı ekran boyutlarında responsive tasarımı kontrol et
    When Ekran boyutu "<width>x<height>" olarak ayarlanır
    Then Sayfa responsive olarak görüntülenmeli
    And Menü öğeleri doğru şekilde görüntülenmeli

    Examples:
      | width | height |
      | 1920  | 1080   |
      | 1366  | 768    |
      | 768   | 1024   |
      | 375   | 812    |

  @regression @ui
  Scenario: Sosyal medya bağlantılarını kontrol et
    When "Hakkımda" bölümüne git
    Then Sosyal medya ikonları görünür olmalı
    And Sosyal medya linkleri doğru URL'lere yönlendirmeli
      | platform  | url                           |
      | LinkedIn  | https://linkedin.com/in/[user] |
      | GitHub    | https://github.com/[user]      |
      | Twitter   | https://twitter.com/[user]     |

  @regression @ui
  Scenario: Proje kartlarının detaylarını kontrol et
    When "Projeler" bölümüne git
    Then Her proje kartı aşağıdaki bilgileri içermeli
      | alan          |
      | Başlık        |
      | Açıklama      |
      | Teknolojiler  |
      | GitHub Linki  |
      | Demo Linki    |
    And Proje kartları hover efekti göstermeli
    And Proje linkleri yeni sekmede açılmalı

  @regression @ui
  Scenario: Yetenekler bölümü animasyonlarını kontrol et
    When "Yetenekler" bölümüne git
    Then Progress barlar animasyonlu şekilde dolmalı
    And Yetenek kartları hover efekti göstermeli
    And Her yetenek için tooltip görünmeli

  @regression @ui
  Scenario: CV indirme fonksiyonunu kontrol et
    When "Hakkımda" bölümüne git
    And CV indir butonuna tıkla
    Then CV dosyası indirilebilmeli
    And İndirilen dosya PDF formatında olmalı

  @regression @ui
  Scenario: İletişim formunu kontrol et
    When "İletişim" bölümüne git
    Then Form alanları görünür olmalı
      | alan         |
      | Ad Soyad    |
      | Email       |
      | Konu        |
      | Mesaj       |
    And Form validasyonları çalışmalı
    And Form başarıyla gönderilebilmeli
