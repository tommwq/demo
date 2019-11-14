package com.tq.startup.tabbedactivity.data;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u000f2\u00020\u0001:\u0001\u000fB\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J!\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\nJ\u0012\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0010"}, d2 = {"Lcom/tq/startup/tabbedactivity/data/QuotationRepository;", "", "quotationDao", "Lcom/tq/startup/tabbedactivity/data/QuotationDao;", "(Lcom/tq/startup/tabbedactivity/data/QuotationDao;)V", "addQuotation", "", "author", "", "content", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getQuotations", "Landroidx/lifecycle/LiveData;", "", "Lcom/tq/startup/tabbedactivity/data/Quotation;", "Companion", "app_debug"})
public final class QuotationRepository {
    private final com.tq.startup.tabbedactivity.data.QuotationDao quotationDao = null;
    private static volatile com.tq.startup.tabbedactivity.data.QuotationRepository instance;
    public static final com.tq.startup.tabbedactivity.data.QuotationRepository.Companion Companion = null;
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.tq.startup.tabbedactivity.data.Quotation>> getQuotations() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object addQuotation(@org.jetbrains.annotations.NotNull()
    java.lang.String author, @org.jetbrains.annotations.NotNull()
    java.lang.String content, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> p2) {
        return null;
    }
    
    private QuotationRepository(com.tq.startup.tabbedactivity.data.QuotationDao quotationDao) {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2 = {"Lcom/tq/startup/tabbedactivity/data/QuotationRepository$Companion;", "", "()V", "instance", "Lcom/tq/startup/tabbedactivity/data/QuotationRepository;", "getInstance", "quotationDao", "Lcom/tq/startup/tabbedactivity/data/QuotationDao;", "app_debug"})
    public static final class Companion {
        
        @org.jetbrains.annotations.NotNull()
        public final com.tq.startup.tabbedactivity.data.QuotationRepository getInstance(@org.jetbrains.annotations.NotNull()
        com.tq.startup.tabbedactivity.data.QuotationDao quotationDao) {
            return null;
        }
        
        private Companion() {
            super();
        }
    }
}