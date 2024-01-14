package warehouseV2;

public class Employee {

  private String name = "";
  private String position = "";
  private int salary = 0;
  private float hours = 0;

  public Employee(String name, String position, int salary, float hours) {
    this.name = name;
    this.position = position;
    this.salary = salary;
    this.hours = hours;
  }

  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getPosition() {
    return position;
  }
  public void setPosition(String position) {
    this.position = position;
  }
  public int getSalary() {
    return salary;
  }
  public void setSalary(int salary) {
    this.salary = salary;
  }
  public float getHours() {
    return hours;
  }
  public void setHours(float hours) {
    this.hours = hours;
  }
}
