
#include <cstdlib>
#include <clocale>
#include <tchar.h>
#include <Windows.h>
#include <wlanapi.h>

#pragma comment(lib, "wlanapi.lib")

const TCHAR* GuidToString(GUID *guid){
    static TCHAR buffer[256];
    WORD x;
    WORD y;
    DWORD z;

    x = *(WORD *)(guid->Data4);
    y = *(WORD *)(&(guid->Data4[2]));
    z = *(DWORD *)(&(guid->Data4[4]));

    _stprintf_s(buffer, TEXT("%08x-%04x-%04x-%04x-%04x%08x"), 
        guid->Data1, guid->Data2, guid->Data3, x, y, z);
    return buffer;
}

const char* WlanInterfaceStateString(WLAN_INTERFACE_STATE state){
    switch (state){
    case wlan_interface_state_not_ready:
        return "未就绪";
    case wlan_interface_state_connected:
        return "已连接";
    case wlan_interface_state_ad_hoc_network_formed:
        return "ad_hoc_network_formed";
    case wlan_interface_state_disconnecting:
        return "正在断开连接";
    case wlan_interface_state_disconnected:
        return "断开已连接";
    case wlan_interface_state_associating:
        return "associating";
    case wlan_interface_state_discovering:
        return "discovering";
    case wlan_interface_state_authenticating:
        return "正在验证";
    default:
        return "未知错误";
    }
}

void PrintWlanInterfaceInfo(WLAN_INTERFACE_INFO *info){
    _tprintf(TEXT("GUID: %s\n"), GuidToString(&(info->InterfaceGuid)));
    _tprintf(TEXT("描述：%s\n"), info->strInterfaceDescription);
    _tprintf(TEXT("状态：%s\n"), WlanInterfaceStateString(info->isState));
}

void PrintWlanInterfaceInfoList(WLAN_INTERFACE_INFO_LIST *list){
    _tprintf(TEXT("条目数：%d\n"), list->dwNumberOfItems);
    _tprintf(TEXT("索引值：%d\n"), list->dwIndex);
    for (DWORD i = 0; i < list->dwNumberOfItems; ++i){
        _tprintf(TEXT("条目 %d\n"), i);
        PrintWlanInterfaceInfo(list->InterfaceInfo + i);
    }
}

void PrintWlanAvailableNetwork(WLAN_AVAILABLE_NETWORK *network){
    // todo
    _tprintf(TEXT("Profile名字：%s\n"), network->strProfileName);
    //_tprintf(TEXT("SSID：%d\n"), network->dot11Ssid);
    //_tprintf(TEXT("%d\n"), network->dot11BssType);
    _tprintf(TEXT("BSSID数目：%d\n"), network->uNumberOfBssids);
    _tprintf(TEXT("可否连接：%d\n"), network->bNetworkConnectable);
    //_tprintf(TEXT("%d\n"), network->wlanNotConnectableReason);
    _tprintf(TEXT("Number of pyh types：%d\n"), network->uNumberOfPhyTypes);
    //_tprintf(TEXT("%d\n"), network->dot11PhyTypes);
    //_tprintf(TEXT("%d\n"), network->bMorePhyTypes);
    //_tprintf(TEXT("%d\n"), network->wlanSignalQuality);
    _tprintf(TEXT("安全性：%d\n"), network->bSecurityEnabled);
    //_tprintf(TEXT("%d\n"), network->dot11DefaultAuthAlgorithm);
    //_tprintf(TEXT("%d\n"), network->dot11DefaultCipherAlgorithm);
    _tprintf(TEXT("标志位：%d\n"), network->dwFlags);
}

void PrintWlanAvailableNetworkList(WLAN_AVAILABLE_NETWORK_LIST *list){
    _tprintf(TEXT("条目数：%d\n"), list->dwNumberOfItems);
    _tprintf(TEXT("索引值：%d\n"), list->dwIndex);
    for (DWORD i = 0; i < list->dwNumberOfItems; ++i){
        _tprintf(TEXT("条目 %d\n"), i);
        PrintWlanAvailableNetwork(list->Network + i);
    }
}

int _tmain(int argc, _TCHAR* argv[])
{
    setlocale(LC_ALL, "chs");

    DWORD result;
    DWORD clientVersion;
    HANDLE client;

    clientVersion = WLAN_API_MAKE_VERSION(2, 0);

    // 打开无线句柄
    result = WlanOpenHandle(2, NULL, &clientVersion, &client);
    if (result != ERROR_SUCCESS){
        _tprintf(TEXT("打开无线句柄错误：%d\n"), result);
        exit(0);
    }

    // 枚举无线网卡，打印列表
    WLAN_INTERFACE_INFO_LIST *interfaceList(NULL);
    result = WlanEnumInterfaces(client, NULL, &interfaceList);
    if (result != ERROR_SUCCESS){
        _tprintf(TEXT("打开无线网络句柄错误：%d\n"), result);
        exit(0);
    }

    PrintWlanInterfaceInfoList(interfaceList);

    // 扫描网络
    for (DWORD i = 0; i < interfaceList->dwNumberOfItems; ++i){
        WLAN_INTERFACE_INFO *info = interfaceList->InterfaceInfo + i;
        WLAN_AVAILABLE_NETWORK_LIST *networkList(NULL);
        DWORD flags = WLAN_AVAILABLE_NETWORK_INCLUDE_ALL_ADHOC_PROFILES 
            | WLAN_AVAILABLE_NETWORK_INCLUDE_ALL_MANUAL_HIDDEN_PROFILES;
        result = WlanGetAvailableNetworkList(client, &(info->InterfaceGuid), 
            flags, NULL, &networkList);
        if (result != ERROR_SUCCESS){
            _tprintf(TEXT("打开无线网络句柄错误：%d\n"), result);
            exit(0);
        }

        PrintWlanAvailableNetworkList(networkList);

        WlanFreeMemory(networkList);
    }

    if (interfaceList != NULL){
        WlanFreeMemory(interfaceList);
    }

    WlanCloseHandle(client, NULL);
    _tprintf(TEXT("正常退出。"));

    std::system("pause");

    return 0;
}
