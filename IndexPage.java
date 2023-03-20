import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.sql.*;

public class IndexPage {
    JFrame frame;
     JTable table;
     DefaultTableModel tableModel;
     JTextField searchField;
     JButton searchButton;
     Connection conn;
     Statement stmt;

    public IndexPage() {
        frame = new JFrame("Menu Bar Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set up the menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);
        frame.setJMenuBar(menuBar);

        // Set up the search bar
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel searchLabel = new JLabel("Search: ");
        searchField = new JTextField(20);
        searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                performSearch();
            }
        });
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        frame.add(searchPanel, BorderLayout.NORTH);

        // Set up the table
        tableModel = new DefaultTableModel(new Object[]{"Recipe Name", "Safe Name", "Procedure", "Calories"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Set up the database connection
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/akash?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "root");
            stmt = conn.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Display the frame
        frame.setSize(500, 500);
        frame.setVisible(true);
    }

    private void performSearch() {
        String searchTerm = searchField.getText();
        String sql = "SELECT * FROM food_search WHERE recipe_name LIKE '%" + searchTerm + "%'";
        try {
            ResultSet rs = stmt.executeQuery(sql);

            // Store the search results in an ArrayList
            java.util.List<Object[]> data = new java.util.ArrayList<>();
            while (rs.next()) {
                String recipeName = rs.getString("recipe_name");
                String safeName = rs.getString("safe_name");
                String recipeProcedure = rs.getString("recipe_procedure");
                int calories = rs.getInt("calories");
                Object[] row = {recipeName, safeName, recipeProcedure, calories};
                data.add(row);
            }

            // Update the table model with the search results
            tableModel.setRowCount(0);
            for (Object[] row : data) {
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new IndexPage();
    }
}
