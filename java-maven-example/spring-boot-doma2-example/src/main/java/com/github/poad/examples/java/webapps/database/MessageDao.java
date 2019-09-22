package com.github.poad.examples.java.webapps.database;

import org.seasar.doma.*;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.experimental.Sql;
import org.seasar.doma.jdbc.Result;
import org.seasar.doma.jdbc.SelectOptions;

import java.util.List;

@Dao
@ConfigAutowireable
public interface MessageDao {
    @Sql("select UUID()")
    @Select(ensureResult = true)
    String uuid();

    @Select
    List<MessageEntity> selectAll(SelectOptions options);

    @Select(ensureResult = true)
    MessageEntity selectById(String id);

    @Insert
    Result<MessageEntity> insert(MessageEntity message);

    @Update
    Result<MessageEntity> update(MessageEntity message);

    @Delete
    Result<MessageEntity> delete(MessageEntity message);
}