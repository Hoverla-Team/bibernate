package com.hoverla.bibernate.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * DataSource is a ConnectionPool witch use HikariCP as a connection pool vendor.
 * HikariCP comes with sane defaults that perform well in most deployments without additional tweaking.
 * Every property is optional, except for the "essentials" marked below.
 * 
 * Essentials: jdbcUrl, username, password
 * Frequently used: autoCommit, connectionTimeout, idleTimeout, keepaliveTime, maxLifetime, connectionTestQuery, minimumIdle, maximumPoolSize.
 * 
 * For more information visit {@link https://github.com/brettwooldridge/HikariCP#popular-datasource-class-names}
 */
public class DataSource {
    private static final String CONFIG_FILE = "src/main/resources/datasource.properties";
    private static final HikariConfig configuration = new HikariConfig(CONFIG_FILE);
    private static final HikariDataSource ds = new HikariDataSource(configuration);

    /**
     * Get a new connection from poll
     *
     * @return Connection
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
