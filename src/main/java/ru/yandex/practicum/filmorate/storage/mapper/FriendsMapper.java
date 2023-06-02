package ru.yandex.practicum.filmorate.storage.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.Friends;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FriendsMapper implements RowMapper<Friends> {
    @Override
    public Friends mapRow(ResultSet rs, int rowNum) throws SQLException {
        Friends friends = new Friends();
        friends.setUserId(rs.getLong("user_id"));
        friends.setFriendId(rs.getLong("friend_id"));
        friends.setMutual(rs.getBoolean("is_mutual"));
        return friends;
    }
}
