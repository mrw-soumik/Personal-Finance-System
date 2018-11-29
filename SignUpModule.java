
package personalfinancesystem;

import static java.awt.PageAttributes.MediaType.D;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/*
1.This is a module that handles the signing up of new accounts where each username that exists has no repition as the username is the most important key used to bind all the user data.
2.It will prompt user to enter their desired username where it will be checked to make sure username is not used in prior account sign ups.
3.Password confirmation will be performed to ensure new users are entering it correctly . When both conditions are true = account will be created .

constructor is <class>SignUpModule</class> are:
<constructor>SignUpModule</constructor> ----    empty constructor used to create an object
Among the modules that exists in <class>SignUpModule</class> are:
    <method>databaseCheck()</method>            void    ----    check whether userdatabase.txt exists or not else call <method>databaseCreate()</method> to create one
    <method>databaseCreate()</method>           void    ----    create userdatabase.txt
    <method>checkDatabaseLines()</method>       int     ----    returns the number of lines that exists in userdatabase.txt
    <method>getUsername()</method>              void    ----    prompt the user to enter their desired username
    <method>checkExistingUsername()</method>    void    ----    check whether the entered username is used before else prompt user to reenter another username
    <method>checkPasswordInput()</method>       void    ----    prompt user to enter their password and confirm it before their account is created
    <method>checkSignUpStatus()</method>        boolean ----    returns whether the username and password entered are legit and unique
    <method>addUseAccount()</method>            void    ----    adds the new username and password to D:/NameList.txt
    <method>signup()</method>                   void    ----    collection of methods used to create a new user account 



*/

public class SignUpModule extends LoginModule {
    private String newUserName;
    private String newPassword;
    private boolean newUsernameAuthentication = true ;
    private boolean newPasswordAuthentication = false ;
    private boolean newAccountAuthentication = false ;
    Scanner input = new Scanner(System.in);
    
    //empty constructor
    public SignUpModule(){
    
    }
    
    //check whether the database file exists else create one 
    public void databaseCheck(){
        try{
            Scanner inputstream = new Scanner (new FileInputStream ("NameList.txt"));
            inputstream.close();
        } catch (FileNotFoundException a){
            System.out.println(a.getMessage());
            databaseCreate();
        }
    }
    
    //create the database
    public void databaseCreate(){
        try{
            PrintWriter createDatabase = new PrintWriter (new FileOutputStream("NameList.txt"));
            createDatabase.close();
        }catch (IOException a){
            System.out.println(a.getMessage());
        }
    }
    
    //check and return how many lines there is in the NameList.txt
    public int checkDatabaseLines(){
        int i = 0;
        try{
            Scanner inputstream = new Scanner (new FileInputStream("NameList.txt"));
            while(inputstream.hasNextLine()){
                inputstream.nextLine();
                i++;
            }
        } catch (FileNotFoundException a){
            System.out.println(a.getMessage());
        }
        return i;
    }
    
    //prompt user to input their username for the new account
    private void getUserName(){
        System.out.println("Please enter your name for your new Account: ");
        newUserName=input.nextLine();        
       }
            
    //check existing username in the database to ensure there is no overlapping else enter a new name
    public void checkExistingUserName(){
        try{
            Scanner inputstream = new Scanner (new FileInputStream("NameList.txt"));
            int i = 0;
            String [] usernameCheck = new String [checkDatabaseLines()];
            while(inputstream.hasNextLine()){
                //get a line in the database
                String check = inputstream.nextLine();
                //split the file
                for(String ret : check.split(",")){
                    usernameCheck[i]=ret;
                    if(newUserName.equals(usernameCheck[i])){
                        System.out.println("Username has been used . Please try again with another username: ");
                        newUserName = input.nextLine();
                        checkExistingUserName();
                    }
                    break;
                    }
                }
            } catch (IOException a){
                System.out.println(a.getMessage());
            }
    }
    
    //prompt user to enter and recomfirm their password before accoount is created
    public void checkPasswordInput(){
        while(!newPasswordAuthentication){
            System.out.println("Please enter your password : ");
            String password1 = input.nextLine();
            System.out.println("Recomfirm your password : ");
            String password2 = input.nextLine();
            if(password1.equals(password2)){
               newPassword = password1;
               newPasswordAuthentication = true;
               break;
            }         
          }
        }
    
    //return whether the new username and password are created succesfully
     public boolean checkSignUpStatus(){
       if(newUsernameAuthentication && newPasswordAuthentication){
       newAccountAuthentication = true ;
    }
     return newAccountAuthentication;
   }
     
     //adds the new username and password into NameList.txt
    public void addUserAccount(){
        try{
            PrintWriter inputstream = new PrintWriter (new FileOutputStream ("NameList.txt",true));
            inputstream.println(newUserName+","+newPassword);
            inputstream.close();
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
    
     //collecting all the methods used to create new account
     public void signUp(){
         databaseCheck();
         System.out.println("Accout Sign Up : ");
         getUserName();
         checkExistingUserName();
         checkPasswordInput();
         if(checkSignUpStatus()){
             addUserAccount();
             System.out.println("Your account has been created Successfully");
             System.out.println("You may enjoy and Log into your account now. ");
         }
     }
}

 
    


