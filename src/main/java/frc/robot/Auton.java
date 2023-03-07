package frc.robot;

import frc.robot.Claw.ClawState;

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
    //boolean for autoLeveling function implementation
    boolean toRunAutoLevelOrNotToRun = false;
    //for use in counting cycles of periodic for wait time
    private int periodicTicCounter = 0;


    /**
     * Constructor method of the Auton class with passed in robot classes
     * @param shuffleboard passes in the shuffleboard communcations and allows updating of that console during Auton
     */
    public Auton() {
        //configure member variables to starting instances of robot systems
        //ex: m_robotShuffleboard = shuffleboard; where shuffleboard is a RobotShuffleboard parameter taken in
        //sets auton initial step to step 0
        m_step = 0;
    }

    /**
     * Method that runs initally at start of every Auton mode, sets Auton path.
     * Takes no parameters and returns nothing.
     */
    public void init() {
        //initializes elements of robot for the Auton specifically
        //m_currentAutonPath = m_robotShuffleboard.getAutonPath();
        m_step = 0;
        m_autonStartOut = true;
    }

    /**
     * Method that checks running state (not path) of auton
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
     * Method for actually selecting Auton path from shuffleboard and set console to reflect that
     */
    public void selectPath(String autonPath) {
        m_currentAutonPath = autonPath;
        m_step = 1;
        //path integer assigned is based on number of objects auton has set to achieve
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
        else{
            System.out.println("No path selected. Please restart auton mode and choose one.");
        }
    }

    /**
     * Method that contains entirety of Auton code and what to do in steps, also updates steps to shuffleboard and bot
     * @param drivePos is a DriveEncoderPos object that is passed in to periodically grab the current position
     * @param isBotLevelBack is a boolean input for passed in from Robot to see if the bot is level; mostly for charging station
     * @return DriveInput object that can control drivetrain outside of drivetrain class via position encoders
     */
    //TODO: sysout step changes should eventually become updates to some shuffleboard widget instead of console (messy, current)
    public AutonInput periodic(boolean stepCompleted) {
        AutonInput newInput = new AutonInput(RobotState.kUnknown, 0, 0, false, ClawState.kUnknown, Double.NaN);
        
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
                        newInput.m_desiredState = RobotState.kMidPiece;
                        System.out.println("step: " + m_step);
                        break;
                    }
                    case 3:
                    {
                        newInput.m_clawState = ClawState.kOpen;
                        System.out.println("step: " + m_step);
                        break;
                    }
                    case 4:
                    {
                        newInput.m_driveTarget = -RobotMap.AutonConstants.SHORT_COMMUNITY_DIST;
                        newInput.m_desiredState = RobotState.kTravel;
                        System.out.println("step: " + m_step);
                        break;
                    }
                    case 5:
                    {
                        newInput.m_driveTarget = -RobotMap.AutonConstants.SHORT_COMMUNITY_DIST;
                        System.out.println("step: " + m_step);
                        break;
                    }
                    case 6:
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
                        newInput.m_desiredState = RobotState.kMidPiece;
                        System.out.println("step: " + m_step);
                        break;
                    }
                    case 3:
                    {
                        newInput.m_clawState = ClawState.kOpen;
                        System.out.println("step: " + m_step);
                        break;
                    }
                    case 4:
                    {
                        newInput.m_driveTarget = -RobotMap.AutonConstants.LONG_COMMUNITY_DIST;
                        newInput.m_desiredState = RobotState.kTravel;
                        System.out.println("step: " + m_step);
                        break;
                    }
                    case 5:
                    {
                        newInput.m_driveTarget = -RobotMap.AutonConstants.LONG_COMMUNITY_DIST;
                        System.out.println("step: " + m_step);
                        break;
                    }
                    case 6:
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
                        newInput.m_delay = 4.0;
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
                        newInput.m_desiredState = RobotState.kHighCone;
                        System.out.println("step: " + m_step);
                        break;
                    }
                    case 3:
                    {
                        newInput.m_clawState = ClawState.kOpen;
                        System.out.println("step: " + m_step);
                        break;
                    }
                    case 4:
                    {
                        newInput.m_driveTarget = -RobotMap.AutonConstants.LONG_COMMUNITY_DIST;
                        newInput.m_desiredState = RobotState.kTravel;
                        System.out.println("step: " + m_step);
                        break;
                    }
                    case 5:
                    {
                        newInput.m_driveTarget = -RobotMap.AutonConstants.LONG_COMMUNITY_DIST;
                        System.out.println("step: " + m_step);
                        break;
                    }
                    case 6:
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
        }
        /**
        //auton path for 0 objects
        if (m_path == "0 Object"){
            if (m_step == 0) {
                m_step += 1;
                System.out.println("Internal auton configuration error detected: non-fatal error.");
                System.out.println("AUTON START UP CONTINUING BUT NOTED");
            }
            //FOR THE STEPS, WE WANT TO: 
            //1. move forward out of the community, 
            //2. move backward enough to start 
            //3. auto level function
            else if (m_step == 1) {
                System.out.println("now on Step 1");
                //number of tics is now good for rough testing; TODO: adjust encoder ticks to reflect 140 inches out [on real bot and on carpet]
                if (drivePos.m_leftLeaderPos >= 33750 && drivePos.m_rightLeaderPos >= 33750) {
                    //speed and turn are already set to 0 in driveInput
                    m_step += 1;
                }
                else {
                    driveInput.m_speed = 0.5;
                    driveInput.m_turnSpeed = 0;
                }
            }
            else if (m_step == 2) {
                System.out.println("now on Step 2");
                if (!isBotLevelBack){
                   //speed and turn are already set to 0 in driveInput
                   m_step += 1;
                }
                else {
                    //slightly slower for backing up
                    driveInput.m_speed = -0.45;
                    driveInput.m_turnSpeed = 0;
                }
            }
            else if (m_step == 3) {
                System.out.println("now on Step 3");
                //run autoLevel
                toRunAutoLevelOrNotToRun = true;
                if (isBotLevelBack) {
                    //autolevel for 10 more seconds
                    if (periodicTicCounter >= 500) {
                        toRunAutoLevelOrNotToRun = false;
                        m_step += 1;
                    }
                }
                periodicTicCounter++;
            }
            else if (m_step == 4){
                //end Auton, instantiate pathing step variables, alert use
                m_path = "";
                m_step = 0;
                m_autonStartOut = false;
                System.out.println("AUTON NOW ENDED");
            }
        }
        else if (m_path == "1 Object") {
            //TODO: create pathing for 1 object auton
            if (m_step == 0) {
                m_step += 1;
                System.out.println("Internal auton configuration error detected: non-fatal error.");
                System.out.println("AUTON START UP CONTINUING BUT NOTED");
            }
            else if (m_step == 1) {

            }


            System.out.println("1 Object Auton not yet created. Output messsage.");
            //end Auton, instantiate pathing step variables, alert use
            m_path = "";
            m_step = 0;
            m_autonStartOut = false;
            System.out.println("AUTON NOW ENDED");
        }
        else if (m_path == "2 Object") {
            //TODO: create pathing for 2 object auton
            System.out.println("2 Object Auton not yet created. Output messsage.");
            //end Auton, instantiate pathing step variables, alert use
            m_path = "";
            m_step = 0;
            m_autonStartOut = false;
            System.out.println("AUTON NOW ENDED");
        }

        //return statement for DriveInput object return type for making bot move
        return driveInput;
        */
        return newInput;
    }
}
