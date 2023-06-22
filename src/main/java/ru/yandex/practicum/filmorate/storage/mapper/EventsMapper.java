package ru.yandex.practicum.filmorate.storage.mapper;

import org.springframework.jdbc.core.RowMapper;
import lombok.NonNull;
import ru.yandex.practicum.filmorate.model.Event;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EventsMapper implements RowMapper<Event> {
    @Override
    public Event mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
        return Event.builder()
                .id(rs.getLong("event_id"))
                .timestamp(rs.getLong("timestamp"))
                .userId(rs.getLong("user_id"))
                .eventType(rs.getString("event_type"))
                .operation(rs.getString("operation"))
                .entityId(rs.getLong("entity_id"))
                .build();
    }
}
