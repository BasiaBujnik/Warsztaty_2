package pl.coderslab.model;

import java.sql.*;
import java.util.ArrayList;

public class Solution {

    private int id;
    private Date created;
    private Date updated;
    private String description;
    private Exercise exercise;
    private User user;


    public Solution() {

    }

    public Solution (int id, Date created, Date updated, String description, Exercise exercise, User user) {
        this.id = id;
        this.created = created;
        this.updated = updated;
        this.description = description;
        this.exercise = exercise;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void saveSolutionToDB(Connection conn) throws SQLException {
        if (this.id == 0) {
            String sql = "INSERT INTO solution (created, updated, description, exercise_id, user_id)	VALUES	(?, ?, ?)";
            String[] generatedColumns = {"ID"};
            PreparedStatement preparedStatement = conn.prepareStatement(sql, generatedColumns);
            preparedStatement.setDate(1, this.created);
            preparedStatement.setDate(2, this.updated);
            preparedStatement.setString(3, this.description);
            preparedStatement.executeUpdate();
            ResultSet rsSolution = preparedStatement.getGeneratedKeys();
            if (rsSolution.next()) {
                this.id = rsSolution.getInt(1);
            }
        } else {
            String sql = "UPDATE solution	SET	created =?, updated =?, description =?	where	id	=	?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setDate (1, this.created);
            preparedStatement.setDate(2, this.updated);
            preparedStatement.setString(3, this.description);
            preparedStatement.setInt(4, this.id);
            preparedStatement.executeUpdate();
        }
    }


    static public Solution loadedSolutionById(Connection conn, int id) throws SQLException {
        String sql = "SELECT	*	FROM	solution	where	id=?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet rsSolution = preparedStatement.executeQuery();
        if (rsSolution.next()) {
            Solution loadedSolution = new Solution();
            loadedSolution.id = rsSolution.getInt("id");
            loadedSolution.created = rsSolution.getDate("created");
            loadedSolution.updated = rsSolution.getDate("updated");
            loadedSolution.description = rsSolution.getString("description");
            return loadedSolution;
        }
        return null;
    }


    static public Solution [] loadAllSolution (Connection conn) throws SQLException {
        ArrayList <Solution> solutions = new ArrayList <Solution>();
        String sql1 = "SELECT	*	FROM solution";
        PreparedStatement preparedStatement1 = conn.prepareStatement(sql1);
        ResultSet solutionSet = preparedStatement1.executeQuery();
        while (solutionSet.next()) {
            Solution loadedSolution = new Solution();
            loadedSolution.id = solutionSet.getInt("id");
            loadedSolution.created = solutionSet.getDate("created");
            loadedSolution.updated = solutionSet.getDate("updated");
            loadedSolution.description = solutionSet.getString("desciption");
            //wczytac exercise i user
            loadedSolution.exercise = new Exercise();
            loadedSolution.user = new User();

            solutions.add(loadedSolution);
        }
        Solution [] solutionArray = new Solution [solutions.size()];
        solutionArray = solutions.toArray(solutionArray);
        return solutionArray;
    }


    public void delete(Connection connection) throws SQLException {
        if (this.id != 0) {
            String sql2 = "DELETE	FROM solution WHERE	id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql2);
            preparedStatement.setInt(1, this.id);
            preparedStatement.executeUpdate();
            this.id = 0;
        }
    }

    // dodatkowa metoda na pobranie	wszystkich	rozwiązań	danego	użytkownika
    public static Solution [] loadAllByUserId (Connection conn, int userId) throws SQLException {
        ArrayList <Solution> allSolByUserId = new ArrayList <Solution>();
        String sql1 = "SELECT	*	FROM solution WHERE users.id = ?";
        PreparedStatement preparedStatement1 = conn.prepareStatement(sql1);
        preparedStatement1.setInt(1, userId);
        ResultSet solutionSet = preparedStatement1.executeQuery();

        while (solutionSet.next()) {
            Solution loadedSolution = new Solution();
            loadedSolution.id = solutionSet.getInt("id");
            loadedSolution.created = solutionSet.getDate("created");
            loadedSolution.updated = solutionSet.getDate("updated");
            loadedSolution.description = solutionSet.getString("desciption");
            loadedSolution.exercise = new Exercise();
            loadedSolution.user = new User();

            allSolByUserId.add(loadedSolution);
        }
        Solution [] allSolutionArray = new Solution [allSolByUserId.size()];
        allSolutionArray = allSolByUserId.toArray(allSolutionArray);
        return allSolutionArray;
    }

    // dodatkowa metoda na pobranie	wszystkich	rozwiązań	danego	zadania,	posortowanych	od	najnowszego	do	najstarszego
    public static Solution [] loadAllByExerciseId (Connection conn, int exerciseId) throws SQLException {
        ArrayList<Solution> allSolByExerciseId = new ArrayList<Solution>();
        String sql1 = " select * from solution WHERE exercise.id = ? ORDER BY updated ASC;";
        PreparedStatement preparedStatement1 = conn.prepareStatement(sql1);
        preparedStatement1.setInt(1, exerciseId);
        ResultSet solutionSet = preparedStatement1.executeQuery();

        while (solutionSet.next()) {
            Solution loadedSolution = new Solution();
            loadedSolution.id = solutionSet.getInt("id");
            loadedSolution.created = solutionSet.getDate("created");
            loadedSolution.updated = solutionSet.getDate("updated");
            loadedSolution.description = solutionSet.getString("desciption");
            loadedSolution.exercise = new Exercise();
            loadedSolution.user = new User();

            allSolByExerciseId.add(loadedSolution);
        }
        Solution[] allSolutionArray = new Solution[allSolByExerciseId.size()];
        allSolutionArray = allSolByExerciseId.toArray(allSolutionArray);
        return allSolutionArray;
    }





    public String toString(Solution solution) {
        return "Solution{" +
                "id=" + id +
                ", created=" + created +
                ", updated=" + updated +
                ", description='" + description + '\'' +
                '}';
    }
}