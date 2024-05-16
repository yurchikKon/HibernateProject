Приложение HibernateApp

Данный проект обеспечивает основную CRUD функциональность ( чтение, вставка, изменение и удаление из базы данных)

Работа ведется с четырьмя сущностями: Работник, проект, личная карта и отдел

Слои проекта:

        Controllers - маппинг запросов на сервер, обработка и возврат результатов. Всего контролера - 4, под каждую
    сущность.
    
        Services - классы, в которых реализуется основная бизнес-логика приложений, их вызывают контроллеры, а они 
    вызывают репозитории. Также, в них присутствует обработка входных данных, валидация и необходимые проверки.
    
        Repositories - классы, работающие с базой данных, вызываются репозиториями, проводят операции над базой данных
    и возвращают результат в сервисы.
    
        Entities - классы, на которые мапятся таблицы базы данных, имеют те же поля что и поля таблиц и методы, для
    работы с ними.
    
        DTO - классы, представляющие обертку для транспортировки данных. Используются как для входных данных, так и
    для выходных.
    
        Exceptions - классы, представляющие кастомные исключения, необходимые проекту.
        
Технологии проекта:
    Java
    Spring Boot
    PostgreSQL
    Hibernate
Более подробно со всеми инструментами и их версиями, которые были использованы в проекте можно ознакомиться в файле
pom.xml в разделе dependencies.
