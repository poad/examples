package com.github.poad.examples.java.webapps;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.argument.Argument;
import org.jdbi.v3.core.argument.ArgumentFactory;
import org.jdbi.v3.core.config.ConfigRegistry;
import org.jdbi.v3.core.mapper.ColumnMapper;
import org.jdbi.v3.core.statement.StatementContext;
import org.jdbi.v3.jpa.JpaPlugin;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Optional;
import java.util.TimeZone;

/**
 * Configuration for JDBI and related persistence.
 */
@Configuration
public class PersistenceConfiguration {
    final DataSource dataSource;

    @Autowired
    public PersistenceConfiguration(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public Jdbi jdbiBean() {
        Jdbi jdbi = Jdbi.create(dataSource);
        jdbi.registerArgument(new DateTimeLocalDateArgumentFactory());
        jdbi.registerArgument(new DateTimeLocalDateArgumentFactory());
        jdbi.registerColumnMapper(new JodaDateTimeMapper());
        jdbi.installPlugin(new JpaPlugin());

        return jdbi;
    }

    private static Calendar getUtcCalendar() {
        return Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    }

    /**
     * JDBI argument factory for converting joda DateTime or LocalDate to sql timestamp
     */
    public static class DateTimeLocalDateArgumentFactory implements ArgumentFactory {

        @Override
        public Optional<Argument> build(Type type, Object value, ConfigRegistry config) {
            System.err.println(type + " : " + value);
            if (value == null) {
                return Optional.empty();
            }
            if (DateTime.class.isAssignableFrom(value.getClass())) {
                return Optional.of((position, statement, ctx) -> {
                    long millis = ((DateTime) value).withZone(DateTimeZone.UTC).getMillis();
                    statement.setTimestamp(position, new Timestamp(millis), getUtcCalendar());
                });
            } else if (LocalDate.class.isAssignableFrom(value.getClass())) {
                return Optional.of((position, statement, ctx) -> statement.setString(position, ((LocalDate) value).toString("yyyy-MM-dd")));
            }else if (String.class.isAssignableFrom(value.getClass())) {
                return Optional.of((position, statement, ctx) -> statement.setString(position, ((String) value)));
            } else {
                return Optional.empty();
            }
        }
    }

    /**
     * A {@link ColumnMapper} to map Joda {@link DateTime} objects.
     */
    public static class JodaDateTimeMapper implements ColumnMapper<DateTime> {

        @Override
        public DateTime map(ResultSet rs, int columnNumber, StatementContext ctx) throws SQLException {
            final Timestamp timestamp = rs.getTimestamp(columnNumber, getUtcCalendar());
            if (timestamp == null) {
                return null;
            }
            return new DateTime(timestamp.getTime());
        }

        @Override
        public DateTime map(ResultSet rs, String columnLabel, StatementContext ctx) throws SQLException {
            final Timestamp timestamp = rs.getTimestamp(columnLabel, getUtcCalendar());
            if (timestamp == null) {
                return null;
            }
            return new DateTime(timestamp.getTime());
        }
    }
}
