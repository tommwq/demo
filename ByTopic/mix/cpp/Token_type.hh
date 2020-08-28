#pragma once

#include <string>

namespace mix {
    enum class Token_type {
                           Instrument,  // ָ����STJ
                           Symbol,      // ���ţ���X
                           Number,      // ��ֵ����10
                           Label,       // ��ţ���2H
                           Command,     // �����ORIG
                           Star,        // �Ǻţ���*
                           Plus,        // �Ӻţ���+
                           Minus,       // ���ţ���-
                           Comma,       // ���ţ���,
                           Unknown,     // δ֪����
    };

    Token_type get_token_type(const std::string& lowercase);
    std::string get_token_type_name(Token_type t);
}
