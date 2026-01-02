# Cara Mengatasi Error ViewBinding

## ✅ Build Sudah Berhasil!

ViewBinding classes sudah di-generate oleh Gradle. Sekarang tinggal sync di Android Studio.

## 🔧 Langkah-langkah:

### 1. Sync Project dengan Gradle
Di Android Studio, klik salah satu:
- **File → Sync Project with Gradle Files**
- Atau klik icon 🐘 (Sync) di toolbar
- Atau tekan **Ctrl+Shift+O** (Windows/Linux) / **Cmd+Shift+O** (Mac)

### 2. Rebuild Project (Opsional)
Jika masih error setelah sync:
- **Build → Rebuild Project**

### 3. Invalidate Caches (Jika masih error)
Jika masih ada masalah:
- **File → Invalidate Caches / Restart**
- Pilih "Invalidate and Restart"

## 📁 ViewBinding Classes yang Di-generate

Setelah sync, class-class ini akan tersedia:

```kotlin
// Auto-generated di: app/build/generated/data_binding_base_class_source_out/
com.latihan.project_tugas_android_akhir.databinding.ActivityMainBinding
com.latihan.project_tugas_android_akhir.databinding.ActivityLoginBinding
com.latihan.project_tugas_android_akhir.databinding.ActivitySplashBinding
com.latihan.project_tugas_android_akhir.databinding.ActivityCalculatorBinding
com.latihan.project_tugas_android_akhir.databinding.ActivityTicketBinding
com.latihan.project_tugas_android_akhir.databinding.ActivityAboutBinding
com.latihan.project_tugas_android_akhir.databinding.FragmentHomeBinding
com.latihan.project_tugas_android_akhir.databinding.FragmentStudentsBinding
com.latihan.project_tugas_android_akhir.databinding.FragmentProfileBinding
com.latihan.project_tugas_android_akhir.databinding.ItemStudentBinding
com.latihan.project_tugas_android_akhir.databinding.DialogStudentFormBinding
```

## ✅ Verifikasi ViewBinding Enabled

Cek di `app/build.gradle.kts`:

```kotlin
android {
    ...
    buildFeatures {
        viewBinding = true  // ✅ Sudah ada
    }
}
```

## 🎯 Setelah Sync

Error "Unresolved reference 'databinding'" akan hilang dan IDE akan mengenali:
- `ActivityMainBinding`
- `ActivityLoginBinding`
- Semua binding classes lainnya

## 💡 Tips

Jika menggunakan terminal/command line untuk build:
```bash
# Build project
.\gradlew.bat assembleDebug

# Clean & rebuild
.\gradlew.bat clean assembleDebug
```

Tapi tetap harus **Sync di Android Studio** agar IDE mengenali generated classes!
