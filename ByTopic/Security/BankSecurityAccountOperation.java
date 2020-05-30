public enum BankSecurityAccountOperation {
// 代码	名称
// 1	银行转证券
// 2	证券转银行
// 3	查证券余额
// 4	查银行余额
// 5	冲银行转证券
// 6	冲证券转银行
// 7	开户
// 8	销户
// M	转帐调整

    BankToSecurity,
    SecurityToBank,
    QuerySecurityBalance,
    QueryBankBalance,
    WriteOffBankToSecurity,
    WriteOffSecurityToBank,
    OpenAccount,
    CloseAccount,
    AccountAdjust;
}
