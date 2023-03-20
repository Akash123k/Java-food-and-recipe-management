import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener {

    // form fields
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public Login() {
        super("Login Form");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // create form components
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Login");
        loginButton.addActionListener(this);

        // add form components to content pane
        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(loginButton);

        // set bounds
        usernameLabel.setBounds(20, 20, 80, 20);
        usernameField.setBounds(100, 20, 150, 20);
        passwordLabel.setBounds(20, 50, 80, 20);
        passwordField.setBounds(100, 50, 150, 20);
        loginButton.setBounds(100, 80, 100, 20);
    }

    public void actionPerformed(ActionEvent e) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // validate form fields
        if (username.equals("") || password.equals("")) {
            JOptionPane.showMessageDialog(this, "Please enter username and password.");
        } else {
            try {
                // create database connection
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/akash", "root", "root");

                // create SQL statement to check if user exists
                String query = "SELECT * FROM users WHERE username = ? AND password = ?";
                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setString(1, username);
                stmt.setString(2, password);

                // execute SQL statement and get result set
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    JOptionPane.showMessageDialog(this, "Login successful!");

                    // redirect to index page
                    IndexPage indexPage = new IndexPage();
                    indexPage.setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid username or password.");
                }

                // close database connection
                rs.close();
                stmt.close();
                con.close();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Login failed. Error: " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        Login login = new Login();
        login.setVisible(true);
    }
}
