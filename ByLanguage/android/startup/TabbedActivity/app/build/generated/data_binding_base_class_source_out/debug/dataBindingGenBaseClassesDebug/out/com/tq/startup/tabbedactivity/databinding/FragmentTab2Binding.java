package com.tq.startup.tabbedactivity.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.tq.startup.tabbedactivity.viewmodel.Tab2ViewModel;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class FragmentTab2Binding extends ViewDataBinding {
  @NonNull
  public final EditText author;

  @NonNull
  public final EditText content;

  @Bindable
  protected Tab2ViewModel mViewModel;

  protected FragmentTab2Binding(Object _bindingComponent, View _root, int _localFieldCount,
      EditText author, EditText content) {
    super(_bindingComponent, _root, _localFieldCount);
    this.author = author;
    this.content = content;
  }

  public abstract void setViewModel(@Nullable Tab2ViewModel viewModel);

  @Nullable
  public Tab2ViewModel getViewModel() {
    return mViewModel;
  }

  @NonNull
  public static FragmentTab2Binding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_tab2, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static FragmentTab2Binding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<FragmentTab2Binding>inflateInternal(inflater, com.tq.startup.tabbedactivity.R.layout.fragment_tab2, root, attachToRoot, component);
  }

  @NonNull
  public static FragmentTab2Binding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_tab2, null, false, component)
   */
  @NonNull
  @Deprecated
  public static FragmentTab2Binding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<FragmentTab2Binding>inflateInternal(inflater, com.tq.startup.tabbedactivity.R.layout.fragment_tab2, null, false, component);
  }

  public static FragmentTab2Binding bind(@NonNull View view) {
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
  public static FragmentTab2Binding bind(@NonNull View view, @Nullable Object component) {
    return (FragmentTab2Binding)bind(component, view, com.tq.startup.tabbedactivity.R.layout.fragment_tab2);
  }
}
