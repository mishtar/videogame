package entidades;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import manejadores.Content;
import manejadores.Mouse;
import manejadores.Mouse.MouseState;
import tileMap.TileMap;

public class Moneda  extends MapObject {
	
	private BufferedImage[] startSprites;

	
	private boolean start;
	private boolean permanent;
	
	private int startX;
	private int startY;
	
	private boolean bPresionado;
	
	private Point Position;
	
	private int type = 0;
	public static int VECTOR = 0;
	public static int GRAVITY = 1;
	public static int BOUNCE = 2;
	
	private int bounceCount = 0;
	
	private Mouse.MouseState obtenerEstadoActual;
	
	
	public Moneda(TileMap tm,int iStartX,int iStartY) {
		super(tm);
		
		width = 53;
		height = 51;
		cwidth = 50;
		cheight = 50;
		
		startSprites = Content.Moneda[0];
		animation.setFrames(startSprites);
		animation.setDelay(4);
		startX=iStartX;
		startY=iStartY;
		bPresionado=false;
	}
	
	public void update() {
		 //obtenerEstadoActual=	Mouse.obtenerEstado(1);
		 obtenerEstadoActual=	Mouse.obtenerEstado(0);
		 //obtenerEstadoActual=	Mouse.obtenerEstado(2);
		 if(obtenerEstadoActual==MouseState.ONCE){
			 Position=Mouse.getPosition();
			 if(Position.x>=startX &&Position.x<=startX+width && Position.y>=startY &&Position.y<=startY+height){
				 System.out.println(startX + startY);
				 if (!bPresionado){
				 startSprites = Content.Moneda2[0];
					animation.setFrames(startSprites);
					animation.setDelay(4);
					bPresionado=true;
				 }else{
					 startSprites = Content.Moneda[0];
						animation.setFrames(startSprites);
						animation.setDelay(4);
						bPresionado=false;
				 }
			 }
		 }
	}
	
	public void draw(Graphics2D g) {
		
		super.draw(g);
		Mouse.obtenerEstado(0);
	}
}
