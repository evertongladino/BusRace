 
package pseudo3d;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.awt.TextRenderer;


public class Renderizador extends MouseAdapter implements GLEventListener,
        KeyListener {
    private GL2 gl;
    private GLU glu;
    private GLAutoDrawable glDrawable;
    private double angulo, aspecto;
    private float rotX, rotY, obsZ, obsY, obsX;
    private boolean luz;
    private double andarObstaculo;
    private double incremento = 0;
    private float movimentoXBus = 0.90f;
    private int pontuacao,pont = 0;
    private float lado = 0.46f;
    private float reverseLado;
    private double auxChao, auxPista;
    private FPSAnimator animator;

    
    Font fonte = new Fonte().carregarFonte().deriveFont(Font.PLAIN, 40);
    TextRenderer textRenderer = new TextRenderer(fonte);

    public void init(GLAutoDrawable drawable) {
        glDrawable = drawable;
        gl = drawable.getGL().getGL2();
        glu = new GLU();

        gl.glMatrixMode (GL2.GL_PROJECTION);
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glMatrixMode (GL2.GL_PROJECTION);
        glu.gluOrtho2D (0.0, 600.0, 0.0, 600.0);

		Textura textura = new Textura(); // objeto que controla a textura
		textura.loadImagePista("pista.jpg");
		textura.loadImageTras("tras.png");
		textura.loadImageLadoDireito("ladoDireito.png");
		textura.loadImageLadoEsquerdo("ladoEsquerdo.png");
		textura.loadImageFrenteObs("frenteObs.png");
		textura.loadImageLadoEsquerdoObs("ladoEsquerdoObs.png");
		textura.loadImageLadoDireitoObs("ladoDireitoObs.png");
		textura.loadImageFavela("fundo.jpg");
		textura.loadImageGrama("grama.jpg");

    }
    
    public void defineIluminacao(){
        if (luz) {
            gl.glEnable(GL2.GL_LIGHTING);
            gl.glMaterialfv(GL.GL_FRONT, GL2.GL_AMBIENT_AND_DIFFUSE, new float[] {1f,1f,1f}, 0 );
            gl.glEnable(GL2.GL_COLOR_MATERIAL);
        } else{
            gl.glDisable(GL2.GL_LIGHTING);
        }
    }
        
    private void update() {
        auxPista += incremento;
        auxChao += incremento;
        andarObstaculo += 0.4+incremento;
    }
    
    public Renderizador() {
        angulo = 50;
        aspecto = 1;
        rotX = 0;
        rotY = 0;
        obsZ = 0.2f;
    }
 
    public void display(GLAutoDrawable drawable) {
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        gl.glColor3f (0.0f, 0.0f, 0.0f);
        gl.glMatrixMode (GL2.GL_MODELVIEW);
        gl.glLoadIdentity();

        update();
        especificaParametrosVisualizacao();
        pista();
        renderizaOnibus();
        renderizaObstaculo();
        background();
        renderizaChao();
        defineIluminacao();
    }
    
    
    private void background() {
    	Textura.favela.enable(gl);
		Textura.favela.bind(gl);
    	gl.glPushMatrix();
    	gl.glScaled(2, 1, 1);
    	gl.glTranslated(0, 9.9, 0);
    		gl.glBegin(GL2.GL_QUADS);
    		gl.glColor3f(1f, 1f, 1f);
    		gl.glTexCoord2f(0.0f, 0.0f);gl.glVertex3f(-40f, -40f, -70f);
    		gl.glTexCoord2f(1.0f, 0.0f);gl.glVertex3f(40f, -40f, -70f);
    		gl.glTexCoord2f(1.0f, 1.0f);gl.glVertex3f(40f, 40f, -70f);
    		gl.glTexCoord2f(0.0f, 1.0f);gl.glVertex3f(-40f, 40f, -70f);
    		gl.glEnd();
    	gl.glPopMatrix();
    	Textura.favela.disable(gl);
    }
    
    private void renderizaChao() {
    	chao();
    	if(auxChao >200) {
    		chao();
    		auxChao = 0;
    	}
    }
    
    private void chao() {
        Textura.grama.enable(gl);
		Textura.grama.bind(gl);
    	gl.glPushMatrix();
        gl.glTranslated(0, -0.2, auxChao);
        gl.glScaled(1, 1, 1);
        gl.glColor3f(1.0f, 1.0f, 1.0f);
        gl.glBegin(GL2.GL_QUADS);
                gl.glNormal3f(0.0f, -1.0f, 0.0f);
                gl.glTexCoord2f(1.0f, 1.0f);
                gl.glVertex3f(-6.0f, -1.0f, -6.0f);
                gl.glTexCoord2f(0.0f, 1.0f);
                gl.glVertex3f(-1.0f, -1.0f, -6.0f);
                gl.glTexCoord2f(0.0f, 0.0f);
                gl.glVertex3f(-1.0f, -1.0f, 6.0f);
                gl.glTexCoord2f(1.0f, 0.0f);
                gl.glVertex3f(-6.0f, -1.0f, 6.0f);
        gl.glEnd();
        gl.glBegin(GL2.GL_QUADS);
	        gl.glNormal3f(0.0f, -1.0f, 0.0f);
	        gl.glTexCoord2f(1.0f, 1.0f);
	        gl.glVertex3f(1.0f, -1.0f, -6.0f);
	        gl.glTexCoord2f(0.0f, 1.0f);
	        gl.glVertex3f(6.0f, -1.0f, -6.0f);
	        gl.glTexCoord2f(0.0f, 0.0f);
	        gl.glVertex3f(6.0f, -1.0f, 6.0f);
	        gl.glTexCoord2f(1.0f, 0.0f);
	        gl.glVertex3f(1.0f, -1.0f, 6.0f);
        gl.glEnd();
    	
        	for(int i = 0; i<50; i++) {
            	gl.glTranslated(0, 0, -6);
                gl.glScaled(1, 1, 1);
                gl.glColor3f(1.0f, 1.0f, 1.0f);
                gl.glBegin(GL2.GL_QUADS);
                        gl.glNormal3f(0.0f, -1.0f, 0.0f);
                        gl.glTexCoord2f(1.0f, 1.0f);
                        gl.glVertex3f(-6.0f, -1.0f, -6.0f);
                        gl.glTexCoord2f(0.0f, 1.0f);
                        gl.glVertex3f(-1.0f, -1.0f, -6.0f);
                        gl.glTexCoord2f(0.0f, 0.0f);
                        gl.glVertex3f(-1.0f, -1.0f, 6.0f);
                        gl.glTexCoord2f(1.0f, 0.0f);
                        gl.glVertex3f(-6.0f, -1.0f, 6.0f);
                gl.glEnd();
                gl.glBegin(GL2.GL_QUADS);
        	        gl.glNormal3f(0.0f, -1.0f, 0.0f);
        	        gl.glTexCoord2f(1.0f, 1.0f);
        	        gl.glVertex3f(1.0f, -1.0f, -6.0f);
        	        gl.glTexCoord2f(0.0f, 1.0f);
        	        gl.glVertex3f(6.0f, -1.0f, -6.0f);
        	        gl.glTexCoord2f(0.0f, 0.0f);
        	        gl.glVertex3f(6.0f, -1.0f, 6.0f);
        	        gl.glTexCoord2f(1.0f, 0.0f);
        	        gl.glVertex3f(1.0f, -1.0f, 6.0f);
                gl.glEnd();

        	}	
        	gl.glPopMatrix();
        	Textura.grama.disable(gl);
    	}
    
    
    private void obstaculo(float ladox) {  

        	Textura.frente.enable(gl);
    		Textura.frente.bind(gl);
            gl.glPushMatrix();
            gl.glTranslated(0, 0, andarObstaculo);
            gl.glScaled(2, 2, 2);
            gl.glTranslatef(ladox, 0f, -30f);
            gl.glBegin(GL2.GL_QUADS); //Tras do onibus
            gl.glColor3f(1f, 1f, 1f);
            gl.glNormal3f(0.0f, -1.0f, 0.0f);
            	gl.glTexCoord2f(0.0f, 0.0f);gl.glVertex3f(-0.35f, -0.5f, 2.2f);
                gl.glTexCoord2f(1.0f, 0.0f);gl.glVertex3f(-0.08f, -0.5f, 2.2f);
                gl.glTexCoord2f(1.0f, 1.0f);gl.glVertex3f(-0.08f, -0.25f, 2.2f);
                gl.glTexCoord2f(0.0f, 1.0f);gl.glVertex3f(-0.35f, -0.25f, 2.2f);
            gl.glEnd();
            Textura.frente.disable(gl);

        	Textura.ladoEsquerdoObs.enable(gl);
    		Textura.ladoEsquerdoObs.bind(gl);
            gl.glBegin(GL2.GL_QUADS); //lado esquerdo
            gl.glColor3f(1f, 1f, 1f);
            gl.glNormal3f(0.0f, -1.0f, 0.0f);
        		gl.glTexCoord2f(0.0f, 1.0f);gl.glVertex3f(-0.08f, -0.25f, 1.0f);
            	gl.glTexCoord2f(1.0f, 1.0f);gl.glVertex3f(-0.08f, -0.25f, 2.2f);
            	gl.glTexCoord2f(1.0f, 0.0f);gl.glVertex3f(-0.08f, -0.5f, 2.2f);
            	gl.glTexCoord2f(0.0f, 0.0f);gl.glVertex3f(-0.08f, -0.5f, 1.0f);
            gl.glEnd();
            Textura.ladoEsquerdoObs.disable(gl);

            Textura.ladoDireitoObs.enable(gl);
    		Textura.ladoDireitoObs.bind(gl);
            gl.glBegin(GL2.GL_QUADS); //lado direito
            gl.glColor3f(1f, 1f, 1f);
            gl.glNormal3f(0.0f, -1.0f, 0.0f);
            gl.glTexCoord2f(0.0f, 1.0f);gl.glVertex3f(-0.35f, -0.25f, 1.0f);
            gl.glTexCoord2f(1.0f, 1.0f);gl.glVertex3f(-0.35f, -0.25f, 2.2f);
            gl.glTexCoord2f(1.0f, 0.0f);gl.glVertex3f(-0.35f, -0.5f, 2.2f);
            gl.glTexCoord2f(0.0f, 0.0f);gl.glVertex3f(-0.35f, -0.5f, 1.0f);
            gl.glEnd();
            Textura.ladoDireitoObs.disable(gl);

            gl.glBegin(GL2.GL_QUADS); //teto
            	gl.glColor3f(0f, 0.39f, 0.79f);
            	gl.glVertex3f(-0.35f, -0.25f, 1.0f);
            	gl.glVertex3f(-0.08f, -0.25f, 1.0f);
            	gl.glVertex3f(-0.08f, -0.25f, 2.2f);
            	gl.glVertex3f(-0.35f, -0.25f, 2.2f);
            gl.glEnd();
         gl.glPopMatrix();
    }
    
    private float renderizaObstaculo() {
        obstaculo(lado);
        if(andarObstaculo >61) {
            lado = random();
            obstaculo(lado);
            andarObstaculo = 0;
            pont ++;
            textRenderer.beginRendering(200, 300);
			textRenderer.setSmoothing(true);
            textRenderer.draw("Ultrapassagens: ", 200, 300);
            textRenderer.endRendering();
        }
        
        if(reverseLado == movimentoXBus && andarObstaculo >= 51.5 && andarObstaculo <=57) {
            andarObstaculo = 0;
            incremento = 0;
            JOptionPane.showMessageDialog(null ,"Game Over" + "\n" + "Pontuação: " + (pont*pontuacao));
            System.out.println("Pontuação: " + (pont*pontuacao));
            pontuacao = 0;
            pont = 0;
            animator.stop();
        }

        return lado;
    }
    
    private float random() {
        int vardir = (int) (Math.random()*100);
        if(vardir > 50) {
            lado = 0.46f;
            reverseLado = -0.02f;
        } else {
            lado = -0.02f;
            reverseLado = 0.90f;
        }
        return lado;
    }
    
    private void renderizaOnibus() {
    	Textura.tras.enable(gl);
		Textura.tras.bind(gl);
        gl.glPushMatrix();
        glu.gluLookAt(0, 0, 0f, 0, 0, 0.5f, 0, 1, 0.5f);
        gl.glTranslatef(movimentoXBus, 0, 0f);
        gl.glScaled(2, 2, 2);
        gl.glBegin(GL2.GL_QUADS); //Tras do onibus
        gl.glColor3f(1f, 1f, 1f);
        gl.glNormal3f(0.0f, -1.0f, 0.0f);
        	gl.glTexCoord2f(0.0f, 0.0f);gl.glVertex3f(-0.35f, -0.5f, 1.0f);
            gl.glTexCoord2f(1.0f, 0.0f);gl.glVertex3f(-0.08f, -0.5f, 1.0f);
            gl.glTexCoord2f(1.0f, 1.0f);gl.glVertex3f(-0.08f, -0.25f, 1.0f);
            gl.glTexCoord2f(0.0f, 1.0f);gl.glVertex3f(-0.35f, -0.25f, 1.0f);

        gl.glEnd();
        Textura.tras.disable(gl);

    	Textura.ladoEsquerdo.enable(gl);
		Textura.ladoEsquerdo.bind(gl);
        gl.glBegin(GL2.GL_QUADS); //lado esquerdo
        gl.glColor3f(1f, 1f, 1f);
        gl.glNormal3f(0.0f, -1.0f, 0.0f);
    		gl.glTexCoord2f(0.0f, 1.0f);gl.glVertex3f(-0.08f, -0.25f, 1.0f);
        	gl.glTexCoord2f(1.0f, 1.0f);gl.glVertex3f(-0.08f, -0.25f, 2.2f);
        	gl.glTexCoord2f(1.0f, 0.0f);gl.glVertex3f(-0.08f, -0.5f, 2.2f);
        	gl.glTexCoord2f(0.0f, 0.0f);gl.glVertex3f(-0.08f, -0.5f, 1.0f);
        gl.glEnd();
        Textura.ladoEsquerdo.disable(gl);

        Textura.ladoDireito.enable(gl);
		Textura.ladoDireito.bind(gl);
        gl.glBegin(GL2.GL_QUADS); //lado direito
        gl.glColor3f(1f, 1f, 1f);
        gl.glNormal3f(0.0f, -1.0f, 0.0f);
        gl.glTexCoord2f(0.0f, 1.0f);gl.glVertex3f(-0.35f, -0.25f, 1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);gl.glVertex3f(-0.35f, -0.25f, 2.2f);
        gl.glTexCoord2f(1.0f, 0.0f);gl.glVertex3f(-0.35f, -0.5f, 2.2f);
        gl.glTexCoord2f(0.0f, 0.0f);gl.glVertex3f(-0.35f, -0.5f, 1.0f);
        gl.glEnd();
        Textura.ladoDireito.disable(gl);

        gl.glBegin(GL2.GL_QUADS);
        	gl.glColor3f(0.85f, 0.70f, 0.09f);
        	gl.glVertex3f(-0.35f, -0.25f, 1.0f);
        	gl.glVertex3f(-0.08f, -0.25f, 1.0f);
        	gl.glVertex3f(-0.08f, -0.25f, 2.2f);
        	gl.glVertex3f(-0.35f, -0.25f, 2.2f);
        gl.glEnd();
     gl.glPopMatrix();

    }
    
    private void pista() {
    	renderizaPista();
    	if(auxPista >130) {
    		renderizaPista();
    		auxPista = 0;
    	}
    }

    private void renderizaPista() {
    	
    	Textura.pista.enable(gl);
		Textura.pista.bind(gl);
		
        gl.glPushMatrix();
        gl.glTranslated(0, 0, auxPista);
        gl.glColor3f(1.0f, 1.0f, 1.0f);
        gl.glBegin(GL2.GL_QUADS);
                gl.glNormal3f(0.0f, -1.0f, 0.0f);
                gl.glTexCoord2f(1.0f, 1.0f);
                gl.glVertex3f(-1.0f, -1.0f, -1.0f);
                gl.glTexCoord2f(0.0f, 1.0f);
                gl.glVertex3f(1.0f, -1.0f, -1.0f);
                gl.glTexCoord2f(0.0f, 0.0f);
                gl.glVertex3f(1.0f, -1.0f, 1.0f);
                gl.glTexCoord2f(1.0f, 0.0f);
                gl.glVertex3f(-1.0f, -1.0f, 1.0f);
        gl.glEnd();
        
        //Loop da pista
        for(int i = 0; i< 100; i++){
            gl.glTranslatef(0, 0, -2);
            gl.glBegin(GL2.GL_QUADS);
                gl.glNormal3f(0.0f, -1.0f, 0.0f);
                gl.glTexCoord2f(1.0f, 1.0f);
                gl.glVertex3f(-1.0f, -1.0f, -1.0f);
                gl.glTexCoord2f(0.0f, 1.0f);
                gl.glVertex3f(1.0f, -1.0f, -1.0f);
                gl.glTexCoord2f(0.0f, 0.0f);
                gl.glVertex3f(1.0f, -1.0f, 1.0f);
                gl.glTexCoord2f(1.0f, 0.0f);
                gl.glVertex3f(-1.0f, -1.0f, 1.0f);
            gl.glEnd();
        }
        
        gl.glEnd();
        gl.glPopMatrix();
	    Textura.pista.disable(gl);
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width,
            int height) {
        gl.glViewport(0, 0, width, height);
        aspecto = (float) width / (float) height;
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged,
            boolean deviceChanged) {
    }

    public void posicionaObservador() {
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glTranslatef(obsX, obsY, -obsZ);
        gl.glRotatef(rotX, 1, 0, 0);
        gl.glRotatef(rotY, 0, 1, 0);
    }

    public void especificaParametrosVisualizacao() {
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(angulo, aspecto, 0.2, 500);
        posicionaObservador();
    }

    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) // Zoom in
            if (obsY == 0) {
            	obsY = 0.4f;
        		obsZ = -3.8f;
            }
        if (e.getButton() == MouseEvent.BUTTON3) // Zoom out
            if (obsY == 0.4f) {
            	obsY = 0;
        		obsZ = 0.2f;
        		obsX = 0;
            }
        glDrawable.display();
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
        
        case KeyEvent.VK_LEFT:
            movimentoXBus = 0.90f;
            if(obsY== 0) {
            	obsX = 0;
            } else {
            	obsX = 0.16f;
            }
            break;
        case KeyEvent.VK_RIGHT:
            movimentoXBus = -0.02f;
            if(obsY== 0) {
            	obsX = 0;
            } else {
            	obsX = -0.16f;
            }
            break;
        case KeyEvent.VK_UP:
            incremento += 0.04;
            pontuacao++;
            break;
        case KeyEvent.VK_DOWN:
            incremento -= 0.04;
            pontuacao--;
            break;
        case KeyEvent.VK_B:
        	luz = false;
            break;
        case KeyEvent.VK_N:
        	luz= true;
            break;
        case KeyEvent.VK_ESCAPE:
            System.exit(0);
            break;
        }
        
        glDrawable.display();
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void dispose(GLAutoDrawable arg0) {
        // TODO Auto-generated method stub

    }
}