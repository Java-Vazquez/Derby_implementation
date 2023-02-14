/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package derby_up;

import org.apache.derby.authentication.UserAuthenticator;
import java.io.FileInputStream;
import java.util.Properties;
import java.sql.SQLException;
/**
  * A simple example of a specialized Authentication scheme.
  * The system property 'derby.connection.requireAuthentication'
  * must be set
  * to true and 'derby.authentication.provider' must
  * contain the full class name of the overriden authentication
  * scheme,  i.e., the name of this class.
  *
  * @see org.apache.derby.authentication.UserAuthenticator 
  */

public class MyAuthenticationSchemeImpl implements
        UserAuthenticator {
    private static final String USERS_CONFIG_FILE = "myUsers.cfg";
    private static Properties usersConfig;

    // Constructor
    // We get passed some Users properties if the 
    // authentication service could not set them as 
    // part of the System properties.
    //
    public MyAuthenticationSchemeImpl() {
    }

    /* Static block where we load the users definition from a
       users configuration file. */
    static {
        /* Load users config file as Java properties.
           File must be in the same directory where
           Derby is started.
           Otherwise, full path must be specified. */
        FileInputStream in = null;
        usersConfig = new Properties();
        try {
            in = new FileInputStream(USERS_CONFIG_FILE);
            usersConfig.load(in);
            in.close();
        } catch (java.io.IOException ie) {
            // No Config file. Raise error message
            System.err.println(
                "WARNING: Error during Users Config file retrieval");
            System.err.println("Exception: " + ie);
        }
    }

    /**
     * Authenticate the passed-in user's credentials.
     * A more complex class could make calls
     * to any external users directory.
     *
     * @param userName               The user's name
     * @param userPassword           The user's password 
     * @param databaseName           The database 
     * @param info                   Additional jdbc connection info.
     * @exception SQLException on failure
     */
    public boolean authenticateUser(String userName,
            String userPassword,
            String databaseName,
            Properties info)
        throws SQLException {
        /* Specific Authentication scheme logic.
           If user has been authenticated, then simply return.
           If user name and/or password are invalid, 
           then raise the appropriate exception.
            
           This example allows only users defined in the
           users config properties object.

           Check if the passed-in user has been defined for the system.
           We expect to find and match the property corresponding to
           the credentials passed in. */
        if (userName == null)
            // We do not tolerate 'guest' user for now.
            return false;

        /* Check if user exists in our users config (file)
           properties set.
           If we did not find the user in the users config set, then
           try to find if the user is defined as a System property. */
        String actualUserPassword;
        actualUserPassword = usersConfig.getProperty(userName);
        if (actualUserPassword == null)
            actualUserPassword = System.getProperty(userName);
        if (actualUserPassword == null)
            // No such passed-in user found
            return false;
        // Check if the password matches
        if (!actualUserPassword.equals(userPassword))
            return false;
        // Now, check if the user is a valid user of the database
        if (databaseName != null) {
         /* If database users restriction lists are present, then 
            check if there is one for this database and if so, 
            check if the user is a valid one for that database.
            For this example, the only user we authorize in database
            DarkSide is user 'DarthVader'. This is the only database
            users restriction list we have for this example.
            We authorize any valid (login) user to access the
            OTHER databases in the system.
            Note that database users ACLs could be set in the same
            properties file or a separate one and implemented as you
            wish. */
            if (databaseName.equals("DarkSide")) {
                // Check if user is a valid one
                if (!userName.equals("DarthVader"))
                    // This user is not a valid one of the passed-in
                    return false;
            }
        }
        // The user is a valid one in this database
        return true;
    }
}
