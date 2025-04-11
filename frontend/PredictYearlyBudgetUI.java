// import org.jfree.chart.ChartFactory;
// import org.jfree.chart.ChartPanel;
// import org.jfree.chart.JFreeChart;
// import org.jfree.chart.plot.CategoryPlot;
// import org.jfree.chart.renderer.category.BarRenderer;
// import org.jfree.data.category.DefaultCategoryDataset;
// import org.jfree.chart.axis.CategoryAxis;
// import org.jfree.chart.axis.CategoryLabelPositions;

// import javax.swing.*;
// import javax.swing.border.EmptyBorder;
// import java.awt.*;
// import java.awt.event.ActionEvent;
// import java.io.BufferedReader;
// import java.io.InputStreamReader;
// import java.net.HttpURLConnection;
// import java.net.URI;
// import java.net.URL;

// public class PredictYearlyBudgetUI {
//     private JFrame frame;
//     private JButton predictButton;
//     private JTextArea resultArea;
//     private JPanel chartPanel;

//     public PredictYearlyBudgetUI() {
//         frame = new JFrame("Predict Yearly Budget");
//         frame.setSize(1200, 800);
//         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         frame.setLayout(new BorderLayout());

//         // Header
//         JPanel headerPanel = new JPanel();
//         headerPanel.setBackground(new Color(52, 152, 219));
//         JLabel titleLabel = new JLabel("Predict Yearly Budget");
//         titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
//         titleLabel.setForeground(Color.WHITE);
//         headerPanel.add(titleLabel);
//         frame.add(headerPanel, BorderLayout.NORTH);

//         // Button Panel
//         JPanel buttonPanel = new JPanel();
//         buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 40));
//         buttonPanel.setBackground(new Color(236, 240, 241));

//         predictButton = new JButton("Predict Budget");
//         predictButton.setFont(new Font("Arial", Font.BOLD, 18));
//         predictButton.setBackground(new Color(96, 209, 110));
//         predictButton.setForeground(Color.WHITE);
//         predictButton.setFocusPainted(false);
//         predictButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

//         predictButton.addMouseListener(new java.awt.event.MouseAdapter() {
//             public void mouseEntered(java.awt.event.MouseEvent evt) {
//                 predictButton.setBackground(new Color(39, 174, 96));
//             }

//             public void mouseExited(java.awt.event.MouseEvent evt) {
//                 predictButton.setBackground(new Color(46, 204, 113));
//             }
//         });

//         predictButton.addActionListener(this::predictBudget);
//         buttonPanel.add(predictButton);
//         frame.add(buttonPanel, BorderLayout.CENTER);

//         // Result Panel
//         JPanel resultPanel = new JPanel();
//         resultPanel.setBackground(new Color(236, 240, 241));
//         resultArea = new JTextArea(8, 40);
//         resultArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
//         resultArea.setEditable(false);
//         resultArea.setWrapStyleWord(true);
//         resultArea.setLineWrap(true);
//         JScrollPane scrollPane = new JScrollPane(resultArea);
//         resultPanel.add(scrollPane);
//         frame.add(resultPanel, BorderLayout.SOUTH);

//         // Chart Panel
//         chartPanel = new JPanel();
//         chartPanel.setBackground(Color.WHITE);
//         frame.add(chartPanel, BorderLayout.EAST);

//         frame.setVisible(true);
//     }

//     // 1. Fetch prediction from backend
//     private void predictBudget(ActionEvent e) {
//         try {
//             URI uri = new URI("http://localhost:8085/budget/fetch");
//             URL url = uri.toURL();

//             HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//             conn.setRequestMethod("GET");

//             try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
//                 String response = reader.readLine();
//                 parseAndDisplayBudget(response);
//             }

//         } catch (Exception ex) {
//             updateResult("Error fetching prediction");
//             ex.printStackTrace();
//         }
//     }

