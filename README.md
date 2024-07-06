# Задание №3 : Check Application : Data Base + Unit testing

Проект собирался на Gradle

## Запуск приложения

1. Для начала выполните сборку проекта:
    ```
    gradle build
    ```

2. Запустите приложение (к примеру):
    Для корректной работы приложения, необходимо вставить в следующую комманду ваши `datasource.url`  `datasource.username ` и `datasource.password`

    ```
    java -cp "build/classes/java/main;lib/postgresql-42.7.1.jar" ru.clevertec.check.CheckRunner 3-1 2-5 5-1 3-2 1-4 discountCard=4444 balanceDebitCard=1000 saveToFile=./result.csv datasource.url=jdbc:postgresql://localhost:5432/check datasource.username=postgres datasource.password=28072004
    ```
3. Чек будет сгенерирован в корень проекта в файл, указанный в `saveToFile`, а также выведен в консоль

4. При появлении ошибок пробрасывается кастомное исключение, а ее описание будет записано в файл `result.csv`, либо в `saveToFile`

### Стек
Использовал Java Core, Lombok, Commons-Collections4, Commons-lang3, Junit5, AssertJ, Mockito