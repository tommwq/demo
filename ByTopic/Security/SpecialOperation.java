public enum SpecialOperation {
// 0	融资买入
// 1	融券卖出
// 2	买券还券
// 3	余券划转
// 4	现券还券
// 5	担保品划入
// 6	担保品划出
// 7	卖券还款
// 0	融资开仓
// 1	融资平仓
// 2	融资强平
// 3	买入担保品
// 4	卖出担保品
// 5	非交易过户
// A	融券开仓
// B	融券平仓
// C	融券强平
    MarginBuy,
    MarginSell,
    ReturnBondAfterBuy, // 买券还券
    TransferRemainBond,
    ReturnBondUsingBelonged, // 现券换券
    TransferMortageIn,
    TransferMortageOut,
    ReturnMoneyAfterSell,
    MarginOpen,
    MarginClose,
    MarginCloseForce,
    BuyMortage,
    SellMortage,
    NonDealTransfer;
}
