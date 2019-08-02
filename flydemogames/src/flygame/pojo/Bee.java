package flygame.pojo;

import flygame.common.GameSet;

import java.util.Random;

public class Bee extends FlyingObject implements Award{
    private int xSpeed = 1;   //x坐标移动速度
    private int ySpeed = 2;
    private int awardType;    //奖励


    public Bee(){
        //设置图片
        this.image = GameSet.beeImage;

        width = image.getWidth();
        height = image.getHeight();
        //设置位置
        y = -height;
        Random rand = new Random();
        x = rand.nextInt(GameSet.WIDTH - width);
        awardType = rand.nextInt(2);   //设置好奖励
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

}
