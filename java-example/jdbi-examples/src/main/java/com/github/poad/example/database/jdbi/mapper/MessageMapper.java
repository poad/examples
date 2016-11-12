package com.github.poad.example.database.jdbi.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import com.github.poad.example.database.jdbi.entity.Message;

public class MessageMapper implements ResultSetMapper<Message> {

	@Override
	public Message map(int index, ResultSet r, StatementContext ctx) throws SQLException {
		return new Message(r.getLong("id"), r.getString("message"));
	}

}
