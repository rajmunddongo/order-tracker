package onlab.aut.bme.hu.java.security;

import java.lang.reflect.Array;
import java.util.List;

public class SecurityConstants{

        public static final List<String> DISABLE_SECURITY = List.of("/index.html", "/", "/home", "/login","/api/merchants,/api/authenticate");
        public static final String SECRET = "MY_VERY_SECRET_SECRET_KEY";
        public static final long EXPIRATION_TIME = 900_000; // 15 mins
        public static final String TOKEN_PREFIX = "Bearer ";
        public static final String HEADER_STRING = "Authorization";
        public static final String SIGN_UP_URL = "/api/services/controller/user";
}
