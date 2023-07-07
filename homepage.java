import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class homepage {
    private JFrame frame;
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem usersItem;
    private JMenuItem algorithmItem;
    private JMenuItem propertiesItem;
    private JMenuItem ratingItem;

    public homepage() {
        frame = new JFrame("DAA DIGI BOOK");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the menu bar
        menuBar = new JMenuBar();

        // Create the file menu
        fileMenu = new JMenu("File");

        // Create the menu items
        usersItem = new JMenuItem("Users");
        algorithmItem = new JMenuItem("Algorithm");
        propertiesItem = new JMenuItem("Properties");
        ratingItem = new JMenuItem("Rating");

        // Add action listeners to the menu items
        usersItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Perform action for Users
                performUsersAction();
            }
        });

        algorithmItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Perform action for Algorithm
                performAlgorithmAction();
            }
        });

        propertiesItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Perform action for Properties
                performPropertiesAction();
            }
        });

        ratingItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Perform action for Rating
                performRatingAction();
            }
        });

        // Add the menu items to the file menu
        fileMenu.add(usersItem);
        fileMenu.add(algorithmItem);
        fileMenu.add(propertiesItem);
        fileMenu.add(ratingItem);

        // Add the file menu to the menu bar
        menuBar.add(fileMenu);

        // Set the menu bar to the frame
        frame.setJMenuBar(menuBar);

        // Set the size and visibility of the frame
        frame.setSize(400, 300);
        frame.setVisible(true);
    }

    private void performUsersAction() {
        // Action to perform when Users is selected
        System.out.println("Users action performed");
        frame.setVisible(false); // Hide the home frame
        JFrame usersFrame = new JFrame("Users"); // Create a new frame for Users
        usersFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        usersFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                frame.setVisible(true); // Show the home frame when Users frame is closed
            }
        });
        
        // Create an instance of the users class
        users usersInstance = new users();
        
        // Add the instance to the frame's content pane
        usersFrame.getContentPane().add(usersInstance);
        
        // Set the size and visibility of the usersFrame
        usersFrame.pack();
        usersFrame.setVisible(true);
    }


    private void performAlgorithmAction() {
        // Action to perform when Algorithm is selected
        System.out.println("Algorithm action performed");
        frame.setVisible(false); // Hide the home frame
        JFrame algorithmFrame = new JFrame("Algorithm"); // Create a new frame for Algorithm
        algorithmFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        algorithmFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                frame.setVisible(true); // Show the home frame when Algorithm frame is closed
            }
        });
        algorithmFrame.setSize(300, 200);
        algorithmFrame.setVisible(true);
    }

    private void performPropertiesAction() {
        // Action to perform when Properties is selected
        System.out.println("Properties action performed");
        frame.setVisible(false); // Hide the home frame
        JFrame propertiesFrame = new JFrame("Properties"); // Create a new frame```
        propertiesFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        propertiesFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                frame.setVisible(true); // Show the home frame when Properties frame is closed
            }
        });
        propertiesFrame.setSize(300, 200);
        propertiesFrame.setVisible(true);
    }

    private void performRatingAction() {
        // Action to perform when Rating is selected
        System.out.println("Rating action performed");
        frame.setVisible(false); // Hide the home frame
        JFrame ratingFrame = new JFrame("Rating"); // Create a new frame for Rating
        ratingFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ratingFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                frame.setVisible(true); // Show the home frame when Rating frame is closed
            }
        });
        ratingFrame.setSize(300, 200);
        ratingFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new homepage();
            }
        });
    }
}