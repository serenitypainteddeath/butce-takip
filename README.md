# 💰 Budget Tracker - Kişisel Bütçe Yönetim Uygulaması

Modern ve kullanıcı dostu arayüzü ile gelir-gider takibi yapabileceğiniz, finansal durumunuzu analiz edebileceğiniz Android uygulaması.

## 📱 Uygulama Hakkında

Budget Tracker, kişisel finansal yönetiminizi kolaylaştırmak için geliştirilmiş kapsamlı bir mobil uygulamadır. Kotlin ve Jetpack Compose teknolojileri kullanılarak modern Android geliştirme standartlarına uygun olarak tasarlanmıştır.

## ✨ Özellikler

### 🔐 Kullanıcı Yönetimi
- **Firebase Authentication** ile güvenli giriş/kayıt
- Kullanıcı profil yönetimi
- Güvenli oturum yönetimi

### 💸 İşlem Yönetimi
- **Gelir ve Gider Kayıtları**: Detaylı kategoriler ile işlem ekleme
- **Akıllı Kategori Sistemi**: 
  - **Gider kategorileri**: Yiyecek, Ulaşım, Kira, Faturalar, Eğlence, Alışveriş, Sağlık, Eğitim
  - **Gelir kategorileri**: Maaş, Bonus, Serbest Çalışma, Yatırım, İş, Hediye, Kira Geliri
- **Sezgisel UI**: Card tabanlı modern tasarım
- İşlem silme ve düzenleme özelliği

### 📊 İstatistik ve Analiz
- **Pasta Grafik Analizleri**: Kategori bazında harcama dağılımı
- **Aylık Raporlar**: Gelir/gider karşılaştırması
- **Detaylı İstatistikler**: Kategori bazında toplam tutarlar
- **Gerçek Zamanlı Bakiye**: Güncel finansal durum gösterimi

### 💱 Döviz Kurları
- **Canlı Döviz Kurları**: Güncel döviz bilgileri
- **Çoklu Para Birimi**: Farklı para birimleri ile işlem desteği
- **Exchange Rate API** entegrasyonu

### 🎨 Kullanıcı Deneyimi
- **Material Design 3**: Modern ve sezgisel arayüz
- **Dark/Light Theme**: Tema desteği
- **Responsive Design**: Farklı ekran boyutlarına uyumluluk
- **Türkçe Dil Desteği**: Tamamen Türkçe arayüz

## 🔧 Sistem Gereksinimleri

### Minimum Sistem Gereksinimleri
- **Android**: 8.0 (API level 26) ve üzeri
- **RAM**: 2 GB
- **Depolama**: 50 MB boş alan
- **İnternet**: Döviz kurları ve senkronizasyon için internet bağlantısı

### Önerilen Sistem Gereksinimleri
- **Android**: 10.0 (API level 29) ve üzeri
- **RAM**: 4 GB ve üzeri
- **Depolama**: 100 MB boş alan
- **İnternet**: Wi-Fi veya 4G/5G bağlantısı

## 🛠️ Teknolojiler

### 📱 Frontend
- **Kotlin**: Modern Android geliştirme dili
- **Jetpack Compose**: Deklaratif UI framework
- **Material Design 3**: Google'ın tasarım dili
- **Navigation Compose**: Sayfa geçişleri

### 🗄️ Veri Yönetimi
- **Room Database**: Yerel veri saklama
- **Firebase Firestore**: Bulut veri tabanı
- **DataStore**: Kullanıcı tercihleri
- **Type Converters**: Veri dönüşümleri

### 🔗 Network & API
- **Retrofit**: REST API istemcisi
- **OkHttp**: HTTP istemcisi
- **Gson**: JSON serileştirme
- **Exchange Rate API**: Döviz kuru servisi

### 🏗️ Mimari ve DI
- **MVVM Architecture**: Temiz mimari yaklaşımı
- **Hilt**: Dependency Injection
- **StateFlow**: Reaktif programlama
- **Coroutines**: Asenkron işlemler

### 📊 Görselleştirme
- **MPAndroidChart**: Pasta grafikleri
- **Lottie**: Animasyonlar
- **Coil**: Resim yükleme

### 🔒 Güvenlik & Auth
- **Firebase Authentication**: Kullanıcı doğrulama
- **Secure Storage**: Güvenli veri saklama

