
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class sensorTable extends javax.swing.JFrame{   
    
     public sensorTable() {
//        initComponents();
    }
     
     @SuppressWarnings("unchecked")
    
    public static DefaultTableModel model = new DefaultTableModel();
    public static ArrayList<Sensor> mainList;
    public static String clickedID;

    public static void getSensorDetailsFromRMI() throws IOException, InterruptedException, JSONException {
               /* Create and display the form */
        System.setProperty("java.security.policy", "file:allowall.policy");
        
        RMIService service = null;
          try {
            // RMI service reqeust/response end point
            service = (RMIService) Naming.lookup("//localhost/rmiservice");

            // assign all sensor JSON strings to JSONArray
            JSONArray sensors = new JSONArray(service.getSensorDetails());

            // define list of ArraList with a type of Sensor
            ArrayList<Sensor> list = new ArrayList<Sensor>();

            // put all sensor objects into list array
            for (int i = 0; i < sensors.length(); i++) {
                JSONObject sensor = sensors.getJSONObject(i);
                String _id = sensor.getString("_id");
                String status = sensor.getString("status");
                int floorNo = sensor.getInt("floorNo");
                int roomNo = sensor.getInt("roomNo");
                int smokeLevel = sensor.getInt("smokeLevel");
                int CO2Level = sensor.getInt("CO2Level");

                Sensor s = new Sensor(_id, status, floorNo, roomNo, smokeLevel, CO2Level);
                list.add(s);
            }
            // mainList = list;

            // show add sensor details to jFrame table row
            Object rowData[] = new Object[6];
            for (int i = 0; i < list.size(); i++) {
                rowData[0] = list.get(i).status;
                rowData[1] = list.get(i).floorNo;
                rowData[2] = list.get(i).roomNo;
                rowData[3] = list.get(i).smokeLevel;
                rowData[4] = list.get(i).co2Level;
                rowData[5] = list.get(i)._id;
                model.addRow(rowData);

                // if sensor status is ACTIVE
                if (list.get(i).status.equals("ACTIVE")) {
                    // check for CO2 level and SMOKE level and show alert if true
                    if (list.get(i).co2Level >= 5 || list.get(i).smokeLevel >= 5) {
                        int count = 0;
                        if (list.get(i).co2Level >= 5 && list.get(i).smokeLevel >= 5 && count <= 1) {
                            JOptionPane.showMessageDialog(null, "Floor no " + list.get(i).floorNo + " Room No "
                                    + list.get(i).roomNo + " Co2 Level & Smoke level are  High");
                            count++;
                        } else if (list.get(i).co2Level >= 5 && count <= 1) {
                            JOptionPane.showMessageDialog(null, "Floor no " + list.get(i).floorNo + " Room No "
                                    + list.get(i).roomNo + " Co2 Level is High");
                            count++;
                        } else if (list.get(i).smokeLevel >= 5 && count <= 1) {
                            JOptionPane.showMessageDialog(null, "Floor no " + list.get(i).floorNo + " Room No "
                                    + list.get(i).roomNo + " Smoke Level is High");
                            count++;
                        }
                    }
                }
            }
        } catch (NotBoundException ex) {
            System.err.println(ex.getMessage());
        } catch (MalformedURLException ex) {
            System.err.println(ex.getMessage());
        } catch (RemoteException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException, JSONException{
       
        // create JFrame and JTable
        JFrame frame = new JFrame();
        JTable table = new JTable(); 
        
        // create a table model and set a Column Identifiers to this model 
        Object[] columns = {"STATUS","FLOOR NO","ROOM NO","SMOKE LEVEL", "CO2 LEVEL", "ID"}; 

        model.setColumnIdentifiers(columns);
        
        // set the model to the table
        table.setModel(model);
        
        // Change A JTable Background Color, Font Size, Font Color, Row Height
        table.setBackground(Color.LIGHT_GRAY);
        table.setForeground(Color.black);
        Font font = new Font("",1,14);
        table.setFont(font);
        table.setRowHeight(30);
        
        // create JTextFields
        JTextField textFname = new JTextField();
        JTextField textLname = new JTextField();
        
        // create Dropdown Menu
        String[] choices = { "ACTIVE","INACTIVE"};

        final JComboBox<String> cb = new JComboBox<String>(choices);

        
        // create JButtons
        JButton btnAdd = new JButton("Add");
        JButton btnUpdate = new JButton("Update");     
      
        cb.setBounds(20, 210, 100, 25);
        
        textFname.setBounds(20, 250, 100, 25);
        textLname.setBounds(20, 295, 100, 25);
        
        btnAdd.setBounds(150, 220, 100, 25);
        btnUpdate.setBounds(150, 265, 100, 25);
        
        // create JScrollPane
        JScrollPane pane = new JScrollPane(table);
        pane.setBounds(0, 0, 880, 200);
        
        frame.setLayout(null);
        
        frame.add(pane);
        
        // add JTextFields to the jframe
        frame.add(textFname);
        frame.add(textLname);
    
        // add JButtons to the jframe
        frame.add(btnAdd);
        frame.add(btnUpdate);
        
        // add Droptown menu to the jframe
        frame.add(cb);
        
        // create an array of objects to set the row data
        Object[] row = new Object[4];
        
        // button add row
        btnAdd.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {               
                System.setProperty("java.security.policy", "file:allowall.policy");

                RMIService service = null;
                try {
                    service = (RMIService) Naming.lookup("//localhost/rmiservice");
                    service.addSensor(textFname.getText() , textLname.getText(), String.valueOf(cb.getSelectedItem()));
                    model.setRowCount(0);
                    getSensorDetailsFromRMI();
                } catch (NotBoundException ex) {
                    System.err.println(ex.getMessage());
                } catch (MalformedURLException ex) {
                    System.err.println(ex.getMessage());
                } catch (RemoteException ex) {
                    System.err.println(ex.getMessage());
                } catch (IOException ex) {
                   // Logger.getLogger(AddSensorForm.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                  //  Logger.getLogger(AddSensorForm.class.getName()).log(Level.SEVERE, null, ex); 
                } catch (JSONException ex) {
                    Logger.getLogger(sensorTable.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
 
        });
        
        
        // get selected row data From table to textfields 
        table.addMouseListener(new MouseAdapter(){
        
            @Override
            public void mouseClicked(MouseEvent e){

                // i = the index of the selected row
                int i = table.getSelectedRow();

                textFname.setText(model.getValueAt(i, 1).toString());
                textLname.setText(model.getValueAt(i, 2).toString());
                clickedID = model.getValueAt(i, 5).toString();
            }
        });
        
        // button update row
        btnUpdate.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
             System.setProperty("java.security.policy", "file:allowall.policy");
              
                RMIService service = null;
                try {
                    service = (RMIService) Naming.lookup("//localhost/rmiservice");
                    service.updateSensor(clickedID, textFname.getText(), textLname.getText(),  String.valueOf(cb.getSelectedItem()));
                    model.setRowCount(0);
                    getSensorDetailsFromRMI();
                } catch (NotBoundException ex) {
                    System.err.println(ex.getMessage());
                } catch (MalformedURLException ex) {
                    System.err.println(ex.getMessage());
                } catch (RemoteException ex) {
                    System.err.println(ex.getMessage());
                } catch (IOException ex) {
                   // Logger.getLogger(AddSensorForm.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                  //  Logger.getLogger(AddSensorForm.class.getName()).log(Level.SEVERE, null, ex); 
                } catch (JSONException ex) {
                    Logger.getLogger(sensorTable.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
         java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new sensorTable().setVisible(true);
            }
        });
        
        getSensorDetailsFromRMI();
        
        frame.setSize(900,400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
         // add timer to request data withing a time limit (every 15 seconds)
        TimerTask task = new TimerTask() {
                   
            @Override
            public void run() {
                try {
                    model.setRowCount(0);
                    getSensorDetailsFromRMI();
                } catch (IOException ex) {
                    Logger.getLogger(sensorTable.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(sensorTable.class.getName()).log(Level.SEVERE, null, ex);
                } catch (JSONException ex) {
                    Logger.getLogger(sensorTable.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        
        Timer timer = new Timer("Timer");
        long delay = 15000L;
        timer.schedule(task, 0, delay);
    }
}