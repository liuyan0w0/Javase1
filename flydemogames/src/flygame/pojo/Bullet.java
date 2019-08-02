package flygame.pojo;

import flygame.common.GameSet;


public class Bullet extends FlyingObject{
    private int speed = 3;  //移动的速度


    public Bullet(int x,int y){
        this.x = x;
        this.y = y;
        this.image = GameSet.bullet;
    }


    @Override
    public void move(){
        y-=speed;
    }


    @Override
    public boolean outOfBounds() {
        return y<-height;
    }//越界处理
}
