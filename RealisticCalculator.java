import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RealisticCalculator extends JFrame implements ActionListener {

    private JTextField display;
    private String currentInput = "";
    private double result = 0;
    private String operator = "";
    private boolean startNewNumber = true;

    public RealisticCalculator() {
        setTitle("Realistic Calculator");
        setSize(400, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        display = new JTextField("0");
        display.setFont(new Font("Arial", Font.BOLD, 30));
        display.setEditable(false);
        display.setHorizontalAlignment(SwingConstants.RIGHT);

        JPanel buttonPanel = new JPanel(new GridLayout(5, 4, 10, 10));
        String[] buttons = {
            "C", "", "", "/",
            "7", "8", "9", "*",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "0", ".", "=", ""
        };

        for (String label : buttons) {
            JButton btn = new JButton(label);
            btn.setFont(new Font("Arial", Font.PLAIN, 20));
            if (!label.isEmpty()) {
                btn.addActionListener(this);
            } else {
                btn.setEnabled(false);
            }
            buttonPanel.add(btn);
        }

        add(display, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
    }

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if (cmd.matches("[0-9\\.]")) {
            if (startNewNumber) {
                currentInput = "";
                startNewNumber = false;
            }
            currentInput += cmd;
            display.setText(currentInput);
        } else if (cmd.matches("[\\+\\-\\*/]")) {
            calculate(Double.parseDouble(currentInput));
            operator = cmd;
            startNewNumber = true;
        } else if (cmd.equals("=")) {
            calculate(Double.parseDouble(currentInput));
            display.setText(String.valueOf(result));
            operator = "";
            startNewNumber = true;
        } else if (cmd.equals("C")) {
            currentInput = "";
            operator = "";
            result = 0;
            display.setText("0");
            startNewNumber = true;
        }
    }

    private void calculate(double number) {
        switch (operator) {
            case "+": result += number; break;
            case "-": result -= number; break;
            case "*": result *= number; break;
            case "/": 
                if (number == 0) {
                    display.setText("Error: Divide by 0");
                    result = 0;
                    return;
                }
                result /= number; break;
            default: result = number;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new RealisticCalculator().setVisible(true);
        });
    }
}
