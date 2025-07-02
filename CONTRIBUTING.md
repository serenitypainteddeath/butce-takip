# 🤝 Katkıda Bulunma Rehberi

Budget Tracker projesine katkıda bulunmak istediğiniz için teşekkür ederiz! Bu rehber, projeye nasıl katkıda bulunabileceğiniz konusunda size yardımcı olacaktır.

## 🚀 Başlamadan Önce

### Gereksinimler
- **Android Studio**: Hedgehog (2023.1.1) veya üzeri
- **JDK**: 17 veya üzeri
- **Git**: Versiyon kontrolü için
- **Firebase Account**: Authentication ve Firestore için

### Proje Kurulumu
1. Repository'yi fork edin
2. Fork'unuzu yerel makinenize klonlayın
3. Firebase konfigürasyonunu yapın
4. Bağımlılıkları yükleyin

## 📋 Katkı Türleri

### 🐛 Bug Raporları
Bug bulduğunuzda:
1. GitHub Issues bölümünde aynı bug'ın zaten rapor edilip edilmediğini kontrol edin
2. Bug'ı yeniden oluşturmak için gerekli adımları listeleyin
3. Beklenen ve gerçek davranışı açıklayın
4. Ekran görüntüleri ve logları ekleyin

### ✨ Özellik Önerileri
Yeni özellik önerirken:
1. Özelliğin nedenini ve kullanım durumunu açıklayın
2. Mümkünse mockup'lar veya wireframe'ler ekleyin
3. Teknik implementasyon önerilerinizi paylaşın

### 🔧 Kod Katkıları
Kod katkısı yaparken:
1. Issue açın ve önerdiğiniz çözümü tartışın
2. Feature branch oluşturun
3. Kod standartlarına uyun
4. Test yazın
5. Pull Request açın

## 📝 Kod Standartları

### Kotlin Kuralları
- **Official Kotlin Coding Conventions** kullanın
- **4 boşluk** indentation kullanın
- Fonksiyon ve değişken isimlerini **camelCase** ile yazın
- Class isimlerini **PascalCase** ile yazın
- Constant'ları **UPPER_SNAKE_CASE** ile yazın

### Commit Mesajları
```
tip: kısa açıklama (50 karakter max)

Daha detaylı açıklama (gerekirse)
- Değişiklik listesi
- Diğer önemli notlar

Fixes #123
```

**Commit Tipleri:**
- `feat`: Yeni özellik
- `fix`: Bug düzeltmesi
- `docs`: Dokümantasyon
- `style`: Kod formatting (mantık değişikliği yok)
- `refactor`: Kod refactoring
- `test`: Test ekleme/düzenleme
- `chore`: Build process, dependencies

### Dosya Yapısı
```kotlin
// Telif hakkı ve lisans bilgisi
package com.erdem.budgettracker.ui.screens.example

// Standart kütüphane import'ları
import androidx.compose.runtime.*

// Üçüncü parti kütüphaneler
import com.example.library.*

// Proje içi import'lar
import com.erdem.budgettracker.data.model.*

/**
 * Example composable açıklaması
 * @param parameter Parametre açıklaması
 */
@Composable
fun ExampleScreen(
    parameter: String,
    modifier: Modifier = Modifier
) {
    // Implementation
}
```

## 🧪 Test Yazma

### Unit Test'ler
- ViewModel'lar için test yazın
- Repository pattern'i test edin
- Utility fonksiyonları test edin

### UI Test'leri
- Critical user flow'lar için UI test yazın
- Compose test utilities kullanın

```kotlin
@Test
fun example_test() {
    // Given
    val expected = "expected_result"
    
    // When
    val actual = functionUnderTest()
    
    // Then
    assertEquals(expected, actual)
}
```

## 🔄 Pull Request Süreci

### PR Öncesi Kontrol Listesi
- [ ] Kod standartlarına uygun mu?
- [ ] Test'ler yazıldı mı?
- [ ] Dokümantasyon güncellendi mi?
- [ ] Build başarılı mı?
- [ ] Lint hataları var mı?

### PR Şablonu
```markdown
## Değişiklik Özeti
Bu PR'da yapılan değişikliklerin kısa açıklaması

## Değişiklik Türü
- [ ] Bug fix
- [ ] New feature
- [ ] Breaking change
- [ ] Documentation update

## Test Edildi Mi?
- [ ] Unit tests
- [ ] Integration tests
- [ ] Manual testing

## Ekran Görüntüleri
(Gerekirse ekran görüntüleri ekleyin)

## İlgili Issue'lar
Fixes #123
Related to #456
```

## 🎨 UI/UX Kılavuzu

### Material Design 3
- Material Design 3 prensiplerine uyun
- Theme.kt dosyasındaki renk paletini kullanın
- Consistent spacing kullanın (8dp grid system)

### Accessibility
- Content description'ları ekleyin
- Semantic'leri doğru kullanın
- Touch target'ları minimum 48dp yapın

### Responsive Design
- Farklı ekran boyutlarını test edin
- Landscape mode'u destekleyin
- Tablet layoutları optimize edin

## 🌐 Çeviri

### Yeni Dil Ekleme
1. `res/values-[language_code]/` klasörü oluşturun
2. `strings.xml` dosyasını çevirin
3. Date/time formatlarını yerelleştirin
4. Currency formatlarını ayarlayın

### Çeviri Kuralları
- Tutarlı terminoloji kullanın
- Teknik terimleri mümkünse yerelleştirin
- Context'e uygun çeviriler yapın

## 📚 Dokümantasyon

### Code Documentation
- Public fonksiyonlar için KDoc yazın
- Complex algoritmalar için inline comment'ler ekleyin
- Architecture kararlarını dokümante edin

### README Güncellemeleri
- Yeni özellikler eklediğinizde README'yi güncelleyin
- Sistem gereksinimlerini kontrol edin
- Setup talimatlarını test edin

## 🤖 CI/CD

### GitHub Actions
- Her PR'da otomatik build çalışır
- Lint check'leri yapılır
- Test'ler otomatik çalıştırılır

### Build Başarısızlıkları
Build başarısız olursa:
1. Log'ları kontrol edin
2. Yerel makinenizde test edin
3. Gerekirse yardım isteyin

## 📞 İletişim

### Soru ve Yardım
- **GitHub Discussions**: Genel sorular için
- **GitHub Issues**: Bug'lar ve özellik önerileri için
- **Email**: Özel konular için

### Topluluk Kuralları
- Saygılı ve yapıcı olun
- Farklı görüşlere açık olun
- Yardımlaşmayı teşvik edin
- Kod of conduct'a uyun

## 🎯 Öncelikli Alanlar

Şu anda katkıya en çok ihtiyaç duyulan alanlar:
- [ ] Unit test coverage artırımı
- [ ] Accessibility iyileştirmeleri
- [ ] Performance optimizasyonları
- [ ] Çoklu dil desteği
- [ ] Tablet UI optimizasyonu

---

Katkılarınız için tekrar teşekkür ederiz! 🙏

Herhangi bir sorunuz varsa çekinmeden sorun. 