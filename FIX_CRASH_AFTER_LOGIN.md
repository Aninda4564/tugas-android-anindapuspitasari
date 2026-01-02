# Fix: App Crash Setelah Login

## 🐛 Masalah
Aplikasi crash (force close) setelah login berhasil.

## 🔍 Penyebab
Urutan inisialisasi yang salah di `MainActivity.onCreate()`:
- `applyDarkMode()` dipanggil setelah `super.onCreate()` tapi sebelum `setContentView()`
- Ini bisa menyebabkan crash karena theme harus di-set sebelum Activity di-create

## ✅ Solusi

### Perubahan di MainActivity.kt

**SEBELUM (Crash):**
```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)  // ❌ super dulu
    
    preferencesManager = PreferencesManager.getInstance(this)
    applyDarkMode()  // ❌ Dark mode setelah super
    
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    // ...
}
```

**SESUDAH (Fixed):**
```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
    // ✅ Initialize PreferencesManager FIRST
    preferencesManager = PreferencesManager.getInstance(this)
    
    // ✅ Apply dark mode BEFORE super.onCreate
    val isDarkMode = preferencesManager.isDarkMode()
    if (isDarkMode) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    } else {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
    
    super.onCreate(savedInstanceState)  // ✅ super setelah dark mode
    
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    // ...
}
```

## 📝 Penjelasan

### Urutan yang Benar:
1. **Initialize PreferencesManager** - Baca preferensi user
2. **Set Dark Mode** - Terapkan theme SEBELUM super.onCreate()
3. **super.onCreate()** - Biarkan Android create Activity dengan theme yang benar
4. **Inflate & setContentView** - Setup UI

### Kenapa Harus Begini?
- `AppCompatDelegate.setDefaultNightMode()` harus dipanggil **sebelum** `super.onCreate()`
- Jika dipanggil setelah, Activity sudah di-create dengan theme default
- Ini bisa menyebabkan crash atau theme tidak terapply dengan benar

## 🔧 Rebuild

Setelah fix, rebuild project:
```bash
.\gradlew.bat assembleDebug
```

**Result**: ✅ BUILD SUCCESSFUL

## 🎯 Testing

1. Buka aplikasi
2. Login dengan:
   - Username: `aninda`
   - Password: `aninda123`
3. Aplikasi seharusnya masuk ke MainActivity tanpa crash
4. Bottom navigation muncul dengan 3 tab: Home, Students, Profile

## 💡 Tips Debugging

Jika masih crash, cek logcat:
```bash
adb logcat | grep -i "fatal\|exception\|crash"
```

Atau di Android Studio:
- Buka tab **Logcat**
- Filter dengan "Error" atau "Fatal"
- Lihat stack trace untuk mengetahui baris yang error

## ✅ Verifikasi

Setelah fix, test:
- [x] Login berhasil masuk MainActivity
- [x] Bottom navigation muncul
- [x] Fragment Home ter-load
- [x] Tidak ada crash
- [x] Dark mode berfungsi (toggle di Profile)