//     // 2. Parse and show budget + chart
//     private void parseAndDisplayBudget(String json) {
//         if (json == null || json.isEmpty()) {
//             updateResult("No data received.");
//             return;
//         }

//         if (json.contains("error")) {
//             updateResult("User not found. Please try again.");
//             return;
//         }

//         try {
//             String currentBudgetStr = json.split("\"currentBudget\":")[1].split(",")[0].trim();
//             double currentBudget = Double.parseDouble(currentBudgetStr);

//             String futureBudgetsStr = json.split("\"futureBudgets\":")[1]
//                     .replace("[", "")
//                     .replace("]", "")
//                     .replace("}", "")
//                     .trim();
//             String[] futureBudgets = futureBudgetsStr.split(",");

//             double year1 = Double.parseDouble(futureBudgets[0].trim());
//             double year2 = Double.parseDouble(futureBudgets[1].trim());
//             double year3 = Double.parseDouble(futureBudgets[2].trim());

//             StringBuilder result = new StringBuilder("📊 Budget Prediction:\n");
//             result.append("Current Budget: $").append(currentBudget).append("\n");
//             result.append("Year 1 (6% Inflation): $").append(year1).append("\n");
//             result.append("Year 2 (7% Inflation): $").append(year2).append("\n");
//             result.append("Year 3 (8% Inflation): $").append(year3).append("\n");
//             updateResult(result.toString());

//             showBudgetChart(currentBudget, year1, year2, year3);

//         } catch (Exception e) {
//             e.printStackTrace();
//             updateResult("Error parsing budget data.");
//         }
//     }

//     // 3. Show the chart
//     private void showBudgetChart(double currentBudget, double year1, double year2, double year3) {
//         DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//         dataset.addValue(currentBudget, "Budget", "Current Year");
//         dataset.addValue(year1, "Budget", "Year 1 (6% Inflation)");
//         dataset.addValue(year2, "Budget", "Year 2 (7% Inflation)");
//         dataset.addValue(year3, "Budget", "Year 3 (8% Inflation)");

//         JFreeChart barChart = ChartFactory.createBarChart(
//                 "Budget Prediction",
//                 "Year",
//                 "Amount ($)",
//                 dataset
//         );

//         CategoryPlot plot = barChart.getCategoryPlot();
//         BarRenderer renderer = (BarRenderer) plot.getRenderer();
//         renderer.setSeriesPaint(0, new Color(200, 99, 71));

//         CategoryAxis domainAxis = plot.getDomainAxis();
//         domainAxis.setCategoryMargin(0.2);
//         domainAxis.setTickLabelFont(new Font("SansSerif", Font.BOLD, 14));
//         domainAxis.setMaximumCategoryLabelWidthRatio(0.9f);
//         domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

//         chartPanel.removeAll();
//         chartPanel.add(new ChartPanel(barChart));
//         chartPanel.revalidate();
//         chartPanel.repaint();
//     }

//     // 4. Update result area
//     private void updateResult(String message) {
//         SwingUtilities.invokeLater(() -> resultArea.setText(message));
//     }

//     public static void main(String[] args) {
//         SwingUtilities.invokeLater(PredictYearlyBudgetUI::new);
//     }
// }




// import javax.swing.*;
// import javax.swing.border.EmptyBorder;
// import java.awt.*;
// import java.awt.event.ActionEvent;
// import java.io.BufferedReader;
// import java.io.InputStreamReader;
// import java.net.HttpURLConnection;
// import java.net.URI;
// import java.net.URL;



// public class PredictYearlyBudgetUI {
//     private JFrame frame;
//     private JTextField userIdField;
//     private JButton predictButton;
//     private JTextArea resultArea;

//     public PredictYearlyBudgetUI() {
//         frame = new JFrame("Predict Yearly Budget");
//         frame.setSize(1080, 600);
//         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         frame.setLayout(new BorderLayout());

