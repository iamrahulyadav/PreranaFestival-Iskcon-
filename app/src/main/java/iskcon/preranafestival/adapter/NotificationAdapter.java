package iskcon.preranafestival.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import iskcon.preranafestival.R;
import iskcon.preranafestival.model.PreranaNews;

/**
 * Created by sibaprasad on 20/07/17.
 */

public class NotificationAdapter extends RecyclerView.Adapter< NotificationAdapter.NotificationViewHolder > {

	List< PreranaNews > preranaNewsList;
	String defaultLink = "https://www.youtube.com/watch?v=7n0aQwrlSEI&list=PLpGI4YxmdseFZ0ujJo_WN1SHqcxBxYioK";
	public NotificationAdapter( List< PreranaNews > preranaNewsList ) {
		this.preranaNewsList = preranaNewsList;
	}

	Context mContext;

	@Override
	public NotificationViewHolder onCreateViewHolder( ViewGroup parent, int viewType ) {
		View itemView = LayoutInflater.from( parent.getContext() )
				.inflate( R.layout.itemview_notification, parent, false );

		return new NotificationViewHolder( itemView );
	}

	@Override
	public void onBindViewHolder( final NotificationViewHolder holder, int position ) {
		PreranaNews preranaNews = preranaNewsList.get( position );
		holder.textViewTopic.setText( "Topic : " + preranaNews.getPreranaTitle() );
		holder.textViewSpeaker.setText( "Speaker : " + preranaNews.getPreranaSpeaker() );
		holder.textViewDate.setText( "Date : " + preranaNews.getPreranaDate() );
		holder.textViewLink.setText( "Click Here See Prerana Youth Festivals Videos" );

		mContext = holder.textViewLike.getContext();

		holder.textViewLink.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick( View view ) {
				mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(defaultLink)));
			}
		} );

		holder.textViewLike.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick( View view ) {
				Toast.makeText(holder.textViewLike.getContext() , "Coming Soon", Toast.LENGTH_SHORT ).show();
			}
		} );

		holder.textViewRemindMe.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick( View view ) {
				Toast.makeText(holder.textViewLike.getContext() , "Coming Soon", Toast.LENGTH_SHORT ).show();
			}
		} );

	}

	@Override
	public int getItemCount() {
		return preranaNewsList.size();
	}

	public class NotificationViewHolder extends RecyclerView.ViewHolder {
		AppCompatTextView textViewTopic;
		AppCompatTextView textViewSpeaker;
		AppCompatTextView textViewDate;
		AppCompatTextView textViewLink;
		AppCompatTextView textViewLike;
		AppCompatTextView textViewRemindMe;

		public NotificationViewHolder( View itemView ) {
			super( itemView );
			textViewTopic = ( AppCompatTextView ) itemView.findViewById( R.id.textViewTopic );
			textViewSpeaker = ( AppCompatTextView ) itemView.findViewById( R.id.textViewSpeaker );
			textViewDate = ( AppCompatTextView ) itemView.findViewById( R.id.textViewDate );
			textViewLink = ( AppCompatTextView ) itemView.findViewById( R.id.textViewLink );
			textViewLike = ( AppCompatTextView ) itemView.findViewById( R.id.textViewLike );
			textViewRemindMe = ( AppCompatTextView ) itemView.findViewById( R.id.textViewRemindMe );
		}
	}

	protected void setTextViewHTML( AppCompatTextView text, String html)
	{
		CharSequence           sequence   = Html.fromHtml(html);
		SpannableStringBuilder strBuilder = new SpannableStringBuilder( sequence);
		URLSpan[]              urls       = strBuilder.getSpans( 0, sequence.length(), URLSpan.class);
		for(URLSpan span : urls) {
			makeLinkClickable(strBuilder, span);
		}
		text.setText(strBuilder);
		text.setMovementMethod( LinkMovementMethod.getInstance());
	}
	protected void makeLinkClickable(SpannableStringBuilder strBuilder, final URLSpan span)
	{
		int start = strBuilder.getSpanStart(span);
		int end = strBuilder.getSpanEnd(span);
		int flags = strBuilder.getSpanFlags(span);
		ClickableSpan clickable = new ClickableSpan() {
			public void onClick(View view) {
				// Do something with span.getURL() to handle the link click...
				Intent videoIntent = new Intent( Intent.ACTION_VIEW);
				videoIntent.setData( Uri.parse(defaultLink));
				videoIntent.setClassName("com.google.android.youtube", "com.google.android.youtube.WatchActivity");
				mContext.startActivity(videoIntent);
			}
		};
		strBuilder.setSpan(clickable, start, end, flags);
		strBuilder.removeSpan(span);
	}
}
