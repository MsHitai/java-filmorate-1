# java-filmorate
## Схема базы данных проекта
![](src/main/resources/schema.png)
- Films
Содержит данные о фильмах
- Genre
Содержит данные о существующих жанрах
- Rating
Содержит данные о существующих рейтингах МРА
- Likes
Содержит данные о том, какой пользователь какой фильм лайкнул
- Users
Содержит данные о пользователях
- Friends
Содержит данные о дружбе, поле friendship отвечает на вопрос - подтверждена ли дружба пользователем friend_id?

### Примеры SQL запросов к БД:
Топ 5 самых популярных фильмов
```
SELECT
films.name
FROM films
WHERE film_id IN (SELECT film_id
                   FROM likes
                   GROUP BY film_id
                   ORDER BY COUNT(user_id) DESC
                   LIMIT 5);
```
Возвращает список всех пользователей 
```
SELECT *
FROM users;
```
