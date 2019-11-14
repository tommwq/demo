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

public abstract class FragmentMain2Binding extends ViewDataBinding {
  @NonNull
  public final TextView message;

  protected FragmentMain2Binding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, TextView message) {
    super(_bindingComponent, _root, _localFieldCount);
    this.message = message;
  }

  @NonNull
  public static FragmentMain2Binding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static FragmentMain2Binding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<FragmentMain2Binding>inflate(inflater, com.tq.navigationdemo.R.layout.fragment_main2, root, attachToRoot, component);
  }

  @NonNull
  public static FragmentMain2Binding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static FragmentMain2Binding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<FragmentMain2Binding>inflate(inflater, com.tq.navigationdemo.R.layout.fragment_main2, null, false, component);
  }

  public static FragmentMain2Binding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static FragmentMain2Binding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (FragmentMain2Binding)bind(component, view, com.tq.navigationdemo.R.layout.fragment_main2);
  }
}
