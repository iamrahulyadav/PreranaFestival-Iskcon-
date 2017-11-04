package iskcon.preranafestival;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

import iskcon.preranafestival.dialogFragment.InsertDataDialogFragment;
import iskcon.preranafestival.model.PreranaNews;
import iskcon.preranafestival.notifiction.Config;
import iskcon.preranafestival.notifiction.NotificationUtils;
import iskcon.preranafestival.utils.CommonUtils;
import iskcon.preranafestival.utils.PreferenceManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

	private static final String TAG = "MainActivity";

	boolean isDataset = false;

	LinearLayout      rootLayout;
	AppCompatTextView tvDate;
	AppCompatTextView tvTopic;
	AppCompatTextView tvSpeakerName;
	AppCompatTextView tvVenue;
	AppCompatTextView tvYoutubeLink;

	Toolbar            toolbarMain;
	AppCompatImageView imageViewNotification, imageViewInsertData, imageViewPreranaBanner;
	FloatingActionButton fabShare;
	List< PreranaNews > preranaNewsList = new ArrayList<>();
	ProgressDialog mProgressDialog;
	String defaultLink = "https://www.youtube.com/watch?v=7n0aQwrlSEI&list=PLpGI4YxmdseFZ0ujJo_WN1SHqcxBxYioK";
	private BroadcastReceiver mRegistrationBroadcastReceiver;
	private DatabaseReference mFirebaseDatabase;
	private FirebaseDatabase  mFirebaseInstance;
	private String            userId;

	@Override
	protected void onNewIntent( Intent intent ) {
		super.onNewIntent( intent );
		getAllData();
	}

	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_main );

		mProgressDialog = new ProgressDialog( MainActivity.this );
		mProgressDialog.setMessage( "Please Wait..." );
		mProgressDialog.setCancelable( false );

		//	getSupportActionBar().setTitle( getString( R.string.title ) );

		initView();

		setSupportActionBar( toolbarMain );

		getAllData();

		mRegistrationBroadcastReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive( Context context, Intent intent ) {

				// checking for type intent filter
				if ( intent.getAction().equals( Config.REGISTRATION_COMPLETE ) ) {
					// gcm successfully registered
					// now subscribe to `global` topic to receive app wide notifications
					FirebaseMessaging.getInstance().subscribeToTopic( Config.TOPIC_GLOBAL );

					displayFirebaseRegId();

				}
				else if ( intent.getAction().equals( Config.PUSH_NOTIFICATION ) ) {
					// new push notification is received

					String message = intent.getStringExtra( "message" );

					Toast.makeText( getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG ).show();

					tvTopic.setText( message );
				}
			}
		};

		displayFirebaseRegId();
	}

	private void initView() {

		toolbarMain = ( Toolbar ) findViewById( R.id.toolbarMain );
		imageViewNotification = ( AppCompatImageView ) findViewById( R.id.imageViewNotification );

		rootLayout = ( LinearLayout ) findViewById( R.id.rootLayout );
		tvDate = ( AppCompatTextView ) findViewById( R.id.tvDate );
		tvTopic = ( AppCompatTextView ) findViewById( R.id.tvTopic );
		tvSpeakerName = ( AppCompatTextView ) findViewById( R.id.tvSpeakerName );
		tvVenue = ( AppCompatTextView ) findViewById( R.id.tvVenue );
		tvYoutubeLink = ( AppCompatTextView ) findViewById( R.id.tvYoutubeLink );
		imageViewInsertData = ( AppCompatImageView ) findViewById( R.id.imageViewInsertData );
		imageViewPreranaBanner = ( AppCompatImageView ) findViewById( R.id.imageViewPreranaBanner );
		fabShare = ( FloatingActionButton ) findViewById( R.id.fabShare );

		fabShare.setOnClickListener( this );

		imageViewNotification.setOnClickListener( this );
		tvYoutubeLink.setText( Html.fromHtml( ""/*getString( R.string.youtube_link )*/ ) );


		tvYoutubeLink.setText( "Click Here See Prerana Youth Festivals Videos" );

		tvYoutubeLink.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick( View view ) {
				startActivity(new Intent( Intent.ACTION_VIEW, Uri.parse( defaultLink)));
			}
		} );

		imageViewInsertData.setOnLongClickListener( new View.OnLongClickListener() {
			@Override
			public boolean onLongClick( View view ) {
				InsertDataDialogFragment.newInstance().show( getSupportFragmentManager(), "" );
				return false;
			}
		} );
	}

	private void setUpUi( PreranaNews preranaNews ) {

		if ( preranaNews != null ) {

			PreferenceManager.getInstance( MainActivity.this ).setTitle( preranaNews.getPreranaTitle() );
			PreferenceManager.getInstance( MainActivity.this ).setSpeaker( preranaNews.getPreranaSpeaker() );
			PreferenceManager.getInstance( MainActivity.this ).setDate( preranaNews.getPreranaDate() );
			PreferenceManager.getInstance( MainActivity.this ).setLink( preranaNews.getPreranaYoutubeLink() );
			PreferenceManager.getInstance( MainActivity.this ).setBanner( preranaNews.getPreranaImageUrl() );
		}

		if ( !TextUtils.isEmpty( PreferenceManager.getInstance( MainActivity.this ).getTitle() ) ) {
			tvDate.setText( PreferenceManager.getInstance( MainActivity.this ).getDate() );
			tvTopic.setText( PreferenceManager.getInstance( MainActivity.this ).getTitle() );
			tvSpeakerName.setText( "   "+PreferenceManager.getInstance( MainActivity.this ).getSpeaker() );
			if ( !TextUtils.isEmpty( PreferenceManager.getInstance( MainActivity.this ).getLink().trim() ) ) {
				tvYoutubeLink.setText( "Youtube Link : " + Html.fromHtml( PreferenceManager.getInstance( MainActivity.this ).getLink() ) );
			}
			String imageUrl = PreferenceManager.getInstance( MainActivity.this ).getBanner().trim();
			if ( !TextUtils.isEmpty( PreferenceManager.getInstance( MainActivity.this ).getBanner().trim() ) ) {
				Glide.with( MainActivity.this ).load( imageUrl )
						.thumbnail( 0.5f )
						.crossFade()
						.placeholder( R.drawable.iskcon_bg )
						.diskCacheStrategy( DiskCacheStrategy.ALL )
						.into( imageViewPreranaBanner );
			}

			isDataset = true;
		}

		/*if ( preranaNews != null ) {
			tvDate.setText( " Date : " + preranaNews.getPreranaDate() );
			tvTopic.setText( "Topic : " + preranaNews.getPreranaTitle() );
			tvSpeakerName.setText( "Speaker : " + preranaNews.getPreranaSpeaker() );
			tvYoutubeLink.setText( "Youtube Link : " + Html.fromHtml( preranaNews.getPreranaYoutubeLink() ) );
			isDataset = true;
		}*/
	}

	// Fetches reg id from shared preferences
	// and displays on the screen
	private void displayFirebaseRegId() {
		SharedPreferences pref  = getApplicationContext().getSharedPreferences( Config.SHARED_PREF, 0 );
		String            regId = pref.getString( "regId", null );

		Log.e( TAG, "Firebase reg id: " + regId );

		if ( !TextUtils.isEmpty( regId ) ) {
			// Toast.makeText( MainActivity.this, "Firebase Reg Id: " + regId, Toast.LENGTH_SHORT ).show();
		}
		else {
			showErrorMessage();
			Toast.makeText( MainActivity.this, "Firebase Reg Id is not received yet!", Toast.LENGTH_SHORT ).show();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();

		// register GCM registration complete receiver
		LocalBroadcastManager.getInstance( this ).registerReceiver( mRegistrationBroadcastReceiver,
		                                                            new IntentFilter( Config.REGISTRATION_COMPLETE ) );

		// register new push message receiver
		// by doing this, the activity will be notified each time a new message arrives
		LocalBroadcastManager.getInstance( this ).registerReceiver( mRegistrationBroadcastReceiver,
		                                                            new IntentFilter( Config.PUSH_NOTIFICATION ) );

		// clear the notification area when the app is opened
		NotificationUtils.clearNotifications( getApplicationContext() );
	}

	@Override
	protected void onPause() {
		LocalBroadcastManager.getInstance( this ).unregisterReceiver( mRegistrationBroadcastReceiver );
		super.onPause();
	}

	private void showErrorMessage() {
		Snackbar snackbar = Snackbar
				.make( rootLayout, "No Internet Connection", Snackbar.LENGTH_LONG )
				.setAction( "UNDO", new View.OnClickListener() {
					@Override
					public void onClick( View view ) {
						Snackbar snackbar1 = Snackbar.make( rootLayout, "Message is restored!", Snackbar.LENGTH_SHORT );
						snackbar1.show();
					}
				} );

		snackbar.show();
	}

	@Override
	public void onClick( View view ) {
		switch ( view.getId() ) {
			case R.id.imageViewNotification:
				if(CommonUtils.isInternetAvailable( MainActivity.this )) {
					NotificationDialogFragment.newInstance( preranaNewsList ).show( getSupportFragmentManager(), "" );
				}
				else{
					showErrorMessage();
				}
				break;
			case R.id.fabShare:
				shareApplication();
				break;

		}
	}

	private void getAllData() {
		mFirebaseDatabase = FirebaseDatabase.getInstance().getReference( "preranaNews" );

		if ( !CommonUtils.isInternetAvailable( MainActivity.this ) ) {
			CommonUtils.showSnackBar( rootLayout, "No Internet Connection", Snackbar.LENGTH_LONG );
			if ( !TextUtils.isEmpty( PreferenceManager.getInstance( MainActivity.this ).getTitle() ) ) {
				tvDate.setText( PreferenceManager.getInstance( MainActivity.this ).getDate() );
				tvTopic.setText( PreferenceManager.getInstance( MainActivity.this ).getTitle() );
				tvSpeakerName.setText( "   "+PreferenceManager.getInstance( MainActivity.this ).getSpeaker() );

				if ( !TextUtils.isEmpty( PreferenceManager.getInstance( MainActivity.this ).getLink().trim() ) ) {
				//	tvYoutubeLink.setText( "Youtube Link : " + Html.fromHtml( PreferenceManager.getInstance( MainActivity.this ).getLink() ) );
				}
				isDataset = true;
			}
			else {
			}
		}
		else {

			mProgressDialog.show();


			mFirebaseDatabase.addChildEventListener( new ChildEventListener() {
				@Override
				public void onChildAdded( DataSnapshot dataSnapshot, String s ) {
					Log.i( TAG, "onChildAdded: " + s );
					PreranaNews preranaNews = dataSnapshot.getValue( PreranaNews.class );

					preranaNewsList.add( preranaNews );


					if ( preranaNewsList.size() > 0 ) {
						setUpUi( preranaNewsList.get( preranaNewsList.size() - 1 ) );
					}


					if ( mProgressDialog != null && mProgressDialog.isShowing() ) {
						mProgressDialog.dismiss();
					}
				}

				@Override
				public void onChildChanged( DataSnapshot dataSnapshot, String s ) {

				}

				@Override
				public void onChildRemoved( DataSnapshot dataSnapshot ) {

				}

				@Override
				public void onChildMoved( DataSnapshot dataSnapshot, String s ) {

				}

				@Override
				public void onCancelled( DatabaseError databaseError ) {

				}
			} );
		}
	}

	private void shareApplication() {
		final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object


		String shareBody = "https://play.google.com/store/apps/details?id=" + appPackageName;

		Intent sharingIntent = new Intent( android.content.Intent.ACTION_SEND );
		sharingIntent.setType( "text/plain" );
		sharingIntent.putExtra( android.content.Intent.EXTRA_SUBJECT, "To get Latest Prerana Festival Event Details , Please Install app from Below Link..: " );
		sharingIntent.putExtra( android.content.Intent.EXTRA_TEXT, shareBody );
		startActivity( Intent.createChooser( sharingIntent, "Share via" ) );

	}
}
