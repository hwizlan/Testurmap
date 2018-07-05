package com.example.hilrudt.testurmmap;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.LocaleList;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.esri.arcgisruntime.geometry.GeometryEngine;
import com.esri.arcgisruntime.geometry.Multipoint;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.PointCollection;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.layers.SublayerList;
import com.esri.arcgisruntime.loadable.LoadStatus;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.LayerList;
import com.esri.arcgisruntime.mapping.MobileMapPackage;
import com.esri.arcgisruntime.mapping.Viewpoint;
import com.esri.arcgisruntime.mapping.view.DefaultMapViewOnTouchListener;
import com.esri.arcgisruntime.mapping.view.DrawStatus;
import com.esri.arcgisruntime.mapping.view.DrawStatusChangedEvent;
import com.esri.arcgisruntime.mapping.view.DrawStatusChangedListener;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;
import com.esri.arcgisruntime.symbology.TextSymbol;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private MapView mMapView;
    public ProgressBar progressBar;

//    public BarChart mChart;
//    public List<BarEntry> dataBar = new ArrayList<>();

    private BarChart mChart;
    private ArrayList<BarEntry> yValue;
    private ArrayList<String> xValue;

    public ListView chart2;
    public Button chart1chart2;
    public Button chart2chart1;
    public HashMap<String, String> hashMap;

    public ArrayList<HashMap<String, String>> catList;

    public long startTimeDrawing;
    public long finishTimeDrawing;
    public long difTimeDrawing;

    public int barCount;
    public String barCountS;
    public float f;
    public float full;
    public float fAverage;

    public ArrayList<Float> medianArray;
    public float medianValue;

    public ArrayList<Float> medianBarArray;
    public float medianBarValue;

    public double scale1;
    public double scale2;
    public double scale3Ten;

    public TextView lastT;
    public TextView averageT;
    public TextView Masstab;
    public TextView coordinates;
    public String lastTS;
    public String averageTS;
    public String MasstabS;

    public Button btnHideChart;
    public boolean hide;

    public Button btnTestTenP;
    public FloatingActionButton startTestTenPoint;
    public FloatingActionButton deletePoints;
    public FloatingActionButton removeLastPoint;
    public boolean testTenP;
    public boolean testTenPBlock;
    public int testTenPCount;
    public int masstabTenPCount;
    public GraphicsOverlay graphicsOverlay;

    public PointCollection stateCapitalsPST;

    public Button btnTestOneP;
    public boolean testOneP;

    public Button btnTestFree;
    public boolean testFree=true;

    public Button layers;
    public boolean layersBool;



    public Intent mapinfo;

    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final String TAG = "MainAct";

    public ListView layerListBase;
    public ListView layerListOperational;
    public TextView srtLayerBase;
    public TextView srtLayerOperational;

    private static final String SHAG = "catname"; // Верхний текст
    private static final String MASSTAB = "description"; // ниже главного
    private static final String COORDINAT = "icon";  // будущая картинка
    private static final String VREMYA = "vrem";  // будущая картинка

    public int colorChart = ColorTemplate.MATERIAL_COLORS[3];



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArcGISRuntimeEnvironment.setLicense("runtimelite,1000,rud5613437765,none,6PB3LNBHPDJP6XCFK224");
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mMapView = findViewById(R.id.mapView);
        progressBar = findViewById(R.id.progressBar);
        lastT = findViewById(R.id.lastT);
        averageT = findViewById(R.id.averageT);
        Masstab = findViewById(R.id.Masstab);
        coordinates = findViewById(R.id.coordanites);

        mChart = findViewById(R.id.chart);
        chart2 = findViewById(R.id.chart2);
        chart1chart2 = findViewById(R.id.chart1chart2);
        chart2chart1 = findViewById(R.id.chart2chart1);

        btnHideChart =findViewById(R.id.hideChart);
        btnTestTenP =findViewById(R.id.testTenP);
        btnTestOneP =findViewById(R.id.testOneP);
        btnTestFree =findViewById(R.id.testFree);
        layers=findViewById(R.id.layers);

        startTestTenPoint = findViewById(R.id.startTenP);
        deletePoints = findViewById(R.id.deletePoints);
        removeLastPoint = findViewById(R.id.removeLastPoint);

        layerListBase = (findViewById(R.id.layerListBase));
        layerListOperational = (findViewById(R.id.layerListOperational));

        srtLayerBase = findViewById(R.id.srtLayerBase);
        srtLayerOperational = findViewById(R.id.srtOperatonalBas);

        initFileManager();


        setupMobileMap();






