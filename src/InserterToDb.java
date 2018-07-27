import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class InserterToDb {
    //инициализация ссылки и логина и пароля
    private static final String jdbc_url = "jdbc:oracle:thin:@10.22.93.34:1521:DBTEST";
    private static final String user = "TESTUSER";
    private static final String password = "testuser";
    private static final String driverName = "org.postgresql.Driver";
    //инициализация объектов подключения
    private static Connection con;

    private static void saveToDatabase(Vector dataHolder)  {
        String ssoid, ts, grp,type, subtype, url, orgid, formid, code, ltpa, sudirresponse, ymdh;




        System.out.println(dataHolder);



        for(Iterator iterator = dataHolder.iterator(); iterator.hasNext();) {
            List list = (List) iterator.next();

            ssoid = list.get(0).toString();
            ts = list.get(1).toString();
            grp = list.get(2).toString();
            type = list.get(3).toString();
            subtype = list.get(4).toString();
            url = list.get(5).toString();
            orgid  = list.get(6).toString();
            formid = list.get(7).toString();
            code  = list.get(8).toString();
            ltpa = list.get(9).toString();
            sudirresponse = list.get(10).toString();
            ymdh = list.get(11).toString();

            try {
                //реализация моя через постгре
                Class.forName(driverName).newInstance();
                con = DriverManager.getConnection(jdbc_url, user, password);

                //Class.forName("com.mysql.jdbc.Driver").newInstance();
                //Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "welcome");
                System.out.println("connection made...");
                PreparedStatement stmt = con.prepareStatement("INSERT INTO DBTEST(ssoid,ts,grp, type, subtype, url, orgid, formid, code, ltpa, sudirresponse, ymdh) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
                stmt.setString(1, ssoid);
                stmt.setString(2, ts);
                stmt.setString(3, grp);
                stmt.setString(4, type);
                stmt.setString(5, subtype);
                stmt.setString(6, url);
                stmt.setString(7, orgid);
                stmt.setString(8, formid);
                stmt.setString(9, code);
                stmt.setString(10, ltpa);
                stmt.setString(11, sudirresponse);
                stmt.setString(12, ymdh);


                stmt.executeQuery();

                System.out.println("Data is inserted");
                stmt.close();
                con.close();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
