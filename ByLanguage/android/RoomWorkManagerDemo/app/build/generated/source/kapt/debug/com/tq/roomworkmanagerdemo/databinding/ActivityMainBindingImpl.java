package com.tq.roomworkmanagerdemo.databinding;
import com.tq.roomworkmanagerdemo.R;
import com.tq.roomworkmanagerdemo.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityMainBindingImpl extends ActivityMainBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = null;
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    // variables
    // values
    // listeners
    private OnClickListenerImpl mModelCreateUserAndroidViewViewOnClickListener;
    // Inverse Binding Event Handlers
    private androidx.databinding.InverseBindingListener edittextandroidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of model.name.getValue()
            //         is model.name.setValue((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(edittext);
            // localize variables for thread safety
            // model
            com.tq.roomworkmanagerdemo.UserViewModel model = mModel;
            // model.name.getValue()
            java.lang.String modelNameGetValue = null;
            // model.name
            androidx.lifecycle.MutableLiveData<java.lang.String> modelName = null;
            // model != null
            boolean modelJavaLangObjectNull = false;
            // model.name != null
            boolean modelNameJavaLangObjectNull = false;



            modelJavaLangObjectNull = (model) != (null);
            if (modelJavaLangObjectNull) {


                modelName = model.getName();

                modelNameJavaLangObjectNull = (modelName) != (null);
                if (modelNameJavaLangObjectNull) {




                    modelName.setValue(((java.lang.String) (callbackArg_0)));
                }
            }
        }
    };

    public ActivityMainBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 4, sIncludes, sViewsWithIds));
    }
    private ActivityMainBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 2
            , (android.widget.Button) bindings[3]
            , (android.widget.EditText) bindings[2]
            , (android.widget.TextView) bindings[1]
            );
        this.button.setTag(null);
        this.edittext.setTag(null);
        this.mboundView0 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.textview.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x8L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.model == variableId) {
            setModel((com.tq.roomworkmanagerdemo.UserViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setModel(@Nullable com.tq.roomworkmanagerdemo.UserViewModel Model) {
        this.mModel = Model;
        synchronized(this) {
            mDirtyFlags |= 0x4L;
        }
        notifyPropertyChanged(BR.model);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeModelName((androidx.lifecycle.MutableLiveData<java.lang.String>) object, fieldId);
            case 1 :
                return onChangeModelId((androidx.databinding.ObservableField<java.lang.String>) object, fieldId);
        }
        return false;
    }
    private boolean onChangeModelName(androidx.lifecycle.MutableLiveData<java.lang.String> ModelName, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeModelId(androidx.databinding.ObservableField<java.lang.String> ModelId, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x2L;
            }
            return true;
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        com.tq.roomworkmanagerdemo.UserViewModel model = mModel;
        java.lang.String modelIdGet = null;
        java.lang.String modelNameGetValue = null;
        androidx.lifecycle.MutableLiveData<java.lang.String> modelName = null;
        androidx.databinding.ObservableField<java.lang.String> modelId = null;
        android.view.View.OnClickListener modelCreateUserAndroidViewViewOnClickListener = null;

        if ((dirtyFlags & 0xfL) != 0) {


            if ((dirtyFlags & 0xdL) != 0) {

                    if (model != null) {
                        // read model.name
                        modelName = model.getName();
                    }
                    updateLiveDataRegistration(0, modelName);


                    if (modelName != null) {
                        // read model.name.getValue()
                        modelNameGetValue = modelName.getValue();
                    }
            }
            if ((dirtyFlags & 0xeL) != 0) {

                    if (model != null) {
                        // read model.id
                        modelId = model.getId();
                    }
                    updateRegistration(1, modelId);


                    if (modelId != null) {
                        // read model.id.get()
                        modelIdGet = modelId.get();
                    }
            }
            if ((dirtyFlags & 0xcL) != 0) {

                    if (model != null) {
                        // read model::createUser
                        modelCreateUserAndroidViewViewOnClickListener = (((mModelCreateUserAndroidViewViewOnClickListener == null) ? (mModelCreateUserAndroidViewViewOnClickListener = new OnClickListenerImpl()) : mModelCreateUserAndroidViewViewOnClickListener).setValue(model));
                    }
            }
        }
        // batch finished
        if ((dirtyFlags & 0xcL) != 0) {
            // api target 1

            this.button.setOnClickListener(modelCreateUserAndroidViewViewOnClickListener);
        }
        if ((dirtyFlags & 0xdL) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.edittext, modelNameGetValue);
        }
        if ((dirtyFlags & 0x8L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.edittext, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, edittextandroidTextAttrChanged);
        }
        if ((dirtyFlags & 0xeL) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.textview, modelIdGet);
        }
    }
    // Listener Stub Implementations
    public static class OnClickListenerImpl implements android.view.View.OnClickListener{
        private com.tq.roomworkmanagerdemo.UserViewModel value;
        public OnClickListenerImpl setValue(com.tq.roomworkmanagerdemo.UserViewModel value) {
            this.value = value;
            return value == null ? null : this;
        }
        @Override
        public void onClick(android.view.View arg0) {
            this.value.createUser(arg0); 
        }
    }
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): model.name
        flag 1 (0x2L): model.id
        flag 2 (0x3L): model
        flag 3 (0x4L): null
    flag mapping end*/
    //end
}