import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class algorithm extends JFrame {
    private JLabel lblAlgorithmId, lblName, lblCategory, lblDescription;
    private JTextField txtAlgorithmId, txtName, txtCategory, txtDescription;
    private JButton btnInsert, btnModify, btnDelete, btnDisplay;

    // JDBC Connection details
    private final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private final String DB_USER = "spandana";
    private final String DB_PASS = "spandana";
    private Connection conn;

    public algorithm() {
        initializeUI();
        initializeDatabase();
    }

    private void initializeUI() {
        lblAlgorithmId = new JLabel("Algorithm ID:");
        lblName = new JLabel("Name:");
        lblCategory = new JLabel("Category:");
        lblDescription = new JLabel("Description:");

        txtAlgorithmId = new JTextField(10);
        txtName = new JTextField(20);
        txtCategory = new JTextField(20);
        txtDescription = new JTextField(50);

        btnInsert = new JButton("Insert");
        btnModify = new JButton("Modify");
        btnDelete = new JButton("Delete");
        btnDisplay = new JButton("Display");

        // Set up action listeners for buttons
        btnInsert.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insertAlgorithm();
            }
        });

        btnModify.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modifyAlgorithm();
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteAlgorithm();
            }
        });

        btnDisplay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayAlgorithms();
            }
        });

        // Set up layout using GridBagLayout
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        add(lblAlgorithmId, c);

        c.gridx = 1;
        c.gridy = 0;
        add(txtAlgorithmId, c);

        c.gridx = 0;
        c.gridy = 1;
        add(lblName, c);

        c.gridx = 1;
        c.gridy = 1;
        add(txtName, c);

        c.gridx = 0;
        c.gridy = 2;
        add(lblCategory, c);

        c.gridx = 1;
        c.gridy = 2;
        add(txtCategory, c);

        c.gridx = 0;
        c.gridy = 3;
        add(lblDescription, c);

        c.gridx = 1;
        c.gridy = 3;
        add(txtDescription, c);

        c.gridx = 0;
        c.gridy = 4;
        add(btnInsert, c);

        c.gridx = 1;
        c.gridy = 4;
        add(btnModify, c);

        c.gridx = 0;
        c.gridy = 5;
        add(btnDelete, c);

        c.gridx = 1;
        c.gridy = 5;
        add(btnDisplay, c);

        setTitle("Daadigi Book App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    private void initializeDatabase() {
        try {
            // Load the JDBC driver
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // Establish the connection to the database
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void insertAlgorithm() {
        try {
            String algorithmId = txtAlgorithmId.getText();
            String name = txtName.getText();
            String category = txtCategory.getText();
            String description = txtDescription.getText();

            // Prepare the SQL statement
            String sql = "INSERT INTO algorithm (algorithm_id, name, category, description) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, algorithmId);
            stmt.setString(2, name);
            stmt.setString(3, category);
            stmt.setString(4, description);

            // Execute the statement
            stmt.executeUpdate();

            // Clear the text fields
            clearTextFields();

            JOptionPane.showMessageDialog(this, "Algorithm inserted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error inserting algorithm: " + e.getMessage());
        }
    }

    private void modifyAlgorithm() {
        try {
            String algorithmId = txtAlgorithmId.getText();
            String name = txtName.getText();
            String category = txtCategory.getText();
            String description = txtDescription.getText();

            // Prepare the SQL statement
            String sql = "UPDATE algorithm SET name = ?, category = ?, description = ? WHERE algorithm_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, category);
            stmt.setString(3, description);
            stmt.setString(4, algorithmId);

            // Execute the statement
            int rowsAffected = stmt.executeUpdate();

            // Clear the text fields
            clearTextFields();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Algorithm modified successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Algorithm not found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error modifying algorithm: " + e.getMessage());
        }
    }

    private void deleteAlgorithm() {
        try {
            String algorithmId = txtAlgorithmId.getText();

            // Prepare the SQL statement
            String sql = "DELETE FROM algorithm WHERE algorithm_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, algorithmId);

            // Execute the statement
            int rowsAffected = stmt.executeUpdate();

            // Clear the text fields
            clearTextFields();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Algorithm deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Algorithm not found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error deleting algorithm: " + e.getMessage());
        }
    }

    private void displayAlgorithms() {
        try {
            // Prepare the SQL statement
            String sql = "SELECT * FROM algorithm";
            Statement stmt = conn.createStatement();

            // Execute the statement
            ResultSet rs = stmt.executeQuery(sql);

            // Display the results
            StringBuilder sb = new StringBuilder();
            while (rs.next()) {
                String algorithmId = rs.getString("algorithm_id");
                String name = rs.getString("name");
                String category = rs.getString("category");
                String description = rs.getString("description");

                sb.append("Algorithm ID: ").append(algorithmId).append("\n");
                sb.append("Name: ").append(name).append("\n");
                sb.append("Category: ").append(category).append("\n");
                sb.append("Description: ").append(description).append("\n");
                sb.append("\n");
            }

            JOptionPane.showMessageDialog(this, sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error displaying algorithms: " + e.getMessage());
        }
    }

    private void clearTextFields() {
        txtAlgorithmId.setText("");
        txtName.setText("");
        txtCategory.setText("");
        txtDescription.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new algorithm();
            }
        });
    }
}
