
package personalfinancesystem;
//prompt the user to divide it's income to different expense area . and calculate the balance that can be used
//example - 15% on medical 30%o on loans 30% on family 15%traveling 10% saving

import java.util.Scanner;



import java.util.Date;
import java.util.Calendar;
import java.text.*;
import java.text.DateFormatSymbols;
import java.text.ParseException;

public class ReportModule {

    private String username;
    private String [] assName;
    private String [] assDate;
    private String [] assValue;
    private String [] incName;
    private String [] incDate;
    private String [] incValue;
    private String [] exName;
    private String [] exType;
    private String [] exDate;
    private String [] exValue;
    private String currentdate;
    private double totalAsset;
    private double totalIncome;
    private double totalExpense;
    
    LoginModule userlogin = new LoginModule();
    AssetModule userAsset;
    IncomeModule userIncome;
    ExpenseModule userExpense;

public ReportModule (String username){
        this.username = username;
        userAsset = new AssetModule(username);
        userIncome = new IncomeModule(username);
        userExpense = new ExpenseModule(username);
    }
    
    //get the values of each field arrays to initialize the instance variables in the ReportingModule
    public void setAllValues(){
        this.assName = userAsset.getAssetNameList();
        this.assDate = userAsset.getAssetDateList();
        this.assValue = userAsset.getAssetValueList();
        this.incName = userIncome.getIncomeNameList();
        this.incDate = userIncome.getIncomeDateList();
        this.incValue = userIncome.getIncomeValueList();
        this.exName = userExpense.getExpenseNameList();
        this.exType = userExpense.getExpenseTypeList();
        this.exDate = userExpense.getExpenseDateList();
        this.exValue = userExpense.getExpenseValueList();
    }
    
    //display the value in specific tab format
    public void display (String x){
       
	if (x.length() <8){
		System.out.print(x + "\t\t\t\t");}
	else if (x.length() <16){
		System.out.print(x + "\t\t\t");}
	else if (x.length() <24){
		System.out.print(x + "\t\t");}
	else if (x.length() <32){
		System.out.print(x + "\t");}
        
    }
    
    public void setDate(){
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy");
        currentdate = ft.format(dNow);
    }
    
    //display a report of all the financial updates done on the day
    public void dailyReport(){
        setDate();
        int counter = 1;
        double total = 0;
        totalAsset = 0;
        totalIncome = 0;
        totalExpense = 0;
        System.out.println("----------DAILY REPORT----------");
        System.out.println("----- ASSET -----");
        System.out.println("\tAsset\t\t\t\tValue[RM]\t\t\t\tDate[DayMonthYear]");
        for (int i = 0; i < userAsset.checkDatabaseLines(); i++){
            if (currentdate.equals(assDate[i])){
                System.out.print(counter + ".\t" );
                display(assName[i]);
                display(assValue[i]);
                display(assDate[i]);
                System.out.println(" ");
                total += Double.parseDouble(assValue[i]);  
                totalAsset += Double.parseDouble(assValue[i]);
                counter++;
            }
        }
        
        System.out.println("----- INCOME -----");
        System.out.println("\tIncome\t\t\tValue[RM]\t\t\t\tDate[DayMonthYear]");
        for (int i = 0; i < userIncome.checkDatabaseLines(); i++){
            if (currentdate.equals (incDate[i])){
                System.out.print(counter + ".\t" );
                display(incName[i]);
                display(incValue[i]);
                display(incDate[i]);
               
                System.out.println(" ");
                total += Double.parseDouble(incValue[i]);
                totalIncome += Double.parseDouble(incValue[i]);
                counter++;
            }
        }
        
        System.out.println("----- EXPENSE -----");
        System.out.println("\tExpense\t\t\t\tExpense Type\t\t\tDate\t\t\t\tValue");
        for (int i = 0; i < userExpense.checkExpenseDatabaseLines(); i++){
            if (currentdate.equals(exDate[i])){
                System.out.print(counter + ".\t" );
                display(exName[i]);
                display(exType[i]);
                display(exValue[i]);
                display(exDate[i]);
                System.out.println(" ");
                total -= Double.parseDouble(exValue[i]);
                totalExpense += Double.parseDouble(exValue[i]);
                counter++;
            }
        }
        System.out.println("Total Asset: " + totalAsset);
        System.out.println("Total Income: " + totalIncome);
        System.out.println("Total Expense: " + totalExpense);
        System.out.println("Gain/Loss: " + total);
    }
    
