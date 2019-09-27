package mx.itesm.seb;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

class PantallaSpaceInvaders implements Screen {
    private final Juego juego;

    private OrthographicCamera camera;
    private Viewport view;

    private SpriteBatch batch;

    //FONDO
    private Texture texturaFondo; //Referencia para la textura de fondo

    //Escena del HUD
    private Stage escenaHUD;

    //Enemigos
    private Array<Enemigo> arrEnemigos;
    private int DX = +7;
    private int pasos = 0;
    private float timerPaso = 0f;
    private float MAX_PASO = 1f;

    //Nave
    private Nave nave;
    private Movimiento estadoNave = Movimiento.QUIETO;  //Estado inicial de la nave

    //bala
    private Bala bala;
    private Texture texturaBala;

    public PantallaSpaceInvaders(Juego juego) {
        this.juego = juego;
    }
    // carga de todo
    @Override
    public void show() {
        configurarVista();
        cargarTexturas();
        crearHUD();
        crearEnemigos();
        crearNave();
    }

    private void crearNave() {
        Texture texturaNave = new Texture("space/nave.png");
        nave = new Nave(texturaNave, Juego.ANCHO/2, 40);
    }

    private void crearEnemigos() {
        arrEnemigos = new Array<>(11 * 5);
        Texture texturaEnemigoArriba = new Texture("space/enemigoArriba.png");
        Texture texturaEnemigoAbajo = new Texture("space/enemigoAbajo.png");
        for(int renglon = 0; renglon < 5; renglon++){
            for(int columna=0;columna <11;columna++){
                Enemigo enemigo = new Enemigo(texturaEnemigoArriba, texturaEnemigoAbajo,
                        40 + columna * 100, 360 + renglon * 60);
                arrEnemigos.add(enemigo);
            }
        }
    }

    private void crearHUD() {
        escenaHUD = new Stage(view);

        //Boton de juego SpaceInvaders
        TextureRegionDrawable trdBack = new TextureRegionDrawable(
                new TextureRegion(
                        new Texture("back.png")));
        TextureRegionDrawable trdBackPressed = new TextureRegionDrawable(
                new TextureRegion(
                        new Texture("backPressed.png")));
        ImageButton btnBack = new ImageButton(trdBack, trdBackPressed);
        btnBack.setPosition(0,
                Juego.ALTO - btnBack.getHeight());

        //Evento
        btnBack.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                //eventos que occuren despues de presionar el boton
                juego.setScreen(new PantallaMenu(juego));
            }
        });

        escenaHUD.addActor(btnBack);

        TextureRegionDrawable trdDer = new TextureRegionDrawable(
                new TextureRegion(
                        new Texture("space/flechaDerecha.png")));
        ImageButton btnDer = new ImageButton(trdDer);
        btnDer.setPosition(btnDer.getWidth() + 10, 0);
        btnDer.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                estadoNave = Movimiento.DERECHA;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                estadoNave = Movimiento.QUIETO;
            }
        });

        escenaHUD.addActor(btnDer);
        //Boton Izquierda
        TextureRegionDrawable trdIzq = new TextureRegionDrawable(
                new TextureRegion(
                        new Texture("space/flechaIzquierda.png")));
        ImageButton btnIzq = new ImageButton(trdIzq);
        btnIzq.setPosition(0, 0);
        btnIzq.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                estadoNave = Movimiento.IZQUIERDA;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                estadoNave = Movimiento.QUIETO;
            }
        });

        escenaHUD.addActor(btnIzq);
        //boton disparo
        TextureRegionDrawable trdDisparo = new TextureRegionDrawable(
                new TextureRegion(new Texture("shot.png")));
        ImageButton btnDisparo = new ImageButton(trdDisparo);
        btnDisparo.setPosition(Juego.ANCHO - btnDisparo.getWidth() - 50, 50);
        btnDisparo.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                //Dispara!!!!
                if(bala == null){
                    bala = new Bala (texturaBala, nave.getSprite().getX() + nave.getSprite().getWidth()/2, nave.getSprite().getHeight() + nave.getSprite().getHeight());
                }
                return true;
            }
        });

        escenaHUD.addActor(btnDisparo);

        Gdx.input.setInputProcessor(escenaHUD);
    }

    private void cargarTexturas() {
        texturaFondo = new Texture("fondoSpace.jpg");
        texturaBala = new Texture("space/bala.png");
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
        //Dibujar enemigos
        actualizar(delta);

        //Borrar pantalla
        borrarPantalla();

    }

    private void actualizar(float delta) {
        actualizarEnemigos(delta);
        actualizarNave();
        actualizarBala(delta);
        verificarChoques();
        verificarPierdeJugador();
    }

    private void verificarPierdeJugador() {
        for(Enemigo enemigo:arrEnemigos){
            if(enemigo.getSprite().getY() <= nave.getSprite().getY()){
                //perdio :-(
                //congelar a los enemigos y desaparecer
                nave.getSprite().setPosition(0, Juego.ALTO*2);
            }
        }
    }

    private void verificarChoques() {
        if(bala==null){return;}
        for(int i = arrEnemigos.size -1; i>=0; i--){
            Rectangle rectanguloBala = bala.getSprite().getBoundingRectangle();
            Rectangle rectanguloEnemigo = arrEnemigos.get(i).getSprite().getBoundingRectangle();
            if(rectanguloBala.overlaps(rectanguloEnemigo)){
                bala = null;
                arrEnemigos.removeIndex(i);
                break;
            }
        }
    }

    private void actualizarBala(float delta) {
        if(bala != null){
            bala.mover(delta);
            if(bala.getSprite().getY() >= Juego.ALTO){
                bala = null;
            }
        }
    }

    private void actualizarNave() {
        switch(estadoNave){
            case DERECHA:
                nave.mover(5, 0);
                break;
            case IZQUIERDA:
                nave.mover(-5, 0);
                break;
        }
    }

    private void actualizarEnemigos(float delta) {
        if(nave.getSprite().getY() == Juego.ALTO*2){
            return;
        }


        timerPaso += delta;
        if (timerPaso>MAX_PASO){
            timerPaso = 0;
            for(Enemigo enemigo:arrEnemigos){
                enemigo.mover(DX,0);
                enemigo.cambiarImagen();
            }
            pasos++;
            if(pasos>= 40){
                pasos = 0;
                DX = -DX;
                for(Enemigo enemigo:arrEnemigos){
                    enemigo.mover(0, -40);
                }
            }
        }

    }

    private void borrarPantalla() {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //batch escala de acuerdo a la vista y la camara
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(texturaFondo, 0, 0);
        //Dibujo de Enemigos
        for(Enemigo enemigo : arrEnemigos){
            enemigo.render(batch);
        }
        nave.render(batch);
        if(bala != null){
            bala.render(batch);
        }
        batch.end();



        escenaHUD.draw();
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

    private enum Movimiento {
        QUIETO,
        DERECHA,
        IZQUIERDA

    }
}
