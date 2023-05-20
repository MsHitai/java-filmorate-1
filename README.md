# java-filmorate
Template repository for Filmorate project.
## Схема базы данных проекта
![](/drawSQL-filmorate-export-2023-05-20.png)
Пример SQL запроса к БД:
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
