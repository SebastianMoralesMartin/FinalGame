package mx.itesm.seb;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Juego extends Game {

	//Dimensiones del mundo
	public static final float ANCHO = 1280;
	public static final float ALTO = 720;

	@Override
	public void create () {
		setScreen(new PantallaMenu(this));
	}

}
