
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
        return "δ����";
    case wlan_interface_state_connected:
        return "������";
    case wlan_interface_state_ad_hoc_network_formed:
        return "ad_hoc_network_formed";
    case wlan_interface_state_disconnecting:
        return "���ڶϿ�����";
    case wlan_interface_state_disconnected:
        return "�Ͽ�������";
    case wlan_interface_state_associating:
        return "associating";
    case wlan_interface_state_discovering:
        return "discovering";
    case wlan_interface_state_authenticating:
        return "������֤";
    default:
        return "δ֪����";
    }
}

void PrintWlanInterfaceInfo(WLAN_INTERFACE_INFO *info){
    _tprintf(TEXT("GUID: %s\n"), GuidToString(&(info->InterfaceGuid)));
    _tprintf(TEXT("������%s\n"), info->strInterfaceDescription);
    _tprintf(TEXT("״̬��%s\n"), WlanInterfaceStateString(info->isState));
}

void PrintWlanInterfaceInfoList(WLAN_INTERFACE_INFO_LIST *list){
    _tprintf(TEXT("��Ŀ����%d\n"), list->dwNumberOfItems);
    _tprintf(TEXT("����ֵ��%d\n"), list->dwIndex);
    for (DWORD i = 0; i < list->dwNumberOfItems; ++i){
        _tprintf(TEXT("��Ŀ %d\n"), i);
        PrintWlanInterfaceInfo(list->InterfaceInfo + i);
    }
}

void PrintWlanAvailableNetwork(WLAN_AVAILABLE_NETWORK *network){
    // todo
    _tprintf(TEXT("Profile���֣�%s\n"), network->strProfileName);
    //_tprintf(TEXT("SSID��%d\n"), network->dot11Ssid);
    //_tprintf(TEXT("%d\n"), network->dot11BssType);
    _tprintf(TEXT("BSSID��Ŀ��%d\n"), network->uNumberOfBssids);
    _tprintf(TEXT("�ɷ����ӣ�%d\n"), network->bNetworkConnectable);
    //_tprintf(TEXT("%d\n"), network->wlanNotConnectableReason);
    _tprintf(TEXT("Number of pyh types��%d\n"), network->uNumberOfPhyTypes);
    //_tprintf(TEXT("%d\n"), network->dot11PhyTypes);
    //_tprintf(TEXT("%d\n"), network->bMorePhyTypes);
    //_tprintf(TEXT("%d\n"), network->wlanSignalQuality);
    _tprintf(TEXT("��ȫ�ԣ�%d\n"), network->bSecurityEnabled);
    //_tprintf(TEXT("%d\n"), network->dot11DefaultAuthAlgorithm);
    //_tprintf(TEXT("%d\n"), network->dot11DefaultCipherAlgorithm);
    _tprintf(TEXT("��־λ��%d\n"), network->dwFlags);
}

void PrintWlanAvailableNetworkList(WLAN_AVAILABLE_NETWORK_LIST *list){
    _tprintf(TEXT("��Ŀ����%d\n"), list->dwNumberOfItems);
    _tprintf(TEXT("����ֵ��%d\n"), list->dwIndex);
    for (DWORD i = 0; i < list->dwNumberOfItems; ++i){
        _tprintf(TEXT("��Ŀ %d\n"), i);
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

    // �����߾��
    result = WlanOpenHandle(2, NULL, &clientVersion, &client);
    if (result != ERROR_SUCCESS){
        _tprintf(TEXT("�����߾������%d\n"), result);
        exit(0);
    }

    // ö��������������ӡ�б�
    WLAN_INTERFACE_INFO_LIST *interfaceList(NULL);
    result = WlanEnumInterfaces(client, NULL, &interfaceList);
    if (result != ERROR_SUCCESS){
        _tprintf(TEXT("����������������%d\n"), result);
        exit(0);
    }

    PrintWlanInterfaceInfoList(interfaceList);

    // ɨ������
    for (DWORD i = 0; i < interfaceList->dwNumberOfItems; ++i){
        WLAN_INTERFACE_INFO *info = interfaceList->InterfaceInfo + i;
        WLAN_AVAILABLE_NETWORK_LIST *networkList(NULL);
        DWORD flags = WLAN_AVAILABLE_NETWORK_INCLUDE_ALL_ADHOC_PROFILES 
            | WLAN_AVAILABLE_NETWORK_INCLUDE_ALL_MANUAL_HIDDEN_PROFILES;
        result = WlanGetAvailableNetworkList(client, &(info->InterfaceGuid), 
            flags, NULL, &networkList);
        if (result != ERROR_SUCCESS){
            _tprintf(TEXT("����������������%d\n"), result);
            exit(0);
        }

        PrintWlanAvailableNetworkList(networkList);

        WlanFreeMemory(networkList);
    }

    if (interfaceList != NULL){
        WlanFreeMemory(interfaceList);
    }

    WlanCloseHandle(client, NULL);
    _tprintf(TEXT("�����˳���"));

    std::system("pause");

    return 0;
}
