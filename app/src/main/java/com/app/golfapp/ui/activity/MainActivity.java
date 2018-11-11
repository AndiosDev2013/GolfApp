package com.app.golfapp.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatTextView;
import android.text.Html;
import android.widget.Toast;

import com.app.golfapp.AppConstant;
import com.app.golfapp.AppPreferences;
import com.app.golfapp.GolfApp;
import com.app.golfapp.R;
import com.app.golfapp.model.CCoord;
import com.app.golfapp.model.Course;
import com.app.golfapp.model.Facility;
import com.app.golfapp.model.Hole;
import com.app.golfapp.utils.CommonUtil;
import com.app.golfapp.utils.ResourceUtil;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.geometry.LatLngBounds;
import com.mapbox.mapboxsdk.geometry.LatLngQuad;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.style.layers.RasterLayer;
import com.mapbox.mapboxsdk.style.layers.TransitionOptions;
import com.mapbox.mapboxsdk.style.sources.ImageSource;
import com.mapbox.mapboxsdk.style.sources.Source;
import com.stanfy.gsonxml.GsonXml;
import com.stanfy.gsonxml.GsonXmlBuilder;
import com.stanfy.gsonxml.XmlParserCreator;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class MainActivity extends BaseActivity {
    //
    public static MainActivity instance;
    //
    @BindView(R.id.mapView)
    MapView mapView;
    @BindView(R.id.txt_course_number)
    AppCompatTextView txt_course_number;
    @BindView(R.id.txt_course_name)
    AppCompatTextView txt_course_name;
    @BindView(R.id.txt_hole_number)
    AppCompatTextView txt_hole_number;
    @BindView(R.id.txt_hole_name)
    AppCompatTextView txt_hole_name;

    //
    MapboxMap mMap;
    Facility mFacility;
    int mCurrentCourseIndex = 0;
    int mCurrentHoleIndex = 0;
    //
    private static final String ID_IMAGE_SOURCE = "animated_image_source";
    private static final String ID_IMAGE_LAYER = "animated_image_layer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        instance = this;
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // read data from xml
        XmlParserCreator parserCreator = () -> {
            try {
                return XmlPullParserFactory.newInstance().newPullParser();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };

        GsonXml gsonXml = new GsonXmlBuilder()
                .setXmlParserCreator(parserCreator)
                .setSameNameLists(true)
                .create();

        String data = ResourceUtil.readTextFileFromAsset(instance, "bayview_golf_club.xml");
        try {
            mFacility = gsonXml.fromXml(data, Facility.class);
        } catch (Exception e) {
            e.printStackTrace();

            new AlertDialog.Builder(instance)
                    .setTitle(R.string.Error)
                    .setMessage(e.getMessage())
                    .setPositiveButton(R.string.OK, (dialog, which) -> {
                        finish();
                    })
                    .show();

            return;
        }

        if (mFacility.courses == null || mFacility.courses.isEmpty()) {
            Toasty.error(instance, getString(R.string.msg_error_no_course), Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        //
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync((mapboxMap) -> {
            mMap = mapboxMap;
            showHoleMap();
        });

        //
        openRating();
    }


    @OnClick({ R.id.txt_course_name_title, R.id.txt_course_name, R.id.txt_course_number_title, R.id.txt_course_number } )
    public void onCourse() {
        String[] courseNames = new String[mFacility.courses.size()];
        for (int i = 0; i < mFacility.courses.size(); i++)
            courseNames[i] = mFacility.courses.get(i).name;

        new AlertDialog.Builder(instance)
                .setTitle(R.string.dlg_select_course)
                .setSingleChoiceItems(courseNames, mCurrentCourseIndex, (dialog, which) -> {
                    dialog.dismiss();

                    if (mCurrentCourseIndex != which) {
                        mCurrentCourseIndex = which;
                        mCurrentHoleIndex = 0;
                        showHoleMap();
                    }
                })
                .show();
    }

    @OnClick(R.id.img_before)
    public void selectPrevHole() {
        mCurrentHoleIndex--;
        if (mCurrentHoleIndex < 0) {
            Toasty.error(instance, getString(R.string.msg_error_no_hole), Toast.LENGTH_SHORT).show();
            mCurrentHoleIndex = 0;

        } else {
            showHoleMap();
        }
    }

    @OnClick(R.id.img_next)
    public void selectNextHole() {
        mCurrentHoleIndex++;
        if (mCurrentHoleIndex >= mFacility.courses.get(mCurrentCourseIndex).holes.size()) {
            Toasty.error(instance, getString(R.string.msg_error_no_hole), Toast.LENGTH_SHORT).show();
            mCurrentHoleIndex = mFacility.courses.get(mCurrentCourseIndex).holes.size() - 1;
        } else {
            showHoleMap();
        }
    }

    @OnClick(R.id.img_setting)
    public void onSetting() {
        Intent intent = new Intent(instance, SettingActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        onBackButtonPressed();
    }

    boolean isBackAllowed = false;

    private void onBackButtonPressed() {
        if (!isBackAllowed) {
            Toasty.info(instance, getString(R.string.msg_alert_on_back_pressed), Toast.LENGTH_SHORT).show();
            isBackAllowed = true;

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    isBackAllowed = false;
                }
            }, AppConstant.DELAY_EXIT);

        } else {
            finish();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    private void openRating() {
        boolean rateEnable = AppPreferences.getBool(AppPreferences.KEY.RATE_ENABLE, true);
        if (rateEnable) {
            int count = AppPreferences.getInt(AppPreferences.KEY.APP_LAUNCH_COUNT, 1);
            if (count % 3 == 0) {
                new AlertDialog.Builder(instance)
                        .setIcon(R.drawable.app_icon)
                        .setTitle(String.format(Locale.getDefault(), getString(R.string.dlg_rate_title), getString(R.string.app_name)))
                        .setMessage(Html.fromHtml(getString(R.string.dlg_rate_message)))
                        .setPositiveButton(R.string.dlg_rate_btn_rate_now, (dialog, which) -> {
                            CommonUtil.launchMarket(GolfApp.getAppPackageName());
                        })
                        .setNegativeButton(R.string.dlg_rate_btn_rate_later, null)
                        .setNeutralButton(R.string.dlg_rate_btn_no_rate, (dialog, which) -> {
                            AppPreferences.setBool(AppPreferences.KEY.RATE_ENABLE, false);
                        })
                        .show();
            }

            count++;
            AppPreferences.setInt(AppPreferences.KEY.APP_LAUNCH_COUNT, count);
        }
    }

    public void showHoleMap() {
        if (mMap == null)
            return;

        mMap.clear();

        //
        Course course = mFacility.courses.get(mCurrentCourseIndex);
        Hole hole = course.holes.get(mCurrentHoleIndex);

        // show info
        txt_course_number.setText(String.format(Locale.getDefault(), "%d", mCurrentCourseIndex + 1));
        txt_course_name.setText(course.name);
        txt_hole_number.setText(String.format(Locale.getDefault(), "%d", mCurrentHoleIndex + 1));
        txt_hole_name.setText(hole.label);

        // move camera
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        LatLng c1 = CCoord.parse(hole.c1);
        LatLng c2 = CCoord.parse(hole.c2);
        LatLng c3 = CCoord.parse(hole.c3);
        LatLng c4 = CCoord.parse(hole.c4);

        builder.include(c1);
        builder.include(c2);
        builder.include(c3);
        builder.include(c4);

        LatLngBounds bounds = builder.build();
        mMap.easeCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 10), 1000);

        // overlap image
        String assetFile = String.format(Locale.getDefault(), "%d/h%d.png", mCurrentCourseIndex + 1, mCurrentHoleIndex + 1);
        Bitmap bitmap = ResourceUtil.getBitmapFromAsset(instance, assetFile);

        LatLngQuad quad = new LatLngQuad(c4, c3, c2, c1);

        ImageSource source = (ImageSource) mMap.getSource(ID_IMAGE_SOURCE);
        if (source == null) {
            // add image
            mMap.addSource(new ImageSource(ID_IMAGE_SOURCE, quad, bitmap));
            // add layer
            RasterLayer layer = new RasterLayer(ID_IMAGE_LAYER, ID_IMAGE_SOURCE);
            mMap.addLayer(layer);

        } else {
            source.setCoordinates(quad);
            source.setImage(bitmap);
        }
    }
}