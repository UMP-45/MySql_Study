package test;
import java.sql.*;

public class MySQLDemo {
  // MySQL8.0以下版本：
  static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
  static final String DB_URL = "jdbc:mysql://localhost:3306/test_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

  // 数据库的用户名与密码：
  static final String USER = "root";
  static final String PASS = "";

  public static void main(String[] args) {
    Connection conn = null;
    Statement stmt = null;
    try{
      // 加载JDBC驱动
      Class.forName(JDBC_DRIVER);

      // 打开链接
      System.out.println("连接数据库...");
      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      // 执行查询
      System.out.println("实例化Statement对象...");
      stmt = conn.createStatement();
      String sql;
      sql = "SELECT id, name, url FROM websites";
      ResultSet rs = stmt.executeQuery(sql);

      // 展示数据库
      while(rs.next()) {
        // 通过字段检索
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String url = rs.getString("url");

        // 输出数据
        System.out.print("ID: " + id);
        System.out.print(", 站点名称: " + name);
        System.out.print(", 站点URL: " + url);
        System.out.print("\n");
      }

      // 完成后关闭
      rs.close();
      stmt.close();
      conn.close();
    }catch(SQLException se) {
      // 处理JDBC错误
      se.printStackTrace();
    }catch(Exception e) {
      // 处理Class.forName错误
      e.printStackTrace();
    }finally {
      // 关闭资源
      try{
        if(stmt != null) stmt.close();
      }catch(SQLException se2){
      } // 什么都不做
      try{
        if(conn != null) conn.close();
      }catch(SQLException se){
        se.printStackTrace();
      }
    }
    System.out.println("Goodbye!");
  }
}

