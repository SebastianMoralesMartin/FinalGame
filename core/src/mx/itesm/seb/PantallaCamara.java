package mx.itesm.seb;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

class PantallaCamara implements Screen {
    private final Juego juego;

    private OrthographicCamera camera;
    private Viewport view;

    private SpriteBatch batch;

    //FONDO
    private Texture texturaFondo; //Referencia para la textura de fondo

    private float xTexturaFondoUno = 1;
    private float xTexturaFondoDos = 2;

    public PantallaCamara(Juego juego) {
        this.juego = juego;
    }
    // carga de todo
    @Override
    public void show() {
        configurarVista();
        cargarTexturas();
    }


    private void cargarTexturas() {
        texturaFondo = new Texture("bruh.png");
    }

    private void configurarVista(){
        camera = new OrthographicCamera();
        camera.position.set(Juego.ANCHO/2, Juego.ALTO/2, 0);
        camera.update();

        view = new StretchViewport(Juego.ANCHO, Juego.ALTO, camera);

        batch = new SpriteBatch();

    }

    @Override
    public void render(float delta) {
        actualizarCamara();
        borrarPantalla();

    }

    private void actualizarCamara() {
        camera.position.x += 2;
        camera.update();
        //Fondo
        if(camera.position.x - juego.ANCHO/2 > texturaFondo.getWidth()*xTexturaFondoUno){
            xTexturaFondoUno += 2;
        }
        if(camera.position.x - juego.ANCHO/2 > texturaFondo.getWidth()*xTexturaFondoDos) {
            xTexturaFondoDos += 2;
        }
    }

    private void borrarPantalla() {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //batch escala de acuerdo a la vista y la camara
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(texturaFondo, texturaFondo.getWidth()*xTexturaFondoUno - texturaFondo.getWidth(), 0);
        batch.draw(texturaFondo, texturaFondo.getWidth()*xTexturaFondoDos - texturaFondo.getWidth(), 0);

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        view.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        //dispose();
    }

    @Override
    public void dispose() {
        texturaFondo.dispose();
    }
}
