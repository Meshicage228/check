# Задание №5 : Check Application : Additional

Проект собирался на Gradle

## Запуск приложения

1. Для начала настройте приложение:
   В `application.yaml` в папке `resources` укажите свои `datasource.name` `datasource.password` и `datasource.url`

2. Запустите приложение через CheckRunner класс

## Что нового?

1. Приложение на SpringBoot

2. Миграция базы данных PostgreSQL при помощи Liquibase

3. Использование JdbcTemplate и RowMapper для удобства и уменьшения кода

4. Использование JakartaValidation для валидации входящих DTO

### Стек
Использовал Java Core, Lombok, Commons-Collections4, Commons-lang3, Liquibase, SpringBoot, JakartaValidation, JdbcTemplate