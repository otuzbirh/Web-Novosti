/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.DBnovosti;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Boolean.parseBoolean;
import static java.lang.Integer.parseInt;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.User;

/**
 *
 * @author korisnik
 */
public class AdminController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
   

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
        doPost(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action=request.getParameter("action");
        int id=parseInt(request.getParameter("id"));
        boolean status=parseBoolean(request.getParameter("status"));
        DBnovosti db=new DBnovosti();
        List<User> ul;
        String role;
        String address="users.jsp";
        if(action!=null){
            
            switch(action){
                
                case "uklonieditora":
                         role="ROLE_USER";
                    db.updateRole(role,id);
                   
                    ul=db.findAllUsers();
                    request.setAttribute("Users", ul);
                  
                    
                    break;
                case "dodajeditora":
                     role="ROLE_EDITOR";
                    db.updateRole(role,id);
                   
                    ul=db.findAllUsers();
                    request.setAttribute("Users", ul);
                   
                    
                    break;
                case "status":
                    if(status==true){
                        status=false;
                    }else{
                        status=true;
                    }
                   
                    
                    db.updateStatus(status, id);
                    ul=db.findAllUsers();
                    request.setAttribute("Users", ul);
                    request.setAttribute("message","Uspjesna izmjena statusa" );
                    break;
                case "edit":
                    uredi(request,response);
                    
                    ul=db.findAllUsers();
                    request.setAttribute("Users", ul);
                    
                    break;
                case "promijenisifru":
                    promijenisifru(request,response);
                     
                    ul=db.findAllUsers();
                    request.setAttribute("Users", ul);
                    
                    break;
            }
            
        }
        RequestDispatcher rd=request.getRequestDispatcher(address);
        rd.forward(request, response);
    }


    public void uredi(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
             String fn=request.getParameter("firstName");
       String ln=request.getParameter("lastName");
       String email=request.getParameter("email");
 
       int id=parseInt(request.getParameter("id"));
       User u=new User();
       u.setId(id);
          u.setFirstName(fn);
       u.setLastName(ln);
      
       
       
       try{
           DBnovosti db=new DBnovosti();
           if(db.findEmailById(id).equals(email)){
               u.setEmail(email);
                db.editUser(u);
                request.setAttribute("message", "Uspjesna izmjena");
           }else{
           if(db.findEmail(email)==false){
                u.setEmail(email);
                db.editUser(u);
                request.setAttribute("message", "Uspjesna izmjena");
                
           }else{
               
            
               request.setAttribute("message", "Email se koristi");
           }}
           
           
           
           
       }catch(Exception e){
           System.out.println(e);
       }
    
       
       
       
    }
   
      public void promijenisifru(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
            String email=request.getParameter("email");
         String pwd=request.getParameter("password");
       String rpwd=request.getParameter("repeat");
       int id=parseInt(request.getParameter("id"));
       User u=new User();
    
       
       
       try{
           DBnovosti db=new DBnovosti();
            if(pwd.length()>7){
       if(pwd.equals(rpwd)){
           
           db.editPWD(pwd,id);
           
       }else{
           request.setAttribute("message", "Molimo unesite poonovno password");
            
       }
      }else{
           request.setAttribute("message", "Molimo unesite password 8 karaktera");
             
      }
       }catch(Exception e){
           System.out.println(e);
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
