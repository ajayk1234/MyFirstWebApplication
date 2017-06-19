
import java.sql.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Hello extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
    public Hello() {}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		
		out.print("<html><body>");
		out.print("<h3>Hello Servlet</h3>");
		out.print("</body></html>");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		
		String name=req.getParameter("name");
		String address=req.getParameter("address");
		String phoneno=req.getParameter("phoneno");
		
		System.out.println("Name="+name);
		System.out.println("Address="+address);
		System.out.println("Phone no="+phoneno);
		
		try
		{  
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost/userdetails","root","root");  
			Statement stmt=con.createStatement();  
			PreparedStatement ps = con.prepareStatement("insert into userdetails values (? , ?, ?)");
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, phoneno);
            ps.executeUpdate();
			ResultSet rs=stmt.executeQuery("select * from userdetails");  
			while(rs.next())
			{
				String name1=rs.getString(1);
				String address1=rs.getString(2);
				String phoneno1=rs.getString(3);
				req.getSession().setAttribute("name", name1);
				req.getSession().setAttribute("address", address1);
				req.getSession().setAttribute("phoneno", phoneno1);
			
				System.out.println("    "+name1+"   "+address1+"   "+phoneno1);  
				 
			}
				
			con.close(); 
		}
		catch(Exception e)
		{ 
			System.out.println(e);
		}  
		
		
		
		resp.sendRedirect("firstjsp.jsp");
	}
	
}
