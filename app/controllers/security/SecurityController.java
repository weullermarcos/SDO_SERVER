package controllers.security;

import controllers.HomeController;
import controllers.Secure.Security;

/**
 * @author jgomes - Jefferson Chaves Gomes | 12/09/2014 - 17:55:15
 */
public class SecurityController extends Security {

    public static boolean authenticate(final String username, final String password) {
        if (username.equals("master") && password.equals("sdoserver")) {
            return true;
        }
        return false;
    }

    public static void onAuthenticated() {
        HomeController.index();
    }
}
