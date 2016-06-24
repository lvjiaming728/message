package msg;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RegistServlet")
public class RegistServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public RegistServlet() {
        super();
          }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String name =  String.valueOf(request.getParameter("name"));
		String pwd1 =  String.valueOf(request.getParameter("password1"));

		System.out.println(name);
		System.out.println(pwd1);
	
		try {
			Class.forName("com.mysql.jdbc.Driver"); 
			String url = "jdbc:mysql://localhost:3306/message";
			String username = "root"; 
			String password = "LVjiaming728000"; 
Connection conn = DriverManager.getConnection(url, username,password); 
			String sql = null; 
			sql = "insert into person(name,password) values(?,?)";
			
			PreparedStatement pstmt = conn.prepareStatement(sql); // 获取PreparedStatement
			
			pstmt.setString(1, name); 
			pstmt.setString(2, pwd1); 			
			pstmt.executeUpdate();
		
			pstmt.close();
			conn.close(); 
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		 request.getRequestDispatcher("index.jsp").forward(request,response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				doGet(request,response);
	}

}
