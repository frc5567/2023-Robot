package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

/**
 * Class that encapsulates shoulder state.
 */
public class Shoulder {
    /**
     * Enum used to store the shoulder state (0 for down, 1 for up).
     */
    public enum ShoulderState {
        kDown(0),
        kUp(1),
        //no input from copilot
        kUnknown(-1);

        private int m_shoulderValue;

        /**
         * Sets the member variable to the shoulder value.
         * @param shoulderValue
         */
        ShoulderState(int shoulderValue) {
            m_shoulderValue = shoulderValue;
        }
        public int getShoulderState() {
            return m_shoulderValue;
        }
    }

    // Declares the double solenoid.
    private DoubleSolenoid m_shoulderSol;

    private ShoulderState m_currentShoulderState;

    /**
     * Constructor for the shoulder class. Instantiates one double solenoid.
     */
    public Shoulder() {
        m_shoulderSol = new DoubleSolenoid(RobotMap.PCM_CAN_ID, PneumaticsModuleType.CTREPCM, RobotMap.ShoulderConstants.DOUBLESOLENOID_UP_PORT, RobotMap.ShoulderConstants.DOUBLESOLENOID_DOWN_PORT);
        m_currentShoulderState = ShoulderState.kUnknown;
    }

    /**
     * Initialization method for shoulder which sets the state to up.
     */
    public void init() {
        this.setShoulderState(ShoulderState.kUp);
    }
    
    /**
     * Sets the solenoid to open or closed.
     * @param shoulderState
     */
    public void setShoulderState(ShoulderState shoulderState) {

        if (shoulderState == ShoulderState.kDown) {
            m_currentShoulderState = ShoulderState.kDown;
            m_shoulderSol.set(Value.kReverse);
            System.out.println("valid Shoulder State: down");
        }
        else if (shoulderState == ShoulderState.kUp){
            m_currentShoulderState = ShoulderState.kUp;
            m_shoulderSol.set(Value.kForward);
            System.out.println("valid Shoulder State: up");
        }
        else {
            //System.out.println("Invalid Shoulder State");
        }

    }

    public void toggleShoulderState() {
        if (m_currentShoulderState == ShoulderState.kUp) {
            this.setShoulderState(ShoulderState.kDown);
        }
        else {
            this.setShoulderState(ShoulderState.kUp);
        }
    }

    public ShoulderState getShoulderState() {
        return m_currentShoulderState;
    }

}
