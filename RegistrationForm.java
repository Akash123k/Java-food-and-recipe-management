import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class RegistrationForm extends JFrame implements ActionListener {

    // form fields
    private JTextField nameField, emailField;
    private JPasswordField passwordField;
    private JButton registerButton;

    public RegistrationForm() {
        super("Registration Form");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // create form components
        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField(20);
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);
        registerButton = new JButton("Register");
        registerButton.addActionListener(this);

        // add form components to content pane
        add(nameLabel);
        add(nameField);
        add(emailLabel);
        add(emailField);
        add(passwordLabel);
        add(passwordField);
        add(registerButton);

        // set bounds
        nameLabel.setBounds(20, 20, 50, 20);
        nameField.setBounds(100, 20, 150, 20);
        emailLabel.setBounds(20, 50, 50, 20);
        emailField.setBounds(100, 50, 150, 20);
        passwordLabel.setBounds(20, 80, 70, 20);
        passwordField.setBounds(100, 80, 150, 20);
        registerButton.setBounds(100, 120, 100, 20);
    }

    public void actionPerformed(ActionEvent e) {
        String name = nameField.getText();
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());

        // validate form fields
        if (name.equals("") || email.equals("") || password.equals("")) {
            JOptionPane.showMessageDialog(this, "Please fill out all fields.");
        } else {
            try {
                // create database connection
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/akash", "root", "root");

                // create SQL statement to insert user data into database
                String query = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";
                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setString(1, name);
                stmt.setString(2, email);
                stmt.setString(3, password);

                // execute SQL statement and close database connection
                stmt.executeUpdate();
                con.close();

                JOptionPane.showMessageDialog(this, "Registration successful!");

                  // redirect to login form
                Login login= new Login();
                login.setVisible(true);
                dispose();
                






            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Registration failed. Error: " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        RegistrationForm registrationForm = new RegistrationForm();
        registrationForm.setVisible(true);
    }
}

