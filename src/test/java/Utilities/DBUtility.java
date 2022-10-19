package Utilities;

import com.mongodb.DBCollection;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBUtility {
    private static Connection connection;
    protected static Statement statement;

    private static void DBConnectionOpen(){
        String url="jdbc:mysql://db-technostudy.ckr1jisflxpv.us-east-1.rds.amazonaws.com:3306/sakila";  // hotstname , port/db adını (eser db)
        String username="root";  // username
        String password="'\"-LhCB'.%k[4S]z";  // password

        try {
            connection = DriverManager.getConnection(url,username,password);
            statement =connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private static void DBConnectionClose(){
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {  // test main
        List<List<String>> tablo= getListData("select * from actor");

        for (List<String> satir: tablo) { // test için kontrol, veriler geldi mi, liste atıldı mı
            System.out.println("tablo = " + satir);
        }
    }


    public static List<List<String>> getListData(String query){
        List<List<String>> tablo=new ArrayList<>();
        // db den bütün satırları ve sütünları okuyup bu liste atıcam

        DBConnectionOpen(); // db bağlantıyı aç

        // işlemleri yap
        try {
            ResultSet rs = statement.executeQuery(query);     // 1- sorguyu çalıştır
//            ResultSetMetaData rsmd = rs.getMetaData();
//
//            int columnCount = rsmd.getColumnCount();
                                                               // 2- bütün satırları ve o satırlardaki sütünları oku tabloya ekle
            int columnCount=rs.getMetaData().getColumnCount();

            while (rs.next()) {
                List<String> satir=new ArrayList<>();
                for (int i = 1; i <= columnCount; i++) {
                    satir.add(rs.getString(i));
                }
                tablo.add(satir);
            }

        }catch (Exception ex){
            System.out.println("ex.getMessage() = " + ex.getMessage());
        }

        DBConnectionClose(); // db bağlantıyı kapat

        return tablo;
    }

}
