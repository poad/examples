/*
 * MIT License
 *
 * Copyright (c) 2017 Kenji Saito
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.github.poad.example.webapi.resource.impl;

import com.github.poad.example.webapi.jdbi.resource.MessageResource;
import com.github.poad.example.webapi.resource.Message;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.util.List;

/**
 * .
 */
public class MessageImpl implements Message {
    @Override
    public String hello() {
        return "hello";
    }

    @Override
    public com.github.poad.example.webapi.jdbi.entity.Message get(long id) {
        HikariDataSource ds = dataSource();

        Jdbi jdbi = Jdbi.create(ds);
        jdbi.installPlugin(new SqlObjectPlugin());
        return jdbi.withExtension(MessageResource.class, resource ->
            resource.get(id)
        );
    }

    @Override
    public List<com.github.poad.example.webapi.jdbi.entity.Message> get() {
        HikariDataSource ds = dataSource();

        Jdbi jdbi = Jdbi.create(ds);
        jdbi.installPlugin(new SqlObjectPlugin());
        return jdbi.withExtension(MessageResource.class, MessageResource::list);
    }

    @Override
    public void post(String message) {
        HikariDataSource ds = dataSource();

        Jdbi jdbi = Jdbi.create(ds);
        jdbi.installPlugin(new SqlObjectPlugin());
        jdbi.useExtension(MessageResource.class, resource ->
            resource.create(message)
        );
    }


    private static HikariDataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/test");
        config.setDriverClassName("com.mysql.jdbc.Driver");
        config.addDataSourceProperty("user", "root");

        config.addDataSourceProperty("dataSourceClassName", "com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        config.addDataSourceProperty("autoCommit", "false");
        config.addDataSourceProperty("useServerPrepStmts", "true");
        config.addDataSourceProperty("cachePrepStmts", "true");

        return new HikariDataSource(config);
    }
}
