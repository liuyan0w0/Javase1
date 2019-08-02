package flygame.pojo;

import flygame.common.GameSet;

import java.util.Random;


public class BadFly extends FlyingObject implements Enemy{
        private int speed = 3;  //移动步骤


        public BadFly(){
            //获取敌机图片
            this.image = GameSet.djImage;//获取图片的宽和高
            width = image.getWidth();
            height = image.getHeight();

            y = -height;  //设置敌机初始位置的y轴

            Random rand = new Random();
            x = rand.nextInt(GameSet.WIDTH - width); //设置敌机初始位置的x轴
        }

        @Override
        public int getScore() {
            return 1;
        }//获取分数


        @Override
        public  boolean outOfBounds() {
            return y>GameSet.HEIGHT;
        }//越界处理


        @Override
        public void move() {
            y += speed;
        }// 移动
}
