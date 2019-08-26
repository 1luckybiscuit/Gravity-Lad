import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

public class ObjectRunner {
	Jumper jump;
	ArrayList<Block> topBlockList = new ArrayList();
	ArrayList<Block> botmBlockList = new ArrayList();
	long timer;
	Random generator = new Random();
	int randInterval = generator.nextInt(5)*100;
	int interval = 130;
	ObjectRunner(Jumper jump) {
		this.jump = jump;
	}
	void manageBlocks() {
		if(System.currentTimeMillis() - timer >= interval) {
			addTopBlock(new Block(GravityGuy.WIDTH, 100, 40, 40));
			addBotmBlock(new Block(GravityGuy.WIDTH, 400, 40, 40));
			timer = System.currentTimeMillis();
		}
	}
	void purgeObjects() {
		for(int i = 0;i < topBlockList.size();i++) {
			if(topBlockList.get(i).active == false) {
				topBlockList.remove(i);
			}
		}
		for(int i = 0;i < botmBlockList.size();i++) {
			if(botmBlockList.get(i).active == false) {
				botmBlockList.remove(i);
			}
		}
	}
	void draw(Graphics g) {
		jump.draw(g);
		for(int i = 0;i < topBlockList.size();i++) {
			topBlockList.get(i).draw(g);
		}
		for(int i = 0;i < botmBlockList.size();i++) {
			botmBlockList.get(i).draw(g);
		}
		
	}
	void update() {
		for(int i = 0;i < topBlockList.size();i++) {
			topBlockList.get(i).update();
		}
		for(int i = 0;i < botmBlockList.size();i++) {
			botmBlockList.get(i).update();
		}
		jump.colBox.y += jump.speed;
		collide();
		jump.update();
		manageBlocks();
		purgeObjects();
	}
	void addTopBlock(Block b) {
		topBlockList.add(b);
	}
	void addBotmBlock(Block b) {
		botmBlockList.add(b);
	}
	void collide() {
		for(Block i: topBlockList) {
			if(jump.colBox.intersects(i.colBox)) {
				if(jump.colBox.getMinY() < i.colBox.getMaxY()) {
					System.out.println("topCollision");
					jump.gravity = 0;
				}
				if(jump.colBox.getMaxX() < i.colBox.getMinX()) {
					System.out.println("horizontalCollision");
				}
			}
		}
		for(Block i: botmBlockList) {
			if(jump.colBox.intersects(i.colBox)) {
				if(jump.colBox.getMaxY() > i.colBox.getMinY()) {
					System.out.println("botCollision");
					jump.gravity = 0;
				}
				if(jump.colBox.getMaxX() < i.colBox.getMinX()) {
					System.out.println("horizontalCollision");
				}
			}
		}
	}
}