//        GraphicsOverlay graphicsOverlay = new GraphicsOverlay();
//        mMapView.getGraphicsOverlays().add(graphicsOverlay);
//        SimpleMarkerSymbol symbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.RED, 12); //size 12, style of circle
//        Point graphicPoint = new Point(55.941015, 54.724915, SpatialReferences.getWgs84());
//        Graphic graphic = new Graphic(graphicPoint, symbol);
//        graphicsOverlay.getGraphics().add(graphic);


        drawProgress();


        yValue = new ArrayList<>();
        xValue = new ArrayList<>();
        catList = new ArrayList<>();

        medianArray = new ArrayList<>();
        medianBarArray = new ArrayList<>();

        hashMap = new HashMap<>();
        hashMap.put(SHAG, "Шаг"); // Шаг
        hashMap.put(MASSTAB, "Масштаб"); // Масштаб
        hashMap.put(COORDINAT, "Координаты"); // Время
        hashMap.put(VREMYA, "Время"); // Масштаб
        catList.add(hashMap);



        stateCapitalsPST = new PointCollection(SpatialReferences.getWgs84());

        View.OnClickListener btnHide = new View.OnClickListener() { // Показать график
            @Override
            public void onClick(View v) {
                layersBool=false;
                enableTestTenPFalse();
                hide=!hide;
                if (hide==true) {


                    hideElementsTrue();


                }
                if (hide==false) {
                    hideElementsFalse();}
            }
        };
        btnHideChart.setOnClickListener(btnHide);



        View.OnClickListener chart1chart2C = new View.OnClickListener() { // Показать второй график
            @Override
            public void onClick(View v) {


                mChart.setVisibility(View.INVISIBLE);
                chart1chart2.setVisibility(View.INVISIBLE);
                chart2.setVisibility(View.VISIBLE);
                chart2chart1.setVisibility(View.VISIBLE);

            }
        };
        chart1chart2.setOnClickListener(chart1chart2C);



        View.OnClickListener chart2chart1C = new View.OnClickListener() { // Скрыть второй график
            @Override
            public void onClick(View v) {

                chart2.setVisibility(View.INVISIBLE);
                chart2chart1.setVisibility(View.INVISIBLE);
                mChart.setVisibility(View.VISIBLE);
                chart1chart2.setVisibility(View.VISIBLE);

            }
        };
        chart2chart1.setOnClickListener(chart2chart1C);



        View.OnClickListener btnTestTenPC = new View.OnClickListener() { // Тест 10 точек
            @Override
            public void onClick(View v) {

                testTenPBlock=!testTenPBlock;
                if (testTenPBlock==true) {

                    enableTestTenPTrue();}
                if (testTenPBlock==false) {
                    enableTestTenPFalse();
                    mMapView.getGraphicsOverlays().clear();
                    stateCapitalsPST.clear();
                }


            }
        };
        btnTestTenP.setOnClickListener(btnTestTenPC);


        View.OnClickListener startBtnTestTenPC = new View.OnClickListener() { // Пуск теста 10и точек
            @Override
            public void onClick(View v) {

                startTestTenPoint();



//                double scale3 = mMapView.getCurrentViewpoint(Viewpoint.Type.CENTER_AND_SCALE).getTargetScale();
//
//                mMapView.setViewpointCenterAsync(Double.parseDouble(arrayXPoints.get(0).toString()), Double.parseDouble(arrayYPoints.get(0).toString()));

//                testOnePoint();
            }
        };
        startTestTenPoint.setOnClickListener(startBtnTestTenPC);


        View.OnClickListener deleteTenPC = new View.OnClickListener() { // Удалить все точки
            @Override
            public void onClick(View v) {

                mMapView.getGraphicsOverlays().clear();
                stateCapitalsPST.clear();

//                deletePoints.setText(getString(R.string.detelePoints));

            }
        };
        deletePoints.setOnClickListener(deleteTenPC);


        View.OnClickListener deleteLastPC = new View.OnClickListener() { // Удалить последную точки
            @Override
            public void onClick(View v) {

                removeLastPoint ();

//                deletePoints.setText(getString(R.string.detelePoints));

            }
        };
        removeLastPoint.setOnClickListener(deleteLastPC);



        View.OnClickListener btnTestOnePC = new View.OnClickListener() { // Тест одной точки
            @Override
            public void onClick(View v) {
                enableTestTenPFalse();
                testOnePoint();
                testFree=false;
                btnTestOneP.setTextColor(getResources().getColor(R.color.buttonPressOn));

                btnTestTenP.setTextColor(getResources().getColor(R.color.buttonPressOff));
                btnHideChart.setTextColor(getResources().getColor(R.color.buttonPressOff));
                layers.setTextColor(getResources().getColor(R.color.buttonPressOff));
            }
        };
        btnTestOneP.setOnClickListener(btnTestOnePC);


        View.OnClickListener btnLayers = new View.OnClickListener() { // Кнопка слои
            @Override
            public void onClick(View v) {

                enableTestTenPFalse();
                hide=false;
                layersBool=!layersBool;
                if (layersBool==true) {
                    showLayerListTrue(); }
                if (layersBool==false) {
                    showLayerListFalse(); }
            }
        };
        layers.setOnClickListener(btnLayers);




//        View.OnClickListener btnTestFreeC = new View.OnClickListener() { // Тест Свободный
//            @Override
//            public void onClick(View v) {
//                testFree();
//            }
//        };
//        btnTestFree.setOnClickListener(btnTestFreeC);


        btnTestFree.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    enableTestTenPFalse();
                    mMapView.getGraphicsOverlays().clear();
                    stateCapitalsPST.clear();
                    medianBarArray.clear();
                    medianArray.clear();
                    testFree=true;
                    btnTestFree.setTextColor(getResources().getColor(R.color.buttonPressOn));
                    testFree();
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    btnTestFree.setTextColor(getResources().getColor(R.color.buttonPressOff));
                    btnTestOneP.setTextColor(getResources().getColor(R.color.buttonPressOff));
                    btnTestTenP.setTextColor(getResources().getColor(R.color.buttonPressOff));
                    btnHideChart.setTextColor(getResources().getColor(R.color.buttonPressOff));
                    layers.setTextColor(getResources().getColor(R.color.buttonPressOff));
                }
                return false;
            }
        });

//        layers();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.file_map) {

                Intent intentOpenmap = new Intent(MainActivity.this, Openmap.class);
                startActivity(intentOpenmap);

            // Handle the camera action
        } else if (id == R.id.map_info) {


            startActivity(mapinfo);

        } else if (id == R.id.basemap) {
            setupMap();

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, "Permission granted!");
            } else {
                Log.i(TAG, "Permission denied");
                requestPermissions(); // Запрашиваем ещё раз
            }
        }
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                PERMISSION_REQUEST_CODE
        );
    }

    private void initFileManager() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            // Разрешение предоставлено
