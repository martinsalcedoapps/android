package py.martinsalcedo.android.web_services;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.google.firebase.components.BuildConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import py.martinsalcedo.android.R;
import py.martinsalcedo.android.common_tools.LibraryConstants;
import py.martinsalcedo.android.common_tools.UpdateAlertDialog;

public class CheckForUpdatesConsult extends AsyncTask<String, Integer, JSONObject> {
    private static RelativeLayout curLayout;
    private static Context curContext;
    private JSONObject wsResult;
    private ProgressBar progressBar;
    private WebServiceTools wstools;

    private String host;
    private Integer port;
    private String method;
    private String service;
    private HashMap<String, String> properties = new HashMap<>();
    private String[] parameters = new String[]{};

    public CheckForUpdatesConsult(Context curContext, RelativeLayout curLayout, String host, Integer port, String curService) {
        CheckForUpdatesConsult.curLayout = curLayout;
        CheckForUpdatesConsult.curContext = curContext;
        this.method = LibraryConstants.WEB_SERVICE_GET;
        this.service = curService;
        this.host = host;
        this.port = port;
    }

    //##################################
    public static RelativeLayout getCurLayout() {
        return curLayout;
    }

    public static void setCurLayout(RelativeLayout curLayout) {
        CheckForUpdatesConsult.curLayout = curLayout;
    }

    public static Context getCurContext() {
        return curContext;
    }

    public static void setCurContext(Context curContext) {
        CheckForUpdatesConsult.curContext = curContext;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String[] getParameters() {
        return parameters;
    }

    public void setParameters(String[] parameters) {
        this.parameters = parameters;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public HashMap<String, String> getProperties() {
        return properties;
    }

    public void setProperties(HashMap<String, String> properties) {
        this.properties = properties;
    }

    public void addProperty(String key, String value) {
        properties.put(key, value);
    }
    //##################################

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar = getCurLayout().findViewById(R.id.progressBar);
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void onCancelled(JSONObject wsResult) {
        super.onCancelled(wsResult);
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        wstools = new WebServiceTools(getHost(), getPort(), getMethod(), getService(), getParameters(), getProperties());
        String rString = wstools.performRequest();

        wsResult = new JSONObject();
        if (WebServiceTools.isValidResponse(rString)) {
            try {
                Log.d("LOG_DATA", rString);
                wsResult = new JSONObject(rString);
            } catch (Exception errString) {
                errString.printStackTrace();
                try {
                    wsResult.put("ErrorCode", "0");
                    wsResult.put("ErrorDescription", "Connection Error");
                    wsResult.put("Object", new JSONObject());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                wsResult.put("ErrorCode", "0");
                wsResult.put("ErrorDescription", "Connection Error");
                wsResult.put("Object", new JSONObject());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return wsResult;
    }

    @Override
    protected void onPostExecute(JSONObject wsResult) {
        //super.onPostExecute(wsResult);
        Boolean hasError = true;

        if (wsResult.has("Object")) {
            try {
                JSONObject wsObject = wsResult.getJSONObject("Object");
                if (wsObject != null) {
                    if (wsObject.has("CurrentAppVersion")) {
                        int curVersion = Integer.parseInt(wsObject.getString("CurrentAppVersion"));
                        int thisVersion = BuildConfig.VERSION_CODE;
                        if (curVersion != -1 && thisVersion < curVersion) {
                            hasError = false;
//                            Toast.makeText(getCurContext(), "New Application Update", Toast.LENGTH_LONG).show();
                            UpdateAlertDialog.updateConfirmation((Activity) getCurContext(), getCurLayout()).show();
                        } else {
//                            Toast.makeText(getCurContext(), "Application Version " + String.valueOf(curVersion), Toast.LENGTH_LONG).show();
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
//        if (hasError) {
//            wstools.performErrorDisplay(getCurContext(), wsResult);
//        }

        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }


    }
}