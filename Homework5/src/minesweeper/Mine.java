package minesweeper;
//Class for each individual mine
//State=4 means it is open, 0 means unclicked, 1 is flag, 2 is question mark

public class Mine{
	private int state; 
	int neighbors=0; //0 means it is undefined
	int posX;
	int posY;
	int rows,columns;
	Field myField;
	boolean redMine=false;// First mine clicked
	boolean changed=true;
	
	public Mine(Field field,int x, int y){
		myField=field;
		state=0;
		posX=x;
		posY=y;
		rows=myField.getRows();
		columns=myField.getColumns();
	}
	//Open up all neighbors, used for right/left click 
	public void openNeighbors(){

		if (state==4){

			//First make sure number of flagged mines== count
			int count=0;
			if (posX>0){
				if (myField.getState(posX-1, posY)==1)count++;
				if (posY>0) if (myField.getState(posX-1, posY-1)==1)count++;
				if (posY<columns-1) if (myField.getState(posX-1, posY+1)==1)count++;
			}
			if (posX<rows-1){
				if (myField.getState(posX+1, posY)==1)count++;
				if (posY>0) if (myField.getState(posX+1, posY-1)==1)count++;
				if (posY<columns-1) if (myField.getState(posX+1, posY+1)==1)count++;
			}
			if (posY>0)if (myField.getState(posX, posY-1)==1)count++;
			if (posY<columns-1)if (myField.getState(posX, posY+1)==1)count++;

			//If count matches, then open up all non-mines, end game if incorrectly marked
			if (neighbors==count){
				if (posX>0){
					if (myField.getNeighbors(posX-1, posY)!=-1||myField.getState(posX-1,posY)!=1)myField.changeState(posX-1, posY, true);
					if (posY>0) if (myField.getNeighbors(posX-1, posY-1)!=-1||myField.getState(posX-1,posY-1)!=1)myField.changeState(posX-1, posY-1, true);
					if (posY<columns-1) if (myField.getNeighbors(posX-1, posY+1)!=-1||myField.getState(posX-1,posY+1)!=1)myField.changeState(posX-1, posY+1, true);
				}
				if (posX<rows-1){
					if (myField.getNeighbors(posX+1, posY)!=-1||myField.getState(posX+1,posY)!=1)myField.changeState(posX+1, posY, true);
					if (posY>0) if (myField.getNeighbors(posX+1, posY-1)!=-1||myField.getState(posX+1,posY-1)!=1)myField.changeState(posX+1, posY-1, true);
					if (posY<columns-1) if (myField.getNeighbors(posX+1, posY+1)!=-1||myField.getState(posX+1,posY+1)!=1)myField.changeState(posX+1, posY+1, true);
				}
				if (posY>0)if (myField.getNeighbors(posX, posY-1)!=-1||myField.getState(posX,posY-1)!=1)myField.changeState(posX, posY-1, true);
				if (posY<columns-1)if (myField.getNeighbors(posX, posY+1)!=-1||myField.getState(posX,posY+1)!=1)myField.changeState(posX, posY+1, true);
			}
		}
	}
	//Reset mine to blank state
	public void resetMine(){
		state=0;
		neighbors=0;
		changed=true;
		redMine=false;
	}
	//Change state based on click type
	//State=4 means it is open, 0 means unclicked, 1 is flag, 2 is question mark
	public void changeState(boolean click) {
		if (!click){
			state=(state+1)%3;
			if (state==1){
				myField.decreaseMines();
			}else if (state==2){
				myField.increaseMines();
			}
		}
		else {
			if (neighbors==-1&&state!=4){
				myField.EndGame(posX,posY);
				redMine=true;
			}else{
				state=4;
			}
		}
		changed=true;
	}
	//Neighbors=-1 means it is a mine
	public void setNeighbors(){
		neighbors=-1;
	}
	public int getNeighbors(){
		return neighbors;
	}
	public void setState(int newState){state=newState;}
	public int getState(){return state;}
	
	//Count number of neighbors in surround area
	public void countNeighbors(){
		int count=0;
		if (neighbors!=-1){
		if (posX>0){
			if (myField.getNeighbors(posX-1, posY)==-1)count++;
			if (posY>0) if (myField.getNeighbors(posX-1, posY-1)==-1)count++;
			if (posY<columns-1) if (myField.getNeighbors(posX-1, posY+1)==-1)count++;
		}
		if (posX<rows-1){
			if (myField.getNeighbors(posX+1, posY)==-1)count++;
			if (posY>0) if (myField.getNeighbors(posX+1, posY-1)==-1)count++;
			if (posY<columns-1) if (myField.getNeighbors(posX+1, posY+1)==-1)count++;
		}
		if (posY>0)if (myField.getNeighbors(posX, posY-1)==-1)count++;
		if (posY<columns-1)if (myField.getNeighbors(posX, posY+1)==-1)count++;
		neighbors=count;
		}
	}
	//Set state to 4
	public void OpenMine(){
		if (neighbors==-1) {
			state=4;
			changed=true;
		}
	}
	public boolean GetRed(){
		return redMine;
	}
	public void sayChanged(){
		changed=false;
	}
	public boolean getChanged(){
		return changed;
	}
	
	//If user clicks on mine with zero mines touching, then open all mines in surrounding field recursively
	public void recursivelyOpen(){
		if (neighbors==0 && state!=4){
			state=4;

		if (posX>0){
			myField.openMine(posX-1, posY);
			if (posY>0)myField.openMine(posX-1, posY-1);
			if (posY<columns-1)myField.openMine(posX-1, posY+1);
		}
		if (posX<rows-1){

			myField.openMine(posX+1, posY);
			if (posY>0)myField.openMine(posX+1, posY-1);
			if (posY<columns-1)myField.openMine(posX+1, posY+1);
		}
		if (posY>0)myField.openMine(posX, posY-1);
		if (posY<columns-1)myField.openMine(posX, posY+1);
		}
		state=4;
		changed=true;
	}
	
}
