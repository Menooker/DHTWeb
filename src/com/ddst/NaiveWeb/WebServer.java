package com.ddst.NaiveWeb;
import java.net.*; 
import java.io.*; 
import java.util.*;

public class WebServer{ 
   public static void main(String args[])throws Exception { 
   ServerSocket listenSocket = new ServerSocket(6789);
   System.out.println("httpServer running on port " + 
   listenSocket.getLocalPort());  
     while(true) {  
       try {Socket socket = listenSocket.accept();
       Thread thread = new Thread(new RequestHandler(socket));  
       thread.start(); 
	}//�����߳�
       catch(Exception e){}
        } 
     } 
} 
class RequestHandler implements Runnable { 
    Socket connectionSocket; 
    OutputStream outToClient; 
    BufferedReader inFormClient;
    String requestMessageLine;
	String fileName;
    // ���췽�� 
    public RequestHandler(Socket connectionSocket) throws Exception { 
    this.connectionSocket = connectionSocket; 
     }              
    public void run(){ // ʵ��Runnable �ӿڵ�run()����
    try { processRequest();} 
    catch(Exception e) { System.out.println(e);} 
     } 
    private void processRequest() throws Exception { 
        //��ȡ����ʾWeb ������ύ��������Ϣ 
        BufferedReader inFormClient = 
        new BufferedReader( new InputStreamReader( connectionSocket.getInputStream() ) );
        DataOutputStream outToClient = new DataOutputStream(
          	connectionSocket.getOutputStream());
        //��ȡhtml�����ĵ�һ��
        requestMessageLine = inFormClient.readLine();
        //�����������ļ���
        StringTokenizer tokenizerLine =    //tokenizerLine���������������
        new StringTokenizer(requestMessageLine);
            if (tokenizerLine.nextToken().equals("GET")){
          	   fileName = tokenizerLine.nextToken();
            if (fileName.startsWith("/")==true) fileName = fileName.substring(1);
          	File file = new File(fileName);
            int numOfBytes = (int)file.length();
          	FileInputStream inFile = new FileInputStream(fileName);
          	byte[] fileInBytes = new byte[numOfBytes]; 
          	inFile.read(fileInBytes);
            //����http��Ӧ����
            outToClient.writeBytes("HTTP/1.0 200 Document Follow\r\n");
            if(fileName.endsWith(".jpg"))
          	  outToClient.writeBytes("Content-type:image/jpeg\r\n");
          	if(fileName.endsWith(".gif"))
          		outToClient.writeBytes("Content-type:image/gif\r\n");
                outToClient.writeBytes("Content-type:" + "numOfBytes" + "\r\n");
          	    outToClient.writeBytes("\r\n");
          	    outToClient.write(fileInBytes,0,numOfBytes);
          	    connectionSocket.close();
               }
          	else System.out.println("Bed Request Message");
    } 
}