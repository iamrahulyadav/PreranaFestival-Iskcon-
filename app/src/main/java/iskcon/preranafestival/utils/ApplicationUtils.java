package iskcon.preranafestival.utils;

import android.content.Context;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import iskcon.preranafestival.R;

/**
 * Created by sibaprasad on 20/07/17.
 */

public class ApplicationUtils {
	public static void setDecoration( Context context, RecyclerView recyclerView ) {
		//add ItemDecoration
		recyclerView.addItemDecoration(
				new DividerItemDecoration( context, R.drawable.divider_drawable ) );

	}

	public static void setLayoutManager( Context context, RecyclerView recyclerView ) {
		recyclerView.setLayoutManager( new LinearLayoutManager( context ) );
	}
}
