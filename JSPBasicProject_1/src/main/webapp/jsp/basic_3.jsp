<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// servlet형식 => service 메서드 영역
	// response.setContentType("text/html;charset=UTF-8")
	// PrintWriter out=reponse.getWriter()
	out.write("<html>");
	out.write("<body>");
	out.write("<h1>Hello Servlet!!</h1>");
	out.write("</body>");
	out.write("</html>");


%>
<%--
  public void _jspService(final jakarta.servlet.http.HttpServletRequest request, final jakarta.servlet.http.HttpServletResponse response)
      throws java.io.IOException, jakarta.servlet.ServletException {

	  public void _jspInit() {
	  }
	
	  public void _jspDestroy() {
	  }
	
	  public void _jspService(final jakarta.servlet.http.HttpServletRequest request, final jakarta.servlet.http.HttpServletResponse response)
	      throws java.io.IOException, jakarta.servlet.ServletException {
	
	    if (!jakarta.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
	      final java.lang.String _jspx_method = request.getMethod();
	      if ("OPTIONS".equals(_jspx_method)) {
	        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
	        return;
	      }
	      if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method)) {
	        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
	        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSP들은 오직 GET, POST 또는 HEAD 메소드만을 허용합니다. Jasper는 OPTIONS 메소드 또한 허용합니다.");
	        return;
	      }
	    }
	
	    PageContext pageContext;
	    HttpSession session = null;
	    ServletContext application;
	    ServletConfig config;
	    JspWriter out = null;
	    Object page = this;
	    JspWriter _jspx_out = null;
	    PageContext _jspx_page_context = null;
	
	    try {
	      response.setContentType("text/html; charset=UTF-8");
		  //////////// JSP 파일		
	      out.write('\r');
	      out.write('\n');
	
		// servlet형식 => service 메서드 영역
		// response.setContentType("text/html;charset=UTF-8")
		// PrintWriter out=reponse.getWriter()
		out.write("<html>");
		out.write("<body>");
		out.write("<h1>Hello Servlet!!</h1>");
		out.write("</body>");
		out.write("</html>");

	    } catch (java.lang.Throwable t) {
	      if (!(t instanceof jakarta.servlet.jsp.SkipPageException)){
	        out = _jspx_out;
	        if (out != null && out.getBufferSize() != 0)
	          try {
	            if (response.isCommitted()) {
	              out.flush();
	            } else {
	              out.clearBuffer();
	            }
	          } catch (java.io.IOException e) {}
	        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
	        else throw new ServletException(t);
	      }
	    } finally {
	      _jspxFactory.releasePageContext(_jspx_page_context);
	    }
	  }
  }

 --%>
    