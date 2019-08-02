package flygame.pojo;

import java.awt.image.BufferedImage;


public abstract class FlyingObject {

    protected int x;
    protected int y;
    protected int width;
    protected int height;

    protected BufferedImage image;     //这里获取的是飞行物显示的图片

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }


    public abstract boolean outOfBounds();//检查飞行物是否出界


    public abstract void move();//飞行物的移动方式


    public boolean shootBy(Bullet bullet){
        int x = bullet.getX();  //子弹横坐标
        int y = bullet.getY();  //子弹纵坐标
        return this.x<x && x<this.x+width && this.y<y && y<this.y+height;
    }

}

