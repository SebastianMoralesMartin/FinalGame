package mx.itesm.seb;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class PantallaMario extends Pantalla {

    private Juego juego;

    //Mapa
    private OrthogonalTiledMapRenderer mapRenderer;
    private TiledMap mapa;

    public PantallaMario(Juego juego){
        this.juego = juego;
    }
    @Override
    public void show() {
        //Creacion de un objeto "AssetManager" y instanciado
        AssetManager manager = new AssetManager();
        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        manager.load("mapa/mapaMario.tmx", TiledMap.class);
        manager.finishLoading();
        mapa = manager.get("mapa/mapaMario.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(mapa);
    }

    @Override
    public void render(float delta) {
        borrarPantalla(1, 0, 0);
        batch.setProjectionMatrix(camara.combined);
        mapRenderer.setView(camara);
        mapRenderer.render();

        batch.begin();
        batch.end();

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
