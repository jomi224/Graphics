package minesweeper;
// Class maintaining entire field with mine objects inside, contains methods to evaluate field as whol

public class Field {
	public Mine[][] field; //the field
	private int mines; //number of mines
	private int fakeMines; //potential mines
	private int rows;
	private int columns;
	private boolean done=false; //game won?
	
	public Field(int rowCount,int columnCount, int mineCount){ //Constructor
		field= new Mine[rowCount][columnCount];
		mines=mineCount;
		fakeMines=mineCount;
		
		rows=rowCount;
		columns=columnCount;
		for (int i=0;i<rows;i++){
			for (int j=0;j<columns;j++){
				field[i][j]=new Mine(this,i,j);
			}
		}
		
	}
	//When user clicks first, set up the minefield around the first click
	public void FirstClick(int x,int y){
		setMines(x,y);
		countNeighbors();
	}
	
	//Reset every minefield to blank slate
	public void resetButtons(){
		for (int i=0;i<rows;i++){
			for (int j=0;j<columns;j++){
				field[i][j].resetMine();
			}
		}
		fakeMines=mines;
		done=false;
	}
	
	//Populate the minefield
	public void setMines(int x, int y){
		int mineCount=0;
		while (mineCount<mines){
			int r=(int) (Math.random()*rows);
			int c=(int)(Math.random()*columns);
			if (field[r][c].getNeighbors()==0){
				if (Math.abs(x-r)>1 ||Math.abs(y-c)>1){
				field[r][c].setNeighbors();
				mineCount+=1;}
			}
		}
	}
	
	//Change state, if click, then left click, if not click, then right click
	public void changeState(int x, int y,boolean click){
		if (click){
			if (getNeighbors(x,y)==0){
				openMine(x,y);
			}
		}
		field[x][y].changeState(click);
		checkBoard(); // See if game is over
	}
	
	//Open all mines, happens if user clicks on mine
	public void EndGame(int x, int y){
		for (int i=0;i<rows;i++){
			for (int j=0;j<columns;j++){
				field[i][j].OpenMine();
			}
		}
		done=true;
	}
	
	public int getMines(){
		return fakeMines;
	}
	public void openNeighbors(int x, int y){
		field[x][y].openNeighbors();
		
	}
	public boolean GetRed(int x, int y){
		return field[x][y].GetRed();
	}
	public void sayChanged(int x, int y){
		field[x][y].sayChanged();
	}
	public boolean getChanged(int x, int y){
		return field[x][y].getChanged();
	}
	public void openMine(int x, int y){
		field[x][y].recursivelyOpen();
	}
	public int getState(int x, int y){return field[x][y].getState();}
	
	public int getNeighbors(int x, int y){
		return field[x][y].getNeighbors();
	}
	
	
	//Check to see if game has been finished
	public void checkBoard(){
		done=true;
		//If only mines remains unchecked or unopened, then game is over
		for (int i=0;i<rows;i++){
			for (int j=0;j<columns;j++){
				if (field[i][j].getState()!=4 && field[i][j].getNeighbors()!=-1)done=false;
			}
		}
		if (!done){
			done=true;
			//If all mines are checked, game is over
			for (int i=0;i<rows;i++){
				for (int j=0;j<columns;j++){
					if (field[i][j].getState()!=1 && field[i][j].getNeighbors()==-1)done=false;
				}
			}
		}
		if (done){//If game is done, flag all mines, and open up everything else
			for (int i=0;i<rows;i++){
				for (int j=0;j<columns;j++){
					if (field[i][j].getNeighbors()==-1)field[i][j].setState(1);
					else{
						field[i][j].setState(4);
					}
				}
			}
		}
	}
	public boolean getDone(){return done;}
	public void countNeighbors(){//calculate number of surrounding mines
		for (int i=0;i<rows;i++)for (int j=0;j<columns;j++)field[i][j].countNeighbors();
	}
	
	public void increaseMines(){ fakeMines++;} //When user flags or unflags a mine
	public void decreaseMines(){ fakeMines--;}
	public int getRows(){ return rows;}
	public int getColumns(){return columns;}
	
}
