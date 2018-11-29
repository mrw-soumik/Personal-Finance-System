
package personalfinancesystem;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.io.File;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/*
1.This is a module used by user to manipulate their income.[add , delete , edit]
2.If the user sell its' asset , the income will increase // pending 
3.All their expenses are saved in file named after their username [username_income.txt]

Constructor 
<constructor>ExpensesModule(String username)</constructor> ---- initialize the value of username taken form <class?LoginModule</class>

<method>incomeFileCreate()</method>         void     ---- create username_income.txt(file that named after the user)
<method>incomeFileCheck()</method>          void     ---- check whether the username_income.txt exists or not else create one
<method>checkIncomeDatabaseLines()</method> int      ---- returns the number of lines that exists in username_income.txt
<method>accessIncomeRecord()</method>       void     ---- access to the user's history record , and display the income for the user
<method>IncomeModule()</method>             void     ---- prompt the user choose whether to continue with adding data, editing data ,saving the data into username_income.txt or exit IncomeModule
<method>addIncome()</method>                Double   ---- prompt the user to enter the name of income and its value followed by storing data into username_income.txt
<method>editIncome()</method>               void     ---- prompt the user to edit data and then stored in the username_income.txt
<method>deleteIncome()</method>             void     ---- prompt the user to delete the income data incase the user entered wrongly or something need to be deleted
<method>Income()</method>                   void     ---- collection of all the methods to manipulate the income of the user
<method>incomeCalculate()</method>          void     ---- search the textfile and calculate the balance and add it into a array 

*/

public class IncomeModule extends LoginModule {
    Scanner assetinput = new Scanner(System.in);
    private String username;
    private String income ;
    private int order;
    private String [] item = new String [50];
    private String [] Date = new String [50];
    private double [] value = new double [50];
    private String [] exItem = new String [50];
    private String [] exValue = new String [50];
    private String [] exDate = new String [50];
    private String [] exRecord = new String [50];
    private boolean search = false;
  
    //arrays of different income fields
    private String [] incRecord;
    private String [] incName;
    private String [] incDate;
    private String [] incValue;
    
    
    LoginModule login = new LoginModule();
    Scanner input = new Scanner(System.in);
    
   // contsructor ,initialise the username taken from LoginModule
    public IncomeModule(String username){
       this.username=username;
    }
    
    //check whether the database of the user exits, else create one
    //create a file name username_income.txt
    public void IncomeFileCreate (){
     try{
        PrintWriter fileCreate = new PrintWriter(new FileOutputStream ( username+"_income.txt"));
        fileCreate.close();
     } catch (IOException a){
        System.out.println(a.getMessage());
      }
    }
    
    //check whether the file of the username_income.txt exist , else create one
    public void IncomeFileCheck(){
        try{
            Scanner inputstream = new Scanner (new FileInputStream (username+"_income.txt"));
            inputstream.close();
        } catch (FileNotFoundException a){
            System.out.println(a.getMessage());
            IncomeFileCreate();
        }
    }
    
    //check and return how many lines are there in the username_income.txt
    public int checkIncomeDatabaseLines(){
        int i = 0;
        try{
            Scanner inputstream = new Scanner (new FileInputStream(username+"_income.txt"));
            while(inputstream.hasNextLine()){
                inputstream.nextLine();
                i++;
            }
        } catch (FileNotFoundException a){
            System.out.println(a.getMessage());
        }
        return i;
    }
    
    //Access the history income records of the user and display it to the user
    //Included calculating the total value of the balance income
    public void accessIncomeRecords(){
       
      
      try {
          Scanner inputstream = new Scanner (new FileInputStream( username+"_income.txt"));
          int i = 1;
          String a;
          System.out.println("\tIncome Name\t\t\tValue\t\t\tDate[DayMonthYear]");
          while(inputstream.hasNextLine()){
              //get a line in the database
              String check =inputstream.nextLine();
              System.out.print(i + ".\t");
              //split the file
              for ( String ret : check.split(",")){
                  String hold = ret ;
                  if( hold.length()< 8){
                      System.out.print(hold + "\t\t\t\t");}
                  else if ( hold.length() < 16){
                      System.out.print(hold +"\t\t\t");}
                  else if (hold.length()< 24){
                      System.out.print(hold+"\t\t");
                 }
               
              }
               System.out.println(" ");
               i++;
          }
         
          inputstream.close();
          }catch(IOException e){
                  System.out.println(e.getMessage());
          }
         IncomeCalculate();
      }
    
   //prompt the user to choose which action to take [add , edit or delete]
    public void IncomeModule(){
    
        input = new Scanner(System.in);
        
        System.out.println("Enter numbers accordingly for the type of operation you want:");
        System.out.println("\n[1]to Add \n[2]to Edit \n[3]to Delete \n[4]to Exit");
        int command = input.nextInt();
        
        switch(command){
            
            case 1:
                System.out.println("-----ADD INCOME-----");
                addIncome();
                IncomeModule();
                
                break;
            
            case 2:
                System.out.println("-----EDIT INCOME-----");
                editIncome();
                IncomeModule();
                
                break;
            
            case 3:
                System.out.println("-----DELETE INCOME-----");
                deleteIncome();
                IncomeModule();
                
               break;
                
            case 4:
                System.out.println("----------EXIT INCOME!----------");
                break;
                
            default:
            //back to question is the user enter other integer other than [1][2][3]
            //IncomeModule im = new IncomeModule();
                System.out.println("Number Not Found.");
                break;
        
        }
    }
    
