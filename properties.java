import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class properties extends JFrame {
    private JLabel lblPropertyId, lblAlgorithmId, lblInputType, lblOutputType, lblComplexity, lblRuntime;
    private JTextField txtPropertyId, txtAlgorithmId, txtInputType, txtOutputType, txtComplexity, txtRuntime;
    private JButton btnInsert, btnModify, btnDelete, btnDisplay;

    public properties() {
        // Initialize JFrame
        setTitle("Property Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize components
        lblPropertyId = new JLabel("Property ID:");
        lblAlgorithmId = new JLabel("Algorithm ID:");
        lblInputType = new JLabel("Input Type:");
        lblOutputType = new JLabel("Output Type:");
        lblComplexity = new JLabel("Complexity:");
        lblRuntime = new JLabel("Runtime:");

        txtPropertyId = new JTextField(10);
        txtAlgorithmId = new JTextField(10);
        txtInputType = new JTextField(10);
        txtOutputType = new JTextField(10);
        txtComplexity = new JTextField(10);
        txtRuntime = new JTextField(10);

        btnInsert = new JButton("Insert");
        btnModify = new JButton("Modify");
        btnDelete = new JButton("Delete");
        btnDisplay = new JButton("Display");

        // Set layout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Add components to the layout
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(lblPropertyId, gbc);

        gbc.gridx = 1;
        add(txtPropertyId, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(lblAlgorithmId, gbc);

        gbc.gridx = 1;
        add(txtAlgorithmId, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(lblInputType, gbc);

        gbc.gridx = 1;
        add(txtInputType, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(lblOutputType, gbc);

        gbc.gridx = 1;
        add(txtOutputType, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(lblComplexity, gbc);

        gbc.gridx = 1;
        add(txtComplexity, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        add(lblRuntime, gbc);

        gbc.gridx = 1;
        add(txtRuntime, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        add(btnInsert, gbc);

        gbc.gridx = 1;
        add(btnModify, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        add(btnDelete, gbc);

        gbc.gridx = 1;
        add(btnDisplay, gbc);

        // Add action listeners
        btnInsert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertProperty();
            }
        });

        btnModify.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifyProperty();
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteProperty();
            }
        });

        btnDisplay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayProperties();
            }
        });

        pack();
        setVisible(true);
    }

    private void insertProperty() {
        String propertyId = txtPropertyId.getText();
        String algorithmId = txtAlgorithmId.getText();
        String inputType = txtInputType.getText();
        String outputType = txtOutputType.getText();
        String complexity = txtComplexity.getText();
        String runtime = txtRuntime.getText();

        // Connect to the database and execute the insert query
        try (Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "spandana", "spandana");
             Statement stmt = conn.createStatement()) {

            String query = "INSERT INTO properties (property_id, algorithm_id, input_type, output_type, complexity, runtime) " +
                    "VALUES ('" + propertyId + "', '" + algorithmId + "', '" + inputType + "', '" + outputType + "', '" + complexity + "', '" + runtime + "')";

            stmt.executeUpdate(query);

            JOptionPane.showMessageDialog(this, "Property inserted successfully.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error inserting property: " + ex.getMessage());
        }
    }

    private void modifyProperty() {
        String propertyId = txtPropertyId.getText();
        String algorithmId = txtAlgorithmId.getText();
        String inputType = txtInputType.getText();
        String outputType = txtOutputType.getText();
        String complexity = txtComplexity.getText();
        String runtime = txtRuntime.getText();

        // Connect to the database and execute the update query
        try (Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "spandana", "spandana");
             Statement stmt = conn.createStatement()) {

            String query = "UPDATE properties SET algorithm_id = '" + algorithmId + "', input_type = '" + inputType +
                    "', output_type = '" + outputType + "', complexity = '" + complexity + "', runtime = '" + runtime +
                    "' WHERE property_id = '" + propertyId + "'";

            stmt.executeUpdate(query);

            JOptionPane.showMessageDialog(this, "Property modified successfully.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error modifying property: " + ex.getMessage());
        }
    }

    private void deleteProperty() {
        String propertyId = txtPropertyId.getText();

        // Connect to the database and execute the delete query
        try (Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "spandana", "spandana");
             Statement stmt = conn.createStatement()) {

            String query = "DELETE FROM properties WHERE property_id = '" + propertyId + "'";

            stmt.executeUpdate(query);

            JOptionPane.showMessageDialog(this, "Property deleted successfully.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error deleting property: " + ex.getMessage());
        }
    }

    private void displayProperties() {
        // Connect to the database and execute the select query
        try (Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "spandana", "spandana");
             Statement stmt = conn.createStatement()) {

            String query = "SELECT * FROM properties";
            ResultSet rs = stmt.executeQuery(query);

            StringBuilder sb = new StringBuilder();
            while (rs.next()) {
                String propertyId = rs.getString("property_id");
                String algorithmId = rs.getString("algorithm_id");
                String inputType = rs.getString("input_type");
                String outputType = rs.getString("output_type");
                String complexity = rs.getString("complexity");
                String runtime = rs.getString("runtime");

                sb.append("Property ID: ").append(propertyId).append("\n");
                sb.append("Algorithm ID: ").append(algorithmId).append("\n");
                sb.append("Input Type: ").append(inputType).append("\n");
                sb.append("Output Type: ").append(outputType).append("\n");
                sb.append("Complexity: ").append(complexity).append("\n");
                sb.append("Runtime: ").append(runtime).append("\n\n");
            }

            JOptionPane.showMessageDialog(this, sb.toString());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error displaying properties: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new properties();
            }
        });
    }
}
