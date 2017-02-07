package helloOpenGL;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.nio.DoubleBuffer;

import com.jogamp.opengl.*;
import com.jogamp.opengl.glu.GLU;




public class JoglEventListener implements GLEventListener, KeyListener, MouseListener, MouseMotionListener {
	
	float[] center={0f,0f,-0.010798f};//Where we start
	float[] pointP={0f,0f,0f}; //Point of click
	float[] mouse={0f,0f,0f};
	boolean scale=false; //Scaling mode
	boolean translate=false; //translating mode
	boolean rotate=false; //rotating mode
	boolean dragging=false;
	boolean updated=false;//has transformation been performed
	boolean rightclick=false; //rightclick or leftclick
	float dist=0f;
	float[] vertices={5.97994f, -0.085086f, -0.010798f, 
			5.97994f, 10.0043f, -0.010798f, 
			7.99077f, 10.0043f, -0.010798f, 
			7.99077f, 11.3449f, -0.010798f, 
			-0.405339f, 11.3449f, -0.010798f, 
			-0.405339f, 9.98083f, -0.010798f, 
			1.65252f, 9.98083f, -0.010798f, 
			1.65252f, 0.549879f, -0.010798f, 
			-0.722839f, 0.549879f, -0.010798f, 
			-0.722839f, -1.69612f, -0.010798f, 
			2.6168f, -1.69612f, -0.010798f, 
			-7.24925f, 0.42055f, -0.010798f, 
			-9.35415f, 0.42055f, -0.010798f, 
			-9.35415f, 10.0043f, -0.010798f, 
			-7.37859f, 10.0043f, -0.010798f, 
			-7.37859f, 11.3802f, -0.010798f, 
			-15.8217f, 11.3802f, -0.010798f, 
			-15.8217f, 9.99258f, -0.010798f, 
			-13.8109f, 9.99258f, -0.010798f, 
			-13.8109f, -0.061591f, -0.010798f, 
			-10.2361f, -1.73139f, -0.010798f, 
			-7.26099f, -1.73139f, -0.010798f, 
			-6.1909f, 0.855631f, -0.010798f, 
			-8.11942f, 0.855631f, -0.010798f, 
			-8.11942f, 2.31379f, -0.010798f, 
			0.217914f, 2.31379f, -0.010798f, 
			0.217914f, 0.926204f, -0.010798f, 
			-1.73415f, 0.926204f, -0.010798f, 
			-1.73415f, -4.10675f, -0.010798f, 
			9.23724f, 0.937952f, -0.010798f, 
			7.26169f, 0.937952f, -0.010798f, 
			7.26169f, 2.38434f, -0.010798f, 
			15.6696f, 2.38434f, -0.010798f, 
			15.6696f, 1.00851f, -0.010798f, 
			14.964f, 1.00851f, -0.010798f, 
			7.75558f, -2.44873f, -0.010798f, 
			14.4231f, -9.36318f, -0.010798f, 
			16.0576f, -9.36318f, -0.010798f, 
			16.0576f, -10.6685f, -0.010798f, 
			7.62625f, -10.6685f, -0.010798f, 
			7.62625f, -9.33965f, -0.010798f, 
			9.67236f, -9.33965f, -0.010798f, 
			4.49827f, -3.90687f, -0.010798f, 
			-1.35784f, -6.59973f, -0.010798f, 
			-1.35784f, -9.3279f, -0.010798f, 
			0.217914f, -9.3279f, -0.010798f, 
			0.217914f, -10.6919f, -0.010798f, 
			-8.22526f, -10.6919f, -0.010798f, 
			-8.22526f, -9.32786f, -0.010798f, 
			-6.20266f, -9.32786f, -0.010798f};
	//original vertices to reset to
	float[] original_vertices={5.97994f, -0.085086f, -0.010798f,  
			5.97994f, 10.0043f, -0.010798f, 
			7.99077f, 10.0043f, -0.010798f, 
			7.99077f, 11.3449f, -0.010798f, 
			-0.405339f, 11.3449f, -0.010798f, 
			-0.405339f, 9.98083f, -0.010798f, 
			1.65252f, 9.98083f, -0.010798f, 
			1.65252f, 0.549879f, -0.010798f, 
			-0.722839f, 0.549879f, -0.010798f, 
			-0.722839f, -1.69612f, -0.010798f, 
			2.6168f, -1.69612f, -0.010798f, 
			-7.24925f, 0.42055f, -0.010798f, 
			-9.35415f, 0.42055f, -0.010798f, 
			-9.35415f, 10.0043f, -0.010798f, 
			-7.37859f, 10.0043f, -0.010798f, 
			-7.37859f, 11.3802f, -0.010798f, 
			-15.8217f, 11.3802f, -0.010798f, 
			-15.8217f, 9.99258f, -0.010798f, 
			-13.8109f, 9.99258f, -0.010798f, 
			-13.8109f, -0.061591f, -0.010798f, 
			-10.2361f, -1.73139f, -0.010798f, 
			-7.26099f, -1.73139f, -0.010798f, 
			-6.1909f, 0.855631f, -0.010798f, 
			-8.11942f, 0.855631f, -0.010798f, 
			-8.11942f, 2.31379f, -0.010798f, 
			0.217914f, 2.31379f, -0.010798f, 
			0.217914f, 0.926204f, -0.010798f, 
			-1.73415f, 0.926204f, -0.010798f, 
			-1.73415f, -4.10675f, -0.010798f, 
			9.23724f, 0.937952f, -0.010798f, 
			7.26169f, 0.937952f, -0.010798f, 
			7.26169f, 2.38434f, -0.010798f, 
			15.6696f, 2.38434f, -0.010798f, 
			15.6696f, 1.00851f, -0.010798f, 
			14.964f, 1.00851f, -0.010798f, 
			7.75558f, -2.44873f, -0.010798f, 
			14.4231f, -9.36318f, -0.010798f, 
			16.0576f, -9.36318f, -0.010798f, 
			16.0576f, -10.6685f, -0.010798f, 
			7.62625f, -10.6685f, -0.010798f, 
			7.62625f, -9.33965f, -0.010798f, 
			9.67236f, -9.33965f, -0.010798f, 
			4.49827f, -3.90687f, -0.010798f, 
			-1.35784f, -6.59973f, -0.010798f, 
			-1.35784f, -9.3279f, -0.010798f, 
			0.217914f, -9.3279f, -0.010798f, 
			0.217914f, -10.6919f, -0.010798f, 
			-8.22526f, -10.6919f, -0.010798f, 
			-8.22526f, -9.32786f, -0.010798f, 
			-6.20266f, -9.32786f, -0.010798f};

