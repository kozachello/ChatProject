package home.chatbot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Козак on 15.06.2017.
 */
public class SimpleChatBot extends JFrame implements ActionListener {

    final String TITLE_OF_PROGRAM = "Robot: ";
    final int START_LOCATION = 200;
    final int WINDOW_WIDTH = 400;
    final int WINDOW_HEIGHT = 400;

    JCheckBox ai;
    JTextArea dialog;
    JTextField message;
    SimpleBot bot;

    public static void main(String[] args) {

        new SimpleChatBot();

    }

    SimpleChatBot() {

        setTitle(TITLE_OF_PROGRAM);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(START_LOCATION, START_LOCATION, WINDOW_WIDTH, WINDOW_HEIGHT);

        // dialog area
        dialog = new JTextArea();
        dialog.setLineWrap(true);
        JScrollPane scrollBar = new JScrollPane(dialog);

        // panel for checkbox, message field & button
        JPanel bp = new JPanel();
        bp.setLayout(new BoxLayout(bp, BoxLayout.X_AXIS));
        ai = new JCheckBox("AI");
        ai.doClick();
        message = new JTextField();
        message.addActionListener(this);
        JButton button = new JButton("enter");
        button.addActionListener(this);

        // adding all elements to the window
        bp.add(ai);
        bp.add(message);
        bp.add(button);
        add(BorderLayout.CENTER, scrollBar);
        add(BorderLayout.SOUTH, bp);
        setVisible(true);
        bot = new SimpleBot(); // creating bot
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(message.getText().trim().length() > 0) {
            dialog.append(message.getText() + "\n");
            dialog.append(TITLE_OF_PROGRAM + bot.sayInReturn(message.getText(), ai.isSelected()) + "\n");
        }
        message.setText("");
        message.requestFocusInWindow();
    }
}
