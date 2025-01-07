# 🌟 Blog Otomasyon Projesi 🚀

<div align="center">
  <h1>
    <img src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Objects/Rocket.png" alt="Rocket" width="25" height="25" />
    Modern Blog Test Otomasyonu
    <img src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Objects/Laptop.png" alt="Laptop" width="25" height="25" />
  </h1>
</div>

<p align="center">
  <img src="https://img.shields.io/badge/Cucumber-23D96C?style=for-the-badge&logo=cucumber&logoColor=white" alt="Cucumber" />
  <img src="https://img.shields.io/badge/Selenium-43B02A?style=for-the-badge&logo=selenium&logoColor=white" alt="Selenium" />
  <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white" alt="Java" />
  <img src="https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white" alt="Maven" />
</p>

Bu proje, blog web sitesinin otomatik testlerini içeren bir test otomasyon framework'üdür. 🎯 Cucumber BDD, 🌐 Selenium WebDriver ve 📊 Allure raporlama araçlarını kullanmaktadır.

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
  - 📱 HTML5
  - 🎭 CSS3
  - ⚡ JavaScript
  - 🅱️ Bootstrap 5 (Responsive tasarım için)
  - 🎯 Font Awesome (İkonlar için)

### ✨ Blog Sayfası Özellikleri
- 📱 Responsive tasarım (Mobil uyumlu)
- 📝 Blog yazıları ekleme ve düzenleme
- 👤 Kullanıcı profil sayfası
- 🔍 Arama fonksiyonu
- 🏷️ Kategori ve etiket sistemi
- 💬 Yorum sistemi
- 📊 Admin paneli

### 🚀 Geliştirme Aşamaları
1. **Tasarım ve Planlama**
   - 📐 Wireframe oluşturma
   - 🎨 Renk paleti seçimi
   - 📝 Tipografi belirleme

2. **Frontend Geliştirme**
   - 🏠 Ana sayfa tasarımı
   - 📄 Blog post sayfası
   - 📑 Kategori sayfaları
   - 📱 Responsive özellikler
   - 🖱️ Kullanıcı etkileşimleri

3. **Özellik Geliştirmeleri**
   - 🔐 Kullanıcı girişi/kaydı
   - ✏️ Blog post CRUD işlemleri
   - 💭 Yorum sistemi entegrasyonu
   - 🔍 Arama fonksiyonu implementasyonu

4. **Test ve Optimizasyon**
   - 🌐 Cross-browser testing
   - 📱 Mobil uyumluluk testleri
   - ⚡ Performans optimizasyonu
   - 🔧 SEO düzenlemeleri

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
