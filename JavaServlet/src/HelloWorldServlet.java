
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloWorldServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out =  resp.getWriter();
		out.println("<html><t1>my test servlet</t1><body>hello servlet!!!</body></html>");
		out.flush();
		out.close();
	}

	private static final long serialVersionUID = 1L;
}
