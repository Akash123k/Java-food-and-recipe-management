import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FoodSearchUpdate extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel panel;
    private JLabel recipeLabel;
    private JTextField recipeField;
    private JLabel caloriesLabel;
    private JTextField caloriesField;
    private JButton updateButton;
    
    public FoodSearchUpdate() {
        super("Update Recipe Calories");
        
        panel = new JPanel();
        
        recipeLabel = new JLabel("Recipe Name:");
        recipeField = new JTextField(20);
        caloriesLabel = new JLabel("Calories:");
        caloriesField = new JTextField(5);

        updateButton = new JButton("Update");

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String recipeName = recipeField.getText();
                int calories = Integer.parseInt(caloriesField.getText());
                
                // update the database
                Connection conn = null;
                PreparedStatement stmt = null;
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    String url = "jdbc:mysql://localhost:3306/akash";
                    String user = "root";
                    String password = "root";
                    conn = DriverManager.getConnection(url, user, password);
                    
                    String query = "UPDATE food_search SET calories = ? WHERE recipe_name = ?";
                    stmt = conn.prepareStatement(query);
                    stmt.setInt(1, calories);
                    stmt.setString(2, recipeName);
                    stmt.executeUpdate();
                    
                    // success message
                    System.out.println("Calories updated for " + recipeName);
                    
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                } finally {
                    try {
                        if (stmt != null) stmt.close();
                        if (conn != null) conn.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        panel.add(recipeLabel);
        panel.add(recipeField);
        panel.add(caloriesLabel);
        panel.add(caloriesField);
        panel.add(updateButton);
        
        add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
    
    public static void main(String[] args) {
        new FoodSearchUpdate();
    }

}
