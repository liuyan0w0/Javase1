package flygame.common;

import flygame.pojo.*;
import flygame.utils.GetPropertiesUtil;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.Timer;


public class GameSet extends JPanel {
    //设置面板宽和高
    public static int WIDTH;
    public static int HEIGHT;


    private int state;
    private static final int START = 0;
    private static final int RUNNING = 1;
    private static final int PAUSE = 2;
    private static final int GAME_OVER = 3;

    //系统设置
    private int score = 0; // 得分
    private Timer timer; // 定时器
    private int intervel = 10; // 时间间隔(毫秒)

    //图片获取
    public static BufferedImage backgroundImage;
    public static BufferedImage startImage;
    public static BufferedImage djImage;
    public static BufferedImage djImage1;
    public static BufferedImage beeImage;
    public static BufferedImage bullet;
    public static BufferedImage hero0Image;
    public static BufferedImage hero1Image;
    public static BufferedImage pauseImage;
    public static BufferedImage gameOver;

    //飞行物准备
    private List <FlyingObject> badFlys = new ArrayList <FlyingObject>( ); // 敌机集合
    private List <Bullet> bullets = new ArrayList <Bullet>( ); // 子弹集合
    private HeroFly hero = new HeroFly( ); // 英雄机


    static {
        try {
            WIDTH = Integer.parseInt(GetPropertiesUtil.getValue("WIDTH"));
            HEIGHT = Integer.parseInt(GetPropertiesUtil.getValue("HEIGHT"));

            backgroundImage = getImage("backgroundImage");
            startImage = getImage("startImage");
            djImage = getImage("djImage");
            djImage1 = getImage("djImage1");
            beeImage = getImage("beeImage");
            bullet = getImage("bullet");
            hero0Image = getImage("hero0Image");
            hero1Image = getImage("hero1Image");
            pauseImage = getImage("pauseImage");
            gameOver = getImage("gameOver");
        } catch (Exception e) {
            e.printStackTrace( );
        }
    }


    //获取BufferedImage对象
    public static BufferedImage getImage(String pathName) throws IOException {
        String imagePath = GetPropertiesUtil.getValue(pathName);
        BufferedImage read = ImageIO.read(GameSet.class.getClassLoader( ).getResourceAsStream(imagePath));
        return read;
    }


