package pl.coderslab;

import pl.coderslab.model.Exercise;
import pl.coderslab.model.Solution;
import pl.coderslab.model.User;
import pl.coderslab.model.UserGroup;
import pl.coderslab.util.DbUtil;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    public String queryUsers = "create table users (id bigint(20) auto_increment, user_name varchar(255), email varchar(255) unique, password varchar (255), user_group_id int, primary key(id), foreign key(user_group_id) references user_group(id));";
    public String queryUserGroup = "create table user_group (id int auto_increment, name varchar(255), primary key(id));";
    public String queryExercise = "create table exercise (id int auto_increment, title varchar(255), description text, primary key(id));";
    public String querySolution = "create table soution (id int auto_increment, created datetime, updated datetime, description text, exercise_id int, users_id bigint(20), primary key(id), foreign key(exercise_id) references exercise(id), foreign key(users_id) references users(id));";


    public static void main(String[] args) {

        UserGroup userGroup = new UserGroup(1, "Grupa 1");

        User user = new User("Basia", "basia@onet.pl", "sad", userGroup);
        System.out.println(user.toString());
        User nuser = new User("ktos", "ktos@onet.pl", "morze", userGroup);
        System.out.println(nuser.toString());

        Exercise exercise = new Exercise(1, "Exercise 1", "Exercise 1 description");
        System.out.println(exercise.toString());

        Solution solution = new Solution();
        System.out.println(solution.toString());



        // spr saveUserToDB z modyfikacją
        try (Connection connection = DbUtil.getConnection()) {
            user.saveUserToDB(connection);
            nuser.saveUserToDB(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        // spr loadUserById
        try (Connection connection = DbUtil.getConnection()) {
            User user1 = User.loadUserById(connection, 1);
            if (user1 != null) {
                System.out.println(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // spr loadAllUsers
        try (Connection connection = DbUtil.getConnection()) {
            User [] uArray = User.loadAllUsers(connection);
            while (uArray != null) {
                System.out.println(uArray);
                break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // spr delete
        try (Connection connection = DbUtil.getConnection()) {
            nuser.delete(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // spr UserGroup
        try (Connection connection = DbUtil.getConnection()) {
            userGroup.saveGroupToDB(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        try (Connection connection = DbUtil.getConnection()) {
            UserGroup userGroup1 = UserGroup.loadUserGroupById(connection, 1);
            if (userGroup1 != null) {
                System.out.println(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (Connection connection = DbUtil.getConnection()) {
            UserGroup [] uGrArray = UserGroup.loadAllUserGroup(connection);
            while (uGrArray != null) {
                System.out.println(uGrArray);
                break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (Connection connection = DbUtil.getConnection()) {
            userGroup.delete(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // spr Exercise
        try (Connection connection = DbUtil.getConnection()) {
            exercise.saveExerciseToDB(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        try (Connection connection = DbUtil.getConnection()) {
            Exercise exercise1 = Exercise.loadExerciseById(connection, 1);
            if (exercise1 != null) {
                System.out.println(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        try (Connection connection = DbUtil.getConnection()) {
            Exercise [] exercises = Exercise.loadAllExercise(connection);
            while (exercises != null) {
                System.out.println(exercises);
                break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        try (Connection connection = DbUtil.getConnection()) {
            exercise.delete(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //  spr Solution
        try (Connection connection = DbUtil.getConnection()) {
            solution.saveSolutionToDB(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        try (Connection connection = DbUtil.getConnection()) {
            Solution solution1 = Solution.loadedSolutionById(connection, 1);
            if (solution1 != null) {
                System.out.println(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        try (Connection connection = DbUtil.getConnection()) {
            Solution [] solutions = Solution.loadAllSolution(connection);
            while (solutions != null) {
                System.out.println(solutions);
                break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        try (Connection connection = DbUtil.getConnection()) {
            solution.delete(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}