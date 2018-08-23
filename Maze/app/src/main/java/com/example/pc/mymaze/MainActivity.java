package com.example.pc.mymaze;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener{

    public static Bitmap temp;
    public static int uniLen = 0;
    public static int sideLength = 0;//用来设置边长
    public static int color;//用来设置画笔颜色
    public static int drawable_background;//用来设置背景图

    public static ImageView iv_canvas;
    public static Paint paint = null;
    public static Canvas canvas = null;
    public static mazeView mazeObject = null;

    private float sx = 0, sy = 0;
    static int startrow = 0,startcol = 0;
    boolean ismoved = false;

    public void ini(){
        sx = 0;
        sy = 0;
        startrow = -1;
        startcol = -1;
        ismoved = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv_canvas = (ImageView) findViewById(R.id.maze_image);
        iv_canvas.setOnTouchListener(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sideLength == 0)
                    Snackbar.make(view, "边长还未初始化！", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                else{
//                    Snackbar.make(view, "Refresh... map", Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
                    down_menu_class.canMove = false;
                    mazeObject.creatMaze();
                    drawMaze();
                    ini();
                }
            }
        });
        color = Color.GREEN;
        drawable_background = R.drawable.back33;
    }

    public void drawMaze(){
        DisplayMetrics display = new DisplayMetrics();//▲
        getWindowManager().getDefaultDisplay().getMetrics(display);//获得屏幕宽度
        int screenWidth = display.widthPixels;
        int screenHeight = display.heightPixels;

        temp = Bitmap.createBitmap(screenWidth + 222,
                screenHeight+20, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(temp);
        canvas.drawColor(Color.TRANSPARENT);

        Bitmap background_pic = BitmapFactory.decodeResource(getResources(),drawable_background);
        //现在空白上面画,然后再贴此图在上面

        paint = new Paint();
        paint.setStrokeWidth(5);
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawBitmap(background_pic, 0, 0, paint);
        canvas.translate(50, 150);

        mazeObject = new mazeView(sideLength, screenWidth);
        uniLen = mazeObject.uniteLength;
        mazeObject.creatMaze();

        for(int i = 0; i<mazeObject.sideNum; i++){
            for(int j = 0; j<mazeObject.sideNum; j++){
                    for(int k = 0; k<4; k++){
                        if(!mazeObject.maze[i][j].side[k])continue;
                        switch(k){
                            case 0://左
                                canvas.drawLine(i*uniLen, j*uniLen, i*uniLen, (j+1)*uniLen, paint);
                                break;
                            case 1://上
                                canvas.drawLine(i*uniLen, j*uniLen, (i+1)*uniLen, j*uniLen, paint);
                                break;
                            case 2://右
                                canvas.drawLine((i+1)*uniLen, j*uniLen, (i+1)*uniLen, (j+1)*uniLen, paint);
                                break;
                            case 3://下
                                canvas.drawLine(i*uniLen, (j+1)*uniLen, (i+1)*uniLen, (j+1)*uniLen, paint);
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
        iv_canvas.setImageBitmap(temp);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            View view = getLayoutInflater().inflate(R.layout.input_info_maze, null);//▲
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(view);
            final AlertDialog alert = builder.create();
            alert.show();
            Button sureButton = view.findViewById(R.id.sure);
            final EditText edit =  view.findViewById(R.id.info_maze);
            edit.setText("");
            final Context t = this;
            sureButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        int temp = Integer.parseInt(edit.getText().toString());//▲
                        if(temp > mazeView.maxNumber)
                            Toast.makeText(t, "输入边长过大（不宜超过64单位）", Toast.LENGTH_SHORT).show();
                        else{
                            sideLength = temp;
                            alert.dismiss();//▲
                        }
                        Toast.makeText(t, "maze has been initial", Toast.LENGTH_SHORT).show();
                    }catch(Exception e){
                        new AlertDialog.Builder(t).setTitle("错误！").setMessage("输入为空或输入如格式错误！")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {}
                                }).create().show();
                    }
                    drawMaze();
                    ini();
                }
            });
        }

        else if(id == R.id.action_download){

        }
        return true;
    }

    int stepCount = 0;//行走步数
    long startSecond = 0, endSecond = 0;

    public boolean canmove(int dir){
        if(startrow + 1 < 0||startcol + 1 <0||startcol + 1 >=sideLength||startrow + 1 >=sideLength)
            return false;
        if(!mazeObject.maze[startrow + 1][startcol + 1].side[dir])
            stepCount++;
        return !mazeObject.maze[startrow + 1][startcol + 1].side[dir];
    }

    Bitmap temp1;//用来复制背景图片的bitmap

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        float ex = 0, ey = 0;


        if(canvas == null||paint == null){
            Toast.makeText(this, "未产生迷宫", Toast.LENGTH_SHORT).show();
            return true;
        }

        temp1 = Bitmap.createBitmap(temp.getWidth(),temp.getHeight(),temp.getConfig());//▲
        Canvas canvas1 = new Canvas(temp1);
        canvas1.drawBitmap(temp, 0, 0, paint);
        canvas1.translate(50, 150);

        if(down_menu_class.canMove){

            paint.setColor(Color.BLUE);
            paint.setStyle(Paint.Style.FILL);
            //canvas.drawCircle(startrow + uniLen/2, startcol + uniLen/2, uniLen/2, paint);

            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if(startSecond == 0)
                        startSecond = motionEvent.getEventTime();
                    sx = motionEvent.getX();
                    sy = motionEvent.getY();
                    break;

                case MotionEvent.ACTION_MOVE:
                    ismoved = true;
                    break;

                case MotionEvent.ACTION_UP:
                    ex = motionEvent.getX();
                    ey = motionEvent.getY();
                    if(!ismoved)break;
                    if(Math.abs(ex-sx)>Math.abs(ey-sy)){
                        if(ex - sx > 0 && canmove(2))
                            startrow++;
                        else if(ex - sx < 0 &&canmove(0))
                            startrow--;
                    }
                    else{
                        if(ey - sy > 0 && canmove(3))
                            startcol++;
                        else if(ey - sy < 0 &&canmove(1))
                            //在这里面先restore
                            //然后重新画上新圆
                            startcol--;
                    }
                    //这里面save没用
                    //只能用temp这个图片来绘制了！
                    canvas1.drawCircle((startrow+1)*uniLen+uniLen/2,(startcol+1)*uniLen+uniLen/2,uniLen/2,paint);
                    iv_canvas.setImageBitmap(temp1);//▲
                    ismoved = false;
                    if(startrow == sideLength - 2 && startcol == sideLength - 2){
                        endSecond = motionEvent.getEventTime();
                        double second = (endSecond - startSecond)/1000.0;
                        AlertDialog.Builder build = new AlertDialog.Builder(this);
                        build.setTitle("完成！");
                        build.setMessage("恭喜完成！\n完成迷宫一共走了 " + stepCount + " 步\n花费时间为 " + second + "秒");
                        build.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        });
                        build.create().show();
                        stepCount = 0;
                        startSecond = endSecond = 0;
                    }
                    break;
            }
        }
        return true;
    }
}
