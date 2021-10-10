package py.martinsalcedo.android.web_services;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import py.martinsalcedo.android.R;
import py.martinsalcedo.android.common_tools.LibraryConstants;
import py.martinsalcedo.android.common_tools.ErrorTools;

public class WebServiceTools {
    private String host;
    private Integer port;
    private String method;
    private String service;
    private HashMap<String, String> properties;
    private String[] parameters;

    public WebServiceTools(String host, Integer port, String method, String service, String[] parameters, HashMap<String, String> properties) {
        this.host = host;
        this.port = port;
        this.method = method;
        this.service = service;
        this.parameters = parameters;
        this.properties = properties;
    }

    public Boolean isValidResponse(String rString) {
        if (rString == null) {
            return false;
        }
        if (rString.isEmpty()) {
            return false;
        }
        return !rString.equals(LibraryConstants.WEB_SERVICE_NOT_CONNECTED);
    }

    //SETTER AND GETTER
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

    private String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    private String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    private String[] getParameters() {
        return parameters;
    }

    public void setParameters(String[] parameters) {
        this.parameters = parameters;
    }

    //#################

    private HashMap<String, String> getProperties() {
        return properties;
    }

    public void setProperties(HashMap<String, String> properties) {
        this.properties = properties;
    }

    public String performRequest() {
        String resString;
        int response;
        URL wsURL;
        try {
            wsURL = setupURL();                                 //Incluye Parametros
            HttpURLConnection conn = setupConnection(wsURL);    //Incluye Método y Propiedades
            response = conn.getResponseCode();
            Log.i("WS_CODE", String.valueOf(response));
            if (response == HttpURLConnection.HTTP_OK) {
                resString = setupResponseString(conn);
            } else {
                resString = String.format("WSResponse:%s", response);
            }
        } catch (Exception errString) {
            resString = LibraryConstants.WEB_SERVICE_NOT_CONNECTED;
            errString.printStackTrace();
        }
        return resString;
    }

    private String setupResponseString(HttpURLConnection conn) throws IOException {
        StringBuilder resSBuilder = new StringBuilder();
        String resString;
        InputStream iStream = new BufferedInputStream(conn.getInputStream());
        BufferedReader bReader = new BufferedReader(new InputStreamReader(iStream));
        String rowString;
        int cnt = 0;
        while ((rowString = bReader.readLine()) != null) {
            cnt += 1;
            resSBuilder.append(rowString);
        }
        Log.i("WS_BUILD", resSBuilder.toString());
        resString = resSBuilder.toString();
        return resString;
    }

    private HttpURLConnection setupConnection(URL wsURL) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) wsURL.openConnection();
        conn.setRequestMethod(getMethod());
        for (Map.Entry<String, String> entry : getProperties().entrySet()) {
            conn.setRequestProperty(entry.getKey(), entry.getValue());
        }
        return conn;
    }

    private URL setupURL() throws MalformedURLException {
        String[] params = getParameters();
        StringBuilder urlParams = new StringBuilder();
        String urlRequest;

        for (int pidx = 0; pidx < params.length; pidx++) {
            if (pidx > 0) {
                urlParams.append("/");
            }
            urlParams.append(params[pidx]);
        }
        urlRequest = String.format("http://%s:%s/%s/%s", host, port, getService(), urlParams.toString());

        urlRequest = urlRequest.replace(" ", "%20");
        Log.i("URL_PREPARED", urlRequest);
        return new URL(urlRequest);
    }

    public void performErrorDisplay(Context context, JSONObject wsResult) {
        Log.i("WS_RESULT", String.valueOf(wsResult));
        if (wsResult == null) {
            return;
        }
        if (wsResult.has("ErrorCode")) {
            String errorCode;
            try {
                errorCode = wsResult.getString("ErrorCode");
                ErrorTools.displayErrorCode(context, context.getString(R.string.InternalError), errorCode);
//                Toast.makeText(context, context.getString(R.string.InternalError), Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                ErrorTools.displayErrorDescription(context, context.getString(R.string.InternalError), e.toString());
                e.printStackTrace();
            }
        } else {
            String errorDescription = "Internal Error";
            if (wsResult.has("ErrorDescription")) {
                try {
                    errorDescription = wsResult.getString("ErrorDescription");
                    if (errorDescription.isEmpty()) {
                        errorDescription = "Internal Error";
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            ErrorTools.displayErrorDescription(context, context.getString(R.string.InternalError), errorDescription);
        }
    }
}