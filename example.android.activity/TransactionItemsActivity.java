package com.example.pazuno;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.example.pazuno.bean.TransactionItemsBean;
import com.example.pazuno.util.GetTransactionItems;
import com.parse.ParseUser;


//import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

public class TransactionItemsActivity extends ActionBarActivity{
	
	private ListView mainListView ;
	private ArrayAdapter<TransactionItemsBean> listAdapter;
    private ArrayList<TransactionItemsBean> retunTransactionItemList;
    //private TransactionItemsBean blankTransactionBean = new TransactionItemsBean();
    private ArrayList<TransactionItemsBean> invoiceActivityTransactionItemList;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		ParseUser currentUser = ParseUser.getCurrentUser();
		if (currentUser != null) {
		  // do stuff with the user
		} else {
			showLoginActivity();
		}
		
        setContentView(com.example.pazuno.R.layout.fragment_transaction_items);    
	    // Find the ListView resource. 
	    mainListView = (ListView) findViewById( R.id.mainListView);
		
	    //NEED TO HANDLE WHEN THIS IS NULL retunTransactionItemList, WHEN IT IS
	    //IT CRASHES THE UI
	    
        try {
        	retunTransactionItemList = new GetTransactionItems().execute().get();
        	System.out.println("LACT >>>>>>>>>>>>> CAN NOT BE NULL retunTransactionItemList " + retunTransactionItemList);
        	//retunTransactionItemList = new ArrayList<TransactionItemsBean>();
        	//retunTransactionItemList.add(blankTransactionBean);
			} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}

        // When item is tapped, toggle checked properties of CheckBox and TransactionItemsBean.
        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick( AdapterView<?> parent, View item, 
                                   int position, long id) {
            TransactionItemsBean transactionItemBean = listAdapter.getItem( position );
            transactionItemBean.toggleChecked();
            TransactionItemViewHolder viewHolder = (TransactionItemViewHolder) item.getTag();
            viewHolder.getCheckBox().setChecked( transactionItemBean.isChecked() );
          }
        });
        
        // Set our custom array adapter as the ListView's adapter.
        listAdapter = new TransactionItemBeanArrayAdapter(this, retunTransactionItemList);
        mainListView.setAdapter( listAdapter );   
    }
    
    /** Custom adapter for displaying an array of Invoice objects. */
  private static class TransactionItemBeanArrayAdapter extends ArrayAdapter<TransactionItemsBean> {
    
    private LayoutInflater inflater;
    
    public TransactionItemBeanArrayAdapter( Context context, List<TransactionItemsBean> retunTransactionItemList ) {
      super( context, R.layout.list_transaction_items_table, R.id.channel, retunTransactionItemList );
      // Cache the LayoutInflate to avoid asking for a new one each time.
      inflater = LayoutInflater.from(context) ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      // TransactionItemsBean to display
  	  TransactionItemsBean transactionItemsBean = (TransactionItemsBean) this.getItem( position ); 

      // The child views in each row.
      CheckBox checkBox ; 
      TextView channelTextView;
      TextView retailTextView;
      TextView nameTextView ; 
      //TextView manufactTextView;
      TextView skuTextView;
      
      // Create a new row view
      if ( convertView == null ) {
        convertView = inflater.inflate(R.layout.list_transaction_items_table, null);
        
        // Find the child views.
        channelTextView = (TextView) convertView.findViewById( R.id.channel);
        retailTextView = (TextView) convertView.findViewById( R.id.retail);
        nameTextView = (TextView) convertView.findViewById( R.id.name);
        //manufactTextView = (TextView) convertView.findViewById( R.id.manufacturer);
        skuTextView = (TextView) convertView.findViewById( R.id.sku);
        checkBox = (CheckBox) convertView.findViewById( R.id.check01box );
        
        // Optimization: Tag the row with it's child views, so we don't have to 
        // call findViewById() later when we reuse the row.
        convertView.setTag( new TransactionItemViewHolder(nameTextView,
        		//manufactTextView,
        		checkBox, 
        		channelTextView, 
        		retailTextView, 
        		skuTextView) );

        // If CheckBox is toggled, update the object it is tagged with.
        
        //System.out.println("LACT >>>>>>>>>>> checkbox " + checkBox);
        
        checkBox.setOnClickListener( new View.OnClickListener() {
          public void onClick(View v) {
            CheckBox cb = (CheckBox) v ;
            TransactionItemsBean transactionItemsBean = (TransactionItemsBean) cb.getTag();
            transactionItemsBean.setChecked( cb.isChecked() );
            System.out.println("LACT >>>>>>>>>>> TransactionItemsBean " + transactionItemsBean.getName() + " " + transactionItemsBean.isChecked());
          }
        });        
      }
      // Reuse existing row view
      else {
        // Because we use a ViewHolder, we avoid having to call findViewById().
      	TransactionItemViewHolder viewHolder = (TransactionItemViewHolder) convertView.getTag();
        checkBox = viewHolder.getCheckBox() ;
        nameTextView = viewHolder.getTextView() ;
        //manufactTextView = viewHolder.getTextView2();
  	    channelTextView = viewHolder.getChannelTextView();
  	    retailTextView = viewHolder.getRetailTextView();
  	  	skuTextView = viewHolder.getSkuTextView();
      }

      // Tag the CheckBox with the TransactionItemsBean it is displaying, so that we can
      // access the TransactionItemsBean in onClick() when the CheckBox is toggled.
      checkBox.setTag( transactionItemsBean ); 
      
      // Display TransactionItemsBean data
      checkBox.setChecked(transactionItemsBean.isChecked());
      nameTextView.setText(transactionItemsBean.getName());      
      //manufactTextView.setText(transactionItemsBean.getManufacturer());
	  channelTextView.setText(transactionItemsBean.getChannel());
	  retailTextView.setText(transactionItemsBean.getRetail().toString());
	  skuTextView.setText(transactionItemsBean.getSku());
      
      return convertView;
    }
  }
  
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.transaction_item, menu);
		return true;
	}
  
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
  
  /** Holds child views for one row. */
  private static class TransactionItemViewHolder {
    private CheckBox checkBox;
    private TextView textView;
    private TextView textView2;
	private TextView channelTextView;
	private TextView retailTextView;
	private TextView skuTextView;		
    public TransactionItemViewHolder( TextView textView, 
  		  //TextView textView2, 
  		  CheckBox checkBox ,
  		  TextView channelTextView,
  		  TextView retailTextView,
  		  TextView skuTextView		  
  		  ) {
      this.checkBox = checkBox ;
      this.textView = textView ;
      //this.textView2 = textView2;
      this.channelTextView = channelTextView;
      this.retailTextView = retailTextView;
      this.skuTextView = skuTextView;
    }
    public CheckBox getCheckBox() {
      return checkBox;
    }
    @SuppressWarnings("unused")
    public void setCheckBox(CheckBox checkBox) {
      this.checkBox = checkBox;
    }
    public TextView getTextView() {
      return textView;
    }
    @SuppressWarnings("unused")
    public void setTextView(TextView textView) {
      this.textView = textView;
    }
    @SuppressWarnings("unused")
	public TextView getTextView2() {
      return textView2;
    }
    @SuppressWarnings("unused")
    public void setTextView2(TextView textView2) {
      this.textView2 = textView2;
    }
    public TextView getChannelTextView() {
		return channelTextView;
    }
    public TextView getRetailTextView() {
		return retailTextView;
    }
    public TextView getSkuTextView() {
		return skuTextView;
    }
    @SuppressWarnings("unused")
	public void setChannelTextView(TextView channelTextView) {
		this.channelTextView = channelTextView;
    }
    @SuppressWarnings("unused")
	public void setRetailTextView(TextView retailTextView) {
		this.retailTextView = retailTextView;
    }
    @SuppressWarnings("unused")
	public void setSkuTextView(TextView skuTextView) {
		this.skuTextView = skuTextView;
    }
  }
  
  public void etPhoneHome(View view){
      startActivity(new Intent(this, MainActivity.class));
  }
  
  public void invoiceActivity(View view){
	  
	   //Add data to a bundle
      Bundle bundleTransactionItemList = new Bundle();
      //Item needs to be serialized
      invoiceActivityTransactionItemList = new ArrayList<TransactionItemsBean>();
      for(int x=0; x<retunTransactionItemList.size(); x++){
    	  if(retunTransactionItemList.get(x).isChecked()==true){
    		  invoiceActivityTransactionItemList.add(retunTransactionItemList.get(x));
    	  }
      }
      
      bundleTransactionItemList.putSerializable("bundleTransList", invoiceActivityTransactionItemList);
     
	  Intent intent = new Intent(this, InvoiceActivity.class);
	  intent.putExtras(bundleTransactionItemList);
      startActivity(intent);
  }
  
  private void showLoginActivity() {
	Intent intent = new Intent(this, LoginActivity.class);
	startActivity(intent);
  }

  //public Object onRetainNonConfigurationInstance() {	
	//return retunTransactionItemList ;
  //}
  
}

