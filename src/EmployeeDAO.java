
public interface EmployeeDAO {
	void createEmployee(String name, String position, int salary, float hours);
	void deleteEmployee(String name);
	void viewEmployee();
	boolean updateEmployee(String name, String newField, int column);
	boolean getEmployee(String name);
	int getSize();
}