//            setupMobileMap();
        } else {
            requestPermissions();
        }
    }



    private void setupMap() {
        if (mMapView != null) {
            Basemap.Type basemapType = Basemap.Type.OPEN_STREET_MAP  ;
            double latitude = 54.724915;
            double longitude = 55.941015;
            int levelOfDetail = 10;
            ArcGISMap map = new ArcGISMap(basemapType, latitude, longitude, levelOfDetail);
            mMapView.setMap(map);

            double maxScale = mMapView.getMap().getMaxScale();

            mMapView.getMap().setMaxScale(maxScale);

            // MapInfo Start
            LayerList blist= mMapView.getMap().getBasemap().getBaseLayers();
            LayerList llist = mMapView.getMap().getOperationalLayers();

            TextView layerText = findViewById(R.id.map);
            layerText.setText("Карта: "+ mMapView.getMap().getBasemap().getName()+
                    "\n"+
                    "\n"+"Максимальный масштаб:   1:"+mMapView.getMap().getMaxScale()+
                    "\n"+"Минимальный масштаб:    1:"+mMapView.getMap().getMinScale()+
                    "\n");

            layerText.append("\n"+"Базовые слои: ");

            for (int i = 0; i < blist.size(); ++i)
            {
                layerText.append("\n"+"  "+blist.get(i).getName());
            }

            layerText.append("\n"+
                    "\n"+"Рабочие слои: ");

            for (int i = 0; i < llist.size(); ++i)
            {
                layerText.append("\n"+"  "+llist.get(i).getName());
            }

            mapinfo = new Intent(MainActivity.this, MapInfo.class);
            mapinfo.putExtra("mapinfo", layerText.getText().toString());

            layers();


            // MapInfo Stop



        }
    }

    private void setupMobileMap() {
        if (mMapView != null) {

            final File mmpkFile;
            final MobileMapPackage mapPackage;


            Intent openmap = getIntent();

            boolean usermapS = openmap.getBooleanExtra("usermapbool",false);

            if(usermapS==true) {
                String pathS = openmap.getStringExtra("usermap");

                mmpkFile = new File(pathS);
                mapPackage = new MobileMapPackage(mmpkFile.getAbsolutePath());

            } else {
                mmpkFile = new File(Environment.getExternalStorageDirectory(), "/Download/devlabs-package.mmpk");
                mapPackage = new MobileMapPackage(mmpkFile.getAbsolutePath());
            }


//            File mmpkFile = new File(Environment.getExternalStorageDirectory(), "/Download/parispariss.mmpk");
//            final MobileMapPackage mapPackage = new MobileMapPackage(mmpkFile.getAbsolutePath());
            mapPackage.addDoneLoadingListener(new Runnable() {
                @Override
                public void run() {
                    // Verify the file loaded and there is at least one map
                    if (mapPackage.getLoadStatus() == LoadStatus.LOADED && mapPackage.getMaps().size() > 0) {
                        mMapView.setMap(mapPackage.getMaps().get(0));

                        double maxScale = mMapView.getMap().getMaxScale();

                        if (maxScale < 1) {
                            mMapView.getMap().setMaxScale(564);
                        }


                        // MapInfo Start
                        LayerList blist= mMapView.getMap().getBasemap().getBaseLayers();
                        LayerList llist = mMapView.getMap().getOperationalLayers();

                        TextView layerText = findViewById(R.id.map);
                        layerText.append("Карта: "+ mmpkFile.getName()+
                                "\n"+
                                "\n"+"Размер: "+mmpkFile.length()/(1024*1024)+" Mb"+
                                "\n"+
                                "\n"+"Расположение: "+mmpkFile.getPath()+
                                "\n"+
                                "\n"+"Изменен: "+new SimpleDateFormat("dd MMMM yyyy HH:mm", Locale.getDefault()).format( new Date(mmpkFile.lastModified()))+
                                "\n"+
                                "\n"+"Максимальный масштаб:   1:"+mMapView.getMap().getMaxScale()+
                                "\n"+"Минимальный масштаб:    1:"+mMapView.getMap().getMinScale()+
                                "\n");

                        layerText.append("\n"+"Базовые слои: ");

                        for (int i = 0; i < blist.size(); ++i)
                        {
                            layerText.append("\n"+"  "+blist.get(i).getName());
                        }

                        layerText.append("\n"+
                                "\n"+"Рабочие слои: ");

                        for (int i = 0; i < llist.size(); ++i)
                        {
                            layerText.append("\n"+"  "+llist.get(i).getName());
                        }

                        mapinfo = new Intent(MainActivity.this, MapInfo.class);
                        mapinfo.putExtra("mapinfo", layerText.getText().toString());

                        layers();


                        // MapInfo Stop

                    } else {
                        // Error if the mobile map package fails to load or there are no maps included in the package
                        setupMap();
                    }
                }
            });
            mapPackage.loadAsync();
        }
    }

    private void testTenPoint() {

        medianBarArray.clear();
        medianArray.clear();
        hashMap.clear();
        catList.clear();

        hashMap = new HashMap<>();
        hashMap.put(SHAG, "Шаг"); // Шаг
        hashMap.put(MASSTAB, "Кол-во мастш"); // Масштаб
        hashMap.put(COORDINAT, "Координаты"); // Время
        hashMap.put(VREMYA, "Время"); // Масштаб
        catList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put(SHAG, ""); // Шаг
        hashMap.put(MASSTAB, ""); // Масштаб
        hashMap.put(COORDINAT, ""); // Время
        hashMap.put(VREMYA, ""); // Масштаб
        catList.add(hashMap);


        barCount=0;
        f=0;
        full=0;
        fAverage =0;

        yValue.clear();
        xValue.clear();

        lastTS = Float.toString(f);
        lastT.setText(lastTS);
        averageTS = Float.toString(fAverage);
        averageT.setText("Медиана: "+averageTS);





//        testTenPBlock=false;


            mMapView.setOnTouchListener(new DefaultMapViewOnTouchListener(this, mMapView) {

                @Override
                public boolean onSingleTapConfirmed(MotionEvent motionEvent) {

                    if (testTenPBlock==true) {
                        graphicsOverlay = new GraphicsOverlay();
                        mMapView.getGraphicsOverlays().add(graphicsOverlay);
                        SimpleMarkerSymbol symbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, colorChart, 16); //size 12, style of circle
                        android.graphics.Point screenPoint = new android.graphics.Point(Math.round(motionEvent.getX()), Math.round(motionEvent.getY()));
                        Point mapPoint = mMapView.screenToLocation(screenPoint);
                        Point wgs84Point = (Point) GeometryEngine.project(mapPoint, SpatialReferences.getWgs84());
                        Graphic graphic = new Graphic(wgs84Point, symbol);
                        graphicsOverlay.getGraphics().add(graphic);

                        stateCapitalsPST.add(wgs84Point.getX(), wgs84Point.getY()); // Sacramento, CA


                        TextSymbol text = new TextSymbol();
                        text.setText(Integer.toString(stateCapitalsPST.size()));
                        text.setColor(Color.WHITE);
                        text.setSize(14f);
                        Graphic grapchic1 = new Graphic(wgs84Point,text);
                        graphicsOverlay.getGraphics().add(grapchic1);



//                        deletePoints.setText(Integer.toString(stateCapitalsPST.size()));
                    }



                    return true;
                }
            });


    }

    private void startTestTenPoint() {

        try {

            medianBarArray.clear();
            medianArray.clear();

            hashMap.clear();
            catList.clear();

            hashMap = new HashMap<>();
            hashMap.put(SHAG, "Шаг"); // Шаг
            hashMap.put(MASSTAB, "Кол-во мастш"); // Масштаб
            hashMap.put(COORDINAT, "Координаты"); // Время
            hashMap.put(VREMYA, "Медиана"); // Масштаб
            catList.add(hashMap);

            hashMap = new HashMap<>();
            hashMap.put(SHAG, ""); // Шаг
            hashMap.put(MASSTAB, ""); // Масштаб
            hashMap.put(COORDINAT, ""); // Время
            hashMap.put(VREMYA, ""); // Масштаб
            catList.add(hashMap);

                    barCount=0;
                    f=0;
                    full=0;
                    fAverage =0;

                    yValue.clear();
                    xValue.clear();

                    lastTS = Float.toString(f);
                    lastT.setText(lastTS);
                    averageTS = Float.toString(fAverage);
                    averageT.setText("Медиана: "+averageTS);



                    testTenPCount = 0;

                    scale2 = mMapView.getCurrentViewpoint(Viewpoint.Type.CENTER_AND_SCALE).getTargetScale();
                    scale3Ten = scale2/2;
//                    mMapView.setViewpointScaleAsync(scale2);
                    mMapView.setViewpointCenterAsync(stateCapitalsPST.get(testTenPCount),scale3Ten);


//                    mMapView.setViewpointCenterAsync(stateCapitalsPST.get(testTenPCount));
                    testTenP=true;




            Toast toast = Toast.makeText(getApplicationContext(),
                    "Тестирование "+Integer.toString(testTenPCount+1)+"-ой точки из "+Integer.toString(stateCapitalsPST.size()), Toast.LENGTH_SHORT);
            toast.show();

        }
        catch (Exception ex) {
            Toast.makeText(this, "Выберите несколько точек!", Toast.LENGTH_SHORT).show();
        }



//        for (int i = 0; i < stateCapitalsPST.size(); ++i)
//        {
//
//        }

////        mMapView.setViewpointCenterAsync(stateCapitalsPST.get(0));
//        scale2 = mMapView.getCurrentViewpoint(Viewpoint.Type.CENTER_AND_SCALE).getTargetScale();
//        scale2 = scale2/2;
//        mMapView.setViewpointScaleAsync(scale2);
////        mMapView.setViewpointScaleAsync(133000);
//
//
//        testOneP=true;
//        btnTestOneP.setEnabled(false);


    }




    private void removeLastPoint() {

        try {

            mMapView.getGraphicsOverlays().remove(stateCapitalsPST.size()-1);
            stateCapitalsPST.remove(stateCapitalsPST.size()-1);

        } catch (Exception ex) {
            Toast.makeText(this, "Выберите несколько точек!",
                    Toast.LENGTH_SHORT).show();

        }

    }


    private void testOnePoint() {

        medianBarArray.clear();
        medianArray.clear();
        hashMap.clear();
        catList.clear();

        hashMap = new HashMap<>();
        hashMap.put(SHAG, "Шаг"); // Шаг
        hashMap.put(MASSTAB, "Масштаб"); // Масштаб
        hashMap.put(COORDINAT, "Координаты"); // Время
        hashMap.put(VREMYA, "Время"); // Масштаб
        catList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put(SHAG, ""); // Шаг
        hashMap.put(MASSTAB, ""); // Масштаб
        hashMap.put(COORDINAT, ""); // Время
        hashMap.put(VREMYA, ""); // Масштаб
        catList.add(hashMap);


        barCount=0;
        f=0;
        full=0;
        fAverage =0;

        yValue.clear();
        xValue.clear();

        lastTS = Float.toString(f);
        lastT.setText(lastTS);
        averageTS = Float.toString(fAverage);
        averageT.setText("Медиана: "+averageTS);


//        mMapView.setViewpointScaleAsync(133000);
        scale2 = mMapView.getCurrentViewpoint(Viewpoint.Type.CENTER_AND_SCALE).getTargetScale();
        scale2 = scale2/2;
        mMapView.setViewpointScaleAsync(scale2);

        testOneP=true;
        btnTestOneP.setEnabled(false);


    }

    private void testFree() {

        medianBarArray.clear();
        medianArray.clear();
        hashMap.clear();
        catList.clear();

        hashMap = new HashMap<>();
        hashMap.put(SHAG, "Шаг"); // Шаг
        hashMap.put(MASSTAB, "Масштаб"); // Масштаб
        hashMap.put(COORDINAT, "Координаты"); // Время
        hashMap.put(VREMYA, "Время"); // Масштаб
        catList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put(SHAG, ""); // Шаг
        hashMap.put(MASSTAB, ""); // Масштаб
        hashMap.put(COORDINAT, ""); // Время
        hashMap.put(VREMYA, ""); // Масштаб
        catList.add(hashMap);

        barCount=0;
        f=0;
        full=0;
        fAverage =0;

        yValue.clear();
        xValue.clear();

        lastTS = Float.toString(f);
        lastT.setText(lastTS);
        averageTS = Float.toString(fAverage);
        averageT.setText("Медиана: "+averageTS);

//        mMapView.setViewpointScaleAsync(267076);

    }

    private void drawProgress() {
        mMapView.addDrawStatusChangedListener(new DrawStatusChangedListener() {
            @Override
            public void drawStatusChanged(DrawStatusChangedEvent drawStatusChangedEvent) {
                if (drawStatusChangedEvent.getDrawStatus() == DrawStatus.IN_PROGRESS) {
                    scale1 = mMapView.getCurrentViewpoint(Viewpoint.Type.CENTER_AND_SCALE).getTargetScale();

                    startTimeDrawing = System.currentTimeMillis();
                    progressBar.setVisibility(View.VISIBLE);



                    Log.d("drawStatusChanged", "spinner visible");
                }
                else if (drawStatusChangedEvent.getDrawStatus() == DrawStatus.COMPLETED) {
                    progressBar.setVisibility(View.INVISIBLE);
                    finishTimeDrawing = System.currentTimeMillis();
                    difTimeDrawing = finishTimeDrawing - startTimeDrawing;

                    scale2 = mMapView.getCurrentViewpoint(Viewpoint.Type.CENTER_AND_SCALE).getTargetScale();


                    f=difTimeDrawing/1000f;
                    full +=f;

                    Point point = mMapView.getCurrentViewpoint(Viewpoint.Type.CENTER_AND_SCALE).getTargetGeometry().getExtent().getCenter();
                    Point wgs84Point = (Point) GeometryEngine.project(point, SpatialReferences.getWgs84());
                    double x = wgs84Point.getX();
                    double y = wgs84Point.getY();

                    coordinates.setText("x: "+String.format(Locale.US,"%.4f" , x)+"    y: "+String.format(Locale.US,"%.4f" , y));

                    if (testFree==true) {


                        barCount+=1;
                        barCountS = Integer.toString(barCount);

                        fAverage = full / barCount;

                        medianArray.add(f);
                        Collections.sort(medianArray);
                        int middle = medianArray.size()/2;

                        if (medianArray.size()%2 == 1)
                        {
                            medianValue = medianArray.get(middle);}
                        else {
                            medianValue = (medianArray.get(middle-1) + medianArray.get(middle)) / 2;}


                        lastTS = Float.toString(f);
                        lastT.setText(lastTS);
                        averageTS = Float.toString(medianValue);
                        averageT.setText("Медиана: "+averageTS);
                        yValue.add(new BarEntry(barCount-1, f+0f));
                        xValue.add(barCountS);


                        hashMap = new HashMap<>();
                        hashMap.put(SHAG, barCountS); // Шаг
                        hashMap.put(MASSTAB, "1:"+String.format(Locale.US,"%.0f" , scale2)); // Масштаб
                        hashMap.put(COORDINAT, "x: "+String.format(Locale.US,"%.4f" , x)+" y: "+String.format(Locale.US,"%.4f" , y)); // Время
                        hashMap.put(VREMYA, lastTS+" .с"); // Масштаб
                        catList.add(hashMap);

                    }

//                    f=difTimeDrawing/1000f;
//
//                    barCount+=1;
//                    barCountS = Integer.toString(barCount);
//
//                    full +=f;
//                    fAverage = full / barCount;
//
//
//                    lastTS = Float.toString(f);
//                    lastT.setText(lastTS);
//                    averageTS = Float.toString(fAverage);
//                    averageT.setText("Среднее "+averageTS);



                    // для ОДНОЙ точки
                    if (testOneP==true) {


                        barCount+=1;
                        barCountS = Integer.toString(barCount);
                        fAverage = full / barCount;

                        medianArray.add(f);
                        Collections.sort(medianArray);
                        int middle = medianArray.size()/2;

                        if (medianArray.size()%2 == 1)
                        {
                            medianValue = medianArray.get(middle);}
                        else {
                            medianValue = (medianArray.get(middle-1) + medianArray.get(middle)) / 2;}


                        lastTS = Float.toString(f);
                        lastT.setText(lastTS);
                        averageTS = Float.toString(medianValue);
                        averageT.setText("Медиана: "+averageTS);
                        yValue.add(new BarEntry(barCount-1, f+0f));
                        xValue.add(barCountS);



//                        double x = mMapView.getCurrentViewpoint(Viewpoint.Type.CENTER_AND_SCALE).getTargetGeometry().getExtent().getCenter().getX();
//                        double y = mMapView.getCurrentViewpoint(Viewpoint.Type.CENTER_AND_SCALE).getTargetGeometry().getExtent().getCenter().getY();


                        hashMap = new HashMap<>();
                        hashMap.put(SHAG, barCountS); // Шаг
                        hashMap.put(MASSTAB, "1:"+String.format(Locale.US,"%.0f" , scale2)); // Масштаб
                        hashMap.put(COORDINAT, "x: "+String.format(Locale.US,"%.4f" , x)+" y: "+String.format(Locale.US,"%.4f" , y)); // Время
                        hashMap.put(VREMYA, lastTS+" .с"); // Масштаб
                        catList.add(hashMap);


                    if (scale1!=scale2 & (scale2 > mMapView.getMap().getMaxScale()+1)) {
                        scale2 = scale2/2;
                        mMapView.setViewpointScaleAsync(scale2);
                    }
                    else {
                        btnTestOneP.setTextColor(getResources().getColor(R.color.buttonPressOff));
                        btnTestOneP.setEnabled(true);
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Тестирование одной точки завершено!" +
                                        "\n"+"Медиана прорисовки: "+averageTS+" с.", Toast.LENGTH_LONG);
                        toast.show();

                        testOneP=false;
                        testFree=true;
                    }
                    }


                    // для ДЕСЯТИ 101010!!! точек
                    if (testTenP==true) {

                        masstabTenPCount+=1;
                        barCountS = Integer.toString(masstabTenPCount);


                        medianBarArray.add(f);
                        Collections.sort(medianBarArray);
                        int middleMiddle = medianBarArray.size()/2;

                        if (medianBarArray.size()%2 == 1)
                        {
                            medianBarValue = medianBarArray.get(middleMiddle);}
                        else {
                            medianBarValue = (medianBarArray.get(middleMiddle-1) + medianBarArray.get(middleMiddle)) / 2;}


                        lastTS = Float.toString( medianBarValue);
                        lastT.setText(barCountS);

                        averageT.setText("Медиана: "+lastTS);

                        if (scale1!=scale2) {

                            if (scale2 > mMapView.getMap().getMaxScale()*2) {
                                scale2 = scale2/2;
                                mMapView.setViewpointScaleAsync(scale2);
                            }

                            else {
                                mMapView.setViewpointScaleAsync(scale2);
                            }

                        }


                        if (scale1==scale2) {

                            testTenPCount+=1;
                            String testTenPCountS = Integer.toString(testTenPCount);

                            yValue.add(new BarEntry(testTenPCount-1,  medianBarValue+0f));
                            xValue.add(testTenPCountS);

                            hashMap = new HashMap<>();
                            hashMap.put(SHAG, testTenPCountS); // Шаг
                            hashMap.put(MASSTAB, ""+barCountS); // Масштаб
                            hashMap.put(COORDINAT, "x: "+String.format(Locale.US,"%.4f" , x)+" y: "+String.format(Locale.US,"%.4f" , y)); // Время
                            hashMap.put(VREMYA, String.format(Locale.US,"%.3f" ,  medianBarValue)+" .с"); // Масштаб
                            catList.add(hashMap);

                            medianArray.add( medianBarValue);
                            Collections.sort(medianArray);
                            int middle = medianArray.size()/2;

                            if (medianArray.size()%2 == 1)
                            {
                                medianValue = medianArray.get(middle);}
                            else {
                                medianValue = (medianArray.get(middle-1) + medianArray.get(middle)) / 2;}



                            medianBarArray.clear();
                            medianBarValue=0;

                            masstabTenPCount=0;




                            if (testTenPCount!=stateCapitalsPST.size()){

                                Toast toast = Toast.makeText(getApplicationContext(),
                                        "Тестирование "+Integer.toString(testTenPCount+1)+"-ой точки из "+Integer.toString(stateCapitalsPST.size()), Toast.LENGTH_SHORT);
                                toast.show();

                                mMapView.setViewpointCenterAsync(stateCapitalsPST.get(testTenPCount),scale3Ten);

                            }

                            if (testTenPCount==stateCapitalsPST.size()){
                                Toast toast = Toast.makeText(getApplicationContext(),
                                        "Тестирование " +Integer.toString(stateCapitalsPST.size())+" точек завершено!" +
                                                "\n"+"Медиана прорисовки: "+String.format(Locale.US,"%.3f" ,  medianValue)+" с.", Toast.LENGTH_LONG);
                                toast.show();


//                                SimpleMarkerSymbol symbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.RED, 20); //size 12, style of circle
//                                int fa = mMapView.getGraphicsOverlays().set(0, )
//                                Log.d("ELEMENT", "FOUNDED"+mMapView.getGraphicsOverlays().size()+fa);

                                testTenP=false;
                                enableTestTenPFalse();
                                testFree=false;

                            }



                        }


                    }

//                    scale2 = mMapView.getCurrentViewpoint(Viewpoint.Type.CENTER_AND_SCALE).getTargetScale();
                    MasstabS = Double.toString(scale2);
                    Masstab.setText("Масштаб 1:"+MasstabS);


                    drawGraphic();
                }
            }
        });
    }

    private void drawGraphic() {


        SimpleAdapter adapter = new SimpleAdapter(this, catList, R.layout.chart2_item,
                new String[]{SHAG, MASSTAB,COORDINAT, VREMYA},
                new int[]{R.id.shag, R.id.masst, R.id.coordinat,R.id.vremyae});
        chart2.setAdapter(adapter);


        Description description = new Description();
        description.setTextColor(Color.BLACK);
        description.setText("Щаг изменения масштаба карты");
        mChart.setDescription(description);
        mChart.getDescription().setTextSize(10f);


        YAxis yl = mChart.getAxisLeft();
        YAxis yr = mChart.getAxisRight();

        yl.setDrawGridLines(true);
        yl.setDrawLabels(true);
        yl.setDrawAxisLine(true);
        yr.setDrawGridLines(false);
        yr.setDrawLabels(false);
        yr.setDrawAxisLine(false);

//        yl.enableGridDashedLine(50f, 50f, 0f);
        yl.setLabelCount(yValue.size());
        yl.setGranularity(1f);

        yl.getLimitLines().clear();
        LimitLine llE = new LimitLine(medianValue, "Медиана");
        llE.setLineColor(Color.RED);
        yl.addLimitLine(llE);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawAxisLine(true);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setTextSize(10f);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(yValue.size());
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xValue));

        xAxis.getLimitLines().clear();
        LimitLine llT = new LimitLine(-0.5f, "Время в секундах ");
        llT.setLineColor(Color.GRAY);
        xAxis.addLimitLine(llT);


        BarDataSet dataSet = new BarDataSet(yValue, "Время отрисовки карты в секундах");
        dataSet.setValueTextSize(9f);

        dataSet.setColors(colorChart);
        dataSet.setDrawValues(true);

        BarData data = new BarData(dataSet);
        mChart.setData(data);
        mChart.invalidate(); // refresh


    }

    private void mapInfo () {

        LayerList blist= mMapView.getMap().getBasemap().getBaseLayers();
        LayerList llist = mMapView.getMap().getOperationalLayers();

        String qB = Long.toString(blist.size());
        String qL = Long.toString(llist.size());
        TextView layerText = findViewById(R.id.map);
        layerText.append("\n"+"Maps "+mMapView.getMap().getVersion()+
                "\n"+"BaseLayers "+qB+
                "\n"+"OperationalLayers "+qL+
                "\n");

        layerText.append("\nLayers: ");

        for (int i = 0; i < blist.size(); ++i)
        {
            layerText.append("\n"+blist.get(i).getName());
        }

        for (int i = 0; i < llist.size(); ++i)
        {
            layerText.append("\n"+llist.get(i).getName());
        }

        Intent intent = new Intent(MainActivity.this, MapInfo.class);

        intent.putExtra("mapinfo", layerText.getText().toString());
        // в ключ gift пихаем текст из второго текстового поля
        startActivity(intent);
    }

    private void layers () {

        try { // мониторим код

            // Определяет Базовые слои

            ArrayList<String> blistList = new ArrayList<>();
            final LayerList blist = mMapView.getMap().getBasemap().getBaseLayers();

            for (int i = 0; i < blist.size(); ++i)
            {
                blistList.add(blist.get(i).getName());
            }

            ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this,
                    R.layout.listlayer_item, blistList);
            layerListBase.setAdapter(adapter1);

            // Определяет Рабочие слои

            ArrayList<String> llistList = new ArrayList<>();
            final LayerList llist = mMapView.getMap().getOperationalLayers();

            for (int i = 0; i < llist.size(); ++i)
            {
                llistList.add(llist.get(i).getName());
            }

            ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this,
                    R.layout.listlayer_item, llistList);
            layerListOperational.setAdapter(adapter2);

            // Чекбокс активен

            for (int i = 0; i < blist.size(); ++i)
            {
                layerListBase.setItemChecked(i,true);
            }

            for (int i = 0; i < llist.size(); ++i)
            {
                layerListOperational.setItemChecked(i,true);
            }


            layerListBase.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                        long id) {

                    Toast.makeText(getApplicationContext(), Integer.toString(position),
                            Toast.LENGTH_SHORT).show();
                    boolean layerVisible = blist.get(position).isVisible();

                    if (layerVisible==true) {
                        blist.get(position).setVisible(false);}
                    if (layerVisible==false) {
                        blist.get(position).setVisible(true);}

                }
            });


            layerListOperational.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                        long id) {

                    Toast.makeText(getApplicationContext(), Integer.toString(position),
                            Toast.LENGTH_SHORT).show();
                    boolean layerVisible = llist.get(position).isVisible();

                    if (layerVisible==true) {
                        llist.get(position).setVisible(false);}
                    if (layerVisible==false) {
                        llist.get(position).setVisible(true);}

                }
            });

        } catch (Exception ex) {
            Toast.makeText(this, "Error: Unable to define layers!", Toast.LENGTH_LONG).show();
        }



