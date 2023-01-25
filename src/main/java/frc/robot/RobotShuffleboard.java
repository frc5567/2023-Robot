package frc.robot;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.networktables.BooleanPublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;

public class RobotShuffleboard {

    private String m_shuffleName;
    
    //declares member variable for the driver tab. 
    ShuffleboardTab m_driverTab;
    
    private BooleanPublisher m_autoLevel;

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
    
        // publish to the topic in "datatable" called "Out"
        m_autoLevel = datatable.getBooleanTopic("is level").publish();
    }

    /**
     * init method to call configuration methods for creating a shuffleboard
     */
    public void init() {
        drivetrainShuffleboardConfig();
    }

    /**
     * Method to set, from curLevel boolean, the widget boolean value for whether bot is level
     * @param isLevel boolean that tells whether bot is level, derived (as of now) only from Drivetrain's isLevel method
     */
    public void setWhetherBotIsLevel(boolean isLevel) {
        m_autoLevel.set(isLevel);

    }

    /**
     * Method that grabs shuffleboard tab for Drivetrain and creates widget (boolean color box) for checking whether bot is level
     */
    public void drivetrainShuffleboardConfig() {
        Shuffleboard.selectTab("Driver Tab");
        m_driverTab.addPersistent("is level", "red").withWidget(BuiltInWidgets.kBooleanBox);

    }
    
}
