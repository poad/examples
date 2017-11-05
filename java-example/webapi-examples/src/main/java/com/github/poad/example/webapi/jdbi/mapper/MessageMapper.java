package com.github.poad.example.webapi.jdbi.mapper;

import com.github.poad.example.webapi.jdbi.entity.Message;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MessageMapper implements ResultSetMapper<Message> {

	@Override
	public Message map(int index, ResultSet r, StatementContext ctx) throws SQLException {
		return new Message(r.getLong("id"), r.getString("message"));
	}

}
