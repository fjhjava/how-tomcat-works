package com.java1234.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import com.java1234.server.HttpServer;
public class Response {
	
	private static final int BUFFER_SIZE=1024;
	OutputStream output;
	Request request;
	public Response(OutputStream output) {
		super();
		this.output = output;
	}
	public void setRequest(Request request) {
		this.request = request;
	}
	
	public void sendStaticResource() throws Exception{
		byte[] bytes=new byte[BUFFER_SIZE];
		FileInputStream fis=null;
		try{
			File file=new File(HttpServer.WEB_ROOT,request.getUrl());
			if(file.exists()){
				fis=new FileInputStream(file);
				int ch=fis.read(bytes, 0, BUFFER_SIZE);
				while(ch!=-1){
					output.write(bytes, 0, ch);
					ch=fis.read(bytes, 0, BUFFER_SIZE);
				}
			}else{
				String errorMessage="<html><font size='+4' color='black'>404</font><br>"
						+ "<a href='http://localhost:8080/favicon.ico'>返回主页</a></html>";
				output.write(errorMessage.getBytes());
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(fis!=null){
				fis.close();
			}
		}
	}

}
