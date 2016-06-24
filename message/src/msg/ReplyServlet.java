package msg;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ReplyServlet")
public class ReplyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    public ReplyServlet() {
        super();
            }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			int id = Integer.parseInt(request.getParameter("id"));
		System.out.println(id);
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://localhost:3306/message";
			String username="root";
			String password="LVjiaming728000";
			Connection conn = DriverManager.getConnection(url, username, password);
			Statement stmt = conn.createStatement();	
			String sql = "select id,title,author,content from note where id='"+id+"'";
			
			ResultSet rs = stmt.executeQuery(sql);	
			
			
			List<ListNoteBean> list = new ArrayList<>();
			while (rs.next()) {
		    ListNoteBean note = new ListNoteBean();
			note.setId(rs.getInt("id"));
			note.setTitle(rs.getString("title"));
			note.setAuthor(rs.getString("author"));
			note.setContent(rs.getString("content"));
			
				list.add(note); 
			}
			request.setAttribute("list", list);
			
			rs.close();
			stmt.close();
			conn.close();
			}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		request.getRequestDispatcher("reply.jsp").forward(request, response);
		}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				doGet(request,response);
	}

}

