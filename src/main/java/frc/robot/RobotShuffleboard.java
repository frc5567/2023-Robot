package frc.robot;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class RobotShuffleboard {
    ShuffleboardTab m_driverTab;
    Drivetrain m_drivetrain;

    //shuffle board member variables: BOTH CURRENTLY HERE MIGHT BE UNUSED, SHOULD BE TESTED
    Shuffleboard m_shuffleboard;
    boolean m_isLevel;

    //Table label entries are created here
    private GenericEntry m_isLevelEntry;

    /**
     * Main constructor for Shuffleboard class; creates tabs for Shuffleboard, though we should only need DriverTab
     */
    public RobotShuffleboard() {
        m_driverTab = Shuffleboard.getTab("Driver Tab");
    }

    //Init method to configure each of the shuffleboard widgets
    public void init() {
        shuffleboardConfig();
    }

    /**
     * Periodic method of Shuffleboard
     * mainly for updating DriverTab box values
     * @param isBotLevel is a boolean passed in for the isLevel widget for updating
     */
    public void periodic(boolean isBotLevel) {
        //SmartDashboard.putBoolean("is Level", isBotLevel);
        m_isLevelEntry.setBoolean(isBotLevel);
    }

/**
 * No need for this code to be used at the moment.
 * Code borrowed from 2022 Robot code, not useful, but used in initial implementation for debugging Shuffleboard.
 * 
    private void setIsLevelEntryThing() {
        m_isLevel = m_isLevelEntry.getBoolean(false);
    }

    public boolean getIsLevelThing(){
        setIsLevelEntryThing();
        return m_isLevel;
    }
*/

    /**
     * Method for setting widgets up with entry member variables updated to reflect widget entry values; returns and takes nothing
     */
    public void shuffleboardConfig() {
        Shuffleboard.selectTab("Driver Tab");
        m_isLevelEntry = m_driverTab.addPersistent("is Level?", false)
                                    .withWidget(BuiltInWidgets.kBooleanBox)
                                    .getEntry();
    }
}
