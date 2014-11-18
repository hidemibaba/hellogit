package blog;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.mysql.jdbc.AbandonedConnectionCleanupThread;

public class MyContextListener implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent sce){

		//ドライバの解放
		Enumeration<Driver> drivers = DriverManager.getDrivers();
		while(drivers.hasMoreElements()){
			Driver driver = drivers.nextElement();
			try {
				DriverManager.deregisterDriver(driver);
			} catch (SQLException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}

		//スレッドの停止
		try {
			AbandonedConnectionCleanupThread.shutdown();
		} catch (InterruptedException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent sce){

	}
}
