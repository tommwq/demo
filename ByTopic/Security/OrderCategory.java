public enum OrderCategory {
    Buy, // 买入
    Sell, // 卖出
    Transfer, // 转股
    Repurchase, // 回售
    ETFBuy, // ETF申购
    ETFRedeem, // ETF赎回
    LOFBuy, // LOF申购
    LOFRedeem, // LOF赎回
    BuyPutExercise, // 认沽行权
    SellPutExercise, // 认售行权
    OtherSideBuy, // 对方买入
    ThisSideBuy, // 本方买入
    ImmediateBuy, // 即时买入
    StallBuy, // 五档买入
    FullAmountBuy, // 全额买入
    OtherSideSell, // 对方卖出
    ThisSideSell, // 本方卖出
    ImmediateSell, // 即时卖出
    StallSell, // 五档卖出
    FullAmountSell, // 全额卖出
    AcceptPledge, // 入质
    Pledge, // 出质
    StallThenLimitBuy, // 转限买入
    StallThenLimitSell; // 转限卖出
}


