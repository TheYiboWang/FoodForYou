package api;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import db.DBConnection;
import db.MySQLDBConnection;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final DBConnection connection = new MySQLDBConnection();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 try {
	   		 JSONObject msg = new JSONObject();
	   		 // get request parameters for userID and password
	   		 String user = request.getParameter("user_id");
	   		 String pwd = request.getParameter("password");
	   		 String fname = request.getParameter("first_name");
	   		 String lname = request.getParameter("last_name");
	   		 
	   		 if (connection.createAccount(user, pwd, fname, lname)) {
	   			 HttpSession session = request.getSession();
	   			 session.setAttribute("user", user);
	   			 // setting session to expire in 10 minutes
	   			 session.setMaxInactiveInterval(10 * 60);
	   			 // Get user name
	   			 String name = connection.getFirstLastName(user);
	   			 msg.put("status", "OK");
	   			 msg.put("user_id", user);
	   			 msg.put("name", name);
	   		 } else {
	   			 response.setStatus(401);
	   		 }
	   		 RpcParser.writeOutput(response, msg);
	   	 } catch (JSONException e) {
	   		 // TODO Auto-generated catch block
	   		 e.printStackTrace();
	   	 }
	}

}
