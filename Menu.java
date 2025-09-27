//Please don't replace listeners with lambda!

package com.android.support;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


import org.xml.sax.ErrorHandler;
import android.os.Looper;
import java.security.Signature;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.InputStream;
import java.net.URLEncoder;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.net.HttpURLConnection;
import java.io.OutputStream;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;


import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.widget.RelativeLayout.ALIGN_PARENT_LEFT;
import static android.widget.RelativeLayout.ALIGN_PARENT_RIGHT;


import android.graphics.drawable.StateListDrawable;
import android.view.animation.RotateAnimation;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;

public class Menu {
    //********** Here you can easly change the menu appearance **********//

    //region Variable
    public static final String TAG = "Mod_Menu"; //Tag for logcat

    int TEXT_COLOR = Color.RED;
    int TEXT_COLOR_2 = Color.parseColor("#FFFFFF");
    int BTN_COLOR = Color.BLACK;
    int MENU_BG_COLOR = Color.BLACK; //#AARRGGBB
    int MENU_FEATURE_BG_COLOR = Color.BLACK; //#AARRGGBB
    int MENU_WIDTH = 250;
    int MENU_HEIGHT = 190;
    int POS_X = 0;
    int POS_Y = 100;

    float MENU_CORNER = 50f;
    int ICON_SIZE = 45; //Change both width and height of image
    float ICON_ALPHA = 0.7f; //Transparent
    int ToggleON = Color.GREEN;
    int ToggleOFF = Color.WHITE;
    int BtnON = Color.GREEN;
    int BtnOFF = Color.RED;
    int CategoryBG = Color.parseColor("#FFFFFF");
    int SeekBarColor = Color.parseColor("#80CBC4");
    int SeekBarProgressColor = Color.parseColor("#80CBC4");
    int CheckBoxColor = Color.WHITE;
    int RadioColor = Color.parseColor("#FFFFFF");
    String NumberTxtColor = "#41c300";
    //********************************************************************//
   
    
    RelativeLayout mCollapsed, mRootContainer;
    LinearLayout mExpanded, mods, mods2, mSettings, mCollapse;
    LinearLayout.LayoutParams scrlLLExpanded, scrlLL;
    WindowManager mWindowManager;
    WindowManager.LayoutParams vmParams;
    ImageView startimage;
    FrameLayout rootFrame;
    ScrollView scrollView;
    boolean stopChecking, overlayRequired;
    Context getContext;

    //initialize methods from the native library
    native void Init(Context context, TextView title, TextView subTitle);

    native String Icon();

    native String IconWebViewData();
    
    native String[] GetUserType();
	
	native String[] Vipdata();
	
	private String currentVipUser = null;

	private String currentVipPass = null;
	
	private String vipStatus = "invalid";

	public String signature = "[VIP]";

    native String[] GetFeatureList();

    native String[] SettingsList();

    native boolean IsGameLibLoaded();