	int[] indices={3, 2, 3, 1, 
			3, 1, 3, 6, 
			3, 1, 6, 10, 
			3, 10, 6, 7, 
			3, 10, 7, 8, 
			3, 4, 5, 6, 
			3, 4, 6, 3, 
			3, 10, 8, 9, 
			3, 1, 10, 0, 
			3, 13, 14, 15, 
			3, 13, 15, 18, 
			3, 13, 18, 20, 
			3, 13, 20, 12, 
			3, 16, 17, 18, 
			3, 16, 18, 15, 
			3, 12, 20, 21, 
			3, 12, 21, 11, 
			3, 20, 18, 19, 
			3, 49, 22, 44, 
			3, 44, 22, 28, 
			3, 44, 28, 43, 
			3, 43, 28, 29, 
			3, 43, 29, 42, 
			3, 42, 29, 35, 
			3, 42, 35, 41, 
			3, 41, 35, 36, 
			3, 41, 36, 38, 
			3, 38, 36, 37, 
			3, 39, 40, 41, 
			3, 39, 41, 38, 
			3, 29, 30, 32, 
			3, 29, 32, 34, 
			3, 29, 34, 35, 
			3, 46, 47, 49, 
			3, 46, 49, 44, 
			3, 46, 44, 45, 
			3, 22, 23, 25, 
			3, 22, 25, 27, 
			3, 22, 27, 28, 
			3, 25, 23, 24, 
			3, 27, 25, 26, 
			3, 49, 47, 48, 
			3, 32, 30, 31, 
			3, 34, 32, 33};
	float backrgb[] = new float[4]; 
	float rot; 
	
	/*
	 * Custom variables for mouse drag operations 
	 */
	int windowWidth, windowHeight;
	float orthoX=40;
	float tVal_x, tVal_y, rVal_x, rVal_y, rVal;
	double rtMat[] = new double[16];
	int mouseX0, mouseY0;
	int saveRTnow=0, mouseDragButton=0;
	
