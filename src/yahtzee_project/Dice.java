package yahtzee_project;

public class Dice {

	public int value = 1;
	public boolean held = false;
	
	public void roll() {
		if(!this.held) {
			this.value = (int)(Math.random() * 6.0) + 1;
		}
	}
}
