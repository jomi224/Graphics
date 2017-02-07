package skybox;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

import java.io.File;

import com.jogamp.opengl.*;
import com.jogamp.opengl.util.texture.*;



public class JoglEventListener implements GLEventListener, KeyListener, MouseListener, MouseMotionListener {
	
	
	float bottom=0f;
	float boxLength=10000f;
	float section= (1-bottom)/3;
	float backrgb[] = new float[4]; 
	Texture texture=null,texture2=null,texture3=null,houseWall=null,roof=null,door=null,map=null;
	int texID,tex2ID,tex3,texHouse,texRoof,texDoor,texMap;
	int windowWidth, windowHeight;
	float orthoX=40;
	
	float playerX=0f,playerY=0f,playerZ=0f;

	
	int mouseX0, mouseY0;	
	float picked_x = 0.0f, picked_y = 0.0f;
	
	float focalLength = 30.0f;
	
	//angle of rotation
	float rotateY = 0.0f; // 
	float rotateX= 0.0f;
	float pointClick[]={0f,0f};
	
	boolean diffuse_flag  = false;
	boolean specular_flag = false;
	
	boolean smooth_flag = true;

    private GLU glu = new GLU();
	
    private GLUT glut = new GLUT();
	

	
	    public void displayChanged(GLAutoDrawable gLDrawable, boolean modeChanged, boolean deviceChanged) {
		 
	    }

	    /** Called by the drawable immediately after the OpenGL context is
	     * initialized for the first time. Can be used to perform one-time OpenGL
	     * initialization such as setup of lights and display lists.
	     * @param gLDrawable The GLAutoDrawable object.
	     */
	    public void init(GLAutoDrawable gLDrawable) {
	        GL2 gl = gLDrawable.getGL().getGL2();
	        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);    // Black Background
	        gl.glClearDepth(1.0f);                      // Depth Buffer Setup
	        gl.glEnable(GL.GL_DEPTH_TEST);              // Enables Depth Testing
	        //gl.glDepthFunc(GL.GL_LEQUAL);               // The Type Of Depth Testing To Do
	        try {
	        	 texture = TextureIO.newTexture(new File("/Users/jacobmiller/Documents/workspace/Homework4/skybox1.jpg"), false);
	        	 texture2= TextureIO.newTexture(new File("/Users/jacobmiller/Documents/workspace/Homework4/wood.jpg"), false);
	        	 texture3= TextureIO.newTexture(new File("/Users/jacobmiller/Documents/workspace/Homework4/ground.jpg"), false);
	        	 houseWall= TextureIO.newTexture(new File("/Users/jacobmiller/Documents/workspace/Homework4/brick.jpg"), false);
	        	 roof= TextureIO.newTexture(new File("/Users/jacobmiller/Documents/workspace/Homework4/roof.jpg"), false);
	        	 door= TextureIO.newTexture(new File("/Users/jacobmiller/Documents/workspace/Homework4/door.jpg"), false);
	        	 map= TextureIO.newTexture(new File("/Users/jacobmiller/Documents/workspace/Homework4/graph.jpg"), false);

	        	 texID = texture.getTextureObject();
	        	 tex2ID=texture2.getTextureObject();
	        	 tex3=texture3.getTextureObject();
	        	 texHouse=houseWall.getTextureObject();
	        	 texRoof=roof.getTextureObject();
	        	 texDoor=door.getTextureObject();
	        	 texMap=map.getTextureObject();
	        	 
	             gl.glEnable(GL.GL_TEXTURE_2D);
	             texture.bind(gl);
	             texture2.bind(gl);
	             texture3.bind(gl);
	             houseWall.bind(gl);
	             roof.bind(gl);
	             door.bind(gl);
	             map.bind(gl);
	             
	        } catch (Exception ex) {
	        	ex.printStackTrace();
	        }

