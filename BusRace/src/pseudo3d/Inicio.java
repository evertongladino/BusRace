package pseudo3d;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

public class Inicio {

	private Renderizador renderizador;
	
	public Inicio() {
        JFrame janela = new JFrame("Projeto");
        janela.setBounds(50, 100, 800, 800);
        janela.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        BorderLayout layout = new BorderLayout();
        Container caixa = janela.getContentPane();
        caixa.setLayout(layout);

        // Cria um objeto GLCapabilities para especificar o número de bits
        // por pixel para RGBA
        GLProfile glp = GLProfile.getDefault();
        GLCapabilities caps = new GLCapabilities(glp);
        caps.setRedBits(8);
        caps.setBlueBits(8);
        caps.setGreenBits(8);
        caps.setAlphaBits(8);

        // Cria o objeto que irá gerenciar os eventos
        Renderizador renderizador = new Renderizador();

        // Cria um canvas, adiciona na janela, e especifica o objeto "ouvinte"
        // para os eventos Gl, de mouse e teclado
        GLCanvas canvas = new GLCanvas(caps);
        janela.add(canvas, BorderLayout.CENTER);
        janela.setSize(800, 600);
        canvas.addGLEventListener(renderizador);
        canvas.addMouseListener(renderizador);
        canvas.addKeyListener(renderizador);
        janela.setVisible(true);
        canvas.requestFocus();
      
        FPSAnimator animator = new FPSAnimator(canvas, 120);
        animator.start();
	}
	
	public static void main(String[] args) {
		Inicio inicio = new Inicio();
	}
}
