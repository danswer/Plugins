package Recipes;

import static Recipes.Recipes.NEED_APAS;
import beast.framework.BeastPlugin;
import beast.framework.PluginEvent;
import beast.framework.PluginListener;
import beast.framework.PluginManager;
import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author daniel.jones
 */
public final class Recipes extends BeastPlugin implements PluginListener {

    /**
     * Global variables
     */
    private static final long serialVersionUID = 1L;
    public static String version = "v1.0";
    public static String description = "Recipes";
    public static boolean NEED_APAS = false;
    public static boolean RUNS_ON_WINDOWS = true;
    public static boolean RUNS_ON_UNIX = false;
    private PluginListener toBeastListener = this;    
    public ImageIcon icon;
    public JPanel mainDisplay;
    public TextPanel textPanel;
    public boolean loggedIn;

    public Recipes(){

        
        setSize(600, 500);

        NEED_APAS = true;
        icon = new ImageIcon(getClass()
                .getResource("/images/recipes.jpg"));
        //System.out.print("The heigh is: " + icon.getIconHeight()
        //		+ " the width is: " + icon.getIconWidth());
       

        super.setFrameIcon(icon);
        setLayout(new BorderLayout());
        textPanel = new TextPanel();
        mainDisplay = new MainDisplay(Recipes.this);

        this.add(textPanel, BorderLayout.CENTER);
        this.add(mainDisplay, BorderLayout.WEST);

        //setSize(600, 500);
        // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        //APASLogon();

    }

    public PluginListener getToBeastListener() {
        System.out.println("getting to beast listener");
        return this;
    }

//    public void APASLogon() {
//        int logonGatewayId = 1000;
//        String[] logonGatewayAddresses = {"ny1-dmem-nefo-10:17000"};
//        String systemGroupId = "NEFO_DEV";
//        String application = null;
//        String[] services;
//        try {
//            if (loggedIn != true) {
//            OpenAccess.initialise(logonGatewayId, logonGatewayAddresses, systemGroupId, application);
//            OpenAccess.logon("System.User@NEFO.US", "System.User@NEFO.US", -1);
//                        services = OpenAccess.getServices();
//            
//	    for (String s : services)
//	    {
//		System.out.println(s);
//	    } 
//            loggedIn = true;
//        }
//        } catch (OAException e) {
//            e.printStackTrace();
//        }
//    }


    /**
     * Retrieves the version of the plugin
     *
     * @return String with version number
     */
    public String GetVersion() {
        return version;
    }

    /**
     * Retrieves description of the plugin
     *
     * @return String with description
     */
    public String GetDescription() {
        return description;
    }

    /**
     * Returns the image associated with the plugin. If "icon" variable doesn't
     * get overwritten a default image will be used.
     *
     * @return ImageIcon
     */
    public ImageIcon GetIconImage() {
        return icon;
    }

    /**
     * Returns whether the plugin runs on Windows
     *
     * @return boolean true if runs on windows, false otherwise
     */
    public boolean RunsOnWindows() {
        return RUNS_ON_WINDOWS;
    }

    /**
     * Returns whether the plugin runs on Unix
     *
     * @return boolean true if runs on Unix, false otherwise
     */
    public boolean RunsOnUnix() {
        return RUNS_ON_UNIX;
    }

    /**
     * Returns whether the plugin requires an APAS connection to work.
     *
     * @return boolean true if requires APAS connection, false otherwise
     */
    public boolean NeedsAPAS() {
        return NEED_APAS;
    }

 
    /**
     * init function will be called by BEAST. Developer needs to implement this
     * function. All GUI elements need to added and set visible in this method.
     */
    public void init() {
        System.out.print("initiating\n" + this.toString());
        PluginManager.RegisterListener(this.getClass().toString(),this);
        setVisible(true);

    }

     /**
     * This function will raise a plugin event
     * @param message
     */
    public synchronized void SendPluginMessage(PluginEvent message) {
        PluginEvent msg = message;

        PluginManager.BroadCastMessage(msg);
    }

    @Override
    public void PluginEventReceived(PluginEvent event) {
        System.out.println(event.GetMessage());
    }

    
}
