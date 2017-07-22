package iskcon.preranafestival.ns;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import iskcon.preranafestival.R;

/**
 * Created by sibaprasad on 17/07/17.
 */

public class MainListActivity extends AppCompatActivity implements OnBubleSUbmitListener, OnBubleCheckListener {
	RecyclerView        recyclerView;
	AppCompatButton     btnSubmit;
	LinearLayoutManager linearLayoutManager;
	BubbleAdapter       bubbleAdapter;
	List< BubleModel > listBuble = new ArrayList<>();

	@Override
	protected void onCreate( @Nullable Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.recycler_layout );
		recyclerView = ( RecyclerView ) findViewById( R.id.recyclerView );
		for ( int i = 0; i < 10; i++ ) {
			listBuble.add( new BubleModel( "Item " + i, false ) );
		}

		bubbleAdapter = new BubbleAdapter( MainListActivity.this, listBuble, this );
		linearLayoutManager = new LinearLayoutManager( MainListActivity.this, LinearLayoutManager.VERTICAL, false );
		recyclerView.setLayoutManager( linearLayoutManager );
		recyclerView.setAdapter( bubbleAdapter );

		btnSubmit = ( AppCompatButton ) findViewById( R.id.btnSubmit );
		btnSubmit.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick( View view ) {
				ListDialogFragment.newInstance( MainListActivity.this, listBuble ).show( getSupportFragmentManager(), "hjjhj" );
			}
		} );
	}

	@Override
	public void onBubleSubmit( List< BubleModel > listModel ) {
		for ( int i = 0; i < listModel.size(); i++ ) {

			bubbleAdapter.notifyItemChanged( i , listModel.get( i ) );
			// listBuble.set( i, listModel.get( i ) );

			if ( listModel.get( i ).isChecked ) {
				// bubbleAdapter.notifyItemChanged( i , listModel.get( i ) );
				//	listBuble.set( i, listModel.get( i ) );
			}
		}
		//	listBuble.clear();
		//	listBuble.addAll( listModel );
		// bubbleAdapter.notifyDataSetChanged();
	}

	@Override
	public void onBubleCheck( boolean isChecked, int position ) {
		listBuble.get( position ).isChecked = isChecked;
	}
}
