// 数据库工具类，用于操作数据库，供业务层调用，方便上层的程序
package hrms;
import java.sql.*;

public class DBUtils {
  public static final String DRIVER = "com.mysql.cj.jdbc.Driver";  // 数据库驱动
  public static final String URL =
    "jdbc:mysql://localhost:3306/hrmsdb?characterEncoding=utf-8";  // 数据库地址
  public static final String USER = "root";  // 数据库的用户名与密码
  public static final String PASSWORD = "";
  private static Connection conn = null; // 创建链接

  public static Connection getConnection() throws Exception {  // 打开链接
    Class.forName(DRIVER);  // 加载驱动
    conn = DriverManager.getConnection(URL, USER, PASSWORD);  // 使用DriverManager获取数据库连接
    return conn;
  }

  public static void closeConnection() throws Exception {  // 关闭链接
    if(conn != null && conn.isClosed()) {
      conn.close();
      conn = null;
    }
  }

  public int executeUpdate(String sql) throws Exception {  // 使用Statement执行SQL语句
    conn = getConnection();
    Statement st = conn.createStatement();  // 使用Connection来创建一个Statement对象
    int r = st.executeUpdate(sql);  // 执行SQL语句
    closeConnection();
    return r;  // 返回受影响的记录条数
  }

  public static int executeUpdate(String sql, Object...obj) throws Exception {  // 更新数据库
    conn = getConnection();
    PreparedStatement pst = conn.prepareStatement(sql);  // 预编译SQL,将SQL中的值用占位符?替代
    if(obj != null && obj.length > 0) {
      for(int i = 0; i < obj.length; i++) {
        pst.setObject(i+1, obj[i]);
      }
    }
    int r = pst.executeUpdate();
    closeConnection();
    return r;
  }

  public static ResultSet executeQuery(String sql, Object...obj) throws Exception {  // 查询数据库
    conn = getConnection();
    PreparedStatement pst = conn.prepareStatement(sql);
    if(obj != null && obj.length > 0) {
      for(int i =0; i < obj.length; i++) {
        pst.setObject(i+1, obj[i]);
      }
    }
    ResultSet rs = pst.executeQuery();
    return rs;
  }

  public static boolean queryLogin(String name, String password) throws Exception {  // 判断能否判断能否登录
    String sql = "select name from t_user where name=? and password=?";
    ResultSet rs = executeQuery(sql, name, password);
    if(rs.next()) {
      return true;
    }else {
      return false;
    }
  }
}
