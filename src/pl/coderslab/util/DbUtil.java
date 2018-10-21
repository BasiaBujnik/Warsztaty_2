package pl.coderslab.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtil {

    public	static Connection getConnection()	throws SQLException {   /// jest throw wiec nie umieszczac w try-catch
        // ta klasa ma tworzyc polaczenie z baza danych i to polaczenie zwrocic

        return DriverManager.getConnection("jdbc:mysql://localhost:3306/Warsztaty_2?useSSL=false&characterEncoding=utf8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                "root", "coderslab");



    }

}
