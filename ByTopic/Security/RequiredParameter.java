import java.util.Optional
    
public class RequiredParameter {
// 功能号	funcid	INT	Y	外围功能编号
// 帐户类型	inputtype	char(1)	Y	对于参数类交易可以为空
// 帐户标识	inputid	char(64)	Y	对于参数类交易可以为空
// 客户机构	custorgid 	char(4)	Y	客户所属机构
// 操作站点	netaddr	char(15)	Y	网卡地址或电话号码。
// 操作机构	orgid 	char(4)	Y	操作地机构代码
// 操作方式	operway	char(1)	Y	
// 客户认证号	authid	char(64)	N	可以为空
// 接入主站	site	char(32)	N	可以为空
// 扩展属性	ext1	char(32)	Y	登录时后台返回，参见420301
// 备用	ext2	char(32)	N	网卡地址2

    int functionNumber;
    // AccountCategory accountCategory;
    String accountId;
    String customerOrgnizationId;
    String operationSource;
    String orgnizationCategory;
    OperationChannel operationChannel;
    String authorizationToken;
    String Site;
    String ext1;
    String ext2;
}