    //Here we write the code for our Menu
    // Reference: https://www.androidhive.info/2016/11/android-floating-widget-like-facebook-chat-head/
    public Menu(Context context) {

        getContext = context;
        Preferences.context = context;
        rootFrame = new FrameLayout(context); // Global markup
        rootFrame.setOnTouchListener(onTouchListener());
        mRootContainer = new RelativeLayout(context); // Markup on which two markups of the icon and the menu itself will be placed
        mCollapsed = new RelativeLayout(context); // Markup of the icon (when the menu is minimized)
        mCollapsed.setVisibility(View.VISIBLE);
        mCollapsed.setAlpha(ICON_ALPHA);

        //********** The box of the mod menu **********
        mExpanded = new LinearLayout(context); // Menu markup (when the menu is expanded)
        mExpanded.setVisibility(View.GONE);
        mExpanded.setBackgroundColor(MENU_BG_COLOR);
        mExpanded.setOrientation(LinearLayout.VERTICAL);
        mExpanded.setPadding(10, 10, 10, 10); //So borders would be visible
        mExpanded.setLayoutParams(new LinearLayout.LayoutParams(dp(MENU_WIDTH), WRAP_CONTENT));
        GradientDrawable gdMenuBody = new GradientDrawable();
        gdMenuBody.setCornerRadius(MENU_CORNER); //Set corner
        gdMenuBody.setColor(MENU_BG_COLOR); //Set background color
        gdMenuBody.setStroke(8, Color.parseColor("#9000FFFF")); //Set border
        mExpanded.setBackground(gdMenuBody); //Apply GradientDrawable to it

        //********** The icon to open mod menu **********
        startimage = new ImageView(context);
        startimage.setLayoutParams(new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
        int applyDimension = (int) TypedValue.applyDimension(1, ICON_SIZE, context.getResources().getDisplayMetrics()); //Icon size
        startimage.getLayoutParams().height = applyDimension;
        startimage.getLayoutParams().width = applyDimension;
        //startimage.requestLayout();
        startimage.setScaleType(ImageView.ScaleType.FIT_XY);
        byte[] decode = Base64.decode(Icon(), 0);
        startimage.setImageBitmap(BitmapFactory.decodeByteArray(decode, 0, decode.length));
        ((ViewGroup.MarginLayoutParams) startimage.getLayoutParams()).topMargin = convertDipToPixels(10);
        //Initialize event handlers for buttons, etc.
        startimage.setOnTouchListener(onTouchListener());
        startimage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mCollapsed.setVisibility(View.GONE);
                mExpanded.setVisibility(View.VISIBLE);
            }
        });

        //********** The icon in Webview to open mod menu **********
        WebView wView = new WebView(context); //Icon size width=\"50\" height=\"50\"
        wView.setLayoutParams(new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
        int applyDimension2 = (int) TypedValue.applyDimension(1, ICON_SIZE, context.getResources().getDisplayMetrics()); //Icon size
        wView.getLayoutParams().height = applyDimension2;
        wView.getLayoutParams().width = applyDimension2;
        wView.loadData("<html>" +
                "<head></head>" +
                "<body style=\"margin: 0; padding: 0\">" +
                "<img src=\"" + IconWebViewData() + "\" width=\"" + ICON_SIZE + "\" height=\"" + ICON_SIZE + "\" >" +
                "</body>" +
                "</html>", "text/html", "utf-8");
        wView.setBackgroundColor(0x00000000); //Transparent
        wView.setAlpha(ICON_ALPHA);
        wView.getSettings().setAppCacheEnabled(true);
        wView.setOnTouchListener(onTouchListener());

        //********** Settings icon **********
        TextView settings = new TextView(context); //Android 5 can't show ⚙, instead show other icon instead
        settings.setText(Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M ? "☠︎︎" : "\uD83D\uDD27");
        settings.setTextColor(TEXT_COLOR);
        settings.setTypeface(Typeface.DEFAULT_BOLD);
        settings.setTextSize(20.0f);
        RelativeLayout.LayoutParams rlsettings = new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        rlsettings.addRule(ALIGN_PARENT_RIGHT);
        settings.setLayoutParams(rlsettings);
        settings.setOnClickListener(new View.OnClickListener() {
            boolean settingsOpen;

            @Override
            public void onClick(View v) {
                try {
                    settingsOpen = !settingsOpen;
                    if (settingsOpen) {
                        scrollView.removeView(mods);
                        scrollView.addView(mSettings);
                        scrollView.scrollTo(0, 0);
                    } else {
                        scrollView.removeView(mSettings);
                        scrollView.addView(mods);
                    }
                } catch (IllegalStateException e) {
                }
            }
        });

        //********** Settings **********
        mSettings = new LinearLayout(context);
        mSettings.setOrientation(LinearLayout.VERTICAL);
        featureList(SettingsList(), mSettings);
        
        mods2 = new LinearLayout(context);
		mods2.setOrientation(LinearLayout.VERTICAL);
		featureList(GetUserType(), mods2);

        //********** Title **********
        RelativeLayout titleText = new RelativeLayout(context);
        titleText.setPadding(10, 5, 10, -7);
        titleText.setVerticalGravity(16);

        TextView title = new TextView(context);
        title.setTextColor(TEXT_COLOR);
        title.setTextSize(18.0f);
        title.setGravity(Gravity.CENTER);
        RelativeLayout.LayoutParams rl = new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        rl.addRule(RelativeLayout.CENTER_HORIZONTAL);
        title.setLayoutParams(rl);

        //********** Sub title **********
        TextView subTitle = new TextView(context);
        subTitle.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        subTitle.setMarqueeRepeatLimit(-1);
        subTitle.setSingleLine(true);
        subTitle.setSelected(true);
        subTitle.setTextColor(TEXT_COLOR);
        subTitle.setTextSize(10.0f);
        subTitle.setGravity(Gravity.CENTER);
        subTitle.setPadding(0, 0, 0, 0);

        //********** Mod menu feature list **********
        scrollView = new ScrollView(context);
        //Auto size. To set size manually, change the width and height example 500, 500
        scrlLL = new LinearLayout.LayoutParams(MATCH_PARENT, dp(MENU_HEIGHT));
        scrlLLExpanded = new LinearLayout.LayoutParams(mExpanded.getLayoutParams());
        scrlLLExpanded.weight = 1.0f;
        scrollView.setLayoutParams(Preferences.isExpanded ? scrlLLExpanded : scrlLL);
        scrollView.setBackgroundColor(MENU_FEATURE_BG_COLOR);
        mods = new LinearLayout(context);
        mods.setOrientation(LinearLayout.VERTICAL);

        //********** RelativeLayout for buttons **********
        RelativeLayout relativeLayout = new RelativeLayout(context);
        relativeLayout.setPadding(10, 3, 10, 3);
        relativeLayout.setVerticalGravity(Gravity.CENTER);

        //**********  Hide/Kill button **********

        //********** Close button **********
        RelativeLayout.LayoutParams lParamsCloseBtn = new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        lParamsCloseBtn.addRule(RelativeLayout.CENTER_IN_PARENT);
        

        Button closeBtn = new Button(context);
        closeBtn.setLayoutParams(lParamsCloseBtn);
        closeBtn.setBackgroundColor(Color.BLACK);
        closeBtn.setTextColor(Color.RED);
        closeBtn.setText("خروج");
        closeBtn.setPadding(30, 30, 30, 30); // Dostosuj padding według potrzeb

// Ustawianie zaokrąglonego białego obramowania
        GradientDrawable gdCloseBtn = new GradientDrawable();
        gdCloseBtn.setColor(Color.BLACK); // Ustaw tło na czarny kolor
        gdCloseBtn.setCornerRadius(30); // Dostosuj promień zaokrąglenia
        gdCloseBtn.setStroke(10, Color.parseColor("#FF5000FF")); // Dostosuj grubość i kolor obramowania

        closeBtn.setBackground(gdCloseBtn);

        closeBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    mCollapsed.setVisibility(View.VISIBLE);
                    mCollapsed.setAlpha(ICON_ALPHA);
                    mExpanded.setVisibility(View.GONE);
                }
            });

        //********** Adding view components **********
        mRootContainer.addView(mCollapsed);
        mRootContainer.addView(mExpanded);
        if (IconWebViewData() != null) {
            mCollapsed.addView(wView);
        } else {
            mCollapsed.addView(startimage);
        }
        titleText.addView(title);
        titleText.addView(settings);
        mExpanded.addView(titleText);
        mExpanded.addView(subTitle);
        scrollView.addView(mods);
        mExpanded.addView(scrollView);
        relativeLayout.addView(closeBtn);
        mExpanded.addView(relativeLayout);

        Init(context, title, subTitle);
    }

    public void ShowMenu() {
        rootFrame.addView(mRootContainer);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            boolean viewLoaded = false;

            @Override
            public void run() {
                //If the save preferences is enabled, it will check if game lib is loaded before starting menu
                //Comment the if-else code out except startService if you want to run the app and test preferences
				if (Preferences.loadPref && !IsGameLibLoaded() && !stopChecking) {
                    if (!viewLoaded) {
						//     Category(mods, "Save preferences was been enabled. Waiting for game lib to be loaded...\n\nForce load menu may not apply mods instantly. You would need to reactivate them again");
						//   Button(mods, -100, "Load Try Again");
                        viewLoaded = true;
                    }
                    handler.postDelayed(this, 600);
                } else {
                    mods.removeAllViews();
                    featureList(GetUserType(), mods);
                }
            }
        }, 500);
}



    @SuppressLint("WrongConstant")
    public void SetWindowManagerWindowService() {
        //Variable to check later if the phone supports Draw over other apps permission
        int iparams = Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O ? 2038 : 2002;
        vmParams = new WindowManager.LayoutParams(WRAP_CONTENT, WRAP_CONTENT, iparams, 8, -3);
        //params = new WindowManager.LayoutParams(WindowManager.LayoutParams.LAST_APPLICATION_WINDOW, 8, -3);
        vmParams.gravity = 51;
        vmParams.x = POS_X;
        vmParams.y = POS_Y;

        mWindowManager = (WindowManager) getContext.getSystemService(getContext.WINDOW_SERVICE);
        mWindowManager.addView(rootFrame, vmParams);

        overlayRequired = true;
    }

    @SuppressLint("WrongConstant")
    public void SetWindowManagerActivity() {
        vmParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                POS_X,//initialX
                POS_Y,//initialy
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                        WindowManager.LayoutParams.FLAG_LAYOUT_IN_OVERSCAN |
                        WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN |
                        WindowManager.LayoutParams.FLAG_SPLIT_TOUCH,
                PixelFormat.TRANSPARENT
        );
        vmParams.gravity = 51;
        vmParams.x = POS_X;
        vmParams.y = POS_Y;

        mWindowManager = ((Activity) getContext).getWindowManager();
        mWindowManager.addView(rootFrame, vmParams);
    }

    private View.OnTouchListener onTouchListener() {
        return new View.OnTouchListener() {
            final View collapsedView = mCollapsed;
            final View expandedView = mExpanded;
            private float initialTouchX, initialTouchY;
            private int initialX, initialY;

            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = vmParams.x;
                        initialY = vmParams.y;
                        initialTouchX = motionEvent.getRawX();
                        initialTouchY = motionEvent.getRawY();
                        return true;
                    case MotionEvent.ACTION_UP:
                        int rawX = (int) (motionEvent.getRawX() - initialTouchX);
                        int rawY = (int) (motionEvent.getRawY() - initialTouchY);
                        mExpanded.setAlpha(1f);
                        mCollapsed.setAlpha(1f);
                        //The check for Xdiff <10 && YDiff< 10 because sometime elements moves a little while clicking.
                        //So that is click event.
                        if (rawX < 10 && rawY < 10 && isViewCollapsed()) {
                            //When user clicks on the image view of the collapsed layout,
                            //visibility of the collapsed layout will be changed to "View.GONE"
                            //and expanded view will become visible.
                            try {
                                collapsedView.setVisibility(View.GONE);
                                expandedView.setVisibility(View.VISIBLE);
                            } catch (NullPointerException e) {

                            }
                        }
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        mExpanded.setAlpha(0.5f);
                        mCollapsed.setAlpha(0.5f);
                        //Calculate the X and Y coordinates of the view.
                        vmParams.x = initialX + ((int) (motionEvent.getRawX() - initialTouchX));
                        vmParams.y = initialY + ((int) (motionEvent.getRawY() - initialTouchY));
                        //Update the layout with new X & Y coordinate
                        mWindowManager.updateViewLayout(rootFrame, vmParams);
                        return true;
                    default:
                        return false;
                }
            }
        };
    }

    private void featureList(String[] listFT, LinearLayout linearLayout) {
        //Currently looks messy right now. Let me know if you have improvements
        int featNum, subFeat = 0;
        LinearLayout llBak = linearLayout;

        for (int i = 0; i < listFT.length; i++) {
            boolean switchedOn = false;
            //Log.i("featureList", listFT[i]);
            String feature = listFT[i];
            if (feature.contains("_True")) {
                switchedOn = true;
                feature = feature.replaceFirst("_True", "");
            }

            linearLayout = llBak;
            if (feature.contains("CollapseAdd_")) {
                //if (collapse != null)
                linearLayout = mCollapse;
                feature = feature.replaceFirst("CollapseAdd_", "");
            }
            String[] str = feature.split("_");

            //Assign feature number
            if (TextUtils.isDigitsOnly(str[0]) || str[0].matches("-[0-9]*")) {
                featNum = Integer.parseInt(str[0]);
                feature = feature.replaceFirst(str[0] + "_", "");
                subFeat++;
            } else {
                //Subtract feature number. We don't want to count ButtonLink, Category, RichTextView and RichWebView
                featNum = i - subFeat;
            }
            String[] strSplit = feature.split("_");
            switch (strSplit[0]) {
                case "Toggle":
					linearLayout.addView(Switch(featNum, strSplit[1], switchedOn));
                    break;
                case "SeekBar":
					linearLayout.addView(SeekBar(featNum, strSplit[1], Integer.parseInt(strSplit[2]), Integer.parseInt(strSplit[3])));
                    break;
                case "Button":
                    linearLayout.addView(Button(featNum, strSplit[1]));
                    break;
                case "ButtonOnOff":
                    linearLayout.addView(ButtonOnOff(featNum, strSplit[1], switchedOn));
                    break;
                case "Spinner":
                    TextView(linearLayout, strSplit[1]);
                    Spinner(linearLayout, featNum, strSplit[1], strSplit[2]);
                    break;
                case "InputText":
                    InputText(linearLayout, featNum, strSplit[1]);
                    break;
                case "InputValue":
                    if (strSplit.length == 3)
                        InputNum(linearLayout, featNum, strSplit[2], Integer.parseInt(strSplit[1]));
                    if (strSplit.length == 2)
                        InputNum(linearLayout, featNum, strSplit[1], 0);
                    break;
                case "CheckBox":
                    CheckBox(linearLayout, featNum, strSplit[1], switchedOn);
                    break;
                case "RadioButton":
                    RadioButton(linearLayout, featNum, strSplit[1], strSplit[2]);
                    break;
                case "Collapse":
                    Collapse(linearLayout, strSplit[1]);
                    subFeat++;
                    break;
                case "ButtonLink":
                    subFeat++;
                    ButtonLink(linearLayout, strSplit[1], strSplit[2]);
                    break;
                case "Category":
                    subFeat++;
                    linearLayout.addView(Category(strSplit[1]));
                    break;
                case "RichTextView":
                    subFeat++;
                    TextView(linearLayout, strSplit[1]);
                    break;
                case "RichWebView":
                    subFeat++;
                    WebTextView(linearLayout, strSplit[1]);
                    break;
            }
        }
    }

    private View Switch(final int featNum, final String featName, boolean swiOn) {
        final Switch switchR = new Switch(getContext);
        final GradientDrawable GD_THUMB_ON = new GradientDrawable();
        GD_THUMB_ON.setSize(dp(20),dp(20));
        GD_THUMB_ON.setShape(1);
        GD_THUMB_ON.setStroke(dp(2), Color.parseColor("#FF5000FF"));
        GD_THUMB_ON.setColor(Color.parseColor("#FFFF00FF"));

        final GradientDrawable GD_THUMB_OFF = new GradientDrawable();
        GD_THUMB_OFF.setSize(dp(20),dp(20));
        GD_THUMB_OFF.setShape(1);
        GD_THUMB_OFF.setStroke(dp(2), Color.parseColor("#FF5000FF"));
        GD_THUMB_OFF.setColor(Color.parseColor("#FF00FFFF"));

        final GradientDrawable GD_TRACK = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{Color.parseColor("#FF00FFFF"), Color.parseColor("#FFFF00FF")});
        GD_TRACK.setSize(dp(10), dp(10));
        GD_TRACK.setCornerRadius(100);
        GD_TRACK.setStroke(dp(2), Color.parseColor("#FF5000FF"));      

        StateListDrawable thumbStates = new StateListDrawable();
        thumbStates.addState(new int[]{android.R.attr.state_checked},GD_THUMB_OFF);
        thumbStates.addState(new int[]{-android.R.attr.state_checked}, GD_THUMB_ON);
        thumbStates.addState(new int[]{}, GD_THUMB_OFF);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            switchR.setThumbDrawable(thumbStates);
        }
        switchR.setText(Html.fromHtml("<font color=white>☠︎︎➪ " + featName));
        //switchR.setTypeface(Typeface.createFromAsset(getAssets(), "SakuraMiku/DaisyFonts/DaisyFont2.ttf"));
        switchR.setTextColor(Color.CYAN);
        switchR.setPadding(dp(7),dp(5),dp(7),dp(5));
        switchR.setTextSize(14.5f);
        switchR.setSingleLine(true);
        switchR.setElevation((float) 5);
        switchR.setAllCaps(true);
        switchR.setGravity(Gravity.CENTER | Gravity.LEFT);
        switchR.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT,MATCH_PARENT));
        switchR.setTrackDrawable(GD_TRACK);
        switchR.setChecked(Preferences.loadPrefBool(featName, featNum, swiOn));
        switchR.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				public void onCheckedChanged(CompoundButton compoundButton, boolean bool) {
					Preferences.changeFeatureBool(featName, featNum, bool);
					if (bool) {
						switchR.setText(Html.fromHtml("<font color=white>☠︎︎➪ " + featName + "</font><font color='#00FFFF'> : ✔"));
						//playSound("On.ogg");
					} else {
						switchR.setText(Html.fromHtml("<font color=white>☠︎︎➪ " + featName + "</font><font color='#FF00FF'> : 𓂀"));
						//playSound("Off.ogg");
					}
					switch (featNum) {
						case -1: //Save perferences
							Preferences.with(switchR.getContext()).writeBoolean(-1, bool);
							if (bool == false)
								Preferences.with(switchR.getContext()).clear(); //Clear perferences if switched off
							break;
						case -3:
							Preferences.isExpanded = bool;
							scrollView.setLayoutParams(bool ? scrlLLExpanded : scrlLL);
							break;


					}
				}
			});
        return switchR;
    }
	
	private View SeekBar(final int featNum, final String featName, final int min, int max) {
        int loadedProg = Preferences.loadPrefInt(featName, featNum);
        LinearLayout linearLayout = new LinearLayout(getContext);
        linearLayout.setPadding(dp(7),dp(2),dp(7),dp(2));
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(Gravity.CENTER);

        final TextView textView = new TextView(getContext);
        textView.setText(Html.fromHtml("<font color=white>☞︎︎︎ " + featName + "</font><br/><font color='#FF00FF'>➪( " + min + " )"));
		//   textView.setTypeface(Typeface.createFromAsset(getAssets(), "SakuraMiku/DaisyFonts/DaisyFont2.ttf"));
        textView.setTextSize(12.5f);
        textView.setAllCaps(true);
        textView.setGravity(Gravity.CENTER | Gravity.LEFT);
        textView.setTextColor(Color.WHITE);

        SeekBar seekBar = new SeekBar(getContext);
        GradientDrawable thumbDrawable = new GradientDrawable();
        thumbDrawable.setShape(GradientDrawable.RECTANGLE);
        thumbDrawable.setColor(Color.parseColor("#905000FF"));
        thumbDrawable.setStroke(dp(2), Color.parseColor("#FF5000FF"));
        thumbDrawable.setSize(dp(15), dp(15));
        seekBar.setThumb(thumbDrawable);
        GradientDrawable progressDrawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{Color.parseColor("#90FF00FF"), Color.parseColor("#9000FFFF")});
        progressDrawable.setShape(GradientDrawable.RECTANGLE);
        progressDrawable.setStroke(dp(2), Color.parseColor("#FF5000FF"));
        seekBar.setProgressDrawable(progressDrawable);
        seekBar.setPadding(dp(8),dp(3),dp(8),dp(3));
        seekBar.setMin(min);
        seekBar.setMax(max);
        seekBar.setProgress(min);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
				public void onStartTrackingTouch(SeekBar seekBar) {
				}
				public void onStopTrackingTouch(SeekBar seekBar) {
				}
				int l;
				public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
					if (l < i) {
						//playSound("SliderIncrease.ogg");
					} else {
						//playSound("SliderDecrease.ogg");
					}
					l = i;
					//if progress is greater than minimum, don't go below. Else, set progress
					seekBar.setProgress(i < min ? min : i);
					Preferences.changeFeatureInt(featName, featNum, i < min ? min : i);
					textView.setText(Html.fromHtml("<font color=RED>☞︎︎︎ " + featName + "</font><br/><font color='#FF00FF'>➪( " + (i < min ? min : i) + " )"));
					//textView.setTypeface(Typeface.createFromAsset(getAssets(), "SakuraMiku/DaisyFonts/DaisyFont2.ttf"));
					textView.setTextSize(12.5f);
					switch (featNum){
						case -8:
							textView.setText(Html.fromHtml(featName + " -> " + (i - 0) + "X"));
							ICON_ALPHA = (i + 1) / 10.0F;
							if (i == 10)
								textView.setText(Html.fromHtml(featName + "-> <font color='" + NumberTxtColor + "'>" + "[DEFAULT]"));
							if (i == min)
								ICON_ALPHA = 1.0F;
							switch(i){
								case 1:
									textView.setText(Html.fromHtml(featName + " -> <font color='" + NumberTxtColor + "'>" + " [DEFAULT]"));
									break;
								case 9:
									textView.setText(Html.fromHtml(featName + "-> <font color='" + NumberTxtColor + "'>" + "[INFINITY]"));

									break;

							}
							break; 
						case -9:
							textView.setText(Html.fromHtml(featName + " -> " + (i - 0) + "X"));
							startimage.getLayoutParams().height = (i + 13) * 5;
							startimage.getLayoutParams().width = (i + 13) * 5;
							if (i == 13)
								textView.setText(Html.fromHtml(featName + "-> <font color='" + NumberTxtColor + "'>" + "[DEFAULT]"));
							switch(i){
								case 1:
									textView.setText(Html.fromHtml(featName + " -> <font color='" + NumberTxtColor + "'>" + " [DEFAULT]"));
									break;
								case 99:
									textView.setText(Html.fromHtml(featName + "-> <font color='" + NumberTxtColor + "'>" + "[INFINITY]"));

									break;

							}
							break;

						case -10:
							textView.setText(Html.fromHtml(featName + " -> " + (i - 0) + "X"));
							if(i >= 1){
								RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
								int speed = i > 10  ? ((i - 10) * 1000) : i * 100; 
								rotateAnimation.setDuration(360 * 1000 / speed);
								rotateAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
								rotateAnimation.setRepeatCount(Animation.INFINITE);
								startimage.startAnimation(rotateAnimation);
							}else{
								startimage.clearAnimation();
							}
							switch(i){
								case 0:
									textView.setText(Html.fromHtml(featName + " -> <font color='" + NumberTxtColor + "'>" + " [DEFAULT]"));
									break;
								case 20:
									textView.setText(Html.fromHtml(featName + "-> <font color='" + NumberTxtColor + "'>" + "[INFINITY]"));

									break;

							}
					}






				}
			});
        linearLayout.addView(textView);
        linearLayout.addView(seekBar);

        return linearLayout;
    }

    private View Button(final int featNum, final String featName) {
        final Button button = new Button(getContext);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT);
        layoutParams.setMargins(dp(5),dp(2),dp(5),dp(2));
        button.setLayoutParams(layoutParams);
        button.setPadding(dp(4),dp(5),dp(4),dp(4));
        button.setTextSize(12.5f);
        button.setSingleLine(true);
        button.setTextColor(Color.WHITE);
        button.setGravity(Gravity.CENTER);
        button.setText(featName);
        button.setAllCaps(true);
        GradientDrawable gdMenuBody = new GradientDrawable();
        gdMenuBody.setColor(Color.parseColor("#90FF00FF"));
        gdMenuBody.setCornerRadius(dp(10));
        gdMenuBody.setStroke(dp(2), Color.parseColor("#FF5000FF"));
        button.setBackgroundDrawable(gdMenuBody);
		//  button.setTypeface(Typeface.createFromAsset(getAssets(), "SakuraMiku/DaisyFonts/DaisyFont7.ttf"));
        button.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					//playSound("Select.ogg");
					switch (featNum) {
						case -6:
							scrollView.removeView(mSettings);
							scrollView.addView(mods);
							break;
						case -100:
							stopChecking = true;
							break;
							case -333:
							logindialog();
							 break;




					}
					Preferences.changeFeatureInt(featName, featNum, 0);
				}
			});

        return button;
    }

    private void ButtonLink(LinearLayout linLayout, final String featName, final String url) {
        final Context context = linLayout.getContext(); // Przypisz getContext() do zmiennej finalnej

        final Button button = new Button(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT);
        layoutParams.setMargins(7, 5, 7, 5);
        button.setLayoutParams(layoutParams);
        button.setAllCaps(false); // Disable caps to support html
        button.setTextColor(Color.WHITE);
        button.setText(Html.fromHtml(featName));

        // Ustaw zadane właściwości dla przycisku z linkiem
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setCornerRadius(10); // Zaokrąglone rogi
        shape.setColor(Color.BLACK); // Kolor tła
        shape.setStroke(2, Color.parseColor("#9000FFFF"));// Białe obramowanie
        button.setBackground(shape);
        button.setTextColor(Color.WHITE);

        button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setData(Uri.parse(url));
                    context.startActivity(intent);
                }
            });

        linLayout.addView(button);
    }

    private View ButtonOnOff(final int featNum, String featName, boolean switchedOn) {
        final Button button = new Button(getContext);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(MATCH_PARENT,MATCH_PARENT);
        layoutParams.setMargins(dp(5),dp(2),dp(5),dp(2));
        button.setLayoutParams(layoutParams);
        button.setTextSize(12.5f);
        button.setTextColor(Color.WHITE);
        GradientDrawable gdMenuBody = new GradientDrawable();
        gdMenuBody.setColor(Color.parseColor("#90FF00FF"));
        gdMenuBody.setCornerRadius(dp(10));
        gdMenuBody.setStroke(dp(2), Color.parseColor("#FF5000FF"));
        button.setGravity(Gravity.CENTER);
        button.setBackground(gdMenuBody);
        button.setPadding(dp(0),dp(3),dp(0),dp(3));
		//    button.setTypeface(Typeface.createFromAsset(getAssets(), "SakuraMiku/DaisyFonts/DaisyFont2.ttf"));
        final String finalfeatName = featName.replace("OnOff_", "");
        boolean isOn = Preferences.loadPrefBool(featName, featNum, switchedOn);
        if (isOn) {
            button.setText(finalfeatName + "\n ON ");
            gdMenuBody = new GradientDrawable();
            gdMenuBody.setColor(Color.parseColor("#9000FFFF"));
            gdMenuBody.setCornerRadius(dp(10));
            gdMenuBody.setStroke(dp(2), Color.parseColor("#FF5000FF"));
            button.setBackground(gdMenuBody);
            isOn = false;
        } else {
            button.setText(finalfeatName + "\n OFF ");
            gdMenuBody = new GradientDrawable();
            gdMenuBody.setColor(Color.parseColor("#90FF00FF"));
            gdMenuBody.setCornerRadius(dp(10));
            gdMenuBody.setStroke(dp(2), Color.parseColor("#FF5000FF"));
            button.setBackground(gdMenuBody);
            isOn = true;
        }
        final boolean finalIsOn = isOn;
        button.setOnClickListener(new View.OnClickListener() {
				boolean isOn = finalIsOn;

				public void onClick(View v) {
					Preferences.changeFeatureBool(finalfeatName, featNum, isOn);
					//Log.d(TAG, finalfeatName + " " + featNum + " " + isActive2);
					if (isOn) {
						//playSound("On.ogg");
						button.setText(finalfeatName + "\n ON ");
						GradientDrawable gdMenuBody = new GradientDrawable();
						gdMenuBody.setColor(Color.parseColor("#9000FFFF"));
						gdMenuBody.setCornerRadius(dp(10));
						gdMenuBody.setStroke(dp(2), Color.parseColor("#FF5000FF"));
						button.setBackground(gdMenuBody);
						isOn = false;
					} else {
						//	playSound("Off.ogg");
						button.setText(finalfeatName + "\n OFF ");
						GradientDrawable gdMenuBody = new GradientDrawable();
						gdMenuBody.setColor(Color.parseColor("#90FF00FF"));
						gdMenuBody.setCornerRadius(dp(10));
						gdMenuBody.setStroke(dp(2), Color.parseColor("#FF5000FF"));
						button.setBackground(gdMenuBody);
						isOn = true;
					}
				}
			});
        return button;
    }

    private void Spinner(LinearLayout linLayout, final int featNum, final String featName, final String list) {
        Log.d(TAG, "spinner " + featNum + " " + featName + " " + list);
        final List<String> lists = new LinkedList<>(Arrays.asList(list.split(",")));

        // Create another LinearLayout as a workaround to use it as a background
        // to keep the down arrow symbol. No arrow symbol if setBackgroundColor set
        LinearLayout linearLayout2 = new LinearLayout(getContext);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT);
        layoutParams2.setMargins(7, 2, 7, 2);
        linearLayout2.setOrientation(LinearLayout.VERTICAL);
        linearLayout2.setBackgroundColor(BTN_COLOR);
        linearLayout2.setLayoutParams(layoutParams2);

        final Spinner spinner = new Spinner(getContext, Spinner.MODE_DROPDOWN);
        spinner.setLayoutParams(layoutParams2);
        spinner.getBackground().setColorFilter(1, PorterDuff.Mode.SRC_ATOP); //trick to show white down arrow color
        //Creating the ArrayAdapter instance having the list
        ArrayAdapter aa = new ArrayAdapter(getContext, android.R.layout.simple_spinner_dropdown_item, lists);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner'
        spinner.setAdapter(aa);
        spinner.setSelection(Preferences.loadPrefInt(featName, featNum));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Preferences.changeFeatureInt(spinner.getSelectedItem().toString(), featNum, position);
                ((TextView) parentView.getChildAt(0)).setTextColor(TEXT_COLOR_2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        linearLayout2.addView(spinner);
        linLayout.addView(linearLayout2);
    }

    private void InputNum(LinearLayout linLayout, final int featNum, final String featName, final int maxValue) {
        LinearLayout linearLayout = new LinearLayout(getContext);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT);
        layoutParams.setMargins(7, 5, 7, 5);

        final Button button = new Button(getContext);
        int num = Preferences.loadPrefInt(featName, featNum);
        button.setText(Html.fromHtml(featName + ": <font color='" + NumberTxtColor + "'>" + ((num == 0) ? 1 : num) + "</font>"));
        button.setAllCaps(false);
        button.setLayoutParams(layoutParams);
        button.setBackgroundColor(BTN_COLOR);
        button.setTextColor(TEXT_COLOR_2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertName = new AlertDialog.Builder(getContext);
                final EditText editText = new EditText(getContext);
                if (maxValue != 0)
                    editText.setHint("Max value: " + maxValue);
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                editText.setKeyListener(DigitsKeyListener.getInstance("0123456789-"));
                InputFilter[] FilterArray = new InputFilter[1];
                FilterArray[0] = new InputFilter.LengthFilter(10);
                editText.setFilters(FilterArray);
                editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        InputMethodManager imm = (InputMethodManager) getContext.getSystemService(getContext.INPUT_METHOD_SERVICE);
                        if (hasFocus) {
                            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                        } else {
                            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                        }
                    }
                });
                editText.requestFocus();

                alertName.setTitle("Input number");
                alertName.setView(editText);
                LinearLayout layoutName = new LinearLayout(getContext);
                layoutName.setOrientation(LinearLayout.VERTICAL);
                layoutName.addView(editText); // displays the user input bar
                alertName.setView(layoutName);

                alertName.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        int num;
                        try {
                            num = Integer.parseInt(TextUtils.isEmpty(editText.getText().toString()) ? "0" : editText.getText().toString());
                            if (maxValue != 0 && num >= maxValue)
                                num = maxValue;
                        } catch (NumberFormatException ex) {
                            if (maxValue != 0)
                                num = maxValue;
                            else
                                num = 2147483640;
                        }

                        button.setText(Html.fromHtml(featName + ": <font color='" + NumberTxtColor + "'>" + num + "</font>"));
                        Preferences.changeFeatureInt(featName, featNum, num);

                        editText.setFocusable(false);
                    }
                });

                alertName.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // dialog.cancel(); // closes dialog
                        InputMethodManager imm = (InputMethodManager) getContext.getSystemService(getContext.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                    }
                });

                if (overlayRequired) {
                    AlertDialog dialog = alertName.create(); // display the dialog
                    dialog.getWindow().setType(Build.VERSION.SDK_INT >= 26 ? 2038 : 2002);
                    dialog.show();
                } else {
                    alertName.show();
                }
            }
        });

        linearLayout.addView(button);
        linLayout.addView(linearLayout);
    }

    private void InputText(LinearLayout linLayout, final int featNum, final String featName) {
        LinearLayout linearLayout = new LinearLayout(getContext);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT);
        layoutParams.setMargins(7, 5, 7, 5);

        final Button button = new Button(getContext);

        String string = Preferences.loadPrefString(featName, featNum);
        button.setText(Html.fromHtml(featName + ": <font color='" + NumberTxtColor + "'>" + string + "</font>"));

        button.setAllCaps(false);
        button.setLayoutParams(layoutParams);
        button.setBackgroundColor(BTN_COLOR);
        button.setTextColor(TEXT_COLOR_2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertName = new AlertDialog.Builder(getContext);

                final EditText editText = new EditText(getContext);
                editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        InputMethodManager imm = (InputMethodManager) getContext.getSystemService(getContext.INPUT_METHOD_SERVICE);
                        if (hasFocus) {
                            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                        } else {
                            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                        }
                    }
                });
                editText.requestFocus();

                alertName.setTitle("ضع الباس هنا");
                alertName.setView(editText);
                LinearLayout layoutName = new LinearLayout(getContext);
                layoutName.setOrientation(LinearLayout.VERTICAL);
                layoutName.addView(editText); // displays the user input bar
                alertName.setView(layoutName);

                alertName.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String str = editText.getText().toString();
                        button.setText(Html.fromHtml(featName + ": <font color='" + NumberTxtColor + "'>" + str + "</font>"));
                        Preferences.changeFeatureString(featName, featNum, str);
                        editText.setFocusable(false);
                    }
                });

                alertName.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //dialog.cancel(); // closes dialog
                        InputMethodManager imm = (InputMethodManager) getContext.getSystemService(getContext.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                    }
                });


                if (overlayRequired) {
                    AlertDialog dialog = alertName.create(); // display the dialog
                    dialog.getWindow().setType(Build.VERSION.SDK_INT >= 26 ? 2038 : 2002);
                    dialog.show();
                } else {
                    alertName.show();
                }
            }
        });

        linearLayout.addView(button);
        linLayout.addView(linearLayout);
    }

    private void CheckBox(LinearLayout linLayout, final int featNum, final String featName, boolean switchedOn) {
        final CheckBox checkBox = new CheckBox(getContext);
        checkBox.setText(featName);
        checkBox.setTextColor(Color.WHITE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            checkBox.setButtonTintList(ColorStateList.valueOf(CheckBoxColor));
        checkBox.setChecked(Preferences.loadPrefBool(featName, featNum, switchedOn));
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkBox.isChecked()) {
                    Preferences.changeFeatureBool(featName, featNum, isChecked);
                } else {
                    Preferences.changeFeatureBool(featName, featNum, isChecked);
                }
            }
        });
        linLayout.addView(checkBox);
    }

    private void RadioButton(LinearLayout linLayout, final int featNum, String featName, final String list) {
        //Credit: LoraZalora
        final List<String> lists = new LinkedList<>(Arrays.asList(list.split(",")));

        final TextView textView = new TextView(getContext);
        textView.setText(featName + ":");
        textView.setTextColor(TEXT_COLOR_2);

        final RadioGroup radioGroup = new RadioGroup(getContext);
        radioGroup.setPadding(10, 5, 10, 5);
        radioGroup.setOrientation(LinearLayout.VERTICAL);
        radioGroup.addView(textView);

        for (int i = 0; i < lists.size(); i++) {
            final RadioButton Radioo = new RadioButton(getContext);
            final String finalfeatName = featName, radioName = lists.get(i);
            View.OnClickListener first_radio_listener = new View.OnClickListener() {
                public void onClick(View v) {
                    textView.setText(Html.fromHtml(finalfeatName + ": <font color='" + NumberTxtColor + "'>" + radioName));
                    Preferences.changeFeatureInt(finalfeatName, featNum, radioGroup.indexOfChild(Radioo));
                }
            };
            System.out.println(lists.get(i));
            Radioo.setText(lists.get(i));
            Radioo.setTextColor(Color.LTGRAY);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                Radioo.setButtonTintList(ColorStateList.valueOf(RadioColor));
            Radioo.setOnClickListener(first_radio_listener);
            radioGroup.addView(Radioo);
        }

        int index = Preferences.loadPrefInt(featName, featNum);
        if (index > 0) { //Preventing it to get an index less than 1. below 1 = null = crash
            textView.setText(Html.fromHtml(featName + ": <font color='" + NumberTxtColor + "'>" + lists.get(index - 1)));
            ((RadioButton) radioGroup.getChildAt(index)).setChecked(true);
        }
        linLayout.addView(radioGroup);
    }

    private void Collapse(LinearLayout linLayout, final String text) {
        LinearLayout.LayoutParams layoutParamsLL = new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT);
        layoutParamsLL.setMargins(dp(5),dp(2),dp(5),dp(2));

        LinearLayout collapse = new LinearLayout(getContext);
        collapse.setLayoutParams(layoutParamsLL);
        collapse.setVerticalGravity(Gravity.CENTER);
        collapse.setOrientation(LinearLayout.VERTICAL);
        final GradientDrawable gdMenuBody2 = new GradientDrawable();
        gdMenuBody2.setColor(Color.parseColor("#20000000"));
        gdMenuBody2.setCornerRadius(dp(10));
        gdMenuBody2.setStroke(dp(2), Color.parseColor("#FFFF00FF"));
        collapse.setBackgroundDrawable(gdMenuBody2);

        final LinearLayout collapseSub = new LinearLayout(getContext);
        collapseSub.setVerticalGravity(Gravity.CENTER);
        collapseSub.setPadding(dp(4),dp(4),dp(4),dp(4));
        collapseSub.setOrientation(LinearLayout.VERTICAL);
        collapseSub.setBackgroundColor(Color.TRANSPARENT);
        collapseSub.setVisibility(View.GONE);
        mCollapse = collapseSub;

        final Button textView = new Button(getContext);
        textView.setText("▽ " + text + " ▽");
        textView.setBackgroundColor(Color.TRANSPARENT);
        textView.setGravity(Gravity.CENTER);
			textView.setTextColor(Color.WHITE);
			textView.setTextSize(12.5f);
        textView.setSingleLine(true);
        textView.setAllCaps(true);
        textView.setShadowLayer(dp(12),dp(1),dp(1), Color.parseColor("#FF000000"));
				textView.setPadding(dp(0),dp(3),dp(0),dp(3));
				//  textView.setTypeface(Typeface.createFromAsset(getContext(), "SakuraMiku/DaisyFonts/DaisyFont7.ttf"));
				textView.setOnClickListener(new View.OnClickListener() {
				boolean isChecked;

            @Override
            public void onClick(View v) {

                boolean z = !isChecked;
                isChecked = z;
                if (z) {
                    collapseSub.setVisibility(View.VISIBLE);
                    textView.setText("△ " + text + " △");
                    return;
                }
                collapseSub.setVisibility(View.GONE);
                textView.setText("▽ " + text + " ▽");
            }
        });
        collapse.addView(textView);
        collapse.addView(collapseSub);
        linLayout.addView(collapse);
    }

	private View Category(String text) {
        int upColor = Color.parseColor("#90FF00FF");
        int downColor = Color.parseColor("#9000FFFF");
        TextView textView = new TextView(getContext);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT);
        layoutParams.setMargins(dp(4),dp(2),dp(4),dp(2));
        textView.setLayoutParams(layoutParams);
        textView.setText(text);
		//new Titanic().start(textView);
        textView.setGravity(Gravity.CENTER);
        textView.setShadowLayer(dp(12),dp(1),dp(1), Color.parseColor("#FF000000"));
        textView.setTextSize(15.5f);
        textView.setSingleLine(true);
        textView.setAllCaps(true);
        GradientDrawable gdMenuBody = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{upColor, downColor});
        gdMenuBody.setCornerRadius(dp(5));
        gdMenuBody.setStroke(dp(2), Color.parseColor("#FF5000FF"));
        textView.setBackground(gdMenuBody);
        textView.setTextColor(Color.WHITE);
		//   textView.setTypeface(Typeface.createFromAsset(getAssets(), "SakuraMiku/DaisyFonts/DaisyFont4.ttf"));
        textView.setPadding(dp(7),dp(3),dp(7),dp(3));
        return textView;
    }
	private void TextView(LinearLayout linLayout, String text) {
        TextView textView = new TextView(getContext);
        textView.setText(Html.fromHtml(text));
        textView.setTextColor(TEXT_COLOR_2);
        textView.setPadding(10, 5, 10, 5);
        linLayout.addView(textView);
    }

    private void WebTextView(LinearLayout linLayout, String text) {
        WebView wView = new WebView(getContext);
        wView.loadData(text, "text/html", "utf-8");
        wView.setBackgroundColor(0x00000000); //Transparent
        wView.setPadding(0, 5, 0, 5);
        wView.getSettings().setAppCacheEnabled(false);
        linLayout.addView(wView);
    }
	

    public String Checker(String user, String pass) {
		String result = "invalid";
		try {
			URL url = new URL("https://raw.githubusercontent.com/felix001qq/Opr/refs/heads/main/Login.json");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setDoInput(true);

			InputStream is = conn.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			StringBuilder sb = new StringBuilder();
			String line;

			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}

			reader.close();
			is.close();
			conn.disconnect();

			// Parse JSON array
			JSONArray jsonArray = new JSONArray(sb.toString());

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				String u = obj.getString("user").trim();
				String p = obj.getString("pass").trim();
				String type = obj.getString("type").trim();

				// Use equalsIgnoreCase to avoid case sensitivity issues
				if (u.equalsIgnoreCase(user.trim()) && p.equalsIgnoreCase(pass.trim())) {
					result = type; // "vip" or "free"
					break;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			result = "error";
		}

		return result;
	}
	
	private void logindialog() {
		new Thread(new Runnable() {
				public void run() {
					final String[] vipData = Vipdata(); // [username, password]
					final String result = Checker(vipData[0], vipData[1]);

					new Handler(Looper.getMainLooper()).post(new Runnable() {
							public void run() {
								currentVipUser = vipData[0];
								currentVipPass = vipData[1];
								vipStatus = result; // store it globally
								if (getContext == null) {
									Log.e("LoginDialog", "Context is null");
									return;
								}

								if (result.equalsIgnoreCase("vip")) {
									Toast.makeText(getContext, "VIP Activated", Toast.LENGTH_SHORT).show();
								} else if (result.equalsIgnoreCase("free")) {
									Toast.makeText(getContext, "Free account logged in", Toast.LENGTH_SHORT).show();
								} else if (result.equalsIgnoreCase("error")) {
									Toast.makeText(getContext, "Login error occurred", Toast.LENGTH_SHORT).show();
								} else {
									Toast.makeText(getContext, "Login failed", Toast.LENGTH_SHORT).show();
									return;
								}

								mods.removeAllViews();
								featureList(GetFeatureList(), mods); // available to both VIP and Free
							}
						});
				}
			}).start();
	}
	private void showVipDialog() {
		AlertDialog.Builder alert = new AlertDialog.Builder(getContext);
		alert.setTitle("Feature Locked");
		alert.setMessage("This feature is not available for free users.\n\nContact us to upgrade to VIP.");
		alert.setPositiveButton("Contact", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					String telegram = "https://t.me/YourChannel"; // your contact link
					Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(telegram));
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					getContext.startActivity(intent);
				}
			});
		alert.setNegativeButton("Cancel", null);

		AlertDialog dialog = alert.create();
		if (Build.VERSION.SDK_INT >= 26) {
			dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
		} else {
			dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_PHONE);
		}
		dialog.show();
	}

	private boolean isFeatureLocked(String featName) {
		String signature = "[VIP]";
		if (featName.contains(signature) && !vipStatus.equalsIgnoreCase("vip")) {
			showVipDialog();
			return true;
		}
		return false;
	}

	public static String cleanFeatureName(String featName) {
		return featName.replace("[VIP]", "").trim();
	}
	

    private boolean isViewCollapsed() {
        return rootFrame == null || mCollapsed.getVisibility() == View.VISIBLE;
    }

    //For our image a little converter
    private int convertDipToPixels(int i) {
        return (int) ((((float) i) * getContext.getResources().getDisplayMetrics().density) + 0.5f);
    }

    private int dp(int i) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) i, getContext.getResources().getDisplayMetrics());
    }

    public void setVisibility(int view) {
        if (rootFrame != null) {
            rootFrame.setVisibility(view);
        }
    }

    public void onDestroy() {
        if (rootFrame != null) {
            mWindowManager.removeView(rootFrame);
        }
    }
}
