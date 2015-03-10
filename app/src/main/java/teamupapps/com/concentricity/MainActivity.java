package teamupapps.com.concentricity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.Random;

import static android.graphics.Color.*;


public class MainActivity extends ActionBarActivity {

    private FrameLayout frame;
    private LinearLayout llTop;
    private LinearLayout llBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frame = (FrameLayout) findViewById(R.id.frame);
        frame.addView(new MyView(this));
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        llTop = (LinearLayout)findViewById(R.id.ll_title);
        llBottom = (LinearLayout)findViewById(R.id.ll_bottom);
    }

    public void hideViews(){
        llTop.setVisibility(View.INVISIBLE);
        llBottom.setVisibility(View.INVISIBLE);
    }

    public void showViews(){
        llTop.setVisibility(View.VISIBLE);
        llBottom.setVisibility(View.VISIBLE);
    }
    public class MyView extends View  {
        float radius;// = 100;
        boolean firstRun = true;

       // bounds = new RectF(canvas.getClipBounds());
        //centerX = bounds.centerX();
       // centerY = bounds.centerY();

        float angleDeg = 0f;

        float angleDegOne = 0f;
        float angleDegTwo = 0f;
        float angleDegThree = 0f;

        boolean freezeFirst = false;
        boolean freezeSecond = false;
        boolean freezeThird = false;

        int	BLACK = Color.BLACK;
        int	GRAY = Color.GRAY;

        //middle circles color, will be changed depending on chosen colors from the balls
        int mColor = WHITE;
        int	RED = Color.RED;
        int	GREEN = Color.GREEN;

        int[] COLORS_PRIMARAY  = new int[] {
            0xFFFF0000, 0xFFFF00FF, 0xFF0000FF, 0xFF00FFFF, 0xFF00FF00,
                    0xFFFFFF00, 0xFFFF0000
        };

        Random r = new Random();
        float rand = r.nextInt(359);
        float rand2 = r.nextInt(359);
        float rand3 = r.nextInt(359);
        private static final float PI = 3.1415926f;

        Paint firstBallPaint = new Paint();
        Paint secondBallPaint = new Paint();
        Paint thirdBallPaint = new Paint();

        RectF bounds;
        float centerX;
        float centerY;

        int score = 0;
        float ball_speed = 1.0f;
        float check_distance = 40f;
        float speed_increment = 0.2f;
        int numberOfCorrect = 0;

        // the number here will be divided by the canvas size to get the radius size
        int GAME_SIZE =14;
        boolean isPlaying = false;

        public MyView(Context context) {
            super(context);
            // TODO Auto-generated constructor stub
        }

        public void redraw() {
            this.invalidate();

        }

        public void reset(){
             isPlaying = false;
             showViews();
             score = 0;
             ball_speed = 1.0f;
             numberOfCorrect = 0;

            freezeFirst = false;
            freezeSecond = false;
            freezeThird = false;

            rand = r.nextInt(359);
            rand2 = r.nextInt(359);
            rand3 = r.nextInt(359);

            angleDegOne = 0;
            angleDegTwo = 0;
            angleDegThree = 0;

            getBallPaints();

        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:

                    if (!isPlaying){
                        isPlaying = true;
                        hideViews();
                    }

                    if (!freezeFirst) {
                        float Posx = (float)Math.cos(Math.toRadians(angleDegOne));
                        float Posy = (float)Math.sin(Math.toRadians(angleDegOne));
                        float angle = (float)java.lang.Math.atan2(Posy, Posx);
                        freezeFirst = true;
                        float unit = angle/(2*PI);
                        if (unit < 0) {
                            unit += 1;
                        }
                        thirdBallPaint.setStyle(Paint.Style.FILL);
                        //thirdBallPaint.setColor(interpColor(COLORS_PRIMARAY, unit));

                       // Log.i("","angleDegOne "+ angleDegOne);
                        //Log.i("","rand "+ rand);
                        if(checkDistance(angleDegOne,rand)){
                            thirdBallPaint.setColor(getResources().getColor(R.color.green));
                            score++;
                            numberOfCorrect++;
                        }
                        else {
                            thirdBallPaint.setColor(getResources().getColor(R.color.red));
                            score--;
                        }
                    }
                    else if (freezeFirst && !freezeSecond) {
                        float Posx = (float)Math.cos(Math.toRadians(angleDegTwo));
                        float Posy = (float)Math.sin(Math.toRadians(angleDegTwo));
                        float angle = (float)java.lang.Math.atan2(Posy, Posx);
                        freezeFirst = true;
                        freezeSecond = true;
                        float unit = angle/(2*PI);
                        if (unit < 0) {
                            unit += 1;
                        }
                        secondBallPaint.setStyle(Paint.Style.FILL);
                        //secondBallPaint.setColor(interpColor(COLORS_PRIMARAY, unit));
                        if(checkDistance(angleDegTwo,rand2)){
                            secondBallPaint.setColor(getResources().getColor(R.color.green));
                            score++;
                            numberOfCorrect++;
                        }
                        else {
                            secondBallPaint.setColor(getResources().getColor(R.color.red));
                            score--;
                        }
                    }

                    else  if (freezeFirst && freezeSecond && !freezeThird) {
                        float Posx = (float)Math.cos(Math.toRadians(angleDegThree));
                        float Posy = (float)Math.sin(Math.toRadians(angleDegThree));
                        float angle = (float)java.lang.Math.atan2(Posy, Posx);
                        freezeThird = true;
                        float unit = angle/(2*PI);
                        if (unit < 0) {
                            unit += 1;
                        }
                        firstBallPaint.setStyle(Paint.Style.FILL);
                       // firstBallPaint.setColor(interpColor(COLORS_PRIMARAY, unit));
                        if(checkDistance(angleDegThree,rand3)){
                            firstBallPaint.setColor(getResources().getColor(R.color.green));
                            score++;
                            numberOfCorrect++;
                        }
                        else {
                            firstBallPaint.setColor(getResources().getColor(R.color.red));
                            score--;
                        }
                        //ball_speed = ball_speed +0.2f;
                    }
                    break;

            }
            return true;

        }

        private boolean checkDistance(float angle1, float angle2){
            float dist =  angle1 - angle2;
            if(dist < check_distance && dist > -check_distance){
                return true;
            }

            return false;
        }

        private void getBallPaints(){
            Paint guessPaint = new Paint();
            guessPaint.setAntiAlias(true);
            guessPaint.setStyle(Paint.Style.FILL);



            float guessCol = rand;

            float Posx = (float)Math.cos(Math.toRadians(guessCol));
            float Posy = (float)Math.sin(Math.toRadians(guessCol));
            float angle = (float)java.lang.Math.atan2(Posy, Posx);

            float unit = angle/(2*PI);
            if (unit < 0) {
                unit += 1;
            }

            thirdBallPaint.setColor(interpColor(COLORS_PRIMARAY, unit));
           // firstBallPaint.setColor(BLACK);


            //  canvas.drawCircle(radius, radius, radius, guessPaint);

            guessPaint.setColor(BLACK);
            //  canvas.drawCircle(Posx, Posy, 20, guessPaint);

            float guessCol2 = rand2;

            Posx = (float)Math.cos(Math.toRadians(guessCol2));
            Posy = (float)Math.sin(Math.toRadians(guessCol2));
            angle = (float)java.lang.Math.atan2(Posy, Posx);

            unit = angle/(2*PI);
            if (unit < 0) {
                unit += 1;
            }

            secondBallPaint.setColor(interpColor(COLORS_PRIMARAY, unit));
          //  guessPaint.setColor(interpColor(COLORS_PRIMARAY, unit));
            // canvas.drawCircle(x / 2 ,radius, radius, guessPaint);

            float guessCol3 = rand3;

            Posx = (float)Math.cos(Math.toRadians(guessCol3));
            Posy = (float)Math.sin(Math.toRadians(guessCol3));
            angle = (float)java.lang.Math.atan2(Posy, Posx);

            unit = angle/(2*PI);
            if (unit < 0) {
                unit += 1;
            }

            firstBallPaint.setColor(interpColor(COLORS_PRIMARAY, unit));
            // guessPaint.setColor(interpColor(COLORS_PRIMARAY, unit));
            //  canvas.drawCircle(x-radius, radius, radius, guessPaint);
        }

        private void getRadiusSize(Canvas canvas) {

            bounds = new RectF(canvas.getClipBounds());
            centerX = bounds.centerX();
            centerY = bounds.centerY();
            radius = bounds.width()/GAME_SIZE;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);


            if(firstRun){

                getBallPaints();
                getRadiusSize(canvas);


                firstRun = false;

            }
            drawStaticObjects(canvas);
            drawArcs(canvas);
            drawMovingCircles(canvas);


            if(freezeFirst){

            }
            if(freezeSecond){

            }
            if(freezeThird){
                if(numberOfCorrect >= 2) {
                    ball_speed = ball_speed + speed_increment;
                    freezeFirst = false;
                    freezeSecond = false;
                    freezeThird = false;
                    rand = r.nextInt(359);
                    rand2 = r.nextInt(359);
                    rand3 = r.nextInt(359);
                    timerHandler.postDelayed(timerRunnable, 10);
                    numberOfCorrect = 0;
                    firstRun = true;
                   // getBallPaints();
                }else {
                    reset();
                    timerHandler.postDelayed(timerRunnable, 10);
                }
            }else{
                timerHandler.postDelayed(timerRunnable, 10);
        }
        }

        private int interpColor(int colors[], float unit) {
            if (unit <= 0) {
                return colors[0];
            }
            if (unit >= 1) {
                return colors[colors.length - 1];
            }

            float p = unit * (colors.length - 1);
            int i = (int)p;
            p -= i;

            // now p is just the fractional part [0...1) and i is the index
            int c0 = colors[i];
            int c1 = colors[i+1];
            int a = ave(Color.alpha(c0), Color.alpha(c1), p);
            int r = ave(Color.red(c0), Color.red(c1), p);
            int g = ave(Color.green(c0), Color.green(c1), p);
            int b = ave(Color.blue(c0), Color.blue(c1), p);

            return Color.argb(a, r, g, b);
        }

        private int ave(int s, int d, float p) {
            return s + java.lang.Math.round(p * (d - s));
        }

        private void drawArcs(Canvas canvas){
            RectF bounds = new RectF(canvas.getClipBounds());
            float centerX = bounds.centerX();
            float centerY = bounds.centerY();

            Paint p = new Paint();
            Shader s = new SweepGradient(centerX, centerY, COLORS_PRIMARAY, null);

            p.setAntiAlias(true);
            p.setStyle(Paint.Style.STROKE);
            p.setStrokeWidth(50);
            p.setShader(s);

            canvas.drawCircle(centerX, centerY, radius*5, p);

        }

        private void drawMovingCircles(Canvas canvas) {

            bounds = new RectF(canvas.getClipBounds());
             centerX = bounds.centerX();
             centerY = bounds.centerY();

            float firstXPos = (radius*2) * (float)Math.cos(Math.toRadians(angleDegThree)) + centerX;
            float firstYPos = (radius*2) * (float)Math.sin(Math.toRadians(angleDegThree)) + centerY;

            float secondXPos = (radius*3) * (float)Math.cos(Math.toRadians(angleDegTwo)) + centerX;
            float secondYPos = (radius*3) * (float)Math.sin(Math.toRadians(angleDegTwo)) + centerY;

            float thirdXPos = (radius*4) * (float)Math.cos(Math.toRadians(angleDegOne)) + centerX;
            float thirdYPos = (radius*4) * (float)Math.sin(Math.toRadians(angleDegOne)) + centerY;

            float colXPos = (radius*5) * (float)Math.cos(Math.toRadians(angleDeg)) + centerX;
            float colYPos = (radius*5) * (float)Math.sin(Math.toRadians(angleDeg)) + centerY;

            float col1XPos = (radius*5) * (float)Math.cos(Math.toRadians(rand)) + centerX;
            float col1YPos = (radius*5) * (float)Math.sin(Math.toRadians(rand)) + centerY;

            float col2XPos = (radius*5) * (float)Math.cos(Math.toRadians(rand2)) + centerX;
            float col2YPos = (radius*5) * (float)Math.sin(Math.toRadians(rand2)) + centerY;

            float col3XPos = (radius*5) * (float)Math.cos(Math.toRadians(rand3)) + centerX;
            float col3YPos = (radius*5) * (float)Math.sin(Math.toRadians(rand3)) + centerY;

            canvas.drawCircle(firstXPos, firstYPos, 20, firstBallPaint);
            canvas.drawCircle(secondXPos, secondYPos, 20, secondBallPaint);
            canvas.drawCircle(thirdXPos, thirdYPos, 20, thirdBallPaint);

            final Paint paint = new Paint();

            // paint.setARGB(128, 255, 255, 255);

           /* canvas.drawCircle(colXPos, colYPos, 20, paint);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(col1XPos, col1YPos, 20, paint);
            paint.setColor(Color.WHITE);
            canvas.drawCircle(col2XPos, col2YPos, 20, paint);
            paint.setColor(Color.RED);
            canvas.drawCircle(col3XPos, col3YPos, 20, paint);*/
            //canvas.drawCircle(x / 2, y / 2,radius*5, paint);


        }

        public void drawStaticObjects(Canvas canvas){
            int x = getWidth();
            int y = getHeight();

            Paint canvasPaint = new Paint();
            canvasPaint.setStyle(Paint.Style.FILL);
            canvasPaint.setColor(getResources().getColor(R.color.background));

            Paint redFillPaint = new Paint();
            redFillPaint.setAntiAlias(true);
            redFillPaint.setStyle(Paint.Style.FILL);
            redFillPaint.setColor(mColor);

            Paint lineFillPaint = new Paint();
            lineFillPaint.setAntiAlias(true);
            lineFillPaint.setStyle(Paint.Style.STROKE);
            lineFillPaint.setColor(WHITE);

            Paint primaryFramePaint = new Paint();
            primaryFramePaint.setAntiAlias(true);
            primaryFramePaint.setStyle(Paint.Style.STROKE);
            primaryFramePaint.setColor(Color.BLUE);

            canvas.drawPaint(canvasPaint);

            canvas.drawCircle(x / 2, y / 2, radius, redFillPaint);
            canvas.drawCircle(x / 2, y / 2, radius*2, lineFillPaint);
            canvas.drawCircle(x / 2, y / 2, radius*3, lineFillPaint);
            canvas.drawCircle(x / 2, y / 2, radius*4, lineFillPaint);

            redFillPaint.setColor(BLACK);
            redFillPaint.setTextSize(100);
            canvas.drawText(Integer.toString(score),x / 2 - 10,y / 2 + 10,redFillPaint);



            //canvas.drawCircle(0, 0, radius, guessPaint);
        }

    Handler timerHandler = new Handler();
        Runnable timerRunnable = new Runnable() {

            @Override
            public void run() {

                if (angleDeg < 360){
                    angleDeg = angleDeg + ball_speed;
                }else {
                    angleDeg = 0.0f;
                }

                if(!freezeFirst) {
                    if (angleDegOne < 360){
                        angleDegOne = angleDegOne + ball_speed;
                    }else {
                        angleDegOne = 0.0f;
                    }
                }

                if(!freezeSecond){
                    if (angleDegTwo < 360){
                        angleDegTwo = angleDegTwo + ball_speed;
                    }else {
                        angleDegTwo = 0.0f;
                    }
                }

                if(!freezeThird){
                    if (angleDegThree < 360){
                        angleDegThree = angleDegThree + ball_speed;
                    }else {
                        angleDegThree = 0.0f;
                    }
                }

                redraw();
            }
        };

    }

}
