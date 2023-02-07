package frc.robot;

import edu.wpi.first.networktables.GenericEntry;
//import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RobotShuffleboard {
    ShuffleboardTab m_driverTab;
    Drivetrain m_drivetrain;

    Shuffleboard m_shuffleboard;
    boolean m_isLevel;

    private GenericEntry m_isLevelEntry;

    public RobotShuffleboard() {
        m_driverTab = Shuffleboard.getTab("Driver Tab");
    }

    public void init() {
        drivetrainShuffleboardConfig();
    }

    public void periodic(boolean isBotLevel) {
        SmartDashboard.putBoolean("is Level", isBotLevel);
        setIsLevelEntryThing();
    }

    private void setIsLevelEntryThing() {
        m_isLevel = m_isLevelEntry.getBoolean(false);
    }

    public boolean getIsLevelThing(){
        setIsLevelEntryThing();
        return m_isLevel;
    }

    public void drivetrainShuffleboardConfig() {
        Shuffleboard.selectTab("Driver Tab");
        m_isLevelEntry = m_driverTab.addPersistent("is Level?", false)
                                    .withWidget(BuiltInWidgets.kBooleanBox)
                                    .getEntry();
    }
}
