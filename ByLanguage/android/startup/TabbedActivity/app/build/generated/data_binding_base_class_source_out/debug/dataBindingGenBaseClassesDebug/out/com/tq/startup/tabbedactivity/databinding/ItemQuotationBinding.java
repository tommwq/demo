package com.tq.startup.tabbedactivity.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.tq.startup.tabbedactivity.viewmodel.QuotationViewModel;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class ItemQuotationBinding extends ViewDataBinding {
  @NonNull
  public final TextView content;

  @Bindable
  protected QuotationViewModel mViewModel;

  protected ItemQuotationBinding(Object _bindingComponent, View _root, int _localFieldCount,
      TextView content) {
    super(_bindingComponent, _root, _localFieldCount);
    this.content = content;
  }

  public abstract void setViewModel(@Nullable QuotationViewModel viewModel);

  @Nullable
  public QuotationViewModel getViewModel() {
    return mViewModel;
  }

  @NonNull
  public static ItemQuotationBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.item_quotation, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ItemQuotationBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ItemQuotationBinding>inflateInternal(inflater, com.tq.startup.tabbedactivity.R.layout.item_quotation, root, attachToRoot, component);
  }

  @NonNull
  public static ItemQuotationBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.item_quotation, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ItemQuotationBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ItemQuotationBinding>inflateInternal(inflater, com.tq.startup.tabbedactivity.R.layout.item_quotation, null, false, component);
  }

  public static ItemQuotationBinding bind(@NonNull View view) {
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
  public static ItemQuotationBinding bind(@NonNull View view, @Nullable Object component) {
    return (ItemQuotationBinding)bind(component, view, com.tq.startup.tabbedactivity.R.layout.item_quotation);
  }
}
