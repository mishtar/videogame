package estadoJuego;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import principal.GamePanel;
import audio.JukeBox;
import enemigos.Gazer;
import enemigos.GelPop;
import entidades.Enemy;
import entidades.Explosion;
import entidades.HUD;
import entidades.Moneda;
import entidades.PlayerSave;
import entidades.Title;
import tileMap.Background;
import tileMap.TileMap;

public class Nivel1  extends GameState{
	
	private Background FondoEscenario;
	private ArrayList<Moneda> Monedas;
	private TileMap tileMap;
	
	private HUD hud;
	private BufferedImage hageonText;
	private Title title;
	private Title subtitle;
	

	// events
	private boolean blockInput = false;
	private int eventCount = 0;
	private boolean eventStart;
	private ArrayList<Rectangle> tb;
	private boolean eventFinish;
	private boolean eventDead;
	
	
	public Nivel1(GameStateManager gsm) {
		super(gsm);
		init();
	}
	
	public void init() {
		// backgrounds
		FondoEscenario = new Background("/Backgrounds/nivel1.jpg", 0);
		
		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/grasstileset.gif");
		tileMap.loadMap("/Maps/nivel1d.map");
		tileMap.setPosition(140, 0);
		tileMap.setBounds(
			tileMap.getWidth() - 1 * tileMap.getTileSize(),
			tileMap.getHeight() - 2 * tileMap.getTileSize(),
			0, 0
		);
		tileMap.setTween(1);
		
		Monedas=new  ArrayList<Moneda>();
		LlenarMonedas();
		try {
			hageonText = ImageIO.read(
				getClass().getResourceAsStream("/HUD/HageonTemple.gif")
			);
			title = new Title(hageonText.getSubimage(0, 0, 178, 20));
			title.sety(60);
			subtitle = new Title(hageonText.getSubimage(0, 20, 82, 13));
			subtitle.sety(85);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		// start event
				eventStart = true;
				tb = new ArrayList<Rectangle>();
				eventStart();
				
				// sfx
				JukeBox.load("/SFX/teleport.mp3", "teleport");
				JukeBox.load("/SFX/explode.mp3", "explode");
				JukeBox.load("/SFX/enemyhit.mp3", "enemyhit");
				
				// music
				JukeBox.load("/Music/level1.mp3", "level1");
				JukeBox.loop("level1", 600, JukeBox.getFrames("level1") - 2200);
	}
	
	private void LlenarMonedas() {
		Monedas.clear();
		/*Tengu t = new Tengu(tileMap, player, enemies);
		t.setPosition(1300, 100);
		enemies.add(t);
		t = new Tengu(tileMap, player, enemies);
		t.setPosition(1330, 100);
		enemies.add(t);
		t = new Tengu(tileMap, player, enemies);
		t.setPosition(1360, 100);
		enemies.add(t);*/
		Moneda m;
		Gazer g;
		
		m = new Moneda(tileMap,80,100);
		m.setPosition(80, 100);
		Monedas.add(m);
		
		m = new Moneda(tileMap,180,100);
		m.setPosition(180, 100);
		Monedas.add(m);
	}
	
	public void update() {
		// play events
				if(eventStart) eventStart();
				if(eventDead) eventDead();
				if(eventFinish) eventFinish();
				
				
				// move title and subtitle
				if(title != null) {
					title.update();
					if(title.shouldRemove()) title = null;
				}
				if(subtitle != null) {
					subtitle.update();
					if(subtitle.shouldRemove()) subtitle = null;
				}
				
				tileMap.update();
				tileMap.fixBounds();
				for(int i = 0; i < Monedas.size(); i++) {
					Moneda m = Monedas.get(i);
					m.update();
				}
				
	}
	
	public void draw(Graphics2D g) {
		FondoEscenario.draw(g);
		
		// draw tilemap
		tileMap.draw(g);
		
		// draw enemies
		for(int i = 0; i < Monedas.size(); i++) {
			Monedas.get(i).draw(g);
		}
		// draw hud
				//hud.draw(g);
				
				// draw title
				if(title != null) title.draw(g);
				if(subtitle != null) subtitle.draw(g);
				
				// draw transition boxes
				g.setColor(java.awt.Color.BLACK);
				for(int i = 0; i < tb.size(); i++) {
					g.fill(tb.get(i));
				}
	}
	
	public void handleInput() {
		
	}
	
	// level started
		private void eventStart() {
			eventCount++;
			if(eventCount == 1) {
				tb.clear();
				tb.add(new Rectangle(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT / 2));
				tb.add(new Rectangle(0, 0, GamePanel.WIDTH / 2, GamePanel.HEIGHT));
				tb.add(new Rectangle(0, GamePanel.HEIGHT / 2, GamePanel.WIDTH, GamePanel.HEIGHT / 2));
				tb.add(new Rectangle(GamePanel.WIDTH / 2, 0, GamePanel.WIDTH / 2, GamePanel.HEIGHT));
			}
			if(eventCount > 1 && eventCount < 60) {
				tb.get(0).height -= 4;
				tb.get(1).width -= 6;
				tb.get(2).y += 4;
				tb.get(3).x += 6;
			}
			if(eventCount == 30) title.begin();
			if(eventCount == 60) {
				eventStart = blockInput = false;
				eventCount = 0;
				subtitle.begin();
				tb.clear();
			}
		}
		
		// player has died
		private void eventDead() {
			eventCount++;
			if(eventCount == 1) {
				
			}
			if(eventCount == 60) {
				tb.clear();
				tb.add(new Rectangle(
					GamePanel.WIDTH / 2, GamePanel.HEIGHT / 2, 0, 0));
			}
			else if(eventCount > 60) {
				tb.get(0).x -= 6;
				tb.get(0).y -= 4;
				tb.get(0).width += 12;
				tb.get(0).height += 8;
			}
			if(eventCount >= 120) {

					eventDead = blockInput = false;
					eventCount = 0;
		
			}
		}
		
		// finished level
		private void eventFinish() {
			eventCount++;
			if(eventCount == 1) {
				JukeBox.play("teleport");
			
			}
			else if(eventCount == 120) {
				tb.clear();
				tb.add(new Rectangle(
					GamePanel.WIDTH / 2, GamePanel.HEIGHT / 2, 0, 0));
			}
			else if(eventCount > 120) {
				tb.get(0).x -= 6;
				tb.get(0).y -= 4;
				tb.get(0).width += 12;
				tb.get(0).height += 8;
				JukeBox.stop("teleport");
			}
			if(eventCount == 180) {
				
				gsm.setState(GameStateManager.LEVEL1BSTATE);
			}
			
		}
		
		
}
