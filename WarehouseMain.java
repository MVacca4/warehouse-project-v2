
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The base class for version 2 of the Warehouse management program.
 * @author Massimiliano Vacca
 **/ 
public class Warehouse {
  String name = "";
  Scanner sc = new Scanner(System.in);
  List<Employee> employeeData = new ArrayList<Employee>();
  EmployeeDAOImpl EmployeeDAO = new EmployeeDAOImpl();

  public static void main(String[] args) {
    Warehouse wh = new Warehouse(); // Warehouse object to call methods with.
    boolean end = false; // False keeps program running, true ends program.

    // Main menu loop, continues until the user enters 0, ending the program.
    while (end == false) {

      Scanner mainSc = new Scanner(System.in);
      int choice = 0; // Holds choice for main menu.
      wh.optionDisplay(); // Display main menu options.
      choice = mainSc.nextInt(); // Get choice selection from user.

      // Call method based on which choice the user made, or end the program if 0 was selected.
      switch (choice) {
        case 0:
          end = true;
          break;
        case 1:
          wh.createEmployee();
          break;
        case 2:
          wh.deleteEmployee();
          break;
        case 3:
          wh.viewEmployee();
          break;
        default:
      }
    }
  }

  public void createEmployee() {
    sc = new Scanner(System.in);
    int salary = 0;
    float hours = 0;

    System.out.println("Fill in the following questions, if the answer is unknown leave the field blank.");
    System.out.print("Enter employee's name: ");
    name = sc.nextLine();
    System.out.print("Enter employee's position: ");
    String position = sc.nextLine();

    // Surround with try...catch in case user enters nothing for salary or hours.
    try {
      System.out.print("Enter employee's salary, no spaces, no commas: ");
      salary = Integer.parseInt(sc.nextLine());
    } catch (NumberFormatException e) {salary = 0;} // Assign default value of 0.
    try {
      System.out.print("Enter employee's hours: ");
      hours = Float.parseFloat(sc.nextLine());
    } catch (NumberFormatException e) {hours = 0;} // Assign default value of 0.
    
    Employee employeeInfo = new Employee(name, position, salary, hours);
    EmployeeDAOImpl.createEmployee(name, position, salary, hours);
    employeeData.add(employeeInfo);
    System.out.println("Employee entry created!\n-----------------------");
    wait(1500);
  }

  public void deleteEmployee() {
    sc = new Scanner(System.in);
    boolean end = false;

    while (end == false) {

      if (employeeData.size() == 0) {
        System.out.println("The employee list currently has no entries. Returning you to the main menu.\n--------------------------------");
        end = true;
        wait(2000);
      } else {
        System.out.println("Enter the name of the employee you wish to delete, or enter 0 to go back to the main menu: ");
        name = sc.nextLine();

        try {
          if (Integer.parseInt(name) == 0) {end = true;} // If user entered 0, end while loop.
        } catch (NumberFormatException e) {
          // Loop through all employee entries and delete an entry if it matches the name typed.
          for (int i = 0; i < employeeData.size(); i++) {
            if (name.equalsIgnoreCase(employeeData.get(i).name)) {
              employeeData.remove(i);
              wdb.deleteEmployee(name);
              System.out.println("Employee entry has been deleted!\n--------------------------------");
              wait(1500);
              break;
            } else {
              if (i == employeeData.size() - 1) { // Display message if loop has gotten to last employee entry.
                System.out.println("Sorry, that employee name doesn't match any in our records. Please ensure you entered the name correctly.\n");
                wait(1500);
              }
            }
          }
        }
      }
    }
  } 

  public void viewEmployee() {
    int choice = 0;
    boolean end = false; // For ending main while loop and going back to the main menu.
    // For ending while loop when user has to select between seeing list of employees or choosing to update the currently selected user.
    boolean listUpdate = false;

    if (wdb.getSize() == 0) {
      System.out.println("There are no existing employee entries, returning you to the main menu.");
      dashes();
      wait(2000);
    } else {
      while (end == false) {
        System.out.println("Type the name of the employee you would like information for from the following list:\n");
        wait(2000);

        wdb.viewEmployees();

        System.out.println("\nEnter 0 to go back to the main menu.");
        sc = new Scanner(System.in);
        name = sc.nextLine();

        try {
          if (Integer.parseInt(name) == 0) {end = true;}
        } catch (NumberFormatException e) {

          if (wdb.getEmployee(name)) {
            wait(1000);
            while (listUpdate == false) {
              System.out.println("\nEnter 0 to see the list of employees again, or 1 if you would like to update "
              + "this employee's information: ");
              choice = sc.nextInt();

              if (choice == 0) {
                listUpdate = true; // Set to true so while loop ends.
              } else if (choice == 1) {
                updateEmployee(name); // Call updateEmployee with name of currently selected entry.
                listUpdate = true;
                wait(2000);
              } else {
                System.out.println("Invalid entry. Enter only 0 or 1.");
                wait(1000);
              }
            }
            listUpdate = false; // Set to false so when it gets set to true after updateEmployee it doesn't stay stuck on true.
          } else {
            wait(500);
            System.out.println("Hit enter to see the list of employees again: ");
            sc.nextLine();
          }
        }
      }
    }
  }

  public void updateEmployee(String name) {
    Scanner sc = new Scanner(System.in);
    boolean updateLoop = false;
    int choice;
    String newField = "";

    while (updateLoop == false) {
      System.out.println("\nEnter the number that corresponds with the field you would like to update.\n");
      System.out.println("1. Name");
      System.out.println("2. Position");
      System.out.println("3. Salary");
      System.out.println("4. Hours");
      choice = sc.nextInt();
      sc.nextLine();

      switch (choice) {
        case 1:
          System.out.println("Please enter the new name you would like for this entry: ");
          newField = sc.nextLine();
          wdb.updateEmployee(name, newField, 1);
          updateLoop = true;
          break;
        case 2:
          System.out.println("Please enter the new position you would like for this entry: ");
          newField = sc.nextLine();
          wdb.updateEmployee(name, newField, 2);
          updateLoop = true;
          break;
        case 3:
          System.out.println("Please enter the new salary you would like for this entry: ");
          newField = sc.nextLine();
          if (wdb.updateEmployee(name, newField, 3)) {
            updateLoop = true;
            break;
          } else {
            System.out.println("Invalid entry. Ensure only numbers are entered with no spaces, "
                      + "punctuation, or special characters.");
            wait(3000);
            break;
          }
        case 4:
          System.out.println("Please enter the new hours you would like for this entry: ");
          newField = sc.nextLine();
          if (wdb.updateEmployee(name, newField, 4)) {
            updateLoop = true;
            break;
          } else {
            System.out.println("Invalid entry. Ensure only numbers are entered with no spaces or special characters.");
            break;
          }
        default:
          System.out.println("Invalid entry.");
          wait(1000);
      }
    }
    System.out.println("Entry updated!");
    dashes();

  }

  public static void wait(int ms) {

    try {
      Thread.sleep(ms);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

  public void optionDisplay() {

    System.out.print("Welcome to the warehouse database system. ");
    System.out.println("Please type the corresponding number of your choice and hit enter:\n");
    System.out.println("0. Exit program.");
    System.out.println("1. Create new employee.");
    System.out.println("2. Delete existing employee.");
    System.out.println("3. View employee list.");
  }

  // Prints 30 dashes for readability of program and code.
  public void dashes() {System.out.println("------------------------------");}
}
