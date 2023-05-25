package AllMethods;

public class PojoEmpl {
	
	private int empId;
	private String empName;
	private int empSalary;
	private String empType;

	public int getEmpid() {
		return empId;
	}

	public String getEmpname() {
		return empName;
	}

	public int getEmpsalary() {
		return empSalary;
	}

	public String getEmptype() {
		return empType;
	}
		
	@Override
	public String toString() {
		return "Employe [empId=" + empId + ", empName=" + empName + ", empSalary=" + empSalary + ", empType=" + empType
				+ "]";
	}
	public PojoEmpl(int empId, String empName, int empSalary, String empType) {
		super();
		this.empId = empId;
		this.empName = empName;
		this.empSalary = empSalary;
		this.empType = empType;
	}
	
	

}

