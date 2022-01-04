package com.customorm.session;

import com.customorm.entity.Person;
import com.hoverla.bibernate.session.JdbcDao;
import com.hoverla.bibernate.session.Session;
import com.hoverla.bibernate.util.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;

import static com.customorm.factory.PersonFactory.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class SessionTest {


    private JdbcDao jdbcDao;
    private DataSource dataSource;
    private Session session;

    @BeforeEach
    public void setup() throws SQLException {
        jdbcDao = Mockito.mock(JdbcDao.class);
        dataSource = Mockito.mock(DataSource.class);
        session = new Session(dataSource);
    }
    public SessionTest() {
    }

    @Test
    public void saveSavesEntityToDB(){
        Person newDefaultPerson = createNewDefaultPerson();
        newDefaultPerson.setId(null);
        when(jdbcDao.save(newDefaultPerson)).thenReturn(newDefaultPerson);

        Person savedPerson = session.save(newDefaultPerson);

        assertThat(savedPerson).isEqualTo(newDefaultPerson);

    }
}
