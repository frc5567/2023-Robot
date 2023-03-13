package frc.robot;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
    private GenericEntry m_autonStepEntry;
    //private GenericEntry m_xOffsetEntry;
    //private GenericEntry m_areaOfScreenEntry;

    /**
     * Main constructor for Shuffleboard class; creates tabs for Shuffleboard, though we should only need DriverTab
     */
    public RobotShuffleboard() {
        m_driverTab = Shuffleboard.getTab("[Driver Tab]");
    }

    /**
     * Init method to configure each of the shuffleboard widgets
     */
    public void init() {
        shuffleboardConfig();
    }

    /**
     * Periodic method of Shuffleboard
     * mainly for updating DriverTab widget values
     * @param isBotLevel is a boolean passed in for the isLevel widget for updating
     * @param autonRunning is a boolean passed for seeing whether Auton is running
     * @param autonStateString String for updating what path the Auton mode is running
     * @param autonStep Step integer for seeing what step Auton is on, regardless of path
     * @param xOffset double for seeing Limelight xOffset var updated
     * @param areaOfScreen double for seeing Limelight areaOfScreen var updated
     * @CodedSmartDashboardComments code with SmartDashboard outputs in mind are for use if the Entry tabs do not properly update, but they have no use at the moment (as the tabs do properly update)
     */
    public void periodic(boolean isBotLevel, boolean autonRunning, String autonStateString, int autonStep, double xOffset, double areaOfScreen) {
        //SmartDashboard.putBoolean("is Level", isBotLevel);
        m_isLevelEntry.setBoolean(isBotLevel);

        //SmartDashboard.putBoolean("Auton Running?", autonRunning);
        m_autonRunningEntry.setBoolean(autonRunning);
        //SmartDashboard.putString("Auton Path:", autonStateString);
        m_autonStateEntry.setString(autonStateString);
        //SmartDashboard.putNumber("Auton Step:", autonStep);
        m_autonStepEntry.setInteger(autonStep);

        //SmartDashboard.putNumber("x Angle Offset", xOffset);
        //SmartDashboard.putNumber("Area of Screen", areaOfScreen);
        //TODO: EXIST ERROR; add back and test these elements when we actually have them (currently erroring due to existance failure)
        //m_xOffsetEntry.setDouble(xOffset);
        //m_areaOfScreenEntry.setDouble(areaOfScreen);
    }

    /**
     * Method for setting widgets up with entry member variables updated to reflect widget entry values; returns and takes nothing
     */
    public void shuffleboardConfig() {
        Shuffleboard.selectTab("[Driver Tab]");
        //Robot state widgets
        m_isLevelEntry = m_driverTab.add("is Level?", false)
                                    .withWidget(BuiltInWidgets.kBooleanBox)
                                    .getEntry();
        //Auton widgets
        m_autonRunningEntry = m_driverTab.add("Auton Running?", false)
                                    .withWidget(BuiltInWidgets.kBooleanBox)
                                    .getEntry();
        m_autonStateEntry = m_driverTab.add("Auton Path:", "No Path Running; pick from SmartDashboard!")
                                    .withWidget(BuiltInWidgets.kTextView)
                                    .getEntry();
        m_autonStepEntry = m_driverTab.add("Auton Step:", 0)
                                    .withWidget(BuiltInWidgets.kTextView)
                                    .getEntry();
        //Limelight widgets                         
        // m_xOffsetEntry = m_driverTab.add("x Angle Offset", 0.0)
        //                             .withWidget(BuiltInWidgets.kTextView)
        //                             .getEntry();
        // m_areaOfScreenEntry = m_driverTab.add("Area of Screen", 0.0)
        //                             .withWidget(BuiltInWidgets.kTextView)
        //                             .getEntry();
    }
}
