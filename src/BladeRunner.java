import org.apache.poi.openxml4j.exceptions.NotOfficeXmlFileException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class BladeRunner {
    public static void main( String [] args ) {
        String fileName="D:\\test_case.csv";
        Vector dataHolder = read(fileName);
        read(fileName);
        System.out.println("Success");
        //saveToDatabase(dataHolder);
    }
    public static Vector read(String fileName)    {
        Vector cellVectorHolder = new Vector();
        try{
            FileInputStream myInput = new FileInputStream(fileName);
            XSSFWorkbook myWorkBook = new XSSFWorkbook(myInput);
            XSSFSheet mySheet = myWorkBook.getSheetAt(0);
            Iterator rowIterator = mySheet.rowIterator();
            while(rowIterator.hasNext()){
                XSSFRow myRow = (XSSFRow) rowIterator.next();
                Iterator cellIterator = myRow.cellIterator();
                //Vector cellStoreVector=new Vector();
                List list = new ArrayList();
                while(cellIterator.hasNext()){
                    XSSFCell myCell = (XSSFCell) cellIterator.next();
                    list.add(myCell);
                }
                cellVectorHolder.addElement(list);
            }
        }
        catch (NotOfficeXmlFileException  noxe){

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return cellVectorHolder;
    }
    /*
    private static void saveToDatabase(Vector dataHolder) {
        String ClientAdd="";
        String Page="";
        String AccessDate="";
        String   ProcessTime="";
        String Bytes="";
        System.out.println(dataHolder);

        for(Iterator iterator = dataHolder.iterator();iterator.hasNext();) {
            List list = (List) iterator.next();
            ClientAdd = list.get(0).toString();
            Page = list.get(1).toString();
            AccessDate = list.get(2).toString();
            ProcessTime = list.get(3).toString();

            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "welcome");
                System.out.println("connection made...");
                PreparedStatement stmt=con.prepareStatement("INSERT INTO ClickStream(ClientAdd,Page,AccessDate,ProcessTime) VALUES(?,?,?,?)");
                stmt.setString(1, ClientAdd);
                stmt.setString(2, Page);
                stmt.setString(3, AccessDate);
                stmt.setString(4, ProcessTime);
                stmt.executeUpdate();

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
    */
}
