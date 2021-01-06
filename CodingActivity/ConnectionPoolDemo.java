import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class ConnectionPool {

    private final static int MAX_CONNECTIONS = 8;
    private static ConnectionPool instance = null;  // lazy loading
    private static Connection[] connections = new Connection[MAX_CONNECTIONS];
    private static String dbUrl = "jdbc:mysql://${MYSQL_HOST:localhost}:3306/reactive";
    private static int counter;

    private ConnectionPool() { }

    public static ConnectionPool getInstance() {
        if(instance == null) {
            synchronized(ConnectionPool.class) {
                if(instance == null) {
                    instance = new ConnectionPool();
                    initializeConnections();
                    counter = 0;
                }
            }
        }

        return instance;
    }

    private static void initializeConnections() {
        for(int i = 0; i < MAX_CONNECTIONS; i++) {
            try {
                connections[i] = DriverManager.getConnection(dbUrl,"root","Sravya@123");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static Connection getConnection() {
        counter++;
        if(counter == Integer.MAX_VALUE)
            counter = 0;

        return connections[counter%MAX_CONNECTIONS];
    }

}

public class ConnectionPoolDemo {

    public static void main(String[] args) {
        ConnectionPool pool = ConnectionPool.getInstance();

        Connection[] connections = new Connection[17];

        for(int i = 0; i < connections.length; i++)
            connections[i] = pool.getConnection();

        int preIndex = connections[0].getClass().getCanonicalName().length();
        for(int i = 0; i < connections.length; i++)
            System.out.printf("Connection %2d : %s\n", i+1, connections[i].toString().substring(preIndex));

        for(int i = 0; i < connections.length; i++)
            try {
                connections[i].close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

}