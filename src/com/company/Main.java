package com.company;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.regex.Pattern;

public class Main {

    public static final Scanner input = new Scanner(System.in); //Global scanner
    public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//Global SDF

    public static void main(String[] args) throws ParseException, IOException{

        Leads[] leads = readLeads(); //Read in leads.csv
        Interactions[] interactions = readInter();//Read in interactions.csv

        //Welcome menu
        while (welcome() == 1){//Return 1 to login
            int login = login();//Login and validate login
            if (login==0){
                System.out.println("Goodbye");
                System.exit(0);
            }
            while (login==1){ //Login correct
                int mainMenuOpt = mainMenu();
                if (mainMenuOpt == 4) {//Exit
                    break;
                }while (mainMenuOpt == 1) {//Lead functionality
                    int leadOpt = leadsMenu();
                    if (leadOpt == 1) {//View leads
                        viewLead(leads);
                    } else if (leadOpt == 2) {//Add leads
                        addLead(leads);
                    } else if (leadOpt == 3) {//Delete leads
                        deleteLead(leads);
                    } else if (leadOpt == 4) {//Update leads
                        updateLead(leads);
                    }else{//Save and exit
                        writeLeads(leads);//Write object back to file
                        break;
                    }
                }while (mainMenuOpt == 2) {//Interaction functionality
                    int interactionsOpt = interactionsMenu();
                    if (interactionsOpt == 1) {//View interactions
                        viewInt(interactions);
                    } else if (interactionsOpt == 2) {//Add interactions
                        addInteractions(interactions);
                    } else if (interactionsOpt == 3) {//Deleted interactions
                        deleteInteractions(interactions);
                    } else if (interactionsOpt == 4) {//Update interactions
                        updateInteractions(interactions);
                    } else {//save and exit
                        writeInteractions(interactions);//Write object back to file
                        break;
                    }
                }while (mainMenuOpt == 3){
                    int reportMenu = reportMenu();
                    if (reportMenu == 1) {//Report by leads by age
                        reportAge(leads);
                    } else if (reportMenu == 2) {//Report potential interactions
                        interactionsReport(interactions);
                    } else if (reportMenu == 3) {//Interaction count by date
                        interactionsCount(interactions);
                    } else {//Exit
                        break;
                    }
                }
            }
        }
        System.out.println("Goodbye");//Exit CRM
    }

