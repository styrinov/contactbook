Контактная книга

Файл конфигурации `application.properties`
```
server.port=8074
spring.datasource.url = jdbc:postgresql://localhost:5444/contact_book
spring.datasource.driver-class-name = org.postgresql.Driver
spring.datasource.username=postgres
spring.datasource.password=1
spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQL95Dialect
spring.jpa.hibernate.ddl-auto = update
```
**Rest API:**
 * Получить все записи по контактной книге. Метод - GET, URL - "/";
 * Добавить запись. Метод - POST, URL - "/contact"; 
 * Редактировать запись. Метод - PUT,  URL - "/contact/{id}"; 
 * Удалить запись. Метод - DELETE,  URL - "/contact/{id}"; 
 