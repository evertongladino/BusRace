package pseudo3d;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureData;	
import com.jogamp.opengl.util.texture.TextureIO;
import com.jogamp.opengl.GLProfile;

public class Textura {
	private TextureData td;
	private BufferedImage image;
	private InputStream stream;
	
	public static Texture pista, tras, ladoDireito, ladoEsquerdo, frente, favela, grama, ladoEsquerdoObs, ladoDireitoObs;
	public static int width, height;
	
	public void loadImage(String fileName){
		// Tenta carregar o arquivo		
		image = null;
		try {
			image = ImageIO.read(new File("src/pseudo3d/" + fileName));
			// Obtém largura e altura
			width  = image.getWidth();
			height = image.getHeight();
		}
		catch (IOException e) {
			JOptionPane.showMessageDialog(null,"Erro na leitura do arquivo "+fileName);
			System.out.println(e.getStackTrace());
		}
	}
		public void loadImagePista(String fileName){
			loadImage(fileName);

			//Carrega a textura		
			try {
				stream = getClass().getResourceAsStream(fileName);
				td = TextureIO.newTextureData(GLProfile.getDefault(), stream, false, "jpg");
				pista = TextureIO.newTexture(td);
			}
			catch (IOException exc) {
				exc.printStackTrace();
				System.exit(1);
			}
		}
		
		public void loadImageTras(String fileName){
			loadImage(fileName);

			//Carrega a textura		
			try {
				stream = getClass().getResourceAsStream(fileName);
				td = TextureIO.newTextureData(GLProfile.getDefault(), stream, false, "png");
				tras = TextureIO.newTexture(td);
			}
			catch (IOException exc) {
				exc.printStackTrace();
				System.exit(1);
			}
		}
		
		public void loadImageLadoDireito(String fileName){
			loadImage(fileName);

			//Carrega a textura		
			try {
				stream = getClass().getResourceAsStream(fileName);
				td = TextureIO.newTextureData(GLProfile.getDefault(), stream, false, "png");
				ladoDireito = TextureIO.newTexture(td);
			}
			catch (IOException exc) {
				exc.printStackTrace();
				System.exit(1);
			}
		}
		
		public void loadImageLadoEsquerdo(String fileName){
			loadImage(fileName);

			//Carrega a textura		
			try {
				stream = getClass().getResourceAsStream(fileName);
				td = TextureIO.newTextureData(GLProfile.getDefault(), stream, false, "png");
				ladoEsquerdo = TextureIO.newTexture(td);
			}
			catch (IOException exc) {
				exc.printStackTrace();
				System.exit(1);
			}
		}
		
		public void loadImageFrenteObs(String fileName){
			loadImage(fileName);

			//Carrega a textura		
			try {
				stream = getClass().getResourceAsStream(fileName);
				td = TextureIO.newTextureData(GLProfile.getDefault(), stream, false, "png");
				frente = TextureIO.newTexture(td);
			}
			catch (IOException exc) {
				exc.printStackTrace();
				System.exit(1);
			}
		}
		
		public void loadImageLadoEsquerdoObs(String fileName){
			loadImage(fileName);

			//Carrega a textura		
			try {
				stream = getClass().getResourceAsStream(fileName);
				td = TextureIO.newTextureData(GLProfile.getDefault(), stream, false, "png");
				ladoEsquerdoObs = TextureIO.newTexture(td);
			}
			catch (IOException exc) {
				exc.printStackTrace();
				System.exit(1);
			}
		}
		
		public void loadImageLadoDireitoObs(String fileName){
			loadImage(fileName);

			//Carrega a textura		
			try {
				stream = getClass().getResourceAsStream(fileName);
				td = TextureIO.newTextureData(GLProfile.getDefault(), stream, false, "png");
				ladoDireitoObs = TextureIO.newTexture(td);
			}
			catch (IOException exc) {
				exc.printStackTrace();
				System.exit(1);
			}
		}
		
		public void loadImageFavela(String fileName){
			loadImage(fileName);

			//Carrega a textura		
			try {
				stream = getClass().getResourceAsStream(fileName);
				td = TextureIO.newTextureData(GLProfile.getDefault(), stream, false, "jpg");
				favela = TextureIO.newTexture(td);
			}
			catch (IOException exc) {
				exc.printStackTrace();
				System.exit(1);
			}
		}
		
		public void loadImageGrama(String fileName){
			loadImage(fileName);

			//Carrega a textura		
			try {
				stream = getClass().getResourceAsStream(fileName);
				td = TextureIO.newTextureData(GLProfile.getDefault(), stream, false, "jpg");
				grama = TextureIO.newTexture(td);
			}
			catch (IOException exc) {
				exc.printStackTrace();
				System.exit(1);
			}
		}
	}
	
	
