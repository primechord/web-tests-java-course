### Тесты для учебного приложения php-addressbook

Требования:  
* XAMPP 5.6.11 и php-addressbook 8.2.5  
* Allure Commandline ```gradlew allureReport```  

Запуск тестов:  
```gradlew clean test -Pbrowser=chrome -PverifyUI=true allureServe```

Файл отчета:  
```build/reports/allure-report/index.html```

***Чувствительные данные лежат в репозитории, так как проект учебный***
