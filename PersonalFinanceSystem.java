
package personalfinancesystem;
import java.util.Scanner;

//Tester
public class PersonalFinanceSystem {

    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
         int i =0 ;
         
        boolean runProgram = true;
        //Display the menu
        
        while(runProgram){
        
            
            System.out.println(" ----- A PENNY SAVED IS A PENNY EARNED . WELCOME TO JASH PERSONAL FINANCIAL CALCULATOR !-----  ");
        
        //This is to guide user to Log into their account or Sign up as a new user
        System.out.println(" Would you like to Log In to your account or sign up as a new user ? [Press [1] to log in || Press [2] to sign up]");
         LoginModule login = new LoginModule();     //Login Module
        SignUpModule signup = new SignUpModule();  //SignUp Module
         while(true){
         String in = input.nextLine();
            if(in.length()==1){
             i=Integer.parseInt(in);
               if(i<4&&i>0){
                    break;
            }
        }
    }
         switch(i){
            case 1:
                System.out.println("-----------LOGIN----------");
                while(login.loginModule()==false){
                if(login.loginModule()== true){
                break;
                }}
                break;
            case 2:
                System.out.println("----------SIGN UP----------");
                signup.signUp();{}
                break;
            case 3:
                runProgram = false ;
                System.exit(0);
                break;
             }
         
         
           while(true){
                if(pfs(login) == 0){
                break;// display the menu of pfs
                
           }else{
           pfs(login);
               }
            }
         }
    }
        public static int pfs(LoginModule login){
        SignUpModule signup = new SignUpModule();  
        Scanner input = new Scanner(System.in);
          System.out.println("----------MAIN MENU OF JASH PERSONAL FINANCIAL SYSTEM----------");
             System.out.println("Please choose the number which you would like to access : \n[1]asset\n[2]Income\n[3]Expense\n[4]report\n[5]log out");
         int choose = input.nextInt();
         test1:
         
         switch(choose){
            case 1 :
               //AssetModule
               System.out.println("\n");
               System.out.println("NAME:"+login.getUsername().toUpperCase());
               System.out.println("----------ASSET MODULE----------");
               AssetModule asset = new AssetModule(login.getUsername());
               asset.Asset();
               
               return 1;
                
            case 2:
              //IncomeModule
              System.out.println("\n");
              System.out.println("NAME:"+login.getUsername().toUpperCase());
              System.out.println("----------INCOME MODULE----------");
              IncomeModule income = new IncomeModule(login.getUsername());
              income.Income();
              return 1;
                 
            case 3:    
              //expenseModule
              System.out.println("\n");
              System.out.println("NAME:"+login.getUsername().toUpperCase());
              System.out.println("----------EXPENSE MODULE----------");
              ExpenseModule expense = new ExpenseModule(login.getUsername());
              expense.Expense();
              return 1;
            
            case 4:
             ReportModule userReport = new ReportModule (login.getUsername());
             userReport.setAllValues();
             System.out.println("\n----------REPORT----------");
                System.out.println("1. Daily Report\n2. Weekly Report\n3. Monthly Report\n4. Back To Finance Management\nPlease Choose...");
                       test3:
                          while (true){
                          int report = input.nextInt();
                          switch (report) {
                          case 1:
                             System.out.println("NAME:"+login.getUsername().toUpperCase());
                             userReport.dailyReport();
                             break test3;
                          case 2:
                             System.out.println("NAME:"+login.getUsername().toUpperCase());
                             userReport.weeklyReport();
                             break test3;
                          case 3:
                             System.out.println("NAME:"+login.getUsername().toUpperCase());
                             userReport.monthlyReport();
                             break test3;
                         case 4:
                         break test1;
                          
                          default:
                              System.out.println("Invalid Input");
                              break;
                          } 
                       }
             //log out
             case 5:
               System.out.println("----------THANK YOU <"+login.getUsername().toUpperCase()+"> FOR USING JASH PERSONAL FINANCIAL SYSTEM !----------");
               login.logOut();
               return 0;
            
           
               
            default    :
                 System.out.println("Invalid input. Please choose again.");
                return 1;
         }
         return 1;
        
   }
  }

