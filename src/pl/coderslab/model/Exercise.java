package pl.coderslab.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Exercise {


    private int id;
    private String title;
    private String description;


    public Exercise() {

    }

    public Exercise(int id) {
        this.id = id;
    }

    public Exercise(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.title = description;
    }

    public int getID() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public void saveExerciseToDB(Connection conn) throws SQLException {
        if (this.id == 0) {
            String sql = "INSERT INTO exercise (title, description)	VALUES	(?, ?)";
            String[] generatedColumns = {"ID"};
            PreparedStatement preparedStatement = conn.prepareStatement(sql, generatedColumns);
            preparedStatement.setString(1, this.title);
            preparedStatement.setString(2, this.description);
            preparedStatement.executeUpdate();
            ResultSet rsExercise = preparedStatement.getGeneratedKeys();
            if (rsExercise.next()) {
                this.id = rsExercise.getInt(1);
            }
        } else {
            String sql = "UPDATE	exercise	SET	title =?, description =?	where	id	=	?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, this.title);
            preparedStatement.setString(2, this.description);
            preparedStatement.setInt(3, this.id);
            preparedStatement.executeUpdate();
        }
    }


    static public Exercise loadExerciseById(Connection conn, int id) throws SQLException {
        String sql = "SELECT	*	FROM	exercise	where	id=?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet rsExercise = preparedStatement.executeQuery();
        if (rsExercise.next()) {
            Exercise loadedExercise = new Exercise();
            loadedExercise.id = rsExercise.getInt("id");
            loadedExercise.title = rsExercise.getString("title");
            loadedExercise.description = rsExercise.getString("description");
            return loadedExercise;
        }
        return null;
    }


    static public Exercise [] loadAllExercise (Connection conn) throws SQLException {
        ArrayList <Exercise> exercises = new ArrayList <Exercise>();
        String sql1 = "SELECT	*	FROM	exercise";
        PreparedStatement preparedStatement1 = conn.prepareStatement(sql1);
        ResultSet exerciseSet = preparedStatement1.executeQuery();
        while (exerciseSet.next()) {
            Exercise loadedExercise = new Exercise();
            loadedExercise.id = exerciseSet.getInt("id");
            loadedExercise.title = exerciseSet.getString("title");
            loadedExercise.description = exerciseSet.getString("desciption");
            exercises.add(loadedExercise);
        }
        Exercise [] exercArray = new Exercise [exercises.size()];
        exercArray = exercises.toArray(exercArray);
        return exercArray;
    }


    public void delete(Connection connection) throws SQLException {
        if (this.id != 0) {
            String sql2 = "DELETE	FROM exercise	WHERE	id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql2);
            preparedStatement.setInt(1, this.id);
            preparedStatement.executeUpdate();
            this.id = 0;
        }
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
