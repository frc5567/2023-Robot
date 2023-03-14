package frc.robot;

import frc.robot.Claw.ClawState;

/**
 * Class that encapsulates the entirety of Autonomous mode code
 */
public class Auton {

    //place for member variables to hold controls and robot parts passed in
    //ex: private RobotShuffleboard m_robotShuffleboard;

    //integer and String, respectively, to store what step (by number, 1-? steps) and what path we are on (comm out, 0, 1, or 2 object)
    int m_step;
    private String m_path = "";
    //default sets AutonPath to 0 object, creates member variable for currentPath (auton)
    private String m_currentAutonPath = RobotMap.AutonConstants.DEFAULT_AUTON_PATH;
    //boolean for showing whether auton is started and is set to false otherwise and after
    boolean m_autonStartOut = false;
    //boolean for running out of class method, set to false by default
    boolean run = false;

    /**
     * Main constructor method of the Auton class
     */
    public Auton() {
        //place to configure member variables to starting instances of robot systems

        //sets auton initial step to step 0
        m_step = 0;
    }

    /**
     * Method that runs initally at start of every Auton mode, currently sets Auton path.
     * Takes no parameters and returns nothing.
     */
    public void init() {
        //initializes elements of robot for the Auton specifically, starting flag waved
        m_step = 0;
        m_autonStartOut = true;
    }

