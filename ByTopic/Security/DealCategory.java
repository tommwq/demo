public enum DealCategory {
    // 0	正常交易
    // 1	缴款
    // 2	申购
    // 3	增发申购
    // 4	配号
    // 7	配售
    // 8	配售配号
    // 9	跨系统转托管
    // A	设置分红方式
    // B	ETF认购
    // X	现金替代
    // C	ETF申购
    // D	基金金额认购
    // E	行权
    // G	质押

    NormalDeal, // 正常交易
    Pay, // 缴款
    Subscribe, // 申购
    SubscribeFollowOnOffering, // 增发申购
    AssignNumber, // 配号
    Place, // 配售
    DesignationTransfer, // 转托管
    AssignBonusPattern, // 设置分红方式
    OfferToBuyETF, // ETF认购
    CashSubstitution, // 现金替代
    BuyETF, // ETF申购
    OfferToBuyFund, // 基金金额认购
    Put, // 行权
    Pledge; // 质押
}
