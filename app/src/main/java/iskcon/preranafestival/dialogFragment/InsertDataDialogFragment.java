package iskcon.preranafestival.dialogFragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import iskcon.preranafestival.R;
import iskcon.preranafestival.model.PreranaNews;


public class InsertDataDialogFragment extends DialogFragment implements View.OnClickListener {

	private static final String TAG = "NotificationDialogFragment";

	Toolbar            toolbarMain;
	AppCompatImageView imageViewNotification;
	AppCompatTextView  textViewToolbarTitle;

	ScrollView        scrollLayoutAdmin;
	AppCompatEditText edittextTitle;
	AppCompatEditText edittextDesc;
	AppCompatEditText edittextDate;
	AppCompatEditText edittextSpeaker;
	AppCompatEditText edittextVenue;
	AppCompatEditText edittextYoutubeLink;
	AppCompatEditText edittextImageUrl;
	AppCompatButton   buttonSubmit;

	LinearLayout      relativeLayoutAdmin;
	AppCompatButton   buttonSubmitPassword;
	AppCompatEditText edittextPassword;

	private DatabaseReference mFirebaseDatabase;

	public static InsertDataDialogFragment newInstance() {
		InsertDataDialogFragment mNotificationDialogFragment = new InsertDataDialogFragment();
		Bundle                   bundle                      = new Bundle();

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
		View view = inflater.inflate( R.layout.dialogfragment_insertdata, container, false );

		toolbarMain = ( Toolbar ) view.findViewById( R.id.toolbarMain );
		imageViewNotification = ( AppCompatImageView ) view.findViewById( R.id.imageViewNotification );
		textViewToolbarTitle = ( AppCompatTextView ) view.findViewById( R.id.textViewToolbarTitle );
		imageViewNotification.setImageResource( R.mipmap.ic_close );
		textViewToolbarTitle.setText( "Admin Section" );

		imageViewNotification.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick( View view ) {
				dismiss();
			}
		} );


		scrollLayoutAdmin = ( ScrollView ) view.findViewById( R.id.scrollLayoutAdmin );
		edittextTitle = ( AppCompatEditText ) view.findViewById( R.id.edittextTitle );
		edittextDesc = ( AppCompatEditText ) view.findViewById( R.id.edittextDesc );
		edittextDate = ( AppCompatEditText ) view.findViewById( R.id.edittextDate );
		edittextSpeaker = ( AppCompatEditText ) view.findViewById( R.id.edittextSpeaker );
		edittextVenue = ( AppCompatEditText ) view.findViewById( R.id.edittextVenue );
		edittextYoutubeLink = ( AppCompatEditText ) view.findViewById( R.id.edittextYoutubeLink );
		edittextImageUrl = ( AppCompatEditText ) view.findViewById( R.id.edittextImageUrl );
		buttonSubmit = ( AppCompatButton ) view.findViewById( R.id.buttonSubmit );

		relativeLayoutAdmin = ( LinearLayout ) view.findViewById( R.id.relativeLayoutAdmin );
		buttonSubmitPassword = ( AppCompatButton ) view.findViewById( R.id.buttonSubmitPassword );
		edittextPassword = ( AppCompatEditText ) view.findViewById( R.id.edittextPassword );

		buttonSubmitPassword.setOnClickListener( this );
		buttonSubmit.setOnClickListener( this );

		return view;
	}

	@Override
	public void onViewCreated( View view, @Nullable Bundle savedInstanceState ) {
		super.onViewCreated( view, savedInstanceState );
		mFirebaseDatabase = FirebaseDatabase.getInstance().getReference( "preranaNews" );
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
	public void onClick( View view ) {
		switch ( view.getId() ) {
			case R.id.buttonSubmit:
				String title = edittextTitle.getText().toString().trim();
				String desc = edittextDesc.getText().toString().trim();
				String date = edittextDate.getText().toString().trim();
				String speaker = edittextSpeaker.getText().toString().trim();
				String venue = edittextVenue.getText().toString().trim();
				String utube = edittextYoutubeLink.getText().toString().trim();
				String imageUrl = edittextImageUrl.getText().toString().trim();
				insertPreranaNews( title, desc, date, speaker, venue, utube, imageUrl );
				break;
			case R.id.buttonSubmitPassword:
				if ( !TextUtils.isEmpty( edittextPassword.getText().toString() ) && edittextPassword.getText().toString().equals( "P@ssw0rd42o" ) ) {
					scrollLayoutAdmin.setVisibility( View.VISIBLE );
					relativeLayoutAdmin.setVisibility( View.GONE );
				}
				else {
					Toast.makeText( getActivity(), "You Are Not Admin", Toast.LENGTH_SHORT ).show();
				}
				break;
		}
	}

	private void insertPreranaNews( String title, String description, String date, String speaker, String venue, String utubeLink, String imageUrl ) {
		PreranaNews preranaNews = new PreranaNews( title, description, date, speaker, venue, utubeLink, imageUrl );
		// Creating new user node, which returns the unique key value
		// new user node would be /users/$userid/
		String userId = mFirebaseDatabase.push().getKey();
		// pushing user to 'users' node using the userId
		mFirebaseDatabase.child( userId ).setValue( preranaNews );
		dismiss();
	}
}

