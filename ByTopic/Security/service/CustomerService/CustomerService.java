public interface CustomerService {
    void login();
    void updateTradePassword();
    void updateCashAccountPassword();
    void prepareOpenAccount();
    void prepareOpenAccountAddAccount();
    void prepareOpenAccountTellerVerify();
    void prepareOpenAccountCreateCashAccount();
    void prepareOpenAccountQueryBankAccount();
    void updateCustomerInfomation();
    void getCustomerInformation();
    void assignPeriodicalInvestFundContract();
    void updatePeriodicalInvestFundContract();
    void quitPeriodicalInvestFundContract();
    void listAssignedPeriodicalInvestFundContract();
    void updateBrokePermission();
    void updateOperationChannel();
    void getOperationChannel();
    void getOpenAccountInformation();

    void getAccessToken();
    void verifyAccessToken();
    void queryCustomerPermission();
    void resetVerifyPassword();
    void cancelVerifyPassword();
}