//         // Header Panel
//         JPanel headerPanel = new JPanel();
//         headerPanel.setBackground(new Color(52, 152, 219));
//         JLabel titleLabel = new JLabel("Predict Yearly Budget Using AI & Inflation");
//         titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
//         titleLabel.setForeground(Color.WHITE);
//         headerPanel.add(titleLabel);
//         frame.add(headerPanel, BorderLayout.NORTH);

//         // Form Panel (User Input)
//         JPanel formPanel = new JPanel();
//         formPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
//         formPanel.setBackground(new Color(236, 240, 241));
//         formPanel.setBorder(new EmptyBorder(50, 50, 50, 50));

//         JLabel userIdLabel = new JLabel("Enter User ID:");
//         userIdLabel.setFont(new Font("Arial", Font.PLAIN, 16));
//         formPanel.add(userIdLabel);

//         userIdField = new JTextField(10);
//         userIdField.setFont(new Font("Arial", Font.PLAIN, 16));
//         userIdField.setBorder(BorderFactory.createLineBorder(new Color(44, 62, 80), 2));
//         formPanel.add(userIdField);

//         predictButton = new JButton("Predict Budget");
//         predictButton.setFont(new Font("Arial", Font.BOLD, 16));
//         predictButton.setBackground(new Color(46, 204, 113));
//         predictButton.setForeground(Color.WHITE);
//         predictButton.setFocusPainted(false);
//         predictButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
//         predictButton.addActionListener(this::predictBudget);
//         formPanel.add(predictButton);

//         frame.add(formPanel, BorderLayout.CENTER);

//         // Result Panel (Prediction Output)
//         JPanel resultPanel = new JPanel();
//         resultPanel.setBackground(new Color(236, 240, 241));
//         resultArea = new JTextArea(8, 40);
//         resultArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
//         resultArea.setEditable(false);
//         JScrollPane scrollPane = new JScrollPane(resultArea);
//         resultPanel.add(scrollPane);
//         frame.add(resultPanel, BorderLayout.SOUTH);

//         frame.setVisible(true);
//     }

//     // Fetch the prediction from the backend
//     private void predictBudget(ActionEvent e) {
//         try {
//             // Get user ID from input
//             String userId = userIdField.getText().trim();
//             if (userId.isEmpty()) {
//                 updateResult("User ID cannot be empty!");
//                 return;
//             }

//             int userIdInt = Integer.parseInt(userId);

//             // API Endpoint
//             URI uri = new URI("http://localhost:8085/budget/fetch?userId=" + userIdInt);
//             URL url = uri.toURL();


//             HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//             conn.setRequestMethod("GET");

//             // Read response
//             try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
//                 String response = reader.readLine();
//                 updateResult(parseBudget(response));
//             }

//         } catch (Exception ex) {
//             updateResult("Error fetching prediction");
//             ex.printStackTrace();
//         }


//     }

// private String parseBudget(String json) {
//     if (json == null || json.isEmpty()) {
//         return "No data received.";
//     }

//     if (json.contains("error")) {
//         return "User not found. Please try again.";
//     }

//     try {
//         // Extracting values manually
//         String currentBudgetStr = json.split("\"currentBudget\":")[1].split(",")[0].trim();
//         double currentBudget = Double.parseDouble(currentBudgetStr);

//         String futureBudgetsStr = json.split("\"futureBudgets\":")[1]
//                                       .replace("[", "")
//                                       .replace("]", "")
//                                       .replace("}","")
//                                       .trim();
//         String[] futureBudgets = futureBudgetsStr.split(",");

//         // Build output message
//         StringBuilder result = new StringBuilder("📊 Budget Prediction:\n");
//         result.append("Current Budget: $").append(currentBudget).append("\n");
//         result.append("Year 1 (6% Inflation): $").append(futureBudgets[0].trim()).append("\n");
//         result.append("Year 2 (7% Inflation): $").append(futureBudgets[1].trim()).append("\n");
//         result.append("Year 3 (8% Inflation): $").append(futureBudgets[2].trim()).append("\n");

