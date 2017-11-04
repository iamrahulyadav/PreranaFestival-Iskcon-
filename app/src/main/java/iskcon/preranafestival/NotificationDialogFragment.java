package iskcon.preranafestival;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import iskcon.preranafestival.adapter.NotificationAdapter;
import iskcon.preranafestival.model.PreranaNews;
import iskcon.preranafestival.utils.ApplicationUtils;


public class NotificationDialogFragment extends DialogFragment {

	private static final String TAG = "NotificationDialogFragment";

	RecyclerView        recyclerViewNotification;
	Toolbar             toolbarMain;
	AppCompatImageView  imageViewNotification;
	AppCompatTextView   textViewToolbarTitle;
	List< PreranaNews > mPreranaNewsList;
	NotificationAdapter notificationAdapter;


	public static NotificationDialogFragment newInstance( List< PreranaNews > preranaNewsList ) {
		NotificationDialogFragment mNotificationDialogFragment = new NotificationDialogFragment();
		Bundle                     bundle                      = new Bundle();
		bundle.putParcelableArrayList( "DATA", ( ArrayList< ? extends Parcelable > ) preranaNewsList );
		mNotificationDialogFragment.setArguments( bundle );
		return mNotificationDialogFragment;
	}

	@Override
	public void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		//second parameter for always be interface
		Bundle mBundle = getArguments();
	}

	@Nullable
	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
		View view = inflater.inflate( R.layout.dialogfragment_notification, container, false );


		Bundle bundle = getArguments();
		mPreranaNewsList = bundle.getParcelableArrayList( "DATA" );

		Collections.reverse(mPreranaNewsList);

		recyclerViewNotification = ( RecyclerView ) view.findViewById( R.id.recyclerViewNotification );
		toolbarMain = ( Toolbar ) view.findViewById( R.id.toolbarMain );
		imageViewNotification = ( AppCompatImageView ) view.findViewById( R.id.imageViewNotification );
		textViewToolbarTitle = ( AppCompatTextView ) view.findViewById( R.id.textViewToolbarTitle );
		imageViewNotification.setImageResource( R.drawable.ic_close );
		textViewToolbarTitle.setText( "Notifications" );

		imageViewNotification.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick( View view ) {
				dismiss();
			}
		} );


		ApplicationUtils.setLayoutManager( getActivity(), recyclerViewNotification );
		notificationAdapter = new NotificationAdapter( mPreranaNewsList );
		recyclerViewNotification.setAdapter( notificationAdapter );

		return view;
	}

	@Override
	public void onViewCreated( View view, @Nullable Bundle savedInstanceState ) {
		super.onViewCreated( view, savedInstanceState );
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
}

