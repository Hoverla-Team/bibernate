package com.hoverla.bibernate.util;

import java.sql.PreparedStatement;
import java.util.Map;

/**
 * {@link StatementHandler} is interface responsible for setting parameters represented as {@link Map}
 * into {@link PreparedStatement}.
 * @author Maryna Melnychuk
 * @since 01.01.2022
 **/
public interface StatementHandler {

    /**
     * Set specified parameters into given statement {@link PreparedStatement}.
     *
     * @param parameters - parameters that need to be set
     * @param statement - prepared statement to set parameters
     **/
    void setStatementParameters(Map<String, Object> parameters, PreparedStatement statement);

}
