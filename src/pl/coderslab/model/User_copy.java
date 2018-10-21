//package pl.coderslab.model;
//
//import pl.coderslab.util.BCrypt;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//public class User_copy {
//
//    //atyrbuty do kolumny w bazie danych
//    private int id;
//    private String userName;
//    private String email;
//    private String password;
//    private UserGroup userGroup;   // obiekt z user_group (w pakiecie model stworzyc nową klase user_group)
//
//    public User_copy() {
//
//    }
//
//    // konstruktor
//    public User_copy(String userName, String email, String password, UserGroup userGroup) {
//        this.userName = userName;
//        this.email = email;
//        setPassword(password);   // this.setPassword(password);
//        this.userGroup = userGroup;
//    }
//
//    public int getId() {
//        return id;
//    }
//
////    public void setId(int id) {
////        this.id = id;                        // ten setter wywalic bo nie chce by ktos z zewnatrz wprowadzal te dane
////    }
//
//    public String getUserName() {
//        return userName;
//    }
//
//    public void setUserName(String userName) {
//        this.userName = userName;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public UserGroup getUserGroup() {
//        return userGroup;
//    }
//
//    public void setUserGrouproup(UserGroup userGrouproup) {
//        this.userGroup = userGrouproup;
//    }
//
//
//    // hasło przez setter bo bedzie szyfrowane
//    private void setPassword(String password) {
//        this.password = BCrypt.hashpw(password, BCrypt.gensalt());  // ta linijka będzie szyfrować hasła, gensalt , czyli generuja zaszyfrowane hasło
//    }
//
//
////    Zapisywanie	nowego	obiektu	do	bazy	danych
//    public	void	saveToDB(Connection conn)	throws SQLException {
//        if	(this.id	==	0)	{
//            String	sql	=	"INSERT	INTO	users(user_name,	email,	password, user_group_id)	VALUES	(?,	?,	?, ?)";
//            String[]	generatedColumns	=	{	"ID"	};
//            PreparedStatement preparedStatement	= conn.prepareStatement(sql, generatedColumns);
//            preparedStatement.setString(1,	this.userName);
//            preparedStatement.setString(2,	this.email);
//            preparedStatement.setString(3,	this.password);
//            preparedStatement.setInt(4, this.userGroup.getID());
//            preparedStatement.executeUpdate();
//            ResultSet rs	=	preparedStatement.getGeneratedKeys();
//            if	(rs.next())	{
//                this.id	=	rs.getInt(1);
//            }
//        }
//    }
//
//    static	public User_copy loadUserById(Connection	conn, int	id)	throws	SQLException	{
//        String	sql	=	"SELECT	*	FROM	users	where	id=?";
//        PreparedStatement	preparedStatement	=	conn.prepareStatement(sql);
//        preparedStatement.setInt(1,	id);
//        ResultSet	resultSet	=	preparedStatement.executeQuery();
//        if	(resultSet.next())	{
//            User_copy loadedUser	=	new User_copy();
//            loadedUser.id	=	resultSet.getInt("id");
//            loadedUser.userName	=	resultSet.getString("username");
//            loadedUser.password	=	resultSet.getString("password");
//            loadedUser.email	=	resultSet.getString("email");
//            loadedUser.userGroup = new UserGroup(1) ;     // usergroup jest typu..., majac id grupy wyciągnac cały obiekt grupy, MOZEMY tu zrobic nowego
//            return	loadedUser;}
//        return	null;}
//
//
//
//    @Override
//    public String toString() {
//        return "User{" +
//                "id=" + id +
//                ", userName='" + userName + '\'' +
//                ", email='" + email + '\'' +
//                ", password='" + password + '\'' +
//                '}';
//    }
//}
//
