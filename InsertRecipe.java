import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class InsertRecipe extends Frame implements ActionListener {
    // Labels and TextFields for Recipe Name, Safe Name, Procedure, and Calories
    private Label lblRecipeName, lblSafeName, lblProcedure, lblCalories;
    private TextField tfRecipeName, tfSafeName, tfProcedure, tfCalories;
    // Button to submit the form
    private Button btnSubmit;

    // Constructor to set up the frame
    public InsertRecipe() {
        // Set the title of the frame
        setTitle("Insert Recipe");

        // Set the layout of the frame
        setLayout(new GridLayout(5, 2));

        // Create Labels and TextFields for Recipe Name, Safe Name, Procedure, and Calories
        lblRecipeName = new Label("Recipe Name:");
        tfRecipeName = new TextField();
        lblSafeName = new Label("Safe Name:");
        tfSafeName = new TextField();
        lblProcedure = new Label("Procedure:");
        tfProcedure = new TextField();
        lblCalories = new Label("Calories:");
        tfCalories = new TextField();

        // Create the submit button
        btnSubmit = new Button("Submit");
        btnSubmit.addActionListener(this);

        // Add the Labels, TextFields, and Button to the frame
        add(lblRecipeName);
        add(tfRecipeName);
        add(lblSafeName);
        add(tfSafeName);
        add(lblProcedure);
        add(tfProcedure);
        add(lblCalories);
        add(tfCalories);
        add(new Label(""));
        add(btnSubmit);

        // Set the size of the frame and make it visible
        setSize(400, 200);
        setVisible(true);
    }

    // ActionListener for the submit button
    public void actionPerformed(ActionEvent e) {
        // Get the values from the TextFields
        String recipeName = tfRecipeName.getText();
        String safeName = tfSafeName.getText();
        String procedure = tfProcedure.getText();
        String calories = tfCalories.getText();

        // Create a Connection object to connect to the database
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/akash", "root", "root");

            // Create a PreparedStatement object to insert the data into the food_search table
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO food_search (recipe_name, safe_name, recipe_procedure, calories) VALUES (?, ?, ?, ?)");
            pstmt.setString(1, recipeName);
            pstmt.setString(2, safeName);
            pstmt.setString(3, procedure);
            pstmt.setString(4, calories);

            // Execute the PreparedStatement
            pstmt.executeUpdate();

            // Close the PreparedStatement and Connection objects
            pstmt.close();
            con.close();

            // Display a message to the user that the data was inserted successfully
            JOptionPane.showMessageDialog(this, "Data inserted successfully.");

            // Clear the TextFields
            tfRecipeName.setText("");
            tfSafeName.setText("");
            tfProcedure.setText("");
            tfCalories.setText("");
        } catch (SQLException ex) {
            // Display an error message if there was an error inserting the data
            JOptionPane.showMessageDialog(this, "Error inserting data: " + ex.getMessage());
        }
    }

    // Main method to create the InsertRecipe object
    public static void main(String[] args) {
        new InsertRecipe();
    }
}
