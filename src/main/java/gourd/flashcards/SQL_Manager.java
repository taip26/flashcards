package gourd.flashcards;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class SQL_Manager {

    /**
     * Initializes the database
     *
     * @param url String path to database file
     */
    public static void initializeDatabase(String url) {
        // database setup
        try (var conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                System.out.println("A new database has been created.");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Initializes the sets table
     *
     * @param url String path to database file
     */
    public static void initializeSetsTable(String url) {
        // create sets table if it does not already exist
        var setsTableReq = "CREATE TABLE IF NOT EXISTS sets ("
                + "	id INTEGER PRIMARY KEY,"
                + "	name text NOT NULL"
                + ");";
        try (var conn = DriverManager.getConnection(url);
             var stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(setsTableReq);
            System.out.println("sets table has been created");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Initializes the cards table
     *
     * @param url String path to database file
     */
    public static void initializeCardsTable(String url) {
        // create cards table if it does not already exist
        var cardsTableReq = "CREATE TABLE IF NOT EXISTS cards ("
                + "	id INTEGER PRIMARY KEY,"
                + "	set_id INTEGER,"
                + "	front text,"
                + "	back text,"
                + "	FOREIGN KEY(set_id) REFERENCES sets(id)"
                + ");";
        try (var conn = DriverManager.getConnection(url);
             var stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(cardsTableReq);
            System.out.println("cards table has been created");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Fetches sets from the database and returns them as an ArrayList object
     *
     * @param url Database URL
     * @return List of sets
     */
    public static ArrayList<Set> getSetsFromDB(String url) {
        ArrayList<Set> sets = new ArrayList<>();
        System.out.println("Querying sets from database: ");
        var sql = "SELECT * FROM sets";

        try (var conn = DriverManager.getConnection(url);
             var stmt = conn.createStatement();
             var rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.printf("%-5s%-25s\n",
                        rs.getInt("id"),
                        rs.getString("name")
                );
                sets.add(new Set(rs.getInt("id"), rs.getString("name")));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return sets;
    }

    public static ArrayList<Card> getCardsFromSetID(String url, int setID) {
        ArrayList<Card> cards = new ArrayList<>();
        System.out.println("Querying cards from database with set id " + setID + ": ");

        var sql = "SELECT * FROM cards WHERE set_id = ?";

        try (var conn = DriverManager.getConnection(url);
             var pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, setID);
                var rs = pstmt.executeQuery();
                while (rs.next()) {
                    System.out.printf("%-5s%-25s%s\n",
                        rs.getInt("id"),
                        rs.getString("front"),
                        rs.getString("back")
                    );
                    // create card from database output
                    Card card = new Card(rs.getInt("id"),
                                         setID,
                                         rs.getString("front"),
                                         rs.getString("back"));
                    cards.add(card);
                }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return cards;
    }


    /**
     * Inserts a new set into the database
     *
     * @param url Database URL
     * @param setName Name of set
     */
    public static void insertSet(String url, String setName) {
        var sql = "INSERT INTO sets(name) VALUES(?)";

        try (var conn = DriverManager.getConnection(url);
             var pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, setName);
                pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Updates the name the set given the unique set ID
     *
     * @param url Database URL
     * @param id ID of set
     * @param newName New name for set
     */
    public static void updateSetName(String url, int id, String newName) {
        var sql = "UPDATE sets SET name = ? "
                + "WHERE id = ?";

        try (var conn = DriverManager.getConnection(url);
             var pstmt = conn.prepareStatement(sql)) {
            // set the parameters
            pstmt.setString(1, newName);
            pstmt.setInt(2, id);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Deletes the set with given ID
     *
     * @param url Database URL
     * @param id set ID
     */
    public static void deleteSet(String url, int id) {
        var sql = "DELETE FROM sets WHERE id = ?";

        try (var conn = DriverManager.getConnection(url);
             var pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Inserts a new card into set with given front and back inputs
     *
     * @param url Database URL
     * @param front Front of card; may be null
     * @param back Back of card; may be null
     * @param set_id Set ID
     */
    public static void insertCard(String url, int set_id, String front, String back) {
        var sql = "INSERT INTO cards(set_id, front, back) VALUES(?, ?, ?)";

        try (var conn = DriverManager.getConnection(url);
             var pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, set_id);

            // Make content empty if null is passed
            pstmt.setString(2, front == null ? "" : front);
            pstmt.setString(3, back == null ? "" : back);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Updates the name the set given the unique set ID
     *
     * @param url Database URL
     * @param id Card ID
     * @param newFront New front of card
     * @param newBack New back of card
     */
    public static void updateCard(String url, int id, String newFront, String newBack) {
        var sql = "UPDATE cards SET front = ?, back = ? "
                + "WHERE id = ?";

        try (var conn = DriverManager.getConnection(url);
             var pstmt = conn.prepareStatement(sql)) {
            // set the parameters
            pstmt.setString(1, newFront);
            pstmt.setString(2, newBack);
            pstmt.setInt(3, id);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Deletes the card with given ID
     *
     * @param url Database URL
     * @param id card ID
     */
    public static void deleteCard(String url, int id) {
        var sql = "DELETE FROM cards WHERE id = ?";

        try (var conn = DriverManager.getConnection(url);
             var pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
