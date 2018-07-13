package com.fileuploading.upload;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@MultipartConfig(fileSizeThreshold=1024*1024*20,maxFileSize=1024*1024*50,maxRequestSize=-1L)
public class FileUploadingServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//InputStream inputstream=null;
	File f=null;
	//String file=null;
	String fileloc=null;
	String appPath=null;
	String dirName=null;
	String filename=null;
	String downloadlocation=null;
	PrintWriter out;
	

	public void doPost(HttpServletRequest request ,HttpServletResponse repsonse) throws IOException, ServletException,FileNotFoundException {
		appPath=System.getProperty("catalina.base");/*request.getServletContext().getRealPath("");*/
		dirName=request.getServletContext().getInitParameter("filedirectory");
		out=repsonse.getWriter();
		fileloc=appPath+File.separator+dirName;
		System.out.println(fileloc);
		f=new File(fileloc);
		if(f.exists()) {
			f.mkdirs();		
			System.out.println(f.getName()+"directory created");
		}		
		for(Part p:request.getParts())
		{		
			filename=getFileName(p);
			downloadlocation=fileloc+File.separator+filename;
			p.write(fileloc);
			System.out.println(filename+"uploaded successfully");
		}
		
		RequestDispatcher r=request.getRequestDispatcher("response.jsp");
		request.setAttribute("downloadloc",downloadlocation);
		r.include(request, repsonse);		
	}
	private String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        System.out.println("content-disposition header= "+contentDisp);
        String[] tokens = contentDisp.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length()-1);
            }
        }
        return "";
    }

}
