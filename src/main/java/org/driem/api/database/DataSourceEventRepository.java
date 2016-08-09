package org.driem.api.database;

import org.driem.api.Event;
import org.driem.api.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@Profile("database")
public class DataSourceEventRepository implements EventRepository {
    private static final Logger logger = LoggerFactory.getLogger(DataSourceEventRepository.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DataSourceEventRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Event> loadAllEvents() {
        List<Event> events = jdbcTemplate.query("select * from events", new RowMapper<Event>() {
            @Override
            public Event mapRow(ResultSet resultSet, int i) throws SQLException {
                String foundName = resultSet.getString("name");
                boolean foundFinished = resultSet.getBoolean("finished");
                int id = resultSet.getInt("id"); // TODO add this to the event object
                return new Event(foundName, foundFinished);
            }
        });
        logger.debug("Found {} events", events.size());
        return events;
    }
}
