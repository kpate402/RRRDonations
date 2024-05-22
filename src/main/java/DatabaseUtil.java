import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DatabaseUtil {

    private static final String JDBC_URL = "jdbc:h2:~/test;MODE=MySQL"; 
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    /* Enter charity api key from rapidapi.com */
    private static final String x_rapidapi_key = ""; //Type here your API key

    private static final String[] einList = {"043683765", "363384397", "463605622", "861017788", "453934671", "721613750", "270833467", "862485825" };
    private static final String[] emailList = {"info@achf.org", "membership@cafha.net", "admin@techcharities.org", "communications@careforlife.org",
                                                "info@daysforgirls.org", "contact@guardiangroup.org", "kaeme.foundation@gmail.com", "speakundefeated@gmail.com"};
    private static final String[] phoneList = {"4076211957", "872-228-7844","8015833656", "4803268918", "5035044813", "8003808913", "6502558446", "8016047794"};
      
    
    // (*) initializeDatabase()
    // 
    public static void initializeDatabase() throws Exception{
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
            //DeleteBaseData(conn);
            if (!isDataInitialized(conn)) {
                System.out.println("Initializing base data...");
                initializeBaseData(conn);
                Insert_all_nonprofits();
            } else {
                System.out.println("Base data already initialized.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    // (*) isDataInitialized()
    // 
    private static boolean isDataInitialized(Connection conn) throws SQLException {
        ResultSet rs = conn.getMetaData().getTables(null, null, "DONORS", null);
        if (!rs.next()) {
            return false;
        }
        try (Statement stmt = conn.createStatement()) {
            ResultSet rsCount = stmt.executeQuery("SELECT COUNT(*) AS count FROM donors");
            if (rsCount.next()) {
                return rsCount.getInt("count") > 0; 
            }
        }
        return false;
    }

    
    // (*) initializeBaseData()
    // 
    public static void initializeBaseData(Connection conn) {
        String sqlFilePath = "resources/database.sql"; 
        try {
            System.out.println("its initalizing");
            String sqlCommands = new String(Files.readAllBytes(Paths.get(sqlFilePath)));
            
            Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            Statement statement = connection.createStatement();
            
        
            String[] commands = sqlCommands.split(";"); 
            //int i = 0;
            for (String command : commands) {
                // System.out.println(i + " " +command);
                // i++;
                if (!command.trim().isEmpty()) {
                    statement.execute(command.trim());
                }
            }
            
            statement.close();
            connection.close();
            System.out.println("Database initialized successfully.");
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        } 
        catch (Exception e) {
            System.err.println("Error initializing database: " + e.getMessage());
            e.printStackTrace();
        }
    }


// (*) DeleteBaseData()
// 
public static void DeleteBaseData(Connection conn) {
    String sqlFilePath = "resources/delete.sql"; 
    try {
        System.out.println("its deleting");
        String sqlCommands = new String(Files.readAllBytes(Paths.get(sqlFilePath)));
        
        Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
        Statement statement = connection.createStatement();
        
    
        String[] commands = sqlCommands.split(";"); 
        //int i = 0;
        for (String command : commands) {
            //System.out.println(i + " " +command);
            //i++;
            if (!command.trim().isEmpty()) {
                statement.execute(command.trim());
            }
        }
        
        statement.close();
        connection.close();
        System.out.println("Database initialized successfully.");
    } catch (SQLException e) {
        System.err.println("SQL Error: " + e.getMessage());
        e.printStackTrace();
    } 
    catch (Exception e) {
        System.err.println("Error initializing database: " + e.getMessage());
        e.printStackTrace();
    }
}


// (*) apiCall()
// This function makes a call to the API containing non-profits
public static String[] apiCall(String number) throws Exception{
    // // code for making an API call
		String x_rapidapi_host = "charityapi.p.rapidapi.com";
		String ein = number; // parameter
		String host = "https://charityapi.p.rapidapi.com/organizations/";
		String url = host + ein;
		// String charset = "UTF-8";
		// String i = "tt0110912";
		// String query = String.format("ein=%s",
		// URLEncoder.encode(ein, charset));
		HttpResponse<JsonNode> response = Unirest.get(url)
		.header("X-RapidAPI-Key", x_rapidapi_key)
		.header("X-RapidAPI-Host", x_rapidapi_host)
		.asJson();
		System.out.println(response.getStatus());
		System.out.println("amount of API call remaining for the month without paying: " + response.getHeaders().get("X-RateLimit-Requests-Remaining"));
		
		// code for arranging data from call in a readable format
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(response.getBody().toString());
		String prettyJsonString = gson.toJson(je);
		System.out.println(prettyJsonString);

		// code for parsing through data for specific values
		String[] list = prettyJsonString.split("\n");
		String[] filteredList = new String[list.length - 4];
		int count = 0;
		for (String line : list) {
			line.trim();
			if (!(line.endsWith("{") || line.endsWith("}"))) {
				// Remove trailing comma if present
				line = line.replaceAll(",$", "");
				line = line.replaceAll("\"", "");
				filteredList[count++] = line.trim();
			}
		}
			//this is printing the filered list
		System.out.println("This is the filtered list");
		for (String e : filteredList) {
			System.out.println(e);
		}
        return filteredList;
}


// (*) Insert_all_nonprofits()
// 
public static void Insert_all_nonprofits() throws Exception{
    for(int i = 0; i < einList.length; i++) {
        String[] filteredList = apiCall(einList[i]);
        Insert_from_api(filteredList, i);
    }
    //PrintAllNonprofits();
}


// (*) Insert_from_api()
// 
public static void Insert_from_api(String[] filtered_list, int count) {
		String name = null;
		String ein = null;
		String email = emailList[count];
		String phone_number = phoneList[count];
		String address = null;
		String street = null;
		String city = null;
		String state = null;
		String zip = null;
		try (Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);) {
		PreparedStatement pstmt = conn.prepareStatement
		("INSERT INTO non_profit_organizations (name, ein, email, phone_number, address) VALUES (?, ?, ?, ?, ?)");
			for (String e : filtered_list) {
			if (e.startsWith("name:")) {
				// Extract the value after "name:"
				name = e.substring(6).trim();
			} else if (e.startsWith("ein:")) {
				// Extract the value after "ein:"
				ein = e.substring(5).trim();
			} 
			else if (e.startsWith("street:")) {
				// Extract the value after "street:"
				street = e.substring(8).trim();
			} else if (e.startsWith("city:")) {
				// Extract the value after "city:"
				city = e.substring(6).trim();
			} else if (e.startsWith("state:")) {
				// Extract the value after "state:"
				state = e.substring(7).trim();
			} else if (e.startsWith("zip:")) {
				// Extract the value after "zip:"
				zip = e.substring(5).trim();
			}
		}
		address = street + " " + city + " " + state + " " + zip;
		System.out.println(address); 
		pstmt.setString(1, name);
		pstmt.setString(2, ein);
		pstmt.setString(3, email);
		pstmt.setString(4, phone_number);
		pstmt.setString(5, address);
		pstmt.executeUpdate();
      
   } catch (SQLException e) {
       System.err.println(e.getMessage());
   }
}


// (*) PrintAllNonprofits()
// 
public static void PrintAllNonprofits() {
    String sql = "SELECT * FROM non_profit_organizations";
    try (Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {
 
        while (rs.next()) {
            System.out.println
             ("Nonprofit ID: " + rs.getInt("org_id") +
              ", Name: " + rs.getString("name") + 
              ", Ein: " + rs.getString("ein") + 
              ", Email: " + rs.getString("email") + 
              ", Phone: " + rs.getString("phone_number") + 
              ", Address: " + rs.getString("address"));
        }
    } catch (SQLException e) {
        System.err.println(e.getMessage());
        e.printStackTrace();
    }
 }


// (*) GetAllNonprofits()
// 
 public static String[] GetAllNonprofits() {
    String sql = "SELECT * FROM non_profit_organizations";
    ArrayList<String> names = new ArrayList<>();
    String[] nameList = {};
    try (Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {
        while (rs.next()) {
           System.out.println
            ("Nonprofit ID: " + rs.getInt("org_id") +
             ", Name: " + rs.getString("name") + 
             ", Ein: " + rs.getString("ein") + 
             ", Email: " + rs.getString("email") + 
             ", Phone: " + rs.getString("phone_number") + 
             ", Address: " + rs.getString("address"));
              names.add(rs.getString("name"));
        }
        nameList = names.toArray(new String[0]);
        return nameList;

    } catch (SQLException e) {
        System.err.println(e.getMessage());
    }
    return nameList;
 } 
 


// (*) GetOneNonprofit()
// 
// public static void GetOneNonprofit(String name) {
//	 
// }

 public static Nonprofit GetOneNonprofit(String name) {

    // String sql = "SELECT * FROM non_profit_organizations WHERE name = ?";
    String sql = "SELECT * FROM non_profit_organizations n JOIN non_profit_organizations_icon ni ON n.org_id = ni.org_id WHERE n.name = ?";
    ArrayList<String> names = new ArrayList<>();
    String[] nameList = {};
    Nonprofit nonprofit = null;
    try (Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);) {
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, name);
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
        //    System.out.println
        //     ("Nonprofit ID: " + rs.getInt("org_id") +
        //      ", Name: " + rs.getString("name") + 
        //      ", Ein: " + rs.getString("ein") + 
        //      ", Email: " + rs.getString("email") + 
        //      ", Phone: " + rs.getString("phone_number") + 
        //      ", Address: " + rs.getString("address"));
             nonprofit = new Nonprofit(
                rs.getInt("org_id"),
                rs.getString("name"),
                rs.getString("ein"),
                rs.getString("email"),
                rs.getString("phone_number"),
                rs.getString("address"),
                rs.getString("icon_path")
            );
            //   names.add(rs.getString("name"));
        }
        // nameList = names.toArray(new String[0]);
        return nonprofit;

    } catch (SQLException e) {
        System.err.println(e.getMessage());
        return nonprofit;
    }
    //return nonprofit;
 } 

 public static void recordVisit(String entityType, int entityId) {
    String sql = "INSERT INTO visits (entity_type, entity_id) VALUES (?, ?) ON DUPLICATE KEY UPDATE visit_count = visit_count + 1";
    try (Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, entityType);
        pstmt.setInt(2, entityId);
        pstmt.executeUpdate();
    } catch (SQLException e) {
        System.err.println("Error recording visit: " + e.getMessage());
    }
}

public static int getVisitCount(String entityType, int entityId) {
    String sql = "SELECT visit_count FROM visits WHERE entity_type = ? AND entity_id = ?";
    try (Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, entityType);
        pstmt.setInt(2, entityId);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("visit_count");
        }
    } catch (SQLException e) {
        System.err.println("Error retrieving visit count: " + e.getMessage());
    }
    return 0;
}



 
 // (*) GetNonprofitaddress()
 // Given the name of the non-profit, this function will provide the address in the form needed to 
 // use by the google directions API function in RRRDonations.java
 public static String GetOneNonprofitaddress(String name) {
    String sql = "SELECT * FROM non_profit_organizations WHERE name = ?";
    ArrayList<String> names = new ArrayList<>();
    String[] nameList = {};
    String nonAddress = "Chicago+Union+Station"; // set a default value so that map will always work.
    try (Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);) {
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, name);
        ResultSet rs = preparedStatement.executeQuery();

        if (rs.next()) {
//           System.out.println
//            ("Nonprofit ID: " + rs.getInt("org_id") +
//             ", Name: " + rs.getString("name") + 
//             ", Ein: " + rs.getString("ein") + 
//             ", Email: " + rs.getString("email") + 
//             ", Phone: " + rs.getString("phone_number") + 
//             ", Address: " + rs.getString("address"));
            //   names.add(rs.getString("name"));
           nonAddress = rs.getString("address");
           //String replace space with + sign but be ware of preceding space
           if (nonAddress.substring(0, 1) == " ") {
        	   nonAddress = nonAddress.substring(1);// Removes any preceding space
           }
           // replace any remaining spaces in the address string with + signs
           nonAddress = nonAddress.replace(' ', '+'); // Prepares the string in the form needed for google directions API call
           System.out.println("nonAddress: " + nonAddress);
        }
        // nameList = names.toArray(new String[0]);
        return nonAddress;

    } catch (SQLException e) {
        System.err.println(e.getMessage());
    }
    return nonAddress;
    //return "";
 } 
 
 public static DataDonor GetOneDonor(String login) {

    // String sql = "SELECT * FROM non_profit_organizations WHERE name = ?";
    String sql = "SELECT * FROM donors WHERE login_id = ?";
    ArrayList<String> names = new ArrayList<>();
    String[] nameList = {};
    DataDonor donor = null;
    try (Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);) {
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, login);
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
        //    System.out.println
        //     ("Nonprofit ID: " + rs.getInt("org_id") +
        //      ", Name: " + rs.getString("name") + 
        //      ", Ein: " + rs.getString("ein") + 
        //      ", Email: " + rs.getString("email") + 
        //      ", Phone: " + rs.getString("phone_number") + 
        //      ", Address: " + rs.getString("address"));
             donor = new DataDonor(
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("email"),
                rs.getString("phone_number"),
                rs.getBoolean("food"),
                rs.getBoolean("first_aid"),
                rs.getBoolean("clothing"),
                rs.getBoolean("time"),
                rs.getString("street"),
                rs.getString("city"),
                rs.getString("state"),
                rs.getString("zip"),
                rs.getString("login_id")
            );
            //   names.add(rs.getString("name"));
        }
        // nameList = names.toArray(new String[0]);
        return donor;

    } catch (SQLException e) {
        System.err.println(e.getMessage());
        return donor;
    }
    //return nonprofit;
 } 

 public static DataBeneficiary GetOneBeneficiary(String login) {

    // String sql = "SELECT * FROM non_profit_organizations WHERE name = ?";
    String sql = "SELECT * FROM beneficiaries WHERE login_id = ?";
    ArrayList<String> names = new ArrayList<>();
    String[] nameList = {};
    DataBeneficiary Beneficiary = null;
    try (Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);) {
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, login);
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
        //    System.out.println
        //     ("Nonprofit ID: " + rs.getInt("org_id") +
        //      ", Name: " + rs.getString("name") + 
        //      ", Ein: " + rs.getString("ein") + 
        //      ", Email: " + rs.getString("email") + 
        //      ", Phone: " + rs.getString("phone_number") + 
        //      ", Address: " + rs.getString("address"));
            Beneficiary = new DataBeneficiary(
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("email"),
                rs.getString("phone_number"),
                rs.getBoolean("food"),
                rs.getBoolean("first_aid"),
                rs.getBoolean("clothing"),
                rs.getString("street"),
                rs.getString("city"),
                rs.getString("state"),
                rs.getString("zip"),
                rs.getString("login_id")
            );
            //   names.add(rs.getString("name"));
        }
        // nameList = names.toArray(new String[0]);
        return Beneficiary;

    } catch (SQLException e) {
        System.err.println(e.getMessage());
        return Beneficiary;
    }
    //return nonprofit;
 } 
 
