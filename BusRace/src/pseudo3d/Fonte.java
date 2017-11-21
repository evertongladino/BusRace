package pseudo3d;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.FileInputStream;
import java.io.IOException;

public class Fonte {
	public Font carregarFonte(){
		try{
			return Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("src/pseudo3d/impact.ttf"));
		}catch(IOException | FontFormatException e){
			return new Font(null, Font.BOLD, 40);
		}
	}
}