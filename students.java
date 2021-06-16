import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import net.proteanit.sql.DbUtils;

public class students {
    private JTextField student_id;
    private JTextField name;
    private JTextField marks;
    private JTextArea grade;
    private JTable table1;
    private JButton searchButton;
    private JButton saveButton;
    private JButton readButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JPanel main;
    private JTextField summation;
    private JTextField avg;
    private DbUtils Dbutils;
    Connection connection;
    PreparedStatement pst;


    public students() throws SQLException {

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String myId = student_id.getText();
                String myName = name.getText();
                String myGrade = grade.getText();
                String myMarks = marks.getText();


                Connection connection = null;
                ResultSet rs = null;
                String host = "localhost";
                String port = "5432";
                String db_name = "postgres";
                String username = "postgres";
                String password = "33744525";
                try {
                    Class.forName("org.postgresql.Driver");
                    connection = DriverManager.getConnection("jdbc:postgresql://" + host + ":" + port + "/" + db_name + "", "" + username + "", "" + password + "");

                    if (connection != null) {
                        String query = "insert into students1 values('" + myId + "', '" + myName + "', '" + myGrade + "','" + myMarks + "')";
                        Statement statement = connection.createStatement();
                        int x = statement.executeUpdate(query);
                        if (x == 0) {
                            JOptionPane.showMessageDialog(saveButton, "Already Added!!");
                        } else {
                            JOptionPane.showMessageDialog(saveButton, "Added!!");
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }


        });
        readButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                table_data();
                summation.setText(Integer.toString(getSum()));


            }

        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String myId = student_id.getText();
                String myName = name.getText();
                String myGrade = grade.getText();
                String myMarks = marks.getText();


                Connection connection = null;
                PreparedStatement pst = null;
                ResultSet rs = null;
                String host = "localhost";
                String port = "5432";
                String db_name = "postgres";
                String username = "postgres";
                String password = "33744525";
                DefaultTableModel tblModel = (DefaultTableModel) table1.getModel();
                try {
                    Class.forName("org.postgresql.Driver");
                    connection = DriverManager.getConnection("jdbc:postgresql://" + host + ":" + port + "/" + db_name + "", "" + username + "", "" + password + "");

                    if (connection != null) {
                        String query = "DELETE FROM students1 WHERE student_id = ?";
                        try (Statement statement = connection.createStatement()) {


                            //  pst.setString(1, student_id.getText());
                            // pst.executeUpdate(query);
                        }
                    }
                    if (table1.getSelectedRowCount() == 1) {
                        tblModel.removeRow(table1.getSelectedRow());
                    } else {
                        if (table1.getRowCount() == 0) {
                            JOptionPane.showMessageDialog(deleteButton, "Table empty!");

                        } else {
                            JOptionPane.showMessageDialog(deleteButton, "Select single row!");
                        }
                    }
                    JOptionPane.showMessageDialog(deleteButton, "deleted");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
                DefaultTableModel tblModel = (DefaultTableModel) table1.getModel();
                String tblId = tblModel.getValueAt(table1.getSelectedRow(),0).toString();
                String tbName = tblModel.getValueAt(table1.getSelectedRow(),1).toString();
                String tblGrade = tblModel.getValueAt(table1.getSelectedRow(),2).toString();
                String tblMarks = tblModel.getValueAt(table1.getSelectedRow(),3).toString();

                student_id .setText(tblId);
                name.setText(tbName);
                grade.setText(tblGrade);
                marks.setText(tblMarks);
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                DefaultTableModel tblModel = (DefaultTableModel) table1.getModel();
                if(table1.getSelectedRowCount()==1){
                    String myId= student_id.getText();
                    String myName = name.getText();
                    String myGrade = grade.getText();
                    String  myMarks = marks.getText();

                    tblModel.setValueAt(student_id,table1.getSelectedRow(),0);
                    tblModel.setValueAt(name,table1.getSelectedRow(),1);
                    tblModel.setValueAt(grade,table1.getSelectedRow(),2);
                    tblModel.setValueAt(marks,table1.getSelectedRow(),3);

                    JOptionPane.showMessageDialog(updateButton,"Updated");


                }

            }
        });
    }
        void table_data () {
            Statement statement = null;
            ResultSet rs = null;
            Connection connection = null;
            String host = "localhost";
            String port = "5432";
            String db_name = "postgres";
            String username = "postgres";
            String password = "33744525";
            try {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection("jdbc:postgresql://" + host + ":" + port + "/" + db_name + "", "" + username + "", "" + password + "");
                String query = "Select * from students1";
                statement = connection.createStatement();
                rs = statement.executeQuery(query);
                table1.setModel(Dbutils.resultSetToTableModel(rs));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        public static void main (String[]args) throws SQLException {
            JFrame frame = new JFrame("students");
            frame.setContentPane(new students().main);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);


        }
        public void column(){
       // initComponents();
        summation.setText(Integer.toString(getSum()));
        avg.setText(Float.toString(average()));

        }
        public int getSum(){
        int rowsCounts =table1.getRowCount();
        int sum=0;
        for(int i=0;i<rowsCounts;i++){
            sum = sum+Integer.parseInt(table1.getValueAt(i,2).toString());
        }
        return sum;
        }
        public float average(){
        float sum= getSum();
        int rowsCount = table1.getRowCount();
        float average =sum/rowsCount;
        return average;
        }


    }





