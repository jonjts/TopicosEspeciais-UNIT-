package br.com.unit.tec.unitplus.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.wearable.view.CardFragment;
import android.support.wearable.view.FragmentGridPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import br.com.unit.tec.unitplus.R;
import br.com.unit.tec.unitplus.entity.Element;

public class ElementGridPagerAdapter extends FragmentGridPagerAdapter {

    private Context mContext;
    private List<Row> mRows;
    private List<Element> mElement;
    private Drawable mBackgroundDrawable;

    public ElementGridPagerAdapter(Context context, List<Element> elements, FragmentManager fm) {
        super(fm);

        this.mContext = context;
        this.mRows = new ArrayList<>();
        this.mElement = elements;

        this.mBackgroundDrawable = mContext.getResources().getDrawable(R.drawable.wearmenu_background);

        for (Element element : elements) {
            mRows.add(new Row(
                            CardFragment.create(element.getTitre(), element.getTexte()),
                            createActionFragment()
                    )
            );
        }
    }

    private Fragment createActionFragment() {
        return new Fragment(){
            @Nullable
            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
                return inflater.inflate(R.layout.fragment_action,container,false);
            }
        };
    }

    @Override
    public Fragment getFragment(int row, int col) {
        Row adapterRow = mRows.get(row);
        return adapterRow.getColumn(col);
    }

    @Override
    public Drawable getBackgroundForRow(final int row) {
        return mBackgroundDrawable;
    }

    @Override
    public Drawable getBackgroundForPage(final int row, final int column) {
        return getBackgroundForRow(row);
    }

    @Override
    public int getRowCount() {
        return mRows.size();
    }

    @Override
    public int getColumnCount(int rowNum) {
        return mRows.get(rowNum).getColumnCount();
    }

    private class Row {
        final List<Fragment> columns = new ArrayList<Fragment>();

        public Row(Fragment... fragments) {
            for (Fragment f : fragments) {
                add(f);
            }
        }

        public void add(Fragment f) {
            columns.add(f);
        }

        Fragment getColumn(int i) {
            return columns.get(i);
        }

        public int getColumnCount() {
            return columns.size();
        }
    }

}