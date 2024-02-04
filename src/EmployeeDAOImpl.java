
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {
	WarehouseDB wdb = new WarehouseDB();

	@Override
	public List<Employee> syncToDatabase() {
	  return wdb.syncToDatabase();
	}

  @Override
  public void createEmployee(String name, String position, int salary, float hours) {
		wdb.createEmployee(name, position, salary, hours);
  }

  @Override
  public void deleteEmployee(String name) {
    wdb.deleteEmployee(name);
  }

  @Override
  public void viewEmployee() {
    wdb.viewEmployees();
  }

  @Override
  public boolean updateEmployee(String name, String newField, int column) {
    return wdb.updateEmployee(name, newField, column);
  }

  @Override
  public boolean getEmployee(String name) {
    return wdb.getEmployee(name);
  }

  @Override
  public int getSize() {
    return wdb.getSize();
  }
}
