package com.hyd.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class DateTypeHandler extends BaseTypeHandler<Date> {
    /**
     * java type to db type
     * @param preparedStatement
     * @param i
     * @param date
     * @param jdbcType
     * @throws SQLException
     */
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Date date, JdbcType jdbcType) throws SQLException {
        long time = date.getTime();
        preparedStatement.setLong(i,time);
    }

    /**
     * db type to java type
     * @param resultSet
     * @param s
     * @return
     * @throws SQLException
     */
    @Override
    public Date getNullableResult(ResultSet resultSet, String s) throws SQLException {
        long time = resultSet.getLong(s);
        return new Date(time);
    }

    /**
     * db type to java type
     * @param resultSet
     * @param i
     * @return
     * @throws SQLException
     */
    @Override
    public Date getNullableResult(ResultSet resultSet, int i) throws SQLException {
        long time = resultSet.getLong(i);
        return new Date(time);
    }

    /**
     * db type to java type
     * @param callableStatement
     * @param i
     * @return
     * @throws SQLException
     */
    @Override
    public Date getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        long time = callableStatement.getLong(i);
        return new Date(time);
    }
}
