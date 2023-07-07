import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class rating extends JFrame {
    private JTextField txtRatingId;
    private JTextField txtUserId;
    private JTextField txtAlgorithmId;
    private JTextField txtRating;
    private JButton btnInsert;
    private JButton btnModify;
    private JButton btnDelete;
    private JButton btnDisplay;
    private JTextArea txtDisplay;

    private Connection conn;

    public rating() {
        super("Rating App");
        initializeComponents();
        connectToDatabase();
    }

    private void initializeComponents() {
        // Create and configure the Swing components
        txtRatingId = new JTextField(10);
        txtUserId = new JTextField(10);
        txtAlgorithmId = new JTextField(10);
        txtRating = new JTextField(10);
        btnInsert = new JButton("Insert");
        btnModify = new JButton("Modify");
        btnDelete = new JButton("Delete");
        btnDisplay = new JButton("Display");
        txtDisplay = new JTextArea(10, 30);

        // Set the layout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Add components to the frame using GridBagLayout
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Rating ID:"), gbc);

        gbc.gridx = 1;
        add(txtRatingId, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("User ID:"), gbc);

        gbc.gridx = 1;
        add(txtUserId, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Algorithm ID:"), gbc);

        gbc.gridx = 1;
        add(txtAlgorithmId, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Rating:"), gbc);

        gbc.gridx = 1;
        add(txtRating, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(btnInsert, gbc);

        gbc.gridx = 1;
        add(btnModify, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        add(btnDelete, gbc);

        gbc.gridx = 1;
        add(btnDisplay, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        add(new JScrollPane(txtDisplay), gbc);

        // Add action listeners to buttons
        btnInsert.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insertRating();
            }
        });

        btnModify.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modifyRating();
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteRating();
            }
        });

        btnDisplay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayRatings();
            }
        });

        // Set frame properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void connectToDatabase() {
        // Set up database connection
        //String url = "jdbc:oracle:thin:@localhost:1521:xe";
        //String username = "spandana";
        //String password = "spandana";

        try {
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "spandana", "spandana");
            System.out.println("Connected to the database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertRating() {
        try {
            // Prepare the SQL statement
            String sql = "INSERT INTO rating (rating_id, user_id, algorithm_id, rating) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            // Set the parameter values
            stmt.setInt(1, Integer.parseInt(txtRatingId.getText()));
            stmt.setInt(2, Integer.parseInt(txtUserId.getText()));
            stmt.setInt(3, Integer.parseInt(txtAlgorithmId.getText()));
            stmt.setDouble(4, Double.parseDouble(txtRating.getText()));

            // Execute the statement
            stmt.executeUpdate();

            // Clear the input fields
            clearFields();

            JOptionPane.showMessageDialog(this, "Rating inserted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void modifyRating() {
        try {
            // Prepare the SQL statement
            String sql = "UPDATE rating SET user_id = ?, algorithm_id = ?, rating = ? WHERE rating_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            // Set the parameter values
            stmt.setInt(1, Integer.parseInt(txtUserId.getText()));
            stmt.setInt(2, Integer.parseInt(txtAlgorithmId.getText()));
            stmt.setDouble(3, Double.parseDouble(txtRating.getText()));
            stmt.setInt(4, Integer.parseInt(txtRatingId.getText()));

            // Execute the statement
            stmt.executeUpdate();

            // Clear the input fields
            clearFields();

            JOptionPane.showMessageDialog(this, "Rating modified successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteRating() {
        try {
            // Prepare the SQL statement
            String sql = "DELETE FROM rating WHERE rating_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            // Set the parameter value
            stmt.setInt(1, Integer.parseInt(txtRatingId.getText()));

            // Execute the statement
            stmt.executeUpdate();

            // Clear the input fields
            clearFields();

            JOptionPane.showMessageDialog(this, "Rating deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void displayRatings() {
        try {
            // Prepare the SQL statement
            String sql = "SELECT * FROM rating";
            Statement stmt = conn.createStatement();

            // Execute the query
            ResultSet rs = stmt.executeQuery(sql);

            // Clear the text area
            txtDisplay.setText("");

            // Display the results
            while (rs.next()) {
                int ratingId = rs.getInt("rating_id");
                int userId = rs.getInt("user_id");
                int algorithmId = rs.getInt("algorithm_id");
                double rating = rs.getDouble("rating");

                String result = "Rating ID: " + ratingId + "\n" +
                        "User ID: " + userId + "\n" +
                        "Algorithm ID: " + algorithmId + "\n" +
                        "Rating: " + rating + "\n\n";

                txtDisplay.append(result);
            }

            // Close the ResultSet and Statement
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        txtRatingId.setText("");
        txtUserId.setText("");
        txtAlgorithmId.setText("");
        txtRating.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new rating();
            }
        });
    }
}
