package com.example.user.projectsix.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.user.projectsix.Consts;
import com.example.user.projectsix.R;
import com.example.user.projectsix.model.GridItem;
import com.example.user.projectsix.ui.adapter.GridViewAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements DownloadResultReceiver.Receiver {

    private DownloadResultReceiver mReceiver;
    private GridView mGridView;
    private ProgressBar mProgressBar;
    private GridViewAdapter mGridAdapter;
    private ArrayList<GridItem> mGridData = new ArrayList<GridItem>();
    private Bitmap mBitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Set activity layout */
        setContentView(R.layout.activity_gridview);
        /* Initialize gridView */
        mGridView = (GridView) findViewById(R.id.gridView);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        /* Starting Download Service */
        mReceiver = new DownloadResultReceiver(new Handler());
        mReceiver.setReceiver(this);
        Intent intent = new Intent(Intent.ACTION_SYNC, null, this, DownloadService.class);

        /* Send optional extras to Download IntentService */
        intent.putExtra("url", Consts.IMAGE_URL_ARRAY);
        intent.putExtra("receiver", mReceiver);

        startService(intent);
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        switch (resultCode) {
            case DownloadService.STATUS_RUNNING:
                /* Show progress bar*/
                mProgressBar.setVisibility(View.VISIBLE);
                break;

            case DownloadService.STATUS_ADD_IMAGES:
                /* Extract result from bundle and fill GridData */
                byte[] imageByteArray = resultData.getByteArray("result");
                mBitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
                GridItem item = new GridItem();
                item.setmImage(mBitmap);
                mGridData.add(item);
                break;

            case DownloadService.STATUS_FINISHED:
                /* Hide progress */
                mProgressBar.setVisibility(View.GONE);
                /* Update GridView with result */
                mGridAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, mGridData);
                mGridView.setAdapter(mGridAdapter);
                break;

            case DownloadService.STATUS_ERROR:
                /* Handle the error */
                String error = resultData.getString(Intent.EXTRA_TEXT);
                Toast.makeText(this, error, Toast.LENGTH_LONG).show();
                break;
        }
    }
}