	        gl.glMatrixMode(GL2.GL_MODELVIEW);
	        gl.glLoadIdentity();
	        
	        
	        
	    }


	    
	    public void reshape(GLAutoDrawable gLDrawable, int x, int y, int width, int height) {
	    	windowWidth = width;
	    	windowHeight = height;
	        final GL2 gl = gLDrawable.getGL().getGL2();

	        if (height <= 0) // avoid a divide by zero error!
	            height = 1;
	        final float h = (float) width / (float) height;
	        gl.glViewport(0, 0, width, height);
	        gl.glMatrixMode(GL2.GL_PROJECTION);
	        gl.glLoadIdentity();
	       // gl.glOrtho(-orthoX*0.5, orthoX*0.5, -orthoX*0.5*height/width, orthoX*0.5*height/width, -100, 100);
	        glu.gluPerspective(45.0f, h, 1, 100000.0);

	    }
	    
	    

		@Override
		public void display(GLAutoDrawable gLDrawable) {
			// TODO Auto-generated method stub
			final GL2 gl = gLDrawable.getGL().getGL2();

			gl.glClearColor(1f, 0, 0, 1);
			gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		
	    	gl.glMatrixMode(GL2.GL_MODELVIEW);
	    	gl.glLoadIdentity();

			
			glu.gluLookAt( playerX, playerY, playerZ,playerX+Math.sin(rotateY*Math.PI/180f)*Math.cos(rotateX*Math.PI/180f),playerY-Math.sin(rotateX*Math.PI/180f), playerZ+Math.cos(rotateY*Math.PI/180f)*Math.cos(rotateX*Math.PI/180f),0.0, 1.0, 0.0); // eye point, x, y, z, looking at x, y, z, Up direction 
			//glu.gluLookAt( playerX, playerY, playerZ,(float)(Math.sin(rotateY*Math.PI/180f)),-(float)(Math.sin(rotateX*Math.PI/180f)*Math.cos(rotateY*Math.PI/180f)),(float)(Math.cos(rotateY*Math.PI/180f)*Math.cos(rotateX*Math.PI/180f)) ,0.0, 1.0, 0.0); // eye point, x, y, z, looking at x, y, z, Up direction 

	    	//glu.gluPerspective(45.0f, 1, 0.5, 15);

	    	gl.glBindTexture(GL.GL_TEXTURE_2D, texID);
	    	
	    	gl.glBegin(GL2.GL_QUADS);
	    	gl.glColor4f(1f, 1f, 1f, 1f);
	    	
	    	gl.glTexCoord2f(0.25f, 2f/3f);
	    	gl.glVertex3f(playerX-boxLength,playerY+ boxLength, playerZ+boxLength);

	    	gl.glTexCoord2f(0.0f, 2/3f);
	    	gl.glVertex3f(playerX+boxLength, playerY+boxLength, playerZ+boxLength);

	    	gl.glTexCoord2f(0.0f,0.34f);
	    	gl.glVertex3f(playerX+boxLength, playerY-boxLength, playerZ+boxLength);

	    	gl.glTexCoord2f(0.25f,0.34f);
	    	gl.glVertex3f(playerX-boxLength, playerY-boxLength, playerZ+boxLength);

	    	
	    	gl.glTexCoord2f(0.25f,0.34f);
	    	gl.glVertex3f(playerX-boxLength, playerY-boxLength,playerZ+ boxLength);
	    	
	    	gl.glTexCoord2f(0.25f, bottom+section*2);
	    	gl.glVertex3f(playerX-boxLength, playerY+boxLength, playerZ+boxLength);
	    	
	    	gl.glTexCoord2f(0.5f, bottom+section*2);
	    	gl.glVertex3f(playerX-boxLength, playerY+boxLength, playerZ-boxLength);
	    	
	    	gl.glTexCoord2f(0.5f,0.34f);
	    	gl.glVertex3f(playerX-boxLength, playerY-boxLength, playerZ-boxLength);
	    	
	    
	  
	    	gl.glTexCoord2f(0.5f, bottom+section*2);
	    	gl.glVertex3f(playerX-boxLength, playerY+boxLength, playerZ-boxLength);

	    	gl.glTexCoord2f(0.75f, bottom+section*2);
	    	gl.glVertex3f(playerX+boxLength, playerY+boxLength, playerZ-boxLength);

	    	gl.glTexCoord2f(0.75f,0.34f);
	    	gl.glVertex3f(playerX+boxLength, playerY-boxLength, playerZ-boxLength);

	    	gl.glTexCoord2f(0.5f,0.34f);
	    	gl.glVertex3f(playerX-boxLength, playerY-boxLength, playerZ-boxLength);

	    
	    	gl.glTexCoord2f(1f,0.34f);
	    	gl.glVertex3f(playerX+boxLength, playerY-boxLength,playerZ+ boxLength);
	    	
	    	gl.glTexCoord2f(1f, bottom+section*2);
	    	gl.glVertex3f(playerX+boxLength, playerY+boxLength,playerZ+ boxLength);
	    	
	    	gl.glTexCoord2f(0.75f, bottom+section*2);
	    	gl.glVertex3f(playerX+boxLength, playerY+boxLength, playerZ-boxLength);
	    	
	    	gl.glTexCoord2f(0.75f,0.34f);
	    	gl.glVertex3f(playerX+boxLength, playerY-boxLength, playerZ-boxLength);
	  
	    	//bottom face
	    	gl.glTexCoord2f(0.499f,0.01f);
	    	
	    	gl.glVertex3f(playerX+boxLength, playerY-boxLength, playerZ-boxLength);
	    	
	    	gl.glTexCoord2f(0.499f, 0.34f);
	    	gl.glVertex3f(playerX-boxLength, playerY-boxLength, playerZ-boxLength);
	    	
	    	gl.glTexCoord2f(0.251f, 0.34f);
	    	gl.glVertex3f(playerX-boxLength,playerY -boxLength, playerZ+boxLength);
	    	
	    	gl.glTexCoord2f(0.251f,0.01f);
	    	gl.glVertex3f(playerX+boxLength, playerY-boxLength, playerZ+boxLength);
	    	
	    	//Top face
	    	gl.glTexCoord2f(0.499f,2/3f);
	    	
	    	gl.glVertex3f(playerX-boxLength,playerY+ boxLength, playerZ-boxLength);
	    	
	    	gl.glTexCoord2f(0.499f, 1f);
	    	gl.glVertex3f(playerX+boxLength, playerY+boxLength, playerZ-boxLength);
	    	
	    	gl.glTexCoord2f(0.251f, 1f);
	    	gl.glVertex3f(playerX+boxLength, playerY+boxLength, playerZ+boxLength);
	    	
	    	gl.glTexCoord2f(0.251f,2/3f);
	    	gl.glVertex3f(playerX-boxLength,playerY+ boxLength, playerZ+boxLength);
	    	
	    	
	    	//gl.glBindTexture(GL2.GL_TEXTURE_2D, 0);
	    	float floorLength=50f;
	    	float playerHeight=5f;
	    	gl.glEnd();
	    	
	    	
	    	//Floor
	    	gl.glBindTexture(GL2.GL_TEXTURE_2D, tex3);

	    	gl.glBegin(GL2.GL_QUADS);	    	
	    	gl.glColor4f(1f, 1f, 1f,1f);
	    	gl.glTexCoord2f(1f, 0f);

	    	gl.glVertex3f(-floorLength, -playerHeight, -floorLength);
	    	gl.glTexCoord2f(0f, 0f);
	    	
	    	gl.glVertex3f(-floorLength, -playerHeight, floorLength);
	    	gl.glTexCoord2f(0f, 1f);

	    	gl.glVertex3f(floorLength, -playerHeight, floorLength);
	    	gl.glTexCoord2f(1f, 1f);

	    	gl.glVertex3f(floorLength, -playerHeight, -floorLength);

	    	gl.glEnd();
	    	
	    	//Make walls
	    	gl.glBindTexture(GL2.GL_TEXTURE_2D, tex2ID);
	    	gl.glBegin(GL2.GL_QUADS);
	    	
	    	gl.glTexCoord2f(0f, 0f);
	    	gl.glVertex3f(-floorLength,-playerHeight,floorLength);
	    	
	    	//front wall
	    	gl.glTexCoord2f(0f, 1f);
	    	gl.glVertex3f(floorLength,-playerHeight,floorLength);
	    	gl.glTexCoord2f(1f, 1f);
	    	gl.glVertex3f(floorLength,0f,floorLength);
	    	gl.glTexCoord2f(1f, 0f);
	    	gl.glVertex3f(-floorLength,0f,floorLength);
	    	
	    	//back wall
	    	gl.glTexCoord2f(0f, 0f);
	    	gl.glVertex3f(-floorLength,-playerHeight,-floorLength);
	    	gl.glTexCoord2f(0f, 1f);
	    	gl.glVertex3f(floorLength,-playerHeight,-floorLength);
	    	gl.glTexCoord2f(1f, 1f);
	    	gl.glVertex3f(floorLength,0f,-floorLength);
	    	gl.glTexCoord2f(1f, 0f);
	    	gl.glVertex3f(-floorLength,0f,-floorLength);
	    	
	    	//left wall
	    	gl.glTexCoord2f(0f, 0f);
	    	gl.glVertex3f(-floorLength,-playerHeight,floorLength);
	    	gl.glTexCoord2f(0f, 1f);
	    	gl.glVertex3f(-floorLength,-playerHeight,-floorLength);
	    	gl.glTexCoord2f(1f, 1f);
	    	gl.glVertex3f(-floorLength,0f,-floorLength);
	    	gl.glTexCoord2f(1f, 0f);
	    	gl.glVertex3f(-floorLength,0f,floorLength);
	    	
	    	//right wall
	    	gl.glTexCoord2f(0f, 0f);
	    	gl.glVertex3f(floorLength,-playerHeight,floorLength);
	    	gl.glTexCoord2f(0f, 1f);
	    	gl.glVertex3f(floorLength,-playerHeight,-floorLength);
	    	gl.glTexCoord2f(1f, 1f);
	    	gl.glVertex3f(floorLength,0f,-floorLength);
	    	gl.glTexCoord2f(1f, 0f);
	    	gl.glVertex3f(floorLength,0f,floorLength);
	    	
	    	gl.glEnd();
	    	//gl.glFlush();
	    	drawHouse(gl, -15f, 20f,7f,6f,playerHeight);
	    	drawDisplay(gl);
		}
		public void drawDisplay(final GL2 gl){
			
	    	gl.glBindTexture(GL2.GL_TEXTURE_2D, texMap);
			gl.glBegin(GL2.GL_QUADS);
			
			gl.glColor3f(0.2f,0.2f,0.2f);
			float xZ=1.5f*(float)(Math.sin(rotateY*Math.PI/180f))*(float)Math.cos(rotateX*Math.PI/180f);
			float yZ=-1.5f*(float)(Math.sin(rotateX*Math.PI/180f));
			float zZ=1.5f*(float)(Math.cos(rotateY*Math.PI/180f)*Math.cos(rotateX*Math.PI/180f));
			
			float xY=(float)(Math.sin(rotateY*Math.PI/180f))*(float)Math.sin(rotateX*Math.PI/180f);
			float yY=(float)Math.cos(rotateX*Math.PI/180f);
			float zY=(float)Math.sin(rotateX*Math.PI/180f)*(float)Math.cos(rotateY*Math.PI/180f);;
			
			
			float xX=(float)Math.cos(rotateY*Math.PI/180f);
			float yX=0f;
			float zX=-(float)Math.sin(rotateY*Math.PI/180f);
			
			float start=-0.25f;
			float end=-0.55f;
			
	    	gl.glTexCoord2f(0f, 0f);
			gl.glVertex3f(playerX+xZ+start*xY+start*xX, playerY+yZ+start*yY+start*yX, playerZ+zZ+start*zY+start*zX);
			
	    	gl.glTexCoord2f(1f, 0f);
			gl.glVertex3f(playerX+xZ+end*xY+start*xX, playerY+yZ+end*yY+start*yX, playerZ+zZ+end*zY+start*zX);
			
	    	gl.glTexCoord2f(1f, 1f);
			gl.glVertex3f(playerX+xZ+end*xY+end*xX, playerY+yZ+end*yY+end*yX, playerZ+zZ+end*zY+end*zX);
			
	    	gl.glTexCoord2f(0f, 1f);
			gl.glVertex3f(playerX+xZ+start*xY+end*xX, playerY+yZ+start*yY+end*yX, playerZ+zZ+start*zY+end*zX);

			
		
		
			
			gl.glEnd(); 
		}
		public void drawHouse(final GL2 gl, float centerX, float centerZ,float houseHeight,float houseLength,float playerHeight){
			
	    	gl.glBindTexture(GL2.GL_TEXTURE_2D, texHouse);
			gl.glBegin(GL2.GL_QUADS);
			
	    	gl.glTexCoord2f(0f, 1f);
	    	gl.glVertex3f(centerX+houseLength,-playerHeight,centerZ+houseLength);
	    	
	    	gl.glTexCoord2f(1f, 1f);
	    	gl.glVertex3f(centerX+houseLength,-playerHeight,centerZ-houseLength);
	    	
	    	gl.glTexCoord2f(1f, 0f);
	    	gl.glVertex3f(centerX+houseLength,-playerHeight+houseHeight,centerZ-houseLength);
	    	
	    	gl.glTexCoord2f(0f, 0f);
	    	gl.glVertex3f(centerX+houseLength,-playerHeight+houseHeight,centerZ+houseLength);
	    	
	    	
	    	gl.glTexCoord2f(0f, 1f);
	    	gl.glVertex3f(centerX-houseLength,-playerHeight,centerZ+houseLength);
	    	
	    	gl.glTexCoord2f(1f, 1f);
	    	gl.glVertex3f(centerX-houseLength,-playerHeight,centerZ-houseLength);
	    	
	    	gl.glTexCoord2f(1f, 0f);
	    	gl.glVertex3f(centerX-houseLength,-playerHeight+houseHeight,centerZ-houseLength);
	    	
	    	gl.glTexCoord2f(0f, 0f);
	    	gl.glVertex3f(centerX-houseLength,-playerHeight+houseHeight,centerZ+houseLength);
	    	
	    	
	    	//Front wall 
	    	gl.glTexCoord2f(0f, 1f);
	    	gl.glVertex3f(centerX-houseLength,-playerHeight,centerZ-houseLength);
	    	
	    	gl.glTexCoord2f(1f, 1f);
	    	gl.glVertex3f(centerX+houseLength,-playerHeight,centerZ-houseLength);
	    	
	    	gl.glTexCoord2f(1f, 0f);
	    	gl.glVertex3f(centerX+houseLength,-playerHeight+houseHeight,centerZ-houseLength);
	    	
	    	gl.glTexCoord2f(0f, 0f);
	    	gl.glVertex3f(centerX-houseLength,-playerHeight+houseHeight,centerZ-houseLength);
	    	
	    	//back wall
	    	gl.glTexCoord2f(0f, 1f);
	    	gl.glVertex3f(centerX-houseLength,-playerHeight,centerZ+houseLength);
	    	
	    	gl.glTexCoord2f(1f, 1f);
	    	gl.glVertex3f(centerX+houseLength,-playerHeight,centerZ+houseLength);
	    	
	    	gl.glTexCoord2f(1f, 0f);
	    	gl.glVertex3f(centerX+houseLength,-playerHeight+houseHeight,centerZ+houseLength);
	    	
	    	gl.glTexCoord2f(0f, 0f);
	    	gl.glVertex3f(centerX-houseLength,-playerHeight+houseHeight,centerZ+houseLength);
	    	
			gl.glEnd();
			
			//Triangles on top of two walls of house
			gl.glBegin(GL.GL_TRIANGLES);
	    	gl.glTexCoord2f(0f, 0.5f);
	    	gl.glVertex3f(centerX-houseLength,-playerHeight+houseHeight,centerZ-houseLength);
	    	
	    	gl.glTexCoord2f(1f, 0.5f);
	    	gl.glVertex3f(centerX+houseLength,-playerHeight+houseHeight,centerZ-houseLength);
	    	
	    	gl.glTexCoord2f(0.5f, 0f);
	    	gl.glVertex3f(centerX,-playerHeight+houseHeight+houseHeight/2,centerZ-houseLength);
			
	    	gl.glTexCoord2f(0f, 0.5f);
	    	gl.glVertex3f(centerX-houseLength,-playerHeight+houseHeight,centerZ+houseLength);
	    	
	    	gl.glTexCoord2f(1f, 0.5f);
	    	gl.glVertex3f(centerX+houseLength,-playerHeight+houseHeight,centerZ+houseLength);
	    	
	    	gl.glTexCoord2f(0.5f, 0f);
	    	gl.glVertex3f(centerX,-playerHeight+houseHeight+houseHeight/2,centerZ+houseLength);

	    	
			gl.glEnd();
	    	gl.glBindTexture(GL2.GL_TEXTURE_2D, texRoof);

			gl.glBegin(GL2.GL_QUADS);
			
			//Make roofs
	    	gl.glTexCoord2f(0f,0f);
	    	gl.glVertex3f(centerX,-playerHeight+houseHeight+houseHeight/2,centerZ+houseLength);
	    	
	    	gl.glTexCoord2f(1f,0f);
	    	gl.glVertex3f(centerX,-playerHeight+houseHeight+houseHeight/2,centerZ-houseLength);
	    	
	    	gl.glTexCoord2f(1f,1f);
	    	gl.glVertex3f(centerX+houseLength,-playerHeight+houseHeight,centerZ-houseLength);
	    	
	    	gl.glTexCoord2f(0f,1f);
	    	gl.glVertex3f(centerX+houseLength,-playerHeight+houseHeight,centerZ+houseLength);

	    	
	    	gl.glTexCoord2f(0f,0f);
	    	gl.glVertex3f(centerX,-playerHeight+houseHeight+houseHeight/2,centerZ+houseLength);
	    	
	    	gl.glTexCoord2f(1f,0f);
	    	gl.glVertex3f(centerX,-playerHeight+houseHeight+houseHeight/2,centerZ-houseLength);
	    	
	    	gl.glTexCoord2f(1f,1f);
	    	gl.glVertex3f(centerX-houseLength,-playerHeight+houseHeight,centerZ-houseLength);
	    	
	    	gl.glTexCoord2f(0f,1f);
	    	gl.glVertex3f(centerX-houseLength,-playerHeight+houseHeight,centerZ+houseLength);

			gl.glEnd();
			
	    	gl.glBindTexture(GL2.GL_TEXTURE_2D, texDoor);
			gl.glBegin(GL2.GL_QUADS);
			
	    	gl.glTexCoord2f(0f, 0f);
	    	gl.glVertex3f(centerX-houseLength/4f,-playerHeight,centerZ-houseLength-0.1f);
	    	
	    	gl.glTexCoord2f(1f, 0f);
	    	gl.glVertex3f(centerX+houseLength/4f,-playerHeight,centerZ-houseLength-0.1f);
	    	
	    	gl.glTexCoord2f(1f, 1f);
	    	gl.glVertex3f(centerX+houseLength/4f,-playerHeight+houseHeight*0.75f,centerZ-houseLength-0.1f);
	    	
	    	gl.glTexCoord2f(0f, 1f);
	    	gl.glVertex3f(centerX-houseLength/4f,-playerHeight+houseHeight*0.75f,centerZ-houseLength-0.1f);
	    	gl.glEnd();
			
			
		}
		@Override
		public void dispose(GLAutoDrawable arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
		    char key= e.getKeyChar();
			
			switch(key)
			{
			case 'w':
				playerZ+=2*Math.cos(rotateY*Math.PI/180f);
				playerX+=2*Math.sin(rotateY*Math.PI/180f);
				break;
			case 's':
				playerZ-=2*Math.cos(rotateY*Math.PI/180f);
				playerX-=2*Math.sin(rotateY*Math.PI/180f);
				break;
			case 'a':
				playerZ-=2*Math.sin(rotateY*Math.PI/180f);
				playerX+=2*Math.cos(rotateY*Math.PI/180f);
				break;
			case 'd':
				playerZ+=2*Math.sin(rotateY*Math.PI/180f);
				playerX-=2*Math.cos(rotateY*Math.PI/180f);
				break;
			
			default:
				
				break;
			
			}
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseDragged(MouseEvent e) {
			float XX = (e.getX()-windowWidth*0.5f)*orthoX/windowWidth;
			float YY = -(e.getY()-windowHeight*0.5f)*orthoX/windowWidth;

			if (YY<pointClick[1]+pointClick[0]-XX && YY>pointClick[1]-pointClick[0]+XX){
				System.out.println("yeah");
				rotateY+=2f;
				if( rotateY>360)rotateY=5;
			}
			else if (YY>pointClick[1]+pointClick[0]-XX && YY<pointClick[1]-pointClick[0]+XX){
				rotateY-=2f;
				if (rotateY<0)rotateY=355;
			}else if (YY>pointClick[1]){
				rotateX-=1f;
				if (rotateX>=90)rotateX=85;
			}
			else if (YY<pointClick[1]){
				rotateX+=1f;
				if (rotateX<=-90)rotateX=-85;
			}	
			
		}
		
		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			System.out.println("Your window get focus."); 

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			/*
			 * Coordinates printout
			 */
			picked_x = (e.getX()-windowWidth*0.5f)*orthoX/windowWidth;
			picked_y = -(e.getY()-windowHeight*0.5f)*orthoX/windowHeight;
			
			System.out.printf("Point clicked: (%.3f, %.3f)\n", picked_x, picked_y);
			
			mouseX0 = e.getX();
			mouseY0 = e.getY();
			
			float XX = (e.getX()-windowWidth*0.5f)*orthoX/windowWidth;
			float YY = -(e.getY()-windowHeight*0.5f)*orthoX/windowWidth;
			pointClick[0]=XX;
			pointClick[1]=YY;
			
			if(e.getButton()==MouseEvent.BUTTON1) {	// Left button
				
				
			}
			else if(e.getButton()==MouseEvent.BUTTON3) {	// Right button
				
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


	
}



