# Задание №2 : Check Application : file work

Проект собирался на Gradle

## Запуск приложения

1. Для начала выполните сборку проекта:
    ```
    gradle build
    ```

2. Запустите приложение (к примеру):
    При корректной работе приложения, необходимо вставить в корень проекта ваш `products.csv` и указать путь в CLI, например : `pathToFile=./products.csv`
    ```
    java -cp build/classes/java/main ru.clevertec.check.CheckRunner 1-1 discountCard=1111 balanceDebitCard=100 pathToFile=./products.csv
    ```
    ```
    java -cp build/classes/java/main ru.clevertec.check.CheckRunner 1-1 discountCard=1111 balanceDebitCard=100 saveToFile=./error_result.csv
    ```
    ```
    java -cp build/classes/java/main ru.clevertec.check.CheckRunner 1-1 discountCard=1111 balanceDebitCard=100 saveToFile=./total_result.csv pathToFile=./products.csv
    ```
3. Чек будет сгенерирован в корень проекта в файл, указанный в `saveToFile`, а также выведен в консоль

4. При появлении ошибок пробрасывается кастомное исключение, а ее описание будет записано в файл `result.csv`, либо в `saveToFile`

### Стек
Использовал Java Core, Lombok, Commons-Collections4, Commons-lang3