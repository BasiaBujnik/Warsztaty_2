package pl.coderslab.model;

import pl.coderslab.util.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class User {

    //atrybuty do kolumny w bazie danych
    private int id;
    private String userName;
    private String email;
    private String password;
    private UserGroup userGroup;   // obiekt z user_group (w pakiecie model stworzyc nową klase user_group)

    public User () {

    }

    // konstruktor
    public User (String userName, String email, String password, UserGroup userGroup) {
        this.userName = userName;
        this.email = email;
        this.setPassword(password);   // setPassword(password);
        this.userGroup = userGroup;
    }

    public int getId() {
        return id;
    }

//    public void setId(int id) {
//        this.id = id;                        // ten setter wywalic bo nie chce by ktos z zewnatrz wprowadzal te dane
//    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    // hasło przez setter bo bedzie szyfrowane
    private void setPassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());  // ta linijka będzie szyfrować hasła, gensalt , czyli generuja zaszyfrowane hasło
    }

    public UserGroup getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }




//    Zapisywanie	nowego	obiektu	do	bazy	danych
    public	void	saveUserToDB(Connection conn)	throws SQLException {
        if	(this.id	==	0)	{                 // Zapisujemy	obiekt	do	bazy,	tylko	wtedy	gdy	jego	id	jest	równe	0.
            String	sql	=	"INSERT	INTO	users(user_name,	email,	password, user_group_id)	VALUES	(?,	?,	?, ?)";
            String[]	generatedColumns	=	{	"ID"	};
            PreparedStatement prepStmt	= conn.prepareStatement(sql, generatedColumns);
            prepStmt.setString(1,	this.userName);
            prepStmt.setString(2,	this.email);
            prepStmt.setString(3,	this.password);
            prepStmt.setInt(4, this.userGroup.getID());
            prepStmt.executeUpdate();                          // Update do ZAPISU
            ResultSet rs	=	prepStmt.getGeneratedKeys();   // wyciągnięcie kluczy o które prosiliśmy i przypisanie do zmiennej rs
            if	(rs.next())	{
                this.id	=	rs.getInt(1);                // Pobieramy	wstawiony	do	bazy	identyfikator,	a	następnie	ustawiamy	id	obiektu.
            }
        } // do modyfikacji obiektu dopisany else, tu kod	aktualizujący	dane	znajdujące	się w bazie
        else {
            String	sql	=	"UPDATE	users	SET	username=?,	email=?, password=?, user_group_id=?	where	id	=	?";
            PreparedStatement	preparedStatement	=	conn.prepareStatement(sql);
            preparedStatement.setString(1,	this.userName);
            preparedStatement.setString(2,	this.email);
            preparedStatement.setString(3,	this.password);
            preparedStatement.setInt(4, this.userGroup.getID());
            preparedStatement.setInt(5,	this.id);
            preparedStatement.executeUpdate();
        }
    }

    // wczytywanie obiektu z bazy danych
    static	public	User	loadUserById(Connection	conn,	int	id)	throws	SQLException {  // Metoda	jest	statyczna	–	możemy	jej	używać	na	klasie,	zamiast	na	obiekcie.
        String	sql	=	"SELECT	*	FROM	users	where	id=?";
        PreparedStatement	preparedStatement	=	conn.prepareStatement(sql);
        preparedStatement.setInt(1,	id);
        ResultSet	resultSet	=	preparedStatement.executeQuery();
        if	(resultSet.next())	{
            User	loadedUser	=	new	User();  // Tworzymy	nowy	obiekt	typu	User	i	ustawiamy	mu	odpowiednie	parametry.
            // 	Jesteśmy	w	środku	klasy, mamy	zatem	dostęp	do	własności	prywatnych,	mimo	korzystania	z	metody	statycznej.
            loadedUser.id	=	resultSet.getInt("id");
            loadedUser.userName	=	resultSet.getString("user_name");
            loadedUser.password	=	resultSet.getString("password");
            loadedUser.email	=	resultSet.getString("email");
            loadedUser.userGroup = new UserGroup(1) ;     // majac id grupy wyciągnac cały obiekt grupy, MOZEMY tu zrobic nowy obiekt userGroup
            return	loadedUser;                               // zwracamy obiekt użytkownika
        }
        return	null;}                                        // albo null




    // wczytywanie wielu oiektów z bazy danych
    static	public	User[]	loadAllUsers(Connection	conn)	throws	SQLException { // Pomocniczo	wykorzystujemy	obiekt	klasy	ArrayList,	traktujmy	go	jako	rozszerzalną	tablicę.
        ArrayList<User>	users	=	new ArrayList<User>();
        String	sql1	=	"SELECT	*	FROM	users";
        PreparedStatement	preparedStatement1	=	conn.prepareStatement(sql1);
        ResultSet	groupSet	=	preparedStatement1.executeQuery();
        while	(groupSet.next())	{   // Tworzymy	nowy	obiekt	użytkownika	i	ustawiamy	mu	odpowiednie	parametry.
            User	loadedUser	=	new	User();
            loadedUser.id	=	groupSet.getInt("id");
            loadedUser.userName	=	groupSet.getString("user_name");
            loadedUser.password	=	groupSet.getString("password");
            loadedUser.email	=	groupSet.getString("email");
            users.add(loadedUser);}               // Po	stworzeniu	i	wypełnieniu	obiektu	danymi	dodajemy	go	do	listy
          // Tworzymy	tablicę	elementów	typu	User	o	rozmiarze	pobranym	z	listy. Przekształcamy	listę	na	tablicę.
        User[]	uArray	=	new	User[users.size()];
        uArray	=	users.toArray(uArray);
        return	uArray;
    }


    // metoda na usunięcie obiektu
    public	void	delete (Connection connection)	throws	SQLException {
        if	(this.id !=	0)	{
            String	sql2	= "DELETE	FROM	users	WHERE	id=?";
            PreparedStatement preparedStatement	=	connection.prepareStatement(sql2);
            preparedStatement.setInt(1,	this.id);
            preparedStatement.executeUpdate();
            this.id	= 0; // Usunęliśmy	obiekt,	zmieniamy	zatem	jego	id	na	0.
        }
    }

    // metoda na pobranie	wszystkich	członków	danej	grupy
    public static User [] loadAllByGroupId (Connection conn, int groupId) throws SQLException {
        ArrayList <User> 
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }



}

