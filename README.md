
# Traditional Way Approach -  Ubuntu 21.04 Server

#### Requirements

- Git on linux server if not found run

```
sudo apt install git
```

- Java 8 Installation

You can install Java 8 on Ubuntu 21.04 from OpenJDK binaries available on the default upstream repositories. To do this, run the command:

```
sudo apt install openjdk-8-jdk
```
Then Confirm if all is GOOD!!
```
java -version
```

Setting the JAVA_HOME Environment Variable

Many programs written using Java use the `JAVA_HOME` environment variable to determine the Java installation location.

To set this environment variable, first determine where Java is installed. Use the update-alternatives command:

(Managing Java Command)
  
  ```
  sudo update-alternatives --config java

  ```
Copy the path from your preferred installation. Then open ``/etc/environment`` using nano or your favorite text editor:

```
sudo nano /etc/environment
```

At the end of this file, add the following line, making sure to replace the highlighted path with your own copied path, but do not include the bin/ portion of the path:

```
JAVA_HOME="/usr/lib/jvm/java-11-openjdk-amd64"
```

Modifying this file will set the JAVA_HOME path for all users on your system.

Save the file and exit the editor.

Now reload this file to apply the changes to your current session:

```
source /etc/environment
```

Verify that the environment variable is set:

```
echo $JAVA_HOME
```

## NB: 
Other users will need to execute the command source /etc/environment or log out and log back in to apply this setting.

- Installing Apache Maven on Ubuntu 21.04 with `apt`

Installing Maven on Ubuntu using `apt` is a simple, straightforward process.

Update the package index and install Maven by entering the following commands:

```
sudo apt update
sudo apt install maven
```

To verify the installation, run `mvn -version`

- Installation of Apache Tomcat 9.0.21

Update the apt repository to bring the pre-installed packages up to date

```
apt update
```

Change the directory to /usr/local

```
cd /usr/local
```

Download the apache tomcat 9.0.21 version usning wget followed by the mentioned downloaded link

```
wget  https://downloads.apache.org/tomcat/tomcat-9/v9.0.45/bin/apache-tomcat-9.0.45.zip
```

List the contents to view the zip file of apache tomcat 9.0.21

```
ll
```

Extract the zip file of apache tomcat

```
unzip apache-tomcat-9.0.21.zip
```

View the extracted directory by listing the contents

```
ll
```
Rename the extracted directory

```
mv apache-tomcat-9.0.21 tomcat9
```

Change the directory to tomcat9 and bin as well

```
cd tomcat9/ && cd bin
```

Configure the environment variable for apache tomcat 9.0.21

```
echo "export CATALINA_HOME="/usr/local/tomcat9"" >> ~/.bashrc
```

Reload the environment file

```
$ /usr/local/tomcat9/bin# source ~/.bashrc
```

Assign executable permissions to both startup.sh and catalina.sh

```
$ /usr/local/tomcat9/bin# chmod a+x catalina.sh
$ /usr/local/tomcat9/bin# chmod a+x startup.sh
```

Now,run the startup.sh script to start the tomcat

```
$ /usr/local/tomcat9/bin# ./startup.sh
```

Hot Tip: You can also use `./catalina.sh run` to start your app. This command will print the logs to your terminal so you don’t need to tail them to see what’s happening.

Verify the tomcat port number usning netstat

```
$ /usr/local/tomcat9/bin# netstat -tulpn | grep 8080
```


NB: If nothing happens check if port is opening for traffics

- Install PostGresQL Database

Ubuntu’s default repositories contain Postgres packages, so you can install these using the apt packaging system.

Then, install the Postgres package along with a -contrib package that adds some additional utilities and functionality:

```
sudo apt install postgresql postgresql-contrib
```

By default, Postgres uses a concept called “roles” to handle authentication and authorization. These are, in some ways, similar to regular Unix-style accounts, but Postgres does not distinguish between users and groups and instead prefers the more flexible term “role”.

Upon installation, Postgres is set up to use ident authentication, meaning that it associates Postgres roles with a matching Unix/Linux system account. If a role exists within Postgres, a Unix/Linux username with the same name is able to sign in as that role.

The installation procedure created a user account called postgres that is associated with the default Postgres role. In order to use Postgres, you can log into that account.

```
sudo -i -u postgres
```

You can now access the PostgreSQL prompt immediately by typing:

```
$ psql
```

Tip: Accessing a Postgres Prompt Without Switching Accounts

```
sudo -u postgres psql
```

Change postgres role password, after login `sudo -u postgres psql`
```
 \password <username>.
```

Creating a New Database

```
sudo -u postgres createdb <db-name>
```

- Create a WAR File from Your Spring Boot Project

You now need to create a WAR file from your Spring Boot application. Add the following just after the <description> node in your pom.xml.

```
<packaging>war</packaging>
```

Remove the embedded Tomcat server by adding the following to your dependencies list:

```
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-tomcat</artifactId>
   <scope>provided</scope>
</dependency>
```

Finally enable your application as a servlet by extending your main class with SpringBootServletInitializer:

```
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class TodographqlApplication extends SpringBootServletInitializer {
    ...
}
```

Now package your application with the following command:

```
mvn package
```

or 

You should see a message like the following:

```
[INFO] Building war: /path/to/target/<project-name>-0.0.1-SNAPSHOT.war
```

Take note where your new .war lives.

- Deploy a WAR to Tomcat from the Browser

You may have noticed that on the right-hand side of the Tomcat welcome screen was three buttons: Server Status, Manager App, and Host Manager. You can deploy a WAR from Manager App but it needs authentication (and there are no users defined by default).
 
Add the following to `conf/tomcat-users.xml` in your Tomcat directory:

```
<user username="username" password="secret" roles="manager-gui" />
```

You’ll need to restart Tomcat for this change to take effect. Because you started it directly you need to stop the process yourself. Find the process id using `ps aux | grep tomcat`.

Here my process ID is 11813. Use the kill command to kill it.

```
kill ID
```

Restart the server by using startup.sh as before. When you click on the Manager App button the user details you entered above should get you to the manager screen.

Scroll to the bottom to the WAR file to deploy section. Click Browse… and select the WAR file from before. Click Deploy.










