// 人力资源管理类，这个类调用Dbutils类，查询、增加、修改、和删除员工信息，但需要在登录之后才能执行
package hrms;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class HrmsByJdbc {
  public static Scanner sc = new Scanner(System.in);

  public static void mainInterface() {
    while(true) {
      System.out.println("\n\n");
      System.out.println("*人力资源管理系统*");
      System.out.println("*1.查看员工信息*");
      System.out.println("*2.添加员工信息*");
      System.out.println("*3.修改员工信息*");
      System.out.println("*4.删除员工信息*");
      System.out.println("*0.退出系统*");
      System.out.print("请选择: ");

      int num = sc.nextInt();
      if(num == 0) {
        System.out.println("谢谢使用！");
        System.exit(0);  // 退出系统
      }else {
        switch (num) {
          case 1 -> query();  // 查询
          case 2 -> add();  // 添加
          case 3 -> update(); // 修改
          case 4 -> del();  // 删除
          default -> System.out.println("无选项，请重新输入!");
        }
      }
    }
  }

  private static void query() {
    System.out.print("员工信息\n a.全部, b.单个: ");
    String num1 = sc.next();
    String sql = null;
    try{
      switch (num1) {
        case "a" -> {
          sql = "SELECT * FROM t_employee";
          ResultSet rsa = DBUtils.executeQuery(sql);  // 调用工具类
          System.out.println("编号\t 姓名\t 性别\t 年龄");
          while (rsa.next()) {
            int id = rsa.getInt(1);
            String name = rsa.getString(2);
            String gender = rsa.getString(3);
            int age = rsa.getInt(4);
            System.out.println(id + "\t" + name + "\t" + gender + "\t" + age);
          }
        }
        case "b" -> {
          System.out.print("请输入要查询的员工的id: ");
          int idnum = sc.nextInt();
          sql = "SELECT * FROM t_employee WHERE id=?";
          ResultSet rsb = DBUtils.executeQuery(sql, idnum);
          System.out.println("编号\t 姓名\t 性别\t 年龄\t");
          while (rsb.next()) {
            int id = rsb.getInt(1);
            String name = rsb.getString(2);
            String gender = rsb.getString(3);
            int age = rsb.getInt(4);
            System.out.println(id + "\t" + name + "\t" + gender + "\t" + age);
          }
        }
        default -> System.out.println("无选项，请重新输入!");
      }
    }catch(SQLException e) {
      System.out.println("db error: " + e.getMessage());
      e.printStackTrace();
    }catch(Exception e) {
      System.out.println("other error: " + e.getMessage());
      e.printStackTrace();
    }finally {
      try {
        DBUtils.closeConnection();
      }catch(Exception e) {
        System.out.println(e.getMessage());
      }
    }
  }

  private static void add() {
    System.out.println("\t 新增员工");
    System.out.print("姓名: ");
    String name = sc.next();
    System.out.print("性别: ");
    String gender = sc.next();
    System.out.print("年龄: ");
    int age = sc.nextInt();
    String sql = "INSERT INTO t_employee(name,gender,age) VALUES(?,?,?)";
    try{
      DBUtils.executeUpdate(sql, name, gender, age);
      System.out.println("添加成功");
    }catch(Exception e) {
      System.out.println("错误: " + e.getMessage());
    }finally {
      try {
        DBUtils.closeConnection();
      }catch(Exception e) {
        e.printStackTrace();
      }
    }
  }

  private static void update() {
    String s1 = "SELECT * FROM t_employee WHERE id=?";
    String s2 = "UPDATE t_employee SET name=? WHERE id=?";
    String s3 = "UPDATE t_employee SET gender=? WHERE id=?";
    String s4 = "UPDATE t_employee SET age=? WHERE id=?";
    System.out.println("请输入要修改员工的id: ");
    int idnum1 = sc.nextInt();
    try {
      ResultSet rsb = DBUtils.executeQuery(s1, idnum1);
      System.out.println("编号\t 姓名\t 性别\t 年龄");
      while(rsb.next()) {
        int id = rsb.getInt(1);
        String name = rsb.getString(2);
        String gender = rsb.getString(3);
        int age = rsb.getInt(4);
        System.out.println(id + "\t" + name + "\t" + gender + "\t" + age);
      }
      System.out.print("是否修改此人信息？ y/n: ");
      String as = sc.next();
      if("y".equals(as)) {
        System.out.print("修改: a.姓名， b.性别， c.年龄 :");
        String as1 = sc.next();
        if("a".equals(as1)) {
          System.out.print("请输入姓名: ");
          String inname = sc.next();
          DBUtils.executeUpdate(s2, inname, idnum1);
        }else if("b".equals(as1)) {
          System.out.print("请输入性别: ");
          String gender = sc.next();
          DBUtils.executeUpdate(s3, gender, idnum1);
        }else if("c".equals(as1)) {
          System.out.print("请输入年龄: ");
          int age = sc.nextInt();
          DBUtils.executeUpdate(s4, age, idnum1);
        }else {
          System.out.println("输入错误,请重新输入!");
        }
      }
      System.out.println("修改成功!");
    }catch(Exception e) {
      e.printStackTrace();
    }finally {
      try {
        DBUtils.closeConnection();
      }catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  private static void del() {
    String s1 = "SELECT * FROM t_employee WHERE id=?";
    String s2 = "DELETE FROM t_employee WHERE id=?";
    System.out.print("请输入要删除员工的id: ");
    int idnum2 = sc.nextInt();
    ResultSet rs4 = null;
    try {
      rs4 = DBUtils.executeQuery(s1, idnum2);
      System.out.println("编号\t 姓名\t 性别\t 年龄");
      while(rs4.next()) {
        int id =rs4.getInt(1);
        String name = rs4.getString(2);
        String gender = rs4.getString(3);
        int age = rs4.getInt(4);
        System.out.println(id + "\t" + name + "\t" + gender + "\t" + age);
      }
      System.out.print("是否确定删除此人信息? y/n: ");
      String as = sc.next();
      if("y".equals(as)) {
        DBUtils.executeUpdate(s2, idnum2);
        System.out.println("删除成功!");
      }else {
        System.out.println("取消删除!");
      }
    }catch(Exception e) {
      e.getMessage();
    }finally {
      try {
        DBUtils.closeConnection();
      }catch(Exception e) {
        e.printStackTrace();
      }
    }
  }
}
