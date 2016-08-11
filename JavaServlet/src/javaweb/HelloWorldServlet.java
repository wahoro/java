package javaweb;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloWorldServlet extends HttpServlet {
	
//	@Override
//	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		PrintWriter out =  resp.getWriter();
//		out.println("<html><title>my test servlet</title><body>hello servlet!!!</body></html>");
//		out.flush();
//		out.close();
//	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("do post");
		doGet(req, resp);
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("do get");
		resp.setContentType("text/html;charset=utf-8");
		
		PrintWriter out =  resp.getWriter();
		out.println("<html><t1>my test servlet</t1><body>ÄãºÃhello servlet!!!</body></html>");
		out.flush();
		out.close();
	}

	private static final long serialVersionUID = 1L;
}
