package frc.robot;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RobotShuffleboard {
    ShuffleboardTab m_driverTab;
    Drivetrain m_drivetrain;

    Shuffleboard m_shuffleboard;
    boolean m_isLevel;

    private NetworkTableEntry m_isLevelEntry;

    public RobotShuffleboard() {
        m_driverTab = Shuffleboard.getTab("Driver Tab");
    }

    public void init() {
        //TODO: call drivetrainShuffleboardConfig() method, to be created
    }

    public void periodic(boolean isBotLevel) {
        SmartDashboard.putBoolean("is Level", isBotLevel);
    }

    //around line 170 in 2022 code
}
