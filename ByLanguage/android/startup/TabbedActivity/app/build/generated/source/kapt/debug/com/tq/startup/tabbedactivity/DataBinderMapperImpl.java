package com.tq.startup.tabbedactivity;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.tq.startup.tabbedactivity.databinding.ActivityMainBindingImpl;
import com.tq.startup.tabbedactivity.databinding.FragmentTab1BindingImpl;
import com.tq.startup.tabbedactivity.databinding.FragmentTab2BindingImpl;
import com.tq.startup.tabbedactivity.databinding.FragmentTab3BindingImpl;
import com.tq.startup.tabbedactivity.databinding.FragmentTab4BindingImpl;
import com.tq.startup.tabbedactivity.databinding.ItemQuotationBindingImpl;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBinderMapperImpl extends DataBinderMapper {
  private static final int LAYOUT_ACTIVITYMAIN = 1;

  private static final int LAYOUT_FRAGMENTTAB1 = 2;

  private static final int LAYOUT_FRAGMENTTAB2 = 3;

  private static final int LAYOUT_FRAGMENTTAB3 = 4;

  private static final int LAYOUT_FRAGMENTTAB4 = 5;

  private static final int LAYOUT_ITEMQUOTATION = 6;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(6);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.tq.startup.tabbedactivity.R.layout.activity_main, LAYOUT_ACTIVITYMAIN);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.tq.startup.tabbedactivity.R.layout.fragment_tab1, LAYOUT_FRAGMENTTAB1);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.tq.startup.tabbedactivity.R.layout.fragment_tab2, LAYOUT_FRAGMENTTAB2);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.tq.startup.tabbedactivity.R.layout.fragment_tab3, LAYOUT_FRAGMENTTAB3);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.tq.startup.tabbedactivity.R.layout.fragment_tab4, LAYOUT_FRAGMENTTAB4);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.tq.startup.tabbedactivity.R.layout.item_quotation, LAYOUT_ITEMQUOTATION);
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = view.getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
        case  LAYOUT_ACTIVITYMAIN: {
          if ("layout/activity_main_0".equals(tag)) {
            return new ActivityMainBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_main is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTTAB1: {
          if ("layout/fragment_tab1_0".equals(tag)) {
            return new FragmentTab1BindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_tab1 is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTTAB2: {
          if ("layout/fragment_tab2_0".equals(tag)) {
            return new FragmentTab2BindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_tab2 is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTTAB3: {
          if ("layout/fragment_tab3_0".equals(tag)) {
            return new FragmentTab3BindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_tab3 is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTTAB4: {
          if ("layout/fragment_tab4_0".equals(tag)) {
            return new FragmentTab4BindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_tab4 is invalid. Received: " + tag);
        }
        case  LAYOUT_ITEMQUOTATION: {
          if ("layout/item_quotation_0".equals(tag)) {
            return new ItemQuotationBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for item_quotation is invalid. Received: " + tag);
        }
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
    if(views == null || views.length == 0) {
      return null;
    }
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = views[0].getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
      }
    }
    return null;
  }

  @Override
  public int getLayoutId(String tag) {
    if (tag == null) {
      return 0;
    }
    Integer tmpVal = InnerLayoutIdLookup.sKeys.get(tag);
    return tmpVal == null ? 0 : tmpVal;
  }

  @Override
  public String convertBrIdToString(int localId) {
    String tmpVal = InnerBrLookup.sKeys.get(localId);
    return tmpVal;
  }

  @Override
  public List<DataBinderMapper> collectDependencies() {
    ArrayList<DataBinderMapper> result = new ArrayList<DataBinderMapper>(1);
    result.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(3);

    static {
      sKeys.put(0, "_all");
      sKeys.put(1, "viewModel");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(6);

    static {
      sKeys.put("layout/activity_main_0", com.tq.startup.tabbedactivity.R.layout.activity_main);
      sKeys.put("layout/fragment_tab1_0", com.tq.startup.tabbedactivity.R.layout.fragment_tab1);
      sKeys.put("layout/fragment_tab2_0", com.tq.startup.tabbedactivity.R.layout.fragment_tab2);
      sKeys.put("layout/fragment_tab3_0", com.tq.startup.tabbedactivity.R.layout.fragment_tab3);
      sKeys.put("layout/fragment_tab4_0", com.tq.startup.tabbedactivity.R.layout.fragment_tab4);
      sKeys.put("layout/item_quotation_0", com.tq.startup.tabbedactivity.R.layout.item_quotation);
    }
  }
}
