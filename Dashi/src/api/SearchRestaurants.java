package api;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import db.DBConnection;
import db.MongoDBConnection;
import db.MySQLDBConnection;

/**
 * Servlet implementation class SearchRestaurants
 */
@WebServlet("/restaurants")
public class SearchRestaurants extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SearchRestaurants() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// response.setContentType("application/json");
		// response.addHeader("Access-Control-Allow-Origin", "*");
		// String username = "";
		// PrintWriter out = response.getWriter();
		// if (request.getParameter("username") != null) {
		// username = request.getParameter("username");
		// out.println("Hello " + username);
		// }
		// String age = "";
		// if (request.getParameter("age") != null) {
		// age = request.getParameter("age");
		// out.print("Age " + age);
		// }

		// response.setContentType("text/html");
		// PrintWriter out = response.getWriter();
		// out.println("<html><body>");
		// out.println("<h1>This is a HTML page</h1>");
		// out.println("</body></html>");
		//
		// out.flush(); // printwriter is buffer. flush() write/send at one
		// time.
		// out.close();

		// response.setContentType("application/json");
		// response.addHeader("Access-Control-Allow-Origin", "*");
		//
		// String username = "";
		// if (request.getParameter("username") != null) {
		// username = request.getParameter("username");
		// }
		// JSONObject obj = new JSONObject();
		// try {
		// obj.put("username", username);
		// } catch (JSONException e) {
		// e.printStackTrace();
		// }
		// PrintWriter out = response.getWriter();
		// out.print(obj);
		// out.flush();
		// out.close();
		
		// allow access only if session exists
		HttpSession session = request.getSession();
		if (session.getAttribute("user") == null) {
			response.setStatus(403);
			return;
		}

		JSONArray array = new JSONArray();
		DBConnection connection = new MySQLDBConnection();
		if (request.getParameterMap().containsKey("user_id") && request.getParameterMap().containsKey("zip_code")) {
			String userId = request.getParameter("user_id");
			String zipcode = request.getParameter("zip_code");
//			double lat = Double.parseDouble(request.getParameter("lat"));
//			double lon = Double.parseDouble(request.getParameter("lon"));
			array = connection.searchRestaurants(userId, zipcode);
		}
		RpcParser.writeOutput(response, array);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// allow access only if session exists
		HttpSession session = request.getSession();
		if (session.getAttribute("user") == null) {
			response.setStatus(403);
			return;
		}
		doGet(request, response);
	}

}
