package Snake;

import java.sql.*;

import static java.lang.Class.forName;

public class Database {
    int id=0;
    Connection conn;
    public Database(){
        try {
            forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:./score.db";
            conn = DriverManager.getConnection(url);
            DatabaseMetaData meta = conn.getMetaData();
            System.out.println("Connection to SQLite has been established.");
            String query = "CREATE TABLE IF NOT EXISTS scores (\n"
                    + "	id integer PRIMARY KEY,\n"
                    + "	score integer NOT NULL\n"
                    + ");";
            Statement stmt = conn.createStatement();
            stmt.execute("DROP TABLE IF EXISTS scores;");
            stmt.execute(query);
            System.out.println("Table has been crearted.");

            addToDB(0);
            addToDB(2);
            addToDB(5);
            addToDB(10);
            addToDB(20);
            addToDB(50);
            addToDB(100);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    int biggerThan(int score) throws Exception {
        String query = "SELECT * FROM scores where score > " + score + ";";

        Statement stmt = conn.createStatement();
        ResultSet set = stmt.executeQuery(query);
        int size=0;
        while(set.next()){
            size++;
        }
        return size;
    }
    void addToDB(int score) throws Exception {
        String query = "INSERT INTO scores(id, score) VALUES(" + id + "," + score + ");";

        PreparedStatement statement = conn.prepareStatement(query);
        statement.executeUpdate();
        System.out.println("Database updated");
        id++;
    }

    String getData(int id) throws Exception {
        String query = "SELECT address FROM scores where id = " + id + ";";
        Statement stmt = conn.createStatement();
        ResultSet set = stmt.executeQuery(query);
        return set.getString("address");
    }

    public static void main(String[] args) {
        new Database();
    }
}