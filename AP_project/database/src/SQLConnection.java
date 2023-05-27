//import java.io.File;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class SQLConnection {

//    private static final File DB_FILE = new File("database.db");
    private static SQLConnection instance = null;

    public static SQLConnection getInstance() {
        if (instance == null) {
            instance = new SQLConnection();
        }

        return instance;
    }

    public synchronized static void closeConnection() {
        if (instance != null) {
            instance.close();
        }
    }

    // CONNECTION
    Connection connection = null;

    private SQLConnection() {
    }

    public void connect() throws SQLException {
        if (connection == null) {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "Ma@12345");//password of your database
        }
        if (connection != null) {
            createTables();
        }
    }

    public void close() {
        if (connection != null) {
            SafeRunning.safe(connection::close);
            instance = null;
        }
    }

    public Connection getConnection() {
        return connection;
    }

    // TABLES
    final TableOfUsers users = new TableOfUsers();

    private void createTables() {
        users.createTable();
    }

    public static TableOfUsers getUsers() {
        return getInstance().users;
    }

    final ArrayList<UserInfoTable> specialUserTable = new ArrayList<>();
    public UserInfoTable createUserTables(String username) {
        UserInfoTable userTable = new UserInfoTable();
        specialUserTable.add(userTable);
        userTable.createTable(username);
        return userTable;
    }
    final FollowTable followTable = new FollowTable();
    public FollowTable createFollowTable() {
        followTable.createTable();
        return followTable;
    }
    final BlockTable blockTable = new BlockTable();
    public BlockTable createBlockTable() {
        blockTable.createTable();
        return blockTable;
    }
}