/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import ejb.NewsEntity;
import java.io.IOException;
import java.io.PrintWriter;
//import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Queue;
import javax.jms.Connection;
import javax.resource.ResourceException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author rgamb
 */
@WebServlet(name = "PostMessage", urlPatterns = {"/PostMessage"})
public class PostMessage extends HttpServlet 
{
    @Resource(mappedName="jms/NewMessageFactory") //jms/NewMessageFactory")
    private  javax.jms.ConnectionFactory connectionFactory;

    @Resource(mappedName="java:app/jms/NewMessage")
    private  Queue queue;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws javax.resource.ResourceException
     */
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ResourceException {
        response.setContentType("text/html;charset=UTF-8");
        
        // ===========================================================
        // Add the following code to send the JMS message
        String title=request.getParameter("title");
        String body=request.getParameter("body");
        if ((title!=null) && (body!=null)) 
        {
          try 
          {
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer messageProducer = session.createProducer(queue);

            ObjectMessage message = session.createObjectMessage();
            // here we create NewsEntity, that will be sent in JMS message
            NewsEntity e = new NewsEntity();
            e.setTitle(title);
            e.setBody(body);

            message.setObject(e);                
            messageProducer.send(message);
            messageProducer.close();
            connection.close();
            response.sendRedirect("ListNews");

           } catch (JMSException ex) 
           {
             ex.printStackTrace();
           }
        }

        // ===========================================================
        
        try (PrintWriter out = response.getWriter()) 
        {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet PostMessage</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PostMessage at " + request.getContextPath() + "</h1>");
        
            // ========================================================
            
            out.println("<form>");
            out.println("Title: <input type='text' name='title'><br/>");
            out.println("Message: <textarea name='body'></textarea><br/>");
            out.println("<input type='submit'><br/>");
            out.println("</form>");

            // ========================================================
            
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ResourceException ex) {
            Logger.getLogger(PostMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ResourceException ex) {
            Logger.getLogger(PostMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
