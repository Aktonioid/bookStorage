# Это Апи для раоботы склада книжного магазина

**В приложении исползуется бд my sql** 

## Что тербуется для начала раоты api
  Открыть в файл application.properties дописать следующие

  {
    
  }
  1) jdbc.url=jdbc:mysql://localhost:3306/{databaseName} - прописать на месте {} имя базы данных в которую сохраняется
  2) jdbc.username={username} - прописать логин для доступа к бд {}
  3) jdbc.password={password} - прописать пароль для доступа к бд на месте {}
  Фигурные скобки везде удалить

### Функционал на данный момент - добавление книг, жанров и заказов. Подтверждения заказов пока нет в течении пары дней доделаю.

### модели расписать грамотно не успеваю.

## Везде маппинг одинаковый
 
  - /delete/{id} - удаление по id
  - /create - на вход принимает дто модель, но для котроллера книги так же приходит и файл
  - update - на вход принимает дто модель, но для котроллера книги так же приходит и файл
  - / - протсо получеине всех моделей, которые лежат в бд
  - /{id} - получение модели ао id
