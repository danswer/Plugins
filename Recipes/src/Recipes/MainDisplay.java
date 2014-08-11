package Recipes;


import com.fidessa.inf.oa.OAException;
import com.fidessa.inf.oa.ServiceEvent;
import com.fidessa.inf.oa.ServiceListener;
import com.fidessa.inf.oa.Session;
import com.fidessa.inf.oa.SyncSession;
import com.fidessa.inf.utl.StructuredSet;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import beast.framework.PluginEvent;
import beast.framework.PluginListener;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author daniel.jones
 */
public class MainDisplay extends JPanel {
    private JLabel stepLabel;
    private JLabel stepNumLabel;
    private JLabel commandLabel;
    private JTextField nameField;
//    private JTextField occupationTextField;
    private JComboBox commandList;
    private JButton addInstructionBtn;
    private JButton viewBtn;
    private JButton removeBtn;
     private JButton runBtn;
     private int xComp;
     private int yComp;
     private int stepNum;
     private GridBagConstraints gc = new GridBagConstraints();
    private SyncSession session;
    private Recipes parent;
    
    public MainDisplay(Recipes rec) {

        stepLabel = new JLabel("Step #");
        stepNumLabel = new JLabel(Integer.toString(stepNum));
        commandLabel = new JLabel("Command");
        nameField = new JTextField(20);
        addInstructionBtn = new JButton("Add Instruction");        
        removeBtn = new JButton("Remove Instruction");
        runBtn = new JButton("Run");
        String[] commands = { "Create Order", "Request Settings", "Query Order" };
        JComboBox commandList= new JComboBox(commands);
        commandList.setSelectedIndex(0);
        xComp =0;
        yComp =0;
        parent = rec;
         Border innerBorder = BorderFactory.createTitledBorder("Recipes");
        Border outerBoard = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        setBorder(BorderFactory.createCompoundBorder(outerBoard, innerBorder));
        
        setLayout(new GridBagLayout());
        
        

        ////////////////// FIRST ROW /////////////////////////////
        
        gc.gridx = 0;
        gc.gridy = 0;
        gc.anchor = GridBagConstraints.NORTH;
        add(addInstructionBtn, gc);
        
        addInstructionBtn.addActionListener(new ActionListener() {

             @Override
             public void actionPerformed(ActionEvent ae) {
                 System.out.println("in listner");
                 AddDynamicInstructions(gc);
             }
         });

        gc.gridx = 1;
        gc.gridy = 0;
        gc.anchor = GridBagConstraints.NORTH;
        add(removeBtn, gc);
        removeBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                System.out.println(ae.getActionCommand() + "WE DOING SOMETHING?");
               
                parent.SendPluginMessage(new PluginEvent(ae, "MON PROCS", "Recipes"));
            }
        });

        gc.gridx = 2;
        gc.gridy = 0;
        gc.anchor = GridBagConstraints.NORTH;
        add(runBtn, gc);
        runBtn.addActionListener(new ActionListener() {

             @Override
             public void actionPerformed(ActionEvent ae) {
                 //SendMessage();
             }
         });

        gc.gridx = 0;
        gc.gridy = 1;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.NORTH;
        add(stepLabel, gc);

        gc.gridx = 1 ;
        gc.gridy = 1;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.NORTH;
        add(commandLabel, gc);

        

        gc.gridx = 0;
        gc.gridy = 2;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.NORTH;
        add(stepNumLabel, gc);

        gc.gridx = 1;
        gc.gridy = 2;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.NORTH;
        add(commandList, gc);
        
        xComp =1;
        yComp =2;

    }
    
    public void AddDynamicInstructions (GridBagConstraints gc) {
        stepNum++;
        JLabel stepNumberLabel = new JLabel(Integer.toString(stepNum));
        String[] commands = { "Create Order", "Request Settings", "Query Order" };
        JComboBox commandList= new JComboBox(commands);
        commandList.setSelectedIndex(0);
        xComp=0;
        yComp++;
        System.out.println(xComp + yComp);

        gc.gridx = xComp;
        gc.gridy = yComp;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.NORTH;
        add(stepNumberLabel, gc);
        
        xComp++;
        

        gc.gridx = xComp;
        gc.gridy = yComp;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.NORTH;
        add(commandList, gc);
     
        
       
    }
    
    public void SendMessage() {
        
        try
        {
            Session session = new Session("APAS_ETH");
            session.addServiceListener(new MyServiceListener());
            String value = "";
            
            int version = 1;            
            StructuredSet rootSds = new StructuredSet();
            rootSds.add("MESSAGE_TYPE", "TXN_MODIFY");
            rootSds.add("USER_NAME", "System.User@NEFO.US");
            rootSds.add("SOURCE_ID", "TEST");
            rootSds.add("MF_VERSION", 1);
            
            StructuredSet detailSds = new StructuredSet();
            detailSds.add("ITEM_NAME", "THE_BEAST");
            
                detailSds.add("ITEM_VALUE", "CreateOrder");
          
            
            
            rootSds.add("SYSTEM", detailSds);
            
            parent.textPanel.appender("About to send BEAST Message...\n" + rootSds.toString());
            boolean updateValue = session.write(rootSds);
            
            if (!updateValue)
            {
                parent.textPanel.appender("BEAST Message cant send...\n");
            } else
            {
                parent.textPanel.appender("BEAST Message sent successful...\n");
            }
            
        } catch (OAException e)
        {
            e.printStackTrace();
        }
        
           
    }
    static class MyServiceListener implements ServiceListener
    {
        public void serviceUp(ServiceEvent event)
        {
            String serviceName = event.getService();
            if (serviceName != null)
            {
                System.out.println("service is up:" + serviceName);
            }
        }
        public void serviceDown(ServiceEvent event) 
        {
            String serviceName = event.getService();
            if (serviceName != null) {
               System.out.println("service is  down: " + serviceName);
            }
        }
    }

}
