package ru.yandex.practicum.filmorate.storage.dao.events;

import ru.yandex.practicum.filmorate.model.Event;

import java.util.List;

public interface EventsStorage {
    List<Event> getUserEvents(long userId);

    void addEvent(long timestamp, long userId, String eventType, String operation, long entityId);
}
