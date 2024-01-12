import java.sql.*;

public class WarehouseDB {
  private Connection connect;

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

  public String getEmployee(String name) {

    try {
      String query = "select * from employees where name = ?";
      PreparedStatement prepStmt = connect.prepareStatement(query);
      prepStmt.setString(1, name);
      ResultSet rs = prepStmt.executeQuery();

      while (rs.next()) {
        String name = rs.getString("name");
        String position = rs.getString("position");
        int salary = rs.getInt("salary");
        float hours = rs.getFloat("hours");
        System.out.println("Name: " + name + "\nPosition: " + position + "\nSalary: " + salary + "\nHours: " + hours);
      }
    } catch (SQLException e) {
      System.err.println(e.getMessage());
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
            break;
          case 2:
            String query = "update employees set position = ? where name = ?";
            PreparedStatement prepStmt = connect.prepareStatement(query);
            prepStmt.setString(1, newField);
            prepStmt.setString(2, name);
            break;
          case 3:
            String query = "update employees set salary = ? where name = ?";
            PreparedStatement prepStmt = connect.prepareStatement(query);
            prepStmt.setInt(1, Integer.parseInt(newField));
            prepStmt.setString(2, name);
            break;
          case 4:
            String query = "update employees set hours = ? where name = ?";
            PreparedStatement prepStmt = connect.prepareStatement(query);
            prepStmt.setFloat(1, Float.parseFloat(newField));
            prepStmt.setString(2, name);
            break;
          default:
        }
      } catch (NumberFormatException e) {
        return false;
      }
    } catch (SQLException e) {
      System.err.println("Error interacting with database." + e.getMessage());
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

      return count;
      connect.close();

    } catch (SQLException e) {
      System.err.println(e.getMessage());
    }
  }

  private void connect() {

    try {
      connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/employee_data", "root", "");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