//        mMapView.getMap().getBasemap().getBaseLayers().get(0).setVisible(false);
//        mMapView.getMap().getOperationalLayers().get(0).setVisible(false);
//        mMapView.getMap().getOperationalLayers().get(1).setVisible(false);
//        mMapView.getMap().getOperationalLayers().get(2).setVisible(false);

    }

    private void hideElementsTrue() {
        mChart.setVisibility(View.VISIBLE);;
        mMapView.setVisibility(View.INVISIBLE);

        lastT.setVisibility(View.INVISIBLE);
        averageT.setVisibility(View.INVISIBLE);
        Masstab.setVisibility(View.INVISIBLE);
        coordinates.setVisibility(View.INVISIBLE);

        layerListBase.setVisibility(View.INVISIBLE);
        layerListOperational.setVisibility(View.INVISIBLE);

        srtLayerBase.setVisibility(View.INVISIBLE);
        srtLayerOperational.setVisibility(View.INVISIBLE);


        btnHideChart.setTextColor(getResources().getColor(R.color.buttonPressOn));

        btnTestFree.setTextColor(getResources().getColor(R.color.buttonPressOff));
        btnTestOneP.setTextColor(getResources().getColor(R.color.buttonPressOff));
        btnTestTenP.setTextColor(getResources().getColor(R.color.buttonPressOff));
        layers.setTextColor(getResources().getColor(R.color.buttonPressOff));

        chart1chart2.setVisibility(View.VISIBLE);

    }

    private void hideElementsFalse() {
        mChart.setVisibility(View.INVISIBLE);
        mMapView.setVisibility(View.VISIBLE);

        lastT.setVisibility(View.VISIBLE);
        averageT.setVisibility(View.VISIBLE);
        Masstab.setVisibility(View.VISIBLE);
        coordinates.setVisibility(View.VISIBLE);

        layerListBase.setVisibility(View.INVISIBLE);
        layerListOperational.setVisibility(View.INVISIBLE);

        srtLayerBase.setVisibility(View.INVISIBLE);
        srtLayerOperational.setVisibility(View.INVISIBLE);

        btnHideChart.setTextColor(getResources().getColor(R.color.buttonPressOff));

        btnTestFree.setTextColor(getResources().getColor(R.color.buttonPressOff));
        btnTestOneP.setTextColor(getResources().getColor(R.color.buttonPressOff));
        btnTestTenP.setTextColor(getResources().getColor(R.color.buttonPressOff));
        layers.setTextColor(getResources().getColor(R.color.buttonPressOff));

        chart1chart2.setVisibility(View.INVISIBLE);
        chart2chart1.setVisibility(View.INVISIBLE);
        chart2.setVisibility(View.INVISIBLE);

    }


    private void showLayerListTrue() {

        layerListBase.setVisibility(View.VISIBLE);
        layerListOperational.setVisibility(View.VISIBLE);

        srtLayerBase.setVisibility(View.VISIBLE);
        srtLayerOperational.setVisibility(View.VISIBLE);

        mChart.setVisibility(View.INVISIBLE);
        mMapView.setVisibility(View.INVISIBLE);

        lastT.setVisibility(View.INVISIBLE);
        averageT.setVisibility(View.INVISIBLE);
        Masstab.setVisibility(View.INVISIBLE);
        coordinates.setVisibility(View.INVISIBLE);

        chart1chart2.setVisibility(View.INVISIBLE);
        chart2chart1.setVisibility(View.INVISIBLE);
        chart2.setVisibility(View.INVISIBLE);

        layers.setTextColor(getResources().getColor(R.color.buttonPressOn));

        btnHideChart.setTextColor(getResources().getColor(R.color.buttonPressOff));
        btnTestFree.setTextColor(getResources().getColor(R.color.buttonPressOff));
        btnTestOneP.setTextColor(getResources().getColor(R.color.buttonPressOff));
        btnTestTenP.setTextColor(getResources().getColor(R.color.buttonPressOff));



    }

    private void showLayerListFalse() {

        layerListBase.setVisibility(View.INVISIBLE);
        layerListOperational.setVisibility(View.INVISIBLE);

        srtLayerBase.setVisibility(View.INVISIBLE);
        srtLayerOperational.setVisibility(View.INVISIBLE);

        mMapView.setVisibility(View.VISIBLE);
        mChart.setVisibility(View.INVISIBLE);

        lastT.setVisibility(View.VISIBLE);
        averageT.setVisibility(View.VISIBLE);
        Masstab.setVisibility(View.VISIBLE);
        coordinates.setVisibility(View.VISIBLE);

        layers.setTextColor(getResources().getColor(R.color.buttonPressOff));

        btnHideChart.setTextColor(getResources().getColor(R.color.buttonPressOff));
        btnTestFree.setTextColor(getResources().getColor(R.color.buttonPressOff));
        btnTestOneP.setTextColor(getResources().getColor(R.color.buttonPressOff));
        btnTestTenP.setTextColor(getResources().getColor(R.color.buttonPressOff));



    }


    private void enableTestTenPTrue() {

        testTenPoint();
        testFree=false;
        testTenPBlock=true;

        showLayerListFalse();
        layersBool=false;

        btnTestTenP.setTextColor(getResources().getColor(R.color.buttonPressOn));

        startTestTenPoint.setVisibility(View.VISIBLE);
        deletePoints.setVisibility(View.VISIBLE);
        removeLastPoint.setVisibility(View.VISIBLE);

        chart1chart2.setVisibility(View.INVISIBLE);
        chart2chart1.setVisibility(View.INVISIBLE);
        chart2.setVisibility(View.INVISIBLE);



    }

    private void enableTestTenPFalse() {

        testTenP=false;
        testTenPBlock=false;
        btnTestTenP.setTextColor(getResources().getColor(R.color.buttonPressOff));

        btnTestOneP.setTextColor(getResources().getColor(R.color.buttonPressOff));
        btnHideChart.setTextColor(getResources().getColor(R.color.buttonPressOff));
        layers.setTextColor(getResources().getColor(R.color.buttonPressOff));

        startTestTenPoint.setVisibility(View.INVISIBLE);
        deletePoints.setVisibility(View.INVISIBLE);
        removeLastPoint.setVisibility(View.INVISIBLE);


//        mMapView.getGraphicsOverlays().clear();
//        stateCapitalsPST.clear();
//        testFree();
//        deletePoints.setText(getString(R.string.detelePoints));

    }

    @Override
    protected void onPause() {
        mMapView.pause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.resume();
    }

}
