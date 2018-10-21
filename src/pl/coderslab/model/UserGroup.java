package pl.coderslab.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class UserGroup {

    private int id;
    private String name;


    public UserGroup () {

    }
    // konstruktor do ustawienia id
    public UserGroup(int id) {
        this.id = id;
    }

    public UserGroup (int id, String name) {
        this.id = id;
        this.name = name;
    }


    // dopisac getter do id
    public int getID () {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //    Zapisywanie	nowego	obiektu	do	bazy	danych
    public	void	saveGroupToDB (Connection conn)	throws SQLException {
        if	(this.id	==	0)	{
            String	sql	=	"INSERT	INTO	user_group (name)	VALUES	(?)";
            String[]	generatedColumns	=	{	"ID"	};
            PreparedStatement preparedStatement	= conn.prepareStatement(sql, generatedColumns);
            preparedStatement.setString(1,	this.name);
            preparedStatement.executeUpdate();                          // Update do ZAPISU
            ResultSet rsGroup	=	preparedStatement.getGeneratedKeys();   // wyciągnięcie kluczy o które prosiliśmy i przypisanie do zmiennej rs
            if	(rsGroup.next())	{
                this.id	= rsGroup.getInt(1);                // Pobieramy	wstawiony	do	bazy	identyfikator,	a	następnie	ustawiamy	id	obiektu.
            }
        } // do modyfikacji obiektu dopisany else, tu kod	aktualizujący	dane	znajdujące	się w bazie
        else {
            String	sql	=	"UPDATE	user_group	SET	name=?	where	id	=	?";
            PreparedStatement	preparedStatement	=	conn.prepareStatement(sql);
            preparedStatement.setString(1, this.name);
            preparedStatement.setInt(2, this.id);
            preparedStatement.executeUpdate();
        }
    }

    // wczytywanie obiektu z bazy danych
    static	public	UserGroup	loadUserGroupById (Connection	conn,	int	id)	throws	SQLException {
        String	sql	=	"SELECT	*	FROM	user_group	where	id=?";
        PreparedStatement	preparedStatement	=	conn.prepareStatement(sql);
        preparedStatement.setInt(1,	id);
        ResultSet	rsGroup	=	preparedStatement.executeQuery();
        if	(rsGroup.next())	{
            UserGroup	loadedUserGroup	=	new UserGroup();
            loadedUserGroup.id = rsGroup.getInt("id");
            loadedUserGroup.name = rsGroup.getString("name");;
            return	loadedUserGroup;}
            return	null;
    }

    // wczytywanie wielu oiektów z bazy danych
    static	public	UserGroup[]	loadAllUserGroup (Connection	conn)	throws	SQLException { // Pomocniczo	wykorzystujemy	obiekt	klasy	ArrayList,	traktujmy	go	jako	rozszerzalną	tablicę.
        ArrayList<UserGroup> userGroups	=	new ArrayList<UserGroup>();
        String	sql1	=	"SELECT	*	FROM	user_group";
        PreparedStatement	preparedStatement1	=	conn.prepareStatement(sql1);
        ResultSet	groupSet	=	preparedStatement1.executeQuery();
        while	(groupSet.next())	{   // Tworzymy	nowy	obiekt	użytkownika	i	ustawiamy	mu	odpowiednie	parametry.
            UserGroup	loadedUserGroup	=	new UserGroup();
            loadedUserGroup.id	=	groupSet.getInt("id");
            loadedUserGroup.name	=	groupSet.getString("name");

            userGroups.add(loadedUserGroup);}               // Po	stworzeniu	i	wypełnieniu	obiektu	danymi	dodajemy	go	do	listy
        // Tworzymy	tablicę	elementów	typu	User	o	rozmiarze	pobranym	z	listy. Przekształcamy	listę	na	tablicę.
        UserGroup [] uGrArray	=	new	UserGroup [userGroups.size()];	// userGroup to nazwa listy
        uGrArray =	userGroups.toArray(uGrArray);
        return	uGrArray;
    }

    // metoda na usunięcie obiektu
    public	void	delete (Connection connection)	throws	SQLException {
        if	(this.id !=	0)	{
            String	sql2	= "DELETE	FROM	user_group	WHERE	id=?";
            PreparedStatement preparedStatement	=	connection.prepareStatement(sql2);
            preparedStatement.setInt(1,	this.id);
            preparedStatement.executeUpdate();
            this.id	= 0;
        }
    }


    @Override
    public String toString() {
        return "UserGroup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
