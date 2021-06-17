// 登录类，提供用户账号注册、登录、修改密码的方法
package hrms;
import java.util.Scanner;

public class Login {
  private static Scanner sc = new Scanner(System.in);

  public static void register() throws Exception {  // 注册
    System.out.println("*****欢迎注册人力资源管理系统******");
    String sql = "INSERT INTO t_user(name,password) VALUES(?,?)";
    while(true) {
      System.out.print("请输入用户名: ");
      String inname = sc.next();
      System.out.print("请设置密码: ");
      String inpassword = sc.next();
      boolean b = DBUtils.queryLogin(inname, inpassword);
      if(b) {
        System.out.println("该用户名已存在, 请重新输入");
      }else {
        DBUtils.executeUpdate(sql, inname, inpassword);
        System.out.print("注册成功!是否立即登录? y/n: ");
        String as = sc.next();
        if("y".equals(as)) {
          login();
        }
        break;
      }
    }
  }

  public static void login() throws Exception {  // 登录
    int count = 0;
    System.out.println("*****欢迎使用人力资源管理系统*****");
    while(true) {
      System.out.print("请输入用户名: ");
      String inName = sc.next();
      System.out.print("请输入密码: ");
      String inPassword = sc.next();
      boolean b = DBUtils.queryLogin(inName, inPassword);
      if(b) {
        HrmsByJdbc.mainInterface();
      }else {
        count++;
        System.out.println("账号与密码不匹配，请重新输入!\n");
      }
      if(count >= 3) {
        System.out.println("您连续三次输入错误，已退出!");
        break;
      }
    }
  }

  public static void updatePassword() throws Exception {  // 修改密码
    System.out.print("请登录后修改密码");
    System.out.println("\n");
    int count = 0;
    System.out.println("*****请修改登录密码*****");
    while(true) {
      System.out.print("请输入用户名: ");
      String inName = sc.next();
      System.out.print("请输入密码: ");
      String inPassword = sc.next();
      boolean b = DBUtils.queryLogin(inName, inPassword);
      if(b) {
        System.out.println("-----修改密码-----\n");
        System.out.print("请输入新的密码: ");
        String newPassword = sc.next();
        String sql = "UPDATE t_user SET password=? WHERE name=?";
        System.out.println("修改成功!请重新登录...");
        login();
      }else {
        count++;
        System.out.println("密码错误，请重新输入!");
      }
      if(count == 3) {
        System.out.println("您连续三次输入错误，已退出!");
        break;
      }
    }
  }
}
