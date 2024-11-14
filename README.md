

# Приложение Create Document

## 📄 Обзор
Create Document Application — это мощное Spring-приложение для генерации отчетов в формате PDF с использованием JasperReports. Оно поддерживает настройку отчетов через JSON-файлы конфигурации и получение данных от клиента.

## 🛠 Структура проекта
- **`ReportConfig`**: Компонент для загрузки конфигурации отчетов из внешнего файла JSON.
- **`JReportService`**: Сервис, отвечающий за компиляцию и генерацию отчетов в формате PDF.
- **`ReportController`**: REST-контроллер, предоставляющий API для генерации отчетов.

## ✨ Функциональность
- Генерация отчетов в формате PDF с использованием шаблонов JasperReports.
- Валидация структуры входных данных на основе конфигурации.
- Поддержка переменных окружения для гибкого управления путями, что удобно для использования с Docker.

## 🔧 Переменные окружения
- **`CONFIG_PATH`**: Путь к JSON-файлу конфигурации отчетов (например, `/external-reports/config.json`).
- **`REPORT_DIRECTORY`**: Путь к папке с шаблонами отчетов (например, `/external-reports`).

## 🚀 Установка и запуск

### 1. Сборка Docker-образа
Используйте предоставленный `Dockerfile`, чтобы собрать Docker-образ:

```bash
docker build -t jasper-app-last:latest .
```

### 2. Сохранение и перенос Docker-образа
Сохраните собранный образ в файл tar для переноса:

```bash
docker save -o jasper-app-last.tar jasper-app-last:latest
```

Загрузите образ на другую машину:

```bash
docker load -i jasper-app-last.tar
```

### 3. Создание директории для конфигурации
Пользователи должны создать собственную директорию с конфигурационным файлом `config.json` и шаблонами отчетов. Путь к этой директории нужно будет указать при запуске контейнера.

### 4. Запуск контейнера
Запустите контейнер с пробросом портов и подключением папки с отчетами:

```bash
docker run -d -p 8000:8081 -e CONFIG_PATH=/external-reports/config.json -e REPORT_DIRECTORY=/external-reports -v /path/to/your/config-directory:/external-reports jasper-app-last:latest
```

## 📬 Использование API

### Конечная точка: `POST /reports/{name}`
Генерация отчета.

**Параметры**:
- `name`: Имя шаблона отчета.
- **Тело запроса**: JSON-объект с данными для генерации отчета.

**Пример запроса**:
```json
{
  "employeeName": "John Doe",
  "employeePosition": "Software Engineer",
  "title": "Annual Leave Request",
  "text": "Request for leave from June 1 to June 15.",
  "bossName": "Jane Smith",
  "bossPosition": "CTO"
}
```

**Ответ**: PDF-файл с отчетом.

## 🗂 Конфигурационный файл
Пользователи должны создать собственный конфигурационный файл `config.json`, который будет содержать описание ожидаемых структур данных для отчетов.

**Пример структуры файла `config.json`**:
```json
{
  "vocation": {
    "employeeName": "string",
    "employeePosition": "string",
    "title": "string",
    "text": "string",
    "bossName": "string",
    "bossPosition": "string"
  },
  "vocation2": {
    "employeeName": "string",
    "employeePosition": "string",
    "title": "string",
    "text": "string",
    "bossName": "string",
    "bossPosition": "string"
  }
}
```

## 📋 Требования
- Java 17+
- Docker
- JasperReports библиотека

## 📦 Зависимости
- Spring Boot
- JasperReports
- Jackson для работы с JSON
