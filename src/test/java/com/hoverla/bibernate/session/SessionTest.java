package com.hoverla.bibernate.session;

import com.hoverla.bibernate.dbOperations.impl.SaverImpl;
import com.hoverla.bibernate.testutil.entity.User;
import com.hoverla.bibernate.util.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.SQLException;

import static com.hoverla.bibernate.testutil.factory.UserFactory.getTestUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class SessionTest {

//    private SaverImpl saverImpl;
//    private DataSource dataSource;
//    private Session session;
//    private Connection connection;
//
//    @BeforeEach
//    public void setup() throws SQLException {
//        connection = Mockito.mock(Connection.class);
//        saverImpl = Mockito.mock(SaverImpl.class);
//        dataSource = Mockito.mock(DataSource.class);
//        session = new Session(dataSource);
//    }
//
//    @Test
//    public void saveSavesEntityToDB(){
//        User newDefaultPerson = getTestUser();
//        newDefaultPerson.setId(null);
//        when(saverImpl.save(newDefaultPerson)).thenReturn(newDefaultPerson);
//
//        User savedPerson = session.save(newDefaultPerson);
//
//        assertThat(savedPerson).isEqualTo(newDefaultPerson);
//
//    }
}
