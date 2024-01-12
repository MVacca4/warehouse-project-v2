import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {
	WarehouseDB wdb = new WarehouseDB();

  @Override
  public void createEmployee(String name, String position, int salary, float hours) {
		wdb.createEmployee(name, position, salary, hours);
  }

  @Override
  public void deleteEmployee() {

  }

  @Override
  public void viewEmployee() {

  }

  @Override
  public void updateEmployee() {

  }
}
