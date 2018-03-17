package utils;

/**
 * Created by riya on 13/2/18.
 */

public class UrlConstant {

    public static String base_url="http://admin.typdelhi.org/";
    public static  String BLOOD_GROUP_URL="http://admin.typdelhi.org/api/User/GetBloodGroup";
    public static String TYP_Location=base_url+"api/User/GetLocation?Id=0";
    public static String CREATE_USER=base_url+"api/User/CreateUser";
    public static String RESEND_OTP=base_url+"api/User/ResendOTPRegistration";
    public static  String LOGIN_URL=base_url+"api/User/Login";
    public static  String VERIFY_URL=base_url+"api/User/VerifyOTP";
    public static  String Forgot_Password_URL=base_url+"api/User/ForgotPassword";
    public static String USEFULL_LINK_URL = base_url + "api/User/GetUseFullLink";
    public static String NEWS_URL = base_url + "api/User/GetNews";

}
