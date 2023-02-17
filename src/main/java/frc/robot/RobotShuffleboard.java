package frc.robot;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

/**
 * Robot Shuffleboard class created for user input for the variety of classes needing it
 */
public class RobotShuffleboard {

    //member variables instantiated for tab object use
    ShuffleboardTab m_driverTab;

    //Table label entries are created here
    private GenericEntry m_isLevelEntry;
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
    public void periodic(boolean isBotLevel, double xOffset, double areaOfScreen) {
        //SmartDashboard.putBoolean("is Level", isBotLevel);
        m_isLevelEntry.setBoolean(isBotLevel);
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
        m_xOffsetEntry = m_driverTab.add("x Angle Offset", 0.0)
                                    .withWidget(BuiltInWidgets.kTextView)
                                    .getEntry();
        m_areaOfScreenEntry = m_driverTab.add("Area of Screen", 0.0)
                                    .withWidget(BuiltInWidgets.kTextView)
                                    .getEntry();
    }
}