//         return result.toString();
//     } catch (Exception e) {
//         e.printStackTrace();
//         return "Error parsing budget data.";
//     }
// }


    

//     private void updateResult(String message) {
//         SwingUtilities.invokeLater(() -> resultArea.setText(message));
//     }

//     public static void main(String[] args) {
//         SwingUtilities.invokeLater(PredictYearlyBudgetUI::new);
//     }
// }

// import org.jfree.chart.ChartFactory;
// import org.jfree.chart.ChartPanel;
// import org.jfree.chart.JFreeChart;
// import org.jfree.data.category.DefaultCategoryDataset;

// import javax.swing.*;
// import javax.swing.border.EmptyBorder;
// import java.awt.*;
// import java.awt.event.ActionEvent;
// import java.io.BufferedReader;
// import java.io.InputStreamReader;
// import java.net.HttpURLConnection;
// import java.net.URI;
// import java.net.URL;

// public class PredictYearlyBudgetUI {
//     private JFrame frame;
//     private JTextField userIdField;
//     private JButton predictButton;
//     private JTextArea resultArea;
//     private JPanel chartPanel; // For displaying the chart

//     public PredictYearlyBudgetUI() {
//         frame = new JFrame("Predict Yearly Budget");
//         frame.setSize(1200, 800);
//         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         frame.setLayout(new BorderLayout());

//         // Header Panel
//         JPanel headerPanel = new JPanel();
//         headerPanel.setBackground(new Color(52, 152, 219));
//         JLabel titleLabel = new JLabel("Predict Yearly Budget Using AI & Inflation");
//         titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
//         titleLabel.setForeground(Color.WHITE);
//         headerPanel.add(titleLabel);
//         frame.add(headerPanel, BorderLayout.NORTH);

//         // Form Panel (User Input)
//         JPanel formPanel = new JPanel();
//         formPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
//         formPanel.setBackground(new Color(236, 240, 241));
//         formPanel.setBorder(new EmptyBorder(30, 30, 30, 30));

//         JLabel userIdLabel = new JLabel("Enter User ID:");
//         userIdLabel.setFont(new Font("Arial", Font.PLAIN, 18));
//         formPanel.add(userIdLabel);

//         userIdField = new JTextField(10);
//         userIdField.setFont(new Font("Arial", Font.PLAIN, 18));
//         userIdField.setBorder(BorderFactory.createLineBorder(new Color(44, 62, 80), 2));
//         formPanel.add(userIdField);

//         predictButton = new JButton("Predict Budget");
//         predictButton.setFont(new Font("Arial", Font.BOLD, 18));
//         predictButton.setBackground(new Color(46, 204, 113));
//         predictButton.setForeground(Color.WHITE);
//         predictButton.setFocusPainted(false);
//         predictButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
//         predictButton.addActionListener(this::predictBudget);
//         formPanel.add(predictButton);

//         frame.add(formPanel, BorderLayout.CENTER);

//         // Result Panel (Prediction Output)
//         JPanel resultPanel = new JPanel();
//         resultPanel.setBackground(new Color(236, 240, 241));
//         resultArea = new JTextArea(8, 40);
//         resultArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
//         resultArea.setEditable(false);
//         JScrollPane scrollPane = new JScrollPane(resultArea);
//         resultPanel.add(scrollPane);
//         frame.add(resultPanel, BorderLayout.SOUTH);

//         // Chart Panel (Budget Visualization)
//         chartPanel = new JPanel();
//         frame.add(chartPanel, BorderLayout.EAST);

//         frame.setVisible(true);
//     }

//     // Fetch the prediction from the backend
//     private void predictBudget(ActionEvent e) {
//         try {
//             String userId = userIdField.getText().trim();
//             if (userId.isEmpty()) {
//                 updateResult("User ID cannot be empty!");
//                 return;
//             }

