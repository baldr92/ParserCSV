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
        InserterToDb inserter = new InserterToDb();
        inserter.saveToDatabase(dataHolder);
        System.out.println("Data has been inputted!");
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
}
