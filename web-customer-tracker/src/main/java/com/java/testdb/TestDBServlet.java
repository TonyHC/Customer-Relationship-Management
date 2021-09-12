package com.java.testdb;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TestDBServlet
 */
@WebServlet("/TestDBServlet")
public class TestDBServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Setup Connection Variables
		String user = "root";
		String password = "Beachhouse3-";
		
		String jdbcURL = "jdbc:mysql://localhost:3306/web_customer_tracker?useSSL=false&serverTimezone=UTC";
		String driver = "com.mysql.cj.jdbc.Driver";
		
		// Get Connection to DB
		try {
			PrintWriter out = response.getWriter();
			out.println("Connection to Database: " + jdbcURL);
			
			Class.forName(driver);
			
			Connection connection = DriverManager.getConnection(jdbcURL, user, password);
			
			out.println("Success");
			
			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

}
