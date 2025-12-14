# Bunny (ЗАЙЧИК)

Мобильное приложение для локального хранения паролей и файлов

<img src="https://i.pinimg.com/736x/ba/f9/16/baf9167cafba9903be117e19925036b4.jpg" width="300" align="right" hspace="20">

### Технологии, фреймворки, библиотеки, архитектура, ...

* Kotlin Multiplatform (Support IOS)
* MVVM
* Clean Architecture
* Jetpack Compose
* Koin (DI)
* Navigation-Compose
* Room (SqlLite)
* ViewModels, ui states, use-cases, repositories, sources
* Crypto, cipher, keystore, sqlcipher
* File
* Snackbar
* DataStore, Tink (in progress)
* Logger
* CI (only Android)
* Git flow

### Реализованный функционал:

* Сохранение информации о пароле с зашифрованном виде
* Получение информации о паролях
* Просмотр пароля в расшифрованном виде
* Изменение информации о пароле
* Удаление паролей
* Добавление файла в папку приложения (временно) в незашифрованном виде
* Просмотр информации о файлах (временно) без предварительного просмотра


### Build and Run Android Application

To build and run the development version of the Android app, use the run configuration from the run widget
in your IDE’s toolbar or build it directly from the terminal:
- on macOS/Linux
  ```shell
  ./gradlew :composeApp:assembleDebug
  ```

### Build and Run iOS Application

To build and run the development version of the iOS app, use the run configuration from the run widget
in your IDE’s toolbar or open the [/iosApp](./iosApp) directory in Xcode and run it from there.

---