
package personalfinancesystem;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.*;

/*
This is a module used by user to manipulate their expenses[add, delete , edit].All their expenses will be saved in a file username_expense.txt

constructor
<constructor>ExpensesModule(String username)</constructor> ---- initialise the value of username taken from <class>LoginModule</class>
<method>expenseFileCreate()</method>         void          ---- create username_expense.txt(file that named after the user)
<method>expenseFileCheck()</method>          void          ---- check whether the username_expense.txt exists or not else create one
<method>checkExpenseDatabaseLines()</method> int           ---- returns the number of lines that exists in username_expense.txt
<method>expenseAccessRecord()</method>       void          ---- access to the user's history record , and display it for the user
<method>tempExpenseMenu()</method>           void          ---- This methos will only go throught once , that is after the access Asset method . After that , assettMenu<method> will take over the step
public void tempDeleteExpenseRecord()        void          ---- Access the history expense records of the user and display it to the user
<method>expenseCalculate()</method>          void          ---- save the history expense value into a array and then calculate the sum of the value and display it
<method>setExpenseType()</method>            void          ---- to ease the user to choose the type of expenses and more systematic when display it out
<method>expenseAdd()</method>                Double        ---- prompt the user to enter the name of Item and its' value followed by storing the information into array <v>Item</v> and <v>value</v>
<method>expenseTempCalculate()</method>      void          ---- calculate the temporary total value of the user's expense and display it (not included the history's assets)
<method>editExpense()</method>               void          ---- prompt the user to edit the information in array
<method>expenseDelete()</method>             void          ---- prompt the user to delete the asset if the user entered wrongly or something need to be edited
<method>expenseMenu()</method>               void          ---- prompt the user choose whether to continue with adding or editing the expense to be stored into the array <v>Item</v> and <v>value</v>
<method>userFileTransfer()</method>          void          ---- transfer the <v>Item</v>array and <v>value</v>array <v>date</v> into the username_expense.txt [ From array to file ]
<method>Expense()</method>                   void          ---- collection of all the methods to manipulate the expense of the user
<method>Date()</method>                      String        ---- Set the date of the input

*/
public class ExpenseModule {
    int i=0 ; // to be used in add method
    private String username;
    private String expense;
    private String expenseType;
    private double value;
    private String[]type= new String [50];
    private String [] name = new String [50];
    private double [] cost = new double [50] ;
    private String [] date = new String [50];
    private String [] type1= new String [50];
    private String [] name1 = new String [50];
    private double [] cost1 = new double [50] ;
    private String [] date1 = new String [50];
    private String [] expenseType1 = new String [50];
    private double totalValue ;
    private GregorianCalendar Date = new GregorianCalendar();
    //declare the array of different type of expense fields (just for display purpose)
       // More types under all the big types (which will be used later)
    private String [] personal;
       private String Pmedical = "Medical";
       private String Pinsurance = "Life Insurance";
       private String Phealth = "Health Supplement";
       private String Phouse = "House items";
       private String Pclothing = "Clothing";
       private String Pmember = "Member for Clubs";
       private String Pgadget = "Gadget";
       private String Pfood = "Foods and Drinks";
       private String Pothers = "Other Personal Expenses";
    private String []transportation;
       private String Tpetrol = "Petrol";
       private String Tinsu = "Car Insurance";
       private String Tservice = "Car Service and Maintenance";
       private String Tcar = "New Car";
       private String Tothers = "Other Transportation Expenses";
    private String [] houseHold;
       private String Hutility = "Utilities Bills";
       private String Hinsu = "House Insurance";
       private String Hservice = "House Servive and Maintenance";
       private String Hothers = "Other Households Expenses";
    private String [] debtReductiom;
       private String Dhouse = "House Debt";
       private String Dcar = "Car Loan";
       private String Dedu = "Education Loan";
       private String Dcard = "Credit Card Loan";
       private String Dothers = "Other Loans";
       private String others = "Others";
   
//arrays of different expense fields
    private String [] exRecord;
    private String [] exName;
    private String [] exType;
    private String [] exDate;
    private String [] exValue;
    
    
LoginModule login = new LoginModule();
    Scanner input = new Scanner(System.in);
    
    //constructor , initialise the username taken from LoginModule
    public ExpenseModule(String username){
       this.username=username;
    }
   
