package iskcon.preranafestival.ns;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sibaprasad on 17/07/17.
 */

public class BubleModel implements Parcelable{
	public String  title;
	public boolean isChecked;

	public String getTitle() {
		return title;
	}

	public void setTitle( String title ) {
		this.title = title;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked( boolean checked ) {
		isChecked = checked;
	}

	public BubleModel( String title, boolean isChecked ) {
		this.title = title;
		this.isChecked = isChecked;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel( Parcel dest, int flags ) {
		dest.writeString( this.title );
		dest.writeByte( this.isChecked ? ( byte ) 1 : ( byte ) 0 );
	}

	protected BubleModel( Parcel in ) {
		this.title = in.readString();
		this.isChecked = in.readByte() != 0;
	}

	public static final Creator< BubleModel > CREATOR = new Creator< BubleModel >() {
		@Override
		public BubleModel createFromParcel( Parcel source ) {
			return new BubleModel( source );
		}

		@Override
		public BubleModel[] newArray( int size ) {
			return new BubleModel[size];
		}
	};
}
