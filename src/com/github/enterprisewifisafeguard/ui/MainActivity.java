package com.github.enterprisewifisafeguard.ui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Set;

import com.github.enterprisewifisafeguard.R;
import com.github.enterprisewifisafeguard.core.WifiSetup;
import com.github.enterprisewifisafeguard.utils.CertificateManager;

import android.app.Activity;
import android.net.wifi.WifiEnterpriseConfig;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.Files;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


public class MainActivity extends Activity {
	private Spinner fcert = null;
	private Spinner feap = null;
	private Spinner fPhase2 = null;
	private EditText fssid = null;
	private EditText fuser = null;
	private EditText fpass = null;
	private EditText fanonymous = null;
	private EditText fCn = null;
	private TextView feedback = null;
	private boolean folderFlag = false;
	private File exData = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //get Values for the Certificate Chooser
        fcert = (Spinner) findViewById(R.id.fca);
        Set<String> certName = CertificateManager.getInstance(this.getApplicationContext()).getAllCertNames();
        ArrayAdapter<String> certAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,certName.toArray(new String[certName.size()]));
        fcert.setAdapter(certAdapter);
        //get the fields
        feap = (Spinner) findViewById(R.id.feap);
    	fPhase2 = (Spinner) findViewById(R.id.fphase2);
    	fssid = (EditText) findViewById(R.id.fssid);
    	fuser = (EditText) findViewById(R.id.fuser);
    	fpass = (EditText) findViewById(R.id.fpass);
    	fanonymous = (EditText) findViewById(R.id.fano);
    	fCn = (EditText) findViewById(R.id.fcn);
    	feedback = (TextView) findViewById(R.id.feedback);
    	//button listener for config button
        Button button= (Button) findViewById(R.id.config_button);
        File folder = new File(Environment.getExternalStorageDirectory() + "/EnterpriseWifiSafeguard");
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdir();    
        }
        folderFlag = success;
        this.exData = folder;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
			public void onClick(View v) {  
            	buttonPressed();
            }
        }); 

        
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
        if (id == R.id.fimport) {
        	this.cImport();
            return true;
        }
        else if (id == R.id.export) {
        	this.cExport();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    public void buttonPressed() {
    	Log.d("ews-debug", "Button has pressed");
    	int eap;
    	int phase2;
    	//Select eap Mehod
    	String seap = feap.getSelectedItem().toString();
    	if (seap.equals("PEAP")) {
    		eap = WifiEnterpriseConfig.Eap.PEAP;
    	}
    	else if (seap.equals("PWD")) {
    		eap = WifiEnterpriseConfig.Eap.PWD;
    	}
    	else if (seap.equals("TLS")) {
    		eap = WifiEnterpriseConfig.Eap.TLS;
    	}
    	else if (seap.equals("TTLS")) {
    		eap = WifiEnterpriseConfig.Eap.TTLS;
    	}
    	else {
    		eap = -1;
    	}
    	Log.d("ews-debug", seap+eap);
    	//Select Phase2 Method
    	String sphase2 = fPhase2.getSelectedItem().toString();
         if (sphase2.equals("MSCHAP")) {
    		phase2 = WifiEnterpriseConfig.Phase2.MSCHAP;
    	}
         else if (sphase2.equals("MSCHAPV2")) {
     		phase2 = WifiEnterpriseConfig.Phase2.MSCHAPV2;
     	}
         else if (sphase2.equals("PAP")) {
      		phase2 = WifiEnterpriseConfig.Phase2.PAP;
      	}
         else {
        	 phase2= -1;
         }
         Log.d("ews-debug", sphase2+phase2);
    	WifiSetup setup = new WifiSetup(this.getApplicationContext());
    	boolean error = setup.createConnection("\""+fssid.getText().toString()+"\"", fuser.getText().toString()
    			, fpass.getText().toString(), fanonymous.getText().toString(), 
    			fCn.getText().toString(), eap, phase2, fcert.getSelectedItem().toString());
    	if(error) {
    		feedback.setText("Could not Save Network");
    	}
    	else {
    		feedback.setText("Network setup finished!");
    	}
    }
    
    //Import Configuration from /EnterpriseWifiSafeguard 
    public void cImport() {
    	 FileInputStream fstream = null;
    	 File f = new File(this.exData+"/config.txt");
    	 if(f.exists())
    	 {
		try {
			fstream = new FileInputStream(this.exData+"/config.txt");
		} catch (FileNotFoundException e) {
			Log.d("ews-debug", "File not found");
		}
    	BufferedReader reader = null;
    	reader = new BufferedReader(new InputStreamReader(fstream));
        try {
			this.fssid.setText(reader.readLine());
			this.fanonymous.setText(reader.readLine());
			this.fCn.setText(reader.readLine());
			 for (int i=0;i<this.feap.getCount();i++){
				   if (this.feap.getItemAtPosition(i).toString().equalsIgnoreCase(reader.readLine())){
				    this.feap.setSelection(i);
				    break;
				   }
				  }
			 for (int i=0;i<this.fPhase2.getCount();i++){
				   if (this.fPhase2.getItemAtPosition(i).toString().equalsIgnoreCase(reader.readLine())){
				    this.fPhase2.setSelection(i);
				    break;
				   }
				  }
			 for (int i=0;i<this.fcert.getCount();i++){
				   if (this.fcert.getItemAtPosition(i).toString().equalsIgnoreCase(reader.readLine())){
				    this.fcert.setSelection(i);
				    break;
				   }
				  }
		} catch (IOException e) {
			Log.d("Exception", e.getMessage());
		}
        feedback.setText("Imported from /EnterpriseWifiSaveguard/config.txt");
    	 }
    	 else {
    		 feedback.setText("There is no file /EnterpriseWifiSaveguard/config.txt to import" );
    	 }
    	 
    }
  //Export Configuration to /EnterpriseWifiSafeguard 
    public void cExport() {
    	File config=new File(this.exData,"config.txt");
    	FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(config, false);
		} catch (FileNotFoundException e) {
			Log.d("ews-debug","File Error");
		}
    	 
    	BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
     
    		try {
				bw.write(this.fssid.getText().toString());
				bw.newLine();
				bw.write(this.fanonymous.getText().toString());
				bw.newLine();
				bw.write(this.fCn.getText().toString());
				bw.newLine();
				bw.write(this.feap.getSelectedItem().toString());
				bw.newLine();
				bw.write(this.fPhase2.getSelectedItem().toString());
				bw.newLine();
				bw.write(this.fcert.getSelectedItem().toString());
				bw.newLine();
				bw.close();
			} catch (IOException e) {	
			Log.d("ews-debug", "could not write");
			} 
    		feedback.setText("Exported to /EnterpriseWifiSaveguard/config.txt");
    }
    
}


