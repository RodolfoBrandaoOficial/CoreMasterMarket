package CoreMasterMarket.config;

public class ConfigReal {

    public static final String urlAPI = "http://localhost:8081";
    public static String token;
    public static String GlobalToken;
    public static String userId;
    public static String userLogin;
    public static String userPassword;
    public static String userRole;
    public static String userName;
    public static String userFullname;
    public static String userUsername;
    public static String userProfileImg;

    // Métodos getters e setters para acessar e modificar os dados
    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        ConfigReal.token = token;
        GlobalToken = token;
    }

    public static String getUserId() {
        return userId;
    }

    public static void setUserId(String userId) {
        ConfigReal.userId = userId;
    }

    public static String getUserLogin() {
        return userLogin;
    }

    public static void setUserLogin(String userLogin) {
        ConfigReal.userLogin = userLogin;
    }

    public static String getUserPassword() {
        return userPassword;
    }

    public static void setUserPassword(String userPassword) {
        ConfigReal.userPassword = userPassword;
    }

    public static String getUserRole() {
        return userRole;
    }

    public static void setUserRole(String userRole) {
        ConfigReal.userRole = userRole;
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        ConfigReal.userName = userName;
    }

    public static String getUserFullname() {
        return userFullname;
    }

    public static void setUserFullname(String userFullname) {
        ConfigReal.userFullname = userFullname;
    }

    public static String getUserUsername() {
        return userUsername;
    }

    public static void setUserUsername(String userUsername) {
        ConfigReal.userUsername = userUsername;
    }

    public static String getUserProfileImg() {
        return userProfileImg;
    }

    public static void setUserProfileImg(String userProfileImg) {
        ConfigReal.userProfileImg = userProfileImg;
    }
}
