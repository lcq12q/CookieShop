package xyz.lcq12q.listener;

import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

/**
 * 应用销毁时清理 MySQL / JDBC 相关线程与驱动，避免 Tomcat 热部署时的内存泄漏警告。
 */
@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // 启动时无需特殊处理
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // 1. 关闭 MySQL 废弃连接清理线程（最常见、最危险的泄漏源）
        try {
            AbandonedConnectionCleanupThread.checkedShutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 2. 注销 JDBC 驱动，释放驱动持有的类加载器引用
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
