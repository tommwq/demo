package com.tq.startup.tabbedactivity.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.tq.startup.tabbedactivity.viewmodel.Tab4ViewModel;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class FragmentTab4Binding extends ViewDataBinding {
  @Bindable
  protected Tab4ViewModel mViewModel;

  protected FragmentTab4Binding(Object _bindingComponent, View _root, int _localFieldCount) {
    super(_bindingComponent, _root, _localFieldCount);
  }

  public abstract void setViewModel(@Nullable Tab4ViewModel viewModel);

  @Nullable
  public Tab4ViewModel getViewModel() {
    return mViewModel;
  }

  @NonNull
  public static FragmentTab4Binding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_tab4, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static FragmentTab4Binding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<FragmentTab4Binding>inflateInternal(inflater, com.tq.startup.tabbedactivity.R.layout.fragment_tab4, root, attachToRoot, component);
  }

  @NonNull
  public static FragmentTab4Binding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_tab4, null, false, component)
   */
  @NonNull
  @Deprecated
  public static FragmentTab4Binding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<FragmentTab4Binding>inflateInternal(inflater, com.tq.startup.tabbedactivity.R.layout.fragment_tab4, null, false, component);
  }

  public static FragmentTab4Binding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.bind(view, component)
   */
  @Deprecated
  public static FragmentTab4Binding bind(@NonNull View view, @Nullable Object component) {
    return (FragmentTab4Binding)bind(component, view, com.tq.startup.tabbedactivity.R.layout.fragment_tab4);
  }
}