	float focalLength = 10.0f;
	float r11 = 1.0f, r12 = 0.0f, r13 = 0.0f, tx = 0.0f,
	      r21 = 0.0f, r22 = 1.0f, r23 = 0.0f, ty = 0.0f,
	      r31 = 0.0f, r32 = 0.0f, r33 = 1.0f, tz = 0.0f;

    private GLU glu = new GLU();

	
	 public void displayChanged(GLAutoDrawable gLDrawable, 
	            boolean modeChanged, boolean deviceChanged) {
	    }

	    /** Called by the drawable immediately after the OpenGL context is
	     * initialized for the first time. Can be used to perform one-time OpenGL
	     * initialization such as setup of lights and display lists.
	     * @param gLDrawable The GLAutoDrawable object.
	     */
	    public void init(GLAutoDrawable gLDrawable) {
	        GL2 gl = gLDrawable.getGL().getGL2();
	        //gl.glShadeModel(GL.GL_LINE_SMOOTH);              // Enable Smooth Shading
	        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);    // Black Background
	        gl.glClearDepth(1.0f);                      // Depth Buffer Setup
	        gl.glEnable(GL.GL_DEPTH_TEST);              // Enables Depth Testing
	        gl.glDepthFunc(GL.GL_LEQUAL);               // The Type Of Depth Testing To Do
	        // Really Nice Perspective Calculations
	        //gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);

