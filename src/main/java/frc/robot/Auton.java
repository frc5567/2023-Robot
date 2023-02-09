package frc.robot;

public class Auton {

    //member variables to hold controls and robot parts passed in
    private RobotShuffleboard m_robotShuffleboard;
    //integer and String, respectively, to store what step (by number, 1-? steps) and what path we are on (0 or 1 object)
    private int m_step;
    private String m_path = "";
    //initally sets AutonPath to 0 object, creates member variable for currentPath (auton)
    private double m_currentAutonPath = RobotMap.RobotShuffleboardConstants.DEFAULT_AUTON_PATH;
    //Boolean for showing whether auton is started and is set to false otherwise and after
    boolean m_autonStartOut = true;


    /**
     * Constructor method of the Auton class with passed in robot classes
     * @param shuffleboard passes in the shuffleboard communcations and allows updating of that console during Auton
     */
    public Auton(RobotShuffleboard shuffleboard) {
        //configure member variables to starting instances of robot systems
        m_robotShuffleboard = shuffleboard;
        //sets auton initial step to step 1
        m_step = 1;
    }

    /**
     * Method that runs initally at start of every Auton mode, sets Auton path.
     * Takes no parameters and returns nothing.
     */
    public void init() {
        //initializes elements of robot for the Auton specifically
        //m_currentAutonPath = m_robotShuffleboard.getAutonPath();
        m_step = 1;
        selectPath();
    }

    /**
     * Method that checks state of auton
     * @return whether auton is running
     */
    public boolean isRunning() {
        if (m_path != ""){
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
     * Method that contains entirety of Auton code and what to do in steps, updates steps to shuffleboard and bot
     * Returns nothing, takes no parameters.
     */
    public DriveInput periodic(DriveEncoderPos drivePos) {
        DriveInput driveInput = new DriveInput(0, 0, Gear.kLowGear);
        if (m_autonStartOut){
            System.out.println("AUTON STARTED");
            m_autonStartOut = false;
        }

        //auton path for 0 objects
        if (m_path == "zero"){
            if (m_step == 0) {
                m_step += 1;
                System.out.println("Internal auton configuration error detected: non-fatal error. AUTON START UP CONTINUING");
            }
            //FOR THE STEPS, WE WANT TO: 
            //1. move forward out of the community, 
            //2. move backward enough to start 
            //3. auto level function
            else if (m_step == 1) {
                if (drivePos.m_leftLeaderPos >= 4096 && drivePos.m_rightLeaderPos >= 4096) {
                    //speed and turn are already set to 0 in driveInput
                    m_step += 1;
                }
                driveInput.m_speed = 0.4;
                driveInput.m_turnSpeed = 0;
            }
        }
        
        //TODO: create pathing for 1 object auton

        //return statment for DriveInput object return type for making bot move
        return driveInput;
    }
}
