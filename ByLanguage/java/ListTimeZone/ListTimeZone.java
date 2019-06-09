import java.util.*;

public class ListTimeZone {

    public static void main(String... args) {
        String[] idArray = TimeZone.getAvailableIDs();

        System.out.println("id, display name, raw offset");
        System.out.println("============================");

        for (String id: idArray) {
            TimeZone timeZone = TimeZone.getTimeZone(id);
            String displayName = timeZone.getDisplayName();
            int rawOffset = timeZone.getRawOffset();

            System.out.printf("%s, %s, %d\n", id, displayName, rawOffset);
        }

        System.out.println("============================");
    }
}

/*
id, display name, raw offset
============================
Africa/Abidjan, 格林威治时间, 0
Africa/Accra, 加纳时间, 0
Africa/Addis_Ababa, 东非时间, 10800000
Africa/Algiers, 中欧时间, 3600000
Africa/Asmara, 东非时间, 10800000
Africa/Asmera, 东非时间, 10800000
Africa/Bamako, 格林威治时间, 0
Africa/Bangui, 西非时间, 3600000
Africa/Banjul, 格林威治时间, 0
Africa/Bissau, 格林威治时间, 0
Africa/Blantyre, 中非时间, 7200000
Africa/Brazzaville, 西非时间, 3600000
Africa/Bujumbura, 中非时间, 7200000
Africa/Cairo, 东欧时间, 7200000
Africa/Casablanca, 西欧时间, 0
Africa/Ceuta, 中欧时间, 3600000
Africa/Conakry, 格林威治时间, 0
Africa/Dakar, 格林威治时间, 0
Africa/Dar_es_Salaam, 东非时间, 10800000
Africa/Djibouti, 东非时间, 10800000
Africa/Douala, 西非时间, 3600000
Africa/El_Aaiun, 西欧时间, 0
Africa/Freetown, 格林威治时间, 0
Africa/Gaborone, 中非时间, 7200000
Africa/Harare, 中非时间, 7200000
Africa/Johannesburg, 南非标准时间, 7200000
Africa/Juba, 东非时间, 10800000
Africa/Kampala, 东非时间, 10800000
Africa/Khartoum, 东非时间, 10800000
Africa/Kigali, 中非时间, 7200000
Africa/Kinshasa, 西非时间, 3600000
Africa/Lagos, 西非时间, 3600000
Africa/Libreville, 西非时间, 3600000
Africa/Lome, 格林威治时间, 0
Africa/Luanda, 西非时间, 3600000
Africa/Lubumbashi, 中非时间, 7200000
Africa/Lusaka, 中非时间, 7200000
Africa/Malabo, 西非时间, 3600000
Africa/Maputo, 中非时间, 7200000
Africa/Maseru, 南非标准时间, 7200000
Africa/Mbabane, 南非标准时间, 7200000
Africa/Mogadishu, 东非时间, 10800000
Africa/Monrovia, 格林威治时间, 0
Africa/Nairobi, 东非时间, 10800000
Africa/Ndjamena, 西非时间, 3600000
Africa/Niamey, 西非时间, 3600000
Africa/Nouakchott, 格林威治时间, 0
Africa/Ouagadougou, 格林威治时间, 0
Africa/Porto-Novo, 西非时间, 3600000
Africa/Sao_Tome, 格林威治时间, 0
Africa/Timbuktu, 格林威治时间, 0
Africa/Tripoli, 东欧时间, 7200000
Africa/Tunis, 中欧时间, 3600000
Africa/Windhoek, 西非时间, 3600000
America/Adak, 夏威夷标准时间, -36000000
America/Anchorage, 阿拉斯加标准时间, -32400000
America/Anguilla, 大西洋标准时间, -14400000
America/Antigua, 大西洋标准时间, -14400000
America/Araguaina, 巴西利亚时间, -10800000
America/Argentina/Buenos_Aires, 阿根廷时间, -10800000
America/Argentina/Catamarca, 阿根廷时间, -10800000
America/Argentina/ComodRivadavia, 阿根廷时间, -10800000
America/Argentina/Cordoba, 阿根廷时间, -10800000
America/Argentina/Jujuy, 阿根廷时间, -10800000
America/Argentina/La_Rioja, 阿根廷时间, -10800000
America/Argentina/Mendoza, 阿根廷时间, -10800000
America/Argentina/Rio_Gallegos, 阿根廷时间, -10800000
America/Argentina/Salta, 阿根廷时间, -10800000
America/Argentina/San_Juan, 阿根廷时间, -10800000
America/Argentina/San_Luis, 阿根廷时间, -10800000
America/Argentina/Tucuman, 阿根廷时间, -10800000
America/Argentina/Ushuaia, 阿根廷时间, -10800000
America/Aruba, 大西洋标准时间, -14400000
America/Asuncion, 巴拉圭时间, -14400000
America/Atikokan, 东部标准时间, -18000000
America/Atka, 夏威夷标准时间, -36000000
America/Bahia, 巴西利亚时间, -10800000
America/Bahia_Banderas, 中央标准时间, -21600000
America/Barbados, 大西洋标准时间, -14400000
America/Belem, 巴西利亚时间, -10800000
America/Belize, 中央标准时间, -21600000
America/Blanc-Sablon, 大西洋标准时间, -14400000
America/Boa_Vista, 亚马逊时间, -14400000
America/Bogota, 哥伦比亚时间, -18000000
America/Boise, Mountain 标准时间, -25200000
America/Buenos_Aires, 阿根廷时间, -10800000
America/Cambridge_Bay, Mountain 标准时间, -25200000
America/Campo_Grande, 亚马逊时间, -14400000
America/Cancun, 东部标准时间, -18000000
America/Caracas, 委内瑞拉时间, -16200000
America/Catamarca, 阿根廷时间, -10800000
America/Cayenne, 法属圭亚那时间, -10800000
America/Cayman, 东部标准时间, -18000000
America/Chicago, 中央标准时间, -21600000
America/Chihuahua, Mountain 标准时间, -25200000
America/Coral_Harbour, 东部标准时间, -18000000
America/Cordoba, 阿根廷时间, -10800000
America/Costa_Rica, 中央标准时间, -21600000
America/Creston, Mountain 标准时间, -25200000
America/Cuiaba, 亚马逊时间, -14400000
America/Curacao, 大西洋标准时间, -14400000
America/Danmarkshavn, 格林威治时间, 0
America/Dawson, 太平洋标准时间, -28800000
America/Dawson_Creek, Mountain 标准时间, -25200000
America/Denver, Mountain 标准时间, -25200000
America/Detroit, 东部标准时间, -18000000
America/Dominica, 大西洋标准时间, -14400000
America/Edmonton, Mountain 标准时间, -25200000
America/Eirunepe, Acre 时间, -18000000
America/El_Salvador, 中央标准时间, -21600000
America/Ensenada, 太平洋标准时间, -28800000
America/Fort_Nelson, Mountain 标准时间, -25200000
America/Fort_Wayne, 东部标准时间, -18000000
America/Fortaleza, 巴西利亚时间, -10800000
America/Glace_Bay, 大西洋标准时间, -14400000
America/Godthab, 西格林兰岛时间, -10800000
America/Goose_Bay, 大西洋标准时间, -14400000
America/Grand_Turk, 大西洋标准时间, -14400000
America/Grenada, 大西洋标准时间, -14400000
America/Guadeloupe, 大西洋标准时间, -14400000
America/Guatemala, 中央标准时间, -21600000
America/Guayaquil, 厄瓜多尔时间, -18000000
America/Guyana, 圭亚那时间, -14400000
America/Halifax, 大西洋标准时间, -14400000
America/Havana, 古巴标准时间, -18000000
America/Hermosillo, Mountain 标准时间, -25200000
America/Indiana/Indianapolis, 东部标准时间, -18000000
America/Indiana/Knox, 中央标准时间, -21600000
America/Indiana/Marengo, 东部标准时间, -18000000
America/Indiana/Petersburg, 东部标准时间, -18000000
America/Indiana/Tell_City, 中央标准时间, -21600000
America/Indiana/Vevay, 东部标准时间, -18000000
America/Indiana/Vincennes, 东部标准时间, -18000000
America/Indiana/Winamac, 东部标准时间, -18000000
America/Indianapolis, 东部标准时间, -18000000
America/Inuvik, Mountain 标准时间, -25200000
America/Iqaluit, 东部标准时间, -18000000
America/Jamaica, 东部标准时间, -18000000
America/Jujuy, 阿根廷时间, -10800000
America/Juneau, 阿拉斯加标准时间, -32400000
America/Kentucky/Louisville, 东部标准时间, -18000000
America/Kentucky/Monticello, 东部标准时间, -18000000
America/Knox_IN, 中央标准时间, -21600000
America/Kralendijk, 大西洋标准时间, -14400000
America/La_Paz, 玻利维亚时间, -14400000
America/Lima, 秘鲁时间, -18000000
America/Los_Angeles, 太平洋标准时间, -28800000
America/Louisville, 东部标准时间, -18000000
America/Lower_Princes, 大西洋标准时间, -14400000
America/Maceio, 巴西利亚时间, -10800000
America/Managua, 中央标准时间, -21600000
America/Manaus, 亚马逊时间, -14400000
America/Marigot, 大西洋标准时间, -14400000
America/Martinique, 大西洋标准时间, -14400000
America/Matamoros, 中央标准时间, -21600000
America/Mazatlan, Mountain 标准时间, -25200000
America/Mendoza, 阿根廷时间, -10800000
America/Menominee, 中央标准时间, -21600000
America/Merida, 中央标准时间, -21600000
America/Metlakatla, 阿拉斯加标准时间, -32400000
America/Mexico_City, 中央标准时间, -21600000
America/Miquelon, 皮埃尔岛及密克隆岛标准时间, -10800000
America/Moncton, 大西洋标准时间, -14400000
America/Monterrey, 中央标准时间, -21600000
America/Montevideo, 乌拉圭时间, -10800000
America/Montreal, 东部标准时间, -18000000
America/Montserrat, 大西洋标准时间, -14400000
America/Nassau, 东部标准时间, -18000000
America/New_York, 东部标准时间, -18000000
America/Nipigon, 东部标准时间, -18000000
America/Nome, 阿拉斯加标准时间, -32400000
America/Noronha, 费尔南多德诺罗尼亚时间, -7200000
America/North_Dakota/Beulah, 中央标准时间, -21600000
America/North_Dakota/Center, 中央标准时间, -21600000
America/North_Dakota/New_Salem, 中央标准时间, -21600000
America/Ojinaga, Mountain 标准时间, -25200000
America/Panama, 东部标准时间, -18000000
America/Pangnirtung, 东部标准时间, -18000000
America/Paramaribo, 苏利南时间, -10800000
America/Phoenix, Mountain 标准时间, -25200000
America/Port-au-Prince, 东部标准时间, -18000000
America/Port_of_Spain, 大西洋标准时间, -14400000
America/Porto_Acre, Acre 时间, -18000000
America/Porto_Velho, 亚马逊时间, -14400000
America/Puerto_Rico, 大西洋标准时间, -14400000
America/Rainy_River, 中央标准时间, -21600000
America/Rankin_Inlet, 中央标准时间, -21600000
America/Recife, 巴西利亚时间, -10800000
America/Regina, 中央标准时间, -21600000
America/Resolute, 中央标准时间, -21600000
America/Rio_Branco, Acre 时间, -18000000
America/Rosario, 阿根廷时间, -10800000
America/Santa_Isabel, 太平洋标准时间, -28800000
America/Santarem, 巴西利亚时间, -10800000
America/Santiago, 智利时间, -10800000
America/Santo_Domingo, 大西洋标准时间, -14400000
America/Sao_Paulo, 巴西利亚时间, -10800000
America/Scoresbysund, 东格林岛时间, -3600000
America/Shiprock, Mountain 标准时间, -25200000
America/Sitka, 阿拉斯加标准时间, -32400000
America/St_Barthelemy, 大西洋标准时间, -14400000
America/St_Johns, 纽芬兰标准时间, -12600000
America/St_Kitts, 大西洋标准时间, -14400000
America/St_Lucia, 大西洋标准时间, -14400000
America/St_Thomas, 大西洋标准时间, -14400000
America/St_Vincent, 大西洋标准时间, -14400000
America/Swift_Current, 中央标准时间, -21600000
America/Tegucigalpa, 中央标准时间, -21600000
America/Thule, 大西洋标准时间, -14400000
America/Thunder_Bay, 东部标准时间, -18000000
America/Tijuana, 太平洋标准时间, -28800000
America/Toronto, 东部标准时间, -18000000
America/Tortola, 大西洋标准时间, -14400000
America/Vancouver, 太平洋标准时间, -28800000
America/Virgin, 大西洋标准时间, -14400000
America/Whitehorse, 太平洋标准时间, -28800000
America/Winnipeg, 中央标准时间, -21600000
America/Yakutat, 阿拉斯加标准时间, -32400000
America/Yellowknife, Mountain 标准时间, -25200000
Antarctica/Casey, 西部标准时间 (澳大利亚), 28800000
Antarctica/Davis, 戴维斯时间, 25200000
Antarctica/DumontDUrville, Dumont-d'Urville 时间, 36000000
Antarctica/Macquarie, 麦夸里岛时间, 39600000
Antarctica/Mawson, 莫森时间, 18000000
Antarctica/McMurdo, 新西兰标准时间, 43200000
Antarctica/Palmer, 智利时间, -10800000
Antarctica/Rothera, 罗瑟拉时间, -10800000
Antarctica/South_Pole, 新西兰标准时间, 43200000
Antarctica/Syowa, Syowa 时间, 10800000
Antarctica/Troll, 协调世界时间, 0
Antarctica/Vostok, 莫斯托克时间, 21600000
Arctic/Longyearbyen, 中欧时间, 3600000
Asia/Aden, 阿拉伯标准时间, 10800000
Asia/Almaty, Alma-Ata 时间, 21600000
Asia/Amman, 东欧时间, 7200000
Asia/Anadyr, 阿那底河时间, 43200000
Asia/Aqtau, Aqtau 时间, 18000000
Asia/Aqtobe, Aqtobe 时间, 18000000
Asia/Ashgabat, 土库曼时间, 18000000
Asia/Ashkhabad, 土库曼时间, 18000000
Asia/Baghdad, 阿拉伯标准时间, 10800000
Asia/Bahrain, 阿拉伯标准时间, 10800000
Asia/Baku, 亚塞拜然时间, 14400000
Asia/Bangkok, 印度支那时间, 25200000
Asia/Beirut, 东欧时间, 7200000
Asia/Bishkek, 吉尔吉斯斯坦时间, 21600000
Asia/Brunei, 文莱时间, 28800000
Asia/Calcutta, 印度标准时间, 19800000
Asia/Chita, 亚库次克时间, 32400000
Asia/Choibalsan, Choibalsan 时间, 28800000
Asia/Chongqing, 中国标准时间, 28800000
Asia/Chungking, 中国标准时间, 28800000
Asia/Colombo, 印度标准时间, 19800000
Asia/Dacca, 孟加拉时间, 21600000
Asia/Damascus, 东欧时间, 7200000
Asia/Dhaka, 孟加拉时间, 21600000
Asia/Dili, 东帝汶时间, 32400000
Asia/Dubai, 波斯湾标准时间, 14400000
Asia/Dushanbe, 塔吉克斯坦时间, 18000000
Asia/Gaza, 东欧时间, 7200000
Asia/Harbin, 中国标准时间, 28800000
Asia/Hebron, 东欧时间, 7200000
Asia/Ho_Chi_Minh, 印度支那时间, 25200000
Asia/Hong_Kong, 香港时间, 28800000
Asia/Hovd, 科布多时间, 25200000
Asia/Irkutsk, 伊尔库次克时间, 28800000
Asia/Istanbul, 东欧时间, 7200000
Asia/Jakarta, 西印度尼西亚时间, 25200000
Asia/Jayapura, 东印度尼西亚时间, 32400000
Asia/Jerusalem, 以色列标准时间, 7200000
Asia/Kabul, 阿富汗时间, 16200000
Asia/Kamchatka, 彼得罗巴甫洛夫斯克时间, 43200000
Asia/Karachi, 巴基斯坦时间, 18000000
Asia/Kashgar, 中国标准时间, 21600000
Asia/Kathmandu, 尼泊尔时间, 20700000
Asia/Katmandu, 尼泊尔时间, 20700000
Asia/Khandyga, 亚库次克时间, 32400000
Asia/Kolkata, 印度标准时间, 19800000
Asia/Krasnoyarsk, 克拉斯诺亚尔斯克时间, 25200000
Asia/Kuala_Lumpur, 马来西亚时间, 28800000
Asia/Kuching, 马来西亚时间, 28800000
Asia/Kuwait, 阿拉伯标准时间, 10800000
Asia/Macao, 中国标准时间, 28800000
Asia/Macau, 中国标准时间, 28800000
Asia/Magadan, Magadan 时间, 36000000
Asia/Makassar, 中部印度尼西亚时间, 28800000
Asia/Manila, 菲律宾时间, 28800000
Asia/Muscat, 波斯湾标准时间, 14400000
Asia/Nicosia, 东欧时间, 7200000
Asia/Novokuznetsk, 克拉斯诺亚尔斯克时间, 25200000
Asia/Novosibirsk, Novosibirsk 时间, 21600000
Asia/Omsk, 鄂木斯克时间, 21600000
Asia/Oral, Oral 时间, 18000000
Asia/Phnom_Penh, 印度支那时间, 25200000
Asia/Pontianak, 西印度尼西亚时间, 25200000
Asia/Pyongyang, 韩国标准时间, 30600000
Asia/Qatar, 阿拉伯标准时间, 10800000
Asia/Qyzylorda, Qyzylorda 时间, 21600000
Asia/Rangoon, 缅甸时间, 23400000
Asia/Riyadh, 阿拉伯标准时间, 10800000
Asia/Saigon, 印度支那时间, 25200000
Asia/Sakhalin, 库页岛时间, 36000000
Asia/Samarkand, 乌兹别克斯坦时间, 18000000
Asia/Seoul, 韩国标准时间, 32400000
Asia/Shanghai, 中国标准时间, 28800000
Asia/Singapore, 新加坡时间, 28800000
Asia/Srednekolymsk, Srednekolymsk Time, 39600000
Asia/Taipei, 中国标准时间, 28800000
Asia/Tashkent, 乌兹别克斯坦时间, 18000000
Asia/Tbilisi, 乔治亚时间, 14400000
Asia/Tehran, 伊朗标准时间, 12600000
Asia/Tel_Aviv, 以色列标准时间, 7200000
Asia/Thimbu, 不丹时间, 21600000
Asia/Thimphu, 不丹时间, 21600000
Asia/Tokyo, 日本标准时间, 32400000
Asia/Ujung_Pandang, 中部印度尼西亚时间, 28800000
Asia/Ulaanbaatar, 库伦时间, 28800000
Asia/Ulan_Bator, 库伦时间, 28800000
Asia/Urumqi, 中国标准时间, 21600000
Asia/Ust-Nera, 乌斯季涅拉时间, 36000000
Asia/Vientiane, 印度支那时间, 25200000
Asia/Vladivostok, 海参崴时间, 36000000
Asia/Yakutsk, 亚库次克时间, 32400000
Asia/Yekaterinburg, Yekaterinburg 时间, 18000000
Asia/Yerevan, 亚美尼亚时间, 14400000
Atlantic/Azores, 亚速尔群岛时间, -3600000
Atlantic/Bermuda, 大西洋标准时间, -14400000
Atlantic/Canary, 西欧时间, 0
Atlantic/Cape_Verde, 佛德角时间, -3600000
Atlantic/Faeroe, 西欧时间, 0
Atlantic/Faroe, 西欧时间, 0
Atlantic/Jan_Mayen, 中欧时间, 3600000
Atlantic/Madeira, 西欧时间, 0
Atlantic/Reykjavik, 格林威治时间, 0
Atlantic/South_Georgia, 南乔治亚标准时间, -7200000
Atlantic/St_Helena, 格林威治时间, 0
Atlantic/Stanley, 福克兰群岛时间, -10800000
Australia/ACT, 东部标准时间 (新南威尔斯), 36000000
Australia/Adelaide, 中央标准时间 (南澳大利亚), 34200000
Australia/Brisbane, 东部标准时间 (昆士兰), 36000000
Australia/Broken_Hill, 中央标准时间 (南澳大利亚/新南威尔斯), 34200000
Australia/Canberra, 东部标准时间 (新南威尔斯), 36000000
Australia/Currie, 东部标准时间 (新南威尔斯), 36000000
Australia/Darwin, 中央标准时间 (北领地), 34200000
Australia/Eucla, 中西部标准时间 (澳大利亚), 31500000
Australia/Hobart, 东部标准时间 (塔斯马尼亚), 36000000
Australia/LHI, 豪公标准时间, 37800000
Australia/Lindeman, 东部标准时间 (昆士兰), 36000000
Australia/Lord_Howe, 豪公标准时间, 37800000
Australia/Melbourne, 东部标准时间 (维多利亚), 36000000
Australia/NSW, 东部标准时间 (新南威尔斯), 36000000
Australia/North, 中央标准时间 (北领地), 34200000
Australia/Perth, 西部标准时间 (澳大利亚), 28800000
Australia/Queensland, 东部标准时间 (昆士兰), 36000000
Australia/South, 中央标准时间 (南澳大利亚), 34200000
Australia/Sydney, 东部标准时间 (新南威尔斯), 36000000
Australia/Tasmania, 东部标准时间 (塔斯马尼亚), 36000000
Australia/Victoria, 东部标准时间 (维多利亚), 36000000
Australia/West, 西部标准时间 (澳大利亚), 28800000
Australia/Yancowinna, 中央标准时间 (南澳大利亚/新南威尔斯), 34200000
Brazil/Acre, Acre 时间, -18000000
Brazil/DeNoronha, 费尔南多德诺罗尼亚时间, -7200000
Brazil/East, 巴西利亚时间, -10800000
Brazil/West, 亚马逊时间, -14400000
CET, 中欧时间, 3600000
CST6CDT, 中央标准时间, -21600000
Canada/Atlantic, 大西洋标准时间, -14400000
Canada/Central, 中央标准时间, -21600000
Canada/East-Saskatchewan, 中央标准时间, -21600000
Canada/Eastern, 东部标准时间, -18000000
Canada/Mountain, Mountain 标准时间, -25200000
Canada/Newfoundland, 纽芬兰标准时间, -12600000
Canada/Pacific, 太平洋标准时间, -28800000
Canada/Saskatchewan, 中央标准时间, -21600000
Canada/Yukon, 太平洋标准时间, -28800000
Chile/Continental, 智利时间, -10800000
Chile/EasterIsland, 复活岛时间, -18000000
Cuba, 古巴标准时间, -18000000
EET, 东欧时间, 7200000
EST5EDT, 东部标准时间, -18000000
Egypt, 东欧时间, 7200000
Eire, 格林威治时间, 0
Etc/GMT, 格林威治时间, 0
Etc/GMT+0, 格林威治时间, 0
Etc/GMT+1, GMT-01:00, -3600000
Etc/GMT+10, GMT-10:00, -36000000
Etc/GMT+11, GMT-11:00, -39600000
Etc/GMT+12, GMT-12:00, -43200000
Etc/GMT+2, GMT-02:00, -7200000
Etc/GMT+3, GMT-03:00, -10800000
Etc/GMT+4, GMT-04:00, -14400000
Etc/GMT+5, GMT-05:00, -18000000
Etc/GMT+6, GMT-06:00, -21600000
Etc/GMT+7, GMT-07:00, -25200000
Etc/GMT+8, GMT-08:00, -28800000
Etc/GMT+9, GMT-09:00, -32400000
Etc/GMT-0, 格林威治时间, 0
Etc/GMT-1, GMT+01:00, 3600000
Etc/GMT-10, GMT+10:00, 36000000
Etc/GMT-11, GMT+11:00, 39600000
Etc/GMT-12, GMT+12:00, 43200000
Etc/GMT-13, GMT+13:00, 46800000
Etc/GMT-14, GMT+14:00, 50400000
Etc/GMT-2, GMT+02:00, 7200000
Etc/GMT-3, GMT+03:00, 10800000
Etc/GMT-4, GMT+04:00, 14400000
Etc/GMT-5, GMT+05:00, 18000000
Etc/GMT-6, GMT+06:00, 21600000
Etc/GMT-7, GMT+07:00, 25200000
Etc/GMT-8, GMT+08:00, 28800000
Etc/GMT-9, GMT+09:00, 32400000
Etc/GMT0, 格林威治时间, 0
Etc/Greenwich, 格林威治时间, 0
Etc/UCT, 协调世界时间, 0
Etc/UTC, 协调世界时间, 0
Etc/Universal, 协调世界时间, 0
Etc/Zulu, 协调世界时间, 0
Europe/Amsterdam, 中欧时间, 3600000
Europe/Andorra, 中欧时间, 3600000
Europe/Athens, 东欧时间, 7200000
Europe/Belfast, 格林威治时间, 0
Europe/Belgrade, 中欧时间, 3600000
Europe/Berlin, 中欧时间, 3600000
Europe/Bratislava, 中欧时间, 3600000
Europe/Brussels, 中欧时间, 3600000
Europe/Bucharest, 东欧时间, 7200000
Europe/Budapest, 中欧时间, 3600000
Europe/Busingen, 中欧时间, 3600000
Europe/Chisinau, 东欧时间, 7200000
Europe/Copenhagen, 中欧时间, 3600000
Europe/Dublin, 格林威治时间, 0
Europe/Gibraltar, 中欧时间, 3600000
Europe/Guernsey, 格林威治时间, 0
Europe/Helsinki, 东欧时间, 7200000
Europe/Isle_of_Man, 格林威治时间, 0
Europe/Istanbul, 东欧时间, 7200000
Europe/Jersey, 格林威治时间, 0
Europe/Kaliningrad, 东欧时间, 7200000
Europe/Kiev, 东欧时间, 7200000
Europe/Lisbon, 西欧时间, 0
Europe/Ljubljana, 中欧时间, 3600000
Europe/London, 格林威治时间, 0
Europe/Luxembourg, 中欧时间, 3600000
Europe/Madrid, 中欧时间, 3600000
Europe/Malta, 中欧时间, 3600000
Europe/Mariehamn, 东欧时间, 7200000
Europe/Minsk, 莫斯科标准时间, 10800000
Europe/Monaco, 中欧时间, 3600000
Europe/Moscow, 莫斯科标准时间, 10800000
Europe/Nicosia, 东欧时间, 7200000
Europe/Oslo, 中欧时间, 3600000
Europe/Paris, 中欧时间, 3600000
Europe/Podgorica, 中欧时间, 3600000
Europe/Prague, 中欧时间, 3600000
Europe/Riga, 东欧时间, 7200000
Europe/Rome, 中欧时间, 3600000
Europe/Samara, 沙马拉时间, 14400000
Europe/San_Marino, 中欧时间, 3600000
Europe/Sarajevo, 中欧时间, 3600000
Europe/Simferopol, 莫斯科标准时间, 10800000
Europe/Skopje, 中欧时间, 3600000
Europe/Sofia, 东欧时间, 7200000
Europe/Stockholm, 中欧时间, 3600000
Europe/Tallinn, 东欧时间, 7200000
Europe/Tirane, 中欧时间, 3600000
Europe/Tiraspol, 东欧时间, 7200000
Europe/Uzhgorod, 东欧时间, 7200000
Europe/Vaduz, 中欧时间, 3600000
Europe/Vatican, 中欧时间, 3600000
Europe/Vienna, 中欧时间, 3600000
Europe/Vilnius, 东欧时间, 7200000
Europe/Volgograd, 莫斯科标准时间, 10800000
Europe/Warsaw, 中欧时间, 3600000
Europe/Zagreb, 中欧时间, 3600000
Europe/Zaporozhye, 东欧时间, 7200000
Europe/Zurich, 中欧时间, 3600000
GB, 格林威治时间, 0
GB-Eire, 格林威治时间, 0
GMT, 格林威治时间, 0
GMT0, 格林威治时间, 0
Greenwich, 格林威治时间, 0
Hongkong, 香港时间, 28800000
Iceland, 格林威治时间, 0
Indian/Antananarivo, 东非时间, 10800000
Indian/Chagos, 印度洋地带时间, 21600000
Indian/Christmas, 圣诞岛时间, 25200000
Indian/Cocos, 可可斯群岛时间, 23400000
Indian/Comoro, 东非时间, 10800000
Indian/Kerguelen, 法属南极时间, 18000000
Indian/Mahe, 塞席尔群岛时间, 14400000
Indian/Maldives, 马尔代夫时间, 18000000
Indian/Mauritius, 摩里西斯时间, 14400000
Indian/Mayotte, 东非时间, 10800000
Indian/Reunion, 留尼旺岛时间, 14400000
Iran, 伊朗标准时间, 12600000
Israel, 以色列标准时间, 7200000
Jamaica, 东部标准时间, -18000000
Japan, 日本标准时间, 32400000
Kwajalein, 马绍尔群岛时间, 43200000
Libya, 东欧时间, 7200000
MET, 中欧时间, 3600000
MST7MDT, Mountain 标准时间, -25200000
Mexico/BajaNorte, 太平洋标准时间, -28800000
Mexico/BajaSur, Mountain 标准时间, -25200000
Mexico/General, 中央标准时间, -21600000
NZ, 新西兰标准时间, 43200000
NZ-CHAT, 查萨姆标准时间, 45900000
Navajo, Mountain 标准时间, -25200000
PRC, 中国标准时间, 28800000
PST8PDT, 太平洋标准时间, -28800000
Pacific/Apia, 西萨摩亚时间, 46800000
Pacific/Auckland, 新西兰标准时间, 43200000
Pacific/Bougainville, Bougainville Standard Time, 39600000
Pacific/Chatham, 查萨姆标准时间, 45900000
Pacific/Chuuk, 丘克时间, 36000000
Pacific/Easter, 复活岛时间, -18000000
Pacific/Efate, 瓦奴阿图时间, 39600000
Pacific/Enderbury, 菲尼克斯群岛时间, 46800000
Pacific/Fakaofo, 托克劳群岛时间, 46800000
Pacific/Fiji, 斐济时间, 43200000
Pacific/Funafuti, 吐鲁瓦时间, 43200000
Pacific/Galapagos, 加拉巴哥时间, -21600000
Pacific/Gambier, 冈比亚时间, -32400000
Pacific/Guadalcanal, 所罗门群岛时间, 39600000
Pacific/Guam, Chamorro 标准时间, 36000000
Pacific/Honolulu, 夏威夷标准时间, -36000000
Pacific/Johnston, 夏威夷标准时间, -36000000
Pacific/Kiritimati, Line 岛时间, 50400000
Pacific/Kosrae, Kosrae 时间, 39600000
Pacific/Kwajalein, 马绍尔群岛时间, 43200000
Pacific/Majuro, 马绍尔群岛时间, 43200000
Pacific/Marquesas, 马克萨斯时间, -34200000
Pacific/Midway, 萨摩亚群岛标准时间, -39600000
Pacific/Nauru, 诺鲁时间, 43200000
Pacific/Niue, 纽威岛时间, -39600000
Pacific/Norfolk, 诺福克时间, 39600000
Pacific/Noumea, 新加勒多尼亚时间, 39600000
Pacific/Pago_Pago, 萨摩亚群岛标准时间, -39600000
Pacific/Palau, 帛琉时间, 32400000
Pacific/Pitcairn, 皮特康岛标准时间, -28800000
Pacific/Pohnpei, 波纳佩时间, 39600000
Pacific/Ponape, 波纳佩时间, 39600000
Pacific/Port_Moresby, 巴布亚新几内亚时间, 36000000
Pacific/Rarotonga, 库克群岛时间, -36000000
Pacific/Saipan, Chamorro 标准时间, 36000000
Pacific/Samoa, 萨摩亚群岛标准时间, -39600000
Pacific/Tahiti, 大溪地岛时间, -36000000
Pacific/Tarawa, 吉伯特群岛时间, 43200000
Pacific/Tongatapu, 东加时间, 46800000
Pacific/Truk, 丘克时间, 36000000
Pacific/Wake, 威克时间, 43200000
Pacific/Wallis, 瓦利斯及福杜纳群岛时间, 43200000
Pacific/Yap, 丘克时间, 36000000
Poland, 中欧时间, 3600000
Portugal, 西欧时间, 0
ROK, 韩国标准时间, 32400000
Singapore, 新加坡时间, 28800000
SystemV/AST4, 大西洋标准时间, -14400000
SystemV/AST4ADT, 大西洋标准时间, -14400000
SystemV/CST6, 中央标准时间, -21600000
SystemV/CST6CDT, 中央标准时间, -21600000
SystemV/EST5, 东部标准时间, -18000000
SystemV/EST5EDT, 东部标准时间, -18000000
SystemV/HST10, 夏威夷标准时间, -36000000
SystemV/MST7, Mountain 标准时间, -25200000
SystemV/MST7MDT, Mountain 标准时间, -25200000
SystemV/PST8, 太平洋标准时间, -28800000
SystemV/PST8PDT, 太平洋标准时间, -28800000
SystemV/YST9, 阿拉斯加标准时间, -32400000
SystemV/YST9YDT, 阿拉斯加标准时间, -32400000
Turkey, 东欧时间, 7200000
UCT, 协调世界时间, 0
US/Alaska, 阿拉斯加标准时间, -32400000
US/Aleutian, 夏威夷标准时间, -36000000
US/Arizona, Mountain 标准时间, -25200000
US/Central, 中央标准时间, -21600000
US/East-Indiana, 东部标准时间, -18000000
US/Eastern, 东部标准时间, -18000000
US/Hawaii, 夏威夷标准时间, -36000000
US/Indiana-Starke, 中央标准时间, -21600000
US/Michigan, 东部标准时间, -18000000
US/Mountain, Mountain 标准时间, -25200000
US/Pacific, 太平洋标准时间, -28800000
US/Pacific-New, 太平洋标准时间, -28800000
US/Samoa, 萨摩亚群岛标准时间, -39600000
UTC, 协调世界时间, 0
Universal, 协调世界时间, 0
W-SU, 莫斯科标准时间, 10800000
WET, 西欧时间, 0
Zulu, 协调世界时间, 0
EST, 东部标准时间, -18000000
HST, 夏威夷标准时间, -36000000
MST, Mountain 标准时间, -25200000
ACT, 中央标准时间 (北领地), 34200000
AET, 东部标准时间 (新南威尔斯), 36000000
AGT, 阿根廷时间, -10800000
ART, 东欧时间, 7200000
AST, 阿拉斯加标准时间, -32400000
BET, 巴西利亚时间, -10800000
BST, 孟加拉时间, 21600000
CAT, 中非时间, 7200000
CNT, 纽芬兰标准时间, -12600000
CST, 中央标准时间, -21600000
CTT, 中国标准时间, 28800000
EAT, 东非时间, 10800000
ECT, 中欧时间, 3600000
IET, 东部标准时间, -18000000
IST, 印度标准时间, 19800000
JST, 日本标准时间, 32400000
MIT, 西萨摩亚时间, 46800000
NET, 亚美尼亚时间, 14400000
NST, 新西兰标准时间, 43200000
PLT, 巴基斯坦时间, 18000000
PNT, Mountain 标准时间, -25200000
PRT, 大西洋标准时间, -14400000
PST, 太平洋标准时间, -28800000
SST, 所罗门群岛时间, 39600000
VST, 印度支那时间, 25200000
============================
 */
