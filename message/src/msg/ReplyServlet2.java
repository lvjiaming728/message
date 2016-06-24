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

@WebServlet("/ReplyServlet2")
public class ReplyServlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ReplyServlet2() {
        super();
           }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
		request.setCharacterEncoding("utf-8");
		int id = Integer.parseInt(request.getParameter("id"));
		String reply = String.valueOf(request.getParameter("reply"));
	
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://localhost:3306/message";
			String username="root";
			String password="LVjiaming728000";
			Connection conn = DriverManager.getConnection(url, username, password);
			String sql = null;
			sql = "update note set reply = ? where id = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(sql); // 获取PreparedStatement
			
			pstmt.setString(1, reply); // 对SQL语句中的第3个参数赋值 
			pstmt.setInt(2, id); 
			
			pstmt.execute();
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				doGet(request,response);

	}
}
