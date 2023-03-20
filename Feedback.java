import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class Feedback extends JFrame implements ActionListener {

    // form fields
    private JTextField nameField, emailField, feedbackField;
    private JButton submitButton;
    private Connection con;

    public Feedback() {
        super("Registration Form");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // create form components
        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField(20);
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField(20);
        JLabel feedbackLabel = new JLabel("feedback:");
        feedbackField = new JTextField(200);
        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);

        // add form components to content pane
        add(nameLabel);
        add(nameField);
        add(emailLabel);
        add(emailField);
        add(feedbackLabel);
        add(feedbackField);
        add(submitButton);

        // set bounds
        nameLabel.setBounds(20, 20, 50, 20);
        nameField.setBounds(100, 20, 150, 20);
        emailLabel.setBounds(20, 50, 50, 20);
        emailField.setBounds(100, 50, 150, 20);
        feedbackLabel.setBounds(20, 80, 70, 20);
        feedbackField.setBounds(100, 80, 150, 20);
        submitButton.setBounds(100, 120, 100, 20);
    }

    public void actionPerformed(ActionEvent e) {
        String name = nameField.getText();
        String email = emailField.getText();
        String feedbackText = feedbackField.getText();

        // validate form fields
        if (name.equals("") || email.equals("") || feedbackText.equals("")) {
            JOptionPane.showMessageDialog(this, "Please fill out all fields.");
        } else {
            try {
                // create database connection
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/akash", "root", "root");

                // create SQL statement to insert user data into database
                String query = "INSERT INTO feedback (name, email, feedback) VALUES (?, ?, ?)";
                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setString(1, name);
                stmt.setString(2, email);
                stmt.setString(3, feedbackText);

                // execute SQL statement and close database connection
                stmt.executeUpdate();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "feedback failed. Error: " + ex.getMessage());
            } finally {
                try {
                    if (con != null) {
                        con.close();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Feedback submission failed. Error: " + ex.getMessage());
                }
            }
        }
    }

    public static void main(String[] args) {
        Feedback feedbackForm = new Feedback();
        feedbackForm.setVisible(true);
    }
}