//             int userIdInt = Integer.parseInt(userId);

//             // API Endpoint
//             URI uri = new URI("http://localhost:8085/budget/fetch?userId=" + userIdInt);
//             URL url = uri.toURL();

//             HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//             conn.setRequestMethod("GET");

//             // Read response
//             try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
//                 String response = reader.readLine();
//                 parseAndDisplayBudget(response);
//             }

//         } catch (Exception ex) {
//             updateResult("Error fetching prediction");
//             ex.printStackTrace();
//         }
//     }

//     // Parse JSON response and display results + chart
//     private void parseAndDisplayBudget(String json) {
//         if (json == null || json.isEmpty()) {
//             updateResult("No data received.");
//             return;
//         }

//         if (json.contains("error")) {
//             updateResult("User not found. Please try again.");
//             return;
//         }

//         try {
//             // Extracting values manually
//             String currentBudgetStr = json.split("\"currentBudget\":")[1].split(",")[0].trim();
//             double currentBudget = Double.parseDouble(currentBudgetStr);

//             String futureBudgetsStr = json.split("\"futureBudgets\":")[1]
//                     .replace("[", "")
//                     .replace("]", "")
//                     .replace("}", "")
//                     .trim();
//             String[] futureBudgets = futureBudgetsStr.split(",");

//             double year1 = Double.parseDouble(futureBudgets[0].trim());
//             double year2 = Double.parseDouble(futureBudgets[1].trim());
//             double year3 = Double.parseDouble(futureBudgets[2].trim());

//             // Display results in text area
//             StringBuilder result = new StringBuilder("📊 Budget Prediction:\n");
//             result.append("Current Budget: $").append(currentBudget).append("\n");
//             result.append("Year 1 (6% Inflation): $").append(year1).append("\n");
//             result.append("Year 2 (7% Inflation): $").append(year2).append("\n");
//             result.append("Year 3 (8% Inflation): $").append(year3).append("\n");
//             updateResult(result.toString());

//             // Display chart
//             showBudgetChart(currentBudget, year1, year2, year3);

//         } catch (Exception e) {
//             e.printStackTrace();
//             updateResult("Error parsing budget data.");
//         }
//     }

//     // Display Bar Chart
//     private void showBudgetChart(double currentBudget, double year1, double year2, double year3) {
//         DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//         dataset.addValue(currentBudget, "Budget", "Current Year");
//         dataset.addValue(year1, "Budget", "Year 1 (6% Inflation)");
//         dataset.addValue(year2, "Budget", "Year 2 (7% Inflation)");
//         dataset.addValue(year3, "Budget", "Year 3 (8% Inflation)");

//         JFreeChart barChart = ChartFactory.createBarChart(
//                 "Budget Prediction",
//                 "Year",
//                 "Amount ($)",
//                 dataset
//         );

//         if (chartPanel.getComponentCount() > 0) {
//             chartPanel.removeAll();
//         }

//         chartPanel.add(new ChartPanel(barChart));
//         chartPanel.revalidate();
//         chartPanel.repaint();
//     }

//     private void updateResult(String message) {
//         SwingUtilities.invokeLater(() -> resultArea.setText(message));
//     }

//     public static void main(String[] args) {
//         SwingUtilities.invokeLater(PredictYearlyBudgetUI::new);
//     }
// }