    /**
     * Boolean method that checks running state (not path) of auton
     * @return whether auton is running via start flag and path assignment verification
     */
    public boolean isRunning() {
        if (m_path != "" && m_autonStartOut){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Method for actually grabbing selection and setting Auton path from shuffleboard; sends message to console to reflect that
     */
    public void selectPath(String autonPath) {
        m_currentAutonPath = autonPath;
        m_step = 1;

        if (m_currentAutonPath == RobotMap.AutonConstants.MID_CUBE_SHORT_COMMUNITY){
            System.out.println("Setting Auton to Mid Cube short community path");
            m_path = m_currentAutonPath;
        }
        else if (m_currentAutonPath == RobotMap.AutonConstants.MID_CONE_SHORT_COMMUNITY){
            System.out.println("Setting Auton to Mid Cone short community path");
            m_path = m_currentAutonPath;
        }
        else if (m_currentAutonPath == RobotMap.AutonConstants.MID_CUBE_LONG_COMMUNITY){
            System.out.println("Setting Auton to Mid cube long community path");
            m_path = m_currentAutonPath;
        }
        else if (m_currentAutonPath == RobotMap.AutonConstants.MID_CONE_LONG_COMMUNITY){
            System.out.println("Setting Auton to Mid cone long community path");
            m_path = m_currentAutonPath;
        }
        else if (m_currentAutonPath == RobotMap.AutonConstants.HIGH_CUBE_SHORT_COMMUNITY){
            System.out.println("Setting Auton to High Cube short community path");
            m_path = m_currentAutonPath;
        }
        else if (m_currentAutonPath == RobotMap.AutonConstants.HIGH_CONE_SHORT_COMMUNITY){
            System.out.println("Setting Auton to High Cone short community path");
            m_path = m_currentAutonPath;
        }
        else if (m_currentAutonPath == RobotMap.AutonConstants.HIGH_CUBE_LONG_COMMUNITY){
            System.out.println("Setting Auton to High cube long community path");
            m_path = m_currentAutonPath;
        }
        else if (m_currentAutonPath == RobotMap.AutonConstants.HIGH_CONE_LONG_COMMUNITY){
            System.out.println("Setting Auton to High cone long community path");
            m_path = m_currentAutonPath;
        }
        else if (m_currentAutonPath == RobotMap.AutonConstants.SHORT_COMMUNITY){
            System.out.println("Setting Auton to Community Out Long Path");
            m_path = m_currentAutonPath;
        }
        else if (m_currentAutonPath == RobotMap.AutonConstants.LONG_COMMUNITY){
            System.out.println("Setting Auton to Community Out Short Path");
            m_path = m_currentAutonPath;
        }
        else if (m_currentAutonPath == RobotMap.AutonConstants.HIGH_CHARGING_COMMUNITY){
            System.out.println("Setting Auton to High Charging Path");
            m_path = m_currentAutonPath;
        }
        else{
            System.out.println("No path selected. Please restart auton mode and choose one.");
        }
    }

    /**
     * Method that contains entirety of Auton pathing, state stuctures, and what to do in steps; also updates steps to shuffleboard and bot
     * @param stepCompleted is a boolean that is passed in from Robot denoted when a step action is completed
     * @return newInput object that can control drivetrain outside of drivetrain class via position encoders
     */
    public AutonInput periodic(boolean stepCompleted) {
        AutonInput newInput = new AutonInput(RobotState.kUnknown, 0, 0, false, ClawState.kUnknown, Double.NaN, false);
        
        if (m_autonStartOut){
            System.out.println("AUTON STARTED");
            System.out.println("Auton Path: " + m_path);
            m_autonStartOut = false;
        }

        if (stepCompleted) {
            System.out.println("Step completed!: " + m_step);
            m_step += 1;
        }

        switch(m_path) {
            case RobotMap.AutonConstants.MID_CUBE_SHORT_COMMUNITY:
            {
                switch(m_step) {
                    case 1:
                    {
                        newInput.m_desiredState = RobotState.kMidPiece;
                        System.out.println("step: " + m_step);
                        break;
                    }
                    case 2:
                    {
                        newInput.m_clawState = ClawState.kOpen;
                        System.out.println("step: " + m_step);
                        break;
                    }
                    case 3:
                    {
                        newInput.m_driveTarget = -RobotMap.AutonConstants.SHORT_COMMUNITY_DIST;
                        newInput.m_desiredState = RobotState.kTravel;
                        System.out.println("step: " + m_step);
                        break;
                    }
                    case 4:
                    {
                        newInput.m_driveTarget = -RobotMap.AutonConstants.SHORT_COMMUNITY_DIST;
                        System.out.println("step: " + m_step);
                        break;
                    }
                    case 5:
                    {
                        newInput.m_autonComplete = true;
                        System.out.println("step: " + m_step);
                        break;
                    }
                }
                break;
            }
            case RobotMap.AutonConstants.MID_CONE_SHORT_COMMUNITY:
            {
                switch(m_step) {
                    case 1:
                    {
                        newInput.m_desiredState = RobotState.kApproachMid;
                        System.out.println("step: " + m_step);
                        break;
                    }
                    case 2:
                    {
                        newInput.m_delay = 1.0;
                        System.out.println("step: " + m_step);
                        break;
                    }
                    case 3:
                    {
                        newInput.m_desiredState = RobotState.kMidPiece;
                        System.out.println("step: " + m_step);
                        break;
                    }
                    case 4:
                    {
                        newInput.m_clawState = ClawState.kOpen;
                        System.out.println("step: " + m_step);
                        break;
                    }
                    case 5:
                    {
                        newInput.m_driveTarget = -RobotMap.AutonConstants.SHORT_COMMUNITY_DIST;
                        newInput.m_desiredState = RobotState.kTravel;
                        System.out.println("step: " + m_step);
                        break;
                    }
                    case 6:
                    {
                        newInput.m_driveTarget = -RobotMap.AutonConstants.SHORT_COMMUNITY_DIST;
                        System.out.println("step: " + m_step);
                        break;
                    }
                    case 7:
                    {
                        newInput.m_autonComplete = true;
                        System.out.println("step: " + m_step);
                        break;
                    }
                }
                break;
            }
            case RobotMap.AutonConstants.MID_CUBE_LONG_COMMUNITY:
            {
                switch(m_step) {
                    case 1:
                    {
                        newInput.m_desiredState = RobotState.kMidPiece;
                        System.out.println("step: " + m_step);
                        break;
                    }
                    case 2:
                    {
                        newInput.m_clawState = ClawState.kOpen;
                        System.out.println("step: " + m_step);
                        break;
                    }
                    case 3:
                    {
                        newInput.m_driveTarget = -RobotMap.AutonConstants.LONG_COMMUNITY_DIST;
                        newInput.m_desiredState = RobotState.kTravel;
                        System.out.println("step: " + m_step);
                        break;
                    }
                    case 4:
                    {
                        newInput.m_driveTarget = -RobotMap.AutonConstants.LONG_COMMUNITY_DIST;
                        System.out.println("step: " + m_step);
                        break;
                    }
                    case 5:
                    {
                        newInput.m_autonComplete = true;
                        System.out.println("step: " + m_step);
                        break;
                    }
                }
                break;
            }
            case RobotMap.AutonConstants.MID_CONE_LONG_COMMUNITY:
            {
                switch(m_step) {
                    case 1:
                    {
                        newInput.m_desiredState = RobotState.kApproachMid;
                        System.out.println("step: " + m_step);
                        break;
                    }
                    case 2:
                    {
                        newInput.m_delay = 1.0;
                        System.out.println("step: " + m_step);
                        break;
                    }
                    case 3:
                    {
                        newInput.m_desiredState = RobotState.kMidPiece;
                        System.out.println("step: " + m_step);
                        break;
                    }
                    case 4:
                    {
                        newInput.m_clawState = ClawState.kOpen;
                        System.out.println("step: " + m_step);
                        break;
                    }
                    case 5:
                    {
                        newInput.m_driveTarget = -RobotMap.AutonConstants.LONG_COMMUNITY_DIST;
                        newInput.m_desiredState = RobotState.kTravel;
                        System.out.println("step: " + m_step);
                        break;
                    }
                    case 6:
                    {
                        newInput.m_driveTarget = -RobotMap.AutonConstants.LONG_COMMUNITY_DIST;
                        System.out.println("step: " + m_step);
                        break;
                    }
                    case 7:
                    {
                        newInput.m_autonComplete = true;
                        System.out.println("step: " + m_step);
                        break;
                    }
                }
                break;
            }
            case RobotMap.AutonConstants.HIGH_CUBE_SHORT_COMMUNITY:
            {
                switch(m_step) {
                    case 1:
                    {
                        newInput.m_desiredState = RobotState.kHighCube;
                        System.out.println("step: " + m_step);
                        break;
                    }
                    case 2:
                    {
                        newInput.m_clawState = ClawState.kOpen;
                        System.out.println("step: " + m_step);
                        break;
                    }
                    case 3:
                    {
                        newInput.m_driveTarget = -RobotMap.AutonConstants.SHORT_COMMUNITY_DIST;
                        newInput.m_desiredState = RobotState.kTravel;
                        System.out.println("step: " + m_step);
                        break;
                    }
                    case 4:
                    {
                        newInput.m_driveTarget = -RobotMap.AutonConstants.SHORT_COMMUNITY_DIST;
                        System.out.println("step: " + m_step);
                        break;
                    }
                    case 5:
                    {
                        newInput.m_autonComplete = true;
                        System.out.println("step: " + m_step);
                        break;
                    }
                }
                break;
            }
            case RobotMap.AutonConstants.HIGH_CONE_SHORT_COMMUNITY:
            {
                switch(m_step) {
                    case 1:
                    {
                        newInput.m_desiredState = RobotState.kApproachHigh;
                        System.out.println("step: " + m_step);
                        break;
                    }
                    case 2:
                    {
                        newInput.m_delay = 1.0;
                        System.out.println("step: " + m_step);
                        break;
                    }
                    case 3:
                    {
                        newInput.m_desiredState = RobotState.kHighCone;
                        System.out.println("step: " + m_step);
                        break;
                    }
                    case 4:
                    {
                        newInput.m_clawState = ClawState.kOpen;
                        System.out.println("step: " + m_step);
                        break;
                    }
                    case 5:
                    {
                        newInput.m_driveTarget = -RobotMap.AutonConstants.SHORT_COMMUNITY_DIST;
                        newInput.m_desiredState = RobotState.kTravel;
                        System.out.println("step: " + m_step);
                        break;
                    }
                    case 6:
                    {
                        newInput.m_driveTarget = -RobotMap.AutonConstants.SHORT_COMMUNITY_DIST;
                        System.out.println("step: " + m_step);
                        break;
                    }
                    case 7:
                    {
                        newInput.m_autonComplete = true;
                        System.out.println("step: " + m_step);
                        break;
                    }
                }
                break;
            }
            case RobotMap.AutonConstants.HIGH_CUBE_LONG_COMMUNITY:
            {
                switch(m_step) {
                    case 1:
                    {
                        newInput.m_desiredState = RobotState.kHighCube;
                        System.out.println("step: " + m_step);
                        break;
                    }
                    case 2:
                    {
                        newInput.m_clawState = ClawState.kOpen;
                        System.out.println("step: " + m_step);
                        break;
                    }
                    case 3:
                    {
                        newInput.m_driveTarget = -RobotMap.AutonConstants.LONG_COMMUNITY_DIST;
                        newInput.m_desiredState = RobotState.kTravel;
                        System.out.println("step: " + m_step);
                        break;
                    }
                    case 4:
                    {
                        newInput.m_driveTarget = -RobotMap.AutonConstants.LONG_COMMUNITY_DIST;
                        System.out.println("step: " + m_step);
                        break;
                    }
                    case 5:
                    {
                        newInput.m_autonComplete = true;
                        System.out.println("step: " + m_step);
                        break;
                    }
                }
                break;
            }
            case RobotMap.AutonConstants.HIGH_CONE_LONG_COMMUNITY:
            {
                switch(m_step) {
                    case 1:
                    {
                        newInput.m_desiredState = RobotState.kApproachHigh;
                        System.out.println("step: " + m_step);
                        break;
                    }
                    case 2:
                    {
                        newInput.m_delay = 1.0;
                        System.out.println("step: " + m_step);
                        break;
                    }
                    case 3:
                    {
                        newInput.m_desiredState = RobotState.kHighCone;
                        System.out.println("step: " + m_step);
                        break;
                    }
                    case 4:
                    {
                        newInput.m_clawState = ClawState.kOpen;
                        System.out.println("step: " + m_step);
                        break;
                    }
                    case 5:
                    {
                        newInput.m_driveTarget = -RobotMap.AutonConstants.LONG_COMMUNITY_DIST;
                        newInput.m_desiredState = RobotState.kTravel;
                        System.out.println("step: " + m_step);
                        break;
                    }
                    case 6:
                    {
                        newInput.m_driveTarget = -RobotMap.AutonConstants.LONG_COMMUNITY_DIST;
                        System.out.println("step: " + m_step);
                        break;
                    }
                    case 7:
                    {
                        newInput.m_autonComplete = true;
                        System.out.println("step: " + m_step);
                        break;
                    }
                }
                break;
            }
            case RobotMap.AutonConstants.SHORT_COMMUNITY:
            {
                switch(m_step) {
                    case 1:
                    {
                        newInput.m_driveTarget = RobotMap.AutonConstants.SHORT_COMMUNITY_DIST;
                        System.out.println("step: " + m_step);
                        break;
                    }
                    case 2:
                    {
                        newInput.m_autonComplete = true;
                        System.out.println("step: " + m_step);
                        break;
                    }
                }
                break;
            }
            case RobotMap.AutonConstants.LONG_COMMUNITY:
            {
                switch(m_step) {
                    case 1:
                    {
                        newInput.m_driveTarget = RobotMap.AutonConstants.LONG_COMMUNITY_DIST;
                        break;
                    }
                    case 2:
                    {
                        newInput.m_autonComplete = true;
                        break;
                    }
                }
                break;
            }
            case RobotMap.AutonConstants.HIGH_CHARGING_COMMUNITY:
            {
                switch(m_step) {
                    case 1:
                    {
                        newInput.m_desiredState = RobotState.kApproachHigh;
                        System.out.println("step: " + m_step);
                        break;
                    }
                    case 2:
                    {
                        newInput.m_delay = 1.0;
                        System.out.println("step: " + m_step);
                        break;
                    }
                    case 3:
                    {
                        newInput.m_desiredState = RobotState.kHighCone;
                        System.out.println("step: " + m_step);
                        break;
                    }
                    case 4:
                    {
                        newInput.m_clawState = ClawState.kOpen;
                        System.out.println("step: " + m_step);
                        break;
                    }
                    case 5:
                    {
                        newInput.m_driveTarget = -RobotMap.AutonConstants.FIRST_CHARGING_DIST;
                        newInput.m_desiredState = RobotState.kTravel;
                        System.out.println("step: " + m_step);
                        break;
                    }
                    case 6:
                    {
                        //case for if travel completion logic skips dist step from 5
                        newInput.m_driveTarget = -RobotMap.AutonConstants.FIRST_CHARGING_DIST;
                        System.out.println("step: " + m_step);
                        break;
                    }
                    case 7:
                    {
                        newInput.m_driveTarget = -RobotMap.AutonConstants.SECOND_CHARGING_DIST;
                        System.out.println("step: " + m_step);
                        break;
                    }
                    case 8:
                    {
                        newInput.m_driveTarget = RobotMap.AutonConstants.SECOND_CHARGING_DIST;
                        System.out.println("step: " + m_step);
                        break;
                    }
                    case 9:
                    {
                        newInput.m_autoLevel = true;
                        System.out.println("step: " + m_step);
                        break;
                    }
                    case 10:
                    {
                        newInput.m_autonComplete = true;
                        System.out.println("step: " + m_step);
                        break;
                    }
                }
                break;
            }
        }
        return newInput;
    }
}
