package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/third")
public class ThirdServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("proccessing do get request by third servlet");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String message = req.getParameter("message");
		System.out.println("form submited using post method");
		System.out.println("message : " + message);

//		sending data to browser

		resp.setContentType("text/html");
		PrintWriter writer = resp.getWriter();

		writer.print("<h1>Form submitted</h1>");

		Date date = new Date();
		writer.print("""


					    <h2>Message : %s</h2>
					    <h3>Current Date is  : %s </h3>


				""".formatted(message,date));
		
		
		resp.setStatus(401);

	}

}
