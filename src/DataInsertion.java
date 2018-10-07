import java.sql.Statement;
import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class DataInsertion {

	public static void main(String[] args) {
		

		try {
			
			DatabaseClient dbClient = new DatabaseClient("log660ora12c.logti.etsmtl.ca", "equipe7", "r74w84K3", 1521, "LOG660");
			ArrayList<Customer> customers = XmlDataExtracter.extractCustomers("data/customers.xml");
			dbClient.insertCustomers(customers);
			System.out.println("Done");
			
		} catch (SQLException | XmlPullParserException | IOException e) {
			e.printStackTrace();
		}
	}
}