	        gl.glMatrixMode(GL2.GL_MODELVIEW);
	        gl.glLoadIdentity();
	        
	        
	        
	    }


	    
	    public void reshape(GLAutoDrawable gLDrawable, int x, int y, int width, 
	            int height) {
	    	windowWidth = width;
	    	windowHeight = height;
	        final GL2 gl = gLDrawable.getGL().getGL2();

	        if (height <= 0) // avoid a divide by zero error!
	            height = 1;
	        final float h = (float) width / (float) height;
	        gl.glViewport(0, 0, width, height);
	        gl.glMatrixMode(GL2.GL_PROJECTION);
	        gl.glLoadIdentity();
	       gl.glOrtho(-orthoX*0.5, orthoX*0.5, -orthoX*0.5*height/width, orthoX*0.5*height/width, -100, 100);
	        //glu.gluPerspective(45.0f, h, 1, 100000.0);

	    }
	    
	    public void project(float[] vertices_in, float[] vertices_out) {
	    
	        
	    	float[] projMatrix = 
		    	{
		    	   1, 0, 0, 0,
		    	   0, 1, 0, 0,
		    	   0, 0, 0, 0,
		    	   0,   0,   1.0f/focalLength, 1
		    	};
		    	
		    	for(int i = 0; i < vertices_in.length; i += 3){
		    		float tempZ = vertices_in[i+2] + 10;  // this translation in Z is needed to pull the camera away from the object. 
		    		// don't change the above line unless you are sure about what you are doing.
		    		
		    		vertices_out[i] = (projMatrix[0] * vertices_in[i] + projMatrix[1] * vertices_in[i + 1] + projMatrix[2] * tempZ + projMatrix[3]);
		    		vertices_out[i + 1] = projMatrix[4] * vertices_in[i] + projMatrix[5] * vertices_in[i + 1] + projMatrix[6] * tempZ+ projMatrix[7];
		    		vertices_out[i + 2] = projMatrix[8] * vertices_in[i] + projMatrix[9] * vertices_in[i + 1] + projMatrix[10] * tempZ+ projMatrix[11];
		    	   
		    		float temp = projMatrix[12] * vertices_in[i] + projMatrix[13] * vertices_in[i + 1] + projMatrix[14] * tempZ+ projMatrix[15];
		    	   
		    	    vertices_out[i]   = vertices_out[i]   / temp;
		    	    vertices_out[i+1] = vertices_out[i+1] / temp;
		    	    vertices_out[i+2] = vertices_out[i+2] / temp;
	    	
		    	}

	    }

	    //Multiply all points in vertices by transformMatrix, create new_vertices
	    public void transform(float[] vertices_in, float[] vertices_out){
	    	//Only do this is something has changed
	    	if (updated){
	    	// perform your transformation
	    
	    	int length = vertices_in.length;
	    	float[] transformMatrix = 
		    	{
		    	   r11, r12, r13, tx,
		    	   r21, r22, r23, ty,
		    	   r31, r32, r33, tz,
		    	   0,   0,   0, 1
		    	};
	    	//Do matrix multiplication
	    	for (int i=0;i<length;i+=3){
	    		vertices_out[i]=transformMatrix[0]*vertices_in[i]+transformMatrix[1]*vertices_in[i+1]+transformMatrix[2]*vertices_in[i+2]+transformMatrix[3];
	    		vertices_out[i+1]=transformMatrix[4]*vertices_in[i]+transformMatrix[5]*vertices_in[i+1]+transformMatrix[6]*vertices_in[i+2]+transformMatrix[7];
	    		vertices_out[i+2]=transformMatrix[8]*vertices_in[i]+transformMatrix[9]*vertices_in[i+1]+transformMatrix[10]*vertices_in[i+2]+transformMatrix[11];
	    	}
	    
	    	//Update values of vertices
	    	for (int i=0; i<length;i++){
	    		vertices_in[i]=vertices_out[i];
	    	}
	    	updated=false; //Transformation has been performed
	    	}
	    }
	    //Change new_vertices back to the original 
	    public void reset(float[] vertices_in, float[] vertices_out){
	    	for (int i=0;i<vertices_in.length;i++){
	    		vertices_out[i]=vertices_in[i];
	    	}
	    }
		@Override
		public void display(GLAutoDrawable gLDrawable) {
			// TODO Auto-generated method stub
			final GL2 gl = gLDrawable.getGL().getGL2();

			gl.glClearColor(backrgb[0], 0, 1, 1);
			gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		
			
	    	gl.glMatrixMode(GL2.GL_MODELVIEW);
	    	gl.glLoadIdentity();
	 	
			float[] vertices_new = new float[vertices.length];

			// call the transform function here
			// transform(...)
			transform(vertices,vertices_new);
			
			project(vertices, vertices_new);
	        
			gl.glBegin(GL.GL_TRIANGLES);        // Drawing Using Triangles
        	for(int i=0; i<44; i++) {
        		gl.glColor3f(0.7f, 0.7f, 0.7f);
        		
        		gl.glVertex3f(vertices_new[(indices[i*4+1])*3],
        				vertices_new[(indices[i*4+1])*3+1],
        				vertices_new[(indices[i*4+1])*3+2]);
        		gl.glVertex3f(vertices_new[(indices[i*4+2])*3],
        				vertices_new[(indices[i*4+2])*3+1],
        				vertices_new[(indices[i*4+2])*3+2]);
        		gl.glVertex3f(vertices_new[(indices[i*4+3])*3],
        				vertices_new[(indices[i*4+3])*3+1],
        				vertices_new[(indices[i*4+3])*3+2]);
        		
        		/*gl.glVertex3f(vertices[(indices[i*4+1])*3],
        					  vertices[(indices[i*4+1])*3+1],
        					  vertices[(indices[i*4+1])*3+2]);
        		gl.glVertex3f(vertices[(indices[i*4+2])*3],
  					  		  vertices[(indices[i*4+2])*3+1],
  					  		  vertices[(indices[i*4+2])*3+2]);
        		gl.glVertex3f(vertices[(indices[i*4+3])*3],
  					  		  vertices[(indices[i*4+3])*3+1],
  					  		  vertices[(indices[i*4+3])*3+2]);*/
        	}
	        gl.glEnd();                         // Finished Drawing The Triangle
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
		    char key= e.getKeyChar();
			System.out.printf("Key typed: %c\n", key); 
			//Change rotation mode
			if (key=='s'){
				scale=true;
				translate=false;
				rotate=false;
			}else if (key=='t'){
				translate=true;
				scale=false;
				rotate=false;
			}
			else if (key=='r'){
				rotate=true;
				translate=false;
				scale=false;
			}else if (key=='0'){ //Reset everything including focal length
				reset(original_vertices,vertices);
				center[0]=0f;
				center[1]=0f;
				rotate=false;
				translate=false;
				scale=false;
				focalLength=10f;
			}else if (key=='g')focalLength+=2f; //zoom in
			else if (key=='h')focalLength-=2f;//zoom out
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			float XX = (e.getX()-windowWidth*0.5f)*orthoX/windowWidth;
			float YY = -(e.getY()-windowHeight*0.5f)*orthoX/windowWidth;
			//If in scaling mode
			if (scale){
				//How far away from center we are
				float newdist=(float) Math.sqrt(Math.pow((XX-pointP[0]),2)+Math.pow((YY-pointP[1]),2));
				//if we are further away from center than before
				if (newdist>dist){
				updated=true;
				dist=newdist;
				float small_dist=0.05f;
				//if in quadrant i increase size
				if (XX>pointP[0] && YY>pointP[1]){
					r11=1+small_dist;
					r22=1+small_dist;
					r33=1+small_dist;
				//else if in quadrant ii decrease size
				}else if(XX<=pointP[0]&&YY<=pointP[1]){
					r11=1-small_dist;
					r22=1-small_dist;
					r33=1-small_dist;
				}
				//Appropriate values to include translation to origin before scaling
				//center is how far away from origin we are
				tx=-r11*center[0]-r12*center[1]+center[0];
				ty=-r21*center[0]-r22*center[1]+center[1];
				tz=-r31*center[0]-r32*center[1];
				}
			}else if (rotate){ //rotating mode
				updated=true;
				float angle= (float) Math.PI/50; //how many angles to rotate
				float cos0= (float) Math.cos(angle);
				float sin0= (float)Math.sin(angle);
				//Set rotation values based on type of click and direction of drag
				if (!rightclick){
				if (YY-pointP[1]>Math.abs(XX-pointP[0])){//left click and right/left is around x axis
					r33= cos0;
					r22=cos0;
					r23=-sin0;
					r32=sin0;
				}else if ((-(YY-pointP[1])>Math.abs(XX-pointP[0]))){
					r33= cos0;
					r22=cos0;
					r23=sin0;
					r32=-sin0;
				} else if (XX- pointP[0]>Math.abs(YY-pointP[1])){//left click and up/down is around y axis
					r33= cos0;
					r11=cos0;
					r13=-sin0;
					r31=sin0;
				}else if (-(XX- pointP[0])>Math.abs(YY-pointP[1])){
					r33= cos0;
					r11=cos0;
					r13=sin0;
					r31=-sin0;
				}
				} else{//right click and up/down is around z-axis
					if (YY-pointP[1]>Math.abs(XX-pointP[0])){
						r11= cos0;
						r22=cos0;
						r12=-sin0;
						r21=sin0;
					}else if ((-(YY-pointP[1])>Math.abs(XX-pointP[0]))){
						r11= cos0;
						r22=cos0;
						r12=sin0;
						r21=-sin0;
					}
				}
				//t values necessary to perform translation to origin then rotate then translate back for transformMatrix
				tx=-r11*center[0]-r12*center[1]+center[0];
				ty=-r21*center[0]-r22*center[1]+center[1];
				tz=-r31*center[0]-r32*center[1];

				
			}else if (translate){
				//change translation values relative to where the mouse was last at
				updated=true;
				tx=(XX-pointP[0]);
				ty=(YY-pointP[1]);
				pointP[0]=XX;
				pointP[1]=YY;
				center[0]+=tx;
				center[1]+=ty;
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			/*
			 * Coordinates printout
			 */
			float XX = (e.getX()-windowWidth*0.5f)*orthoX/windowWidth;
			float YY = -(e.getY()-windowHeight*0.5f)*orthoX/windowWidth;
			System.out.printf("Point clicked: (%.3f, %.3f)\n", XX, YY);
			
			mouseX0 = e.getX();
			mouseY0 = e.getY();
			pointP[0]=XX;
			pointP[1]=YY;
			
			if(e.getButton()==MouseEvent.BUTTON1) {	// Left button
				rightclick=false;
			}
			else if(e.getButton()==MouseEvent.BUTTON3) {	// Right button
				rightclick=true;
			}
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			//Set translation matrix back to identity
			r11=1f;
			r22=1f;
			r33=1f;
			r12=0f;
			r13=0f;
			r21=0f;
			r23=0f;
			r31=0f;
			r32=0f;
			tx=0f;
			ty=0f;
			tz=0f;
			dist=0f;
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
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
		public void dispose(GLAutoDrawable arg0) {
			// TODO Auto-generated method stub
			
		}

}



