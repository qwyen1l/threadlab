import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;

/**
   This component draws two car shapes.
*/
public class CarPanel extends JComponent
{  
	private Car car1;
	private int x,y, delay;
	private CarQueue carQueue;
	private int direction;
	private static final int MOVE_AMOUNT = 10;
	private static final int CAR_WIDTH = 60;
	private static final int CAR_HEIGHT = 30;
	
	CarPanel(int x1, int y1, int d, CarQueue queue)
	{
		delay = d;
        x=x1;
        y=y1;
        car1 = new Car(x, y, this);
        carQueue = queue;
        // Get initial direction from queue
        Integer dir = carQueue.deleteQueue();
        if (dir != null) {
            direction = dir;
        } else {
            direction = 2; // default to right
        }
	}
	public void startAnimation()
	   {
	      class AnimationRunnable implements Runnable
	      {
	         public void run()
	         {
	            try
	            {
	               while(true)
	               {
	            	   // Get direction from queue if available
	            	   Integer dir = carQueue.deleteQueue();
	            	   if (dir != null) {
	            		   direction = dir;
	            	   }
	            	   
	            	   // Move car based on direction
	            	   // 0 = up, 1 = down, 2 = right, 3 = left
	            	   switch(direction) {
	            	   case 0: // up
	            		   y -= MOVE_AMOUNT;
	            		   break;
	            	   case 1: // down
	            		   y += MOVE_AMOUNT;
	            		   break;
	            	   case 2: // right
	            		   x += MOVE_AMOUNT;
	            		   break;
	            	   case 3: // left
	            		   x -= MOVE_AMOUNT;
	            		   break;
	            	   }
	            	   
	            	   // Check boundaries and reverse direction if needed
	            	   checkBoundaries();
	            	   
	            	   // Add to queue periodically
	            	   carQueue.addToQueue();
	            	   
	            	   repaint();
	            	   Thread.sleep(delay*100);
	            	   
	               }
	            }
	            catch (InterruptedException exception)
	            {
	            	
	            }
	            finally
	            {
	            	
	            }
	         }
	      }
	      
	      Runnable r = new AnimationRunnable();
	      Thread t = new Thread(r);
	      t.start();
	   }
	
	/**
	 * Check boundaries and reverse direction if car hits a boundary
	 */
	private void checkBoundaries() {
		int panelWidth = getWidth();
		int panelHeight = getHeight();
		
		// Check if car hits a boundary
		if (x <= 0) {
			// Hit left boundary, go right
			direction = 2;
			x = 0;
		} else if (x + CAR_WIDTH >= panelWidth) {
			// Hit right boundary, go left
			direction = 3;
			x = panelWidth - CAR_WIDTH;
		} else if (y <= 0) {
			// Hit top boundary, go down
			direction = 1;
			y = 0;
		} else if (y + CAR_HEIGHT >= panelHeight) {
			// Hit bottom boundary, go up
			direction = 0;
			y = panelHeight - CAR_HEIGHT;
		}
	}
	
   public void paintComponent(Graphics g)
   {  
      Graphics2D g2 = (Graphics2D) g;

      car1.draw(g2,x,y);    
   }
}