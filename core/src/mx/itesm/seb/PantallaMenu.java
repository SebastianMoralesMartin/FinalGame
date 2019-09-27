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
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import javax.swing.ImageIcon;

class PantallaMenu implements Screen {
    private final Juego juego;

    private OrthographicCamera camera;
    private Viewport view;

    private SpriteBatch batch;

    //FONDO
    private Texture texturaFondo; //Referencia para la textura de fondo

    //Escena del menu
    private Stage escenaMenu;

    public PantallaMenu(Juego juego) {
        this.juego = juego;
    }
    // carga de todo
    @Override
    public void show() {
        configurarVista();
        cargarTexturas();
        crearMenu();
    }

    private void crearMenu() {
        escenaMenu = new Stage(view);

        //Boton de juego SpaceInvaders
        TextureRegionDrawable trdSpace = new TextureRegionDrawable(
                                                        new TextureRegion(
                                                        new Texture("UnpressBtn.png")));
        TextureRegionDrawable trdSpacePressed = new TextureRegionDrawable(
                new TextureRegion(
                        new Texture("PressBtn.png")));
        ImageButton btnSpace = new ImageButton(trdSpace, trdSpacePressed);
        btnSpace.setPosition(Juego.ANCHO/2 - btnSpace.getWidth()/2, 2*Juego.ALTO/3);

        //Evento
        btnSpace.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                //eventos que occuren despues de presionar el boton
                //juego.setScreen(new PantallaSpaceInvaders(juego));
                //juego.setScreen(new PantallaCamara(juego));
                juego.setScreen(new PantallaMario(juego));
            }
        });

        escenaMenu.addActor(btnSpace);

        Gdx.input.setInputProcessor(escenaMenu);
    }

    private void cargarTexturas() {
        texturaFondo = new Texture("fondo.jpg");
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
        borrarPantalla();
    }

    private void borrarPantalla() {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //batch escala de acuerdo a la vista y la camara
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(texturaFondo, 0, 0);
        batch.end();

        escenaMenu.draw();
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
