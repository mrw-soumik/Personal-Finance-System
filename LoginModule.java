

package personalfinancesystem;

/* 
 Among the modules .This is a module used to determine whether the username and password entered by user matches with the ones which resides in the D:/NameList.txt . Only the corresponding usernames with its respective password can gain access to further data linked with that account .
 exists in <class>LogInModule</class> are:
    <method>userLogIn()</method>            void     ----    prompt user to enter their username and password then update instance variable <v>username</v> and <v>password</>
    <method>databaseCheck()</method>        void     ----    checks whether the userdatabase.txt exists or not else call the <method>databaseCreate()</method> to create one
    <method>databaseCreate</method>         void     ----    creates userdatabase.txt
    <method>checkDatabaseLines()</method>   int      ----    returns the number of lines that exists in D:/NameList.txt
    <method>checkUserName</method>          void     ----    checks whether the username entered by user exists in D:/NameList.txt, if true the username will be authenticated
    <method>checkPassword()</method>        void     ----    checks whether the password entered by user matches its corresponding username, if true the password will be authenticated
    <method>checkLogInStatus()</method>     void     ----    checks whether the username and password have been authenticated. If true, the user login will be authenticated else prompt user to try again  
    <method>loginModule()</method>          boolean  ----    contains all the methods arranged in the desired order. Returns whether the log in is authenticated so that other processes can proceed in the main method
    <method>logOut()</method>               boolean  ----    erases the username and password used to sign into the account. Goes back to the main screen for new user log in or sign up or to close the program
*/

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class LoginModule {
    
    Scanner input = new Scanner(System.in);
    private int passwordLocation =1;
    private String username;
    private String password;
    private boolean logInAuthentication = false;
    private boolean checkNameAuthentication = false;
    private boolean passwordAuthentication = false;
    
    // empty constructor
    public LoginModule(){
        
    }
    
    public LoginModule(String username , String password){
        this.username = username;
        this.password = password;
    }
  
 
    // For user to enter the name and password.
     public void userLogIn(){
        
        System.out.println(" Please enter your Username: ");
        username = input.next();
        System.out.println(" Please enter your password: ");
        password = input.next();
        
    }
     public String getUsername(){
         return username;
         
    }
  
    //Check whether the userdatabase file exists else , create one .
     public void databaseCheck(){
       try{
            Scanner inputstream =  new Scanner (new FileInputStream("NameList.txt"));
            inputstream.close();
            } catch (FileNotFoundException a){
            databaseCreate();
        }
     }
      
    //Create the database
     public void databaseCreate(){
        try{
            PrintWriter createDatabase =  new PrintWriter ( new FileOutputStream("NameList.txt"));
            createDatabase.close();
           } catch (IOException b){
            System.out.println(b.getMessage());
        }
    }
    
     //Check how many lines there is in the NameLsit.txt
     public int checkDatabaseLines(){
        int i=0;
        try{
            Scanner inputstream = new Scanner (new FileInputStream("NameList.txt"));
                while(inputstream.hasNextLine()){
                inputstream.nextLine();
                i++;
     }
            } catch (FileNotFoundException c){
                    System.out.println(c.getMessage());
                    }
            return i;
        }
    
     //check whether the username exists
     public void checkUserName(){
       
        try{
            Scanner inputstream2 = new Scanner (new FileInputStream ("NameList.txt"));
            int i=0;
            //array checkname can be made piblic in cases if other classes were to access it . 
            String [] CheckName = new String [ checkDatabaseLines() ];
            test : 
            while (inputstream2.hasNextLine()){
                //Get a line in the database
                String check = inputstream2.nextLine();
                //Split the file
                for(String ret : check.split(",")){
                    CheckName[i]=ret;
                    if(username.equals(CheckName[i])){
                        checkNameAuthentication = true;
                        break test;
                    }
                    i++;
                    break;
                    }
                //passwordLocation is used to check the username lies in which line so that only that line is checked when matching up the password input by the account owner , further description can be found below
                passwordLocation++;
            }
            inputstream2.close();
        }catch(IOException e){
            System.out.println(e.getMessage());
                }
        //check whether the password matches the username
        //more lines should be added if it consists of multiple users
    }
     public void checkPassword(){
       try{
           Scanner inputstream = new Scanner (new FileInputStream("NameList.txt"));
           int i = 1;
           String passwordcheck;
           while (inputstream.hasNextLine()){
               //get a line in database
               String check = inputstream.nextLine();
               //only perform password checking when reading the line where the authenticated username lies
               //this ensures the account is not logged in in cased when there are different user accounts with the same password
               if(i == passwordLocation){
                   //split the file
                   for(String ret : check.split(",")){
                       passwordcheck = ret;
                        if(password.equals(passwordcheck)){
                           System.out.println("PASSWORD CHECKED SUCCESSFULLY!");
                           passwordAuthentication = true;
                       }
                   }
               }
               i++;
           }
            inputstream.close();
       }catch(IOException e){
            System.out.println(e.getMessage());
          }
        }
       

     //display if the user log in succesfully
     public void checkLogInStatus(){
if(passwordAuthentication && checkNameAuthentication){
   logInAuthentication = true ;
    System.out.println("----------LOG IN SUCCESSFULLY---------- !");
}
else{
    System.out.println("Username or Password incorrect . Please log in again to continue .");
}

//return LogInAuthentication
}

     //combination of all the methods in this class in sequence
     public boolean loginModule(){
    userLogIn();
    checkUserName();
    checkPassword();
    checkLogInStatus();
    return logInAuthentication;
}

      //erases all the credentials before logging out and returning to the main screen
      public boolean logOut(){
    System.out.println("----------LOGGED OUT SUCCESFFULLY!----------");
    username = null;
    password = null;
    checkNameAuthentication = false;
    passwordAuthentication = false;
   
    return true;
 }
}

    

