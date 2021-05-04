package api.services;

import api.services.models.Waiter;

import java.sql.*;
import java.util.*;


public class CafeDatabase {
    private Connection conn;
    private final String host = "jdbc:postgresql://localhost/cafe";
    private final String user = "coder";
    private final String pw = "coder123";
    private static CafeDatabase cafeDatabase;

    public static CafeDatabase getInstance() {
        if (cafeDatabase == null) {
            cafeDatabase = new CafeDatabase();
        }
        return cafeDatabase;
    }


    //    ##########| CREATE |##########
    private final String ADD_NEW_WAITER_SQL = "INSERT INTO waiters (waiterid, waiter) VALUES (?, ?)";
    private PreparedStatement psAddNewWaiter;

    //    ##########| READ |##########
    private final String GET_ALL_WAITERS_SQL = "Select * from waiters";
    private PreparedStatement psGetAllWaiters;

    private final String DOES_WAITER_NAME_EXIST_SQL = "select exists(select 1 from waiters where waiter=?)";
    private PreparedStatement psDoesWaiterNameExist;

    private final String GET_MAX_WAITER_ID_VAL_SQL = "SELECT max(waiterid) FROM waiters";
    private PreparedStatement psGetMaxWaiterIDVal;


    //    ##########| UPDATE |##########


    //    ##########| DELETE |##########
    private final String DELETE_WAITER_BY_ID_SQL = "DELETE FROM waiters WHERE waiterid = ?";
    private PreparedStatement psDeleteWaiterByID;


    private CafeDatabase() {
        try {
            conn = DriverManager.getConnection(host, user, pw);

            psAddNewWaiter = conn.prepareStatement(ADD_NEW_WAITER_SQL);

            psGetAllWaiters = conn.prepareStatement(GET_ALL_WAITERS_SQL);
            psDoesWaiterNameExist = conn.prepareStatement(DOES_WAITER_NAME_EXIST_SQL);
            psGetMaxWaiterIDVal = conn.prepareStatement(GET_MAX_WAITER_ID_VAL_SQL);

            psDeleteWaiterByID = conn.prepareStatement(DELETE_WAITER_BY_ID_SQL);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<Waiter> getWaiters() throws SQLException {
        List<Waiter> waiters = new ArrayList<>();

        ResultSet rs = psGetAllWaiters.executeQuery();
        while (rs.next()) {
            Waiter waiter = new Waiter(rs.getInt("waiterid"), rs.getString("waiter"));
            waiters.add(waiter);
        }
        return waiters;
    }

    public Boolean waiterExists(String name) throws SQLException {
        psDoesWaiterNameExist.setString(1, name);
        ResultSet resultSet = psDoesWaiterNameExist.executeQuery();
        resultSet.next();
        return resultSet.getBoolean("exists");
    }

    public Map<String, String> addWaiter(String name) throws SQLException, Exception{
        if(name=="" || name.isBlank()){
            throw new Exception("Waiter name is invalid");
        }
        psAddNewWaiter.setInt(1, maxIdVal() + 1);
        psAddNewWaiter.setString(2, name.toLowerCase());
        Boolean res = !psAddNewWaiter.execute();
        if(res == false){throw new Exception("Waiter name is invalid");}
        HashMap<String, String> result = new HashMap<>();
        result.put("message", name + " has been added.");
        result.put("result", res.toString());
        return result;

    }

    private Integer maxIdVal(){
        try {
            ResultSet rs = psGetMaxWaiterIDVal.executeQuery();
            rs.next();
            return rs.getInt("max");
        }
        catch (SQLException e){
            e.printStackTrace();
            return -1;
        }
    }

    public Map<String, String> deleteWaiter(Waiter waiter) throws SQLException, Exception{
        psDeleteWaiterByID.setLong(1, waiter.getWaiterID());
        Boolean res = !psDeleteWaiterByID.execute();
        if(res == false){throw new Exception("Waiter name is invalid");}
        HashMap<String, String> result = new HashMap<>();
        result.put("message", waiter.getWaiterName() + " has been deleted.");
        result.put("result", res.toString());
        return result;
    }

//    public boolean exists(String name) {
//        boolean result = false;
//        try {
//            psGetIdOfUser.setString(1, name);
//            ResultSet rs = psGetIdOfUser.executeQuery();
//            result = rs.next();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            return result;
//        }
//    }
//
//    public void addUser(String name) {
//        try {
//            psAddNewUser.setString(1, name);
//            psAddNewUser.execute();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public int getTotalGreets() {
//        int total = 0;
//        try {
//            ResultSet rs = psGetAllUsers.executeQuery();
//            while (rs.next()) {
//                total += rs.getInt("greet_count");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            return total;
//        }
//
//    }
//
//    public int getTotalGreets(String name) {
//        int total = 0;
//        try {
//            psGetTotalGreetsOfUser.setString(1, name);
//            ResultSet rs = psGetTotalGreetsOfUser.executeQuery();
//            rs.next();
//            total = rs.getInt("greet_count");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            return total;
//        }
//    }
//
//    public int getTotalUsers() {
//        int total = 0;
//        try {
//            ResultSet rs = psGetUsersTotalGreets.executeQuery();
//            rs.next();
//            total = rs.getInt("count");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            return total;
//        }
//    }
//
//    public void incrementGreets(String name) {
//        try {
//            psUpdateGreetCount.setString(1, name);
//            psUpdateGreetCount.execute();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public Map<String, Integer> getUsers() {
//        Map<String, Integer> result = new HashMap<>();
//
//        try {
//            ResultSet rs = psGetAllUsers.executeQuery();
//            while (rs.next()) {
//                result.put(rs.getString("username"), rs.getInt("greet_count"));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            return result;
//        }
//    }
//
//    public void clear() {
//        try {
//            psTruncateTable.execute();
//            Statement statement = conn.createStatement();
//            statement.addBatch("ALTER SEQUENCE greeted_id_seq RESTART with 1");
//            statement.executeBatch();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void delete(String name) {
//        try {
//            psDeleteUser.setString(1, name);
//            psDeleteUser.execute();
//            Statement statement = conn.createStatement();
//            statement.addBatch("ALTER SEQUENCE greeted_id_seq RESTART with 1");
//            statement.executeBatch();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }


}

