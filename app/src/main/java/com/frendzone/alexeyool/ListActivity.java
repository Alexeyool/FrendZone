package com.frendzone.alexeyool;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    ListView list;
    TextView nameTextView;
    BaseAdapter adapter;

    ArrayList<FriendData> fList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        list = (ListView) findViewById(R.id.ALIST_listview);
        nameTextView = (TextView) findViewById(R.id.ALIST_textView_name);

        getFriendList();

    }

    GraphRequest graphRequest1 = new GraphRequest(AccessToken.getCurrentAccessToken(),
    "/me/friends", null, HttpMethod.GET, new GraphRequest.Callback() {
        public void onCompleted(GraphResponse response) {
            try {
            JSONObject object = response.getJSONObject();
            Log.d("my", object.toString());
                JSONArray arrayOfUsersInFriendList = object.getJSONArray("data");
                Log.d("my", arrayOfUsersInFriendList.toString());
                Log.d("my", arrayOfUsersInFriendList.getJSONObject(0).getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    });
    private void getListArray(String friendlistId) {
        GraphRequest graphRequest2 = new GraphRequest(AccessToken.getCurrentAccessToken(),
                "/" + friendlistId + "/members/", null, HttpMethod.GET, new GraphRequest.Callback() {
            public void onCompleted(GraphResponse graphResponse) {
                JSONObject object = graphResponse.getJSONObject();
                try {
                    JSONArray arrayOfUsersInFriendList= object.getJSONArray("data");
                    if (arrayOfUsersInFriendList.length() > 0) {
                        fList = new ArrayList<FriendData>();
                        for (int i = 0; i < arrayOfUsersInFriendList.length(); i++) {
                            JSONObject user = arrayOfUsersInFriendList.getJSONObject(i);
                            fList.add(new FriendData(
                                    user.getInt("uid"),
                                    user.getString("name"),
                                    user.getString("gender"),
                                    user.getString("age_range"),
                                    0,
                                    0,
                                    0,
                                    0,
                                    0));
                        }
                    }
                    if (fList != null) {
                        adapter = new ListAdapter(ListActivity.this, fList);
                        list.setAdapter(adapter);
                        list.setOnItemClickListener(onItemClickListener);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        graphRequest2.executeAsync();
    }


    private ArrayList<FriendData> getFriendList() {
        // TODO: 6/6/2017
          graphRequest1.executeAsync();
//        for() {
//            fList.add(new FriendData(i));
//        }
        return null;
    }

    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // TODO: 6/6/2017
        }
    };

//    @Override
//    protected void onStop() {
//        super.onStop();
//        graphRequest1.cancel(true);
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
