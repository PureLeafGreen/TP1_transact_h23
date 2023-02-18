package Utils;

import Models.Client;
import org.h2.jdbc.JdbcSQLSyntaxErrorException;

import java.sql.*;

public class JDBCclass {
    // JDBC driver name and database URL
    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:~/TP1_h23;AUTO_SERVER=true;";

    private static String QUERY = "SELECT id, prenom FROM Client";

    //  Database credentials
    private static final String USER = "sa";
    private static final String PASS = "";

    private static Connection conn = null;
    private static Statement stmt = null;
    private static String sqlPs = "SELECT id, prenom FROM Client" +
            " WHERE id = ?;";
    private static String insertClient = "INSERT INTO Client(id, prenom) values(?,?);";

    static {
        // STEP 1: Register JDBC driver
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JDBCclass.createDatabase();
    }

    public static void createDatabase() {
        try {
            //STEP 2: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //STEP 3: Execute a query
            System.out.println("Creating table in given database...");
            stmt = conn.createStatement();
            String sql =  "CREATE TABLE IF NOT EXISTS CLIENT " +
                    "(id INTEGER auto_increment, " +
                    " prenom VARCHAR(255), " +
                    " PRIMARY KEY ( id ))";
            stmt.executeUpdate(sql);
            System.out.println("Created table in given database...");

            // STEP 4: Clean-up environment
            stmt.close();
            conn.close();
        } catch(JdbcSQLSyntaxErrorException e) {
            // Database already exists
            handleException(e);
        } catch(SQLException se) {
            //Handle errors for JDBC
            handleException(se);
        } catch(Exception e) {
            //Handle errors for Class.forName
            handleException(e);
        } finally {
            //finally block used to close resources
            try{
                if(stmt!=null) stmt.close();
            } catch(SQLException se2) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se){
                handleException(se);
            } //end finally try
        } //end try
        System.out.println("Goodbye!");
    }

    public static void dropTable() {
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
        ) {
            String sql = "DROP TABLE CLIENT";
            stmt.executeUpdate(sql);
            System.out.println("Table deleted in given database...");
        } catch (SQLException e) {
            handleException(e);
        }
    }

    public static void selectRecords() {

        // Open a connection
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY);
        ) {
            while(rs.next()){
                //Display values
                printResult(rs);
            }
            System.out.println();
        } catch (SQLException e) {
            handleException(e);
        }
    }

    public static void deleteRecord(int id) {
        // Open a connection
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
        ) {
            String sql = "DELETE FROM CLIENT " +
                    "WHERE id = " + id + "";
            stmt.executeUpdate(sql);
            ResultSet rs = stmt.executeQuery(QUERY);
            System.out.println("Deleted record");
            while(rs.next()){
                //Display values
                printResult(rs);
            }
            System.out.println();
            rs.close();
        } catch (SQLException e) {
            handleException(e);
        }
    }

    public static void prepareStatement() {
        // Open a connection
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement ps = conn.prepareStatement(sqlPs);) {

            // Select all records having ID equal or greater than 101
            System.out.println();
            System.out.println("Fetching records with prepared statement...");

            ps.setInt(1, 101);

            // NOTEZ le try à l'intérieur du try
            try (ResultSet rs = ps.executeQuery();) {
                System.out.println("records with id > 101");
                while (rs.next()) {
                    //Display values
                    printResult(rs);
                }
            }
        } catch (SQLException e) {
            handleException(e);
        }
    }

    private static void printResult(ResultSet rs) throws SQLException {
        System.out.print("ID: " + rs.getInt("id"));
        System.out.print(", Prenom: " + rs.getString("prenom"));
    }

    private static void handleException(Exception exception) {
        if (exception instanceof SQLException) {
            SQLException sqlException = (SQLException) exception;
            System.out.println("Error Code: " + sqlException.getErrorCode());
            System.out.println("SQL State: " + sqlException.getSQLState());
        }
        System.out.println("SQLException message: " + exception.getMessage());
        System.out.println("Stacktrace: ");
        exception.printStackTrace();
    }

    public static void save(Client client) {
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement ps = conn.prepareStatement(insertClient)) {

            // Select all records having ID equal or greater than 101
            System.out.println();
            System.out.println("inserting records with prepared statement...");


            ps.setInt(1, client.getId());
            ps.setString(2, client.getPrenom());

            ps.executeUpdate();
        } catch (SQLException e) {
            handleException(e);
        }
    }

    public static Client getClient(int id) {
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement ps = conn.prepareStatement(sqlPs);) {

            // Select all records having ID equal or greater than 101
            System.out.println();
            System.out.println("retrieving statement...");

            ps.setInt(1, id);

            // NOTEZ le try à l'intérieur du try
            try (ResultSet rs = ps.executeQuery();) {
                if (rs.next()) {
                    return new Client(rs.getInt(1),
                    rs.getString(2));
                }
            }
        } catch (SQLException e) {
            handleException(e);
        }
        return null;
    }
}
