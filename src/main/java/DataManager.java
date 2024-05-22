
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class DataManager {
    private List<Donor> donors;
    private List<Beneficiary> beneficiaries;

    public DataManager() {
        this.donors = new ArrayList<>();
        this.beneficiaries = new ArrayList<>();
        initializeDummyData();
    }

    private void initializeDummyData() {
        // Add donors
    	try {
     	   File donorFile = new File("resources/text/Donors.txt");
     	   Scanner donorScanner = new Scanner(donorFile);
     	   while (donorScanner.hasNextLine()) {
     		   String donorLine = donorScanner.nextLine();
     		   if (donorLine.length()==0) {
     			   continue;
     		   }
     		   String[] infoArray = donorLine.split("\\s*,\\s*");
     		   String fName = infoArray[0];
     		   String lName = infoArray[1];
     		   String eMail = infoArray[2];
     		   String phone = infoArray[3];
     		   boolean food = Boolean.parseBoolean(infoArray[4]);
     		   boolean firstAid = Boolean.parseBoolean(infoArray[5]);
     		   boolean clothing = Boolean.parseBoolean(infoArray[6]);
     		   boolean time = Boolean.parseBoolean(infoArray[7]);
     		   donors.add(new Donor(fName, lName, eMail, phone, food, firstAid, clothing, time));
     	   }
     	   donorScanner.close();
         }
         catch (FileNotFoundException e){
         	System.out.println("ERROR");
         	e.printStackTrace();
         }
        donors.add(new Donor("Charles", "Leclerc", "charles16@gmail.com", "000-000-0000", true, true, true, false));
        donors.add(new Donor("Max", "Verstappen", "max33@gmail.com", "111-111-1111", false, true, false, true));

        // Add beneficiaries
       try {
    	   File benefitFile = new File("resources/text/Beneficiaries.txt");
    	   Scanner benefitScanner = new Scanner(benefitFile);
    	   while (benefitScanner.hasNextLine()) {
    		   String benefitLine = benefitScanner.nextLine();
    		   if (benefitLine.length()==0) {
     			   continue;
     		   }
    		   String[] infoArray = benefitLine.split("\\s*,\\s*");
    		   String fName = infoArray[0];
    		   String lName = infoArray[1];
    		   String eMail = infoArray[2];
    		   String phone = infoArray[3];
    		   boolean food = Boolean.parseBoolean(infoArray[4]);
    		   boolean firstAid = Boolean.parseBoolean(infoArray[5]);
    		   boolean clothing = Boolean.parseBoolean(infoArray[6]);
    		   beneficiaries.add(new Beneficiary(fName, lName, eMail, phone, food, firstAid, clothing));
    	   }
    	   benefitScanner.close();
        }
        catch (FileNotFoundException e){
        	System.out.println("ERROR");
        	e.printStackTrace();
        }
    }

    public void addDonor(Donor donor) {
    	try {
    		FileWriter writer = new FileWriter("resources/text/Donors.txt", true);
    		writer.write("\n" + donor.firstName + ", " + donor.lastName + ", " + donor.email + ", " + donor.phoneNumber + ", " + String.valueOf(donor.food) + ", " + String.valueOf(donor.firstAid) + ", " + String.valueOf(donor.clothing) + ", " + String.valueOf(donor.time));
    		writer.close();
    	} catch (IOException e) {
    		System.out.println("WRITE ERROR");
    		e.printStackTrace();
    	}
        donors.add(donor);
    }

    public void addBeneficiary(Beneficiary beneficiary) {
    	try {
    		FileWriter writer = new FileWriter("resources/text/Beneficiaries.txt", true);
    		writer.write("\n" + beneficiary.firstName + ", " + beneficiary.lastName + ", " + beneficiary.email + ", " + beneficiary.phoneNumber + ", " + String.valueOf(beneficiary.food) + ", " + String.valueOf(beneficiary.firstAid) + ", " + String.valueOf(beneficiary.clothing));
    		writer.close();
    	} catch (IOException e) {
    		System.out.println("WRITE ERROR");
    		e.printStackTrace();
    	}
        beneficiaries.add(beneficiary);
    }

    public List<Donor> getDonors() {
        return donors;
    }

    public List<Beneficiary> getBeneficiaries() {
        return beneficiaries;
    }
}

class Donor {
    String firstName, lastName, email, phoneNumber;
    boolean food, firstAid, clothing, time;

    public Donor(String firstName, String lastName, String email, String phoneNumber, boolean food, boolean firstAid, boolean clothing, boolean time) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.food = food;
        this.firstAid = firstAid;
        this.clothing = clothing;
        this.time = time;
    }
}

class Beneficiary {
    String firstName, lastName, email, phoneNumber;
    boolean food, firstAid, clothing;

    public Beneficiary(String firstName, String lastName, String email, String phoneNumber, boolean food, boolean firstAid, boolean clothing) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.food = food;
        this.firstAid = firstAid;
        this.clothing = clothing;
    }
}

class Nonprofit {
	public int id;
    public String name, ein, email, phoneNumber, address, imgPath;

    public Nonprofit(int id, String name, String ein, String email, String phoneNumber, String address, String imgPath) {
		this.id = id;
        this.name = name;
		this.ein = ein;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
		this.imgPath = imgPath;
    }
}

class DataDonor {
    String firstName, lastName, email, phoneNumber, street, city, state, zip, login_id;
    boolean food, firstAid, clothing, time;
public DataDonor(String firstName, String lastName, String email, String phoneNumber, boolean food, boolean firstAid, boolean clothing, boolean time, String street, String city, String state, String zip, String login_id) {
	this.firstName = firstName;
	this.lastName = lastName;
	this.email = email;
	this.phoneNumber = phoneNumber;
	this.food = food;
	this.firstAid = firstAid;
	this.clothing = clothing;
	this.time = time;
	this.street = street;
	this.city = city;
	this.state = state;
	this.zip = zip;
	this.login_id = login_id;
}
}

class DataBeneficiary {
String firstName, lastName, email, phoneNumber, street, city, state, zip, login_id;
boolean food, firstAid, clothing;

public DataBeneficiary(String firstName, String lastName, String email, String phoneNumber, boolean food, boolean firstAid, boolean clothing, String street, String city, String state, String zip, String login_id) {
	this.firstName = firstName;
	this.lastName = lastName;
	this.email = email;
	this.phoneNumber = phoneNumber;
	this.food = food;
	this.firstAid = firstAid;
	this.clothing = clothing;
	this.street = street;
	this.city = city;
	this.state = state;
	this.zip = zip;
	this.login_id = login_id;
}
}
