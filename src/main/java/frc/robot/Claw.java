package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

/**
 * Claw class that encapsulates the claw position.
 */
public class Claw {
    /**
     * Enum used to store the claw state (0 for open, 1 for closed).
     */
    public enum ClawState {
        kOpen(0),
        kClosed(1);

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
    
    // Delcares the double solenoid.
    DoubleSolenoid m_clawSol;

    /**
     * Sets the solenoid to open or close.
     * @param clawValue the enum is passed to the solenoid value.
     */
    public void setClawState(ClawState clawValue) {
        if (clawValue == ClawState.kOpen) {
            //TODO: kForward and kReverse might be reversed; test.
            m_clawSol.set(Value.kForward);
        }
        else if (clawValue == ClawState.kClosed) {
            m_clawSol.set(Value.kReverse);
        }
        else {
            System.out.println("Claw Error");
        }
    }

}