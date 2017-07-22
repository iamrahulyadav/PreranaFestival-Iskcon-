package iskcon.preranafestival.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class PreranaNews implements Parcelable{

	public String preranaTitle;
	public String preranaDesc;
	public String preranaDate;
	public String preranaSpeaker;
	public String preranaPlace;
	public String preranaYoutubeLink;

	public String getPreranaTitle() {
		return preranaTitle;
	}

	public void setPreranaTitle( String preranaTitle ) {
		this.preranaTitle = preranaTitle;
	}

	public String getPreranaDesc() {
		return preranaDesc;
	}

	public void setPreranaDesc( String preranaDesc ) {
		this.preranaDesc = preranaDesc;
	}

	public String getPreranaDate() {
		return preranaDate;
	}

	public void setPreranaDate( String preranaDate ) {
		this.preranaDate = preranaDate;
	}

	public String getPreranaSpeaker() {
		return preranaSpeaker;
	}

	public void setPreranaSpeaker( String preranaSpeaker ) {
		this.preranaSpeaker = preranaSpeaker;
	}

	public String getPreranaPlace() {
		return preranaPlace;
	}

	public void setPreranaPlace( String preranaPlace ) {
		this.preranaPlace = preranaPlace;
	}

	public String getPreranaYoutubeLink() {
		return preranaYoutubeLink;
	}

	public void setPreranaYoutubeLink( String preranaYoutubeLink ) {
		this.preranaYoutubeLink = preranaYoutubeLink;
	}

	public String getPreranaImageUrl() {
		return preranaImageUrl;
	}

	public void setPreranaImageUrl( String preranaImageUrl ) {
		this.preranaImageUrl = preranaImageUrl;
	}

	public String preranaImageUrl;

	// Default constructor required for calls to
	// DataSnapshot.getValue(User.class)
	public PreranaNews() {
	}

	public PreranaNews( String preranaTitle, String preranaDesc, String preranaDate,
	                   String preranaSpeaker, String preranaPlace, String preranaYoutubeLink,
	                   String preranaImageUrl ) {
		this.preranaTitle = preranaTitle;
		this.preranaDesc = preranaDesc;
		this.preranaDate = preranaDate;
		this.preranaSpeaker = preranaSpeaker;
		this.preranaPlace = preranaPlace;
		this.preranaYoutubeLink = preranaYoutubeLink;
		this.preranaImageUrl = preranaImageUrl;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel( Parcel dest, int flags ) {
		dest.writeString( this.preranaTitle );
		dest.writeString( this.preranaDesc );
		dest.writeString( this.preranaDate );
		dest.writeString( this.preranaSpeaker );
		dest.writeString( this.preranaPlace );
		dest.writeString( this.preranaYoutubeLink );
		dest.writeString( this.preranaImageUrl );
	}

	protected PreranaNews( Parcel in ) {
		this.preranaTitle = in.readString();
		this.preranaDesc = in.readString();
		this.preranaDate = in.readString();
		this.preranaSpeaker = in.readString();
		this.preranaPlace = in.readString();
		this.preranaYoutubeLink = in.readString();
		this.preranaImageUrl = in.readString();
	}

	public static final Creator< PreranaNews > CREATOR = new Creator< PreranaNews >() {
		@Override
		public PreranaNews createFromParcel( Parcel source ) {
			return new PreranaNews( source );
		}

		@Override
		public PreranaNews[] newArray( int size ) {
			return new PreranaNews[size];
		}
	};
}