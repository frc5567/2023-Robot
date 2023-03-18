package frc.robot;


/**
 * This encapsulates the inputs for Copilot GamePad
 */
public class CoDriveInput {
    public ToggleInput m_clawPos;
    public ToggleInput m_shoulderPos;
    public double m_manualElevator;
    public RobotState m_desiredState;
    public double m_manualArm;

    /**
     * Enum for setting intial structure for ToggleStates
     */
    public enum ToggleInput {
        kNoInput(0),
        kToggle(-1);

        private int m_toggleInput;

        /**
         * sets the member variable to the claw value
         * @param value
         */
        ToggleInput(int value) {
            m_toggleInput = value;
        }
        public int getToggleState() {
            return m_toggleInput;
        }
    }

    /**
     * Default constructor which instantiates input values
     */
    public CoDriveInput() {
        m_manualElevator = 0;
        m_manualArm = 0;
        m_clawPos = ToggleInput.kNoInput;
        m_shoulderPos = ToggleInput.kNoInput;
        m_desiredState = RobotState.kUnknown;
       
    }  
}

