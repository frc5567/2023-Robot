package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;


/**
 *  Class that encapsulates shoulder state.
 */
public class Shoulder {
    
    /**
     * Enum used to store the shoulder state (0 for down, 1 for up).
     */
    public enum ShoulderState {
        kDown(0),
        kUp(1);

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
    DoubleSolenoid m_shoulderSol;

    /**
     * Constructor for the shoulder class
     * One double solenoid
     */
    public Shoulder() {
        m_shoulderSol = new DoubleSolenoid(RobotMap.PCM_CAN_ID, PneumaticsModuleType.CTREPCM, RobotMap.ShoulderConstants.DOUBLESOLENOID_UP_PORT, RobotMap.ShoulderConstants.DOUBLESOLENOID_DOWN_PORT);
    }
    
    /**
     * Sets the solenoid to open or closed.
     * @param shoulderState
     */
    public void setShoulderState(ShoulderState shoulderState) {

        if (shoulderState == ShoulderState.kDown) {
            //TODO: kForward and kReverse might be reversed; test.
            m_shoulderSol.set(Value.kForward);
        }
        else if (shoulderState == ShoulderState.kUp) {
            //TODO: kForward and kReverse might be reversed; test.
            m_shoulderSol.set(Value.kReverse);
        }
        else {
            System.out.println("Shoulder Error");
        }

    }

}
