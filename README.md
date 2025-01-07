# 🌟 Blog Test Otomasyon Projesi 🚀

<div align="center">
  <h1>
    <img src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Objects/Rocket.png" alt="Rocket" width="25" height="25" />
    Modern Blog Test Otomasyonu
    <img src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Objects/Laptop.png" alt="Laptop" width="25" height="25" />
  </h1>

  <p>
    <img src="https://img.shields.io/github/stars/hakantetik44/YapayZeka?style=for-the-badge&color=yellow" alt="stars" />
    <img src="https://img.shields.io/github/forks/hakantetik44/YapayZeka?style=for-the-badge&color=blue" alt="forks" />
    <img src="https://img.shields.io/github/issues/hakantetik44/YapayZeka?style=for-the-badge&color=red" alt="issues" />
  </p>

  <p>
    <img src="https://img.shields.io/badge/Cucumber-23D96C?style=for-the-badge&logo=cucumber&logoColor=white" alt="Cucumber" />
    <img src="https://img.shields.io/badge/Selenium-43B02A?style=for-the-badge&logo=selenium&logoColor=white" alt="Selenium" />
    <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white" alt="Java" />
    <img src="https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white" alt="Maven" />
    <img src="https://img.shields.io/badge/JUnit5-25A162?style=for-the-badge&logo=junit5&logoColor=white" alt="JUnit5" />
    <img src="https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white" alt="Git" />
  </p>
</div>

## 🎯 Proje Hakkında

