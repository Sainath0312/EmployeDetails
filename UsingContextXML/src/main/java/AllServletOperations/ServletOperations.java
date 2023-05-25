package AllServletOperations;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.*;

import com.google.gson.Gson;

import AllMethods.DataBaseConnector;
import AllMethods.PojoEmpl;




public class ServletOperations extends HttpServlet {
	// static Logger log = Logger.getLogger("InseretOpration");
	private static final Logger log = LogManager.getLogger(ServletOperations.class);

	private static final long serialVersionUID = 1L;

	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();

		ServletContext cxt = request.getServletContext();
		DBclass db = (DBclass) cxt.getAttribute("EmployeeDatabase");
		Connection c = db.getConnection();

		DataBaseConnector dbcon = new DataBaseConnector();
		Gson gs = new Gson();
		PojoEmpl pe = gs.fromJson(request.getReader(), PojoEmpl.class);
		List<AllMethods.PojoEmpl> li = dbcon.displayjs(pe.getEmpid(), c);

		if (li.isEmpty()) {
			try {

				response.setContentType("application/JSON");
				response.setCharacterEncoding("UTF-8");
				dbcon.insertDetails(pe, c);
				out.println("Insterted");
				List<PojoEmpl> l3 = dbcon.allEmpDetails(c);
				String s2 = gs.toJson(l3);
				out.println("Records That Are Present In That DataBase:");
				out.println(s2);

			} catch (Exception e) {
				e.printStackTrace();
				log.fatal("src/main/java/ServletOperation/AllOperations.java/doPut : " + e.getMessage());

			}
		} else {

			response.setContentType("application/JSON");
			response.setCharacterEncoding("UTF-8");
			out.println("Employee With Id :" + pe.getEmpid() + " Is Already Exists In DataBase");
			out.println(gs.toJson(li));

		}
		destory(c);

	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		ServletContext cxt = request.getServletContext();
		DBclass db = (DBclass) cxt.getAttribute("EmployeeDatabase");
		Connection c = db.getConnection();

		PrintWriter out = response.getWriter();

		DataBaseConnector dbcon = new DataBaseConnector();
		Gson gs = new Gson();
		PojoEmpl pe = gs.fromJson(request.getReader(), PojoEmpl.class);
		List<PojoEmpl> li = dbcon.displayjs(pe.getEmpid(), c);

		if (!li.isEmpty()) {
			try {
				response.setContentType("application/JSON");
				response.setCharacterEncoding("UTF-8");
				dbcon.deletRecord(pe.getEmpid(), c);
				out.println("Record With ID Num : " + pe.getEmpid() + " Is Deleted From The DataBase");
				out.println("Remaing Record That Are Present In The Database :\n");
				List<PojoEmpl> l2 = dbcon.allEmpDetails(c);
				String s1 = gs.toJson(l2);
				out.println(s1);
			} catch (Exception e) {
				e.printStackTrace();
				log.fatal("src/main/java/ServletOperation/AllOperations.java/doDelet : " + e.getMessage());
			}

		} else {
			response.setContentType("application/JSON");
			response.setCharacterEncoding("UTF-8");
			out.println("Record With ID Num :" + pe.getEmpid() + " Is Not Present In The DataBase");
			out.println("Records That Are Present In The DataBase :\n ");
			List<PojoEmpl> l2 = dbcon.allEmpDetails(c);
			out.println(gs.toJson(l2));
		}
		destory(c);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		ServletContext cxt = request.getServletContext();
		DBclass db = (DBclass) cxt.getAttribute("EmployeeDatabase");
		Connection c = db.getConnection();

		DataBaseConnector dbcon = new DataBaseConnector();

		Gson gs = new Gson();
		PojoEmpl pe = gs.fromJson(request.getReader(), PojoEmpl.class);
		List<PojoEmpl> li = dbcon.displayjs(pe.getEmpid(), c);
		String s = gs.toJson(li);
		if (!(li.isEmpty())) {
			try {
				response.setContentType("application/JSON");
				response.setCharacterEncoding("UTF-8");
				out.println("Before Update:");
				out.println(s);
				dbcon.updateDetails(pe, c);
				List<PojoEmpl> l2 = dbcon.displayjs(pe.getEmpid(), c);
				String s2 = gs.toJson(l2);
				out.println("Rocord With ID Num : " + pe.getEmpid() + " Is Updated");
				out.println(s2);
			} catch (Exception e) {
				e.printStackTrace();
				log.fatal("src/main/java/ServletOperation/AllOperations.java/doPost : " + e.getMessage());
			}
		} else {
			response.setContentType("application/JSON");
			response.setCharacterEncoding("UTF-8");
			out.println("With ID Num :" + pe.getEmpid() + " Is Does Not Exits In DataBase");
			List<PojoEmpl> l2 = dbcon.allEmpDetails(c);
			out.println((gs.toJson(l2)));
		}
		destory(c);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ServletContext cxt = request.getServletContext();
		DBclass db = (DBclass) cxt.getAttribute("EmployeeDatabase");
		Connection con = db.getConnection();

		DataBaseConnector dbcon = new DataBaseConnector();

		Gson gson = new Gson();
		PojoEmpl pe = gson.fromJson(request.getReader(), PojoEmpl.class);
		

		List<PojoEmpl> li = dbcon.displayjs(pe.getEmpid(), con);

		String s = gson.toJson(li);

		List<PojoEmpl> l2 = dbcon.allEmpDetails(con);

		String s1 = gson.toJson(l2);

		PrintWriter out = response.getWriter();
		if (!li.isEmpty()) {
			try {

				response.setContentType("application/JSON");
				response.setCharacterEncoding("UTF-8");
				out.println("Employee With Id :" + pe.getEmpid() + " Is Found In EmployeeList");
				out.println(s);
			} catch (Exception e) {
				e.printStackTrace();
				log.fatal("src/main/java/ServletOperation/AllOperations.java/doGet(1) : " + e.getMessage());
			}
		} else {
			out.println("Employee With Id :" + pe.getEmpid() + " Is Not Found In EmployeeList");
		}
		out.println("All Employee Details:\n");
		try {
			response.setContentType("application/JSON");
			response.setCharacterEncoding("UTF-8");
			out.println(s1);
		} catch (Exception e) {
			e.printStackTrace();
			log.fatal("src/main/java/ServletOperation/AllOperations.java/doGet(2) : " + e.getMessage());
		}
		destory(con);
	}

	private void destory(Connection c) {
		try {
			c.close();
		} catch (SQLException e) {
			log.error("src/main/java/ServletOperation/AllOperations.java/destory : " + e.getMessage()
					+ " SQLException Error");

		}

	}

}
