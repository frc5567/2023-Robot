package frc.robot;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.networktables.BooleanPublisher;
import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.GenericSubscriber;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;

public class RobotShuffleboard {

    private String m_shuffleName;
    
    //declares member variable for the driver tab. 
    ShuffleboardTab m_driverTab;

    //declares member variable for tab updater for autoLevel function
    private BooleanPublisher m_autoLevel;
    //declares member variable for tab updater for whether autonRunning
    private BooleanPublisher m_autonRunning;
    //declares member variable for auton path and 
    double m_autonPath;
    private DoublePublisher m_autonPathEntry;

    /**
     * Constructor method that creates table for Shuffleboard, with subtable "Datatable".
     * 
     * creates a table tab for the checking the bot level
     * @param shuffleName assigns the name of an instantiated Shuffleboard
     */
    public RobotShuffleboard(String shuffleName) {
        m_shuffleName = shuffleName;
        m_driverTab = Shuffleboard.getTab("Driver Tab");
        NetworkTableInstance inst = NetworkTableInstance.getDefault();

        // get the subtable called "datatable"
        NetworkTable datatable = inst.getTable("Shuffleboard");
    
        // publish a topic in "datatable" called "is level"
        m_autoLevel = datatable.getBooleanTopic("is level").publish();

        // publish a topic in "datatable" called "auton running"
        m_autoLevel = datatable.getBooleanTopic("auton running").publish();

        // publish a topic in "datatable" called "auton"
        m_autonPathEntry = datatable.getDoubleTopic("auton").publish();
    }

    /**
     * init method to call configuration methods for creating a shuffleboard
     */
    public void init() {
        drivetrainShuffleboardConfig();
        setAutonPath();
    }

    /**
     * Method to set, from curLevel boolean, the widget boolean value for whether bot is level
     * @param isLevel boolean that tells whether bot is level, derived (as of now) only from Drivetrain's isLevel method
     */
    public void setWhetherBotIsLevel(boolean isLevel) {
        m_autoLevel.set(isLevel);

    }

    /**
     * Method to set, from curAutonState boolean, the widget boolean value for whether bot Auton is running
     * @param autonRunning boolean that tells whether bot is level, derived (as of now) only from Drivetrain's isLevel method
     */
    public void setWhetherAutonRunning(boolean autonRunning) {
        m_autonRunning.set(autonRunning);

    }


    /**
     * Method for setting autonPath on shuffleboard, default is 0 object auton
     * Takes no parameters and returns nothing.
     * Used for both Auton and Robot classes
     */
    public void setAutonPath() {
        //typecasted to GenericSubscriber for grabbing value, not setting like Publisher
        //TODO: see if there is better (less clunky) method for finding value of autonPathEntry
        m_autonPath = ((GenericSubscriber) m_autonPathEntry).getDouble(RobotMap.RobotShuffleboardConstants.DEFAULT_AUTON_PATH);
    }

    /**
     * Method to grab the current Auton path from the shuffleboard
     * specifically for translating the choice to the Auton class
     * @return the path (double) that the robot will take in auton; takes no parameters
     */
    public double getAutonPath(){
        setAutonPath();
        return m_autonPath;
    }


    /**
     * Method that grabs shuffleboard tab for Drivetrain and creates widget (boolean color box) for checking whether bot is level
     */
    public void drivetrainShuffleboardConfig() {
        Shuffleboard.selectTab("Driver Tab");
        m_driverTab.addPersistent("is level", "red")
                                    .withWidget(BuiltInWidgets.kBooleanBox)
                                    .getEntry();

        //created boolean box for checking whether auton actually started and is running, figured it would be useful for shuffleboard interface, as well as console
        m_driverTab.addPersistent("auton started", "red")
                                    .withWidget(BuiltInWidgets.kBooleanBox)
                                    .getEntry();
        
        //widget created for auton similar to widget for whether bot is level, but value can be assigned to entry member variable for later use
        m_autonPathEntry = (DoublePublisher) m_driverTab.addPersistent("Auton Path", RobotMap.RobotShuffleboardConstants.DEFAULT_AUTON_PATH)
                                    .withWidget(BuiltInWidgets.kTextView)
                                    .getEntry();

    }
    
}
