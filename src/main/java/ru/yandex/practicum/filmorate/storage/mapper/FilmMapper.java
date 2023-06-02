package ru.yandex.practicum.filmorate.storage.mapper;

import lombok.NonNull;
import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FilmMapper implements RowMapper<Film> {
    @Override
    public Film mapRow(@NonNull ResultSet rS, int rowNum) throws SQLException {
        Mpa mpa = new Mpa();
        mpa.setId(rS.getInt("rating_id"));

        Film film = new Film();
        film.setId(rS.getLong("film_id"));
        film.setName(rS.getString("name"));
        film.setDescription(rS.getString("description"));
        film.setReleaseDate(rS.getDate("release_date").toLocalDate());
        film.setDuration(rS.getInt("duration"));
        film.setMpa(mpa);
        return film;
    }
}
