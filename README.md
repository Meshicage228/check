# Задание №4 : Check Application : RESTFUL - API (Servlet)

Проект собирался на Gradle

## Запуск приложения

1. Для начала выполните сборку проекта + war:
    ```
    gradle build
    gradle build war
    ```
2. В `resources` присутствует `data.sql`, где находятся sql-скрипты, которые нужно запустить в той последовательности, в которой они расположены.

3. В ветке присутствует smarttomcat, а именно catalina.properties, из которых считывались DB переменные:

    Для корректной работы приложения, необходимо запустить tomcat с catalina.properties, где указать ваши `datasource.url`  `datasource.username ` и `datasource.password`

### Стек
Использовал Java Core, Lombok, Commons-Collections4, Commons-lang3, Junit5, AssertJ, Mockito, Servlet-API