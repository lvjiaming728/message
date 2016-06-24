package msg;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/InsertServlet")
public class InsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public InsertServlet() {
		super();
		}

	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
				doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
				request.setCharacterEncoding("utf-8");
				
		String title =  String.valueOf(request.getParameter("title"));
		String author = String.valueOf(request.getParameter("author"));
		String content = String.valueOf(request.getParameter("content"));

		try {
			Class.forName("com.mysql.jdbc.Driver"); 
			String url = "jdbc:mysql://localhost:3306/message";
			String username = "root"; 
			String password = "LVjiaming728000"; 
			Connection conn = DriverManager.getConnection(url, username,password); 			String sql = null; 
			sql = "insert into note(title,author,content) values(?,?,?)";
			
			PreparedStatement pstmt = conn.prepareStatement(sql); 			
			pstmt.setString(1, title);
			pstmt.setString(2, author);
			pstmt.setString(3, content); 			
			pstmt.execute();
			//pstmt.executeQuery();
			sql = "select * from note";
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			
			List<ListNoteBean> list = new ArrayList<>();
			while (rs.next()) {
				ListNoteBean note = new ListNoteBean();
				note.setId(rs.getInt("id"));
				note.setTitle(rs.getString("title"));
				note.setAuthor(rs.getString("author"));
				note.setContent(rs.getString("content"));
				note.setReply(rs.getString("reply"));
			
				list.add(note);
			}
			request.setAttribute("list", list);
			
			pstmt.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		request.getRequestDispatcher("listnote.jsp").forward(request, response);
	}
}