    //display a report of all the financial updates within a week
    public void weeklyReport(){
        setDate();
        int counter = 1;
        double total = 0;
        totalAsset = 0;
        totalIncome = 0;
        totalExpense = 0;
        int currentWeek = 0;
        String format = "ddMMyyyy"; 
        Calendar calendar = Calendar.getInstance();
        currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        
        //getting the number of week of the current date
        try{
            SimpleDateFormat df = new SimpleDateFormat(format);
            Date d = new Date();
            Date date = df.parse(currentdate);
            Calendar cale = Calendar.getInstance();
            cale.setTime(date);
        } catch (ParseException e){
            System.out.println("Cannot parse value.");
        }
        System.out.println("----------WEEKLY REPORT----------");
        System.out.println("-----  WEEK :" + currentWeek + " -----" );
        System.out.println("----- ASSET -----");
        System.out.println("\tAsset\t\t\tValue[RM]\t\t\t\tDate[DayMonthYear]");
        
        //getting the weeks of the record inputted by user
        int  weekOfAsset;
        String cal;
        for (int i = 0; i < userAsset.checkDatabaseLines()-1; i++){
            cal = assDate[i];

             cal = cal.replace("/",""); 
            try{
                SimpleDateFormat df = new SimpleDateFormat(format);
                Date date = df.parse(cal);
                Calendar cale = Calendar.getInstance();
                cale.setTime(date);
                weekOfAsset = cale.get(Calendar.WEEK_OF_YEAR);
                if (weekOfAsset == currentWeek){
                    System.out.print(counter + ".\t" );
                    display(assName[i]);
                    display(assValue[i]);
                    display(assDate[i]);
                    
                    System.out.println(" ");
                    total += Double.parseDouble(assValue[i]);  
                    totalAsset += Double.parseDouble(assValue[i]);
                    counter++;
                }
            } catch (ParseException e){
                System.out.println("Cannot parse value.");
            }
        }
       
        System.out.println("------- INCOME -------");
        System.out.println("\tIncome\t\t\tValue[RM]\t\t\t\tDate[DayMonthYear]");
        //getting the weeks of the record inputted by user
        int  weekOfIncome;
        for (int i = 0; i < userIncome.checkDatabaseLines()-3; i++){
            cal = incDate[i];
            cal = cal.replace("/", "");       
            try{
                SimpleDateFormat df = new SimpleDateFormat(format);
                Date date = df.parse(cal);
                Calendar cale = Calendar.getInstance();
                cale.setTime(date);
                weekOfIncome = cale.get(Calendar.WEEK_OF_YEAR);
                if (weekOfIncome == currentWeek){
                    System.out.print(counter + ".\t" );
                    display(incName[i]);
                    display(incValue[i]);
                    display(incDate[i]);
                    
                    System.out.println(" ");
                    total += Double.parseDouble(incValue[i]);  
                    totalIncome += Double.parseDouble(incValue[i]);
                    counter++;
                }
            } catch (ParseException e){
                System.out.println("Cannot parse value.");
            }
        }
        
        System.out.println("----- EXPENSE -----");
        System.out.println("\tExpense\t\t\t\tExpense Type\t\t\tValue[RM]\t\t\t\tDate[DayMonthYear]");
        //getting the weeks of the record inputted by user
        int  weekOfExpense;
        for (int i = 0; i < userExpense.checkExpenseDatabaseLines()/3; i++){
            System.out.println("number"+userExpense.checkExpenseDatabaseLines());
            cal = exDate[i];
            cal = cal.replace("/", "");  
            try{
                SimpleDateFormat df = new SimpleDateFormat(format);
                Date date = df.parse(cal);
                Calendar cale = Calendar.getInstance();
                cale.setTime(date);
                weekOfExpense= cale.get(Calendar.WEEK_OF_YEAR);
                if (weekOfExpense == currentWeek){
                    System.out.print(counter + ".\t" );
                    display(exName[i]);
                    display(exType[i]);
                    display(exValue[i]);
                    display(exDate[i]);
                    
                    System.out.println(" ");
                    total -= Double.parseDouble(exValue[i]);  
                    totalExpense += Double.parseDouble(exValue[i]);
                    counter++;
                }
            } catch (ParseException e){
                System.out.println("Cannot parse value.");
            }
        }
        System.out.println("Total Asset: " + totalAsset);
        System.out.println("Total Income: " + totalIncome);
        System.out.println("Total Expense: " + totalExpense);
        System.out.println("Gain/Loss: " + total);
    }
    
