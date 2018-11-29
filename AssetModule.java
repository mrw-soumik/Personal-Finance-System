
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

/*
1.This is a module used to manipulate and record all the input Asset and output Asset of the user.
2.Determine whether the username matches with the respective file name . Only open the corresponding file that matches with the user's name.
3.The text file contain the item name of the user's assets and its' value.

<method>assetFileCreate()</method>         void     ---- create username_asset.txt(file that named after the user)
<method>assetFileCheck()</method>          void     ---- check whether the username_asset.txt exists or not else create one
<method>checkAssetDatabaseLines()</method> int      ---- returns the number of lines that exists in username_asset.txt
<method>assetAccessRecord()</method>       void     ---- access to the user's history record , and display it for the user
<method>deleteAccessRecord()</method>      void     ---- prompt the user to choose whether which record to be deleted
<method>assetCalculate()</method>          void     ---- save the history assets value into a array and then calculate the sum of the value and display it
<method>assetAdd()</method>                Double   ---- prompt the user to enter the name of Item and its' 1value followed by storing the information into array <v>Item</v> and <v>value</v>
<method>assetTempCalculate()</method>      void     ---- calculate the temporary total value of the user's asset and display it (not included the history's assets)
<method>assetDelete()</method>             void     ---- prompt the user to delete the asset if the user entered wrongly or something need to be edited
<method>assetMenu()</method>               void     ---- prompt the user choose whether to continue with adding or editing the asset to be stored into the array <v>Item</v> and <v>value</v>
<method>userFileTransfer()</method>        void     ---- transfer the <v>Item</v>array and <v>value</v>array into the username_asset.txt [ From array to file ]
<method>Date()</method>                    String   ---- Set the date of the input
<method>Asset()</method>                   void     ---- collection of all the methods to manipulate the asset of the user

*/

public class AssetModule extends LoginModule {
    
    Scanner assetinput = new Scanner(System.in);
    int i =0;
    private double totalValue;
    private boolean editor =false;
    private String username;
    private String [] edit = new String [3];
    private String [] item = new String[50] ;
    private String [] item1 = new String [50];
    private double [] value = new double [50];
    private String [] Date = new String [50];
    private double [] value2 =new double[50];
    private double [] value1 = new double [50];
    private String date;
    private int order;
    private String [] exRecord = new String [50];
    private String [] exItem;
    private String [] exValue;
    private String [] exDate;
    
    //arrays of different expense fields
    private String [] assRecord;
    private String [] assType;
    private String [] assDate;
    private String [] assValue;
    
    
    LoginModule login = new LoginModule();
    Scanner input = new Scanner(System.in);
    
    //constructor , initialise the username taken from LoginModule
    public AssetModule(String username){
       this.username=username;
    }
   
    //create a file name username_asset.txt
    public void assetFileCreate (){
     try{
        PrintWriter fileCreate = new PrintWriter(new FileOutputStream ( username+"_asset.txt"));
        fileCreate.close();
     } catch (IOException a){
        System.out.println(a.getMessage());
      }
    }
    
    //check whether the file of the username_asset.txt exist , else create one
    public void assetFileCheck(){
        try{
            Scanner inputstream = new Scanner (new FileInputStream (username+"_asset.txt"));
            inputstream.close();
        } catch (FileNotFoundException a){
            System.out.println(a.getMessage());
            assetFileCreate();
        }
    }
    
    //check and return how many lines are there in the username_asset.txt
    public int checkAssetDatabaseLines(){
        int i = 0;
        try{
            Scanner inputstream = new Scanner (new FileInputStream(username+"_asset.txt"));
            while(inputstream.hasNextLine()){
                inputstream.nextLine();
                i++;
            }
        } catch (FileNotFoundException a){
            System.out.println(a.getMessage());
        }
        return i*3;
    }
    
