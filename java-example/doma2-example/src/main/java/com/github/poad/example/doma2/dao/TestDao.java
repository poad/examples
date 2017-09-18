package com.github.poad.example.doma2.dao;

import com.github.poad.example.doma2.DatabaseConfig;
import com.github.poad.example.doma2.entity.Test;
import org.seasar.doma.Dao;
import org.seasar.doma.Select;

import java.util.List;

@Dao(config = DatabaseConfig.class)
public interface TestDao {

    @Select
    List<Test> all();

    @Select
    Test get(String id);
}