    //prompt the user to add income
    public void addIncome(){
        
        //set date
        Date date = new Date();
        
        Format formatter = new SimpleDateFormat("dd/MM/yyyy");
        //put the day/month/year into a string
        String s = formatter.format(date);
        
        try {
            //Transfer information into file "username_income.txt"
            PrintWriter pw = new PrintWriter(new FileOutputStream( username+"_income.txt",true));
            
            input = new Scanner(System.in);
            
            System.out.print("Enter Income Name : ");
            
            String income_name= input.nextLine();
            
            System.out.print("Enter Income Amount RM: ");
            
            double income_amount = input.nextDouble();
            
            pw.println(income_name+","+income_amount+","+s);  // s=date
            
            pw.close();
            
            System.out.println("Income has been added Successfully ");
            
        
        } catch (Exception e) {
        }
        accessIncomeRecords();
        
    }
    
    //calculate the total value of the user's income [Asset in the file]
    public void IncomeCalculate(){
    //put the item ,value and date into array . and then sums up the value of the income and display it to the user 
            double sum=0;
             int L=checkIncomeDatabaseLines();
             String [] store = new String [4];
             String [] item1 = new String [L+2];   
             double [] value1 = new double[L+2];
             String[]Date = new String[L+2];
              
                try{
                    Scanner inputStream = new Scanner(new FileInputStream (username+"_income.txt"));
                    int counter2 = 0;
                    while(inputStream.hasNextLine()){
                        String check = inputStream.nextLine();
                        int counter1 = 0 ;
                        for(String retrieve : check.split(",")){
                            store[counter1]=retrieve;
                            counter1++;
                        }
                        item1[counter2] = store[0];
                        value1[counter2] = Double.parseDouble(store[1]);
                        Date[counter2] = store [2];
                        counter2++;
                  }
                  for(int b = 0 ; b <= L ; b++){
                      sum=value1[b]+sum;}
           
            System.out.println("The total value of your current income are:RM"+sum);
              inputStream.close();
            }catch(FileNotFoundException a){
            System.out.println(a.getMessage());
                    
           
            }
           
          }
    
    //prompt the user to edit the data in username_income.txt
    public void editIncome(){
    
    //declare array for income_name, income_type, income_amount, income_date 
        accessIncomeRecords();
        
        int L = checkIncomeDatabaseLines();
        
        String [] income_name = new String[L]; 
        
        double [] income_value = new double[L];
        
        String [] income_date = new String [L];
        
        double sum=0;
             
        String [] store = new String [6];
         // call out date
         Date date = new Date();   
            
         Format formatter = new SimpleDateFormat("dd/MM/yyyy");
       
         String s = formatter.format(date); 
        
        
        try{
                    
            Scanner inputStream = new Scanner(new FileInputStream (username+"_income.txt"));
            int counter2 = 0;
              while(inputStream.hasNextLine()){
              String check = inputStream.nextLine();
               int counter1 = 0 ;
                   for(String retrieve : check.split(",")){
                   store[counter1]=retrieve;
                   counter1++;
           }
                        item[counter2] = store[0];
                        value[counter2] = Double.parseDouble(store[1]);
                        Date[counter2] = store [2];
                        counter2++;
                  }
                  inputStream.close();
            }catch(FileNotFoundException a){
            System.out.println(a.getMessage());
            }
        
         //let the user choose which to delete [int]   
            System.out.println("Which income would you like to edit : [choose the number]");
            int del =input.nextInt();
            
            item[del-1]="null";
            
            value[del-1]=0;
            
            Date[del-1]="null";
            
            System.out.println("Please enter the new Income Name : ");
            
            String income_name_edit= input.next();
            
            System.out.println("Please enter the new Income Amount : ");
            
            double income_amount_edit = input.nextDouble();
            
               //replace the deleted element with new data provided
                item[del-1]=income_name_edit;
                value[del-1]=income_amount_edit;
                Date[del-1]=s;
             
                //transfer information into file
             try{
                     PrintWriter inputStream = new PrintWriter (new FileOutputStream (username+"_income.txt"));
                       for(int a = 0 ; a<item.length ; a++){
                        if(item[a]!=null||value[a]!=0){
                      inputStream.println(item[a]+","+value[a]+","+Date[a]);}
                  else{
                      break;
                  }
              }
              inputStream.close();
          } catch (IOException e){
              System.out.println(e.getMessage());
          }
          System.out.println("--RECORD HAS BEEN SAVED!--");
          System.out.println("----------YOUR NEW REOCORDS----------");
        accessIncomeRecords();
        IncomeModule();
      }
  