    //create a file name username_expense.txt
    public void expenseFileCreate (){
     try{
        PrintWriter fileCreate = new PrintWriter(new FileOutputStream ( username +"_expense.txt"));
        fileCreate.close();
     } catch (IOException a){
        System.out.println(a.getMessage());
      }
    }
    
    //check whether the file of the username_expense.txt exist , else create one
    public void expenseFileCheck(){
        try{
            Scanner inputExpense = new Scanner (new FileInputStream (username+"_expense.txt"));
            inputExpense.close();
        } catch (FileNotFoundException a){
            System.out.println(a.getMessage());
            expenseFileCreate();
        }
    }
    
    //check and return how many lines are there in the username_expense.txt
    public int checkExpenseDatabaseLines(){
        int i = 0;
        try{
            Scanner inputExpense = new Scanner (new FileInputStream(username+"_expense.txt"));
            while(inputExpense.hasNextLine()){
                inputExpense.nextLine();
                i++;
            }
        } catch (FileNotFoundException a){
            System.out.println(a.getMessage());
        }
        return i*3;
    }
    
    //Access the history expense records of the user and display it to the user
    //Included calculating the total value of the expense
    public void expenseAccessRecord(){
        try{
            Scanner inputExpense = new Scanner (new FileInputStream(username+"_expense.txt"));
             int k=1;
            System.out.println(" \tExpense Type\t\t\tExpense Name\t\t\tValue(RM)\t\tDate(DayMonthYear)");
           
            while(inputExpense.hasNextLine()){
                    //get a line in file username_expense.txt
                    String access = inputExpense.nextLine();
                     System.out.print(k+".\t");
                    //split the file
                   for( String ret : access.split(",")){
                    String record = ret;
                    if(record.length()<8){
                        System.out.print(record+"\t\t\t\t");
                    }
                    else if (record.length()<16){
                        System.out.print(record+"\t\t\t");
                    }
                    else if(record.length()<24){
                        System.out.print(record+"\t\t");
                    }
                   }
                    System.out.println("");
                    k++;
                 } 
            expenseCalculate();
            inputExpense.close();
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
          }
    
    //collecting all the methods used to store the asset of the user
    //this methos will only go throught once , that is after the access Asset method . After that , assettMenu<method> will take over the step
    public void tempExpenseMenu(){
      expenseFileCheck();
          expenseAccessRecord();
          expenseCalculate();
          System.out.println("Do you wish to delete anything from the record? Press[1]to delete, Press[2] to back to Expense menu");
          int dec=input.nextInt();
          switch(dec){
              case 1:
                tempDeleteExpenseRecord();
                expenseMenu();
                break;
              case 2 :
                expenseMenu();
                break;
          }
      }
    
    //transfer data in file into array 
   //display the data , choose which data to delete
   //save information into file 
    public void tempDeleteExpenseRecord(){
    //put the item ,value and date into array 
             double sum=0;
             int L=checkExpenseDatabaseLines();
             String [] store = new String [8];
            
              
                try{
                    Scanner inputStream = new Scanner(new FileInputStream (username+"_expense.txt"));
                    int counter2 = 0;
                    while(inputStream.hasNextLine()){
                        String check = inputStream.nextLine();
                        int counter1 = 0 ;
                        for(String retrieve : check.split(",")){
                            store[counter1]=retrieve;
                            counter1++;
                        }
                        type1[counter2] = store[0];
                        name1[counter2] = store[1];
                        cost1[counter2] = Double.parseDouble(store[2]);
                        date1[counter2] = store [3];
                        counter2++;
                  }
                  inputStream.close();
            }catch(FileNotFoundException a){
            System.out.println(a.getMessage());
            }
     //display all the records 
            int k=1;
            
            System.out.println(" \tType of expense\t\t\tName of Expense\t\t\tCost(RM)+\t\t\tDate[DateMonthYear]");
             for(int j=0 ; j<type1.length ; j++){
               if(name1[j]!=null || cost1[j]!=0){
                 
                    if (type1[j].length()<8){
                        System.out.print(k+".\t"+type1[j]+"\t\t\t\t"+name1[j]+ "\t\t\t\t"+cost1[j]+"\t\t\t\t"+date1[j]+"\n");
                    }
                    else if (type1[j].length()<16){
                        System.out.print(k+".\t"+type1[j]+"\t\t\t"+name1[j]+"\t\t\t"+cost1[j]+"\t\t\t"+date1[j]+"\n");
                    }
                    else if(type1[j].length()<24){
                        System.out.print(k+".\t"+type1[j]+"\t\t"+name1[j]+"\t\t"+cost1[j]+"\t\t"+date1[j]+"\n");
                    }
               k++;
                }
            }
            
            //let the user choose which to delete [int]   
            System.out.println("Which expense would you like to delete : [choose the number]");
            int del =input.nextInt();
            type1[del-1]="null";
            name1[del-1]="null";
            cost1[del-1]=0;
            date1[del-1]="null";
            
            //move forward for all the elements after array[del]
            for(int d=del ; d<=type1.length ; d++){
                if(d<35){
                type1[d-1]=type1[d];
                name1[d-1]=name1[d];
                cost1[d-1]=cost1[d];
                date1[d-1]=date1[d];
                }
            }  
             //display the current records after deleting the number
                int M=1;
                System.out.println("----------YOUR NEW ASSET RECORDS---------- :");
                System.out.println(" \tType\t\t\tName\t\t\t\tValue(RM)\t\t\t\tDate[DateMonthYear]");
                for(int e =0 ; e<type1.length ; e++){
                 if(type1[e]!=null || cost1[e]!=0.0){
                       if (type1[e].length()<=8){
                        System.out.print(M+".\t"+type1[e]+"\t\t\t\t"+name1[e]+ "\t\t\t\t"+cost1[e]+"\t\t\t\t"+date1[e]+"\n");
                    }
                    else if (type1[e].length()<=16){
                        System.out.print(M+".\t"+type1[e]+"\t\t\t"+name1[e]+"\t\t\t"+cost1[e]+"\t\t\t"+date1[e]+"\n");
                    }
                    else if(type1[e].length()<=24){
                        System.out.print(M+".\t"+type1[e]+"\t\t"+name1[e]+"\t\t"+cost1[e]+"\t\t"+date1[e]+"\n");
                    }
                 }
                    M++;
              }
                //transfer new data into file
                 try{
                     PrintWriter inputStream = new PrintWriter (new FileOutputStream (username+"_expense.txt"));
                       for(int a = 0 ; a<type1.length ; a++){
                          if(type1[a]!=null||cost1[a]!=0){
                      inputStream.println(type1[a]+","+name1[a]+","+cost1[a]+","+date1[a]);}
                  else{
                      break;
                  }
              }
              inputStream.close();
          } catch (IOException e){
              System.out.println(e.getMessage());
          }
          System.out.println("--RECORD HAS BEEN SAVED!--");
          System.out.println("-----YOUR NEW RECORD-----");
          expenseAccessRecord();
    }
    
    //calculate the total value of the user's expense[Expense in the file]
    public void expenseCalculate(){
       //put the item type , item, value and date into array and then sums up the value of the expense and display it to the user
       double sum=0;
       int L = checkExpenseDatabaseLines();
       String [] store = new String [4];
       String [] expenseType = new String[L+2];
       String [] expenseName = new String [L+2];
       double [] cost = new double [L+2];
       String [] date = new String [L+2];
       
       try {
           Scanner inputExpense = new Scanner(new FileInputStream(username+"_expense.txt"));
           int counter2 =0 ;
           while(inputExpense.hasNextLine()){
               String check = inputExpense.nextLine();
               int counter1 = 0 ;
               for(String ret : check.split(",")){
                   store[counter1]=ret;
                   counter1++;
               }
               expenseType[counter2] = store[0];
               expenseName[counter2] = store[1];
               cost[counter2] = Double.parseDouble(store[2]);
               date[counter2] = store[2];
               counter2++;
           }
           for(int b = 0 ; b <= L ; b++){
            sum=cost[b]+sum;}
         
           System.out.println("The total value of your current expense are:RM"+sum);
              inputExpense.close();
            }catch(FileNotFoundException a){
            System.out.println(a.getMessage());
            }         
       }
       
    //used to set the type of expense by user , all the types are final hence user cannot alter it
    public String setExpenseType(){
         int decision;
           //prompt the user t
           System.out.print("Please choose the type of expense : \n1.Personal\n2.Transportation\n3.Household\n4.Debt Reduction\n5.others\nSelection: ");
           int type = input.nextInt();
           switch(type){
               case 1 :
                   System.out.print("Please choose type of personal expenses:\n1.Medical\n2.Insurance\n3.Health supplement\n4.House\n5.Clothing\n6.Membership for clubs\n7.Gadgets\n8.Foods and Drinks\n9.Others\nSelection:");
                   int personal = input.nextInt();
                   switch(personal){
                       case 1 :
                           expenseType = Pmedical;
                           break;
                       case 2 :
                           expenseType = Pinsurance ;
                           break;
                       case 3 :
                           expenseType = Phealth ;
                           break;
                       case 4 :
                           expenseType = Phouse ;
                           break;
                       case 5 :
                           expenseType = Pclothing ;
                           break;
                       case 6 :
                           expenseType = Pmember;
                           break;
                       case 7 :
                           expenseType = Pgadget;
                           break;
                       case 8 :
                           expenseType = Pfood;
                       case 9 :
                           expenseType = Pothers;
                           break;
                       default : 
                           System.out.println("Input number does not exist");
                           break ;
                   }
                    break;
                 case 2:
                    System.out.print("Please choose type of transportation expenses:\n1.Petrol\n2.Insurance\n3.Service and maintenance\n4.New Car\n5.Others\nSelection:");
                    int transport = input.nextInt();
                    switch(transport){
                       case 1 : 
                            expenseType = Tpetrol;
                           break;
                       case 2 :
                            expenseType = Tinsu;
                           break;
                       case 3 :
                           expenseType = Tservice;
                           break;
                       case 4 :
                           expenseType = Tcar;
                           break;
                       case 5 :
                           expenseType = Tothers ;
                           break;
                       default :
                           System.out.println("Input number does not exist");
                           break;
                      }
                      break;
                 case 3:
                     System.out.print("Please choose type of Household expenses:\n1.Utilities Bills\n2.House Insurance\n3.Service and maintenance\n4.Others\nSelection:");
                     int house = input.nextInt();
                     switch(house){
                       case 1 : 
                           expenseType = Hutility ;
                           break;
                       case 2 :
                           expenseType = Hinsu ;
                           break;
                       case 3 :
                           expenseType = Hservice ;
                           break;
                       case 4 :
                           expenseType = Hothers;
                           break;
                     }
                    break;
                case 4 :        
                    System.out.print("Please choose type of Debt Reduction expenses:\n1.House Loan\n2.Car Loan\n3.Education Loan\n4.Credit Card Loan\n5.Others\nSelection:");
                    int debt = input.nextInt();
                    switch(debt){
                       case 1 :
                           expenseType = Dhouse;
                           break;
                       case 2 :
                           expenseType = Dcar;
                           break;
                       case 3 :
                           expenseType = Dedu;
                           break;
                       case 4 :
                           expenseType = Dcard;
                           break;
                       case 5 :
                           expenseType = Dothers;
                           break;
                    }
                   break;
                case 5 :
                    expenseType = others;
                     break;
                default :
                    System.out.println("Type is not found");
                    break;
           }
           return expenseType;
       }
           
    //user can now add new expences
    public double [] expenseAdd(){
                
                //calling method to set expense type
                type[i]=setExpenseType();
                //prompt the user to name the expense 
                System.out.print("Please enter the expense Name: ");
                input.nextLine();
                name[i] = input.next();
                System.out.print("Please enter the Cost of the expense: RM");
                cost[i] = input.nextDouble();
                i++;
                
                expenseTempCalculate();
                System.out.println("Do you wish to continue entering your asset ? \n[1] to add \n[2] to delete \n[0] to back to AssetMenu]");
                int decision = input.nextInt();
                test1:
       switch(decision){
           case 1:
           expenseAdd();
           break;
               
           case 2:
           expenseDelete();
           break;
           
           case 0 :
           expenseMenu();
           break ;
               
           default:
               System.out.println("Error found , try again");
           break test1;
           }
        
        return cost;
    }   
    
    //calculate the temporary total value of user's expense [excluded the history records of asset]
    public void expenseTempCalculate (){
            
            totalValue=0;
            int k=1;
            System.out.println("Date: "+date());
            System.out.println(" \tType of expense\t\t\tName of Expense\t\t\tCost(RM)");
              for(int j=0 ; j<type.length ; j++){
                if(name[j]!=null && cost[j]!=0){
                 
                    if (type[j].length()<8){
                        System.out.print(k+".\t"+type[j]+"\t\t\t\t"+name[j]+ "\t\t\t\t"+cost[j]+"\n");
                    }
                    else if (type[j].length()<16){
                        System.out.print(k+".\t"+type[j]+"\t\t\t"+name[j]+"\t\t\t"+cost[j]+"\n");
                    }
                    else if(type[j].length()<24){
                        System.out.print(k+".\t"+type[j]+"\t\t"+name[j]+"\t\t"+cost[j]+"\n");
                    }
                }
                    k++; // so the number of display digit add one each loop
             
                   // add the value in each loop
                   totalValue=cost[j]+totalValue;
              }  
        
            System.out.println("\n");
                System.out.println("The temporary total cost of your expense are RM: "+totalValue); 
            
        }
    
    //prompt the user to edit the information in expense 
    public void editExpense(){
    
    //declare array for income_name, income_type, income_amount, income_date 
        expenseAccessRecord();
        
        int L = checkExpenseDatabaseLines();
        
        String [] expense_type = new String [L];
        
        String [] expense_name = new String[L]; 
        
        double [] expense_value = new double[L];
        
        String [] expense_date = new String [L];
        
        double sum=0;
             
        String [] store = new String [8];
        
         String s = date();
        
        
        try{
                    
            Scanner inputStream = new Scanner(new FileInputStream (username+"_expense.txt"));
          
            int counter2 = 0;
             
            while(inputStream.hasNextLine()){
              String check = inputStream.nextLine();
               int counter1 = 0 ;
                   for(String retrieve : check.split(",")){
                   store[counter1]=retrieve;
                   counter1++;
           }
                        type[counter2] = store[0];
                        name[counter2] = store[1];
                        cost[counter2] = Double.parseDouble(store[2]);
                        date[counter2] = store [3];
                        counter2++;
                  }
            
                  inputStream.close();
            }catch(FileNotFoundException a){
            System.out.println(a.getMessage());
            }
        
         //let the user choose which to delete [int]   
            System.out.println("Which expense would you like to edit : [choose the number]");
            int del =input.nextInt();
            
            type[del-1]="null";
            
            name[del-1]="null";
            
            cost[del-1]=0;
            
            date[del-1]="null";
            
            System.out.println("Please enter the new expense Type : ");
            String expense_type_edit= setExpenseType();
            
            System.out.println("Please enter the new expense Name : ");
            String asset_name_edit= input.next();
            
            System.out.println("Please enter the new Asset Amount : ");
            double asset_amount_edit = input.nextDouble();
            
            System.out.println("Today's Date : "+s);
            
               //replace the deleted element with new data provided
                type[del-1]=expense_type_edit;
                name[del-1]=asset_name_edit;
                cost[del-1]=asset_amount_edit;
                date[del-1]=s;
             
                //transfer information into file
             try{
                     PrintWriter inputStream = new PrintWriter (new FileOutputStream (username+"_expense.txt"));
                       for(int a = 0 ; a<type.length ; a++){
                           
                        if(type[a]!=null||cost[a]!=0){
                            
                      inputStream.println(type[a]+","+name[a]+","+cost[a]+","+date[a]);}
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
        expenseAccessRecord();
        expenseMenu();
      }
   
    //Prompt the user to delete unwanted expense
    public void expenseDelete(){
             //calling method to set expense type
            //display all the records 
            int k=1;
            System.out.println("Date: "+date());
            System.out.println(" \tType of expense\t\t\tName of Expense\t\t\tCost(RM)");
             for(int j=0 ; j<type.length ; j++){
               if(name[j]!=null || cost[j]!=0){
                 
                    if (type[j].length()<8){
                        System.out.print(k+".\t"+type[j]+"\t\t\t\t\t\t"+name[j]+ "\t\t\t\t\t\t"+cost[j]+"\n");
                    }
                    else if (type[j].length()<16){
                        System.out.print(k+".\t"+type[j]+"\t\t\t"+name[j]+"\t\t\t"+cost[j]+"\n");
                    }
                    else if(type[j].length()<24){
                        System.out.print(k+".\t"+type[j]+"\t\t"+name[j]+"\t\t"+cost[j]+"\n");
                    }
               k++;
                }
            }
            //let the user choose which to delete [int]   
            System.out.println("Which expense do you wish to delete : [choose the number]");
            int del =input.nextInt();
            //calling method to set expense type
            type[del-1]="null";
            name[del-1]="null";
            cost[del-1]=0;
            
            //move forward for all the elements after array[del]
            for(int d =del ; d<=cost.length ; d++){
                if(d<35){
                type[d-1]=type[d];
                name[d-1]=name[d];
                cost[d-1]=cost[d];
             }
            }  
                //display the current records after deleting the number
                int M=1;
                System.out.println("Date: "+date());
                System.out.println("Your new expense records :");
                System.out.println(" \tType of expense\t\t\tName of Expense\t\t\tCost(RM)");
                for(int e =0 ; e<name.length ; e++){
                 if(name[e]!=null || cost[e]!=0.0){
                    if (type[e].length()<8){
                        System.out.print(M+".\t"+type[e]+"\t\t\t\t"+name[e]+ "\t\t\t\t"+cost[e]+"\n");
                    }
                    else if (type[e].length()<16){
                        System.out.print(M+".\t"+type[e]+"\t\t\t"+name[e]+"\t\t\t"+cost[e]+"\n");
                    }
                    else if(type[e].length()<24){
                        System.out.print(M+".\t"+type[e]+"\t\t"+name[e]+"\t\t"+cost[e]+"\n");
                    }
                    M++;
            
                 }
            }
        
              
            expenseMenu();
            }
     
    //menu , to ease the user to choose the steps he wants
    public void expenseMenu(){
           System.out.println("Do you wish to edit your record? \n[1] to add \n[2] to delete \n[3] to edit[save automatically after editing] \n[0] to save your records ");
           int enter=input.nextInt();
           switch(enter){
               case 1 :
                   expenseAdd();
                   break;
               case 2:
                   expenseDelete();
                   break;
               case 3 :
                   editExpense();
                   break;
               case 0:
                   userFileTransfer();
                   break; 
               default:
                   expenseMenu();
                   break;
       }
        }
    
    //transfer all the data from array into username_expense.txt
    public void userFileTransfer(){
          try{
              PrintWriter inputStream = new PrintWriter (new FileOutputStream (username+"_expense.txt",true));
              for(int a = 0 ; a<name.length ; a++){
                  if(name[a]!=null||cost[a]!=0){
                  
              inputStream.println(type[a]+","+name[a]+","+cost[a]+","+date());}
                  else{
                      break;
                  }
              }
              inputStream.close();
          } catch (IOException e){
              System.out.println(e.getMessage());
          }
          System.out.println("Record has been saved !");
          expenseAccessRecord();
        
      }
      
    //collecting all the methods used to store the asset of the user
    public void Expense(){
         expenseFileCheck();
         tempExpenseMenu();
       }
      
    //get the date by date.gregorion method and save it in the file
    public String date(){
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("d/M/y");
        String date = ft.format(dNow);
        return date;
    }
    
    //dividing the expenses input by the user into different fields of arrays 
    //each record are divided into different fields but are assigned with the same index
    public void sortExpense (){ 
        int x = checkExpenseDatabaseLines();
        String [] store = new String [4];
        this.exRecord = new String [x];
        this.exName = new String [x];
        this.exType = new String [x];
        this.exDate = new String [x];
        this.exValue = new String [x];
        try{
            Scanner inputstream = new Scanner (new FileInputStream (username+"_expense.txt"));
            int counter2 = 0;
            while (inputstream.hasNextLine()){
                String check = inputstream.nextLine();
                int counter1 = 0; 
                exRecord [counter2] = check;
                for (String ret : check.split(",")){
                    store [counter1] = ret;
                    counter1++;
                }
                this.exType[counter2] = store[0];
                this.exName[counter2] = store[1];
                this.exValue[counter2] = store[2];
                this.exDate[counter2] = store[3];
                counter2++;
            }
        } catch (FileNotFoundException e){
            System.out.println("File is not found.");
        }
    }
    
 
    //returns the total expense 
    public double getTotalExpense (){
        sortExpense();
        double totalExpense = 0;
        for (int i = 0; i < exValue.length; i++){
            double adder = Double.parseDouble(exValue[i]);
            totalExpense += adder;
        }
        return totalExpense;
    }
    
    //returns the array containing all expenses name
    public String [] getExpenseNameList(){
        sortExpense();
        return exName;
    }
    
    //returns the array containing all expenses type
    public String [] getExpenseTypeList(){
        sortExpense();
        return exType;
    }
    
    //returns the array containing all expenses date
    public String [] getExpenseDateList(){
        sortExpense();
        return exDate;
    }
    
    //returns the array containing all expenses value
    public String [] getExpenseValueList(){
        sortExpense();
        return exValue;
    }

    
}


 

       



