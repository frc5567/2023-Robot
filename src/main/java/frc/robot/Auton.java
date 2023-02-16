package frc.robot;

public class Auton {

    //member variables to hold controls and robot parts passed in
    private RobotShuffleboard m_robotShuffleboard;
    //integer and String, respectively, to store what step (by number, 1-? steps) and what path we are on (comm out, 0, 1, or 2 object)
    private int m_step;
    private String m_path = "";
    //default sets AutonPath to 0 object, creates member variable for currentPath (auton)
    private double m_currentAutonPath = RobotMap.RobotShuffleboardConstants.DEFAULT_AUTON_PATH;
    //boolean for showing whether auton is started and is set to false otherwise and after
    boolean m_autonStartOut = false;
    //boolean for running out of class method, set to false by default
    boolean run = false;
    //boolean for autoLeveling function implementation
    boolean toRunAutoLevelOrNotToRun = false;
    //for use in counting cycles of periodic for wait time
    private int periodicTicCounter = 0;
    //sysout counter for console clarity
    private int sysOutCounter = 0;


    /**
     * Constructor method of the Auton class with passed in robot classes
     * @param shuffleboard passes in the shuffleboard communcations and allows updating of that console during Auton
     */
    public Auton(RobotShuffleboard shuffleboard) {
        //configure member variables to starting instances of robot systems
        m_robotShuffleboard = shuffleboard;
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
        selectPath();
        //TODO: set path from shuffleboard choice here
    }

    /**
     * Method that checks state of auton
     * @return whether auton is running
     */
    public boolean isRunning() {
        if (m_path != "" || m_autonStartOut){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Method for actually selecting Auton path from shuffleboard and set console to reflect that
     */
    private void selectPath() {
        //TODO: add code here for grabbing chosen path from Shuffleboard and setting m_currentAutonPath to it
        //path integer assigned is based on number of objects auton has set to achieve
        if (m_currentAutonPath == 0){
            System.out.println("Setting Auton to Charging Station Path");
            m_path = "zero";
        }
        else if (m_currentAutonPath == 1){
            System.out.println("Setting Auton to 1 Object Path");
            m_path = "one";
        }
        else{
            m_currentAutonPath = 0;
            System.out.println("Setting Auton to Charging Station Path");
            m_path = "zero";
        }
    }

    /**
     * Method that contains entirety of Auton code and what to do in steps, also updates steps to shuffleboard and bot
     * @param drivePos is a DriveEncoderPos object that is passed in to periodically grab the current position
     * @param isBotLevelBack is a boolean input for passed in from Robot to see if the bot is level; mostly for charging station
     * @return DriveInput object that can control drivetrain outside of drivetrain class via position encoders
     */
    //TODO: sysout step changes should eventually become updates to some shuffleboard widget instead of console (messy, current)
    public DriveInput periodic(DriveEncoderPos drivePos, boolean isBotLevelBack) {
        DriveInput driveInput = new DriveInput(0, 0, Gear.kLowGear);
        if (m_autonStartOut){
            if (sysOutCounter == m_step) {
                System.out.println("AUTON STARTED");
                m_step += 1;
                sysOutCounter = m_step;
            }
            else {
                sysOutCounter += 1;
            }
        }
        else {
            m_path = "";
            m_step = 0;
        }

        //auton path for 0 objects
        if (m_path == "zero"){
            if (m_step == 0) {
                m_step += 1;
                sysOutCounter = m_step;
                System.out.println("Internal auton configuration error detected: non-fatal error. AUTON START UP CONTINUING BUT NOTED");
            }
            //FOR THE STEPS, WE WANT TO: 
            //1. move forward out of the community, 
            //2. move backward enough to start 
            //3. auto level function
            else if (m_step == 1) {
                if(sysOutCounter == m_step) {
                    System.out.println("now on Step 1");
                }
                sysOutCounter += 1;
                //TODO: adjust encoder ticks to reflect 140 inches out, currently set for 2 wheel revolutions
                if (drivePos.m_leftLeaderPos >= 8192 && drivePos.m_rightLeaderPos >= 8192) {
                    //speed and turn are already set to 0 in driveInput
                    m_step += 1;
                    sysOutCounter = m_step;
                }
                else {
                    driveInput.m_speed = 0.4;
                    driveInput.m_turnSpeed = 0;
                }
            }
            else if (m_step == 2) {
                if (sysOutCounter == m_step) {
                    System.out.println("now on Step 2");
                }
                sysOutCounter += 1;
                if (!isBotLevelBack){
                   //speed and turn are already set to 0 in driveInput
                   m_step += 1;
                   sysOutCounter = m_step;
                }
                else {
                    //slightly slower for backing up
                    driveInput.m_speed = -0.3;
                    driveInput.m_turnSpeed = 0;
                }
            }
            else if (m_step == 3) {
                if (sysOutCounter == m_step) {
                    System.out.println("now on Step 3");
                }
                sysOutCounter += 1;
                //run autoLevel
                toRunAutoLevelOrNotToRun = true;
                if (isBotLevelBack) {
                    //autolevel for 10 more seconds
                    if (periodicTicCounter >= 500) {
                        toRunAutoLevelOrNotToRun = false;
                        m_step += 1;
                        sysOutCounter = m_step;
                    }
                }
                periodicTicCounter++;
            }
            else if (m_step == 4){
                //end Auton, instantiate some variables, alert user
                if (sysOutCounter == m_step) {
                    System.out.println("AUTON NOW ENDED");
                    m_autonStartOut = false;
                }
                sysOutCounter += 1;
            }
        }
        
        //TODO: create pathing for 1 object auton

        //return statment for DriveInput object return type for making bot move
        return driveInput;
    }
}
