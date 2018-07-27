import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.UUID;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sound.sampled.Clip;
import javax.sql.DataSource;

//import oracle.sql.CLOB;


public class Config_JDBC {


    private static final String url = "jdbc:oracle:thin:@10.22.93.34:1521:DBTEST";
    private static final String user = "TESTUSER";
    private static final String password = "testuser";
    private static final String driverName = "oracle.jdbc.OracleDriver";
    private static InitialContext initContext;
    private static DataSource ds;
    private Connection conn = null;


    public void insertToDb(XMLCreator xmlCreator) throws ClassNotFoundException, SQLException, NamingException, ParseException {

        if (initContext==null) {
            initContext = new InitialContext();
        }

        //Context envContext = (Context) initContext.lookup("java:/comp/env");
        if (ds==null) {
            ds = (DataSource) initContext.lookup("java:comp/env/jdbc/DBTEST");// ������� ����
        }

//		conn = DriverManager.getConnection(url, user, password);
        conn = ds.getConnection();
        conn.setAutoCommit(false);




        //xml
        //CLOB clob = CLOB.createTemporary(conn, true, CLOB.DURATION_SESSION);
        Clob clob = conn.createClob();
        clob.setString(1, xmlCreator.modifyXML);
        //date
        String dateString = xmlCreator.dateDb;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        java.util.Date utilDate = format.parse(dateString);
        //java.sql.Date sqlDate = new java.sql.Date ;
        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(utilDate.getTime());
        //System.out.println(sqlTimestamp);


        //UUID

        UUID gu = xmlCreator.uuid1;
        String guid = gu.toString();

        //EnvelopeID
        UUID env = xmlCreator.envId;
        String envId = env.toString();
        //java.util.regex.Pattern patEnv = XMLCreator.patEnvID;

        //Type of XML
        String type = xmlCreator.typeXml;
        //System.out.println(type);

        PreparedStatement prepState1;
        prepState1 = conn.prepareStatement(
                "INSERT INTO KPSPP_TEST (MQUUID,SENDTIME,SENDXML,TYPEXML) VALUES(?,?,?,?)");

        if (xmlCreator.modifyXML.contains("EnvelopeID>")) {
            prepState1.setString(1, envId);
        } else {
            prepState1.setString(1, guid); //MQUUID
        }
        prepState1.setTimestamp(2, sqlTimestamp);
        prepState1.setClob(3, clob); //SENDXML
        prepState1.setString(4, type); //TYPEXML
        prepState1.executeQuery();
        prepState1.close();
        conn.commit();
        conn.close();
        //System.out.println("Success");

    }

}
