package pl.coderslab;

import pl.coderslab.model.User;
import pl.coderslab.model.UserGroup;
import pl.coderslab.util.DbUtil;

import java.sql.Connection;
import java.sql.SQLException;

public class Main_copy {


    public String queryUsers = "create table users (id bigint(20) auto_increment, user_name varchar(255), email varchar(255) unique, password varchar (255), user_group_id int, primary key(id), foreign key(user_group_id) references user_group(id));";
    public String queryUserGroup = "create table user_group (id int auto_increment, name varchar(255), primary key(id));";
    public String queryExercise = "create table exercise (id int auto_increment, title varchar(255), description text, primary key(id));";
    public String querySolution = "create table soution (id int auto_increment, created datetime, updated datetime, description text, exercise_id int, users_id bigint(20), primary key(id), foreign key(exercise_id) references exercise(id), foreign key(users_id) references users(id));";

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

            User user1= User.loadUserById(connection, 2);
            if(user1 != null) {
                System.out.println(user1);
            }

            //user1.delete(connection);

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
