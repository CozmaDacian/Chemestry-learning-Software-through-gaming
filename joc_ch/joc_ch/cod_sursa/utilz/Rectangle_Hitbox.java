package utilz;

public class Rectangle_Hitbox {
	
	    int leftX, bottomY, width, height;

	    public Rectangle_Hitbox(int leftX, int bottomY, int width, int height) {
	        this.leftX = leftX;
	        this.bottomY = bottomY;
	        this.width = width;
	        this.height = height;
	    }

	    public int getLeftX() {
	        return leftX;
	    }

	    public int getBottomY() {
	        return bottomY;
	    }

	    public int getWidth() {
	        return width;
	    }

	    public int getHeight() {
	        return height;
	    }

	    public boolean doCollide(Rectangle_Hitbox other) {
	        int thisRightX = this.leftX + this.width;
	        int thisTopY = this.bottomY + this.height;

	        int otherRightX = other.leftX + other.width;
	        int otherTopY = other.bottomY + other.height;

	        // Check for collision
	        boolean collisionX = this.leftX < otherRightX && thisRightX > other.leftX;
	        boolean collisionY = this.bottomY < otherTopY && thisTopY > other.bottomY;

	        return collisionX && collisionY;
	    }
	}


