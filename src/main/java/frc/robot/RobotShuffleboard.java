package frc.robot;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

/**
 * Robot Shuffleboard class created for user input for the variety of classes needing it.
 */
public class RobotShuffleboard {

    //member variable instantiated for tab object use
    ShuffleboardTab m_driverTab;

    //Table label entries are created here
    private GenericEntry m_isLevelEntry;
    private GenericEntry m_autonRunningEntry;
    private GenericEntry m_autonStateEntry;
    private GenericEntry m_xOffsetEntry;
    private GenericEntry m_areaOfScreenEntry;

    /**
     * Main constructor for Shuffleboard class; creates tabs for Shuffleboard, though we should only need DriverTab
     */
    public RobotShuffleboard() {
        m_driverTab = Shuffleboard.getTab("Driver Tab");
    }

    /**
     * Init method to configure each of the shuffleboard widgets
     */
    public void init() {
        shuffleboardConfig();
    }

    /**
     * Periodic method of Shuffleboard
     * mainly for updating DriverTab box values
     * @param isBotLevel is a boolean passed in for the isLevel widget for updating
     * @SmartDashboardComments with SmartDashboard outputs are for use if the Entry tabs do not properly update, but they have no use at the moment (as the tabs do properly update)
     */
    public void periodic(boolean isBotLevel, boolean autonRunning, String autonStateString, double xOffset, double areaOfScreen) {
        //SmartDashboard.putBoolean("is Level", isBotLevel);
        m_isLevelEntry.setBoolean(isBotLevel);
        //SmartDashboard.putBoolean("Auton Running?", autonRunning);
        m_autonRunningEntry.setBoolean(autonRunning);
        m_autonStateEntry.setString(autonStateString);

        //SmartDashboard.putBoolean("x Angle Offset", xOffset);
        //SmartDashboard.putBoolean("Area of Screen", areaOfScreen);
        //TODO: EXIST ERROR; add back and test these elements when we actually have them (currently erroring due to existance failure)
        //m_xOffsetEntry.setDouble(xOffset);
        //m_areaOfScreenEntry.setDouble(areaOfScreen);
    }

    /**
     * Method for setting widgets up with entry member variables updated to reflect widget entry values; returns and takes nothing
     */
    public void shuffleboardConfig() {
        Shuffleboard.selectTab("Driver Tab");
        m_isLevelEntry = m_driverTab.add("is Level?", false)
                                    .withWidget(BuiltInWidgets.kBooleanBox)
                                    .getEntry();
        m_autonRunningEntry = m_driverTab.add("Auton Running?", false)
                                    .withWidget(BuiltInWidgets.kBooleanBox)
                                    .getEntry();
        m_autonStateEntry = m_driverTab.add("Auton Path:", "No Path Running; pick from SmartDashboard!")
                                    .withWidget(BuiltInWidgets.kTextView)
                                    .getEntry();                            
        m_xOffsetEntry = m_driverTab.add("x Angle Offset", 0.0)
                                    .withWidget(BuiltInWidgets.kTextView)
                                    .getEntry();
        m_areaOfScreenEntry = m_driverTab.add("Area of Screen", 0.0)
                                    .withWidget(BuiltInWidgets.kTextView)
                                    .getEntry();
    }
}
