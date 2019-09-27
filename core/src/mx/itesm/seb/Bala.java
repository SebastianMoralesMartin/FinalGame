package mx.itesm.seb;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bala {
    private Sprite sprite;
    private float vel = 300; //pixeles por segundo

    public Bala(Texture textura, float x, float y){
        sprite = new Sprite(textura);
        sprite.setPosition(x, y);
    }
    public Sprite getSprite(){
        return sprite;
    }


    public void mover(float delta){
        float distancia = vel * delta;
        sprite.setY(sprite.getY() + distancia);
    }

    public void render(SpriteBatch batch){
        sprite.draw(batch);
    }
}
