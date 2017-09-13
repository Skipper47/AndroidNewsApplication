package gr.hua.android.assignment1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;

public class TitleHandler extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.titlehandler);

        Intent i = getIntent();
        final ArrayList<String> array = i.getExtras().getStringArrayList("array");
        final ArrayList<String> titles = new ArrayList<String>();
        final ArrayList<String> descriptions = new ArrayList<String>();

        //Check if all titles have description or all description have titles and fill two new ArrayLists with equal size
        //Also for those titles who don't have description add "No Description" and for the description "Untitled"
        for(int j=0; j<array.size()-2; j+=2){
            if(array.get(j).equals("title") && array.get(j+2).equals("title")) {
                titles.add(array.get(j + 1));
                descriptions.add("No Description");
            }else if(array.get(j).equals("title") && array.get(j+2).equals("description")){
                titles.add(array.get(j + 1));
                descriptions.add(array.get(j + 3));
            }else if(array.get(j).equals("description") && j+2!=array.size()){//Don't get out of bounts
                if(array.get(j+2).equals("description")) {
                    titles.add("Untitled");
                    descriptions.add(array.get(j + 3));
                }
            }else if(array.get(j).equals("description") && array.get(j+2).equals("title")){
                titles.add(array.get(j + 1));
                descriptions.add(array.get(j + 3));
            }
        }

        //Create ListView to display titles
        final ListView mListView = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, titles);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //When user select one title then it's description open in a new layout with a Back button
                //Pass the description of chosen title
                Intent i = new Intent();
                i.putExtra("description", descriptions.get(position));
                i.setClassName("gr.hua.android.assignment1", "gr.hua.android.assignment1.DescriptionHandler");
                startActivityForResult(i,5);
            }
        });

    }


}
