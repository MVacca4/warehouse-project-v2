
import java.util.List;
import java.util.ArrayList;
import java.sql.*;

public class WarehouseDB {
  private Connection connect;

  public List<Employee> syncToDatabase() {
    connect();
    Employee emp = new Employee();
    List<Employee> empList = new ArrayList<Employee>();

    try {
      String query = "select * from employees";
      PreparedStatement prepStmt = connect.prepareStatement(query);
      ResultSet rs = prepStmt.executeQuery();

      while (rs.next()) {
        String employeeName = rs.getString("name");
        String position = rs.getString("position");
        int salary = rs.getInt("salary");
        float hours = rs.getFloat("hours");
        emp.setName(employeeName);
        emp.setPosition(position);
        emp.setSalary(salary);
        emp.setHours(hours);
        empList.add(emp);
      }
      
      connect.close();
      return empList;
    } catch (SQLException e) {
      System.err.println(e.getMessage());
      return empList;
    }
  }

  public void createEmployee(String name, String position, int salary, float hours) {
    connect();

    try {
      String query = "insert into employees (name, position, salary, hours) values (?, ?, ?, ?)";
      PreparedStatement prepStmt = connect.prepareStatement(query);
      prepStmt.setString(1, name);
      prepStmt.setString(2, position);
      prepStmt.setInt(3, salary);
      prepStmt.setFloat(4, hours);
      prepStmt.execute();
      connect.close();

    } catch (SQLException e) {
      System.err.println(e.getMessage());
    }
  }

  public void deleteEmployee(String name) {
    connect();

    try {
      String query = "delete from employees where name = ?";
      PreparedStatement prepStmt = connect.prepareStatement(query);
      prepStmt.setString(1, name);
      prepStmt.execute();
      connect.close();

    } catch (SQLException e) {
      System.err.println(e.getMessage());
    }
  }

  public void viewEmployees() {
    connect();

    try {
      String query = "select * from employees";
      Statement st = connect.createStatement();
      ResultSet rs = st.executeQuery(query);

      while (rs.next()) {
        String name = rs.getString("name");
        System.out.println(name);
      }
      connect.close();

    } catch (SQLException e) {
      System.err.println(e.getMessage());
    }
  }

  public boolean getEmployee(String name) {
    connect();

    try {
      String query = "select * from employees where name = ?";
      PreparedStatement prepStmt = connect.prepareStatement(query);
      prepStmt.setString(1, name);
      ResultSet rs = prepStmt.executeQuery();

      while (rs.next()) {
        String employeeName = rs.getString("name");
        String position = rs.getString("position");
        int salary = rs.getInt("salary");
        float hours = rs.getFloat("hours");
        System.out.println("Name: " + employeeName + "\nPosition: " + position + "\nSalary: " + salary + "\nHours: " + hours);
      }
      return true;
    } catch (SQLException e) {
      System.err.println(e.getMessage());
      return false;
    }
  }

  public boolean updateEmployee(String name, String newField, int column) {

    try {
      try {
        switch (column) {
          case 1:
            String query = "update employees set name = ? where name = ?";
            PreparedStatement prepStmt = connect.prepareStatement(query);
            prepStmt.setString(1, newField);
            prepStmt.setString(2, name);
            prepStmt.executeUpdate();
            break;
          case 2:
            String query1 = "update employees set position = ? where name = ?";
            PreparedStatement prepStmt1 = connect.prepareStatement(query1);
            prepStmt1.setString(1, newField);
            prepStmt1.setString(2, name);
            prepStmt1.executeUpdate();
            break;
          case 3:
            String query2 = "update employees set salary = ? where name = ?";
            PreparedStatement prepStmt2 = connect.prepareStatement(query2);
            prepStmt2.setInt(1, Integer.parseInt(newField));
            prepStmt2.setString(2, name);
            prepStmt2.executeUpdate();
            break;
          case 4:
            String query3 = "update employees set hours = ? where name = ?";
            PreparedStatement prepStmt3 = connect.prepareStatement(query3);
            prepStmt3.setFloat(1, Float.parseFloat(newField));
            prepStmt3.setString(2, name);
            prepStmt3.executeUpdate();
            break;
          default:
        }
      } catch (NumberFormatException e) {
        return false;
      }
    } catch (SQLException e) {
      System.err.println("Error interacting with database. " + e.getMessage());
    }
    return true;
  }

  public int getSize() {
    connect();
    int count = 0;

    try {
      String query = "select * from employees";
      Statement st = connect.createStatement();
      ResultSet rs = st.executeQuery(query);

      while (rs.next()) {
        ++count;
      }

      connect.close();
      return count;


    } catch (SQLException e) {
      System.err.println(e.getMessage());
      return count;
    }
  }

  private void connect() {

    try {
      connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/employee_data", "root", "");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
