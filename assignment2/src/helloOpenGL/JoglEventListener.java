package helloOpenGL;



import java.io.File;

import com.jogamp.opengl.*;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.texture.*;



public class JoglEventListener implements GLEventListener {
	
	float backrgb[] = new float[4]; 
	float rot; 
	Texture mytex = null; 

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
	        
	        // load the texture;
	        
	        try {
	        	 //mytex = TextureIO.newTexture(new File("C:/Users/ruigang/workspace/helloOpenGL/hp.png"), false);
	        	 mytex = TextureIO.newTexture(new File("C:/Users/ruigang/pictures/cicy.jpg"), false);
	        	 int texID = mytex.getTextureObject();
	        	 gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);  
	             gl.glEnable(GL.GL_TEXTURE_2D);
	             mytex.bind(gl);
	             //gl.glBindTexture(GL.GL_TEXTURE_2D, texID);
	        	 gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
	             gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
	             gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL2.GL_CLAMP);
	             gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL2.GL_CLAMP);
	             gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_REPLACE);
	             
	             gl.glBlendFunc(GL2.GL_SRC_ALPHA,GL2.GL_ONE);
	             
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }

	       
	    }


	    
	    public void reshape(GLAutoDrawable gLDrawable, int x, int y, int width, 
	            int height) {
	        final GL2 gl = gLDrawable.getGL().getGL2();

	        if (height <= 0) // avoid a divide by zero error!
	            height = 1;
	        final float h = (float) width / (float) height;
	        gl.glViewport(width/2*0, 0, width, height);
	        gl.glMatrixMode(GL2.GL_PROJECTION);
	        gl.glLoadIdentity();
	        glu.gluPerspective(45.0f, h, 1.0, 20.9);
	        
	        gl.glMatrixMode(GL2.GL_MODELVIEW);
	        
	        gl.glLoadIdentity();
	     
	        glu.gluLookAt(0, 0, 3, 0, 0, 0, 0, 1, 0);
	    }

		@Override
		public void display(GLAutoDrawable gLDrawable) {
			// TODO Auto-generated method stub
			final GL2 gl = gLDrawable.getGL().getGL2();

			gl.glClearColor(backrgb[0], 0, 1, 1);
			gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

			//backrgb[0]+=0.0005;
			if (backrgb[0]> 1) backrgb[0] = 0; 
			
			
			//gl.glLoadIdentity();
			
			 //gl.glTranslatef(0.0f, 0.0f, -0.1f);
			  
			gl.glColor3f(1.0f, 1.0f, 1.0f); 
			gl.glBegin(GL.GL_LINE_LOOP);
			
			float a = 0.5f, b = 1.0f; 
			for (int i = 0; i < 360; i+=5){
				double x = a* Math.cos(i/180.0* Math.PI);
				double y = b* Math.sin(i/180.0* Math.PI);
				gl.glVertex2d(x, y);
			}

			gl.glEnd(); 
			
			
			gl.glBegin(GL.GL_LINES);
			
			gl.glColor3f(1.0f, 0, 0);
			gl.glVertex2f(0, 0);
			gl.glVertex2f(10, 0);
			
			gl.glColor3f(0.0f, 1.0f, 0.0f);
			gl.glVertex2f(0, 0);
			gl.glVertex2f(0, 10);
			
			
			gl.glColor3f(1.0f, 1.0f, 1.0f);
			gl.glVertex2f(0, 0);
			gl.glVertex3f(0, 0,10);
			
			
			gl.glEnd(); 
	       
			
	        gl.glRotatef(rot, 0.0f, 1.0f, 0.0f);
	        
	        gl.glEnable(GL.GL_TEXTURE_2D);
	        //gl.glDisable(GL.GL_TEXTURE_2D);
	        gl.glBegin(GL2.GL_QUADS);
	        gl.glColor3f(1.0f, 1.0f, 1.0f);     // white
		    //gl.glTexCoord2f(0,0);   
	        gl.glTexCoord2f(0,0); 
	        gl.glVertex3f(-1.0f, -1.0f, -1.0f);    
	         
	        
	        gl.glColor3f(0.0f, 1.0f, 0.0f);     // Green
	        gl.glTexCoord2f(1,0);   
	        gl.glVertex3f(1.0f, -1.0f, -1.0f);  // 
	        
	        gl.glColor3f(1.0f, 0.0f, 0.0f);     
	        gl.glTexCoord2f(1,1);   
	        gl.glVertex3f(1.0f, 1.0f, -1.0f);   
	        
	        gl.glColor3f(0.0f, 0.0f, 1.0f);     
	        gl.glTexCoord2f(0,1);   
	        gl.glVertex3f(-1.0f, 1.0f, -1.0f);   
	        gl.glEnd(); 
	        
	        gl.glDisable(GL.GL_TEXTURE_2D);
	        
	        /*
	        gl.glBegin(GL.GL_TRIANGLES);        // Drawing Using Triangles
	        
	        gl.glColor3f(1.0f, 0.0f, 0.0f);     // Red
	       
	        gl.glVertex3f(0.0f, 1.0f, 0.0f);    // Top Of Triangle (Front)
	        gl.glColor3f(0.0f, 1.0f, 0.0f);     // Green
	        gl.glVertex3f(-1.0f, -1.0f, 1.0f);  // Left Of Triangle (Front)
	        gl.glColor3f(0.0f, 0.0f, 0.0f);     // Blue
	        gl.glVertex3f(1.0f, -1.0f, 1.0f);   // Right Of Triangle (Front)
	        
	        gl.glColor3f(1.0f, 0.0f, 0.0f);     // Red
	        gl.glVertex3f(0.0f, 1.0f, 0.0f);    // Top Of Triangle (Right)
	        gl.glColor3f(0.0f, 0.0f, 1.0f);     // Blue
	        gl.glVertex3f(1.0f, -1.0f, 1.0f);   // Left Of Triangle (Right)
	        gl.glColor3f(0.0f, 1.0f, 0.0f);     // Green
	        gl.glVertex3f(1.0f, -1.0f, -1.0f);  // Right Of Triangle (Right)
	        
	        gl.glColor3f(1.0f, 0.0f, 0.0f);     // Red
	        gl.glVertex3f(0.0f, 1.0f, 0.0f);    // Top Of Triangle (Back)
	        gl.glColor3f(0.0f, 1.0f, 0.0f);     // Green
	        gl.glVertex3f(1.0f, -1.0f, -1.0f);  // Left Of Triangle (Back)
	        gl.glColor3f(0.0f, 0.0f, 1.0f);     // Blue
	        gl.glVertex3f(-1.0f, -1.0f, -1.0f); // Right Of Triangle (Back)
	        
	        gl.glColor3f(1.0f, 0.0f, 0.0f);     // Red
	        gl.glVertex3f(0.0f, 1.0f, 0.0f);    // Top Of Triangle (Left)
	        gl.glColor3f(0.0f, 0.0f, 1.0f);     // Blue
	        gl.glVertex3f(-1.0f, -1.0f, -1.0f); // Left Of Triangle (Left)
	        gl.glColor3f(0.0f, 1.0f, 0.0f);     // Green
	        gl.glVertex3f(-1.0f, -1.0f, 1.0f);  // Right Of Triangle (Left)
	       
	        gl.glEnd();                         // Finished Drawing The Triangle
	        */
	        
	        //rot += 0.01;
	        //gl.glLoadIdentity();

	        
		}

		@Override
		public void dispose(GLAutoDrawable arg0) {
			// TODO Auto-generated method stub
			
		}

	  /*  
	public void init(GLDrawable gLDrawable) {
		final GL gl = glDrawable.getGL();
        final GLU glu = glDrawable.getGLU();

        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluOrtho2D(-1.0f, 1.0f, -1.0f, 1.0f); // drawing square
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
    }*/
	
}
