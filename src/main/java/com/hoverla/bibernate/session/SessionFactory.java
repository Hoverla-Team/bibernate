package com.hoverla.bibernate.session;

import com.hoverla.bibernate.util.DataSource;
import lombok.RequiredArgsConstructor;

import java.sql.SQLException;

@RequiredArgsConstructor
public class SessionFactory {
    private final DataSource dataSource;

    public Session createSession() throws SQLException {
        return new Session(dataSource);
    }
}