## 🚀 Kurulum

### Geliştirici Kurulumu

#### Ön Koşullar
- **Android Studio**: Hedgehog (2023.1.1) veya üzeri
- **JDK**: 17 veya üzeri
- **Kotlin**: 2.1.0
- **Android SDK**: 34
- **Git**: Versiyon kontrolü için

#### Kurulum Adımları

1. **Projeyi klonlayın**
```bash
git clone https://github.com/[kullanici-adi]/budget-tracker.git
cd budget-tracker
```

2. **Android Studio'da açın**
```bash
# Android Studio'yu açın ve projeyi import edin
```

3. **Firebase Konfigürasyonu**
- Firebase Console'da yeni proje oluşturun
- `google-services.json` dosyasını `app/` klasörüne ekleyin
- Authentication ve Firestore'u etkinleştirin

4. **Bağımlılıkları yükleyin**
```bash
./gradlew build
```

5. **Uygulamayı çalıştırın**
```bash
./gradlew installDebug
```

## 📁 Proje Yapısı

```
app/
├── src/main/java/com/erdem/budgettracker/
│   ├── auth/                    # Kimlik doğrulama
│   ├── data/                    # Veri katmanı
│   │   ├── api/                 # API servisleri
│   │   ├── local/               # Room database
│   │   ├── model/               # Veri modelleri
│   │   └── repository/          # Repository pattern
│   ├── di/                      # Dependency injection
│   ├── ui/                      # UI katmanı
│   │   ├── screens/             # Ekranlar
│   │   │   ├── add_transaction/ # İşlem ekleme
│   │   │   ├── auth/            # Giriş/Kayıt
│   │   │   ├── exchange_rates/  # Döviz kurları
│   │   │   ├── home/            # Ana sayfa
│   │   │   ├── settings/        # Ayarlar
│   │   │   └── stats/           # İstatistikler
│   │   └── theme/               # Tema ve stil
│   └── util/                    # Yardımcı sınıflar
```

## 📸 Ekran Görüntüleri

*Ekran görüntüleri yakında eklenecek...*

## 🎯 Gelecek Özellikler

- [ ] **Bütçe Hedefleri**: Aylık harcama hedefleri
- [ ] **Bildirimler**: Harcama uyarıları
- [ ] **Export/Import**: Veri dışa/içe aktarma
- [ ] **Çoklu Hesap**: Birden fazla hesap yönetimi
- [ ] **Recurring Transactions**: Tekrarlayan işlemler
- [ ] **Kategori Özelleştirme**: Özel kategori oluşturma
- [ ] **Widget**: Ana ekran widget'ı
- [ ] **Cloud Backup**: Otomatik yedekleme

## 🤝 Katkıda Bulunma

Projeye katkıda bulunmak isterseniz:

1. Bu repository'yi fork edin
2. Feature branch oluşturun (`git checkout -b feature/yeni-ozellik`)
3. Değişikliklerinizi commit edin (`git commit -am 'Yeni özellik: Açıklama'`)
4. Branch'inizi push edin (`git push origin feature/yeni-ozellik`)
5. Pull Request oluşturun

### Kod Standartları
- **Kotlin Coding Conventions** kullanın
- Commit mesajlarını Türkçe yazın
- Her yeni özellik için test yazın
- Documentation güncelleyin

## 📄 Lisans

Bu proje MIT lisansı altında lisanslanmıştır. Detaylar için `LICENSE` dosyasına bakın.

## 👨‍💻 Geliştirici

**Erdem KAPLAN** - *Full Stack Developer*

- GitHub: [@erdem](https://github.com/serenitypainteddeath)
- Email: erdemkaplan100@gmail.com

## 🙏 Teşekkürler

- **Firebase**: Authentication ve Database servisleri için
- **Exchange Rate API**: Döviz kuru verileri için
- **MPAndroidChart**: Grafik kütüphanesi için
- **Material Design Team**: Tasarım rehberi için

## 📞 Destek

Sorularınız veya önerileriniz için:
- Issue açın
- Email gönderin
- Discussions bölümünü kullanın

---

⭐ Bu projeyi beğendiyseniz yıldız vermeyi unutmayın!

**Version**: 1.0.0  
**Last Updated**: Haziran 2025 