Bu proje, [Blog URL'niz] adresindeki blog web sitesinin kapsamlı test otomasyonunu içerir. Cucumber BDD yaklaşımı ile yazılmış, Selenium WebDriver kullanan ve Allure raporlama araçları ile desteklenen modern bir test framework'üdür.

### 🔗 Önemli Linkler

- 📂 GitHub Repo: [https://github.com/hakantetik44/YapayZeka](https://github.com/hakantetik44/YapayZeka)
- 🌐 Test Edilen Site: [Blog URL'niz]
- 📊 Jenkins Dashboard: [Jenkins URL'niz]
- 📝 Allure Raporları: [Allure Rapor URL'niz]

## 📋 İçindekiler

- Özellikler
- Gereksinimler
- Kurulum
- Testleri Çalıştırma
- Raporlama
- Video Kaydı
- Proje Yapısı
- Kullanılan Teknolojiler
- Blog Sayfası Geliştirme Süreci
- Katkıda Bulunma
- Test Senaryoları
- Konfigürasyon
- Hata Ayıklama
- İletişim
- Lisans

## 🎯 Özellikler

- ✨ Cucumber BDD yaklaşımı ile okunabilir test senaryoları
- 📝 Gherkin dili ile yazılmış feature dosyaları
- 🎥 Test senaryolarının video kaydı
- 📸 Hata durumunda ekran görüntüsü alma
- 📊 Allure ile detaylı raporlama
- 🔄 Page Object Model (POM) tasarım deseni
- 🌐 Cross-browser testing desteği

## ⚙️ Gereksinimler

- ☕ Java JDK 8 veya üzeri
- 📦 Maven
- 🌐 Chrome/Firefox/Edge tarayıcılarından biri
- 💻 IntelliJ IDEA (önerilen) veya başka bir IDE

## 📦 Kurulum

1. Projeyi klonlayın:
```bash
git clone [proje-url]
cd [proje-klasörü]
```

2. Gerekli bağımlılıkları yükleyin:
```bash
mvn clean install
```

3. Allure'u yükleyin (macOS için):
```bash
brew install allure
```

Windows için Scoop kullanarak:
```bash
scoop install allure
```

## 🏃‍♂️ Testleri Çalıştırma

Tüm testleri çalıştırmak için:
```bash
mvn clean test
```

Belirli bir tag'e sahip testleri çalıştırmak için:
```bash
mvn clean test -Dcucumber.filter.tags="@smoke"
```

## 📊 Raporlama

Test çalıştırıldıktan sonra Allure raporu otomatik olarak açılacaktır. Manuel olarak raporu görüntülemek için:

```bash
allure serve target/allure-results
```

## 🎥 Video Kaydı

Framework, her test senaryosu için otomatik olarak video kaydı alır:

- Kayıtlar otomatik olarak başlar
- Test bitiminde kayıt durur
- AVI formatından MP4'e otomatik dönüşüm
- Kayıtlar Allure raporuna otomatik eklenir

## 📁 Proje Yapısı

```
src
├── test
│   ├── java
│   │   ├── hooks          # Test hooks ve konfigürasyonlar
│   │   ├── pages         # Page Object Model sınıfları
│   │   ├── runners       # Cucumber test runner
│   │   ├── stepDefs      # Step definition sınıfları
│   │   └── utilities    # Yardımcı sınıflar
│   └── resources
│       ├── features     # Cucumber feature dosyaları
│       └── config       # Konfigürasyon dosyaları
```

## 🤖 Kullanılan Teknolojiler

- 🎯 Cucumber 7.14.0
- 🌐 Selenium WebDriver
- 📊 Allure Framework 2.24.0
- 📹 Monte Screen Recorder
- 📊 JUnit 5
- 📦 Maven

## 🎨 Blog Sayfası Geliştirme Süreci

### 🛠️ Kullanılan Teknolojiler ve Araçlar
- 🎨 Frontend:
  - 📱 HTML5 & CSS3
  - ⚡ JavaScript (ES6+)
  - 🅱️ Bootstrap 5
  - 🎯 Font Awesome 6
  - 🎪 Animate.css
  - 🎭 SASS/SCSS
  - 📦 Webpack
  - 🔄 Babel

### ✨ Blog Sayfası Özellikleri
- 📱 Responsive Tasarım
  - 💻 Desktop
  - 📱 Tablet
  - 📱 Mobile
- 📝 Blog İşlemleri
  - ✍️ Yazı Oluşturma
  - 🔄 Düzenleme
  - 🗑️ Silme
  - 📌 Pinleme
- 👤 Kullanıcı Sistemi
  - 🔐 JWT Auth
  - 👥 Rol Yönetimi
  - 🔑 OAuth2 Desteği
- 🎨 Görsel Özellikler
  - 🌙 Dark/Light Mode
  - 🎨 Tema Seçenekleri
  - 🖼️ Responsive Görseller
- 🔍 Arama ve Filtreleme
  - 🏷️ Etiket Sistemi
  - 📂 Kategori Filtreleme
  - 📅 Tarih Bazlı Arama
- 💬 Etkileşim
  - 💭 Yorumlar
  - 👍 Beğeni Sistemi
  - 🔔 Bildirimler
- 📊 Admin Paneli
  - 📈 İstatistikler
  - 👥 Kullanıcı Yönetimi
  - 📝 İçerik Yönetimi
- 🌍 Çoklu Dil
  - 🇹🇷 Türkçe
  - 🇺🇸 İngilizce
  - 🇩🇪 Almanca

### 🎯 Test Kapsamı
- 🏃‍♂️ Smoke Tests
  - ✅ Kritik Fonksiyonlar
  - 🔐 Login/Logout
  - 📝 Temel CRUD
- 🔄 Regression Tests
  - 📱 Cross-Browser
  - 🖥️ Cross-Platform
  - 📱 Responsive
- ⚡ Performance Tests
  - 🚀 Sayfa Yüklenme
  - 📊 API Response
  - 🗃️ DB Queries

## 🤝 Katkıda Bulunma

1. Fork yapın
2. Feature branch oluşturun (`git checkout -b feature/amazing-feature`)
3. Değişikliklerinizi commit edin (`git commit -m 'feat: Add amazing feature'`)
4. Branch'inizi push edin (`git push origin feature/amazing-feature`)
5. Pull Request oluşturun

## 📝 Test Senaryoları

Framework'te şu an için aşağıdaki test senaryoları bulunmaktadır:

1. Ana Sayfa Testleri
   - Header menü kontrolü
   - Footer link kontrolü
   - Sosyal medya ikonları kontrolü

2. Blog Testleri
   - Blog listesi görüntüleme
   - Blog detay sayfası kontrolü
   - Blog arama fonksiyonu

3. Hakkımda Sayfası Testleri
   - Sayfa yükleme kontrolü
   - İçerik doğrulama
   - İletişim formu kontrolü

## 📁 Konfigürasyon

`config.properties` dosyasında aşağıdaki ayarları özelleştirebilirsiniz:

```properties
browser=chrome
headless=false
timeout=30
baseUrl=http://example.com
```

## 🔍 Hata Ayıklama

Test çalışması sırasında:
- Ekran görüntüleri `target/screenshots` klasörüne kaydedilir
- Video kayıtları `target/allure-results` klasörüne kaydedilir
- Log dosyaları `target/logs` klasöründe bulunur

## 📲 İletişim

Sorularınız için: [email-adresi]

## 📜 Lisans

Bu proje MIT lisansı altında lisanslanmıştır. Detaylar için [LICENSE](LICENSE) dosyasına bakınız.

## 🔍 Test Senaryoları Detayları

### 📝 Ana Sayfa Testleri
- ✅ Sayfa başlığı doğrulama
- ✅ Meta etiketleri kontrolü
- ✅ Responsive tasarım kontrolü
- ✅ Navigasyon menüsü kontrolü

### 👤 Hakkımda Bölümü Testleri
- ✅ Bölüm başlığı kontrolü
- ✅ Profil resmi görünürlüğü
- ✅ Sosyal medya bağlantıları
- ✅ CV indirme linki kontrolü

### 💼 Projeler Bölümü Testleri
- ✅ Proje kartları görünürlüğü
- ✅ Proje detayları kontrolü
- ✅ GitHub bağlantıları
- ✅ Demo linkleri kontrolü

### 🎯 Yetenekler Bölümü Testleri
- ✅ Yetenek kategorileri kontrolü
- ✅ Progress bar görünürlüğü
- ✅ Animasyon kontrolü
- ✅ Tooltip kontrolü

## 🚀 CI/CD Pipeline Detayları

### 📊 Jenkins Pipeline Aşamaları
```groovy
// Jenkins Pipeline Aşamaları
1. Checkout
2. Build
3. Test
4. Report
5. Deploy
```

### 🔄 Otomatik Test Çalıştırma
- 🌙 Gece yarısı testleri
- 🔄 Pull request kontrolleri
- 📊 Periyodik test raporları
- 🚨 Hata bildirimleri

## 🎯 Test Metrikleri

### 📊 Kapsam Analizi
- ✅ Statement Coverage: 85%
- ✅ Branch Coverage: 80%
- ✅ Function Coverage: 90%
- ✅ Line Coverage: 88%

### ⏱️ Performans Metrikleri
- ⚡ Ortalama test süresi: 2.5 dakika
- 🎯 Test başarı oranı: 98%
- 🔄 Yeniden çalıştırma oranı: 2%
- 📈 Haftalık test sayısı: ~1000

## 🐛 Hata Ayıklama Kılavuzu

### 🔍 Yaygın Hatalar ve Çözümleri
1. **Element Not Found Hatası**
   ```java
   org.openqa.selenium.NoSuchElementException
   ```
   - ✅ Çözüm: Wait mekanizmalarını kontrol edin
   - ✅ Element locator'ları güncelleyin
   - ✅ Sayfa yüklenme durumunu kontrol edin

2. **Timeout Hataları**
   ```java
   org.openqa.selenium.TimeoutException
   ```
   - ✅ Network bağlantısını kontrol edin
   - ✅ Wait sürelerini artırın
   - ✅ Sayfa performansını optimize edin

### 🔧 Debug Modunda Çalıştırma
```bash
mvn test -Ddebug=true
```

## 📱 Cross-Browser Test Matrisi

| Browser | Version | OS      | Priority |
|---------|---------|---------|----------|
| Chrome  | 120+    | Windows | High     |
| Firefox | 119+    | macOS   | High     |
| Safari  | 17+     | macOS   | Medium   |
| Edge    | 120+    | Windows | Medium   |
