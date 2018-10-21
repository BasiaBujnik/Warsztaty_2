package pl.coderslab;

import pl.coderslab.model.Exercise;
import pl.coderslab.model.User;
import pl.coderslab.model.UserGroup;
import pl.coderslab.util.DbUtil;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {

        // by zapisac uzytkownika do bazy danych potrzeba stworzyc uzytkownika (obiekt)
        UserGroup userGroup = new UserGroup(1, "Grupa 1");
        User user = new User("Basia", "basia@onet.pl", "sad", userGroup);
        System.out.println(user.toString());
        User nuser = new User("ktos", "ktos@onet.pl", "morze", userGroup);
        System.out.println(nuser.toString());

       // Exercise exercise = new Exercise();



        // zapisac obiekt do bazy
        try ( Connection connection = DbUtil.getConnection()){   // try (with resources)

            //user.saveToDB(connection);
            //nuser.saveToDB(connection);

            User user1= User.loadUserById(connection, 8);
            if(user1 != null) {
                System.out.println(user1);
            }

            user1.delete(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }


        // zapisanie wielu obiektów do bazy
        try (Connection connection = DbUtil.getConnection()) {
            User [] user2 = User.loadAllUsers(connection);
            if (user2 != null) {
                System.out.println(user2);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }




    }
}
