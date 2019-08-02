package flygame.pojo;

import flygame.common.GameSet;
import java.awt.image.BufferedImage;


public class HeroFly extends FlyingObject{
    private BufferedImage[] images = {};
    private int index = 0;                //英雄机图片切换索引

    private int doubleFire;   //双倍火力
    private int life;

    public HeroFly(){
        life = 3;
        doubleFire = 0;
        images = new BufferedImage[]{GameSet.hero0Image,GameSet.hero1Image};
        image = GameSet.hero0Image;

        //设置飞机的宽高为图片的宽和高
        width = image.getWidth();
        height = image.getHeight();

        x = 150;
        y = 400;
    }

    public int isDoubleFire() {
        return doubleFire;
    }//获取双倍火力


    public void setDoubleFire(int doubleFire) {
        this.doubleFire = doubleFire;
    }//设置双倍火力

    public void addDoubleFire(){
        doubleFire = 80;
    }//增加火力


    public void addLife(){  //增命
        life++;
    }

    public void subtractLife(){   //减命
        life--;
    }


    public int getLife(){
        return life;
    }//获取命

    //物体移动了一下，相对距离，x,y鼠标位置
    public void moveTo(int x,int y){
        this.x = x - width/2;
        this.y = y - height/2;
    }

    //越界处理
    @Override
    public boolean outOfBounds() {
        return false;
    }

    //发射子弹
    public Bullet[] shoot(){
        int xStep = width/12;
        int yStep = 20;
        if(doubleFire>0){  //双倍火力
            Bullet[] bullets = new Bullet[2];

            bullets[0] = new Bullet(x+5*xStep,y-yStep);  //y-yStep(子弹距飞机的位置)
            bullets[1] = new Bullet(x+xStep,y-yStep);
            return bullets;
        }else{      //单倍火力
            Bullet[] bullets = new Bullet[1];
            bullets[0] = new Bullet(x+3*xStep,y-yStep);
            return bullets;
        }
    }

  // 移动
    @Override
    public void move() {
        if(images.length>0){
          image = images[index++/1000%images.length];  //切换图片hero0，hero1
        }
    }

    // 碰撞算法
    public boolean hit(FlyingObject other){

        int x1 = other.x - this.width/5;
        int x2 = other.x + this.width/5 + other.width;
        int y1 = other.y - this.height/5;
        int y2 = other.y + this.height/5 + other.height;

        int herox = this.x + this.width/5;               //英雄机x坐标中心点距离
        int heroy = this.y + this.height/5;              //英雄机y坐标中心点距离

        return herox>x1 && herox<x2 && heroy>y1 && heroy<y2;   //区间范围内为撞上了
    }

}
