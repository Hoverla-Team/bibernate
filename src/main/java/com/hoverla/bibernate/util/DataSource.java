package com.hoverla.bibernate.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * DataSource is a ConnectionPool witch use HikariCP as a connection pool vendor.
 * HikariCP comes with sane defaults that perform well in most deployments without additional tweaking.
 * Every property is optional, except for the "essentials" marked below.
 * <p>
 * Essentials: jdbcUrl, username, password
 * Frequently used: autoCommit, connectionTimeout, idleTimeout, keepaliveTime, maxLifetime, connectionTestQuery, minimumIdle, maximumPoolSize.
 * <p>
 * For more information visit {@link <a href="https://github.com/brettwooldridge/HikariCP#popular-datasource-class-names">documentation</a>}
 */
public class DataSource {
    private static final String CONFIG_FILE = "src/main/resources/application.properties";
    private static final HikariConfig configuration = new HikariConfig(CONFIG_FILE);
    private static final HikariDataSource ds = new HikariDataSource(configuration);

    /**
     * Get a new connection from poll
     *
     * @return Connection
     * @throws SQLException if connection is not successes
     */
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
