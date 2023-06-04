import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

public class PagesToBeShownToUser {

    public static  <T> SocketModel signInPage(UserToBeSigned user) throws SQLException {
        T out = SQLConnection.getUsers().select(user);

        if (out instanceof UserToBeSigned) {
            return new SocketModel(Api.TYPE_SIGNIN, (UserToBeSigned)out);
        } else {
            return new SocketModel(Api.TYPE_SIGNIN, (ResponseOrErrorType) out, null);
        }
    }


    public static SocketModel signUpPage(UserToBeSigned userModule) throws SQLException {
        UsersTable table = SQLConnection.getUsers();
        if (table.userNameExists(userModule.getUsername())){
            return new SocketModel(Api.TYPE_SIGNUP, ResponseOrErrorType.DUPLICATE_USERNAME, false);
        }
        if (table.emailExists(userModule.getEmail())){
            return new SocketModel(Api.TYPE_SIGNUP, ResponseOrErrorType.DUPLICATE_EMAIL, true);
        }
        if (table.phoneNumberExists(userModule.getPhoneNumber())){
            return new SocketModel(Api.TYPE_SIGNUP, ResponseOrErrorType.DUPLICATE_PHONENUMBER, false);
        }
        if (table.insert(userModule)) {
            userModule.setUserDbId(table.getUserFromDatabase(userModule.getUsername()).getDatabaseId());
            return new SocketModel(Api.TYPE_SIGNUP, ResponseOrErrorType.SUCCESSFUL, true);
        } else {
            return new SocketModel(Api.TYPE_SIGNUP, ResponseOrErrorType.UNSUCCESSFUL, false);
        }
    }

    public static ResponseOrErrorType updateProfile(User thisUser) {
        if(SafeRunning.safe(() -> {
            UsersTable out = SQLConnection.getUsers();
            out.updateUsername(thisUser.getDatabaseId(), thisUser.getUsername());
            out.updateHeader(thisUser.getDatabaseId(), thisUser.getHeader());
            out.updateBio(thisUser.getDatabaseId(), thisUser.getBio());
            out.updateWebsite(thisUser.getDatabaseId(), thisUser.getWebsite());
            out.updateAvatar(thisUser.getDatabaseId(), thisUser.getAvatar());
            out.updateLocation(thisUser.getDatabaseId(), thisUser.getLocation());
            out.updateBirthDate(thisUser.getDatabaseId(), thisUser.getBirthDate());
            out.updateFirstName(thisUser.getDatabaseId(), thisUser.getFirstName());
            out.updateLastName(thisUser.getDatabaseId(), thisUser.getLastName());
            out.updateEmail(thisUser.getDatabaseId(), thisUser.getEmail());
            out.updatePassword(thisUser.getDatabaseId(), thisUser.getPassword());
            out.updatePhoneNumber(thisUser.getDatabaseId(), thisUser.getPhoneNumber());
            out.updateRegion(thisUser.getDatabaseId(), thisUser.getRegionOrCountry());
        })){
            return ResponseOrErrorType.SUCCESSFUL;
        }else {
            return ResponseOrErrorType.UNSUCCESSFUL;
        }
    }

//    public static ResponseOrErrorType setProfileHeader(String JWT, String pathHeader) {
//        if (SafeRunning.safe(() -> {
//            SQLConnection.getUsers()
//        })){
//            return ResponseOrErrorType.SUCCESSFUL;
//        }else {
//            return ResponseOrErrorType.UNSUCCESSFUL;
//        }
//    }

//    public static ResponseOrErrorType setProfileBio(String JWT, String bio) {
//        final int MAX_LENGTH = 160;
//        if (bio.length() > MAX_LENGTH){
//            return ResponseOrErrorType.OUT_OF_BOUND_LENGTH;
//        }
//        if (SafeRunning.safe(() -> {
//            SQLConnection.getUsers().updateBio(JWT, bio);
//        })){
//            return ResponseOrErrorType.SUCCESSFUL;
//        }else {
//            return ResponseOrErrorType.UNSUCCESSFUL;
//        }
//
//    }

//    public static ResponseOrErrorType setLocation(String JWT, String location) {
//        if (SafeRunning.safe(() -> {
//            SQLConnection.getUsers().updateLocation(JWT, location);
//        })){
//            return ResponseOrErrorType.SUCCESSFUL;
//        }else {
//            return ResponseOrErrorType.UNSUCCESSFUL;
//        }
//    }

//    public static ResponseOrErrorType setWebsite(String JWT, String website) {
//        ResponseOrErrorType isValidLink = Validate.validateWebsite(website);
//        if (isValidLink == ResponseOrErrorType.INVALID_LINK){
//            //TODO handle it
//        }
//        if (SafeRunning.safe(() -> {
//            SQLConnection.getUsers().updateWebsite(JWT, website);
//        })){
//            return ResponseOrErrorType.SUCCESSFUL;
//        }else {
//            return ResponseOrErrorType.UNSUCCESSFUL;
//        }
//    }

    public static ResponseOrErrorType follow(String JWTusername, String followingUsername){
        UsersTable out = SQLConnection.getUsers();
        User currentUser = null;
        try {
            currentUser = out.getUserFromDatabase(JWTusername);
            currentUser.follow(followingUsername);
        } catch (SQLException e) {
            return ResponseOrErrorType.UNSUCCESSFUL;
        }
        return ResponseOrErrorType.SUCCESSFUL;
    }

    public static ResponseOrErrorType unfollow(String JWTusername, String unfollowingUsername){
        UsersTable out = SQLConnection.getUsers();
        User currentUser = null;
        try {
            currentUser = out.getUserFromDatabase(JWTusername);
            currentUser.unfollow(unfollowingUsername);
        } catch (SQLException e) {
            return ResponseOrErrorType.UNSUCCESSFUL;
        }
        return ResponseOrErrorType.SUCCESSFUL;
    }

    public static ResponseOrErrorType block(String JWTusername, String blockingUsername){
        UsersTable out = SQLConnection.getUsers();
        User currentUser = null;
        try {
            currentUser = out.getUserFromDatabase(JWTusername);
            currentUser.block(blockingUsername);
        } catch (SQLException e) {
            return ResponseOrErrorType.UNSUCCESSFUL;
        }
        return ResponseOrErrorType.SUCCESSFUL;
    }

    public static SocketModel addTweet(Tweet tweet) {
        return TweetsFileConnection.addTweet(tweet);
    }
    public SocketModel tweetShowPage(){
        return null;
    }

    public void homePage() {

    }

    public static HashSet<String> searchInUsers(String key) throws SQLException {
        UsersTable out = SQLConnection.getUsers();
        return out.searchInUsers(key);

    }

    public static SocketModel goToTheUsersProfile(String userName){///////////////////////////////////////////////////////////////////////
        UsersTable out = SQLConnection.getUsers();
        try {
            User profileUser = out.getUserFromDatabase(userName);
            return new SocketModel(null, ResponseOrErrorType.SUCCESSFUL, profileUser);
        } catch (SQLException e) {
            return new SocketModel(null, ResponseOrErrorType.USER_NOTFOUND, false);
        }
        //TODO show the profile in the scenebuilder
    }
}
