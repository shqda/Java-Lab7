# Задание. Settings и телефонная книга

## 1. Settings

Реализовать класс `Settings` для хранения пар **«имя параметра — значение»**:

* имя — `String`
* значение — `int`

### Требования

* Использовать `HashMap`

### Методы

* `toString()`
* `equals()`
* `put(String key, int value)`
* `int get(String key)`
* `delete(String key)`

---

### Работа с файлами

* `loadFromBinaryFile(String filename)`
* `saveToBinaryFile(String filename)`
* `loadFromTextFile(String filename)`
* `saveToTextFile(String filename)`

---

## Дополнительное задание

### Телефонная книга

Создать классы для хранения информации об абонентах.

### Требования

* Реализовать:

  * чтение из бинарного файла
  * запись в бинарный файл
  * чтение из текстового файла
  * запись в текстовый файл

* Продумать структуру данных абонента так, чтобы:

  * бинарный файл занимал меньше места, чем текстовый
