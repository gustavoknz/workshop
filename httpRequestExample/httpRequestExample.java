package com.klaus.mauricio.httpexample;

/**
 * Created by mauricio on 24/07/2014.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class httpRequestExample {

    private static String url_favorito       = "http://104.236.115.126/workshop.php";
   //http://104.236.115.126/post.log

    private static final String TAG_TEXTO           = "texto";
    private static final String TAG_IDDIUSPOSITIVO  = "iddispositivo";

    private static final String TAG_MSG          = "mensagen";
    private static final String TAG_SUCCESS      = "sucesso";


    // Declaring Your View and Variables
    int v_success = 0;
    String v_msg      = "";
    static JSONObject jObj_result = null;
    String vTexto;

    // Progress Dialog
    private ProgressDialog pDialog;
    private Context myContext;

    // constructor
    public httpRequestExample(Context pContext, String pTexto ) {
        myContext = pContext;
        vTexto = pTexto;


        Log.d("workshop constructor:",pTexto);

        if(isConnected()){
            //Conexao de rede ok
            new EnviarDadosCadastro().execute();
        }
        else{
            Toast.makeText(myContext, myContext.getString(R.string.msg_not_connected), Toast.LENGTH_LONG).show();
        }

    }


    //------------------------------------------------------------------------------------------
    // TESTAR CONEXAO
    //------------------------------------------------------------------------------------------
    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) myContext.getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    //------------------------------------------------------------------------------------------
    // TRANFORMAR DADOS EM JSON
    //------------------------------------------------------------------------------------------
    public String encode_dados_acesso(){

        String result = "";

        try {
            String iddispositivo = Settings.Secure.getString(myContext.getContentResolver(), Settings.Secure.ANDROID_ID);

            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter(TAG_IDDIUSPOSITIVO, iddispositivo)
                    //.appendQueryParameter(TAG_EMAIL,          v_email)
                    //.appendQueryParameter(TAG_SENHA,          v_senha)
                    .appendQueryParameter(TAG_TEXTO,          vTexto);

            result = builder.build().getEncodedQuery();
            Log.d("workshop encode:",result);

        } catch (Exception e) {
            //Log.d("InputStream", e.getLocalizedMessage());
        }

        // 11. return result
        return result;
    }

    //------------------------------------------------------------------------------------------
    // CONVERTINPUTSTREAMTOSTRING
    //------------------------------------------------------------------------------------------
    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }
    //------------------------------------------------------------------------------------------
    // POST
    //------------------------------------------------------------------------------------------
    public static String POST(String p_query, String p_url){
        InputStream inputStream = null;
        String result = "";
        try {
            URL url = new URL(p_url);
            URLConnection urlConn = url.openConnection();

            if (!(urlConn instanceof HttpURLConnection)) {
                throw new IOException("URL is not an Http URL");
            }
            HttpURLConnection httpConn = (HttpURLConnection) urlConn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("POST");


            OutputStream os = httpConn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));

            writer.write(p_query);
            writer.flush();
            writer.close();
            os.close();

            httpConn.connect();

            int resCode = httpConn.getResponseCode();

            if (resCode == HttpURLConnection.HTTP_OK) {
                inputStream = httpConn.getInputStream();
            }
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";
        }

        catch (MalformedURLException e) {
            e.printStackTrace();
        }

        catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("workshop POST:",result);
        return result;
    }

    //------------------------------------------------------------------------------------------
    // Background Async Task to Load all product by making HTTP Request
    //------------------------------------------------------------------------------------------
    class EnviarDadosCadastro extends AsyncTask<String, String, String> {
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(myContext);
            pDialog.setMessage("Enviando . Aguarde....");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
        /*** getting All products from url  **/
        protected String doInBackground(String... args) {
                return POST(encode_dados_acesso(),url_favorito);
        }
        /*** After completing background task Dismiss the progress dialog  ***/
        protected void onPostExecute(String result) {
            pDialog.dismiss();

            Log.d("workshop onPostExecute:",result);

            try {
                // Checking for SUCCESS TAG
                // Check your log cat for JSON reponse
                jObj_result  = new JSONObject(result);
                v_success    = jObj_result.getInt(TAG_SUCCESS);
                v_msg        = jObj_result.getString(TAG_MSG);

            } catch (JSONException e) {
                v_success  = 0;
                e.printStackTrace();
            }

            Log.d("workshop v_msg:",v_msg);
            if (v_success == 1) {
                Toast.makeText(myContext, v_msg, Toast.LENGTH_SHORT).show();
            } //else msg Erro

        }

    }

}
