
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class users extends JFrame {
    private JTextField userIdField, nameField, emailField, passwordField;
    private JButton insertButton, modifyButton, deleteButton, displayButton;

    public users() {
        setTitle("users");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize components
        userIdField = new JTextField(10);
        nameField = new JTextField(10);
        emailField = new JTextField(10);
        passwordField = new JTextField(10);

        insertButton = new JButton("Insert");
        modifyButton = new JButton("Modify");
        deleteButton = new JButton("Delete");
        displayButton = new JButton("Display");

        // Set layout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("User ID:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        add(userIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Name:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Email:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(insertButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        add(modifyButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        add(deleteButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        add(displayButton, gbc);

        pack();
        setVisible(true);

        // Set action listeners for buttons
        insertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insertUser();
            }
        });

        modifyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modifyUser();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteUser();
            }
        });

        displayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayUsers();
            }
        });
    }

    private void insertUser() {
        int userId = Integer.parseInt(userIdField.getText());
        String name = nameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();

        try (Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "spandana", "spandana")) {
            String query = "INSERT INTO users (user_id, name, email, password) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, userId);
            statement.setString(2, name);
            statement.setString(3, email);
            statement.setString(4, password);
            statement.executeUpdate();
            JOptionPane.showMessageDialog(this, "User inserted successfully.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void modifyUser() {
        int userId = Integer.parseInt(userIdField.getText());
        String name = nameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();

        try (Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "spandana", "spandana")) {
            String query = "UPDATE users SET name = ?, email = ?, password = ? WHERE user_id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, password);
            statement.setInt(4, userId);
            statement.executeUpdate();
            JOptionPane.showMessageDialog(this, "User modified successfully.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void deleteUser() {
        int userId = Integer.parseInt(userIdField.getText());

        try (Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "spandana", "spandana")) {
            String query = "DELETE FROM users WHERE user_id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, userId);
            statement.executeUpdate();
            JOptionPane.showMessageDialog(this, "User deleted successfully.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void displayUsers() {
        try (Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "spandana", "spandana")) {
            String query = "SELECT * FROM users";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            StringBuilder usersInfo = new StringBuilder();
            usersInfo.append("User ID\tName\tEmail\tPassword\n");
            while (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                usersInfo.append(userId).append("\t").append(name).append("\t").append(email).append("\t").append(password).append("\n");
            }

            JTextArea textArea = new JTextArea(usersInfo.toString());
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(400, 200));
            JOptionPane.showMessageDialog(this, scrollPane, "Users", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new users();
            }
        });
    }
}
