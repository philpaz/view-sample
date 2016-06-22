package com.example.pazuno.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.pazuno.bean.TransactionItemsBean;

import android.os.AsyncTask;
import android.util.Log;

public class GetTransactionItems extends AsyncTask<String, Void, ArrayList<TransactionItemsBean>> {
	
    // URL to get contacts JSON
    private static String url = "https://vendmicro.com/json....";
    //private ArrayList<HashMap<String, String>> transactionItemList;
    private ArrayList<TransactionItemsBean> transactionItemList;
    int goodTogo;
    
    
    // JSON Node names
    private static final String TAG_CHANNEL = "channel";
    private static final String TAG_MANUFACTURER = "manufacturer";
    private static final String TAG_NAME = "name";
    private static final String TAG_PRODID = "prodid";
    private static final String TAG_QUANTITY = "quantity";
    private static final String TAG_RETAIL = "retail";
    private static final String TAG_SERVICEID = "serviceid";
    private static final String TAG_SKU = "sku";
    private static final String TAG_SUPPLIER = "supplier";
    private static final String TAG_TAXABLE = "taxable";
    
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        goodTogo = 0;
    }

	@Override
	protected ArrayList<TransactionItemsBean> doInBackground(String... urlIn) {
        // Creating service handler class instance
        ServiceHandler sh = new ServiceHandler();
        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
        String errorAccessString = "User cannot access the resource";

        if (jsonStr != null) {
        	//Token could be back and getting the User cannot access the resource
        	if(!jsonStr.contains(errorAccessString)){
        		try { 
        			transactionItemList = new ArrayList<TransactionItemsBean>();
        			JSONArray jsonarray = new JSONArray(jsonStr);
        			//System.out.println("LACT >>>>>>>>>>>>>>> jsonStr " + jsonStr);
        			// looping through All Contacts
        			//MODIFICATION, check first what channel it is
        			for (int i = 0; i < jsonarray.length(); i++) {
        				JSONObject jsonobject = jsonarray.getJSONObject(i);        				
        				TransactionItemsBean transItemBean = new TransactionItemsBean();
        					transItemBean.setChannel(jsonobject.getString(TAG_CHANNEL));
        					transItemBean.setManufacturer(jsonobject.getString(TAG_MANUFACTURER));
        					transItemBean.setName(jsonobject.getString(TAG_NAME));
        					transItemBean.setProdid(Integer.parseInt(jsonobject.getString(TAG_PRODID)));
        					transItemBean.setQuantity(Integer.parseInt(jsonobject.getString(TAG_QUANTITY)));
        					transItemBean.setRetail(new BigDecimal(jsonobject.getString(TAG_RETAIL)));
        					transItemBean.setServiceid(Integer.parseInt(jsonobject.getString(TAG_SERVICEID)));
        					transItemBean.setSku(jsonobject.getString(TAG_SKU));
        					transItemBean.setSupplier(jsonobject.getString(TAG_SUPPLIER));
        					transItemBean.setTaxable(Integer.parseInt(jsonobject.getString(TAG_TAXABLE)));
                        transactionItemList.add(transItemBean);
        			}
        		} catch (JSONException e) {
        			goodTogo = 1;
        			e.printStackTrace();
        		}
        	}//inner if
        } else {
        	goodTogo = 1;
            Log.e("ServiceHandler", "Couldn't get any data from the url");
        }

        return transactionItemList;
    }
	
    @Override
    protected void onPostExecute(ArrayList<TransactionItemsBean> result) {
        super.onPostExecute(result);
    }

}
