# JWT_Teacher_Student
[_**Dmitriy Liakhov**_](https://www.linkedin.com/in/dmitiy-liakhov-82388a183/)<br>
[dimaliahov@gmail.com](mailto:dimaliahov@gmail.com)

<ul align="center">
    <li>
        <h1>TASK<br></h1>
    </li>
</ul>
<ul>
Розробити Appointment API для онлайн університету, в якому буде 2 ролі: викладач та
студент.<br>
<h4>Викладач :<br></h4>
    
- може вказувати коли і скільки часу він доступний для індивідуальних занять
- може вказувати ціну за певну к-сть часу. Наприклад 15 хв коштують 30$, 30 хв = 55$, 60
хв = 100$
- може підтверджувати або відхиляти участь студентів (approve/decline)

<h4>Студент :<br></h4>

- може зарезервувати певний час
- може скасувати резервацію

<h4>Система повинна мати:<br></h4>

- REST Api для реєстрації, логування користувача (JWT token)<br>
- REST Api Апі для викладача<br>
- REST Api Апі для студента<br>
- Логіку та Валідацію даних<br>
- Збереження даних в БД<br>

<h4>Мінімально необхідні валідації даних:<br></h4>
- Чи поля об’єкта не пусті<br>
- Чи dateFrom < dateTo<br>
- і т.д.<br>
<h4>Технічний стек :<br></h4>
1. Java 8<br>
2. Spring Boot 2.2.* (Security, MVC, Data Jpa)<br>
3. SQL<br>
4. Maven 3.6.*<br>

</ul>
<ul align="center">
    <li>
        <h1>Technical documentation<br></h1>
    </li>
</ul>
    <h4>Как зупустить проект?<br></h4>
    
- Скачать репозиторий
- Создать SQL базу
- В файле application.properties внести зміни
  - spring.datasource.url =jdbc:mysql://localhost:3306/[імя SQL бази]?serverTimezone=UTC<br>
            spring.liquibase.url=jdbc:mysql://localhost:3306/[імя SQL бази]?serverTimezone=UTC
  - spring.datasource.username=[SQL username]<br>
            spring.liquibase.user=[SQL username]
  - spring.datasource.password=[SQL password]<br>
            spring.liquibase.password=[SQL password]
- P.S: База повинна бути пустою.

