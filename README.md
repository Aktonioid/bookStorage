# Это Апи для раоботы склада книжного магазина

**В приложении исползуется бд my sql** 

## Что тербуется для начала раоты api
  Открыть в файл application.properties дописать следующие(Убрать "{}" и написать необходимые данные)
  код applicaion.properties
  '{
    jdbc.url=jdbc:mysql://{url бд}/{имя бд}
    jdbc.username={Ввести username для доступа к бд }
    jdbc.password={Ввести пароль от бд}
    jdbc.driverClassName=com.mysql.cj.jdbc.Driver

    # ?useUnicode=true&characterEncoding=utf-8&useSSL=false

    #Echo all executed sql to console
    hibernate.dialect=org.hibernate.dialect.MySQLDialect
    hibernate.show_sql=true 
    hibernate.format_sql=true
    hibernate.highlight_sql=true
    logging.level.org.hibernate.SQL=debug

    #Automatically export the scheme
    hibernate.hbm2ddl.auto=update
  }'

### Функционал на данный момент
  1) CURD операции для модели книг, жанров и поставок
  2) Получение книг по заданному жанру
  3) Подтверждение получения заказа

### Добавится в ближайшее время
  1) Генерация смет о поставке книг на склад
  2) Моделирование отгрузки книг по заказу польззователя из некого онлайн-магазина
  

### Модели
  *Указаны Dto так как именно их принимают на вход контроллеры*

  **BookModelDto**
  - модель книги
    |Название переменной|Тип данных|Описание|
    |----|----|----|
    |id|UUID|уникальный идентификатор|
    |bookName|String|Название книги|
    |authorName|String|Имя автора книги|
    |publisher|String|Название издателя книги|
    |price|int|Цена|
    |isbn|Strign|Международный стандартный книжный номер|
    |genres|ArrayList<GenreModelDto>|Список жанров, относящихся к книге|
    |description|String|Описание книги|
    |pictureUrl|String|Путь до изображения обложки книги|
    |leftovers|short|Сколько книг осталось на складе|
    |----|----|----|

  **GenreModelDto**
  - модель жанра
    |Название переменной|Тип данных|Описание|
    |----|----|----|
    |id|UUID|уникальный идентификатор|
    |name|String|Имя жанра|

  **SuppliesModelDto**
  - модель поставки
    |Название переменной|Тип данных|Описание|
    |----|----|----|
    |id|UUID|уникальный идентификатор|
    |books|ArrayList<SupplyPartModelDto>|Список книг и их колличества в поставке|
    |prvider|String|Название поставщика|
    |expectedDeliveryDate|Date(TimeStamp)|Ожидаемая дата поставки|
    |isAriived|boolean|Пришла ли поставка|

  **SupplyPartModelDto**
  - модель части поставки
    |----|----|----|
    |id|UUID|уникальный идентификатор|
    |book|BookModelDto|Модель книги|
    |supplyId|UUID|id поставки к к которой эта часть поставки относится|
    |bookCount|int|Количество поставляемых копий книги|
    |----|----|----|

## Маппинг
  Имеется 3 роута
    1) /book 
    2) /genre
    3) /supplies
  
  **Роут /book**
  Роут book имеет следующие роуты:

  |Роут|Тип запроса|Описание|
  |----|----|----|
  |/delete/{id} |delete| удаление по id|
  |/create |create| на вход принимает две form-data: 1) "cover" - обложка книги 2) "book" - BookModelDto в json|
  |/update |put| на вход принимает две form-data: 1) "cover" - обложка книги 2) "book" - BookModelDto в json|
  |/ |get| получение всех книг из бд|
  |/{id} |get| получение книги по id|
  |/genre/{genre_id} |get|получение всех книг в которых есть указаный жанр|
  |----|----|----|

  **Роут /genre**

  Роут genre имеет след роуты:
    
  |Роут|Тип запроса|Описание|
  |----|----|----|
  |/delete/{id}|delete| удаление по id|
  |/create|post|на вход принимает GenreDto|
  |update|put|на вход принимает GenreDto|
  |/|get|просто получение всех жанров, которые лежат в бд|
  |/{id}|get|получение жанра по id|
  |----|----|----|

  **Роут /supplies**

  Роут supplies имеет след роуты:
  |Роут|Тип запроса|Описание|
  |----|----|----| 
  |/delete/{id}|delete| удаление по id|
  |/create|post| на вход принимает SuppliesDto|
  |update|put| на вход принимает SuppliesDto|
  |/|get|просто получение всех поставок, которые лежат в бд|
  |/{id}|get| получение поставки по id|
  |/arrived|put|Подтвержение что поставка пришла|
  |----|----|----|