import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class PredictYearlyBudgetUI {
    private JFrame frame;
    private JTextField userIdField;
    private JButton predictButton;
    private JTextArea resultArea;
    private JPanel chartPanel; // For displaying the chart

    public PredictYearlyBudgetUI() {
        frame = new JFrame("Predict Yearly Budget");
        frame.setSize(1200, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(52, 152, 219));
        JLabel titleLabel = new JLabel("Predict Yearly Budget");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        frame.add(headerPanel, BorderLayout.NORTH);

        // Form Panel (User Input)
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        formPanel.setBackground(new Color(236, 240, 241));
        formPanel.setBorder(new EmptyBorder(30, 30, 30, 30));

        JLabel userIdLabel = new JLabel("Enter User ID:");
        userIdLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        formPanel.add(userIdLabel);

        userIdField = new JTextField(10);
        userIdField.setFont(new Font("Arial", Font.PLAIN, 18));
        userIdField.setBorder(BorderFactory.createLineBorder(new Color(44, 62, 80), 2));
        formPanel.add(userIdField);

        predictButton = new JButton("Predict Budget");
        predictButton.setFont(new Font("Arial", Font.BOLD, 18));
        predictButton.setBackground(new Color(96, 209, 110)); // Green button
        predictButton.setForeground(Color.WHITE); // White text
        predictButton.setFocusPainted(false);
        predictButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        // Button hover effect
        predictButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                predictButton.setBackground(new Color(39, 174, 96)); // Darker green on hover
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                predictButton.setBackground(new Color(46, 204, 113)); // Original green
            }
        });
        
        predictButton.addActionListener(this::predictBudget);
        formPanel.add(predictButton);

        frame.add(formPanel, BorderLayout.CENTER);

        // Result Panel (Prediction Output)
        JPanel resultPanel = new JPanel();
        resultPanel.setBackground(new Color(236, 240, 241));
        resultArea = new JTextArea(8, 40);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
        resultArea.setEditable(false);
        resultArea.setWrapStyleWord(true);
        resultArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        resultPanel.add(scrollPane);
        frame.add(resultPanel, BorderLayout.SOUTH);

        // Chart Panel (Budget Visualization)
        chartPanel = new JPanel();
        chartPanel.setBackground(Color.WHITE);
        frame.add(chartPanel, BorderLayout.EAST);

        frame.setVisible(true);
    }

    // Fetch the prediction from the backend
    private void predictBudget(ActionEvent e) {
        try {
            String userId = userIdField.getText().trim();
            if (userId.isEmpty()) {
                updateResult("User ID cannot be empty!");
                return;
            }

           // int userIdInt = Integer.parseInt(userId);

            // API Endpoint
            URI uri = new URI("http://localhost:8085/predict/fetch?userId=" + userId);
            URL url = uri.toURL();

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Read response
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String response = reader.readLine();
                parseAndDisplayBudget(response);
            }

        } catch (Exception ex) {
            updateResult("Error fetching prediction");
            ex.printStackTrace();
        }
    }

    // Parse JSON response and display results + chart
    private void parseAndDisplayBudget(String json) {
        if (json == null || json.isEmpty()) {
            updateResult("No data received.");
            return;
        }

        if (json.contains("error")) {
            updateResult("User not found. Please try again.");
            return;
        }

        try {
            String currentBudgetStr = json.split("\"currentBudget\":")[1].split(",")[0].trim();
            double currentBudget = Double.parseDouble(currentBudgetStr);

            String futureBudgetsStr = json.split("\"futureBudgets\":")[1]
                    .replace("[", "")
                    .replace("]", "")
                    .replace("}", "")
                    .trim();
            String[] futureBudgets = futureBudgetsStr.split(",");

            double year1 = Double.parseDouble(futureBudgets[0].trim());
            double year2 = Double.parseDouble(futureBudgets[1].trim());
            double year3 = Double.parseDouble(futureBudgets[2].trim());

            // Display results in text area
            StringBuilder result = new StringBuilder("📊 Budget Prediction:\n");
            result.append("Current Budget: $").append(currentBudget).append("\n");
            result.append("Year 1 (6% Inflation): $").append(year1).append("\n");
            result.append("Year 2 (7% Inflation): $").append(year2).append("\n");
            result.append("Year 3 (8% Inflation): $").append(year3).append("\n");
            updateResult(result.toString());

            // Display chart
            showBudgetChart(currentBudget, year1, year2, year3);

        } catch (Exception e) {
            e.printStackTrace();
            updateResult("Error parsing budget data.");
        }
    }

    // Display Bar Chart with custom colors
    // private void showBudgetChart(double currentBudget, double year1, double year2, double year3) {
    //     DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    //     dataset.addValue(currentBudget, "Budget", "Current Year");
    //     dataset.addValue(year1, "Budget", "Year 1 (6% Inflation)");
    //     dataset.addValue(year2, "Budget", "Year 2 (7% Inflation)");
    //     dataset.addValue(year3, "Budget", "Year 3 (8% Inflation)");

    //     JFreeChart barChart = ChartFactory.createBarChart(
    //             "Budget Prediction",
    //             "Year",
    //             "Amount ($)",
    //             dataset
    //     );

    //     CategoryPlot plot = barChart.getCategoryPlot();
    //     BarRenderer renderer = (BarRenderer) plot.getRenderer();

    //     // Custom bar colors
    //     renderer.setSeriesPaint(0, new Color(200, 99, 71)); // Tomato Red
    //     CategoryAxis domainAxis = plot.getDomainAxis();
    // domainAxis.setCategoryMargin(0.2);
    // domainAxis.setTickLabelFont(new Font("SansSerif", Font.BOLD, 14));
    // domainAxis.setMaximumCategoryLabelWidthRatio(0.9f);
    // domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
    // domainAxis.setMaximumCategoryLabelWidthRatio(1.0f);
    // domainAxis.setLowerMargin(0.02); // Space before first category
    // domainAxis.setUpperMargin(0.02);

    //     if (chartPanel.getComponentCount() > 0) {
    //         chartPanel.removeAll();
    //     }

    //     chartPanel.add(new ChartPanel(barChart));
    //     chartPanel.revalidate();
    //     chartPanel.repaint();
    // }
    // Display Bar Chart with improved X-axis label handling
