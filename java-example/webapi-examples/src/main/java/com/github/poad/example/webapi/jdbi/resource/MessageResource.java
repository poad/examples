package com.github.poad.example.webapi.jdbi.resource;

import com.github.poad.example.webapi.jdbi.entity.Message;
import com.github.poad.example.webapi.jdbi.mapper.MessageMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

/**
 * {@link MessageResource} インタフェースは、messageテーブルへアクセスするためのJDBI用インタフェースです。<br>
 * このインタフェースの実装はありません。SQLの発行やマッピングクラスの呼び出しは、JDBIにより行われ、ユーザー(開発者)には隠蔽されます。
 */
@RegisterMapper(MessageMapper.class)
public interface MessageResource extends AutoCloseable {

	@SqlUpdate("insert into message (message) values (:message)")
	void create(@Bind("message") String message);
	
	@SqlQuery("select * from message")
	List<Message> list();
	
	@SqlQuery("select * from message where id = :id")
	Message get(@Bind("id") long id);
	
	@SqlUpdate("update message set (message = :message) where id = :id")
	void update(@Bind("id") long id, @Bind("message") String message);
	
	@SqlUpdate("delete from message where id = :id")
	void delete(@Bind("id") long id);
	
	/**
	 * DBのコネクションを閉じます
	 */
	void close();
}
