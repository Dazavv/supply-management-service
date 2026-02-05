# Supply Management Service

**Supply Management Service** — это RESTful сервис для управления поставщиками и продуктами. Реализован на **Spring Boot**, использует **Spring Security с JWT** для аутентификации и авторизации, а также **PostgreSQL** в качестве базы данных.

---

## Основные функции

### Пользователи и роли

* **ADMIN** — доступ к созданию/изменению поставщиков, просмотр продуктов/поставок/отчета.
* **SUPPLIER** — управление своими продуктами/поставками.
* **VIEWER** — роль, полученная при регистрации. Для дальнейшего взаимодействия с сервисом нужно будет получить роль Admin или Supplier.

### Поставщики (Suppliers)

* Создание нового поставщика (только ADMIN)
* Получение информации о поставщике
* Удаление поставщика (только ADMIN, с удалением всех связанных продуктов)

### Продукты (Products)

* Создание продукта (SUPPLIER)
* Редактирование продукта (SUPPLIER)
* Удаление продукта (SUPPLIER)
* Получение списка продуктов:

    * По конкретному поставщику (ADMIN)
    * Своих продуктов (SUPPLIER)
* Получение конкретного продукта по ID

---

## Технологии

* **Spring Boot 4**
* **Spring Security + JWT**
* **Spring Data JPA / Hibernate**
* **PostgreSQL**
* **Lombok**
* **JUnit 5 + MockMvc** для тестов
* **Docker**

---

## Настройка и запуск

1. Клонируем репозиторий:

```bash
git clone <repo_url>
cd supply-management-service
```

2. Настройка `application.yml` / `application.properties`:

пример yaml:
```yaml
jwt.secret.access=secretAccessToken
jwt.secret.refresh=secretRefreshToken

users.logins=admin,supplier1,supplier2,supplier3
users.passwords=admin,supplier1,supplier2,supplier3
users.names=Admin,supplier1,supplier2,supplier3
users.surnames=Admin,supplier1,supplier2,supplier3
users.emails=admin@example.com,supplier1@example.com,supplier2@example.com,supplier3@example.com
users.phones=+70000000001,+70000000002,+70000000003,+70000000004
users.roles=ADMIN,SUPPLIER,SUPPLIER,SUPPLIER
```

3. Сборка и запуск через Docker Compose:

```bash
docker-compose up --build
```

---

## Аутентификация

* Используется **JWT токен**.
* Для доступа к защищённым эндпоинтам нужно передавать токен в заголовке:

```
Authorization: Bearer <your_jwt_token>
```

* Эндпоинты для SUPPLIER доступны только пользователю с ролью `SUPPLIER`.
* Эндпоинты для ADMIN — только для роли `ADMIN`.

---

## API Эндпоинты

### Suppliers

| Метод  | URL                     | Роль  | Описание                                                        |
| ------ | ----------------------- | ----- | --------------------------------------------------------------- |
| GET    | `/api/v1/supplier`      | ADMIN | Получить список всех поставщиков                                |
| POST   | `/api/v1/supplier`      | ADMIN | Создать нового поставщика                                       |
| GET    | `/api/v1/supplier/{id}` | ADMIN | Получить поставщика по ID                                       |
| PUT    | `/api/v1/supplier`      | ADMIN | Обновить поставщика                                             |
| DELETE | `/api/v1/supplier/{id}` | ADMIN | Удалить поставщика (с удалением связанных продуктов и доставок) |


### Products

| Метод  | URL                                            | Роль     | Описание                                 |
| ------ | ---------------------------------------------- | -------- | ---------------------------------------- |
| POST   | `/api/v1/products`                             | SUPPLIER | Создать продукт                          |
| PUT    | `/api/v1/products/{productId}`                 | SUPPLIER | Обновить продукт                         |
| DELETE | `/api/v1/products/delete/{productId}`          | SUPPLIER | Удалить продукт                          |
| GET    | `/api/v1/products/supplier/me`                 | SUPPLIER | Получить свои продукты                   |
| GET    | `/api/v1/products/supplier/me/{productId}`     | SUPPLIER | Получить свой продукт по ID              |
| GET    | `/api/v1/products/admin/{productId}`           | ADMIN    | Получить продукт по ID                   |
| GET    | `/api/v1/products/admin/supplier/{supplierId}` | ADMIN    | Получить продукты конкретного поставщика |

