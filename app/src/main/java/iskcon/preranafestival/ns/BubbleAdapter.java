package iskcon.preranafestival.ns;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import java.util.List;

import iskcon.preranafestival.R;

public class BubbleAdapter extends RecyclerView.Adapter< BubbleAdapter.BubbleViewHolder > {
	Context              context;
	List< BubleModel >   listBuble;
	LayoutInflater       inflater;
	OnBubleCheckListener onBubleCheckListener;

	public BubbleAdapter( Context context, List< BubleModel > listBuble, OnBubleCheckListener onBubleCheckListener ) {
		this.context = context;
		this.listBuble = listBuble;
		inflater = LayoutInflater.from( context );
		this.onBubleCheckListener = onBubleCheckListener;
	}

	@Override
	public BubbleViewHolder onCreateViewHolder( ViewGroup parent, int viewType ) {
		View             view       = inflater.inflate( R.layout.itemview_recycler, parent, false );
		BubbleViewHolder viewHolder = new BubbleViewHolder( view );
		return viewHolder;
	}

	@Override
	public void onBindViewHolder( BubbleViewHolder holder, final int position ) {
		holder.textview.setText( listBuble.get( position ).title );
		if ( listBuble.get( position ).isChecked ) {
			holder.checkbox.setChecked( true );
		}
		else {
			holder.checkbox.setChecked( false );
		}
		holder.checkbox.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged( CompoundButton compoundButton, boolean b ) {
				if ( onBubleCheckListener != null ) {
					onBubleCheckListener.onBubleCheck( b, position );
				}
			}
		} );
	}

	@Override
	public int getItemCount() {
		return listBuble.size();
	}

	public class BubbleViewHolder extends RecyclerView.ViewHolder {
		AppCompatTextView textview;
		AppCompatCheckBox checkbox;

		public BubbleViewHolder( View itemView ) {
			super( itemView );
			textview = ( AppCompatTextView ) itemView.findViewById( R.id.textview );
			checkbox = ( AppCompatCheckBox ) itemView.findViewById( R.id.checkbox );
		}
	}
}
