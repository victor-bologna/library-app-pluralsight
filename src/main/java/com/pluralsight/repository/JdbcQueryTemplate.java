package com.pluralsight.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public abstract class JdbcQueryTemplate<T> extends AbstractDao {
    public List<T> queryForList(String sql) {
        List<T> itemList = new ArrayList<>();
        try(Connection connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)) {

            while(resultSet.next()) {
                itemList.add(mapItem(resultSet));
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return itemList;
    }

    public abstract T mapItem (ResultSet resultSet) throws SQLException;
}
