package com.cloud.util.dataup;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class Jdbc {

	    public static void main(String[] args) {
	    	new Jdbc().txt2String(new File("C:/Users/Administrator/Desktop/123.txt"));
	    	
	    	System.out.println("*******"+queryCourt("普陀区人民法院"));    //方法名调用数据库连接
	  }
	//查询数据，定义的query方法
	public static String queryCourt(String info){
		String result = null;
		 Connection conn = getConnection();// 获取数据库连接
	        String Sql="select id from court where court_name like '%"+info+"'";
	        try{
	            Statement stmt=conn.createStatement(); //也可以使用PreparedStatement来做
	            ResultSet rs=stmt.executeQuery(Sql);//执行sql语句并返还结束
	                                                                       
	            while(rs.next()){//遍历结果集 ，向下一行                              
	            	result = rs.getString("id");
	            }
	          if(rs !=null){
	            try{
	                rs.close();
	            } catch (SQLException e){
	            e.printStackTrace();
	           }
	        }
	        if(stmt !=null){
	            try{
	               stmt.close();
	            }catch(SQLException e){
	             e.printStackTrace();
	          }
	        }
	        if(conn !=null){
	            try{
	                conn.close();
	               }catch(SQLException e){
	                e.printStackTrace();
	            }
	        }
	     }catch(Exception e){
	       e.printStackTrace();
	     }
        releaseConnection(conn);
        return result;
	 }
	//查询数据，定义的query方法
	//查询数据，定义的query方法
		public static String queryOrgan(String info){
			String result = null;
			 Connection conn = getConnection();// 获取数据库连接
		        String Sql="select id from organ where organ_name = '"+info+"'";
		        try{
		            Statement stmt=conn.createStatement(); //也可以使用PreparedStatement来做
		            ResultSet rs=stmt.executeQuery(Sql);//执行sql语句并返还结束
		                                                                       
		            while(rs.next()){//遍历结果集 ，向下一行                              
		            	result = rs.getString("id");
		            }
		          if(rs !=null){
		            try{
		                rs.close();
		            } catch (SQLException e){
		            e.printStackTrace();
		           }
		        }
		        if(stmt !=null){
		            try{
		               stmt.close();
		            }catch(SQLException e){
		             e.printStackTrace();
		          }
		        }
		        if(conn !=null){
		            try{
		                conn.close();
		               }catch(SQLException e){
		                e.printStackTrace();
		            }
		        }
		     }catch(Exception e){
		       e.printStackTrace();
		     }
	        releaseConnection(conn);
	        return result;
		 }
	//数据库连接
	  public static Connection getConnection() {
		  
	            Connection conn = null;//声明连接对象
	            String driver = "com.mysql.jdbc.Driver";// 驱动程序类名
	            String url = "jdbc:mysql://192.168.16.30:3306/nams?characterEncoding=utf8&useSSL=false";
	            String user="root";
	            String pass="123456";
	     try {
	          Class.forName(driver);// 注册(加载)驱动程序
	          conn = DriverManager.getConnection(url, user, pass);// 获取数据库连接
	         } catch (Exception e) {
	           e.printStackTrace();
	        }
	          return conn;
	       }
	//释放数据库连接
	  public static void releaseConnection(Connection conn) {
	       try {
	            if (conn != null)
	                 conn.close();
	           } catch (Exception e) {
	             e.printStackTrace();
	           }
	       }
	  
	  public static String txt2String(File file){
	        String result = "";
	        try{
	            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
	            String s = null;
	            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
//	                s = s.substring(24, s.length());
	            	System.out.println(s);
	                result = queryOrgan(s);
	                write(result);
	    	    	
	            }
	            br.close();    
	        }catch(Exception e){
	            e.printStackTrace();
	        }
	        return result;
	    }
	  
	  public static void write(String args) throws IOException {
			String file="C:/Users/Administrator/Desktop/stream.txt";
			String charSet="UTF-8";
			//写字符转换成字节流
//			FileOutputStream fileWriter=new FileOutputStream(file);
//			OutputStreamWriter writer=new OutputStreamWriter(fileWriter, charSet);
			Writer writer=new FileWriter(file,true);//写入的文本不附加在原来的后面而是直接覆盖
			try {
				writer.write(args+"\r\n");
			} catch (Exception e) {
				// TODO: handle exception
			}finally{
				writer.close();
			}
			//读取字节转换到字符
			FileInputStream fileInputStream=new FileInputStream(file);
			InputStreamReader reader=new InputStreamReader(fileInputStream, charSet);
			StringBuilder builder=new StringBuilder();
			char [] buf=new char[64];
			int count=0;
			try {
				while ((count = reader.read(buf)) != -1) {
					builder.append(buf,0,count);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}finally{
				reader.close();
			}
			System.out.println(builder.toString());
		}


}
