package com.github.poad.example.database.jdbi.resource;

import com.github.poad.example.database.jdbi.entity.Message;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

/**
 * {@link MessageResource} インタフェースは、messageテーブルへアクセスするためのJDBI用インタフェースです。<br>
 * このインタフェースの実装はありません。SQLの発行やマッピングクラスの呼び出しは、JDBIにより行われ、ユーザー(開発者)には隠蔽されます。
 */
public interface MessageResource {

	@SqlUpdate("insert into message (message) values (:message)")
	void create(@Bind("message") String message);
	
	@SqlQuery("select * from message")
    @RegisterBeanMapper(Message.class)
	List<Message> list();
	
	@SqlQuery("select * from message where id = :id")
    @RegisterBeanMapper(Message.class)
	Message get(@Bind("id") long id);
	
	@SqlUpdate("update message set (message = :message) where id = :id")
	void update(@Bind("id") long id, @Bind("message") String message);
	
	@SqlUpdate("delete from message where id = :id")
	void delete(@Bind("id") long id);
}
