package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

/**
 * Claw class that encapsulates the claw position.
 */
public class Claw {
    /**
     * Enum used to store the claw state (0 for open, 1 for closed).
     */
    public enum ClawState {
        kOpen(0),
        kClosed(1),
        //no input from copilot
        kUnknown(-1);

        private int m_clawValue;

        /**
         * Sets the member variable to the claw value.
         * @param clawValue
         */
        ClawState(int clawValue) {
            m_clawValue = clawValue;
        }
        public int getClawState() {
            return m_clawValue;
        }
    }
    
    //Delcares the double solenoid.
    private DoubleSolenoid m_clawSol;

    private ClawState m_currentClawState;

    /**
     * Constructor for claw class. 
     * One double solenoid
     */
    public Claw() {
        m_clawSol = new DoubleSolenoid(RobotMap.PCM_CAN_ID, PneumaticsModuleType.CTREPCM, RobotMap.ClawConstants.DOUBLESOLENOID_OPEN_PORT, RobotMap.ClawConstants.DOUBLESOLENOID_CLOSE_PORT);
        m_currentClawState = ClawState.kUnknown;
    }

    /**
     * Initialization method which sets the state to closed
     */
    public void init() {
        this.setClawState(ClawState.kClosed);
    }

    /**
     * Sets the solenoid to open or close.
     * @param clawValue the enum is passed to the solenoid value.
     */
    public void setClawState(ClawState clawValue) {
        if (clawValue == ClawState.kOpen) {
            m_currentClawState = ClawState.kOpen;
            //TODO: kForward and kReverse might be reversed; test.
            m_clawSol.set(Value.kForward);
            System.out.println("Valid Claw State: open");
        }
        else if (clawValue == ClawState.kClosed) {
            m_currentClawState = ClawState.kClosed;
            m_clawSol.set(Value.kReverse);
            System.out.println("Valid Claw State: closed");
        }
        else {
            //Somebody passed in kUnknown
            //System.out.println("Invalid Claw State");
        }
    }

    public void toggleClawState() {
        if (m_currentClawState == ClawState.kOpen) {
            this.setClawState(ClawState.kClosed);
        }
        else {
            this.setClawState(ClawState.kOpen);
        }
    }

    public ClawState getClawState() {
        return m_currentClawState;
    }

}
