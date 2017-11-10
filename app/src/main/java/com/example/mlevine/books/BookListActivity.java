package com.example.mlevine.books;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.net.URL;

public class BookListActivity extends AppCompatActivity {
    private ProgressBar mLoadingProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        mLoadingProgress = (ProgressBar) findViewById(R.id.pb_loading);

        try {
            URL bookUrl = ApiUtil.buildUrl("cooking");
            new BooksQueryTask().execute(bookUrl);
        }
        catch (Exception e) {
            Log.d("error", e.toString());
        }
    }

    public class BooksQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            String result = null;

            try {
                result = ApiUtil.getJson(searchUrl);
            }
            catch (Exception e) {
                Log.d("Error", e.toString());
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            TextView tvResult = (TextView) findViewById(R.id.tvResponse);
            TextView tvError = (TextView) findViewById(R.id.tv_error);

            tvError.setVisibility(result == null ? View.VISIBLE : View.INVISIBLE);
            tvResult.setVisibility(result == null ? View.INVISIBLE : View.VISIBLE);
            tvResult.setText(result);
            mLoadingProgress.setVisibility(View.INVISIBLE);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingProgress.setVisibility(View.VISIBLE);
        }
    }
}
