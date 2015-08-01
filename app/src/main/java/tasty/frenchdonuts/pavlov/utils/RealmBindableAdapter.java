package tasty.frenchdonuts.pavlov.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.realm.RealmBaseAdapter;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by frenchdonuts on 1/6/15.
 */
public abstract class RealmBindableAdapter<T extends RealmObject> extends RealmBaseAdapter {

	public RealmBindableAdapter(Context context, RealmResults<T> realmResults, boolean automaticUpdate) {
		super(context, realmResults, automaticUpdate);
	}

	@Override public final View getView(int position, View view, ViewGroup container) {
		if (view == null) {
			view = newView(inflater, position, container);
			if (view == null) {
				throw new IllegalStateException("newView result must not be null.");
			}
		}
		bindView(getItem(position), position, view);
		return view;
	}

	@Override
	public T getItem(int i) {
		return (T) realmResults.get(i);
	}

	/** Create a new instance of a view for the specified position. */
	public abstract View newView(LayoutInflater inflater, int position, ViewGroup container);

	/** Bind the data for the specified {@code position} to the view. */
	public abstract void bindView(T item, int position, View view);

	@Override public final View getDropDownView(int position, View view, ViewGroup container) {
		if (view == null) {
			view = newDropDownView(inflater, position, container);
			if (view == null) {
				throw new IllegalStateException("newDropDownView result must not be null.");
			}
		}
		bindDropDownView(getItem(position), position, view);
		return view;
	}

	/** Create a new instance of a drop-down view for the specified position. */
	public View newDropDownView(LayoutInflater inflater, int position, ViewGroup container) {
		return newView(inflater, position, container);
	}

	/** Bind the data for the specified {@code position} to the drop-down view. */
	public void bindDropDownView(T item, int position, View view) {
		bindView(item, position, view);
	}
}