    public static Leads[] readLeads() throws FileNotFoundException, ParseException {
        //Read in leads.csv to object Leads
        Leads[] leads = new Leads[1000];
        int count = 0;
        Scanner scanner = new Scanner(new File("leads.csv"));
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String element[] = line.split(",");
            Date dob = sdf.parse(element[2]);
            leads[count] = new Leads(element[1], dob, Boolean.parseBoolean(element[3]), element[4], element[5], element[6]);
            count++;
        }
        return leads;
    }

    public static Interactions[] readInter() throws FileNotFoundException, ParseException {
        //Read in interactions.csv to object Interactions
        Interactions[] inter = new Interactions[1000];
        int count = 0;
        Scanner scanner = new Scanner(new File("interactions.csv"));
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String element[] = line.split(",");
            Date dateInt = sdf.parse(element[1]);
            inter[count] = new Interactions(dateInt, element[2], element[3], element[4]);
            count++;
        }
        return inter;
    }

    public static int welcome() {
        //Welcome menu
        System.out.println("Welcome to CRM");
        System.out.println("1. Login");
        System.out.println("2. Exit");
        System.out.print("Enter option:");
        String option = input.next();
        if (option.equals("1")) {
            return 1;
        } else if (option.equals("2")) {
            return 2;
        } else {
            System.out.println("Incorrect input");
            return welcome();
        }
    }

    public static int login() {
        //Login menu
        System.out.print("Username:");
        String userName = input.next();
        System.out.print("Password:");
        String password = input.next();
        while (userAuth(userName, password) == 1) {
            System.out.println("Username or password incorrect");
            System.out.print("Try again (Y/N):");
            String exit = input.next();
            if (exit.equals("Y")) {
                return login();
            } else {
                return 0;
            }
        }
        return 1;
    }

    public static int userAuth(String userName, String password) {
        //Check username and password
        if (userName.equals("admin") && password.equals("admin")) {
            return 0;
        } else {
            return 1;
        }
    }

    public static int mainMenu() {
        //Main menu
        System.out.println("1. Managing leads");
        System.out.println("2. Managing interactions");
        System.out.println("3. Report & statistics");
        System.out.println("4. Exit");
        System.out.print("Enter option:");
        String option = input.next();
        if (option.equals("1")) {
            return 1;
        } else if (option.equals("2")) {
            return 2;
        } else if (option.equals("3")) {
            return 3;
        }
        else if (option.equals("4")) {
            return 4;
        }else {
            System.out.println("Incorrect input");
            return mainMenu();
        }

    }


    /* Lead functionality*/
    public static int leadsMenu() {
        //Menu for leads functionality
        System.out.println("1. View leads");
        System.out.println("2. Add lead");
        System.out.println("3. Delete lead");
        System.out.println("4. Update leads");
        System.out.println("5. Save & Exit");
        System.out.print("Enter option:");
        String option = input.next();
        if (option.equals("1")) {
            return 1;
        } else if (option.equals("2")) {
            return 2;
        } else if (option.equals("3")) {
            return 3;
        } else if (option.equals("4")) {
            return 4;
        } else if (option.equals("5")) {
            return 5;
        } else {
            System.out.println("Invalid option");
            return leadsMenu();
        }
    }

    public static void viewLead(Leads[] leads) {
        //Views all the leads
        for (Leads n : leads) {
            if (n != null) {
                if (n.getName() != null) {//deleted leads
                    System.out.println(n.getId() + "\t\t" + n.getName() + "\t\t" + sdf.format(n.getDob()) + "\t\t" + n.isGender() + "\t\t" + n.getPhone() + "\t\t" + n.getEmail() + "\t\t" + n.getAddress());
                }
            }
        }
    }

    public static void addLead(Leads[] leads) throws ParseException {
        //Add leads
        System.out.print("Name:");
        String name = input.next();
        input.nextLine();
        System.out.print("Date of birth:");
        Date dob = sdf.parse(input.nextLine());
        System.out.print("Gender:");
        Boolean gender = input.nextBoolean();
        input.nextLine();
        System.out.print("Phone number:");
        String phoneNo = input.nextLine();
        System.out.print("Email:");
        String email = input.nextLine();
        System.out.print("Address:");
        String address =input.nextLine();
        if(nameValidation(name) || phoneValidation(phoneNo) || emailValidation(email)) {
            leads[Leads.getCounter()] = new Leads(name, dob, gender, phoneNo, email, address);
        }else {
            System.out.println("Incorrect syntax");
        }
    }

    public static void deleteLead(Leads[] leads) {
        //Delete leads
        int counter = 0;
        System.out.print("ID that you want to delete:");
        String id = input.next();
        for (Leads n : leads) {
            if (n != null) {
                if (n.getId().equals(id)) {
                    n.setName(null);
                }
            }
        }
    }

    public static void updateLead(Leads[] leads) throws ParseException {
        System.out.print("ID that you want to update:");
        String id = input.next();
        input.nextLine();
        System.out.print("Name:");
        String name = input.next();
        input.nextLine();
        System.out.print("Date of birth:");
        Date dob = sdf.parse(input.nextLine());
        System.out.print("Gender:");
        Boolean gender = input.nextBoolean();
        input.nextLine();
        System.out.print("Phone number:");
        String phoneNo = input.nextLine();
        System.out.print("Email:");
        String email = input.nextLine();
        System.out.print("Address:");
        String address =input.nextLine();
        for (Leads n : leads) {
            if (n != null) {
                if (n.getName() != null || nameValidation(name) || emailValidation(email) || addressValidation(address)) {
                    if (n.getId().equals(id)) {
                        n.setName(name);
                        n.setDob(dob);
                        n.setGender(gender);
                        n.setPhone(phoneNo);
                        n.setEmail(email);
                        n.setAddress(address);
                    }
                }else {
                    System.out.println("Incorrect syntax");
                }
            }
        }
    }

    public static void writeLeads(Leads[] leads) {
        //Write lead object to file
        PrintWriter output = null;
        try {
            output = new PrintWriter(new FileWriter("leads.csv", false));
            for (Leads n : leads) {
                if (n != null) {
                    if (n.getName() != null) {//Name is null mean that the lead was deleted
                        output.println(n.getId() + "," + n.getName() + "," + sdf.format(n.getDob()) + "," + n.isGender() + "," + n.getPhone() + "," + n.getEmail() + "," + n.getAddress());
                    }
                }
            }
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        } finally {
            if (output != null) {
                output.close();
            }
        }
    }


    /* Interactions functionality*/
    public static int interactionsMenu() {
        //Menu for interactions functionality
        System.out.println("1. View interactions");
        System.out.println("2. Add interactions");
        System.out.println("3. Delete interactions");
        System.out.println("4. Update interactions");
        System.out.println("5. Save & Exit");
        System.out.print("Enter option:");
        String option = input.next();
        if (option.equals("1")) {
            return 1;
        } else if (option.equals("2")) {
            return 2;
        } else if (option.equals("3")) {
            return 3;
        } else if (option.equals("4")) {
            return 4;
        } else if (option.equals("5")) {
            return 5;
        } else {
            System.out.println("Invalid option");
            return interactionsMenu();
        }
    }

    public static void viewInt(Interactions[] interactions) {
        for (Interactions n : interactions) {
            if (n != null) {
                if (n.getLeadID() != null) {//deleted
                    System.out.println(n.getId() + "\t" + sdf.format(n.getDate()) + "\t" + n.getLeadID() + "\t" + n.getMethod() + "\t" + n.getPotential());
                }
            }
        }
    }

    public static void addInteractions(Interactions[] interactions) throws ParseException {
        System.out.print("Date:");
        Date date = sdf.parse(input.next());
        System.out.print("Lead ID:");
        String leadID = input.next();
        System.out.print("Method:");
        String method = input.next();
        System.out.print("Potential:");
        String address = input.next();
        if (methodValidation(method)) {
            interactions[Interactions.getCounter()] = new Interactions(date, leadID, method, address);
        }else {
            System.out.println("Incorrect syntax");
        }
    }

    public static void deleteInteractions(Interactions[] interactions) {
        int counter = 0;
        System.out.print("ID that you want to delete:");
        String id = input.next();
        for (Interactions n : interactions) {
            if (n != null) {
                if (n.getLeadID() != null) {//deleted
                    if (n.getId().equals(id)) {
                        n.setLeadID(null);
                    }
                }
            }
        }
    }

    public static void updateInteractions(Interactions[] interactions) throws ParseException {
        System.out.print("ID that you want to update:");
        String id = input.next();
        System.out.print("Date:");
        Date date = sdf.parse(input.next());
        System.out.print("Lead ID:");
        String leadID = input.next();
        System.out.print("Method");
        String method = input.next();
        System.out.print("Potential:");
        String potential = input.next();
        for (Interactions n : interactions) {
            if (n != null) {
                if (n.getLeadID() != null && methodValidation(method)) {//deleted
                    if (n.getId().equals(id)) {
                        n.setDate(date);
                        n.setLeadID(leadID);
                        n.setMethod(method);
                        n.setPotential(potential);
                    }
                }else {
                    System.out.println("Incorrect syntax");
                }
            }
        }
    }

    public static void writeInteractions(Interactions[] interactions) {
        PrintWriter output = null;
        try {
            output = new PrintWriter(new FileWriter("interactions.csv", false));
            for (Interactions n : interactions) {
                if (n != null) {
                    if (n.getLeadID() != null) {
                        output.println(n.getId() + "," + sdf.format(n.getDate()) + "," + n.getLeadID() + "," + n.getMethod() + "," + n.getPotential());
                    }
                }
            }
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        } finally {
            if (output != null) {
                output.close();
            }
        }
    }

    /* Report and statistics*/
    public static int reportMenu(){
        //Report menu
        System.out.println("1. Age summary");
        System.out.println("2. Potential interactions");
        System.out.println("3. Interactions report by month");
        System.out.println("4. Exit");
        System.out.print("Enter option:");
        String option = input.next();
        if (option.equals("1")){
            return 1;
        }else if(option.equals("2")){
            return 2;
        }else if (option.equals("3")){
            return 3;
        }else if (option.equals("4")){
            return 4;
        }else {
            System.out.println("Invalid option");
            return reportMenu();
        }
    }

    public static void reportAge(Leads[] leads) {
        int under10 = 0, from10t20 = 0, from20t60 = 0, above60 = 0;
        for (Leads n : leads) {
            if (n != null) {
                if (n.getAge() <= 10) {
                    under10++;
                } else if (n.getAge() <= 20) {
                    from10t20++;
                } else if (n.getAge() <= 60) {
                    from20t60++;
                } else {
                    above60++;
                }
            }
        }
        System.out.println("0-10(years old)" + "  " + "10-20(years old)" + "  " + "20-60(years old)" + "  " + ">60(years old)");
        System.out.println(under10 + "\t\t\t\t\t" + from10t20 + "\t\t\t\t\t" + from20t60 + "\t\t\t\t\t" + above60);
    }

    public static void interactionsReport(Interactions[] interactions) throws ParseException {
        System.out.print("From date:");
        Date fromDate = sdf.parse(input.next());
        System.out.print("To date:");
        Date toDate = sdf.parse(input.next());
        int positive = 0, negative = 0, neutral = 0;
        for (Interactions n : interactions) {
            if (n != null) {
                if ((n.getDate().after(fromDate) || n.getDate().equals(fromDate)) && (n.getDate().before(toDate) || n.getDate().equals(toDate))) {
                    if (n.getPotential().equals("Positive")) {
                        positive++;
                    } else if (n.getPotential().equals("Negative")) {
                        negative++;
                    } else {
                        neutral++;
                    }
                }
            }
        }
        System.out.println("Positive" + "\t" + "Negative" + "\t" + "Neutral");
        System.out.println(positive + "\t" + negative + "\t" + neutral);
    }

    public static void interactionsCount(Interactions[] interactions) throws ParseException {
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        int[][] counter = new int[10][12];
        Calendar calendar = Calendar.getInstance();
        System.out.print("From date:");
        Date fromDate = sdf.parse(input.next());
        System.out.print("To date:");
        Date toDate = sdf.parse(input.next());
        calendar.setTime(fromDate);
        int fromMonth = calendar.get(Calendar.MONTH);
        int fromYear = calendar.get(Calendar.YEAR);
        calendar.setTime(toDate);
        int toMonth = calendar.get(Calendar.MONTH);
        int toYear = calendar.get(Calendar.YEAR);
        int yearDiff = toYear - fromYear;
        for (int i = 0; i <= yearDiff; i++) {
            for (int y = fromMonth; y <= 11; y++) {
                for (Interactions n : interactions) {
                    if (n != null) {
                        if (n.getMonth() == y && n.getYear() == (fromYear + i)) {
                            counter[i][y]++;
                        }
                    }
                }
                if (counter[i][y] > 0) {
                    System.out.print(months[y] + " " + (fromYear + i) +"\t");
                }
            }
        }
        System.out.println("");
        for (int i = 0; i <= yearDiff; i++) {
            for (int y = fromMonth; y <= 11; y++) {
                if (counter[i][y] > 0) {
                    System.out.print(counter[i][y]+"\t\t\t\t");
                }
            }
        }
    }

    /* Input validation*/
    public static boolean emailValidation(String email){
        //Email validation
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$"; ;
        Pattern pat = Pattern.compile(regex);
        if (email==null){
            return false;
        }
        return pat.matcher(email).matches();
    }

     public static boolean nameValidation(String name){
         //Name validation
         String regex  = "^[\\p{L} .'-]+$";
         Pattern pat = Pattern.compile(regex);
         if (name==null){
             return false;
         }
         return pat.matcher(name).matches();
     }

     public static boolean phoneValidation(String phoneNumber){
         //Phone number validation
         String regex ="(09|01[2|6|8|9]|07|08)+([0-9]{8})";
         Pattern pat = Pattern.compile(regex);
         if (phoneNumber==null){
             return false;
         }
         return pat.matcher(phoneNumber).matches();
     }

     public static boolean methodValidation(String method){
         //Method validation
         String[] allMethod = new String[]{"email","telephone","face to face","social media"};
         for (String element :allMethod){
             if (element.equals(method)){
                 return true;
             }
         }
         return false;
     }

    public static boolean potentialValidation(String potential){
        //Method validation
        String[] allMethod = new String[]{"positive","negative","neutral"};
        for (String element :allMethod){
            if (element.equals(potential)){
                return true;
            }
        }
        return false;
    }

     public static boolean addressValidation(String address){
         //Address validation
         String regex ="[A-Za-z0-9'\\.\\-\\s\\,]";
         Pattern pat = Pattern.compile(regex);
         if (address==null){
             return false;
         }
         return pat.matcher(address).matches();
     }

}
