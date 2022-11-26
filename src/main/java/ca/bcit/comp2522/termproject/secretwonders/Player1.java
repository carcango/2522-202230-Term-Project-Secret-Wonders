package ca.bcit.comp2522.termproject.secretwonders;

import javafx.geometry.Point2D;
import javafx.scene.transform.Rotate;

/**
 * Player One Class.
 * @author Olafson and Mahannah.
 * @version 2022.
 * This is the Bee character that is controlled with the arrow keys and CTRL.
 */
public class Player1 extends Character {
    /**
     * how fast the character moves and turns.
     */
    final int movementFactor = Constants.PLAYER_ONE_MOVEMENT;
    /**
     * monitors if the flag for turning left is on.
     */
    protected boolean turnLeft = false;
    /**
     * monitors if the flag for turning right is on.
     */
    protected boolean turnRight = false;
    /**
     * monitors if the flag for going forward is on.
     */
    protected boolean goForward = false;
    /**
     * monitors if the flag for going backward is on.
     */
    protected boolean goBackward = false;
    /**
     * creates rotate object for player.
     */
    final Rotate rotatePlayer = new Rotate();
    /**
     * Creates initial Direction of directly up.
     */
    Point2D initialDirection = new Point2D(0, -1);

    /**
     * Constructs player one.
     */

    public Player1() {
        super("bee.gif", Constants.PLAYER_ONE_WIDTH,
                Constants.PLAYER_ONE_HEIGHT, Constants.PLAYER_ONE_HEALTH);
        setInitialPosition();
    }

    /**
     * Sets initial spawn location and sets the rotate object to the player to allow rotation Transformations.
     */
    private void setInitialPosition() {
        setX((Constants.SCREEN_WIDTH - Constants.PLAYER_ONE_WIDTH));
        setY(Constants.SCREEN_HEIGHT - Constants.PLAYER_ONE_HEIGHT);
        rotatePlayer.setPivotX(getCenterX());
        rotatePlayer.setPivotY(getCenterY());
        getTransforms().addAll(rotatePlayer);
    }

    /**
     * sets the movement booleans to true.
     * @param dir Up, down, left or right.
     */
    public void startMovement(GameEngine.Direction dir) {
        switch (dir) {
            case UP:
                goForward = true; break;
            case DOWN:
                goBackward = true; break;
            case LEFT:
                turnLeft = true; break;
            case RIGHT:
                turnRight = true; break;
        }
    }

    /**
     * Interprets instructions to move Player one.
     * Sets the pivot to centre of player, increments or rotates the movementChange based on what booleans are true,
     * and transforms the player in that direction.
     */
    @Override
    public void doMovement() {
        rotatePlayer.setPivotX(getCenterX());
        rotatePlayer.setPivotY(getCenterY());

        double movementChangePlayerOne = 0;

        if (goForward) {
            movementChangePlayerOne += movementFactor;
        }
        if (goBackward) {
            movementChangePlayerOne -= movementFactor;
        }
        if (turnRight) {
            rotatePlayer1(movementFactor);
        }
        if (turnLeft) {
            rotatePlayer1(-movementFactor);
        }

        Point2D pt1 = rotatePlayer.deltaTransform(initialDirection.multiply(movementChangePlayerOne));
        movePlayer(pt1.getX(), pt1.getY());
    }

    /**
     * stops player movements when key press has been released.
     * @param dir UP, DOWN, LEFT, or RIGHT.
     */
    public void stopMovement(GameEngine.Direction dir) {
        switch (dir) {
            case UP -> goForward = false;
            case DOWN -> goBackward = false;
            case LEFT -> turnLeft = false;
            case RIGHT -> turnRight = false;
        }
    }

    /**
     * Moves the player one step forward or backward.
     * @param changeInX how much the player is supposed to move along relative X coordinate (if turning).
     * @param changeInY how much the player is supposed to move along relative Y coordinate (forward or backwards).
     */
    private void movePlayer(final double changeInX, final double changeInY) {
        if (changeInX == 0 && changeInY == 0) {
            return;
        }
        double x = changeInX + getX();
        double y = changeInY + getY();
        setX(x);
        setY(y);
    }

    /**
     * rotates player one.
     * @param angle how much to rotate player one.
     * if angle is 360, set back to zero. (full rotation)
     */
    public void rotatePlayer1(float angle) {
        angle += rotatePlayer.getAngle();
        if (angle == Constants.PLAYER_ROTATION) {
            angle = 0;
        }
        rotatePlayer.setAngle(angle);
    }

    /**
     * creates Player One Projectile at characters location.
     */
    public void fireProjectile() {
        ((GamePane)getParent()).getEngine().queueAddition(
                new Player1Projectile(
                        getCenterX(),
                        getCenterY(),
                        rotatePlayer.getAngle()
                )
        );
    }
}
