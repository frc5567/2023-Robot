# 2023-Robot

### Code standard naming convention notes:
* Member variables should be pre-pended with m_ and utilize camelCasing
* Static non-constant variables should use lower_snake_case_formatting
* Constants should use C-style ALL_CAPS_FORMAT
* Class names should always start with a capital letter
* Method names should always start with a lower-case letter
* Variable names should always start with a lower-case letter

### CONTROLS:

##### Pilot (xbox)

|System          | Control       |
|----------------|---------------|
| Drive Forward  | R. Trigger    |
| Drive Reverse  | L. Trigger    |
| Turn Control   | Left Stick X  |
| Auto Leveling  | Back Button   |
| Low Gear       | R. Bumper     |
| High Gear      | L. Bumper     |
| Manual Arm     | Right Stick Y |
| Start Pos. Arm | Start Button  |
| Floor Pos. Arm | A Button      |
| Mid Pos. Arm   | B Button      |
| High Pos. Arm  | X Button      |
| Approach Arm   | Y Button      |

##### Copilot (gamepad)

|System          | ID            |
|----------------|---------------|
| Travel         | 1             |
| Floor Pickup   | 2             |
| Shelf Pickup   | 3             |
| Claw Toggle    | 4             |
| Mid Approach   | 5             |
| Mid Place      | 6             |
| Low Place      | 7             |
| High Approach  | 8             |
| Cone Place Hgh | 9             |
| Cube Place Hgh | 10            |
| Shoulder Togg. | 11            |
| Elevator Up    | 12            |
| Elevator Down  | AL (no port)  |
| Arm Up         | AR (no port)  |
| Arm Down       | AD (no port)  |