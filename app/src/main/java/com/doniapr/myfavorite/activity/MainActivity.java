package com.doniapr.myfavorite.activity;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.doniapr.myfavorite.R;
import com.doniapr.myfavorite.adapter.FavoriteAdapter;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity implements LoadFavCallback {
    public static final String AUTHORITY = "com.doniapr.moviecatalogue5.provider";
    public static final String TB_NAME = "tbFavorite";
    public static final Uri URI_FAV = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TB_NAME)
            .build();
    FavoriteAdapter adapter;
    RecyclerView rv;
    private DataObserver dataObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv = findViewById(R.id.rv_favorite);
        adapter = new FavoriteAdapter();
        rv.setLayoutManager(new GridLayoutManager(this, 2));


        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        dataObserver = new DataObserver(handler, this);
        getContentResolver().registerContentObserver(URI_FAV, true, dataObserver);
        new getData(this, this).execute();
    }

    @Override
    public void postExecute(Cursor favorite) {
        if (favorite.getCount() > 0) {
            adapter.setData(favorite);
            adapter.notifyDataSetChanged();
            rv.setAdapter(adapter);
        } else {
            Toast.makeText(this, "Tidak Ada data saat ini", Toast.LENGTH_SHORT).show();
        }

    }

    private static class getData extends AsyncTask<Void, Void, Cursor> {
        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadFavCallback> weakCallback;

        private getData(Context context, LoadFavCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);

        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return weakContext.get().getContentResolver().query(URI_FAV, null, null, null, null);

        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            weakCallback.get().postExecute(cursor);
        }
    }

    static class DataObserver extends ContentObserver {
        final Context context;

        public DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new getData(context, (MainActivity) context).execute();
        }
    }
}