    //在面板上画英雄飞机、敌机、子弹
    @Override
    public void paint(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, null); // 画背景图
        paintHero(g);
        paintBullets(g);
        paintFlyingObjects(g); // 画飞行物
        paintScore(g); // 画分数
        paintState(g); // 画游戏状态
    }

    //画英雄机

    public void paintHero(Graphics g) {
        g.drawImage(hero.getImage( ), hero.getX( ), hero.getY( ), null);
    }

    //画子弹

    public void paintBullets(Graphics g) {
        for (int i = 0; i < bullets.size( ); i++) {
            Bullet b = bullets.get(i);
            g.drawImage(b.getImage( ), b.getX( ) - b.getWidth( ) / 3, b.getY( ),
                    null);
        }
    }

    //画敌机

    public void paintFlyingObjects(Graphics g) {
        for (int i = 0; i < badFlys.size( ); i++) {
            FlyingObject f = badFlys.get(i);
            g.drawImage(f.getImage( ), f.getX( ), f.getY( ), null);
        }
    }


    public void paintScore(Graphics g) {
        int x = 10; // x坐标
        int y = 25; // y坐标
        Font font = new Font(Font.SANS_SERIF, Font.BOLD, 18); // 字体
        g.setColor(new Color(0x3A3B3B));
        g.setFont(font); // 设置字体
        g.drawString("SCORE:" + score, x, y);
        y += 20; // y坐标增20
        g.drawString("LIFE:" + hero.getLife( ), x, y); // 画生命值
    }


    public void paintState(Graphics g) {
        switch (state) {
            case START:
                g.drawImage(startImage, 0, 0, null);
                break;
            case PAUSE:
                g.drawImage(pauseImage, 0, 0, null);
                break;
            case GAME_OVER:
                g.drawImage(gameOver, 0, 0, null);// 游戏终止状态
                break;
        }
    }


    public void action() {
        // 鼠标监听事件
        MouseAdapter l = new MouseAdapter( ) {
            // 鼠标在界面移动
            @Override
            public void mouseMoved(MouseEvent e) {
                // 游戏运行状态下，英雄机随鼠标位置移动
                if (state == RUNNING) {
                    int x = e.getX( );
                    int y = e.getY( );
                    hero.moveTo(x, y);
                }
            }

            // 鼠标进入界面
            @Override
            public void mouseEntered(MouseEvent e) {
                // 游戏从暂停状态下进入运行状态
                if (state == PAUSE) {
                    state = RUNNING;
                }



            }

            // 鼠标退出界面
            @Override
            public void mouseExited(MouseEvent e) {
                if (state != GAME_OVER && state != START) {
                    state = PAUSE;
                }
            }

            // 鼠标在界面点击
            @Override
            public void mouseClicked(MouseEvent e) {
                switch (state) {
                    case START:
                        state = RUNNING; // 启动状态下运行
                        break;
                    case GAME_OVER:
                        badFlys.removeAll(badFlys); // 清空飞行物
                        bullets.removeAll(bullets); // 清空子弹
                        hero = new HeroFly( ); // 重新创建英雄机
                        score = 0;
                        state = START;
                        break;
                }
            }
        };
        this.addMouseListener(l); // 加载处理鼠标点击操作
        this.addMouseMotionListener(l); // 加载处理鼠标滑动操作

        timer = new Timer( ); // 主流程控制,启动定时器任务
        timer.schedule(new TimerTask( ) {
            @Override
            public void run() {
                if (state == RUNNING) {
                    enterAction( );
                    stepAction( );
                    shootAction( );
                    bangAction( );
                    outOfBoundsAction( ); // 删除越界飞行物及子弹
                    checkGameOverAction( ); // 检查游戏结束
                }
                repaint( );
            }

        }, intervel, intervel);
    }


    int flyEnteredIndex = 0;


    public void enterAction() {
        flyEnteredIndex++;
        if (flyEnteredIndex % 30 == 0) {
            FlyingObject obj = nextOne( ); // 随机生成一个飞行物
            badFlys.add(obj);
        }


    }


    public void stepAction() {
        for (int i = 0; i < badFlys.size( ); i++) { // 飞行物走一步
            FlyingObject f = badFlys.get(i);
            f.move( );
        }

        for (int i = 0; i < bullets.size( ); i++) {
            Bullet b = bullets.get(i);
            b.move( );
        }
        hero.move( );
    }


    int shootIndex = 0;


    public void shootAction() {
        shootIndex++;
        if (shootIndex % 40 == 0) {
            Bullet[] bs = hero.shoot( ); // 英雄打出子弹
            for (int i = 0; i < bs.length; i++) {
                bullets.add(bs[i]);
            }
        }
    }






    public void bangAction() {
        for (int i = 0; i < bullets.size( ); i++) { // 遍历所有子弹
            Bullet b = bullets.get(i);
            bang(b); // 子弹和飞行物之间的碰撞检查
        }
    }


    //删除越界飞行物及子弹

    public void outOfBoundsAction() {
        for (int i = 0; i < badFlys.size( ); i++) {
            if (badFlys.get(i).outOfBounds( )) {
                badFlys.remove(i);
            }
        }
        for (int i = 0; i < bullets.size( ); i++) {
            Bullet b = bullets.get(i);
            if (b.outOfBounds( )) {
                bullets.remove(i);
            }
        }
    }

    public void checkGameOverAction() {
        if (isGameOver( )) {
            state = GAME_OVER; // 改变状态
        }
    }


    public boolean isGameOver() {
        for (int i = 0; i < badFlys.size( ); i++) {
            FlyingObject obj = badFlys.get(i);
            if (hero.hit(obj)) { // 检查英雄机与飞行物是否碰撞
                hero.subtractLife( );
                hero.setDoubleFire(0);
                badFlys.remove(i);
            }
        }

        return hero.getLife( ) <= 0;
    }


    public void bang(Bullet bullet) {
        for (int i = 0; i < badFlys.size( ); i++) {
            FlyingObject obj = badFlys.get(i);
            // 判断是否击中
            if (obj.shootBy(bullet)) {
                if (obj instanceof Enemy) { // 检查类型，是敌人，则加分
                    Enemy e = (Enemy) obj;
                    score += e.getScore( );
                } else if (obj instanceof Award) {
                    Award a = (Award) obj;
                    int type = a.getType( ); // 获取奖励类型
                    switch (type) {
                        case Award.DOUBLE_FIRE:
                            hero.addDoubleFire( ); // 设置双倍火力
                            break;
                        case Award.LIFE:
                            hero.addLife( ); // 设置加命
                            break;
                    }
                }
                //移除被击中的飞行物
                badFlys.remove(i);
            }
        }

    }

    //生成飞行物对象
    public static FlyingObject nextOne() {
        Random random = new Random( );
        int type = random.nextInt(20);
        if (type == 0) {
            return new Bee( );
        } else if (type > 11 && type < 20) {
            return new BadFly( );
        } else {
            return new BadFly1( );
        }
    }


}
