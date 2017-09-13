package gr.hua.android.assignment1;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Parser extends AsyncTask<Void, Void, ArrayList<String>> {

    private String title = null;
    private String description = null;
    private String urlString = null;
    private XmlPullParserFactory xmlFactoryObject;
    ArrayList<String> info = new ArrayList<String>();
    Context context;

    //Constructor to pass parameters
    public Parser(String url,Context context) {
        this.urlString = url;
        this.context = context.getApplicationContext();
    }

    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }

    //Get the titles and description from file and put them on ArrayList
    public void getData(XmlPullParser myParser) {
        int event;
        String text=null;
        try {
            event = myParser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                String name=myParser.getName();
                switch (event){
                    case XmlPullParser.START_TAG:
                        break;
                    case XmlPullParser.TEXT:
                        text = myParser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        //Put "title" or "description" on ArrayList so we know if the title has description or if the description has title
                        if(name.equals("title")){
                            title = text;
                            info.add("title");
                            info.add(title);
                        }else if(name.equals("description")){
                            description = text;
                            info.add("description");
                            info.add(description);
                        }
                        break;
                }
                event = myParser.next();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Connect to URL and parse file with XMLPullParser
    public void makeConnection() throws IOException, XmlPullParserException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.connect();
        InputStream stream = conn.getInputStream();

        //Create XMLPullParser
        xmlFactoryObject = XmlPullParserFactory.newInstance();
        XmlPullParser myparser = xmlFactoryObject.newPullParser();
        //In order to switch off namespace processing
        myparser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        myparser.setInput(stream, null);
        getData(myparser);
        stream.close();

    }

    //Call makeConnection to run in background
    @Override
    protected ArrayList<String> doInBackground(Void... params) {
        try {
            makeConnection();
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }
        return info;
    }

    //Get the ArrayList with the results and send him to TitleHandler to display the titles
    @Override
    protected void onPostExecute(ArrayList<String> array) {
        super.onPostExecute(array);
        Intent i = new Intent();
        i.setType("plain/text");
        i.setAction("gr.hua.android.assignment1.RSSpresentation");
        i.putExtra("array", array);
        //Give Intent this flag to present a launcher
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }


}