// (*) SelectDonor()
// 

//  public static String[] SelectDonor(String firstname, String lastname) {

//     String sql = "SELECT email, phone_number FROM donors WHERE first_name = ? AND last_name = ?";

//     try (Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
//          PreparedStatement preparedStatement = conn.prepareStatement(sql);
//          ResultSet resultSet = preparedStatement.executeQuery();) {
//          preparedStatement.setString(1, firstName);
//          preparedStatement.setString(2, lastName);
//          while (resultSet.next()) {
//             String email = resultSet.getString("email");
//             String phoneNumber = resultSet.getString("phone_number");
//             System.out.println("Email: " + email);
//             System.out.println("Phone Number: " + phoneNumber);

//          }


//     } catch (SQLException e) {
//             e.printStackTrace();
//     }
// }




} //this is class ending bracket


// (*) PrintAllDonors()
// A private function to print all donors from the database

// private void PrintAllDonors() {
//     String sql = "SELECT * FROM donors";
//     String sql = "SELECT * FROM donors WHERE first_name = ? AND last_name = ?";
//     String sql = "INSERT"

//     try (Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
//          Statement stmt = conn.createStatement();
//          ResultSet rs = stmt.executeQuery(sql)) {

//         while (rs.next()) {
//             System.out.println("Donor ID: " + rs.getInt("donor_id") + ", Name: " + rs.getString("first_name") + " " + rs.getString("last_name") + ", Email: " + rs.getString("email") + ", Phone: " + rs.getString("phone_number") + ", Food: " + rs.getBoolean("food") + ", First Aid: " + rs.getBoolean("first_aid") + ", Clothing: " + rs.getBoolean("clothing") + ", Time: " + rs.getBoolean("time"));
//         }
//     } catch (SQLException e) {
//         System.err.println(e.getMessage());
//     }
// }


