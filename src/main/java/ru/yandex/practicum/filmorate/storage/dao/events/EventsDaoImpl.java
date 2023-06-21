package ru.yandex.practicum.filmorate.storage.dao.events;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Event;
import ru.yandex.practicum.filmorate.storage.mapper.EventsMapper;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class EventsDaoImpl implements EventsDao {
    private final JdbcTemplate jdbcTemplate;

    public List<Event> getUserEvents(Long userId) {
        return jdbcTemplate.query("SELECT * FROM events WHERE user_id = ?", new EventsMapper(), userId);
    }

    public Event findEventById(Long id) {
        return jdbcTemplate.query("SELECT * FROM events WHERE event_id = ?", new EventsMapper(), id)
                .stream()
                .findAny()
                .orElse(null);
    }

    public void addEvent(long timestamp, long userId, String eventType, String operation, long entityId) {
        jdbcTemplate.update("INSERT INTO events (timestamp, user_id, event_type, operation, entity_id) " +
                "VALUES (?, ?, ?, ?, ?)",
                timestamp,
                userId,
                eventType,
                operation,
                entityId);
    }
}