### Delivery

| Метод  | URL                                            | Роль     | Описание                                 |
| ------ | ---------------------------------------------- | -------- | ---------------------------------------- |
| POST   | `/api/v1/products`                             | SUPPLIER | Создать продукт                          |
| PUT    | `/api/v1/products/{productId}`                 | SUPPLIER | Обновить продукт                         |
| DELETE | `/api/v1/products/delete/{productId}`          | SUPPLIER | Удалить продукт                          |
| GET    | `/api/v1/products/supplier/me`                 | SUPPLIER | Получить свои продукты                   |
| GET    | `/api/v1/products/supplier/me/{productId}`     | SUPPLIER | Получить свой продукт по ID              |
| GET    | `/api/v1/products/admin/{productId}`           | ADMIN    | Получить продукт по ID                   |
| GET    | `/api/v1/products/admin/supplier/{supplierId}` | ADMIN    | Получить продукты конкретного поставщика |

### User

| Метод  | URL                   | Роль  | Описание                    |
| ------ | --------------------- | ----- | --------------------------- |
| POST   | `/api/v1/users/roles` | ADMIN | Назначить роль пользователю |
| GET    | `/api/v1/users`       | ADMIN | Получить всех пользователей |
| DELETE | `/api/v1/users/{id}`  | ADMIN | Удалить пользователя        |

### Report

| Метод | URL                                                | Роль  | Описание                                  |
| ----- | -------------------------------------------------- | ----- | ----------------------------------------- |
| POST  | `/api/v1/reports/deliveries`                       | ADMIN | Сформировать отчёт по доставкам           |
| POST  | `/api/v1/reports/deliveries/supplier/{supplierId}` | ADMIN | Отчёт по доставкам конкретного поставщика |
| POST  | `/api/v1/reports/deliveries/export/csv`            | ADMIN | Экспорт отчёта по доставкам в CSV         |


### Auth

| Метод | URL                     | Роль | Описание                           |
| ----- | ----------------------- | ---- | ---------------------------------- |
| POST  | `/api/v1/auth/register` | —    | Регистрация нового пользователя    |
| POST  | `/api/v1/auth/login`    | —    | Вход пользователя (возвращает JWT) |
| POST  | `/api/v1/auth/logout`   | —    | Выход пользователя (отзыв токена)  |
| POST  | `/api/v1/auth/refresh`  | —    | Обновление JWT токена              |
| POST  | `/api/v1/auth/token`    | —    | Генерация нового токена            |

---


## Пример отчёта по доставкам

Эндпоинт:

```
POST /api/v1/reports/deliveries
```

**Описание:**
Возвращает сводный отчёт по доставкам за указанный период, включая данные по поставщикам, продуктам и суммарные показатели.

**Пример запроса:**

```json
{
  "startDate": "2026-02-05T18:55:55.727Z",
  "endDate": "2026-02-28T18:55:55.727Z"
}
```

**Пример ответа:**

```json
{
  "period": {
    "start": "2026-02-05T18:55:55.727",
    "end": "2026-02-28T18:55:55.727"
  },
  "suppliers": [
    {
      "supplierId": 1,
      "supplierName": "Supplier1",
      "totalWeight": 300,
      "totalPrice": 65000,
      "products": [
        {
          "supplierId": 1,
          "productName": "Apple Green",
          "productType": "APPLE",
          "totalWeight": 100,
          "totalPrice": 25000
        },
        {
          "supplierId": 1,
          "productName": "Apple Red",
          "productType": "APPLE",
          "totalWeight": 200,
          "totalPrice": 40000
        }
      ]
    },
    {
      "supplierId": 2,
      "supplierName": "Supplier2",
      "totalWeight": 600,
      "totalPrice": 130000,
      "products": [
        {
          "supplierId": 2,
          "productName": "Apple Green",
          "productType": "APPLE",
          "totalWeight": 200,
          "totalPrice": 50000
        },
        {
          "supplierId": 2,
          "productName": "Apple Red",
          "productType": "APPLE",
          "totalWeight": 400,
          "totalPrice": 80000
        }
      ]
    }
  ],
  "grandTotals": {
    "totalWeight": 900,
    "totalPrice": 195000
  }
}
```
