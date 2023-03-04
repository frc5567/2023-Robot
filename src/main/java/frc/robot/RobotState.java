package frc.robot;

/**
 * Enum to encapsulate combinations of positions for multiple mechanisms on the Robot
 */
public enum RobotState {
    /**
     * Default state for travelling
     */
    kTravel ("Travel", RobotMap.ArmConstants.ARM_START_POS, RobotMap.ElevatorConstants.ELEVATOR_MID_POS),
    
    /**
     * State for doing floor pickup of gamepiece
     */
    kFloorPickup ("Floor Pickup", RobotMap.ArmConstants.ARM_FLOOR_POS, RobotMap.ElevatorConstants.ELEVATOR_FLOOR_POS),

    /**
     * State for placing gamepiece at Mid level
     */
    kMidPiece ("Mid Game Piece", RobotMap.ArmConstants.ARM_MID_POS, RobotMap.ElevatorConstants.ELEVATOR_MID_POS),

    /**
     * State for placing cone at High level
     */
    kHighCone ("High Cone", RobotMap.ArmConstants.ARM_HIGH_POS, RobotMap.ElevatorConstants.ELEVATOR_HIGH_POS),

    /**
     * State for approaching cone at High level
     */
    kApproachHighCone ("Approach High Cone", RobotMap.ArmConstants.ARM_APPROACH_POS, RobotMap.ElevatorConstants.ELEVATOR_HIGH_POS),

    /**
     * Unknown State -- should generally not be used for actual positioning.
     */
    kUnknown ("Unknown", 0.0, 0.0);

    private String CurrentState;
    private Double armTicks;
    private Double elevatorTicks;

    /**
    * This is the constructor for the enum RobotState, setting the intial state. 
    */
    RobotState (String State, Double armTicks, Double elevatorTicks){
        this.CurrentState = State;
        this.armTicks = armTicks;
        this.elevatorTicks = elevatorTicks;
    }

    /**
     * Returns CurrentState as a string.
     * @return CurrentState
     */
    public String toString(){
        return this.CurrentState;
    }

    /**
     * Returns the Elevator position in Ticks
     * @return elevatorTicks
     */
    public Double getElevatorTarget() {
        return this.elevatorTicks;
    }

    /**
     * Returns the Arm position in Ticks
     * @return armTicks
     */
    public Double getArmTarget() {
        return this.armTicks;
    }
}
