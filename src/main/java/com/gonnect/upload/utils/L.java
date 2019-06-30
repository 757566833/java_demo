package com.gonnect.upload.utils;

import java.sql.*;
import java.util.*;


public class L {
	private static String drive ;
	private static String url ;
	private static String name;
	private static String pass;
	private static Connection conn = null;
	private static PreparedStatement pstmt = null;
	private static ResultSet rs = null;
	static 
	{
		drive = "com.mysql.cj.jdbc.Driver";
  		url = "jdbc:mysql://192.168.0.150:3306/excel";
  		name = "root";
  		pass = "123456";
  		try {
   			Class.forName(drive);
  		} catch (ClassNotFoundException e) {
   		// TODO Auto-generated catch block
   		e.printStackTrace();
  }
	}
	/**
	 * 得到结果集
	 * @param sql
	 * @param str
	 * @return
	 */
	public static List<Map<String,Object>> getL(String sql , Object ...str)
	{
		List<Map<String,Object>> L = new ArrayList<Map<String,Object>>();
		try {
			conn = DriverManager.getConnection(url,name,pass);
			pstmt = conn.prepareStatement(sql);
			for(int i = 1;i<=str.length;i++)
			{
				pstmt.setObject(i, str[i-1]);
			}
			rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int d = rsmd.getColumnCount();
			while(rs.next())
			{
				Map<String , Object> m = new HashMap<String,Object>();
				for(int i = 1;i<=d;i++)
				{
					String name = rsmd.getColumnName(i);
					Object value = rs.getObject(name);
					m.put(name, value);
				}
				L.add(m);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try {
				if(rs!=null)
				{
					rs.close();
				}
				if(pstmt!=null)
				{
					pstmt.close();
				}
				if(conn!=null)
				{
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		return L;
	}
	/**
	 * 得到修改条数
	 * @param sql
	 * @param str
	 * @return
	 */
	public static int getn(String sql , Object ...str)
	{
		int n = 0;
		try {
			conn = DriverManager.getConnection(url,name,pass);
			pstmt = conn.prepareStatement(sql);
			for(int i = 1;i<=str.length;i++)
			{
				pstmt.setObject(i, str[i-1]);
			}
			n = pstmt.executeUpdate();
			
	
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try {
				if(pstmt!=null)
				{
					pstmt.close();
				}
				if(conn!=null)
				{
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		return n;
	}
	/**
	 * 得到结果集是否为空
	 * @param sql
	 * @param str
	 * @return
	 */
	public static boolean getb(String sql , Object ...str)
	{
		Boolean b = false;
		try {
			conn = DriverManager.getConnection(url,name,pass);
			pstmt = conn.prepareStatement(sql);
			for(int i = 1;i<=str.length;i++)
			{
				pstmt.setObject(i, str[i-1]);
			}
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				b = true;
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try {
				if(rs!=null)
				{
					rs.close();
				}
				if(pstmt!=null)
				{
					pstmt.close();
				}
				if(conn!=null)
				{
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		return b;
	}
	/**
	 * 得到number主键值
	 * @param sequPK
	 * @return
	 */
	public static int getPKN(String sequPK)
	{
		int n = 0; 
		try {
			conn = DriverManager.getConnection(url,name,pass);
			String sql = "select "+sequPK+".nextval as PK from dual";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				
				n=rs.getInt("PK");
				System.out.println(n);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try {
				if(rs!=null)
				{
					rs.close();
				}
				if(pstmt!=null)
				{
					pstmt.close();
				}
				if(conn!=null)
				{
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
		return n;
	}
	/**
	 * 得到字符串主键
	 * @return
	 */
	public String getPKS()
	{
		String str = null;
		UUID u = UUID.randomUUID();
		str = u.toString().replace("-", "");
		return str;
	}
}
