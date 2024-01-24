# Это Апи для раоботы склада книжного магазина

**В приложении исползуется бд my sql** 

## Что тербуется для начала раоты api
  Открыть в файл application.properties дописать следующие(Убрать "{}" и написать необходимые данные)

  код applicaion.properties:
  
  ```
  
  jdbc.url=jdbc:mysql://{url бд}/{имя бд}
  jdbc.username={Ввести username для доступа к бд }
  jdbc.password={Ввести пароль от бд}
  jdbc.driverClassName=com.mysql.cj.jdbc.Driver 
  
  #Echo all executed sql to console
  hibernate.dialect=org.hibernate.dialect.MySQLDialect
  hibernate.show_sql=true 
  hibernate.format_sql=true
  hibernate.highlight_sql=true
  
  logging.level.org.hibernate.SQL=debug 
  #Automatically export the scheme
  hibernate.hbm2ddl.auto=update
  
  ```
  
### Функционал на данный момент
  1) CURD операции для модели книг, жанров и поставок
  2) Получение книг по заданному жанру
  3) Подтверждение получения заказа
  4) Создание смет при отгрузке и получении книг

  

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
    |provider|String|Название поставщика|
    |expectedDeliveryDate|Date(TimeStamp)|Ожидаемая дата поставки|
    |isAriived|boolean|Пришла ли поставка|

  **SupplyPartModelDto**
  - модель части поставки
    |Название переменной|Тип данных|Описание|
    |----|----|----|
    |id|UUID|уникальный идентификатор|
    |book|BookModelDto|Модель книги|
    |supplyId|UUID|id поставки к к которой эта часть поставки относится|
    |bookCount|int|Количество поставляемых копий книги|

     **OrderModelDto**
  - модель поставки
    |Название переменной|Тип данных|Описание|
    |----|----|----|
    |id|UUID|уникальный идентификатор|
    |books|ArrayList<OrderPartModelDto>|Список книг и их колличества в заказе|
    |sendDate|Date(TimeStamp)|Дата отправки заказа|
    |isAriived|boolean|отправлен ли заказ|
    |userFullName|String|ФИО пользователя|
    |deliveryAdress|String|Адрес доставки|
    |userId|UUID|id пользователя, которому это все едет|

  **OrderPartModelDto**
  - модель части поставки
    |Название переменной|Тип данных|Описание|
    |----|----|----|
    |id|UUID|уникальный идентификатор|
    |book|BookModelDto|Модель книги|
    |orderId|UUID|id заказа к к которому эта часть заказа относится|
    |bookCount|int|Количество поставляемых копий книги|

## Маппинг
  Имеется 4 роута:
  1) /book 
  2) /genre
  3) /supplies
  4) /orders
  
  **Роут /book**
  Роут book имеет следующие эндпоинты:

  |Роут|Тип запроса|Описание|
  |----|----|----|
  |/{id} |delete| удаление по id|
  |/ |create| на вход принимает две form-data: 1) "cover" - обложка книги 2) "book" - BookModelDto в json|
  |/ |put| на вход принимает две form-data: 1) "cover" - обложка книги 2) "book" - BookModelDto в json|
  |/ |get| получение всех книг из бд|
  |/{id} |get| получение книги по id|
  |/genre/{genre_id} |get|получение всех книг в которых есть указаный жанр|

  **Роут /genre**

  Роут genre имеет след эндпоинты:
    
  |Роут|Тип запроса|Описание|
  |----|----|----|
  |/{id}|delete| удаление по id|
  |/|post|на вход принимает GenreDto|
  |/|put|на вход принимает GenreDto|
  |/|get|просто получение всех жанров, которые лежат в бд|
  |/{id}|get|получение жанра по id|

  **Роут /supplies**

  Роут supplies имеет след эндпоинты:
  |Роут|Тип запроса|Описание|
  |----|----|----| 
  |/{id}|delete| удаление по id|
  |/|post| на вход принимает SuppliesDto|
  |/|put| на вход принимает SuppliesDto|
  |/|get|просто получение всех поставок, которые лежат в бд|
  |/{id}|get| получение поставки по id|
  |/arrived|put|Подтвержение что поставка пришла|
  
  **Роут /orders**

  Роут orders имеет след эндпоинты:
  |Роут|Тип запроса|Описание|
  |----|----|----| 
  |/{id}|delete| удаление по id|
  |/|post| на вход принимает OrderModelDto|
  |/|put| на вход принимает OrderModelDto|
  |/|get|просто получение всех заказов, которые лежат в бд|
  |/{id}|get| получение заказа по id|
  |/send|put|Подтвержение что заказ отправлен|
