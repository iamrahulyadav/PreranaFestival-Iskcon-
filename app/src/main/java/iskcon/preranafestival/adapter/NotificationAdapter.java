package iskcon.preranafestival.adapter;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import iskcon.preranafestival.R;
import iskcon.preranafestival.model.PreranaNews;

/**
 * Created by sibaprasad on 20/07/17.
 */

public class NotificationAdapter extends RecyclerView.Adapter< NotificationAdapter.NotificationViewHolder > {

	List< PreranaNews > preranaNewsList;

	public NotificationAdapter( List< PreranaNews > preranaNewsList ) {
		this.preranaNewsList = preranaNewsList;
	}

	@Override
	public NotificationViewHolder onCreateViewHolder( ViewGroup parent, int viewType ) {
		View itemView = LayoutInflater.from( parent.getContext() )
				.inflate( R.layout.itemview_notification, parent, false );

		return new NotificationViewHolder( itemView );
	}

	@Override
	public void onBindViewHolder( NotificationViewHolder holder, int position ) {
		PreranaNews preranaNews = preranaNewsList.get( position );
		holder.textViewTopic.setText( "Topic : " + preranaNews.getPreranaTitle() );
		holder.textViewSpeaker.setText( "Speaker : " + preranaNews.getPreranaSpeaker() );
		holder.textViewDate.setText( "Date " + preranaNews.getPreranaDate() );
		holder.textViewLink.setText( "Youtube Link " + preranaNews.getPreranaYoutubeLink() );
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

		public NotificationViewHolder( View itemView ) {
			super( itemView );
			textViewTopic = ( AppCompatTextView ) itemView.findViewById( R.id.textViewTopic );
			textViewSpeaker = ( AppCompatTextView ) itemView.findViewById( R.id.textViewSpeaker );
			textViewDate = ( AppCompatTextView ) itemView.findViewById( R.id.textViewDate );
			textViewLink = ( AppCompatTextView ) itemView.findViewById( R.id.textViewLink );
		}
	}
}
