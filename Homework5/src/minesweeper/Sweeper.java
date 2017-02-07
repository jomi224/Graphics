package minesweeper;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class Sweeper extends JFrame implements MouseListener{
	
	private GridLayout experimentLayout;
	private JButton buttons[];
	private JPanel panel,panel2;
	
	private static int rows=16,columns=30;
	private ImageIcon imgBlock,imgBlank,imgFlag,imgQuestion,imgBomb,imgOne,imgTwo,imgThree,imgFour,imgFive, imgSix,imgRed,imgSmiley,imgClock; //imageIcons used for buttons
	private ImageIcon numbers[]; //more of them
	
	private JLabel time1,time2,time3,bomb1,bomb2,bomb3;
	private int gameTime=0;
	private int clickTime=0;
	private int widths[]={580, 320,180};//Widths for 3 game sizes
	private int heights[]={300,320,180}; //Heights
	private static int board=1;// Which board we are using
	private int blanks[]={200,90,25};//Blanks for setting differences between timer and minecounter
	private static int rowCount[]={16, 16,9};//size of field for each board
	private static int columnCount[]={30,16,9};
	private static int mineCount[]={99,40,10};
	private JLabel blank1,blank2;
	private int buttonCount;
	private int currentCount=0;
	private boolean leftClick=true;
	private MouseListener ml;
	private static Field myFields[]; //Holding all the potential boards from user
	private boolean paused=false;//is game paused waiting for user
	private boolean firstClick=true; //do we need first click from user
	
		//Create a new field with same size and update the graphics window
		public void resetBoard(){
			myFields[board].resetButtons();
			gameTime=0;
			setClock(gameTime);
			setBombCount(myFields[board].getMines());
			firstClick=true;
			updateButtons();
		}
		
		
		//Method to switch between different board sizes and difficulties
		public void changeBoard(int newBoard){
			if (newBoard!=board){
				board=newBoard;
			      setSize(new Dimension(widths[board],heights[board]+100));

			      buttonCount=rowCount[board]*columnCount[board];
			      experimentLayout = new GridLayout(rowCount[board],columnCount[board]);
			      experimentLayout.setHgap(0);
			      experimentLayout.setVgap(0);
			      
			      panel.setLayout(experimentLayout);
			      panel2.setPreferredSize(new Dimension(widths[board],40));
			      panel.setPreferredSize(new Dimension(widths[board],heights[board]));
			      blank1.setPreferredSize(new Dimension(blanks[board],25));
			      blank2.setPreferredSize(new Dimension(blanks[board],25));
			      //Reset all the buttons and change size of board
				      for (int i=0;i<buttons.length;i++){
				    	  try{//Remove buttons from panel
				    	  panel.remove(buttons[i]);
				    	  }catch(Exception e){
				    	  }
				    	  buttons[i]=null;//set button to null
				    	  if (i<buttonCount){ //add back all the new buttons with their event listeners attached
				    		  buttons[i]=new JButton();
					    	  
					    	  final int rowI=(int) i/columnCount[board];
					    	  final int columnI= i%columnCount[board];

					    	  ml=new MouseAdapter(){
					    		    public void mousePressed(MouseEvent e){

					    		    	if (e.getButton()==MouseEvent.BUTTON3){ //left click
					    		    		if (leftClick&& Math.abs(clickTime-currentCount)<=1){//double click
					    		    			myFields[board].openNeighbors(rowI, columnI);
					    		    		}
					    		    		if (myFields[board].getState(rowI, columnI)!=4&&!myFields[board].getDone()){
					    		    			myFields[board].changeState(rowI, columnI,false);
					    		    			setBombCount(myFields[board].getMines());
					    		    		}
					    		    		leftClick=false;
					    		    	} else if (e.getButton()==MouseEvent.BUTTON1){//right click

					    		    		if (!leftClick&& Math.abs(clickTime-currentCount)<=1){//double click
					    		    			myFields[board].openNeighbors(rowI, columnI);

					    		    		}
					    		    		if (firstClick){ //if first click, then treat differently
					    		    			myFields[board].FirstClick(rowI, columnI);
					    		    			firstClick=false;
					    		    			paused=false;
						    		    		myFields[board].changeState(rowI,columnI,true);

					    		    		}
					    		    		else if (!paused){
						    		    		myFields[board].openNeighbors(rowI, columnI);

					    		    			if (myFields[board].getNeighbors(rowI, columnI)==-1){
					    		    				paused=true;
					    		    			}
						    		    		myFields[board].changeState(rowI,columnI,true);

					    		    		}
					    		    		leftClick=true;
					    		    		
					    		    	}
					    		    	currentCount=clickTime;
					    		    	updateButtons();
					    		    }

					    		};
					    		buttons[i].addMouseListener(ml);
					    	  
				    		  panel.add(buttons[i]);
				    	  }

				    	  
				      }
				  resetBoard();//Reset board properties
			      
			}
		}
		
		//Set the clock to a specific time
		public void setClock(int time){
			
			time1.setIcon(numbers[0]);
			time2.setIcon(numbers[8]);
			time3.setIcon(numbers[9]);
			String timeString=Integer.toString(time); //convert time to string
			if (timeString.length()==1){
				timeString="00"+timeString;
			}else if (timeString.length()==2){
				timeString="0"+timeString;
			}
			//set the three labels to the proper image based on the string representign time
			time1.setIcon(numbers[Integer.parseInt(timeString.substring(0, 1))]);
			time2.setIcon(numbers[Integer.parseInt(timeString.substring(1, 2))]);
			time3.setIcon(numbers[Integer.parseInt(timeString.substring(2, 3))]);
			
		}
		
		//Set the bomb counter
		//Works the same way that the timer works
		public void setBombCount(int bombs){
			String bombString=Integer.toString(bombs);
			if (bombString.length()==1){
				bombString="00"+bombString;
			}else if (bombString.length()==2){
				bombString="0"+bombString;
			}
			
			bomb1.setIcon(numbers[Integer.parseInt(bombString.substring(0, 1))]);
			bomb2.setIcon(numbers[Integer.parseInt(bombString.substring(1, 2))]);
			bomb3.setIcon(numbers[Integer.parseInt(bombString.substring(2, 3))]);
		}
		
		//Add all the menu and drop down options
		public void createMenu(){
			 JMenuBar menubar = new JMenuBar();
		        ImageIcon icon = new ImageIcon("exit.png");

		        JMenu game = new JMenu("Game");
		        //JMenu options= new JMenu("Options");
		        //JMenu help= new JMenu("Help");
		        
		        //game.setMnemonic(KeyEvent.VK_F);
		        //options.setMnemonic(KeyEvent.VK_F);
		        
		        JMenuItem menuItem1 = new JMenuItem("New", icon);
		        
		        menuItem1.setMnemonic(KeyEvent.VK_E);
		        menuItem1.setToolTipText("Exit application");
		        menuItem1.addActionListener((ActionEvent event) -> {
		            resetBoard();
		        });
		        
		        //Switch to different difficulty levels
		        JMenuItem menuItem2= new JMenuItem("Beginner",icon);
		        menuItem2.setMnemonic(KeyEvent.VK_E);
		        menuItem2.addMouseListener(new MouseAdapter(){
		        	 public void mousePressed(MouseEvent e){
		    		    	changeBoard(2);
		    		    	firstClick=true;
		    		    }
		        });
		        JMenuItem menuItem3= new JMenuItem("Intermediate",icon);
		        menuItem3.setMnemonic(KeyEvent.VK_E);
		        menuItem3.addMouseListener(new MouseAdapter(){
		        	 public void mousePressed(MouseEvent e){
		    		    	changeBoard(1);
		    		    	firstClick=true;

		    		    }
		        });
		        JMenuItem menuItem4= new JMenuItem("Expert",icon);
		        menuItem4.setMnemonic(KeyEvent.VK_E);
		        menuItem4.addMouseListener(new MouseAdapter(){
		        	 public void mousePressed(MouseEvent e){
		    		    	changeBoard(0);
		    		    	firstClick=true;

		    		    }
		        });
		        //Exit the game
		        JMenuItem menuItem5= new JMenuItem("Exit",icon);
		        menuItem5.setMnemonic(KeyEvent.VK_E);
		        menuItem5.addActionListener((ActionEvent event) -> {
		            System.exit(0);
		        });
		        
		        
		        //Add items to menu
		        game.add(menuItem1);
		        game.add(menuItem2);
		        game.add(menuItem3);
		        game.add(menuItem4);
		        game.add(menuItem5);


		        menubar.add(game);
		        //menubar.add(options);
		        //menubar.add(help);
		        setJMenuBar(menubar);
		}

	   
		//Constructor method
	   public Sweeper()
	   {
	      super( "Jacob's Minesweeper" );
	      buttons=new JButton[rowCount[0]*columnCount[0]];
	      experimentLayout = new GridLayout(rowCount[board],columnCount[board]);
	      experimentLayout.setHgap(0);
	      experimentLayout.setVgap(0);
	      Container c=getContentPane();
	      createMenu();
	      panel=new JPanel();
	      panel.setLayout(experimentLayout);
	      panel2= new JPanel();
	      panel2.setLayout(new FlowLayout(1,0,5));
	      buttonCount=rowCount[board]*columnCount[board];
	      
	      //Add listeners to all buttons in minefield
	      for (int i=0;i<rowCount[board]*columnCount[board];i++){
	    	  buttons[i]=new JButton(); //Buttons are held in 1d array
	    	  JButton tempButton=buttons[i];
	    	  
	    	  final int rowI=(int) i/columnCount[board];
	    	  final int columnI= i%columnCount[board];
	    	  ml=new MouseAdapter(){
	    		    public void mousePressed(MouseEvent e){//action listener, same as the one listed earlier for button

	    		    	if (e.getButton()==MouseEvent.BUTTON3){
	    		    		if (leftClick&& Math.abs(clickTime-currentCount)<=1){
	    		    			myFields[board].openNeighbors(rowI, columnI);
	    		    		}
	    		    		if (myFields[board].getState(rowI, columnI)!=4 && !myFields[board].getDone()){
	    		    			myFields[board].changeState(rowI, columnI,false);
	    		    			setBombCount(myFields[board].getMines());
	    		    		}
	    		    		leftClick=false;
	    		    	} else if (e.getButton()==MouseEvent.BUTTON1){
	    		    		if (!leftClick&& Math.abs(clickTime-currentCount)<=1){
	    		    			myFields[board].openNeighbors(rowI, columnI);

	    		    		}
	    		    		if (firstClick){
	    		    			myFields[board].FirstClick(rowI, columnI);
	    		    			firstClick=false;
	    		    			paused=false;
		    		    		myFields[board].changeState(rowI,columnI,true);

	    		    		}
	    		    		else if (!paused){
	    		    			if (myFields[board].getNeighbors(rowI, columnI)==-1){
	    		    				paused=true;
	    		    			}
		    		    		myFields[board].changeState(rowI,columnI,true);

	    		    		}
	    		    		leftClick=true;
	    		    	}
	    		    	updateButtons();
	    		    	currentCount=clickTime;
	    		    }

	    		};
	    	  tempButton.addMouseListener (ml);
	    	  panel.add(buttons[i]);
	    	  
	      }
	      Image newimg;

	      
	      
	      panel2.setPreferredSize(new Dimension(widths[board],40));
	      panel.setPreferredSize(new Dimension(widths[board],heights[board]));

	      
	      
	      JButton smiley= new JButton(); //Reset button
	      smiley.setPreferredSize(new Dimension(25,25));
	      smiley.addMouseListener (new MouseAdapter(){
  		    public void mouseClicked(MouseEvent e){
  		    	firstClick=true;
  		    	resetBoard();
  		    }
  		});
	      
	      //set timers and bomb
	      numbers=new ImageIcon[10];
	      time1= new JLabel();
	      time2= new JLabel();
	      time3= new JLabel();

	      blank1= new JLabel("");
	      blank2= new JLabel("");
	      
	      time1.setPreferredSize(new Dimension(15,25));
	      time2.setPreferredSize(new Dimension(15,25));
	      time3.setPreferredSize(new Dimension(15,25));

	      blank1.setPreferredSize(new Dimension(blanks[board],25));
	      blank2.setPreferredSize(new Dimension(blanks[board],25));
	      
	      bomb1= new JLabel();
	      bomb2= new JLabel();
	      bomb3= new JLabel();
	      
	      bomb1.setPreferredSize(new Dimension(15,25));
	      bomb2.setPreferredSize(new Dimension(15,25));
	      bomb3.setPreferredSize(new Dimension(15,25));
	      
	      
	      panel2.add(bomb1);
	      panel2.add(bomb2);
	      panel2.add(bomb3);

	      panel2.add(blank1);
	      panel2.add(smiley);
	      panel2.add(blank2);
	      

	      panel2.add(time1);
	      panel2.add(time2);
	      panel2.add(time3);


	      panel2.setBorder(BorderFactory.createRaisedBevelBorder());
	      panel.setBorder(BorderFactory.createLoweredBevelBorder());
	      c.add(panel2,BorderLayout.NORTH);
	      c.add(panel,BorderLayout.SOUTH);
	      
	      pack();
	      setVisible(true);
	      
	      int delay = 1000; //milliseconds

	      int delay2=100;
	      //this is used for clock
	      ActionListener taskPerformer = new ActionListener() {
	          public void actionPerformed(ActionEvent evt) {//dont use clock unless first click has happened, game is not paused, and game has not been finished
	        	  if (!firstClick && !paused && !myFields[board].getDone()){
	        	  gameTime++;
	        	  setClock(gameTime);
	        	  }
	          }
	      };
	      
	      //This timer is used to determine if right and left clicks happen close enough together
	      ActionListener taskPerformer2 = new ActionListener() {
	          public void actionPerformed(ActionEvent evt) {
	        	  clickTime++;
	          }
	      };
	      
	      new Timer(delay, taskPerformer).start();
	      new Timer(delay2, taskPerformer2).start();


	     
	      //Get all the proper images and convert into image icons
		   imgSmiley= new ImageIcon("/Users/jacobmiller/Documents/workspace/Homework5/Smiley.png");
		   newimg=imgSmiley.getImage().getScaledInstance( smiley.getWidth(), smiley.getHeight(),  java.awt.Image.SCALE_SMOOTH );  
		   imgSmiley= new ImageIcon(newimg);
		   smiley.setIcon(imgSmiley);
	     
		   for (int i=0;i<10;i++){
			   String location="/Users/jacobmiller/Documents/workspace/Homework5/";
			   location=location+i+".png";
			   numbers[i]=new ImageIcon(location);
			   newimg=numbers[i].getImage().getScaledInstance( time1.getWidth(), time1.getHeight(),  java.awt.Image.SCALE_SMOOTH );  
			   numbers[i]= new ImageIcon(newimg);
		   }

		   imgClock= new ImageIcon("/Users/jacobmiller/Documents/workspace/Homework5/clock.png");
		   newimg=imgClock.getImage().getScaledInstance( bomb1.getWidth(), bomb1.getHeight(),  java.awt.Image.SCALE_SMOOTH );  
		   imgClock= new ImageIcon(newimg);
		   
		   imgBlock= new ImageIcon("/Users/jacobmiller/Documents/workspace/Homework5/block.png");
		   newimg=imgBlock.getImage().getScaledInstance( buttons[0].getWidth(), buttons[0].getHeight(),  java.awt.Image.SCALE_SMOOTH );  
		   imgBlock= new ImageIcon(newimg);
		   
		   imgBlank= new ImageIcon("/Users/jacobmiller/Documents/workspace/Homework5/blank.png");
		   newimg=imgBlank.getImage().getScaledInstance( buttons[0].getWidth(), buttons[0].getHeight(),  java.awt.Image.SCALE_SMOOTH );  
		   imgBlank= new ImageIcon(newimg);
		   
		   imgFlag= new ImageIcon("/Users/jacobmiller/Documents/workspace/Homework5/flag.png");
		   newimg=imgFlag.getImage().getScaledInstance( buttons[0].getWidth(), buttons[0].getHeight(),  java.awt.Image.SCALE_SMOOTH );  
		   imgFlag= new ImageIcon(newimg);
		   
		   imgQuestion= new ImageIcon("/Users/jacobmiller/Documents/workspace/Homework5/question.png");
		   newimg=imgQuestion.getImage().getScaledInstance( buttons[0].getWidth(), buttons[0].getHeight(),  java.awt.Image.SCALE_SMOOTH );  
		   imgQuestion= new ImageIcon(newimg);
		   
		   imgBomb= new ImageIcon("/Users/jacobmiller/Documents/workspace/Homework5/bomb.png");
		   newimg=imgBomb.getImage().getScaledInstance( buttons[0].getWidth(), buttons[0].getHeight(),  java.awt.Image.SCALE_SMOOTH );  
		   imgBomb= new ImageIcon(newimg);
		   
		   imgRed= new ImageIcon("/Users/jacobmiller/Documents/workspace/Homework5/redbomb.png");
		   newimg=imgRed.getImage().getScaledInstance( buttons[0].getWidth(), buttons[0].getHeight(),  java.awt.Image.SCALE_SMOOTH );  
		   imgRed= new ImageIcon(newimg);
		   
		   imgOne= new ImageIcon("/Users/jacobmiller/Documents/workspace/Homework5/one.png");
		   newimg=imgOne.getImage().getScaledInstance( buttons[0].getWidth(), buttons[0].getHeight(),  java.awt.Image.SCALE_SMOOTH );  
		   imgOne= new ImageIcon(newimg);
		   
		   imgTwo= new ImageIcon("/Users/jacobmiller/Documents/workspace/Homework5/two.png");
		   newimg=imgTwo.getImage().getScaledInstance( buttons[0].getWidth(), buttons[0].getHeight(),  java.awt.Image.SCALE_SMOOTH );  
		   imgTwo= new ImageIcon(newimg);
		   
		   imgThree= new ImageIcon("/Users/jacobmiller/Documents/workspace/Homework5/three.png");
		   newimg=imgThree.getImage().getScaledInstance( buttons[0].getWidth(), buttons[0].getHeight(),  java.awt.Image.SCALE_SMOOTH );  
		   imgThree= new ImageIcon(newimg);
		   
		   imgFour= new ImageIcon("/Users/jacobmiller/Documents/workspace/Homework5/four.png");
		   newimg=imgFour.getImage().getScaledInstance( buttons[0].getWidth(), buttons[0].getHeight(),  java.awt.Image.SCALE_SMOOTH );  
		   imgFour= new ImageIcon(newimg);
		   
		   imgFive= new ImageIcon("/Users/jacobmiller/Documents/workspace/Homework5/five.png");
		   newimg=imgFive.getImage().getScaledInstance( buttons[0].getWidth(), buttons[0].getHeight(),  java.awt.Image.SCALE_SMOOTH );  
		   imgFive= new ImageIcon(newimg);
		   
		   imgSix= new ImageIcon("/Users/jacobmiller/Documents/workspace/Homework5/six.png");
		   newimg=imgSix.getImage().getScaledInstance( buttons[0].getWidth(), buttons[0].getHeight(),  java.awt.Image.SCALE_SMOOTH );  
		   imgSix= new ImageIcon(newimg);
		   
		   
		   setClock(gameTime);
		   setBombCount(mineCount[board]);
   	   	  updateButtons();

	      
	      
	   }
	   
	   //Update the field of buttons to reflect what has changed within the field object
	   public void updateButtons(){
		   columns=columnCount[board];
		   //go through all buttons
		   for (int i=0;i<rowCount[board];i++){
			   for (int j=0;j<columnCount[board];j++){

				   if (myFields[board].getState(i, j)!=4){// If not opened mine
					   if (myFields[board].getState(i,j)==0){ //unclicked square
						   buttons[i*columns+j].setIcon(imgBlock);
					   }
					   else if (myFields[board].getState(i,j)==1){ //flag
						   buttons[i*columns+j].setIcon(imgFlag);


					   }
					   else if (myFields[board].getState(i,j)==2){//?
						   buttons[i*columns+j].setIcon(imgQuestion);
					   }

				   }else{
					   if (myFields[board].getNeighbors(i,j)==-1){ //It is a mine
						   if (myFields[board].GetRed(i,j)==false)buttons[i*columns+j].setIcon(imgBomb);//red mine
						   else buttons[i*columns+j].setIcon(imgRed);
					   }
					   else{
						   if (myFields[board].getNeighbors(i, j)==0){

							   buttons[i*columns+j].setIcon(imgBlank);

						   }else{//Give proper count of neighboring mines

							   switch (myFields[board].getNeighbors(i, j)){
							   		case 1:
										   buttons[i*columns+j].setIcon(imgOne);
										   break;
							   		case 2:
										   buttons[i*columns+j].setIcon(imgTwo);		
										   break;				
							   		case 3:
										   buttons[i*columns+j].setIcon(imgThree);												   
										   break;
							   		case 4:
										   buttons[i*columns+j].setIcon(imgFour);		
										   break;
							   		case 5:
										   buttons[i*columns+j].setIcon(imgFive);		
										   break;
							   		case 6:
										   buttons[i*columns+j].setIcon(imgSix);		
										   break;
							   			
							   	
							   }
						   }

					   }

				   }
			   }
		   }
		   repaint();

	   }

	   //Main function
	   public static void main( String args[] )
	   {
		  myFields=new Field[3];
		  
		  for (int i=0;i<3;i++){
			  myFields[i]=new Field(rowCount[i],columnCount[i],mineCount[i]);
		  }
		  
		  //myField= new Field(rowCount[board],columnCount[board],mineCount[board]);
		  
	      Sweeper app = new Sweeper();

	      app.addWindowListener(
	         new WindowAdapter() {
	            public void windowClosing( WindowEvent e )
	            {
	               System.exit( 0 );
	            }
	         }
	      );

	      //app.show();
	      //app.setSize(new Dimension(400, 400));
	      
	      app.setVisible(true);

	      
	   }
		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getButton()==MouseEvent.BUTTON1) {	// Left button
				
				
			}
			else if(e.getButton()==MouseEvent.BUTTON3) {	// Right button
				for (int i=0;i<rows*columns;i++){
					
				}
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
			
		}

		@Override
		public void mouseEntered(MouseEvent e) { // cursor enter the window
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) { // cursor exit the window
			// TODO Auto-generated method stub
			
		}
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			System.out.println("Your window get focus."); 

		}
}