    //display a report of financial updates done within the month
    public void monthlyReport(){
        setDate();
        int counter = 1;
        double total = 0;
        totalAsset = 0;
        totalIncome = 0;
        totalExpense = 0;
        Date dNow = new Date();
        SimpleDateFormat f = new SimpleDateFormat("MM");
        int currentmonth = Integer.parseInt(f.format(dNow));
        System.out.println("current month" + currentmonth);
        String MonthInWord = new DateFormatSymbols().getMonths()[currentmonth -1];
        System.out.println("----------MONTHLY REPORT----------");
        System.out.println("----- Month: " + MonthInWord + " -----" );
        System.out.println("----- ASSET -----");
        System.out.println("\tAsset\t\t\tValue[RM]\t\t\t\tDate[DayMonthYear]");
        //getting the month of the record inputted by user
        int [] monthOfAsset = new int [3];
        for (int i = 0; i < userAsset.checkDatabaseLines()-1; i++){  
            int x = 0;
            for (String ret : assDate[i].split("/")){
                monthOfAsset[x] = Integer.parseInt(ret);
                x++;
            }
            if (monthOfAsset[1] == currentmonth){
                    System.out.print(counter + ".\t" );
                    display(assName[i]);
                    display(assValue[i]);
                    display(assDate[i]);
                    System.out.println(" ");
                    total += Double.parseDouble(assValue[i]);  
                    totalAsset += Double.parseDouble(assValue[i]);
            }   
        }
        
        System.out.println("----- INCOME -----");
        System.out.println("\tIncome\t\t\tValue[RM]\t\t\t\tDate[DayMonthYear]");
        //getting the month of the record inputted by user
        int [] monthOfIncome = new int [3];
        for (int i = 0; i < userIncome.checkDatabaseLines()-3; i++){  
            int x = 0;
            for (String ret : incDate[i].split("/",0)){
                monthOfIncome[x] = Integer.parseInt(ret);
                x++;
            }   
            if (monthOfIncome[1] == currentmonth){
                    System.out.print(counter + ".\t" );
                    display(incName[i]);
                    display(incDate[i]);
                    display(incValue[i]);
                    System.out.println(" ");
                    total += Double.parseDouble(incValue[i]);  
                    totalIncome += Double.parseDouble(incValue[i]);    
            }
        }
        
        System.out.println("-----Expense -----");
        System.out.println("\tExpense\t\t\t\tExpense Type\t\t\tDate\t\t\t\tValue");
        //getting the month of the record inputted by user
        int [] monthOfExpense = new int [3];
        for (int i = 0; i < userIncome.checkDatabaseLines()/3; i++){  
            int x = 0;
            for (String ret : exDate[i].split("/")){
                monthOfExpense[x] = Integer.parseInt(ret);
                x++;
            }
            if (monthOfExpense[1] == currentmonth){
                    System.out.print(counter + ".\t" );
                    display(exName[i]);
                    display(exType[i]);
                    display(exValue[i]);
                    display(exDate[i]);
                    System.out.println(" ");
                    total -= Double.parseDouble(exValue[i]);  
                    totalExpense += Double.parseDouble(exValue[i]);    
            } 
        }
        System.out.println("Total Asset: " + totalAsset);
        System.out.println("Total Income: " + totalIncome);
        System.out.println("Total Expense: " + totalExpense);
        System.out.println("Gain/Loss: " + total);
    counter ++;
    }
}
//d