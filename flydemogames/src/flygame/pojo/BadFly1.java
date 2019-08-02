package flygame.pojo;

import flygame.common.GameSet;

import java.util.Random;

public class BadFly1 extends FlyingObject implements Enemy{
    private int xSpeed = 1;   //x坐标移动速度
    private int ySpeed = 2;   //y坐标移动速度
    private int awardType;    //奖励


    public BadFly1(){
        //设置图片
        this.image = GameSet.djImage1;
        //设置宽高
        width = image.getWidth();
        height = image.getHeight();
        //设置位置
        y = -height;
        Random rand = new Random();
        x = rand.nextInt(GameSet.WIDTH - width);

    }


    public int getType(){
        return awardType;
    }


    @Override   //越界处理
    public boolean outOfBounds() {
        return y>GameSet.HEIGHT;
    }

    //不要超过边界
    @Override
    public void move() {
        x += xSpeed;
        y += ySpeed;
        if(x > GameSet.WIDTH-width){
            xSpeed = -1;
        }
        if(x < 0){
            xSpeed = 1;
        }
    }

    @Override
    public int getScore() {
        return 0;
    }
}