    //prompt the user to delete the income
    public void deleteIncome(){
       accessIncomeRecords();
       //let the user choose which to delete [int]   
           //put the item ,value and date into array 
             double sum=0;
             int L=checkIncomeDatabaseLines();
             String [] store = new String [6];
           
                try{
                    Scanner inputStream = new Scanner(new FileInputStream (username+"_income.txt"));
                    int counter2 = 0;
                    while(inputStream.hasNextLine()){
                        String check = inputStream.nextLine();
                        int counter1 = 0 ;
                        for(String retrieve : check.split(",")){
                            store[counter1]=retrieve;
                            counter1++;
                        }
                        item[counter2] = store[0];
                        value[counter2] = Double.parseDouble(store[1]);
                        Date[counter2] = store [2];
                        counter2++;
                  }
                  inputStream.close();
            }catch(FileNotFoundException a){
            System.out.println(a.getMessage());
            }
          //Display all the records 
            int k=1;
            
            System.out.println(" \tItem\t\t\t\tValue\t\t\t\tDate[DateMonthYear]");
              for(int j=0 ; j<value.length ; j++){
                 if(item[j]!=null || value[j]!=0){
                 
                    
                    if (item[j].length()<8){
                        System.out.print(k+".\t"+item[j]+"\t\t\t\t"+value[j]+"\t\t\t\t"+Date[j]+"\n");
                    }
                    else if (item[j].length()<16){
                        System.out.print(k+".\t"+item[j]+"\t\t\t"+value[j]+"\t\t\t"+Date[j]+"\n");
                    }
                    else if(item[j].length()<24){
                        System.out.print(k+".\t"+item[j]+"\t\t"+value[j]+"\t\t"+Date[j]+"\n");
                    }
               k++;
                }
            }
            //let the user choose which to delete [int]   
            System.out.println("Which income would you like to delete : [choose the number]");
            int del =input.nextInt();
            item[del-1]="null";
            value[del-1]=0;
            Date[del-1]="null";
            
            //move forward for all the elements after array[del]
            for(int d =del ; d<=value.length ; d++){
                if(d<35){
                item[d-1]=item[d];
                value[d-1]=value[d];
                Date[d-1]=Date[d];
                }
            }  
             //display the current records after deleting the number
                int M=1;
                System.out.println("----------YOUR NEW INCOME RECORDS---------- :");
                System.out.println(" \tItem\t\t\t\tValue\t\t\t\tDate");
                for(int e =0 ; e<item.length ; e++){
                 if(item[e]!=null || value[e]!=0.0){
                    if (item[e].length()<8){
                        System.out.print(M+".\t"+item[e]+ "\t\t\t\t"+value[e]+"\t\t\t\t"+Date[e]+"\n");
                    }
                    else if (item[e].length()<16){
                        System.out.print(M+".\t"+item[e]+"\t\t\t"+value[e]+"\t\t\t"+Date[e]+"\n");
                    }
                    else if(item[e].length()<24){
                        System.out.print(M+".\t"+item[e]+"\t\t"+value[e]+"\t\t"+Date[e]+"\n");
                    }
                    M++;
            
                }
             }
                 try{
                     PrintWriter inputStream = new PrintWriter (new FileOutputStream (username+"_income.txt"));
                       for(int a = 0 ; a<item.length ; a++){
                          if(item[a]!=null||value[a]!=0){
                      inputStream.println(item[a]+","+value[a]+","+Date[a]);}
                  else{
                      break;
                  }
              }
              inputStream.close();
          } catch (IOException e){
              System.out.println(e.getMessage());
          }
          System.out.println("--RECORD HAS BEEN SAVED!--");
    }

    //throw all the method into this method
     public void Income(){
             IncomeFileCheck();
             accessIncomeRecords();
             IncomeModule();
             
             
         }
    
     public void sortIncome(){ 
        int x = checkDatabaseLines();
        String [] store = new String [4];
        this.incRecord = new String [x];
        this.incName = new String [x];
        this.incDate = new String [x];
        this.incValue = new String [x];
        try{
            Scanner inputstream = new Scanner (new FileInputStream (username+"_income.txt"));
            int counter2 = 0;
            while (inputstream.hasNextLine()){
                String check = inputstream.nextLine();
                int counter1 = 0; 
                this.incRecord[counter2] = check;
                for (String ret : check.split(",")){
                    store [counter1] = ret;
                    counter1++;
                };
                this.incName[counter2] = store[0];
                this.incValue[counter2] = store[1];
                this.incDate[counter2] = store[2];
                
                counter2++;
            }
            inputstream.close();
        } catch (FileNotFoundException e){
            System.out.println("File is not found.");
        }
    }
    
    //returns the total income
    public double getTotalIncome (){
        sortIncome();
        double totalIncome = 0;
        for (int i = 0; i < incValue.length; i++){
            double adder = Double.parseDouble(incValue[i]);
            totalIncome += adder;
        }
        return totalIncome;
    }
    
    //returns the array containing all income names
    public String [] getIncomeNameList(){
        sortIncome();
        return incName;
    }
   
    //returning the array containing all income dates
    public String [] getIncomeDateList(){
        sortIncome();
        return incDate;
    }
    
    //returning the array containing all income value
    public String [] getIncomeValueList(){
        sortIncome();
        return incValue;
    }
    
    
}
