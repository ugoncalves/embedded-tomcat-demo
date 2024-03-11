package net.jupiter;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class App {

    public static void main( String[] args ) {

        try {

            Tomcat tomcat = new Tomcat();
            tomcat.setBaseDir("/tmp");
            tomcat.setPort(8080);

            String contextPath = ""; // '/'
            String appsPath = new File(".").getAbsolutePath();

            Context context = tomcat.addContext(contextPath, appsPath);

            HttpServlet servlet = new HttpServlet(){
                @Override
                protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                        throws IOException {

                    PrintWriter writer = resp.getWriter();
                    writer.println("<html><body><h2>Hello!</h2></body></html>");
                }
            };
            String servletName = "test";
            tomcat.addServlet(contextPath, servletName, servlet);
            context.addServletMappingDecoded("/test", servletName);

            tomcat.start();
            tomcat.getServer().await();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
