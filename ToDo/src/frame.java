import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class frame extends JFrame{

    private JPanel panel;
    private JLabel label;
    private JButton addButton;
    private JButton clearAllButton;

    private int count = 0;
    private ArrayList<Task> tasks = new ArrayList<Task>();

    public frame(){

        initializeComponents();
        setUpLayout();
        setUpListeners();

        this.setSize(500,700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void initializeComponents(){
        panel = new JPanel();
        label = new JLabel("DAILY TO-DO LIST");
        addButton = new JButton("ADD TASK");
        clearAllButton = new JButton("CLEAR COMPLETED TASK");
    }

    private void setUpLayout(){
        panel.setLayout(null);
        label.setBounds(200, 30, 200, 30);
        addButton.setBounds(75, 600, 100, 30);
        clearAllButton.setBounds(270, 600, 200, 30);

        panel.add(label);
        panel.add(addButton);
        panel.add(clearAllButton);
        this.add(panel);
    }

    private void setUpListeners(){
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTask();
            }
        });

        clearAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearTasks();
            }
        });
    }
    private void clearTasks(){
        count = 0;
        ArrayList<Task> completedTasks = new ArrayList<>();

        for (Task task : tasks) {
            if (task.completed) {
                panel.remove(task.textfield);
                panel.remove(task.button);
                completedTasks.add(task);
            }
        }

        tasks.removeAll(completedTasks);

        // Reposition the remaining tasks
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            int newY = 70 + i * 100;
            task.textfield.setBounds(25, newY, 325, 70);
            task.button.setBounds(400, newY, 60, 70);
        }

        panel.revalidate();
        panel.repaint();

    }

    private void addTask(){
        if (count == 4) {
            addButton.setEnabled(false);
        }

        JTextField textField = new JTextField();
        textField.setBounds(25, 70 + tasks.size() * 100, 325, 70);
        textField.setHorizontalAlignment(JTextField.CENTER);

        JButton doneButton = new JButton("DONE");
        doneButton.setBounds(400, 70 + tasks.size() * 100, 60, 70);
        doneButton.setBackground(Color.RED);

        Task task = new Task(textField, doneButton);

        doneButton.addActionListener(e -> {
            task.setToComplete();
            doneButton.setBackground(Color.GREEN);
        });

        panel.add(textField);
        panel.add(doneButton);
        panel.revalidate();
        panel.repaint();

        tasks.add(task);

        count++;
    }

    public class Task{

        public JTextField textfield;
        public JButton button;
        public boolean completed;

        public Task(JTextField textfield,JButton button){
            this.textfield = textfield;
            this.button = button;
            this.completed = false;
        }

        public void setToComplete(){
            this.completed = true;
        }

    }

}