private void showBudgetChart(double currentBudget, double year1, double year2, double year3) {
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    dataset.addValue(currentBudget, "Budget", "Current Year");
    dataset.addValue(year1, "Budget", "Year 1 (6% Inflation)");
    dataset.addValue(year2, "Budget", "Year 2 (7% Inflation)");
    dataset.addValue(year3, "Budget", "Year 3 (8% Inflation)");

    JFreeChart barChart = ChartFactory.createBarChart(
            "Budget Prediction",
            "Year",
            "Amount ($)",
            dataset
    );

    CategoryPlot plot = barChart.getCategoryPlot();
    BarRenderer renderer = (BarRenderer) plot.getRenderer();

    // Custom bar colors
    renderer.setSeriesPaint(0, new Color(200, 99, 71)); // Tomato Red

    // Ensure X-axis labels are visible and properly rotated
    CategoryAxis domainAxis = plot.getDomainAxis();
    domainAxis.setCategoryMargin(0.3); // Increase margin to prevent overlap
    domainAxis.setTickLabelFont(new Font("SansSerif", Font.BOLD, 14));
    domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45); // Rotate 45 degrees

    // Ensure label visibility
    domainAxis.setMaximumCategoryLabelWidthRatio(1.0f);
    domainAxis.setLowerMargin(0.05);
    domainAxis.setUpperMargin(0.05);

    // Improve bar spacing
    plot.setDomainGridlinesVisible(true);
    plot.setRangeGridlinesVisible(true);

    // Ensure proper re-rendering
    if (chartPanel.getComponentCount() > 0) {
        chartPanel.removeAll();
    }

    chartPanel.add(new ChartPanel(barChart));
    chartPanel.revalidate();
    chartPanel.repaint();
}


    private void updateResult(String message) {
        SwingUtilities.invokeLater(() -> resultArea.setText(message));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PredictYearlyBudgetUI::new);
    }
}