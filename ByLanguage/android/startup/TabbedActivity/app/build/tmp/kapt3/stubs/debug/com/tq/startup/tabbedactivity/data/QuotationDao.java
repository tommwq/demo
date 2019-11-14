package com.tq.startup.tabbedactivity.data;

import java.lang.System;

@androidx.room.Dao()
@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\bg\u0018\u00002\u00020\u0001J\u0014\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0003H\'J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0005H\'\u00a8\u0006\t"}, d2 = {"Lcom/tq/startup/tabbedactivity/data/QuotationDao;", "", "getQuotations", "Landroidx/lifecycle/LiveData;", "", "Lcom/tq/startup/tabbedactivity/data/Quotation;", "insert", "", "quotation", "app_debug"})
public abstract interface QuotationDao {
    
    @org.jetbrains.annotations.NotNull()
    @androidx.room.Query(value = "SELECT id,author,content FROM quotation")
    public abstract androidx.lifecycle.LiveData<java.util.List<com.tq.startup.tabbedactivity.data.Quotation>> getQuotations();
    
    @androidx.room.Insert()
    public abstract void insert(@org.jetbrains.annotations.NotNull()
    com.tq.startup.tabbedactivity.data.Quotation quotation);
}