package by.yarzl.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DataProvider {
    private static volatile DataProvider provider;
    // Это поле используется для генерации ID, Вам оно не понадобится, поскольку ID генерирует база данных

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DATABASE_URL = "jdbc:mysql://localhost:3306";
    static final String USER = "root";
    static final String PASSWORD = "root";

    // Строки кода с 13 по 27 отвечают за создание DataProvider только в одном экземпляре, чтобы избежать потери данных
    // при работе через разные потоки
    // При реализации работы с JDBC слоем эта функциональность Вам не понадобится, Ваши данные хранятся в базе а не в
    // оперативной памяти


    //провайдер делает работу с данными
    private DataProvider() {
    }

    public static DataProvider getProvider() {
        DataProvider localProvider = provider;
        if (localProvider == null) {
            synchronized (DataProvider.class) {
                localProvider = provider;
                if (localProvider == null) {
                    provider = localProvider = new DataProvider();
                }
            }
        }
        return localProvider;
    }

    public List<DemoEntity> getAllEntities() throws SQLException, ClassNotFoundException {

        List<DemoEntity> entities = new ArrayList<>();
        Class.forName(JDBC_DRIVER);
        Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT * FROM `schema`.users;");

        while (resultSet.next()) {
            DemoEntity entity = new DemoEntity();

            entity.setId(resultSet.getInt("id"));
            entity.setName(resultSet.getString("name"));
            entity.setSurname(resultSet.getString("surname"));

            entities.add(entity);
        }

        try {
            return entities;
        } finally {
            statement.close();
            connection.close();
        }

    }

    public void addEntity(DemoEntity entity) throws SQLException, ClassNotFoundException {

        Class.forName(JDBC_DRIVER);
        Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);


        String sql = ("INSERT INTO `schema`.users VALUES (0,'"
                + entity.getName() + "','" + entity.getSurname() + "');");
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.execute(sql);

        statement.close();
        connection.close();

    }

    public void deleteEntityById(int id) throws Exception {

        Class.forName(JDBC_DRIVER);
        Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
        Statement statement = connection.createStatement();

        statement.execute("DELETE FROM `schema`.users WHERE id = " + id + "; ");

        statement.close();
        connection.close();

    }

    public boolean isNoteExistsById(int id) throws Exception {

        Class.forName(JDBC_DRIVER);
        Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
        Statement statement = connection.createStatement();

        if(statement.execute("SELECT EXISTS(SELECT id FROM `schema`.users WHERE id = %d);", id) == true){
            statement.close();
            connection.close();
            return true;
        } else {
            statement.close();
            connection.close();
            return false;
        }
    }
}
