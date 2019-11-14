package com.tq.navigationdemo.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

public abstract class FragmentMain1Binding extends ViewDataBinding {
  @NonNull
  public final TextView message;

  protected FragmentMain1Binding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, TextView message) {
    super(_bindingComponent, _root, _localFieldCount);
    this.message = message;
  }

  @NonNull
  public static FragmentMain1Binding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static FragmentMain1Binding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<FragmentMain1Binding>inflate(inflater, com.tq.navigationdemo.R.layout.fragment_main1, root, attachToRoot, component);
  }

  @NonNull
  public static FragmentMain1Binding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static FragmentMain1Binding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<FragmentMain1Binding>inflate(inflater, com.tq.navigationdemo.R.layout.fragment_main1, null, false, component);
  }

  public static FragmentMain1Binding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static FragmentMain1Binding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (FragmentMain1Binding)bind(component, view, com.tq.navigationdemo.R.layout.fragment_main1);
  }
}
