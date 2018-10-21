package pl.coderslab;

import pl.coderslab.model.User;
import pl.coderslab.util.DbUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AdministrationProgram {

    static Scanner scan;

    public static void main(String[] args) {

        try (Connection connection = DbUtil.getConnection()) {
            User[] uArray = User.loadAllUsers(connection);
            while (uArray != null) {
                System.out.println("List of all users: " + uArray);
                break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        while (true) {

            System.out.println("Choose one of the options:");
            System.out.println("add (to add user),     edit (to edit user),    delete (to delete user),     quit (to quit program)");

            try {
                String add = null;
                String edit = null;
                String delete = null;
                String quit = null;
                scan = new Scanner(System.in);
                String userChoise = scan.nextLine();

                if (userChoise.equals(add)) {
// zapyta o wszystkie dane z klasy User bez id
                } else if (userChoise.equals(edit)) {
// zapyta o wszystkie dane z kalsy User i id
                } else if (userChoise.equals(delete)) {
                scan = new Scanner(System.in);
                System.out.println("Give user.id to delete:");
                int userId = scan.nextInt();
                scan.nextLine();
               // User.delete(Connection);

                } else if (userChoise.equals(quit)) {
                   // break;
                }
            }
            catch (InputMismatchException e) {
                System.out.println("Must be chosen on of following: add, edit, delete or quit");
            }


        break;
        }
    }
}
