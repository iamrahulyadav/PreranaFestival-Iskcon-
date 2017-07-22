package iskcon.preranafestival.ns;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import java.util.ArrayList;
import java.util.List;

import iskcon.preranafestival.R;


public class ListDialogFragment extends DialogFragment implements OnBubleCheckListener {

	public static final String TAG = "ListDialogFragment";
	RecyclerView        recyclerView;
	LinearLayoutManager linearLayoutManager;
	BubbleAdapter       bubbleAdapter;
	List< BubleModel > listBuble = new ArrayList<>();
	OnBubleSUbmitListener OnBubleSUbmitListener;
	AppCompatButton       btnSubmit;

	public static ListDialogFragment newInstance( Context mcontext,List<BubleModel>  list) {
		ListDialogFragment mListDialogFragment = new ListDialogFragment();
		Bundle             bundle              = new Bundle();
		try {
			 	bundle.putParcelableArrayList( "list", ( ArrayList< ? extends Parcelable > ) list );
		}
		catch ( Exception e ) {
			Log.i( TAG, "newInstance:Error" + "\n" + e.getMessage() );
		}
		mListDialogFragment.setArguments( bundle );
		return mListDialogFragment;
	}

	@Override
	public void onAttach( Context context ) {
		super.onAttach( context );
		try {
			OnBubleSUbmitListener = ( ( OnBubleSUbmitListener ) context );
		}
		catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	@Override
	public void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		//second parameter for always be interface
		Bundle mBundle = getArguments();
		listBuble = ( List< BubleModel > ) mBundle.getSerializable( "list" );


	}

	@Nullable
	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
		View view = inflater.inflate( R.layout.recycler_layout, container, false );
		recyclerView = ( RecyclerView ) view.findViewById( R.id.recyclerView );
		btnSubmit = ( AppCompatButton ) view.findViewById( R.id.btnSubmit );
		btnSubmit.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick( View view ) {
				OnBubleSUbmitListener.onBubleSubmit(listBuble);
				dismiss();
			}
		} );
		return view;

	}

	@Override
	public void onViewCreated( View view, @Nullable Bundle savedInstanceState ) {
		super.onViewCreated( view, savedInstanceState );

		/*for ( int i = 0; i < 10; i++ ) {
			listBuble.add( new BubleModel( "Item " + i, false ) );
		}*/

		bubbleAdapter = new BubbleAdapter( getActivity(), listBuble, this );
		linearLayoutManager = new LinearLayoutManager( getActivity(), LinearLayoutManager.VERTICAL, false );
		recyclerView.setLayoutManager( linearLayoutManager );
		recyclerView.setAdapter( bubbleAdapter );
//        busRouteAdapter.setType(1);
	}


	@NonNull
	@Override
	public Dialog onCreateDialog( Bundle savedInstanceState ) {
		Dialog dialog = super.onCreateDialog( savedInstanceState );
		dialog.getWindow().requestFeature( Window.FEATURE_NO_TITLE );
		return dialog;
	}

	@Override
	public void onStart() {
		super.onStart();
		Dialog dialog = getDialog();
		if ( dialog != null ) {
			dialog.getWindow().setWindowAnimations(
					R.style.styleDialogFragment );
			dialog.getWindow().setLayout( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT );
			dialog.getWindow().setBackgroundDrawable( new ColorDrawable( Color.WHITE ) );
		}
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	public void onBubleCheck( boolean isChecked, int position ) {
		listBuble.get( position ).isChecked = isChecked;
	}
}