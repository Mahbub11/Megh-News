package com.mahbub.rahim.slidingtest;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.github.ybq.android.spinkit.style.Wave;
import com.yarolegovich.slidingrootnav.SlideGravity;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.util.Timer;

public class MainActivity extends AppCompatActivity {

    private SlidingRootNav slidingRootNav;



    private String[] screenTitles;
    private Drawable[] screenIcons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);







        slidingRootNav = new SlidingRootNavBuilder(this)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(false)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.menu_left_drawer)
                .withDragDistance(140) //Horizontal translation of a view. Default == 180dp
                .withRootViewScale(0.7f) //Content view's scale will be interpolated between 1f and 0.7f. Default == 0.65f;
                .withRootViewElevation(10) //Content view's elevation will be interpolated between 0 and 10dp. Default == 8.
                .withRootViewYTranslation(4) //Content view's translationY will be interpolated between 0 and 4. Default == 0
                  .withMenuOpened(false) //Initial menu opened/closed state. Default == false
                .withMenuLocked(false) //If true, a user can't open or close the menu. Default == false.
                .withGravity(SlideGravity.LEFT) //If LEFT you can swipe a menu from left to right, if RIGHT - the direction is opposite.
                .withSavedState(savedInstanceState) //If you call the method, layout will restore its opened/closed state
                .inject();






        //
        TextView t3=findViewById(R.id.firstmenu2);

        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogue.ViewDialog dialog=new dialogue.ViewDialog();
                dialog.showDialog2(MainActivity.this,"AAAAAAAAAAAAA");

            }
        });

        ///
        TextView t2=findViewById(R.id.firstmenu3);


        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogue alert = new dialogue();
                alert.showDialog(MainActivity.this, "Error de conexión al servidor");
            }
        });







        //
        TextView t1=findViewById(R.id.firstmenu);

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                WebView  w1=(WebView)findViewById(R.id.webview);
                String webURL=w1.getUrl();
                w1.loadUrl(webURL);

                Intent share=new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

                share.putExtra(Intent.EXTRA_SUBJECT,"Share it On");
                share.putExtra(Intent.EXTRA_TEXT,webURL);
                startActivity(Intent.createChooser(share,"Share This News"));
            }
        });



      final WebView  w1 = (WebView) findViewById(R.id.webview);

        w1.loadUrl("http://meghtechltd.com/meghnews/");
        w1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        w1.getSettings().setLoadsImagesAutomatically(true);
        w1.setWebViewClient(new WebViewClient());
        w1.getSettings().setLoadsImagesAutomatically(true);
        WebSettings webSettings = w1.getSettings();
        webSettings.setJavaScriptEnabled(true);


        w1.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {

                ProgressBar progressBar = (ProgressBar)findViewById(R.id.spin_kit);
                Wave doubleBounce = new Wave();
                progressBar.setIndeterminateDrawable(doubleBounce);

                progressBar.setProgress(progress);
                if (progress == 100) {
                    progressBar.setVisibility(View.GONE);

                } else {
                    progressBar.setVisibility(View.VISIBLE);

                }
            }
        });



        //add
       WebView w2=(WebView)findViewById(R.id.Addview);
        w2.loadUrl("http://meghtechltd.com/meghnews/ws/index.php");
        w2.getSettings().setLoadsImagesAutomatically(true);
        webSettings = w2.getSettings();
        webSettings.setJavaScriptEnabled(true);


        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {



            @Override
            public void onRefresh() {

             //   String webUrl = w1.getUrl();
                //webview.reload();

//                String currentURL=w1.getUrl();
//                w1.loadUrl(currentURL);
//                swipeRefreshLayout.setRefreshing(false);

                Load1();

            }
        }

        );






    }


    @Override
    public void onBackPressed() {
        WebView w1 = (WebView) findViewById(R.id.webview);
        if (w1.canGoBack()) {
            w1.goBack();
        } else {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);


            builder.setTitle("Are you sure you want to exit.?")
                    .setCancelable(false)

                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            MainActivity.this.finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();

            alert.show();

            alert.getWindow().setBackgroundDrawableResource(android.R.color.darker_gray);
        }


    }


   //RefreshMethod
    private void Load1() {

        SwipeRefreshLayout  swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipe);

       WebView w1=(WebView)findViewById(R.id.webview);
        w1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        w1.getSettings().setLoadsImagesAutomatically(true);
        WebSettings webSettings=w1.getSettings();
        webSettings.setJavaScriptEnabled(true);
        w1.setWebViewClient(new WebViewClient());
        swipeRefreshLayout.setRefreshing(true);
        String webURL=w1.getUrl();
        w1.loadUrl(webURL);
        swipeRefreshLayout.setRefreshing(false);


    }




}
