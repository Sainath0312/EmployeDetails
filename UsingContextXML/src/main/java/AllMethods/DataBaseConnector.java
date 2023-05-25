package AllMethods;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DataBaseConnector {
	   private static final Logger logger = LogManager.getLogger(DataBaseConnector.class);

	public void updateDetails(PojoEmpl e, Connection con) throws Exception {
		logger.debug("Now You Can Start Update The Details");
		truncate(con);
		String queryCheck = "SELECT  EmpId from employeelist WHERE EmpId = ?";
		PreparedStatement ps = con.prepareStatement(queryCheck);
		ps.setInt(1, e.getEmpid());
		ResultSet resultSet = ps.executeQuery();
		if (resultSet.next()) {
			String sql = "UPDATE employeelist SET EmpName=?,EmpSalary=?,EmpType=?  where EmpId=?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, e.getEmpname());
			stmt.setInt(2, e.getEmpsalary());
			stmt.setString(3, e.getEmptype());
			stmt.setInt(4, e.getEmpid());
			stmt.execute();
			logger.info("Updated");
			

			arrange(con);
		} else {
			System.out.println("Empployee With ID " + e.getEmpid() + " Is Not Present IN DataBase");
			logger.warn("src/main/java/DataBaseConnector.java/updateDetails/-->Enter Valid Employee Id");
		}

	}

	public void deletRecord(int EmpId, Connection c) throws Exception {
		truncate(c);
		logger.debug("Delet The Details");
		String queryCheck = "SELECT  EmpId from employeelist WHERE EmpId = ?";
		PreparedStatement ps = c.prepareStatement(queryCheck);
		ps.setInt(1, EmpId);
		ResultSet resultSet = ps.executeQuery();
		if (resultSet.next()) {
			String sql = "DELETE FROM employeelist WHERE EmpId=?";
			PreparedStatement stmt = c.prepareStatement(sql);
			stmt.setInt(1, EmpId);
			stmt.execute();
			logger.info("Deleted Record");
			arrange(c);
		} else {
			System.out.println("Employee Id " + EmpId + " Is Not Present In Employee DataBase");
			logger.warn("src/main/java/DataBaseConnector.java/insertDetails/-->Enter Valid ID");
		}

	}

	public void arrange(Connection con) throws Exception {

		truncate(con);
		String perm = "INSERT INTO permemp(EmpId,EmpSalary, EmpName)SELECT EmpId,EmpSalary, EmpName FROM employeelist WHERE EmpType='permenant'";
		PreparedStatement permst = con.prepareStatement(perm);
		permst.execute();

		String contr = "INSERT INTO conemp(EmpId,EmpSalary, EmpName)SELECT EmpId,EmpSalary, EmpName FROM employeelist WHERE EmpType='contract'";
		PreparedStatement constm = con.prepareStatement(contr);
		constm.execute();

		String temp = "INSERT INTO tempemp(EmpId,EmpSalary, EmpName)SELECT EmpId,EmpSalary, EmpName FROM employeelist WHERE EmpType='temp'";
		PreparedStatement tempst = con.prepareStatement(temp);
		tempst.execute();
	}

	public void truncate(Connection con) throws Exception {

		String trunTemp = "Truncate TABLE tempemp";
		PreparedStatement stmTemp = con.prepareStatement(trunTemp);
		stmTemp.execute();

		String trunCon = "Truncate TABLE conemp";
		PreparedStatement stmCon = con.prepareStatement(trunCon);
		stmCon.execute();
		String trunPerm = "Truncate TABLE permemp";
		PreparedStatement stmPerm = con.prepareStatement(trunPerm);
		stmPerm.execute();
	}

	public void insertDetails(PojoEmpl e, Connection con) {
		logger.debug("Insert The Employee Details");
		try {
			String sql = "INSERT INTO employeelist (EmpId,EmpName,EmpSalary,EmpType)VALUES (?, ?, ?, ?)";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, e.getEmpid());
			stmt.setString(2, e.getEmpname());
			stmt.setInt(3, e.getEmpsalary());
			stmt.setString(4, e.getEmptype());
			stmt.execute();
	
			logger.info("Insterted");
		
			arrange(con);
		} catch (Exception t) {
			System.out.println("Duplicate Id Are Not Allowed ");
			t.printStackTrace();
			logger.fatal("src/main/java/DataBaseConnector.java/insertDetails" + t.getMessage());
		}

	}

	public List<PojoEmpl> displayjs(int id, Connection con) {

		List<PojoEmpl> li = new ArrayList<PojoEmpl>();
		try {
			String jasq = "select * from employeelist where EmpId=?"; // jasq=jasonQuery

			PreparedStatement pstmt = con.prepareStatement(jasq);

			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				PojoEmpl e = new PojoEmpl(rs.getInt("EmpId"), rs.getString("EmpName"), rs.getInt("EmpSalary"),
						rs.getString("EmpType"));
				li.add(e);

			}
			return li;
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			logger.fatal("src/main/java/DataBaseConnector.java/displayjs" + e1.getMessage());
		}
		return li;

	}

	public List<PojoEmpl> allEmpDetails(Connection con) {

		List<PojoEmpl> l1 = new ArrayList<PojoEmpl>();

		try {

			String jasq = "select * from employeelist"; // jasq=jasonQuery

			PreparedStatement pstmt = con.prepareStatement(jasq);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				PojoEmpl e = new PojoEmpl(rs.getInt("EmpId"), rs.getString("EmpName"), rs.getInt("EmpSalary"),
						rs.getString("EmpType"));
				l1.add(e);

			}
			
			return l1;
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			logger.fatal("src/main/java/DataBaseConnector.java/allEmpDetails" + e1.getMessage());

			return l1;

		}

//    public void displayJason() throws Exception {
//        Connection dbConnection=empDbConnector();
//        String jasonQuery = "SELECT * FROM employeelist";
//        PreparedStatement pstmt=dbConnection.prepareStatement(jasonQuery);
//        ResultSet rs = pstmt.executeQuery();
//        JSONObject jsonObject = new JSONObject();
//        JSONArray array = new JSONArray();
//        while(rs.next()) {
//               JSONObject record = new JSONObject();
//               //Inserting key-value pairs into the json object
//               record.put("EmployeeName", rs.getString("EmpName"));
//               record.put("EmployeeSalary", rs.getInt("EmpSalary"));
//               record.put("EmplyeeType", rs.getString("EmpType"));
//               record.put("EmployeeID", rs.getInt("EmpId"));
//               array.add(record);
//               jsonObject.put("Employee Details", array);  
//            }
//  
//        System.out.println("\nDataBase");
//        System.out.println(jsonObject.toJSONString()+"\n");
//    }

	}
}
