# Задание №1 : Check Application

Проект собирался на Gradle

## Запуск приложения

1. Для начала выполните сборку проекта:
    ```
    gradle build
    ```

2. Запустите приложение (один из вариантов):
    ```
    java -cp build/classes/java/main ru.clevertec.check.CheckRunner 1-5 4-30 9-1 13-2 13-3 1-1 discountCard=1111 balanceDebitCard=100
    ```

3. Чек будет сгенерирован в корень проекта в файл `result.csv`, а также выведен в консоль


4. При появлении ошибок пробрасывается кастомное исключение, а ее описание будет записано в файл `result.csv`

### Стек
Использовал Java Core, Lombok, Commons-Collections4, Commons-lang3