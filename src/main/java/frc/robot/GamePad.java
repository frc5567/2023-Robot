package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

public class GamePad extends Joystick {


    /**
     * Constructor, used for calling the super constructor (constructor used in the parent class GenericHID).
     * @param port
     */
    public GamePad(final int port) {
        super(port);
    }


    /**
     * Initialization method for the GamePad class
     */
    public void init() {
    }
    
    /**
     * Enum that defines buttons on the GamePad and what they do when active
     */
    public enum GamePadControls {

        Travel(1),
        Floor_Pickup(2),
        Shelf_Pickup(3),
        High_Cone(4),
        High_Cube(5),
        Mid_Place(6),
        Low_Place(7),
        High_Approach(8),
        Mid_Approach(9),
        Toggle_Claw(10),
        Toggle_Shoulder(11),
        Manual_Elevator_Up(12);
        //Manual_Elevator_Down(13),
       // Manual_Arm_Up(14),
       // Manual_Arm_Down(15);

        public final int portNum;

        /**
         * 
         * @param newPortNum
         */
        GamePadControls(int newPortNum) {
            this.portNum = newPortNum;
        }
    }

    /**
     * Method to check if Travel button is pressed. 
     * @return whether the Travel button is pressed or not
     */
    public boolean getTravelPressed() {
        return super.getRawButton(GamePadControls.Travel.portNum);
    }
    
    /**
     * Method to check if FloorPickup was pressed. 
     * @return whether the Floor Pickup button is pressed or not 
     */
    public boolean getFloorPickupPressed() {
        return super.getRawButton(GamePadControls.Floor_Pickup.portNum);
    }

    /**
     * Method to get if ShelfPickup was pressed
     * @return whether the Shelf Pickup button is pressed or not 
     */
    public boolean getShelfPickupPressed() {
        return super.getRawButton(GamePadControls.Shelf_Pickup.portNum);
    }

    /**
     * Method to check if HighCone was pressed. 
     * @return whether the High Cone Place button is pressed or not 
     */
    public boolean getHighConePressed() {
        return super.getRawButton(GamePadControls.High_Cone.portNum);
    }

    /**
     * Method to check if HighCube was pressed. 
     * @return whether the High Cube Place button is pressed or not 
     */
    public boolean getHighCubePressed() {
        return super.getRawButton(GamePadControls.High_Cube.portNum);
    }

    /**
     * Method to check if MidPlace has been pressed.
     * @return whether the Mid Gamepiece Place button is pressed or not 
     */
    public boolean getMidPlacePressed() {
        return super.getRawButton(GamePadControls.Mid_Place.portNum);
    }

    /**
     * Method to check if LowPlace was pressed. 
     * @return whether the Low Gamepiece Place button is pressed or not 
     */
    public boolean getLowPlacePressed() {
        return super.getRawButton(GamePadControls.Low_Place.portNum);
    }

    /**
     * Method to check if High approach is pressed.
     * @return whether the High Approach button was pressed 
     */
    public boolean getHighApproachPressed() {
        return super.getRawButton(GamePadControls.High_Approach.portNum);
    }

    /**
     * Method to check if MidApproach was pressed. 
     * @return whether the Mid Approach button was pressed 
     */
    public boolean getMidApproachPressed() {
        return super.getRawButton(GamePadControls.Mid_Approach.portNum);
    }

    /**
     * Method to check if ToggleClawPressed was pressed.
     * @return whether the Toggle Claw button was pressed.
     */
    public boolean getToggleClawPressed() {
        return super.getRawButton(GamePadControls.Toggle_Claw.portNum);
    }

    /**
     * Method to check if ToggleShoulderPressed was pressed.
     * @return whether the Toggle Shoulder button was pressed.
     */
    public boolean getToggleShoulderPressed() {
        return super.getRawButton(GamePadControls.Toggle_Shoulder.portNum);
    }

    /**
     * Method to check if ElevatorUpPressed was pressed
     * @return whether the Elevator Up button is pressed or not 
     */
    public boolean getElevatorUpPressed() {
        return super.getRawButton(GamePadControls.Manual_Elevator_Up.portNum);
    }

    /**
     * Method to check if ElevatorDownPressed was pressed
     * @return whether the Elevator Down button is pressed or not 
     */
    public boolean getElevatorDownPressed() {
        return (super.getX() < -0.5);
    }

    /**
     * Method to check if ArmUpPressed was pressed
     * @return whether the Arm Up button is pressed or not 
     */
    public boolean getArmUpPressed() {
        return (super.getX() > 0.5);
    }

    /**
     * Method to check if ArmDownPressed was pressed
     * @return whether the Arm Down button is pressed or not 
     */
    public boolean getArmDownPressed() {
        return (super.getY() > 0.5);
    }



}