    //Access the history asset records of the user and display it to the user 
    //Included calculating the total value of the the asset
    public void assetAccessRecord(){
        //String[] records=new String [ checkAssetDatabaseLines()];
        
        try{
            Scanner inputStream = new Scanner(new FileInputStream (username+"_asset.txt"));
            int k=1;
            System.out.println(" \tItem\t\t\t\tValue(RM)\t\t\tDate(DayMonthYear)");
           
            while(inputStream.hasNextLine()){
                    //get a line in file username_asset.txt
                    String access = inputStream.nextLine();
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
          
            inputStream.close();
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
    }
   
    //calculate the total value of the user's asset [Asset in the file]
    public void assetCalculate(){
    //put the item ,value and date into array . and then sums up the value of the assets and display it to the user 
            double sum=0;
             int L=checkAssetDatabaseLines();
             String [] store = new String [4];
             item = new String [L+2];   
             value = new double[L+2];
             String[]Date = new String[L+2];
              
                try{
                    Scanner inputStream = new Scanner(new FileInputStream (username+"_asset.txt"));
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
                  for(int b = 0 ; b <= L ; b++){
                      sum=value[b]+sum;}
           
            System.out.println("The total value of your current assets are:RM"+sum);
              inputStream.close();
            }catch(FileNotFoundException a){
            System.out.println(a.getMessage());
                    
           
            }
           
          }
    
   //transfer data in file into array 
   //display the data , choose which data to delete
   //save information into file 
    public void deleteAssetRecord(){
    //put the item ,value and date into array 
             double sum=0;
             int L=checkAssetDatabaseLines();
             String [] store = new String [6];
            // item = new String [L+2];   
             //value = new double[L+2];
             //String[]Date = new String[L+2];
              
                try{
                    Scanner inputStream = new Scanner(new FileInputStream (username+"_asset.txt"));
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
     //display all the records 
            int k=1;
            
            System.out.println(" \tItem\t\t\t\tValue\t\t\t\tDate[DateMonthYear]");
              for(int j=0 ; j<value.length ; j++){
                 if(item[j]!=null || value[j]!=0){
                 
                    if (item[j].length()<8){
                        System.out.print(k+".\t"+item[j]+ "\t\t\t\t"+value[j]+"\t\t\t\t"+Date[j]+"\n");
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
            System.out.println("Which asset do you wish to delete : [choose the number]");
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
                System.out.println("----------YOUR NEW ASSET RECORDS---------- :");
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
                     PrintWriter inputStream = new PrintWriter (new FileOutputStream (username+"_asset.txt"));
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
   
    //add all the value of the asset
    public double [] assetAdd(){
       int decision;
        System.out.println("----------ADD ASSET----------");
        System.out.println( "Please enter the item of your asset : ");
        item1[i]=assetinput.next();
        System.out.println("Please enter the value of your asset[RM]:  ");
        value1[i]=assetinput.nextDouble();
        i++;
     
        assetTempCalculate();
        System.out.println("Do you wish to continue entering your asset ? [press [1] to add ,[2] to delete [0] to back to AssetMenu]");
        decision = assetinput.nextInt();
    
       switch(decision){
           case 1:
           assetAdd();
           break;
               
           case 2:
           assetDelete();
               break;
           
           case 0 :
           assetMenu();
           break ;
               
           default:
           System.out.println("Error found , try again");
           assetMenu();
           break;
           }
        
        return value;
    }   
       
    //calculate the temporary total value of user's asset [excluded the history records of asset]
     public void assetTempCalculate (){
            totalValue=0;
            int k=1;
            System.out.println("TODAY'S DATE: "+date());
           System.out.println(" \tItem\t\t\t\tValue[RM]");
           for(int j=0 ; j<value1.length ; j++){
             if(item1[j]!=null && value1[j]!=0){
                 
                    if (item1[j].length()<8){
                        System.out.print(k+".\t"+item1[j]+ "\t\t\t\t"+value1[j]+"\n");
                    }
                    else if (item1[j].length()<16){
                        System.out.print(k+".\t"+item1[j]+"\t\t\t"+value1[j]+"\n");
                    }
                    else if(item1[j].length()<24){
                        System.out.print(k+".\t"+item1[j]+"\t\t"+value1[j]+"\n");
                    }
               k++; // so the number of diaplay digit add one each loop
             }  
                // add the value in each loop
               totalValue=value1[j]+totalValue;
                 }
            System.out.println("\n");
                System.out.println("The temporary total value of your assets are RM: "+totalValue); 
            }
        
     //Edit data in the array , can only be done after transfering data into username_asset
      public void editAsset(){
    
    //declare array for income_name, income_type, income_amount, income_date 
        assetAccessRecord();
        
        int L = checkAssetDatabaseLines();
        
        String [] asset_name = new String[L]; 
        
        double [] asset_value = new double[L];
        
        String [] asset_date = new String [L];
        
        double sum=0;
             
        String [] store = new String [6];
        
         String s = date();
        
        
        try{
                    
            Scanner inputStream = new Scanner(new FileInputStream (username+"_asset.txt"));
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
            System.out.println("Which asset would you like to edit : [choose the number]");
            int del =input.nextInt();
            
            item[del-1]="null";
            
            value[del-1]=0;
            
            Date[del-1]="null";
            
            System.out.println("Please enter the new Asset Name : ");
            
            String asset_name_edit= input.next();
            
            System.out.println("Please enter the new Asset Amount : ");
            
            double asset_amount_edit = input.nextDouble();
            
            System.out.println("Today's Date : "+s);
            
               //replace the deleted element with new data provided
                item[del-1]=asset_name_edit;
                value[del-1]=asset_amount_edit;
                Date[del-1]=s;
             
                //transfer information into file
             try{
                     PrintWriter inputStream = new PrintWriter (new FileOutputStream (username+"_asset.txt"));
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
        assetAccessRecord();
        assetMenu();
      }
   
      //Prompt the user to delete unwanted asset
    public void assetDelete(){
            //display all the records 
            int k=1;
            System.out.println("DATE: "+date());
           System.out.println(" \tItem\t\t\t\tValue");
           for(int j=0 ; j<value1.length ; j++){
             if(item1[j]!=null || value1[j]!=0){
                 
                    if (item1[j].length()<8){
                        System.out.print(k+".\t"+item1[j]+ "\t\t\t\t"+value1[j]+"\n");
                    }
                    else if (item1[j].length()<16){
                        System.out.print(k+".\t"+item1[j]+"\t\t\t"+value1[j]+"\n");
                    }
                    else if(item1[j].length()<24){
                        System.out.print(k+".\t"+item1[j]+"\t\t"+value1[j]+"\n");
                    }
               k++;
                }
            }
            //let the user choose which to delete [int]   
            System.out.println("Which asset do you wish to delete : [choose the number]");
            int del =input.nextInt();
            item1[del-1]="null";
            value1[del-1]=0;
            
            //move forward for all the elements after array[del]
            for(int d =del ; d<=value1.length ; d++){
                if(d<35){
                item1[d-1]=item1[d];
                value1[d-1]=value1[d];
             }
            }  
                //display the current records after deleting the number
                int M=1;
                System.out.println("Date: "+date());
                System.out.println("Your new assets records :");
                System.out.println(" \tItem\t\t\t\tValue");
                for(int e =0 ; e<item1.length ; e++){
                 if(item1[e]!=null || value1[e]!=0.0){
                    if (item1[e].length()<8){
                        System.out.print(M+".\t"+item1[e]+ "\t\t\t\t"+value1[e]+"\n");
                    }
                    else if (item1[e].length()<16){
                        System.out.print(M+".\t"+item1[e]+"\t\t\t"+value1[e]+"\n");
                    }
                    else if(item1[e].length()<24){
                        System.out.print(M+".\t"+item1[e]+"\t\t"+value1[e]+"\n");
                    }
                    M++;
            
                 }
        }
        
              
            assetMenu();
        }
       
    //menu , to ease the user to choose the steps he wants
    public void assetMenu(){
           System.out.println("----------MENU----------");
           System.out.println("Please choose the number that you would like to continue: \n[1] to add\n[2] to edit[Save records before editing] \n[3] to delete \n[0] to save your records] ");
           int enter=input.nextInt();
           switch(enter){
               case 1 :
                   assetAdd();
                   break;
               case 2 :
                   editAsset();
                   break;
               case 3:
                   assetDelete();
                   break;
               case 0:
                   userFileTransfer();
                   break; 
               default:
                   System.out.println("Error found , Please enter again");
                   assetMenu();
                   break;
       }
    }
     
    //transfer all the data from array into username_asset.txt
    public void userFileTransfer(){
          try{
              PrintWriter inputStream = new PrintWriter (new FileOutputStream (username+"_asset.txt",true));
              for(int a = 0 ; a<item1.length ; a++){
                  if(item1[a]!=null||value1[a]!=0){
              inputStream.println(item1[a]+","+value1[a]+","+date());}
                  else{
                      break;
                  }
              }
              inputStream.close();
          } catch (IOException e){
              System.out.println(e.getMessage());
          }
          System.out.println("----------RECORD HAS BEEN SAVED----------");
          assetAccessRecord();
          assetCalculate();
    
          
      }
    
    //get the date by date method and save it in the file
    public String date(){
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("d/M/y");
        date = ft.format(dNow);
        return date;
    }
     
    //collecting all the methods used to store the asset of the user
    //this methos will only go throught once , that is after the access Asset method . After that , assettMenu<method> will take over the step
    public void Asset(){
      assetFileCheck();
          assetAccessRecord();
          assetCalculate();
          System.out.println("Do you wish to delete anything from the record? Press[1]to delete, Press[2] to back to asset menu");
          int dec=input.nextInt();
          switch(dec){
              case 1:
                deleteAssetRecord();
                assetMenu();
                break;
              case 2 :
                assetMenu();
                break;
          }
      }

   //dividing the expenses input by the user into different fields of arrays 
    //each record are divided into different fields but are assigned with the same index
    public void sortAsset(){ 
        int x = checkDatabaseLines();
        String [] store = new String [4];
        this.assRecord = new String [x];
        this.assType = new String [x];
        this.assValue = new String [x];
        this.assDate = new String [x];
       try{
                    
            Scanner inputStream = new Scanner(new FileInputStream (username+"_asset.txt"));
            int counter2 = 0;
              while(inputStream.hasNextLine()){
              String check = inputStream.nextLine();
               int counter1 = 0 ;
                   for(String retrieve : check.split(",")){
                   store[counter1]=retrieve;
                   counter1++;
           }
                        assType[counter2] = store[0];
                        assValue[counter2] = store[1];
                        assDate[counter2] = store [2];
                        counter2++;
                  }
                  inputStream.close();
            }catch(FileNotFoundException a){
            System.out.println(a.getMessage());
            }
    }
    
    
    //returns the total asset 
    public double getTotalAsset (){
        sortAsset();
        double totalAsset = 0;
        for (int i = 0; i < assValue.length; i++){
            double adder = Double.parseDouble(assValue[i]);
            totalAsset += adder;
        }
        return totalAsset;
    }
    
    //returns the array containing all asset names
    public String [] getAssetNameList(){
        sortAsset();
        return assType;
    }
    
    //returns the array containing all asset values
    public String [] getAssetValueList(){
        sortAsset();
        return assValue;
}
    
    //returns the array containing all asset dates
    public String [] getAssetDateList(){
        sortAsset();
        return assDate;
  
    }
    
   
        } 
        
 




