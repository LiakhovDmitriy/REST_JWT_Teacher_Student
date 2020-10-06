# JWT_Teacher_Student
[_**Dmitriy Liakhov**_](https://www.linkedin.com/in/dmitiy-liakhov-82388a183/)
[dimaliahov@gmail.com](mailto:dimaliahov@gmail.com)

# Завдання

>Розробити Appointment API для онлайн університету, в якому буде 2 ролі: викладач та
студент.
 - Викладач :
   - може вказувати коли і скільки часу він доступний для індивідуальних занять
   - може вказувати ціну за певну к-сть часу. Наприклад 15 хв коштують 30$, 30 хв = 55$, 60
хв = 100$
   - може підтверджувати або відхиляти участь студентів (approve/decline)
 - Студент :
   - може зарезервувати певний час
   - може скасувати резервацію
 - Система повинна мати :
   - REST Api для реєстрації, логування користувача (JWT token)<br>
   - REST Api Апі для викладача<br>
   - REST Api Апі для студента<br>
   - Логіку та Валідацію даних<br>
   - Збереження даних в БД<br>
 - Мінімально необхідні валідації даних :
   - Чи поля об’єкта не пусті
   - Чи dateFrom < dateTo
   - і т.д.
 - Технічний стек :
   - Java 8
   - Spring Boot 2.2.* (Security, MVC, Data Jpa)
   - SQL
   - Maven 3.6.*

# Технічна документація
## Як зупустить проект
   - Скачать репозиторій
   - Створити SQL базу
   - В файлі application.properties внести зміни
     - spring.datasource.url =jdbc:mysql://localhost:3306/[імя SQL бази]?serverTimezone=UTC
     - spring.liquibase.url=jdbc:mysql://localhost:3306/[імя SQL бази]?serverTimezone=UTC
     - spring.datasource.username=[SQL username]
     - spring.liquibase.user=[SQL username]
     - spring.datasource.password=[SQL password]<br>
     - spring.liquibase.password=[SQL password]
   - P.S: База повинна бути пустою.

## Запити
####  -  Реєстрація
 - Метод POST
 - http://localhost:{yourPort}/api/registration
 - Тіло запиту, приклад:
```json
{
    "username":"Ivan",
    "firstName":"Ivanovich",
    "lastName":"Ivanov",
    "email":"ivan007@gmail.com",
    "password":"verySecurePassword",
    "role":"STUDENT"
}
```
- Відповідь:
     
```json
{
    "user Ivan": "Registration successful: User{username='Ivan', firstName='Ivanovich', lastName='Ivanov', email='ivan007@gmail.com', money='0', password='[password for encryption via bcrypt]'}"
}
```
####  -  Логін
 - Метод POST
 - http://localhost:{yourPort}/api/login
 - Тіло запиту, приклад:
```json
{
    "username":"Ivan",
    "password":"verySecurePassword"
}
```
- Відповідь:
    
```json
{
    "username": "Ivan",
    "token": "[token]"
}
```
####  -  В якості ролі "ADMIN"
 - Метод Get
 - http://localhost:{yourPort}/api/{id}
 - Відповідь:
```json
{
    "id": 2,
    "username": "teacher",
    "firstName": "FirstNameT",
    "lastName": "LastNameT",
    "email": "emailT@gmail.com"
}
```
####  -  В якості ролі "STUDENT"
###### Створити запит на індивідуальне зайняття
 - Метод POST
 - http://localhost:{yourPort}/api/student/create
 - Тіло запиту, приклад:
```json
{
    "dateStart":"2020-01-24 00:00",
    "dateEnd":"2020-01-24 00:01",
    "price":"24",
    "idTeacher":"2"
}
```
- Відповідь:
    
```json
{
    "msg": "The lesson was created by - Ivan; he chose a teacher - teacher",
    "timeEnd": "2020-01-24 00:00",
    "timeStart": "2020-01-24 00:01"
}
```
###### Подивитися всі свої запити на індивідуальні зайняття
 - Метод Get
 - http://localhost:{yourPort}/api/student/myLessons
 - Відповідь:
```json
{
    "msg": [
        {
            "id": 1,
            "created": "2020-01-23 00:00",
            "updated": "2020-01-23 00:00",
            "status": "ACTIVE",
            "idTeacher": 2,
            "dateStart": "2020-01-24 00:00",
            "dateEnd": "2020-01-24 00:01",
            "price": 24
        }
    ]
}
```
###### Видалити (змінити статус) запит на індивідуальне зайняття
 - Метод POST
 - http://localhost:{yourPort}/api/student/myLessons
 - Тіло запиту, приклад:
```json
[
    1,
    2
]
```
- Відповідь:
    
```json
{
    "lesson 1": "Now the status is this: NOT_ACTIVE",
    "lesson 2": "Now the status is this: NOT_ACTIVE"
}
```
###### Подивитися всих вчителів
 - Метод Get
 - http://localhost:{yourPort}/api/student/teacher
 - Відповідь:
```json
{
    "Available teacher 'teacher'": "His id: 2"
}
```
###### Подивитися вчителя та його прайс по "id" вчителя
 - Метод Get
 - http://localhost:{yourPort}/api/student/teacher/{id}
 - Відповідь:
```json
{
    "Teacher: 'teacher'; His id 2": {
        "Price 1": "Time 60 minutes; Price: 55"
    }
}
```
####  -  В якості ролі "TEACHER"
###### Подивитися пропозиції на індивідуальні зайняття
 - Метод Get
 - http://localhost:{yourPort}/api/teacher/offers
 - Відповідь:
```json
{
    "msg": [
        {
            "id": 6,
            "created": "2020-01-24 00:00",
            "updated": "2020-01-24 00:00",
            "status": "CONSIDERATION",
            "idTeacher": 2,
            "dateStart": "2020-01-25 00:00",
            "dateEnd": "2020-01-25 00:01",
            "price": 24
        }
    ]
}
```
###### Прийняти та відмінити зайняття
 - Метод POST
 - http://localhost:{yourPort}/api/teacher/offers
 - Тіло запиту, приклад:
```json
[
    {
        "lessonId":[
           1,
           2
        ],
        "status": "CONSIDERATION"
    }
]
```
- Відповідь:
    
```json
{
    "lesson 1": "Now the status is: NOT_ACTIVE",
    "lesson 2": "Now the status is: NOT_ACTIVE"
}
```
###### Додати час коли вільний
 - Метод POST
 - http://localhost:{yourPort}/api/teacher/add
 - Тіло запиту, приклад:
```json
[
    {
        "timeStart": "2020-01-24 08:30",
        "timeEnd": "2020-01-24 09:00"
    },
    {
        "timeStart": "2020-01-25 08:30",
        "timeEnd": "2020-01-25 09:00"
    }
]
```
- Відповідь:
    
```json
{
    "msg": "Available time was add!",
    "interval 0": "Start: 2020-01-24 08:30;  End: 2020-01-24 09:00",
    "interval 1": "Start: 2020-01-25 08:30;  End: 2020-01-25 09:00"
}
```
###### Додати ціну за певний проміжок часу (Прайс-лист)
 - Метод POST
 - http://localhost:{yourPort}/api/teacher/addToPriceList
 - Тіло запиту, приклад:
```json
{
    "time":"60",
    "price":"100"
}
```
- Відповідь:
    
```json
{
    "price": {
        "time": 60,
        "price": 100
    }
}
```
###### Подивитися свій прайс-лист
 - Метод Get
 - http://localhost:{yourPort}/api/teacher/myPriceList
 - Відповідь:
```json
{
    "Prices": {
        "Price 0": "Time 60 minutes; Price: 100",
    },
    "My username": "teacher"
}
```
###### Видалити щось з прайс-листу
 - Метод POST
 - http://localhost:{yourPort}/api/teacher/myPriceList
 - Тіло запиту, приклад:
```json
[
    1
]
```
- Відповідь:
    
```json
{
    "Price 1": "Now the status is this: NOT_ACTIVE"
}
```
####  -  Залишилось додати
 - Перевірка тайм слотів на перетин між собою та на унікальність
