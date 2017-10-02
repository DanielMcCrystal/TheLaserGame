package board;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import engine.MiscMethods;

/**
 * Represents the theme for a single team
 * Each Theme has two TeamThemes
 * @author Daniel McCrystal
 */
public class TeamTheme {
	public String name;
	public BufferedImage laserTexture, verticalBeamTexture,
	horizontalBeamTexture, topBeamTexture, rightBeamTexture,
	bottomBeamTexture, leftBeamTexture, mirrorTexture, splitterTexture,
	portalTexture, magnifierTexture, healthOutlineTexture,
	healthBarTexture, victoryScreen;
	
	public TeamTheme(String name) {
		this.name = name;
	}
	
	public void setLaserTexture(String path) {
		System.out.println("Searching path: " + path);
		try {
			laserTexture = ImageIO.read(new File(path));
			System.out
					.println("--Loaded LASER texture for " + name + " team--");
		} catch (IOException e) {
			laserTexture = MiscMethods.missingTexture;
			System.out.println("==Couldn't find LASER texture for " + name
					+ " team==");
		}
	}
	
	public void setBeamTexture(String path) {
		System.out.println("Searching path: " + path);
		try {
			verticalBeamTexture = ImageIO.read(new File(path));
			System.out.println("--Loaded BEAM texture for " + name + " team--");
		} catch (IOException e) {
			verticalBeamTexture = MiscMethods.missingBeam;
			System.out.println("==Couldn't find BEAM texture for " + name
					+ " team==");
		}
		
		AffineTransform transformer = new AffineTransform();
		transformer.rotate(Math.PI / 2, verticalBeamTexture.getWidth() / 2,
				verticalBeamTexture.getHeight() / 2);
		AffineTransformOp op = new AffineTransformOp(transformer,
				AffineTransformOp.TYPE_BILINEAR);
		horizontalBeamTexture = op.filter(verticalBeamTexture, null);

		topBeamTexture = verticalBeamTexture.getSubimage(0, 0, 100, 50);
		bottomBeamTexture = verticalBeamTexture.getSubimage(0, 50, 100, 50);
		leftBeamTexture = horizontalBeamTexture.getSubimage(0, 0, 50, 100);
		rightBeamTexture = horizontalBeamTexture.getSubimage(50, 0, 50, 100);
	}

	public void setMirrorTexture(String path) {
		System.out.println("Searching path: " + path);
		try {
			mirrorTexture = ImageIO.read(new File(path));
			System.out.println("--Loaded MIRROR texture for " + name
					+ " team--");
		} catch (IOException e) {
			mirrorTexture = MiscMethods.missingTexture;
			System.out.println("==Couldn't find MIRROR texture for " + name
					+ " team==");
		}
	}

	public void setSplitterTexture(String path) {
		System.out.println("Searching path: " + path);
		try {
			splitterTexture = ImageIO.read(new File(path));
			System.out.println("--Loaded SPLITTER texture for " + name
					+ " team--");
		} catch (IOException e) {
			splitterTexture = MiscMethods.missingTexture;
			System.out.println("==Couldn't find SPLITTER texture for " + name
					+ " team==");
		}
	}

	public void setPortalTexture(String path) {
		System.out.println("Searching path: " + path);
		try {
			portalTexture = ImageIO.read(new File(path));
			System.out.println("--Loaded PORTAL texture for " + name
					+ " team--");
		} catch (IOException e) {
			portalTexture = MiscMethods.missingTexture;
			System.out.println("==Couldn't find PORTAL texture for " + name
					+ " team==");
		}
	}

	public void setMagnifierTexture(String path) {
		System.out.println("Searching path: " + path);
		try {
			magnifierTexture = ImageIO.read(new File(path));
			System.out.println("--Loaded MAGNIFIER texture for " + name
					+ " team--");
		} catch (IOException e) {
			magnifierTexture = MiscMethods.missingTexture;
			System.out.println("==Couldn't find MAGNIFIER texture for " + name
					+ " team==");
		}
	}

	public void setHealthTexture(String outline, String bar) {
		System.out.println("Searching path: " + outline);
		try {
			healthOutlineTexture = ImageIO.read(new File(outline));
			System.out.println("--Loaded HEALTH OUTLINE texture for " + name
					+ " team--");
		} catch (IOException e) {
			System.out.println("==Couldn't find HEALTH OUTLINE texture for "
					+ name + " team==");
		}

		System.out.println("Searching path: " + bar);
		try {
			healthBarTexture = ImageIO.read(new File(bar));
			System.out.println("--Loaded HEALTH BAR texture for " + name
					+ " team--");
		} catch (IOException e) {
			System.out.println("==Couldn't find HEALTH BAR texture for " + name
					+ " team==");
		}
	}
}
